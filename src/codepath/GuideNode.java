package codepath;

import java.util.LinkedHashMap;
import java.util.Map;

public class GuideNode {
    private String question;
    private Map<String, GuideNode> children;
    private Recommendation recommendation;

    public GuideNode(String question) {
        this.question = question;
        this.children = new LinkedHashMap<>();
        this.recommendation = null;
    }

    public GuideNode(Recommendation recommendation) {
        this.question = null;
        this.children = new LinkedHashMap<>();
        this.recommendation = recommendation;
    }

    public boolean isLeaf() { return recommendation != null; }
    public String getQuestion() { return question; }
    public Recommendation getRecommendation() { return recommendation; }
    public Map<String, GuideNode> getChildren() { return children; }

    public void addChild(String optionLabel, GuideNode node) {
        children.put(optionLabel, node);
    }
}