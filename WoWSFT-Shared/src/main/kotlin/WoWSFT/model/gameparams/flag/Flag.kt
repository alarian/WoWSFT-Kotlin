package WoWSFT.model.gameparams.flag

import WoWSFT.config.WoWSFT
import WoWSFT.model.Constant.CDN_IMAGE
import WoWSFT.model.gameparams.CommonModifier
import WoWSFT.model.gameparams.TypeInfo

@WoWSFT
class Flag : CommonModifier()
{
    val canBuy = false
    val canBuyCustom: Any? = null
    val flags = listOf<String>()
    val group = 0
    val hidden = false
    val hiddenCustom: Any? = null
    val id = 0L
    val index = ""
    val name = ""
    val typeinfo = TypeInfo()
    val sortOrder = 0
    val identifier = ""
    val bonus = LinkedHashMap<String, String>()
    val description = ""
    val image get() = "$CDN_IMAGE/signal_flags/$name.png"
}