/*
 * Created by JFormDesigner on Mon Jun 30 21:28:35 MDT 2014
 */

package scout.clientPnls;

import constants.KeyConst;
import guiUtil.JPasswordFieldDefaultText;
import guiUtil.JTextFieldDefaultText;
import scout.GuiManager;
import scout.SignIn;
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
public class PnlSignIn extends JPanel implements GuiManager {
    private Properties properties;
    private String propertyFileName;
    private boolean createIsShowing;
    private SignIn parentFrame;

    {
        propertyFileName = "/properties/users.properties";
        createIsShowing = true;  // do this so init works
    }

    public PnlSignIn() {
        initComponents();

        properties = new Properties();

        try {
            properties.load(getClass().getResourceAsStream(propertyFileName));
        } catch (IOException ignore) {
            return;
        }

        loadSavedUser();
        showCreateUser(false);
    }

    private void loadSavedUser() {
        String savedUser = properties.getProperty(KeyConst.SAVED_USER.getName());
        txtUsername.requestFocus();
        if (!Util.isEmpty(savedUser)) {
            txtUsername.setTextColorToActive();
            txtUsername.setText(savedUser);
            chkRememberUser.setSelected(true);
        } else {
            txtUsername.setDefault();
        }
    }

    public PnlSignIn(SignIn parentFrame) {
        this();
        this.parentFrame = parentFrame;
    }

    private void showCreateUser(boolean show) {
        if (show == createIsShowing) {
            return;
        }

        createIsShowing = show;
        txtNewUserName.setVisible(show);
        txtNewUserPassword.setVisible(show);
        txtNewUserPasswordVerify.setVisible(show);
        btnSubmit.setVisible(show);
        btnCancel.setVisible(show);

        if (!show) {
            txtNewUserName.setDefault();
            txtNewUserPassword.setDefault();
            txtNewUserPasswordVerify.setDefault();
            clearCreateErrors();
            enableSignInInfo(true);
        } else {
            enableSignInInfo(false);
        }
    }

    private void enableSignInInfo(boolean enable) {
        txtUsername.setEnabled(enable);
        txtPassword.setEnabled(enable);
        chkRememberUser.setEnabled(enable);
        btnSignIn.setEnabled(enable);

        if (!enable) {
            clearSignInErrors();
        }
    }

    private void btnSignInMouseClicked() {
        if (!btnSignIn.isEnabled()) {
            return;
        }

        clearSignInErrors();
        boolean hasErrors = false;

        if (Util.isEmpty(txtUsername.getText()) || txtUsername.isMessageDefault()) {
            Util.setError(lblUserNameError, "Username cannot be left blank.");
            hasErrors = true;
        }

        if (Util.isEmpty(txtPassword.getText()) || txtPassword.isMessageDefault()) {
            Util.setError(lblPasswordError, "Password cannot be left blank.");
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

        clearFields();
        nextStep();
    }

    private boolean validateUsernameAndPassword() {
        String password = properties.getProperty(txtUsername.getText());

        if (password == null) {
            Util.setError(lblUserNameError, "User does not exists.");
            return false;
        }

        if (!password.equals(txtPassword.getText())) {
            Util.setError(lblPasswordError, "Password does not match.");
            return false;
        }

        return true;
    }

    private void clearCreateErrors() {
        lblNewUserError.setVisible(false);
        lblNewUserError.setText("");
        lblNewPasswordError.setVisible(false);
        lblNewPasswordError.setText("");
        lblNewPasswordVerifyError.setVisible(false);
        lblNewPasswordVerifyError.setText("");
    }

    private void clearSignInErrors() {
        lblUserNameError.setVisible(false);
        lblUserNameError.setText("");
        lblPasswordError.setVisible(false);
        lblPasswordError.setText("");
    }

    private void btnNewUserMouseClicked() {
        showCreateUser(true);
    }

    private void btnCancelMouseClicked() {
        showCreateUser(false);
    }

    private void btnSubmitMouseClicked() {
        clearCreateErrors();
        boolean hasErrors = false;

        if (Util.isEmpty(txtNewUserName.getText()) || txtNewUserName.isMessageDefault()) {
            Util.setError(lblNewUserError, "Username cannot be left blank.");
            hasErrors = true;
        }

        if (Util.isEmpty(txtNewUserPassword.getText()) || txtNewUserPassword.isMessageDefault()) {
            Util.setError(lblNewPasswordError, "Password cannot be left blank.");
            hasErrors = true;
        }

        if (Util.isEmpty(txtNewUserPasswordVerify.getText()) || txtNewUserPasswordVerify.isMessageDefault()) {
            Util.setError(lblNewPasswordVerifyError, "Password cannot be left blank.");
            hasErrors = true;
        }

        if (hasErrors) {
            return;
        }

        if (!validateNewUserCredentials()) {
            return;
        }

        properties.setProperty(txtNewUserName.getText(), txtNewUserPassword.getText());
        Util.saveProperties(properties, getClass().getResource(propertyFileName).toString());

        clearFields();
        nextStep();
    }

    private boolean validateNewUserCredentials() {
        String password = properties.getProperty(txtNewUserName.getText());

        if (password != null) {
            Util.setError(lblNewUserError, "Username already exists.");
            return false;
        }

        if (!txtNewUserPassword.getText().equals(txtNewUserPasswordVerify.getText())) {
            Util.setError(lblNewPasswordVerifyError, "Passwords do not match.");
            return false;
        }

        return true;
    }

    private void clearFields() {
        showCreateUser(false);
        txtPassword.setDefault();
        loadSavedUser();
    }

    @Override
    public void nextStep() {
        parentFrame.nextStep();
    }

    @Override
    public void previousStep() {
        parentFrame.previousStep();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pnlSignIn = new JPanel();
        lblHeader = new JLabel();
        lblBanner = new JLabel();
        pnlSignInInfo = new JPanel();
        txtUsername = new JTextFieldDefaultText();
        lblUserNameError = new JLabel();
        txtPassword = new JPasswordFieldDefaultText();
        lblPasswordError = new JLabel();
        chkRememberUser = new JCheckBox();
        btnSignIn = new JButton();
        label1 = new JLabel();
        btnNewUser = new JButton();
        txtNewUserName = new JTextFieldDefaultText();
        lblNewUserError = new JLabel();
        txtNewUserPassword = new JPasswordFieldDefaultText();
        lblNewPasswordError = new JLabel();
        txtNewUserPasswordVerify = new JPasswordFieldDefaultText();
        lblNewPasswordVerifyError = new JLabel();
        btnSubmit = new JButton();
        btnCancel = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(1200, 800));
        setPreferredSize(new Dimension(1200, 800));
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

        //======== pnlSignIn ========
        {
            pnlSignIn.setBackground(Color.white);
            pnlSignIn.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlSignIn.getLayout()).columnWidths = new int[] {692, 0, 0};
            ((GridBagLayout)pnlSignIn.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)pnlSignIn.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
            ((GridBagLayout)pnlSignIn.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

            //---- lblHeader ----
            lblHeader.setIcon(new ImageIcon(getClass().getResource("/images/signInTitlebar.png")));
            pnlSignIn.add(lblHeader, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblBanner ----
            lblBanner.setIcon(new ImageIcon(getClass().getResource("/images/timelessValues.png")));
            pnlSignIn.add(lblBanner, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(90, 100, 106, 55), 0, 0));

            //======== pnlSignInInfo ========
            {
                pnlSignInInfo.setOpaque(false);
                pnlSignInInfo.setLayout(new GridBagLayout());
                ((GridBagLayout)pnlSignInInfo.getLayout()).columnWidths = new int[] {0, 0, 69, 0, 52, 0};
                ((GridBagLayout)pnlSignInInfo.getLayout()).rowHeights = new int[] {67, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)pnlSignInInfo.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)pnlSignInInfo.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- txtUsername ----
                txtUsername.setMinimumSize(new Dimension(400, 40));
                txtUsername.setPreferredSize(new Dimension(400, 40));
                txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtUsername.setDefaultText("Username");
                pnlSignInInfo.add(txtUsername, new GridBagConstraints(0, 1, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- lblUserNameError ----
                lblUserNameError.setText("* Error Message");
                lblUserNameError.setForeground(Color.red);
                lblUserNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblUserNameError.setVisible(false);
                pnlSignInInfo.add(lblUserNameError, new GridBagConstraints(0, 2, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- txtPassword ----
                txtPassword.setPreferredSize(new Dimension(400, 40));
                txtPassword.setMinimumSize(new Dimension(400, 40));
                txtPassword.setDefaultText("Password");
                txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
                pnlSignInInfo.add(txtPassword, new GridBagConstraints(0, 3, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- lblPasswordError ----
                lblPasswordError.setText("* Error Message");
                lblPasswordError.setForeground(Color.red);
                lblPasswordError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblPasswordError.setVisible(false);
                pnlSignInInfo.add(lblPasswordError, new GridBagConstraints(0, 4, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- chkRememberUser ----
                chkRememberUser.setText("Remember me");
                chkRememberUser.setOpaque(false);
                chkRememberUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
                chkRememberUser.setBorder(null);
                chkRememberUser.setFocusPainted(false);
                pnlSignInInfo.add(chkRememberUser, new GridBagConstraints(0, 5, 4, 1, 0.0, 0.0,
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
                btnSignIn.setFocusPainted(false);
                btnSignIn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnSignInMouseClicked();
                    }
                });
                pnlSignInInfo.add(btnSignIn, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(10, 0, 8, 8), 0, 0));

                //---- label1 ----
                label1.setBackground(new Color(51, 102, 153));
                label1.setOpaque(true);
                label1.setPreferredSize(new Dimension(400, 2));
                label1.setMinimumSize(new Dimension(400, 2));
                label1.setMaximumSize(new Dimension(400, 10));
                pnlSignInInfo.add(label1, new GridBagConstraints(0, 7, 5, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(10, 0, 8, 0), 0, 0));

                //---- btnNewUser ----
                btnNewUser.setText("or Create a New User");
                btnNewUser.setMargin(new Insets(5, 20, 5, 20));
                btnNewUser.setMinimumSize(new Dimension(70, 30));
                btnNewUser.setMaximumSize(new Dimension(70, 30));
                btnNewUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnNewUser.setBackground(new Color(51, 102, 153));
                btnNewUser.setForeground(Color.white);
                btnNewUser.setFocusPainted(false);
                btnNewUser.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnNewUserMouseClicked();
                    }
                });
                pnlSignInInfo.add(btnNewUser, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(10, 0, 18, 8), 0, 0));

                //---- txtNewUserName ----
                txtNewUserName.setPreferredSize(new Dimension(14, 40));
                txtNewUserName.setMinimumSize(new Dimension(14, 40));
                txtNewUserName.setDefaultText("Username");
                txtNewUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                pnlSignInInfo.add(txtNewUserName, new GridBagConstraints(0, 9, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- lblNewUserError ----
                lblNewUserError.setText("* Error Message");
                lblNewUserError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblNewUserError.setForeground(Color.red);
                lblNewUserError.setVisible(false);
                pnlSignInInfo.add(lblNewUserError, new GridBagConstraints(0, 10, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- txtNewUserPassword ----
                txtNewUserPassword.setMinimumSize(new Dimension(14, 40));
                txtNewUserPassword.setPreferredSize(new Dimension(14, 40));
                txtNewUserPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtNewUserPassword.setDefaultText("Password");
                pnlSignInInfo.add(txtNewUserPassword, new GridBagConstraints(0, 11, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- lblNewPasswordError ----
                lblNewPasswordError.setText("* Error Message");
                lblNewPasswordError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblNewPasswordError.setForeground(Color.red);
                lblNewPasswordError.setVisible(false);
                pnlSignInInfo.add(lblNewPasswordError, new GridBagConstraints(0, 12, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- txtNewUserPasswordVerify ----
                txtNewUserPasswordVerify.setPreferredSize(new Dimension(14, 40));
                txtNewUserPasswordVerify.setMinimumSize(new Dimension(14, 40));
                txtNewUserPasswordVerify.setDefaultText("Re-enter password");
                txtNewUserPasswordVerify.setFont(new Font("Tahoma", Font.PLAIN, 14));
                pnlSignInInfo.add(txtNewUserPasswordVerify, new GridBagConstraints(0, 13, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- lblNewPasswordVerifyError ----
                lblNewPasswordVerifyError.setText("* Error Message");
                lblNewPasswordVerifyError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblNewPasswordVerifyError.setForeground(Color.red);
                lblNewPasswordVerifyError.setVisible(false);
                pnlSignInInfo.add(lblNewPasswordVerifyError, new GridBagConstraints(0, 14, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- btnSubmit ----
                btnSubmit.setText("Submit");
                btnSubmit.setBackground(new Color(51, 102, 153));
                btnSubmit.setForeground(Color.white);
                btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnSubmit.setFocusPainted(false);
                btnSubmit.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnSubmitMouseClicked();
                    }
                });
                pnlSignInInfo.add(btnSubmit, new GridBagConstraints(0, 15, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(10, 0, 0, 8), 0, 0));

                //---- btnCancel ----
                btnCancel.setText("Cancel");
                btnCancel.setBackground(new Color(51, 102, 153));
                btnCancel.setForeground(Color.white);
                btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnCancel.setFocusPainted(false);
                btnCancel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnCancelMouseClicked();
                    }
                });
                pnlSignInInfo.add(btnCancel, new GridBagConstraints(1, 15, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(10, 0, 0, 8), 0, 0));
            }
            pnlSignIn.add(pnlSignInInfo, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(pnlSignIn, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel pnlSignIn;
    private JLabel lblHeader;
    private JLabel lblBanner;
    private JPanel pnlSignInInfo;
    private JTextFieldDefaultText txtUsername;
    private JLabel lblUserNameError;
    private JPasswordFieldDefaultText txtPassword;
    private JLabel lblPasswordError;
    private JCheckBox chkRememberUser;
    private JButton btnSignIn;
    private JLabel label1;
    private JButton btnNewUser;
    private JTextFieldDefaultText txtNewUserName;
    private JLabel lblNewUserError;
    private JPasswordFieldDefaultText txtNewUserPassword;
    private JLabel lblNewPasswordError;
    private JPasswordFieldDefaultText txtNewUserPasswordVerify;
    private JLabel lblNewPasswordVerifyError;
    private JButton btnSubmit;
    private JButton btnCancel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
