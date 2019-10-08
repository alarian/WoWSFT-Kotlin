package wowsft.utils

import wowsft.model.Constant
import kotlin.math.round

class CommonUtils
{
    companion object {
        @JvmStatic
        fun getDistCoefWG(number: Number): Double = round(number.toDouble() / Constant.distCoefWG.toDouble() * 100.toDouble()) / 100.toDouble()
        @JvmStatic
        fun getBonusCoef(number: Number): Double = (round(number.toDouble() * 1000.toDouble()) - 1000.toDouble()) / 10.toDouble()
        @JvmStatic
        fun getBonus(number: Number): Double = round(number.toDouble() * 1000.toDouble()) / 10.toDouble()
        @JvmStatic
        fun replaceZero(number: String): String = if (number.endsWith(".0")) number.substring(0, number.length - 2) else number
        @JvmStatic
        fun getNumSym(number: Number): String = (if (number.toDouble() >= 0) "+" else "") + replaceZero(number.toString())
    }
}