package wowsft.model.gameparams.commander

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class CrewPersonality
{
    var dismissable = false
    var hasManyNations = false
    var hasOverlay = false
    var hasRank = false
    @JsonAlias("person")
    var person = false
    @JsonAlias("retrainable")
    var retrainable = false
    @JsonAlias("unique")
    var unique = false
    var canResetSkillsForFree = false
    var peculiarity: String? = null
    var personName: String? = null
    var ships: Ships? = null
    var costCR = 0
    var costELXP = 0
    var costGold = 0
    var costXP = 0
}
