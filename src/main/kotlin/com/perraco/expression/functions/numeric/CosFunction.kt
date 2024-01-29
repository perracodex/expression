/*
 * Copyright (c) 2024 Perraco Labs. All rights reserved.
 * This work is licensed under the terms of the MIT license.
 * For a copy, see <https://opensource.org/licenses/MIT>
 */

package com.perraco.expression.functions.numeric

import com.perraco.expression.functions.EvalFunction
import kotlin.math.cos

class CosFunction : EvalFunction {
    override fun evaluate(arguments: List<Any>): Any {
        if (arguments.size != 1 || arguments[0] !is Number) {
            throw IllegalArgumentException("Required 1 numeric argument: cos(number)")
        }
        val value: Double = (arguments[0] as Number).toDouble()
        return cos(x = value)
    }
}
