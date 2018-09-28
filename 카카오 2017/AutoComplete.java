/**
 * 20180928
 * Kakao Blind 2017
 * 3차 자동완성
 *
 * 1. Recursive한 방식으로 풀었는데, 주어진 단어들의 개수를 계산하고, prefix에 맞춰 분류해서 다시 함수를 호출하는 방식.
 *
 * 2. 시간 복잡도는 단어 개수를 N, 최대 단어 길이를 M이라고 했을 때 
 *
 * 2. 많은 경우 Trie를 사용해서 풀었는데 시간복잡도에서 차이는 없는 것 같
 */

package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoComplete {

    public int solution(String[] words) {
        List<List<String>> classifiedWordsList = classifyByPrefix(new ArrayList<>(Arrays.asList(words)));
        int answer = 0;
        for (List<String> classifiedWords : classifiedWordsList) {
            answer += countTyping(classifiedWords);
        }

        return answer;
    }

    public int countTyping(List<String> words) {
        if (words.isEmpty()) {
            return 0;
        }

        if (words.size() == 1) {
            return 1;
        }

        int count = words.size();

        List<String> remainingWords = removePrefix(words);

        List<List<String>> classifiedWordsList = classifyByPrefix(remainingWords);

        for (List<String> classifiedWords : classifiedWordsList) {
            count += countTyping(classifiedWords);
        }

        return count;
    }

    public List<String> removePrefix(List<String> words) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (word.length() > 1) {
                result.add(word.substring(1));
            }
        }

        return result;
    }

    public List<List<String>> classifyByPrefix(List<String> words) {
        List<List<String>> classification = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            classification.add(new ArrayList<>());
        }

        for (String word : words) {
            classification.get((word.charAt(0) - 'a')).add(word);
        }

        return classification;
    }
}
