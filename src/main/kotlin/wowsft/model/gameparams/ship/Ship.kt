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

    @JsonAlias("ShipAbilities")
    var shipAbilities: LinkedHashMap<String, AbilitySlot>? = null
    @JsonAlias("ShipUpgradeInfo")
    var shipUpgradeInfo: ShipUpgradeInfo? = null

    var apDamageLimitCoeff: Float = 0.toFloat()
    var battleLevels: BattleLevels? = null
    var canEquipCamouflage: Boolean = false
    var defaultCrew: String = ""
    var group: String = ""
    var id: Long = 0
    var index: String = ""
    @JsonAlias("isPaperShip")
    var paperShip: Boolean = false
    var level: Int = 0
    var maxEquippedFlags: Int = 0
    var name: String = ""
    var navalFlag: String = ""
    var needShowProjectYear: Boolean = false
    var peculiarity: String = ""
    var steerAngle: List<Float>? = null
    var typeinfo: TypeInfo? = null
    var weight: Int = 0

    var realShipType: String = ""
        set(realShipType) {
            if (!realShipType.isNullOrEmpty()) {
                field = realShipType
                realShipTypeId = if ("Premium".equals(realShipType, true)) "FILTER_PREMIUM" else realShipType.toUpperCase()
            }
        }
    var realShipTypeId: String = ""
    var fullName: String = ""
    var research: Boolean = false
    var prevShipIndex: String = ""
    var prevShipName: String = ""
    var prevShipXP: Int = 0
    var prevShipCompXP: Int = 0
    val typeImage: String?
        get() = if (typeinfo != null && typeinfo!!.species.isNotEmpty() && realShipType!!.isNotEmpty()) {
            "https://cdn.wowsft.com/images/vehicles/types/" + typeinfo!!.species + (if (realShipType!!.equals("Premium", true)) "/premium" else "/normal") + ".png"
        } else ""
    val imageSmall: String?
        get() = if (index!!.isNotEmpty()) "https://cdn.wowsft.com/images/vehicles/ship_previews/$index.png" else ""
    var planes = LinkedHashMap<String, String>()

    var consumables = ArrayList<List<Consumable>>()
    var upgrades = ArrayList<List<Modernization>>()
    var upgradesRow: Int = 0
    @JsonIgnore
    var selectConsumables = ArrayList<Int>()
    @JsonIgnore
    var selectUpgrades = ArrayList<Int>()
    @JsonIgnore
    var selectSkills = ArrayList<Int>()
    @JsonIgnore
    var selectSkillPts: Int = 0
    var modules = LinkedHashMap<String, String>()
    var positions = LinkedHashMap<String, Int>()

    var commander: Commander? = null

    var turrets: List<Turret>? = null
    var launchers: List<Launcher>? = null

    var auraFar = ArrayList<Aura>()
    var auraFarBubble = ArrayList<Aura>()
    var auraMedium = ArrayList<Aura>()
    var auraNear = ArrayList<Aura>()

    var adrenaline: Float = 0.toFloat()
    var arUse: Boolean = false

    @JsonAnySetter
    fun setUpTempComponents(name: String, value: Any) {
        tempComponents[name] = value
    }
}
