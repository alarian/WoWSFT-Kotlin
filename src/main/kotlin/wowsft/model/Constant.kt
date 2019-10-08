package wowsft.model

import java.math.BigDecimal
import java.util.*

object Constant
{
    @JvmStatic val distCoefWG = BigDecimal(100) / BigDecimal(3)
    const val smallGun: Double = 0.139

    const val oneCoeff = 1
    const val IDS = "IDS_"
    const val MODIFIER = "PARAMS_MODIFIER_"
    const val CONSUME = "DOCK_CONSUME_"
    const val TITLE = "TITLE_"
    const val DESCRIPTION = "DESCRIPTION_"
    const val DESC = "DESC_"
    const val TYPE_SHIP = "ships"
    const val TYPE_SHIP_LIST = "shipsList"
    const val TYPE_WARSHIP = "warship"
    const val TYPE_MODULE = "modules"
    const val TYPE_UPGRADE = "upgrades"
    const val TYPE_CONSUMABLE = "consumables"
    const val TYPE_COMMANDER = "commanders"
    const val TYPE_SKILL = "skills"
    const val TYPE_SHELL = "shells"
    const val TYPE_FLAG = "flags"
    const val flightControl = "flightControl"
    const val artillery = "artillery"
    const val hull = "hull"
    const val torpedoes = "torpedoes"
    const val suo = "suo"
    const val fighter = "fighter"
    const val torpedoBomber = "torpedoBomber"
    const val diveBomber = "diveBomber"
    const val engine = "engine"
    const val airArmament = "airArmament"
    const val airDefense = "airDefense"
    const val atba = "atba"
    const val fireControl = "fireControl"
    const val maxBitsToInt = 3766517952L
    const val GENERAL_INTERNAL_ERROR = "GENERAL_INTERNAL_ERROR"
    const val jsonParser = "jsonParser"
    const val nameToId = "nameToId"
    const val idToName = "idToName"
    const val notification = "notification"
    const val translation = "translation"
    const val global = "global"
    const val gameParamsHM = "gameParamsHM"
    const val loadFinish = "loadFinish"
    const val Ability = "Ability"

    @JvmStatic val componentsList = ArrayList(listOf(flightControl, artillery, hull, torpedoes, suo, fighter, torpedoBomber, diveBomber, engine))
    @JvmStatic val compStatsList = ArrayList(listOf(flightControl, airArmament, airDefense, artillery, atba, hull, suo, torpedoes, fighter, torpedoBomber, diveBomber, engine))

    @JvmStatic val globalLanguage = HashSet(listOf("en", "ko"))

    @JvmStatic val excludeShipGroups = HashSet(listOf("unavailable", "disabled", "preserved", "clan"))
    @JvmStatic val supertestShipGroups = HashSet(listOf("demoWithoutStats"))
    @JvmStatic val researchShipGroups = HashSet(listOf("upgradeable", "start"))
    @JvmStatic val premiumShipGroups = HashSet(listOf("special", "specialUnsellable", "upgradeableExclusive", "ultimate", "earlyAccess"))
    @JvmStatic val excludeShipNations = HashSet(listOf("Events", "disabled", "preserved", "clan"))
    @JvmStatic val excludeShipSpecies = HashSet(listOf("Auxiliary", "Submarine"))
    @JvmStatic val excludeCompStats = HashSet(listOf("directors", "finders", "radars"))
    @JvmStatic val excludeModernization = HashSet(listOf("extra", "aimingtime"))

    @JvmStatic val coeff = HashSet(listOf("coef", "maxdist", "idealradius"))
    @JvmStatic val noUnit = HashSet(listOf("num"))
    @JvmStatic val extra = HashSet(listOf("count", "level", "additional"))
    @JvmStatic val meter = HashSet(listOf("radius", "height", "dist"))
    @JvmStatic val rate = HashSet(listOf("regeneration", "probabilitybonus", "chance"))
    @JvmStatic val rateNoSym = HashSet(listOf("step"))
    @JvmStatic val multiple = HashSet(listOf("multiplier"))
    @JvmStatic val extraAngle = HashSet(listOf("gunbonus"))
    @JvmStatic val angle = HashSet(listOf("angle"))
    @JvmStatic val time = HashSet(listOf("time"))
    @JvmStatic val speed = HashSet(listOf("speedbonus"))
}