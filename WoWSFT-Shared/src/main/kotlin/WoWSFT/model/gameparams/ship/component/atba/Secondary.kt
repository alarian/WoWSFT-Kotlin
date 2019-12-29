package WoWSFT.model.gameparams.ship.component.atba

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import kotlin.math.ceil

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Secondary
{
    var ammoList = listOf<String>()
    var antiAirAuraDistance = 0.0
    var antiAirAuraStrength = 0.0
    var barrelDiameter = 0.0
    var deadZone = listOf<List<Double>>()
    var delim = 0.0
    var horizSector = listOf<Double>()
    var id = 0L
    var idealDistance = 0.0
    var idealRadius = 0.0
    var index = ""
    var minRadius = 0.0
    var name = ""
    var numBarrels = 0
    var radiusOnDelim = 0.0
    var radiusOnMax = 0.0
    var radiusOnZero = 0.0
    var rotationSpeed = listOf<Double>()
    var shotDelay = 0.0
    var smallGun = false
    var smokePenalty = 0.0
    var typeinfo = TypeInfo()
    var vertSector = listOf<Double>()
    @JsonInclude
    var GSIdealRadius = 1.0
    var count = 0
    var alphaDamage = 0.0
    var alphaPiercingHE = 0.0
    var ammoType = ""
    var bulletSpeed = 0.0
    var burnProb = 0.0
    val alphaPiercingHEReal get() = ceil(alphaPiercingHE).toInt() - 1
}