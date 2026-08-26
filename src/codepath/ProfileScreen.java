package codepath;

import javax.swing.*;
        import java.awt.*;
        import java.sql.*;

public class ProfileScreen extends JPanel {
    public ProfileScreen(CodePathApp app) {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Your Progress Summary");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JTextArea summary = new JTextArea();
        summary.setEditable(false);
        summary.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        summary.setText(buildSummary(app.getCurrentUser().getId()));
        add(new JScrollPane(summary), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("Back to Home");
        backBtn.addActionListener(e -> app.showScreen(CodePathApp.HOME));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?", "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                app.showScreen(CodePathApp.LOGIN);
            }
        });

        bottomPanel.add(backBtn, BorderLayout.WEST);
        bottomPanel.add(logoutBtn, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private String buildSummary(int userId) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT l.name AS lang_name, COUNT(t.id) AS total_topics, " +
                "SUM(CASE WHEN p.completed = 1 THEN 1 ELSE 0 END) AS done_topics " +
                "FROM languages l " +
                "JOIN topics t ON t.language_id = l.id " +
                "LEFT JOIN progress p ON p.topic_id = t.id AND p.user_id = ? " +
                "GROUP BY l.name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            boolean any = false;
            while (rs.next()) {
                any = true;
                String lang = rs.getString("lang_name");
                int total = rs.getInt("total_topics");
                int done = rs.getInt("done_topics");
                int percent = total == 0 ? 0 : (done * 100) / total;
                sb.append(lang).append(": ").append(done).append("/").append(total)
                        .append(" topics completed (").append(percent).append("%)\n");
            }
            if (!any) {
                sb.append("No progress yet — start a language from Home and mark topics as completed!");
            }
        } catch (SQLException e) {
            sb.append("Could not load progress: ").append(e.getMessage());
        }
        return sb.toString();
    }
}