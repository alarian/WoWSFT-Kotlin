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
class AirDefense {
    var auraFar = ArrayList<Aura>()
    var auraMedium = ArrayList<Aura>()
    var auraNear = ArrayList<Aura>()

    var ownerlessTracesScatterCoefficient = 0f
    var prioritySectorChangeDelay = 0f
    var prioritySectorDisableDelay = 0f
    var prioritySectorEnableDelay = 0f
    var prioritySectorStrength = 0f
    var sectors = ArrayList<MutableList<Float>>()
    var prioritySectorPhases = ArrayList<MutableList<Any>>()
        set(value) {
            field = value

            if (prioritySectorPhases.size == 2) {
                prioritySectorPreparation = prioritySectorPhases[0][0].toString().toFloat()
                prioritySectorDuration = prioritySectorPhases[1][0].toString().toFloat()
                prioritySectorDamageInitial = prioritySectorPhases[0][2].toString().toFloat()
                prioritySectorCoefficientInitial = prioritySectorPhases[0][3].toString().toFloat()
                prioritySectorCoefficientDuring = prioritySectorPhases[0][4].toString().toFloat()
            }
        }
    var prioritySectorPreparation = 0f
    var prioritySectorDuration = 0f
    var prioritySectorDamageInitial = 0f
    var prioritySectorCoefficientInitial = 0f
    var prioritySectorCoefficientDuring = 0f

    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setAura(name: String, value: Any) {
        if (value is HashMap<*, *>) {
            val tempObject = mapper.convertValue<HashMap<String, Any>>(value, object : TypeReference<HashMap<String, Any>>() {})

            if ("far".equals(tempObject["type"] as String, true)) {
                auraFar.add(mapper.convertValue(value, Aura::class.java))
            } else if ("medium".equals(tempObject["type"] as String, true)) {
                auraMedium.add(mapper.convertValue(value, Aura::class.java))
            } else if ("near".equals(tempObject["type"] as String, true)) {
                auraNear.add(mapper.convertValue(value, Aura::class.java))
            }
        }
    }
}
