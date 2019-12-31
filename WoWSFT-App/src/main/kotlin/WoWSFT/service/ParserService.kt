package WoWSFT.service

import WoWSFT.model.Constant.hull
import WoWSFT.model.gameparams.ship.Ship
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgrade
import org.springframework.stereotype.Service
import java.util.*

@Service
class ParserService
{
    fun parseModules(ship: Ship, bits: String)
    {
//        ship.modules = LinkedHashMap()
//        ship.positions = LinkedHashMap()

        val baseModules = LinkedHashMap<String, String>()
        val basePositions = LinkedHashMap<String, Int>()
        val list = mutableListOf<Int>()
        val shipUpgrades = LinkedHashMap<String, ShipUpgrade>()

        if (bits.isNotEmpty()) {
            for (i in bits.indices) {
                if (Character.isDigit(bits[i])) {
                    list.add(Character.getNumericValue(bits[i]))
                } else {
                    list.add(1)
                }
            }
        }

        var pos = 0
        ship.shipUpgradeInfo.components.forEach { (type, value) ->
            if (value.isNotEmpty()) {
                val position = pos
                pos++

                basePositions[type] = 1
                value[0].components.forEach { (x, y) ->
                    if (y.isNotEmpty()) {
                        baseModules[x] = y[0]
                    }
                }

                if (list.isNotEmpty() && value.size >= list[position]) {
                    ship.positions[type] = list[position]
                    shipUpgrades[type] = value[list[position] - 1]
                }
            }
        }

        shipUpgrades.forEach { (type, upgrade) ->
            if (!type.equals(hull, ignoreCase = true)) {
                if (shipUpgrades[hull]!!.components[type]!!.isEmpty()) {
                    if (shipUpgrades[upgrade.prevType]!!.position >= upgrade.prevPosition) {
                        ship.modules[type] = upgrade.components[type]!![0]
                    }
                } else {
                    shipUpgrades[hull]!!.components[type]?.forEach { x ->
                        if (upgrade.components[type]!!.contains(x)) {
                            ship.modules[type] = x
                        }
                    }
                }
            } else {
                ship.modules[type] = shipUpgrades[hull]!!.components[type]!![0]
            }
        }

        if (bits.isNotEmpty() && ship.modules.size == bits.length) {
            shipUpgrades[hull]!!.components.forEach { (x, y) ->
                if (!ship.modules.containsKey(x) && y.isNotEmpty()) {
                    ship.modules[x] = y[0]
                }
            }
        } else {
            ship.modules.clear()
            ship.modules.putAll(baseModules)
            ship.positions.clear()
            ship.positions.putAll(basePositions)
        }
    }

    fun parseUpgrades(ship: Ship, bits: String)
    {
        val list: MutableList<Int> = ArrayList()
        if (bits.isNotEmpty()) {
            for (i in ship.upgrades.indices) {
                if (i < bits.length && Character.isDigit(bits[i]) && Character.getNumericValue(bits[i]) <= ship.upgrades[i].size) {
                    list.add(Character.getNumericValue(bits[i]))
                } else {
                    list.add(0)
                }
            }
            ship.selectUpgrades = list
        }
    }

    fun parseConsumables(ship: Ship, bits: String)
    {
        val list = mutableListOf<Int>()
        if (bits.isNotEmpty()) {
            for (i in ship.consumables.indices) {
                if (i < bits.length && Character.isDigit(bits[i]) && Character.getNumericValue(bits[i]) <= ship.consumables[i].size) {
                    list.add(Character.getNumericValue(bits[i]))
                } else {
                    list.add(1)
                }
            }
            ship.selectConsumables = list
        }
    }

    fun parseSkills(ship: Ship, skill: Long, ar: Int)
    {
        val list = mutableListOf<Int>()
        val bits = java.lang.Long.toBinaryString(skill)
        var pts = 0

        for (i in bits.length - 1 downTo 0) {
            val check = Character.getNumericValue(bits[i])
            val tempPts = (bits.length - 1 - i) / 8 + 1
            if (check == 1 && tempPts + pts <= 19) {
                pts += tempPts
                list.add(Character.getNumericValue(bits[i]))
                // Adrenaline Rush
                if (bits.length - 1 - i == 14) {
                    ship.arUse = Character.getNumericValue(bits[i]) == 1
                    ship.adrenaline = (100 - ar) / 100.0
                }
            } else {
                list.add(0)
            }
        }
        if (bits.length < 32) {
            for (i in 0 until 32 - bits.length) {
                list.add(0)
            }
        }
        ship.selectSkills = list
        ship.selectSkillPts = pts
    }
}