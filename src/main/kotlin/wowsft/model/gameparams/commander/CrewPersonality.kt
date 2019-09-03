package wowsft.model.gameparams.commander

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class CrewPersonality {
    var dismissable: Boolean = false
    var hasManyNations: Boolean = false
    var hasOverlay: Boolean = false
    var hasRank: Boolean = false
    @JsonAlias("isPerson")
    var person: Boolean = false
    @JsonAlias("isRetrainable")
    var retrainable: Boolean = false
    @JsonAlias("isUnique")
    var unique: Boolean = false
    var canResetSkillsForFree: Boolean = false
    var peculiarity: String = ""
    var personName: String = ""
    var ships: Ships? = null
    var costCR: Int = 0
    var costELXP: Int = 0
    var costGold: Int = 0
    var costXP: Int = 0
}
