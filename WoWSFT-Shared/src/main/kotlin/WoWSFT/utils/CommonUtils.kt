package WoWSFT.utils

import WoWSFT.model.Constant.*
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.util.zip.ZipFile
import kotlin.math.pow
import kotlin.math.roundToInt

object CommonUtils {
    val mapper = jacksonObjectMapper()

    fun getDistCoefWG(number: Number): Double = getDecimalRounded(number.toDouble() / distCoefWG.toDouble(), 3)

    fun getBonusCoef(number: Number): Double = getDecimalRounded(number.toDouble() * 100.0, 3) - 100.0

    fun getBonus(number: Number): Double = getDecimalRounded(number.toDouble() * 100.0, 3)

    fun replaceZero(number: String): String = if (number.endsWith(".0")) number.substring(0, number.length - 2) else number

    fun getNumSym(number: Number): String = (if (number.toFloat() >= 0) "+" else "") + replaceZero(number.toFloat().toString())

    fun getDecimalRounded(num: Double, digits: Int): Double = 10.0.pow(digits).also { (num * it).roundToInt() / it }

    inline fun <reified R> zFetch(zf: ZipFile, index: String, typeRef: TypeReference<R> = jacksonTypeRef()): R? =
        zf.getEntry("${index}${FILE_JSON}")?.let { mapper.readValue(zf.getInputStream(it), typeRef) }

    fun getBonus(copy: LinkedHashMap<String, Any>): LinkedHashMap<String, String> {
        return LinkedHashMap<String, String>().also {
            copy.forEach { (param: String, cVal: Any) ->
                it["${MODIFIER}${param.toUpperCase()}"] =
                    if (speed.stream().anyMatch { param.toLowerCase().contains(it) }) { "${getNumSym(cVal as Double)} kts" }
                    else if (param.toLowerCase().contains("boostcoeff")) { if (cVal as Double >= 2.0) getNumSym(cVal) else "${getNumSym(getBonus(cVal))} %" }
                    else if (rate.stream().anyMatch { param.toLowerCase().contains(it) }) { "${getNumSym(getBonus(cVal as Double))} %" }
                    else if (multiple.stream().anyMatch { param.toLowerCase().contains(it) }) { "X ${replaceZero(cVal.toString())}" }
                    else if (coeff.stream().anyMatch { param.toLowerCase().contains(it) }) { "${getNumSym(getBonusCoef(cVal as Double))} %" }
                    else if (noUnit.stream().anyMatch { param.toLowerCase().contains(it) }) { if (cVal as Double > 0) replaceZero(cVal.toString()) else "∞" }
                    else if (meter.stream().anyMatch { param.toLowerCase().contains(it) }) { "${getDistCoefWG(cVal as Double)} km" }
                    else if (rateNoSym.stream().anyMatch { param.toLowerCase().contains(it) }) { "${replaceZero(cVal.toString())} %" }
                    else if (time.stream().anyMatch { param.toLowerCase().contains(it) }) { "${replaceZero(cVal.toString())} s" }
                    else if (extraAngle.stream().anyMatch { param.toLowerCase().contains(it) }) { "${getNumSym(cVal as Double)} °" }
                    else if (angle.stream().anyMatch { param.toLowerCase().contains(it) }) { "${replaceZero(cVal.toString())} °" }
                    else if (extra.stream().anyMatch { param.toLowerCase().contains(it) }) { getNumSym(cVal as Number) }
                    else if (param.toLowerCase().equals("affectedClasses", true)) {
                        mapper.convertValue(cVal, jacksonTypeRef<List<String>?>())?.let {
                            var affected = ""
                            for (tl in it) { affected = "${affected}${IDS_}${tl.toUpperCase()} " }
                            affected.trim()
                        } ?: ""
                    } else ""
            }
        }
    }
}