package kotlin_game.extensions

import java.awt.Graphics2D
import java.awt.Image
import java.awt.geom.Ellipse2D

fun Ellipse2DDouble(x: Int, y: Int, w: Int, h: Int): Ellipse2D.Double {
    return Ellipse2D.Double(x.toDouble(), y.toDouble(), w.toDouble(), h.toDouble())
}

fun Graphics2D.drawImage(img: Image, x: Double, y: Double) {
    drawImage(img, x.toInt(), y.toInt(), null)
}

fun Double.sign(): Int {
    return when {
        this > 0 -> 1
        this < 0 -> -1
        else -> 0
    }
}

fun Int.sign(): Int {
    return when {
        this > 0 -> 1
        this < 0 -> -1
        else -> 0
    }
}