package com.adisalagic.second22bytestest.utils

import com.adisalagic.second22bytestest.R
import java.time.temporal.TemporalAmount
import kotlin.random.Random

fun <T> List<T>.uniqueAmountOfRandom(number: Int): List<T> {
    val finalList = mutableListOf<T>()
    val copy = this.toMutableList()
    repeat(number) {
        finalList.add(copy.takeRandom())
    }
    return finalList
}

fun <T> MutableList<T>.takeRandom(): T {
    val randomIndex = Random.nextInt(0, size)
    return removeAt(randomIndex);
}

val countries =
    listOf("Москва", "Париж", "Лондон", "Нью-Йорк", "Лос-Анжелес", "Сидней", "Токио", "Гонконг")
val clocks = listOf(
    R.drawable.__2,
    R.drawable.__3,
    R.drawable.__4,
    R.drawable.__5,
    R.drawable.__6,
    R.drawable.__7,
    R.drawable.__8,
    R.drawable.__9
)

fun giveRandom(amount: Int): List<Pair<Int, String>> {
    val clocksList = countries.uniqueAmountOfRandom(amount)
    val imagesList = clocks.uniqueAmountOfRandom(amount)
    val finalList = mutableListOf<Pair<Int, String>>()
    repeat(amount) {
        finalList.add(imagesList[it] to clocksList[it])
    }
    return finalList
}

fun Int.toTimer(): String {
    val minutes = this / 60
    val secondsLeft = this - minutes * 60
    if (secondsLeft < 10) {
        return "$minutes:0$secondsLeft"
    }else {
        return "$minutes:$secondsLeft"
    }
}