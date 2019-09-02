package wowsft.model.gameparams.ship.component;

import wowsft.config.WoWSFT;
import wowsft.model.gameparams.ship.component.airarmament.AirArmament;
import wowsft.model.gameparams.ship.component.airdefense.AirDefense;
import wowsft.model.gameparams.ship.component.artillery.Artillery;
import wowsft.model.gameparams.ship.component.atba.ATBA;
import wowsft.model.gameparams.ship.component.engine.Engine;
import wowsft.model.gameparams.ship.component.firecontrol.FireControl;
import wowsft.model.gameparams.ship.component.flightcontrol.FlightControl;
import wowsft.model.gameparams.ship.component.hull.Hull;
import wowsft.model.gameparams.ship.component.planes.Plane;
import wowsft.model.gameparams.ship.component.torpedo.Torpedo;

import java.util.LinkedHashMap;

@WoWSFT
public class ShipComponent
{
    private LinkedHashMap<String, FlightControl> flightControl = new LinkedHashMap<>();
    private LinkedHashMap<String, AirArmament> airArmament = new LinkedHashMap<>();
    private LinkedHashMap<String, AirDefense> airDefense = new LinkedHashMap<>();
    private LinkedHashMap<String, Artillery> artillery = new LinkedHashMap<>();
    private LinkedHashMap<String, ATBA> atba = new LinkedHashMap<>();
    private LinkedHashMap<String, Hull> hull = new LinkedHashMap<>();
    private LinkedHashMap<String, FireControl> suo = new LinkedHashMap<>();
    private LinkedHashMap<String, Torpedo> torpedoes = new LinkedHashMap<>();
    private LinkedHashMap<String, Plane> fighter = new LinkedHashMap<>();
    private LinkedHashMap<String, Plane> torpedoBomber = new LinkedHashMap<>();
    private LinkedHashMap<String, Plane> diveBomber = new LinkedHashMap<>();
    private LinkedHashMap<String, Engine> engine = new LinkedHashMap<>();
}
