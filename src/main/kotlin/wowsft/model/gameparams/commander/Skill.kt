package wowsft.model.gameparams.commander

import wowsft.config.WoWSFT
import wowsft.model.gameparams.CommonModifier
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

import java.util.LinkedHashMap

@WoWSFT
class Skill : CommonModifier() {
    var tier: Int = 0
    @JsonInclude
    var column: Int = 0
    @JsonIgnore
    var skillType: Int = 0
    @JsonIgnore
    var turnOffOnRetraining: Boolean = false

    @JsonAlias("isEpic")
    var epic: Boolean = false

    var bonus = LinkedHashMap<String, String>()
    var description = ""
    val image: String
        get() = if (!modifier.isNullOrEmpty()) "https://cdn.wowsft.com/images/crew_commander/skills/icon_perk_$modifier.png" else ""
}
