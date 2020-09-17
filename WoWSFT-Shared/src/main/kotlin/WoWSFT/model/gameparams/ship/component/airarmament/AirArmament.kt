package WoWSFT.model.gameparams.ship.component.airarmament

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class AirArmament {
    var auraCoeff = 0.0
    var deckPlaceCount = 0
    var independentLaunchpad = false
    var launchPrepareTime = 0.0
    var launchpadType = ""
    var planesReserveCapacity = 0
}