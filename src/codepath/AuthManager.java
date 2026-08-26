
package codepath;

import java.security.MessageDigest;
import java.sql.*;
        import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private Map<String, User> userCache;

    public AuthManager() {
        userCache = new HashMap<>();
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    public boolean signup(String username, String password) {
        String hashed = hashPassword(password);
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, hashed);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Signup failed: " + e.getMessage());
            return false;
        }
    }

    public User login(String username, String password) {
        String hashed = hashPassword(password);

        if (userCache.containsKey(username)) {
            User cached = userCache.get(username);
            if (cached.getHashedPassword().equals(hashed)) {
                return cached;
            }
        }

        String sql = "SELECT id, username, password_hash FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String dbHash = rs.getString("password_hash");
                if (dbHash.equals(hashed)) {
                    User user = new User(rs.getInt("id"), username, dbHash);
                    userCache.put(username, user);
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
        }
        return null;
    }
}