package wowsft.model.gameparams.ship.component.artillery

import wowsft.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

import java.util.LinkedHashMap

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
class Shell {
    val alphaDamage: Float = 0f
    val alphaPiercingCS: Float = 0f
    var alphaPiercingHE: Float = 0f
        set(alphaPiercingHE) {
            field = alphaPiercingHE
            this.alphaPiercingHEReal = alphaPiercingHE - 1f
        }
    var alphaPiercingHEReal: Float = 0f
    val ammoType: String? = null
    val bulletAirDrag: Float = 0f
    val bulletAlwaysRicochetAt: Float = 0f
    val bulletCap: Boolean = false
    val bulletCapNormalizeMaxAngle: Float = 0f
    val bulletDetonator: Float = 0f
    val bulletDetonatorSpread: Float = 0f
    val bulletDetonatorThreshold: Float = 0f
    val bulletDiametr: Float = 0f
    val bulletKrupp: Float = 0f
    val bulletMass: Float = 0f
    val bulletPenetrationSpread: Float = 0f
    val bulletRicochetAt: Float = 0f
    val bulletSpeed: Float = 0f
    val bulletUnderwaterDistFactor: Float = 0f
    val bulletUnderwaterPenetrationFactor: Float = 0f
    val bulletWaterDrag: Float = 0f
    val burnProb: Float = 0f
    val costCR: Int = 0
    val damage: Float = 0f
    val directDamage: Float = 0f
    val id: Long = 0
    val index: String? = null
    val name: String? = null
    val typeinfo: TypeInfo? = null
    val uwAbility: Boolean = false
    val uwCritical: Float = 0f
    val volume: Float = 0f
    val waterRefractionReflectDeltaAngleInterval: List<Float>? = null

    //    @JsonIgnore
    var penetration: LinkedHashMap<String, Float>? = null
    //    @JsonIgnore
    var flightTime: LinkedHashMap<String, Float>? = null
    //    @JsonIgnore
    var impact: LinkedHashMap<String, Float>? = null
    @JsonIgnore
    val launchAngle: LinkedHashMap<String, Float>? = null
    //    @JsonIgnore
    //    LinkedHashMap<String, Float> vertPlus;
    //    @JsonIgnore
    //    LinkedHashMap<String, Float> vertMinus;
    //    @JsonIgnore
    var distanceList: List<String>? = null
    var minDistV: Float = 0f
    var penetrationAtFive: Float = 0f
    var penetrationAtTen: Float = 0f
    var penetrationAtFifteen: Float = 0f
    var penetrationAtTwenty: Float = 0f
    val penetrationAtMax: Float = 0f
    var flightTimeAtFive: Float = 0f
    var flightTimeAtTen: Float = 0f
    var flightTimeAtFifteen: Float = 0f
    var flightTimeAtTwenty: Float = 0f
    val flightTimeAtMax: Float = 0f
    var impactAtFive: Float = 0f
    var impactAtTen: Float = 0f
    var impactAtFifteen: Float = 0f
    var impactAtTwenty: Float = 0f
    val impactAtMax: Float = 0f
    val vertPlusAtFive: Float = 0f
    val vertPlusAtTen: Float = 0f
    val vertPlusAtFifteen: Float = 0f
    val vertPlusAtTwenty: Float = 0f
    val vertPlusAtMax: Float = 0f
    val vertMinusAtFive: Float = 0f
    val vertMinusAtTen: Float = 0f
    val vertMinusAtFifteen: Float = 0f
    val vertMinusAtTwenty: Float = 0f
    val vertMinusAtMax: Float = 0f

    val penetrationIFHE: Int = 0

    @JsonIgnore
    val distFive = 5000f
    @JsonIgnore
    val distTen = 10000f
    @JsonIgnore
    val distFifteen = 15000f
    @JsonIgnore
    var distTwenty = 20000f

    fun setShell(
        flightTime: LinkedHashMap<String, Float>,
        penetration: LinkedHashMap<String, Float>?,
        impact: LinkedHashMap<String, Float>?,
        distanceList: List<String>?,
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

        val tempData: LinkedHashMap<String, Float> = if (apShell && !penetration.isNullOrEmpty()) {
            penetration
        } else {
            flightTime
        }

        for ((key) in tempData) {
            val tempFloat = java.lang.Float.parseFloat(key)

            if (tempFloat < distFive) {
                if (java.lang.Float.parseFloat(fiveOne) < tempFloat) {
                    fiveOne = key
                }
            } else if (tempFloat >= distFive && tempFloat < distTen) {
                if (fiveTwo.isEmpty()) {
                    fiveTwo = key
                }

                if (java.lang.Float.parseFloat(tenOne) < tempFloat) {
                    tenOne = key
                }
            } else if (tempFloat >= distTen && tempFloat < distFifteen) {
                if (tenTwo.isEmpty()) {
                    tenTwo = key
                }

                if (java.lang.Float.parseFloat(fifteenOne) < tempFloat) {
                    fifteenOne = key
                }
            } else if (tempFloat >= distFifteen && tempFloat < distTwenty) {
                if (fifteenTwo.isEmpty()) {
                    fifteenTwo = key
                }

                if (java.lang.Float.parseFloat(twentyOne) < tempFloat) {
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
                penetrationAtFive = setMiddleAtDistance(
                    java.lang.Float.parseFloat(fiveOne),
                    penetration[fiveOne], java.lang.Float.parseFloat(fiveTwo), penetration[fiveTwo], distFive
                )
                impactAtFive = setMiddleAtDistance(
                    java.lang.Float.parseFloat(fiveOne),
                    impact[fiveOne], java.lang.Float.parseFloat(fiveTwo), impact[fiveTwo], distFive
                )

                //                vertMinusAtFive = calcVertDist(distFive, (getVertDist(fiveOne, distFive, true) + getVertDist(fiveTwo, distFive, true)) / 2f);
                //                vertPlusAtFive = calcVertDist(distFive, (getVertDist(fiveOne, distFive, false) + getVertDist(fiveTwo, distFive, false)) / 2f);
            }
            flightTimeAtFive = setMiddleAtDistance(
                java.lang.Float.parseFloat(fiveOne),
                flightTime[fiveOne], java.lang.Float.parseFloat(fiveTwo), flightTime[fiveTwo], distFive
            )
        }

        if (tenOne.isNotEmpty() && tenTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtTen = setMiddleAtDistance(
                    java.lang.Float.parseFloat(tenOne),
                    penetration[tenOne], java.lang.Float.parseFloat(tenTwo), penetration[tenTwo], distTen
                )
                impactAtTen = setMiddleAtDistance(
                    java.lang.Float.parseFloat(tenOne),
                    impact[tenOne], java.lang.Float.parseFloat(tenTwo), impact[tenTwo], distTen
                )

                //                vertMinusAtTen = calcVertDist(distTen, (getVertDist(tenOne, distTen, true) + getVertDist(tenTwo, distTen, true)) / 2f);
                //                vertPlusAtTen = calcVertDist(distTen, (getVertDist(tenOne, distTen, false) + getVertDist(tenTwo, distTen, false)) / 2f);
            }
            flightTimeAtTen = setMiddleAtDistance(
                java.lang.Float.parseFloat(tenOne),
                flightTime[tenOne], java.lang.Float.parseFloat(tenTwo), flightTime[tenTwo], distTen
            )
        }

        if (fifteenOne.isNotEmpty() && fifteenTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtFifteen = setMiddleAtDistance(
                    java.lang.Float.parseFloat(fifteenOne),
                    penetration[fifteenOne],
                    java.lang.Float.parseFloat(fifteenTwo),
                    penetration[fifteenTwo],
                    distFifteen
                )
                impactAtFifteen = setMiddleAtDistance(
                    java.lang.Float.parseFloat(fifteenOne),
                    impact[fifteenOne], java.lang.Float.parseFloat(fifteenTwo), impact[fifteenTwo], distFifteen
                )

                //                vertMinusAtFifteen = calcVertDist(distFifteen, (getVertDist(fifteenOne, distFifteen, true) + getVertDist(fifteenTwo, distFifteen, true)) / 2f);
                //                vertPlusAtFifteen = calcVertDist(distFifteen, (getVertDist(fifteenOne, distFifteen, false) + getVertDist(fifteenTwo, distFifteen, false)) / 2f);
            }
            flightTimeAtFifteen = setMiddleAtDistance(
                java.lang.Float.parseFloat(fifteenOne),
                flightTime[fifteenOne], java.lang.Float.parseFloat(fifteenTwo), flightTime[fifteenTwo], distFifteen
            )
        }

        if (twentyOne.isNotEmpty() && twentyTwo.isNotEmpty()) {
            if (apShell && !penetration.isNullOrEmpty() && !impact.isNullOrEmpty()) {
                penetrationAtTwenty = setMiddleAtDistance(
                    java.lang.Float.parseFloat(twentyOne),
                    penetration[twentyOne], java.lang.Float.parseFloat(twentyTwo), penetration[twentyTwo], distTwenty
                )
                impactAtTwenty = setMiddleAtDistance(
                    java.lang.Float.parseFloat(twentyOne),
                    impact[twentyOne], java.lang.Float.parseFloat(twentyTwo), impact[twentyTwo], distTwenty
                )

                //                vertMinusAtTwenty = calcVertDist(distTwenty, (getVertDist(twentyOne, distTwenty, true) + getVertDist(twentyTwo, distTwenty, true)) / 2f);
                //                vertPlusAtTwenty = calcVertDist(distTwenty, (getVertDist(twentyOne, distTwenty, false) + getVertDist(twentyTwo, distTwenty, false)) / 2f);
            }
            flightTimeAtTwenty = setMiddleAtDistance(
                java.lang.Float.parseFloat(twentyOne),
                flightTime[twentyOne], java.lang.Float.parseFloat(twentyTwo), flightTime[twentyTwo], distTwenty
            )
        }
    }

    private fun calcVertDist(dist: Float, vert: Float): Float {
        return dist + (vert - dist)
    }

    private fun getVertDist(dist: String, mid: Float, low: Boolean): Float {
        val minDistVOffset = minDistV / 2f
        var radAtDist = Math.atan((minDistVOffset / mid).toDouble()).toFloat()

        if (low) {
            radAtDist = -radAtDist
        }

        val arcRad = this.launchAngle!![dist]
        var e1 = ""
        var e2 = ""

        if (arcRad != null) {
            for ((key, tempFloat) in launchAngle) {

                if (tempFloat < arcRad + radAtDist) {
                    e1 = key
                } else if (tempFloat >= arcRad + radAtDist) {
                    e2 = key
                    break
                }
            }
        }

        return if (e1.isEmpty() || e2.isEmpty()) {
            0f
        } else (java.lang.Float.parseFloat(e1) + java.lang.Float.parseFloat(e2)) / 2f

    }

    private fun setMiddleAtDistance(x1: Float?, y1: Float?, x2: Float?, y2: Float?, mid: Float?): Float {
        if (x1 == null || x2 == null || y1 == null || y2 == null) {
            return 0f
        }

        val a = (y2 - y1) / (x2 - x1)
        val c = y1 - a * x1

        return a * mid!! + c
    }
}