package WoWSFT.utils

import WoWSFT.model.Constant.*
import WoWSFT.model.gameparams.CommonModifier
import WoWSFT.model.gameparams.CommonModifierShip
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import kotlin.math.pow
import kotlin.math.roundToInt

object CommonUtils {
    private val mapper = ObjectMapper()

    fun getDist(number: Number): Double {
        return getDecimalRounded(number.toDouble() / 1000.0, 3)
    }

    fun getDistCoefWG(number: Number): Double {
        return getDecimalRounded(number.toDouble() / distCoefWG.toDouble(), 3)
    }

    fun getBonusCoef(number: Number): Double
    {
        return getDecimalRounded(number.toDouble() * 100.0, 3) - 100.0
    }

    fun getBonusCoeffInverse(number: Number): Double {
        return getDecimalRounded((1.0 + 1.0 - number.toDouble()) * 100.0, 3) - 100.0
    }

    fun getBonusCoeffPercent(number: Number): Double {
        return number.toDouble()
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

    fun getBonus(copy: LinkedHashMap<String, Any>, isConsumable: Boolean = false): LinkedHashMap<String, String>
    {
        val bonus = LinkedHashMap<String, String>()

        copy.forEach { (param: String, cVal: Any) ->
            if (cVal is LinkedHashMap<*, *>) {
                mapper.convertValue(cVal, jacksonTypeRef<CommonModifierShip>()).let { cValConvert ->
                    if (cValConvert.aircraftCarrier != 1.0 && cValConvert.aircraftCarrier != 0.0) {
                        getBonus(linkedMapOf<String, Any>(Pair(param, cValConvert.aircraftCarrier))).forEach { (convertKey, convertVal) ->
                            bonus["${convertKey}_${AIRCARRIER.toUpperCase()}"] = convertVal
                        }
                    }

                    if (cValConvert.battleship != 1.0 && cValConvert.battleship != 0.0) {
                        getBonus(linkedMapOf<String, Any>(Pair(param, cValConvert.battleship))).forEach { (convertKey, convertVal) ->
                            bonus["${convertKey}_${BATTLESHIP.toUpperCase()}"] = convertVal
                        }
                    }

                    if (cValConvert.cruiser != 1.0 && cValConvert.cruiser != 0.0) {
                        getBonus(linkedMapOf<String, Any>(Pair(param, cValConvert.cruiser))).forEach { (convertKey, convertVal) ->
                            bonus["${convertKey}_${CRUISER.toUpperCase()}"] = convertVal
                        }
                    }

                    if (cValConvert.destroyer != 1.0 && cValConvert.destroyer != 0.0) {
                        getBonus(linkedMapOf<String, Any>(Pair(param, cValConvert.destroyer))).forEach { (convertKey, convertVal) ->
                            bonus["${convertKey}_${DESTROYER.toUpperCase()}"] = convertVal
                        }
                    }
                }
            } else if (param.contains("visionXRay")) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getDist(cVal as Double)} km"
            } else if (meter.any { param.contains(it, true) } && isConsumable) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getDistCoefWG(cVal as Double)} km"
            } else if (time.any { param.endsWith(it, true) } || (timeConsumables.any { param.endsWith(it, true) } && isConsumable)) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${replaceZero(cVal.toString())} s"
            } else if (param.startsWith("lastChance")) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${replaceZero(cVal.toString())} %"
            } else if (rate.any { param.contains(it, true) } || (repair.any { param.contains(it, true) } && isConsumable)) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getNumSym(getBonus(cVal as Double))} %"
            } else if (coeffPercent.any { param.contains(it, true) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getNumSym(getBonusCoeffPercent(cVal as Double))} %"
            } else if (coeffInverse.any { param.contains(it, true) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getNumSym(getBonusCoeffInverse(cVal as Double))} %"
            } else if (param.toLowerCase().equals("affectedClasses", true)) {
                mapper.convertValue(cVal, jacksonTypeRef<List<String>?>())
                    .takeIf { it.isNullOrEmpty().not() }
                    ?.let { list ->
                        bonus["${MODIFIER}${param.toUpperCase()}"] = list.joinToString { affected -> "${IDS_}${affected.toUpperCase()} " }.trim()
                    }
            } else if (noUnit.any { param.contains(it, true) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = if (cVal as Double > 0) replaceZero(cVal.toString()) else "∞"
            } else if (cVal is Int || extra.any { param.contains(it, true) }) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = getNumSym(cVal as Number)
            } else if (cVal is Double) {
                bonus["${MODIFIER}${param.toUpperCase()}"] = "${getNumSym(getBonusCoef(cVal))} %"
            }
        }

        return bonus
    }
}