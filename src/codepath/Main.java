package codepath;

import javax.swing.*;

public class

Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CodePathApp app = new CodePathApp();
            app.setVisible(true);
        });
    }
}