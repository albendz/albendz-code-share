package com.alicia.problems.com.alicia.problems.graph

import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.collections.ArrayList

object WordLadder {

    /**
     * Given a starting word, HEAD, and an ending word, TAIL
     * Find the sequence of single letter transformations of valid words
     * that turns HEAD into TAIL.
     *
     * Ex. HEAD > TEAL > TELL > TALL > TAIL
     */
    fun findWordLadder(head: String, tail: String): List<String> {
        val visited = mutableSetOf<String>()
        val next: Queue<WordNode> = LinkedList()
        val dictionary = Dictionary()

        // Could do validation here for valid chars, matching size
        // Invalid character (not english letters)
        // convert to all upper case
        // no empty strings

        if (head == tail) return listOf(head)

        if (head.length != tail.length) return emptyList()

        if (!dictionary.isValidWord(head) || !dictionary.isValidWord(tail)) return emptyList()

        next.add(WordNode(head, null))
        visited.add(head)

        while(!next.isEmpty()) {
            val current = next.remove()

            if (current.word == tail) {
                return getPathTo(current)
            }

            getWordsOneLetterDifferent(current.word)
                .filter {
                    dictionary.isValidWord(it) && it !in visited
                }.forEach {
                    next.add(WordNode(it, current))
                    visited.add(it)
                }
        }

        return emptyList()
    }


    private fun getWordsOneLetterDifferent(word: String): List<String> {
        val words = mutableListOf<String>()
        val charArray = word.toCharArray()

        for(i in charArray.indices) {
            for (c in Dictionary.letters) {
                if (charArray[i] == c) continue

                val newWord = charArray.copyOf()
                newWord[i] = c

                words.add(String(newWord))
            }
        }

        return words
    }

    private fun getPathTo(word: WordNode): List<String> {
        val path = mutableListOf<String>()
        var current: WordNode? = word

        while(current != null) {
            path.add(current.word)
            current = current.previous
        }

        path.reverse()
        return path
    }
}

class WordNode(
    val word: String,
    val previous: WordNode?
)

class Dictionary {

    companion object {
        var letters = listOf(
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H','I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        )
    }
    private val words: Set<String>

    init {
        words = Files.readAllLines(Path.of("C:\\Users\\alici\\repos\\albendz-code-share\\kotlin\\problems\\src\\main\\resources\\dictionary.txt")).toHashSet()
    }

    fun isValidWord(word: String): Boolean = word in words

}
