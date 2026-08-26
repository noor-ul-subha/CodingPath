package codepath;

import javax.swing.*;
import java.awt.*;
import java.awt.Desktop;
import java.net.URI;
import java.sql.*;

public class LanguageDetailScreen extends JPanel {
    public LanguageDetailScreen(CodePathApp app, Language language) {
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BG_DARK);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel(language.getName() + " - Learning Path");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_WHITE);
        add(title, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Topic t : language.getTopics()) {
            listModel.addElement((t.isCompleted() ? "[Done] " : "[ ] ") + t.getTitle());
        }
        JList<String> topicList = new JList<>(listModel);
        topicList.setBackground(Theme.BG_CARD);
        topicList.setForeground(Theme.TEXT_WHITE);
        topicList.setSelectionBackground(Theme.ACCENT);
        topicList.setFont(Theme.FONT_BODY);
        JScrollPane topicScroll = new JScrollPane(topicList);
        topicScroll.setPreferredSize(new Dimension(180, 0));
        add(topicScroll, BorderLayout.WEST);

        JPanel resourcePanel = new JPanel();
        resourcePanel.setLayout(new BoxLayout(resourcePanel, BoxLayout.Y_AXIS));
        resourcePanel.setBackground(Theme.BG_CARD);
        resourcePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane resourceScroll = new JScrollPane(resourcePanel);
        resourceScroll.getViewport().setBackground(Theme.BG_CARD);
        add(resourceScroll, BorderLayout.CENTER);

        JButton completeBtn = new JButton("Mark as Completed");
        completeBtn.setEnabled(false);
        styleButton(completeBtn, Theme.SUCCESS);

        topicList.addListSelectionListener(e -> {
            int idx = topicList.getSelectedIndex();
            if (idx >= 0 && idx < language.getTopics().size()) {
                Topic t = language.getTopics().get(idx);
                resourcePanel.removeAll();

                JLabel overviewHeading = new JLabel("Overview");
                overviewHeading.setFont(Theme.FONT_HEADING);
                overviewHeading.setForeground(Theme.ACCENT);
                overviewHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
                resourcePanel.add(overviewHeading);
                resourcePanel.add(Box.createVerticalStrut(6));

                JTextArea desc = new JTextArea(t.getDescription());
                desc.setEditable(false);
                desc.setLineWrap(true);
                desc.setWrapStyleWord(true);
                desc.setFont(Theme.FONT_BODY);
                desc.setForeground(Theme.TEXT_WHITE);
                desc.setBackground(Theme.BG_CARD);
                desc.setAlignmentX(Component.LEFT_ALIGNMENT);
                resourcePanel.add(desc);
                resourcePanel.add(Box.createVerticalStrut(18));

                if (t.getContent() != null && !t.getContent().isBlank()) {
                    JLabel detailHeading = new JLabel("What you'll learn");
                    detailHeading.setFont(Theme.FONT_HEADING);
                    detailHeading.setForeground(Theme.ACCENT);
                    detailHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
                    resourcePanel.add(detailHeading);
                    resourcePanel.add(Box.createVerticalStrut(6));

                    JTextArea content = new JTextArea(t.getContent());
                    content.setEditable(false);
                    content.setLineWrap(true);
                    content.setWrapStyleWord(true);
                    content.setFont(Theme.FONT_BODY);
                    content.setForeground(Theme.TEXT_GRAY);
                    content.setBackground(Theme.BG_CARD);
                    content.setAlignmentX(Component.LEFT_ALIGNMENT);
                    resourcePanel.add(content);
                    resourcePanel.add(Box.createVerticalStrut(18));
                }

                JLabel resHeading = new JLabel("Resources");
                resHeading.setFont(Theme.FONT_HEADING);
                resHeading.setForeground(Theme.ACCENT);
                resHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
                resourcePanel.add(resHeading);
                resourcePanel.add(Box.createVerticalStrut(8));

                for (Resource r : t.getResources()) {
                    JButton linkBtn = new JButton("[" + r.getType() + "] " + r.getTitle());
                    linkBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
                    linkBtn.setHorizontalAlignment(SwingConstants.LEFT);
                    linkBtn.setForeground(Theme.ACCENT);
                    linkBtn.setBackground(Theme.BG_CARD);
                    linkBtn.setBorderPainted(false);
                    linkBtn.setContentAreaFilled(false);
                    linkBtn.setFont(Theme.FONT_BODY);
                    linkBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    linkBtn.addActionListener(ev -> {
                        try {
                            Desktop.getDesktop().browse(new URI(r.getUrl()));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Could not open link: " + ex.getMessage());
                        }
                    });

                    resourcePanel.add(linkBtn);
                    resourcePanel.add(Box.createVerticalStrut(5));
                }

                resourcePanel.revalidate();
                resourcePanel.repaint();
                completeBtn.setEnabled(true);
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Theme.BG_DARK);
        JButton backBtn = new JButton("Back to Home");
        styleButton(backBtn, Theme.BG_INPUT);
        backBtn.addActionListener(e -> app.showScreen(CodePathApp.HOME));
        bottomPanel.add(backBtn, BorderLayout.WEST);

        completeBtn.addActionListener(e -> {
            int idx = topicList.getSelectedIndex();
            if (idx >= 0) {
                Topic t = language.getTopics().get(idx);
                t.setCompleted(true);
                saveProgress(app.getCurrentUser().getId(), t.getId());
                listModel.set(idx, "[Done] " + t.getTitle());
            }
        });
        bottomPanel.add(completeBtn, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Theme.TEXT_WHITE);
        btn.setFont(Theme.FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void saveProgress(int userId, int topicId) {
        String checkSql = "SELECT id FROM progress WHERE user_id = ? AND topic_id = ?";
        String insertSql = "INSERT INTO progress (user_id, topic_id, completed) VALUES (?, ?, 1)";
        String updateSql = "UPDATE progress SET completed = 1 WHERE user_id = ? AND topic_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement check = conn.prepareStatement(checkSql)) {
                check.setInt(1, userId);
                check.setInt(2, topicId);
                ResultSet rs = check.executeQuery();
                if (rs.next()) {
                    try (PreparedStatement update = conn.prepareStatement(updateSql)) {
                        update.setInt(1, userId);
                        update.setInt(2, topicId);
                        update.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insert = conn.prepareStatement(insertSql)) {
                        insert.setInt(1, userId);
                        insert.setInt(2, topicId);
                        insert.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving progress: " + e.getMessage());
        }
    }
}