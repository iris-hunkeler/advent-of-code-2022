package day_01

import InputType
import java.io.File

fun main(args: Array<String>) {
    val calories = getInput(InputType.INPUT)

    calculateCaloriesTopElf(calories)

    calculateCaloriesTopThreeElf(calories)
}

private fun calculateCaloriesTopElf(calories: List<List<Int>>) {
    var maxCalories = 0
    calories.forEach { caloriesForElf ->
        val sumForElf = caloriesForElf.sum()
        if (sumForElf > maxCalories) {
            maxCalories = sumForElf
        }
    }

    println("*** Result part 1: $maxCalories ***")
}

private fun calculateCaloriesTopThreeElf(calories: List<List<Int>>) {

    val topThreeCalories = calories
            .map { caloriesForElf -> caloriesForElf.sum() }
            .sortedDescending()
            .take(3)
            .sum()

    println("*** Result part 2: $topThreeCalories ***")
}


private fun getInput(inputType: InputType): List<List<Int>> {
    val calories = mutableListOf<MutableList<Int>>()
    var caloriesForElf = mutableListOf<Int>()
    File("src/main/kotlin/day_01/${inputType.fileName}.txt")
            .readLines()
            .forEach { item ->
                if (item.isEmpty()) {
                    calories.add(caloriesForElf)
                    caloriesForElf = mutableListOf()
                } else {
                    caloriesForElf.add(item.toInt())
                }
            }
    calories.add(caloriesForElf)

//    println(calories)

    return calories
}