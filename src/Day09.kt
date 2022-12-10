import kotlin.math.abs
import kotlin.math.sign

fun main() {

    val day = "Day09"

    fun getHeadSteps(lines: List<String>) =
        sequence {
            var x = 0
            var y = 0
            yield(P(x, y))
            for (line in lines) {
                repeat(line.drop(2).toInt()) {
                    when (line[0]) {
                        'L' -> x--
                        'R' -> x++
                        'U' -> y--
                        'D' -> y++
                    }
                    yield(P(x, y))
                }
            }
        }

    fun part1(lines: List<String>): Int =
        getHeadSteps(lines)
            .moveTail()
            .toSet()
            .count()

    fun part2(lines: List<String>): Int =
        getHeadSteps(lines)
            .moveTail()
            .moveTail()
            .moveTail()
            .moveTail()
            .moveTail()
            .moveTail()
            .moveTail()
            .moveTail()
            .moveTail()
            .toSet()
            .count()

    val testInput = readInput(name = "${day}_test")
    val testInputSecond = readInput(name = "${day}_2_test")
    val input = readInput(name = day)

    check(part1(testInput) == 13)
    check(part2(testInputSecond) == 36)

    println(part1(input))
    println(part2(input))
}

private fun Sequence<P>.moveTail(): Sequence<P> = scan(P(0, 0)) { tail, (x, y) ->
    val dx = x - tail.first
    val dy = y - tail.second
    val absX = abs(dx)
    val absY = abs(dy)
    when {
        absX <= 1 && absY <= 1 -> tail
        absX < absY -> P(x, y - dy.sign)
        absX > absY -> P(x - dx.sign, y)
        else -> P(x - dx.sign, y - dy.sign)
    }
}

data class P(val first: Int, val second: Int)