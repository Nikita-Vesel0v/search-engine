package search

import java.io.File

class SearchEngine {
    private var dataBase = mutableMapOf<Int, List<String>>()
    private var resultSearch = mutableListOf<String>()

    fun fillDataBase(fileName: String) {
        val workingDirectory = System.getProperty ("user.dir")
        val separator = File.separator
        val absolutePath = "$workingDirectory${separator}src${separator}resources${separator}$fileName"

        val textFile = File(absolutePath).readLines()

        for (i in textFile.indices) {
            dataBase[i] = textFile[i].split(" ")
        }
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
        if (dataBase.isNotEmpty()) {
            println("\n=== List of people ===")
            dataBase.values.forEach { println(it.joinToString(" ")) }
        }
        else println("\nNo people in data base")
        println()
    }
    private fun clearResultSearch() = resultSearch.clear()
    private fun find() {
        println("\nEnter a name or email to search all suitable people.")
        val query = readln().lowercase()
        dataBase.values.forEach {
            for (word in it) {
                if (word.lowercase() == query) { resultSearch.add(it.joinToString(" ")) }
            }
        }
        printResultOfSearch()
        clearResultSearch()
    }
    private fun printResultOfSearch() {
        if(resultSearch.isNotEmpty()) {
            println("${resultSearch.size} persons found:\n${resultSearch.joinToString("\n")}\n")
        } else {
            println("No matching people found.\n")
        }
    }
}

fun main(args: Array<String>) {
    val searchEngine = SearchEngine()
    searchEngine.fillDataBase(fileName = args[1])
    searchEngine.search()
}