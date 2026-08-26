package codepath;

public class PlatformLink {
    private String name;
    private String url;

    public PlatformLink(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() { return name; }
    public String getUrl() { return url; }
}