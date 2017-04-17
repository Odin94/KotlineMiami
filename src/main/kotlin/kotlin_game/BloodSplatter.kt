package kotlin_game

data class BloodSplatter(val x: Double, val y: Double, var imgName: String = "random") {
    init {
        if(imgName == "random") imgName = "blood${rnd.nextInt(7 + 1 - 1) + 1}"
    }
}