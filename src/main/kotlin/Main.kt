package search

fun main() {
    println("Enter the number of people:")
    val size = readln().toInt()

    println("Enter all people:")
    val db = List(size) { readln() }

    println("\nEnter the number of search queries:")
    val numberQuery = readln().toInt()

    var wordFind: String
    var found: Boolean
    val resultSearch = mutableListOf<String>()

    for (i in 1..numberQuery) {
        println("\nEnter data to search people:")
        wordFind = readln().lowercase()
        found = false
        db.forEach { if (it.lowercase().contains(wordFind)) { resultSearch.add(it); found = true } }

        println(if(!found) {
            "No matching people found."
        } else {
            "\nPeople found:\n${resultSearch.joinToString(separator = "\n")}"
        })
        resultSearch.clear()
    }
}

