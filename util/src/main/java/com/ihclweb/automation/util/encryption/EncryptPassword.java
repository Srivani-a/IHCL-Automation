package com.ihclweb.automation.util.encryption;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

public class EncryptPassword {
	
private static String encryptKey;
private static final Logger LOG = Logger.getLogger(EncryptPassword.class.getName());
    
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        final JFrame frame = new JFrame("String Encryption Tool");
        frame.setDefaultCloseOperation(3);
        final Dimension minSize = new Dimension();
        minSize.height = 150;
        minSize.width = 400;
        frame.setMinimumSize(minSize);
        final Container pane = frame.getContentPane();
        pane.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        final JLabel pwdLabel = new JLabel("     Enter String:    ", 11);
        c.weightx = 0.5;
        c.weighty = 0.33;
        c.fill = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 0, 0);
        pane.add(pwdLabel, c);
        final JTextField pwdField = new JTextField(16);
        pwdField.setHorizontalAlignment(4);
        c.fill = 2;
        c.weightx = 0.5;
        c.weighty = 0.33;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 0, 10);
        pane.add(pwdField, c);
        final JLabel keyLabel = new JLabel("     Enter Key:    ", 11);
        c.weightx = 0.5;
        c.weighty = 0.33;
        c.fill = 2;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 10, 0, 0);
        pane.add(keyLabel, c);
        final JTextField keyField = new JTextField(16);
        if (EncryptPassword.encryptKey != null) {
            keyField.setText(EncryptPassword.encryptKey);
        }
        keyField.setHorizontalAlignment(4);
        c.fill = 2;
        c.weightx = 0.5;
        c.weighty = 0.33;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 0, 10);
        pane.add(keyField, c);
        final JButton btnEnter = new JButton("Encrypt String");
        c.fill = 2;
        c.weightx = 1.0;
        c.weighty = 0.33;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = 20;
        c.insets = new Insets(10, 10, 10, 10);
        pane.add(btnEnter, c);
        btnEnter.addActionListener(new ActionListener() {
            
            public void actionPerformed(final ActionEvent e) {
                final String pwd = pwdField.getText();
                EncryptPassword.access1(keyField.getText());
                frame.dispose();
                displayresult(EncryptDecryptPassword.encrypt(pwd, EncryptPassword.encryptKey));
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    private static void displayresult(final String encryptedPwd) {
        final JFrame frame2 = new JFrame("String Encryption Tool");
        frame2.setDefaultCloseOperation(3);
        final Dimension minSize2 = new Dimension();
        minSize2.height = 150;
        minSize2.width = 400;
        frame2.setMinimumSize(minSize2);
        final Container pane = frame2.getContentPane();
        pane.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        final JLabel label = new JLabel("Encrypted String: ", 11);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 0, 0);
        pane.add(label, c);
        final JTextField txtField = new JTextField(20);
        txtField.setText(encryptedPwd);
        txtField.setHorizontalAlignment(0);
        c.fill = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 0, 10);
        pane.add(txtField, c);
        final JButton btnRerun = new JButton("Run Again");
        c.fill = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = 20;
        c.insets = new Insets(10, 10, 10, 10);
        pane.add(btnRerun, c);
        final JButton btnCopy = new JButton("Copy to Clipboard");
        c.fill = 2;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = 20;
        c.insets = new Insets(10, 10, 10, 10);
        pane.add(btnCopy, c);
        btnCopy.addActionListener(new ActionListener() {
            
            public void actionPerformed(final ActionEvent e) {
                final StringSelection stringSelection = new StringSelection(txtField.getText());
                final Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
                LOG.info("Copied to Clipboard");
            }
        });
        btnRerun.addActionListener(new ActionListener() {
            
            public void actionPerformed(final ActionEvent e) {
                frame2.dispose();
                createAndShowGUI();
            }
        });
        frame2.setMinimumSize(minSize2);
        frame2.pack();
        frame2.setLocationRelativeTo(null);
        frame2.setResizable(false);
        frame2.setVisible(true);
    }
    
    static /* synthetic */ void access1(final String encryptKey) {
    	EncryptPassword.encryptKey = encryptKey;
    }

}
