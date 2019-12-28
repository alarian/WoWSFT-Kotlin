package WoWSFT.model.gameparams.ship.component

import WoWSFT.config.WoWSFT
import WoWSFT.model.gameparams.ship.component.airarmament.AirArmament
import WoWSFT.model.gameparams.ship.component.airdefense.AirDefense
import WoWSFT.model.gameparams.ship.component.artillery.Artillery
import WoWSFT.model.gameparams.ship.component.atba.ATBA
import WoWSFT.model.gameparams.ship.component.engine.Engine
import WoWSFT.model.gameparams.ship.component.firecontrol.FireControl
import WoWSFT.model.gameparams.ship.component.flightcontrol.FlightControl
import WoWSFT.model.gameparams.ship.component.hull.Hull
import WoWSFT.model.gameparams.ship.component.planes.Plane
import WoWSFT.model.gameparams.ship.component.torpedo.Torpedo
import java.util.*

@WoWSFT
class ShipComponent
{
    val flightControl = LinkedHashMap<String, FlightControl>()
    val airArmament = LinkedHashMap<String, AirArmament>()
    val airDefense = LinkedHashMap<String, AirDefense>()
    val artillery = LinkedHashMap<String, Artillery>()
    val atba = LinkedHashMap<String, ATBA>()
    val hull = LinkedHashMap<String, Hull>()
    val suo = LinkedHashMap<String, FireControl>()
    val torpedoes = LinkedHashMap<String, Torpedo>()
    val fighter = LinkedHashMap<String, Plane>()
    val torpedoBomber = LinkedHashMap<String, Plane>()
    val diveBomber = LinkedHashMap<String, Plane>()
    val engine = LinkedHashMap<String, Engine>()
}