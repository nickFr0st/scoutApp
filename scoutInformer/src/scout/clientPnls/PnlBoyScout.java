/*
 * Created by JFormDesigner on Mon Oct 27 20:10:28 MDT 2014
 */

package scout.clientPnls;

import guiUtil.SelectionBorder;

import javax.swing.*;
import java.awt.*;

/**
 * @author User #2
 */
public class PnlBoyScout extends JPanel implements PnlGui {
    public PnlBoyScout() {
        initComponents();
        ((SelectionBorder)getBorder()).cutSelectedArea(235, 335);
    }

    @Override
    public void resetPanel() {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents

        //======== this ========
        setMaximumSize(new Dimension(1100, 800));
        setMinimumSize(new Dimension(1100, 800));
        setPreferredSize(new Dimension(1100, 800));
        setBackground(Color.white);
        setBorder(new SelectionBorder());
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
