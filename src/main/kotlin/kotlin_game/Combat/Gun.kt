package kotlin_game.Combat

import kotlin_game.rnd


open class Gun {

    open var bulletCount = 1

    open fun shoot(x: Double, y: Double, angle: Double): ArrayList<Projectile> {
        val projectiles = ArrayList<Projectile>()

        for (i in 1..bulletCount) {
            projectiles.add(Projectile(x, y, angle))
        }

        return projectiles
    }
}

class Shotgun : Gun() {
    override var bulletCount = 15
    private val spreadDegrees = 20

    private val minSpread: Int = -(spreadDegrees / 2)
    private val maxSpread: Int = (spreadDegrees / 2)

    override fun shoot(x: Double, y: Double, angle: Double): ArrayList<Projectile> {
        val projectiles = ArrayList<Projectile>()

        for (i in 1..bulletCount) {
            val projectileSpread = rnd.nextInt(maxSpread + 1 - minSpread) + minSpread
            projectiles.add(ShotgunPellet(x, y, (angle + projectileSpread)))
        }

        return projectiles
    }
}