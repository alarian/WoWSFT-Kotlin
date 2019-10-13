package wowsft.model.gameparams.ship.component.atba

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
class ATBA {
    var auraFar = ArrayList<Aura>()
    var auraMedium = ArrayList<Aura>()
    var auraNear = ArrayList<Aura>()

    var turrets = ArrayList<Secondary>()
    var secondaries = LinkedHashMap<String, Secondary>()

    var maxDist = 0.0
    var minDistH = 0.0
    var minDistV = 0.0
    var sigmaCount = 0.0
    var taperDist = 0.0
    @JsonInclude
    var GSIdealRadius = 1.0

    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setGuns(name: String, value: Any) {
        if (value is HashMap<*, *>) {
            val tempObject = mapper.convertValue<HashMap<String, Any>>(value, object : TypeReference<HashMap<String, Any>>() {})

            if ("far".equals(tempObject["type"] as String?, ignoreCase = true)) {
                auraFar.add(mapper.convertValue(value, Aura::class.java))
            } else if ("medium".equals(tempObject["type"] as String?, ignoreCase = true)) {
                auraMedium.add(mapper.convertValue(value, Aura::class.java))
            } else if ("near".equals(tempObject["type"] as String?, ignoreCase = true)) {
                auraNear.add(mapper.convertValue(value, Aura::class.java))
            } else if (tempObject.containsKey("HitLocationATBA")) {
                val tempS = mapper.convertValue(value, Secondary::class.java)

                if (secondaries[tempS.name] != null) {
                    secondaries[tempS.name]!!.count = secondaries[tempS.name]!!.count + 1
                } else {
                    tempS.count = 1
                    secondaries[tempS.name!!] = tempS
                }
            }
        }
    }
}
