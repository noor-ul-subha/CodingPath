package codepath;

import java.sql.*;

public class LanguageRepository {

    public static Language getLanguage(String name) {
        Language language = null;
        String langSql = "SELECT id, name, icon_path FROM languages WHERE name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(langSql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                language = new Language(rs.getInt("id"), rs.getString("name"), rs.getString("icon_path"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching language: " + e.getMessage());
        }

        if (language == null) {
            return new Language(0, name, "/icons/default.png");
        }

        String topicSql = "SELECT id, title, description FROM topics WHERE language_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(topicSql)) {
            ps.setInt(1, language.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Topic topic = new Topic(rs.getInt("id"), rs.getString("title"), rs.getString("description"));
                loadResourcesForTopic(topic);
                language.addTopic(topic);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching topics: " + e.getMessage());
        }

        return language;
    }

    private static void loadResourcesForTopic(Topic topic) {
        String sql = "SELECT type, title, url FROM resources WHERE topic_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, topic.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                String title = rs.getString("title");
                String url = rs.getString("url");
                if (type.equalsIgnoreCase("Video")) {
                    topic.addResource(new VideoResource(title, url));
                } else {
                    topic.addResource(new ArticleResource(title, url));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching resources: " + e.getMessage());
        }
    }
}