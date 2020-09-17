package WoWSFT.service

import WoWSFT.model.Constant.TYPE_CONSUMABLE
import WoWSFT.model.Constant.TYPE_SHELL
import WoWSFT.model.Constant.TYPE_SHIP
import WoWSFT.model.Constant.TYPE_UPGRADE
import WoWSFT.model.Constant.artillery
import WoWSFT.model.Constant.atba
import WoWSFT.model.Constant.diveBomber
import WoWSFT.model.Constant.fighter
import WoWSFT.model.Constant.torpedoBomber
import WoWSFT.model.Constant.torpedoes
import WoWSFT.model.gameparams.consumable.Consumable
import WoWSFT.model.gameparams.modernization.Modernization
import WoWSFT.model.gameparams.ship.Ship
import WoWSFT.model.gameparams.ship.component.artillery.Shell
import WoWSFT.model.gameparams.ship.component.planes.Plane
import WoWSFT.model.gameparams.ship.component.torpedo.TorpedoAmmo
import WoWSFT.utils.CommonUtils
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*
import java.util.zip.ZipFile

@Service
class GPService(
    @Qualifier(TYPE_CONSUMABLE) private val consumables: LinkedHashMap<String, Consumable>,
    @Qualifier(TYPE_UPGRADE) private val upgrades: LinkedHashMap<Int, LinkedHashMap<String?, Modernization>>,
    @Qualifier(TYPE_SHIP) private val zShip: String,
    @Qualifier(TYPE_SHELL) private val zShell: String
) {
    companion object {
        private val mapper = jacksonObjectMapper()
        private val commonUtils = CommonUtils
    }

    @Throws(Exception::class)
    fun getShip(index: String): Ship {
        return ZipFile(zShip).run {
            commonUtils.zFetch(this, index, jacksonTypeRef<Ship>()).also { this.close() }
        }?.run {
            setUpgrades(this)
            setConsumables(this)
            this
        } ?: throw NullPointerException()
    }

    private fun setUpgrades(ship: Ship) {
        val upgradesList = mutableListOf<MutableList<Modernization>>()
        upgrades.forEach { (_, upgradesInSlot) ->
            upgradesInSlot.forEach { (_, upgrade) ->
                if ((upgrade.excludes.contains(ship.name).not()
                            && upgrade.group.contains(ship.group)
                            && upgrade.nation.contains(ship.typeinfo.nation)
                            && upgrade.shiptype.contains(ship.typeinfo.species)
                            && upgrade.shiplevel.contains(ship.level))
                    || upgrade.ships.contains(ship.name)
                ) {
                    if (upgradesList.size < upgrade.slot + 1) {
                        upgradesList.add(mutableListOf())
                    }
                    upgradesList[upgrade.slot].add(upgrade)
                }
            }
        }

        ship.upgradesRow = upgradesList.maxBy { it.size > 0 }.orEmpty().size
        ship.upgrades = upgradesList
    }

    @Throws(Exception::class)
    private fun setConsumables(ship: Ship) {
        val consumablesList = mutableListOf<MutableList<Consumable>>()
        for ((_, value) in ship.shipAbilities) {
            for (consumable in value.abils) {
                if (consumable[0].contains("Super").not()) {
                    mapper.readValue(mapper.writeValueAsString(consumables[consumable[0]]), jacksonTypeRef<Consumable>()).apply {
                        subConsumables.entries.removeIf { e -> !e.key.equals(consumable[1], ignoreCase = true) }
                        if (consumablesList.size < value.slot + 1) {
                            consumablesList.add(mutableListOf())
                        }
                        consumablesList[value.slot].add(this)
                    }
                }
            }
        }
        ship.consumables = consumablesList
    }

    @Throws(Exception::class)
    fun setShipAmmo(ship: Ship) {
        val zFile = ZipFile(zShip)

        if (ship.components.artillery.size > 0 && ship.components.artillery[ship.modules[artillery]] != null) {
            for (ammo in ship.components.artillery[ship.modules[artillery]]!!.turrets[0].ammoList) {
                ship.components.artillery[ship.modules[artillery]]!!.shells[ammo] = commonUtils.zFetch(zFile, ammo, jacksonTypeRef<Shell>())!!
            }
        }

        if (ship.components.torpedoes.size > 0 && ship.components.torpedoes[ship.modules[torpedoes]] != null) {
            val ammo = ship.components.torpedoes[ship.modules[torpedoes]]!!.launchers[0].ammoList[0]
            ship.components.torpedoes[ship.modules[torpedoes]]!!.ammo = commonUtils.zFetch(zFile, ammo, jacksonTypeRef<TorpedoAmmo>())!!
        }

        if (ship.components.atba.size > 0 && ship.components.atba[ship.modules[atba]] != null) {
            for ((_, value) in ship.components.atba[ship.modules[atba]]!!.secondaries) {
                commonUtils.zFetch(zFile, value.ammoList[0], jacksonTypeRef<Shell>())?.run {
                    value.alphaDamage = alphaDamage
                    value.alphaPiercingHE = alphaPiercingHE
                    value.ammoType = ammoType
                    value.bulletSpeed = bulletSpeed
                    value.burnProb = burnProb
                }
            }
        }

        if (ship.planes.size > 0) {
            val planes = listOf(diveBomber, fighter, torpedoBomber)
            planes.forEach { plane ->
                if (ship.modules[plane] != null && ship.planes[ship.modules[plane]!!] != null) {
                    commonUtils.zFetch(zFile, ship.planes[ship.modules[plane]!!]!!, jacksonTypeRef<Plane>())?.run {
                        this[plane] = commonUtils.zFetch(zFile, bombName, jacksonTypeRef())
                        setPlaneConsumables(this)
                        ship.components[plane][ship.modules[plane]!!] = this
                    }
                }
            }
        }
        zFile.close()
    }

    @Throws(Exception::class)
    private fun setPlaneConsumables(plane: Plane) {
        val consumablesList = mutableListOf<Consumable>()
        for ((_, value) in plane.planeAbilities) {
            for (consumable in value.abils) {
                if (consumable[0].contains("Super").not()) {
                    consumablesList.add(
                        mapper.readValue(mapper.writeValueAsString(consumables[consumable[0]]), jacksonTypeRef<Consumable>()).apply {
                            subConsumables.entries.removeIf { e -> !e.key.equals(consumable[1], ignoreCase = true) }
                        }
                    )
                }
            }
        }
        plane.consumables = consumablesList
    }

    @Throws(Exception::class)
    fun getArtyAmmoOnly(index: String, artyId: String): Shell? {
        ZipFile(zShip).run {
            commonUtils.zFetch(this, index, jacksonTypeRef<Ship>()).apply { close() }
        }?.run {
            shipUpgradeInfo.components[artillery].takeIf { it.isNullOrEmpty().not() }?.forEach { su ->
                if (su.name.equals(artyId, ignoreCase = true)) {
                    components.artillery[su.components[artillery]!![su.components[artillery]!!.size - 1]]!!.turrets[0].ammoList.forEach { ammo ->
                        ZipFile(zShell).run {
                            commonUtils.zFetch(this, ammo, jacksonTypeRef<Shell>()).apply { close() }
                        }?.run { return this }
                    }
                }
            }
        }
        return null
    }
}