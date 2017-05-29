package kotlin_game.Particles

import kotlin_game.approachZero
import kotlin_game.collide
import kotlin_game.rnd
import kotlin_game.walls

data class CorpseParticle(var x: Double, var y: Double, val max_age: Int = 30, val permanent: Boolean = false) {
    val maxSpeed = 60
    val minSpeed = -maxSpeed

    var r: Double = rnd.nextInt(5 + 1 - 2) + 2.0

    var xVel = rnd.nextInt(maxSpeed + 1 - minSpeed) + minSpeed.toDouble()
    var yVel = rnd.nextInt(maxSpeed + 1 - minSpeed) + minSpeed.toDouble()

    var moving = true

    var age = 0
    var active = true

    fun update() {
        if (moving) {
            x += xVel
            y += yVel

            xVel = approachZero(xVel, rnd.nextDouble() * 4)
            yVel = approachZero(yVel, rnd.nextDouble() * 4)

            for (wall in walls) {
                if (collide(wall, this)) {
                    moving = false
                    break
                }
            }
        }

        age++
        if (age > max_age && !permanent) active = false
    }
}