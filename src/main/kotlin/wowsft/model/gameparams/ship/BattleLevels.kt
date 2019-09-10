package wowsft.model.gameparams.ship;

import wowsft.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class BattleLevels
{
    @JsonProperty("COOPERATIVE")
    private List<Integer> cooperative;
    @JsonProperty("PVP")
    private List<Integer> pvp;
}
