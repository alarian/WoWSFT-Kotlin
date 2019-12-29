package WoWSFT.model.gameparams.ship.component.torpedo

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class TorpedoAmmo
{
    var additionalShips = listOf<Any>()
    var alertDist = 0.0
    var alphaDamage = 0.0
    var alphaPiercingHE = 0.0
    var ammoType = ""
    var armingTime = 0.0
    var bulletDiametr = 0.0
    var burnProb = 0.0
    var costCR = 0.0
    var damage = 0.0
    var damageUW = 0.0
    var depth = 0.0
    var directDamage = 0.0
    var fallingTimeCoef = 0.0
    var id = 0L
    var ignoreClasses = listOf<String>()
    var index = ""
    @JsonAlias("isDeepWater")
    var deepWater = false
    var maxDist = 0.0
    var name = ""
    var planeAmmoType = ""
    var speed = 0.0
    var splashArmorCoeff = 0.0
    var splashCubeSize = 0.0
    var typeinfo = TypeInfo()
    var uwAbility = false
    var uwCritical = 0.0
    var visibilityFactor = 0.0
    var volume = 0.0
}