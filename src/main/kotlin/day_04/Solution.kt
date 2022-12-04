package day_04

import InputType
import java.io.File

fun main(args: Array<String>) {
    val input = getInput(InputType.INPUT)

    val assignmentsWithTotalOverlapCount = countAssignmentsWithTotalOverlap(input)
    println("*** Result part 1: $assignmentsWithTotalOverlapCount ***")

    val assignmentsWithPartialOverlapCount = countAssignmentsWithPartialOverlap(input)
    println("*** Result part 2: $assignmentsWithPartialOverlapCount ***")
}

private fun countAssignmentsWithTotalOverlap(input: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
    var count = 0
    input.forEach { (elf1, elf2) ->
        if (isContained(elf1, elf2) || isContained(elf2, elf1)) {
            count++
        }
    }
    return count
}

private fun countAssignmentsWithPartialOverlap(input: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
    var count = 0
    input.forEach { (elf1, elf2) ->
        if (hasOverlap(elf1, elf2) || hasOverlap(elf2, elf1)) {
            count++
        }
    }
    return count
}

private fun isContained(pair1: Pair<Int, Int>, pair2: Pair<Int, Int>): Boolean {
    return pair1.first >= pair2.first
            && pair1.first <= pair2.second
            && pair1.second <= pair2.second
}

private fun hasOverlap(pair1: Pair<Int, Int>, pair2: Pair<Int, Int>): Boolean {
    return (pair1.first >= pair2.first && pair1.first <= pair2.second)
            || (pair1.second >= pair2.first && pair1.second <= pair2.second)
}


private fun getInput(inputType: InputType): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
    return File("src/main/kotlin/day_04/${inputType.fileName}.txt")
        .readLines()
        .map { line ->
            val (elf1, elf2) = line.split(",")
            val (elf1Start, elf1End) = elf1.split("-")
            val (elf2Start, elf2End) = elf2.split("-")
            Pair(Pair(elf1Start.toInt(), elf1End.toInt()), Pair(elf2Start.toInt(), elf2End.toInt()))
        }
}