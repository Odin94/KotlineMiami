package kotlin_game

import kotlin_game.extensions.sign
import java.awt.event.ActionEvent
import javax.swing.AbstractAction


class MoveAction(val actor: Actor, val xAccel: Int, val yAccel: Int) : AbstractAction() {

    private val maxXAccel = 0.5
    private val maxYAccel = 0.5

    override fun actionPerformed(e: ActionEvent?) {
        if (xAccel != 0) actor.xAccel = xAccel * maxXAccel
        if (yAccel != 0) actor.yAccel = yAccel * maxYAccel
    }
}

class StopMoveAction(val actor: Actor, val xAccel: Int, val yAccel: Int) : AbstractAction() {

    override fun actionPerformed(e: ActionEvent?) {
        if(xAccel.sign() == actor.xAccel.sign()) actor.xAccel = 0.0
        if(yAccel.sign() == actor.yAccel.sign()) actor.yAccel = 0.0
    }
}