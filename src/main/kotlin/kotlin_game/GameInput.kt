package kotlin_game

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseEvent.BUTTON1
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.KeyStroke

class GameInput(panel: JPanel, actor: Actor) : MouseAdapter() {

    private val whenInFocus = JComponent.WHEN_IN_FOCUSED_WINDOW
    private val MOVE_UP = "MOVE_UP"
    private val MOVE_DOWN = "MOVE_DOWN"
    private val MOVE_RIGHT = "MOVE_RIGHT"
    private val MOVE_LEFT = "MOVE_LEFT"

    private val STOP_MOVE_UP = "STOP_MOVE_UP"
    private val STOP_MOVE_DOWN = "STOP_MOVE_DOWN"
    private val STOP_MOVE_RIGHT = "STOP_MOVE_RIGHT"
    private val STOP_MOVE_LEFT = "STOP_MOVE_LEFT"

    private var actionMap = panel.actionMap
    private var inputMap = panel.getInputMap(whenInFocus)

    var mouseX = 0
    var mouseY = 0

    init {
        initInputMap()
        initActionMap(actor)
    }

    private fun initInputMap() {
        inputMap.put(KeyStroke.getKeyStroke("W"), MOVE_UP)
        inputMap.put(KeyStroke.getKeyStroke("A"), MOVE_LEFT)
        inputMap.put(KeyStroke.getKeyStroke("S"), MOVE_DOWN)
        inputMap.put(KeyStroke.getKeyStroke("D"), MOVE_RIGHT)

        inputMap.put(KeyStroke.getKeyStroke("released W"), STOP_MOVE_UP)
        inputMap.put(KeyStroke.getKeyStroke("released A"), STOP_MOVE_LEFT)
        inputMap.put(KeyStroke.getKeyStroke("released S"), STOP_MOVE_DOWN)
        inputMap.put(KeyStroke.getKeyStroke("released D"), STOP_MOVE_RIGHT)
    }

    private fun initActionMap(actor: Actor) {
        actionMap.put(MOVE_UP, MoveAction(actor, 0, -1))
        actionMap.put(MOVE_LEFT, MoveAction(actor, -1, 0))
        actionMap.put(MOVE_DOWN, MoveAction(actor, 0, 1))
        actionMap.put(MOVE_RIGHT, MoveAction(actor, 1, 0))

        actionMap.put(STOP_MOVE_UP, StopMoveAction(actor, 0, -1))
        actionMap.put(STOP_MOVE_LEFT, StopMoveAction(actor, -1, 0))
        actionMap.put(STOP_MOVE_DOWN, StopMoveAction(actor, 0, 1))
        actionMap.put(STOP_MOVE_RIGHT, StopMoveAction(actor, 1, 0))
    }

    override fun mouseMoved(e: MouseEvent?) {
        super.mouseMoved(e)

        mouseX = e?.x ?: 0
        mouseY = e?.y ?: 0
    }

    override fun mouseDragged(e: MouseEvent?) {
        super.mouseDragged(e)
    }

    override fun mouseClicked(e: MouseEvent?) {
        super.mouseClicked(e)
    }

    override fun mousePressed(e: MouseEvent?) {
        super.mousePressed(e)

        if (e?.button == BUTTON1) player.shoot()
    }
}