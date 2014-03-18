package so.pada.dota.youku;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import so.pada.dota.util.HttpUtil;
import so.pada.dota.util.JsonUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gk23
 * Date: 14-3-18
 * Time: 下午8:47
 * To change this template use File | Settings | File Templates.
 */
public class VideoService {
    public static final String CLENT_ID = "cb79cfdaa0768aa9";
    public static final String SERECT_ID = "fec4f8bbd233b8f586762c6abde1623b";
    public static final String GET_BY_USER = "https://openapi.youku.com/v2/videos/by_user.json?client_id=" + CLENT_ID + "&user_name=";

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Video> getVideo() {
        String content = HttpUtil.getContent(GET_BY_USER + name);
        JSONObject json = JsonUtil.parseObject(content);
        JSONArray videos = (JSONArray)json.get("videos");
        Iterator<JSONObject> it =(Iterator<JSONObject>)videos.iterator();
        List<Video> list = new ArrayList<Video>(videos.size());
        while (it.hasNext()) {
            JSONObject object = it.next();
            Video video = new Video();
            video.setId(object.get("id").toString());
            video.setTitle(object.get("title").toString());
            video.setLink(object.get("link").toString());
            video.setPublished(object.get("published").toString());
            video.setDuration(object.get("duration").toString());
//            video.setDescription(object.get("description").toString());
            list.add(video);
        }
        return list;
    }
}
