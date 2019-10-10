package wowsft.service

import wowsft.model.gameparams.CommonModifier
import wowsft.model.gameparams.ship.Ship
import wowsft.model.gameparams.ship.component.airdefense.Aura
import wowsft.model.gameparams.ship.component.planes.Plane
import wowsft.utils.CommonUtils
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.collections4.CollectionUtils
import org.springframework.stereotype.Service

import java.util.LinkedHashMap

import wowsft.model.Constant
import kotlin.collections.ArrayList

@Service
class ParamService
{
    private val mapper = ObjectMapper()

    fun setAA(ship: Ship)
    {
        val auraFar = ArrayList<Aura>()
        val auraMedium = ArrayList<Aura>()
        val auraNear = ArrayList<Aura>()

        ship.components.airDefense.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.airDefense], ignoreCase = true)) {
                addAura(auraFar, value.auraFar, true)
                addAura(auraMedium, value.auraMedium, false)
                addAura(auraNear, value.auraNear, false)
            }
        }

        ship.components.atba.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.atba], ignoreCase = true)) {
                addAura(auraFar, value.auraFar, true)
                addAura(auraMedium, value.auraMedium, false)
                addAura(auraNear, value.auraNear, false)
            }
        }

        ship.components.artillery.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.artillery], ignoreCase = true)) {
                addAura(auraFar, value.auraFar, true)
                addAura(auraMedium, value.auraMedium, false)
                addAura(auraNear, value.auraNear, false)
            }
        }

        ship.auraFar = sortAura(auraFar.filter { it.areaDamage > 0 } as ArrayList<Aura>)
        ship.auraFarBubble = sortAura(auraFar.filter { it.bubbleDamage > 0 } as ArrayList<Aura>)
        ship.auraMedium = sortAura(auraMedium)
        ship.auraNear = sortAura(auraNear)
    }

    private fun addAura(aura: MutableList<Aura>, temp: List<Aura>, hasBubble: Boolean)
    {
        for (x in temp) {
            if (!hasBubble || x.innerBubbleCount > 0 || x.outerBubbleCount > 0) {
                aura.add(x)
            }
        }
    }

    private fun sortAura(aura: ArrayList<Aura>): ArrayList<Aura>
    {
        if (aura.size > 1) {
            for (i in 1 until aura.size) {
                aura[0].innerBubbleCount = aura[0].innerBubbleCount + aura[i].innerBubbleCount
                aura[0].outerBubbleCount = aura[0].outerBubbleCount + aura[i].outerBubbleCount
            }
            aura.subList(1, aura.size).clear()
        }
        return aura
    }

    fun setParameters(ship: Ship)
    {
        for (i in 0 until ship.selectSkills.size) {
            if (ship.selectSkills[i] == 1) {
                val modifier = mapper.convertValue(ship.commander.cSkills[i / 8][i % 8], CommonModifier::class.java)
                setUpgrades(ship, modifier)
            }
        }

        for (i in 0 until ship.selectUpgrades.size) {
            if (ship.selectUpgrades[i] > 0) {
                val modifier = mapper.convertValue(ship.upgrades[i][ship.selectUpgrades[i] - 1], CommonModifier::class.java)
                setUpgrades(ship, modifier)
            }
        }

        ship.consumables.forEach { slot ->
            slot.forEach { c ->
                c.subConsumables.forEach { (_, sub) ->
                    sub.bonus = getBonus(mapper.convertValue(sub, object : TypeReference<LinkedHashMap<String, Any>>() {}))
                }
            }
        }
        ship.components.fighter.forEach { (_, p) ->
            p.consumables.forEach { c ->
                c.subConsumables.forEach { (_, sVal) ->
                    sVal.bonus = getBonus(mapper.convertValue(sVal, object : TypeReference<LinkedHashMap<String, Any>>() {}))
                }
            }
        }
        ship.components.diveBomber.forEach { (_, p) ->
            p.consumables.forEach { c ->
                c.subConsumables.forEach { (_, sVal) ->
                    sVal.bonus = getBonus(mapper.convertValue(sVal, object : TypeReference<LinkedHashMap<String, Any>>() {}))
                }
            }
        }
        ship.components.torpedoBomber.forEach { (_, p) ->
            p.consumables.forEach { c ->
                c.subConsumables.forEach { (_, sVal) ->
                    sVal.bonus = getBonus(mapper.convertValue(sVal, object : TypeReference<LinkedHashMap<String, Any>>() {}))
                }
            }
        }
    }

    private fun setUpgrades(ship: Ship, modifier: CommonModifier)
    {
        ship.components.artillery.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.artillery], ignoreCase = true)) {
                value.GMIdealRadius = value.GMIdealRadius * modifier.gmidealRadius
                value.maxDist = value.maxDist * modifier.gmmaxDist * if (value.barrelDiameter > Constant.smallGun) Constant.oneCoeff else modifier.smallGunRangeCoefficient
                value.turrets.forEach { t ->
                    t.rotationSpeed[0] = (t.rotationSpeed[0] + if (t.barrelDiameter > Constant.smallGun) modifier.bigGunBonus else modifier.smallGunBonus) * modifier.gmrotationSpeed
                    t.shotDelay = (t.shotDelay * modifier.gmshotDelay * (if (t.barrelDiameter > Constant.smallGun) Constant.oneCoeff else modifier.smallGunReloadCoefficient)
                            * (Constant.oneCoeff - ship.adrenaline / modifier.hpStep * modifier.timeStep))
                }
                value.shells.forEach { (_, ammo) ->
                    if ("HE".equals(ammo.ammoType, ignoreCase = true)) {
                        ammo.burnProb = ammo.burnProb + modifier.probabilityBonus + if (ammo.bulletDiametr > Constant.smallGun) modifier.chanceToSetOnFireBonusBig else modifier.chanceToSetOnFireBonusSmall
                        ammo.alphaPiercingHE = ammo.alphaPiercingHE * if (ammo.bulletDiametr > Constant.smallGun) modifier.thresholdPenetrationCoefficientBig else modifier.thresholdPenetrationCoefficientSmall
                    }
                }
            }
        }

        ship.components.torpedoes.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.torpedoes], ignoreCase = true)) {
                value.launchers.forEach { l ->
                    l.rotationSpeed[0] = l.rotationSpeed[0] * modifier.gtrotationSpeed
                    l.shotDelay = l.shotDelay * modifier.gtshotDelay * modifier.launcherCoefficient * (Constant.oneCoeff - ship.adrenaline / modifier.hpStep * modifier.timeStep)
                }
                value.ammo.maxDist = value.ammo.maxDist * modifier.torpedoRangeCoefficient
                value.ammo.speed = value.ammo.speed + modifier.torpedoSpeedBonus
            }
        }

        ship.components.fighter.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.fighter], ignoreCase = true)) {
                setPlanes(ship, value, modifier)

                value.maxHealth = ((value.maxHealth + ship.level * modifier.planeHealthPerLevel) * modifier.airplanesFightersHealth * modifier.airplanesHealth).toInt()
                if ("HE".equals(value.rocket!!.ammoType, ignoreCase = true)) {
                    value.rocket!!.burnProb = value.rocket!!.burnProb + modifier.rocketProbabilityBonus
                }
            }
        }

        ship.components.diveBomber.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.diveBomber], ignoreCase = true)) {
                setPlanes(ship, value, modifier)

                value.maxHealth = ((value.maxHealth + ship.level * modifier.planeHealthPerLevel) * modifier.airplanesDiveBombersHealth * modifier.airplanesHealth).toInt()
                if ("HE".equals(value.bomb!!.ammoType, ignoreCase = true)) {
                    value.bomb!!.burnProb = value.bomb!!.burnProb + modifier.bombProbabilityBonus
                }
            }
        }

        ship.components.torpedoBomber.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.torpedoBomber], ignoreCase = true)) {
                setPlanes(ship, value, modifier)

                value.maxHealth = ((value.maxHealth + ship.level * modifier.planeHealthPerLevel) * modifier.airplanesTorpedoBombersHealth * modifier.airplanesHealth).toInt()
                value.torpedo!!.maxDist = value.torpedo!!.maxDist * modifier.planeTorpedoRangeCoefficient
                value.torpedo!!.speed = value.torpedo!!.speed + modifier.planeTorpedoSpeedBonus
            }
        }

        ship.components.airArmament.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.airArmament], ignoreCase = true)) {
                value.deckPlaceCount = value.deckPlaceCount + modifier.airplanesExtraHangarSize
            }
        }

        ship.components.atba.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.atba], ignoreCase = true)) {
                value.GSIdealRadius = value.GSIdealRadius * modifier.gsidealRadius
                value.maxDist = value.maxDist * modifier.gsmaxDist * modifier.smallGunRangeCoefficient

                value.secondaries.forEach { (_, sec) ->
                    sec.shotDelay = sec.shotDelay * modifier.gsshotDelay * modifier.smallGunReloadCoefficient * (Constant.oneCoeff - ship.adrenaline / modifier.hpStep * modifier.timeStep)
                    sec.GSIdealRadius = sec.GSIdealRadius * modifier.gsidealRadius * if (ship.level >= 7) modifier.atbaIdealRadiusHi else modifier.atbaIdealRadiusLo
                    if ("HE".equals(sec.ammoType, ignoreCase = true)) {
                        sec.burnProb = sec.burnProb + modifier.probabilityBonus + modifier.chanceToSetOnFireBonusSmall
                        sec.alphaPiercingHE = sec.alphaPiercingHE * modifier.thresholdPenetrationCoefficientSmall
                    }
                }
            }
        }

        ship.components.airDefense.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.airDefense], ignoreCase = true)) {
                value.prioritySectorStrength = value.prioritySectorStrength * modifier.prioritySectorStrengthCoefficient
                value.prioritySectorChangeDelay = value.prioritySectorChangeDelay * modifier.sectorSwitchDelayCoefficient
                value.prioritySectorEnableDelay = value.prioritySectorEnableDelay * modifier.sectorSwitchDelayCoefficient

                value.prioritySectorPreparation = value.prioritySectorPreparation * modifier.prioSectorCooldownCoefficient
                value.prioritySectorDuration = value.prioritySectorDuration * modifier.prioSectorCooldownCoefficient
                value.prioritySectorDamageInitial = value.prioritySectorDamageInitial * modifier.prioSectorStartPhaseStrengthCoefficient
            }
        }

        ship.components.hull.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.hull], ignoreCase = true)) {
                value.burnProb = value.burnProb * modifier.burnProb * modifier.probabilityCoefficient
                value.burnTime = value.burnTime * modifier.burnTime * modifier.critTimeCoefficient
                value.burnSizeSkill = if (modifier.probabilityCoefficient != Constant.oneCoeff) 3 else value.burnSizeSkill
                value.floodProb = value.floodProb * modifier.floodProb
                value.floodTime = value.floodTime * modifier.floodTime * modifier.critTimeCoefficient
                value.rudderTime = value.rudderTime * modifier.sgrudderTime
                value.visibilityFactor = value.visibilityFactor * modifier.visibilityDistCoeff
                value.visibilityFactorByPlane = value.visibilityFactorByPlane * modifier.visibilityDistCoeff

                if (!Constant.excludeShipSpecies.contains(ship.typeinfo!!.species)) {
                    value.visibilityFactor = value.visibilityFactor * modifier.cruiserCoefficient
                    value.visibilityFactorByPlane = value.visibilityFactorByPlane * modifier.cruiserCoefficient
                }

                value.health = value.health + ship.level * modifier.healthPerLevel
            }
        }

        ship.components.engine.forEach { (c, value) ->
            if (c.equals(ship.modules[Constant.engine], ignoreCase = true)) {
                value.backwardEngineForsagMaxSpeed = value.backwardEngineForsagMaxSpeed * modifier.engineBackwardForsageMaxSpeed
                value.backwardEngineForsag = value.backwardEngineForsag * modifier.engineBackwardForsagePower
                value.backwardEngineUpTime = value.backwardEngineUpTime * modifier.engineBackwardUpTime
                value.forwardEngineForsagMaxSpeed = value.forwardEngineForsagMaxSpeed * modifier.engineForwardForsageMaxSpeed
                value.forwardEngineUpTime = value.forwardEngineUpTime * modifier.engineForwardUpTime
                value.forwardEngineForsag = value.forwardEngineForsag * modifier.engineForwardForsagePower
            }
        }

        ship.auraFar.forEach { aura -> setAura(aura, modifier) }
        ship.auraFarBubble.forEach { aura -> setAura(aura, modifier) }
        ship.auraMedium.forEach { aura -> setAura(aura, modifier) }
        ship.auraNear.forEach { aura -> setAura(aura, modifier) }

        ship.consumables.forEach { c ->
            c.forEach { s ->
                s.subConsumables.forEach { (_, sC) ->
                    sC.workTime = sC.workTime * if ("scout".equals(sC.consumableType, ignoreCase = true)) modifier.scoutWorkTime else Constant.oneCoeff
                    sC.workTime = sC.workTime * if ("crashCrew".equals(sC.consumableType, ignoreCase = true)) modifier.crashCrewWorkTime else Constant.oneCoeff
                    sC.reloadTime = sC.reloadTime *
                            if ("crashCrew".equals(sC.consumableType, ignoreCase = true) && "EmergencyTeamCooldownModifier".equals(modifier.modifier, ignoreCase = true)) modifier.reloadCoefficient
                            else Constant.oneCoeff
                    sC.workTime = sC.workTime * if ("speedBoosters".equals(sC.consumableType, ignoreCase = true)) modifier.speedBoosterWorkTime else Constant.oneCoeff
                    sC.workTime = sC.workTime * if ("airDefenseDisp".equals(sC.consumableType, ignoreCase = true)) modifier.airDefenseDispWorkTime else Constant.oneCoeff
                    sC.workTime = sC.workTime * if ("sonar".equals(sC.consumableType, ignoreCase = true)) modifier.sonarSearchWorkTime else Constant.oneCoeff
                    sC.workTime = sC.workTime * if ("rls".equals(sC.consumableType, ignoreCase = true)) modifier.rlsSearchWorkTime else Constant.oneCoeff
                    sC.workTime = sC.workTime * if ("smokeGenerator".equals(sC.consumableType, ignoreCase = true)) modifier.smokeGeneratorWorkTime else Constant.oneCoeff
                    sC.lifeTime = sC.lifeTime * if ("smokeGenerator".equals(sC.consumableType, ignoreCase = true)) modifier.smokeGeneratorLifeTime else Constant.oneCoeff
                    sC.radius = sC.radius * if ("smokeGenerator".equals(sC.consumableType, ignoreCase = true)) modifier.radiusCoefficient else Constant.oneCoeff
                    sC.reloadTime = sC.reloadTime * if ("AllSkillsCooldownModifier".equals(modifier.modifier, ignoreCase = true)) modifier.reloadCoefficient else Constant.oneCoeff

                    sC.numConsumables = sC.numConsumables + if (sC.numConsumables > 0) modifier.additionalConsumables else 0.toDouble()
                }
            }
        }
    }

    private fun setAura(aura: Aura?, modifier: CommonModifier)
    {
        if (aura != null) {
            if (aura.innerBubbleCount > 0) {
                aura.innerBubbleCount = aura.innerBubbleCount + modifier.aaextraBubbles
                aura.bubbleDamage = aura.bubbleDamage * modifier.aaouterDamage * modifier.advancedOuterAuraDamageCoefficient
            }
            aura.areaDamage = aura.areaDamage * modifier.aanearDamage * modifier.nearAuraDamageCoefficient
        }
    }

    fun setBonusParams(key: String, tempCopy: LinkedHashMap<String, Any>, bonus: LinkedHashMap<String, String>)
    {
        tempCopy.forEach { (param, cVal) ->
            if (cVal is Double && cVal != 0.0) {
                bonus[Constant.MODIFIER + param.toUpperCase()] =
                        if (Constant.excludeModernization.any { param.toLowerCase().contains(it) }) CommonUtils.getNumSym(cVal)
                        else CommonUtils.getNumSym(CommonUtils.getBonusCoef(cVal)) + " %"
            }
        }
    }

    fun getBonus(copy: LinkedHashMap<String, Any>): LinkedHashMap<String, String>
    {
        val bonus = LinkedHashMap<String, String>()

        copy.forEach { (param, cVal) ->
            if (Constant.speed.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.getNumSym(cVal as Double) + " kts"
            } else if (param.toLowerCase().contains("boostcoeff")) {
                if (cVal as Double >= 2.0) {
                    bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.getNumSym(cVal)
                } else {
                    bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.getNumSym(CommonUtils.getBonus(cVal)) + " %"
                }
            } else if (Constant.rate.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.getNumSym(CommonUtils.getBonus(cVal as Double)) + " %"
            } else if (Constant.multiple.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = "X " + CommonUtils.replaceZero(cVal.toString())
            } else if (Constant.coeff.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.getNumSym(CommonUtils.getBonusCoef(cVal as Double)) + " %"
            } else if (Constant.noUnit.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = if (cVal as Double > 0) CommonUtils.replaceZero(cVal.toString()) else "∞"
            } else if (Constant.meter.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.getDistCoefWG(cVal as Double).toString() + " km"
            } else if (Constant.rateNoSym.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.replaceZero(cVal.toString()) + " %"
            } else if (Constant.time.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.replaceZero(cVal.toString()) + " s"
            } else if (Constant.extraAngle.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.getNumSym(cVal as Double) + " °"
            } else if (Constant.angle.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.replaceZero(cVal.toString()) + " °"
            } else if (Constant.extra.any { param.toLowerCase().contains(it) }) {
                bonus[Constant.MODIFIER + param.toUpperCase()] = CommonUtils.getNumSym(cVal as Double)
            } else if (param.toLowerCase().equals("affectedClasses", ignoreCase = true)) {
                val tempList = mapper.convertValue<List<String>>(cVal, object : TypeReference<List<String>>() {})
                if (CollectionUtils.isNotEmpty(tempList)) {
                    var affected = ""
                    for (tl in tempList) {
                        affected += (Constant.IDS + tl.toUpperCase() + " ")
                    }
                    bonus[Constant.MODIFIER + param.toUpperCase()] = affected.trim()
                }
            }
        }
        return bonus
    }

    private fun setPlanes(ship: Ship, plane: Plane, modifier: CommonModifier) {
        if (plane.hangarSettings != null) {
            plane.hangarSettings!!.timeToRestore = plane.hangarSettings!!.timeToRestore * modifier.planeSpawnTimeCoefficient * modifier.airplanesSpawnTime
        }
        plane.maxForsageAmount = plane.maxForsageAmount * modifier.forsageDurationCoefficient * modifier.airplanesForsageDuration
        plane.consumables.forEach { consumable -> consumable.subConsumables.values.forEach { sub -> sub.reloadTime = sub.reloadTime * modifier.reloadCoefficient } }
        plane.speedMoveWithBomb = plane.speedMoveWithBomb * modifier.flightSpeedCoefficient
        plane.speedMove = plane.speedMove * modifier.flightSpeedCoefficient
        plane.maxVisibilityFactor = plane.maxVisibilityFactor * modifier.squadronCoefficient * modifier.squadronVisibilityDistCoeff
        plane.maxVisibilityFactorByPlane = plane.maxVisibilityFactorByPlane * modifier.squadronCoefficient * modifier.squadronVisibilityDistCoeff
        plane.speedMoveWithBomb = plane.speedMoveWithBomb * modifier.airplanesSpeed * (Constant.oneCoeff + ship.adrenaline / modifier.squadronHealthStep * modifier.squadronSpeedStep)
        plane.consumables.forEach { c ->
            c.subConsumables.forEach { (_, value) ->
                value.reloadTime = value.reloadTime * if ("AllSkillsCooldownModifier".equals(modifier.modifier, ignoreCase = true)) modifier.reloadCoefficient else Constant.oneCoeff
                value.fightersNum = value.fightersNum + if (value.fightersNum > 0) modifier.extraFighterCount else 0.0
            }
        }
    }
}
