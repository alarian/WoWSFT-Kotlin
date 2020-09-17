package WoWSFT.model.gameparams.ship.abilities

import WoWSFT.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonInclude

@WoWSFT
class AbilitySlot {
    var abils = mutableListOf<MutableList<String>>()

    @JsonInclude
    var slot = 0
}