package codepath;
import java.util.List;

public class Recommendation {
    private String title;
    private List<String> languages;
    private List<String> jobRoles;
    private List<PlatformLink> platforms;

    public Recommendation(String title, List<String> languages, List<String> jobRoles, List<PlatformLink> platforms) {
        this.title = title;
        this.languages = languages;
        this.jobRoles = jobRoles;
        this.platforms = platforms;
    }

    public String getTitle() { return title; }
    public List<String> getLanguages() { return languages; }
    public List<String> getJobRoles() { return jobRoles; }
    public List<PlatformLink> getPlatforms() { return platforms; }
}