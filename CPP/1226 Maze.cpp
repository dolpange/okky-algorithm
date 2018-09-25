/*
 * 20180411 삼성SWexpert 코딩테스트 준비
 * 1226. [S/W 문제해결 기본] 7일차 - 미로1
 *
 * 일종의 bfs 방식으로 길을 순회하는 방식입니다.
 * 좌우상하에 있는 칸 중에서 막혀있지 않은 칸들은 막은 뒤에 queue에 넣어줍니다.
 * queue에서 칸 하나씩을 pop하면서 다시 좌우상하의 칸에 대해 작업을 반복합니다.
 * input 받는 것, 이미 살펴본 칸을 다시 살펴보는 일이 없도록 하는 것 등 구현에서 까다로운 부분들이 좀 있네요.
 *
 */

#include <iostream>
#include <queue>
#include <tuple>
#include <string>

using namespace std;

// 목적지에 도착하면 true를 리턴하고, 목적지가 아닐 경우라면 해당 칸을 막은 다음, queue에 넣어주는 함수입니다.
bool checkBox(int (&maze)[16][16], int row, int col, queue <tuple<int,int>> &fringe) {
    if (maze[row][col] == 3) {
        return true;
    } else {
        maze[row][col] = 1;
        fringe.push(tuple<int,int>(row, col));
        return false;
    }
};

int main() {
    int testNum;
    int answer;
    int maze[16][16];
    tuple<int,int> start;
    for (int i = 0; i < 10; i++) {
        cin >> testNum;
        answer = 0;

        for (int row = 0; row < 16; row++) {
            string line;
            cin >> line;
            for (int col = 0; col < 16; col++) {
                maze[row][col] = stoi(line.substr(col, 1));
                if (maze[row][col] == 2) {
                    start = tuple<int,int> (row, col);
                }
            }
        }

        queue <tuple<int,int>> fringe;
        fringe.push(start);
        while (!fringe.empty()) {
            int row = get<0>(fringe.front());
            int col = get<1>(fringe.front());
            fringe.pop();
            int& element = maze[row][col];
            element = 1;
            
            // 상하좌우, 총 4번의 경우에 대하여, 막혀있지 않다면 checkBox 함수를 호출하는 부분입니다.
            // 안전하게 row와 col의 범위도 동시에 체크해줬습니다.
            if (row+1 < 16 && maze[row+1][col] != 1) {
                if (checkBox(maze, row+1, col, fringe) == true) {
                    answer = 1;
                    break;
                }
            }
            if (col + 1 < 16 && maze[row][col+1] != 1) {
                if (checkBox(maze, row, col+1, fringe) == true) {
                    answer = 1;
                    break;
                }
            }
            if (0 <= row -1 && maze[row-1][col] != 1) {
                if (checkBox(maze, row-1, col, fringe) == true) {
                    answer = 1;
                    break;
                }
            }
            if (0 <= col -1 && maze[row][col-1] != 1) {
                if (checkBox(maze, row, col-1, fringe) == true) {
                    answer = 1;
                    break;
                }
            }
        }
        cout << "#" << testNum << " " << answer << '\n';
    }

    return 0;
}
