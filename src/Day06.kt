fun main() {

    val day = "Day06"

    fun part1(input: String): Int = input.getMarkerIndex(4)

    fun part2(input: String): Int = input.getMarkerIndex(14)

    val input = readInputText(name = day)

    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)
    check(part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 19)
    check(part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)

    println(part1(input))
    println(part2(input))
}

private fun String.getMarkerIndex(markerSize: Int) =
    windowed(markerSize)
        .indexOfFirst { it.toSet().size == markerSize }
        .plus(markerSize)