package kotlin_game

import kotlin_game.Combat.Projectile


fun approachZero(x: Double, step: Double = 1.0): Double {
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

fun collide(wall: Wall, x: Double, y: Double, w: Double, h: Double): Boolean {
    return wall.x + wall.w >= x && wall.x <= x + w &&
            wall.y + wall.h >= y && wall.y <= y + h

    NotImplementedError("rotated wall collision not implemented")  // TODO: Implement
    return false
}

fun collide(wall: Wall, actor: Actor): Boolean {
    return collide(wall, actor.x, actor.y, actor.w, actor.h)
}

fun collide(wall: Wall, proj: Projectile): Boolean {
    return collide(wall, proj.x, proj.y, proj.w.toDouble(), proj.h.toDouble())
}

fun collide(wall: Wall, bloodPart: BloodParticle): Boolean {
    return collide(wall, bloodPart.x, bloodPart.y, bloodPart.w, bloodPart.h)
}