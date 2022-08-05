var x: Int = 0
var y: Int = 0

fun main() {
    var board = Array(3) { charArrayOf(' ', ' ', ' ') }

    val names = arrayOf("Player 1", "Player 2")
    val marks = arrayOf('O', 'X')

    play(board, names, marks)
}

fun printBoard(board: Array<CharArray>) {
    // 가로 줄번호
    print("  ")
    for (x in 0..2) print("$x ")
    println()

    // 세로 줄번호 및 플레이어 표기
    for (y in 0..2) {
        print("$y ")
        for (x in 0..2) {
            print("${board[y][x]}")
            if (x != 2) print("|")
        }
        println()

        // 세로 격자
        if (y != 2) {
            print("  ")
            for (x in 0..2) {
                print("-")
                if (x != 2) print("+")
            }
            println()
        }
    }
}
val isInRange: (x: Int, y: Int) -> Boolean = { x, y -> x in 0..2 && y in 0..2 }

fun isValid(board: Array<CharArray>, x: Int, y: Int) = isInRange(x, y) && board[y][x] == ' '

fun playerInput(name: String, board: Array<CharArray>): Boolean {
    print("$name 입력(줄, 칸): ")
    val input = readln()
    val arr = input.split(',').map { it.toInt() }
    y = arr[0]
    x = arr[1]

    return isValid(board, x, y)
}

// 해당 칸이 승리할 수 있는지 검사
fun isWin(board: Array<CharArray>, x: Int, y: Int): Boolean {
    // 가로, 세로, 우하 대각선, 우상 대각선 방향에 대한 x, y 변화량
    val dx = arrayOf(-1, 1, 0, 0, -1, 1, 1, -1)
    val dy = arrayOf(0, 0, -1, 1, -1, 1, -1, 1)

    for (d in 0..3) {
        var count = 1
        for (b in 0..1) {
            val index = 2 * d + b
            var cx = x + dx[index]
            var cy = y + dy[index]
            while (isInRange(cx, cy)) {
                if (board[cy][cx] == board[y][x]) count++
                cx += dx[index]
                cy += dy[index]
            }
        }
        if (count == 3) return true
    }
    return false
}

fun play(board: Array<CharArray>, name: Array<String>, marks: Array<Char>) {
    var round = 0
    var turn = 0

    while(true) {
        println("\n ${turn + 1}번째 턴\n")
        printBoard(board)
        if(!playerInput(name[turn], board))
            continue
        board[y][x] = marks[turn]
        println("$y $x ${marks[turn]}")
        round++

        if(isWin(board, x, y)) {
            println("${name[turn]} 승리!")
            break
        }

        turn = if(turn == 1) 0 else 1
    }
}