package wowsft.model.gameparams.ship.component.planes

import wowsft.model.gameparams.TypeInfo
import wowsft.model.gameparams.consumable.Consumable
import wowsft.model.gameparams.ship.abilities.AbilitySlot
import wowsft.model.gameparams.ship.component.artillery.Shell
import wowsft.model.gameparams.ship.component.torpedo.TorpedoAmmo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonAlias
import wowsft.model.gameparams.CommonModifier

import java.util.LinkedHashMap

@JsonIgnoreProperties(ignoreUnknown = true)
class Plane {
    @JsonAlias("PlaneAbilities")
    var planeAbilities = LinkedHashMap<String, AbilitySlot>()

    var attackCount = 0
    var attackerSize = 0
    var bombName: String? = null
    var forsageRegeneration = 0.0
    var fuelTime = 0.0
    var hangarSettings: HangarSetting? = null
    var id: Long = 0
    var index: String? = null
    var level = 0
    var maxForsageAmount = 0.0
    var maxHealth = 0
    var maxVisibilityFactor = 0.0
    var maxVisibilityFactorByPlane = 0.0
    var minVisibilityFactor = 0.0
    var minVisibilityFactorByPlane = 0.0
    var name: String? = null
    var numPlanesInSquadron = 0
    var speedMax = 0.0
    var speedMin = 0.0
    var speedMove = 0.0
    var speedMoveWithBomb = 0.0
    var typeinfo : TypeInfo? = null
    var rocket: Shell? = null
    var bomb: Shell? = null
    var torpedo: TorpedoAmmo? = null
    var consumables = ArrayList<Consumable>()
}
