package WoWSFT.model.gameparams.ship.upgrades

import WoWSFT.config.WoWSFT
import WoWSFT.model.Constant.componentsList
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

@WoWSFT
class ShipUpgradeInfo
{
    var components = LinkedHashMap<String, MutableList<ShipUpgrade>>()
    var cols = LinkedHashMap<String, Int>()
    var maxRows = 0
    var costCR = 0
    var costGold = 0
    var costSaleGold = 0
    var costXP = 0
    var lockedConfig = mutableListOf<Any>()
    var prepareTimeFactor = 0.0
    var value = 0

    init {
        componentsList.forEach { components[it] = mutableListOf() }
    }

    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setShipUpgrades(name: String, value: Any) {
        val upgrade = mapper.convertValue(value, ShipUpgrade::class.java)
        upgrade.name = name
        upgrade.position = if (upgrade.prev.isEmpty()) 1 else 2
        components[upgrade.ucTypeShort]?.add(upgrade)
    }
}