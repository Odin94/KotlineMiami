package kotlin_game

import java.awt.Dimension
import javax.swing.JFrame


/**
 * Created by Odin on 31.03.2017.
 */

val SCREEN_WIDTH = 1200
val SCREEN_HEIGHT = 800

val SCREEN_CENTER_X = SCREEN_WIDTH / 2
val SCREEN_CENTER_Y = SCREEN_HEIGHT / 2

class KotlinGaem : JFrame() {

    fun run() {
        initGame()
    }

    fun initGame() {
        val gameMap = Map()
        add(gameMap)

        size = Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)
        title = "kotlin_game"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)

        isVisible = true


        gameMap.startGameLoop()
    }

}

fun main(args: Array<String>) {
    KotlinGaem().run()
}