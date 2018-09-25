package main;

public class SecretMap {

    public String[] solution(int n, int[] arr1, int[] arr2) {
        int[] map = new int[n];
        String[] answer = new String[n];
        for (int i = 0; i < n; i++) {
            map[i] = orOperation(arr1[i], arr2[i]);
            answer[i] = createMap(map[i], n);
        }
        return answer;
    }

    public int orOperation(int a, int b) {
        return a | b;
    }

    public String createMap(int a, int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int lastDigit = a & 1;
            if (lastDigit == 0) {
                sb.append(' ');
            } else {
                sb.append('#');
            }
            a = a >> 1;
        }
        return sb.reverse().toString();
    }
}
