package wowsft.model.gameparams.ship.component.atba

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Secondary {
    var ammoList = ArrayList<String>()
    var antiAirAuraDistance = 0.0
    var antiAirAuraStrength = 0.0
    var barrelDiameter = 0.0
    var deadZone = ArrayList<MutableList<Double>>()
    var delim = 0.0
    var horizSector = ArrayList<Double>()
    var id: Long = 0
    var idealDistance = 0.0
    var idealRadius = 0.0
    var index: String? = null
    var minRadius = 0.0
    var name: String? = null
    var numBarrels = 0
    var radiusOnDelim = 0.0
    var radiusOnMax = 0.0
    var radiusOnZero = 0.0
    var rotationSpeed = ArrayList<Double>()
    var shotDelay = 0.0
    var smallGun = false
    var smokePenalty = 0.0
    var typeinfo : TypeInfo? = null
    var vertSector = ArrayList<Double>()
    @JsonInclude
    var GSIdealRadius = 1.0

    var count = 0

    var alphaDamage = 0.0
    var alphaPiercingHE = 0.0
    var ammoType: String? = null
    var bulletSpeed = 0.0
    var burnProb = 0.0

    val alphaPiercingHEReal
        get() = alphaPiercingHE - 1.0
}
