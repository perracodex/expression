/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex

import io.github.perracodex.expression.engine.Evaluator

fun main() {
    val evaluator = Evaluator()
    println("Enter a math expression (or 'exit' to quit)")
    println("Built-in functions: sin, cos, tan, len, reverse, replace, encode64, decode64, base")

    while (true) {
        val input: String? = readlnOrNull()

        if (input == "exit") {
            break // Exit the loop and end the program.
        }

        try {
            if (input.isNullOrBlank()) {
                println("Error: No input provided")
                // Skip to the next iteration of the loop.
            } else {
                val result = evaluator.evaluate(input = input)
                println(result)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
