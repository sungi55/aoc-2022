fun main() {

    val day = "Day02"
    val winCombination = setOf("A Y", "B Z", "C X")
    val drawCombination = setOf("B X", "C Y", "A Z")

    fun part1(input: List<String>): Int =
        input.sumOf { move ->
            when (move) {
                in winCombination -> 6
                in drawCombination -> 0
                else -> 3
            }.plus(move.takeLast(1).asMoveBonus())
        }

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput(name = "${day}_test")
    val input = readInput(name = day)

    check(part1(testInput) == 15)
//    check(part2(testInput) == 12)

    println(part1(input))
//    println(part2(input))
}

private fun String.asMoveBonus() =
    when (this) {
        "X" -> 1
        "Y" -> 2
        else -> 3
    }
