package wowsft.model.gameparams.ship.abilities

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonInclude

@WoWSFT
class AbilitySlot {
    var abils = ArrayList<MutableList<String>>()
    @JsonInclude
    var slot = 0
}
