package WoWSFT.model.gameparams.ship.component.artillery

import WoWSFT.model.gameparams.TypeInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*
import kotlin.math.floor

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
class Shell {
    var alphaDamage = 0.0
    var alphaPiercingCS = 0.0
    var alphaPiercingHE = 0.0
    val alphaPiercingHEReal get() = floor(alphaPiercingHE).toInt()
    var ammoType = ""
    var bulletAirDrag = 0.0
    var bulletAlwaysRicochetAt = 0.0
    var bulletCap = false
    var bulletCapNormalizeMaxAngle = 0.0
    var bulletDetonator = 0.0
    var bulletDetonatorSpread = 0.0
    var bulletDetonatorThreshold = 0.0
    var bulletDiametr = 0.0
    var bulletKrupp = 0.0
    var bulletMass = 0.0
    var bulletPenetrationSpread = 0.0
    var bulletRicochetAt = 0.0
    var bulletSpeed = 0.0
    var bulletUnderwaterDistFactor = 0.0
    var bulletUnderwaterPenetrationFactor = 0.0
    var bulletWaterDrag = 0.0
    var burnProb = 0.0
        set(value) {
            field = value
            burnProbReal = if (burnProbReal == 0.0) value else burnProbReal
        }
    var burnProbReal = 0.0
    var costCR = 0
    var damage = 0.0
    var directDamage = 0.0
    var id = 0L
    var index = ""
    var name = ""
    var typeinfo = TypeInfo()
    var uwAbility = false
    var uwCritical = 0.0
    var volume = 0.0
    var waterRefractionReflectDeltaAngleIntervar = listOf<Double>()
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
//        launchAngle: LinkedHashMap<String, Double>,
        minDistV: Double
//        apShell: Boolean
    ) {
        this.penetration = penetration
        this.flightTime = flightTime
        this.impact = impact
        this.distanceList = distanceList
        this.minDistV = minDistV
    }
}