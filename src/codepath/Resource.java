
package codepath;

public abstract class Resource {
    protected String title;
    protected String url;

    public Resource(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() { return title; }
    public String getUrl() { return url; }

    public abstract String getType();
}