package net.sf.jftp.gui.base;

import net.sf.jftp.JFtp;
import net.sf.jftp.system.logging.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Base64;
import java.util.Random;

public class LoginPanel extends JFrame {

    private static final Random RANDOM = new SecureRandom();
    private static final Base64.Encoder enc = Base64.getEncoder();
    private static final Base64.Decoder dec = Base64.getDecoder();

    private JTextField usernameBox = null;
    private JTextField passwordBox = null;
    private JButton loginButton = null;
    private JButton registerButton = null;
    private JFtp jFtpInstance = null;
    private JSONArray usersArray = null;

    public LoginPanel(JFtp jFtp) {
        this.jFtpInstance = jFtp;

        this.add(this.getPanel(this));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);

        usersArray = readUsers();
    }

    public JSONArray getUsersArray() {
        return usersArray;
    }

    private JPanel getPanel(LoginPanel complete) {
        JPanel panel = new JPanel();
        this.usernameBox = new JTextField();
        this.passwordBox = new JPasswordField();
        GridLayout layout = new GridLayout(3, 2);
        layout.setHgap(25);
        layout.setVgap(25);
        panel.setLayout(layout);

        panel.add(new JLabel(JFtp.getMessage("base", "username")));
        panel.add(this.usernameBox);

        panel.add(new JLabel(JFtp.getMessage("base", "password")));
        panel.add(this.passwordBox);

        this.loginButton = new JButton(JFtp.getMessage("base", "login"));
        this.loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean loginSuccessful = complete.login(LoginPanel.this.usernameBox.getText(), LoginPanel.this.passwordBox.getText());
                if (!loginSuccessful) {
                    LoginPanel.this.usernameBox.setText("");
                    LoginPanel.this.passwordBox.setText("");
                }
            }
        });

        this.registerButton = new JButton(JFtp.getMessage("base", "register"));
        this.registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                complete.register(LoginPanel.this.usernameBox.getText(), LoginPanel.this.passwordBox.getText());

            }
        });

        panel.add(this.registerButton);
        panel.add(this.loginButton);
        return panel;
    }

    public boolean login(String username, String password) {
        for(Object o : usersArray) {
            JSONObject user = (JSONObject) o;

            String testUsername = (String) user.get("username");
            if(username.equals(testUsername)) {
                String passSaltString = (String) user.get("passwordSalt");
                String passHash = (String) user.get("passwordHash");

                String newPassHash = hashPassword(dec.decode(passSaltString), password);
                if(newPassHash.equals(passHash)) {
                    long accessLevel = (long) user.get("accessLevel");
                    jFtpInstance.loginSuccessful(accessLevel);
                    this.dispose();
                    return true;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Login Failed.");
        return false;
    }

    public void register(String username, String password) {
        byte[] newSalt = getNewSalt();
        String passHash = hashPassword(newSalt, password);
        String passSalt = getStringFromBytes(newSalt);

        JSONObject userObject = new JSONObject();

        userObject.put("username", username);
        userObject.put("passwordHash", passHash);
        userObject.put("passwordSalt", passSalt);
        userObject.put("accessLevel", 0);

        usersArray.add(userObject);

        writeUsersArray();

        jFtpInstance.loginSuccessful(0);
        this.dispose();
    }

    public byte[] getNewSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    public String getStringFromBytes(byte[] data) {
        return enc.encodeToString(data);
    }

    public String hashPassword(byte[] salt, String password) {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f;
        byte[] hash = null;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
            e.printStackTrace();
        }
        return getStringFromBytes(hash);
    }

    private JSONArray readUsers() {
        JSONParser jsonParser = new JSONParser();

        try {
            return (JSONArray) jsonParser.parse(new FileReader("./users/users.json"));
        } catch (FileNotFoundException e) {
            Log.error("Users file not found...");
            Log.error("Ensure ./users/users.json exists in the project root");
            Log.error("Exception: " + e.getMessage());
        } catch (IOException e) {
            Log.error("Error reading users.json file");
            Log.error("Ensure ./users/users.json exists in the project root");
            Log.error("Exception: " + e.getMessage());
        } catch (ParseException e) {
            Log.error("Error parsing users.json file");
            Log.error("Ensure users.json contains a JSON Array of users");
            Log.error("Exception: " + e.getMessage());
        }

        System.exit(0);
        return null;
    }

    private void writeUsersArray() {

        try {
           FileWriter fileWriter = new FileWriter("./users/users.json");
           usersArray.writeJSONString(fileWriter);
           fileWriter.close();
        } catch (FileNotFoundException e) {
            Log.error("Users file not found...");
            Log.error("Ensure ./users/users.json exists in the project root");
            Log.error("Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.error("Error writing to users.json file");
            Log.error("Ensure ./users/users.json exists in the project root");
            Log.error("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveUsers() {
        writeUsersArray();
    }
}
