/*
 * Created by JFormDesigner on Mon Jun 30 21:50:13 MDT 2014
 */

package scout.clientPnls;

import scout.GuiManager;
import scout.SignIn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class PnlHome extends JPanel implements GuiManager {
    private SignIn parentFrame;

    public PnlHome() {
        initComponents();
    }

    public PnlHome(SignIn parentFrame) {
        this();
        this.parentFrame = parentFrame;
    }

    private void btnSettingsMouseEntered() {
        btnSettings.setIcon(new ImageIcon(getClass().getResource("/images/settings_selected90.png")));
    }

    private void btnSettingsMouseExited() {
        btnSettings.setIcon(new ImageIcon(getClass().getResource("/images/settings90.png")));
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
        panel1 = new JPanel();
        btnSettings = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(1200, 800));
        setPreferredSize(new Dimension(1200, 800));
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        ((GridBagLayout) getLayout()).columnWidths = new int[]{105, 0, 0};
        ((GridBagLayout) getLayout()).rowHeights = new int[]{0, 0, 0, 0};
        ((GridBagLayout) getLayout()).columnWeights = new double[]{0.0, 0.0, 1.0E-4};
        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.0, 0.0, 1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setOpaque(false);
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout) panel1.getLayout()).columnWidths = new int[]{0, 0};
            ((GridBagLayout) panel1.getLayout()).rowHeights = new int[]{0, 0, 0, 0, 0, 0};
            ((GridBagLayout) panel1.getLayout()).columnWeights = new double[]{0.0, 1.0E-4};
            ((GridBagLayout) panel1.getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

            //---- btnSettings ----
            btnSettings.setIcon(new ImageIcon(getClass().getResource("/images/settings90.png")));
            btnSettings.setFocusPainted(false);
            btnSettings.setBackground(Color.white);
            btnSettings.setSelectedIcon(new ImageIcon(getClass().getResource("/images/settings_filled100.png")));
            btnSettings.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btnSettingsMouseEntered();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btnSettingsMouseExited();
                }
            });
            panel1.add(btnSettings, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 0, 1, 3, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JButton btnSettings;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
