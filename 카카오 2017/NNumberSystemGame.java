package main;

import java.util.ArrayList;
import java.util.List;

public class NNumberSystemGame {

    private int numSystem;
    private int nextNumber;
    private String tape;

    public String solution(int n, int t, int m, int p) {
        numSystem = n;
        nextNumber = 0;
        tape = new String();
        List<Character> numbersFromTape = pickNumbersFromTape(p-1, m, t);
        String answer = "";

        for (Character number : numbersFromTape) {
            answer += number;
        }
        return answer;
    }

    public List<Character> pickNumbersFromTape(int initNumber, int lenLeap, int numNumbersToPick) {
        List<Character> answer = new ArrayList<>();

        for (int i = 0; i < numNumbersToPick; i++) {
            int indexToPick = initNumber + lenLeap * i;
            answer.add(pickNumberFromTape(indexToPick));
        }

        return answer;
    }

    public Character pickNumberFromTape(int indexToPick) {
        while (tape.length() <= indexToPick) {
            tape += Integer.toString(nextNumber++, numSystem).toUpperCase();
        }

        return tape.charAt(indexToPick);
    }
}