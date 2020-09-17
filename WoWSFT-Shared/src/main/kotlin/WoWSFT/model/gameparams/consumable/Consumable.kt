package WoWSFT.model.gameparams.consumable

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.TypeInfo
import WoWSFT.utils.CommonUtils.mapper
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.util.*

@WoWSFT
class Consumable {
    var subConsumables = LinkedHashMap<String, ConsumableSub>()
    var canBuy = false
    var canBuyCustom = LinkedHashMap<String, Boolean>()
    var costCR = 0.0
    var costGold = 0.0
    var freeOfCharge = false
    var id = 0L
    var index = ""
    var name = ""
    var typeinfo = TypeInfo()
    var description = ""

    @JsonAnySetter
    fun setSubs(name: String, value: Any?) {
        subConsumables[name] = mapper.convertValue(value, jacksonTypeRef<ConsumableSub>())
    }
}