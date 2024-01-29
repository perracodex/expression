/*
 * Copyright (c) 2024 Perraco Labs. All rights reserved.
 * This work is licensed under the terms of the MIT license.
 * For a copy, see <https://opensource.org/licenses/MIT>
 */

package com.perraco.expression.functions.text

import com.perraco.expression.functions.EvalFunction
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class Decode64Function : EvalFunction {
    @OptIn(ExperimentalEncodingApi::class)
    override fun evaluate(arguments: List<Any>): Any {
        if (arguments.size != 1 || arguments[0] !is String) {
            throw IllegalArgumentException("Required 1 text argument: decode64(text)")
        }
        val value: ByteArray = (arguments[0] as String).toByteArray()
        return Base64.decode(source = value).decodeToString()
    }
}
