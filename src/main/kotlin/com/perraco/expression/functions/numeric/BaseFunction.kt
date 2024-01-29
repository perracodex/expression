/*
 * Copyright (c) 2024 Perraco Labs. All rights reserved.
 * This work is licensed under the terms of the MIT license.
 * For a copy, see <https://opensource.org/licenses/MIT>
 */

package com.perraco.expression.functions.numeric

import com.perraco.expression.functions.EvalFunction

class BaseFunction : EvalFunction {
    override fun evaluate(arguments: List<Any>): Any {
        if (arguments.size != 3) {
            throw IllegalArgumentException("Required 3 arguments: base(value, fromBase, toBase)")
        }

        val value: String = parseValue(value = arguments[0])
        val fromBase: Int = parseBase(base = arguments[1])
        val toBase: Int = parseBase(base = arguments[2])

        // Convert from the original base to decimal.
        val decimalValue: Int = convertToDecimal(value = value, fromBase = fromBase)

        // Convert from decimal to the target base.
        return convertFromDecimal(value = decimalValue, toBase = toBase)
    }

    private fun parseBase(base: Any): Int {
        val baseAsDouble: Double = base.toString().toDoubleOrNull()
            ?: throw IllegalArgumentException("Base conversion failed: '${base}' is not a valid number.")
        val baseAsInt: Int = baseAsDouble.toInt()
        if (baseAsInt !in 2..36) {
            throw IllegalArgumentException("Invalid base: $baseAsInt. Base must be between 2 and 36.")
        }
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
            if (decimalPart.any { it != '0' }) {
                throw IllegalArgumentException("Value conversion failed: '$value' contains decimals.")
            }
            // Return the integer part only.
            return value.substringBefore('.')
        }
        return value
    }

    private fun convertFromDecimal(value: Int, toBase: Int): Any {
        return if (toBase == 10) {
            value
        } else {
            "\"${value.toString(radix = toBase).uppercase()}\""
        }
    }
}
