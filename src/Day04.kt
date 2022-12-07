fun main() {

    val day = "Day04"

    fun getRanges(input: List<String>) =
        input.map { set ->
            set.split(",")
                .map { ranges -> ranges.split("-").map { it.toInt() } }
                .map { (it.component1()..it.component2()).toList() }
        }

    fun part1(input: List<String>): Int =
        getRanges(input)
            .count { (firstElf, secondElf) ->
                firstElf.containsAll(secondElf) || secondElf.containsAll(firstElf)
            }

    fun part2(input: List<String>): Int =
        getRanges(input)
            .count { (firstElves, secondElves) ->
                firstElves.intersect(secondElves.toSet()).isNotEmpty()
            }

    val testInput = readInput(name = "${day}_test")
    val input = readInput(name = day)

    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    println(part1(input))
    println(part2(input))
}