package wowsft.model.gameparams.ship.component.torpedo

import com.fasterxml.jackson.annotation.JsonProperty
import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class TorpedoAmmo {
    var additionalShips = ArrayList<Any>()
    var alertDist = 0.toDouble()
    var alphaDamage = 0.toDouble()
    var alphaPiercingHE = 0.toDouble()
    var ammoType: String = ""
    var armingTime = 0.toDouble()
    var bulletDiametr = 0.toDouble()
    var burnProb = 0.toDouble()
    var costCR = 0.toDouble()
    var damage = 0.toDouble()
    var damageUW = 0.toDouble()
    var depth = 0.toDouble()
    var directDamage = 0.toDouble()
    var fallingTimeCoef = 0.toDouble()
    var id: Long = 0
    var ignoreClasses = ArrayList<String>()
    var index: String = ""
    @JsonProperty("isDeepWater")
    var deepWater = false
    var maxDist = 0.toDouble()
    var name: String = ""
    var planeAmmoType: String = ""
    var speed = 0.toDouble()
    var splashArmorCoeff = 0.toDouble()
    var splashCubeSize = 0.toDouble()
    var typeinfo = TypeInfo()
    var uwAbility = false
    var uwCritical = 0.toDouble()
    var visibilityFactor = 0.toDouble()
    var volume = 0.toDouble()
}
