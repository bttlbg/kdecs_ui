package org.ichilabs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    String engine, accent, darkMode;

    ButtonGroup engineGroup = new ButtonGroup();
    ButtonGroup accentGroup = new ButtonGroup();
    ButtonGroup darkModeGroup = new ButtonGroup();
    JButton applyButton = new JButton("Apply");

    public Window() {
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(238, 238, 238));

        JLabel engineLabel = new JLabel("Engine");
        engineLabel.setBounds(10, 10, 50, 30);
        add(engineLabel);
        JRadioButton engineWalRadioButton = new JRadioButton("Wal", true);
        engineWalRadioButton.setBounds(20, 50, 100, 30);
        engineWalRadioButton.setActionCommand("wal");
        add(engineWalRadioButton);
        JRadioButton engineColorzRadioButton = new JRadioButton("Colorz", false);
        engineColorzRadioButton.setBounds(120, 50, 100, 30);
        engineColorzRadioButton.setActionCommand("colorz");
        add(engineColorzRadioButton);
        engineGroup.add(engineWalRadioButton);
        engineGroup.add(engineColorzRadioButton);

        JLabel accentLabel = new JLabel("Accent color");
        accentLabel.setBounds(10, 90, 100, 30);
        add(accentLabel);

        JRadioButton[] accentsRadioButtons = new JRadioButton[6];
        for (int i = 0; i < 6; i++) {
            accentsRadioButtons[i] = new JRadioButton(String.valueOf(i + 1), false);
            accentsRadioButtons[i].setBounds(20 + (50 * (i)), 120, 50, 30);
            accentsRadioButtons[i].setActionCommand(String.valueOf(i + 1));
            add(accentsRadioButtons[i]);
            accentGroup.add(accentsRadioButtons[i]);
        }

        JLabel darkModeLabel = new JLabel("Dark mode");
        darkModeLabel.setBounds(10, 160, 100, 30);
        add(darkModeLabel);
        JRadioButton darkModeEnabledRadioButton = new JRadioButton("Enabled", false);
        darkModeEnabledRadioButton.setBounds(20, 190, 100, 30);
        darkModeEnabledRadioButton.setActionCommand("false");
        add(darkModeEnabledRadioButton);
        JRadioButton darkModeDisabledRadioButton = new JRadioButton("Disabled", true);
        darkModeDisabledRadioButton.setBounds(120, 190, 100, 30);
        darkModeDisabledRadioButton.setActionCommand("true");
        add(darkModeDisabledRadioButton);
        darkModeGroup.add(darkModeEnabledRadioButton);
        darkModeGroup.add(darkModeDisabledRadioButton);

        applyButton.setBounds(10, 240, 300, 35);
        applyButton.addActionListener(this);
        add(applyButton);

        /* Set current settings to the window */
        String[] currentConfig = ReadConfig.GetConfig();
        switch (currentConfig[0]) {
            case "wal" -> {
                engineGroup.setSelected(engineWalRadioButton.getModel(), true);
                engineGroup.setSelected(engineColorzRadioButton.getModel(), false);
            }
            case "colorz" -> {
                engineGroup.setSelected(engineWalRadioButton.getModel(), false);
                engineGroup.setSelected(engineColorzRadioButton.getModel(), true);
            }
            default -> throw new IllegalStateException("Unexpected value: " + currentConfig[0]);
        }

        int x = Integer.parseInt(currentConfig[1]);
        for (int i = 0; i < 6; i++) {
            accentGroup.setSelected(accentsRadioButtons[i].getModel(), (i + 1) == x);
        }

        switch (currentConfig[2]) {
            case "false" -> {
                darkModeGroup.setSelected(darkModeEnabledRadioButton.getModel(), true);
                darkModeGroup.setSelected(darkModeDisabledRadioButton.getModel(), false);
            }
            case "true" -> {
                darkModeGroup.setSelected(darkModeEnabledRadioButton.getModel(), false);
                darkModeGroup.setSelected(darkModeDisabledRadioButton.getModel(), true);
            }
            default -> throw new IllegalStateException("Unexpected value: " + currentConfig[2]);
        }

        class VoteActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine = engineGroup.getSelection().getActionCommand();
                accent = accentGroup.getSelection().getActionCommand();
                darkMode = darkModeGroup.getSelection().getActionCommand();
            }
        }

        ActionListener al = new VoteActionListener();
        engineWalRadioButton.addActionListener(al);
        engineColorzRadioButton.addActionListener(al);
        for (int i = 0; i < 6; i++) {
            accentsRadioButtons[i].addActionListener(al);
        }
        darkModeEnabledRadioButton.addActionListener(al);
        darkModeDisabledRadioButton.addActionListener(al);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == applyButton) {
            new WriteConfig(engine, accent, darkMode);
            JOptionPane.showMessageDialog(this, "Settings saved!");
        }
    }
}
