package kotlin_game

import kotlin_game.extensions.drawImage
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.awt.RenderingHints
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.swing.ImageIcon
import javax.swing.JPanel


class Map : JPanel() {

    var player = Actor(100.0, 100.0, 64.0, 60.0)
    val gameInput: GameInput = GameInput(this, player)
    val eclipseDrawer = EclipseDrawer()

    val gameLoopExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    lateinit var gameLoopExecutorFuture: ScheduledFuture<*>

    private val img: Image by lazy { loadImage() }

    init {
        addMouseListener(gameInput)
        addMouseMotionListener(gameInput)
    }

    fun startGameLoop() {
        gameLoopExecutorFuture = gameLoopExecutor.scheduleAtFixedRate({
            update()
            repaint()
        }, 0, 20, TimeUnit.MILLISECONDS)
    }

    fun update() {
        eclipseDrawer.update()
        updateActor(player)
    }

    fun updateActor(actor: Actor) {
        actor.addAccel()
        actor.move()
        actor.updateAngle(gameInput.mouseX, gameInput.mouseY)
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        draw(g!!)
    }

    fun drawActor(actor: Actor, g2d: Graphics2D) {
        val transform = g2d.transform

        g2d.rotate(actor.angle, actor.centerX, actor.centerY)
        g2d.drawImage(img, actor.x, actor.y)

        g2d.transform = transform
    }

    fun draw(g: Graphics) {
        val g2d = g as Graphics2D

        val renderHints = RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g2d.setRenderingHints(renderHints)

        eclipseDrawer.drawEclipses(g2d)

        drawActor(player, g2d)
    }


    private fun loadImage(): Image {
        return ImageIcon("res/test.png").image
    }

}