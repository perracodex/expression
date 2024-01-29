/*
 * Copyright (c) 2024 Perraco Labs. All rights reserved.
 * This work is licensed under the terms of the MIT license.
 * For a copy, see <https://opensource.org/licenses/MIT>
 */

package com.perraco.expression.functions

/**
 * Interface for functions that can be evaluated.
 */
interface EvalFunction {
    fun evaluate(arguments: List<Any>): Any
}
