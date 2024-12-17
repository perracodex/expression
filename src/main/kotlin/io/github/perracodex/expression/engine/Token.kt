/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.engine

/**
 * A token holding the type and value of a lexeme.
 */
data class Token(val type: Type, val value: String) {

    /**
     * The type of the token.
     */
    enum class Type(val string: String) {
        PLUS("+"),
        MINUS("-"),
        DIVIDE("/"),
        MULTIPLY("*"),
        MODULO("%"),
        OPEN_PARENTHESIS("("),
        CLOSE_PARENTHESIS(")"),
        NUMBER("number"),
        TEXT("text"),
        FUNCTION("function"),
        COMMA(","),
        END("end of expression")
    }
}
