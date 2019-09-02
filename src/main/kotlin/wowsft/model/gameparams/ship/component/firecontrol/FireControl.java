package wowsft.model.gameparams.ship.component.firecontrol;

import wowsft.config.WoWSFT;
import lombok.Data;

@Data
@WoWSFT
public class FireControl
{
    private float maxDistCoef;
    private float sigmaCountCoef;
}
