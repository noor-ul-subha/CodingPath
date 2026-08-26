
package codepath;

import javax.swing.*;
        import java.awt.*;

public class SignupScreen extends JPanel {
    public SignupScreen(CodePathApp app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel title = new JLabel("Create Your CodePath Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        JTextField usernameField = new JTextField(18);
        JPasswordField passwordField = new JPasswordField(18);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        JButton signupBtn = new JButton("Sign Up");
        JButton backBtn = new JButton("Back to Login");
        JLabel status = new JLabel(" ");
        status.setForeground(Color.RED);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        add(signupBtn, gbc);
        gbc.gridy = 4;
        add(backBtn, gbc);
        gbc.gridy = 5;
        add(status, gbc);

        signupBtn.addActionListener(e -> {
            String u = usernameField.getText().trim();
            String p = new String(passwordField.getPassword());
            if (u.isEmpty() || p.isEmpty()) {
                status.setText("Please fill both fields.");
                return;
            }
            boolean success = app.getAuthManager().signup(u, p);
            if (success) {
                status.setForeground(new Color(0, 128, 0));
                status.setText("Account created! Please login.");
                app.showScreen(CodePathApp.LOGIN);
            } else {
                status.setForeground(Color.RED);
                status.setText("Signup failed. Username may already exist.");
            }
        });

        backBtn.addActionListener(e -> app.showScreen(CodePathApp.LOGIN));
    }
}
