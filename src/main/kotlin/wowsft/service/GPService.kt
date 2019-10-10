package wowsft.service

import wowsft.model.gameparams.commander.Commander
import wowsft.model.gameparams.consumable.Consumable
import wowsft.model.gameparams.modernization.Modernization
import wowsft.model.gameparams.ship.Ship
import wowsft.model.gameparams.ship.ShipIndex
import wowsft.model.gameparams.ship.abilities.AbilitySlot
import wowsft.model.gameparams.ship.component.artillery.Shell
import wowsft.model.gameparams.ship.component.atba.Secondary
import wowsft.model.gameparams.ship.component.planes.Plane
import wowsft.model.gameparams.ship.component.torpedo.TorpedoAmmo
import wowsft.model.gameparams.ship.upgrades.ShipUpgrade
import wowsft.utils.PenetrationUtils
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

import java.util.*

import wowsft.model.Constant

/**
 * Created by Aesis on 2016-12-05.
 */
@Service
class GPService(
        @Autowired @Qualifier(value = "gameParamsHM") private val gameParamsHM: HashMap<String, Any>,
        @Autowired @Qualifier(value = "global") private val global: HashMap<String, HashMap<String, Any>>,
        @Autowired @Qualifier(value = Constant.TYPE_SHIP) private val ships: LinkedHashMap<String, Ship>,
        @Autowired @Qualifier(value = Constant.TYPE_CONSUMABLE) private val consumables: LinkedHashMap<String, Consumable>,
        @Autowired @Qualifier(value = Constant.TYPE_SHIP_LIST) private val shipsList: LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>,
        @Autowired @Qualifier(value = Constant.TYPE_UPGRADE) private val upgrades: LinkedHashMap<Int, LinkedHashMap<String, Modernization>>,
        @Autowired @Qualifier(value = Constant.TYPE_COMMANDER) private val commanders: LinkedHashMap<String, Commander>,
        @Autowired private val paramService: ParamService
)
{
    private val mapper = ObjectMapper()

    @Cacheable("ship", key = "#index")
    @Throws(Exception::class)
    fun getShip(index: String): Ship
    {
        val ship = mapper.readValue(mapper.writeValueAsString(ships[index]), Ship::class.java)

        if (ship != null) {
            setUpgrades(ship)
            setConsumables(ship)

            return ship
        }
        throw NullPointerException()
    }

    private fun setUpgrades(ship: Ship)
    {
        val upgradesList = ArrayList<MutableList<Modernization>>()

        upgrades.forEach { (_, upgrades) ->
            upgrades.forEach { (_, upgrade) ->
                if ((!upgrade.excludes.contains(ship.name) && upgrade.group.contains(ship.group) && upgrade.nation.contains(ship.typeinfo!!.nation)
                                && upgrade.shiptype.contains(ship.typeinfo!!.species) && upgrade.shiplevel.contains(ship.level)) || upgrade.ships.contains(ship.name)) {
                    if (upgradesList.size < upgrade.slot + 1) {
                        upgradesList.add(ArrayList())
                    }
                    upgradesList[upgrade.slot].add(upgrade)
                }
            }
        }

        val maxRows = upgradesList.map(MutableList<Modernization>::size).filter { it > 0 }.max() ?: 0

        ship.upgradesRow = maxRows
        ship.upgrades = upgradesList
    }

    @Throws(Exception::class)
    private fun setConsumables(ship: Ship)
    {
        val consumablesList = ArrayList<MutableList<Consumable>>()
        for ((_, value) in ship.shipAbilities) {
            for (consumable in value.abils) {
                if (!consumable[0].contains("Super")) {
                    val tempConsumable = mapper.readValue(mapper.writeValueAsString(consumables[consumable[0]]), Consumable::class.java)
                    tempConsumable.subConsumables.entries.removeIf { e -> !e.key.equals(consumable[1], ignoreCase = true) }
                    if (consumablesList.size < value.slot + 1) {
                        consumablesList.add(ArrayList())
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
        if (ship.components.artillery.size > 0 && ship.components.artillery[ship.modules[Constant.artillery]] != null) {
            for (ammo in ship.components.artillery[ship.modules[Constant.artillery]]!!.turrets[0].ammoList) {
                val shell = mapper.readValue(mapper.writeValueAsString(gameParamsHM[ammo]), Shell::class.java)
//                PenetrationUtils.setPenetration(shell,
//                        ship.getComponents().getArtillery().get(ship.getModules().get(artillery)).getTurrets().get(0).getVertSector().get(1),
//                        ship.getComponents().getArtillery().get(ship.getModules().get(artillery)).getMinDistV(),
//                        ship.getComponents().getArtillery().get(ship.getModules().get(artillery)).getMaxDist(),
//                        "AP".equalsIgnoreCase(shell.getAmmoType().toLowerCase()));

                ship.components.artillery[ship.modules[Constant.artillery]]!!.shells[ammo] = shell
            }
        }

        if (ship.components.torpedoes.size > 0 && ship.components.torpedoes[ship.modules[Constant.torpedoes]] != null) {
            val ammo = ship.components.torpedoes[ship.modules[Constant.torpedoes]]!!.launchers[0].ammoList[0]
            ship.components.torpedoes[ship.modules[Constant.torpedoes]]?.ammo = mapper.readValue(mapper.writeValueAsString(gameParamsHM[ammo]), TorpedoAmmo::class.java)
        }

        if (ship.components.atba.size > 0 && ship.components.atba[ship.modules[Constant.atba]] != null) {
            for ((_, value) in ship.components.atba[ship.modules[Constant.atba]]!!.secondaries) {
                val ammo = mapper.readValue(mapper.writeValueAsString(gameParamsHM[value.ammoList[0]]), Shell::class.java)
                value.alphaDamage = ammo.alphaDamage
                value.alphaPiercingHE = ammo.alphaPiercingHE
                value.ammoType = ammo.ammoType
                value.bulletSpeed = ammo.bulletSpeed
                value.burnProb = ammo.burnProb
            }
        }

        if (ship.planes.size > 0) {
            if (ship.modules[Constant.diveBomber] != null && ship.planes[ship.modules[Constant.diveBomber]] != null) {
                val dbPlane = mapper.readValue(mapper.writeValueAsString(gameParamsHM[ship.planes[ship.modules[Constant.diveBomber]]]), Plane::class.java)
                dbPlane.bomb = mapper.readValue(mapper.writeValueAsString(gameParamsHM[dbPlane.bombName]), Shell::class.java)
                setPlaneConsumables(dbPlane)
                ship.components.diveBomber[ship.modules[Constant.diveBomber]!!] = dbPlane
            }

            if (ship.modules[Constant.fighter] != null && ship.planes[ship.modules[Constant.fighter]] != null) {
                val fPlane = mapper.readValue(mapper.writeValueAsString(gameParamsHM[ship.planes[ship.modules[Constant.fighter]]]), Plane::class.java)
                fPlane.rocket = mapper.readValue(mapper.writeValueAsString(gameParamsHM[fPlane.bombName]), Shell::class.java)
                setPlaneConsumables(fPlane)
                ship.components.fighter[ship.modules[Constant.fighter]!!] = fPlane
            }

            if (ship.modules[Constant.torpedoBomber] != null && ship.planes[ship.modules[Constant.torpedoBomber]] != null) {
                val tbPlane = mapper.readValue(mapper.writeValueAsString(gameParamsHM[ship.planes[ship.modules[Constant.torpedoBomber]]]), Plane::class.java)
                tbPlane.torpedo = mapper.readValue(mapper.writeValueAsString(gameParamsHM[tbPlane.bombName]), TorpedoAmmo::class.java)
                setPlaneConsumables(tbPlane)
                ship.components.torpedoBomber[ship.modules[Constant.torpedoBomber]!!] = tbPlane
            }
        }
    }

    @Throws(Exception::class)
    private fun setPlaneConsumables(plane: Plane)
    {
        val consumablesList = ArrayList<Consumable>()

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
        val ship: Ship = ships[index]!!
        if (ship.shipUpgradeInfo.components[Constant.artillery]!!.size > 0) {
            for (su in ship.shipUpgradeInfo.components[Constant.artillery]!!) {
                if (su.name.equals(artyId, ignoreCase = true)) {
                    val tempId = su.components[Constant.artillery]?.get(su.components[Constant.artillery]!!.size - 1)

                    for (ammo in ship.components.artillery[tempId]!!.turrets[0].ammoList) {
                        val shell = mapper.readValue(mapper.writeValueAsString(gameParamsHM[ammo]), Shell::class.java)
                        if ("AP".equals(shell.ammoType, ignoreCase = true)) {
                            PenetrationUtils.setPenetration(shell,
                                    ship.components.artillery[tempId]!!.turrets[0].vertSector[1].toDouble(),
                                    ship.components.artillery[tempId]!!.minDistV,
                                    ship.components.artillery[tempId]!!.maxDist,
                                    "AP".equals(shell.ammoType?.toLowerCase(), ignoreCase = true))
                            return shell
                        }
                    }
                }
            }
        }
        return null
    }
}
