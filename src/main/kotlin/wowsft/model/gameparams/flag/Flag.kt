package wowsft.model.gameparams.flag

import wowsft.config.WoWSFT
import wowsft.model.gameparams.CommonModifier
import wowsft.model.gameparams.TypeInfo

import java.util.LinkedHashMap

@WoWSFT
class Flag : CommonModifier()
{
    var canBuy = false
    var canBuyCustom = Any()
    var flags = ArrayList<String>()
    var group = 0
    var hidden = false
    var hiddenCustom = Any()
    var id: Long = 0
    var index: String? = null
    var name: String? = null
    var typeinfo : TypeInfo? = null
    var sortOrder = 0
    var identifier: String? = null
    var bonus = LinkedHashMap<String, String>()
    var description: String? = null
    val image: String
        get() = if (!name.isNullOrBlank()) "https://cdn.wowsft.com/images/signal_flags/$name.png" else ""
}
