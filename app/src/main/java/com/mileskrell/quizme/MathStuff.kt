package com.mileskrell.quizme

import java.io.Serializable

enum class Operation(val method: (num1: Int, num2: Int) -> Int) {
    PLUS(Int::plus), MINUS(Int::minus), TIMES(Int::times);

    override fun toString() = when (this) {
        PLUS -> "+"
        MINUS -> "-"
        TIMES -> "×"
    }
}

class Problem(private val op: Operation, private val num1: Int, private val num2: Int) :
    Serializable {
    val answer = op.method(num1, num2)

    override fun toString() = "$num1$op$num2"
}
