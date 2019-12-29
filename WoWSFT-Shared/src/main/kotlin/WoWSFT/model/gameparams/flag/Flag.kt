package WoWSFT.model.gameparams.flag

import WoWSFT.config.WoWSFT
import WoWSFT.model.Constant.CDN_IMAGE
import WoWSFT.model.gameparams.CommonModifier
import WoWSFT.model.gameparams.TypeInfo

@WoWSFT
class Flag : CommonModifier()
{
    var canBuy = false
    var canBuyCustom: Any? = null
    var flags = listOf<String>()
    var group = 0
    var hidden = false
    var hiddenCustom: Any? = null
    var id = 0L
    var index = ""
    var name = ""
    var typeinfo = TypeInfo()
    var sortOrder = 0
    var identifier = ""
    var bonus = LinkedHashMap<String, String>()
    var description = ""
    val image get() = "$CDN_IMAGE/signal_flags/$name.png"
}