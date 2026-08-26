package codepath;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CodePathApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AuthManager authManager;
    private User currentUser;

    public static final String LOGIN = "LOGIN";
    public static final String SIGNUP = "SIGNUP";
    public static final String INTEREST = "INTEREST";
    public static final String HOME = "HOME";
    public static final String DETAIL = "DETAIL";
    public static final String PROFILE = "PROFILE";
    public static final String GUIDANCE = "GUIDANCE";
    public static final String AI_GUIDANCE = "AI_GUIDANCE";

    public CodePathApp() {
        authManager = new AuthManager();

        setTitle("CodePath - Programming Learning Hub");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginScreen(this), LOGIN);
        mainPanel.add(new SignupScreen(this), SIGNUP);

        add(mainPanel);
        showScreen(LOGIN);
    }

    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }

    public AuthManager getAuthManager() {
        return authManager;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        mainPanel.add(new OnboardingScreen(this), "ONBOARDING");
        showScreen("ONBOARDING");

    }


    public User getCurrentUser() {
        return currentUser;
    }


    public void openLanguageDetail(Language language) {
        mainPanel.add(new LanguageDetailScreen(this, language), DETAIL);
        showScreen(DETAIL);
    }

    public void openProfile() {
        mainPanel.add(new ProfileScreen(this), PROFILE);
        showScreen(PROFILE);
    }



    public void openAIGuidance() {
        mainPanel.add(new AIChatScreen(this), AI_GUIDANCE);
        showScreen(AI_GUIDANCE);

        }
    }
