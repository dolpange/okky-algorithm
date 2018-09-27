/**
 * 20180927
 * Kakao Blind 2017
 * 3차 방금그곡
 *
 * 1. 별로 좋지 않은 접근 방식이었다.
 *
 * 2. 주어진 String에서 'C'와 'C#'을 다른 문자로 취급해야 했는데,
 *  이를 위해 각 문자를 tokenize해서 배열로 만들었었다.
 *  하지만 이렇게 하면 sequence의 비교가 어려워지고,
 *  그래서 종국에는 KMP 알고리즘을 직접 구현해야하는 상황까지 가버렸다.
 *
 * 3. 잔꾀를 내어서 'C#'을 소문자 'c'로 대치시켰더라면 String을 배열로 만들지는 않아도 되었고,
 *  그러면 sequence 비교도 매우 쉬워진다.
 *
 * 4. 하지만 오랜만에 KMP 알고리즘을 구현해보는 경험을 했고, 숙지해두면 좋을 것이다.
 *
 */



package main;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SongRightNow {

    private List<Song> songList;
    private List<Integer> prefixlist;

    public class Song {
        private int id;
        private List<String> melody;
        private int length;
        private String title;

        public Song(int id, List<String> melody, int length, String title) {
            this.id = id;
            this.melody = melody;
            this.length = length;
            this.title = title;
        }

        public void setMelody(List<String> melody) {
            this.melody = melody;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public boolean melodyMatch(List<String> clipMelody) {
            List<String> completeMelody = this.getCompleteMelody();
            if (completeMelody.size() < clipMelody.size()) {
                return false;
            }

            int indexOfClip = 0;
            int curIndexOfMelody = 0;
            while (curIndexOfMelody < completeMelody.size()) {
                if (indexOfClip >= clipMelody.size()) {
                    return true;
                }

                if (completeMelody.get(curIndexOfMelody).equals(clipMelody.get(indexOfClip))) {
                    indexOfClip++;
                    curIndexOfMelody++;
                } else {
                    if (indexOfClip == 0) {
                        curIndexOfMelody++;
                    } else {
                        indexOfClip = prefixlist.get(indexOfClip-1);
                    }
                }
            }

            if (indexOfClip >= clipMelody.size()) {
                return true;
            }
            return false;
        }

        public List<String> getCompleteMelody() {
            if (melody.size() > length) {
                return melody.subList(0, length);
            }

            List<String> result = new ArrayList<>();
            for (int i = 0; i < length / melody.size(); i++) {
                result.addAll(melody);
            }

            if (length % melody.size() != 0) {
                result.addAll(melody.subList(0, length % melody.size()));
            }

            return result;
        }
    }

    public String solution(String m, String[] musicinfos) {
        this.songList = parseMusicinfos(musicinfos);
        calculatePrefixIndex(m);
        return search(parseMelody(m));
    }

    public List<Song> parseMusicinfos(String[] musicinfos) {
        List<Song> result = new ArrayList<>();

        int id = 0;

        for (String musicinfo : musicinfos) {
            String[] tokens = musicinfo.split(",");
            LocalTime startTime = LocalTime.parse(tokens[0]);
            LocalTime endTime = LocalTime.parse(tokens[1]);
            List<String> melody = parseMelody(tokens[3]);
            int duration = (int) startTime.until(endTime, ChronoUnit.MINUTES);

            result.add(new Song (id++, melody, duration, tokens[2]));
        }

        return result;
    }

    public List<String> parseMelody(String melody) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < melody.length(); i++) {
            if (i+1 < melody.length() && melody.charAt(i+1) == '#') {
                result.add(melody.substring(i, i+2));
                i++;
            } else {
                result.add(melody.substring(i, i+1));
            }
        }

        return result;
    }

    public String search(List<String> melody) {
        List<Song> matchingSongList = findMatchingSongs(melody);
        if (matchingSongList.isEmpty()) {
            return "(None)";
        }

        return matchingSongList.stream().max(new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                if (o1.length != o2.length) {
                    return o1.length - o2.length;
                }
                return o2.id - o1.id;
            }
        }).get().title;
    }

    public List<Song> findMatchingSongs(List<String> melody) {
        List<Song> result = new ArrayList<>();
        for (Song song : songList) {
            if (song.melodyMatch(melody)) {
                result.add(song);
            }
        }
        return result;
    }

    public void calculatePrefixIndex(String melody) {
        prefixlist = new ArrayList<>();
        prefixlist.add(0);
        int prevPrefixLength = 0;
        int curIndex = 1;
        while (curIndex < melody.length()) {
            if (melody.charAt(prevPrefixLength) == melody.charAt(curIndex)) {
                prevPrefixLength++;
                curIndex++;
                prefixlist.add(prevPrefixLength);
            } else {
                if (prevPrefixLength == 0) {
                    curIndex++;
                    prefixlist.add(prevPrefixLength);
                } else {
                    prevPrefixLength = prefixlist.get(prevPrefixLength-1);
                }
            }
        }
    }
}
