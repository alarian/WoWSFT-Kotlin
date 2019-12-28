package WoWSFT.model.gameparams.ship.component.airdefense

import WoWSFT.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Aura
{
    val areaDamage = 0.0
    val areaDamageFrequency = 0.0
    val bubbleDamage = 0.0
    val bubbleDuration = 0.0
    val bubbleRadius = 0.0
    val enableBarrage = false
    val enableCrewSelectedTargetCoeff = false
    val explosionCount = 0.0
    val guns = listOf<String>()
    val halfOuterBubbleZone = 0.0
    val hitChance = 0.0
    val innerBubbleCount = 0.0
    val innerBubbleSpawnTimeRange = listOf<Double>()
    val innerBubbleZone = 0.0
    @JsonAlias("isJoint")
    val joint = false
    val maxBubbleActivationDelay = 0.0
    val maxDistance = 0.0
    val maxDistanceStartWorkGap = 0.0
    val minBubbleActivationDelay = 0.0
    val minDistance = 0.0
    val outerBubbleCount = 0.0
    val outerBubbleSpawnTimeRange = listOf<Double>()
    val shotDelay = 0.0
    val shotTravelTime = 0.0
    val timeUniversalsOff = 0.0
    val timeUniversalsOn = 0.0
    val type = ""
    val bubbleDamageModifier = 7.0
}