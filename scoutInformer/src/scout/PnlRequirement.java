/*
 * Created by JFormDesigner on Sat Jun 07 18:04:30 MDT 2014
 */

package scout;

import javax.swing.*;
import java.awt.*;

/**
 * @author User #2
 */
public class PnlRequirement extends JPanel {
    public PnlRequirement(String reqName, String reqDescription) {
        initComponents();

        chkReq1.setText(reqName);
        lblRequirement1Discription = new JLabel("<HTML>" + reqDescription + "<HTML>");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        chkReq1 = new JCheckBox();
        lblRequirement1Discription = new JLabel("<html>Present yourself to your leader, properly dressed, <BR>before going on an overnight camping trip. Show the camping gear you will use. <BR>Show the right way to pack and carry it.</html>");

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout) getLayout()).columnWidths = new int[]{25, 0, 0};
        ((GridBagLayout) getLayout()).rowHeights = new int[]{0, 0, 0};
        ((GridBagLayout) getLayout()).columnWeights = new double[]{0.0, 1.0, 1.0E-4};
        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.0, 1.0, 1.0E-4};

        //---- chkReq1 ----
        chkReq1.setText("Requirement 1:");
        chkReq1.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(chkReq1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- lblRequirement1Discription ----
        lblRequirement1Discription.setFont(new Font("Tahoma", Font.PLAIN, 13));
        add(lblRequirement1Discription, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JCheckBox chkReq1;
    private JLabel lblRequirement1Discription;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
