/*
 * Created by JFormDesigner on Sun Jul 06 13:16:43 MDT 2014
 */

package scout.clientPnls;

import guiUtil.SelectionBorder;
import scout.clientPnls.configPnls.PnlAdvancements;
import scout.clientPnls.configPnls.PnlConfigSplash;
import scout.clientPnls.configPnls.PnlMeritBadges;
import scout.clientPnls.configPnls.PnlOtherAwards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class PnlBadgeConf extends JPanel implements PnlGui {
    public static final int ADVANCEMENT = 0;
    public static final int MERIT_BADGE = 1;
    public static final int OTHER = 2;

    private JPanel currentPnl;
    private int currentSelected;

    public PnlBadgeConf() {
        initComponents();
        ((SelectionBorder)getBorder()).cutSelectedArea(120, 220);

        init();
    }

    private void init() {
        enableComponents(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        currentPnl = pnlSplash;
    }

    private void enableComponents(boolean enable) {
        btnImport.setVisible(enable);
        btnExport.setVisible(enable);
        btnSave.setVisible(enable);
        btnNew.setVisible(enable);
        btnUpdate.setVisible(enable);
        btnDelete.setVisible(enable);
    }

    @Override
    public void resetPanel() {
        enableComponents(false);
        updateConfigPnl(pnlSplash);
    }

    private void btnAdvancementsMouseClicked() {
        enableComponents(true);

        btnSave.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        currentSelected = ADVANCEMENT;

        updateConfigPnl(new PnlAdvancements(this));

        revalidate();
        repaint();
    }

    private void btnMeritBadgesMouseClicked() {
        enableComponents(true);

        btnSave.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        currentSelected = MERIT_BADGE;

        updateConfigPnl(new PnlMeritBadges(this));

        revalidate();
        repaint();
    }

    private void updateConfigPnl(JPanel newPanel) {
        remove(currentPnl);

        add(newPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 5), 0, 0));

        currentPnl = newPanel;
    }

    private void btnNewMouseClicked() {
        switch (currentSelected) {
            case ADVANCEMENT:
                ((PnlAdvancements)currentPnl).createNew();
                break;
            case MERIT_BADGE:
                ((PnlMeritBadges)currentPnl).createNew();
                break;
            case OTHER:
                ((PnlOtherAwards)currentPnl).createNew();
                break;
        }
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    private void btnSaveMouseClicked() {
        switch (currentSelected) {
            case ADVANCEMENT:
                ((PnlAdvancements)currentPnl).save();
                break;
            case MERIT_BADGE:
                ((PnlMeritBadges)currentPnl).save();
                break;
            case OTHER:
                ((PnlOtherAwards)currentPnl).save();
                break;
        }
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    private void btnDeleteMouseClicked() {
        switch (currentSelected) {
            case ADVANCEMENT:
                ((PnlAdvancements)currentPnl).delete();
                break;
            case MERIT_BADGE:
                ((PnlMeritBadges)currentPnl).delete();
                break;
            case OTHER:
        }
    }

    private void btnUpdateMouseClicked() {
        switch (currentSelected) {
            case ADVANCEMENT:
                ((PnlAdvancements)currentPnl).update();
                break;
            case MERIT_BADGE:
                ((PnlMeritBadges)currentPnl).update();
                break;
            case OTHER:
                ((PnlOtherAwards)currentPnl).update();
                break;
        }
    }

    private void btnExportMouseClicked() {
        switch (currentSelected) {
            case ADVANCEMENT:
                ((PnlAdvancements)currentPnl).export();
                break;
            case MERIT_BADGE:
                ((PnlMeritBadges)currentPnl).export();
                break;
            case OTHER:
                ((PnlOtherAwards)currentPnl).export();
                break;
        }
    }

    private void btnImportMouseClicked() {
        switch (currentSelected) {
            case ADVANCEMENT:
                ((PnlAdvancements)currentPnl).importData();
                break;
            case MERIT_BADGE:
                ((PnlMeritBadges)currentPnl).importData();
                break;
            case OTHER:
                ((PnlOtherAwards)currentPnl).importData();
                break;
        }
    }

    private void btnOtherMouseClicked() {
        enableComponents(true);

        btnSave.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        currentSelected = OTHER;

        updateConfigPnl(new PnlOtherAwards(this));

        revalidate();
        repaint();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel1 = new JPanel();
        JButton btnAdvancements = new JButton();
        JButton btnMeritBadges = new JButton();
        JButton btnOther = new JButton();
        btnImport = new JButton();
        btnExport = new JButton();
        btnNew = new JButton();
        btnUpdate = new JButton();
        btnSave = new JButton();
        btnDelete = new JButton();
        pnlSplash = new PnlConfigSplash();

        //======== this ========
        setMinimumSize(new Dimension(1100, 800));
        setPreferredSize(new Dimension(1100, 800));
        setBorder(new SelectionBorder());
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {25, 240, 20, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {11, 0, 54, 20, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {50, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- btnAdvancements ----
            btnAdvancements.setText("Advancements");
            btnAdvancements.setBackground(new Color(51, 102, 153));
            btnAdvancements.setForeground(Color.white);
            btnAdvancements.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnAdvancements.setFocusPainted(false);
            btnAdvancements.setName("btnAdvancements");
            btnAdvancements.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnAdvancementsMouseClicked();
                }
            });
            panel1.add(btnAdvancements, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnMeritBadges ----
            btnMeritBadges.setText("Merit Badges");
            btnMeritBadges.setBackground(new Color(51, 102, 153));
            btnMeritBadges.setForeground(Color.white);
            btnMeritBadges.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnMeritBadges.setFocusPainted(false);
            btnMeritBadges.setName("btnMeritBadges");
            btnMeritBadges.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnMeritBadgesMouseClicked();
                }
            });
            panel1.add(btnMeritBadges, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnOther ----
            btnOther.setText("Other Awards");
            btnOther.setBackground(new Color(51, 102, 153));
            btnOther.setForeground(Color.white);
            btnOther.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnOther.setFocusPainted(false);
            btnOther.setName("btnOther");
            btnOther.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnOtherMouseClicked();
                }
            });
            panel1.add(btnOther, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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
            panel1.add(btnImport, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
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
            panel1.add(btnExport, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
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
            panel1.add(btnNew, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
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
            panel1.add(btnUpdate, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0,
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
            panel1.add(btnSave, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0,
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
            panel1.add(btnDelete, new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- pnlSplash ----
        pnlSplash.setName("pnlSplash");
        add(pnlSplash, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
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
    private PnlConfigSplash pnlSplash;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
