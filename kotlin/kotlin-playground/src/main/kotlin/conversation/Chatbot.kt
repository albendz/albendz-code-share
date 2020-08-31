package com.kotlin.playground.conversation

class Chatbot {
    companion object {
        private const val EXIT = "EXIT"

        @JvmStatic
        fun start(): String {
            var session = Session()
            var questionnaire = Questionnaire()
            var line: String

            session.start()

            do {
                if (!questionnaire.isDone()) println(questionnaire.next())
                println("Input your next answer. Type \"EXIT\" or hit \"ENTER\" on a blank line to end chat:")
                line = readLine().orEmpty()
                println(questionnaire.answer(line))
                session.appendMessage(line)
            } while (!isEnd(line) && !questionnaire.isDone())

            session.end()
            questionnaire.print()

            return session.summary()
        }

        fun isEnd(s: String): Boolean {
            return s.isEmpty() || s == EXIT
        }
    }
}