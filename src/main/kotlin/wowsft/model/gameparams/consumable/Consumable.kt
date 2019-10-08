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
    var costCR = 0.toDouble()
    var costGold = 0.toDouble()
    var freeOfCharge = false
    var id: Long = 0
    var index: String = ""
    var name: String = ""
    var typeinfo = TypeInfo()
    var description: String = ""

    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setSubs(name: String, value: Any) {
        subConsumables[name] = mapper.convertValue(value, ConsumableSub::class.java)
    }
}
