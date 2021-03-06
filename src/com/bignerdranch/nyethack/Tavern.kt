package com.bignerdranch.nyethack
import java.io.File
import com.bignerdranch.nyethack.extensions.random

const val TAVERN_NAME = "Taernyl's Folly"

val String.toDragonSpeak
    get() = this.replace(Regex("[aeiouAEIOU]")){
        when(it.value){
            "a","A"->"4"
            "e","E"->"3"
            "i","I"->"1"
            "o","O"->"0"
            "u","U"->"|_|"
            else->it.value
        }
    }

// Currency vars
val patronGold = mutableMapOf<String, Double>()

val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableSetOf<String>()

val menuList = File("data/tavern-menu-data.txt").readText().split("\n")

fun main() {
    /*
    if(com.bignerdranch.nyethack.getPatronList.contains("Eli")){
        println("The tavern master says: Eli's in the back playing cards.")
    } else {
        println("The tavern master says: Eli is not here.")
    }

    if(com.bignerdranch.nyethack.getPatronList.containsAll(listOf("Sophie", "Mordoc"))){
        println("The tavern master says: Yea, they're seated by the stew kettle.")
    } else {
        println("The tavern master says: Nay, they departed hours ago.")
    }
     */

    //com.bignerdranch.nyethack.placeOrder("shandy,Dragon's Breath, 5.91")
    //com.bignerdranch.nyethack.placeOrder("elixir,Shirley's Temple,4.12")

    var temp = 5

    (0..9).forEach{
        temp += it
        val first = patronList.random()
        val last = lastName.random()
        val name = "$first $last"
        uniquePatrons += name
    }

    uniquePatrons.forEach{
        patronGold[it] = 6.0
    }

    var orderCount = 0
    while(orderCount <= 9){
        placeOrder(uniquePatrons.random(), menuList.random())
        orderCount++

        if(uniquePatrons.isEmpty()){
            break
        }
    }

    displayPatronBalances()
}

private fun placeOrder(patronName:String, menuData:String){
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("$patronName speaks with $tavernMaster about their order...")

    val (type, name, price) = menuData.split(',')
    // val message = "$patronName buys a $name ($type) for $price."
    // println(message)


    if(performPurchase(price.toDouble(), patronName)){
        val message = "$patronName buys a $name ($type) for $price."
        println(message)

        val phrase = if(name == "Dragon's Breath"){
            "$patronName exclaims: ${"Ah delicious $name!\n".toDragonSpeak}"
        } else {
            "$patronName says: Thanks for the $name.\n"
        }

        println(phrase)
    } else {
        println("Bartender says: You don't have enough money for $name, $patronName!")
        println("Bartender says: Bouncer! Show $patronName where the peasants\nwith no gold in their pockets go!")
        tavernBounce(patronName)
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

        val numDots = 20 + (18 - name.length)
        println("$name${".".repeat(numDots)}$price")
    }

    println("\n~~[Elixir]~~")
    for(item in elixir){
        val(_,name,price) = item.split(",")

        val numDots = 20 + (18 - name.length)
        println("$name${".".repeat(numDots)}$price")
    }

    println("\n~~[Meal]~~")
    for(item in meal){
        val(_,name,price) = item.split(",")

        val numDots = 20 + (18 - name.length)
        println("$name${".".repeat(numDots)}$price")
    }

    println("\n~~[Desert Dessert]~~")
    for(item in desertDessert){
        val(_,name,price) = item.split(",")

        val numDots = 20 + (18 - name.length)
        println("$name${".".repeat(numDots)}$price")
    }
}

fun performPurchase(price:Double, patronName:String): Boolean {
    val totalPurse = patronGold.getValue(patronName)
    if(totalPurse >= price){
        patronGold[patronName] = totalPurse - price
    } else { return false }

    return true
}

private fun displayPatronBalances(){
    patronGold.forEach{ patron, balance ->
        println("$patron balance: ${"%.2f".format(balance)}")
    }
}

private fun tavernBounce(patronName: String){
    uniquePatrons.remove(patronName)
    patronGold.remove(patronName)
    println("$patronName is shown to the door by the bouncer.\n")
}

