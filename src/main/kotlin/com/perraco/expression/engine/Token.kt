/*
 * Copyright (c) 2024 Perraco Labs. All rights reserved.
 * This work is licensed under the terms of the MIT license.
 * For a copy, see <https://opensource.org/licenses/MIT>
 */

package com.perraco.expression.engine

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
        DIVIDE ("/"),
        MULTIPLY ("*"),
        MODULO ("%"),
        OPEN_PARENTHESIS ("("),
        CLOSE_PARENTHESIS (")"),
        NUMBER ("number"),
        TEXT ("text"),
        FUNCTION ("function"),
        COMMA (","),
        END ("end of expression")
    }
}
