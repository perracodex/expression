/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.functions.text

import io.github.perracodex.expression.functions.EvalFunction

class ReverseFunction : EvalFunction {
    override fun evaluate(arguments: List<Any>): Any {
        require(arguments.size == ARGUMENTS_SIZE) { "Required $ARGUMENTS_SIZE text argument: reverse(text)" }
        require(arguments[0] is String) { "Required $ARGUMENTS_SIZE text argument: reverse(text)" }

        val value: String = arguments[0] as String
        return value.reversed()
    }

    private companion object {
        const val ARGUMENTS_SIZE: Int = 1
    }
}
