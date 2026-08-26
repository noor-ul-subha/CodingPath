
package codepath;

import java.util.ArrayList;
import java.util.List;

public class Language {
    private int id;
    private String name;
    private String iconPath;
    private List<Topic> topics;

    public Language(int id, String name, String iconPath) {
        this.id = id;
        this.name = name;
        this.iconPath = iconPath;
        this.topics = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getIconPath() { return iconPath; }
    public List<Topic> getTopics() { return topics; }
    public void addTopic(Topic t) { topics.add(t); }

    public int getProgressPercent() {
        if (topics.isEmpty()) return 0;
        long done = topics.stream().filter(Topic::isCompleted).count();
        return (int) ((done * 100) / topics.size());
    }

    @Override
    public String toString() {
        return name;
    }
}