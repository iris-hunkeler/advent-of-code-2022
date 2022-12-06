package day_05

import InputType
import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val (stacks, movements) = getInput(InputType.INPUT)
    
/*    val resultPart1 = getResultPart1(movements, stacks)
    println("*** Result part 1: $resultPart1 ***")*/

    val resultPart2 = getResultPart2(movements, stacks)
    println("*** Result part 2: $resultPart2 ***")
}

private fun getResultPart1(
    movements: List<Triple<Int, Int, Int>>,
    stacks: Map<Int, Stack<Char>>
): String {
    movements.forEach { (amount, from, to) ->
        for (i in 1..amount) {
            stacks[to]?.add(stacks[from]?.pop() ?: error("Error during movement from $from to $to"))
        }
    }

    return getTopCrates(stacks)
}

private fun getResultPart2(
    movements: List<Triple<Int, Int, Int>>,
    stacks: Map<Int, Stack<Char>>
): String {
    movements.forEach { (amount, from, to) ->
        val tempStack = Stack<Char>()
        for (i in 1..amount) {
            tempStack.add(stacks[from]?.pop() ?: error("Error during movement from $from to $to"))
        }

        while(tempStack.isNotEmpty()) {
            stacks[to]?.add(tempStack.pop())
        }
    }

    return getTopCrates(stacks)
}

private fun getTopCrates(stacks: Map<Int, Stack<Char>>): String {
    var result = ""
    stacks.forEach { (_, stack) ->
        result += stack.peek()
    }
    return result
}

private enum class ParsingStep {
    PARSE_STACKS,
    REARRANGE_STACKS,
    PARSE_MOVEMENT;
}

private fun getInput(inputType: InputType): Pair<Map<Int, Stack<Char>>, List<Triple<Int, Int, Int>>> {
    val stacks = sortedMapOf<Int, Stack<Char>>()

    val movements = mutableListOf<Triple<Int, Int, Int>>()

    var step = ParsingStep.PARSE_STACKS

    File("src/main/kotlin/day_05/${inputType.fileName}.txt")
        .readLines()
        .forEach { line ->
            if (line.isEmpty()) {
                step = ParsingStep.REARRANGE_STACKS
            }
            if(line.isNotEmpty() && step == ParsingStep.REARRANGE_STACKS) {
                step = ParsingStep.PARSE_MOVEMENT
            }

            if (step == ParsingStep.PARSE_STACKS) {
                line.forEachIndexed { i, c ->
                    if (c.isUpperCase()) {
                        //println("$c at $i")
                        val stackIndex = ((i - 1) / 4) + 1
                        val stack = stacks.getOrDefault(stackIndex, Stack<Char>())
                        stack.add(c)
                        stacks[stackIndex] = stack
                    }
                }
            }

            if(step == ParsingStep.REARRANGE_STACKS) {
                stacks.forEach { (key, originalStack) ->
                    val tempStack = Stack<Char>()

                    while(originalStack.isNotEmpty()) {
                        tempStack.add(originalStack.pop())
                    }
                    stacks[key] = tempStack
                }
            }

            if (step == ParsingStep.PARSE_MOVEMENT) {
                val parts = line.split(" ")
                movements.add(Triple(parts[1].toInt(), parts[3].toInt(), parts[5].toInt()))
            }
        }

    return Pair(stacks, movements)
}