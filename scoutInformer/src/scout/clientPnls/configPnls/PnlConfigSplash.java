/*
 * Created by JFormDesigner on Fri Oct 17 14:03:59 MDT 2014
 */

package scout.clientPnls.configPnls;

import javax.swing.*;
import java.awt.*;

/**
 * @author User #2
 */
public class PnlConfigSplash extends JPanel implements Configuration {
    public PnlConfigSplash() {
        initComponents();
    }

    @Override
    public void onShow() {
        // do nothing
    }

    @Override
    public void onHide() {
        // do nothing
    }

    @Override
    public void createNew() {
        // do nothing
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblWelcome = new JLabel();
        lblWelcome2 = new JLabel();

        //======== this ========
        setMaximumSize(new Dimension(1000, 680));
        setMinimumSize(new Dimension(1000, 680));
        setPreferredSize(new Dimension(1000, 680));
        setBackground(Color.white);
        setOpaque(false);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0, 1.0E-4};

        //---- lblWelcome ----
        lblWelcome.setText("Welcome to the advancements and merit badge manager");
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Vijaya", Font.PLAIN, 28));
        lblWelcome.setForeground(new Color(51, 102, 153));
        lblWelcome.setName("lblWelcome");
        add(lblWelcome, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(20, 0, 5, 0), 0, 0));

        //---- lblWelcome2 ----
        lblWelcome2.setText("to start working please select one of the above options");
        lblWelcome2.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome2.setFont(new Font("Vijaya", Font.PLAIN, 28));
        lblWelcome2.setForeground(new Color(51, 102, 153));
        lblWelcome2.setName("lblWelcome2");
        add(lblWelcome2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(20, 0, 5, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblWelcome;
    private JLabel lblWelcome2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
