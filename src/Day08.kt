fun main() {

    val day = "Day08"

    fun getTreeGrid(input: List<String>): Grid =
        Grid().apply {
            input.forEachIndexed { rowIndex, row ->
                val treesInRow = row.map { it.digitToInt() }
                rows[rowIndex] = treesInRow
                treesInRow.forEachIndexed { columnIndex, treeHeight ->
                    val column = columns[columnIndex] ?: mutableListOf()
                    columns[columnIndex] = column.apply { add(treeHeight) }
                }
            }
        }

    fun onEachTree(grid: Grid, action: (TreeView) -> Unit) {
        grid.rows.forEach { (rowIndex, row) ->
            grid.columns.forEach { (columnIndex, column) ->
                action(
                    TreeView(
                        currentTree = row[columnIndex],
                        up = column.take(rowIndex).reversed(),
                        down = column.takeLast(column.lastIndex - rowIndex),
                        left = row.take(columnIndex).reversed(),
                        fight = row.takeLast(row.lastIndex - columnIndex)
                    )
                )
            }
        }
    }

    fun part1(lines: List<String>): Int =
        getTreeGrid(lines).let { grid ->
            mutableListOf<Int>().apply {
                onEachTree(grid) { (current, up, down, left, right) ->
                    if (left.firstOrNull { it >= current } == null
                        || right.firstOrNull { it >= current } == null
                        || up.firstOrNull { it >= current } == null
                        || down.firstOrNull { it >= current } == null) add(1)
                }
            }.sum()
        }

    fun List<Int>.takeUntil(predicate: (Int) -> Boolean): List<Int> {
        val list = ArrayList<Int>()
        for (item in this) {
            list.add(item)
            if (predicate(item))
                break
        }
        return list
    }

    fun part2(lines: List<String>): Int = getTreeGrid(lines).let { grid ->
        mutableListOf<Int>().apply {
            onEachTree(grid) { (current, up, down, left, right) ->
                val l = left.takeUntil { it >= current }.count()
                val r = right.takeUntil { it >= current }.count()
                val u = up.takeUntil { it >= current }.count()
                val d = down.takeUntil { it >= current }.count()
                add(l * r * u * d)
            }
        }.max()
    }

    val testInput = readInput(name = "${day}_test")
    val input = readInput(name = day)

    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    println(part1(input))
    println(part2(input))
}

data class Grid(
    val rows: HashMap<Int, List<Int>> = hashMapOf(),
    val columns: HashMap<Int, MutableList<Int>> = hashMapOf()
)

data class TreeView(
    val currentTree: Int,
    val up: List<Int>,
    val down: List<Int>,
    val left: List<Int>,
    val fight: List<Int>,
)