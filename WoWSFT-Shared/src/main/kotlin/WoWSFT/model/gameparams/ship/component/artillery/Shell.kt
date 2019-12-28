package WoWSFT.model.gameparams.ship.component.artillery

import WoWSFT.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*
import kotlin.math.ceil

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
class Shell
{
    val alphaDamage = 0.0
    val alphaPiercingCS = 0.0
    var alphaPiercingHE = 0.0
        set(value) {
            field = value
            alphaPiercingHEReal = ceil(value) - 1.0
        }
    var alphaPiercingHEReal = 0.0
    val ammoType = ""
    val bulletAirDrag = 0.0
    val bulletAlwaysRicochetAt = 0.0
    val bulletCap = false
    val bulletCapNormalizeMaxAngle = 0.0
    val bulletDetonator = 0.0
    val bulletDetonatorSpread = 0.0
    val bulletDetonatorThreshold = 0.0
    val bulletDiametr = 0.0
    val bulletKrupp = 0.0
    val bulletMass = 0.0
    val bulletPenetrationSpread = 0.0
    val bulletRicochetAt = 0.0
    val bulletSpeed = 0.0
    val bulletUnderwaterDistFactor = 0.0
    val bulletUnderwaterPenetrationFactor = 0.0
    val bulletWaterDrag = 0.0
    val burnProb = 0.0
    val costCR = 0
    val damage = 0.0
    val directDamage = 0.0
    val id = 0L
    val index = ""
    val name = ""
    val typeinfo = TypeInfo()
    val uwAbility = false
    val uwCritical = 0.0
    val volume = 0.0
    val waterRefractionReflectDeltaAngleInterval = listOf<Double>()
    var penetration = LinkedHashMap<String, Double>()
    var flightTime = LinkedHashMap<String, Double>()
    var impact = LinkedHashMap<String, Double>()
    @JsonIgnore
    var launchAngle = LinkedHashMap<String, Double>()
    var distanceList = mutableListOf<String>()
    var minDistV = 0.0
    var penetrationAtFive = 0.0
    var penetrationAtTen = 0.0
    var penetrationAtFifteen = 0.0
    var penetrationAtTwenty = 0.0
    var penetrationAtMax = 0.0
    var flightTimeAtFive = 0.0
    var flightTimeAtTen = 0.0
    var flightTimeAtFifteen = 0.0
    var flightTimeAtTwenty = 0.0
    var flightTimeAtMax = 0.0
    var impactAtFive = 0.0
    var impactAtTen = 0.0
    var impactAtFifteen = 0.0
    var impactAtTwenty = 0.0
    var impactAtMax = 0.0
    var vertPlusAtFive = 0.0
    var vertPlusAtTen = 0.0
    var vertPlusAtFifteen = 0.0
    var vertPlusAtTwenty = 0.0
    var vertPlusAtMax = 0.0
    var vertMinusAtFive = 0.0
    var vertMinusAtTen = 0.0
    var vertMinusAtFifteen = 0.0
    var vertMinusAtTwenty = 0.0
    var vertMinusAtMax = 0.0
    var penetrationIFHE = 0
    @JsonIgnore
    var distFive = 5000.0
    @JsonIgnore
    var distTen = 10000.0
    @JsonIgnore
    var distFifteen = 15000.0
    @JsonIgnore
    var distTwenty = 20000.0

    fun setShell(
        flightTime: LinkedHashMap<String, Double>,
        penetration: LinkedHashMap<String, Double>,
        impact: LinkedHashMap<String, Double>,
        distanceList: MutableList<String>,
        launchAngle: LinkedHashMap<String, Double>,
        minDistV: Double,
        apShell: Boolean
    ) {
        this.penetration = penetration
        this.flightTime = flightTime
        this.impact = impact
        this.distanceList = distanceList
        this.minDistV = minDistV
    }
}