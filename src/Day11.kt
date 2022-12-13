fun main() {

    val day = "Day11"

    fun getMonkey(index: Int, lines: List<String>, decreaseWorryStrategy: (Long) -> Long): Monkey {
        val (sign, increaseBy) = lines[index + 2]
            .substringAfter("old ")
            .split(" ")
            .let {
                it.first() to if (it.last() == "old") null else it.last().toLong()
            }
        return Monkey(
            items = lines[index + 1]
                .substringAfter(": ")
                .split(", ")
                .map { it.toLong() }
                .toMutableList(),
            worryLevel = Monkey.WorryLevel(
                divisibleValue = lines[index + 3].substringAfterLast(" ").toLong(),
                increaseBy = increaseBy,
                increaseSign = sign,
                decreaseBy = decreaseWorryStrategy
            ),
            moveFirst = lines[index + 4].substringAfterLast(" ").toInt(),
            moveSecond = lines[index + 5].substringAfterLast(" ").toInt()
        )
    }

    fun getMonkeys(lines: List<String>, decreaseWorryStrategy: (Long) -> Long = { 0L }) =
        mutableMapOf<Int, Monkey>().apply {
            for (input in lines.indices step 7) {
                val monkeyPoint = lines[input].dropLast(1).substringAfterLast(" ").toInt()
                this[monkeyPoint] = getMonkey(input, lines, decreaseWorryStrategy)
            }
        }

    fun getMonkeysBusiness(lines: List<String>, rounds: Int, decreaseWorryStrategy: (Long) -> Long) =
        getMonkeys(lines, decreaseWorryStrategy).apply {
            repeat(rounds) {
                values.forEach { monkey ->
                    monkey.items.indices.onEach {
                        monkey.inspectAndThrow()?.let { (monkeyPoint, pack) ->
                            this[monkeyPoint]?.catchPack(pack)
                        }
                    }
                }
            }
        }.withMostBusiness()


    fun part1(lines: List<String>): Long = getMonkeysBusiness(lines, 20) { it.div(3) }

    fun part2(lines: List<String>): Long {
        val decreaseWorryStrategy = getMonkeys(lines).values.map { it.worryLevel.divisibleValue }.reduce(Long::times)
        return getMonkeysBusiness(lines, 10000) { it % decreaseWorryStrategy }
    }

    val testInput = readInput(name = "${day}_test")
    val input = readInput(name = day)

    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158)

    println(part1(input))
    println(part2(input))
}

private data class Monkey(
    val items: MutableList<Long>,
    val worryLevel: WorryLevel,
    val moveFirst: Int,
    val moveSecond: Int,
    var packInspected: Int = 0,
) {

    data class WorryLevel(
        val divisibleValue: Long,
        val increaseSign: String,
        val increaseBy: Long?,
        val decreaseBy: (Long) -> Long
    )

    fun catchPack(pack: Long): Monkey = apply { items.add(pack) }

    fun inspectAndThrow(): Pair<Int, Long>? = startInspection().completeInspection().throwPack()

    private fun startInspection(): Monkey = apply {
        items.firstOrNull()?.also { pack ->
            items.removeFirst()
            items.add(0, worryLevel.applySignWith(pack))
            packInspected++
        }
    }

    private fun WorryLevel.applySignWith(pack: Long) =
        when (increaseSign) {
            "+" -> (increaseBy ?: pack) + pack
            "-" -> (increaseBy ?: pack) - pack
            "*" -> (increaseBy ?: pack) * pack
            else -> (increaseBy ?: pack) / pack
        }

    private fun completeInspection() = apply {
        items.firstOrNull()?.also { pack ->
            items.removeFirst()
            items.add(0, worryLevel.decreaseBy(pack))
        }
    }

    private fun throwPack(): Pair<Int, Long>? = when {
        items.isEmpty() -> null
        items.first().passWorryLevel() -> moveFirst to items.first()
        else -> moveSecond to items.first()
    }.also {
        items.removeFirstOrNull()
    }

    private fun Long.passWorryLevel() = this % worryLevel.divisibleValue == 0L
}

private fun Map<Int, Monkey>.withMostBusiness() =
    values
        .sortedByDescending { it.packInspected }
        .take(2)
        .map { it.packInspected }
        .let { it[0].toLong() * it[1] }