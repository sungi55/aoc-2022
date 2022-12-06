fun main() {

    val day = "Day02"
    val winCombination = setOf("A Y", "B Z", "C X")
    val drawCombination = setOf("B X", "C Y", "A Z")

    fun sumScore(moves: List<String>, isDefaultStrategy: Boolean) =
        moves.map { move ->
            move.takeIf { isDefaultStrategy } ?: move.swapToNewStrategy()
        }.sumOf { move ->
            when (move) {
                in winCombination -> 6
                in drawCombination -> 0
                else -> 3
            }.plus(move.takeLast(1).asMoveBonus())
        }

    fun part1(input: List<String>): Int = sumScore(input, isDefaultStrategy = true)

    fun part2(input: List<String>): Int = sumScore(input, isDefaultStrategy = false)

    val testInput = readInput(name = "${day}_test")
    val input = readInput(name = day)

    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    println(part1(input))
    println(part2(input))
}

private fun String.asMoveBonus() =
    when (this) {
        "X" -> 1
        "Y" -> 2
        else -> 3
    }

private fun String.swapToNewStrategy() =
    take(2).plus(
        when (takeLast(1)) {
            "X" -> first().asLoseMove()
            "Y" -> first().asDrawMove()
            else -> first().asWinMove()
        }
    )

private fun Char.asLoseMove() =
    when (this) {
        'A' -> "Z"
        'B' -> "X"
        else -> "Y"
    }

private fun Char.asDrawMove() =
    when (this) {
        'A' -> "X"
        'B' -> "Y"
        else -> "Z"
    }

private fun Char.asWinMove() =
    when (this) {
        'A' -> "Y"
        'B' -> "Z"
        else -> "X"
    }
