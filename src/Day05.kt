fun main() {

    val day = "Day05"

    val testPackages = mapOf<Int, List<String>>(
        1 to mutableListOf("N", "Z"),
        2 to mutableListOf("D", "C", "M"),
        3 to mutableListOf("P")
    )

    val inputPackages = mapOf<Int, List<String>>(
        1 to mutableListOf("N", "R", "J", "T", "Z", "B", "D", "F"),
        2 to mutableListOf("H", "J", "N", "S", "R"),
        3 to mutableListOf("Q", "F", "Z", "G", "J", "N", "R", "C"),
        4 to mutableListOf("Q", "T", "R", "G", "N", "V", "F"),
        5 to mutableListOf("F", "Q", "T", "L"),
        6 to mutableListOf("N", "G", "R", "B", "Z", "W", "C", "Q"),
        7 to mutableListOf("M", "H", "N", "S", "L", "C", "F"),
        8 to mutableListOf("J", "T", "M", "Q", "N", "D"),
        9 to mutableListOf("S", "G", "P")
    )

    fun getBottomPackages(
        packages: Map<Int, List<String>>,
        commands: List<String>,
        isReverseOrdering: Boolean
    ): String {
        val stack = packages.toMutableMap()
        return commands.map {
            it.split(" ").let { move ->
                Triple(move[1].toInt(), move[3].toInt(), move[5].toInt())
            }
        }.forEach { (amount, from, to) ->
            val fromColumn = stack[from]!!.take(amount)
            val toColumn = fromColumn.let { if (isReverseOrdering) it.reversed() else it }.plus(stack[to]!!)
            val newFromColumn = stack[from]!!.drop(amount)
            stack[from] = newFromColumn
            stack[to] = toColumn
        }.let {
            stack.values.joinToString("") { it.first() }
        }
    }

    fun part1(packages: Map<Int, List<String>>, commands: List<String>): String =
        getBottomPackages(packages, commands, isReverseOrdering = true)

    fun part2(packages: Map<Int, List<String>>, commands: List<String>): String =
        getBottomPackages(packages, commands, isReverseOrdering = false)

    val testInput = readInput(name = "${day}_test")
    val inputCommands = readInput(name = day)

    check(part1(testPackages, testInput) == "CMZ")
    check(part2(testPackages, testInput) == "MCD")

    println(part1(inputPackages, inputCommands))
    println(part2(inputPackages, inputCommands))
}