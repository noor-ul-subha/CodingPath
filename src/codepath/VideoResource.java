
package codepath;

public class VideoResource extends Resource {
    public VideoResource(String title, String url) {
        super(title, url);
    }

    @Override
    public String getType() {
        return "Video";
    }
}
