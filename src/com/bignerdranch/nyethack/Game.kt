package com.bignerdranch.nyethack

import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main() {
    Game.play()
}

object Game {
    private val player = Player("Danyo")
    private var currentRoom:Room = TownSquare()

    private var worldMap = listOf(
            listOf(currentRoom, Room("Tavern"), Room("Back Room")),
            listOf(Room("Long Corridor"), Room("Generic Room"))
    )

    init {
        println("Welcome, adventurer")
        player.castFireball()
    }

    fun play(){
        while(true){
            println(currentRoom.description())
            println(currentRoom.load())

            // Player Status
            printPlayerStatus(player)

            print(">Enter your command:")
            println(GameInput(readLine()).processCommand())
        }
    }

    private fun printPlayerStatus(player: Player) {
        println("(Aura: ${player.auraColor()}) " +
                "(Blessed: ${if (player.isBlessed) "Yes" else "No"})")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(arg:String?){
        private val input = arg?:""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        fun processCommand() = when(command.toLowerCase()){
            "move" -> move(argument)
            "exit", "quit" -> {
                println("Goodbye, adventurer")
                exitProcess(0)
            }
            "map" -> map(player.currentPosition)
            "ring" -> {
                try {
                    if(currentRoom is TownSquare){
                        (currentRoom as TownSquare).ringBell(player)
                    } else {
                        println("You are currently not in the Town Square.")
                    }
                } catch (e:Exception) {
                    "Sorry, something went wrong"
                }
            }
            "fight" -> fight()
            else -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do.."
    }

    private fun move(directionInput:String) =
            try{
                val direction = Direction.valueOf(directionInput.toUpperCase())
                val newPosition = direction.updateCoordinate(player.currentPosition)
                if(!newPosition.isInBounds){
                    throw IllegalStateException("$direction is out of bounds.")
                }

                val newRoom = worldMap[newPosition.y][newPosition.x]
                player.currentPosition = newPosition
                currentRoom = newRoom
                "Okay, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
            } catch (e:Exception){
                "Invalid direction: $directionInput"
            }

    private fun map(coordinate: Coordinate):String{
        var line1 = "OOO"
        var line2 = "OO"

        if(coordinate.y == 0){
            line2 = "OO"
           when(coordinate.x){
               0 -> line1 = "XOO"
               1 -> line1 = "OXO"
               2 -> line1 = "OOX"
           }
        } else {
            line1 = "OOO"
            when(coordinate.x){
                0 -> line2 = "XO"
                1 -> line2 = "OX"
            }
        }

        return line1 + "\n" + line2
    }

    private fun fight() = currentRoom.monster?.let {
        while(player.healthPoints > 0 && it.healthPoints > 0){
            slay(it)
            Thread.sleep(1000)
        }

        "Combat complete"
    }?:"There's no beast to slay here."

    private fun slay(monster:Monster){
        println("${monster.name} did ${monster.attack(player)} damage!")
        println("${player.name} did ${player.attack(monster)} damage!")

        if(player.healthPoints <= 0){
            println(">>>> You have been defeated! Thanks for playing. <<<<")
            exitProcess(0)
        }

        if(monster.healthPoints <= 0){
            println(">>>> ${monster.name} has been defeated! <<<<")
            currentRoom.monster = null
        }
    }
}
