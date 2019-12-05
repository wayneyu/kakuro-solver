package kakuro

data class Board(val xSize: Int, val ySize: Int, val sums: List<NSum>) {

    fun isSolved(): Boolean {TODO("")}

    fun printBoard(): String {
        val board = (1..xSize).map{ (1..ySize).map{"00\\00"}.toMutableList() }.toMutableList()

        fun pad(i: Int, filler: Char, len: Int): String {
            val s = i.toString()
            return CharArray(len - s.length){filler}.joinToString("") + s
        }
        fun spad(i: Int, len: Int): String = pad(i, ' ', len)
        fun zpad(i: Int, len: Int): String = pad(i, '0', len)

        sums.forEach { sum ->
            println(sum)
            (1..sum.length).forEach { i ->
                println("i: $i")
                when (sum.direction) {
                    1 -> {
                        board[sum.x][sum.y] = board[sum.x][sum.y].replaceRange(3, 5, spad(sum.sum, 2));
                        board[sum.x][sum.y + i] = spad(sum.combo[i - 1], 5)
                    }
                    0 -> {
                        board[sum.x][sum.y] = board[sum.x][sum.y].replaceRange(0, 2, spad(sum.sum, 2))
                        board[sum.x + i][sum.y] = spad(sum.combo[i - 1], 5)
                    }
                    else -> throw Exception("direction should be 0 or 1")
                }
            }
        }

        return board.joinToString("\n"){ it -> it.joinToString("  ", "/", "/")}
    }


    fun fillSquare(x: Int, y: Int, value: Int) { TODO("") }

    fun neighbors(): List<Board> { TODO("") }

    companion object {

        private const val emptySpaceToken = "0"

        fun fromString(layout: String): Board {
            val xys = layout.split("\n").map{it.trimEnd().trimStart('/').trimEnd('/').toCharArray().toList().map{c -> c.toString()}}
            val xSize = xys.size
            val ySize = xys[0].size

//            val squares = xys.mapIndexedNotNull { x, row ->
//                row.mapIndexedNotNull { y, c ->
//                    if (c != emptySpaceToken) c.toIntOrNull()?.let{ value -> Square(x, y, value)}
//                    else null
//                }
//            }.flatten()

            return Board(xSize, ySize, emptyList())
        }

    }
}

data class NSum(val x: Int,
                val y: Int,
                val sum: Int,
                val direction: Int,
                val length: Int,
                val combo: Array<Int> = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NSum

        if (x != other.x) return false
        if (y != other.y) return false
        if (sum != other.sum) return false
        if (direction != other.direction) return false
        if (length != other.length) return false
        if (!combo.contentEquals(other.combo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + sum
        result = 31 * result + direction
        result = 31 * result + length
        result = 31 * result + combo.contentHashCode()
        return result
    }
}
