package wowsft.model.gameparams.ship.component.artillery

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Turret {
    var ammoList = ArrayList<String>()
    var antiAirAuraDistance = 0.toDouble()
    var antiAirAuraStrength = 0.toDouble()
    var barrelDiameter = 0.toDouble()
    var coeffPerSecondMin = 0.toDouble()
    var deadZone = ArrayList<MutableList<Float>>()
    var delim = 0.toDouble()
    var ellipseRangeMax = 0.toDouble()
    var ellipseRangeMin = 0.toDouble()
    var horizSector = ArrayList<Float>()
    var id: Long = 0
    var idealDistance = 0.toDouble()
    var idealRadius = 0.toDouble()
    var index: String = ""
    var maxEllipseRanging = 0.toDouble()
    var medEllipseRanging = 0.toDouble()
    var minEllipseRanging = 0.toDouble()
    var minRadius = 0.toDouble()
    var name: String = ""
    var numBarrels = 0
    var onMoveTarPosCoeffDelim = 0.toDouble()
    var onMoveTarPosCoeffMaxDist = 0.toDouble()
    var onMoveTarPosCoeffZero = 0.toDouble()
    var onMoveTarPosDelim = 0.toDouble()
    var position = ArrayList<Float>()
    var radiusOnDelim = 0.toDouble()
    var radiusOnMax = 0.toDouble()
    var radiusOnZero = 0.toDouble()
    var reduceTime = 0.toDouble()
    var rotationSpeed = ArrayList<Float>()
    var shotDelay = 0.toDouble()
    var smallGun = false
    var smokePenalty = 0.toDouble()
    var typeinfo = TypeInfo()
    var vertSector = ArrayList<Float>()
}
