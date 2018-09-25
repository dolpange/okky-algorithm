/*
 * 20180410 삼성SWexpert 코딩테스트 준비
 * 4050. 재관이의 대량 할인
 *
 * 묶음의 모든 경우의 수를 비교할 필요는 없습니다.
 * 최대한 비싼 옷들을 많이 할인받기만 하면 되기 때문입니다.
 * 그래서 가장 비싼 옷 순서대로 세 개씩 묶음을 만들어주고, 세번째 옷을 할인해주면 됩니다.
 */

#include<iostream>
#include<vector>
#include<algorithm>

using namespace std;

int main(int argc, char** argv)
{
    int test_case;
    int T;
    int numClothes;

    cin>>T;
    for(test_case = 1; test_case <= T; ++test_case)
    {
        cin >> numClothes;
        vector<int> prices;
        int element;
        for (int i = 0; i < numClothes; i++) {
            cin >> element;
            prices.push_back(element);
        }
        sort(prices.begin(), prices.end());

        int answer = 0;
        int set = 0; // 옷이 3개씩 묶이도록 개수를 세는 변수
        for(auto rit = prices.rbegin(); rit != prices.rend(); rit++) {
            set = (set + 1) % 3;
            if (set != 0) {
                answer += *rit;
            }
        }

        cout << "#" << test_case << " " << answer << '\n';
    }
    return 0;
}