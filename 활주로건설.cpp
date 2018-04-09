// Created by Dooho on 2018. 4. 9..
//
//
// 180408 삼성SWexpert 코딩테스트 준비 (난이도 3)
// 코드 재사용성에 대해서..

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