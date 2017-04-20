package kotlin_game

data class BloodSplatter(val centerX: Double, val centerY: Double, var imgName: String = "random") {
    val x: Double
    val y: Double

    init {
        if(imgName == "random") imgName = "blood${rnd.nextInt(7 + 1 - 1) + 1}"

        val imgSize: Int
        when(imgName) {
            "smallBlood" -> imgSize = 26
            else -> imgSize = 64
        }

        x = centerX -imgSize / 2
        y = centerY - imgSize / 2
    }
}