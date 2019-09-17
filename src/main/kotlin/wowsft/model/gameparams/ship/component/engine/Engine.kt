package wowsft.model.gameparams.ship.component.engine

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Engine {
    var backwardEngineForsag = 0f
    var backwardEngineForsagMaxSpeed = 0f
    var backwardEngineUpTime = 0f
    var backwardSpeedOnFlood = 0f
    var damagedEnginePowerMultiplier = 0f
    var damagedEnginePowerTimeMultiplier = 0f
    var forwardEngineForsag = 0f
    var forwardEngineForsagMaxSpeed = 0f
    var forwardEngineUpTime = 0f
    var forwardSpeedOnFlood = 0f
    var histEnginePower = 0f
    var speedCoef = 0f
}
