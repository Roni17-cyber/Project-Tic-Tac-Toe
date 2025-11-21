package app.ui;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {

    private final JLabel label = new JLabel("Welcome to Tic Tac Toe");

    public StatusBar() {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45));
        setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));

        add(label, BorderLayout.CENTER);
    }

    public void setMessage(String message) {
        label.setText(message);
    }
}
