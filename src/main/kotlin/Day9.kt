import kotlin.math.sign

fun main() {
    val commands = readFile("input/9.txt")
        .map {
            MoveCommand(it.first(), it.substringAfter(" ").toInt())
        }

    val rope1 = Rope(knots = 2)
    val rope2 = Rope(knots = 10)

    commands.forEach { command ->
        rope1.move(command)
        rope2.move(command)
    }

    val result = rope1.tail().log.distinct().count()
    rope1.tail().printLog()
    println("part 1: $result")

    val result2 = rope2.tail().log.distinct().count()
    rope2.tail().printLog()
    println("part 2: $result2")
}

class Rope(knots: Int) {
    private val knots = buildList {
        repeat(knots) {
            add(Knot())
        }
    }

    fun head() = knots.first()

    fun tail() = knots.last()

    fun move(command: MoveCommand) {
        repeat(command.steps) {
            val currentPosition = head().position
            val newPosition = when (command.direction) {
                'R' -> currentPosition.copy(first = currentPosition.first + 1)
                'D' -> currentPosition.copy(second = currentPosition.second - 1)
                'L' -> currentPosition.copy(first = currentPosition.first - 1)
                'U' -> currentPosition.copy(second = currentPosition.second + 1)
                else -> error("")
            }

            head().moveTo(newPosition)

            for (index in 1 until knots.size) {
                val knot = knots[index]
                val previous = knots[index - 1]

                if (!knot.touches(previous.position)) {
                    val distHorizontal = previous.position.first - knot.position.first
                    val distVertical = previous.position.second - knot.position.second

                    val pos = knot.position.apply(Pair(distHorizontal.sign, distVertical.sign))
                    knot.moveTo(pos)
                }
            }
        }

    }
}

fun Pair<Int, Int>.apply(otherPair: Pair<Int, Int>): Pair<Int, Int> {
    return copy(first = this.first + otherPair.first, second = this.second + otherPair.second)
}

data class MoveCommand(val direction: Char, val steps: Int)

data class Knot(
    var position: Pair<Int, Int> = 0 to 0,
    val log: MutableList<Pair<Int, Int>> = mutableListOf(0 to 0)
) {
    fun moveTo(newPosition: Pair<Int, Int>) {
        log.add(newPosition)
        position = newPosition
    }

    fun touches(newPosition: Pair<Int, Int>): Boolean =
        (position.first in newPosition.first - 1..newPosition.first + 1
                && position.second in newPosition.second - 1..newPosition.second + 1)

    fun printLog() {
        val distinct = log.distinct()

        //find bounds
        val left = distinct.minBy { it.first }.first
        val right = distinct.maxBy { it.first }.first
        val top = distinct.maxBy { it.second }.second
        val bottom = distinct.minBy { it.second }.second

        println("$left $top $right $bottom")

        val result = mutableListOf<String>()
        for (row in bottom..top) {
            val sb = StringBuilder()
            for (col in left..right) {
                val pair = distinct.find { it.first == col && it.second == row }
                if (pair != null) {
                    if (pair == 0 to 0)
                        sb.append("s")
                    else
                        sb.append("#")
                } else sb.append(".")
            }
            result.add(sb.toString())
        }

        result.reversed().forEach {
            println(it)
        }
    }
}



