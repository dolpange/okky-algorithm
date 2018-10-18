/**
 * 20181018
 *
 * 백준 15686번 [치킨배달]
 *
 * 1. 완전탐색 문제.
 *
 */

package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

class ChickenDelivery {
    private int N, M;
    private List<Position> houseList;
    private List<Position> chickenPlaceList;


    public class Position {
        public int row, col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getDistance(Position other) {
            return Math.abs(this.row - other.row) + Math.abs(this.col - other.col);
        }
    }

    public static void main(String[] args) {
        ChickenDelivery test = new ChickenDelivery();
        test.run();
    }

    public void run() {
        getInput();
        Stack<Position> chosenChickenPlaceList = new Stack<>();
        int result = dfs(chosenChickenPlaceList, 0);
        System.out.println(result);
    }

    public int dfs(Stack<Position> chosenChickenPlaceList, int chosenChickenPlaceEndIndex) {
        if (chosenChickenPlaceList.size() == M) {
            return getCityChickenDistance(chosenChickenPlaceList);
        }

        if (M - chosenChickenPlaceList.size() > chickenPlaceList.size() - chosenChickenPlaceEndIndex) {
            return Integer.MAX_VALUE;
        }

        int minChickenDistance = Integer.MAX_VALUE;
        int chickenDistance;
        for (int i = chosenChickenPlaceEndIndex; i < chickenPlaceList.size(); i++) {
            chosenChickenPlaceList.push(chickenPlaceList.get(i));
            chickenDistance = dfs(chosenChickenPlaceList, i + 1);
            if (minChickenDistance > chickenDistance) {
                minChickenDistance = chickenDistance;
            }
            chosenChickenPlaceList.pop();
        }
        return minChickenDistance;
    }

    public int getCityChickenDistance(Stack<Position> chosenChickenPlaceList) {
        int cityChickenDistance = 0;
        for (int i = 0; i < houseList.size(); i++) {
            cityChickenDistance += getChickenDistance(houseList.get(i), chosenChickenPlaceList);
        }

        return cityChickenDistance;
    }

    public int getChickenDistance(Position house, Stack<Position> chosenChickenPlaceList) {
        int minDistance = Integer.MAX_VALUE;
        int distance;
        for (int i = 0; i < chosenChickenPlaceList.size(); i++) {
            distance = house.getDistance(chosenChickenPlaceList.get(i));
            if (minDistance > distance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }

    public void getInput() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        houseList = new ArrayList<>();
        chickenPlaceList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                evaluatePlace(sc.nextInt(), i, j);
            }
        }
    }

    public void evaluatePlace(int place, int row, int col) {
        if (place == 1) {
            houseList.add(new Position(row, col));
        } else if (place == 2) {
            chickenPlaceList.add(new Position(row, col));
        }
    }
}
