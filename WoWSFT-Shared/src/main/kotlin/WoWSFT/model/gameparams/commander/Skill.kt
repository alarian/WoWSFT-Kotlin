package WoWSFT.model.gameparams.commander

import WoWSFT.config.WoWSFT
import WoWSFT.model.Constant.CDN_IMAGE
import WoWSFT.model.gameparams.CommonModifier
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

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
    @JsonAlias("isEpic")
    var epic = false
    var bonus = LinkedHashMap<String, String>()
    var description = ""
    val image get() = "$CDN_IMAGE/skills/icon_perk_$modifier.png"
}