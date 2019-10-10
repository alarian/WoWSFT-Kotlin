package wowsft.model.gameparams.ship.component.artillery

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Turret
{
    var ammoList = ArrayList<String>()
    var antiAirAuraDistance = 0.0
    var antiAirAuraStrength = 0.0
    var barrelDiameter = 0.0
    var coeffPerSecondMin = 0.0
    var deadZone = ArrayList<MutableList<Double>>()
    var delim = 0.0
    var ellipseRangeMax = 0.0
    var ellipseRangeMin = 0.0
    var horizSector = ArrayList<Double>()
    var id: Long = 0
    var idealDistance = 0.0
    var idealRadius = 0.0
    var index: String? = null
    var maxEllipseRanging = 0.0
    var medEllipseRanging = 0.0
    var minEllipseRanging = 0.0
    var minRadius = 0.0
    var name: String? = null
    var numBarrels = 0
    var onMoveTarPosCoeffDelim = 0.0
    var onMoveTarPosCoeffMaxDist = 0.0
    var onMoveTarPosCoeffZero = 0.0
    var onMoveTarPosDelim = 0.0
    var position = ArrayList<Double>()
    var radiusOnDelim = 0.0
    var radiusOnMax = 0.0
    var radiusOnZero = 0.0
    var reduceTime = 0.0
    var rotationSpeed = ArrayList<Double>()
    var shotDelay = 0.0
    var smallGun = false
    var smokePenalty = 0.0
    var typeinfo : TypeInfo? = null
    var vertSector = ArrayList<Double>()
}
