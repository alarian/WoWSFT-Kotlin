package wowsft.utils

import wowsft.model.Constant
import kotlin.math.round

class CommonUtils {
    fun getDistCoefWG(number: Number): Float {
        return round(number.toFloat() / Constant.distCoefWG.toFloat() * 100) / 100
    }

    fun getBonusCoef(number: Number): Float {
        return (round(number.toFloat() * 1000) - 1000) / 10
    }

    fun getBonus(number: Number): Float {
        return round(number.toFloat() * 1000) / 10
    }

    fun replaceZero(number: String): String {
        return if (number.endsWith(".0")) number.substring(0, number.length - 2) else number
    }

    fun getNumSym(number: Number): String {
        return (if (number.toFloat() >= 0) "+" else "") + replaceZero(number.toString())
    }
}