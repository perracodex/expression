/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.functions.numeric

import io.github.perracodex.expression.functions.EvalFunction

class BaseFunction : EvalFunction {
    override fun evaluate(arguments: List<Any>): Any {
        require(arguments.size == ARGUMENTS_SIZE) { "Required $ARGUMENTS_SIZE arguments: base(value, fromBase, toBase)" }

        val value: String = parseValue(value = arguments[0])
        val fromBase: Int = parseBase(base = arguments[1])
        val toBase: Int = parseBase(base = arguments[2])

        // Convert from the original base to decimal.
        val decimalValue: Int = convertToDecimal(value = value, fromBase = fromBase)

        // Convert from decimal to the target base.
        return convertFromDecimal(value = decimalValue, toBase = toBase)
    }

    private fun parseBase(base: Any): Int {
        val baseAsInt: Int = base.toString().toDoubleOrNull()?.toInt()
            ?: throw IllegalArgumentException("Base conversion failed: '$base' is not a valid number.")

        require(baseAsInt in BASE_RANGE) { "Invalid base: $baseAsInt. Base must be between 2 and 36." }

        return baseAsInt
    }

    private fun parseValue(value: Any): String {
        return value.toString()
    }

    private fun convertToDecimal(value: String, fromBase: Int): Int {
        val processedValue: String = preprocessValue(value = value)
        return processedValue.toIntOrNull(radix = fromBase)
            ?: throw IllegalArgumentException("Value conversion failed: '$processedValue' is not a valid number in base $fromBase.")
    }

    private fun preprocessValue(value: String): String {
        if (value.contains('.')) {
            // Check if there are only trailing zeros after the decimal point
            val decimalPart: String = value.substringAfter('.')
            require(decimalPart.length <= 1) { "Value conversion failed: '$value' contains more than one decimal point." }

            // Return the integer part only.
            return value.substringBefore('.')
        }
        return value
    }

    private fun convertFromDecimal(value: Int, toBase: Int): Any {
        return if (toBase == DECIMAL_BASE) {
            value
        } else {
            value.toString(radix = toBase).uppercase()
        }
    }

    private companion object {
        const val ARGUMENTS_SIZE: Int = 1
        const val DECIMAL_BASE: Int = 10
        val BASE_RANGE: IntRange = 2..36
    }
}
