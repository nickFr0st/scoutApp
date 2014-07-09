/*
 * Created by JFormDesigner on Sun Jul 06 13:16:43 MDT 2014
 */

package scout.clientPnls;

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
        vSpacer1 = new JPanel(null);
        hSpacer1 = new JPanel(null);
        scrollPane1 = new JScrollPane();
        list1 = new JList<Object>();
        hSpacer2 = new JPanel(null);
        panel2 = new JPanel();
        scrollPane2 = new JScrollPane();
        list2 = new JList();

        //======== this ========
        setMinimumSize(new Dimension(1100, 800));
        setPreferredSize(new Dimension(1100, 800));
        setBorder(new SelectionBorder());
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {10, 45, 277, 22, 199, 27, 498, 20, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {11, 0, 54, 232, 301, 501, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

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
        add(panel1, new GridBagConstraints(1, 1, 6, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- vSpacer1 ----
        vSpacer1.setBackground(Color.white);
        add(vSpacer1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer1 ----
        hSpacer1.setBackground(Color.white);
        add(hSpacer1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setBorder(new LineBorder(new Color(51, 102, 153), 3));
            scrollPane1.setViewportView(list1);
        }
        add(scrollPane1, new GridBagConstraints(2, 3, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer2 ----
        hSpacer2.setBackground(Color.white);
        add(hSpacer2, new GridBagConstraints(3, 3, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel2 ========
        {
            panel2.setBackground(Color.white);
            panel2.setBorder(new LineBorder(new Color(51, 102, 153), 3));
            panel2.setLayout(new GridBagLayout());
            ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
        }
        add(panel2, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane2 ========
        {

            //---- list2 ----
            list2.setBorder(new LineBorder(new Color(51, 102, 153), 3));
            scrollPane2.setViewportView(list2);
        }
        add(scrollPane2, new GridBagConstraints(6, 3, 1, 2, 0.0, 0.0,
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
    private JPanel vSpacer1;
    private JPanel hSpacer1;
    private JScrollPane scrollPane1;
    private JList<Object> list1;
    private JPanel hSpacer2;
    private JPanel panel2;
    private JScrollPane scrollPane2;
    private JList list2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
