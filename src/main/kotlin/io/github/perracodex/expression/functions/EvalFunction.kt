/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.functions

/**
 * Interface for functions that can be evaluated.
 */
interface EvalFunction {
    fun evaluate(arguments: List<Any>): Any
}
