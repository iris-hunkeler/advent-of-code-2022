package day_02

import java.lang.IllegalArgumentException

enum class RockPaperScissors(
        private val opponent: String,
        private val player: String,
        val points: Int
) {
    ROCK("A", "X", 1),
    PAPER("B", "Y", 2),
    SCISSORS("C", "Z", 3);

    companion object {
        fun getByOpponent(opponent: String): RockPaperScissors {
            return RockPaperScissors.values().find { it.opponent == opponent }
                    ?: throw IllegalArgumentException("Cannot find value for $opponent")
        }

        fun getByPlayer(player: String): RockPaperScissors {
            return RockPaperScissors.values().find { it.player == player }
                    ?: throw IllegalArgumentException("Cannot find value for $player")
        }
    }
}