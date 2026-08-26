
package codepath;

public class ArticleResource extends Resource {
    public ArticleResource(String title, String url) {
        super(title, url);
    }

    @Override
    public String getType() {
        return "Article";
    }
}
