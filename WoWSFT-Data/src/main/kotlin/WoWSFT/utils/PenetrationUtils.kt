package WoWSFT.utils

import WoWSFT.model.gameparams.ship.component.artillery.Shell
import WoWSFT.utils.CommonUtils.getDecimalRounded
import java.util.*
import kotlin.math.*

class PenetrationUtils
{
    // SHELL CONSTANTS
    private val G = 9.80665 // GRAVITY # N / kg
//    private static final double G = 9.8; // Gravity used by WG
    private val T_0 = 288.15 // TEMPERATURE AT SEA LEVEL # K
    private val L = 0.0065 // TEMPERATURE LAPSE RATE # K / m
    private val P_0 = 101325.0 // PRESSURE AT SEA LEVEL # Pa
    private val R = 8.31447 // UNIV GAS CONSTANT # N.m / (mol.K)
    private val M = 0.0289644 // MOLAR MASS OF AIR # kg / mol

    private val intervalDeg = 0.05
    private val dt = 0.03 // TIME STEP

    fun setPenetration(shell: Shell, maxVertAngle: Double, minDistV: Double, maxDist: Double, apShell: Boolean)
    {
        // SHELL CONSTANTS
        var C = 0.5561613 // PENETRATION
        val W = shell.bulletMass // SHELL WEIGHT
        val D = shell.bulletDiametr // SHELL DIAMETER
        val c_D = shell.bulletAirDrag // SHELL DRAG
        val V_0 = shell.bulletSpeed // SHELL MUZZLE VELOCITY
        val K = shell.bulletKrupp // SHELL KRUPP
        val ricochet = (shell.bulletAlwaysRicochetAt + shell.bulletCapNormalizeMaxAngle) * PI / 180.0 // Ignores after ricochet

        val cw_1 = 1.0 // QUADRATIC DRAG COEFFICIENT
        val cw_2 = 100.0 + 1000.0 / 3.0 * D // LINEAR DRAG COEFFICIENT

        C = C * K / 2400.0 // KRUPP INCLUSION
        val k = 0.5 * c_D * Math.pow(D / 2.0, 2.0) * PI / W // CONSTANTS TERMS OF DRAG

//        List<Double> alpha = linspace(PI * maxVertAngle / 360.0 * 2.0); // ELEV. ANGLES 0...MAX
        val alpha = linspace(maxVertAngle) // ELEV Deg. ANGLES 0...MAX
        val dt = 0.03 // TIME STEP

        val penetration = LinkedHashMap<String, Double>()
        val flightTime = LinkedHashMap<String, Double>()
        val impactAngle = LinkedHashMap<String, Double>()
//        LinkedHashMap<String, Double> launchAngle = new LinkedHashMap<>();
        val distanceList = mutableListOf<String>()

        // for each alpha angle do:
        for (i in alpha.indices) {
//            val angle = alpha[i]
            val angle = getDecimalRounded(alpha[i] * PI / 180.0, 9)
            var v_x = cos(angle) * V_0
            var v_y = sin(angle) * V_0

            var y = 0.0
            var x = 0.0
            var t = 0.0

            while (y >= 0) { // follow flight path until shell hits ground again
                x += dt * v_x
                y += dt * v_y

                val T = T_0 - L * y
                val p = P_0 * (1 - L * y / T_0).pow(G * M / (R * L))
                val rho = p * M / (R * T)

                v_x -= dt * k * rho * (cw_1 * v_x.pow(2.0) + cw_2 * v_x)
                v_y = v_y - dt * G - dt * k * rho * (cw_1 * v_y.pow(2.0) + cw_2 * abs(v_y)) * sign(v_y)
                t += dt
            }

            val v_total = (v_y.pow(2.0) + v_x.pow(2.0)).pow(0.5)
            val p_athit = C * v_total.pow(1.1) * W.pow(0.55) / (D * 1000.0).pow(0.65) // PENETRATION FORMULA
            val IA = atan(abs(v_y) / abs(v_x)) // IMPACT ANGLE ON BELT ARMOR
            
            if (IA > ricochet || x > maxDist * 1.25 || x > 25000) {
                break
            }

            x = getDecimalRounded(x, 4)
            val xString = x.toString()

            flightTime[xString] = getDecimalRounded(t / 3.0, 5)

            if (apShell) {
                penetration[xString] = getDecimalRounded(cos(IA) * p_athit, 5)
                impactAngle[xString] = getDecimalRounded(IA * 180.0 / PI, 5)
                distanceList.add(xString)
//                launchAngle.put(xString, alpha.get(i));
            }
        }
        if (apShell) {
            shell.setShell(flightTime, penetration, impactAngle, distanceList, LinkedHashMap(), minDistV, true)
        } else {
            shell.setShell(flightTime, LinkedHashMap(), LinkedHashMap(), mutableListOf(), LinkedHashMap(), minDistV, false)
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
//        var begin = 0.0
        var countDeg = 0.0
        val intervalDeg = 0.05
        val alpha = mutableListOf<Double>()

        while (countDeg <= end) {
//            alpha.add(begin)
            alpha.add(countDeg)
            countDeg = getDecimalRounded(countDeg + intervalDeg, 2)
//            begin = getDecimalRounded(PI * countDeg / 180.0, 9)
        }
        return alpha
    }
}