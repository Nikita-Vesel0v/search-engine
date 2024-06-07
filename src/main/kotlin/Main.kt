package search

import java.io.File

class SearchEngine {
    private var dataBase = mutableMapOf<String, MutableList<Int>>()
    private var resultSearch = emptySet<Int>()
    private var fileData = emptyList<String>()

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
                else -> println("Incorrect option! Try again.\n")
            }
        }
        println("\nBye!")
    }
    private fun findAll(query: List<String>) {
        query.forEach { word ->
             resultSearch =
                 if (resultSearch.isEmpty()) (dataBase[word] ?: emptySet()).toSet()
                 else resultSearch.intersect((dataBase[word] ?: emptySet()).toSet())
        }
    }

    private fun findAny(query: List<String>) {
        query.forEach { word ->
            if (resultSearch.isEmpty()) resultSearch = (dataBase[word] ?: emptySet()).toSet()
            else resultSearch += dataBase[word] ?: emptySet()
        }
    }
    private fun findNone(query: List<String>) {
        var index = 0
        repeat(fileData.size) { resultSearch += index++ }
        query.forEach { word ->
            resultSearch = resultSearch.filter { it !in (dataBase[word] ?: emptySet()) }.toSet()
        }
    }
    private fun printDatabase() {
        if (fileData.isNotEmpty()) {
            println("\n=== List of people ===")
            fileData.forEach { println(it) }
        }
        else println("\nNo people in data base")
        println()
    }
    private fun clearResultSearch() {
        resultSearch = emptySet()
    }
    private fun find() {
        println("\nSelect a matching strategy: ALL, ANY, NONE")
        val strategy = readln()
        if (strategy.uppercase() !in listOf("ALL", "ANY", "NONE") ) {
            println("Unknown strategy!\n")
            return
        }
        println("\nEnter a name or email to search all suitable people.")
        val query = readln().lowercase().split(" ")
        when (strategy) {
            "ALL" -> findAll(query)
            "ANY" -> findAny(query)
            "NONE" -> findNone(query)
        }
        printResultOfSearch()
        clearResultSearch()
    }
    private fun printResultOfSearch() {
        if(resultSearch.isNotEmpty()) {
            println("${resultSearch.size} persons found:")
            resultSearch.forEach { println(fileData[it]) }
            println()
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