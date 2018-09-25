/**
 * 20180925
 * Kakao Blind 2017
 * 3차 [압축]
 */

package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compression {

    Map<String, Integer> dictionary;
    List<Integer> encodedSequence;
    int nextIndex;

    public int[] solution(String msg) {
        dictionary = new HashMap<>();
        encodedSequence = new ArrayList<>();
        nextIndex = 1;
        initDictionary();
        parseMsg(msg);

        int[] answer = new int[encodedSequence.size()];
        for (int i = 0; i < encodedSequence.size(); i++) {
            answer[i] = encodedSequence.get(i);
        }

        return answer;
    }

    public void initDictionary() {
        char c = 'A';
        for (int i = 0; i < 26 ; i++) {
            dictionary.put(String.valueOf(c), nextIndex++);
            c += 1;
        }
    }

    public void parseMsg(String msg) {
        int from = 0;
        int to;
        while (from < msg.length()) {
            to = longestMatch(msg, from);
            encodeSubstring(msg, from, to);
            from = to;
        }
    }

    // encodedSequence에 추가하고, dictionary에 추가
    public void encodeSubstring(String msg, int from, int to) {
        encodedSequence.add(dictionary.get(msg.substring(from, to)));
        if (to < msg.length()) {
            dictionary.put(msg.substring(from, to + 1), nextIndex++);
        }
    }

    // 제일 길게 매치 되는 substring의 마지막 index 반환
    public int longestMatch(String msg, int from) {
        for (int i = from+1; i <= msg.length(); i++) {
            if (!dictionary.containsKey(msg.substring(from, i))) {
                return i-1;
            }
        }
        return msg.length();
    }
}
