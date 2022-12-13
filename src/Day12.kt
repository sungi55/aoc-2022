import java.util.*

fun main() {

    val day = "Day12"
    val testGraph: Graph = readInput(name = "${day}_test").asGraph()
    val graph: Graph = readInput(name = "Day12").asGraph()

    fun part1(graph: Graph): Int = graph.bfs(
        start = graph.start,
        isComplete = { it == graph.destination },
        isNotBlocked = { from, to -> to - from <= 1 }
    )

    fun part2(graph: Graph): Int = graph.bfs(
        start = graph.destination,
        isComplete = { graph.elevations[it] == 0 },
        isNotBlocked = { from, to -> from - to <= 1 }
    )

    check(part1(testGraph) == 31)
    check(part2(testGraph) == 29)

    println(part1(graph))
    println(part2(graph))
}

data class Position(
    val x: Int = 0, val y: Int = 0
) {
    fun neighbors(): Set<Position> = setOf(
        copy(x = x - 1), copy(x = x + 1), copy(y = y - 1), copy(y = y + 1)
    )
}

data class Path(
    val position: Position, val weight: Int
) : Comparable<Path> {

    override fun compareTo(other: Path): Int = weight.compareTo(other.weight)
}

class Graph(
    var start: Position,
    var destination: Position,
    var elevations: Map<Position, Int>,
) {

    fun bfs(
        start: Position,
        isComplete: (Position) -> Boolean,
        isNotBlocked: (Int, Int) -> Boolean
    ): Int {
        val visitedPosition = mutableSetOf<Position>()
        val queue = PriorityQueue<Path>().apply { add(Path(start, 0)) }

        while (queue.isNotEmpty()) {
            val nextPath = queue.poll()
            nextPath.position
                .takeIf { it !in visitedPosition }
                ?.also { visitedPosition.add(it) }
                ?.neighbors()
                ?.filter { it in elevations }
                ?.filter { isNotBlocked(elevations[nextPath.position]!!, elevations[it]!!) }
                ?.also { neighbors ->
                    when (neighbors.any { isComplete(it) }) {
                        true -> return nextPath.weight + 1
                        else -> queue.addAll(neighbors.map { Path(it, nextPath.weight + 1) })
                    }
                }
        }
        return -1
    }
}

fun List<String>.asGraph(): Graph = let {
    var start = Position()
    var destination = Position()
    val elevations = it.flatMapIndexed { y, row ->
        row.mapIndexed { x, height ->
            val currentPosition = Position(x, y)
            currentPosition to when (height) {
                'S' -> 0.also { start = currentPosition }
                'E' -> 25.also { destination = currentPosition }
                else -> height - 'a'
            }
        }
    }.toMap()
    Graph(start, destination, elevations)
}

