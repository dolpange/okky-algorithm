/**
 * 20180926
 * Kakao Blind 2017
 * 3차 파일명 정렬
 *
 * 1. Regular expression을 익혀두면 좋다.
 *  \d는 숫자, \D는 숫자 이외의 것.
 *  *는 0 혹은 그 이상, +는 1 혹은 그 이상.
 *  split을 할 때 delimiter를 *로 표현하면 0개를 포함해서 원하지 않는 결과를 얻음.
 *
 * 2. Collection이 아닌 배열을 정렬할 때는 Arrays.sort를 사용할 수 있다.
 *
 * 3. Java에서 지원하는 sorting은 stable함.
 *
 * 4. Comparator를 이용해서 custom한 sorting하는 방법 숙지하기.
 *
 */

package main;

import java.util.*;

public class FileSorting {
    public String[] solution(String[] files) {
        Arrays.sort(files, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (!getHead(o1).equals(getHead(o2))) {
                    return getHead(o1).compareTo(getHead(o2));
                }
                return getNumber(o1) - getNumber(o2);
            }
        });
        return files;
    }


    public String getHead(String file) {
        return file.split("\\d+")[0].toLowerCase();
    }

    public int getNumber(String file) {
        String numberPart = file.split("\\D+")[1];
        if (numberPart.length() > 5) {
            numberPart = numberPart.substring(0, 5);
        }
        return Integer.parseInt(numberPart);
    }
}
