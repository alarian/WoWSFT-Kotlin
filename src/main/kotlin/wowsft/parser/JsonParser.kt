package wowsft.parser

import wowsft.model.gameparams.TypeInfo
import wowsft.model.gameparams.commander.Commander
import wowsft.model.gameparams.consumable.Consumable
import wowsft.model.gameparams.flag.Flag
import wowsft.model.gameparams.modernization.Modernization
import wowsft.model.gameparams.ship.Ship
import wowsft.model.gameparams.ship.ShipIndex
import wowsft.model.gameparams.ship.component.airarmament.AirArmament
import wowsft.model.gameparams.ship.component.airdefense.AirDefense
import wowsft.model.gameparams.ship.component.artillery.Artillery
import wowsft.model.gameparams.ship.component.atba.ATBA
import wowsft.model.gameparams.ship.component.engine.Engine
import wowsft.model.gameparams.ship.component.firecontrol.FireControl
import wowsft.model.gameparams.ship.component.flightcontrol.FlightControl
import wowsft.model.gameparams.ship.component.hull.Hull
import wowsft.model.gameparams.ship.component.torpedo.Torpedo
import wowsft.model.gameparams.ship.upgrades.ShipUpgrade
import wowsft.service.ParamService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.Async
import wowsft.model.Constant

import java.io.IOException
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.ToIntFunction
import java.util.zip.ZipFile

open class JsonParser {
    @Qualifier(Constant.notification)
    @Autowired private lateinit var notification: LinkedHashMap<String, LinkedHashMap<String, String>>

    @Qualifier(Constant.translation)
    @Autowired private lateinit var translation: LinkedHashMap<String, LinkedHashMap<String, String>>

    @Qualifier(Constant.nameToId)
    @Autowired private lateinit var nameToId: HashMap<String, String>

    @Qualifier(Constant.idToName)
    @Autowired private lateinit var idToName: HashMap<String, String>

    @Qualifier(Constant.gameParamsHM)
    @Autowired private lateinit var gameParamsHM: HashMap<String, Any>

    @Qualifier(Constant.global)
    @Autowired private lateinit var global: HashMap<String, HashMap<String, Any>>

    @Qualifier(Constant.TYPE_SHIP)
    @Autowired private lateinit var ships: LinkedHashMap<String, Ship>

    @Qualifier(Constant.TYPE_CONSUMABLE)
    @Autowired private lateinit var consumables: LinkedHashMap<String, Consumable>

    @Qualifier(Constant.TYPE_SHIP_LIST)
    @Autowired private lateinit var shipsList: LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>

    @Qualifier(Constant.TYPE_UPGRADE)
    @Autowired private lateinit var upgrades: LinkedHashMap<Int, LinkedHashMap<String, Modernization>>

    @Qualifier(Constant.TYPE_COMMANDER)
    @Autowired private lateinit var commanders: LinkedHashMap<String, Commander>

    @Qualifier(Constant.TYPE_FLAG)
    @Autowired private lateinit var flags: LinkedHashMap<String, Flag>

    @Autowired private lateinit var paramService: ParamService

    companion object {
        private val log = LoggerFactory.getLogger(JsonParser::class.java)
    }

    private val mapper = ObjectMapper()

    @Async
    @Throws(IOException::class)
    open fun setTranslation() {
        log.info("Setting up translation")

        for (language in Constant.globalLanguage) {
            val notificationFile = ClassPathResource("/json/translation/custom-trans-$language.json")
            val temp = mapper.readValue<LinkedHashMap<String, String>>(notificationFile.url, object : TypeReference<LinkedHashMap<String, String>>() {})
            translation[language] = temp
        }
    }

    @Async
    @Throws(IOException::class)
    open fun setNotification() {
        log.info("Setting up notification")

        for (language in Constant.globalLanguage) {
            val notificationFile = ClassPathResource("/json/notification/notification-$language.json")
            val temp = mapper.readValue<LinkedHashMap<String, String>>(notificationFile.url, object : TypeReference<LinkedHashMap<String, String>>() {})
            notification[language] = temp
        }
    }

    @Throws(IOException::class)
    fun setGlobal() {
        log.info("Setting up Global")

        for (language in Constant.globalLanguage) {
            val globalFile = ClassPathResource("/json/live/global-$language.json")
            val temp = mapper.readValue<HashMap<String, Any>>(globalFile.inputStream, object : TypeReference<HashMap<String, Any>>() {})
            global[language] = temp
        }
    }

    //    @Async
    @Throws(IOException::class)
    fun setGameParams() {
        log.info("Setting up GameParams")

        val zf = ZipFile(ClassPathResource("/json/live/GameParams.zip").file.path)
        val temp = mapper.readValue<LinkedHashMap<String, LinkedHashMap<String, Any>>>(zf.getInputStream(zf.entries().nextElement()),
            object : TypeReference<LinkedHashMap<String, LinkedHashMap<String, Any>>>() {})

        for (i in 0..5) {
            upgrades[i] = LinkedHashMap()
        }

        temp.forEach { (key, value) ->
            val typeInfo = mapper.convertValue(value["typeinfo"], TypeInfo::class.java)

            if (typeInfo.type.equals("Ship", true) && !Constant.excludeShipNations.contains(typeInfo.nation) && !Constant.excludeShipSpecies.contains(typeInfo.species)) {
                val ship = mapper.convertValue(value, Ship::class.java)
                if (!Constant.excludeShipGroups.contains(ship.group) && ship.defaultCrew.isEmpty()) {
                    ship.shipUpgradeInfo!!.components.forEach { (cType, c) ->
                        c.forEach { su ->
                            for (s in Constant.excludeCompStats) {
                                su.components.remove(s)
                            }
                            su.elem = Constant.componentsList.indexOf(cType)
                        }
                    }
                    addShips(ship)
                }
            } else if (typeInfo.type.equals("Modernization", true)) {
                val modernization = mapper.convertValue(value, Modernization::class.java)
                if (modernization.slot >= 0) {
                    paramService.setBonusParams(key, mapper.convertValue(modernization, object : TypeReference<LinkedHashMap<String, Any>>() {}), modernization.bonus)
                    upgrades[modernization.slot]?.set(modernization.name, modernization)
                }
            } else if (typeInfo.type.equals(Constant.Ability, true) && !Constant.excludeShipNations.contains(typeInfo.nation) && !key.contains("Super")) {
                val consumable = mapper.convertValue(value, Consumable::class.java)
                consumables[key] = consumable
            } else if (typeInfo.type.equals("Crew", true)) {
                val commander = mapper.convertValue(value, Commander::class.java)
                if (!"Events".equals(commander.typeinfo?.nation, true)) {
                    if (!commander.crewPersonality!!.unique && commander.typeinfo?.nation.equals("Common")) {
                        commander.identifier = "IDS_CREW_LASTNAME_DEFAULT"
                        commanders[commander.index.toUpperCase()] = commander
                    } else if (commander.crewPersonality!!.unique) {
                        commander.identifier = Constant.IDS + commander.crewPersonality!!.personName.toUpperCase()
                        commanders[commander.index.toUpperCase()] = commander
                    }
                }
            } else if (typeInfo.type.equals("Exterior", true) && typeInfo.species.equals("Flags", true)) {
                val flag = mapper.convertValue(value, Flag::class.java)
                if (flag.group == 0) {
                    flag.identifier = Constant.IDS + flag.name.toUpperCase()
                    paramService.setBonusParams(key, mapper.convertValue(flag, object : TypeReference<LinkedHashMap<String, Any>>() {}), flag.bonus)
                    flags[flag.name] = flag
                }
            } else {
                gameParamsHM[key] = value
            }
//            nameToId.put(key, String.valueOf(value.get("id")));
//            idToName.put(String.valueOf(value.get("id")), key);
//            gameParamsHM.put(String.valueOf(value.get("id")), value);
        }

        generateShipsList()
//        sortShipsList();
//        calculateXp();
        sortUpgrades()
        setCommanderParams()
        sortFlags()

        temp.clear()
    }

    private fun addShips(ship: Ship) {
        sortShipUpgradeInfo(ship)
        setRealShipType(ship)
        setRows(ship)

        ships[ship.index] = ship
        idToName[ship.name] = ship.index
        nameToId[global["en"]!![Constant.IDS + ship.index.toUpperCase() + "_FULL"].toString()] = ship.index.toUpperCase()
    }

    private fun sortShipUpgradeInfo(ship: Ship) {
        ship.shipUpgradeInfo!!.components.forEach { (key, value) ->
            value.forEach { upgrade ->
                if (upgrade.position == 3 && ship.shipUpgradeInfo!!.components[key]!!.size < 3) {
                    upgrade.position = 2
                }

                upgrade.components.forEach { (cKey, cValue) ->
                    when {
                        cKey.equals(Constant.artillery, true) -> {
                            cValue.forEach { cVal -> ship.components.artillery[cVal] = mapper.convertValue(ship.tempComponents[cVal], Artillery::class.java) }
                        } cKey.equals(Constant.airDefense, true) -> {
                            cValue.forEach { cVal -> ship.components.airDefense[cVal] = mapper.convertValue(ship.tempComponents[cVal], AirDefense::class.java) }
                        } cKey.equals(Constant.atba, true) -> {
                            cValue.forEach { cVal -> ship.components.atba[cVal] = mapper.convertValue(ship.tempComponents[cVal], ATBA::class.java) }
                        } cKey.equals(Constant.engine, true) -> {
                                cValue.forEach { cVal -> ship.components.engine[cVal] = mapper.convertValue(ship.tempComponents[cVal], Engine::class.java) }
                        } cKey.equals(Constant.suo, true) -> {
                            cValue.forEach { cVal -> ship.components.suo[cVal] = mapper.convertValue(ship.tempComponents[cVal], FireControl::class.java) }
                        } cKey.equals(Constant.hull, true) -> {
                            cValue.forEach { cVal -> ship.components.hull[cVal] = mapper.convertValue(ship.tempComponents[cVal], Hull::class.java) }
                        } cKey.equals(Constant.torpedoes, true) -> {
                            cValue.forEach { cVal -> ship.components.torpedoes[cVal] = mapper.convertValue(ship.tempComponents[cVal], Torpedo::class.java) }
                        } cKey.equals(Constant.airArmament, true) -> {
                            cValue.forEach { cVal -> ship.components.airArmament[cVal] = mapper.convertValue(ship.tempComponents[cVal], AirArmament::class.java) }
                        } cKey.equals(Constant.flightControl, true) -> {
                            cValue.forEach { cVal -> ship.components.flightControl[cVal] = mapper.convertValue(ship.tempComponents[cVal], FlightControl::class.java) }
                        } cKey.equals(Constant.fighter, true) || cKey.equals(Constant.diveBomber, true) || cKey.equals(Constant.torpedoBomber, true) -> {
                            cValue.forEach { cVal ->
                                val tempPlaneType = mapper.convertValue(ship.tempComponents[cVal], object : TypeReference<LinkedHashMap<String, String>>() {}) as LinkedHashMap<String, String>
                                ship.planes[cVal] = tempPlaneType["planeType"] ?: ""
                            }
                        }
                    }
                }
            }
            value.sortWith(Comparator.comparingInt<ShipUpgrade>{ it.position }.thenComparing<String>{ it.name })
        }

        ship.shipUpgradeInfo!!.components.forEach { (_, value) ->
            value.forEach { upgrade ->
                if (upgrade.prev.isNotEmpty()) {
                    for (entry in ship.shipUpgradeInfo!!.components.entries) {
                        val tSU = entry.value.stream().filter{ v -> v.name!!.equals(upgrade.prev, true) }.findFirst().orElse(null)
                        if (tSU != null) {
                            upgrade.prevType = tSU.ucTypeShort
                            upgrade.prevPosition = tSU.position
                            upgrade.prevElem = Constant.componentsList.indexOf(tSU.ucTypeShort)

                            if (upgrade.position == upgrade.prevPosition && upgrade.ucTypeShort.equals(upgrade.prevType, true)) {
                                upgrade.position = upgrade.prevPosition + 1
                            }
                            break
                        }
                    }
                }
            }
        }

        ship.tempComponents = LinkedHashMap()
    }

    private fun setRealShipType(ship: Ship) {
        when {
            Constant.researchShipGroups.contains(ship.group) -> {
                ship.realShipType = ship.typeinfo!!.species
                ship.research = true
            }
            Constant.premiumShipGroups.contains(ship.group) -> ship.realShipType = "Premium"
            Constant.supertestShipGroups.contains(ship.group) -> ship.realShipType = "Test_Sample"
        }
    }

    private fun setRows(ship: Ship) {
        val colCount = LinkedHashMap<String, Int>()

        var maxRows = 0
        for (i in 1..3) {
            val hasRow = AtomicBoolean(false)
            ship.shipUpgradeInfo!!.components.forEach { (key, compList) ->
                colCount.putIfAbsent(key, 0)
                val posCount = compList.stream().filter{ c -> c.position == i }.count()

                if (posCount > 0) {
                    hasRow.set(true)
                }

                if (colCount[key]!! < posCount) {
                    colCount[key] = posCount.toInt()
                }
            }

            if (hasRow.get()) {
                maxRows++
            }
        }
        ship.shipUpgradeInfo!!.setColsAndMaxRows(colCount, maxRows)
    }

    private fun generateShipsList() {
        ships.forEach { (_, ship) ->
            ship.shipUpgradeInfo!!.components.forEach { (_, components) ->
                components.forEach { component ->
                    if (!component.nextShips.isNullOrEmpty()) {
                        component.nextShips.forEach { ns ->
                            if (idToName[ns] != null) {
                                var currentPosition = component.position
                                var current = component.name
                                var prev = component.prev
                                var prevType = component.prevType
                                var compXP = 0
                                while (currentPosition > 1 && prev.isNotEmpty() && prevType.isNotEmpty() && gameParamsHM.containsKey(current)) {
                                    val comp = mapper.convertValue<HashMap<String, Any>>(
                                        gameParamsHM[current],
                                        object : TypeReference<HashMap<String, Any>>() {

                                        })
                                    compXP += comp["costXP"] as Int

                                    if (prev.isNotEmpty()) {
                                        val tempSUList = ship.shipUpgradeInfo!!.components[prevType]
                                        if (tempSUList != null) {
                                            for (su in tempSUList) {
                                                if (su.name.equals(prev, true)) {
                                                    currentPosition = su.position
                                                    prevType = su.prevType
                                                    current = su.name
                                                    prev = su.prev
                                                    break
                                                }
                                            }
                                        }
                                    } else {
                                        break
                                    }
                                }
                                ships[idToName[ns]]?.prevShipIndex = ship.index
                                ships[idToName[ns]]?.prevShipName = ship.name
                                ships[idToName[ns]]?.prevShipXP = ship.shipUpgradeInfo!!.costXP
                                ships[idToName[ns]]?.prevShipCompXP = compXP
                            }
                        }
                    }
                }
            }
        }

        ships.forEach { (_, ship) ->
            shipsList.putIfAbsent(ship.getTypeinfo().getNation(), LinkedHashMap())
            shipsList[ship.getTypeinfo().getNation()].putIfAbsent(ship.getRealShipTypeId().toUpperCase(), LinkedHashMap())
            (shipsList[ship.getTypeinfo().getNation()][ship.getRealShipTypeId().toUpperCase()] as java.util.Map<String, LinkedHashMap<Int, List<ShipIndex>>>).putIfAbsent(
                ship.getTypeinfo().getSpecies().toUpperCase(),
                LinkedHashMap()
            )
            (shipsList[ship.getTypeinfo().getNation()][ship.getRealShipTypeId().toUpperCase()][ship.getTypeinfo().getSpecies().toUpperCase()] as java.util.Map<Int, List<ShipIndex>>).putIfAbsent(
                ship.getLevel(),
                ArrayList()
            )

            val arties = ArrayList<String>()
            ship.getShipUpgradeInfo().components.get(artillery).forEach { arty -> arties.add(arty.getName()) }

            shipsList[ship.getTypeinfo().getNation()][ship.getRealShipTypeId().toUpperCase()][ship.getTypeinfo().getSpecies().toUpperCase()][ship.getLevel()]
                .add(
                    ShipIndex(
                        ship.getName(),
                        ship.getIndex(),
                        ship.getPrevShipIndex(),
                        ship.getPrevShipName(),
                        ship.isResearch(),
                        ship.getShipUpgradeInfo().getCostXP(),
                        ship.getPrevShipXP(),
                        ship.getPrevShipCompXP(),
                        arties
                    )
                )
        }

        shipsList!!.entries.stream()
            .sorted(Map.Entry.comparingByKey<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>())
            .forEach { nation ->
                nation.value.entries.stream()
                    .sorted(Map.Entry.comparingByKey<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>())
                    .forEach { realShipType ->
                        realShipType.value.forEach { (shipType, tiers) ->
                            tiers.entries.stream().sorted(Map.Entry.comparingByKey<Int, List<ShipIndex>>())
                                .forEach { tier ->
                                    tier.value.sort(Comparator.comparing<ShipIndex, String>(Function<ShipIndex, String> { it.getIndex() }))
                                    shipsList[nation.key][realShipType.key][shipType].remove(tier.key)
                                    shipsList[nation.key][realShipType.key][shipType][tier.key] = tier.value
                                }

                            var tier = 10
                            while (tier > 0) {
                                if (CollectionUtils.isNotEmpty(tiers[tier])) {
                                    tiers[tier]
                                        .sort(Comparator.comparing<ShipIndex, String>(Function<ShipIndex, String> { it.getIndex() }))

                                    val cTier = tier
                                    val pos = AtomicInteger(1)
                                    tiers[tier].forEach { ship ->
                                        if (ship.isResearch()) {
                                            if (ship.position == 0) {
                                                ship.position = pos.getAndIncrement()
                                            }

                                            if (CollectionUtils.isNotEmpty(
                                                    shipsList[nation.key][realShipType.key][shipType][cTier - 1]
                                                )
                                            ) {
                                                shipsList[nation.key][realShipType.key][shipType][cTier - 1]
                                                    .forEach { tShip ->
                                                        if (ships[tShip.index].getTypeinfo().getSpecies().equalsIgnoreCase(
                                                                shipType
                                                            )
                                                        ) {
                                                            ships[tShip.index].getShipUpgradeInfo().components
                                                                .forEach({ comp, list ->
                                                                    list.forEach { u1 ->
                                                                        if (u1.getNextShips().contains(ship.identifier)) {
                                                                            if (list.stream().filter{ u2 ->
                                                                                    CollectionUtils.isNotEmpty(
                                                                                        u2.getNextShips()
                                                                                    )
                                                                                }.count() == 1) {
                                                                                tShip.position = ship.position
                                                                            }
                                                                        }
                                                                    }
                                                                })
                                                        }
                                                    }
                                            }
                                        }
                                    }
                                    tiers[tier]
                                        .sort(Comparator.comparingInt<ShipIndex>(ToIntFunction<ShipIndex> { it.getPosition() }))
                                }
                                tier--
                            }
                        }
                        if (realShipType.key.equals("FILTER_PREMIUM", true)) {
                            shipsList[nation.key].remove(realShipType.key)
                            shipsList[nation.key][realShipType.key] = realShipType.value
                        } else if (realShipType.key.equals("TEST_SAMPLE", true)) {
                            shipsList[nation.key].remove(realShipType.key)
                            shipsList[nation.key][realShipType.key] = realShipType.value
                        }
                    }
                shipsList.remove(nation.key)
                shipsList[nation.key] = nation.value
            }
        val russia = shipsList["Russia"]
        shipsList.remove("Russia")
        shipsList["Russia"] = russia
    }

    private fun sortUpgrades() {
        upgrades.forEach { (slot, mod) ->
            mod.entries.stream().sorted(Map.Entry.comparingByKey<String, Modernization>()).forEach { u ->
                upgrades[slot].remove(u.key)
                upgrades[slot][u.key] = u.value
            }
        }
    }

    private fun sortFlags() {
        flags!!.values.stream().sorted(Comparator.comparingInt(ToIntFunction<Flag> { getSortOrder() }))
            .forEach { flag ->
                flags.remove(flag.getName())
                flags[flag.getName()] = flag
            }
    }

    private fun setCommanderParams() {
        commanders!!.forEach { (key, commander) ->
            commander.cSkills.forEach { r ->
                r.forEach { s ->
                    s.bonus =
                        paramService!!.getBonus(
                            mapper.convertValue(
                                s,
                                object : TypeReference<LinkedHashMap<String, Any>>() {

                                })
                        )
                }
            }
        }
    }
}
