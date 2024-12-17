/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.functions

import io.github.perracodex.expression.functions.numeric.BaseFunction
import io.github.perracodex.expression.functions.numeric.CosFunction
import io.github.perracodex.expression.functions.numeric.SinFunction
import io.github.perracodex.expression.functions.numeric.TanFunction
import io.github.perracodex.expression.functions.text.*

/**
 * Registry of functions that can be evaluated.
 */
object FunctionRegistry {
    private val registry: MutableMap<String, EvalFunction> = mutableMapOf<String, EvalFunction>()

    init {
        // Numeric.
        registry["base"] = BaseFunction()
        registry["cos"] = CosFunction()
        registry["sin"] = SinFunction()
        registry["tan"] = TanFunction()

        // Text.
        registry["encode64"] = Encode64Function()
        registry["decode64"] = Decode64Function()
        registry["len"] = LenFunction()
        registry["replace"] = ReplaceFunction()
        registry["reverse"] = ReverseFunction()
    }

    /**
     * Get a function by name. Expected to be in lowercase.
     *
     * @param name Name of the function.
     * @return Function if found, null otherwise.
     */
    fun get(name: String): EvalFunction? {
        return registry[name]
    }
}

