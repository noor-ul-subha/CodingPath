
package codepath;

import javax.swing.*;
        import java.awt.*;
        import java.awt.Desktop;
import java.net.URI;

public class ChatScreen extends JPanel {
    private GuidanceEngine engine;
    private GuideNode currentNode;
    private JPanel chatArea;
    private JPanel optionsPanel;
    private JScrollPane chatScroll;

    public ChatScreen(CodePathApp app) {
        engine = new GuidanceEngine();
        currentNode = engine.getRoot();

        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BG_DARK);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Career & Language Guide");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_WHITE);
        add(title, BorderLayout.NORTH);

        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));
        chatArea.setBackground(Theme.BG_DARK);
        chatScroll = new JScrollPane(chatArea);
        chatScroll.getViewport().setBackground(Theme.BG_DARK);
        chatScroll.setBorder(null);
        add(chatScroll, BorderLayout.CENTER);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(Theme.BG_DARK);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Theme.BG_DARK);
        bottomPanel.add(optionsPanel, BorderLayout.CENTER);

        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navButtons.setBackground(Theme.BG_DARK);
        JButton backBtn = new JButton("Back to Home");
        styleFlatButton(backBtn);
        backBtn.addActionListener(e -> app.showScreen(CodePathApp.HOME));
        JButton restartBtn = new JButton("Start Over");
        styleFlatButton(restartBtn);
        restartBtn.addActionListener(e -> restart());
        navButtons.add(backBtn);
        navButtons.add(restartBtn);
        bottomPanel.add(navButtons, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        addAssistantBubble(currentNode.getQuestion());
        showOptions();
    }

    private void restart() {
        currentNode = engine.getRoot();
        chatArea.removeAll();
        addAssistantBubble(currentNode.getQuestion());
        showOptions();
        chatArea.revalidate();
        chatArea.repaint();
    }

    private void showOptions() {
        optionsPanel.removeAll();
        if (currentNode.isLeaf()) {
            showRecommendation(currentNode.getRecommendation());
        } else {
            for (String option : currentNode.getChildren().keySet()) {
                JButton optBtn = new JButton(option);
                styleOptionButton(optBtn);
                optBtn.addActionListener(e -> selectOption(option));
                optionsPanel.add(optBtn);
                optionsPanel.add(Box.createVerticalStrut(6));
            }
        }
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private void selectOption(String option) {
        addUserBubble(option);
        currentNode = currentNode.getChildren().get(option);
        if (!currentNode.isLeaf()) {
            addAssistantBubble(currentNode.getQuestion());
        }
        showOptions();
        scrollToBottom();
    }

    private void showRecommendation(Recommendation rec) {
        StringBuilder sb = new StringBuilder();
        sb.append(rec.getTitle()).append("\n\n");
        sb.append("Recommended languages:\n");
        for (String lang : rec.getLanguages()) sb.append("- ").append(lang).append("\n");
        sb.append("\nJob roles you could aim for:\n");
        for (String job : rec.getJobRoles()) sb.append("- ").append(job).append("\n");

        addAssistantBubble(sb.toString());

        JPanel linksPanel = new JPanel();
        linksPanel.setLayout(new BoxLayout(linksPanel, BoxLayout.Y_AXIS));
        linksPanel.setBackground(Theme.BG_CARD);
        linksPanel.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        JLabel platformHeading = new JLabel("Where to learn:");
        platformHeading.setForeground(Theme.ACCENT);
        platformHeading.setFont(Theme.FONT_HEADING);
        platformHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        linksPanel.add(platformHeading);
        linksPanel.add(Box.createVerticalStrut(6));

        for (PlatformLink link : rec.getPlatforms()) {
            JButton linkBtn = new JButton(link.getName());
            linkBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            linkBtn.setHorizontalAlignment(SwingConstants.LEFT);
            linkBtn.setForeground(Theme.ACCENT);
            linkBtn.setBackground(Theme.BG_CARD);
            linkBtn.setBorderPainted(false);
            linkBtn.setContentAreaFilled(false);
            linkBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            linkBtn.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(link.getUrl()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Could not open link: " + ex.getMessage());
                }
            });
            linksPanel.add(linkBtn);
        }

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setBackground(Theme.BG_DARK);
        wrapper.add(linksPanel);
        chatArea.add(wrapper);
        chatArea.revalidate();
        chatArea.repaint();
        scrollToBottom();
    }

    private void addAssistantBubble(String text) {
        JTextArea bubble = new JTextArea(text);
        bubble.setEditable(false);
        bubble.setLineWrap(true);
        bubble.setWrapStyleWord(true);
        bubble.setFont(Theme.FONT_BODY);
        bubble.setForeground(Theme.TEXT_WHITE);
        bubble.setBackground(Theme.BG_CARD);
        bubble.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        bubble.setMaximumSize(new Dimension(500, Short.MAX_VALUE));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setBackground(Theme.BG_DARK);
        wrapper.add(bubble);
        chatArea.add(wrapper);
        chatArea.add(Box.createVerticalStrut(8));
        chatArea.revalidate();
        chatArea.repaint();
    }

    private void addUserBubble(String text) {
        JLabel bubble = new JLabel(text);
        bubble.setOpaque(true);
        bubble.setBackground(Theme.ACCENT);
        bubble.setForeground(Color.WHITE);
        bubble.setFont(Theme.FONT_BODY);
        bubble.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapper.setBackground(Theme.BG_DARK);
        wrapper.add(bubble);
        chatArea.add(wrapper);
        chatArea.add(Box.createVerticalStrut(8));
        chatArea.revalidate();
        chatArea.repaint();
    }

    private void styleOptionButton(JButton btn) {
        btn.setBackground(Theme.BG_INPUT);
        btn.setForeground(Theme.TEXT_WHITE);
        btn.setFont(Theme.FONT_BODY);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleFlatButton(JButton btn) {
        btn.setBackground(Theme.BG_DARK);
        btn.setForeground(Theme.TEXT_GRAY);
        btn.setFont(Theme.FONT_BODY);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = chatScroll.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }
}
