package WoWSFT.model.gameparams.commander

import WoWSFT.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class CrewPersonality {
    var dismissable = false
    var hasManyNations = false
    var hasOverlay = false
    var hasRank = false

    @JsonAlias("isPerson")
    var person = false

    @JsonAlias("isRetrainable")
    var retrainable = false

    @JsonAlias("isUnique")
    var unique = false
    var canResetSkillsForFree = false
    var peculiarity = ""
    var personName = ""
    var ships = Ships()
    var costCR = 0
    var costELXP = 0
    var costGold = 0
    var costXP = 0
}