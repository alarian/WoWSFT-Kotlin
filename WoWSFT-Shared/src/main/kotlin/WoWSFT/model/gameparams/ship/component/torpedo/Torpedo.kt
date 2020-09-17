package WoWSFT.model.gameparams.ship.component.torpedo

import WoWSFT.config.WoWSFT
import WoWSFT.utils.CommonUtils.mapper
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.util.*

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Torpedo {
    var launchers = mutableListOf<Launcher>()
    var launcherTypes = LinkedHashMap<Int, MutableList<Any>>()
    var numTorpsInSalvo = 0
    var oneShotWaitTime = 0.0
    var useGroups = false
    var useOneShot = false
    var ammo = TorpedoAmmo()

    @JsonAnySetter
    fun setGuns(name: String, value: Any?) {
        if (value is HashMap<*, *>) {
            mapper.convertValue(value, jacksonTypeRef<HashMap<String, Any>>()).also { tempObject ->
                if (tempObject.containsKey("HitLocationTorpedo")) {
                    mapper.convertValue(value, jacksonTypeRef<Launcher>()).also { launcher ->
                        launchers.add(launcher)
                        if (launcherTypes.containsKey(launcher.numBarrels)) {
                            launcherTypes[launcher.numBarrels]?.set(0, launcherTypes[launcher.numBarrels]!![0] as Int + 1)
                        } else {
                            launcherTypes[launcher.numBarrels] = mutableListOf<Any>().also { tObject ->
                                tObject.add(1)
                                tObject.add(launcher.name)
                            }
                        }
                    }
                }
            }
        }
    }
}