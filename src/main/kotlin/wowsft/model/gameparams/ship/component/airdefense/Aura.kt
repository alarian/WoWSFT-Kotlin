package wowsft.model.gameparams.ship.component.airdefense

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Aura {
    var areaDamage = 0f
    var areaDamageFrequency = 0f
    var bubbleDamage = 0f
    var bubbleDuration = 0f
    var bubbleRadius = 0f
    var enableBarrage = false
    var enableCrewSelectedTargetCoeff = false
    var explosionCount = 0f
    var guns = ArrayList<String>()
    var halfOuterBubbleZone = 0f
    var hitChance = 0f
    var innerBubbleCount = 0f
    var innerBubbleSpawnTimeRange = ArrayList<Float>()
    var innerBubbleZone = 0f
    @JsonProperty("isJoint")
    var joint = false
    var maxBubbleActivationDelay = 0f
    var maxDistance = 0f
    var maxDistanceStartWorkGap = 0f
    var minBubbleActivationDelay = 0f
    var minDistance = 0f
    var outerBubbleCount = 0f
    var outerBubbleSpawnTimeRange = ArrayList<Float>()
    var shotDelay = 0f
    var shotTravelTime = 0f
    var timeUniversalsOff = 0f
    var timeUniversalsOn = 0f
    var type = ""
    var bubbleDamageModifier = 7.0f
}
