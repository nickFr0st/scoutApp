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

        chkReq.setText(reqName);
        lblReqDiscription.setText("<HTML>" + reqDescription + "<HTML>");
    }

    public JCheckBox getChkReq() {
        return chkReq;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        chkReq = new JCheckBox();
        lblReqDiscription = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {25, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //---- chkReq ----
        chkReq.setText("Requirement 1:");
        chkReq.setFont(new Font("Tahoma", Font.BOLD, 14));
        chkReq.setBackground(Color.white);
        add(chkReq, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- lblReqDiscription ----
        lblReqDiscription.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblReqDiscription.setBackground(Color.white);
        add(lblReqDiscription, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JCheckBox chkReq;
    private JLabel lblReqDiscription;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
