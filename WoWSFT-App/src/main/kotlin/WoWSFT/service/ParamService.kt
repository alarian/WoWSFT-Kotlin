package WoWSFT.service

import WoWSFT.model.Constant.*
import WoWSFT.model.gameparams.CommonModifier
import WoWSFT.model.gameparams.flag.Flag
import WoWSFT.model.gameparams.ship.Ship
import WoWSFT.model.gameparams.ship.component.airdefense.Aura
import WoWSFT.model.gameparams.ship.component.planes.Plane
import WoWSFT.model.gameparams.ship.component.torpedo.Launcher
import WoWSFT.utils.CommonUtils.getBonus
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*

@Service
class ParamService(
    @Autowired @Qualifier(TYPE_FLAG) private val flagsLHM: LinkedHashMap<String, Flag>
)
{
    private val mapper = ObjectMapper()

    fun setAA(ship: Ship)
    {
        val auraFar = mutableListOf<Aura>()
        val auraMedium = mutableListOf<Aura>()
        val auraNear = mutableListOf<Aura>()

        ship.components.airDefense.forEach { (c, v) ->
            if (c.equals(ship.modules[airDefense], ignoreCase = true)) {
                addAura(auraFar, v.auraFar, true)
                addAura(auraMedium, v.auraMedium, false)
                addAura(auraNear, v.auraNear, false)
            }
        }
        ship.components.atba.forEach { (c, v) ->
            if (c.equals(ship.modules[atba], ignoreCase = true)) {
                addAura(auraFar, v.auraFar, true)
                addAura(auraMedium, v.auraMedium, false)
                addAura(auraNear, v.auraNear, false)
            }
        }
        ship.components.artillery.forEach { (c, v) ->
            if (c.equals(ship.modules[artillery], ignoreCase = true)) {
                addAura(auraFar, v.auraFar, true)
                addAura(auraMedium, v.auraMedium, false)
                addAura(auraNear, v.auraNear, false)
            }
        }

        ship.auraFar = sortAura(auraFar.filter { it.areaDamage > 0 }.toMutableList())
        ship.auraFarBubble = sortAura(auraFar.filter { it.bubbleDamage > 0 }.toMutableList())
        ship.auraMedium = sortAura(auraMedium)
        ship.auraNear = sortAura(auraNear)
    }

    private fun addAura(aura: MutableList<Aura>, temp: MutableList<Aura>, hasBubble: Boolean)
    {
        for (x in temp) {
            if (!hasBubble || x.innerBubbleCount > 0 || x.outerBubbleCount > 0) {
                aura.add(x)
            }
        }
    }

    private fun sortAura(aura: MutableList<Aura>): MutableList<Aura>
    {
        if (aura.size > 1) {
            for (i in 1 until aura.size) {
                aura[0].innerBubbleCount = aura[0].innerBubbleCount + aura[i].innerBubbleCount
                aura[0].outerBubbleCount = aura[0].outerBubbleCount + aura[i].outerBubbleCount
                aura[0].areaDamage = aura[0].areaDamage + aura[i].areaDamage
            }
            aura.subList(1, aura.size).clear()
        }
        return aura
    }

    fun setParameters(ship: Ship)
    {
        for (i in ship.selectUpgrades.indices) {
            if (ship.selectUpgrades[i] > 0) {
                val modifier = mapper.convertValue(ship.upgrades[i][ship.selectUpgrades[i] - 1], CommonModifier::class.java)
                setUpgrades(ship, modifier)
            }
        }

        for (i in ship.selectSkills.indices) {
            if (ship.selectSkills[i] == 1) {
                val modifier = mapper.convertValue(ship.commander!!.crewSkills[i / 8][i % 8], CommonModifier::class.java)
                setUpgrades(ship, modifier)
            }
        }

        val flagsKey = flagsLHM.keys.toList()
        for (i in ship.selectFlags.indices) {
            if (ship.selectFlags[i] == 1) {
                val modifier = mapper.convertValue(flagsLHM[flagsKey[i]], CommonModifier::class.java)
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
        ship.components.artillery.forEach { (c, v) ->
            if (c.equals(ship.modules[artillery], ignoreCase = true)) {
//                v.GMIdealRadius = v.GMIdealRadius * modifier.gmidealRadius
                v.maxDist = v.maxDist * modifier.gmMaxDist * if (v.barrelDiameter > smallGun) oneCoeff else modifier.smallGunRangeCoefficient

                v.turrets.forEach { t ->
                    t.rotationSpeed[0] = t.rotationSpeed[0] * modifier.gmRotationSpeed + if (t.barrelDiameter > smallGun) modifier.bigGunBonus else modifier.smallGunBonus
                    t.shotDelay = t.shotDelay * modifier.gmShotDelay *
                            (if (t.barrelDiameter > smallGun) oneCoeff else modifier.smallGunReloadCoefficient) *
                            (oneCoeff - ship.adrenaline / modifier.hpStep * modifier.timeStep)
                    t.idealRadiusModifier = t.idealRadiusModifier * modifier.gmIdealRadius
                }

                v.shells.forEach { (_, ammo) ->
                    if (HE.equals(ammo.ammoType, ignoreCase = true)) {
                        ammo.burnProb = ammo.burnProb + modifier.probabilityBonus +
                                (if (ammo.bulletDiametr > smallGun) modifier.chanceToSetOnFireBonusBig else modifier.chanceToSetOnFireBonusSmall) +
                                (if (ammo.bulletDiametr > smallGunFlag) (modifier.burnChanceFactorBig - 1.0) else (modifier.burnChanceFactorSmall - 1.0))
                        ammo.alphaPiercingHE = ammo.alphaPiercingHE *
                                if (ammo.bulletDiametr > smallGun) modifier.thresholdPenetrationCoefficientBig else modifier.thresholdPenetrationCoefficientSmall
                    }
                }
            }
        }

        ship.components.torpedoes.forEach { (c, v) ->
            if (c.equals(ship.modules[torpedoes], ignoreCase = true)) {
                v.launchers.forEach { l: Launcher ->
                    l.rotationSpeed[0] = l.rotationSpeed[0] * modifier.gtRotationSpeed
                    l.shotDelay = l.shotDelay * modifier.gtShotDelay * modifier.launcherCoefficient * (oneCoeff - ship.adrenaline / modifier.hpStep * modifier.timeStep)
                }

                v.ammo.maxDist = v.ammo.maxDist * modifier.torpedoRangeCoefficient
                v.ammo.speed = v.ammo.speed + modifier.torpedoSpeedBonus
                v.ammo.speed = v.ammo.speed * modifier.torpedoSpeedMultiplier
                v.ammo.uwCritical = v.ammo.uwCritical + (modifier.floodChanceFactor - 1.0)
            }
        }

        ship.components.fighter.forEach { (c, v) ->
            if (c.equals(ship.modules[fighter], ignoreCase = true)) {
                setPlanes(ship, v, modifier)
                v.maxHealth = ((v.maxHealth + ship.level * modifier.planeHealthPerLevel) * modifier.airplanesFightersHealth * modifier.airplanesHealth).toInt()
                if (HE.equals(v.rocket.ammoType, ignoreCase = true)) {
                    v.rocket.burnProb = v.rocket.burnProb + modifier.rocketProbabilityBonus + (modifier.burnChanceFactorSmall - 1.0)
                }
            }
        }

        ship.components.diveBomber.forEach { (c, v) ->
            if (c.equals(ship.modules[diveBomber], ignoreCase = true)) {
                setPlanes(ship, v, modifier)
                v.maxHealth = ((v.maxHealth + ship.level * modifier.planeHealthPerLevel) * modifier.airplanesDiveBombersHealth * modifier.airplanesHealth).toInt()
                if (HE.equals(v.bomb.ammoType, ignoreCase = true)) {
                    v.bomb.burnProb = v.bomb.burnProb + modifier.bombProbabilityBonus + (modifier.burnChanceFactorBig - 1.0)
                }
            }
        }

        ship.components.torpedoBomber.forEach { (c, v) ->
            if (c.equals(ship.modules[torpedoBomber], ignoreCase = true)) {
                setPlanes(ship, v, modifier)
                v.maxHealth = ((v.maxHealth + ship.level * modifier.planeHealthPerLevel) * modifier.airplanesTorpedoBombersHealth * modifier.airplanesHealth).toInt()
                v.torpedo.maxDist = v.torpedo.maxDist * modifier.planeTorpedoRangeCoefficient
                v.torpedo.speed = v.torpedo.speed + modifier.planeTorpedoSpeedBonus
                v.torpedo.speed = v.torpedo.speed * modifier.airplanesTorpedoSpeedMultiplier
                v.torpedo.uwCritical = v.torpedo.uwCritical + (modifier.floodChanceFactorPlane - 1.0)
            }
        }

        ship.components.airArmament.forEach { (c, v) ->
            if (c.equals(ship.modules[airArmament], ignoreCase = true)) {
                v.deckPlaceCount = (v.deckPlaceCount + modifier.airplanesExtraHangarSize).toInt()
            }
        }

        ship.components.atba.forEach { (c, v) ->
            if (c.equals(ship.modules[atba], ignoreCase = true)) {
//                v.GSIdealRadius = v.GSIdealRadius * modifier.gsidealRadius
                v.maxDist = v.maxDist * modifier.gsMaxDist * modifier.smallGunRangeCoefficient
                v.secondaries.forEach { (_, sec) ->
                    sec.shotDelay = sec.shotDelay * modifier.gsShotDelay * modifier.smallGunReloadCoefficient *
                            (oneCoeff - ship.adrenaline / modifier.hpStep * modifier.timeStep)
//                    sec.GSIdealRadius = sec.GSIdealRadius * modifier.gsidealRadius *
//                            if (ship.level >= 7) modifier.atbaIdealRadiusHi else modifier.atbaIdealRadiusLo
                    sec.idealRadiusModifier = sec.idealRadiusModifier * modifier.gsIdealRadius *
                            if (ship.level >= 7) modifier.atbaIdealRadiusHi else modifier.atbaIdealRadiusLo
                    if (HE.equals(sec.ammoType, ignoreCase = true)) {
                        sec.burnProb = sec.burnProb + modifier.probabilityBonus + modifier.chanceToSetOnFireBonusSmall + (modifier.burnChanceFactorSmall - 1.0)
                        sec.alphaPiercingHE = sec.alphaPiercingHE * modifier.thresholdPenetrationCoefficientSmall
                    }
                }
            }
        }

        ship.components.airDefense.forEach { (c, v) ->
            if (c.equals(ship.modules[airDefense], ignoreCase = true)) {
                v.prioritySectorStrength = v.prioritySectorStrength * modifier.prioritySectorStrengthCoefficient
                v.prioritySectorChangeDelay = v.prioritySectorChangeDelay * modifier.sectorSwitchDelayCoefficient
                v.prioritySectorEnableDelay = v.prioritySectorEnableDelay * modifier.sectorSwitchDelayCoefficient
                v.prioritySectorPreparation = v.prioritySectorPreparation * modifier.prioSectorCooldownCoefficient * modifier.prioritySectorCooldownMultiplier
                v.prioritySectorDuration = v.prioritySectorDuration * modifier.prioSectorCooldownCoefficient
                v.prioritySectorDamageInitial = v.prioritySectorDamageInitial * modifier.prioSectorStartPhaseStrengthCoefficient
            }
        }

        ship.components.hull.forEach { (c, v) ->
            if (c.equals(ship.modules[hull], ignoreCase = true)) {
                v.burnProb = v.burnProb * modifier.burnProb * modifier.probabilityCoefficient
                v.burnTime = v.burnTime * modifier.burnTime * modifier.critTimeCoefficient
                v.burnSizeSkill = if (modifier.probabilityCoefficient != oneCoeff) 3 else v.burnSizeSkill
                v.floodProb = v.floodProb * modifier.floodProb
                v.floodTime = v.floodTime * modifier.floodTime * modifier.critTimeCoefficient
                v.maxSpeed = v.maxSpeed * modifier.speedCoef
                v.rudderTime = v.rudderTime * modifier.sgRudderTime
                v.visibilityFactor = v.visibilityFactor * modifier.visibilityDistCoeff
                v.visibilityFactorByPlane = v.visibilityFactorByPlane * modifier.visibilityDistCoeff
                if (!excludeShipSpecies.contains(ship.typeinfo.species)) {
                    v.visibilityFactor = v.visibilityFactor * modifier.cruiserCoefficient
                    v.visibilityFactorByPlane = v.visibilityFactorByPlane * modifier.cruiserCoefficient
                }
                v.health = v.health + ship.level * modifier.healthPerLevel
            }
        }

        ship.components.engine.forEach { (c, v) ->
            if (c.equals(ship.modules[engine], ignoreCase = true)) {
                v.backwardEngineForsagMaxSpeed = v.backwardEngineForsagMaxSpeed * modifier.engineBackwardForsageMaxSpeed
                v.backwardEngineForsag = v.backwardEngineForsag * modifier.engineBackwardForsagePower
                v.backwardEngineUpTime = v.backwardEngineUpTime * modifier.engineBackwardUpTime
                v.forwardEngineForsagMaxSpeed = v.forwardEngineForsagMaxSpeed * modifier.engineForwardForsageMaxSpeed
                v.forwardEngineUpTime = v.forwardEngineUpTime * modifier.engineForwardUpTime
                v.forwardEngineForsag = v.forwardEngineForsag * modifier.engineForwardForsagePower
            }
        }

        ship.auraFar.forEach { aura -> setAura(aura, modifier) }
        ship.auraFarBubble.forEach { aura -> setAura(aura, modifier) }
        ship.auraMedium.forEach { aura -> setAura(aura, modifier) }
        ship.auraNear.forEach { aura -> setAura(aura, modifier) }
        ship.consumables.forEach { c ->
            c.forEach { s ->
                s.subConsumables.forEach { (_, sC) ->
                    if ("scout".equals(sC.consumableType, ignoreCase = true)) {
                        sC.workTime = sC.workTime * modifier.scoutWorkTime
                    } else if ("crashCrew".equals(sC.consumableType, ignoreCase = true)) {
                        sC.workTime = sC.workTime * modifier.crashCrewWorkTime
                        if ("EmergencyTeamCooldownModifier".equals(modifier.modifier, ignoreCase = true)) {
                            sC.reloadTime = sC.reloadTime * modifier.reloadCoefficient
                        }
                    } else if ("speedBoosters".equals(sC.consumableType, ignoreCase = true)) {
                        sC.workTime = sC.workTime * modifier.speedBoosterWorkTime
                    } else if ("airDefenseDisp".equals(sC.consumableType, ignoreCase = true)) {
                        sC.workTime = sC.workTime * modifier.airDefenseDispWorkTime
                        sC.reloadTime = sC.reloadTime * modifier.airDefenseDispReloadMultiplier
                    } else if ("sonar".equals(sC.consumableType, ignoreCase = true)) {
                        sC.workTime = sC.workTime * modifier.sonarSearchWorkTime
                        if ("TorpedoAlertnessModifier".equals(modifier.modifier, ignoreCase = true)) {
                            sC.distTorpedo = sC.distTorpedo * modifier.rangeCoefficient
                        }
                    } else if ("rls".equals(sC.consumableType, ignoreCase = true)) {
                        sC.workTime = sC.workTime * modifier.rlsSearchWorkTime
                    } else if ("smokeGenerator".equals(sC.consumableType, ignoreCase = true)) {
                        sC.workTime = sC.workTime * modifier.smokeGeneratorWorkTime
                        sC.lifeTime = sC.lifeTime * modifier.smokeGeneratorLifeTime
                        sC.radius = sC.radius * modifier.radiusCoefficient
                    } else if ("regenCrew".equals(sC.consumableType, ignoreCase = true)) {
                        sC.regenerationHPSpeed = sC.regenerationHPSpeed * modifier.regenerationHPSpeed
                    }

                    if ("AllSkillsCooldownModifier".equals(modifier.modifier, ignoreCase = true)) {
                        sC.reloadTime = sC.reloadTime * modifier.reloadCoefficient
                    }

                    if (sC.numConsumables > 0) {
                        sC.numConsumables = sC.numConsumables + modifier.additionalConsumables
                    }

                    sC.reloadTime = sC.reloadTime * modifier.abilReloadTimeFactor
                    sC.workTime = sC.workTime * modifier.shipConsumablesWorkTimeMultiplier
                }
            }
        }
    }

    private fun setAura(aura: Aura?, modifier: CommonModifier)
    {
        if (aura != null) {
            if (aura.innerBubbleCount > 0) {
                aura.innerBubbleCount = aura.innerBubbleCount + modifier.aaExtraBubbles
                aura.bubbleDamage = aura.bubbleDamage * modifier.aaOuterDamage * modifier.advancedOuterAuraDamageCoefficient
            }
            aura.areaDamage = aura.areaDamage * modifier.aaNearDamage * modifier.nearAuraDamageCoefficient
        }
    }

    private fun setPlanes(ship: Ship, plane: Plane, modifier: CommonModifier)
    {
        if (plane.hangarSettings != null) {
            plane.hangarSettings!!.timeToRestore = plane.hangarSettings!!.timeToRestore * modifier.planeSpawnTimeCoefficient * modifier.airplanesSpawnTime
        }
        plane.maxForsageAmount = plane.maxForsageAmount * modifier.forsageDurationCoefficient * modifier.airplanesForsageDuration
        plane.speedMoveWithBomb = plane.speedMoveWithBomb * modifier.flightSpeedCoefficient
        plane.speedMove = plane.speedMove * modifier.flightSpeedCoefficient
        plane.maxVisibilityFactor = plane.maxVisibilityFactor * modifier.squadronCoefficient * modifier.squadronVisibilityDistCoeff
        plane.maxVisibilityFactorByPlane = plane.maxVisibilityFactorByPlane * modifier.squadronCoefficient * modifier.squadronVisibilityDistCoeff
        plane.speedMoveWithBomb = plane.speedMoveWithBomb * modifier.airplanesSpeed * (oneCoeff + ship.adrenaline / modifier.hpStep * modifier.squadronSpeedStep)
        plane.consumables.forEach { c ->
            c.subConsumables.forEach { (_, v) ->
                if ("AllSkillsCooldownModifier".equals(modifier.modifier, ignoreCase = true)) {
                    v.reloadTime = v.reloadTime * modifier.reloadCoefficient
                }
                v.reloadTime = v.reloadTime * modifier.abilReloadTimeFactor
                v.fightersNum = v.fightersNum + if (v.fightersNum > 0) modifier.extraFighterCount else 0.0

                if ("regenerateHealth".equals(v.consumableType, ignoreCase = true)) {
                    v.regenerationRate = v.regenerationRate * modifier.regenerationPlaneRate
                }
                v.workTime = v.workTime * modifier.squadronConsumablesWorkTimeMultiplier
            }
        }
    }
}