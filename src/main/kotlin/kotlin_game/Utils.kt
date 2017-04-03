package kotlin_game


fun approachZero(x: Double, step: Double): Double {
    var y = x

    if (y > 0) {
        if (y >= step) y -= step else y = 0.0
    } else if (y < 0) {
        if (y <= step) y += step else y = 0.0
    }

    return y
}

fun toDegrees(x: Double): Double {
    var y = x

    y = Math.toDegrees(y)
    y %= 360
    if (y < 0) y += 360

    return y
}