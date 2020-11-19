package kakuro

data class Board(val xSize: Int, val ySize: Int, val sums: List<NSum>) {

    fun isSolved(): Boolean {TODO("")}

    fun printBoard(): String {
        val board = (1..xSize).map{ (1..ySize).map{"00\\00"}.toMutableList() }.toMutableList()

        fun pad(i: Int, filler: Char, len: Int): String = i.toString().padStart(len, filler)
        fun spad(i: Int, len: Int): String = pad(i, ' ', len)
        fun zpad(i: Int, len: Int): String = pad(i, '0', len)

        sums.forEach { sum ->
            (1..sum.length).forEach { i ->
                when (sum.direction) {
                    1 -> {
                        board[sum.x][sum.y] = board[sum.x][sum.y].replaceRange(3, 5, zpad(sum.sum, 2));
                        board[sum.x][sum.y + i] = spad(sum.combo[i - 1], 5)
                    }
                    0 -> {
                        board[sum.x][sum.y] = board[sum.x][sum.y].replaceRange(0, 2, zpad(sum.sum, 2))
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

        private const val emptySpaceToken = "00\\00"

        fun splitByLength(str: String, len: Int, delimiterLen: Int): List<String> {
            val period = len + delimiterLen
            return (0..(str.length/period)).map{i -> str.substring(i*period, i*period + len)}
        }

        fun getCombo(xys: List<List<String>>, x: Int, y: Int, direction: Int): List<Int> {
            // x, y: location of sum header
            val out = mutableListOf<Int>()
            val lx = xys.size
            val ly = xys[x].size
            when (direction) {
                1 -> {
                    var yi = y + 1
                    while (yi < ly && xys[x][yi] != emptySpaceToken) {
                        out.add(xys[x][yi].trim().toInt())
                        yi += 1
                    }
                }
                0 -> {
                    var xi = x + 1
                    while (xi < lx && xys[xi][y] != emptySpaceToken) {
                        out.add(xys[xi][y].trim().toInt())
                        xi += 1
                    }
                }
                else -> throw IllegalArgumentException("Direction should bo 0 or 1")
            }
            return out.filter { it -> it != 0 }
        }

        fun fromString(layout: String): Board {
            val xys = layout.split("\n").map{it.trimStart('/').trimEnd('/').let{it -> splitByLength(it, 5, 2)}}
            val xSize = xys.size
            val ySize = xys[0].size
            val headerRegex = """(\d\d)\\(\d\d)""".toRegex()

            val sums = xys.mapIndexedNotNull { x, row ->
                row.mapIndexedNotNull { y, str ->
                    when {
                        str.contains("\\") -> {
                            val matchResult = headerRegex.find(str)
                            val downSum = matchResult!!.groupValues[1].toInt()
                            val rightSum = matchResult.groupValues[2].toInt()
                            val sums = mutableListOf<NSum>()

                            if (downSum > 0) {
                                val combo = getCombo(xys, x, y, 0)
                                val len = combo.count { it > 0 }
                                sums.add(NSum(x, y, downSum, 0, len, combo))
                            }
                            if (rightSum > 0) {
                                val combo = getCombo(xys, x, y, 1)
                                val len = combo.count { it > 0 }
                                sums.add(NSum(x, y, rightSum, 1, len, combo))
                            }
                            sums
                        }
                        else -> null
                    }
                }.flatten()
            }.flatten()
            println("sums $sums")
            return Board(xSize, ySize, sums)
        }

    }
}

data class NSum(val x: Int,
                val y: Int,
                val sum: Int,
                val direction: Int,
                val length: Int,
                val combo: List<Int> = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NSum

        if (x != other.x) return false
        if (y != other.y) return false
        if (sum != other.sum) return false
        if (direction != other.direction) return false
        if (length != other.length) return false
        if (combo != other.combo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + sum
        result = 31 * result + direction
        result = 31 * result + length
        result = 31 * result + combo.hashCode()
        return result
    }
}
