package kotlin_game

import kotlin_game.Combat.Projectile
import kotlin_game.extensions.drawImage
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.swing.JPanel
import kotlin.collections.ArrayList


val rnd = Random()

var player = Actor(SCREEN_CENTER_X.toDouble(), 100.0, 64.0, 60.0)
var projectiles: MutableList<Projectile> = ArrayList()
var walls: MutableList<Wall> = ArrayList()
var enemies: MutableList<Actor> = ArrayList()
var bloodSplatters: MutableList<BloodSplatter> = ArrayList()
var bloodParticles: MutableList<BloodParticle> = ArrayList()
var corpses: MutableList<Corpse> = ArrayList()
val enemySpawner = EnemySpawner()

class Map : JPanel() {

    val gameInput: GameInput = GameInput(this, player)
    val eclipseDrawer = EclipseDrawer()

    val gameLoopExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    lateinit var gameLoopExecutorFuture: ScheduledFuture<*>

    init {
        enemies.add(Actor(300.0, 100.0, 64.0, 60.0, maxXVel = 3.0, maxYVel = 3.0))
        walls.add(Wall(SCREEN_WIDTH - (25.0 + 64), 100.0, 64.0, 500.0, 0.0))
        walls.add(Wall(25.0, 100.0, 64.0, 500.0, 0.0))

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
        updateProjectiles()
        updateEnemies()
        updateActor(player)
        updateEnemySpawner()
        updateBloodParticles()
    }

    //-------------------------------------------------//
    //                  Updates                        //
    //-------------------------------------------------//

    fun updateActor(actor: Actor) {
        actor.addAccel()
        actor.move()
        actor.updateAngle(gameInput.mouseX, gameInput.mouseY)
    }

    fun updateEnemies() {
        for (enemy in enemies) {
            enemy.approachActor(player)
            enemy.addAccel()
            enemy.move()
        }

        for (deadEnemy in enemies.filter { it.health <= 0 }) {
            deadEnemy.onDeath()
        }

        enemies = enemies.filter { it.health > 0 } as MutableList<Actor>
    }

    fun updateProjectiles() {
        for (proj in projectiles) {
            proj.update()

            enemies.filter { collide(it, proj) }.forEach {
                proj.hitSomething = true
                it.takeDamage(proj)
                bloodParticles.add(BloodParticle(proj.x, proj.y, rnd.nextInt(5).toDouble() + 1, rnd.nextInt(5).toDouble() + 1, proj.xVel, proj.yVel))
            }

            walls
                    .filter { collide(it, proj) }
                    .forEach { proj.hitSomething = true }
        }

        projectiles = projectiles.filter { !it.hitSomething && !it.isOffscreen() && it.active } as MutableList<Projectile>
    }

    fun updateEnemySpawner() {
        enemySpawner.update()
        if (enemySpawner.spawnTimeHasCome()) {
            enemies.add(enemySpawner.getNewEnemy())
            enemySpawner.updateSpawnInterval()
        }
    }

    fun updateBloodParticles() {
        bloodParticles.map { it.update() }
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        draw(g!!)
    }

    //-------------------------------------------------//
    //                  Draws                          //
    //-------------------------------------------------//

    fun drawBloodSplatters(g2d: Graphics2D) {
        for (bloodsplatter in bloodSplatters) {
            g2d.drawImage(Resources.getImage(bloodsplatter.imgName), bloodsplatter.x, bloodsplatter.y)
        }
    }

    fun drawBloodParticles(g2d: Graphics2D) {
        g2d.color = Color.red
        bloodParticles.map { g2d.fillOval(it.x.toInt(), it.y.toInt(), it.w.toInt(), it.h.toInt()) }
    }

    fun drawCorpses(g2d: Graphics2D) {
        for (corpse in corpses) {
            g2d.drawImage(Resources.getImage(corpse.imgName), corpse.x, corpse.y)
        }
    }

    fun drawActor(actor: Actor, g2d: Graphics2D) {
        val transform = g2d.transform

        g2d.rotate(actor.angle, actor.centerX, actor.centerY)
        g2d.drawImage(Resources.getImage("actorImage"), actor.x, actor.y)

        g2d.transform = transform
    }

    fun drawProjectiles(g2d: Graphics2D) {
        g2d.color = Color.darkGray
        for (proj in projectiles) {
            g2d.fillOval(proj.x.toInt(), proj.y.toInt(), proj.w, proj.h)
        }
    }

    fun drawEnemies(g2d: Graphics2D) {
        val transform = g2d.transform

        for (enemy in enemies) {
            g2d.rotate(enemy.angle, enemy.centerX, enemy.centerY)
            g2d.drawImage(Resources.getImage("actorImage"), enemy.x, enemy.y)
        }

        g2d.transform = transform
    }

    fun drawWalls(g2d: Graphics2D) {
        val transform = g2d.transform

        g2d.color = Color(0, 0, 0, 230)
        for (wall in walls) {
            g2d.rotate(wall.angle, wall.centerX, wall.centerY)
            g2d.fillRect(wall.x.toInt(), wall.y.toInt(), wall.w.toInt(), wall.h.toInt())
        }

        g2d.transform = transform
    }

    fun draw(g: Graphics) {
        val g2d = g as Graphics2D

        val renderHints = RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g2d.setRenderingHints(renderHints)

        eclipseDrawer.drawEclipses(g2d)
        drawBloodSplatters(g2d)
        drawCorpses(g2d)
        drawProjectiles(g2d)
        drawEnemies(g2d)
        drawActor(player, g2d)
        drawWalls(g2d)
        drawBloodParticles(g2d)
    }
}