package wowsft.model.gameparams.ship

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class BattleLevels {
    @JsonProperty("COOPERATIVE")
    val cooperative = ArrayList<Int>()
    @JsonProperty("PVP")
    val pvp = ArrayList<Int>()
}
