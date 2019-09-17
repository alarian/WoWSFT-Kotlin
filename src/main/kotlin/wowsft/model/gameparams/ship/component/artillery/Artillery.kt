package wowsft.model.gameparams.ship.component.artillery

import wowsft.config.WoWSFT
import wowsft.model.gameparams.ship.component.airdefense.Aura
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashMap

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Artillery {
    var auraFar = ArrayList<Aura>()
    var auraMedium = ArrayList<Aura>()
    var auraNear = ArrayList<Aura>()

    var turrets = ArrayList<Turret>()
    var turretTypes = LinkedHashMap<Int, MutableList<Any>>()

    var artificialOffset = 0f
    var maxDist = 0f
    var minDistH = 0f
    var minDistV = 0f
    var normalDistribution = false
    var sigmaCount = 0f
    var taperDist = 0f
    @JsonInclude
    var GMIdealRadius = 1f
    var barrelDiameter = 0f

    var shells = LinkedHashMap<String, Shell>()

    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setGuns(name: String, value: Any) {
        if (value is HashMap<*, *>) {
            val tempObject = mapper.convertValue<HashMap<String, Any>>(value, object : TypeReference<HashMap<String, Any>>() {})

            if ("far".equals(tempObject["type"] as String, true)) {
                auraFar.add(mapper.convertValue(value, Aura::class.java))
            } else if ("medium".equals(tempObject["type"] as String, true)) {
                auraMedium.add(mapper.convertValue(value, Aura::class.java))
            } else if ("near".equals(tempObject["type"] as String, true)) {
                auraNear.add(mapper.convertValue(value, Aura::class.java))
            } else if (tempObject.containsKey("HitLocationArtillery")) {
                val turret = mapper.convertValue(value, Turret::class.java)
                turrets.add(turret)
                this.barrelDiameter = turret.barrelDiameter

                if (turretTypes.containsKey(turret.numBarrels)) {
                    turretTypes[turret.numBarrels]?.set(0, turretTypes[turret.numBarrels]?.get(0) as Int + 1)
                } else {
                    val tObject = ArrayList<Any>()
                    tObject.add(1)
                    tObject.add(turret.name)
                    turretTypes[turret.numBarrels] = tObject
                }
            }
        }
    }
}
