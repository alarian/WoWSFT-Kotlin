package WoWSFT.model.gameparams.consumable

import WoWSFT.config.WoWSFT
import WoWSFT.utils.CommonUtils
import java.util.*

@WoWSFT
class ConsumableSub
{
    var activationDelay = 0.0
    var affectedClasses = listOf<String>()
    var areaDamageMultiplier = 0.0
    var artilleryDistCoeff = 0.0
    var backwardEngineForsag = 0.0
    var backwardEngineForsagMaxSpeed = 0.0
    var boostCoeff = 0.0
    var bubbleDamageMultiplier = 0.0
    var climbAngle = 0.0
    var consumableType = ""
    var criticalChance = 0.0
    var distanceToKill = 0.0
    var distShip = 0.0
    var distTorpedo = 0.0
    var dogFightTime = 0.0
    var fightersName = ""
    var fightersNum = 0.0
    var flyAwayTime = 0.0
    var forwardEngineForsag = 0.0
    var forwardEngineForsagMaxSpeed = 0.0
    var group = ""
    var height = 0.0
    var lifeTime = 0.0
    var numConsumables = 0.0
    var planeType = ""
    var radius = 0.0
    var regenerationHPSpeed = 0.0
    var regenerationRate = 0.0
    var reloadTime = 0.0
        get() = if (field > 0) CommonUtils.getDecimalRounded(field, 1) else field
    var spawnBackwardShift = 0.0
    var speedLimit = 0.0
    var startDelayTime = 0.0
    var timeDelayAttack = 0.0
    var timeFromHeaven = 0.0
    var timeToHeaven = 0.0
    var timeToTryingCatch = 0.0
    var timeWaitDelayAttack = 0.0
    var torpedoReloadTime = 0.0
    var workTime = 0.0
        get() = if (field > 0) CommonUtils.getDecimalRounded(field, 1) else field
    // Effects. Ignore.
    var livePointEffect = ""
    var radarEffect = ""
    var radarEffectForPlayer = ""
    var spawnEffect = ""
    var spawnPointEffect = ""
    var waveEffect = ""
    var descIDs = ""
    var iconIDs = ""
    var titleIDs = ""
    var bonus = LinkedHashMap<String, String>()
}