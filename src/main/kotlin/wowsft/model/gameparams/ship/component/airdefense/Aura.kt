package wowsft.model.gameparams.ship.component.airdefense

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Aura {
    var areaDamage = 0.toDouble()
    var areaDamageFrequency = 0.toDouble()
    var bubbleDamage = 0.toDouble()
    var bubbleDuration = 0.toDouble()
    var bubbleRadius = 0.toDouble()
    var enableBarrage = false
    var enableCrewSelectedTargetCoeff = false
    var explosionCount = 0.toDouble()
    var guns = ArrayList<String>()
    var halfOuterBubbleZone = 0.toDouble()
    var hitChance = 0.toDouble()
    var innerBubbleCount = 0.toDouble()
    var innerBubbleSpawnTimeRange = ArrayList<Float>()
    var innerBubbleZone = 0.toDouble()
    @JsonProperty("isJoint")
    var joint = false
    var maxBubbleActivationDelay = 0.toDouble()
    var maxDistance = 0.toDouble()
    var maxDistanceStartWorkGap = 0.toDouble()
    var minBubbleActivationDelay = 0.toDouble()
    var minDistance = 0.toDouble()
    var outerBubbleCount = 0.toDouble()
    var outerBubbleSpawnTimeRange = ArrayList<Float>()
    var shotDelay = 0.toDouble()
    var shotTravelTime = 0.toDouble()
    var timeUniversalsOff = 0.toDouble()
    var timeUniversalsOn = 0.toDouble()
    var type: String = ""
    var bubbleDamageModifier = 7.0f
}
