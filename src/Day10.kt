fun main() {

    val day = "Day10"
    val oneCycleCommand = "noop"
    val twoCycleCommand = "addx"

    fun part1(lines: List<String>): Int {
        val commands = lines.iterator()
        var signalStrength = 1
        var currentCycle = 0

        return listOf(20, 60, 100, 140, 180, 220).sumOf { nextCycle ->
            var currentStrength = signalStrength
            while (currentCycle < nextCycle) {
                currentStrength = signalStrength
                val command = commands.next()
                when {
                    command.startsWith(oneCycleCommand) -> currentCycle++
                    command.startsWith(twoCycleCommand) -> {
                        currentCycle += 2
                        signalStrength += command.substringAfter(" ").toInt()
                    }
                }
            }
            nextCycle * currentStrength
        }
    }

    fun part2(lines: List<String>): String = buildList {
        val commands = lines.iterator()
        var currentCycle = 0
        var signalStrength = 1

        repeat(6) { row ->
            val line = buildString {
                repeat(40) { column ->
                    append(if (signalStrength - column in -1..1) '#' else '.')
                    while (currentCycle < 40 * row + column) {
                        val command = commands.next()
                        when {
                            command.startsWith(oneCycleCommand) -> currentCycle++
                            command.startsWith(twoCycleCommand) -> {
                                currentCycle += 2
                                signalStrength += command.substringAfter(" ").toInt()
                            }
                        }
                    }
                }
            }
            add(line)
        }
    }.joinToString("\n")

    val testInput = readInput(name = "${day}_test")
    val input = readInput(name = day)

    check(part1(testInput) == 13140)
    println(part2(testInput))

    println(part1(input))
    println(part2(input))
}