package wowsft.model.gameparams.ship.component.atba

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Secondary {
    var ammoList = ArrayList<String>()
    var antiAirAuraDistance = 0.toDouble()
    var antiAirAuraStrength = 0.toDouble()
    var barrelDiameter = 0.toDouble()
    var deadZone = ArrayList<MutableList<Float>>()
    var delim = 0.toDouble()
    var horizSector = ArrayList<Float>()
    var id: Long = 0
    var idealDistance = 0.toDouble()
    var idealRadius = 0.toDouble()
    var index: String = ""
    var minRadius = 0.toDouble()
    var name: String = ""
    var numBarrels = 0
    var radiusOnDelim = 0.toDouble()
    var radiusOnMax = 0.toDouble()
    var radiusOnZero = 0.toDouble()
    var rotationSpeed = ArrayList<Float>()
    var shotDelay = 0.toDouble()
    var smallGun = false
    var smokePenalty = 0.toDouble()
    var typeinfo = TypeInfo()
    var vertSector = ArrayList<Float>()
    @JsonInclude
    var GSIdealRadius: Double = 1.0

    var count = 0

    var alphaDamage = 0.toDouble()
    var alphaPiercingHE = 0.toDouble()
    var ammoType: String = ""
    var bulletSpeed = 0.toDouble()
    var burnProb = 0.toDouble()

    val alphaPiercingHEReal
        get() = alphaPiercingHE - 1f
}
