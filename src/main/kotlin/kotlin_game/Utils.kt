package kotlin_game

import kotlin_game.Combat.Projectile


fun approachZero(x: Double, step: Double): Double {
    var y = x

    if (y > 0) {
        if (y >= step) y -= step else y = 0.0
    } else if (y < 0) {
        if (y <= step) y += step else y = 0.0
    }

    return y
}

fun toNormalizedDegrees(x: Double): Double {
    var y = x

    y = Math.toDegrees(y)
    y %= 360
    if (y < 0) y += 360

    return y
}

fun collide(a: Actor, p: Projectile): Boolean {
    return a.x + a.w >= p.x && a.x <= p.x + p.w &&
            a.y + a.h >= p.y && a.y <= p.y + p.h
}