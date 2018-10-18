/**
 * 20181018
 *
 * 백준 15684 [사다리 조작]
 *
 * 1. 완전 탐색 문제.
 *
 * 2. 사다리 타기의 결과를 표현하는 방법에 대한 고민이 필요하다.
 * 사다리 게임의 초기 상태를 각 세로줄에 대하여 [0, 1, 2, 3, 4]라고 정의할 때,
 * 첫번째 세로줄과 두번째 세로줄 사이에 가로줄 사다리가 존재할 경우,
 * 변화한 상태를 [1, 0, 2, 3, 4]로 표현할 수 있다.
 * 이는 첫번째 세로줄과 두번째 세로줄의 상태값이 swap된 것이다.
 *
 */

import java.util.Arrays;
import java.util.Scanner;

public class LadderGame {

    private int N, M, H;
    private boolean[][] board;

    public static void main(String[] args) {
        LadderGame test = new LadderGame();
        Scanner sc = new Scanner(System.in);
        test.N = sc.nextInt();
        test.M = sc.nextInt();
        test.H = sc.nextInt();
        test.board = new boolean[test.H][test.N-1];
        for (int i = 0; i < test.board.length; i++) {
            Arrays.fill(test.board[i], false);
        }

        for (int i = 0; i < test.M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            test.board[a - 1][b - 1] = true;
        }

        for (int i = 0; i < 4; i++) {
            int result = test.bfs(test.board, i, 0);
            if (result >= 0) {
                System.out.println(result);
                return;
            }
        }
        System.out.println(-1);
    }

    public int[] initResult(int N) {
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            result[i] = i;
        }
        return result;
    }

    public void swap(int[] result, int b) {
        int x = result[b];
        result[b] = result[b + 1];
        result[b + 1] = x;
        return;
    }

    public boolean isAnswer(int[] result) {
        for (int i = 0; i < result.length; i++) {
            if (result[i] != i) {
                return false;
            }
        }
        return true;
    }

    public boolean runGame(boolean[][] board) {
        int[] result = initResult(N);
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (board[i][j]) {
                    swap(result, j);
                }
            }
        }
        return isAnswer(result);
    }

    public int bfs(boolean[][] board, int numBridgesToBuild, int newBridgesSoFar) {
        if (numBridgesToBuild == newBridgesSoFar) {
            if (runGame(board)) {
                return newBridgesSoFar;
            } else {
                return -1;
            }
        }

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (isLegitimateBridge(board, i, j)) {
                    board[i][j] = true;
                    int result = bfs(board, numBridgesToBuild, newBridgesSoFar + 1);
                    if (result >= 0) {
                        return result;
                    }
                    board[i][j] = false;
                }
            }
        }

        return -1;
    }

    public boolean isLegitimateBridge(boolean[][] board, int a, int b) {
        if (board[a][b] == true) {
            return false;
        }

        if (b - 1 >= 0 && board[a][b - 1] == true) {
            return false;
        }

        if (b + 1 < board[a].length && board[a][b + 1] == true) {
            return false;
        }

        return true;
    }
}
