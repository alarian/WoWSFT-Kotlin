package WoWSFT.model.gameparams.ship.upgrades

import WoWSFT.config.WoWSFT
import WoWSFT.model.Constant.CDN_IMAGE
import WoWSFT.model.Constant.fireControl
import WoWSFT.model.Constant.suo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class ShipUpgrade
{
    var disabledAbilities = mutableListOf<String>()
    var nextShips = mutableListOf<String>()
    var fullName = ""
    var name = ""
    var prev = ""
    var ucType = ""
        set(value) {
            field = value
            ucTypeShort = if (field.isNotEmpty()) field.replace("_", "").decapitalize() else field
        }
    var ucTypeShort = ""
    var position = 0
    @JsonInclude
    var elem = 0
    var prevType = ""
        get() = if (field.isEmpty()) ucTypeShort else field
    @JsonInclude
    var prevPosition = 0
    var prevElem = 0
    var components = LinkedHashMap<String, MutableList<String>>()
    val image get() = if (ucTypeShort.isNotEmpty()) "$CDN_IMAGE/modules/${ucTypeShort.toLowerCase()}.png" else ""

    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonSetter
    fun setComponents(value: Any?)
    {
        val temp = mapper.convertValue(value, object : TypeReference<LinkedHashMap<String, MutableList<String>>>() {})
        temp.forEach { (key: String, list: MutableList<String>) ->
            val name = if (key.equals(fireControl, ignoreCase = true)) suo else key
            list.sort()
            components[name] = list
        }
    }
}