/*
    This file contains exercises from the Kotlin Programming
    Big Nerd Ranch book Ch.6 Null Safety and Exceptions, Ch. 7 Strings, & Ch. 8
    Numbers.

    - Daniel De Leon II
*/
import kotlin.math.roundToInt
const val TAVERN_NAME = "Taernyl's Folly"

// player currency vars
var playerGold = 10
var playerSilver = 10
var playerDragonCoin = 0

fun main() {
    placeOrder("shandy,Dragon's Breath, 5.91")
    //placeOrder("elixir,Shirley's Temple,4.12")
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

private fun placeOrder(menuData:String){
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("Madrigal speaks with $tavernMaster about their order...")

    val (type, name, price) = menuData.split(',')

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

