package WoWSFT.model.gameparams.ship.component.artillery

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Turret
{
    val ammoList = listOf<String>()
    val antiAirAuraDistance = 0.0
    val antiAirAuraStrength = 0.0
    val barrelDiameter = 0.0
    val coeffPerSecondMin = 0.0
    val deadZone = listOf<List<Double>>()
    val delim = 0.0
    val ellipseRangeMax = 0.0
    val ellipseRangeMin = 0.0
    val horizSector = listOf<Double>()
    val id = 0L
    val idealDistance = 0.0
    val idealRadius = 0.0
    val index = ""
    val maxEllipseRanging = 0.0
    val medEllipseRanging = 0.0
    val minEllipseRanging = 0.0
    val minRadius = 0.0
    val name = ""
    val numBarrels = 0
    val onMoveTarPosCoeffDelim = 0.0
    val onMoveTarPosCoeffMaxDist = 0.0
    val onMoveTarPosCoeffZero = 0.0
    val onMoveTarPosDelim = 0.0
    val position = listOf<Double>()
    val radiusOnDelim = 0.0
    val radiusOnMax = 0.0
    val radiusOnZero = 0.0
    val reduceTime = 0.0
    val rotationSpeed = listOf<Double>()
    val shotDelay = 0.0
    val smallGun = false
    val smokePenalty = 0.0
    val typeinfo = TypeInfo()
    val vertSector = listOf<Double>()
}