/*
 * Copyright (c) 2024 Perraco Labs. All rights reserved.
 * This work is licensed under the terms of the MIT license.
 * For a copy, see <https://opensource.org/licenses/MIT>
 */

package com.perraco.expression.functions

import com.perraco.expression.functions.numeric.BaseFunction
import com.perraco.expression.functions.numeric.CosFunction
import com.perraco.expression.functions.numeric.SinFunction
import com.perraco.expression.functions.numeric.TanFunction
import com.perraco.expression.functions.text.*

/**
 * Registry of functions that can be evaluated.
 */
object FunctionRegistry {
    private val registry = mutableMapOf<String, EvalFunction>()

    init {
        // Numeric
        registry["base"] = BaseFunction()
        registry["cos"] = CosFunction()
        registry["sin"] = SinFunction()
        registry["tan"] = TanFunction()

        // Text
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

