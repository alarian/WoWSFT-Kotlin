package wowsft.model.gameparams

import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
open class CommonModifier {
    @JsonProperty("AAExtraBubbles")
    var aaextraBubbles = 0.toDouble()
    @JsonProperty("AANearDamage")
    var aanearDamage: Double = 1.0
    @JsonProperty("AAOuterDamage")
    var aaouterDamage: Double = 1.0
    @JsonProperty("ADMaxHP")
    var admaxHP: Double = 1.0
    @JsonProperty("GMCritProb")
    var gmcritProb: Double = 1.0
    @JsonProperty("GMIdealRadius")
    var gmidealRadius: Double = 1.0
    @JsonProperty("GMMaxDist")
    var gmmaxDist: Double = 1.0
    @JsonProperty("GMMaxHP")
    var gmmaxHP: Double = 1.0
    @JsonProperty("GMRepairTime")
    var gmrepairTime: Double = 1.0
    @JsonProperty("GMRotationSpeed")
    var gmrotationSpeed: Double = 1.0
    @JsonProperty("GMShotDelay")
    var gmshotDelay: Double = 1.0
    @JsonProperty("GMSigmaCount")
    var gmsigmaCount: Double = 1.0
    @JsonProperty("GSIdealRadius")
    var gsidealRadius: Double = 1.0
    @JsonProperty("GSMaxDist")
    var gsmaxDist: Double = 1.0
    @JsonProperty("GSMaxHP")
    var gsmaxHP: Double = 1.0
    @JsonProperty("GSShotDelay")
    var gsshotDelay: Double = 1.0
    @JsonProperty("GSSigmaCount")
    var gssigmaCount: Double = 1.0
    @JsonProperty("GTCritProb")
    var gtcritProb: Double = 1.0
    @JsonProperty("GTMaxHP")
    var gtmaxHP: Double = 1.0
    @JsonProperty("GTRepairTime")
    var gtrepairTime: Double = 1.0
    @JsonProperty("GTRotationSpeed")
    var gtrotationSpeed: Double = 1.0
    @JsonProperty("GTShotDelay")
    var gtshotDelay: Double = 1.0
    @JsonProperty("PMCritProb")
    var pmcritProb: Double = 1.0
    @JsonProperty("PMDetonationProb")
    var pmdetonationProb: Double = 1.0
    @JsonProperty("PMRepairTime")
    var pmrepairTime: Double = 1.0
    @JsonProperty("SGCritProb")
    var sgcritProb: Double = 1.0
    @JsonProperty("SGRepairTime")
    var sgrepairTime: Double = 1.0
    @JsonProperty("SGRudderTime")
    var sgrudderTime: Double = 1.0
    var airDefenseDispWorkTime: Double = 1.0
    var airplanesDiveBombersHealth: Double = 1.0
    var airplanesEmptyReturnSpeed: Double = 1.0
    var airplanesExtraHangarSize = 0.toDouble()
    var airplanesFighterAimingTime = 0.toDouble()
    var airplanesFightersHealth: Double = 1.0
    var airplanesForsageDuration: Double = 1.0
    var airplanesHealth: Double = 1.0
    var airplanesSpawnTime: Double = 1.0
    var airplanesSpeed: Double = 1.0
    var airplanesTorpedoAimingTime = 0.toDouble()
    var airplanesTorpedoBombersHealth: Double = 1.0
    var burnProb: Double = 1.0
    var burnTime: Double = 1.0
    var crashCrewWorkTime: Double = 1.0
    var engineBackwardForsageMaxSpeed: Double = 1.0
    var engineBackwardForsagePower: Double = 1.0
    var engineBackwardUpTime: Double = 1.0
    var engineCritProb: Double = 1.0
    var engineForwardForsageMaxSpeed: Double = 1.0
    var engineForwardForsagePower: Double = 1.0
    var engineForwardUpTime: Double = 1.0
    var engineRepairTime: Double = 1.0
    var floodProb: Double = 1.0
    var floodTime: Double = 1.0
    var invulnerableWorkTime: Double = 1.0
    var rlsSearchWorkTime: Double = 1.0
    var scoutWorkTime: Double = 1.0
    var shootShift: Double = 1.0
    var smokeGeneratorLifeTime: Double = 1.0
    var smokeGeneratorWorkTime: Double = 1.0
    var sonarSearchWorkTime: Double = 1.0
    var speedBoosterWorkTime: Double = 1.0
    var squadronVisibilityDistCoeff: Double = 1.0
    var trigger1SearchWorkTime: Double = 1.0
    var trigger2SearchWorkTime: Double = 1.0
    var trigger3SearchWorkTime: Double = 1.0
    var trigger4SearchWorkTime: Double = 1.0
    var trigger5SearchWorkTime: Double = 1.0
    var trigger6SearchWorkTime: Double = 1.0
    var visibilityDistCoeff: Double = 1.0
    var visionDistCoeff: Double = 1.0
    var visionTorpedoCoeff: Double = 1.0
    var visionXRayShipCoeff: Double = 1.0

    //
    var modifier: String = ""
    var diveBomber: Double = 1.0
    var fighter: Double = 1.0
    var torpedoBomber: Double = 1.0
    var radiusCoefficient: Double = 1.0
    var smallGunReloadCoefficient: Double = 1.0
    var smallGunRangeCoefficient: Double = 1.0
    var consumablePlaneSpeedCoefficient: Double = 1.0
    var reloadCoefficient: Double = 1.0
    var alertMinDistance = 0.toDouble()
    var critTimeCoefficient: Double = 1.0
    var atbaIdealRadiusHi: Double = 1.0
    var atbaIdealRadiusLo: Double = 1.0
    var fightersAmmunitionCoefficient: Double = 1.0
    var fightersEfficiencyCoefficient: Double = 1.0
    var probabilityBonus = 0.toDouble()
    var probabilityCoefficient: Double = 1.0
    var switchAmmoReloadCoef: Double = 1.0

    var chanceToSetOnFireBonus = 0.toDouble()
    var chanceToSetOnFireBonusBig = 0.toDouble()
    var chanceToSetOnFireBonusSmall = 0.toDouble()
    var thresholdPenetrationCoefficient: Double = 1.0
    var thresholdPenetrationCoefficientBig: Double = 1.0
    var thresholdPenetrationCoefficientSmall: Double = 1.0

    var hpStep: Double = 1.0
    var timeStep = 0.toDouble()
    var critRudderTimeCoefficient: Double = 1.0
    var bigGunBonus = 0.toDouble()
    var smallGunBonus = 0.toDouble()
    var critProbCoefficient: Double = 1.0
    var fightersSpeedCoefficient: Double = 1.0
    var fightersVisibCoefficient: Double = 1.0
    var fightersVitalCoefficient: Double = 1.0
    var burnTimePenalty = 0.toDouble()
    var diveBombersPrepareCoefficient: Double = 1.0
    var fightersPrepareCoefficient: Double = 1.0
    var torpedoBombersPrepareCoefficient: Double = 1.0
    var vitalityCoefficient: Double = 1.0
    var fightersPassiveEfficiencyCoefficient: Double = 1.0
    var additionalConsumables = 0.toDouble()
    var healthPerLevel = 0.toDouble()
    var torpedoRangeCoefficient: Double = 1.0
    var torpedoSpeedBonus = 0.toDouble()
    var rangeCoefficient: Double = 1.0
    var bomberCoefficient: Double = 1.0
    var launcherCoefficient: Double = 1.0
    var aircraftCarrierCoefficient: Double = 1.0
    var battleshipCoefficient: Double = 1.0
    var cruiserCoefficient: Double = 1.0
    var destroyerCoefficient: Double = 1.0
    var submarineCoefficient: Double = 1.0
    var squadronCoefficient: Double = 1.0
    var nearAuraDamageCoefficient: Double = 1.0
    var advancedOuterAuraDamageCoefficient: Double = 1.0
    var extraFighterCount = 0.toDouble()
    var fighterLifeTimeCoefficient: Double = 1.0
    var hangarSizeBonus = 0.toDouble()
    var planeSpawnTimeCoefficient: Double = 1.0
    var prioritySectorStrengthCoefficient: Double = 1.0
    var sectorSwitchDelayCoefficient: Double = 1.0
    var bombProbabilityBonus = 0.toDouble()
    var rocketProbabilityBonus = 0.toDouble()
    var flightSpeedCoefficient: Double = 1.0
    var forsageDurationCoefficient: Double = 1.0
    var squadronHealthStep = 0.toDouble()
    var squadronSpeedStep: Double = 1.0
    var nearAuraDamageTakenCoefficient: Double = 1.0
    var planeHealthPerLevel = 0.toDouble()
    var planeTorpedoRangeCoefficient: Double = 1.0
    var planeTorpedoSpeedBonus = 0.toDouble()
    var planeRangeCoefficient: Double = 1.0
    var prioSectorCooldownCoefficient: Double = 1.0
    var prioSectorPhaseDurationCoefficient: Double = 1.0
    var prioSectorStartPhaseStrengthCoefficient: Double = 1.0
    var prioSectorStrengthCoefficient: Double = 1.0
}
