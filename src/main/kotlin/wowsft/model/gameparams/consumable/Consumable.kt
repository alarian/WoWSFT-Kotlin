package wowsft.model.gameparams.consumable

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper

import java.util.LinkedHashMap

@WoWSFT
class Consumable {
    var subConsumables = LinkedHashMap<String, ConsumableSub>()

    var canBuy = false
    var canBuyCustom = LinkedHashMap<String, Boolean>()
    var costCR = 0f
    var costGold = 0f
    var freeOfCharge = false
    var id = 0L
    var index = ""
    var name = ""
    var typeinfo = TypeInfo()
    var description = ""

    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setSubs(name: String, value: Any) {
        subConsumables[name] = mapper.convertValue(value, ConsumableSub::class.java)
    }
}
