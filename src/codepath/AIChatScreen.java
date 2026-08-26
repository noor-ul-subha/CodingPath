package codepath;

import javax.swing.*;
import java.awt.*;

public class AIChatScreen extends JPanel {
    private JPanel chatArea;
    private JScrollPane chatScroll;
    private AIChatService aiService;
    private JTextField inputField;
    private JButton sendBtn;

    public AIChatScreen(CodePathApp app) {
        aiService = new AIChatService();

        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BG_DARK);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Theme.BG_DARK);

        JLabel title = new JLabel("Coding Guide");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_WHITE);
        topBar.add(title, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(Theme.BG_INPUT);
        logoutBtn.setForeground(Theme.TEXT_WHITE);
        logoutBtn.setFont(Theme.FONT_BUTTON);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?", "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                app.showScreen(CodePathApp.LOGIN);
            }
        });
        topBar.add(logoutBtn, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));
        chatArea.setBackground(Theme.BG_DARK);
        chatScroll = new JScrollPane(chatArea);
        chatScroll.getViewport().setBackground(Theme.BG_DARK);
        chatScroll.setBorder(null);
        add(chatScroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        bottomPanel.setBackground(Theme.BG_DARK);

        inputField = new JTextField();
        inputField.setBackground(Theme.BG_INPUT);
        inputField.setForeground(Theme.TEXT_WHITE);
        inputField.setCaretColor(Theme.TEXT_WHITE);
        inputField.setFont(Theme.FONT_BODY);
        inputField.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        sendBtn = new JButton("Send");
        sendBtn.setBackground(Theme.ACCENT);
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFont(Theme.FONT_BUTTON);
        sendBtn.setFocusPainted(false);

        JPanel inputRow = new JPanel(new BorderLayout(8, 0));
        inputRow.setBackground(Theme.BG_DARK);
        inputRow.add(inputField, BorderLayout.CENTER);
        inputRow.add(sendBtn, BorderLayout.EAST);

        bottomPanel.add(inputRow, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        sendBtn.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        addAssistantBubbleReturn("Hi! Tell me what you're interested in — web development, mobile apps, " +
                "games, AI, or anything else — and I'll suggest languages, job roles, and where to learn them.");
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;

        addUserBubble(text);
        inputField.setText("");
        inputField.setEnabled(false);
        sendBtn.setEnabled(false);

        JLabel typing = addAssistantBubbleReturn("Thinking...");

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                return aiService.sendMessage(text);
            }

            @Override
            protected void done() {
                try {
                    String reply = get();
                    typing.setText("<html><div style='width:400px'>" + reply.replace("\n", "<br>") + "</div></html>");
                } catch (Exception ex) {
                    typing.setText("Something went wrong: " + ex.getMessage());
                }
                inputField.setEnabled(true);
                sendBtn.setEnabled(true);
                inputField.requestFocus();
                scrollToBottom();
            }
        };
        worker.execute();
    }

    private void addUserBubble(String text) {
        JLabel bubble = new JLabel("<html><div style='width:300px'>" + text + "</div></html>");
        bubble.setOpaque(true);
        bubble.setBackground(Theme.BG_INPUT);
        bubble.setForeground(Theme.TEXT_WHITE);
        bubble.setFont(Theme.FONT_BODY);
        bubble.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapper.setBackground(Theme.BG_DARK);
        wrapper.add(bubble);
        chatArea.add(wrapper);
        chatArea.add(Box.createVerticalStrut(8));
        chatArea.revalidate();
        chatArea.repaint();
        scrollToBottom();
    }

    private JLabel addAssistantBubbleReturn(String text) {
        JLabel bubble = new JLabel("<html><div style='width:400px'>" + text + "</div></html>");
        bubble.setOpaque(true);
        bubble.setBackground(Theme.BG_CARD);
        bubble.setForeground(Theme.TEXT_WHITE);
        bubble.setFont(Theme.FONT_BODY);
        bubble.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setBackground(Theme.BG_DARK);
        wrapper.add(bubble);
        chatArea.add(wrapper);
        chatArea.add(Box.createVerticalStrut(8));
        chatArea.revalidate();
        chatArea.repaint();
        scrollToBottom();
        return bubble;
    }

    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = chatScroll.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }
}