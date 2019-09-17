package wowsft.model.gameparams.ship.component.airarmament

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class AirArmament {
    var auraCoeff = 0f
    var deckPlaceCount = 0f
    var isIndependentLaunchpad = false
    var launchPrepareTime = 0f
    var launchpadType = ""
    var planesReserveCapacity = 0f
}
