package wowsft.model.gameparams.ship.component.planes

import wowsft.model.gameparams.TypeInfo
import wowsft.model.gameparams.consumable.Consumable
import wowsft.model.gameparams.ship.abilities.AbilitySlot
import wowsft.model.gameparams.ship.component.artillery.Shell
import wowsft.model.gameparams.ship.component.torpedo.TorpedoAmmo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import wowsft.model.gameparams.CommonModifier

import java.util.LinkedHashMap

@JsonIgnoreProperties(ignoreUnknown = true)
class Plane {
    @JsonProperty("PlaneAbilities")
    var planeAbilities = LinkedHashMap<String, AbilitySlot>()

    var attackCount = 0
    var attackerSize = 0
    var bombName: String = ""
    var forsageRegeneration = 0.toDouble()
    var fuelTime = 0.toDouble()
    var hangarSettings = HangarSetting()
    var id: Long = 0
    var index: String = ""
    var level = 0
    var maxForsageAmount = 0.toDouble()
    var maxHealth = 0
    var maxVisibilityFactor = 0.toDouble()
    var maxVisibilityFactorByPlane = 0.toDouble()
    var minVisibilityFactor = 0.toDouble()
    var minVisibilityFactorByPlane = 0.toDouble()
    var name: String = ""
    var numPlanesInSquadron = 0
    var speedMax = 0.toDouble()
    var speedMin = 0.toDouble()
    var speedMove = 0.toDouble()
    var speedMoveWithBomb = 0.toDouble()
    var typeinfo = TypeInfo()
    var rocket = Shell()
    var bomb = Shell()
    var torpedo = TorpedoAmmo()
    var consumables = ArrayList<Consumable>()
}
