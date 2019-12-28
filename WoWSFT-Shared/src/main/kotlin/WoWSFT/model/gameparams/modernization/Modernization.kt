package WoWSFT.model.gameparams.modernization

import WoWSFT.config.WoWSFT
import WoWSFT.model.Constant.CDN_IMAGE
import WoWSFT.model.gameparams.CommonModifier
import WoWSFT.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonInclude

@WoWSFT
class Modernization : CommonModifier()
{
    val costCR = 0
    val costGold = 0
    val excludes = mutableListOf<String>()
    val group = mutableListOf<String>()
    val id: Long = 0
    val index = ""
    val name = ""
    val nation = mutableListOf<String>()
    val shiplevel = mutableListOf<Int>()
    val ships = mutableListOf<String>()
    val shiptype = mutableListOf<String>()
    @JsonInclude
    val slot = 0
    val type = 0
    val typeinfo = TypeInfo()
    val fullName = ""
    val image get() = "$CDN_IMAGE/modernization_icons/icon_modernization_$name.png"
    val bonus = LinkedHashMap<String, String>()
    val description = ""
}