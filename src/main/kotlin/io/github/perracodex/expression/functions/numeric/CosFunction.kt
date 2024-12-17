/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.functions.numeric

import io.github.perracodex.expression.functions.EvalFunction
import kotlin.math.cos

class CosFunction : EvalFunction {
    override fun evaluate(arguments: List<Any>): Any {
        require(arguments.size == ARGUMENTS_SIZE) { "Required $ARGUMENTS_SIZE numeric argument: cos(number)" }
        require(arguments[0] is Number) { "Required $ARGUMENTS_SIZE numeric argument: cos(number)" }

        val value: Double = (arguments[0] as Number).toDouble()
        return cos(x = value)
    }

    private companion object {
        const val ARGUMENTS_SIZE: Int = 1
    }
}
