/*
 * 180408 삼성 SW expert 코딩테스트 준비
 * 4014. [모의 SW 역량테스트] 활주로 건설
 * 
 *
 * Row-major로 한 번 순회하고, column-major로 다시 한 번 순회하는 구조입니다.
 * 엄청 까다로운 알고리즘적 사고를 요구하지는 않지만, 여러가지 경우의 수에 대비해서 조건을 철저하게 지키는게 어렵네요.
 * 1) 앞 칸과 현재 칸이 같은 레벨일 경우
 * 2) 앞 칸보다 현재 칸이 한 레벨 높을 경우
 * 3) 앞 칸보다 현재 칸이 한 레벨 낮을 경우
 * 4) 앞 칸과 현재 칸이 두 레벨 이상 차이날 경우
 *
 * 각각의 경우에 대해서 생각해봐야 합니다.
 * loop를 돌 때 맨 첫번째 칸과 맨 마지막 칸을 제대로 점검하는지 체크하는 것도 중요하구요.
 */


#include<iostream>
#include<vector>
using namespace std;

int main(int argc, char** argv)
{
    int test_case;
    int T;
    cin>>T;

    for(test_case = 1; test_case <= T; ++test_case)
    {

        int N, X;
        cin >> N >> X;

        vector<vector<int>> land;
        vector<int> row;

        int height;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cin >> height;
                row.push_back(height);
            }
            land.push_back(row);
            row.clear();
        }

        int result = 0;

        // Row-major로 순회하기
        for (int i = 0; i < N; i++) {
            int pastHeight = land[i][0];
            int pastSameLevel = 0;
            bool needLeveling = false;
            bool constructionAvailable = true;

            for (int j = 0; j < N; j++) {
                // 경사면을 세워야 하는 상태인데, 여태까지의 거리로 봤을 때 경사면을 세울 수 있는 상태라면, 경사면을 세움.
                if (needLeveling == true && pastSameLevel >= X) {
                    needLeveling = false;
                    pastSameLevel -= X;
                }

                if (pastHeight == land[i][j]) {
                    pastSameLevel++;
                } else if (pastHeight - land[i][j] == 1) {
                    // 땅이 낮아져서 이제부터 경사면을 세워야 함
                    // 경사면 세웠어야 하는게 있었는데 아직도 못세웠다면 X
                    if (needLeveling == true) {
                        constructionAvailable = false;
                        break;
                    }
                    needLeveling = true;
                    pastSameLevel = 1;
                    pastHeight = land[i][j];
                } else if (land[i][j] - pastHeight == 1) {
                    // 땅이 높아져서, 앞 부분에 경사면을 세워야 함
                    // 경사면 세웠어야 하는게 있었는데 아직도 못세웠다면 X
                    if (needLeveling == true) {
                        constructionAvailable = false;
                        break;
                    }
                    // 경사면 지을 면적이 되지 않는다면 X
                    if (pastSameLevel < X) {
                        constructionAvailable = false;
                        break;
                    }
                    pastSameLevel = 1;
                    pastHeight = land[i][j];
                } else {
                    // 높이 차이가 1이상 나면 X
                    constructionAvailable = false;
                    break;
                }
            }

            if (needLeveling == true && pastSameLevel < X) {
                constructionAvailable = false;
            }

            if (constructionAvailable == true) {
                result++;
            }
        }
        
        // Column-major로 순회하기
        for (int i = 0; i < N; i++) {
            int pastHeight = land[0][i];
            int pastSameLevel = 0;
            bool needLeveling = false;
            bool constructionAvailable = true;

            for (int j = 0; j < N; j++) {
                // 경사면을 세워야 하는 상태인데, 여태까지의 거리로 봤을 때 경사면을 세울 수 있는 상태라면, 경사면을 세움.
                if (needLeveling == true && pastSameLevel >= X) {
                    needLeveling = false;
                    pastSameLevel -= X;
                }

                if (pastHeight == land[j][i]) {
                    pastSameLevel++;
                } else if (pastHeight - land[j][i] == 1) {
                    // 땅이 낮아져서 이제부터 경사면을 세워야 함
                    // 경사면 세웠어야 하는게 있었는데 아직도 못세웠다면 X
                    if (needLeveling == true) {
                        constructionAvailable = false;
                        break;
                    }
                    needLeveling = true;
                    pastSameLevel = 1;
                    pastHeight = land[j][i];
                } else if (land[j][i] - pastHeight == 1) {
                    // 땅이 높아져서, 앞 부분에 경사면을 세워야 함
                    // 경사면 세웠어야 하는게 있었는데 아직도 못세웠다면 X
                    if (needLeveling == true) {
                        constructionAvailable = false;
                        break;
                    }
                    // 경사면 지을 면적이 되지 않는다면 X
                    if (pastSameLevel < X) {
                        constructionAvailable = false;
                        break;
                    }
                    pastSameLevel = 1;
                    pastHeight = land[j][i];
                } else {
                    // 높이 차이가 1이상 나면 X
                    constructionAvailable = false;
                    break;
                }
            }

            if (needLeveling == true && pastSameLevel < X) {
                constructionAvailable = false;
            }

            if (constructionAvailable == true) {
                result++;
            }
        }

        cout << "#" << test_case << " " << result << '\n';
    }
    return 0;//정상종료시 반드시 0을 리턴해야합니다.
}
