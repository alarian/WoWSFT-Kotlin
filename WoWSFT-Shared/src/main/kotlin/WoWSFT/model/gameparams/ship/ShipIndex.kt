package WoWSFT.model.gameparams.ship

class ShipIndex
{
    constructor() {}
    constructor(
        identifier: String,
        index: String,
        prevShipIndex: String,
        prevShipName: String,
        research: Boolean,
        costXP: Int,
        prevShipXP: Int,
        prevShipCompXP: Int,
        arties: MutableList<String>
    ) {
        this.identifier = identifier
        this.index = index
        this.prevShipIndex = prevShipIndex
        this.prevShipName = prevShipName
        this.research = research
        this.costXP = costXP
        this.prevShipXP = prevShipXP
        this.prevShipCompXP = prevShipCompXP
        this.arties = arties
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
    var arties = mutableListOf<String>()
}