package codepath;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AIChatService {
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private String apiKey;
    private List<JSONObject> conversation;

    public AIChatService() {
        apiKey = loadApiKey();
        conversation = new ArrayList<>();
        JSONObject systemMsg = new JSONObject();
        systemMsg.put("role", "system");
        systemMsg.put("content", "You are a friendly programming career guide inside a learning app called " +
                "CodePath. Help users figure out which programming language(s) to learn based on their " +
                "interests, suggest realistic job roles, and recommend free learning platforms (official docs, " +
                "freeCodeCamp, W3Schools, GeeksforGeeks, YouTube channels like Apna College, CodeWithHarry, " +
                "Traversy Media). Keep answers concise, friendly, and practical.");
        conversation.add(systemMsg);
    }

    private String loadApiKey() {
        try {
            return Files.readString(Paths.get("openai.key/key.txt")).trim();
        } catch (Exception e) {
            return null;
        }
    }

    public String sendMessage(String userMessage) {
        if (apiKey == null || apiKey.isBlank()) {
            return "AI service isn't set up yet — API key missing.";
        }

        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        conversation.add(userMsg);

        JSONObject body = new JSONObject();
        body.put("model", "llama-3.3-70b-versatile");
        body.put("messages", new JSONArray(conversation));
        body.put("temperature", 0.7);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());

            if (json.has("error")) {
                return "AI error: " + json.getJSONObject("error").getString("message");
            }

            String reply = json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            JSONObject assistantMsg = new JSONObject();
            assistantMsg.put("role", "assistant");
            assistantMsg.put("content", reply);
            conversation.add(assistantMsg);

            return reply;
        } catch (Exception e) {
            return "Could not reach AI service: " + e.getMessage();
        }
    }
}