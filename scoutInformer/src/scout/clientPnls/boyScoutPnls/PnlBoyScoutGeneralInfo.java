/*
 * Created by JFormDesigner on Mon Oct 27 22:51:15 MDT 2014
 */

package scout.clientPnls.boyScoutPnls;

import guiUtil.JTextFieldDefaultText;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import scout.dbObjects.Advancement;
import util.LogicAdvancement;
import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.util.List;
import java.util.Properties;

/**
 * @author User #2
 */
public class PnlBoyScoutGeneralInfo extends JPanel {
    public PnlBoyScoutGeneralInfo() {
        initComponents();

        init();
    }

    private void init() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateFormatter());
        pnlGeneralInfo.add(datePicker, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        List<Advancement> advancementList = LogicAdvancement.findAllAdvancements();
        if (!Util.isEmpty(advancementList)) {
            for(Advancement advancement : advancementList) {
                cboCurrentRank.addItem(advancement.getName());
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pnlSelectedImage = new JPanel();
        lblImage = new JLabel();
        pnlGeneralInfo = new JPanel();
        lblBadgeName = new JLabel();
        txtBadgeName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblBirthDate = new JLabel();
        lblAge = new JLabel();
        lblAgeValue = new JLabel();
        lblCurrentRank = new JLabel();
        cboCurrentRank = new JComboBox();

        //======== this ========
        setMaximumSize(new Dimension(775, 654));
        setMinimumSize(new Dimension(775, 654));
        setPreferredSize(new Dimension(775, 654));
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {158, 529, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {181, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

        //======== pnlSelectedImage ========
        {
            pnlSelectedImage.setBackground(new Color(204, 204, 204));
            pnlSelectedImage.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            pnlSelectedImage.setMaximumSize(new Dimension(132, 132));
            pnlSelectedImage.setName("pnlSelectedImage");
            pnlSelectedImage.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlSelectedImage.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)pnlSelectedImage.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)pnlSelectedImage.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)pnlSelectedImage.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //---- lblImage ----
            lblImage.setIcon(new ImageIcon(getClass().getResource("/images/no_image.png")));
            lblImage.setName("lblImage");
            pnlSelectedImage.add(lblImage, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(pnlSelectedImage, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== pnlGeneralInfo ========
        {
            pnlGeneralInfo.setBackground(Color.white);
            pnlGeneralInfo.setName("pnlGeneralInfo");
            pnlGeneralInfo.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWidths = new int[] {0, 297, 37, 95, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowHeights = new int[] {0, 0, 0, 40, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- lblBadgeName ----
            lblBadgeName.setText("Name:");
            lblBadgeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblBadgeName.setForeground(new Color(51, 102, 153));
            lblBadgeName.setName("lblBadgeName");
            pnlGeneralInfo.add(lblBadgeName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 8, 5), 0, 0));

            //---- txtBadgeName ----
            txtBadgeName.setPreferredSize(new Dimension(14, 40));
            txtBadgeName.setMinimumSize(new Dimension(14, 40));
            txtBadgeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtBadgeName.setDefaultText("Scout Name");
            txtBadgeName.setName("txtBadgeName");
            pnlGeneralInfo.add(txtBadgeName, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 8, 0), 0, 0));

            //---- lblNameError ----
            lblNameError.setText("* Error Message");
            lblNameError.setForeground(Color.red);
            lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblNameError.setVisible(false);
            lblNameError.setName("lblNameError");
            pnlGeneralInfo.add(lblNameError, new GridBagConstraints(0, 1, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 8, 10), 0, 0));

            //---- lblBirthDate ----
            lblBirthDate.setText("Birth Date:");
            lblBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblBirthDate.setForeground(new Color(51, 102, 153));
            lblBirthDate.setName("lblBirthDate");
            pnlGeneralInfo.add(lblBirthDate, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 8, 5), 0, 0));

            //---- lblAge ----
            lblAge.setText("Age:");
            lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblAge.setForeground(new Color(51, 102, 153));
            lblAge.setName("lblAge");
            pnlGeneralInfo.add(lblAge, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 15, 8, 5), 0, 0));

            //---- lblAgeValue ----
            lblAgeValue.setText("11");
            lblAgeValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblAgeValue.setForeground(new Color(51, 102, 153));
            lblAgeValue.setName("lblAgeValue");
            pnlGeneralInfo.add(lblAgeValue, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 8, 0), 0, 0));

            //---- lblCurrentRank ----
            lblCurrentRank.setText("Current Rank:");
            lblCurrentRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblCurrentRank.setForeground(new Color(51, 102, 153));
            lblCurrentRank.setName("lblCurrentRank");
            pnlGeneralInfo.add(lblCurrentRank, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 0, 5), 0, 0));

            //---- cboCurrentRank ----
            cboCurrentRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
            cboCurrentRank.setBackground(Color.white);
            cboCurrentRank.setName("cboCurrentRank");
            pnlGeneralInfo.add(cboCurrentRank, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 0, 0, 5), 0, 0));
        }
        add(pnlGeneralInfo, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 0, 5, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel pnlSelectedImage;
    private JLabel lblImage;
    private JPanel pnlGeneralInfo;
    private JLabel lblBadgeName;
    private JTextFieldDefaultText txtBadgeName;
    private JLabel lblNameError;
    private JLabel lblBirthDate;
    private JLabel lblAge;
    private JLabel lblAgeValue;
    private JLabel lblCurrentRank;
    private JComboBox cboCurrentRank;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
