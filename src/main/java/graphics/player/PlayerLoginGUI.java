package graphics.player;

import javax.swing.*;

import kalisz.KaliszTimes;

import java.awt.*;
import java.io.File;

import logic.Player;

public class PlayerLoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, createButton;
    private Player playerManager;
    private String filePath = "src/main/java/logic/user files/";

    public PlayerLoginGUI() {
        super("Login Menu");

        playerManager = new Player();

        // Set layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Username label & field
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Username: "), gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(usernameField, gbc);

        // Password label & field
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Password: "), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        // Create Account button
        createButton = new JButton("Create Account");
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createButton, gbc);

        // Login button
        loginButton = new JButton("Login");
        gbc.gridx = 1; gbc.gridy = 2;
        add(loginButton, gbc);

        // Button listeners
        createButton.addActionListener(e -> createAccount());
        loginButton.addActionListener(e -> login());

        // JFrame basics
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // center on screen
        setVisible(true);
    }

    private void createAccount() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

                if (new File(filePath + username + ".txt").exists()) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        playerManager.saveLoginInfo(username, password);
        JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int loginResult = playerManager.login(username, password); //loads all values

        if (loginResult == 1) {
            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + playerManager.getUsername(), "Success", JOptionPane.INFORMATION_MESSAGE);
            // Here you can proceed to your main game menu or dashboard
            
            KaliszTimes.initiateGame(playerManager);


        } else if (loginResult == 0) {
            JOptionPane.showMessageDialog(this, "Incorrect password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Username does not exist.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

}
