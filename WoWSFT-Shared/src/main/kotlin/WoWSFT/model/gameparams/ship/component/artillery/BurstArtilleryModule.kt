package WoWSFT.model.gameparams.ship.component.artillery

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.CommonModifier

@WoWSFT
class BurstArtilleryModule {
    var burstReloadTime = 0.0
    var fullReloadTime = 0.0
    var shotIntensity = 0.0
    var shotsCount = 0
    var modifiers = CommonModifier()
}