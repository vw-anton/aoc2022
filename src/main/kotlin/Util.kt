import java.io.File

fun readFile(fileName: String): List<String> = File(fileName).useLines { it.toList() }

fun <E> List<E>.splitBy(predicate: (E) -> Boolean): List<List<E>> =
    this.fold(mutableListOf(mutableListOf<E>())) { acc, element ->
        if (predicate.invoke(element)) {
            acc += mutableListOf<E>()
        } else {
            acc.last() += element
        }
        acc
    }

fun <T> List<T>.toPair(): Pair<T, T> {
    return this.first() to this.last()
}

fun <E> transpose(xs: List<List<E>>): List<List<E>> {
    fun <E> List<E>.head(): E = this.first()
    fun <E> List<E>.tail(): List<E> = this.takeLast(this.size - 1)
    fun <E> E.append(xs: List<E>): List<E> = listOf(this).plus(xs)

    xs.filter { it.isNotEmpty() }.let { ys ->
        return when (ys.isNotEmpty()) {
            true -> ys.map { it.head() }.append(transpose(ys.map { it.tail() }))
            else -> emptyList()
        }
    }
}