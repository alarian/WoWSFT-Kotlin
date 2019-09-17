package wowsft.model.gameparams.ship.upgrades

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper

import java.util.ArrayList
import java.util.LinkedHashMap
import wowsft.model.Constant.componentsList

@WoWSFT
class ShipUpgradeInfo {
    var components = LinkedHashMap<String, MutableList<ShipUpgrade>>()
    var cols = LinkedHashMap<String, Int>()
    var maxRows = 0

    var costCR = 0
    var costGold = 0
    var costSaleGold = 0
    var costXP = 0
    var lockedConfig = ArrayList<Any>()
    var prepareTimeFactor = 0f
    var value = 0

    @JsonIgnore
    private val mapper = ObjectMapper()

    init {
        componentsList.forEach { c -> components[c] = ArrayList() }
    }

    @JsonAnySetter
    fun setShipUpgrades(name: String, value: Any) {
        val upgrade = mapper.convertValue(value, ShipUpgrade::class.java)
        upgrade.name = name
        upgrade.position = if (upgrade.prev.isEmpty()) 1 else 2
        components[upgrade.ucTypeShort]?.plus(upgrade)
    }

    fun setColsAndMaxRows(col: LinkedHashMap<String, Int>, row: Int) {
        cols = col
        maxRows = row
    }
}
