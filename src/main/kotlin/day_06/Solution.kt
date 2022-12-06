package day_06

import InputType
import java.io.File

fun main(args: Array<String>) {
    val input = getInput(InputType.INPUT)

    val part1 = getMarker(input, 4)
    println("*** Result part 1: $part1 ***")

    val part2 = getMarker(input, 14)
    println("*** Result part 2: $part2 ***")
}

private fun getMarker(input: List<Char>, windowSize: Int): Int {
    val window = mutableMapOf<Char, Int>()

    // initialize window
    for (i in 0 until windowSize) {
        val char = input[i]
        val charCountUpdated = window.getOrDefault(char, 0) + 1
        window[char] = charCountUpdated
    }

    for (i in windowSize until input.size) {
        // check if we have a marker
        if (window.keys.count() == windowSize) {
            return i
        }

        // make window smaller at start
        val charToRemove = input[i - windowSize]
        val charCountOldUpdated = window.getOrDefault(charToRemove, 0) - 1
        if (charCountOldUpdated == 0) {
            window.remove(charToRemove)
        } else {
            window[charToRemove] = charCountOldUpdated
        }

        // make window larger at end
        val charToAdd = input[i]
        val charCountNewUpdated = window.getOrDefault(charToAdd, 0) + 1
        window[charToAdd] = charCountNewUpdated
    }

    error("No marker found")
}


private fun getInput(inputType: InputType): List<Char> {
    return File("src/main/kotlin/day_06/${inputType.fileName}.txt")
        .readLines()
        .first()
        .toList()
}