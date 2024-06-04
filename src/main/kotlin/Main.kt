package search

fun main() {
    val words = readln().split(" ")
    val wordIndex = words.indexOf(readln())
    println(if (wordIndex != -1) wordIndex + 1; else "Not found")
}
