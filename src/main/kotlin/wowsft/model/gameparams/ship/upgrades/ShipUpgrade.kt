package wowsft.model.gameparams.ship.upgrades

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import wowsft.model.Constant.fireControl
import wowsft.model.Constant.suo

import java.util.ArrayList
import java.util.LinkedHashMap

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class ShipUpgrade {
    var disabledAbilities: List<String>? = null
    var nextShips = ArrayList<String>()
    var fullName: String? = null
    var name: String? = null
    var prev = ""
    var ucType: String? = null
    var position: Int = 0
    @JsonInclude
    var elem: Int = 0

    var prevType: String? = null
        get() = if (prevType.isNullOrEmpty()) ucTypeShort else field
    @JsonInclude
    var prevPosition: Int = 0
    var prevElem: Int = 0
    var components = LinkedHashMap<String, List<String>>()

    @JsonIgnore
    private val mapper = ObjectMapper()

    val ucTypeShort: String?
        get() = if (!ucType.isNullOrEmpty()) ucType!!.replace("_", "").decapitalize() else ucType

    val image: String
        get() = if (!ucTypeShort.isNullOrEmpty()) "https://cdn.wowsft.com/images/modules/module_" + ucTypeShort!!.toLowerCase() + ".png" else ""

    @JsonSetter
    fun setComponents(value: Any) {
        val temp = mapper.convertValue<LinkedHashMap<String, MutableList<String>>>(value, object : TypeReference<LinkedHashMap<String, MutableList<String>>>() {})

        temp.forEach { (key, list) ->
            val name = if (key.equals(fireControl, ignoreCase = true)) suo else key
            list.sort()
            components[name] = list
        }
    }
}
