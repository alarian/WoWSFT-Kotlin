package wowsft.model.gameparams.ship.component.hull

import com.fasterxml.jackson.annotation.JsonAlias
import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Hull {
    var backwardPowerCoef = 0.0
    var baseUnderwaterPitchAngle = 0.0
    var buoyancy = 0.0
    var buoyancyRudderTime = 0.0
    var burnNodes = ArrayList<MutableList<Any>>()
        set(value) {
            field = value
            this.burnProb = BigDecimal(burnNodes[0][1] as Double).toDouble()
            this.burnDamage = BigDecimal(burnNodes[0][2] as Double).toDouble()
            this.burnTime = BigDecimal(burnNodes[0][3] as Double).toDouble()
            this.burnSize = burnNodes.size
        }
    var burnProb = 0.0
    var burnDamage = 0.0
    var burnTime = 0.0
    var burnSize = 0
    var deckHeight = 0.0
    var deepwaterVisibilityCoeff = 0.0
    var dockYOffset = 0.0
    var draft = 0.0
    var enginePower = 0.0
    var floodNodes = ArrayList<MutableList<Double>>()
        set(value) {
            field = value
            this.floodProb = floodNodes[0][0]
            this.floodProtection = 100.0 - floodNodes[0][0] * 3.toDouble() * 100.0
            this.floodDamage = floodNodes[0][1]
            this.floodTime = floodNodes[0][2]
            this.floodSize = floodNodes.size
        }
    var floodProb = 0.0
    var floodProtection = 0.0
    var floodDamage = 0.0
    var floodTime = 0.0
    var floodSize = 0
    var health = 0.0
    @JsonAlias("isBlind")
    var blind = false
    var mass = 0.0
    var maxBuoyancyLevel = 0.0
    var maxBuoyancySpeed = 0.0
    var maxRudderAngle = 0.0
    var maxSpeed = 0.0
    var numOfParts = 0
    var pushingMaxRudderAngle = 0.0
    var pushingMinRudderAngle = 0.0
    var regenerationHPSpeed = 0.0
    var repairingCoeff = 0.0
    var rollEffect = 0.0
    var rudderPower = 0.0
    var rudderTime = 0.0
    var sideDragCoef = 0.0
    var size = ArrayList<Double>()
    var smokeScanRadius = 0.0
    var speedCoef = 0.0
    var tonnage = 0.0
    var torpedoImpactMassImpulseCoeff = 0.0
    var turningRadius = 0.0
    var underwaterMaxRudderAngle = 0.0
    var underwaterRollEffect = 0.0
    var underwaterVisibilityCoeff = 0.0
    var visibilityCoefATBA = 0.0
    var visibilityCoefATBAByPlane = 0.0
    var visibilityCoefFire = 0.0
    var visibilityCoefFireByPlane = 0.0
    var visibilityCoefGK = 0.0
    var visibilityCoefGKByPlane = 0.0
    var visibilityCoefGKInSmoke = 0.0
    var visibilityCoeff = 0.0
    var visibilityCoeffUnderwater = 0.0
    var visibilityFactor = 0.0
    var visibilityFactorByPlane = 0.0
    var visibilityFactorInSmoke = 0.0

    @JsonInclude
    var burnSizeSkill = 4
}
