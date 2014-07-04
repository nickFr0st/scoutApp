/*
 * Created by JFormDesigner on Thu Jul 03 20:09:51 MDT 2014
 */

package scout.clientPnls;

import constants.ErrorConst;
import guiUtil.JPasswordFieldDefaultText;
import guiUtil.JTextFieldDefaultText;
import guiUtil.SelectionBorder;
import scout.dbObjects.User;
import util.DBConnector;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class PnlSetupWizard extends JPanel {
    private Icon completeIcon;
    private DBConnector connector;
    private int userExists;

    {
        completeIcon = new ImageIcon(getClass().getResource("/images/complete.png"));
        connector = new DBConnector();
    }

    public PnlSetupWizard() {
        initComponents();
        enableStep2(false);

        ((SelectionBorder)getBorder()).cutSelectedArea(5, 105);
    }

    private void enableStep1(boolean enable) {
        txtDBName.setEnabled(enable);
        txtServerUsername.setEnabled(enable);
        txtServerPassword.setEnabled(enable);
        btnConnect.setEnabled(enable);
        btnCreate.setEnabled(enable);
    }

    private void enableStep2(boolean enable) {
        txtLeaderName.setEnabled(enable);
        txtTroopNumber.setEnabled(enable);
        txtScoutCouncil.setEnabled(enable);
        btnSubmit.setEnabled(enable);
        btnSkipStep.setEnabled(enable);
    }

    private void btnCreateMouseClicked() {
        clearErrors();
        if (validateDBFields()) return;

        int responseCode = connector.createDatabase(txtDBName.getText(), txtServerUsername.getText(), txtServerPassword.getText());

        if (responseCode == ErrorConst.INVALID_SERVER_CREDENTIALS.getId()) {
            Util.setError(lblServerPasswordError, ErrorConst.INVALID_SERVER_CREDENTIALS.getMessage());
        } else if (responseCode == ErrorConst.DATABASE_NAME_ALREADY_EXISTS.getId()) {
            Util.setError(lblDBNameError, ErrorConst.DATABASE_NAME_ALREADY_EXISTS.getMessage());
        } else if (responseCode == 0) {
            enableStep1(false);
            lblStepOne.setIcon(completeIcon);
            enableStep2(true);
        }
    }

    private boolean validateDBFields() {
        clearErrors();
        boolean hasErrors = false;

        if (Util.isEmpty(txtDBName.getText()) || txtDBName.isMessageDefault()) {
            Util.setError(lblDBNameError, "Database name cannot be left blank.");
            hasErrors = true;
        }

        if (Util.isEmpty(txtServerUsername.getText()) || txtServerUsername.isMessageDefault()) {
            Util.setError(lblServerUsernameError, "Server username cannot be left blank.");
            hasErrors = true;
        }

        if (Util.isEmpty(txtServerPassword.getText()) || txtServerPassword.isMessageDefault()) {
            Util.setError(lblServerPasswordError, "Server password cannot be left blank.");
            hasErrors = true;
        }

        if (hasErrors) {
            return true;
        }
        return false;
    }

    private void clearErrors() {
        lblDBNameError.setText("");
        lblDBNameError.setVisible(false);

        lblServerUsernameError.setText("");
        lblServerUsernameError.setVisible(false);

        lblServerPasswordError.setText("");
        lblServerPasswordError.setVisible(false);
    }

    private void btnSubmitMouseClicked() {
        if (!btnSubmit.isEnabled()) {
            return;
        }

        if (userExists > 0) {
            connector.updateById(userExists, "user", new String[]{"troopLeader", "troop", "council"}, new String[]{txtLeaderName.getText(), txtTroopNumber.getText(), txtScoutCouncil.getText()});
        } else {
            connector.insert("user", new String[]{"troopLeader", "troop", "council"}, new String[]{txtLeaderName.getText(), txtTroopNumber.getText(), txtScoutCouncil.getText()});
        }

        enableStep2(false);
        lblStepTwo.setIcon(completeIcon);
    }

    private void btnConnectMouseClicked() {
        clearErrors();
        if (validateDBFields()) return;

        int responseCode = connector.connectToDB(txtDBName.getText(), txtServerUsername.getText(), txtServerPassword.getText());

        if (responseCode == ErrorConst.INVALID_SERVER_CREDENTIALS.getId()) {
            Util.setError(lblServerPasswordError, ErrorConst.INVALID_SERVER_CREDENTIALS.getMessage());
        } else if (responseCode == ErrorConst.UNKNOWN_DATABASE.getId()) {
            Util.setError(lblDBNameError, ErrorConst.UNKNOWN_DATABASE.getMessage());
        } else if (responseCode == 0) {
            enableStep1(false);
            lblStepOne.setIcon(completeIcon);
            populateStep2();
            enableStep2(true);
        }
    }

    private void populateStep2() {
        User user = connector.getUser();
        if (user != null) {
            userExists = user.getId();
            txtLeaderName.setText(user.getTroopLeader());
            txtTroopNumber.setText(user.getTroopNumber());
            txtScoutCouncil.setText(user.getCouncil());
        }
    }

    private void btnSkipStepMouseClicked() {
        if (!btnSkipStep.isEnabled()) {
            return;
        }

        enableStep2(false);
        lblStepTwo.setIcon(completeIcon);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblWelcome = new JLabel();
        lblSetupMessage = new JLabel();
        label4 = new JLabel();
        lblStepOne = new JLabel();
        pnlCreateDatabase = new JPanel();
        txtDBName = new JTextFieldDefaultText();
        lblDBNameError = new JLabel();
        txtServerUsername = new JTextFieldDefaultText();
        lblServerUsernameError = new JLabel();
        txtServerPassword = new JPasswordFieldDefaultText();
        lblServerPasswordError = new JLabel();
        btnCreate = new JButton();
        btnConnect = new JButton();
        lblScoutLogo = new JLabel();
        label5 = new JLabel();
        lblStepTwo = new JLabel();
        pnlTroopInfo = new JPanel();
        txtLeaderName = new JTextFieldDefaultText();
        txtTroopNumber = new JTextFieldDefaultText();
        txtScoutCouncil = new JTextFieldDefaultText();
        btnSubmit = new JButton();
        btnSkipStep = new JButton();
        label6 = new JLabel();

        //======== this ========
        setMinimumSize(new Dimension(1100, 800));
        setPreferredSize(new Dimension(1100, 800));
        setBackground(Color.white);
        setBorder(new SelectionBorder());
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {15, 518, 19, 518, 10, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 11, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- lblWelcome ----
        lblWelcome.setText("Welcome to BSA Database");
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Vijaya", Font.PLAIN, 28));
        lblWelcome.setForeground(new Color(51, 102, 153));
        add(lblWelcome, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(20, 0, 5, 5), 0, 0));

        //---- lblSetupMessage ----
        lblSetupMessage.setText("Before you can start working let's setup your database and general troop information.");
        lblSetupMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblSetupMessage.setFont(new Font("Vijaya", Font.PLAIN, 28));
        lblSetupMessage.setForeground(new Color(51, 102, 153));
        add(lblSetupMessage, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label4 ----
        label4.setBackground(new Color(51, 102, 153));
        label4.setOpaque(true);
        label4.setMaximumSize(new Dimension(600, 2));
        label4.setMinimumSize(new Dimension(0, 2));
        label4.setPreferredSize(new Dimension(0, 2));
        add(label4, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblStepOne ----
        lblStepOne.setText("Step 1: Create or Connect to a database");
        lblStepOne.setForeground(new Color(51, 102, 153));
        lblStepOne.setFont(new Font("Vijaya", Font.PLAIN, 24));
        add(lblStepOne, new GridBagConstraints(1, 3, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(6, 10, 5, 5), 0, 0));

        //======== pnlCreateDatabase ========
        {
            pnlCreateDatabase.setBorder(null);
            pnlCreateDatabase.setBackground(Color.white);
            pnlCreateDatabase.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlCreateDatabase.getLayout()).columnWidths = new int[] {163, 193, 0, 0};
            ((GridBagLayout)pnlCreateDatabase.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)pnlCreateDatabase.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)pnlCreateDatabase.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- txtDBName ----
            txtDBName.setMinimumSize(new Dimension(14, 40));
            txtDBName.setPreferredSize(new Dimension(14, 40));
            txtDBName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtDBName.setDefaultText("Database name");
            pnlCreateDatabase.add(txtDBName, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- lblDBNameError ----
            lblDBNameError.setText("* Error Message");
            lblDBNameError.setForeground(Color.red);
            lblDBNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblDBNameError.setVisible(false);
            pnlCreateDatabase.add(lblDBNameError, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- txtServerUsername ----
            txtServerUsername.setMinimumSize(new Dimension(14, 40));
            txtServerUsername.setPreferredSize(new Dimension(14, 40));
            txtServerUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtServerUsername.setDefaultText("MySql server username");
            pnlCreateDatabase.add(txtServerUsername, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- lblServerUsernameError ----
            lblServerUsernameError.setText("* Error Message");
            lblServerUsernameError.setForeground(Color.red);
            lblServerUsernameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblServerUsernameError.setVisible(false);
            pnlCreateDatabase.add(lblServerUsernameError, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- txtServerPassword ----
            txtServerPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtServerPassword.setDefaultText("MySql server password");
            txtServerPassword.setMinimumSize(new Dimension(14, 40));
            txtServerPassword.setPreferredSize(new Dimension(72, 40));
            pnlCreateDatabase.add(txtServerPassword, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- lblServerPasswordError ----
            lblServerPasswordError.setText("* Error Message");
            lblServerPasswordError.setForeground(Color.red);
            lblServerPasswordError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblServerPasswordError.setVisible(false);
            pnlCreateDatabase.add(lblServerPasswordError, new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- btnCreate ----
            btnCreate.setText("Create");
            btnCreate.setMargin(new Insets(5, 20, 5, 20));
            btnCreate.setMinimumSize(new Dimension(70, 40));
            btnCreate.setMaximumSize(new Dimension(70, 30));
            btnCreate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnCreate.setBackground(new Color(51, 102, 153));
            btnCreate.setForeground(Color.white);
            btnCreate.setFocusPainted(false);
            btnCreate.setPreferredSize(new Dimension(82, 40));
            btnCreate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnCreateMouseClicked();
                }
            });
            pnlCreateDatabase.add(btnCreate, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(15, 20, 0, 5), 0, 0));

            //---- btnConnect ----
            btnConnect.setText("Connect");
            btnConnect.setMargin(new Insets(5, 20, 5, 20));
            btnConnect.setMinimumSize(new Dimension(70, 40));
            btnConnect.setMaximumSize(new Dimension(70, 30));
            btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnConnect.setBackground(new Color(51, 102, 153));
            btnConnect.setForeground(Color.white);
            btnConnect.setFocusPainted(false);
            btnConnect.setPreferredSize(new Dimension(82, 40));
            btnConnect.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnConnectMouseClicked();
                }
            });
            pnlCreateDatabase.add(btnConnect, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(15, 20, 0, 5), 0, 0));
        }
        add(pnlCreateDatabase, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblScoutLogo ----
        lblScoutLogo.setIcon(new ImageIcon(getClass().getResource("/images/BSA-logo400.png")));
        lblScoutLogo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblScoutLogo, new GridBagConstraints(3, 4, 1, 4, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label5 ----
        label5.setBackground(new Color(51, 102, 153));
        label5.setOpaque(true);
        label5.setMaximumSize(new Dimension(600, 2));
        label5.setMinimumSize(new Dimension(0, 2));
        label5.setPreferredSize(new Dimension(0, 2));
        add(label5, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(5, 0, 5, 5), 0, 0));

        //---- lblStepTwo ----
        lblStepTwo.setText("Step 2: Setup Troop Information");
        lblStepTwo.setForeground(new Color(51, 102, 153));
        lblStepTwo.setFont(new Font("Vijaya", Font.PLAIN, 24));
        add(lblStepTwo, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(6, 10, 5, 5), 0, 0));

        //======== pnlTroopInfo ========
        {
            pnlTroopInfo.setBackground(Color.white);
            pnlTroopInfo.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlTroopInfo.getLayout()).columnWidths = new int[] {168, 162, 108, 0, 0};
            ((GridBagLayout)pnlTroopInfo.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)pnlTroopInfo.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)pnlTroopInfo.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- txtLeaderName ----
            txtLeaderName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtLeaderName.setDefaultText("Troop leader name");
            txtLeaderName.setMinimumSize(new Dimension(14, 40));
            txtLeaderName.setPreferredSize(new Dimension(135, 40));
            pnlTroopInfo.add(txtLeaderName, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 5), 0, 0));

            //---- txtTroopNumber ----
            txtTroopNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtTroopNumber.setDefaultText("Troop #");
            txtTroopNumber.setMinimumSize(new Dimension(14, 40));
            txtTroopNumber.setPreferredSize(new Dimension(65, 40));
            pnlTroopInfo.add(txtTroopNumber, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 5), 0, 0));

            //---- txtScoutCouncil ----
            txtScoutCouncil.setPreferredSize(new Dimension(14, 40));
            txtScoutCouncil.setMinimumSize(new Dimension(14, 40));
            txtScoutCouncil.setDefaultText("Scout Council");
            txtScoutCouncil.setFont(new Font("Tahoma", Font.PLAIN, 14));
            pnlTroopInfo.add(txtScoutCouncil, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 5), 0, 0));

            //---- btnSubmit ----
            btnSubmit.setText("Submit");
            btnSubmit.setMargin(new Insets(5, 20, 5, 20));
            btnSubmit.setMinimumSize(new Dimension(70, 40));
            btnSubmit.setMaximumSize(new Dimension(70, 30));
            btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnSubmit.setBackground(new Color(51, 102, 153));
            btnSubmit.setForeground(Color.white);
            btnSubmit.setFocusPainted(false);
            btnSubmit.setPreferredSize(new Dimension(82, 40));
            btnSubmit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnSubmitMouseClicked();
                }
            });
            pnlTroopInfo.add(btnSubmit, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(15, 20, 0, 5), 0, 0));

            //---- btnSkipStep ----
            btnSkipStep.setText("Skip Step");
            btnSkipStep.setMargin(new Insets(5, 20, 5, 20));
            btnSkipStep.setMinimumSize(new Dimension(70, 40));
            btnSkipStep.setMaximumSize(new Dimension(70, 30));
            btnSkipStep.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnSkipStep.setBackground(new Color(51, 102, 153));
            btnSkipStep.setForeground(Color.white);
            btnSkipStep.setFocusPainted(false);
            btnSkipStep.setPreferredSize(new Dimension(82, 40));
            btnSkipStep.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnSkipStepMouseClicked();
                }
            });
            pnlTroopInfo.add(btnSkipStep, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(15, 20, 0, 5), 0, 0));
        }
        add(pnlTroopInfo, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label6 ----
        label6.setBackground(new Color(51, 102, 153));
        label6.setOpaque(true);
        label6.setMaximumSize(new Dimension(600, 2));
        label6.setMinimumSize(new Dimension(0, 2));
        label6.setPreferredSize(new Dimension(0, 2));
        add(label6, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(5, 0, 0, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblWelcome;
    private JLabel lblSetupMessage;
    private JLabel label4;
    private JLabel lblStepOne;
    private JPanel pnlCreateDatabase;
    private JTextFieldDefaultText txtDBName;
    private JLabel lblDBNameError;
    private JTextFieldDefaultText txtServerUsername;
    private JLabel lblServerUsernameError;
    private JPasswordFieldDefaultText txtServerPassword;
    private JLabel lblServerPasswordError;
    private JButton btnCreate;
    private JButton btnConnect;
    private JLabel lblScoutLogo;
    private JLabel label5;
    private JLabel lblStepTwo;
    private JPanel pnlTroopInfo;
    private JTextFieldDefaultText txtLeaderName;
    private JTextFieldDefaultText txtTroopNumber;
    private JTextFieldDefaultText txtScoutCouncil;
    private JButton btnSubmit;
    private JButton btnSkipStep;
    private JLabel label6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
