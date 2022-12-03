package day_03

import InputType
import java.io.File

fun main(args: Array<String>) {
    val input = getInput(InputType.INPUT)

    val prioritySum = calculatePrioritySumOfCommonItemPerRucksack(input)
    println("*** Result part 1: $prioritySum ***")

    val prioritySumBadges = calculatePrioritySumOfBadgesPerThreeElfGroup(input)
    println("*** Result part 2: $prioritySumBadges ***")
}

private fun calculatePrioritySumOfCommonItemPerRucksack(input: List<String>): Int {
    var prioritySum = 0
    for (line in input) {
        val itemSet = mutableSetOf<Char>()

        for (item in line.subSequence(0, line.length / 2)) {
            itemSet.add(item)
        }

        for (item in line.subSequence(line.length / 2, line.length)) {
            if (itemSet.contains(item)) {
                val priority = getPriority(item)
                prioritySum += priority
                break
            }
        }
    }
    return prioritySum
}

fun calculatePrioritySumOfBadgesPerThreeElfGroup(input: List<String>): Int {
    var prioritySum = 0
    var elfIndexInGroup = 0 // 0, 1, 2
    var itemSet = mutableSetOf<Char>()

    for (line in input) {
        if (elfIndexInGroup == 0) {
            itemSet = mutableSetOf()
            itemSet.addAll(line.toList())
        } else {
            val tempSet = mutableSetOf<Char>()
            for (item in line) {
                if(itemSet.contains(item)) {
                    tempSet.add(item)
                }
            }
            itemSet = tempSet
        }
        elfIndexInGroup++

        if (elfIndexInGroup == 3) {
            prioritySum += getPriority(itemSet.first())
            elfIndexInGroup = 0
        }
    }

    return prioritySum
}


private fun getPriority(item: Char): Int {
    val priority = if (item.isLowerCase()) {
        item.code - 'a'.code + 1
    } else {
        item.code - 'A'.code + 27
    }
    return priority
}


private fun getInput(inputType: InputType): List<String> {
    return File("src/main/kotlin/day_03/${inputType.fileName}.txt")
        .readLines()
}