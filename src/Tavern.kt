/*
    This file contains exercises from the Kotlin Programming
    Big Nerd Ranch book Ch.6 Null Safety and Exceptions, Ch. 7 Strings, & Ch. 8
    Numbers.

    - Daniel De Leon II
*/
import kotlin.math.roundToInt
import java.io.File

const val TAVERN_NAME = "Taernyl's Folly"

// player currency vars
var playerGold = 10
var playerSilver = 10
var playerDragonCoin = 0

val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableSetOf<String>()

val menuList = File("data/tavern-menu-data.txt").readText().split("\n")

fun main() {
    if(patronList.contains("Eli")){
        println("The tavern master says: Eli's in the back playing cards.")
    } else {
        println("The tavern master says: Eli is not here.")
    }

    if(patronList.containsAll(listOf("Sophie", "Mordoc"))){
        println("The tavern master says: Yea, they're seated by the stew kettle.")
    } else {
        println("The tavern master says: Nay, they departed hours ago.")
    }

    //placeOrder("shandy,Dragon's Breath, 5.91")
    //placeOrder("elixir,Shirley's Temple,4.12")

    (0..9).forEach{
        val first = patronList.shuffled().first()
        val last = lastName.shuffled().first()
        val name = "$first $last"
        uniquePatrons += name
    }

    println(uniquePatrons)

    var orderCount = 0
    while(orderCount <= 9){
        placeOrder(uniquePatrons.shuffled().first(), menuList.shuffled().first())
        orderCount++
    }
}

fun performPurchase(price:Double): Boolean {
    displayBalance()
    val totalPurse = playerGold + (playerSilver / 100.0)
    println("Total Purse: $totalPurse")

    if(totalPurse >= price) {
        println("Purchasing item for $price")
        val remainingBalance = totalPurse - price
        println("Remaining balance: ${"%.2f".format(remainingBalance)}")

        // Logic for updating balance
        val remainingGold = remainingBalance.toInt()
        val remainingSilver = (remainingBalance % 1 * 100).roundToInt()
        playerGold = remainingGold
        playerSilver = remainingSilver
        displayBalance()

        return true
    }

    return false
}

private fun displayBalance(){
    println("Player's purse balance:\n- Gold: $playerGold\n- Silver: $playerSilver")
    if(playerDragonCoin > 0){ println("Dragoncoin: $playerDragonCoin") }
}

private fun placeOrder(patronName:String, menuData:String){
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("$patronName speaks with $tavernMaster about their order...")

    val (type, name, price) = menuData.split(',')
    val message = "$patronName buys a $name ($type) for $price."
    println(message)

    /*
    if(performPurchase(price.toDouble())){
        val message = "Madrigal buys a $name ($type) for $price."
        println(message)

        val phrase = if(name == "Dragon's Breath"){
            "Madrigal exclaims: ${toDragonSpeak("Ah delicious $name!")}"
        } else {
            "Madrigal says: Thanks for the $name."
        }

        println(phrase)
    } else {
        println("Bartender says: You don't have enough money friend! Come back when you do")
    }
     */

    val phrase = if(name == "Dragon's Breath"){
        "$patronName exclaims: ${toDragonSpeak("Ah delicious $name!")}"
    } else {
        "$patronName says: Thanks for the $name."
    }

    println(phrase)
}

private fun toDragonSpeak(phrase:String)=
        phrase.replace(Regex("[aeiouAEIOU]")){
            when(it.value){
                "a","A"->"4"
                "e","E"->"3"
                "i","I"->"1"
                "o","O"->"0"
                "u","U"->"|_|"
                else->it.value
            }
        }

fun printMenu(){
    println("\t*** Welcome to $TAVERN_NAME ***")

    val shandy = mutableListOf<String>()
    val elixir = mutableListOf<String>()
    val desertDessert = mutableListOf<String>()
    val meal = mutableListOf<String>()

    for(item in menuList){
        when {
            item.split(",").first() == "shandy" -> {
                shandy.add(item)
            }
            item.split(",").first() == "elixir" -> {
                elixir.add(item)
            }
            item.split(",").first() == "meal" -> {
                meal.add(item)
            }
            item.split(",").first() == "desert dessert" -> {
                desertDessert.add(item)
            }
            else -> {
                println("Type mismatch")
            }
        }
    }

    println("~~[Shandy]~~")
    for(item in shandy){
        val(_,name,price) = item.split(",")

        var numDots = 20 + (18 - name.length)
        println("$name${".".repeat(numDots)}$price")
    }

    println("\n~~[Elixir]~~")
    for(item in elixir){
        val(_,name,price) = item.split(",")

        var numDots = 20 + (18 - name.length)
        println("$name${".".repeat(numDots)}$price")
    }

    println("\n~~[Meal]~~")
    for(item in meal){
        val(_,name,price) = item.split(",")

        var numDots = 20 + (18 - name.length)
        println("$name${".".repeat(numDots)}$price")
    }

    println("\n~~[Desert Dessert]~~")
    for(item in desertDessert){
        val(_,name,price) = item.split(",")

        var numDots = 20 + (18 - name.length)
        println("$name${".".repeat(numDots)}$price")
    }
}

