package WoWSFT.service

import WoWSFT.model.Constant.MODIFIER
import WoWSFT.model.Constant.excludeModernization
import WoWSFT.utils.CommonUtils.getBonusCoef
import WoWSFT.utils.CommonUtils.getNumSym
import org.springframework.stereotype.Service

@Service
class ParamService
{
    fun setBonusParams(key: String, tempCopy: LinkedHashMap<String, Any>, bonus: LinkedHashMap<String, String>)
    {
        tempCopy.forEach { (param, cVal) ->
            if (cVal is Double && cVal != 0.0) {
                bonus["$MODIFIER${param.toUpperCase()}"] =
                    if (excludeModernization.any { param.toLowerCase().contains(it) }) getNumSym(cVal)
                    else "${getNumSym(getBonusCoef(cVal))} %"
            }
        }
    }
}