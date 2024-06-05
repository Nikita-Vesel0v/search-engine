package search

class SearchEngine {
    private var dataBase = mutableListOf<String>()
    private var resultSearch = mutableListOf<String>()

    fun fillDataBase() {
        println("Enter the number of people:")
        val size = readln().toInt()

        println("Enter all people:")
        for (i in 0 until size) dataBase.add(readln())
    }
    fun start() {
        while (true) {
            println("""
                
                === Menu ===
                1. Find a person
                2. Print all people
                0. Exit
            """.trimIndent())
            when (readln()) {
                "1" -> find()
                "2" -> printDatabase()
                "0" -> break
                else -> println("\nIncorrect option! Try again.")
            }
        }
        println("\nBye!")

    }

    private fun printDatabase() {
        if (dataBase.isNotEmpty()) println("\n=== List of people ===\n${dataBase.joinToString("\n")}")
        else println("\nNo people in data base")
    }
    private fun clearResultSearch() = resultSearch.clear()
    private fun find() {
        println("\nEnter a name or email to search all suitable people.")
        val query = readln()
        dataBase.forEach { if (it.lowercase().contains(query.lowercase())) { resultSearch.add(it)} }
        printResultOfSearch()
        clearResultSearch()
    }
    private fun printResultOfSearch() {
        if(resultSearch.isNotEmpty()) {
            println(resultSearch.joinToString("\n"))
        } else {
            println("No matching people found.")
        }
    }
}

fun main() {
    val searchEngine = SearchEngine()
    searchEngine.fillDataBase()
    searchEngine.start()
}