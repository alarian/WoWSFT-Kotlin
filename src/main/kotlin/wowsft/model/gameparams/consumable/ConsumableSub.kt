package wowsft.model.gameparams.consumable

import wowsft.config.WoWSFT

import java.util.LinkedHashMap

@WoWSFT
class ConsumableSub {
    var activationDelay = 0.toDouble()
    var affectedClasses = ArrayList<String>()
    var areaDamageMultiplier = 0.toDouble()
    var artilleryDistCoeff = 0.toDouble()
    var backwardEngineForsag = 0.toDouble()
    var backwardEngineForsagMaxSpeed = 0.toDouble()
    var boostCoeff = 0.toDouble()
    var bubbleDamageMultiplier = 0.toDouble()
    var climbAngle = 0.toDouble()
    var consumableType: String = ""
    var criticalChance = 0.toDouble()
    var distanceToKill = 0.toDouble()
    var distShip = 0.toDouble()
    var distTorpedo = 0.toDouble()
    var dogFightTime = 0.toDouble()
    var fightersName: String = ""
    var fightersNum = 0.toDouble()
    var flyAwayTime = 0.toDouble()
    var forwardEngineForsag = 0.toDouble()
    var forwardEngineForsagMaxSpeed = 0.toDouble()
    var group: String = ""
    var height = 0.toDouble()
    var lifeTime = 0.toDouble()
    var numConsumables = 0.toDouble()
    var planeType: String = ""
    var radius = 0.toDouble()
    var regenerationHPSpeed = 0.toDouble()
    var regenerationRate = 0.toDouble()
    var reloadTime = 0.toDouble()
    var spawnBackwardShift = 0.toDouble()
    var speedLimit = 0.toDouble()
    var startDelayTime = 0.toDouble()
    var timeDelayAttack = 0.toDouble()
    var timeFromHeaven = 0.toDouble()
    var timeToHeaven = 0.toDouble()
    var timeToTryingCatch = 0.toDouble()
    var timeWaitDelayAttack = 0.toDouble()
    var torpedoReloadTime = 0.toDouble()
    var workTime = 0.toDouble()

    // Effects. Ignore.
    var livePointEffect: String = ""
    var radarEffect: String = ""
    var radarEffectForPlayer: String = ""
    var spawnEffect: String = ""
    var spawnPointEffect: String = ""
    var waveEffect: String = ""

    var descIDs: String = ""
    var iconIDs: String = ""
    var titleIDs: String = ""

    var bonus = LinkedHashMap<String, String>()
}
