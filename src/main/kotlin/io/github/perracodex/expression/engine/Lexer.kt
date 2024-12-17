/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.engine

/**
 * Lexer for parsing arithmetic expressions and function names,
 * taking an input string and breaking it down into a series of tokens.
 *
 * @property input The string input that is to be tokenized.
 */
class Lexer(private val input: String) {
    /** The length of the input string. */
    private val length: Int = input.length

    /** The current position in the input string as the lexer scans through it. */
    private var position: Int = 0

    /**
     * Retrieves the next token from the input string by scanning the input
     * from the current position, and identifying the next token.
     *
     * @return The next token as identified in the input string.
     * @throws [LexerException] If an unexpected character is encountered.
     */
    fun nextToken(): Token {
        while (position < length) {
            val currentChar: Char = currentChar()
            when {
                currentChar.isWhitespace() -> skipWhitespace()
                currentChar.isDigit() -> return numberToken()
                currentChar.isOperator() -> return operatorToken(character = currentChar)
                currentChar == '(' -> return singleCharToken(type = Token.Type.OPEN_PARENTHESIS, character = currentChar)
                currentChar == ')' -> return singleCharToken(type = Token.Type.CLOSE_PARENTHESIS, character = currentChar)
                currentChar == '"' -> return textToken()
                currentChar == ',' -> return singleCharToken(type = Token.Type.COMMA, character = currentChar)
                currentChar.isLetter() || (currentChar == '_') -> return functionToken()
                else -> throw LexerException(message = "Unexpected '$currentChar' at position $position")
            }
        }

        return Token(type = Token.Type.END, value = "")
    }

    /**
     * Returns the current character in the input string without advancing the position.
     *
     * @return The current character.
     */
    private fun currentChar(): Char = input[position]

    /**
     * Advances the position in the input string skipping over whitespace characters
     * upto the next non-whitespace character.
     */
    private fun skipWhitespace() {
        while (position < length && currentChar().isWhitespace()) {
            position++
        }
    }

    /**
     * Tokenizes a number from the current position in the input string.
     * Handles both integer and floating-point numbers, including
     * those in scientific notation (e.g., "123.45", "6.02E23").
     *
     * @return A token of type [Token.Type.NUMBER] representing the parsed number.
     */
    private fun numberToken(): Token {
        val start: Int = position
        while (position < length && currentChar().isPartOfNumber()) {
            position++
        }

        // Check if the end of the number is followed by invalid characters
        if (position < length && !currentChar().isValidNumberFollowChar()) {
            throw LexerException(message = "Invalid number format at position $start")
        }

        val numberValue: String = input.substring(startIndex = start, endIndex = position)
        return Token(type = Token.Type.NUMBER, value = numberValue)
    }

    /**
     * Checks if the character can validly follow a number in an expression.
     * Valid characters include whitespace, operators, commas, and closing parentheses.
     *
     * @return True if it is a valid following character, false otherwise.
     */
    @Suppress("KotlinConstantConditions")
    private fun Char.isValidNumberFollowChar(): Boolean {
        return this == ',' || this == ')' || this.isWhitespace() || this.isOperator()
    }

    /**
     * Determines whether the current character is part of a numeric value.
     * This includes digits, decimal points, and elements of scientific notation
     * such as the exponent 'E' character.
     *
     * @return True if the current character is part of a numeric value, false otherwise.
     */
    @Suppress("KotlinConstantConditions")
    private fun Char.isPartOfNumber(): Boolean {
        return this == '.' || this.isDigit() || this.isPartOfExponent()
    }

    /**
     * Checks if the current character and its context form part of an exponent
     * in scientific notation. Specifically it looks for 'E' or 'e' followed by
     * a digit or a sign indicator ('+' or '-').
     *
     * @return True if the current and following characters are part of an exponent, false otherwise.
     */
    @Suppress("KotlinConstantConditions")
    private fun Char.isPartOfExponent(): Boolean {
        if ((this == 'E' || this == 'e') && position + 1 < length) {
            val nextChar = input[position + 1]
            return nextChar.isDigit() || nextChar == '+' || nextChar == '-'
        }
        return false
    }

    /**
     * Tokenizes a text value from the current position in the input string.
     *
     * @return A token of type [Token.Type.TEXT] representing the parsed text value.
     * @throws [LexerException] If an unclosed text string is encountered.
     */
    private fun textToken(): Token {
        position++ // Skip the opening quote.
        val start: Int = position

        while (position < length && currentChar() != '"') {
            position++
        }

        // Check for a matching closing quote, and if it's missing, raise an error.
        if (position >= length || currentChar() != '"') {
            throw LexerException(message = "Unclosed text string starting at position $start")
        }

        val textValue: String = input.substring(startIndex = start, endIndex = position)
        position++ // Skip the closing quote.

        return Token(type = Token.Type.TEXT, value = textValue)
    }

    /**
     * Tokenizes an operator from the current position in the input string.
     *
     * @param character The character to be checked.
     * @return A token corresponding to the identified operator.
     * @throws [LexerException] If an unexpected operator is encountered.
     */
    private fun operatorToken(character: Char): Token {
        val operatorValue: String = character.toString()
        val operatorType: Token.Type = when (operatorValue[0]) {
            '+' -> Token.Type.PLUS
            '-' -> Token.Type.MINUS
            '*' -> Token.Type.MULTIPLY
            '/' -> Token.Type.DIVIDE
            '%' -> Token.Type.MODULO
            else -> throw LexerException(message = "Unexpected operator: ${operatorValue[0]}")
        }

        position++
        return Token(type = operatorType, value = operatorValue)
    }

    /**
     * Creates a single character token based on the given type. Primarily used for parentheses.
     *
     * @param character The character to be checked.
     * @param type The [Token.Type] of the token to be created.
     * @return A token of the specified type.
     */
    private fun singleCharToken(type: Token.Type, character: Char): Token {
        val charValue: String = character.toString()
        val token = Token(type = type, value = charValue)
        position++
        return token
    }

    /**
     * Tokenizes a function name from the current position in the input string.
     * Function names can include alphanumeric characters and underscores,
     * for example: `sin`, `base64`, `custom_function`.
     *
     * @return A token of type [Token.Type.FUNCTION] representing the parsed function name.
     */
    private fun functionToken(): Token {
        val start: Int = position
        while (position < length && currentChar().isFunctionNameLetterOrDigit()) {
            position++
        }

        val functionNameValue: String = input.substring(start, position).lowercase()
        return Token(type = Token.Type.FUNCTION, value = functionNameValue)
    }

    /**
     * Checks if the current character is a letter, digit, or underscore.
     * Used to identify valid characters in function names.
     *
     * @return True if the current character is a letter, digit, or underscore, false otherwise.
     */
    @Suppress("KotlinConstantConditions")
    private fun Char.isFunctionNameLetterOrDigit(): Boolean {
        return this == '_' || this.isLetterOrDigit()
    }

    /**
     * Checks if the current character is an operator.
     * Used to identify valid characters in function names.
     *
     * @return True if the current character is an operator, false otherwise.
     */
    private fun Char.isOperator(): Boolean {
        return this in operatorChars
    }

    companion object {
        /**
         * Set of characters representing the supported operators.
         */
        private val operatorChars: Set<Char> = setOf('+', '-', '*', '/', '%')
    }
}

/**
 * Exception raised when an unexpected character is encountered during tokenization.
 *
 * @param message The error message to be displayed.
 */
class LexerException(message: String) : Exception(message)
