package wowsft.model.gameparams.consumable

import wowsft.config.WoWSFT

import java.util.LinkedHashMap

@WoWSFT
class ConsumableSub {
    var activationDelay = 0f
    var affectedClasses = ArrayList<String>()
    var areaDamageMultiplier = 0f
    var artilleryDistCoeff = 0f
    var backwardEngineForsag = 0f
    var backwardEngineForsagMaxSpeed = 0f
    var boostCoeff = 0f
    var bubbleDamageMultiplier = 0f
    var climbAngle = 0f
    var consumableType = ""
    var criticalChance = 0f
    var distanceToKill = 0f
    var distShip = 0f
    var distTorpedo = 0f
    var dogFightTime = 0f
    var fightersName = ""
    var fightersNum = 0f
    var flyAwayTime = 0f
    var forwardEngineForsag = 0f
    var forwardEngineForsagMaxSpeed = 0f
    var group = ""
    var height = 0f
    var lifeTime = 0f
    var numConsumables = 0f
    var planeType = ""
    var radius = 0f
    var regenerationHPSpeed = 0f
    var regenerationRate = 0f
    var reloadTime = 0f
    var spawnBackwardShift = 0f
    var speedLimit = 0f
    var startDelayTime = 0f
    var timeDelayAttack = 0f
    var timeFromHeaven = 0f
    var timeToHeaven = 0f
    var timeToTryingCatch = 0f
    var timeWaitDelayAttack = 0f
    var torpedoReloadTime = 0f
    var workTime = 0f

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
