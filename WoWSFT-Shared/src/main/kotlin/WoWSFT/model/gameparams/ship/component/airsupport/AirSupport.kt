package WoWSFT.model.gameparams.ship.component.airsupport

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.ship.component.planes.Plane
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class AirSupport
{
    var chargesNum: Int = 0
    var climbAngle: Int = 0
    var flyAwayTime: Double = 0.0
    var maxDist: Int = 0
    var maxPlaneFlightDist: Int = 0
    var minDist: Int = 0
    var planeName: String = ""
    var reloadTime: Double = 0.0
    var timeBetweenShots: Double = 0.0
    var timeFromHeaven: Double = 0.0

    var plane: Plane? = null
}