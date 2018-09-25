package main;

public class Friends4Blocks {

    public class Block {
        public char type;
        public boolean matched = false;
        public boolean deleted = false;

        public Block(char type) {
            this.type = type;
        }

        public Block(char type, boolean deleted) {
            this.type = type;
            this.deleted = deleted;
        }
    }

    public int solution(int m, int n, String[] board) {
        Block[][] blockBoard = parseBoard(m, n, board);
        return runGame(m, n, blockBoard);
    }

    public Block[][] parseBoard(int m, int n, String[] board) {
        Block[][] blockBoard = new Block[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                blockBoard[i][j] = new Block(board[i].charAt(j));
            }
        }
        return blockBoard;
    }

    public int runGame(int m, int n, Block[][] board) {
        int numMatch = countMatch(m, n, board);
        int totalErasedBlocks = numMatch;
        while (numMatch > 0) {
            renewBoard(m, n, board);
            numMatch = countMatch(m, n, board);
            totalErasedBlocks += numMatch;
        }

        return totalErasedBlocks;
    }
    
    public int countMatch(int m, int n, Block[][] board) {
        int numMatch = 0;
        for (int i = 0; i < m-1; i++) {
            for (int j = 0; j < n-1; j++) {
                numMatch += match(board[i][j], board[i+1][j], board[i][j+1], board[i+1][j+1]);
            }
        }
        return numMatch;
    }

    public int match(Block a, Block b, Block c, Block d) {
        int numMatch = 0;
        if (isMatch(a, b, c, d)) {
            if (!a.matched) {
                a.matched = true;
                numMatch++;
            }
            if (!b.matched) {
                b.matched = true;
                numMatch++;
            }
            if (!c.matched) {
                c.matched = true;
                numMatch++;
            }
            if (!d.matched) {
                d.matched = true;
                numMatch++;
            }
        }
        return numMatch;
    }

    public boolean isMatch(Block a, Block b, Block c, Block d) {
        if (!a.deleted && !b.deleted && !c.deleted && !d.deleted &&
                a.type == b.type && a.type == c.type && a.type == d.type) {
            return true;
        }
        return false;
    }

    public void renewBoard(int m, int n, Block[][] board) {
        for (int i = 0; i < n; i++) {
            renewColumn(m, i, board);
        }
    }

    public void renewColumn(int m, int i, Block[][] board) {
        int numMove = 0;
        for (int j = m - 1; j >= 0; j--) {
            if (board[j][i].matched) {
                numMove++;
            } else if (numMove > 0) {
                board[j + numMove][i] = board[j][i];
            }
        }

        for (int j = numMove - 1; j >= 0; j--) {
            board[j][i] = new Block('X', true);
        }
    }
}

