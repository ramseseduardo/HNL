package mx.gob.nl.fragment.model;

/**
 * Created by Ramses on 26/10/14.
 */
public class ModelList {

    private String title;
    private String url;

    public ModelList(String title, String url) {
        super();
        this.title = title;
        this.url = url;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
