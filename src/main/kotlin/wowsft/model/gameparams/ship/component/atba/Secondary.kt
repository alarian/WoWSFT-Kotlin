package wowsft.model.gameparams.ship.component.atba

import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Secondary {
    var ammoList = ArrayList<String>()
    var antiAirAuraDistance = 0f
    var antiAirAuraStrength = 0f
    var barrelDiameter = 0f
    var deadZone = ArrayList<MutableList<Float>>()
    var delim = 0f
    var horizSector = ArrayList<Float>()
    var id = 0L
    var idealDistance = 0f
    var idealRadius = 0f
    var index = ""
    var minRadius = 0f
    var name = ""
    var numBarrels = 0
    var radiusOnDelim = 0f
    var radiusOnMax = 0f
    var radiusOnZero = 0f
    var rotationSpeed = ArrayList<Float>()
    var shotDelay = 0f
    var smallGun = false
    var smokePenalty = 0f
    var typeinfo = TypeInfo()
    var vertSector = ArrayList<Float>()
    @JsonInclude
    var GSIdealRadius = 1f

    var count = 0

    var alphaDamage = 0f
    var alphaPiercingHE = 0f
    var ammoType = ""
    var bulletSpeed = 0f
    var burnProb = 0f

    val alphaPiercingHEReal
        get() = alphaPiercingHE - 1f
}
