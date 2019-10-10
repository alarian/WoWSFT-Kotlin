package wowsft.model.gameparams.ship.component.flightcontrol

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonAlias

import java.util.LinkedHashMap

@JsonIgnoreProperties(ignoreUnknown = true)
class FlightControl {
    var consumablePlanesReserve = LinkedHashMap<String, Int>()
    var consumableSquadrons = ArrayList<Any>()
    var hasConsumablePlanes = false
    @JsonAlias("isGroundAttackEnabled")
    var groundAttackEnabled = false
    var planesReserveAssignment = LinkedHashMap<String, Int>()
    var prepareTimeFactor = 0.0
    var squadrons = ArrayList<String>()
}
