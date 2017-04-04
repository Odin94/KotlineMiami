package kotlin_game.Combat

import java.util.Random
import kotlin.collections.ArrayList


class Gun {

    private val bulletCount = 15
    private val spreadDegrees = 10
    private val rnd = Random()

    private val minSpread: Int = -(spreadDegrees / 2)
    private val maxSpread: Int = (spreadDegrees / 2)


    fun shoot(x: Double, y: Double, angle: Double): ArrayList<Projectile> {
        val projectiles = ArrayList<Projectile>()

        for (i in 1..bulletCount) {
            val projectileSpread = rnd.nextInt(maxSpread + 1 - minSpread) + minSpread
            projectiles.add(Projectile(x, y, (angle + projectileSpread)))
        }

        return projectiles
    }
}