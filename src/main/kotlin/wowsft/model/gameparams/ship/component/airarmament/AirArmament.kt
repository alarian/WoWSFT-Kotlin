package wowsft.model.gameparams.ship.component.airarmament

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class AirArmament {
    var auraCoeff = 0.toDouble()
    var deckPlaceCount = 0.toDouble()
    var isIndependentLaunchpad = false
    var launchPrepareTime = 0.toDouble()
    var launchpadType: String = ""
    var planesReserveCapacity = 0.toDouble()
}
