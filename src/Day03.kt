fun main() {

    val day = "Day03"

    fun part1(input: List<String>): Int =
        input.map { rucksack -> rucksack.chunked(rucksack.length / 2) }
            .sumOf { rucksack ->
                val compartment1 = rucksack.component1().toSet()
                val compartment2 = rucksack.component2().toSet()
                compartment1.intersect(compartment2)
                    .first()
                    .asNumber()
            }

    fun part2(input: List<String>): Int =
        input.chunked(3)
            .sumOf { (rucksacks1, rucksacks2, rucksacks3) ->
                rucksacks1.toSet()
                    .intersect(rucksacks2.toSet())
                    .intersect(rucksacks3.toSet())
                    .single()
                    .asNumber()
            }

    val testInput = readInput(name = "${day}_test")
    val input = readInput(name = day)

    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    println(part1(input))
    println(part2(input))
}

private fun Char.asNumber(): Int =
    when {
        isLowerCase() -> ('a'..'z').indexOf(this)
        else -> ('A'..'Z').indexOf(this).plus(26)
    }.plus(1)