package codepath;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JPanel {

    private Image backgroundImage;

    public LoginScreen(CodePathApp app) {
        setLayout(new GridBagLayout());

        try {
            backgroundImage = new ImageIcon("assets/login_bg.jpg").getImage();
        } catch (Exception e) {
            backgroundImage = null;
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);

        // Welcome heading - biggest, top most
        JLabel welcome = new JLabel("Welcome to CodingPath");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcome.setForeground(Color.WHITE);
        gbc.gridy = 0; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 8, 8, 8);
        add(welcome, gbc);

        // Tagline - below welcome, smaller
        JLabel tagline = new JLabel("Make it work, make it right, make it fast.");
        tagline.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
        tagline.setForeground(new Color(220, 220, 220));
        gbc.gridy = 1; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 8, 25, 8);
        add(tagline, gbc);

        // Title - right above the form
        JLabel title = new JLabel("Login to CodingPath");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(4, 8, 15, 8);
        add(title, gbc);

        gbc.insets = new Insets(6, 8, 6, 8); // reset spacing for rest of form

        JTextField usernameField = new JTextField(18);
        JPasswordField passwordField = new JPasswordField(18);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);

        gbc.gridwidth = 1;

        gbc.gridy = 3; gbc.gridx = 0;
        gbc.insets = new Insets(6, 8, 6, 2);
        add(userLabel, gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(6, 2, 6, 8);
        add(usernameField, gbc);

        gbc.gridy = 4; gbc.gridx = 0;
        gbc.insets = new Insets(6, 8, 6, 2);
        add(passLabel, gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(6, 2, 6, 8);
        add(passwordField, gbc);

        gbc.insets = new Insets(6, 8, 6, 8); // reset for buttons below

        JButton loginBtn = new JButton("Login");
        JButton toSignup = new JButton("Create Account");
        JLabel status = new JLabel(" ");
        status.setForeground(Color.YELLOW);

        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2;
        add(loginBtn, gbc);
        gbc.gridy = 6;
        add(toSignup, gbc);
        gbc.gridy = 7;
        add(status, gbc);

        loginBtn.addActionListener(e -> {
            String u = usernameField.getText().trim();
            String p = new String(passwordField.getPassword());
            if (u.isEmpty() || p.isEmpty()) {
                status.setText("Please fill both fields.");
                return;
            }
            User user = app.getAuthManager().login(u, p);
            if (user != null) {
                app.setCurrentUser(user);
            } else {
                status.setText("Invalid username or password.");
            }
        });

        toSignup.addActionListener(e -> app.showScreen(CodePathApp.SIGNUP));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(30, 30, 40));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}