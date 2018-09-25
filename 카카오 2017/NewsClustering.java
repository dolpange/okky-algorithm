package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsClustering {
    public int solution(String str1, String str2) {
        List<String> tokens1 = tokenize(str1);
        List<String> tokens2 = tokenize(str2);
        return calculateJ(tokens1, tokens2);
    }

    public List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < input.length()-1; i++) {
            if (isAlphabet(input.charAt(i)) && isAlphabet(input.charAt(i+1))) {
                tokens.add(input.substring(i, i+2).toLowerCase());
            }
        }

        Collections.sort(tokens);
        return tokens;
    }

    public int calculateJ(List<String> tokens1, List<String> tokens2) {
        if (tokens1.isEmpty() && tokens2.isEmpty()) {
            return 65536;
        }

        if (tokens1.isEmpty() || tokens2.isEmpty()) {
            return 0;
        }

        int index1 = 0, index2 = 0;
        int numIntersection = 0;

        while(index1 < tokens1.size() && index2 < tokens2.size()) {
            if (tokens1.get(index1).equals(tokens2.get(index2))) {
                numIntersection++;
                index1++;
                index2++;
            } else if (tokens1.get(index1).compareTo(tokens2.get(index2)) > 0) {
                index2++;
            } else {
                index1++;
            }
        }

        if (numIntersection == 0) {
            return 0;
        }

        int numUnion = tokens1.size() + tokens2.size() - numIntersection;

        return 65536 * numIntersection / numUnion;
    }


    public boolean isAlphabet(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return true;
        }
        return false;
    }
}
