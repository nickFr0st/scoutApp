/*
 * Created by JFormDesigner on Sun Jul 06 13:16:43 MDT 2014
 */

package scout.clientPnls;

import guiUtil.JTextFieldDefaultText;
import guiUtil.SelectionBorder;
import util.DBConnector;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author User #2
 */
public class PnlBadgeConf extends JPanel implements PnlGui {
    DBConnector connector;

    {
        connector = new DBConnector();
    }

    public PnlBadgeConf() {
        initComponents();
        ((SelectionBorder)getBorder()).cutSelectedArea(120, 220);

        init();
    }

    private void init() {
        list1.setListData(connector.getAdvancementList().toArray());
    }

    @Override
    public void resetPanel() {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        button6 = new JButton();
        label1 = new JLabel();
        label3 = new JLabel();
        hSpacer1 = new JPanel(null);
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        hSpacer2 = new JPanel(null);
        scrollPane2 = new JScrollPane();
        list2 = new JList();
        panel2 = new JPanel();
        label6 = new JLabel();
        vSpacer1 = new JPanel(null);
        panel3 = new JPanel();
        label2 = new JLabel();
        label4 = new JLabel();
        textFieldDefaultText1 = new JTextFieldDefaultText();
        button7 = new JButton();
        label5 = new JLabel();
        textFieldDefaultText2 = new JTextFieldDefaultText();
        checkBox1 = new JCheckBox();

        //======== this ========
        setMinimumSize(new Dimension(1100, 800));
        setPreferredSize(new Dimension(1100, 800));
        setBorder(new SelectionBorder());
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {10, 22, 213, 59, 78, 115, 64, 498, 20, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {11, 0, 54, 131, 117, 118, 36, 166, 501, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {50, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- button1 ----
            button1.setText("Advancements");
            button1.setBackground(new Color(51, 102, 153));
            button1.setForeground(Color.white);
            button1.setFont(new Font("Tahoma", Font.PLAIN, 14));
            button1.setFocusPainted(false);
            panel1.add(button1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- button2 ----
            button2.setText("Merit Badges");
            button2.setBackground(new Color(51, 102, 153));
            button2.setForeground(Color.white);
            button2.setFont(new Font("Tahoma", Font.PLAIN, 14));
            button2.setFocusPainted(false);
            panel1.add(button2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- button3 ----
            button3.setText("Other Awards");
            button3.setBackground(new Color(51, 102, 153));
            button3.setForeground(Color.white);
            button3.setFont(new Font("Tahoma", Font.PLAIN, 14));
            button3.setFocusPainted(false);
            panel1.add(button3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- button4 ----
            button4.setText("Import");
            button4.setBackground(new Color(32, 154, 26));
            button4.setForeground(Color.white);
            button4.setFont(new Font("Tahoma", Font.PLAIN, 14));
            button4.setFocusPainted(false);
            panel1.add(button4, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- button5 ----
            button5.setText("Export");
            button5.setBackground(new Color(32, 154, 26));
            button5.setForeground(Color.white);
            button5.setFont(new Font("Tahoma", Font.PLAIN, 14));
            button5.setFocusPainted(false);
            panel1.add(button5, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- button6 ----
            button6.setText("Save");
            button6.setBackground(new Color(32, 154, 26));
            button6.setForeground(Color.white);
            button6.setFont(new Font("Tahoma", Font.PLAIN, 14));
            button6.setFocusPainted(false);
            panel1.add(button6, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(1, 1, 7, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label1 ----
        label1.setText("Advancements");
        label1.setVerticalAlignment(SwingConstants.BOTTOM);
        label1.setFont(new Font("Vijaya", Font.PLAIN, 24));
        label1.setForeground(new Color(51, 102, 153));
        add(label1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label3 ----
        label3.setText("Requirements");
        label3.setVerticalAlignment(SwingConstants.BOTTOM);
        label3.setFont(new Font("Vijaya", Font.PLAIN, 24));
        label3.setForeground(new Color(51, 102, 153));
        add(label3, new GridBagConstraints(7, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer1 ----
        hSpacer1.setBackground(Color.white);
        add(hSpacer1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            list1.setFont(new Font("Tahoma", Font.PLAIN, 14));
            scrollPane1.setViewportView(list1);
        }
        add(scrollPane1, new GridBagConstraints(2, 3, 1, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer2 ----
        hSpacer2.setBackground(Color.white);
        add(hSpacer2, new GridBagConstraints(3, 3, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane2 ========
        {

            //---- list2 ----
            list2.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            scrollPane2.setViewportView(list2);
        }
        add(scrollPane2, new GridBagConstraints(7, 3, 1, 5, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel2 ========
        {
            panel2.setBackground(new Color(204, 204, 204));
            panel2.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            panel2.setLayout(new GridBagLayout());
            ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //---- label6 ----
            label6.setIcon(new ImageIcon(getClass().getResource("/images/no_image.png")));
            panel2.add(label6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel2, new GridBagConstraints(4, 4, 2, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- vSpacer1 ----
        vSpacer1.setBackground(Color.white);
        add(vSpacer1, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel3 ========
        {
            panel3.setBackground(Color.white);
            panel3.setLayout(new GridBagLayout());
            ((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 276, 105, 0, 0};
            ((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- label2 ----
            label2.setText("General Information");
            label2.setVerticalAlignment(SwingConstants.BOTTOM);
            label2.setFont(new Font("Vijaya", Font.PLAIN, 24));
            label2.setForeground(new Color(51, 102, 153));
            panel3.add(label2, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 5), 0, 0));

            //---- label4 ----
            label4.setText("Image Path:");
            label4.setFont(new Font("Tahoma", Font.PLAIN, 14));
            label4.setForeground(new Color(51, 102, 153));
            panel3.add(label4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- textFieldDefaultText1 ----
            textFieldDefaultText1.setFont(new Font("Tahoma", Font.PLAIN, 14));
            textFieldDefaultText1.setMinimumSize(new Dimension(14, 40));
            textFieldDefaultText1.setPreferredSize(new Dimension(14, 40));
            textFieldDefaultText1.setDefaultText("Path");
            panel3.add(textFieldDefaultText1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- button7 ----
            button7.setText("Browse");
            button7.setBackground(new Color(51, 102, 153));
            button7.setForeground(Color.white);
            button7.setFont(new Font("Tahoma", Font.PLAIN, 14));
            button7.setFocusPainted(false);
            panel3.add(button7, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

            //---- label5 ----
            label5.setText("Name:");
            label5.setFont(new Font("Tahoma", Font.PLAIN, 14));
            label5.setForeground(new Color(51, 102, 153));
            panel3.add(label5, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- textFieldDefaultText2 ----
            textFieldDefaultText2.setPreferredSize(new Dimension(14, 40));
            textFieldDefaultText2.setMinimumSize(new Dimension(14, 40));
            textFieldDefaultText2.setFont(new Font("Tahoma", Font.PLAIN, 14));
            textFieldDefaultText2.setDefaultText("Badge Name");
            panel3.add(textFieldDefaultText2, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- checkBox1 ----
            checkBox1.setText("Required For Eagle");
            checkBox1.setFont(new Font("Tahoma", Font.PLAIN, 14));
            checkBox1.setBackground(Color.white);
            checkBox1.setForeground(new Color(51, 102, 153));
            panel3.add(checkBox1, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
        }
        add(panel3, new GridBagConstraints(2, 7, 5, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JLabel label1;
    private JLabel label3;
    private JPanel hSpacer1;
    private JScrollPane scrollPane1;
    private JList list1;
    private JPanel hSpacer2;
    private JScrollPane scrollPane2;
    private JList list2;
    private JPanel panel2;
    private JLabel label6;
    private JPanel vSpacer1;
    private JPanel panel3;
    private JLabel label2;
    private JLabel label4;
    private JTextFieldDefaultText textFieldDefaultText1;
    private JButton button7;
    private JLabel label5;
    private JTextFieldDefaultText textFieldDefaultText2;
    private JCheckBox checkBox1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
