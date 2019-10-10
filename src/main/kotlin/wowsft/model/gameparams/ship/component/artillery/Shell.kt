package wowsft.model.gameparams.ship.component.artillery

import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

import java.util.LinkedHashMap
import kotlin.math.atan

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
class Shell
{
    var alphaDamage: Double = 0.0
    var alphaPiercingCS: Double = 0.0
    var alphaPiercingHE: Double = 0.0
        set(alphaPiercingHE) {
            field = alphaPiercingHE
            this.alphaPiercingHEReal = alphaPiercingHE - 1.0
        }
    var alphaPiercingHEReal: Double = 0.0
    var ammoType: String? = null
    var bulletAirDrag: Double = 0.0
    var bulletAlwaysRicochetAt: Double = 0.0
    var bulletCap = false
    var bulletCapNormalizeMaxAngle: Double = 0.0
    var bulletDetonator: Double = 0.0
    var bulletDetonatorSpread: Double = 0.0
    var bulletDetonatorThreshold: Double = 0.0
    var bulletDiametr: Double = 0.0
    var bulletKrupp: Double = 0.0
    var bulletMass: Double = 0.0
    var bulletPenetrationSpread: Double = 0.0
    var bulletRicochetAt: Double = 0.0
    var bulletSpeed: Double = 0.0
    var bulletUnderwaterDistFactor: Double = 0.0
    var bulletUnderwaterPenetrationFactor: Double = 0.0
    var bulletWaterDrag: Double = 0.0
    var burnProb: Double = 0.0
    var costCR = 0
    var damage: Double = 0.0
    var directDamage: Double = 0.0
    var id: Long = 0
    var index: String? = null
    var name: String? = null
    var typeinfo : TypeInfo? = null
    var uwAbility = false
    var uwCritical: Double = 0.0
    var volume: Double = 0.0
    var waterRefractionReflectDeltaAngleInterval = ArrayList<Double>()

//    @JsonIgnore
    var penetration: LinkedHashMap<String, Double>? = null
//    @JsonIgnore
    var flightTime = LinkedHashMap<String, Double>()
//    @JsonIgnore
    var impact: LinkedHashMap<String, Double>? = null
    @JsonIgnore
    var launchAngle: LinkedHashMap<String, Double>? = null
//    @JsonIgnore
//    LinkedHashMap<String, Double> vertPlus;
//    @JsonIgnore
//    LinkedHashMap<String, Double> vertMinus;
//    @JsonIgnore
    var distanceList: ArrayList<String>? = null
    var minDistV: Double = 0.0
    var penetrationAtFive: Double = 0.0
    var penetrationAtTen: Double = 0.0
    var penetrationAtFifteen: Double = 0.0
    var penetrationAtTwenty: Double = 0.0
    var penetrationAtMax: Double = 0.0
    var flightTimeAtFive: Double = 0.0
    var flightTimeAtTen: Double = 0.0
    var flightTimeAtFifteen: Double = 0.0
    var flightTimeAtTwenty: Double = 0.0
    var flightTimeAtMax: Double = 0.0
    var impactAtFive: Double = 0.0
    var impactAtTen: Double = 0.0
    var impactAtFifteen: Double = 0.0
    var impactAtTwenty: Double = 0.0
    var impactAtMax: Double = 0.0
    var vertPlusAtFive: Double = 0.0
    var vertPlusAtTen: Double = 0.0
    var vertPlusAtFifteen: Double = 0.0
    var vertPlusAtTwenty: Double = 0.0
    var vertPlusAtMax: Double = 0.0
    var vertMinusAtFive: Double = 0.0
    var vertMinusAtTen: Double = 0.0
    var vertMinusAtFifteen: Double = 0.0
    var vertMinusAtTwenty: Double = 0.0
    var vertMinusAtMax: Double = 0.0

    var penetrationIFHE = 0

    @JsonIgnore
    val distFive = 5000.0
    @JsonIgnore
    val distTen = 10000.0
    @JsonIgnore
    val distFifteen = 15000.0
    @JsonIgnore
    val distTwenty = 20000.0

    fun setShell(
        flightTime: LinkedHashMap<String, Double>,
        penetration: LinkedHashMap<String, Double>?,
        impact: LinkedHashMap<String, Double>?,
        distanceList: ArrayList<String>?,
        launchAngle: LinkedHashMap<String, Double>?,
        minDistV: Double,
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

        val tempData: LinkedHashMap<String, Double> = if (apShell && !penetration.isNullOrEmpty()) penetration else flightTime

        for ((key) in tempData) {
            val tempDouble = key.toDouble()

            if (tempDouble < distFive) {
                if (fiveOne.toDouble() < tempDouble) {
                    fiveOne = key
                }
            } else if (tempDouble >= distFive && tempDouble < distTen) {
                if (fiveTwo.isEmpty()) {
                    fiveTwo = key
                }

                if (tenOne.toDouble() < tempDouble) {
                    tenOne = key
                }
            } else if (tempDouble >= distTen && tempDouble < distFifteen) {
                if (tenTwo.isEmpty()) {
                    tenTwo = key
                }

                if (fifteenOne.toDouble() < tempDouble) {
                    fifteenOne = key
                }
            } else if (tempDouble >= distFifteen && tempDouble < distTwenty) {
                if (fifteenTwo.isEmpty()) {
                    fifteenTwo = key
                }

                if (twentyOne.toDouble() < tempDouble) {
                    twentyOne = key
                }
            } else if (tempDouble >= distTwenty) {
                if (twentyTwo.isEmpty()) {
                    twentyTwo = key
                }
            }
        }

        if (fiveOne.isNotEmpty() && fiveTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtFive = setMiddleAtDistance(fiveOne.toDouble(), penetration[fiveOne], fiveTwo.toDouble(), penetration[fiveTwo], distFive)
                impactAtFive = setMiddleAtDistance(fiveOne.toDouble(), impact[fiveOne], fiveTwo.toDouble(), impact[fiveTwo], distFive)

//                vertMinusAtFive = calcVertDist(distFive, (getVertDist(fiveOne, distFive, true) + getVertDist(fiveTwo, distFive, true)) / 2f);
//                vertPlusAtFive = calcVertDist(distFive, (getVertDist(fiveOne, distFive, false) + getVertDist(fiveTwo, distFive, false)) / 2f);
            }
            flightTimeAtFive = setMiddleAtDistance(fiveOne.toDouble(), flightTime[fiveOne], fiveTwo.toDouble(), flightTime[fiveTwo], distFive)
        }

        if (tenOne.isNotEmpty() && tenTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtTen = setMiddleAtDistance(tenOne.toDouble(), penetration[tenOne], tenTwo.toDouble(), penetration[tenTwo], distTen)
                impactAtTen = setMiddleAtDistance(tenOne.toDouble(), impact[tenOne], tenTwo.toDouble(), impact[tenTwo], distTen)

//                vertMinusAtTen = calcVertDist(distTen, (getVertDist(tenOne, distTen, true) + getVertDist(tenTwo, distTen, true)) / 2f);
//                vertPlusAtTen = calcVertDist(distTen, (getVertDist(tenOne, distTen, false) + getVertDist(tenTwo, distTen, false)) / 2f);
            }
            flightTimeAtTen = setMiddleAtDistance(tenOne.toDouble(), flightTime[tenOne], tenTwo.toDouble(), flightTime[tenTwo], distTen)
        }

        if (fifteenOne.isNotEmpty() && fifteenTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtFifteen = setMiddleAtDistance(fifteenOne.toDouble(), penetration[fifteenOne], fifteenTwo.toDouble(), penetration[fifteenTwo], distFifteen)
                impactAtFifteen = setMiddleAtDistance(fifteenOne.toDouble(), impact[fifteenOne], fifteenTwo.toDouble(), impact[fifteenTwo], distFifteen)

//                vertMinusAtFifteen = calcVertDist(distFifteen, (getVertDist(fifteenOne, distFifteen, true) + getVertDist(fifteenTwo, distFifteen, true)) / 2f);
//                vertPlusAtFifteen = calcVertDist(distFifteen, (getVertDist(fifteenOne, distFifteen, false) + getVertDist(fifteenTwo, distFifteen, false)) / 2f);
            }
            flightTimeAtFifteen = setMiddleAtDistance(fifteenOne.toDouble(), flightTime[fifteenOne], fifteenTwo.toDouble(), flightTime[fifteenTwo], distFifteen)
        }

        if (twentyOne.isNotEmpty() && twentyTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtTwenty = setMiddleAtDistance(twentyOne.toDouble(), penetration[twentyOne], twentyTwo.toDouble(), penetration[twentyTwo], distTwenty)
                impactAtTwenty = setMiddleAtDistance(twentyOne.toDouble(), impact[twentyOne], twentyTwo.toDouble(), impact[twentyTwo], distTwenty)

//                vertMinusAtTwenty = calcVertDist(distTwenty, (getVertDist(twentyOne, distTwenty, true) + getVertDist(twentyTwo, distTwenty, true)) / 2f);
//                vertPlusAtTwenty = calcVertDist(distTwenty, (getVertDist(twentyOne, distTwenty, false) + getVertDist(twentyTwo, distTwenty, false)) / 2f);
            }
            flightTimeAtTwenty = setMiddleAtDistance(twentyOne.toDouble(), flightTime[twentyOne], twentyTwo.toDouble(), flightTime[twentyTwo], distTwenty)
        }
    }

    private fun calcVertDist(dist: Double, vert: Double): Double
    {
        return dist + (vert - dist)
    }

    private fun getVertDist(dist: String, mid: Double, low: Boolean): Double
    {
        val minDistVOffset = minDistV / 2.toDouble()
        var radAtDist = atan(minDistVOffset / mid)

        if (low) {
            radAtDist = -radAtDist
        }

        val arcRad = launchAngle!![dist]
        var e1 = ""
        var e2 = ""

        if (arcRad != null) {
            for ((key, tempDouble) in launchAngle!!) {
                if (tempDouble < arcRad + radAtDist) {
                    e1 = key
                } else if (tempDouble >= arcRad + radAtDist) {
                    e2 = key
                    break
                }
            }
        }

        return if (e1.isEmpty() || e2.isEmpty()) 0.0 else (e1.toDouble() + e2.toDouble()) / 2.0
    }

    private fun setMiddleAtDistance(x1: Double?, y1: Double?, x2: Double?, y2: Double?, mid: Double) : Double {
        if (x1 == null || y1 == null || x2 == null || y2 == null) {
            return 0.0
        }

        val a = (y2 - y1) / (x2 - x1)
        val c = y1 - a * x1

        return a * mid + c
    }
}