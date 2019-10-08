package wowsft.model.gameparams.commander

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

import java.util.ArrayList
import java.util.LinkedHashMap

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Commander {
    @JsonProperty("CrewPersonality")
    var crewPersonality = CrewPersonality()
    var id: Long = 0
    var identifier: String = ""
    var index: String = ""
    var name: String = ""
    var typeinfo = TypeInfo()
    @JsonProperty("cSkills")
    var cSkills = ArrayList<MutableList<Skill>>()

    @JsonIgnore
    private val mapper = ObjectMapper()

    init {
        for (i in 1..4) {
            val s = ArrayList<Skill>()
            for (j in 1..8) {
                s.add(Skill())
            }
            cSkills.add(s)
        }
    }

    @JsonAnySetter
    fun setSkills(name: String, value: Any) {
        if (name.equals("Skills", ignoreCase = true)) {
            val temp = mapper.convertValue<LinkedHashMap<String, Skill>>(value, object : TypeReference<LinkedHashMap<String, Skill>>() {})

            temp.forEach { (key, value) ->
                value.modifier = key
                cSkills[value.tier - 1][value.column] = value
            }
        }
    }
}
