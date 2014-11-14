/*
 * Created by JFormDesigner on Mon Oct 27 22:51:15 MDT 2014
 */

package scout.clientPnls.boyScoutPnls;

import guiUtil.JTextFieldDefaultText;
import scout.dbObjects.Advancement;
import util.LogicAdvancement;
import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author User #2
 */
public class PnlBoyScoutGeneralInfo extends JPanel {

    private DefaultTableModel tableModelContacts;
    private DefaultTableModel tableModelParents;

    public PnlBoyScoutGeneralInfo() {
        initComponents();

        init();
    }

    private void init() {
        List<Advancement> advancementList = LogicAdvancement.findAllAdvancements();
        if (!Util.isEmpty(advancementList)) {
            for(Advancement advancement : advancementList) {
                cboCurrentRank.addItem(advancement.getName());
            }
        }
    }

    private void createUIComponents() {
        tableModelContacts = new DefaultTableModel(new Object[] {"Type", "Name", "Relation", "Data"}, 0);

        tblContacts = new JTable();
        tblContacts.setBackground(Color.WHITE);

        JTableHeader headerContacts = tblContacts.getTableHeader();
        headerContacts.setBackground(new Color(51, 102, 153));
        headerContacts.setForeground(Color.WHITE);
        headerContacts.setFont(new Font("Tahoma", Font.PLAIN, 14));

        tblContacts.setModel(tableModelContacts);
        tblContacts.setSurrendersFocusOnKeystroke(true);
    }

    private void btnDeleteCounselorMouseClicked() {
        // TODO add your code here
    }

    private void btnNewContactMouseClicked() {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        createUIComponents();

        pnlSelectedImage = new JPanel();
        lblImage = new JLabel();
        pnlGeneralInfo = new JPanel();
        lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblBirthDate = new JLabel();
        txtBirthDate = new JTextFieldDefaultText();
        lblAge = new JLabel();
        lblAgeValue = new JLabel();
        lblBirthDateError = new JLabel();
        lblCurrentRank = new JLabel();
        cboCurrentRank = new JComboBox();
        vSpacer1 = new JPanel(null);
        panel1 = new JPanel();
        panel2 = new JPanel();
        label2 = new JLabel();
        btnNewContact = new JLabel();
        btnDeleteContact = new JLabel();
        scrollPane1 = new JScrollPane();

        //======== this ========
        setMaximumSize(new Dimension(770, 654));
        setMinimumSize(new Dimension(770, 654));
        setPreferredSize(new Dimension(770, 654));
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {158, 585, 40, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {181, 15, 432, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

        //======== pnlSelectedImage ========
        {
            pnlSelectedImage.setBackground(new Color(204, 204, 204));
            pnlSelectedImage.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            pnlSelectedImage.setMaximumSize(new Dimension(132, 132));
            pnlSelectedImage.setName("pnlSelectedImage");
            pnlSelectedImage.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlSelectedImage.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)pnlSelectedImage.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)pnlSelectedImage.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)pnlSelectedImage.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //---- lblImage ----
            lblImage.setIcon(new ImageIcon(getClass().getResource("/images/no_image.png")));
            lblImage.setName("lblImage");
            pnlSelectedImage.add(lblImage, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(pnlSelectedImage, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== pnlGeneralInfo ========
        {
            pnlGeneralInfo.setBackground(Color.white);
            pnlGeneralInfo.setName("pnlGeneralInfo");
            pnlGeneralInfo.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWidths = new int[] {0, 335, 85, 62, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 35, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- lblName ----
            lblName.setText("Name:");
            lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblName.setForeground(new Color(51, 102, 153));
            lblName.setName("lblName");
            pnlGeneralInfo.add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- txtName ----
            txtName.setPreferredSize(new Dimension(14, 40));
            txtName.setMinimumSize(new Dimension(14, 40));
            txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtName.setDefaultText("Scout Name");
            txtName.setName("txtName");
            pnlGeneralInfo.add(txtName, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 10), 0, 0));

            //---- lblNameError ----
            lblNameError.setText("* Error Message");
            lblNameError.setForeground(Color.red);
            lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblNameError.setVisible(false);
            lblNameError.setName("lblNameError");
            pnlGeneralInfo.add(lblNameError, new GridBagConstraints(0, 1, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- lblBirthDate ----
            lblBirthDate.setText("Birth Date:");
            lblBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblBirthDate.setForeground(new Color(51, 102, 153));
            lblBirthDate.setName("lblBirthDate");
            pnlGeneralInfo.add(lblBirthDate, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- txtBirthDate ----
            txtBirthDate.setPreferredSize(new Dimension(14, 40));
            txtBirthDate.setMinimumSize(new Dimension(14, 40));
            txtBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtBirthDate.setDefaultText("MM/DD/YYYY");
            txtBirthDate.setName("txtBirthDate");
            pnlGeneralInfo.add(txtBirthDate, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblAge ----
            lblAge.setText("Age:");
            lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblAge.setForeground(new Color(51, 102, 153));
            lblAge.setName("lblAge");
            pnlGeneralInfo.add(lblAge, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblAgeValue ----
            lblAgeValue.setText("11");
            lblAgeValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblAgeValue.setForeground(new Color(51, 102, 153));
            lblAgeValue.setName("lblAgeValue");
            pnlGeneralInfo.add(lblAgeValue, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblBirthDateError ----
            lblBirthDateError.setText("* Error Message");
            lblBirthDateError.setForeground(Color.red);
            lblBirthDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblBirthDateError.setVisible(false);
            lblBirthDateError.setName("lblBirthDateError");
            pnlGeneralInfo.add(lblBirthDateError, new GridBagConstraints(0, 3, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- lblCurrentRank ----
            lblCurrentRank.setText("Current Rank:");
            lblCurrentRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblCurrentRank.setForeground(new Color(51, 102, 153));
            lblCurrentRank.setName("lblCurrentRank");
            pnlGeneralInfo.add(lblCurrentRank, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 0, 5), 0, 0));

            //---- cboCurrentRank ----
            cboCurrentRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
            cboCurrentRank.setBackground(Color.white);
            cboCurrentRank.setName("cboCurrentRank");
            pnlGeneralInfo.add(cboCurrentRank, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 0, 0, 5), 0, 0));
        }
        add(pnlGeneralInfo, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- vSpacer1 ----
        vSpacer1.setOpaque(false);
        vSpacer1.setName("vSpacer1");
        add(vSpacer1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {353, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 221, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //======== panel2 ========
            {
                panel2.setBackground(Color.white);
                panel2.setName("panel2");
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- label2 ----
                label2.setText("Contact Information");
                label2.setFont(new Font("Vijaya", Font.PLAIN, 24));
                label2.setForeground(new Color(51, 102, 153));
                label2.setName("label2");
                panel2.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 5, 0, 5), 0, 0));

                //---- btnNewContact ----
                btnNewContact.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
                btnNewContact.setPreferredSize(new Dimension(24, 24));
                btnNewContact.setMinimumSize(new Dimension(24, 24));
                btnNewContact.setMaximumSize(new Dimension(24, 24));
                btnNewContact.setHorizontalAlignment(SwingConstants.CENTER);
                btnNewContact.setBackground(Color.white);
                btnNewContact.setOpaque(true);
                btnNewContact.setToolTipText("Add New Requirement");
                btnNewContact.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnNewContact.setName("btnNewContact");
                btnNewContact.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnNewContactMouseClicked();
                    }
                });
                panel2.add(btnNewContact, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnDeleteContact ----
                btnDeleteContact.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
                btnDeleteContact.setPreferredSize(new Dimension(24, 24));
                btnDeleteContact.setMinimumSize(new Dimension(24, 24));
                btnDeleteContact.setMaximumSize(new Dimension(24, 24));
                btnDeleteContact.setHorizontalAlignment(SwingConstants.CENTER);
                btnDeleteContact.setBackground(Color.white);
                btnDeleteContact.setOpaque(true);
                btnDeleteContact.setToolTipText("Delete selected requirement");
                btnDeleteContact.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnDeleteContact.setName("btnDeleteContact");
                btnDeleteContact.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnDeleteCounselorMouseClicked();
                    }
                });
                panel2.add(btnDeleteContact, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== scrollPane1 ========
            {
                scrollPane1.setName("scrollPane1");

                //---- tblContacts ----
                tblContacts.setFont(new Font("Tahoma", Font.PLAIN, 14));
                tblContacts.setBackground(Color.white);
                tblContacts.setFillsViewportHeight(true);
                tblContacts.setForeground(Color.black);
                tblContacts.setName("tblContacts");
                scrollPane1.setViewportView(tblContacts);
            }
            panel1.add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel pnlSelectedImage;
    private JLabel lblImage;
    private JPanel pnlGeneralInfo;
    private JLabel lblName;
    private JTextFieldDefaultText txtName;
    private JLabel lblNameError;
    private JLabel lblBirthDate;
    private JTextFieldDefaultText txtBirthDate;
    private JLabel lblAge;
    private JLabel lblAgeValue;
    private JLabel lblBirthDateError;
    private JLabel lblCurrentRank;
    private JComboBox cboCurrentRank;
    private JPanel vSpacer1;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel label2;
    private JLabel btnNewContact;
    private JLabel btnDeleteContact;
    private JScrollPane scrollPane1;
    private JTable tblContacts;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
