package wowsft.model.gameparams.commander;

import wowsft.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewPersonality
{
    private boolean dismissable;
    private boolean hasManyNations;
    private boolean hasOverlay;
    private boolean hasRank;
    @JsonAlias("isPerson")
    private boolean person;
    @JsonAlias("isRetrainable")
    private boolean retrainable;
    @JsonAlias("isUnique")
    private boolean unique;
    private boolean canResetSkillsForFree;
    private String peculiarity;
    private String personName;
    private Ships ships;
    private int costCR;
    private int costELXP;
    private int costGold;
    private int costXP;
}
