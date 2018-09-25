package kakao2019;

import java.util.ArrayList;
import java.util.List;

public class Mukbang {

    public List<Tray> trayList;
    public int lastTrayNum;

    public class Tray {
        int num;
        int remainder = 0;

        public Tray(int num, long remainder) {
            this.num = num;
            this.remainder = (int) remainder;
        }
    }

    public int solution(int[] food_times, long k) {
        trayList = new ArrayList<>();
        parseFoodTimes(food_times);
        long amountToGo = k;
        while (amountToGo > 0) {
            if (trayList.isEmpty()) {
                break;
            }
            amountToGo = eat(amountToGo);
        }
        return getRestartTrayNum();
    }

    public void parseFoodTimes(int[] food_times) {
        for (int i = 0; i < food_times.length; i++) {
            trayList.add(new Tray(i+1, food_times[i]));
        }
    }

    public long eat(long amount) {
        long quota = amount / trayList.size();
        if (quota == 0) {
            quota = 1;
        }
        long amountToGo = amount;
        List<Tray> newTrayList = new ArrayList<>();
        for (Tray tray : trayList) {
            if (amountToGo <= 0) {
                newTrayList.add(tray);
                continue;
            }

            if (tray.remainder > quota) {
                newTrayList.add(new Tray(tray.num, tray.remainder - quota));
                amountToGo -= quota;
            } else {
                amountToGo -= tray.remainder;
            }

            lastTrayNum = tray.num;
        }

        trayList = newTrayList;
        return amountToGo;
    }

    public int getRestartTrayNum() {
        if (trayList.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < trayList.size(); i++) {
            if (trayList.get(i).num > lastTrayNum) {
                return trayList.get(i).num;
            }
        }

        return trayList.get(0).num;
    }
}
