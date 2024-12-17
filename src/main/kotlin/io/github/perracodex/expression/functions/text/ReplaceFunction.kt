/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.functions.text

import io.github.perracodex.expression.functions.EvalFunction

class ReplaceFunction : EvalFunction {
    override fun evaluate(arguments: List<Any>): Any {
        require(arguments.size == ARGUMENTS_SIZE) { "Required $ARGUMENTS_SIZE text arguments: replace(text, old, new)" }
        require(arguments.all { it is String }) { "Required $ARGUMENTS_SIZE text arguments: replace(text, old, new)" }

        val value: String = arguments[0] as String
        val oldValue: String = arguments[1] as String
        val newValue: String = arguments[2] as String
        return value.replace(oldValue = oldValue, newValue = newValue)
    }

    private companion object {
        const val ARGUMENTS_SIZE: Int = 3
    }
}
