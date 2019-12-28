package WoWSFT.model.gameparams.commander

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Commander
{
    @JsonProperty("CrewPersonality")
    val crewPersonality = CrewPersonality()
    val id = 0L
    val identifier = ""
    val index = ""
    val name = ""
    val typeinfo = TypeInfo()
    @JsonProperty("cSkills")
    var cSkills = MutableList(4) { MutableList(8) { Skill() } }

    @JsonIgnore
    private val mapper = ObjectMapper()

    @JsonAnySetter
    fun setSkills(name: String, value: Any?) {
        if (name.equals("Skills", ignoreCase = true)) {
            val temp = mapper.convertValue(value, object : TypeReference<LinkedHashMap<String, Skill>>() {})
            temp.forEach { (k: String, v: Skill) ->
                v.modifier = k
                cSkills[v.tier - 1][v.column] = v
            }
        }
    }
}