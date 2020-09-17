package WoWSFT.model.gameparams.commander

import WoWSFT.config.WoWSFT

@WoWSFT
class Ships {
    var nation = mutableListOf<String>()
    var peculiarity = mutableListOf<String>()
    var ships = mutableListOf<String>()
    var groups = mutableListOf<String>()
}