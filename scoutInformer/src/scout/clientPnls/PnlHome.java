/*
 * Created by JFormDesigner on Mon Jun 30 21:50:13 MDT 2014
 */

package scout.clientPnls;

import guiUtil.ButtonSideMenu;
import guiUtil.JButtonImageChange;
import scout.GuiManager;
import scout.SignIn;
import util.DBConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class PnlHome extends JPanel implements GuiManager {
    private JPanel currentPnl;
    private SignIn parentFrame;
    private PnlDefaultSplash defaultSplash;
    private PnlSetupWizard setupWizard;
    private DBConnector dbConnector;

    {
        defaultSplash = new PnlDefaultSplash();
        setupWizard = new PnlSetupWizard();
        dbConnector = new DBConnector();
    }

    public PnlHome() {
        initComponents();
    }

    public PnlHome(SignIn parentFrame) {
        this();
        this.parentFrame = parentFrame;

        currentPnl = defaultSplash;
        pnlBase.add(currentPnl);
    }

    @Override
    public void nextStep() {
        parentFrame.nextStep();
    }

    @Override
    public void previousStep() {
        parentFrame.previousStep();
    }

    private void btnSignOutMouseClicked() {
        cleanup();
        previousStep();
    }

    private void cleanup() {
        btnSettings.setDefault();
        btnSignOut.setDefault();
        clearSelected();
        changePanel(defaultSplash);
    }

    private void btnSettingsMouseClicked() {
        // todo: un-comment when wizard is working

//        if (!dbConnector.checkForDBConnection()) {
            changePanel(setupWizard);
//        } else {
//            changePanel(pnlSettings);
//            pnlSettings.activatePnl();
//        }


    }

    private void clearSelected() {
        btnSettings.setDefault();
    }

    private void changePanel(JPanel newPanel) {
        if (currentPnl.equals(newPanel)) {
            return;
        }

        pnlBase.remove(currentPnl);
        currentPnl = newPanel;
        pnlBase.add(currentPnl);
        pnlBase.revalidate();
        pnlBase.repaint();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pnlOptionsMenu = new JPanel();
        btnSettings = new ButtonSideMenu();
        btnSignOut = new JButtonImageChange();
        pnlBase = new JPanel();

        //======== this ========
        setMinimumSize(new Dimension(1200, 800));
        setPreferredSize(new Dimension(1200, 800));
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {100, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

        //======== pnlOptionsMenu ========
        {
            pnlOptionsMenu.setBackground(new Color(190, 174, 150));
            pnlOptionsMenu.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlOptionsMenu.getLayout()).columnWidths = new int[] {100, 0};
            ((GridBagLayout)pnlOptionsMenu.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
            ((GridBagLayout)pnlOptionsMenu.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)pnlOptionsMenu.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

            //---- btnSettings ----
            btnSettings.setDefaultImage(new ImageIcon(getClass().getResource("/images/settings90.png")));
            btnSettings.setSelectedImage(new ImageIcon(getClass().getResource("/images/settings_selected90.png")));
            btnSettings.setToolTipText("Settings");
            btnSettings.setBackground(Color.white);
            btnSettings.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnSettingsMouseClicked();
                }
            });
            pnlOptionsMenu.add(btnSettings, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- btnSignOut ----
            btnSignOut.setIcon(new ImageIcon(getClass().getResource("/images/sign_out90.png")));
            btnSignOut.setBorderPainted(false);
            btnSignOut.setFocusPainted(false);
            btnSignOut.setOpaque(false);
            btnSignOut.setDefaultImage(new ImageIcon(getClass().getResource("/images/sign_out90.png")));
            btnSignOut.setSelectedImage(new ImageIcon(getClass().getResource("/images/sign_out_selected90.png")));
            btnSignOut.setBackground(Color.white);
            btnSignOut.setToolTipText("Sign out");
            btnSignOut.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnSignOutMouseClicked();
                }
            });
            pnlOptionsMenu.add(btnSignOut, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        }
        add(pnlOptionsMenu, new GridBagConstraints(0, 0, 1, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== pnlBase ========
        {
            pnlBase.setBackground(Color.white);
            pnlBase.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlBase.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)pnlBase.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)pnlBase.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)pnlBase.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};
        }
        add(pnlBase, new GridBagConstraints(1, 0, 1, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel pnlOptionsMenu;
    private ButtonSideMenu btnSettings;
    private JButtonImageChange btnSignOut;
    private JPanel pnlBase;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
