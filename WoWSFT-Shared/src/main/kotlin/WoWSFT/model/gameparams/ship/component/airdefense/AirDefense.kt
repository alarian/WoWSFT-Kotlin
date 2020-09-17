package WoWSFT.model.gameparams.ship.component.airdefense

import WoWSFT.config.WoWSFT
import WoWSFT.utils.CommonUtils.mapper
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.util.*

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class AirDefense {
    val aaJoint = AAJoint()
    var ownerlessTracesScatterCoefficient = 0.0
    var prioritySectorChangeDelay = 0.0
    var prioritySectorDisableDelay = 0.0
    var prioritySectorEnableDelay = 0.0
    var prioritySectorStrength = 0.0
    var sectors = mutableListOf<MutableList<Double>>()
    var prioritySectorPhases = mutableListOf<MutableList<Any>>()
        set(value) {
            field = value
            if (value.size >= 2 && value[0].size >= 5) {
                prioritySectorPreparation = value[0][0].toString().toDouble() + value[1][0].toString().toDouble()
                prioritySectorDuration = value[0][0].toString().toDouble() + value[1][0].toString().toDouble()
                prioritySectorDamageInitial = value[0][2].toString().toDouble()
                prioritySectorCoefficientInitial = value[0][3].toString().toDouble()
                prioritySectorCoefficientDuring = value[0][4].toString().toDouble()
            }
        }

    var prioritySectorPreparation = 0.0
    var prioritySectorDuration = 0.0
    var prioritySectorDamageInitial = 0.0
    var prioritySectorCoefficientInitial = 0.0
    var prioritySectorCoefficientDuring = 0.0

    @JsonAnySetter
    fun setAura(name: String, value: Any?) {
        if (value is HashMap<*, *>) {
            mapper.convertValue(value, jacksonTypeRef<HashMap<String, Any>>()).also { tempObject ->
                when (tempObject["type"].toString().toLowerCase()) {
                    "far" -> {
                        aaJoint.auraFar.add(mapper.convertValue(value, jacksonTypeRef<Aura>()))
                    }
                    "medium" -> {
                        aaJoint.auraMedium.add(mapper.convertValue(value, jacksonTypeRef<Aura>()))
                    }
                    "near" -> {
                        aaJoint.auraNear.add(mapper.convertValue(value, jacksonTypeRef<Aura>()))
                    }
                }
            }
        }
    }
}