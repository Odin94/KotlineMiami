package kotlin_game

import java.util.*

data class BloodSplatter(val x: Double, val y: Double) {
    private val rnd = Random()
    val imgName: String

    init {
        imgName = "blood${rnd.nextInt(7 + 1 - 1) + 1}"
    }
}