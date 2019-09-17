package wowsft.model.gameparams.ship.component.torpedo

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Launcher {
    var ammoList = ArrayList<String>()
    var barrelDiameter = 0f
    var canRotate = false
    var deadZone = ArrayList<MutableList<Float>>()
    var horizSector = ArrayList<Float>()
    var id = 0L
    var index = ""
    var mainSector = ArrayList<Float>()
    var name = ""
    var numAmmos = 0
    var numBarrels = 0
    var position = ArrayList<Float>()
    var rotationSpeed = ArrayList<Float>()
    var shootSector = ArrayList<Float>()
    var shotDelay = 0f
    var smallGun = false
    var timeBetweenShots = 0f
    var timeToChangeAngle = 0f
    var timeToChangeSpeed = 0f
    var torpedoAngles = ArrayList<Float>()
    var typeinfo = TypeInfo()
    var vertSector = ArrayList<Float>()
}
