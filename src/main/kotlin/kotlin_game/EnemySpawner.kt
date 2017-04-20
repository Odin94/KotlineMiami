package kotlin_game

class EnemySpawner(val constSpawnInterval: Int = 60, val maxVariableSpawnInterval: Int = 60) {
    var spawnAcc = 0
    var currentSpawnInterval = getNextSpawnInterval()


    fun update() {
        spawnAcc++
    }

    fun spawnTimeHasCome() = spawnAcc >= currentSpawnInterval

    fun getNewEnemy(): Actor {
        val location = rnd.nextInt(4)
        when (location) {
            0 -> return Actor(-90.0, 100.0, 64.0, 60.0, maxXVel = 3.0, maxYVel = 3.0)  // spawns left top-ish
            1 -> return Actor(SCREEN_WIDTH + 90.0, SCREEN_CENTER_Y.toDouble(), 64.0, 60.0, maxXVel = 3.0, maxYVel = 3.0)  // spawns right middle
            2 -> return Actor(SCREEN_CENTER_X.toDouble(), -90.0, 64.0, 60.0, maxXVel = 3.0, maxYVel = 3.0)  // spawns top middle
            3 -> return Actor(300.0, SCREEN_HEIGHT + 90.0, 64.0, 64.0, maxXVel = 3.0, maxYVel = 3.0) // spawns bottom left-ish
            else -> return Actor(-90.0, 100.0, 64.0, 60.0, maxXVel = 3.0, maxYVel = 3.0)
        }
    }

    fun updateSpawnInterval() {
        currentSpawnInterval = getNextSpawnInterval()
        spawnAcc = 0
    }

    private fun getNextSpawnInterval() = constSpawnInterval + rnd.nextInt(maxVariableSpawnInterval + 1)
}