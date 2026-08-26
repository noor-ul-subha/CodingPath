
package codepath;

import java.util.*;

public class InterestMatcher {
    private Map<String, List<String>> keywordToLanguages;

    public InterestMatcher() {
        keywordToLanguages = new HashMap<>();
        keywordToLanguages.put("web", Arrays.asList("HTML", "CSS", "JavaScript"));
        keywordToLanguages.put("website", Arrays.asList("HTML", "CSS", "JavaScript"));
        keywordToLanguages.put("frontend", Arrays.asList("HTML", "CSS", "JavaScript"));
        keywordToLanguages.put("design", Arrays.asList("HTML", "CSS"));
        keywordToLanguages.put("game", Arrays.asList("C++", "Java", "Python"));
        keywordToLanguages.put("games", Arrays.asList("C++", "Java", "Python"));
        keywordToLanguages.put("ai", Arrays.asList("Python", "Java"));
        keywordToLanguages.put("data", Arrays.asList("Python", "Java", "SQL"));
        keywordToLanguages.put("mobile", Arrays.asList("Java", "Kotlin", "Swift"));
        keywordToLanguages.put("app", Arrays.asList("Java", "Kotlin", "Swift"));
        keywordToLanguages.put("apps", Arrays.asList("Java", "Kotlin", "Swift"));
        keywordToLanguages.put("backend", Arrays.asList("Java", "Python", "C++"));
        keywordToLanguages.put("server", Arrays.asList("Java", "Python"));
        keywordToLanguages.put("database", Arrays.asList("SQL", "Java"));
    }

    public List<String> matchLanguages(String userInput) {
        Map<String, Integer> score = new HashMap<>();
        String[] words = userInput.toLowerCase().split("[^a-zA-Z]+");

        for (String word : words) {
            if (keywordToLanguages.containsKey(word)) {
                for (String lang : keywordToLanguages.get(word)) {
                    score.put(lang, score.getOrDefault(lang, 0) + 1);
                }
            }
        }

        List<String> result = new ArrayList<>(score.keySet());
        result.sort((a, b) -> score.get(b) - score.get(a));
        return result;
    }
}
