package kotlin_game

data class Wall(val x: Double, val y: Double, val w: Double, val h: Double, val angle: Double) {
    val centerX = x + w / 2
    val centerY = y + h / 2
}