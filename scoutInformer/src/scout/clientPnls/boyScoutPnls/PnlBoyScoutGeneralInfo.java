/*
 * Created by JFormDesigner on Mon Oct 27 22:51:15 MDT 2014
 */

package scout.clientPnls.boyScoutPnls;

import javax.swing.*;
import java.awt.*;

/**
 * @author User #2
 */
public class PnlBoyScoutGeneralInfo extends JPanel {
    public PnlBoyScoutGeneralInfo() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();

        //======== this ========
        setMaximumSize(new Dimension(775, 654));
        setMinimumSize(new Dimension(775, 654));
        setPreferredSize(new Dimension(775, 654));
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("text");
        label1.setName("label1");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
