package WoWSFT.model.gameparams.ship

import WoWSFT.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class BattleLevels {
    @JsonProperty("COOPERATIVE")
    var cooperative = listOf<Int>()

    @JsonProperty("PVP")
    var pvp = listOf<Int>()
}