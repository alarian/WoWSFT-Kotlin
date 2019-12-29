package WoWSFT.model.gameparams.commander

import WoWSFT.config.WoWSFT

@WoWSFT
class Ships
{
    var nation = listOf<String>()
    var peculiarity = listOf<String>()
    var ships = listOf<String>()
    var groups = listOf<String>()
}