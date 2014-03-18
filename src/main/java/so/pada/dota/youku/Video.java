package so.pada.dota.youku;

/**
 * Created with IntelliJ IDEA.
 * User: gk23
 * Date: 14-3-18
 * Time: 下午8:50
 * To change this template use File | Settings | File Templates.
 */
public class Video {
    private String id;
    private String link;
    private String title;
    private String duration;
    private String published;
    private String description;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
