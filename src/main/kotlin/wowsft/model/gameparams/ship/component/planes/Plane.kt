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
    var bombName = ""
    var forsageRegeneration = 0f
    var fuelTime = 0f
    var hangarSettings = HangarSetting()
    var id = 0L
    var index = ""
    var level = 0
    var maxForsageAmount = 0f
    var maxHealth = 0
    var maxVisibilityFactor = 0f
    var maxVisibilityFactorByPlane = 0f
    var minVisibilityFactor = 0f
    var minVisibilityFactorByPlane = 0f
    var name = ""
    var numPlanesInSquadron = 0
    var speedMax = 0f
    var speedMin = 0f
    var speedMove = 0f
    var speedMoveWithBomb = 0f
    var typeinfo = TypeInfo()
    var rocket = Shell()
    var bomb = Shell()
    var torpedo = TorpedoAmmo()
    var consumables = ArrayList<Consumable>()
}
