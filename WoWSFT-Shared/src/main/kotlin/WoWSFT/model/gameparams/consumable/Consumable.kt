package WoWSFT.model.gameparams.consumable

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

@WoWSFT
class Consumable
{
    val subConsumables = LinkedHashMap<String, ConsumableSub>()
    val canBuy = false
    val canBuyCustom = LinkedHashMap<String, Boolean>()
    val costCR = 0.0
    val costGold = 0.0
    val freeOfCharge = false
    val id = 0L
    val index = ""
    val name = ""
    val typeinfo = TypeInfo()
    val description = ""
    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setSubs(name: String, value: Any?) {
        subConsumables[name] = mapper.convertValue(value, ConsumableSub::class.java)
    }
}