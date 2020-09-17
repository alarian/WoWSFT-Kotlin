package WoWSFT.model.gameparams.commander

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.TypeInfo
import WoWSFT.utils.CommonUtils.mapper
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Commander {
    @JsonProperty("CrewPersonality")
    var crewPersonality = CrewPersonality()
    var id = 0L
    var identifier = ""
    var index = ""
    var name = ""
    var typeinfo = TypeInfo()

    @JsonProperty("CrewSkills")
    var crewSkills = MutableList(4) { MutableList(8) { Skill() } }

    @JsonAnySetter
    fun setSkills(name: String, value: Any?) {
        if (name.equals("Skills", ignoreCase = true)) {
            mapper.convertValue(value, jacksonTypeRef<LinkedHashMap<String, Skill>>()).also { temp ->
                temp.forEach { (k: String, v: Skill) ->
                    v.modifier = k
                    crewSkills[v.tier - 1][v.column] = v
                }
            }
        }
    }
}