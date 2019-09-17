package wowsft.model.gameparams.ship.component.torpedo

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashMap

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Torpedo {
    var launchers = ArrayList<Launcher>()
    var launcherTypes = LinkedHashMap<Int, MutableList<Any>>()

    var numTorpsInSalvo = 0
    var oneShotWaitTime = 0f
    var useGroups = false
    var useOneShot = false

    var ammo = TorpedoAmmo()

    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setGuns(name: String, value: Any) {
        if (value is HashMap<*, *>) {
            val tempObject = mapper.convertValue<HashMap<String, Any>>(value, object : TypeReference<HashMap<String, Any>>() {})

            if (tempObject.containsKey("HitLocationTorpedo")) {
                val launcher = mapper.convertValue(value, Launcher::class.java)
                launchers.add(launcher)

                if (launcherTypes.containsKey(launcher.numBarrels)) {
                    launcherTypes[launcher.numBarrels]?.set(0, launcherTypes[launcher.numBarrels]?.get(0) as Int + 1)
                } else {
                    val tObject = ArrayList<Any>()
                    tObject.add(1)
                    tObject.add(launcher.name)
                    launcherTypes[launcher.numBarrels] = tObject
                }
            }
        }
    }
}
