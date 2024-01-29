/*
 * Copyright (c) 2024 Perraco Labs. All rights reserved.
 * This work is licensed under the terms of the MIT license.
 * For a copy, see <https://opensource.org/licenses/MIT>
 */

package com.perraco.expression.functions.text

import com.perraco.expression.functions.EvalFunction

class ReplaceFunction : EvalFunction {
    override fun evaluate(arguments: List<Any>): Any {
        if (arguments.size != 3 || !arguments.all { it is String }) {
            throw IllegalArgumentException("Required 3 text arguments: replace(text, old, new)")
        }
        val value: String = arguments[0] as String
        val oldValue: String = arguments[1] as String
        val newValue: String = arguments[2] as String
        return value.replace(oldValue = oldValue, newValue = newValue)
    }
}
