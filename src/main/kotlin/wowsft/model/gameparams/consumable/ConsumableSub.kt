package wowsft.model.gameparams.consumable

import wowsft.config.WoWSFT

import java.util.LinkedHashMap

@WoWSFT
class ConsumableSub
{
    var activationDelay = 0.0
    var affectedClasses = ArrayList<String>()
    var areaDamageMultiplier = 0.0
    var artilleryDistCoeff = 0.0
    var backwardEngineForsag = 0.0
    var backwardEngineForsagMaxSpeed = 0.0
    var boostCoeff = 0.0
    var bubbleDamageMultiplier = 0.0
    var climbAngle = 0.0
    var consumableType: String? = null
    var criticalChance = 0.0
    var distanceToKill = 0.0
    var distShip = 0.0
    var distTorpedo = 0.0
    var dogFightTime = 0.0
    var fightersName: String? = null
    var fightersNum = 0.0
    var flyAwayTime = 0.0
    var forwardEngineForsag = 0.0
    var forwardEngineForsagMaxSpeed = 0.0
    var group: String? = null
    var height = 0.0
    var lifeTime = 0.0
    var numConsumables = 0.0
    var planeType: String? = null
    var radius = 0.0
    var regenerationHPSpeed = 0.0
    var regenerationRate = 0.0
    var reloadTime = 0.0
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

    // Effects. Ignore.
    var livePointEffect: String? = null
    var radarEffect: String? = null
    var radarEffectForPlayer: String? = null
    var spawnEffect: String? = null
    var spawnPointEffect: String? = null
    var waveEffect: String? = null

    var descIDs: String? = null
    var iconIDs: String? = null
    var titleIDs: String? = null

    var bonus = LinkedHashMap<String, String>()
}
