package wowsft.model.gameparams.ship.component.hull

import com.fasterxml.jackson.annotation.JsonProperty
import wowsft.config.WoWSFT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Hull {
    var backwardPowerCoef = 0f
    var baseUnderwaterPitchAngle = 0f
    var buoyancy = 0f
    var buoyancyRudderTime = 0f
    var burnNodes = ArrayList<MutableList<Any>>()
        set(value) {
            field = value
            this.burnProb = BigDecimal(burnNodes[0][1] as Double).toFloat()
            this.burnDamage = BigDecimal(burnNodes[0][2] as Double).toFloat()
            this.burnTime = BigDecimal(burnNodes[0][3] as Double).toFloat()
            this.burnSize = burnNodes.size
        }
    var burnProb = 0f
    var burnDamage = 0f
    var burnTime = 0f
    var burnSize = 0
    var deckHeight = 0f
    var deepwaterVisibilityCoeff = 0f
    var dockYOffset = 0f
    var draft = 0f
    var enginePower = 0f
    var floodNodes = ArrayList<MutableList<Float>>()
        set(value) {
            field = value
            this.floodProb = floodNodes[0][0]
            this.floodProtection = 100f - floodNodes[0][0] * 3.0f * 100f
            this.floodDamage = floodNodes[0][1]
            this.floodTime = floodNodes[0][2]
            this.floodSize = floodNodes.size
        }
    var floodProb = 0f
    var floodProtection = 0f
    var floodDamage = 0f
    var floodTime = 0f
    var floodSize = 0
    var health = 0f
    @JsonProperty("isBlind")
    var blind = false
    var mass = 0f
    var maxBuoyancyLevel = 0f
    var maxBuoyancySpeed = 0f
    var maxRudderAngle = 0f
    var maxSpeed = 0f
    var numOfParts = 0
    var pushingMaxRudderAngle = 0f
    var pushingMinRudderAngle = 0f
    var regenerationHPSpeed = 0f
    var repairingCoeff = 0f
    var rollEffect = 0f
    var rudderPower = 0f
    var rudderTime = 0f
    var sideDragCoef = 0f
    var size = ArrayList<Float>()
    var smokeScanRadius = 0f
    var speedCoef = 0f
    var tonnage = 0f
    var torpedoImpactMassImpulseCoeff = 0f
    var turningRadius = 0f
    var underwaterMaxRudderAngle = 0f
    var underwaterRollEffect = 0f
    var underwaterVisibilityCoeff = 0f
    var visibilityCoefATBA = 0f
    var visibilityCoefATBAByPlane = 0f
    var visibilityCoefFire = 0f
    var visibilityCoefFireByPlane = 0f
    var visibilityCoefGK = 0f
    var visibilityCoefGKByPlane = 0f
    var visibilityCoefGKInSmoke = 0f
    var visibilityCoeff = 0f
    var visibilityCoeffUnderwater = 0f
    var visibilityFactor = 0f
    var visibilityFactorByPlane = 0f
    var visibilityFactorInSmoke = 0f

    @JsonInclude
    var burnSizeSkill = 4
}
