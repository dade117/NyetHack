package com.bignerdranch.nyethack

class Player {
    var name = "madrigal"
        get() = field.capitalize()
        private set(value){
            field = value.trim()
        }

    var healthPoints = 89
    val isBlessed = true
    private val isImmortal = false

    fun castFireball(numFireballs: Int = 2) {
        if (numFireballs > 1){ println("$numFireballs glasses of Fireball spring into existence!") }
        else { println("Just one glass of Fireball springs into existence!") }
    }

    fun auraColor(): String {
        val auraVisible = isBlessed && healthPoints > 50 || isImmortal
        val auraColor = if (auraVisible) "GREEN" else "NONE"
        return auraColor
    }

    fun formatHealthStatus() =
        when (healthPoints) {
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