package kotlin_game.Combat

import kotlin_game.SCREEN_HEIGHT
import kotlin_game.SCREEN_WIDTH


open class Projectile(var x: Double, var y: Double, val angle: Double, val damage: Int = 20) {

    open protected val speed = 20.0

    // by lazy allows speed to be effectively overridden
    private val xVel by lazy { speed * Math.cos(Math.toRadians(angle)) }
    private val yVel by lazy { speed * Math.sin(Math.toRadians(angle)) }

    var active = true

    var damage_modifier = 1.0

    open val w = 12
    open val h = 12

    var hitSomething = false

    open fun update() {
        x += xVel
        y += yVel
    }

    fun isOffscreen(): Boolean {
        return x > SCREEN_WIDTH || x + w < 0 || y > SCREEN_HEIGHT || y + h < 0
    }
}

class ShotgunPellet(x: Double, y: Double, angle: Double, damage: Int = 20) : Projectile(x, y, angle, damage) {
    private val original_size = 16
    override var w = original_size
    override var h = original_size

    override val speed = 30.0

    val shrink_interval = 1
    var shrink_acc = 0

    override fun update() {
        super.update()
        handle_bullet_shrinking()

        damage_modifier = w / original_size.toDouble()

        if (w <= 3) {
            active = false
        }
    }

    private fun handle_bullet_shrinking() {
        shrink_acc++
        if (shrink_acc == shrink_interval) {
            w -= 1
            h -= 1

            shrink_acc = 0
        }
    }
}