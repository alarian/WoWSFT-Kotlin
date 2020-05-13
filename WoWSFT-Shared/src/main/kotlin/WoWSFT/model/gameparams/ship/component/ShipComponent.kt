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
import WoWSFT.model.gameparams.ship.component.hydrophone.Hydrophone
import WoWSFT.model.gameparams.ship.component.planes.Plane
import WoWSFT.model.gameparams.ship.component.torpedo.Torpedo
import java.util.*

@WoWSFT
class ShipComponent
{
    var flightControl = LinkedHashMap<String, FlightControl>()
    var airArmament = LinkedHashMap<String, AirArmament>()
    var airDefense = LinkedHashMap<String, AirDefense>()
    var artillery = LinkedHashMap<String, Artillery>()
    var atba = LinkedHashMap<String, ATBA>()
    var hull = LinkedHashMap<String, Hull>()
    var suo = LinkedHashMap<String, FireControl>()
    var torpedoes = LinkedHashMap<String, Torpedo>()
    var fighter = LinkedHashMap<String, Plane>()
    var torpedoBomber = LinkedHashMap<String, Plane>()
    var diveBomber = LinkedHashMap<String, Plane>()
    var engine = LinkedHashMap<String, Engine>()
    var hydrophone = LinkedHashMap<String, Hydrophone>()
}