package wowsft.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.ClassPathResource
import wowsft.model.Constant
import java.io.IOException
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import kotlin.math.pow
import kotlin.math.roundToInt

object CommonUtils
{
    private val mapper = ObjectMapper()

    fun getDistCoefWG(number: Number): Double
    {
        return (number.toDouble() / Constant.distCoefWG.toDouble() * 1000.0).roundToInt() / 1000.0
    }

    fun getBonusCoef(number: Number): Double
    {
        return ((number.toDouble() * 1000.0).roundToInt() - 1000.0) / 10.0
    }

    fun getBonus(number: Number): Double
    {
        return (number.toDouble() * 1000.0).roundToInt() / 10.0
    }

    fun replaceZero(number: String): String
    {
        return if (number.endsWith(".0")) number.substring(0, number.length - 2) else number
    }

    fun getNumSym(number: Number): String
    {
        return (if (number.toDouble() >= 0) "+" else "") + replaceZero(number.toString())
    }

    fun getDecimalRounded(num: Double, digits: Int): Double
    {
        val rounder: Double = 10.0.pow(digits)
        return (num * rounder).roundToInt() / rounder
    }

    @Throws(IOException::class)
    fun gameParamsDir(): String
    {
        var directory: String = ClassPathResource("/json/live/GameParams.zip").url.path.replaceFirst(Constant.SLASH, "")
        if (directory.startsWith("var") || directory.startsWith("Users")) {
            directory = "${Constant.SLASH}${directory}"
        }
        return directory
    }

    @Throws(IOException::class)
    fun zFetch(zf: ZipFile, index: String, obj: Class<*>?): Any?
    {
        val zipEntry: ZipEntry? = zf.getEntry("${index}${Constant.FILE_JSON}")
        return if (zipEntry != null) mapper.readValue(zf.getInputStream(zipEntry), obj) else null
    }

    fun getBonus(copy: LinkedHashMap<String, Any>): LinkedHashMap<String, String>
    {
        val bonus = LinkedHashMap<String, String>()
        copy.forEach { (param: String, cVal: Any) ->
            if (Constant.speed.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = "${getNumSym(cVal as Double)} kts"
            } else if (param.toLowerCase().contains("boostcoeff")) {
                if (cVal as Double >= 2.0) bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = getNumSym(cVal)
                else bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = "${getNumSym(getBonus(cVal))} %"
            } else if (Constant.rate.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = "${getNumSym(getBonus(cVal as Double))} %"
            } else if (Constant.multiple.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = "X ${replaceZero(cVal.toString())}"
            } else if (Constant.coeff.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = "${getNumSym(getBonusCoef(cVal as Double))} %"
            } else if (Constant.noUnit.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = if (cVal as Double > 0) replaceZero(cVal.toString()) else "∞"
            } else if (Constant.meter.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = "${getDistCoefWG(cVal as Double)} km"
            } else if (Constant.rateNoSym.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = "${replaceZero(cVal.toString())} %"
            } else if (Constant.time.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = "${replaceZero(cVal.toString())} s"
            } else if (Constant.extraAngle.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = "${getNumSym(cVal as Double)} °"
            } else if (Constant.angle.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = "${replaceZero(cVal.toString())} °"
            } else if (Constant.extra.stream().anyMatch { param.toLowerCase().contains(it) }) {
                bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = getNumSym(cVal as Double)
            } else if (param.toLowerCase().equals("affectedClasses", true)) {
                val tempList = mapper.convertValue(cVal, object : TypeReference<List<String>?>() {})
                if (!tempList.isNullOrEmpty()) {
                    var affected = ""
                    for (tl in tempList) {
                        affected = "${affected}${Constant.IDS_}${tl.toUpperCase()} "
                    }
                    bonus["${Constant.MODIFIER}${param.toUpperCase()}"] = affected.trim()
                }
            }
        }
        return bonus
    }
}