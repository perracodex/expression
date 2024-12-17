/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.functions.text

import io.github.perracodex.expression.functions.EvalFunction

class LenFunction : EvalFunction {
    override fun evaluate(arguments: List<Any>): Any {
        require(arguments.size == ARGUMENTS_SIZE) { "Required $ARGUMENTS_SIZE text argument: len(text)" }
        require(arguments[0] is String) { "Required $ARGUMENTS_SIZE text argument: len(text)" }

        return (arguments[0] as String).length
    }

    private companion object {
        const val ARGUMENTS_SIZE: Int = 1
    }
}
