package kotlin_game

import kotlin_game.Combat.Projectile
import kotlin_game.Combat.Shotgun
import java.lang.Math.sqrt


data class Actor(var x: Double, var y: Double, var w: Double, var h: Double,
                 var centerX: Double = x + w / 2, var centerY: Double = y + h / 2,
                 var xAccel: Double = 0.0, var yAccel: Double = 0.0,
                 var xVel: Double = 0.0, var yVel: Double = 0.0,
                 var maxXVel: Double = 10.0, var maxYVel: Double = 10.0,
                 var xSlowDown: Double = 1.0, var ySlowDown: Double = 1.0,
                 var angle: Double = 0.0, var tarAngle: Double = 0.0,
                 var health: Int = 100) {

    private val rotationSpeed = 10

    var weapon = Shotgun()
    var angleInDegrees = Math.toDegrees(angle)

    fun shoot() {
        //TODO: make projectiles spawn on the weapon's tip instead of player's center
        val newProjectiles: ArrayList<Projectile> = weapon.shoot(centerX, centerY, angleInDegrees - 90)
        projectiles = (projectiles + newProjectiles) as ArrayList<Projectile>
    }

    fun takeDamage(proj: Projectile) {
        if (rnd.nextInt(2) == 1) bloodSplatters.add(BloodSplatter(proj.x, proj.y, "smallBlood"))
        health -= (proj.damage * proj.damage_modifier).toInt()
    }

    fun onDeath() {
        bloodSplatters.add(BloodSplatter(centerX, centerY))
        corpses.add(Corpse(x, y))
    }

    fun updateAngle(mouseX: Int, mouseY: Int) {

        tarAngle = Math.atan2(centerY - mouseY, centerX - mouseX) - Math.PI / 2
        tarAngle = toNormalizedDegrees(tarAngle)

        angle = toNormalizedDegrees(angle)

        if (angle < tarAngle) {
            if (Math.abs(angle - tarAngle) < 180) angle += rotationSpeed else angle -= rotationSpeed
        } else {
            if (Math.abs(angle - tarAngle) < 180) angle -= rotationSpeed else angle += rotationSpeed
        }

        if (Math.abs(angle - tarAngle) < rotationSpeed * 2) angle = tarAngle

        angleInDegrees = angle
        angle = Math.toRadians(angle)
    }

    fun addAccel() {
        slowDown()

        xVel += xAccel
        if (xVel > maxXVel) xVel = maxXVel
        if (xVel < -maxXVel) xVel = -maxXVel

        yVel += yAccel
        if (yVel > maxYVel) yVel = maxYVel
        if (yVel < -maxYVel) yVel = -maxYVel
    }

    private fun slowDown() {
        if (xAccel == 0.0) xVel = approachZero(xVel, xSlowDown)
        if (yAccel == 0.0) yVel = approachZero(yVel, ySlowDown)
    }

    fun move() {
        handle_wall_collision()

        x += xVel
        y += yVel

        centerX = x + w / 2
        centerY = y + h / 2
    }

    fun handle_wall_collision() {
        for (wall in walls) {
            while (collide(wall, x + xVel, y, w, h)) {
                xVel = approachZero(xVel)
                if (Math.abs(xVel) < 1.0) xVel = 0.0
                if (xVel == 0.0) break
            }
            while (collide(wall, x, y + yVel, w, h)) {
                yVel = approachZero(yVel)
                if (Math.abs(yVel) < 1.0) yVel = 0.0
                if (yVel == 0.0) break
            }
        }
    }

    fun approachActor(actor: Actor) {
        approachLocation(actor.centerX, actor.centerY)
    }

    fun approachLocation(x: Double, y: Double) {
        val xDist = x - centerX
        val yDist = y - centerY

        val vectorLength = sqrt(xDist * xDist + yDist * yDist)
        val newLength = 0.5

        xAccel = xDist / vectorLength * newLength
        yAccel = yDist / vectorLength * newLength
    }
}