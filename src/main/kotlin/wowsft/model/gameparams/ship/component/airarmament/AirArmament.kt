package wowsft.model.gameparams.ship.component.airarmament

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class AirArmament
{
    var auraCoeff = 0.0
    var deckPlaceCount = 0.0
    var isIndependentLaunchpad = false
    var launchPrepareTime = 0.0
    var launchpadType: String? = null
    var planesReserveCapacity = 0.0
}
