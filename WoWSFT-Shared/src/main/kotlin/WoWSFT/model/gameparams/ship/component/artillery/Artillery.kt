package WoWSFT.model.gameparams.ship.component.artillery

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
class Artillery {
    val aaJoint = AAJoint()
    var turrets = mutableListOf<Turret>()
    var turretTypes = LinkedHashMap<Int, MutableList<Any>>()
    var artificialOffset = 0.0
    var maxDist = 0.0
    var minDistH = 0.0
    var minDistV = 0.0
    var normalDistribution = false
    var sigmaCount = 0.0
    var taperDist = 0.0

    @JsonInclude
    var GMIdealRadius = 1.0
    var barrelDiameter = 0.0
    var shells = LinkedHashMap<String, Shell>()

    val numBarrels: Int get() = turretTypes.entries.sumBy { it.key * (it.value[0] as Int) }

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
                } else if (tempObject.containsKey("HitLocationArtillery")) {
                    mapper.convertValue(value, jacksonTypeRef<Turret>()).also { turret ->
                        turrets.add(turret)
                        barrelDiameter = turret.barrelDiameter
                        if (turretTypes.containsKey(turret.numBarrels)) {
                            turretTypes[turret.numBarrels]?.set(0, turretTypes[turret.numBarrels]!![0] as Int + 1)
                        } else {
                            turretTypes[turret.numBarrels] = mutableListOf<Any>().also { tObject ->
                                tObject.add(1)
                                tObject.add(turret.name)
                            }
                        }
                    }
                }
            }
        }
    }
}