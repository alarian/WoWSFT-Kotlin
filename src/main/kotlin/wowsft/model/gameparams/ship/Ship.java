package wowsft.model.gameparams.ship;

import com.fasterxml.jackson.annotation.*;
import wowsft.config.WoWSFT;
import wowsft.model.gameparams.TypeInfo;
import wowsft.model.gameparams.ship.abilities.AbilitySlot;
import wowsft.model.gameparams.ship.component.ShipComponent;
import wowsft.model.gameparams.ship.component.airdefense.Aura;
import wowsft.model.gameparams.ship.component.artillery.Turret;
import wowsft.model.gameparams.ship.component.torpedo.Launcher;
import wowsft.model.gameparams.ship.upgrades.ShipUpgradeInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ship
{
    private LinkedHashMap<String, Object> tempComponents = new LinkedHashMap<>();
    private ShipComponent components = new ShipComponent();

    @JsonAlias("ShipAbilities")
    private LinkedHashMap<String, AbilitySlot> shipAbilities;
    @JsonAlias("ShipUpgradeInfo")
    private ShipUpgradeInfo shipUpgradeInfo;

    private float apDamageLimitCoeff;
    private BattleLevels battleLevels;
    private boolean canEquipCamouflage;
    private String defaultCrew;
    private String group;
    private long id;
    private String index;
    @JsonAlias("isPaperShip")
    private boolean paperShip;
    private int level;
    private int maxEquippedFlags;
    private String name;
    private String navalFlag;
    private boolean needShowProjectYear;
    private String peculiarity;
    private List<Float> steerAngle;
    private TypeInfo typeinfo;
    private int weight;

    private String realShipType;
    private String realShipTypeId;
    private String fullName;
    private boolean research;
    private String prevShipIndex;
    private String prevShipName;
    private int prevShipXP;
    private int prevShipCompXP;
    private String typeImage;
    private String imageSmall;

    private LinkedHashMap<String, String> planes = new LinkedHashMap<>();

    private List<List<Consumable>> consumables = new ArrayList<>();
    private List<List<Modernization>> upgrades = new ArrayList<>();
    private int upgradesRow;
    @JsonIgnore
    private List<Integer> selectConsumables = new ArrayList<>();
    @JsonIgnore
    private List<Integer> selectUpgrades = new ArrayList<>();
    @JsonIgnore
    private List<Integer> selectSkills = new ArrayList<>();
    @JsonIgnore
    private int selectSkillPts;
    private LinkedHashMap<String, String> modules = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> positions = new LinkedHashMap<>();

    private Commander commander;

    private List<Turret> turrets;
    private List<Launcher> launchers;

    private List<Aura> auraFar = new ArrayList<>();
    private List<Aura> auraFarBubble = new ArrayList<>();
    private List<Aura> auraMedium = new ArrayList<>();
    private List<Aura> auraNear = new ArrayList<>();

    private float adrenaline;
    private boolean arUse;

    @JsonSetter
    public void setRealShipType(String realShipType)
    {
        if (StringUtils.isNotEmpty(realShipType)) {
            this.realShipType = realShipType;
            this.realShipTypeId = realShipType.toUpperCase();
            if ("Premium".equalsIgnoreCase(realShipType)) {
                this.realShipTypeId = "FILTER_PREMIUM";
            }
        }
    }

    @JsonAnySetter
    public void setUpTempComponents(String name, Object value)
    {
        tempComponents.put(name, value);
    }

    public String getTypeImage()
    {
        if (typeinfo != null && StringUtils.isNotEmpty(typeinfo.getSpecies()) && StringUtils.isNotEmpty(realShipType)) {
            return "https://cdn.wowsft.com/images/vehicles/types/" + typeinfo.getSpecies() + (realShipType.equalsIgnoreCase("Premium") ? "/premium" : "/normal") + ".png";
        }
        return "";
    }

    public String getImageSmall()
    {
        if (StringUtils.isNotEmpty(index)) {
            return "https://cdn.wowsft.com/images/vehicles/ship_previews/" + index + ".png";
        }
        return "";
    }
}
