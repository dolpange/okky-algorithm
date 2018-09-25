package main;

import java.util.ArrayList;
import java.util.List;

public class Cache {

    public int solution(int cacheSize, String[] cities) {
        if (cacheSize == 0) {
            return 5 * cities.length;
        }

        List<String> cache = new ArrayList<>();

        int answer = 0;

        for (int i = 0; i < cities.length; i++) {
            String city = cities[i].toLowerCase();
            if (hit(cache, city)) {
                answer += 1;
                cache.remove(city);
                cache.add(city);
            } else {
                answer += 5;
                if (cache.size() < cacheSize) {
                    cache.add(city);
                } else {
                    cache.remove(0);
                    cache.add(city);
                }
            }
        }

        return answer;
    }

    public boolean hit(List<String> cache, String city) {
        return cache.contains(city);
    }
}
