package wowsft.model.gameparams.ship

import com.fasterxml.jackson.annotation.*
import wowsft.config.WoWSFT
import wowsft.model.gameparams.TypeInfo
import wowsft.model.gameparams.commander.Commander
import wowsft.model.gameparams.consumable.Consumable
import wowsft.model.gameparams.modernization.Modernization
import wowsft.model.gameparams.ship.abilities.AbilitySlot
import wowsft.model.gameparams.ship.component.ShipComponent
import wowsft.model.gameparams.ship.component.airdefense.Aura
import wowsft.model.gameparams.ship.component.artillery.Turret
import wowsft.model.gameparams.ship.component.torpedo.Launcher
import wowsft.model.gameparams.ship.upgrades.ShipUpgradeInfo

import java.util.ArrayList
import java.util.LinkedHashMap

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
class Ship
{
    var tempComponents = LinkedHashMap<String, Any>()
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
    var index: String? = null
    @JsonAlias("isPaperShip")
    var paperShip = false
    var level = 0
    var maxEquippedFlags = 0
    var name: String? = null
    var navalFlag: String? = null
    var needShowProjectYear = false
    var peculiarity: String? = null
    var steerAngle = ArrayList<Double>()
    var typeinfo : TypeInfo? = null
    var weight = 0

    var realShipType: String? = null
        set(realShipType) {
            if (!realShipType.isNullOrBlank()) {
                field = realShipType
                realShipTypeId = if ("Premium".equals(realShipType, ignoreCase = true)) "FILTER_PREMIUM" else realShipType.toUpperCase()
            }
        }
    var realShipTypeId: String? = null
    var fullName: String? = null
    var research = false
    var prevShipIndex: String? = null
    var prevShipName: String? = null
    var prevShipXP = 0
    var prevShipCompXP = 0
    val typeImage: String
        get() = if (!typeinfo?.nation.isNullOrBlank() && !typeinfo?.species.isNullOrBlank() && !realShipType.isNullOrBlank()) {
            "https://cdn.wowsft.com/images/vehicles/types/" + typeinfo?.species + (if (realShipType.equals("Premium", ignoreCase = true)) "/premium" else "/normal") + ".png"
        } else ""
    val imageSmall: String
        get() = if (!index.isNullOrBlank()) "https://cdn.wowsft.com/images/vehicles/ship_previews/$index.png" else ""
    var planes = LinkedHashMap<String, String>()

    var consumables = ArrayList<MutableList<Consumable>>()
    var upgrades = ArrayList<MutableList<Modernization>>()
    var upgradesRow = 0
    @JsonIgnore
    var selectConsumables = ArrayList<Int>()
    @JsonIgnore
    var selectUpgrades = ArrayList<Int>()
    @JsonIgnore
    var selectSkills = ArrayList<Int>()
    @JsonIgnore
    var selectSkillPts = 0
    var modules = LinkedHashMap<String, String>()
    var positions = LinkedHashMap<String, Int>()

    var commander = Commander()

    var turrets = ArrayList<Turret>()
    var launchers = ArrayList<Launcher>()

    var auraFar = ArrayList<Aura>()
    var auraFarBubble = ArrayList<Aura>()
    var auraMedium = ArrayList<Aura>()
    var auraNear = ArrayList<Aura>()

    var adrenaline = 0.0
    var arUse = false
    var steerDist = 0.0

    @JsonAnySetter
    fun setUpTempComponents(name: String, value: Any) {
        tempComponents[name] = value
    }
}
