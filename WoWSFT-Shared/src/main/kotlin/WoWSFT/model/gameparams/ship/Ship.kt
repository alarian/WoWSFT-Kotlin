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
    var tempComponents = LinkedHashMap<String, Any?>()
    var components = ShipComponent()
    @JsonAlias("ShipAbilities")
    var shipAbilities = LinkedHashMap<String, AbilitySlot>()
    @JsonAlias("ShipUpgradeInfo")
    var shipUpgradeInfo = ShipUpgradeInfo()
    var apDamageLimitCoeff = 0.0
    var battleLevels = BattleLevels()
    var canEquipCamouflage = false
    var defaultCrew: String? = null
    var group: String? = null
    var id: Long = 0
    var index = ""
    @JsonAlias("isPaperShip")
    var paperShip = false
    var level = 0
    var maxEquippedFlags = 0
    var name = ""
    var navalFlag = ""
    var needShowProjectYear = false
    var peculiarity = ""
    var steerAngle = listOf<Double>()
    var typeinfo = TypeInfo()
    var weight = 0
    var realShipType = ""
        set(value) {
            field = value
            realShipTypeId = if ("Premium".equals(value, ignoreCase = true)) "FILTER_PREMIUM" else value.toUpperCase()
        }
    var realShipTypeId = ""
    var fullName = ""
    var research = false
    var prevShipIndex = ""
    var prevShipName = ""
    var prevShipXP = 0
    var prevShipCompXP = 0
    val typeImage get() = if (!typeinfo.species.isNullOrBlank() && realShipType.isNotEmpty()) "$CDN_IMAGE/vehicles/ship_classes/icon_${typeinfo.species!!.toLowerCase()}_standard.png" else ""
    val imageSmall get() = if (index.isNotEmpty()) "$CDN_IMAGE/vehicles/ship_previews/$index.png" else ""
    var planes = LinkedHashMap<String, String>()
    var consumables = mutableListOf<MutableList<Consumable>>()
    var upgrades = mutableListOf<MutableList<Modernization>>()
    var upgradesRow = 0
    @JsonIgnore
    var selectConsumables = mutableListOf<Int>()
    @JsonIgnore
    var selectUpgrades = mutableListOf<Int>()
    @JsonIgnore
    var selectSkills = mutableListOf<Int>()
    @JsonIgnore
    var selectSkillPts = 0
    @JsonIgnore
    var selectFlags = mutableListOf<Int>()
    var modules = LinkedHashMap<String, String>()
    var positions = LinkedHashMap<String, Int>()
    var commander: Commander? = null
    var turrets = mutableListOf<Turret>()
    var launchers = mutableListOf<Launcher>()
    var auraFar = mutableListOf<Aura>()
    var auraFarBubble = mutableListOf<Aura>()
    var auraMedium = mutableListOf<Aura>()
    var auraNear = mutableListOf<Aura>()
    var adrenaline = 0.0
    var arUse = false

    @JsonAnySetter
    fun setUpTempComponents(name: String, value: Any?)
    {
        tempComponents[name] = value
    }
}