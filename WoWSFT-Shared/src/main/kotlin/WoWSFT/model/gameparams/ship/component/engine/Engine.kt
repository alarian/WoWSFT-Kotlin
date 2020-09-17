package WoWSFT.model.gameparams.ship.component.engine

import WoWSFT.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Engine {
    var backwardEngineForsag = 0.0
    var backwardEngineForsagMaxSpeed = 0.0
    var backwardEngineUpTime = 0.0
    var backwardSpeedOnFlood = 0.0
    var damagedEnginePowerMultiplier = 0.0
    var damagedEnginePowerTimeMultiplier = 0.0
    var forwardEngineForsag = 0.0
    var forwardEngineForsagMaxSpeed = 0.0
    var forwardEngineUpTime = 0.0
    var forwardSpeedOnFlood = 0.0
    var histEnginePower = 0.0
    var speedCoef = 0.0
}