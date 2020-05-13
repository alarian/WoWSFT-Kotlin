package WoWSFT.model.gameparams.ship.component.hydrophone

import WoWSFT.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Hydrophone
{
    var acousticWaveLifetime = 0.0
    var acousticWaveSpeed = 0
    var updateFrequency = 0.0
    var zoneLifetime = 0.0
}