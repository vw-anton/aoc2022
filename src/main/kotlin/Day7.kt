fun main() {
    val parsed = readFile("input/7.txt")
        .drop(1)
        .map { line ->
            when {
                line == "\$ cd /" -> Directory(name = "/")
                line.startsWith("$") -> Command.from(line)
                line.first().isDigit() -> File.from(line)
                else -> Directory.from(line)
            }
        }

    val root = Directory(name = "/")
    var currentDir = root
    parsed.forEach { item ->
        when (item) {
            is File -> currentDir.childs.add(item)
            is Directory -> currentDir.childs.add(item)
            is Command -> {
                when {
                    item.isMoveUp() -> currentDir = currentDir.parent!!
                    item.isMoveDown() -> {
                        currentDir =
                            currentDir.childs
                                .filterIsInstance<Directory>()
                                .first { it.name == item.param }
                                .also { newDir -> newDir.parent = currentDir }
                    }
                }
            }
        }
    }

    println(part1(parsed))
    println(part2(root, parsed))
}

private fun part1(parsed: List<Any>): Int {
    return parsed
        .filterIsInstance<Directory>()
        .filter { it.getSize() < 100_000 }
        .sumOf { it.getSize() }
}

private fun part2(root: Directory, parsed: List<Any>): Int {
    val totalSpace = 70_000_000
    val freeSpaceRequired = 30_000_000
    val usedSpace = root.getSize()
    val freeSpace = totalSpace - usedSpace
    val requiredSpace = freeSpaceRequired - freeSpace

    return parsed
        .filterIsInstance<Directory>()
        .filter { it.getSize() >= requiredSpace }
        .minOf { it.getSize() }
}

data class Command(val command: String, val param: String) {

    fun isMoveUp() = command == "cd" && param == ".."

    fun isMoveDown() = command == "cd" && param != ".."

    companion object {
        fun from(input: String): Command {
            val split = input.split(" ")
            return Command(command = split[1], param = split.getOrElse(2) { "" })
        }
    }
}

data class Directory(
    val childs: MutableList<Any> = mutableListOf(),
    val name: String,
    var parent: Directory? = null
) {

    fun getSize(): Int {
        return childs.sumOf {
            when (it) {
                is Directory -> it.getSize()
                is File -> it.size
                else -> 0
            }
        }
    }

    override fun toString(): String {
        return "Directory(name='$name')"
    }

    companion object {
        fun from(line: String): Directory {
            val split = line.split(" ")
            return Directory(name = split[1])

        }
    }
}

data class File(val size: Int, val name: String) {
    companion object {
        fun from(input: String): File {
            val split = input.split(" ")
            return File(size = split[0].toInt(), name = split[1])
        }
    }
}