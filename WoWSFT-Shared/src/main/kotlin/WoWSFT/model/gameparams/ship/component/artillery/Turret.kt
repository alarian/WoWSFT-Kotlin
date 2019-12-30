package WoWSFT.model.gameparams.ship.component.artillery

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Turret
{
    var ammoList = listOf<String>()
    var antiAirAuraDistance = 0.0
    var antiAirAuraStrength = 0.0
    var barrelDiameter = 0.0
    var coeffPerSecondMin = 0.0
    @JsonInclude
    var deadZone = listOf<List<Double>>()
    var delim = 0.0
    var ellipseRangeMax = 0.0
    var ellipseRangeMin = 0.0
    var horizSector = listOf<Double>()
    var id = 0L
    var idealDistance = 0.0
    var idealRadius = 0.0
    var index = ""
    var maxEllipseRanging = 0.0
    var medEllipseRanging = 0.0
    var minEllipseRanging = 0.0
    var minRadius = 0.0
    var name = ""
    var numBarrels = 0
    var onMoveTarPosCoeffDelim = 0.0
    var onMoveTarPosCoeffMaxDist = 0.0
    var onMoveTarPosCoeffZero = 0.0
    var onMoveTarPosDelim = 0.0
    var position = listOf<Double>()
    var radiusOnDelim = 0.0
    var radiusOnMax = 0.0
    var radiusOnZero = 0.0
    var reduceTime = 0.0
    var rotationSpeed = mutableListOf<Double>()
    var shotDelay = 0.0
    var smallGun = false
    var smokePenalty = 0.0
    var typeinfo = TypeInfo()
    var vertSector = listOf<Double>()
}