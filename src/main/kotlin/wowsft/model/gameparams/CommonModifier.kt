package wowsft.model.gameparams

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
open class CommonModifier {
    @JsonProperty("AAExtraBubbles")
    var aaextraBubbles: Float = 0f
    @JsonProperty("AANearDamage")
    var aanearDamage = 1f
    @JsonProperty("AAOuterDamage")
    var aaouterDamage = 1f
    @JsonProperty("ADMaxHP")
    var admaxHP = 1f
    @JsonProperty("GMCritProb")
    var gmcritProb = 1f
    @JsonProperty("GMIdealRadius")
    var gmidealRadius = 1f
    @JsonProperty("GMMaxDist")
    var gmmaxDist = 1f
    @JsonProperty("GMMaxHP")
    var gmmaxHP = 1f
    @JsonProperty("GMRepairTime")
    var gmrepairTime = 1f
    @JsonProperty("GMRotationSpeed")
    var gmrotationSpeed = 1f
    @JsonProperty("GMShotDelay")
    var gmshotDelay = 1f
    @JsonProperty("GMSigmaCount")
    var gmsigmaCount = 1f
    @JsonProperty("GSIdealRadius")
    var gsidealRadius = 1f
    @JsonProperty("GSMaxDist")
    var gsmaxDist = 1f
    @JsonProperty("GSMaxHP")
    var gsmaxHP = 1f
    @JsonProperty("GSShotDelay")
    var gsshotDelay = 1f
    @JsonProperty("GSSigmaCount")
    var gssigmaCount = 1f
    @JsonProperty("GTCritProb")
    var gtcritProb = 1f
    @JsonProperty("GTMaxHP")
    var gtmaxHP = 1f
    @JsonProperty("GTRepairTime")
    var gtrepairTime = 1f
    @JsonProperty("GTRotationSpeed")
    var gtrotationSpeed = 1f
    @JsonProperty("GTShotDelay")
    var gtshotDelay = 1f
    @JsonProperty("PMCritProb")
    var pmcritProb = 1f
    @JsonProperty("PMDetonationProb")
    var pmdetonationProb = 1f
    @JsonProperty("PMRepairTime")
    var pmrepairTime = 1f
    @JsonProperty("SGCritProb")
    var sgcritProb = 1f
    @JsonProperty("SGRepairTime")
    var sgrepairTime = 1f
    @JsonProperty("SGRudderTime")
    var sgrudderTime = 1f
    var airDefenseDispWorkTime = 1f
    var airplanesDiveBombersHealth = 1f
    var airplanesEmptyReturnSpeed = 1f
    var airplanesExtraHangarSize: Float = 0f
    var airplanesFighterAimingTime: Float = 0f
    var airplanesFightersHealth = 1f
    var airplanesForsageDuration = 1f
    var airplanesHealth = 1f
    var airplanesSpawnTime = 1f
    var airplanesSpeed = 1f
    var airplanesTorpedoAimingTime: Float = 0f
    var airplanesTorpedoBombersHealth = 1f
    var burnProb = 1f
    var burnTime = 1f
    var crashCrewWorkTime = 1f
    var engineBackwardForsageMaxSpeed = 1f
    var engineBackwardForsagePower = 1f
    var engineBackwardUpTime = 1f
    var engineCritProb = 1f
    var engineForwardForsageMaxSpeed = 1f
    var engineForwardForsagePower = 1f
    var engineForwardUpTime = 1f
    var engineRepairTime = 1f
    var floodProb = 1f
    var floodTime = 1f
    var invulnerableWorkTime = 1f
    var rlsSearchWorkTime = 1f
    var scoutWorkTime = 1f
    var shootShift = 1f
    var smokeGeneratorLifeTime = 1f
    var smokeGeneratorWorkTime = 1f
    var sonarSearchWorkTime = 1f
    var speedBoosterWorkTime = 1f
    var squadronVisibilityDistCoeff = 1f
    var trigger1SearchWorkTime = 1f
    var trigger2SearchWorkTime = 1f
    var trigger3SearchWorkTime = 1f
    var trigger4SearchWorkTime = 1f
    var trigger5SearchWorkTime = 1f
    var trigger6SearchWorkTime = 1f
    var visibilityDistCoeff = 1f
    var visionDistCoeff = 1f
    var visionTorpedoCoeff = 1f
    var visionXRayShipCoeff = 1f

    //
    var modifier: String? = null
    var diveBomber = 1f
    var fighter = 1f
    var torpedoBomber = 1f
    var radiusCoefficient = 1f
    var smallGunReloadCoefficient = 1f
    var smallGunRangeCoefficient = 1f
    var consumablePlaneSpeedCoefficient = 1f
    var reloadCoefficient = 1f
    var alertMinDistance: Float = 0f
    var critTimeCoefficient = 1f
    var atbaIdealRadiusHi = 1f
    var atbaIdealRadiusLo = 1f
    var fightersAmmunitionCoefficient = 1f
    var fightersEfficiencyCoefficient = 1f
    var probabilityBonus: Float = 0f
    var probabilityCoefficient = 1f
    var switchAmmoReloadCoef = 1f

    var chanceToSetOnFireBonus: Float = 0f
    var chanceToSetOnFireBonusBig: Float = 0f
    var chanceToSetOnFireBonusSmall: Float = 0f
    var thresholdPenetrationCoefficient = 1f
    var thresholdPenetrationCoefficientBig = 1f
    var thresholdPenetrationCoefficientSmall = 1f

    var hpStep = 1f
    var timeStep: Float = 0f
    var critRudderTimeCoefficient = 1f
    var bigGunBonus: Float = 0f
    var smallGunBonus: Float = 0f
    var critProbCoefficient = 1f
    var fightersSpeedCoefficient = 1f
    var fightersVisibCoefficient = 1f
    var fightersVitalCoefficient = 1f
    var burnTimePenalty: Float = 0f
    var diveBombersPrepareCoefficient = 1f
    var fightersPrepareCoefficient = 1f
    var torpedoBombersPrepareCoefficient = 1f
    var vitalityCoefficient = 1f
    var fightersPassiveEfficiencyCoefficient = 1f
    var additionalConsumables: Float = 0f
    var healthPerLevel: Float = 0f
    var torpedoRangeCoefficient = 1f
    var torpedoSpeedBonus: Float = 0f
    var rangeCoefficient = 1f
    var bomberCoefficient = 1f
    var launcherCoefficient = 1f
    var aircraftCarrierCoefficient = 1f
    var battleshipCoefficient = 1f
    var cruiserCoefficient = 1f
    var destroyerCoefficient = 1f
    var submarineCoefficient = 1f
    var squadronCoefficient = 1f
    var nearAuraDamageCoefficient = 1f
    var advancedOuterAuraDamageCoefficient = 1f
    var extraFighterCount: Float = 0f
    var fighterLifeTimeCoefficient = 1f
    var hangarSizeBonus: Float = 0f
    var planeSpawnTimeCoefficient = 1f
    var prioritySectorStrengthCoefficient = 1f
    var sectorSwitchDelayCoefficient = 1f
    var bombProbabilityBonus: Float = 0f
    var rocketProbabilityBonus: Float = 0f
    var flightSpeedCoefficient = 1f
    var forsageDurationCoefficient = 1f
    var squadronHealthStep: Float = 0f
    var squadronSpeedStep = 1f
    var nearAuraDamageTakenCoefficient = 1f
    var planeHealthPerLevel: Float = 0f
    var planeTorpedoRangeCoefficient = 1f
    var planeTorpedoSpeedBonus: Float = 0f
    var planeRangeCoefficient = 1f
    var prioSectorCooldownCoefficient = 1f
    var prioSectorPhaseDurationCoefficient = 1f
    var prioSectorStartPhaseStrengthCoefficient = 1f
    var prioSectorStrengthCoefficient = 1f
}
