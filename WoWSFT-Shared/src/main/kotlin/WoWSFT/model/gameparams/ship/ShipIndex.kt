package WoWSFT.model.gameparams.ship

class ShipIndex {
    constructor() {}
    constructor(
        ship: Ship,
        artyList: List<String>
    ) {
        identifier = ship.name
        index = ship.index
        prevShipIndex = ship.prevShipIndex
        prevShipName = ship.prevShipName
        research = ship.research
        costXP = ship.shipUpgradeInfo.costXP
        prevShipXP = ship.prevShipXP
        prevShipCompXP = ship.prevShipCompXP
        arties = artyList
    }

    var identifier = ""
    var index = ""
    var prevShipIndex = ""
    var prevShipName = ""
    var research = false
    var costXP = 0
    var prevShipXP = 0
    var prevShipCompXP = 0
    var position = 0
    var arties = listOf<String>()
}