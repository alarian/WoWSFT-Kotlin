package wowsft.model.gameparams.flag

import wowsft.config.WoWSFT
import wowsft.model.gameparams.CommonModifier
import wowsft.model.gameparams.TypeInfo

import java.util.LinkedHashMap

@WoWSFT
class Flag : CommonModifier() {
    var canBuy = false
    var canBuyCustom = Any()
    var flags = ArrayList<String>()
    var group = 0
    var hidden = false
    var hiddenCustom = Any()
    var id: Long = 0
    var index: String = ""
    var name: String = ""
    var typeinfo = TypeInfo()
    var sortOrder = 0
    var identifier: String = ""
    var bonus = LinkedHashMap<String, String>()
    var description: String = ""
    val image = if (name.isNotEmpty()) "https://cdn.wowsft.com/images/signal_flags/$name.png" else ""
}
