/*
 * Created by JFormDesigner on Mon Oct 27 20:10:28 MDT 2014
 */

package scout.clientPnls;

import guiUtil.JTextFieldDefaultText;
import guiUtil.SelectionBorder;
import scout.clientPnls.boyScoutPnls.PnlBoyScoutGeneralInfo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class PnlBoyScout extends JPanel implements PnlGui {
    JPanel currentPnl;

    public PnlBoyScout() {
        initComponents();
        ((SelectionBorder)getBorder()).cutSelectedArea(235, 335);

        currentPnl = pnlSplash;

        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
    }

    @Override
    public void resetPanel() {
        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

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

    private void btnImportMouseClicked() {
        // TODO add your code here
    }

    private void btnExportMouseClicked() {
        // TODO add your code here
    }

    private void btnNewMouseClicked() {
        // TODO add your code here
    }

    private void btnUpdateMouseClicked() {
        // TODO add your code here
    }

    private void btnSaveMouseClicked() {
        // TODO add your code here
    }

    private void btnDeleteMouseClicked() {
        // TODO add your code here
    }

    private void txtSearchNameKeyReleased() {
        // TODO add your code here
    }

    private void listScoutNamesMouseClicked() {
        // TODO add your code here
    }

    private void listScoutNamesKeyReleased(KeyEvent e) {
        // TODO add your code here
    }

    private void lblGeneralInfoMouseEntered() {
        lblGeneralInfo.setForeground(new Color(32, 154, 26));
    }

    private void lblGeneralInfoMouseExited() {
        lblGeneralInfo.setForeground(new Color(51, 102, 153));
    }

    private void lblDetailsMouseEntered() {
        lblDetails.setForeground(new Color(32, 154, 26));
    }

    private void lblDetailsMouseExited() {
        lblDetails.setForeground(new Color(51, 102, 153));
    }

    private void lblGeneralInfoMouseClicked() {
        updateTabbedPnl(new PnlBoyScoutGeneralInfo());
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
        lblGeneralInfo = new JLabel();
        JSeparator separator1 = new JSeparator();
        lblDetails = new JLabel();
        pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        pnlParentTab = new JPanel();
        pnlSplash = new JPanel();
        JLabel lblWelcome = new JLabel();
        JScrollPane scrollPane3 = new JScrollPane();
        listScoutNames = new JList();
        JPanel hSpacer2 = new JPanel(null);

        //======== this ========
        setMaximumSize(new Dimension(1100, 800));
        setMinimumSize(new Dimension(1100, 800));
        setPreferredSize(new Dimension(1100, 800));
        setBackground(Color.white);
        setBorder(new SelectionBorder());
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {25, 232, 33, 170, 10, 65, 0, 20, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {11, 0, 45, 0, 0, 20, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
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
        add(panel1, new GridBagConstraints(1, 1, 6, 1, 0.0, 0.0,
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

        //---- lblGeneralInfo ----
        lblGeneralInfo.setText("General Information");
        lblGeneralInfo.setVerticalAlignment(SwingConstants.BOTTOM);
        lblGeneralInfo.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblGeneralInfo.setForeground(new Color(51, 102, 153));
        lblGeneralInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblGeneralInfo.setName("lblGeneralInfo");
        lblGeneralInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblGeneralInfoMouseClicked();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lblGeneralInfoMouseEntered();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblGeneralInfoMouseExited();
            }
        });
        add(lblGeneralInfo, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- separator1 ----
        separator1.setOrientation(SwingConstants.VERTICAL);
        separator1.setBackground(Color.white);
        separator1.setForeground(new Color(51, 102, 153));
        separator1.setName("separator1");
        add(separator1, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(13, 2, 10, 5), 0, 0));

        //---- lblDetails ----
        lblDetails.setText("Details");
        lblDetails.setVerticalAlignment(SwingConstants.BOTTOM);
        lblDetails.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblDetails.setForeground(new Color(51, 102, 153));
        lblDetails.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblDetails.setName("lblDetails");
        lblDetails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblDetailsMouseEntered();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblDetailsMouseExited();
            }
        });
        add(lblDetails, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

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
        add(pnlParentTab, new GridBagConstraints(3, 3, 4, 2, 0.0, 0.0,
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
    private JLabel lblGeneralInfo;
    private JLabel lblDetails;
    private JPanel pnlSearch;
    private JTextFieldDefaultText txtSearchName;
    private JPanel pnlParentTab;
    private JPanel pnlSplash;
    private JList listScoutNames;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
