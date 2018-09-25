package kakao2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FailRate {

    public class Stage implements Comparable<Stage> {
        int stageNum;
        double currentUser;
        double cumulativeUser;

        public Stage(int stageNum, int currentUser, int cumulativeUser) {
            this.stageNum = stageNum;
            this.currentUser = currentUser;
            this.cumulativeUser = cumulativeUser;
        }

        public double getFailRate() {
            if (currentUser == 0 || cumulativeUser == 0) {
                return 0;
            }
            return currentUser / cumulativeUser;
        }

        @Override
        public int compareTo(Stage o) {
            if (this.getFailRate() == o.getFailRate()) {
                return this.stageNum - o.stageNum;
            }
            if (this.getFailRate() > o.getFailRate()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
    public int[] solution(int N, int[] stages) {
        int[] stageStatus = parseStates(N, stages);
        List<Stage> sortedStageList = getSortedStageList(stageStatus);
        return getResult(sortedStageList);
    }

    public int[] parseStates(int N, int[] stages) {
        int[] stageStatus = new int[N+1];
        for (int stage : stages) {
            stageStatus[stage-1]++;
        }
        return stageStatus;
    }

    public List<Stage> getSortedStageList(int[] stageStatus) {
        int cumulativeUser = stageStatus[stageStatus.length-1];
        int currentUser = 0;
        List<Stage> stageList = new ArrayList<>();
        for (int i = stageStatus.length-2; i >= 0 ; i--) {
            currentUser = stageStatus[i];
            cumulativeUser += currentUser;
            stageList.add(new Stage(i, currentUser, cumulativeUser));
        }

        Collections.sort(stageList);
        return stageList;
    }

    public int[] getResult(List<Stage> stageList) {
        return stageList.stream().mapToInt(stage -> stage.stageNum + 1).toArray();
    }
}
