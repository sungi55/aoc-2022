fun main() {

    val day = "Day08"

    fun getTreeGrid(input: List<String>): Grid =
        Grid().apply {
            input.forEachIndexed { rowIndex, row ->
                val treesInRow = row.chunked(1) { "$it".toInt() }
                rows[rowIndex] = treesInRow
                treesInRow.forEachIndexed { columnIndex, treeHeight ->
                    val column = columns[columnIndex] ?: mutableListOf()
                    columns[columnIndex] = column.apply { add(treeHeight) }
                }
            }
        }

    fun part1(lines: List<String>): Int =
        getTreeGrid(lines).let { (rows, columns) ->
            mutableListOf<Int>().apply {
                rows.forEach { (rowIndex, row) ->
                    columns.forEach { (columnIndex, column) ->
                        val rowBeforeTree = row.take(columnIndex)
                        val rowAfterTree = row.takeLast(row.lastIndex - columnIndex)
                        val columnBeforeTree = column.take(rowIndex)
                        val columnAfterTree = column.takeLast(column.lastIndex - rowIndex)
                        val currentTree = row[columnIndex]
                        if (rowBeforeTree.firstOrNull { it >= currentTree } == null
                            || rowAfterTree.firstOrNull { it >= currentTree } == null
                            || columnBeforeTree.firstOrNull { it >= currentTree } == null
                            || columnAfterTree.firstOrNull { it >= currentTree } == null) add(1)
                    }
                }
            }.sum()
        }


    fun part2(lines: List<String>): Int = lines.size

    val testInput = readInput(name = "${day}_test")
    val input = readInput(name = day)

    println(part1(testInput))
    check(part1(testInput) == 21)
//    check(part2(testInput) == 8)

    println(part1(input))
//    println(part2(input))
}

data class Grid(
    val rows: HashMap<Int, List<Int>> = hashMapOf(),
    val columns: HashMap<Int, MutableList<Int>> = hashMapOf()
)