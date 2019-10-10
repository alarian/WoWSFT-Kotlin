package wowsft.model.gameparams.ship.component.airdefense

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

import java.util.ArrayList
import java.util.HashMap

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class AirDefense
{
    var auraFar = ArrayList<Aura>()
    var auraMedium = ArrayList<Aura>()
    var auraNear = ArrayList<Aura>()

    var ownerlessTracesScatterCoefficient = 0.0
    var prioritySectorChangeDelay = 0.0
    var prioritySectorDisableDelay = 0.0
    var prioritySectorEnableDelay = 0.0
    var prioritySectorStrength = 0.0
    var sectors = ArrayList<MutableList<Double>>()
    var prioritySectorPhases = ArrayList<MutableList<Any>>()
        set(value) {
            field = value

            if (prioritySectorPhases.size == 2) {
                prioritySectorPreparation = prioritySectorPhases[0][0].toString().toDouble()
                prioritySectorDuration = prioritySectorPhases[1][0].toString().toDouble()
                prioritySectorDamageInitial = prioritySectorPhases[0][2].toString().toDouble()
                prioritySectorCoefficientInitial = prioritySectorPhases[0][3].toString().toDouble()
                prioritySectorCoefficientDuring = prioritySectorPhases[0][4].toString().toDouble()
            }
        }
    var prioritySectorPreparation = 0.0
    var prioritySectorDuration = 0.0
    var prioritySectorDamageInitial = 0.0
    var prioritySectorCoefficientInitial = 0.0
    var prioritySectorCoefficientDuring = 0.0

    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setAura(name: String, value: Any) {
        if (value is HashMap<*, *>) {
            val tempObject = mapper.convertValue<HashMap<String, Any>>(value, object : TypeReference<HashMap<String, Any>>() {})

            if ("far".equals(tempObject["type"] as String?, ignoreCase = true)) {
                auraFar.add(mapper.convertValue(value, Aura::class.java))
            } else if ("medium".equals(tempObject["type"] as String?, ignoreCase = true)) {
                auraMedium.add(mapper.convertValue(value, Aura::class.java))
            } else if ("near".equals(tempObject["type"] as String?, ignoreCase = true)) {
                auraNear.add(mapper.convertValue(value, Aura::class.java))
            }
        }
    }
}
