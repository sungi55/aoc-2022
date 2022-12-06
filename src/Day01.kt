fun main() {

    val day = "Day01"

    fun part1(input: String): Int = input.sumTopOfCalories(1)

    fun part2(input: String): Int  = input.sumTopOfCalories(3)

    val testInput = readInputText(name = "${day}_test")
    val input = readInputText(name = day)

    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    println(part1(input))
    println(part2(input))
}
fun String.sumTopOfCalories(top: Int) =
    split("\n\n")
        .map { it.sumCaloriesInGroup() }
        .sortedDescending()
        .take(top)
        .sum()
fun String.sumCaloriesInGroup() = lines().sumOf { calorie -> calorie.toInt() }
