fun main() {

    val instructions = readFile("input/10.txt")
        .map { CpuInstruction.from(it) }

    var spriteCenter = 1
    var cycle = 0

    val cycleValues = mutableMapOf<Int, Int>()
    val pixels = mutableListOf<String>()

    instructions.forEach {
        repeat(it.cycles) { innerCycle ->
            cycle++
            cycleValues[cycle] = spriteCenter

            val pixel = pixels.size % 40
            if (pixel in spriteCenter - 1..spriteCenter + 1)
                pixels.add("#")
            else
                pixels.add(".")

            if (it.x != 0 && innerCycle == it.cycles - 1) {
                spriteCenter += it.x
            }
        }
    }

    pixels.windowed(40, 40)
        .forEach {
            println(it.joinToString(separator = ""))
        }

    val sum = cycleValues
        .filter { entry ->
            entry.key in listOf(20, 60, 100, 140, 180, 220)
        }
        .map { entry -> entry.key * entry.value }
        .sum()

    println("sum: $sum")
}

data class CpuInstruction(val cycles: Int = 0, val x: Int = 0) {
    companion object {
        fun from(input: String): CpuInstruction {
            val split = input.split(" ")
            return when (split.first()) {
                "noop" -> CpuInstruction(1)
                "addx" -> CpuInstruction(2, split.last().toInt())
                else -> error("invalid input")
            }
        }
    }
}