package WoWSFT.utils

import WoWSFT.model.Constant.*
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import kotlin.math.pow
import kotlin.math.roundToInt

object CommonUtils
{
    private val mapper = ObjectMapper()

    fun getDistCoefWG(number: Number): Double
    {
        return getDecimalRounded(number.toDouble() / distCoefWG.toDouble(), 3)
    }

    fun getBonusCoef(number: Number): Double
    {
        return getDecimalRounded(number.toDouble() * 100.0, 3) - 100.0
    }

    fun getBonus(number: Number): Double
    {
        return getDecimalRounded(number.toDouble() * 100.0, 3)
    }

    fun replaceZero(number: String): String
    {
        return if (number.endsWith(".0")) number.substring(0, number.length - 2) else number
    }

    fun getNumSym(number: Number): String
    {
        return (if (number.toFloat() >= 0) "+" else "") + replaceZero(number.toFloat().toString())
    }

    fun getDecimalRounded(num: Double, digits: Int): Double
    {
        val rounder: Double = 10.0.pow(digits)
        return (num * rounder).roundToInt() / rounder
    }

    @Throws(IOException::class)
    fun zFetch(zf: ZipFile, index: String, obj: Class<*>?): Any?
    {
        val zipEntry: ZipEntry? = zf.getEntry("${index}${FILE_JSON}")
        return if (zipEntry != null) mapper.readValue(zf.getInputStream(zipEntry), obj) else null
    }

    fun getBonus(copy: LinkedHashMap<String, Any>): LinkedHashMap<String, String>
    {
        val bonus = LinkedHashMap<String, String>()
        copy.forEach { (param: String, cVal: Any) ->
            if (speed.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getNumSym(cVal as Double)} kts"
            } else if (param.toLowerCase().contains("boostcoeff")) {
                if (cVal as Double >= 2.0) bonus["${MODIFIER}${param.toUpperCase()}"] = getNumSym(cVal)
                else bonus["${MODIFIER}${param.toUpperCase()}"] = "${getNumSym(getBonus(cVal))} %"
            } else if (rate.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getNumSym(getBonus(cVal as Double))} %"
            } else if (multiple.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "X ${replaceZero(cVal.toString())}"
            } else if (coeff.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getNumSym(getBonusCoef(cVal as Double))} %"
            } else if (noUnit.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = if (cVal as Double > 0) replaceZero(cVal.toString()) else "∞"
            } else if (meter.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getDistCoefWG(cVal as Double)} km"
            } else if (rateNoSym.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${replaceZero(cVal.toString())} %"
            } else if (time.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${replaceZero(cVal.toString())} s"
            } else if (extraAngle.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getNumSym(cVal as Double)} °"
            } else if (angle.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${replaceZero(cVal.toString())} °"
            } else if (extra.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = getNumSym(cVal as Number)
            } else if (param.toLowerCase().equals("affectedClasses", true)) {
                val tempList = mapper.convertValue(cVal, object : TypeReference<List<String>?>() {})
                if (!tempList.isNullOrEmpty()) {
                    var affected = ""
                    for (tl in tempList) {
                        affected = "${affected}${IDS_}${tl.toUpperCase()} "
                    }
                    bonus["${MODIFIER}${param.toUpperCase()}"] = affected.trim()
                }
            }
        }
        return bonus
    }
}