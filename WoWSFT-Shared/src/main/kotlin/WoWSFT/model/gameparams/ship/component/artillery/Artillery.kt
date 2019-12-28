package WoWSFT.model.gameparams.ship.component.artillery

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.ship.component.airdefense.Aura
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Artillery
{
    val auraFar = mutableListOf<Aura>()
    val auraMedium = mutableListOf<Aura>()
    val auraNear = mutableListOf<Aura>()
    val turrets = mutableListOf<Turret>()
    val turretTypes = LinkedHashMap<Int, MutableList<Any>>()
    val artificialOffset = 0.0
    val maxDist = 0.0
    val minDistH = 0.0
    val minDistV = 0.0
    val normalDistribution = false
    val sigmaCount = 0.0
    val taperDist = 0.0
    @JsonInclude
    val GMIdealRadius = 1.0
    var barrelDiameter = 0.0
    val shells = LinkedHashMap<String, Shell>()
    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setGuns(name: String, value: Any?) {
        if (value is HashMap<*, *>) {
            val tempObject = mapper.convertValue(value, object : TypeReference<HashMap<String, Any>>() {})
            if ("far".equals(tempObject["type"] as String?, ignoreCase = true)) {
                auraFar.add(mapper.convertValue(value, Aura::class.java))
            } else if ("medium".equals(tempObject["type"] as String?, ignoreCase = true)) {
                auraMedium.add(mapper.convertValue(value, Aura::class.java))
            } else if ("near".equals(tempObject["type"] as String?, ignoreCase = true)) {
                auraNear.add(mapper.convertValue(value, Aura::class.java))
            } else if (tempObject.containsKey("HitLocationArtillery")) {
                val turret = mapper.convertValue(value, Turret::class.java)
                turrets.add(turret)
                barrelDiameter = turret.barrelDiameter
                if (turretTypes.containsKey(turret.numBarrels)) {
                    turretTypes[turret.numBarrels]?.set(0, turretTypes[turret.numBarrels]!![0] as Int + 1)
                } else {
                    val tObject = mutableListOf<Any>()
                    tObject.add(1)
                    tObject.add(turret.name)
                    turretTypes[turret.numBarrels] = tObject
                }
            }
        }
    }
}