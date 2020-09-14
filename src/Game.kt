fun main() {
    val playerName = "Daniel" // player name
    var healthPoints = 89 // player hp
    val isBlessed = true // blessedness allows player to heal from minor wounds
    val isImmortal = false // player mortality

    // Aura
    val auraColor = auraColor(isBlessed, healthPoints, isImmortal)
    //println(auraColor)

    // status messages based on player health status
    /*
    val healthStatus = if (healthPoints == 100){ "is in excellent condition!" }
        else if (healthPoints in 90..99){ "has a few scratches. Not too shabby!" }
        else if (healthPoints in 75..89){
            if (isBlessed){ "has minor wounds but is healing quickly!" }
            else { "has minor wounds. Be careful.." }
        }
        else if (healthPoints in 15..74){ "is seriously hurt. Seek medical aid soon." }
        else { "is in awful condition!" }
    */
    val healthStatus = formatHealthStatus(healthPoints, isBlessed)

    // player status
    printPlayerStatus(auraColor, isBlessed, playerName, healthStatus)

    //test func
    castFireball()
}

private fun printPlayerStatus(auraColor: String, isBlessed: Boolean, playerName: String, healthStatus: String) {
    println("(Aura: $auraColor) " +
            "(Blessed: ${if (isBlessed) "Yes" else "No"})")
    println("$playerName $healthStatus")
}

private fun auraColor(isBlessed: Boolean, healthPoints: Int, isImmortal: Boolean): String {
    val auraVisible = isBlessed && healthPoints > 50 || isImmortal
    val auraColor = if (auraVisible) "GREEN" else "NONE"
    return auraColor
}

private fun formatHealthStatus(healthPoints: Int, isBlessed: Boolean): String {
    return when (healthPoints) {
        100 -> "is in excellent condition!"
        in 90..99 -> "has a few scratches. Not too shabby!"
        in 75..89 -> if (isBlessed) {
            "has minor wounds but is healing quickly!"
        } else {
            "has minor wounds. Be careful.."
        }
        in 15..74 -> "is seriously hurt. Seek medical aid soon."
        else -> "is in awful condition!"
    }
}

private fun castFireball(numFireballs: Int = 2) {
    if (numFireballs > 1){ println("$numFireballs glasses of Fireball spring into existence!") }
    else { println("Just one glass of Fireball springs into existence!") }
}