/**
 * 20181017
 * 백준 14501 퇴사
 *
 * 1. DP로 풀 수 있다.
 *
 */

package main;

import java.util.Scanner;

public class LeavingOffice {

    private int N;
    private int[] jobList;
    private int[] fareList;
    private int[] maxIncomeList;
    private int totalMax;

    public static void main(String[] args) {
        LeavingOffice test = new LeavingOffice();
        test.run();
    }

    public void run() {
        getInput();
        maxIncomeList = new int[N];
        totalMax = 0;
        for (int i = N-1; i >= 0; i--) {
            fillMaxIncomeList(i);
        }
        System.out.println(totalMax);
    }

    public void getInput() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        jobList = new int[N];
        fareList = new int[N];
        for (int i = 0; i < N; i++) {
            jobList[i] = sc.nextInt();
            fareList[i] = sc.nextInt();
        }
    }

    public void fillMaxIncomeList(int index) {
        int jobEndIndex = index + jobList[index];
        int maxIncome = 0;
        if (jobEndIndex <= N) {
            maxIncome += fareList[index];
            maxIncome += getMaxIncome(jobEndIndex);
        }

        if (totalMax < maxIncome) {
            totalMax = maxIncome;
        }

        maxIncomeList[index] = maxIncome;
    }

    public int getMaxIncome(int from) {
        int max = 0;
        for (int i = from; i < N; i++) {
            if (max < maxIncomeList[i]) {
                max = maxIncomeList[i];
            }
        }
        return max;
    }
}
