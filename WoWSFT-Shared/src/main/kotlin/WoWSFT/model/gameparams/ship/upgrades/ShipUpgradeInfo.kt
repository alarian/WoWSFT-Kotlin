package WoWSFT.model.gameparams.ship.upgrades

import WoWSFT.config.WoWSFT
import WoWSFT.model.Constant.componentsList
import WoWSFT.utils.CommonUtils.mapper
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.util.*

@WoWSFT
class ShipUpgradeInfo {
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

    @JsonAnySetter
    fun setShipUpgrades(name: String, value: Any?) {
        mapper.convertValue(value, jacksonTypeRef<ShipUpgrade>()).also { upgrade ->
            upgrade.name = name
            upgrade.position = if (upgrade.prev.isBlank()) 1 else 2
            components[upgrade.ucTypeShort]?.add(upgrade)
        }
    }
}