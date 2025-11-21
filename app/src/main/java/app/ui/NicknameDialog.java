package app.ui;

import javax.swing.*;
import java.awt.*;

public class NicknameDialog extends JDialog {

    private final JTextField xField = new JTextField("Player X");
    private final JTextField oField = new JTextField("Player O");
    private boolean confirmed = false;

    public NicknameDialog(JFrame owner) {
        super(owner, "Start Game", true);

        setSize(400, 250);
        setLocationRelativeTo(owner);
        setResizable(false);

        // Dark background
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setBackground(new Color(45, 45, 60));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        JLabel title = new JLabel("Enter Nicknames", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        gbc.gridwidth = 1;

        // Player X
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel xLabel = new JLabel("Player X");
        xLabel.setForeground(Color.LIGHT_GRAY);
        card.add(xLabel, gbc);

        gbc.gridx = 1;
        styleTextField(xField);
        card.add(xField, gbc);

        // Player O
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel oLabel = new JLabel("Player O");
        oLabel.setForeground(Color.LIGHT_GRAY);
        card.add(oLabel, gbc);

        gbc.gridx = 1;
        styleTextField(oField);
        card.add(oField, gbc);

        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setOpaque(false);

        JButton cancel = new JButton("Cancel");
        styleButton(cancel, new Color(100, 100, 100));
        cancel.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        JButton start = new JButton("Start Game");
        styleButton(start, new Color(76, 175, 80));
        start.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        buttonsPanel.add(cancel);
        buttonsPanel.add(start);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        card.add(buttonsPanel, gbc);

        add(card);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
    }

    private void styleButton(JButton button, Color bg) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getXName() {
        return xField.getText();
    }

    public String getOName() {
        return oField.getText();
    }
}
