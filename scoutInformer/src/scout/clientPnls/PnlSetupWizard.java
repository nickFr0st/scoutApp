/*
 * Created by JFormDesigner on Thu Jul 03 20:09:51 MDT 2014
 */

package scout.clientPnls;

import guiUtil.JPasswordFieldDefaultText;
import guiUtil.JTextFieldDefaultText;
import guiUtil.SelectionBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class PnlSetupWizard extends JPanel {
    public PnlSetupWizard() {
        initComponents();

        ((SelectionBorder)getBorder()).cutSelectedArea(5, 105);
    }

    private void btnConnectMouseClicked() {
        showStep1(false);
    }

    private void showStep1(boolean show) {
        pnlCreateDatabase.setVisible(show);
        pnlConnectDataBase.setVisible(show);
        if (!show) {
            lblStepOne.setIcon(new ImageIcon(getClass().getResource("/images/complete.png")));
        }
    }

    private void showStep2(boolean show) {

    }

    private void btnCreateMouseClicked() {
        showStep1(false);
    }

    private void btnBrowseNewMouseClicked() {
    }

    private void lblStepOneMouseEntered() {
        if (!pnlCreateDatabase.isVisible()) {
            lblStepOne.setForeground(new Color(32,154,26));
        }
    }

    private void lblStepOneMouseExited() {
        if (!pnlCreateDatabase.isVisible()) {
            lblStepOne.setForeground(new Color(51,102,153));
        }
    }

    private void lblStepOneMouseClicked() {
        if (!pnlCreateDatabase.isVisible()) {
            lblStepOne.setForeground(new Color(51,102,153));
            showStep1(true);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblWelcome = new JLabel();
        lblSetupMessage = new JLabel();
        label4 = new JLabel();
        lblStepOne = new JLabel();
        pnlCreateDatabase = new JPanel();
        txtNewDatabasePath = new JTextFieldDefaultText();
        btnBrowseNew = new JButton();
        txtNewDatabaseName = new JTextFieldDefaultText();
        txtNewDBUsername = new JTextFieldDefaultText();
        lblNewDBUsernameError = new JLabel();
        txtNewDBPassword = new JPasswordFieldDefaultText();
        lblNewDBPasswordError = new JLabel();
        txtNewDBPasswordVerify = new JPasswordFieldDefaultText();
        lblNEwDBPasswordVerifyError = new JLabel();
        btnCreate = new JButton();
        lblSeparator = new JLabel();
        pnlConnectDataBase = new JPanel();
        txtSelectedDatabasePath = new JTextFieldDefaultText();
        btnBrowseSelect = new JButton();
        txtSelectDBUsername = new JTextFieldDefaultText();
        lblSelectDBUsernameError = new JLabel();
        txtSelectDBPassword = new JPasswordFieldDefaultText();
        lblDBSelectPasswordError = new JLabel();
        btnConnect = new JButton();
        label5 = new JLabel();
        lblStepOne2 = new JLabel();
        label6 = new JLabel();

        //======== this ========
        setMinimumSize(new Dimension(1100, 800));
        setPreferredSize(new Dimension(1100, 800));
        setBackground(Color.white);
        setBorder(new SelectionBorder());
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {15, 518, 19, 518, 10, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

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
        lblStepOne.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblStepOneMouseClicked();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lblStepOneMouseEntered();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblStepOneMouseExited();
            }
        });
        add(lblStepOne, new GridBagConstraints(1, 3, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(6, 10, 5, 5), 0, 0));

        //======== pnlCreateDatabase ========
        {
            pnlCreateDatabase.setBorder(null);
            pnlCreateDatabase.setBackground(Color.white);
            pnlCreateDatabase.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlCreateDatabase.getLayout()).columnWidths = new int[] {163, 193, 0, 0};
            ((GridBagLayout)pnlCreateDatabase.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)pnlCreateDatabase.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)pnlCreateDatabase.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- txtNewDatabasePath ----
            txtNewDatabasePath.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtNewDatabasePath.setMinimumSize(new Dimension(14, 40));
            txtNewDatabasePath.setPreferredSize(new Dimension(14, 40));
            txtNewDatabasePath.setDefaultText("Select a location to create your database");
            txtNewDatabasePath.setEditable(false);
            txtNewDatabasePath.setFocusable(false);
            pnlCreateDatabase.add(txtNewDatabasePath, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 5), 0, 0));

            //---- btnBrowseNew ----
            btnBrowseNew.setText("Browse");
            btnBrowseNew.setMargin(new Insets(5, 20, 5, 20));
            btnBrowseNew.setMinimumSize(new Dimension(70, 30));
            btnBrowseNew.setMaximumSize(new Dimension(70, 30));
            btnBrowseNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnBrowseNew.setBackground(new Color(51, 102, 153));
            btnBrowseNew.setForeground(Color.white);
            btnBrowseNew.setFocusPainted(false);
            btnBrowseNew.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnBrowseNewMouseClicked();
                }
            });
            pnlCreateDatabase.add(btnBrowseNew, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 10), 0, 0));

            //---- txtNewDatabaseName ----
            txtNewDatabaseName.setMinimumSize(new Dimension(14, 40));
            txtNewDatabaseName.setPreferredSize(new Dimension(14, 40));
            txtNewDatabaseName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtNewDatabaseName.setDefaultText("Database name");
            pnlCreateDatabase.add(txtNewDatabaseName, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- txtNewDBUsername ----
            txtNewDBUsername.setMinimumSize(new Dimension(14, 40));
            txtNewDBUsername.setPreferredSize(new Dimension(14, 40));
            txtNewDBUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtNewDBUsername.setDefaultText("Username");
            pnlCreateDatabase.add(txtNewDBUsername, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- lblNewDBUsernameError ----
            lblNewDBUsernameError.setText("* Error Message");
            lblNewDBUsernameError.setForeground(Color.red);
            lblNewDBUsernameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblNewDBUsernameError.setVisible(false);
            pnlCreateDatabase.add(lblNewDBUsernameError, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- txtNewDBPassword ----
            txtNewDBPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtNewDBPassword.setDefaultText("Password");
            txtNewDBPassword.setMinimumSize(new Dimension(14, 40));
            txtNewDBPassword.setPreferredSize(new Dimension(72, 40));
            pnlCreateDatabase.add(txtNewDBPassword, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- lblNewDBPasswordError ----
            lblNewDBPasswordError.setText("* Error Message");
            lblNewDBPasswordError.setForeground(Color.red);
            lblNewDBPasswordError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblNewDBPasswordError.setVisible(false);
            pnlCreateDatabase.add(lblNewDBPasswordError, new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- txtNewDBPasswordVerify ----
            txtNewDBPasswordVerify.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtNewDBPasswordVerify.setDefaultText("Re-enter password");
            txtNewDBPasswordVerify.setMinimumSize(new Dimension(14, 40));
            txtNewDBPasswordVerify.setPreferredSize(new Dimension(72, 40));
            pnlCreateDatabase.add(txtNewDBPasswordVerify, new GridBagConstraints(0, 6, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- lblNEwDBPasswordVerifyError ----
            lblNEwDBPasswordVerifyError.setText("* Error Message");
            lblNEwDBPasswordVerifyError.setForeground(Color.red);
            lblNEwDBPasswordVerifyError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblNEwDBPasswordVerifyError.setVisible(false);
            pnlCreateDatabase.add(lblNEwDBPasswordVerifyError, new GridBagConstraints(0, 7, 3, 1, 0.0, 0.0,
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
            pnlCreateDatabase.add(btnCreate, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 20, 0, 5), 0, 0));
        }
        add(pnlCreateDatabase, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblSeparator ----
        lblSeparator.setMinimumSize(new Dimension(2, 0));
        lblSeparator.setPreferredSize(new Dimension(2, 0));
        lblSeparator.setBackground(new Color(51, 102, 153));
        lblSeparator.setOpaque(true);
        add(lblSeparator, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== pnlConnectDataBase ========
        {
            pnlConnectDataBase.setBackground(Color.white);
            pnlConnectDataBase.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlConnectDataBase.getLayout()).columnWidths = new int[] {156, 196, 66, 0};
            ((GridBagLayout)pnlConnectDataBase.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)pnlConnectDataBase.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)pnlConnectDataBase.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- txtSelectedDatabasePath ----
            txtSelectedDatabasePath.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtSelectedDatabasePath.setMinimumSize(new Dimension(14, 40));
            txtSelectedDatabasePath.setPreferredSize(new Dimension(14, 40));
            txtSelectedDatabasePath.setDefaultText("Select an existing BSA database");
            txtSelectedDatabasePath.setEditable(false);
            txtSelectedDatabasePath.setFocusable(false);
            pnlConnectDataBase.add(txtSelectedDatabasePath, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 8, 8), 0, 0));

            //---- btnBrowseSelect ----
            btnBrowseSelect.setText("Browse");
            btnBrowseSelect.setMargin(new Insets(5, 20, 5, 20));
            btnBrowseSelect.setMinimumSize(new Dimension(70, 30));
            btnBrowseSelect.setMaximumSize(new Dimension(70, 30));
            btnBrowseSelect.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnBrowseSelect.setBackground(new Color(51, 102, 153));
            btnBrowseSelect.setForeground(Color.white);
            btnBrowseSelect.setFocusPainted(false);
            pnlConnectDataBase.add(btnBrowseSelect, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 8, 20), 0, 0));

            //---- txtSelectDBUsername ----
            txtSelectDBUsername.setMinimumSize(new Dimension(14, 40));
            txtSelectDBUsername.setPreferredSize(new Dimension(14, 40));
            txtSelectDBUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtSelectDBUsername.setDefaultText("Username");
            pnlConnectDataBase.add(txtSelectDBUsername, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 8, 20), 0, 0));

            //---- lblSelectDBUsernameError ----
            lblSelectDBUsernameError.setText("* Error Message");
            lblSelectDBUsernameError.setForeground(Color.red);
            lblSelectDBUsernameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblSelectDBUsernameError.setVisible(false);
            pnlConnectDataBase.add(lblSelectDBUsernameError, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 8, 20), 0, 0));

            //---- txtSelectDBPassword ----
            txtSelectDBPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtSelectDBPassword.setDefaultText("Password");
            txtSelectDBPassword.setMinimumSize(new Dimension(14, 40));
            txtSelectDBPassword.setPreferredSize(new Dimension(72, 40));
            pnlConnectDataBase.add(txtSelectDBPassword, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 8, 20), 0, 0));

            //---- lblDBSelectPasswordError ----
            lblDBSelectPasswordError.setText("* Error Message");
            lblDBSelectPasswordError.setForeground(Color.red);
            lblDBSelectPasswordError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblDBSelectPasswordError.setVisible(false);
            pnlConnectDataBase.add(lblDBSelectPasswordError, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 8, 20), 0, 0));

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
            pnlConnectDataBase.add(btnConnect, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 10, 0, 8), 0, 0));
        }
        add(pnlConnectDataBase, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label5 ----
        label5.setBackground(new Color(51, 102, 153));
        label5.setOpaque(true);
        label5.setMaximumSize(new Dimension(600, 2));
        label5.setMinimumSize(new Dimension(0, 2));
        label5.setPreferredSize(new Dimension(0, 2));
        add(label5, new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(5, 0, 5, 5), 0, 0));

        //---- lblStepOne2 ----
        lblStepOne2.setText("Step 2: Setup Troop Information");
        lblStepOne2.setForeground(new Color(51, 102, 153));
        lblStepOne2.setFont(new Font("Vijaya", Font.PLAIN, 24));
        add(lblStepOne2, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(6, 10, 5, 5), 0, 0));

        //---- label6 ----
        label6.setBackground(new Color(51, 102, 153));
        label6.setOpaque(true);
        label6.setMaximumSize(new Dimension(600, 2));
        label6.setMinimumSize(new Dimension(0, 2));
        label6.setPreferredSize(new Dimension(0, 2));
        add(label6, new GridBagConstraints(1, 7, 3, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(5, 0, 5, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblWelcome;
    private JLabel lblSetupMessage;
    private JLabel label4;
    private JLabel lblStepOne;
    private JPanel pnlCreateDatabase;
    private JTextFieldDefaultText txtNewDatabasePath;
    private JButton btnBrowseNew;
    private JTextFieldDefaultText txtNewDatabaseName;
    private JTextFieldDefaultText txtNewDBUsername;
    private JLabel lblNewDBUsernameError;
    private JPasswordFieldDefaultText txtNewDBPassword;
    private JLabel lblNewDBPasswordError;
    private JPasswordFieldDefaultText txtNewDBPasswordVerify;
    private JLabel lblNEwDBPasswordVerifyError;
    private JButton btnCreate;
    private JLabel lblSeparator;
    private JPanel pnlConnectDataBase;
    private JTextFieldDefaultText txtSelectedDatabasePath;
    private JButton btnBrowseSelect;
    private JTextFieldDefaultText txtSelectDBUsername;
    private JLabel lblSelectDBUsernameError;
    private JPasswordFieldDefaultText txtSelectDBPassword;
    private JLabel lblDBSelectPasswordError;
    private JButton btnConnect;
    private JLabel label5;
    private JLabel lblStepOne2;
    private JLabel label6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
