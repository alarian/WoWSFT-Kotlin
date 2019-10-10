package wowsft.service

import wowsft.model.gameparams.ship.Ship
import wowsft.model.gameparams.ship.upgrades.ShipUpgrade
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest
import java.math.BigInteger
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.concurrent.atomic.AtomicInteger

import wowsft.model.Constant

@Service
class ParserService(
        @Autowired @Qualifier(value = "nameToId") private val nameToId: HashMap<String, String>
)
{
    fun parseModules(ship: Ship, bits: String)
    {
        ship.modules = LinkedHashMap()
        ship.positions = LinkedHashMap()
        val baseModules = LinkedHashMap<String, String>()
        val basePositions = LinkedHashMap<String, Int>()
        val list = ArrayList<Int>()
        val shipUpgrades = LinkedHashMap<String, ShipUpgrade>()

        if (StringUtils.isNotEmpty(bits)) {
            for (i in bits.indices) {
                if (Character.isDigit(bits[i])) {
                    list.add(Character.getNumericValue(bits[i]))
                } else {
                    list.add(1)
                }
            }
        }

        val pos = AtomicInteger()
        ship.shipUpgradeInfo.components.forEach { (type, value) ->
            if (CollectionUtils.isNotEmpty(value)) {
                val position = pos.getAndIncrement()
                basePositions[type] = 1
                value[0].components.forEach { (x, y) ->
                    if (CollectionUtils.isNotEmpty(y)) {
                        baseModules[x] = y[0]
                    }
                }

                if (CollectionUtils.isNotEmpty(list) && value.size >= list[position]) {
                    ship.positions[type] = list[position]
                    shipUpgrades[type] = value[list[position] - 1]
                }
            }
        }

        shipUpgrades.forEach { (type, upgrade) ->
            if (!type.equals(Constant.hull, ignoreCase = true)) {
                if (CollectionUtils.isEmpty(shipUpgrades[Constant.hull]!!.components.get(type))) {
                    if (shipUpgrades[upgrade.prevType]!!.position >= upgrade.prevPosition) {
                        ship.modules[type] = upgrade.components[type]!![0]
                    }
                } else {
                    shipUpgrades[Constant.hull]!!.components[type]?.forEach { x ->
                        if (upgrade.components[type]!!.contains(x)) {
                            ship.modules[type] = x
                        }
                    }
                }
            } else {
                ship.modules[type] = shipUpgrades[Constant.hull]!!.components[type]!![0]
            }
        }

        if (bits.isNotEmpty() && ship.modules.size == bits.length) {
            shipUpgrades[Constant.hull]!!.components.forEach { (x, y) ->
                if (!ship.modules.containsKey(x) && CollectionUtils.isNotEmpty(y)) {
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
        val list = ArrayList<Int>()

        if (StringUtils.isNotEmpty(bits)) {
            for (i in 0 until ship.upgrades.size) {
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
        val list = ArrayList<Int>()

        if (StringUtils.isNotEmpty(bits)) {
            for (i in 0 until ship.consumables.size) {
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
        val list = ArrayList<Int>()
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
                    ship.adrenaline = (100.toDouble() - ar.toDouble()) / 100.toDouble()
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

    @Throws(Exception::class)
    fun parseLegacyUrl(request: HttpServletRequest): String
    {
        var url = "/ship"

        if (!request.parameterMap.isNullOrEmpty()) {
            val shipName = getParameter(request.parameterMap["ship"])
            if (StringUtils.isNotEmpty(shipName)) {
                val index = nameToId[shipName.replace("+", " ")]

                if (!index.isNullOrBlank()) {
                    url += ("?index=" + index.toUpperCase())

                    val modules = getParameter(request.parameterMap["moduleN"])
                    url += if (StringUtils.isNotEmpty(modules)) "&modules=$modules" else ""

                    val upgrades = getParameter(request.parameterMap["upgradeN"])
                    url += if (StringUtils.isNotEmpty(upgrades)) "&upgrades=$upgrades" else ""

                    val skills = getParameter(request.parameterMap["skillN"])
                    if (StringUtils.isNotEmpty(skills)) {
                        var bits = ""
                        for (i in skills.length.coerceAtMost(32) - 1 downTo 0) {
                            bits += if (skills[i].toString().toIntOrNull() == 1) 1.toString() else 0.toString()
                        }

                        if (StringUtils.isNotEmpty(bits)) {
                            url += ("&skills=" + BigInteger(bits, 2).toLong())
                        }
                    }
                }
            }
        }
        return url
    }

    @Throws(Exception::class)
    private fun getParameter(temp: Array<String>?): String
    {
        return if (!temp.isNullOrEmpty() && StringUtils.isNotEmpty(temp[0])) URLDecoder.decode(temp[0], StandardCharsets.UTF_8.name()) else ""
    }
}
