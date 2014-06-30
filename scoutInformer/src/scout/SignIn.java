/*
 * Created by JFormDesigner on Sun Jun 29 18:20:11 MDT 2014
 */

package scout;

import guiUtil.JPasswordFieldDefaultText;
import guiUtil.JTextFieldDefaultText;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class SignIn extends JFrame {
    public SignIn() {
        initComponents();
        btnSignIn.setFocusPainted(false);
        btnNewUser.setFocusPainted(false);
        chkRememberUser.setFocusPainted(false);

        txtUsername.requestFocus();
    }

    private void btnSignInMouseClicked() {
        if (Util.isEmpty(txtUsername.getText()) || txtUsername.isMessageDefault()) {
            // tell user they can't leave this field blank
            return;
        }

        if (Util.isEmpty(txtPassword.getText()) || txtPassword.isMessageDefault()) {
            // tell user they can't leave this field blank
            return;
        }

        // validate userName, if does not exist, say so

        // if password is wrong, say so

        // if all is good check to see if there is a valid database setup
        // if yes then load info and start the program
        // if no go to database setup screen.
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
        txtPassword = new JPasswordFieldDefaultText();
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
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {90, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- txtUsername ----
                txtUsername.setMinimumSize(new Dimension(400, 40));
                txtUsername.setPreferredSize(new Dimension(400, 40));
                txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtUsername.setDefaultText("Username");
                panel2.add(txtUsername, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- txtPassword ----
                txtPassword.setPreferredSize(new Dimension(400, 40));
                txtPassword.setMinimumSize(new Dimension(400, 40));
                txtPassword.setDefaultText("Password");
                txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
                panel2.add(txtPassword, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 8), 0, 0));

                //---- chkRememberUser ----
                chkRememberUser.setText("Remember me");
                chkRememberUser.setOpaque(false);
                chkRememberUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
                chkRememberUser.setBorder(null);
                panel2.add(chkRememberUser, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
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
                panel2.add(btnSignIn, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(20, 0, 8, 8), 0, 0));

                //---- label1 ----
                label1.setBackground(new Color(51, 102, 153));
                label1.setOpaque(true);
                label1.setPreferredSize(new Dimension(400, 2));
                label1.setMinimumSize(new Dimension(400, 2));
                label1.setMaximumSize(new Dimension(400, 10));
                panel2.add(label1, new GridBagConstraints(0, 5, 4, 1, 0.0, 0.0,
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
                panel2.add(btnNewUser, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
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
    private JPasswordFieldDefaultText txtPassword;
    private JCheckBox chkRememberUser;
    private JButton btnSignIn;
    private JLabel label1;
    private JButton btnNewUser;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
