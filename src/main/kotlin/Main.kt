package search

import java.io.File

class SearchEngine {
    private var dataBase = mutableListOf<String>()
    private var resultSearch = mutableListOf<String>()

    fun fillDataBase(fileName: String) {
        val workingDirectory = System.getProperty ("user.dir")
        val separator = File.separator
        val absolutePath = "${workingDirectory}${separator}src${separator}main${separator}resources${separator}$fileName"

        File(absolutePath).readLines().forEach { dataBase.add(it) }
    }
    fun search() {
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
        if (dataBase.isNotEmpty()) println("\n=== List of people ===\n${dataBase.joinToString("\n")}\n")
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

fun main(args: Array<String>) {
    val searchEngine = SearchEngine()
    searchEngine.fillDataBase(args[1])
    searchEngine.search()
}