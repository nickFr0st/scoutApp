/*
 * Created by JFormDesigner on Tue Jul 01 19:28:23 MDT 2014
 */

package scout.clientPnls;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * @author User #2
 */
public class PnlDefaultSplash extends JPanel {
    public PnlDefaultSplash() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label8 = new JLabel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label7 = new JLabel();
        label6 = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setBorder(new MatteBorder(5, 5, 7, 7, new Color(51, 102, 153)));
        setMinimumSize(new Dimension(1100, 800));
        setPreferredSize(new Dimension(1100, 800));
        setMaximumSize(new Dimension(1100, 800));
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0E-4};

        //---- label8 ----
        label8.setText("Welcome to BSA Database");
        label8.setFont(new Font("Vijaya", Font.PLAIN, 55));
        label8.setHorizontalAlignment(SwingConstants.CENTER);
        label8.setForeground(new Color(51, 102, 153));
        add(label8, new GridBagConstraints(1, 0, 5, 1, 0.0, 0.0,
            GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label1 ----
        label1.setIcon(new ImageIcon(getClass().getResource("/images/advancements/new_scout.png")));
        add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- label2 ----
        label2.setIcon(new ImageIcon(getClass().getResource("/images/advancements/tenderfoot.png")));
        add(label2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- label3 ----
        label3.setIcon(new ImageIcon(getClass().getResource("/images/advancements/second_class.png")));
        add(label3, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- label4 ----
        label4.setIcon(new ImageIcon(getClass().getResource("/images/advancements/first_class.png")));
        add(label4, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- label5 ----
        label5.setIcon(new ImageIcon(getClass().getResource("/images/advancements/star.png")));
        add(label5, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- label7 ----
        label7.setIcon(new ImageIcon(getClass().getResource("/images/advancements/life.png")));
        add(label7, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- label6 ----
        label6.setIcon(new ImageIcon(getClass().getResource("/images/advancements/eagle.png")));
        add(label6, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label8;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label7;
    private JLabel label6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
