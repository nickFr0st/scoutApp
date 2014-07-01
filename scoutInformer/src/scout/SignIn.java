/*
 * Created by JFormDesigner on Sun Jun 29 18:20:11 MDT 2014
 */

package scout;

import constants.KeyConst;
import guiUtil.JPasswordFieldDefaultText;
import guiUtil.JTextFieldDefaultText;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Properties;

/**
 * @author User #2
 */
public class SignIn extends JFrame {
    private Properties properties;
    private String propertyFileName;

    {
        propertyFileName = "/properties/users.properties";
    }

    public SignIn() {
        initComponents();
        properties = new Properties();

        try {
            properties.load(getClass().getResourceAsStream(propertyFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        btnSignIn.setFocusPainted(false);
        btnNewUser.setFocusPainted(false);
        chkRememberUser.setFocusPainted(false);

        String savedUser = properties.getProperty(KeyConst.SAVED_USER.getName());

        txtUsername.requestFocus();
        if (!Util.isEmpty(savedUser)) {
            txtUsername.setTextColorToActive();
            txtUsername.setText(savedUser);
            chkRememberUser.setSelected(true);
        }
    }

    private void btnSignInMouseClicked() {
        clearErrors();
        boolean hasErrors = false;

        if (Util.isEmpty(txtUsername.getText()) || txtUsername.isMessageDefault()) {
            setUserError("Username cannot be left blank.");
            hasErrors = true;
        }

        if (Util.isEmpty(txtPassword.getText()) || txtPassword.isMessageDefault()) {
            setPasswordError("Password cannot be left blank.");
            hasErrors = true;
        }

        if (hasErrors) {
            return;
        }

        if (!validateUsernameAndPassword()) {
            return;
        }

        if (chkRememberUser.isSelected()) {
            properties.setProperty(KeyConst.SAVED_USER.getName(), txtUsername.getText());
            Util.saveProperties(properties, getClass().getResource(propertyFileName).toString());
        }

        // if all is good check to see if there is a valid database setup
        // if yes then load info and start the program
        // if no go to database setup screen.
    }

    private boolean validateUsernameAndPassword() {
        String password = properties.getProperty(txtUsername.getText());

        if (password == null) {
            setUserError("User does not exists.");
            return false;
        }

        if (!password.equals(txtPassword.getText())) {
            setPasswordError("Password does not match.");
            return false;
        }

        return true;
    }

    private void setPasswordError(String errorMessage) {
        lblPasswordErrorMessage.setText("* " + errorMessage);
        lblPasswordErrorMessage.setVisible(true);
    }

    private void setUserError(String errorMessage) {
        lblUserNameErrorMessage.setText("* " + errorMessage);
        lblUserNameErrorMessage.setVisible(true);
    }

    private void clearErrors() {
        lblUserNameErrorMessage.setVisible(false);
        lblUserNameErrorMessage.setText("");
        lblPasswordErrorMessage.setVisible(false);
        lblPasswordErrorMessage.setText("");
    }

    private void btnNewUserMouseClicked() {
        // take user to user setup screen
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        lblHeader = new JLabel();
        lblBanner = new JLabel();
        panel2 = new JPanel();
        txtUsername = new JTextFieldDefaultText();
        lblUserNameErrorMessage = new JLabel();
        txtPassword = new JPasswordFieldDefaultText();
        lblPasswordErrorMessage = new JLabel();
        chkRememberUser = new JCheckBox();
        btnSignIn = new JButton();
        label1 = new JLabel();
        btnNewUser = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(1200, 800));
        setResizable(false);
        setIconImage(new ImageIcon(getClass().getResource("/images/flurDeLis16.png")).getImage());
        setTitle("BSA Database");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- lblHeader ----
            lblHeader.setIcon(new ImageIcon(getClass().getResource("/images/signInTitlebar.png")));
            panel1.add(lblHeader, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblBanner ----
            lblBanner.setIcon(new ImageIcon(getClass().getResource("/images/timelessValues.png")));
            panel1.add(lblBanner, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(90, 100, 106, 55), 0, 0));

            //======== panel2 ========
            {
                panel2.setOpaque(false);
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 52, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {90, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- txtUsername ----
                txtUsername.setMinimumSize(new Dimension(400, 40));
                txtUsername.setPreferredSize(new Dimension(400, 40));
                txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtUsername.setDefaultText("Username");
                panel2.add(txtUsername, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- lblUserNameErrorMessage ----
                lblUserNameErrorMessage.setText("*Error Message");
                lblUserNameErrorMessage.setForeground(Color.red);
                lblUserNameErrorMessage.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblUserNameErrorMessage.setVisible(false);
                panel2.add(lblUserNameErrorMessage, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- txtPassword ----
                txtPassword.setPreferredSize(new Dimension(400, 40));
                txtPassword.setMinimumSize(new Dimension(400, 40));
                txtPassword.setDefaultText("Password");
                txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
                panel2.add(txtPassword, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- lblPasswordErrorMessage ----
                lblPasswordErrorMessage.setText("*Error Message");
                lblPasswordErrorMessage.setForeground(Color.red);
                lblPasswordErrorMessage.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblPasswordErrorMessage.setVisible(false);
                panel2.add(lblPasswordErrorMessage, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- chkRememberUser ----
                chkRememberUser.setText("Remember me");
                chkRememberUser.setOpaque(false);
                chkRememberUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
                chkRememberUser.setBorder(null);
                panel2.add(chkRememberUser, new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- btnSignIn ----
                btnSignIn.setText("Sign in");
                btnSignIn.setMargin(new Insets(5, 20, 5, 20));
                btnSignIn.setMinimumSize(new Dimension(70, 30));
                btnSignIn.setMaximumSize(new Dimension(70, 30));
                btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnSignIn.setBackground(new Color(51, 102, 153));
                btnSignIn.setForeground(Color.white);
                btnSignIn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnSignInMouseClicked();
                    }
                });
                panel2.add(btnSignIn, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(20, 0, 8, 8), 0, 0));

                //---- label1 ----
                label1.setBackground(new Color(51, 102, 153));
                label1.setOpaque(true);
                label1.setPreferredSize(new Dimension(400, 2));
                label1.setMinimumSize(new Dimension(400, 2));
                label1.setMaximumSize(new Dimension(400, 10));
                panel2.add(label1, new GridBagConstraints(0, 7, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(30, 0, 8, 0), 0, 0));

                //---- btnNewUser ----
                btnNewUser.setText("or Create a New User");
                btnNewUser.setMargin(new Insets(5, 20, 5, 20));
                btnNewUser.setMinimumSize(new Dimension(70, 30));
                btnNewUser.setMaximumSize(new Dimension(70, 30));
                btnNewUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnNewUser.setBackground(new Color(51, 102, 153));
                btnNewUser.setForeground(Color.white);
                btnNewUser.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnNewUserMouseClicked();
                    }
                });
                panel2.add(btnNewUser, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(20, 0, 0, 8), 0, 0));
            }
            panel1.add(panel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        contentPane.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel lblHeader;
    private JLabel lblBanner;
    private JPanel panel2;
    private JTextFieldDefaultText txtUsername;
    private JLabel lblUserNameErrorMessage;
    private JPasswordFieldDefaultText txtPassword;
    private JLabel lblPasswordErrorMessage;
    private JCheckBox chkRememberUser;
    private JButton btnSignIn;
    private JLabel label1;
    private JButton btnNewUser;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
