package WoWSFT.service

import WoWSFT.model.Constant.*
import WoWSFT.model.gameparams.consumable.Consumable
import WoWSFT.model.gameparams.modernization.Modernization
import WoWSFT.model.gameparams.ship.Ship
import WoWSFT.model.gameparams.ship.component.artillery.Shell
import WoWSFT.model.gameparams.ship.component.planes.Plane
import WoWSFT.model.gameparams.ship.component.torpedo.TorpedoAmmo
import WoWSFT.utils.CommonUtils
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.util.*
import java.util.zip.ZipFile

@Service
class GPService(
    @Qualifier(TYPE_CONSUMABLE) private val consumables: LinkedHashMap<String, Consumable>,
    @Qualifier(TYPE_UPGRADE) private val upgrades: LinkedHashMap<Int, LinkedHashMap<String?, Modernization>>,
    @Qualifier(TYPE_SHIP) private val zShip: String,
    @Qualifier(TYPE_SHELL) private val zShell: String
)
{
    companion object {
        private val mapper = ObjectMapper()
        private val commonUtils = CommonUtils
    }

//    @Cacheable(value = "ship", key = "#index")
    @Throws(Exception::class)
    fun getShip(index: String?): Ship
    {
        val zFile = ZipFile(zShip)
        val ship = commonUtils.zFetch(zFile, index!!, Ship::class.java) as Ship?
        zFile.close()

        if (ship != null) {
            setUpgrades(ship)
            setConsumables(ship)

            return ship
        }
        throw NullPointerException()
    }

    private fun setUpgrades(ship: Ship)
    {
        val upgradesList = mutableListOf<MutableList<Modernization>>()
        upgrades.forEach { (_, upgrades) ->
            upgrades.forEach { (_, upgrade) ->
                if ((!upgrade.excludes.contains(ship.name) && upgrade.group.contains(ship.group)
                            && upgrade.nation.contains(ship.typeinfo.nation) && upgrade.shiptype.contains(ship.typeinfo.species)
                            && upgrade.shiplevel.contains(ship.level)) || upgrade.ships.contains(ship.name)) {
                    if (upgradesList.size < upgrade.slot + 1) {
                        upgradesList.add(mutableListOf())
                    }
                    upgradesList[upgrade.slot].add(upgrade)
                }
            }
        }

        val maxRows = upgradesList.maxBy { it.size > 0 }.orEmpty().size
//            .stream()
//            .mapToInt { obj -> obj.size }
//            .filter { slot -> slot > 0 }.max()
//            .orElse(0)
        ship.upgradesRow = maxRows
        ship.upgrades = upgradesList
    }

    @Throws(Exception::class)
    private fun setConsumables(ship: Ship)
    {
        val consumablesList = mutableListOf<MutableList<Consumable>>()
        for ((_, value) in ship.shipAbilities) {
            for (consumable in value.abils) {
                if (!consumable[0].contains("Super")) {
                    val tempConsumable = mapper.readValue(mapper.writeValueAsString(consumables[consumable[0]]), Consumable::class.java)
                    tempConsumable.subConsumables.entries.removeIf { e -> !e.key.equals(consumable[1], ignoreCase = true) }

                    if (consumablesList.size < value.slot + 1) {
                        consumablesList.add(mutableListOf())
                    }
                    consumablesList[value.slot].add(tempConsumable)
                }
            }
        }
        ship.consumables = consumablesList
    }

    @Throws(Exception::class)
    fun setShipAmmo(ship: Ship)
    {
        val zFile = ZipFile(zShip)

        if (ship.components.artillery.size > 0 && ship.components.artillery[ship.modules[artillery]] != null) {
            for (ammo in ship.components.artillery[ship.modules[artillery]]!!.turrets[0].ammoList) {
                val shell = commonUtils.zFetch(zFile, ammo, Shell::class.java) as Shell
                ship.components.artillery[ship.modules[artillery]]!!.shells[ammo] = shell
            }
        }

        if (ship.components.torpedoes.size > 0 && ship.components.torpedoes[ship.modules[torpedoes]] != null) {
            ship.components.torpedoes[ship.modules[torpedoes]]!!.launchers[0].ammoList.forEach { ammo ->
                ship.components.torpedoes[ship.modules[torpedoes]]!!.ammo.add(commonUtils.zFetch(zFile, ammo, TorpedoAmmo::class.java) as TorpedoAmmo)
            }
        }

        if (ship.components.airSupport.size > 0 && ship.components.airSupport[ship.modules[airSupport]] != null) {
            val plane = commonUtils.zFetch(zFile, ship.components.airSupport[ship.modules[airSupport]]!!.planeName, Plane::class.java) as Plane?
            if (plane != null) {
                plane.bomb = commonUtils.zFetch(zFile, plane.bombName, Shell::class.java) as Shell

                ship.components.airSupport[ship.modules[airSupport]!!]!!.plane = plane
            }
        }

        if (ship.components.atba.size > 0 && ship.components.atba[ship.modules[atba]] != null) {
            for ((_, value) in ship.components.atba[ship.modules[atba]]!!.secondaries) {
                val ammo = commonUtils.zFetch(zFile, value.ammoList[0], Shell::class.java) as Shell?
                if (ammo != null) {
                    value.alphaDamage = ammo.alphaDamage
                    value.alphaPiercingHE = ammo.alphaPiercingHE
                    value.ammoType = ammo.ammoType
                    value.bulletSpeed = ammo.bulletSpeed
                    value.burnProb = ammo.burnProb
                }
            }
        }

        if (ship.planes.size > 0) {
            if (ship.modules[diveBomber] != null && ship.planes[ship.modules[diveBomber]!!] != null) {
                val dbPlane = commonUtils.zFetch(zFile, ship.planes[ship.modules[diveBomber]!!]!!, Plane::class.java) as Plane?
                if (dbPlane != null) {
                    dbPlane.bomb = commonUtils.zFetch(zFile, dbPlane.bombName, Shell::class.java) as Shell
                    setPlaneConsumables(dbPlane)
                    ship.components.diveBomber[ship.modules[diveBomber]!!] = dbPlane
                }
            }

            if (ship.modules[fighter] != null && ship.planes[ship.modules[fighter]!!] != null) {
                val fPlane = commonUtils.zFetch(zFile, ship.planes[ship.modules[fighter]!!]!!, Plane::class.java) as Plane?
                if (fPlane != null) {
                    fPlane.rocket = commonUtils.zFetch(zFile, fPlane.bombName, Shell::class.java) as Shell
                    setPlaneConsumables(fPlane)
                    ship.components.fighter[ship.modules[fighter]!!] = fPlane
                }
            }

            if (ship.modules[skipBomber] != null && ship.planes[ship.modules[skipBomber]!!] != null) {
                val sbPlane = commonUtils.zFetch(zFile, ship.planes[ship.modules[skipBomber]!!]!!, Plane::class.java) as Plane?
                if (sbPlane != null) {
                    sbPlane.skip = commonUtils.zFetch(zFile, sbPlane.bombName, Shell::class.java) as Shell
                    setPlaneConsumables(sbPlane)
                    ship.components.skipBomber[ship.modules[skipBomber]!!] = sbPlane
                }
            }

            if (ship.modules[torpedoBomber] != null && ship.planes[ship.modules[torpedoBomber]!!] != null) {
                val tbPlane = commonUtils.zFetch(zFile, ship.planes[ship.modules[torpedoBomber]!!]!!, Plane::class.java) as Plane?
                if (tbPlane != null) {
                    tbPlane.torpedo = commonUtils.zFetch(zFile, tbPlane.bombName, TorpedoAmmo::class.java) as TorpedoAmmo
                    setPlaneConsumables(tbPlane)
                    ship.components.torpedoBomber[ship.modules[torpedoBomber]!!] = tbPlane
                }
            }
        }
        zFile.close()
    }

    @Throws(Exception::class)
    private fun setPlaneConsumables(plane: Plane)
    {
        val consumablesList = mutableListOf<Consumable>()
        for ((_, value) in plane.planeAbilities) {
            for (consumable in value.abils) {
                if (!consumable[0].contains("Super")) {
                    val tempConsumable = mapper.readValue(mapper.writeValueAsString(consumables[consumable[0]]), Consumable::class.java)
                    tempConsumable.subConsumables.entries.removeIf { e -> !e.key.equals(consumable[1], ignoreCase = true) }
                    consumablesList.add(tempConsumable)
                }
            }
        }
        plane.consumables = consumablesList
    }

    @Throws(Exception::class)
    fun getArtyAmmoOnly(index: String, artyId: String): Shell?
    {
        var zFile = ZipFile(zShip)
        val ship = commonUtils.zFetch(zFile, index, Ship::class.java) as Ship?
        zFile.close()

        if (ship != null && ship.shipUpgradeInfo.components[artillery]!!.size > 0) {
            for (su in ship.shipUpgradeInfo.components[artillery]!!) {
                if (su.name.equals(artyId, ignoreCase = true)) {
                    val tempId = su.components[artillery]!![su.components[artillery]!!.size - 1]
                    for (ammo in ship.components.artillery[tempId]!!.turrets[0].ammoList) {
                        zFile = ZipFile(zShell)
                        val shell = commonUtils.zFetch(zFile, ammo, Shell::class.java) as Shell?
                        zFile.close()

                        if (shell != null) { return shell }
                    }
                }
            }
        }
        return null
    }

}