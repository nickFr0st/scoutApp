/*
 * Created by JFormDesigner on Sat Nov 15 13:34:05 MST 2014
 */

package scout.clientPnls.boyScoutPnls;

import constants.ContactTypeConst;
import guiUtil.JTextFieldDefaultText;
import scout.dbObjects.Contact;
import util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class ContactDialog extends JDialog {
    public static final int BTN_OK = 1;

    private Contact contact;
    private int btnChoice;

    {
        btnChoice = 0;
    }

    public ContactDialog(Frame owner) {
        super(owner, true);
        initComponents();

        for (ContactTypeConst typeConst : ContactTypeConst.values()) {
            cboType.addItem(typeConst.getName());
        }

        cboType.setSelectedItem(0);

        txtPhone.setVisible(true);
        txtEmail.setVisible(false);
        clearErrors();
    }

    private void clearErrors() {
        lblNameError.setText("");
        lblNameError.setVisible(false);

        lblRelationError.setText("");
        lblRelationError.setVisible(false);

        lblEmailError.setText("");
        lblEmailError.setVisible(false);

        lblPhoneError.setText("");
        lblPhoneError.setVisible(false);

        revalidate();
    }

    public Contact getContact() {
        return contact;
    }

    private void btnCancelMouseClicked() {
        contact = null;
        dispose();
    }

    private void btnOkMouseClicked() {
        if (!validateFields()) return;

        contact = new Contact();
        contact.setName(txtName.getText());
        contact.setRelation(txtRelation.getText());
        contact.setTypeId(ContactTypeConst.getIdByName(cboType.getSelectedItem().toString()));

        if (cboType.getSelectedItem().toString().equals(ContactTypeConst.EMAIL.getName())) {
            contact.setData(txtEmail.getText());
        } else {
            contact.setData(txtPhone.getText());
        }

        btnChoice = BTN_OK;
        dispose();
    }

    private boolean validateFields() {
        clearErrors();
        boolean hasErrors = false;

        if (txtName.isMessageDefault()) {
            Util.setError(lblNameError, "Cannot leave name blank");
            hasErrors = true;
        }

        if (txtRelation.isMessageDefault()) {
            Util.setError(lblRelationError, "Cannot leave relation blank");
            hasErrors = true;
        }

        if (cboType.getSelectedItem().toString().equals(ContactTypeConst.EMAIL.getName())) {
            if (txtEmail.isMessageDefault()) {
                Util.setError(lblEmailError, "Cannot leave contact data blank");
                hasErrors = true;
            } else if (!Util.validateEmail(txtEmail.getText())) {
                Util.setError(lblPhoneError, "contact data format is invalid");
                hasErrors = true;
            }
        } else {
            if (txtPhone.isMessageDefault()) {
                Util.setError(lblPhoneError, "Cannot leave contact data blank");
                hasErrors = true;
            } else if (!Util.validatePhoneNumber(txtPhone.getText())) {
                Util.setError(lblPhoneError, "contact data format is invalid");
                hasErrors = true;
            }
        }

        return !hasErrors;
    }

    public int getBtnChoice() {
        return btnChoice;
    }

    private void cboTypeActionPerformed() {
        if (cboType.getSelectedItem().equals(ContactTypeConst.EMAIL.getName())) {
            txtPhone.setVisible(false);
            txtEmail.setVisible(true);
        } else {
            txtEmail.setVisible(false);
            txtPhone.setVisible(true);
        }
        revalidate();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        lblType = new JLabel();
        cboType = new JComboBox();
        lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblRelation = new JLabel();
        txtRelation = new JTextFieldDefaultText();
        lblRelationError = new JLabel();
        lblData = new JLabel();
        pnlData = new JPanel();
        txtEmail = new JTextFieldDefaultText();
        lblEmailError = new JLabel();
        txtPhone = new JTextFieldDefaultText();
        lblPhoneError = new JLabel();
        buttonBar = new JPanel();
        btnOk = new JButton();
        btnCancel = new JButton();

        //======== this ========
        setTitle("Contact Creation");
        setMinimumSize(new Dimension(550, 380));
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBackground(Color.white);
            dialogPane.setName("dialogPane");
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setBackground(Color.white);
                contentPanel.setName("contentPanel");
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {110, 270, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- lblType ----
                lblType.setText("Contact Type:");
                lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblType.setForeground(new Color(51, 102, 153));
                lblType.setName("lblType");
                contentPanel.add(lblType, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- cboType ----
                cboType.setPreferredSize(new Dimension(14, 40));
                cboType.setFont(new Font("Tahoma", Font.PLAIN, 14));
                cboType.setBackground(Color.white);
                cboType.setName("cboType");
                cboType.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cboTypeActionPerformed();
                    }
                });
                contentPanel.add(cboType, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblName ----
                lblName.setText("Name:");
                lblName.setForeground(new Color(51, 102, 153));
                lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblName.setName("lblName");
                contentPanel.add(lblName, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- txtName ----
                txtName.setPreferredSize(new Dimension(14, 40));
                txtName.setDefaultText("Name");
                txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtName.setName("txtName");
                contentPanel.add(txtName, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblNameError ----
                lblNameError.setText("text");
                lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblNameError.setForeground(Color.red);
                lblNameError.setName("lblNameError");
                contentPanel.add(lblNameError, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblRelation ----
                lblRelation.setText("Relation to Scout:");
                lblRelation.setForeground(new Color(51, 102, 153));
                lblRelation.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblRelation.setName("lblRelation");
                contentPanel.add(lblRelation, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- txtRelation ----
                txtRelation.setPreferredSize(new Dimension(14, 40));
                txtRelation.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtRelation.setDefaultText("(Parent, Self, etc.)");
                txtRelation.setName("txtRelation");
                contentPanel.add(txtRelation, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblRelationError ----
                lblRelationError.setText("text");
                lblRelationError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblRelationError.setForeground(Color.red);
                lblRelationError.setName("lblRelationError");
                contentPanel.add(lblRelationError, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblData ----
                lblData.setText("Contact Data:");
                lblData.setForeground(new Color(51, 102, 153));
                lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblData.setName("lblData");
                contentPanel.add(lblData, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //======== pnlData ========
                {
                    pnlData.setBackground(Color.white);
                    pnlData.setName("pnlData");
                    pnlData.setLayout(new GridBagLayout());
                    ((GridBagLayout)pnlData.getLayout()).columnWidths = new int[] {0, 0};
                    ((GridBagLayout)pnlData.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
                    ((GridBagLayout)pnlData.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                    ((GridBagLayout)pnlData.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                    //---- txtEmail ----
                    txtEmail.setPreferredSize(new Dimension(14, 40));
                    txtEmail.setDefaultText("example@email.com");
                    txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    txtEmail.setName("txtEmail");
                    pnlData.add(txtEmail, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- lblEmailError ----
                    lblEmailError.setText("text");
                    lblEmailError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                    lblEmailError.setForeground(Color.red);
                    lblEmailError.setName("lblEmailError");
                    pnlData.add(lblEmailError, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- txtPhone ----
                    txtPhone.setPreferredSize(new Dimension(14, 40));
                    txtPhone.setDefaultText("123-456-7890");
                    txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    txtPhone.setName("txtPhone");
                    pnlData.add(txtPhone, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- lblPhoneError ----
                    lblPhoneError.setText("text");
                    lblPhoneError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                    lblPhoneError.setForeground(Color.red);
                    lblPhoneError.setName("lblPhoneError");
                    pnlData.add(lblPhoneError, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                contentPanel.add(pnlData, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setBackground(Color.white);
                buttonBar.setName("buttonBar");
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).rowHeights = new int[] {50};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- btnOk ----
                btnOk.setText("OK");
                btnOk.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnOk.setForeground(Color.white);
                btnOk.setBackground(new Color(51, 156, 229));
                btnOk.setFocusPainted(false);
                btnOk.setName("btnOk");
                btnOk.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnOkMouseClicked();
                    }
                });
                buttonBar.add(btnOk, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnCancel ----
                btnCancel.setText("Cancel");
                btnCancel.setForeground(Color.white);
                btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnCancel.setBackground(new Color(206, 0, 0));
                btnCancel.setFocusPainted(false);
                btnCancel.setName("btnCancel");
                btnCancel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnCancelMouseClicked();
                    }
                });
                buttonBar.add(btnCancel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel lblType;
    private JComboBox cboType;
    private JLabel lblName;
    private JTextFieldDefaultText txtName;
    private JLabel lblNameError;
    private JLabel lblRelation;
    private JTextFieldDefaultText txtRelation;
    private JLabel lblRelationError;
    private JLabel lblData;
    private JPanel pnlData;
    private JTextFieldDefaultText txtEmail;
    private JLabel lblEmailError;
    private JTextFieldDefaultText txtPhone;
    private JLabel lblPhoneError;
    private JPanel buttonBar;
    private JButton btnOk;
    private JButton btnCancel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
