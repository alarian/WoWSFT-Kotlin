package wowsft.model.gameparams.ship.abilities;

import wowsft.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@WoWSFT
public class AbilitySlot
{
    private List<List<String>> abils;
    @JsonInclude
    private int slot;
}
