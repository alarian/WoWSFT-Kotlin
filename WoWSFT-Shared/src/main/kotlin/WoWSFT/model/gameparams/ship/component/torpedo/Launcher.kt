package WoWSFT.model.gameparams.ship.component.torpedo

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Launcher
{
    var ammoList = listOf<String>()
    var barrelDiameter = 0.0
    var canRotate = false
    @JsonInclude
    var deadZone = listOf<List<Double>>()
    var horizSector = listOf<Double>()
    var id = 0L
    var index = ""
    var mainSector = listOf<Double>()
    var name = ""
    var numAmmos = 0
    var numBarrels = 0
    var position = listOf<Double>()
    var rotationSpeed = listOf<Double>()
    var shootSector = listOf<Double>()
    var shotDelay = 0.0
    var smallGun = false
    var timeBetweenShots = 0.0
    var timeToChangeAngle = 0.0
    var timeToChangeSpeed = 0.0
    var torpedoAngles = listOf<Double>()
    var typeinfo = TypeInfo()
    var vertSector = listOf<Double>()
}