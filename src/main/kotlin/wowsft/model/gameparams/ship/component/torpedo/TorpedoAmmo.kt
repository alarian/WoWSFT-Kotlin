package wowsft.model.gameparams.ship.component.torpedo

import com.fasterxml.jackson.annotation.JsonProperty
import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class TorpedoAmmo {
    var additionalShips = ArrayList<Any>()
    var alertDist = 0f
    var alphaDamage = 0f
    var alphaPiercingHE = 0f
    var ammoType = ""
    var armingTime = 0f
    var bulletDiametr = 0f
    var burnProb = 0f
    var costCR = 0f
    var damage = 0f
    var damageUW = 0f
    var depth = 0f
    var directDamage = 0f
    var fallingTimeCoef = 0f
    var id = 0L
    var ignoreClasses = ArrayList<String>()
    var index = ""
    @JsonProperty("isDeepWater")
    var deepWater = false
    var maxDist = 0f
    var name = ""
    var planeAmmoType = ""
    var speed = 0f
    var splashArmorCoeff = 0f
    var splashCubeSize = 0f
    var typeinfo = TypeInfo()
    var uwAbility = false
    var uwCritical = 0f
    var visibilityFactor = 0f
    var volume = 0f
}
