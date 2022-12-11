package day_11

import InputType
import java.io.File
import java.math.BigInteger

fun main(args: Array<String>) {
    val monkeys = getInput(InputType.SAMPLE)
    println("Monkeys original: ${monkeys.map { it.items }}")

    simulateMonkeyBusiness(monkeys, true, 20)
    println("*** Result part 1: ${calculateResult(monkeys)} ***")

    //simulateMonkeyBusiness(monkeys, false, 1000)
    //println("*** Result part 2: ${calculateResult(monkeys)} ***")
}

private fun calculateResult(monkeys: List<Monkey>): Long {
    val mostActiveMonkeys = monkeys.map { it.itemsInspected }.sortedDescending().take(2)
    return mostActiveMonkeys[0] * mostActiveMonkeys[1]
}

private fun simulateMonkeyBusiness(monkeys: List<Monkey>, reduceWorryLevel: Boolean, numberOfRounds: Int) {
    for (round in 1..numberOfRounds) {
        monkeys.forEachIndexed { index, m ->
            while (m.items.isNotEmpty()) {
                var item = m.items.removeFirst()
                m.itemsInspected++
                item = calculateNewWorryLevel(m, item, reduceWorryLevel)

                val targetMonkey = if ((item % m.test) == BigInteger.ZERO) {
                    m.targetMonkeyTestTrue
                } else {
                    m.targetMonkeyTestFalse
                }
                monkeys[targetMonkey].items.add(item)
                //println("Monkey $index throws item $item to monkey $targetMonkey")
            }
        }
        //println("Items after round $round: ${monkeys.map { it.items }}")

        println("After $round: " +
            monkeys.mapIndexed { index, m ->
                "$index: ${m.itemsInspected}"
            }
        )
    }
}

private fun calculateNewWorryLevel(m: Monkey, item: BigInteger, reduceWorryLevel: Boolean): BigInteger {
    var worryLevel = item
    val operand = if (m.operandDynamic) {
        worryLevel
    } else {
        m.operand
    }

    if (m.operator == Operator.ADDITION) {
        worryLevel += operand
    } else {
        worryLevel *= operand
    }
    if (reduceWorryLevel) {
        worryLevel /= BigInteger.valueOf(3)
    }
    return worryLevel
}

private const val START_MONKEY = "Monkey "
private const val START_ITEMS = "  Starting items: "
private const val START_OPERATION = "  Operation: new = "
private const val START_TEST = "  Test: divisible by "
private const val START_TEST_TRUE = "    If true: throw to monkey "
private const val START_TEST_FALSE = "    If false: throw to monkey "

private fun getInput(inputType: InputType): List<Monkey> {
    val monkeys = mutableListOf<Monkey>()
    var monkey = Monkey()
    File("src/main/kotlin/day_11/${inputType.fileName}.txt")
        .readLines()
        .map { line ->
            if (line.startsWith(START_MONKEY)) {
                monkey = Monkey()
            }
            if (line.startsWith(START_ITEMS)) {
                line.substring(START_ITEMS.length)
                    .split(", ")
                    .map(String::toBigInteger)
                    .forEach {
                        monkey.items.add(it)
                    }
            }
            if (line.startsWith(START_OPERATION)) {
                val operation = line.substring(START_OPERATION.length)

                val operator = operation[4]
                if (operator == '+') {
                    monkey.operator = Operator.ADDITION
                } else {
                    monkey.operator = Operator.MULTIPLICATION
                }

                val operand = operation.substring(6)
                if (operand == "old") {
                    monkey.operandDynamic = true
                } else {
                    monkey.operand = operand.toBigInteger()
                }
            }
            if (line.startsWith(START_TEST)) {
                monkey.test = line.substring(START_TEST.length).toBigInteger()
            }
            if (line.startsWith(START_TEST_TRUE)) {
                monkey.targetMonkeyTestTrue = line.substring(START_TEST_TRUE.length).toInt()
            }
            if (line.startsWith(START_TEST_FALSE)) {
                monkey.targetMonkeyTestFalse = line.substring(START_TEST_FALSE.length).toInt()
            }

            if (line.isBlank()) {
                monkeys.add(monkey)
            }
        }
    monkeys.add(monkey)

    return monkeys

}

