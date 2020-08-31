package com.kotlin.playground.conversation

// List of questions, function to handle each quetions
// Iterator over questions
class Questionnaire {
    private val questions = listOf(ColourQuestion(), AnimalQuestion()).iterator()
    private val answers = mutableMapOf<Question?, String>();
    private var question: Question? = null
    private var isDone = false

    fun next(): String? {
        if (!isDone) {
            question = questions.next()
            if (!questions.hasNext()) isDone = true
            return question!!.question
        } else {
            return null
        }
    }

    fun answer(a: String): String {
        println(question)
        if (!isDone || question != null) {
            answers[question] = a
            return question!!.response(a)
        }

        if (isDone) question = null

        return "Thank you for completing the survey!"
    }

    fun print() {
        answers.keys.forEach{
            if (it != null) {
                println("${it.question}: ${answers[it]}")
            }
        }
    }

    fun isDone(): Boolean = isDone
}

open class Question constructor(val question: String) {
    open fun response(a: String): String = "Interesting..."
}

class ColourQuestion : Question("What is your favourite colour?") {
    override fun response(a: String): String {
        return when (a.toUpperCase()) {
            "RED" -> ColouredResponse.RED.commentary()
            "BLUE" -> ColouredResponse.BLUE.commentary()
            else -> "I don't know that one."
        }
    }

    enum class ColouredResponse {
        RED {
            override fun commentary(): String = "Fiery!"
        },
        BLUE {
            override fun commentary(): String = "Brrr, Cold!"
        };

        abstract fun commentary(): String
    }
}

class AnimalQuestion: Question("What is your favourite animal?") {
    override fun response(a: String): String {
        return when (a.toUpperCase()) {
            "SLOTH" -> AnimalResponse.SLOTH.commentary()
            "CAT" -> AnimalResponse.CAT.commentary()
            "DOG" -> AnimalResponse.DOG.commentary()
            else -> "I don't know that one."
        }
    }

    enum class AnimalResponse {
        CAT {
            override fun commentary(): String = "Cats rule!"
        },
        DOG {
            override fun commentary(): String = "Dogs drool!"
        },
        SLOTH {
            override fun commentary(): String = "Soooooo slooooooooow"
        };

        abstract fun commentary(): String
    }
}