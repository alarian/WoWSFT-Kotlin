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
    val tier = 0
    @JsonInclude
    val column = 0
    @JsonIgnore
    val skillType = 0
    @JsonIgnore
    val turnOffOnRetraining = false
    @JsonAlias("isEpic")
    val epic = false
    val bonus = LinkedHashMap<String, String>()
    val description = ""
    val image get() = "$CDN_IMAGE/skills/icon_perk_$modifier.png"
}