/*
 * 180409 삼성 SW expert 코딩테스트 준비
 * 1952. [모의 SW 역량테스트] 활주로 건설
 *
 */

#include <iostream>
using namespace std;

int main() {
    int numCase;
    cin >> numCase;

    int oneDay, oneMonth, threeMonth, oneYear;
    int monthlyPlan[12];
    int plans[3][12];
    int totalSum;

    for (int test_case = 1; test_case <= numCase; test_case++) {
        totalSum = 0;

        cin >> oneDay >> oneMonth >> threeMonth >> oneYear;
        for (int& month : monthlyPlan) {
            cin >> month;
        }

        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                plans[0][i] = monthlyPlan[i] * oneDay;
                plans[1][i] = oneMonth;
            } else {
                plans[0][i] = min (min (plans[0][i-1], plans[1][i-1]), plans[2][i-1]) + monthlyPlan[i] * oneDay;
                plans[1][i] = min (min (plans[0][i-1], plans[1][i-1]), plans[2][i-1])  + oneMonth;
            }

            if (0 <= i-3) {
                plans[2][i] = min (min (plans[0][i-3], plans[1][i-3]), plans[2][i-3]) + threeMonth;
            } else {
                plans[2][i] = threeMonth;
            }
        }

        int answer = min (min (min (plans[0][11], plans[1][11]), plans[2][11]), oneYear);
        cout << "#" << test_case << " " << answer << '\n';

    }
}