package kotlin_game

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit


class EclipseDrawer {

    val eclipseTransformChanger: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    var eclipseTransformChangerFuture: ScheduledFuture<*>

    private var xTransformer = 0.0
    private var yTransformer = 0.0

    private var xMovement = 10.0
    private var yMovement = 0.0

    private var maxMovement = 30
    private var minMovement = -30

    init {
        val BPM = 140.0
        val timeBetweenQuarters = 60000 / BPM

        eclipseTransformChangerFuture = eclipseTransformChanger.scheduleAtFixedRate({
            pickNewTransformerMovement()
        }, 0, timeBetweenQuarters.toLong(), TimeUnit.MILLISECONDS)
    }

    fun update() {
        xTransformer += xMovement
        yTransformer += yMovement
    }

    fun pickNewTransformerMovement() {
        xMovement = (rnd.nextInt(maxMovement + 1 - minMovement) + minMovement).toDouble()
        yMovement = (rnd.nextInt(maxMovement + 1 - minMovement) + minMovement).toDouble()

        if(Math.abs(xTransformer) >= 4500) {
            xTransformer = 0.0
        }
        if(Math.abs(yTransformer) >= 4500) {
            yTransformer = 0.0
        }
    }

    fun drawEclipses(g2d: Graphics2D) {
        for (i in 2..10 step 2) {

            val ellipse = Ellipse2D.Double(xTransformer / i, yTransformer / i, i * 8.0, i * 13.0)
            g2d.stroke = BasicStroke(1F)
            g2d.color = Color.gray

            for (degree in 0..360 step 5) {
                val affineTransform = AffineTransform.getTranslateInstance(SCREEN_WIDTH / 2.0, SCREEN_HEIGHT / 2.0)
                affineTransform.rotate(Math.toRadians(degree.toDouble()))
                g2d.draw(affineTransform.createTransformedShape(ellipse))
            }
        }
    }
}