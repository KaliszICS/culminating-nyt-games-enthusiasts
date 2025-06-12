package graphics.player;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import kalisz.KaliszTimes;
import logic.Player;

/**
 * GUI frame for player login and account creation.
 * <p>
 * Provides input fields for username and password,
 * and buttons for creating a new account or logging in.
 * Displays relevant user feedback dialogs.
 * </p>
 * 
 * @author @elliot-chan-ics4u1-2-2025
 */
public class PlayerLoginGUI extends JFrame {

    private static final int FIELD_COLUMNS = 15;
    private static final String USER_FILES_PATH = "src/main/java/logic/user files/";

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton createButton;

    private final Player playerManager;

    /**
     * Constructs the login GUI frame, initializes components and layout.
     */
    public PlayerLoginGUI() {
        super("Login Menu");

        playerManager = new Player();

        // Set up layout with GridBagLayout for flexibility
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Username label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Username: "), gbc);

        // Username input field
        usernameField = new JTextField(FIELD_COLUMNS);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(usernameField, gbc);

        // Password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Password: "), gbc);

        // Password input field
        passwordField = new JPasswordField(FIELD_COLUMNS);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        // Create Account button
        createButton = new JButton("Create Account");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createButton, gbc);

        // Login button
        loginButton = new JButton("Login");
        gbc.gridx = 1;
        add(loginButton, gbc);

        // Register event listeners
        createButton.addActionListener(e -> createAccount());
        loginButton.addActionListener(e -> login());

        // Final JFrame setup
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center window on screen
        setVisible(true);
    }

    /**
     * Attempts to create a new user account.
     * Validates input, checks for existing usernames,
     * saves new account info if valid.
     * Shows feedback dialogs accordingly.
     */
    private void createAccount() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter username and password.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        File userFile = new File(USER_FILES_PATH + username + ".txt");

        if (userFile.exists()) {
            JOptionPane.showMessageDialog(
                this,
                "Username already exists. Please choose another.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        playerManager.saveLoginInfo(username, password);

        JOptionPane.showMessageDialog(
            this,
            "Account created successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Attempts to log in with provided username and password.
     * Validates input and displays feedback based on login success or failure.
     * If login succeeds, proceeds to initiate the main game.
     */
    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter username and password.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int loginResult = playerManager.login(username, password); // loads all values on success

        if (loginResult == 1) {
            JOptionPane.showMessageDialog(
                this,
                "Login successful! Welcome, " + playerManager.getUsername(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );

            KaliszTimes.initiateGame(playerManager);
            this.dispose();
        
        } else if (loginResult == 0) {
            JOptionPane.showMessageDialog(
                this,
                "Incorrect password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Username does not exist.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
