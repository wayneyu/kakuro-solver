package kakuro

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(JUnitPlatform::class)
class BoardSpec : Spek({

    describe("board") {

        it("should print board") {
            val board = Board(5, 5,
                listOf(
                    NSum(0, 3, 12, 0, 3, listOf(2, 3, 7)),
                    NSum(1, 1, 6, 0, 3, listOf(1, 2, 3)),
                    NSum(1, 1, 7, 1, 3, listOf(1, 2, 4)),
                    NSum(3, 0, 14, 1, 3, listOf(2, 5, 7)))
            )

            val expected = """
                /00\00  00\00  00\00  12\00  00\00/
                /00\00  06\07      1      2      4/
                /00\00      1  00\00      3  00\00/
                /00\14      2      5      7  00\00/
                /00\00      3  00\00  00\00  00\00/
            """.trimIndent()

            assertEquals(expected, board.printBoard())
        }

        it("should create board from layout") {
            val layout = """
                /00\00  00\00  00\00  12\00  00\00/
                /00\00  06\07      1      2      4/
                /00\00      1  00\00      3  00\00/
                /00\14      2      5      7  00\00/
                /00\00      3  00\00  00\00  00\00/
            """.trimIndent()

            val expected = Board(5, 5,
                    listOf(
                            NSum(0, 3, 12, 0, 3, listOf(2, 3, 7)),
                            NSum(1, 1, 6, 0, 3, listOf(1, 2, 3)),
                            NSum(1, 1, 7, 1, 3, listOf(1, 2, 4)),
                            NSum(3, 0, 14, 1, 3, listOf(2, 5, 7)))
            )

            println(expected.printBoard())
            println(Board.fromString(layout).printBoard())

            assertEquals(expected, Board.fromString(layout))
        }
    }

})