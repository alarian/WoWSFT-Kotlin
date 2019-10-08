package wowsft.model.gameparams.ship.component.torpedo

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Launcher {
    var ammoList = ArrayList<String>()
    var barrelDiameter = 0.toDouble()
    var canRotate = false
    var deadZone = ArrayList<MutableList<Float>>()
    var horizSector = ArrayList<Float>()
    var id: Long = 0
    var index: String = ""
    var mainSector = ArrayList<Float>()
    var name: String = ""
    var numAmmos = 0
    var numBarrels = 0
    var position = ArrayList<Float>()
    var rotationSpeed = ArrayList<Float>()
    var shootSector = ArrayList<Float>()
    var shotDelay = 0.toDouble()
    var smallGun = false
    var timeBetweenShots = 0.toDouble()
    var timeToChangeAngle = 0.toDouble()
    var timeToChangeSpeed = 0.toDouble()
    var torpedoAngles = ArrayList<Float>()
    var typeinfo = TypeInfo()
    var vertSector = ArrayList<Float>()
}
