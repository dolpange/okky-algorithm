package kakao2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenChat {
    public class Record {
        String action;
        String id;

        public Record(String action, String id) {
            this.action = action;
            this.id = id;
        }
    }

    public String[] solution(String[] record) {
        HashMap<String, String> nicknameMap = parseNickname(record);
        List<Record> history = parseHistory(record);
        return getMessages(history, nicknameMap);
    }

    public HashMap<String, String> parseNickname(String[] record) {
        HashMap<String, String> nicknameMap = new HashMap<>();
        for (String s : record) {
            if (s.startsWith("Leave")) {
                continue;
            }

            String id, nickname;
            String[] tokens = s.split(" ");
            id = tokens[1];
            nickname = tokens[2];
            nicknameMap.put(id, nickname);
        }
        return nicknameMap;
    }

    public List<Record> parseHistory(String[] record) {
        List<Record> history = new ArrayList<>();
        for (String s : record) {
            if (s.startsWith("Change")) {
                continue;
            }

            String[] tokens = s.split(" ");
            String action = tokens[0];
            String id = tokens[1];
            history.add(new Record(action, id));
        }
        return history;
    }

    public String[] getMessages(List<Record> recordList, HashMap<String, String> nicknameMap) {
        String[] result = new String[recordList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = getMessage(recordList.get(i), nicknameMap);
        }
        return result;
    }

    public String getMessage(Record record, HashMap<String, String> nicknameMap) {
        if (record.action.equals("Enter")) {
            return getEnterMessage(nicknameMap.get(record.id));
        }
        return getLeaveMessage(nicknameMap.get(record.id));
    }

    public String getEnterMessage(String nickname) {
        return String.format("%s님이 들어왔습니다.", nickname);
    }

    public String getLeaveMessage(String nickname) {
        return String.format("%s님이 나갔습니다.", nickname);
    }
}
