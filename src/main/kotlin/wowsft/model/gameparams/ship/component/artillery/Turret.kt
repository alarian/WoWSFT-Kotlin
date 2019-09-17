package wowsft.model.gameparams.ship.component.artillery

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Turret {
    var ammoList = ArrayList<String>()
    var antiAirAuraDistance = 0f
    var antiAirAuraStrength = 0f
    var barrelDiameter = 0f
    var coeffPerSecondMin = 0f
    var deadZone = ArrayList<MutableList<Float>>()
    var delim = 0f
    var ellipseRangeMax = 0f
    var ellipseRangeMin = 0f
    var horizSector = ArrayList<Float>()
    var id = 0L
    var idealDistance = 0f
    var idealRadius = 0f
    var index = ""
    var maxEllipseRanging = 0f
    var medEllipseRanging = 0f
    var minEllipseRanging = 0f
    var minRadius = 0f
    var name = ""
    var numBarrels = 0
    var onMoveTarPosCoeffDelim = 0f
    var onMoveTarPosCoeffMaxDist = 0f
    var onMoveTarPosCoeffZero = 0f
    var onMoveTarPosDelim = 0f
    var position = ArrayList<Float>()
    var radiusOnDelim = 0f
    var radiusOnMax = 0f
    var radiusOnZero = 0f
    var reduceTime = 0f
    var rotationSpeed = ArrayList<Float>()
    var shotDelay = 0f
    var smallGun = false
    var smokePenalty = 0f
    var typeinfo = TypeInfo()
    var vertSector = ArrayList<Float>()
}
