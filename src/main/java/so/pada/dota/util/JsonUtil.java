package so.pada.dota.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static JSONObject parseObject(String json) {
        if (json == null || json.trim().length() == 0)
            return null;
        try {
            return (JSONObject) new JSONParser().parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list返回成json格式的字符串
     *
     * @param list the list to parse as string
     * @return 返回json格式的字符串
     */
    @SuppressWarnings("unchecked")
    public static String parseList(List<String> list) {
        if (list == null || list.size() == 0)
            return null;
        if (list.size() == 1)
            return list.get(0);
        JSONArray array = new JSONArray();
        array.addAll(list);
        return array.toJSONString();
    }

    /**
     * 将json格式的字符串，变为list实例
     *
     * @param strlist json格式的字符串
     * @return 返回list
     */
    public static List<String> parseArray(String strlist) {
        if (!(strlist.startsWith("[\"") && strlist.endsWith("\"]"))) {
            List<String> list = new ArrayList<String>();
            list.add(strlist);
            return list;
        }
        List<String> list = new ArrayList<String>();
        try {
            JSONArray array = (JSONArray) new JSONParser().parse(strlist);
            for (int i = 0, len = array.size(); i < len; i++) {
                list.add((String) array.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            list.add(strlist);
            System.err.println("------------------------" + strlist + "---------------------------");
        }
        return list;
    }

}
