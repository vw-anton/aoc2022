import java.util.Stack

fun main() {
    val (stack_raw, instructions_raw) = readFile("input/5-test.txt")
        .splitBy { it.isBlank() }
        .toPair()

    val stacks = parseStacks(stack_raw)
    val instructions = parseInstructions(instructions_raw)

    /*
    part1(stacks, instructions)
    var result = stacks.map { it.last() }.joinToString(separator = "") { it.toString() }
    println("part 1: $result")
    check(result == "BSDMQFLSP" || result == "CMZ")
*/
    part2(stacks, instructions)
    val result = stacks.map { it.last() }.joinToString(separator = "") { it.toString() }
    println("part 2: $result")
    assert(result == "PGSQBFLDP" || result == "MCD")
}

private fun part1(stacks: List<ArrayDeque<Char>>, instructions: List<Instruction>) {
    instructions.forEach { op ->
        repeat(op.count) {
            if (stacks[op.src].isNotEmpty()) {
                val crate = stacks[op.src].removeLast()
                stacks[op.dest].addLast(crate)
            }
        }
    }
}

private fun part2(stacks: List<ArrayDeque<Char>>, instructions: List<Instruction>) {
    instructions.forEach { op ->
        buildList {
            repeat(op.count) {
                if (stacks[op.src].isNotEmpty())
                    add(stacks[op.src].removeLast())
            }
        }
            .reversed()
            .forEach {
                stacks[op.dest].addLast(it)
            }
    }
}

fun parseStacks(input: List<String>): List<ArrayDeque<Char>> {
    val lists = input
        .dropLast(1)
        .reversed()
        .map { line ->
            var index = 1
            buildList {
                while (index <= line.length) {
                    add(line[index])
                    index += 4
                }
            }
        }

    return transpose(lists)
        .map { line -> line.filter { it != ' ' } }
        .map { item -> ArrayDeque(item) }
}

fun parseInstructions(input: List<String>) = input.map {
    Instruction.fromList(it.split(" "))
}

data class Instruction(val count: Int, val src: Int, val dest: Int) {
    companion object {
        fun fromList(input: List<String>) = Instruction(
            count = input[1].toInt(),
            src = input[3].toInt() - 1,
            dest = input[5].toInt() - 1
        )
    }
}