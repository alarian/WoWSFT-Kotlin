package WoWSFT.model.gameparams.ship.component.airarmament

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class AirArmament
{
    val auraCoeff = 0.0
    val deckPlaceCount = 0.0
    val isIndependentLaunchpad = false
    val launchPrepareTime = 0.0
    val launchpadType = ""
    val planesReserveCapacity = 0.0
}