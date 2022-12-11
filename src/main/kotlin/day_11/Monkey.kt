package day_11

import java.math.BigInteger

data class Monkey(
    var itemsInspected: Long = 0,
    var items: MutableList<BigInteger> = mutableListOf(),
    var operator: Operator = Operator.ADDITION,
    var operandDynamic: Boolean = false,
    var operand: BigInteger = BigInteger.ZERO,
    var test: BigInteger = BigInteger.ZERO,
    var targetMonkeyTestTrue: Int = -1,
    var targetMonkeyTestFalse: Int = -1
)

enum class Operator {
    ADDITION,
    MULTIPLICATION
}