package wowsft.utils

import wowsft.model.gameparams.ship.component.artillery.Shell
import java.util.ArrayList
import java.util.LinkedHashMap
import kotlin.math.*

class PenetrationUtils
{
    companion object {
        // SHELL CONSTANTS
        private val a = 9.80665 // GRAVITY
        private val T_0 = 288.toDouble() // TEMPERATURE AT SEA LEVEL
        private val L = 0.0065 // TEMPERATURE LAPSE RATE
        private val p_0 = 101325 // PRESSURE AT SEA LEVEL
        private val R = 8.31447 // UNIV GAS CONSTANT
        private val M = 0.0289644 // MOLAR MASS OF AIR

        @JvmStatic
        fun setPenetration(ArtyShell: Shell, maxVertAngle: Double, minDistV: Double, maxDist: Double, apShell: Boolean)
        {
            // SHELL CONSTANTS
            var C = 0.5561613 // PENETRATION
            val W = ArtyShell.bulletMass // SHELL WEIGHT
            val D = ArtyShell.bulletDiametr // SHELL DIAMETER
            val c_D = ArtyShell.bulletAirDrag  // SHELL DRAG
            val V_0 = ArtyShell.bulletSpeed // SHELL MUZZLE VELOCITY
            val K = ArtyShell.bulletKrupp // SHELL KRUPP
            val ricochet = (ArtyShell.bulletAlwaysRicochetAt + ArtyShell.bulletCapNormalizeMaxAngle) * PI / 360.toDouble() * 2.toDouble() // Ignores after ricochet

            val cw_1: Double = 1.toDouble() // QUADRATIC DRAG COEFFICIENT
            val cw_2 = 100.toDouble() + 1000.toDouble() / 3.toDouble() * D // LINEAR DRAG COEFFICIENT

            C = C * K / 2400.toDouble() // KRUPP INCLUSION
            val k = 0.5 * c_D * (D / 2.toDouble()).pow(2.toDouble()) * PI / W // CONSTANTS TERMS OF DRAG

            val alpha = linspace(PI * maxVertAngle / 360.toDouble() * 2.toDouble()) // ELEV. ANGLES 0...MAX
            val dt = 0.1 // TIME STEP

            val penetration = LinkedHashMap<String, Double>()
            val flightTime = LinkedHashMap<String, Double>()
            val impactAngle = LinkedHashMap<String, Double>()
//        LinkedHashMap<String, Float> launchAngle = new LinkedHashMap<>();
            val distanceList = ArrayList<String>()

            var maxDistCalc: Double
            var prevMax = 0.toDouble()

            // for each alpha angle do:
            for (i in alpha.indices) {
                var v_x = cos(alpha[i]) * V_0
                var v_y = sin(alpha[i]) * V_0

                var y = 0.toDouble()
                var x = 0.toDouble()
                var t = 0.toDouble()

                var tX_1 = 0.toDouble()
                var tX_2 = 0.toDouble()
                var tY_1 = 0.toDouble()
                var tY_2 = 0.toDouble()

                var tempNext = true

                while (tempNext) { // follow flight path until shell hits ground again
                    tempNext = y >= 0.toDouble()

                    if (tempNext) {
                        tX_1 = x
                        tY_1 = y
                    } else {
                        tX_2 = x
                        tY_2 = y
                    }

                    x += dt * v_x
                    y += dt * v_y

                    val T = T_0 - L * y
                    val p = p_0 * (1 - L * y / T_0).pow(a * M / (R * L))
                    val rho = p * M / (R * T)

                    v_x -= dt * k * rho * (cw_1 * v_x.pow(2.toDouble()) + cw_2 * v_x)
                    v_y -= - dt * a - dt * k * rho * (cw_1 * v_y.pow(2.toDouble()) + cw_2 * abs(v_y)) * sign(v_y)

                    t += dt
                }

                val v_total = (v_y.pow(2.toDouble()) + v_x.pow(2.toDouble())).pow(0.5)
                val p_athit = C * v_total.pow(1.1) * W.pow(0.55) / (D * 1000.toDouble()).pow(0.65) // PENETRATION FORMULA
                val IA = atan((abs(v_y) / abs(v_x))) // IMPACT ANGLE ON BELT ARMOR

                maxDistCalc = getMidAtY(tX_1, tY_1, tX_2, tY_2, 0.toDouble())

                if (IA > ricochet || prevMax > maxDistCalc || maxDistCalc > maxDist * 1.5 || maxDistCalc > 25000.toDouble()) {
                    break
                }
                prevMax = maxDistCalc

                flightTime[maxDistCalc.toString()] = t / 3.toDouble()

                if (apShell) {
                    penetration[maxDistCalc.toString()] = cos(IA) * p_athit
                    impactAngle[maxDistCalc.toString()] = IA * 180.toDouble() / PI
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

        fun getMidAtY(x1: Double, y1: Double, x2: Double, y2: Double, yAxis: Double): Double
        {
            val a = (y2 - y1) / (x2 - x1)
            val c = y1 - a * x1

            return (yAxis - c) / a
        }

        private fun linspace(end: Double): List<Double>
        {
            val alpha = ArrayList<Double>()
            val begin = 0.toDouble()
            val incrementalBig = 0.00375

            alpha.add(begin)
            while (begin <= end) {
                alpha.add(round((begin + incrementalBig) * 100000.toDouble()) / 100000.toDouble())
            }

            return alpha
        }
    }
}