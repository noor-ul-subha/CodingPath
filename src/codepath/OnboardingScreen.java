package codepath;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class OnboardingScreen extends JPanel {

    private Image backgroundImage;

    public OnboardingScreen(CodePathApp app) {
        setLayout(new GridBagLayout());

        try {
            java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(new File("assets/onboarding_bg.png"));
            if (img != null) {
                backgroundImage = img;
                System.out.println("Image loaded successfully via ImageIO!");
            } else {
                System.out.println("ImageIO could not decode this file.");
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            backgroundImage = null;
        }

        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel heading = new JLabel("Tell us a bit about yourself");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setForeground(Theme.TEXT_WHITE);
        gbc.gridy = 0;
        add(heading, gbc);

        JLabel sub = new JLabel("This helps us guide you better — takes 10 seconds.");
        sub.setFont(Theme.FONT_BODY);
        sub.setForeground(Theme.TEXT_GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 25, 10);
        add(sub, gbc);
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel degreeLabel = new JLabel("What are you studying / your field?");
        degreeLabel.setForeground(Theme.TEXT_WHITE);
        gbc.gridy = 2;
        add(degreeLabel, gbc);

        JTextField degreeField = new JTextField(25);
        degreeField.setBackground(Theme.BG_INPUT);
        degreeField.setForeground(Theme.TEXT_WHITE);
        degreeField.setCaretColor(Theme.TEXT_WHITE);
        degreeField.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        gbc.gridy = 3;
        add(degreeField, gbc);

        JLabel expLabel = new JLabel("Your coding experience level:");
        expLabel.setForeground(Theme.TEXT_WHITE);
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(expLabel, gbc);
        gbc.insets = new Insets(10, 10, 10, 10);

        String[] levels = {"New / No experience", "Beginner (some basics)", "Intermediate", "Experienced"};
        JComboBox<String> expBox = new JComboBox<>(levels);
        gbc.gridy = 5;
        add(expBox, gbc);

        JButton continueBtn = new JButton("Continue");
        continueBtn.setBackground(Theme.ACCENT);
        continueBtn.setForeground(Color.WHITE);
        continueBtn.setFont(Theme.FONT_BUTTON);
        continueBtn.setFocusPainted(false);
        continueBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        gbc.gridy = 6;
        gbc.insets = new Insets(30, 10, 10, 10);
        add(continueBtn, gbc);

        JLabel skip = new JLabel("Skip for now");
        skip.setForeground(Theme.TEXT_GRAY);
        skip.setFont(Theme.FONT_BODY);
        skip.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 10, 10, 10);
        add(skip, gbc);

        continueBtn.addActionListener(e -> {
            String degree = degreeField.getText().trim();
            String experience = (String) expBox.getSelectedItem();
            app.getCurrentUser().addInterest(degree.isEmpty() ? "Not specified" : degree);
            app.getCurrentUser().addInterest(experience);
            app.openAIGuidance();
        });

        skip.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                app.openAIGuidance();
            }
        });
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