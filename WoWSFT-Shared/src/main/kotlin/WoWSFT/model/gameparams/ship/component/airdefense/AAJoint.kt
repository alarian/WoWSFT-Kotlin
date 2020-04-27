package WoWSFT.model.gameparams.ship.component.airdefense

data class AAJoint(
    val auraFar: MutableList<Aura> = mutableListOf(),
    val auraMedium: MutableList<Aura> = mutableListOf(),
    val auraNear: MutableList<Aura> = mutableListOf()
)