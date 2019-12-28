package WoWSFT.model.gameparams.ship

import WoWSFT.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class BattleLevels
{
    @JsonProperty("COOPERATIVE")
    val cooperative = listOf<Int>()
    @JsonProperty("PVP")
    val pvp = listOf<Int>()
}