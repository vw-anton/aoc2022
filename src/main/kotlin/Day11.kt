fun main() {
    val monkeys = buildList<Monkey> {
        add(Monkey(
            items = mutableListOf(72, 64, 51, 57, 93, 97, 68),
            operation = { it * 19 },
            test = { (it % 17).toInt() == 0 },
            handling = { if (it) 4 else 7 },
            receive = { monkey, level -> this[monkey].items.add(level) }
        ))
        add(Monkey(
            items = mutableListOf(62),
            operation = { it * 11 },
            test = { (it % 3).toInt() == 0 },
            handling = { if (it) 3 else 2 },
            receive = { monkey, level -> this[monkey].items.add(level) }
        ))
        add(Monkey(
            items = mutableListOf(57, 94, 69, 79, 72),
            operation = { it + 6 },
            test = { (it % 19).toInt() == 0 },
            handling = { if (it) 0 else 4 },
            receive = { monkey, level -> this[monkey].items.add(level) }
        ))
        add(Monkey(
            items = mutableListOf(80, 64, 92, 93, 64, 56),
            operation = { it + 5 },
            test = { (it % 7).toInt() == 0 },
            handling = { if (it) 2 else 0 },
            receive = { monkey, level -> this[monkey].items.add(level) }
        ))
        add(Monkey(
            items = mutableListOf(70, 88, 95, 99, 78, 72, 65, 94),
            operation = { it + 7 },
            test = { (it % 2).toInt() == 0 },
            handling = { if (it) 7 else 5 },
            receive = { monkey, level -> this[monkey].items.add(level) }
        ))
        add(Monkey(
            items = mutableListOf(57, 95, 81, 61),
            operation = { it * it },
            test = { (it % 5).toInt() == 0 },
            handling = { if (it) 1 else 6 },
            receive = { monkey, level -> this[monkey].items.add(level) }
        ))
        add(Monkey(
            items = mutableListOf(79, 99),
            operation = { it + 2 },
            test = { (it % 11).toInt() == 0 },
            handling = { if (it) 3 else 1 },
            receive = { monkey, level -> this[monkey].items.add(level) }
        ))
        add(Monkey(
            items = mutableListOf(68, 98, 62),
            operation = { it + 3 },
            test = { (it % 13).toInt() == 0 },
            handling = { if (it) 5 else 6 },
            receive = { monkey, level -> this[monkey].items.add(level) }
        ))
    }
    val mod = 17 * 3 * 19 * 7 * 2 * 5 * 11 * 13L

    repeat(10_000) { round ->
        println("---")
        println("Starting round ${round + 1}")
        monkeys.forEach { it.inspect(mod) }
        monkeys.forEachIndexed { idx, monkey ->
            println("Monkey $idx: " + monkey.items)
        }
    }

    monkeys.forEachIndexed { idx, monkey ->
        println("Monkey inspected ${monkey.inspectionCounter}")
    }

    val result = monkeys
        .map { it.inspectionCounter }
        .sortedDescending()
        .take(2)
        .fold(1L) { acc, monkey -> acc * monkey }

    println(result)
}

private class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val handling: (Boolean) -> Int,
    val receive: (Int, Long) -> Unit
) {

    var inspectionCounter = 0

    fun inspect(mod: Long) {
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            inspectionCounter++

            val item = iterator.next()
            iterator.remove()
            val worryLevel = operation(item)
            val target = handling(test(worryLevel))
            receive(target, worryLevel % mod)
        }
    }
}