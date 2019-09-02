package wowsft.model.gameparams.flag;

import wowsft.config.WoWSFT;
import wowsft.model.gameparams.CommonModifier;
import wowsft.model.gameparams.TypeInfo;

import java.util.LinkedHashMap;
import java.util.List;

@WoWSFT
public class Flag extends CommonModifier
{
    private boolean canBuy;
    private Object canBuyCustom;
    private List<String> flags;
    private int group;
    private boolean hidden;
    private Object hiddenCustom;
    private long id;
    private String index;
    private String name;
    private TypeInfo typeinfo;
    private int sortOrder;
    private String identifier;
    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();
    private String description = "";

    public String getImage()
    {
        return "https://cdn.wowsft.com/images/signal_flags/" + name + ".png";
    }
}
