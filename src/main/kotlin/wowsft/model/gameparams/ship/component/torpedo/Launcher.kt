package wowsft.model.gameparams.ship.component.torpedo

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Launcher {
    var ammoList = ArrayList<String>()
    var barrelDiameter = 0.0
    var canRotate = false
    var deadZone = ArrayList<MutableList<Double>>()
    var horizSector = ArrayList<Double>()
    var id: Long = 0
    var index: String? = null
    var mainSector = ArrayList<Double>()
    var name: String? = null
    var numAmmos = 0
    var numBarrels = 0
    var position = ArrayList<Double>()
    var rotationSpeed = ArrayList<Double>()
    var shootSector = ArrayList<Double>()
    var shotDelay = 0.0
    var smallGun = false
    var timeBetweenShots = 0.0
    var timeToChangeAngle = 0.0
    var timeToChangeSpeed = 0.0
    var torpedoAngles = ArrayList<Double>()
    var typeinfo : TypeInfo? = null
    var vertSector = ArrayList<Double>()
}
