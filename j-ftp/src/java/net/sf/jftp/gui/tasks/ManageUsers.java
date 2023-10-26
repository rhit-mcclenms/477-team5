package net.sf.jftp.gui.tasks;

import net.sf.jftp.JFtp;
import net.sf.jftp.config.Settings;
import net.sf.jftp.gui.framework.HFrame;
import net.sf.jftp.gui.framework.HPanel;
import net.sf.jftp.system.logging.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ManageUsers extends HFrame implements ActionListener, WindowListener
{
    private JButton save = new JButton(JFtp.getMessage("tasks", "save"));
    private JButton close = new JButton(JFtp.getMessage("tasks", "close"));
    private Map<JSONObject, JComboBox<String>> accessLevels = new HashMap<>();
    private JFtp jftp;

    public ManageUsers(JFtp jftp)
    {
        this.jftp = jftp;
        JSONArray users = jftp.getUsers();

        setSize(650, 400);
        setLocation(50, 150);
        setTitle(JFtp.getMessage("tasks", "manageUsers"));

        getContentPane().setLayout(new GridLayout(users.size() + 1, 2));

        JLabel accessLabel = new JLabel("Access Level");
        JLabel usernameLabel = new JLabel("User");
        add(usernameLabel);
        add(accessLabel);
        for(Object o : users) {
            JSONObject user = (JSONObject) o;
            String username = (String) user.get("username");
            long accessLevel = (long) user.get("accessLevel");

            if(!username.equals("sys-admin")) {
                JLabel userLabel = new JLabel(username);
                Font f = userLabel.getFont();
                userLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

                String[] accessChoices = new String[] {
                        "Regular User",
                        "Network Admin",
                        "System Admin"
                };

                JComboBox<String> accessDropdown = new JComboBox<>(accessChoices);
                accessDropdown.setSelectedItem(accessChoices[(int) accessLevel]);
                add(userLabel);
                add(accessDropdown);

                accessLevels.put(user, accessDropdown);
            }
        }


        HPanel closeP = new HPanel();
        closeP.setLayout(new FlowLayout(FlowLayout.CENTER));

        //closeP.add(close);
        closeP.add(save);

        close.addActionListener(this);
        save.addActionListener(this);
        add(closeP);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == close)
        {
            this.dispose();
        }
        else
        {
            save();
            this.dispose();
        }
    }

    private void save()
    {
        for(Map.Entry<JSONObject, JComboBox<String>> accessLevel : accessLevels.entrySet()) {
            JComboBox<String> access = accessLevel.getValue();
            String selectedLevel = (String) access.getSelectedItem();

            long accessLong = 0;
            switch (selectedLevel) {
                case "Regular User": accessLong = 0; break;
                case "Network Admin": accessLong = 1; break;
                case "System Admin": accessLong = 2; break;
            }

            JSONObject user = accessLevel.getKey();
            user.put("accessLevel", accessLong);
        }

        jftp.saveUsers();
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}