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
class Ship {
    var tempComponents = LinkedHashMap<String, Any>()
    var components = ShipComponent()

    @JsonProperty("ShipAbilities")
    var shipAbilities = LinkedHashMap<String, AbilitySlot>()
    @JsonProperty("ShipUpgradeInfo")
    var shipUpgradeInfo = ShipUpgradeInfo()

    var apDamageLimitCoeff = 0f
    var battleLevels = BattleLevels()
    var canEquipCamouflage = false
    var defaultCrew = ""
    var group = ""
    var id = 0L
    var index = ""
    @JsonProperty("isPaperShip")
    var paperShip = false
    var level = 0
    var maxEquippedFlags = 0
    var name = ""
    var navalFlag = ""
    var needShowProjectYear = false
    var peculiarity = ""
    var steerAngle = ArrayList<Float>()
    var typeinfo = TypeInfo()
    var weight = 0

    var realShipType = ""
        set(realShipType) {
            if (realShipType.isNotEmpty()) {
                field = realShipType
                realShipTypeId = if ("Premium".equals(realShipType, true)) "FILTER_PREMIUM" else realShipType.toUpperCase()
            }
        }
    var realShipTypeId = ""
    var fullName = ""
    var research = false
    var prevShipIndex = ""
    var prevShipName = ""
    var prevShipXP = 0
    var prevShipCompXP = 0
    val typeImage = if (typeinfo.nation.isNotEmpty() && typeinfo.species.isNotEmpty() && realShipType.isNotEmpty()) {
            "https://cdn.wowsft.com/images/vehicles/types/" + typeinfo.species + (if (realShipType.equals("Premium", true)) "/premium" else "/normal") + ".png"
        } else ""
    val imageSmall = if (index.isNotEmpty()) "https://cdn.wowsft.com/images/vehicles/ship_previews/$index.png" else ""
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

    var adrenaline = 0f
    var arUse = false

    @JsonAnySetter
    fun setUpTempComponents(name: String, value: Any) {
        tempComponents[name] = value
    }
}
