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
    var components = LinkedHashMap<String, List<ShipUpgrade>>()
    var cols = LinkedHashMap<String, Int>()
    var maxRows: Int = 0

    var costCR: Int = 0
    var costGold: Int = 0
    var costSaleGold: Int = 0
    var costXP: Int = 0
    var lockedConfig: List<Any>? = null
    var prepareTimeFactor: Float = 0f
    var value: Int = 0

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
        components[upgrade.ucTypeShort]!!.plus(upgrade)
    }
}
