
package example.pley.model

import kotlin.random.Random

class GameModel {
    private var targetNumber: Int = Random.nextInt(1, 51)
    private var score: Int = 0
    private var roundsPlayed: Int = 0
    private val maxRounds: Int = 5

    fun getTargetNumber(): Int {
        return targetNumber
    }

    fun guess(sliderValue: Int): Int {
        if (isGameOver()) {
            return 0 // Игра окончена, не начисляем очки
        }

        val difference = Math.abs(targetNumber - sliderValue)
        val points = when (difference) {
            0 -> 10
            in 1..5 -> 7
            in 6..10 -> 5
            in 11..20 -> 3
            else -> 0
        }
        score += points
        roundsPlayed++

        if (!isGameOver()) {
            targetNumber = Random.nextInt(1, 51) // Обновляем только если игра продолжается
        }
        
        return points
    }

    fun isGameOver(): Boolean {
        return roundsPlayed >= maxRounds
    }

    fun getScore(): Int {
        return score
    }

    fun resetGame() {
        score = 0
        roundsPlayed = 0
        targetNumber = Random.nextInt(1, 51)
    }
}

