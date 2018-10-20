package main;

/**
 * 20181020
 *
 * 백준 15683번 [감시]
 *
 * 1. 구현이 까다로운 문제. 다섯가지 카메라 타입에 따라서 회전할 때 감시 가능한 방향의 집합을 동/서/남/북으로 나눠서 하드 코딩했다.
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

class Surveillance {

    private int N, M;
    private int[][] space;
    private ArrayList<Camera> cameraList;

    public class Camera {
        int row, col, type;

        public Camera(int row, int col, int type) {
            this.row = row;
            this.col = col;
            this.type = type;
        }

        public String[][] getPossibleDirectionSetList() {
            String[][] result;
            if (this.type == 1) {
                result = new String[4][];
                result[0] = new String[]{"N"};
                result[1] = new String[]{"E"};
                result[2] = new String[]{"W"};
                result[3] = new String[]{"S"};
            } else if (this.type == 2) {
                result = new String[2][];
                result[0] = new String[]{"N", "S"};
                result[1] = new String[]{"E", "W"};
            } else if (this.type == 3) {
                result = new String[4][];
                result[0] = new String[]{"N", "E"};
                result[1] = new String[]{"W", "N"};
                result[2] = new String[]{"S", "W"};
                result[3] = new String[]{"E", "S"};
            } else if (this.type == 4) {
                result = new String[4][];
                result[0] = new String[]{"N", "E", "S"};
                result[1] = new String[]{"W", "N", "E"};
                result[2] = new String[]{"S", "W", "N"};
                result[3] = new String[]{"E", "S", "W"};
            } else {
                result = new String[1][];
                result[0] = new String[]{"N", "E", "W", "S"};
            }
            return result;
        }
    }

    public static void main(String[] args) {
        Surveillance test = new Surveillance();
        test.run();
    }

    public void run() {
        getInput();
        int answer = minBlindSpot(new Stack<>());
        System.out.print(answer);
    }

    public int minBlindSpot(Stack<String[]> directionsList) {
        if (directionsList.size() == cameraList.size()) {
            return getBlindSpot(directionsList);
        }

        String[][] possibleDirectionSetList = cameraList.get(directionsList.size()).getPossibleDirectionSetList();
        int minSize = Integer.MAX_VALUE;
        int size;
        for (int i = 0; i < possibleDirectionSetList.length; i++) {
            directionsList.push(possibleDirectionSetList[i]);
            size = minBlindSpot(directionsList);
            if (minSize > size) {
                minSize = size;
            }
            directionsList.pop();
        }
        return minSize;
    }

    public int getBlindSpot(Stack<String[]> directionsList) {
        int[][] copiedSpace = copy(space);
        for (int i = 0; i < directionsList.size(); i++) {
            mark(copiedSpace, i, directionsList.get(i));
        }
        int result = calculateBlindSpot(copiedSpace);
        return result;
    }

    public int calculateBlindSpot(int[][] space) {
        int count = 0;
        for (int i = 0; i < space.length; i++) {
            for (int j = 0; j < space[0].length; j++) {
                if (space[i][j] == 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public void mark(int[][] space, int cameraIndex, String[] directions) {
        int row = cameraList.get(cameraIndex).row;
        int col = cameraList.get(cameraIndex).col;
        for (int i = 0; i < directions.length; i++) {
            markOneDirection(space, row, col, directions[i]);
        }
    }

    public void markOneDirection(int[][] space, int row, int col, String direction) {
        if (direction.equals("N") && row - 1 >= 0) {
            for (int r = row - 1; r >= 0; r--) {
                if (space[r][col] == 6) {
                    return;
                } else if (space[r][col] == 0) {
                    space[r][col] = -1;
                }
            }
        } else if (direction.equals("S") && row + 1 < N) {
            for (int r = row + 1; r < N; r++) {
                if (space[r][col] == 6) {
                    return;
                } else if (space[r][col] == 0) {
                    space[r][col] = -1;
                }
            }
        } else if (direction.equals("E") && col + 1 < M) {
            for (int c = col + 1; c < M; c++) {
                if (space[row][c] == 6) {
                    return;
                } else if (space[row][c] == 0) {
                    space[row][c] = -1;
                }
            }
        } else if (direction.equals("W") && col - 1 >= 0) {
            for (int c = col - 1; c >= 0; c--) {
                if (space[row][c] == 6) {
                    return;
                } else if (space[row][c] == 0) {
                    space[row][c] = -1;
                }
            }
        }
    }

    public int[][] copy(int[][] original) {
        int[][] copied = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            copied[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copied;
    }

    public void getInput() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        space = new int[N][];
        cameraList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            space[i] = new int[M];
            for (int j = 0; j < M; j++) {
                space[i][j] = sc.nextInt();
                if (space[i][j] > 0 && space[i][j] < 6) {
                    cameraList.add(new Camera(i, j, space[i][j]));
                }
            }
        }
    }
}
