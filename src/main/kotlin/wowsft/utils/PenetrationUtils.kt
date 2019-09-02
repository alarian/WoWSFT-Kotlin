package wowsft.utils

import wowsft.model.gameparams.ship.component.artillery.Shell
import java.util.ArrayList
import java.util.LinkedHashMap
import kotlin.math.round

class PenetrationUtils {
    // SHELL CONSTANTS
    private val a = 9.80665f // GRAVITY
    private val T_0 = 288f // TEMPERATURE AT SEA LEVEL
    private val L = 0.0065f // TEMPERATURE LAPSE RATE
    private val p_0 = 101325f // PRESSURE AT SEA LEVEL
    private val R = 8.31447f // UNIV GAS CONSTANT
    private val M = 0.0289644f // MOLAR MASS OF AIR

    fun setPenetration(ArtyShell: Shell, maxVertAngle: Float, minDistV: Float, maxDist: Float, apShell: Boolean) {
        // SHELL CONSTANTS
        var C = 0.5561613f // PENETRATION
        val W = ArtyShell.bulletMass // SHELL WEIGHT
        val D = ArtyShell.bulletDiametr // SHELL DIAMETER
        val c_D = ArtyShell.bulletAirDrag  // SHELL DRAG
        val V_0 = ArtyShell.bulletSpeed // SHELL MUZZLE VELOCITY
        val K = ArtyShell.bulletKrupp // SHELL KRUPP
        val ricochet =
            (ArtyShell.bulletAlwaysRicochetAt + ArtyShell.bulletCapNormalizeMaxAngle) * Math.PI.toFloat() / 360f * 2f // Ignores after ricochet

        val cw_1 = 1f // QUADRATIC DRAG COEFFICIENT
        val cw_2 = 100f + 1000f / 3f * D // LINEAR DRAG COEFFICIENT

        C = C * K / 2400f // KRUPP INCLUSION
        val k =
            0.5f * c_D * Math.pow((D / 2f).toDouble(), 2.0).toFloat() * Math.PI.toFloat() / W // CONSTANTS TERMS OF DRAG

        val alpha = linspace(Math.PI.toFloat() * maxVertAngle / 360f * 2f) // ELEV. ANGLES 0...MAX
        val dt = 0.1f // TIME STEP

        val penetration = LinkedHashMap<String, Float>()
        val flightTime = LinkedHashMap<String, Float>()
        val impactAngle = LinkedHashMap<String, Float>()
        //        LinkedHashMap<String, Float> launchAngle = new LinkedHashMap<>();
        val distanceList = ArrayList<String>()

        var maxDistCalc = 0f
        var prevMax = 0f

        // for each alpha angle do:
        for (i in alpha.indices) {
            var v_x = Math.cos(alpha[i].toDouble()).toFloat() * V_0
            var v_y = Math.sin(alpha[i].toDouble()).toFloat() * V_0

            var y = 0f
            var x = 0f
            var t = 0f

            var tX_1 = 0f
            var tX_2 = 0f
            var tY_1 = 0f
            var tY_2 = 0f

            var tempNext = true

            while (tempNext) { // follow flight path until shell hits ground again
                tempNext = y >= 0f

                if (tempNext) {
                    tX_1 = x
                    tY_1 = y
                } else {
                    tX_2 = x
                    tY_2 = y
                }

                x = x + dt * v_x
                y = y + dt * v_y

                val T = T_0 - L * y
                val p = p_0 * Math.pow((1 - L * y / T_0).toDouble(), (a * M / (R * L)).toDouble()).toFloat()
                val rho = p * M / (R * T)

                v_x = v_x - dt * k * rho * (cw_1 * Math.pow(v_x.toDouble(), 2.0).toFloat() + cw_2 * v_x)
                v_y = v_y - dt * a - dt * k * rho * (cw_1 * Math.pow(
                    v_y.toDouble(),
                    2.0
                ).toFloat() + cw_2 * Math.abs(v_y)) * Math.signum(v_y)

                t = t + dt
            }

            val v_total = Math.pow(Math.pow(v_y.toDouble(), 2.0) + Math.pow(v_x.toDouble(), 2.0), 0.5).toFloat()
            val p_athit = C * Math.pow(v_total.toDouble(), 1.1).toFloat() * Math.pow(
                W.toDouble(),
                0.55
            ).toFloat() / Math.pow((D * 1000f).toDouble(), 0.65).toFloat() // PENETRATION FORMULA
            val IA = Math.atan((Math.abs(v_y) / Math.abs(v_x)).toDouble()).toFloat() // IMPACT ANGLE ON BELT ARMOR

            maxDistCalc = getMidAtY(tX_1, tY_1, tX_2, tY_2, 0f)

            if (IA > ricochet || prevMax > maxDistCalc || maxDistCalc > maxDist * 1.5f || maxDistCalc > 25000f) {
                break
            }
            prevMax = maxDistCalc

            flightTime[maxDistCalc.toString()] = t / 3f

            if (apShell) {
                penetration[maxDistCalc.toString()] = Math.cos(IA.toDouble()).toFloat() * p_athit
                impactAngle[maxDistCalc.toString()] = IA * 180f / Math.PI.toFloat()
                distanceList.add(maxDistCalc.toString())
                //                launchAngle.put(String.valueOf(maxDistCalc), alpha.get(i));
            }
        }

        if (apShell) {
            ArtyShell.setShell(flightTime, penetration, impactAngle, distanceList, null, minDistV, true)
        } else {
            ArtyShell.setShell(flightTime, null, null, null, null, minDistV, false)
        }
    }

    fun getMidAtY(x1: Float, y1: Float, x2: Float, y2: Float, yAxis: Float): Float {
        val a = (y2 - y1) / (x2 - x1)
        val c = y1 - a * x1

        return (yAxis - c) / a
    }

    private fun linspace(end: Float): List<Float> {
        val alpha = ArrayList<Float>()
        val begin = 0
        val incrementalBig = 0.00375

        alpha.add(begin.toFloat())
        while (begin <= end) {
            alpha.add(round((begin.toFloat() + incrementalBig.toFloat()) * 100000) / 100000)
        }

        return alpha
    }
}