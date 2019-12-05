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
            val board = Board(4, 4, emptyList(),
                listOf(Square(0, 1, 3), Square(0, 2, 4), Square(3, 2, 5)))

            val expected = """
                / 34 /
                /    /
                /    /
                /  5 /
            """.trimIndent()

            assertEquals(expected, board.printBoard())
        }


    }

})