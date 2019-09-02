package wowsft.model

import java.math.BigDecimal
import java.util.*

object Constant {
    val distCoefWG = BigDecimal(100) / BigDecimal(3)
    val smallGun = BigDecimal(0.139)
    val oneCoeff = 1

    val IDS = "IDS_"
    val MODIFIER = "PARAMS_MODIFIER_"
    val CONSUME = "DOCK_CONSUME_"
    val TITLE = "TITLE_"
    val DESCRIPTION = "DESCRIPTION_"
    val DESC = "DESC_"

    val TYPE_SHIP = "ships"
    val TYPE_SHIP_LIST = "shipsList"
    val TYPE_WARSHIP = "warship"
    val TYPE_MODULE = "modules"
    val TYPE_UPGRADE = "upgrades"
    val TYPE_CONSUMABLE = "consumables"
    val TYPE_COMMANDER = "commanders"
    val TYPE_SKILL = "skills"
    val TYPE_SHELL = "shells"
    val TYPE_FLAG = "flags"

    val flightControl = "flightControl"
    val artillery = "artillery"
    val hull = "hull"
    val torpedoes = "torpedoes"
    val suo = "suo"
    val fighter = "fighter"
    val torpedoBomber = "torpedoBomber"
    val diveBomber = "diveBomber"
    val engine = "engine"

    val airArmament = "airArmament"
    val airDefense = "airDefense"
    val atba = "atba"
    val fireControl = "fireControl"

    val componentsList: List<String> = ArrayList(
        listOf(
            flightControl,
            artillery,
            hull,
            torpedoes,
            suo,
            fighter,
            torpedoBomber,
            diveBomber,
            engine
        )
    )
    val compStatsList: List<String> = ArrayList(
        listOf(
            flightControl,
            airArmament,
            airDefense,
            artillery,
            atba,
            hull,
            suo,
            torpedoes,
            fighter,
            torpedoBomber,
            diveBomber,
            engine
        )
    )

    val globalLanguage = HashSet(listOf("en", "ko"))

    val excludeShipGroups = HashSet(listOf("unavailable", "disabled", "preserved", "clan"))
    val supertestShipGroups = HashSet(listOf("demoWithoutStats"))
    val researchShipGroups = HashSet(listOf("upgradeable", "start"))
    val premiumShipGroups = HashSet(listOf("special", "specialUnsellable", "upgradeableExclusive", "ultimate", "earlyAccess"))
    val excludeShipNations = HashSet(listOf("Events", "disabled", "preserved", "clan"))
    val excludeShipSpecies = HashSet(listOf("Auxiliary", "Submarine"))
    val excludeCompStats = HashSet(listOf("directors", "finders", "radars"))
    val excludeModernization = HashSet(listOf("extra", "aimingtime"))

    val coeff = HashSet(listOf("coef", "maxdist", "idealradius"))
    val noUnit = HashSet(listOf("num"))
    val extra = HashSet(listOf("count", "level", "additional"))
    val meter = HashSet(listOf("radius", "height", "dist"))
    val rate = HashSet(listOf("regeneration", "probabilitybonus", "chance"))
    val rateNoSym = HashSet(listOf("step"))
    val multiple = HashSet(listOf("multiplier"))
    val extraAngle = HashSet(listOf("gunbonus"))
    val angle = HashSet(listOf("angle"))
    val time = HashSet(listOf("time"))
    val speed = HashSet(listOf("speedbonus"))

    val maxBitsToInt = 3766517952L

    val GENERAL_INTERNAL_ERROR = "GENERAL_INTERNAL_ERROR"
}