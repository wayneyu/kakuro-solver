package kakuro

data class Board(val xSize: Int, val ySize: Int, val sums: List<NSum>, val squares: List<Square>) {

    fun printBoard(): String {
        val board = (1..xSize).map{ (1..ySize).map{"0"}.toMutableList() }.toMutableList()

        squares.forEach { it -> board[it.x][it.y] = it.value.toString() }

        return board.joinToString("\n"){ it -> it.joinToString("", "/", "/").replace("0", " ")}
    }


    fun fillSquare(x: Int, y: Int, value: Int) { TODO("") }

    fun neighbors(): List<Board> { TODO("") }

    companion object {
        fun fromString(layout: String): Board { TODO("") }
    }
}

data class NSum(val sum: Int, val direction: Int, val length: Int)

data class Square(val x: Int, val y: Int, val value: Int)