package WoWSFT.model.gameparams.ship

import WoWSFT.config.WoWSFT
import WoWSFT.model.Constant.CDN_IMAGE
import WoWSFT.model.gameparams.TypeInfo
import WoWSFT.model.gameparams.commander.Commander
import WoWSFT.model.gameparams.consumable.Consumable
import WoWSFT.model.gameparams.modernization.Modernization
import WoWSFT.model.gameparams.ship.abilities.AbilitySlot
import WoWSFT.model.gameparams.ship.component.ShipComponent
import WoWSFT.model.gameparams.ship.component.airdefense.Aura
import WoWSFT.model.gameparams.ship.component.artillery.Turret
import WoWSFT.model.gameparams.ship.component.torpedo.Launcher
import WoWSFT.model.gameparams.ship.upgrades.ShipUpgradeInfo
import com.fasterxml.jackson.annotation.*
import java.util.*

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Ship
{
    val tempComponents = LinkedHashMap<String, Any>()
    val components = ShipComponent()
    @JsonAlias("ShipAbilities")
    val shipAbilities = LinkedHashMap<String, AbilitySlot>()
    @JsonAlias("ShipUpgradeInfo")
    val shipUpgradeInfo = ShipUpgradeInfo()
    val apDamageLimitCoeff = 0.0
    val battleLevels = BattleLevels()
    val canEquipCamouflage = false
    val defaultCrew = ""
    val group = ""
    val id: Long = 0
    val index = ""
    @JsonAlias("isPaperShip")
    val paperShip = false
    val level = 0
    val maxEquippedFlags = 0
    val name = ""
    val navalFlag = ""
    val needShowProjectYear = false
    val peculiarity = ""
    val steerAngle = listOf<Double>()
    val typeinfo = TypeInfo()
    val weight = 0
    var realShipType = ""
        set(value) {
            field = value
            realShipTypeId = if ("Premium".equals(value, ignoreCase = true)) "FILTER_PREMIUM" else value.toUpperCase()
        }
    var realShipTypeId = ""
    val fullName = ""
    val research = false
    var prevShipIndex = ""
    var prevShipName = ""
    var prevShipXP = 0
    var prevShipCompXP = 0
    val typeImage get() = if (typeinfo.species.isNotEmpty() && realShipType.isNotEmpty()) "$CDN_IMAGE/vehicles/ship_classes/icon_${typeinfo.species.toLowerCase()}_standard.png" else ""
    val imageSmall get() = if (index.isNotEmpty()) "$CDN_IMAGE/vehicles/ship_previews/$index.png" else ""
    val planes = LinkedHashMap<String, String>()
    val consumables = mutableListOf<MutableList<Consumable>>()
    val upgrades = mutableListOf<MutableList<Modernization>>()
    val upgradesRow = 0
    @JsonIgnore
    val selectConsumables = mutableListOf<Int>()
    @JsonIgnore
    val selectUpgrades = mutableListOf<Int>()
    @JsonIgnore
    val selectSkills = mutableListOf<Int>()
    @JsonIgnore
    val selectSkillPts = 0
    val modules = LinkedHashMap<String, String>()
    val positions = LinkedHashMap<String, Int>()
    val commander = Commander()
    val turrets = mutableListOf<Turret>()
    val launchers = mutableListOf<Launcher>()
    val auraFar = mutableListOf<Aura>()
    val auraFarBubble = mutableListOf<Aura>()
    val auraMedium = mutableListOf<Aura>()
    val auraNear = mutableListOf<Aura>()
    val adrenaline = 0.0
    val arUse = false

    @JsonAnySetter
    fun setUpTempComponents(name: String, value: Any) {
        tempComponents[name] = value
    }
}