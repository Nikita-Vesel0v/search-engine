package search

import java.io.File

class SearchEngine {
    private var dataBase = mutableMapOf<String, MutableList<Int>>()
    private var resultSearch = mutableListOf<Int>()
    private var fileData = listOf<String>()

    fun fillDataBase(fileName: String) {
        val workingDirectory = System.getProperty ("user.dir")
        val separator = File.separator
        val absolutePath = "${workingDirectory}${separator}src${separator}main${separator}resources${separator}$fileName"
        var index = 0

        fileData = File(absolutePath).readLines()
        fileData.forEach {
            val line = it.split(" ")
            for (word in line) {
                if (!dataBase.containsKey(word.lowercase())) {
                    dataBase[word.lowercase()] = mutableListOf(index)
                } else {
                    dataBase[word.lowercase()]?.add(index)
                }
            }
            index++
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
        if (fileData.isNotEmpty()) {
            println("\n=== List of people ===")
            fileData.forEach { println(it) }
        }
        else println("\nNo people in data base")
        println()
    }
    private fun clearResultSearch() = resultSearch.clear()
    private fun find() {
        println("\nEnter a name or email to search all suitable people.")
        val query = readln().lowercase()
        dataBase.keys.forEach {
            if (it == query) { resultSearch = dataBase[it]!! }
        }
        printResultOfSearch()
        clearResultSearch()
    }
    private fun printResultOfSearch() {
        if(resultSearch.isNotEmpty()) {
            println("${resultSearch.size} persons found:")
            resultSearch.forEach { println(fileData[it]) }
        } else {
            println("No matching people found.")
        }
        println()
    }
}

fun main(args: Array<String>) {
    val searchEngine = SearchEngine()
    searchEngine.fillDataBase(fileName = args[1])
    searchEngine.search()
}