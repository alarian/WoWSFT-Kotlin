package wowsft.model.gameparams.ship

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonAlias

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class BattleLevels {
    @JsonAlias("COOPERATIVE")
    val cooperative = ArrayList<Int>()
    @JsonAlias("PVP")
    val pvp = ArrayList<Int>()
}
