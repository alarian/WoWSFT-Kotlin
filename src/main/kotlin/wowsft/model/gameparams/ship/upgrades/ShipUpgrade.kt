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
    var fullName: String = ""
    var name: String = ""
    var prev = ""
    var ucType: String = ""
    var position: Int = 0
    @JsonInclude
    var elem: Int = 0

    var prevType: String = ""
        get() = if (prevType.isEmpty()) ucTypeShort else field
    @JsonInclude
    var prevPosition: Int = 0
    var prevElem: Int = 0
    var components = LinkedHashMap<String, List<String>>()

    @JsonIgnore
    private val mapper = ObjectMapper()

    val ucTypeShort: String = if (ucType.isNotEmpty()) ucType.replace("_", "").decapitalize() else ucType

    val image: String = if (ucTypeShort.isNotEmpty()) "https://cdn.wowsft.com/images/modules/module_" + ucTypeShort.toLowerCase() + ".png" else ""

    @JsonSetter
    fun setComponents(value: Any) {
        val temp = mapper.convertValue<LinkedHashMap<String, MutableList<String>>>(value, object : TypeReference<LinkedHashMap<String, MutableList<String>>>() {})

        temp.forEach { (key, list) ->
            val name = if (key.equals(fireControl, true)) suo else key
            list.sort()
            components[name] = list
        }
    }
}
