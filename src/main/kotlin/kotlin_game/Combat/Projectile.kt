package kotlin_game.Combat

import kotlin_game.SCREEN_HEIGHT
import kotlin_game.SCREEN_WIDTH


class Projectile(var x: Double, var y: Double, val angle: Double) {

    private val speed = 20.0

    val w = 12
    val h = 12

    private val xVel = speed * Math.cos(Math.toRadians(angle))
    private val yVel = speed * Math.sin(Math.toRadians(angle))

    fun update() {
        x += xVel
        y += yVel
    }

    fun isOffscreen(): Boolean {
        return x > SCREEN_WIDTH || x + w < 0 || y > SCREEN_HEIGHT || y + h < 0
    }
}