package wowsft.model.gameparams.commander

import wowsft.config.WoWSFT
import wowsft.model.gameparams.CommonModifier
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

import java.util.LinkedHashMap

@WoWSFT
class Skill : CommonModifier()
{
    var tier = 0
    @JsonInclude
    var column = 0
    @JsonIgnore
    var skillType = 0
    @JsonIgnore
    var turnOffOnRetraining = false

    @JsonAlias("epic")
    var epic = false

    var bonus = LinkedHashMap<String, String>()
    var description: String? = null
    val image: String
        get() = if (!modifier.isNullOrBlank()) "https://cdn.wowsft.com/images/crew_commander/skills/icon_perk_$modifier.png" else ""
}
