package com.perraco.expression.engine

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class EvaluatorTest {

    @Test
    fun testSpaces() {
        val eval = Evaluator()
        assertEquals(2L, eval.evaluate(input = "1+1"))
        assertEquals(2L, eval.evaluate(input = "1 + 1"))
        assertEquals(2L, eval.evaluate(input = "1 + 1 "))
        assertEquals(2L, eval.evaluate(input = "   1  +     1 "))
        assertEquals(2L, eval.evaluate(input = "   (  1)  +     (1 ) "))
        assertEquals(2.5, eval.evaluate(input = "   (  1)  +1    /2+   (1 ) "))
    }

    @Test
    fun testSimpleNumericFunctions() {
        val eval = Evaluator()
        assertEquals(0.8415, eval.evaluate(input = "sin(1)"))
        assertEquals(0.5403, eval.evaluate(input = "cos(1)"))
        assertEquals(1.5574, eval.evaluate(input = "tan(1)"))
    }

    @Test
    fun testComplexNumericFunctions() {
        val eval = Evaluator()
        assertEquals("A", eval.evaluate(input = "base(10, 10, 16)"))
        assertEquals("10001", eval.evaluate(input = "base(17, 10, 2)"))
        assertEquals(17, eval.evaluate(input = "base(17, 10, 10)"))
    }

    @Test
    fun testNumericFunctionExpression() {
        val eval = Evaluator()
        assertEquals(0.3378, eval.evaluate(input = "sin(1) + cos(17) / tan(0.5)"))
        assertEquals(0.8676, eval.evaluate(input = "sin(1) + cos(17) / -(tan(0.5) + base(16, 10, 16))"))
    }

    @Test
    fun testSimpleTextFunctions() {
        val eval = Evaluator()
        assertEquals(11, eval.evaluate(input = """len("Hello World")"""))
        assertEquals("dlroW olleH", eval.evaluate(input = """reverse("Hello World")"""))
        assertEquals("HeLLo WorLd", eval.evaluate(input = """replace("Hello World", "l", "L")"""))
        assertEquals("SGVsbG8gV29ybGQ=", eval.evaluate(input = """encode64("Hello World")"""))
        assertEquals("Hello World", eval.evaluate(input = """decode64("SGVsbG8gV29ybGQ=")"""))
    }

    @Test
    fun testComplexExpressions() {
        val eval = Evaluator()
        assertEquals(0.2697, eval.evaluate(input = "sin(1) / cos(1) + -(tan(1) + 123.00000 / -456.1212)"))
        assertEquals(-11.1818, eval.evaluate(input = "sin(1) / cos(1) + -(tan(1) + 123.00000 / len(\"Hello World\"))"))
        assertEquals(7.9531, eval.evaluate(input = "-(-17) + -(tan(24) + 123.00000 / len(reverse(\"Hello World\")))"))
    }

    @Test
    fun testUnbalancedParenthesis() {
        val eval = Evaluator()
        assertThrows(EvalException::class.java) { eval.evaluate(input = "(") }
        assertThrows(EvalException::class.java) { eval.evaluate(input = ")") }
        assertThrows(EvalException::class.java) { eval.evaluate(input = "(1+1") }
        assertThrows(EvalException::class.java) { eval.evaluate(input = "1+1)") }
        assertThrows(EvalException::class.java) { eval.evaluate(input = "((1+1)") }
        assertThrows(EvalException::class.java) { eval.evaluate(input = "1+1))") }
        assertThrows(EvalException::class.java) { eval.evaluate(input = "(1+1))") }
    }

    @Test
    fun testInvalidNumber() {
        val eval = Evaluator()
        assertThrows(EvalException::class.java) { eval.evaluate(input = "123abc") }
    }

    @Test
    fun testUnknownFunction() {
        val eval = Evaluator()
        assertThrows(EvalException::class.java) { eval.evaluate(input = "function(123)") }
    }

    @Test
    fun testInvalidFunctionArguments() {
        val eval = Evaluator()
        assertThrows(EvalException::class.java) { eval.evaluate(input = "sin()") }
        assertThrows(EvalException::class.java) { eval.evaluate(input = "sin") }
        assertThrows(EvalException::class.java) { eval.evaluate(input = "sin(1, 2)") }
        assertThrows(EvalException::class.java) { eval.evaluate(input = "sin(abc)") }
    }

    @Test
    fun testInvalidExpression() {
        val eval = Evaluator()
        assertThrows(EvalException::class.java) { eval.evaluate(input = "1 2 / * 12 ( 12 //)") }
    }
}
