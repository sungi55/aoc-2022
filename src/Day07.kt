private const val CD = "$ cd "
private const val LS = "$ ls"
private const val DIR = "dir"

fun main() {

    val day = "Day07"
    val directorySizes: MutableList<Int> = mutableListOf()

    fun getRootNode(input: List<String>): Node {
        val root = Node("/", null)
        var current = root
        input.forEach { command ->
            when {
                command.startsWith(CD) -> {
                    current = when {
                        command.endsWith("/") -> root
                        command.endsWith("..") -> current.parent!!
                        else -> current.children.first {
                            it.name == command.substringAfter(CD)
                        }
                    }
                }

                command.startsWith(LS) -> {

                }

                command.startsWith(DIR) -> current.children.add(
                    Node(command.substringAfter(" "), current)
                )

                else -> current.size += command.substringBefore(" ").toInt()
            }
        }
        return root
    }

    fun recursiveSizes(node: Node): Int {
        directorySizes.add(node.size + node.children.sumOf(::recursiveSizes))
        return directorySizes.last()
    }

    fun part1(input: List<String>): Int {
        val root = getRootNode(input)
        recursiveSizes(root)
        return directorySizes.filter { it <= 100000 }.sum().also {
            directorySizes.clear()
        }
    }

    fun part2(input: List<String>): Int {
        val root = getRootNode(input)
        recursiveSizes(root)
        val max = directorySizes.max()
        val available = 70000000
        val unused = 30000000
        val required = unused - (available - max)
        return directorySizes.filter { it >= required }.min().also {
            directorySizes.clear()
        }
    }

    val testInput = readInput(name = "${day}_test")
    val input = readInput(name = day)

    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    println(part1(input))
    println(part2(input))
}

data class Node(
    val name: String,
    val parent: Node?,
) {
    var size: Int = 0
    var children: MutableList<Node> = mutableListOf()
}