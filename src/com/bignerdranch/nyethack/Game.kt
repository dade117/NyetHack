package com.bignerdranch.nyethack

fun main() {
    val player = Player()
    player.castFireball()

    // Aura
    val auraColor = player.auraColor()
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

    // player status
    printPlayerStatus(player)

    // Aura
    player.auraColor()
}

private fun printPlayerStatus(player: Player) {
    println("(Aura: ${player.auraColor()}) " +
            "(Blessed: ${if (player.isBlessed) "Yes" else "No"})")
    println("${player.name} ${player.formatHealthStatus()}")
}

