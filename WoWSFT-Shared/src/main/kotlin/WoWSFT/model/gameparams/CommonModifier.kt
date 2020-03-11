package WoWSFT.model.gameparams

import WoWSFT.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
open class CommonModifier
{
    @JsonProperty("AAExtraBubbles")
    var aaExtraBubbles = 0.0
    @JsonProperty("AANearDamage")
    var aaNearDamage = 1.0
    @JsonProperty("AAOuterDamage")
    var aaOuterDamage = 1.0
    @JsonProperty("ADMaxHP")
    var adMaxHP = 1.0
    @JsonProperty("GMCritProb")
    var gmCritProb = 1.0
    @JsonProperty("GMIdealRadius")
    var gmIdealRadius = 1.0
    @JsonProperty("GMMaxDist")
    var gmMaxDist = 1.0
    @JsonProperty("GMMaxHP")
    var gmMaxHP = 1.0
    @JsonProperty("GMRepairTime")
    var gmRepairTime = 1.0
    @JsonProperty("GMRotationSpeed")
    var gmRotationSpeed = 1.0
    @JsonProperty("GMShotDelay")
    var gmShotDelay = 1.0
    @JsonProperty("GMSigmaCount")
    var gmSigmaCount = 1.0
    @JsonProperty("GSIdealRadius")
    var gsIdealRadius = 1.0
    @JsonProperty("GSMaxDist")
    var gsMaxDist = 1.0
    @JsonProperty("GSMaxHP")
    var gsMaxHP = 1.0
    @JsonProperty("GSShotDelay")
    var gsShotDelay = 1.0
    @JsonProperty("GSSigmaCount")
    var gsSigmaCount = 1.0
    @JsonProperty("GTCritProb")
    var gtCritProb = 1.0
    @JsonProperty("GTMaxHP")
    var gtMaxHP = 1.0
    @JsonProperty("GTRepairTime")
    var gtRepairTime = 1.0
    @JsonProperty("GTRotationSpeed")
    var gtRotationSpeed = 1.0
    @JsonProperty("GTShotDelay")
    var gtShotDelay = 1.0
    @JsonProperty("PMCritProb")
    var pmCritProb = 1.0
    @JsonProperty("PMDetonationProb")
    var pmDetonationProb = 1.0
    @JsonProperty("PMRepairTime")
    var pmRepairTime = 1.0
    @JsonProperty("SGCritProb")
    var sgCritProb = 1.0
    @JsonProperty("SGRepairTime")
    var sgRepairTime = 1.0
    @JsonProperty("SGRudderTime")
    var sgRudderTime = 1.0
    var airDefenseDispWorkTime = 1.0
    var airplanesDiveBombersHealth = 1.0
    var airplanesEmptyReturnSpeed = 1.0
    var airplanesExtraHangarSize = 0.0
    var airplanesFighterAimingTime = 0.0
    var airplanesFightersHealth = 1.0
    var airplanesForsageDuration = 1.0
    var airplanesHealth = 1.0
    var airplanesSpawnTime = 1.0
    var airplanesSpeed = 1.0
    var airplanesTorpedoAimingTime = 0.0
    var airplanesTorpedoBombersHealth = 1.0
    var burnProb = 1.0
    var burnTime = 1.0
    var crashCrewWorkTime = 1.0
    var engineBackwardForsageMaxSpeed = 1.0
    var engineBackwardForsagePower = 1.0
    var engineBackwardUpTime = 1.0
    var engineCritProb = 1.0
    var engineForwardForsageMaxSpeed = 1.0
    var engineForwardForsagePower = 1.0
    var engineForwardUpTime = 1.0
    var engineRepairTime = 1.0
    var floodProb = 1.0
    var floodTime = 1.0
    var invulnerableWorkTime = 1.0
    var rlsSearchWorkTime = 1.0
    var scoutWorkTime = 1.0
    var shootShift = 1.0
    var smokeGeneratorLifeTime = 1.0
    var smokeGeneratorWorkTime = 1.0
    var sonarSearchWorkTime = 1.0
    var speedBoosterWorkTime = 1.0
    var squadronVisibilityDistCoeff = 1.0
    var trigger1SearchWorkTime = 1.0
    var trigger2SearchWorkTime = 1.0
    var trigger3SearchWorkTime = 1.0
    var trigger4SearchWorkTime = 1.0
    var trigger5SearchWorkTime = 1.0
    var trigger6SearchWorkTime = 1.0
    var visibilityDistCoeff = 1.0
    var visionDistCoeff = 1.0
    var visionTorpedoCoeff = 1.0
    var visionXRayShipCoeff = 1.0
    //
    var modifier = ""
    var diveBomber = 1.0
    var fighter = 1.0
    var torpedoBomber = 1.0
    var radiusCoefficient = 1.0
    var smallGunReloadCoefficient = 1.0
    var smallGunRangeCoefficient = 1.0
    var consumablePlaneSpeedCoefficient = 1.0
    var reloadCoefficient = 1.0
    var alertMinDistance = 0.0
    var critTimeCoefficient = 1.0
    var atbaIdealRadiusHi = 1.0
    var atbaIdealRadiusLo = 1.0
    var fightersAmmunitionCoefficient = 1.0
    var fightersEfficiencyCoefficient = 1.0
    var probabilityBonus = 0.0
    var probabilityCoefficient = 1.0
    var switchAmmoReloadCoef = 1.0
    var chanceToSetOnFireBonus = 0.0
    var chanceToSetOnFireBonusBig = 0.0
        set(value) {
            field = if (value != 0.0) -value else 0.0
        }
    var chanceToSetOnFireBonusSmall = 0.0
        set(value) {
            field = if (value != 0.0) -value else 0.0
        }
    var thresholdPenetrationCoefficient = 1.0
    var thresholdPenetrationCoefficientBig = 1.0
    var thresholdPenetrationCoefficientSmall = 1.0
    var hpStep = 1.0
    var timeStep = 0.0
    var critRudderTimeCoefficient = 1.0
    var bigGunBonus = 0.0
    var smallGunBonus = 0.0
    var critProbCoefficient = 1.0
    var fightersSpeedCoefficient = 1.0
    var fightersVisibCoefficient = 1.0
    var fightersVitalCoefficient = 1.0
    var burnTimePenalty = 0.0
    var diveBombersPrepareCoefficient = 1.0
    var fightersPrepareCoefficient = 1.0
    var torpedoBombersPrepareCoefficient = 1.0
    var vitalityCoefficient = 1.0
    var fightersPassiveEfficiencyCoefficient = 1.0
    var additionalConsumables = 0.0
    var healthPerLevel = 0.0
    var torpedoRangeCoefficient = 1.0
    var torpedoSpeedBonus = 0.0
    var rangeCoefficient = 1.0
    var bomberCoefficient = 1.0
    var launcherCoefficient = 1.0
    var aircraftCarrierCoefficient = 1.0
    var battleshipCoefficient = 1.0
    var cruiserCoefficient = 1.0
    var destroyerCoefficient = 1.0
    var submarineCoefficient = 1.0
    var squadronCoefficient = 1.0
    var nearAuraDamageCoefficient = 1.0
    var advancedOuterAuraDamageCoefficient = 1.0
    var extraFighterCount = 0.0
    var fighterLifeTimeCoefficient = 1.0
    var hangarSizeBonus = 0.0
    var planeSpawnTimeCoefficient = 1.0
    var prioritySectorStrengthCoefficient = 1.0
    var sectorSwitchDelayCoefficient = 1.0
    var bombProbabilityBonus = 0.0
    var rocketProbabilityBonus = 0.0
    var flightSpeedCoefficient = 1.0
    var forsageDurationCoefficient = 1.0
    var squadronHealthStep = 1.0
    var squadronSpeedStep = 1.0
    var nearAuraDamageTakenCoefficient = 1.0
    var planeHealthPerLevel = 0.0
    var planeTorpedoRangeCoefficient = 1.0
    var planeTorpedoSpeedBonus = 0.0
    var planeRangeCoefficient = 1.0
    var prioSectorCooldownCoefficient = 1.0
    var prioSectorPhaseDurationCoefficient = 1.0
    var prioSectorStartPhaseStrengthCoefficient = 1.0
    var prioSectorStrengthCoefficient = 1.0

    var abilReloadTimeFactor = 1.0
    var burnChanceFactorBig = 1.0
    var burnChanceFactorSmall = 1.0
    var collisionDamageApply = 1.0
    var collisionDamageNerf = 1.0
    var damageCoeffGM = 1.0
    var damageCoeffTorpedo = 1.0
    var floodChanceFactor = 1.0
    var floodChanceFactorPlane = 1.0
    var regenerationHPSpeed = 1.0
    var regenerationPlaneRate = 1.0
    var speedCoef= 1.0

    var torpedoSpeedMultiplier = 1.0
    var airplanesTorpedoSpeedMultiplier = 1.0
    var shipConsumablesWorkTimeMultiplier = 1.0
    var squadronConsumablesWorkTimeMultiplier = 1.0
    @JsonProperty("InnerAADefenseDispExtraBubbles")
    var innerAADefenseDispExtraBubbles = 0.0
    var airDefenseDispReloadMultiplier = 1.0
    var prioritySectorCooldownMultiplier = 1.0
    var guaranteedTorpedoVisibilityDistance = 0
}