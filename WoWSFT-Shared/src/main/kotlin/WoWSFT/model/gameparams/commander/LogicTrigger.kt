package WoWSFT.model.gameparams.commander

import WoWSFT.model.gameparams.CommonModifier

class LogicTrigger {
    var modifiers = CommonModifier()
    var duration = 0.0
    var triggerDescIds = ""
    var triggerType: String? = null
    var burnCount = 0
    var changePriorityTargetPenalty = 0.0
    var consumableType = ""
    var coolingDelay = 0.0
    var coolingInterpolator: List<List<Int>> = listOf()
    var dividerType =  ""
    var dividerValue = 0.0
    var energyCoeff= 0.0
    var floodCount = 0
    var heatInterpolator: List<List<Int>> = listOf()
}