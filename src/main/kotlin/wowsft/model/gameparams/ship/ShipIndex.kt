package wowsft.model.gameparams.ship

class ShipIndex (
    val identifier: String,
    val index: String,
    val prevShipIndex: String,
    val prevShipName: String,
    val research: Boolean,
    val costXP: Int,
    val prevShipXP: Int,
    val prevShipCompXP: Int,
    val arties: List<String>
) {
    var position: Int = 0
}
