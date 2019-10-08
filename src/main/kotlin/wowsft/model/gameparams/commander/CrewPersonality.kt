package wowsft.model.gameparams.commander

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class CrewPersonality {
    var dismissable = false
    var hasManyNations = false
    var hasOverlay = false
    var hasRank = false
    @JsonProperty("person")
    var person = false
    @JsonProperty("retrainable")
    var retrainable = false
    @JsonProperty("unique")
    var unique = false
    var canResetSkillsForFree = false
    var peculiarity: String = ""
    var personName: String = ""
    var ships = Ships()
    var costCR = 0
    var costELXP = 0
    var costGold = 0
    var costXP = 0
}
