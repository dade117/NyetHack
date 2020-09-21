package com.bignerdranch.nyethack

import java.io.File

class Player(
        _name:String,
        var healthPoints:Int = 100,
        var isBlessed:Boolean,
        var isImmortal:Boolean
) {
    var name = _name
        get() = "${field.capitalize()} of $hometown"
        private set(value){
            field = value.trim()
        }

    val hometown by lazy{selectHometown()}

    init{
        require(healthPoints > 0) { "healthPoints must be greater than 0." }
        require(name.isNotBlank()) { "Player must have a name." }
    }

    constructor(name:String):this(
            name,
            isBlessed = true,
            isImmortal = false
    ){
        if(name.toLowerCase() == "kar") healthPoints = 40
    }

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

    private fun selectHometown() = File("data/towns.txt")
            .readText()
            .split("\n")
            .shuffled()
            .first()
}