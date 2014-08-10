/*
 * Created by JFormDesigner on Sun Aug 10 15:02:30 MDT 2014
 */

package guiUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author User #2
 */
public class PnlSearchBar extends JPanel {

    public PnlSearchBar() {
        initComponents();
    }

    public PnlSearchBar(String defualtText) {
        initComponents();
        txtSearchField.setDefaultText(defualtText);
    }

    public JTextFieldDefaultText getTxtSearchField() {
        return txtSearchField;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        txtSearchField.setEnabled(enabled);
        btnSearch.setEnabled(enabled);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        txtSearchField = new JTextFieldDefaultText();
        btnSearch = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setPreferredSize(new Dimension(275, 30));
        setMinimumSize(new Dimension(275, 30));
        setBorder(new LineBorder(new Color(51, 102, 153), 2));
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {239, 33, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {45, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

        //---- txtSearchField ----
        txtSearchField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtSearchField.setMinimumSize(new Dimension(235, 25));
        txtSearchField.setPreferredSize(new Dimension(235, 25));
        txtSearchField.setBorder(null);
        txtSearchField.setName("txtSearchField");
        add(txtSearchField, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 5, 0, 0), 0, 0));

        //---- btnSearch ----
        btnSearch.setIcon(new ImageIcon(getClass().getResource("/images/search.png")));
        btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSearch.setName("btnSearch");
        add(btnSearch, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextFieldDefaultText txtSearchField;
    private JLabel btnSearch;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
