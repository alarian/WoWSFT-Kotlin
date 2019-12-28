package WoWSFT.model.gameparams.commander

import WoWSFT.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class CrewPersonality
{
    val dismissable = false
    val hasManyNations = false
    val hasOverlay = false
    val hasRank = false
    @JsonAlias("isPerson")
    val person = false
    @JsonAlias("isRetrainable")
    val retrainable = false
    @JsonAlias("isUnique")
    val unique = false
    val canResetSkillsForFree = false
    val peculiarity = ""
    val personName = ""
    val ships = Ships()
    val costCR = 0
    val costELXP = 0
    val costGold = 0
    val costXP = 0
}