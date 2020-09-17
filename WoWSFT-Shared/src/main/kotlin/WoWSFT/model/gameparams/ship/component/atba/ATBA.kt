package WoWSFT.model.gameparams.ship.component.atba

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.ship.component.airdefense.AAJoint
import WoWSFT.model.gameparams.ship.component.airdefense.Aura
import WoWSFT.utils.CommonUtils.mapper
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.util.*

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class ATBA {
    val aaJoint = AAJoint()
    var turrets = mutableListOf<Secondary>()
    var secondaries = LinkedHashMap<String, Secondary>()
    var maxDist = 0.0
    var minDistH = 0.0
    var minDistV = 0.0
    var sigmaCount = 0.0
    var taperDist = 0.0

    @JsonInclude
    var GSIdealRadius = 1.0

    @JsonAnySetter
    fun setGuns(name: String, value: Any?) {
        if (value is HashMap<*, *>) {
            mapper.convertValue(value, jacksonTypeRef<HashMap<String, Any>>()).also { tempObject ->
                if ("far".equals(tempObject["type"] as String?, ignoreCase = true)) {
                    aaJoint.auraFar.add(mapper.convertValue(value, jacksonTypeRef<Aura>()))
                } else if ("medium".equals(tempObject["type"] as String?, ignoreCase = true)) {
                    aaJoint.auraMedium.add(mapper.convertValue(value, jacksonTypeRef<Aura>()))
                } else if ("near".equals(tempObject["type"] as String?, ignoreCase = true)) {
                    aaJoint.auraNear.add(mapper.convertValue(value, jacksonTypeRef<Aura>()))
                } else if (tempObject.containsKey("HitLocationATBA")) {
                    mapper.convertValue(value, jacksonTypeRef<Secondary>()).also { tempS ->
                        if (!secondaries.containsKey(tempS.name)) {
                            tempS.count = 1
                            secondaries[tempS.name] = tempS
                        } else {
                            secondaries[tempS.name]!!.count = secondaries[tempS.name]!!.count + 1
                        }
                    }
                }
            }
        }
    }
}