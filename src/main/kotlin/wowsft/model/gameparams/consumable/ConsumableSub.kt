package wowsft.model.gameparams.consumable

import wowsft.config.WoWSFT

import java.util.LinkedHashMap

@WoWSFT
class ConsumableSub {
    var activationDelay: Float = 0f
    var affectedClasses: List<String>? = null
    var areaDamageMultiplier: Float = 0f
    var artilleryDistCoeff: Float = 0f
    var backwardEngineForsag: Float = 0f
    var backwardEngineForsagMaxSpeed: Float = 0f
    var boostCoeff: Float = 0f
    var bubbleDamageMultiplier: Float = 0f
    var climbAngle: Float = 0f
    var consumableType: String? = null
    var criticalChance: Float = 0f
    var distanceToKill: Float = 0f
    var distShip: Float = 0f
    var distTorpedo: Float = 0f
    var dogFightTime: Float = 0f
    var fightersName: String? = null
    var fightersNum: Float = 0f
    var flyAwayTime: Float = 0f
    var forwardEngineForsag: Float = 0f
    var forwardEngineForsagMaxSpeed: Float = 0f
    var group: String? = null
    var height: Float = 0f
    var lifeTime: Float = 0f
    var numConsumables: Float = 0f
    var planeType: String? = null
    var radius: Float = 0f
    var regenerationHPSpeed: Float = 0f
    var regenerationRate: Float = 0f
    var reloadTime: Float = 0f
    var spawnBackwardShift: Float = 0f
    var speedLimit: Float = 0f
    var startDelayTime: Float = 0f
    var timeDelayAttack: Float = 0f
    var timeFromHeaven: Float = 0f
    var timeToHeaven: Float = 0f
    var timeToTryingCatch: Float = 0f
    var timeWaitDelayAttack: Float = 0f
    var torpedoReloadTime: Float = 0f
    var workTime: Float = 0f

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
