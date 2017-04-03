package kotlin_game


data class Actor(var x: Double, var y: Double, var w: Double, var h: Double,
                 var centerX: Double = x + w / 2, var centerY: Double = y + h / 2,
                 var xAccel: Double = 0.0, var yAccel: Double = 0.0,
                 var xVel: Double = 0.0, var yVel: Double = 0.0,
                 var maxXVel: Double = 10.0, var maxYVel: Double = 10.0,
                 var xSlowDown: Double = 1.0, var ySlowDown: Double = 1.0,
                 var angle: Double = 0.0, var tarAngle: Double = 0.0) {

    private val rotationSpeed = 10

    fun updateAngle(mouseX: Int, mouseY: Int) {

        tarAngle = Math.atan2(centerY - mouseY, centerX - mouseX) - Math.PI / 2
        tarAngle = toDegrees(tarAngle)

        angle = toDegrees(angle)

        if (angle < tarAngle) {
            if (Math.abs(angle - tarAngle) < 180) angle += rotationSpeed else angle -= rotationSpeed
        } else {
            if (Math.abs(angle - tarAngle) < 180) angle -= rotationSpeed else angle += rotationSpeed
        }

        if (Math.abs(angle - tarAngle) < rotationSpeed * 2) angle = tarAngle

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
        x += xVel
        y += yVel

        centerX = x + w / 2
        centerY = y + h / 2
    }
}