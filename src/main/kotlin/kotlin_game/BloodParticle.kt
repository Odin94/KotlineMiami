package kotlin_game

data class BloodParticle(var x: Double, var y: Double, val w: Double, val h: Double, var xVel: Double, var yVel: Double) {
    var moving = true

    fun update() {
        if (moving) {
            x += xVel
            y += yVel

            xVel = approachZero(xVel, rnd.nextDouble() * 4)
            yVel = approachZero(yVel, rnd.nextDouble() * 4)

            for (wall in walls) {
                if(collide(wall, this)) {
                    moving = false
                    break
                }
            }
        }
    }
}