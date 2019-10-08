package wowsft.model.gameparams.ship.component.engine

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Engine {
    var backwardEngineForsag = 0.toDouble()
    var backwardEngineForsagMaxSpeed = 0.toDouble()
    var backwardEngineUpTime = 0.toDouble()
    var backwardSpeedOnFlood = 0.toDouble()
    var damagedEnginePowerMultiplier = 0.toDouble()
    var damagedEnginePowerTimeMultiplier = 0.toDouble()
    var forwardEngineForsag = 0.toDouble()
    var forwardEngineForsagMaxSpeed = 0.toDouble()
    var forwardEngineUpTime = 0.toDouble()
    var forwardSpeedOnFlood = 0.toDouble()
    var histEnginePower = 0.toDouble()
    var speedCoef = 0.toDouble()
}
