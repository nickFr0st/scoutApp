/*
 * Created by JFormDesigner on Mon Oct 27 20:10:28 MDT 2014
 */

package scout.clientPnls;

import guiUtil.JTextFieldDefaultText;
import guiUtil.SelectionBorder;
import scout.clientPnls.boyScoutPnls.PnlBoyScoutGeneralInfo;
import scout.dbObjects.Scout;
import util.LogicScout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author User #2
 */
public class PnlBoyScout extends JPanel implements PnlGui {
    private final String TRACKING = "Tracking";
    private final String GENERAL_INFORMATION = "General Information";
    private final String DETAILS = "Details";

    private JPanel currentPnl;
    private Scout scout;

    private PnlBoyScoutGeneralInfo pnlBoyScoutGeneralInfo = new PnlBoyScoutGeneralInfo();

    public PnlBoyScout() {
        initComponents();
        ((SelectionBorder)getBorder()).cutSelectedArea(235, 335);

        init();
        clearData();
    }

    private void init() {
        cboScoutTab.addItem(TRACKING);
        cboScoutTab.addItem(GENERAL_INFORMATION);
        cboScoutTab.addItem(DETAILS);
        cboScoutTab.setSelectedIndex(0);
    }

    private void clearData() {
        currentPnl = pnlSplash;

        btnSave.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        scout = null;

        cboScoutTab.setEnabled(false);

        populateScoutNameList();
        revalidate();
    }

    private void clearErrors() {
        if (currentPnl instanceof PnlBoyScoutGeneralInfo) {
            ((PnlBoyScoutGeneralInfo) currentPnl).clearErrors();
        }
    }

    @Override
    public void resetPanel() {
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        cboScoutTab.setEnabled(false);
        updateTabbedPnl(pnlSplash);
    }

    private void updateTabbedPnl(JPanel newPanel) {
        pnlParentTab.remove(currentPnl);

        pnlParentTab.add(newPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));

        currentPnl = newPanel;
        pnlParentTab.revalidate();
    }

    private void populateScoutNameList() {
        java.util.List<String> advancements = LogicScout.getNameList();

        if (advancements != null) {
            listScoutNames.setListData(advancements.toArray());
        }

        listScoutNames.revalidate();
    }

    private void btnImportMouseClicked() {
        // TODO add your code here
    }

    private void btnExportMouseClicked() {
        // TODO add your code here
    }

    private void btnNewMouseClicked() {
        pnlBoyScoutGeneralInfo.setScout(new Scout());
        cboScoutTab.setEnabled(true);
        btnDelete.setVisible(false);
        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        cboScoutTab.setSelectedItem(GENERAL_INFORMATION);
    }

    private void btnUpdateMouseClicked() {
        // TODO add your code here
    }

    private void btnSaveMouseClicked() {
        // this will need to go through each of the panels
        // start with general information
        // then details
        // then tracing

        // validate each panel
        if (!pnlBoyScoutGeneralInfo.validateInfo()) {
            return;
        }

        // if it is good then save each panel
        pnlBoyScoutGeneralInfo.save();
        reloadDataFromSave(pnlBoyScoutGeneralInfo.getScout());
    }

    private void reloadDataFromSave(Scout newScout) {
        if (newScout == null) {
            return;
        }

        clearData();

        for (int i = 0; i < listScoutNames.getModel().getSize(); ++i) {
            if (listScoutNames.getModel().getElementAt(i).toString().equals(newScout.getName())) {
                listScoutNames.setSelectedIndex(i);
                break;
            }
        }

        listScoutNamesMouseClicked();
    }

    private void btnDeleteMouseClicked() {
        // we will need to work backwards through each of the panels
        // start with tracing
        // then details
        // then general information
        if (listScoutNames.getSelectedValue() == null) {
            return;
        }

        pnlBoyScoutGeneralInfo.delete();
        clearData();
    }

    private void txtSearchNameKeyReleased() {
        java.util.List<String> scoutNameList = LogicScout.getNameList();
        if (scoutNameList == null) {
            return;
        }

        if (txtSearchName.isMessageDefault()) {
            listScoutNames.setListData(scoutNameList.toArray());
            listScoutNames.revalidate();
            return;
        }

        java.util.List<String> filteredList = new ArrayList<String>();
        for (String scoutName : scoutNameList) {
            if (scoutName.toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(scoutName);
            }
        }

        listScoutNames.setListData(filteredList.toArray());
        listScoutNames.revalidate();
    }

    private void listScoutNamesMouseClicked() {
        if (listScoutNames.getSelectedValue() == null) {
            return;
        }

        clearErrors();

        scout = LogicScout.findByName(listScoutNames.getSelectedValue().toString());
        if (scout == null) {
            return;
        }

        btnSave.setVisible(false);
        btnDelete.setVisible(true);
        btnUpdate.setVisible(true);

        cboScoutTab.setEnabled(true);

        pnlBoyScoutGeneralInfo.setScout(scout);

        cboScoutTabActionPerformed();

        revalidate();
        repaint();

        currentPnl.repaint();
    }

    private void listScoutNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listScoutNamesMouseClicked();
        }
    }

    private void cboScoutTabActionPerformed() {
        switch (cboScoutTab.getSelectedIndex()) {
            case 0:
                break;
            case 1:
                pnlBoyScoutGeneralInfo.init();
                updateTabbedPnl(pnlBoyScoutGeneralInfo);
                break;
            default:
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel1 = new JPanel();
        JButton btnPrintProgessReport = new JButton();
        btnImport = new JButton();
        btnExport = new JButton();
        btnNew = new JButton();
        btnUpdate = new JButton();
        btnSave = new JButton();
        btnDelete = new JButton();
        lblListName = new JLabel();
        cboScoutTab = new JComboBox<String>();
        pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        pnlParentTab = new JPanel();
        pnlSplash = new JPanel();
        JLabel lblWelcome = new JLabel();
        JScrollPane scrollPane3 = new JScrollPane();
        listScoutNames = new JList<Object>();
        JPanel hSpacer2 = new JPanel(null);

        //======== this ========
        setMaximumSize(new Dimension(1100, 800));
        setMinimumSize(new Dimension(1100, 800));
        setPreferredSize(new Dimension(1100, 800));
        setBackground(Color.white);
        setBorder(new SelectionBorder());
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {25, 232, 33, 170, 0, 20, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {11, 0, 45, 0, 0, 20, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {50, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- btnPrintProgessReport ----
            btnPrintProgessReport.setText("Print Progress Report");
            btnPrintProgessReport.setBackground(new Color(51, 102, 153));
            btnPrintProgessReport.setForeground(Color.white);
            btnPrintProgessReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnPrintProgessReport.setFocusPainted(false);
            btnPrintProgessReport.setName("btnPrintProgessReport");
            panel1.add(btnPrintProgessReport, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnImport ----
            btnImport.setText("Import");
            btnImport.setBackground(new Color(32, 154, 26));
            btnImport.setForeground(Color.white);
            btnImport.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnImport.setFocusPainted(false);
            btnImport.setName("btnImport");
            btnImport.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnImportMouseClicked();
                }
            });
            panel1.add(btnImport, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnExport ----
            btnExport.setText("Export");
            btnExport.setBackground(new Color(32, 154, 26));
            btnExport.setForeground(Color.white);
            btnExport.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnExport.setFocusPainted(false);
            btnExport.setName("btnExport");
            btnExport.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnExportMouseClicked();
                }
            });
            panel1.add(btnExport, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnNew ----
            btnNew.setText("New");
            btnNew.setBackground(new Color(51, 156, 229));
            btnNew.setForeground(Color.white);
            btnNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnNew.setFocusPainted(false);
            btnNew.setName("btnNew");
            btnNew.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnNewMouseClicked();
                }
            });
            panel1.add(btnNew, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnUpdate ----
            btnUpdate.setText("Update");
            btnUpdate.setBackground(new Color(51, 102, 153));
            btnUpdate.setForeground(Color.white);
            btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnUpdate.setFocusPainted(false);
            btnUpdate.setName("btnUpdate");
            btnUpdate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnUpdateMouseClicked();
                }
            });
            panel1.add(btnUpdate, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnSave ----
            btnSave.setText("Save");
            btnSave.setBackground(new Color(51, 102, 153));
            btnSave.setForeground(Color.white);
            btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnSave.setFocusPainted(false);
            btnSave.setName("btnSave");
            btnSave.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnSaveMouseClicked();
                }
            });
            panel1.add(btnSave, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnDelete ----
            btnDelete.setText("Delete");
            btnDelete.setBackground(new Color(207, 0, 0));
            btnDelete.setForeground(Color.white);
            btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnDelete.setFocusPainted(false);
            btnDelete.setName("btnDelete");
            btnDelete.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnDeleteMouseClicked();
                }
            });
            panel1.add(btnDelete, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(1, 1, 4, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblListName ----
        lblListName.setText("Boy Scouts");
        lblListName.setVerticalAlignment(SwingConstants.BOTTOM);
        lblListName.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblListName.setForeground(new Color(51, 102, 153));
        lblListName.setName("lblListName");
        add(lblListName, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- cboScoutTab ----
        cboScoutTab.setFont(new Font("Vijaya", Font.PLAIN, 24));
        cboScoutTab.setForeground(new Color(51, 102, 153));
        cboScoutTab.setBackground(Color.white);
        cboScoutTab.setName("cboScoutTab");
        cboScoutTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cboScoutTabActionPerformed();
            }
        });
        add(cboScoutTab, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 0, 5, 5), 0, 0));

        //======== pnlSearch ========
        {
            pnlSearch.setBackground(Color.white);
            pnlSearch.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            pnlSearch.setName("pnlSearch");
            pnlSearch.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlSearch.getLayout()).columnWidths = new int[] {235, 0};
            ((GridBagLayout)pnlSearch.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)pnlSearch.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)pnlSearch.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- txtSearchName ----
            txtSearchName.setDefaultText("Search by Name");
            txtSearchName.setBorder(null);
            txtSearchName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtSearchName.setMinimumSize(new Dimension(0, 20));
            txtSearchName.setPreferredSize(new Dimension(101, 20));
            txtSearchName.setName("txtSearchName");
            txtSearchName.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    txtSearchNameKeyReleased();
                }
            });
            pnlSearch.add(txtSearchName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 0, 0), 0, 0));
        }
        add(pnlSearch, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 6), 0, 0));

        //======== pnlParentTab ========
        {
            pnlParentTab.setBackground(Color.white);
            pnlParentTab.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            pnlParentTab.setName("pnlParentTab");
            pnlParentTab.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlParentTab.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)pnlParentTab.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)pnlParentTab.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)pnlParentTab.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //======== pnlSplash ========
            {
                pnlSplash.setMaximumSize(new Dimension(775, 654));
                pnlSplash.setMinimumSize(new Dimension(775, 654));
                pnlSplash.setPreferredSize(new Dimension(775, 654));
                pnlSplash.setBackground(Color.white);
                pnlSplash.setName("pnlSplash");
                pnlSplash.setLayout(new GridBagLayout());
                ((GridBagLayout)pnlSplash.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)pnlSplash.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)pnlSplash.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)pnlSplash.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};

                //---- lblWelcome ----
                lblWelcome.setText("Welcome to the boy scout manager");
                lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
                lblWelcome.setFont(new Font("Vijaya", Font.PLAIN, 28));
                lblWelcome.setForeground(new Color(51, 102, 153));
                lblWelcome.setName("lblWelcome");
                pnlSplash.add(lblWelcome, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(20, 0, 5, 0), 0, 0));
            }
            pnlParentTab.add(pnlSplash, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));
        }
        add(pnlParentTab, new GridBagConstraints(3, 3, 2, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane3 ========
        {
            scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane3.setName("scrollPane3");

            //---- listScoutNames ----
            listScoutNames.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            listScoutNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
            listScoutNames.setName("listScoutNames");
            listScoutNames.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    listScoutNamesMouseClicked();
                }
            });
            listScoutNames.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    listScoutNamesKeyReleased(e);
                }
            });
            scrollPane3.setViewportView(listScoutNames);
        }
        add(scrollPane3, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer2 ----
        hSpacer2.setBackground(Color.white);
        hSpacer2.setName("hSpacer2");
        add(hSpacer2, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton btnImport;
    private JButton btnExport;
    private JButton btnNew;
    private JButton btnUpdate;
    private JButton btnSave;
    private JButton btnDelete;
    private JLabel lblListName;
    private JComboBox<String> cboScoutTab;
    private JPanel pnlSearch;
    private JTextFieldDefaultText txtSearchName;
    private JPanel pnlParentTab;
    private JPanel pnlSplash;
    private JList<Object> listScoutNames;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
