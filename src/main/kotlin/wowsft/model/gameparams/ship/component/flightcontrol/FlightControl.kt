package wowsft.model.gameparams.ship.component.flightcontrol

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import java.util.LinkedHashMap

@JsonIgnoreProperties(ignoreUnknown = true)
class FlightControl {
    var consumablePlanesReserve = LinkedHashMap<String, Int>()
    var consumableSquadrons = ArrayList<Any>()
    var hasConsumablePlanes = false
    @JsonProperty("isGroundAttackEnabled")
    var groundAttackEnabled = false
    var planesReserveAssignment = LinkedHashMap<String, Int>()
    var prepareTimeFactor = 0f
    var squadrons = ArrayList<String>()
}
