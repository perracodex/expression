/*
 * Copyright (c) 2024 Perraco Labs. All rights reserved.
 * This work is licensed under the terms of the MIT license.
 * For a copy, see <https://opensource.org/licenses/MIT>
 */

package com.perraco.expression.engine

/**
 * Represents a node in an Abstract Syntax Tree (AST),
 * serving as a base for different types of nodes
 * that represent various parsed constructs.
 */
sealed class ASTNode {
    /**
     * Represents a numeric value in the AST.
     *
     * @property value The double value of the number.
     */
    data class NumberNode(val value: Double) : ASTNode()

    /**
     * Represents a text value in the AST.
     *
     * @property value The string value of the text.
     */
    data class TextNode(val value: String) : ASTNode()

    /**
     * Represents a binary operation (like +, -, *, /) in the AST.
     *
     * @property left The left operand of the binary operation.
     * @property operator The operator token of the binary operation.
     * @property right The right operand of the binary operation.
     */
    data class BinaryOperationNode(val left: ASTNode, val operator: Token, val right: ASTNode) : ASTNode()

    /**
     * Represents a function call in the AST.
     *
     * @property name The name of the function being called.
     * @property arguments The list of arguments passed to the function.
     */
    data class FunctionNode(val name: String, val arguments: List<ASTNode>) : ASTNode()

    /**
     * Represents a unary operation (like - or +) in the AST.
     *
     * @property operator The operator token of the unary operation.
     * @property operand The operand of the unary operation.
     */
    data class UnaryOperationNode(val operator: Token, val operand: ASTNode) : ASTNode()
}
