/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.functions.text

import io.github.perracodex.expression.functions.EvalFunction
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class Encode64Function : EvalFunction {
    @OptIn(ExperimentalEncodingApi::class)
    override fun evaluate(arguments: List<Any>): Any {
        require(arguments.size == ARGUMENTS_SIZE) { "Required $ARGUMENTS_SIZE text argument: encode64(text)" }
        require(arguments[0] is String) { "Required $ARGUMENTS_SIZE text argument: encode64(text)" }

        val value: ByteArray = (arguments[0] as String).toByteArray()
        return Base64.encode(source = value)
    }

    private companion object {
        const val ARGUMENTS_SIZE: Int = 1
    }
}
