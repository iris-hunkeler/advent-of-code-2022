package day_02

import InputType
import java.io.File
import java.lang.IllegalArgumentException

fun main(args: Array<String>) {
    val input = getInput(InputType.INPUT)

    val scoreStrategy1 = evaluateWithStrategy1(input)
    println("*** Result part 1: $scoreStrategy1 ***")

    val scoreStrategy2 = evaluateWithStrategy2(input)
    println("*** Result part 2: $scoreStrategy2 ***")
}

private fun evaluateWithStrategy1(input: List<Pair<String, String>>): Int {
    var score = 0
    input.forEach { (opponent, player) ->
        score += evaluateGame(RockPaperScissors.getByOpponent(opponent), RockPaperScissors.getByPlayer(player))
    }
    return score
}

private fun evaluateWithStrategy2(input: List<Pair<String, String>>): Int {
    var score = 0
    input.forEach { (opponent, goal) ->
        score += evaluateGame(RockPaperScissors.getByOpponent(opponent), getAnswerForStrategy2(RockPaperScissors.getByOpponent(opponent), goal))
    }
    return score
}

fun evaluateGame(opponent: RockPaperScissors, player: RockPaperScissors): Int {
    return getWinningPoints(opponent, player) + player.points
}

private fun getWinningPoints(opponent: RockPaperScissors, player: RockPaperScissors): Int {
    return if (player == RockPaperScissors.ROCK && opponent == RockPaperScissors.SCISSORS
            || player == RockPaperScissors.PAPER && opponent == RockPaperScissors.ROCK
            || player == RockPaperScissors.SCISSORS && opponent == RockPaperScissors.PAPER) {
        6
    } else if (player == opponent) {
        3
    } else {
        0
    }
}

private fun getAnswerForStrategy2(opponent: RockPaperScissors, goal: String): RockPaperScissors {
    return if (goal == "X") {
        getLosingAnswer(opponent)
    } else if(goal == "Y") {
        opponent
    } else if(goal == "Z") {
        getWinningAnswer(opponent)
    } else {
        throw IllegalArgumentException("No goal found for $goal")
    }
}

private fun getWinningAnswer(opponent: RockPaperScissors): RockPaperScissors {
    return when (opponent) {
        RockPaperScissors.ROCK -> RockPaperScissors.PAPER
        RockPaperScissors.PAPER -> RockPaperScissors.SCISSORS
        RockPaperScissors.SCISSORS -> RockPaperScissors.ROCK
    }
}

private fun getLosingAnswer(opponent: RockPaperScissors): RockPaperScissors {
    return when (opponent) {
        RockPaperScissors.ROCK -> RockPaperScissors.SCISSORS
        RockPaperScissors.PAPER -> RockPaperScissors.ROCK
        RockPaperScissors.SCISSORS -> RockPaperScissors.PAPER
    }
}

private fun getInput(inputType: InputType): List<Pair<String, String>> {
    return File("src/main/kotlin/day_02/${inputType.fileName}.txt")
            .readLines()
            .map {
                val play = it.split(" ")
                Pair(play[0], play[1])
            }
}