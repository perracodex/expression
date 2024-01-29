/*
 * Copyright (c) 2024 Perraco Labs. All rights reserved.
 * This work is licensed under the terms of the MIT license.
 * For a copy, see <https://opensource.org/licenses/MIT>
 */

package com.perraco.expression.functions.text

import com.perraco.expression.functions.EvalFunction

class LenFunction : EvalFunction {
    override fun evaluate(arguments: List<Any>): Any {
        if (arguments.size != 1 || arguments[0] !is String) {
            throw IllegalArgumentException("Required 1 text argument: len(text)")
        }
        return (arguments[0] as String).length
    }
}
