package codepath;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private int id;
    private String title;
    private String description;
    private String content; // brief explanation shown under headings
    private List<Resource> resources;
    private boolean completed;

    public Topic(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = "";
        this.resources = new ArrayList<>();
        this.completed = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public List<Resource> getResources() { return resources; }
    public void addResource(Resource r) { resources.add(r); }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}