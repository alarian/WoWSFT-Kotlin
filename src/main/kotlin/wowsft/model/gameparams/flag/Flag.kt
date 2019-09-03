package wowsft.model.gameparams.flag

import wowsft.config.WoWSFT
import wowsft.model.gameparams.CommonModifier
import wowsft.model.gameparams.TypeInfo

import java.util.LinkedHashMap

@WoWSFT
class Flag : CommonModifier() {
    var canBuy: Boolean = false
    var canBuyCustom: Any? = null
    var flags: List<String>? = null
    var group: Int = 0
    var hidden: Boolean = false
    var hiddenCustom: Any? = null
    var id: Long = 0
    var index: String = ""
    var name: String = ""
    var typeinfo: TypeInfo? = null
    var sortOrder: Int = 0
    var identifier: String = ""
    var bonus = LinkedHashMap<String, String>()
    var description = ""
    val image: String = "https://cdn.wowsft.com/images/signal_flags/$name.png"
}
