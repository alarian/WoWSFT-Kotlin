package wowsft.model.gameparams.ship.component.hull

import com.fasterxml.jackson.annotation.JsonProperty
import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Hull {
    var backwardPowerCoef = 0.toDouble()
    var baseUnderwaterPitchAngle = 0.toDouble()
    var buoyancy = 0.toDouble()
    var buoyancyRudderTime = 0.toDouble()
    var burnNodes = ArrayList<MutableList<Any>>()
        set(value) {
            field = value
            this.burnProb = BigDecimal(burnNodes[0][1] as Double).toDouble()
            this.burnDamage = BigDecimal(burnNodes[0][2] as Double).toDouble()
            this.burnTime = BigDecimal(burnNodes[0][3] as Double).toDouble()
            this.burnSize = burnNodes.size
        }
    var burnProb = 0.toDouble()
    var burnDamage = 0.toDouble()
    var burnTime = 0.toDouble()
    var burnSize = 0
    var deckHeight = 0.toDouble()
    var deepwaterVisibilityCoeff = 0.toDouble()
    var dockYOffset = 0.toDouble()
    var draft = 0.toDouble()
    var enginePower = 0.toDouble()
    var floodNodes = ArrayList<MutableList<Double>>()
        set(value) {
            field = value
            this.floodProb = floodNodes[0][0]
            this.floodProtection = 100.toDouble() - floodNodes[0][0] * 3.toDouble() * 100.toDouble()
            this.floodDamage = floodNodes[0][1]
            this.floodTime = floodNodes[0][2]
            this.floodSize = floodNodes.size
        }
    var floodProb = 0.toDouble()
    var floodProtection = 0.toDouble()
    var floodDamage = 0.toDouble()
    var floodTime = 0.toDouble()
    var floodSize = 0
    var health = 0.toDouble()
    @JsonProperty("isBlind")
    var blind = false
    var mass = 0.toDouble()
    var maxBuoyancyLevel = 0.toDouble()
    var maxBuoyancySpeed = 0.toDouble()
    var maxRudderAngle = 0.toDouble()
    var maxSpeed = 0.toDouble()
    var numOfParts = 0
    var pushingMaxRudderAngle = 0.toDouble()
    var pushingMinRudderAngle = 0.toDouble()
    var regenerationHPSpeed = 0.toDouble()
    var repairingCoeff = 0.toDouble()
    var rollEffect = 0.toDouble()
    var rudderPower = 0.toDouble()
    var rudderTime = 0.toDouble()
    var sideDragCoef = 0.toDouble()
    var size = ArrayList<Float>()
    var smokeScanRadius = 0.toDouble()
    var speedCoef = 0.toDouble()
    var tonnage = 0.toDouble()
    var torpedoImpactMassImpulseCoeff = 0.toDouble()
    var turningRadius = 0.toDouble()
    var underwaterMaxRudderAngle = 0.toDouble()
    var underwaterRollEffect = 0.toDouble()
    var underwaterVisibilityCoeff = 0.toDouble()
    var visibilityCoefATBA = 0.toDouble()
    var visibilityCoefATBAByPlane = 0.toDouble()
    var visibilityCoefFire = 0.toDouble()
    var visibilityCoefFireByPlane = 0.toDouble()
    var visibilityCoefGK = 0.toDouble()
    var visibilityCoefGKByPlane = 0.toDouble()
    var visibilityCoefGKInSmoke = 0.toDouble()
    var visibilityCoeff = 0.toDouble()
    var visibilityCoeffUnderwater = 0.toDouble()
    var visibilityFactor = 0.toDouble()
    var visibilityFactorByPlane = 0.toDouble()
    var visibilityFactorInSmoke = 0.toDouble()

    @JsonInclude
    var burnSizeSkill = 4
}
