package wowsft.model.gameparams.ship.component.artillery

import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

import java.util.LinkedHashMap
import kotlin.math.atan

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
class Shell {
    var alphaDamage = 0f
    var alphaPiercingCS = 0f
    var alphaPiercingHE = 0f
        set(alphaPiercingHE) {
            field = alphaPiercingHE
            this.alphaPiercingHEReal = alphaPiercingHE - 1f
        }
    var alphaPiercingHEReal = 0f
    var ammoType = ""
    var bulletAirDrag = 0f
    var bulletAlwaysRicochetAt = 0f
    var bulletCap = false
    var bulletCapNormalizeMaxAngle = 0f
    var bulletDetonator = 0f
    var bulletDetonatorSpread = 0f
    var bulletDetonatorThreshold = 0f
    var bulletDiametr = 0f
    var bulletKrupp = 0f
    var bulletMass = 0f
    var bulletPenetrationSpread = 0f
    var bulletRicochetAt = 0f
    var bulletSpeed = 0f
    var bulletUnderwaterDistFactor = 0f
    var bulletUnderwaterPenetrationFactor = 0f
    var bulletWaterDrag = 0f
    var burnProb = 0f
    var costCR = 0
    var damage = 0f
    var directDamage = 0f
    var id = 0L
    var index = ""
    var name = ""
    var typeinfo = TypeInfo()
    var uwAbility = false
    var uwCritical = 0f
    var volume = 0f
    var waterRefractionReflectDeltaAngleInterval = ArrayList<Float>()

//    @JsonIgnore
    var penetration: LinkedHashMap<String, Float>? = null
//    @JsonIgnore
    var flightTime = LinkedHashMap<String, Float>()
//    @JsonIgnore
    var impact: LinkedHashMap<String, Float>? = null
    @JsonIgnore
    var launchAngle: LinkedHashMap<String, Float>? = null
//    @JsonIgnore
//    LinkedHashMap<String, Float> vertPlus;
//    @JsonIgnore
//    LinkedHashMap<String, Float> vertMinus;
//    @JsonIgnore
    var distanceList: ArrayList<String>? = null
    var minDistV = 0f
    var penetrationAtFive = 0f
    var penetrationAtTen = 0f
    var penetrationAtFifteen = 0f
    var penetrationAtTwenty = 0f
    var penetrationAtMax = 0f
    var flightTimeAtFive = 0f
    var flightTimeAtTen = 0f
    var flightTimeAtFifteen = 0f
    var flightTimeAtTwenty = 0f
    var flightTimeAtMax = 0f
    var impactAtFive = 0f
    var impactAtTen = 0f
    var impactAtFifteen = 0f
    var impactAtTwenty = 0f
    var impactAtMax = 0f
    var vertPlusAtFive = 0f
    var vertPlusAtTen = 0f
    var vertPlusAtFifteen = 0f
    var vertPlusAtTwenty = 0f
    var vertPlusAtMax = 0f
    var vertMinusAtFive = 0f
    var vertMinusAtTen = 0f
    var vertMinusAtFifteen = 0f
    var vertMinusAtTwenty = 0f
    var vertMinusAtMax = 0f

    var penetrationIFHE = 0

    @JsonIgnore
    var distFive = 5000f
    @JsonIgnore
    var distTen = 10000f
    @JsonIgnore
    var distFifteen = 15000f
    @JsonIgnore
    var distTwenty = 20000f

    fun setShell(
        flightTime: LinkedHashMap<String, Float>,
        penetration: LinkedHashMap<String, Float>?,
        impact: LinkedHashMap<String, Float>?,
        distanceList: ArrayList<String>?,
        launchAngle: LinkedHashMap<String, Float>?,
        minDistV: Float,
        apShell: Boolean
    ) {
        this.penetration = penetration
        this.flightTime = flightTime
        this.impact = impact
        this.distanceList = distanceList
//        this.launchAngle = launchAngle;
        this.minDistV = minDistV

        var fiveOne = "0"
        var fiveTwo = ""
        var tenOne = "0"
        var tenTwo = ""
        var fifteenOne = "0"
        var fifteenTwo = ""
        var twentyOne = "0"
        var twentyTwo = ""

        val tempData: LinkedHashMap<String, Float> = if (apShell && !penetration.isNullOrEmpty()) penetration else flightTime

        for ((key) in tempData) {
            val tempFloat = key.toFloat()

            if (tempFloat < distFive) {
                if (fiveOne.toFloat() < tempFloat) {
                    fiveOne = key
                }
            } else if (tempFloat >= distFive && tempFloat < distTen) {
                if (fiveTwo.isEmpty()) {
                    fiveTwo = key
                }

                if (tenOne.toFloat() < tempFloat) {
                    tenOne = key
                }
            } else if (tempFloat >= distTen && tempFloat < distFifteen) {
                if (tenTwo.isEmpty()) {
                    tenTwo = key
                }

                if (fifteenOne.toFloat() < tempFloat) {
                    fifteenOne = key
                }
            } else if (tempFloat >= distFifteen && tempFloat < distTwenty) {
                if (fifteenTwo.isEmpty()) {
                    fifteenTwo = key
                }

                if (twentyOne.toFloat() < tempFloat) {
                    twentyOne = key
                }
            } else if (tempFloat >= distTwenty) {
                if (twentyTwo.isEmpty()) {
                    twentyTwo = key
                }
            }
        }

        if (fiveOne.isNotEmpty() && fiveTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtFive = setMiddleAtDistance(fiveOne.toFloat(), penetration[fiveOne], fiveTwo.toFloat(), penetration[fiveTwo], distFive)
                impactAtFive = setMiddleAtDistance(fiveOne.toFloat(), impact[fiveOne], fiveTwo.toFloat(), impact[fiveTwo], distFive)

//                vertMinusAtFive = calcVertDist(distFive, (getVertDist(fiveOne, distFive, true) + getVertDist(fiveTwo, distFive, true)) / 2f);
//                vertPlusAtFive = calcVertDist(distFive, (getVertDist(fiveOne, distFive, false) + getVertDist(fiveTwo, distFive, false)) / 2f);
            }
            flightTimeAtFive = setMiddleAtDistance(fiveOne.toFloat(), flightTime[fiveOne], fiveTwo.toFloat(), flightTime[fiveTwo], distFive)
        }

        if (tenOne.isNotEmpty() && tenTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtTen = setMiddleAtDistance(tenOne.toFloat(), penetration[tenOne], tenTwo.toFloat(), penetration[tenTwo], distTen)
                impactAtTen = setMiddleAtDistance(tenOne.toFloat(), impact[tenOne], tenTwo.toFloat(), impact[tenTwo], distTen)

//                vertMinusAtTen = calcVertDist(distTen, (getVertDist(tenOne, distTen, true) + getVertDist(tenTwo, distTen, true)) / 2f);
//                vertPlusAtTen = calcVertDist(distTen, (getVertDist(tenOne, distTen, false) + getVertDist(tenTwo, distTen, false)) / 2f);
            }
            flightTimeAtTen = setMiddleAtDistance(tenOne.toFloat(), flightTime[tenOne], tenTwo.toFloat(), flightTime[tenTwo], distTen)
        }

        if (fifteenOne.isNotEmpty() && fifteenTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtFifteen = setMiddleAtDistance(fifteenOne.toFloat(), penetration[fifteenOne], fifteenTwo.toFloat(), penetration[fifteenTwo], distFifteen)
                impactAtFifteen = setMiddleAtDistance(fifteenOne.toFloat(), impact[fifteenOne], fifteenTwo.toFloat(), impact[fifteenTwo], distFifteen)

//                vertMinusAtFifteen = calcVertDist(distFifteen, (getVertDist(fifteenOne, distFifteen, true) + getVertDist(fifteenTwo, distFifteen, true)) / 2f);
//                vertPlusAtFifteen = calcVertDist(distFifteen, (getVertDist(fifteenOne, distFifteen, false) + getVertDist(fifteenTwo, distFifteen, false)) / 2f);
            }
            flightTimeAtFifteen = setMiddleAtDistance(fifteenOne.toFloat(), flightTime[fifteenOne], fifteenTwo.toFloat(), flightTime[fifteenTwo], distFifteen)
        }

        if (twentyOne.isNotEmpty() && twentyTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtTwenty = setMiddleAtDistance(twentyOne.toFloat(), penetration[twentyOne], twentyTwo.toFloat(), penetration[twentyTwo], distTwenty)
                impactAtTwenty = setMiddleAtDistance(twentyOne.toFloat(), impact[twentyOne], twentyTwo.toFloat(), impact[twentyTwo], distTwenty)

//                vertMinusAtTwenty = calcVertDist(distTwenty, (getVertDist(twentyOne, distTwenty, true) + getVertDist(twentyTwo, distTwenty, true)) / 2f);
//                vertPlusAtTwenty = calcVertDist(distTwenty, (getVertDist(twentyOne, distTwenty, false) + getVertDist(twentyTwo, distTwenty, false)) / 2f);
            }
            flightTimeAtTwenty = setMiddleAtDistance(twentyOne.toFloat(), flightTime[twentyOne], twentyTwo.toFloat(), flightTime[twentyTwo], distTwenty)
        }
    }

    private fun calcVertDist(dist: Float, vert: Float): Float {
        return dist + (vert - dist)
    }

    private fun getVertDist(dist: String, mid: Float, low: Boolean): Float {
        val minDistVOffset = minDistV / 2f
        var radAtDist = atan(minDistVOffset / mid)

        if (low) {
            radAtDist = -radAtDist
        }

        val arcRad = launchAngle!![dist]
        var e1 = ""
        var e2 = ""

        if (arcRad != null) {
            for ((key, tempFloat) in launchAngle!!) {

                if (tempFloat < arcRad + radAtDist) {
                    e1 = key
                } else if (tempFloat >= arcRad + radAtDist) {
                    e2 = key
                    break
                }
            }
        }

        return if (e1.isEmpty() || e2.isEmpty()) 0f else (e1.toFloat() + e2.toFloat()) / 2f

    }

    private fun setMiddleAtDistance(x1: Float?, y1: Float?, x2: Float?, y2: Float?, mid: Float) : Float {
        if (x1 == null || y1 == null || x2 == null || y2 == null) {
            return 0f
        }

        val a = (y2 - y1) / (x2 - x1)
        val c = y1 - a * x1

        return a * mid + c
    }
}