package main;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DartGame {
    public int solution(String dartResult) {
        String[] games = splitGames(dartResult);
        int[] gameScores = Arrays.stream(games)
                .mapToInt(game -> getScore(game))
                .toArray();
        for (int i = 0; i < 3; i++) {
            int option = getOption(games[i]);
            gameScores[i] *= option;
            if (option == 2 && i > 0) {
                gameScores[i-1] *= option;
            }
        }

        return Arrays.stream(gameScores).sum();
    }

    public String[] splitGames(String dartResult) {
        Pattern pattern = Pattern.compile("[0-9]+[SDT][*#]?");
        Matcher matcher = pattern.matcher(dartResult);
        String[] result = new String[3];
        for (int i = 0; i < 3; i++) {
            if (matcher.find()) {
                result[i] = matcher.group();
            } else {
                throw new UnknownError();
            }
        }
        return result;
    }

    public int getScore(String game) {
        Matcher scoreMatcher = Pattern.compile("[0-9]+").matcher(game);
        Matcher bonusMatcher = Pattern.compile("[SDT]").matcher(game);

        if (!scoreMatcher.find()) {
            throw new UnknownError();
        }

        int score = Integer.parseInt(scoreMatcher.group());

        if (!bonusMatcher.find()) {
            throw new UnknownError();
        }
        String bonus = bonusMatcher.group();
        if (bonus.equals("D")) {
            return score*score;
        } else if (bonus.equals("T")) {
            return score*score*score;
        }

        return score;
    }

    public int getOption(String game) {
        Matcher optionMatcher = Pattern.compile("[*#]").matcher(game);
        if (!optionMatcher.find()) {
            return 1;
        }

        if (optionMatcher.group().equals("#")) {
            return -1;
        } else {
            return 2;
        }
    }
}
