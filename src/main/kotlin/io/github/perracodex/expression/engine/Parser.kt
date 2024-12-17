/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

package io.github.perracodex.expression.engine

/**
 * A parser for mathematical expressions taking a string input and converting
 * it into an Abstract Syntax Tree (AST) which represents the mathematical expression.
 *
 * This parser handles arithmetic operations, function calls, and text literals.
 *
 * Operator precedence is maintained by the structure of the parser's methods:
 * [expression], [term], and [factor].
 *
 * The [expression] method processes addition and subtraction and delegates
 * multiplication, division, and modulo operations to the [term] method, which in turn
 * relies on the [factor] method for handling the highest precedence operations like
 * unary operators, numbers, and parenthesized expressions.
 *
 * This hierarchical approach ensures that operations are parsed according to standard
 * arithmetic rules of precedence.
 *
 * @param input The string input that is to be parsed into an AST.
 */
class Parser(input: String) {
    private val lexer: Lexer = Lexer(input = input)
    private var currentToken: Token = lexer.nextToken()

    /**
     * Parses the input string into an AST.
     *
     * @return The root [ASTNode] if the input is non-empty, otherwise null.
     */
    fun parse(): ASTNode? {
        if (currentToken.type == Token.Type.END) {
            // Indicates empty input or end of parsing.
            return null
        }

        val node: ASTNode = expression()

        if (currentToken.type != Token.Type.END) {
            throw ParserException(currentToken = currentToken.type)
        }

        return node
    }

    /**
     * Parses an expression handling operations like addition and subtraction.
     *
     * Operator precedence is handled by calling the [term] method for higher
     * precedence operations (like multiplication and division) before handling
     * addition and subtraction.
     *
     * @return The [ASTNode] representing the parsed expression.
     */
    private fun expression(): ASTNode {
        var node: ASTNode = term()

        while (currentToken.type == Token.Type.PLUS || currentToken.type == Token.Type.MINUS) {
            val token: Token = currentToken
            consume(tokenType = token.type)
            node = ASTNode.BinaryOperationNode(left = node, operator = token, right = term())
        }

        return node
    }

    /**
     * Parses a term handling operations like multiplication, division, and modulo.
     *
     * This method has a higher precedence in the expression hierarchy.
     * It calls the [factor] method to handle even higher precedence operations like
     * unary operations, numbers, and parenthesized expressions.
     *
     * @return The [ASTNode] representing the parsed term.
     */
    private fun term(): ASTNode {
        var node: ASTNode = factor()

        while (currentToken.type in termList) {
            val token: Token = currentToken
            consume(tokenType = token.type)
            node = ASTNode.BinaryOperationNode(left = node, operator = token, right = factor())
        }

        return node
    }

    /**
     * Parses a factor, which can be a number, a text literal, a parenthesized expression,
     * or a unary operation. Factors represent the highest precedence operations in the
     * expression hierarchy.
     *
     * @return The [ASTNode] representing the parsed factor.
     */
    private fun factor(): ASTNode {
        val token: Token = currentToken

        // Handle unary operators.
        if (token.type == Token.Type.PLUS || token.type == Token.Type.MINUS) {
            consume(tokenType = token.type)
            val operand: ASTNode = factor()
            return ASTNode.UnaryOperationNode(operator = token, operand = operand)
        }

        return when (token.type) {
            Token.Type.NUMBER -> {
                consume(tokenType = Token.Type.NUMBER)
                ASTNode.NumberNode(value = token.value.toDouble())
            }

            Token.Type.TEXT -> {
                consume(tokenType = Token.Type.TEXT)
                ASTNode.TextNode(value = token.value)
            }

            Token.Type.OPEN_PARENTHESIS -> {
                consume(tokenType = Token.Type.OPEN_PARENTHESIS)
                val node: ASTNode = expression()
                consume(tokenType = Token.Type.CLOSE_PARENTHESIS)
                node
            }

            Token.Type.FUNCTION -> {
                consume(tokenType = Token.Type.FUNCTION)
                ASTNode.FunctionNode(name = token.value, arguments = functionArguments())
            }

            else -> throw ParserException(currentToken = token.type)
        }
    }

    /**
     * Parses the arguments of a function call.
     *
     * @return A list of [ASTNode]s representing each argument of the function.
     */
    private fun functionArguments(): List<ASTNode> {
        val arguments: MutableList<ASTNode> = mutableListOf()
        consume(tokenType = Token.Type.OPEN_PARENTHESIS)

        // Check if the first token after '(' is not ')', indicating at least one argument.
        if (currentToken.type != Token.Type.CLOSE_PARENTHESIS) {
            arguments.add(expression())

            // Continue parsing arguments separated by commas.
            while (currentToken.type == Token.Type.COMMA) {
                consume(tokenType = Token.Type.COMMA)
                arguments.add(expression())
            }
        }

        // Expect a closing parenthesis after parsing all arguments.
        consume(tokenType = Token.Type.CLOSE_PARENTHESIS)
        return arguments
    }

    /**
     * Consumes the current token and advances to the next token in the input.
     *
     * @param tokenType The expected type of the current token.
     * @throws ParserException if the current token does not match the expected type.
     */
    private fun consume(tokenType: Token.Type) {
        if (currentToken.type == tokenType) {
            currentToken = lexer.nextToken()
        } else {
            throw ParserException(currentToken = currentToken.type, expectedToken = tokenType)
        }
    }

    companion object {
        private val termList: List<Token.Type> = listOf(
            Token.Type.MULTIPLY,
            Token.Type.DIVIDE,
            Token.Type.MODULO
        )
    }
}

/**
 * Exception raised when an unexpected token is encountered.
 *
 * @param currentToken The unexpected current token that triggered the exception.
 * @param expectedToken The actual expected token, if any.
 */
class ParserException(
    currentToken: Token.Type,
    expectedToken: Token.Type? = null
) : Exception(
    buildMessage(currentToken = currentToken, expectedToken = expectedToken)
) {
    companion object {
        private fun buildMessage(currentToken: Token.Type, expectedToken: Token.Type?): String {
            return when (currentToken) {
                Token.Type.END ->
                    "Unexpected end of expression" + (expectedToken?.let { ". Expected: '${it.string}'" } ?: "")

                else ->
                    "Unexpected: '${currentToken.string}'" + (expectedToken?.let { ". Expected: '${it.string}'" } ?: "")
            }
        }
    }
}
