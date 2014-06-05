/*
 * Created by JFormDesigner on Tue Jun 03 18:59:11 MDT 2014
 */

package scout;

import javax.swing.border.*;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class PnlMain extends JFrame {
    public PnlMain() {
        initComponents();
    }

    private void init() {
        // load scout list info from the database
        
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        menuBar1 = new JMenuBar();
        btnNewScout = new JButton();
        scrollPane1 = new JScrollPane();
        listScouts = new JList();
        tbpDetails = new JTabbedPane();
        pnlGeneral = new JPanel();
        lblCurrenctBadge = new JLabel();
        lblName = new JLabel();
        lblNameValue = new JLabel();
        lblAge = new JLabel();
        lblAgeValue = new JLabel();

        //======== this ========
        setMinimumSize(new Dimension(1200, 800));
        setBackground(new Color(51, 51, 51));
        setTitle("Scout Informer");
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBackground(new Color(51, 51, 51));
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

            //======== menuBar1 ========
            {
                menuBar1.setBackground(new Color(195, 169, 117));
                menuBar1.setName("menuBar1");

                //---- btnNewScout ----
                btnNewScout.setIcon(new ImageIcon(getClass().getResource("/scout/images/new_scout.png")));
                btnNewScout.setHorizontalTextPosition(SwingConstants.CENTER);
                btnNewScout.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnNewScout.setBorder(null);
                btnNewScout.setOpaque(false);
                btnNewScout.setBackground(new Color(60, 63, 65, 0));
                btnNewScout.setToolTipText("New");
                btnNewScout.setName("btnNewScout");
                menuBar1.add(btnNewScout);
            }
            panel1.add(menuBar1, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== scrollPane1 ========
            {
                scrollPane1.setPreferredSize(new Dimension(200, 600));
                scrollPane1.setMinimumSize(new Dimension(200, 600));
                scrollPane1.setName("scrollPane1");

                //---- listScouts ----
                listScouts.setMinimumSize(new Dimension(20, 300));
                listScouts.setMaximumSize(new Dimension(20, 300));
                listScouts.setName("listScouts");
                scrollPane1.setViewportView(listScouts);
            }
            panel1.add(scrollPane1, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(22, 10, 10, 15), 0, 0));

            //======== tbpDetails ========
            {
                tbpDetails.setName("tbpDetails");

                //======== pnlGeneral ========
                {
                    pnlGeneral.setName("pnlGeneral");
                    pnlGeneral.setLayout(new GridBagLayout());
                    ((GridBagLayout)pnlGeneral.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
                    ((GridBagLayout)pnlGeneral.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
                    ((GridBagLayout)pnlGeneral.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
                    ((GridBagLayout)pnlGeneral.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

                    //---- lblCurrenctBadge ----
                    lblCurrenctBadge.setIcon(new ImageIcon(getClass().getResource("/scout/images/badge_new_scout128.png")));
                    lblCurrenctBadge.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
                    lblCurrenctBadge.setName("lblCurrenctBadge");
                    pnlGeneral.add(lblCurrenctBadge, new GridBagConstraints(0, 0, 1, 3, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(10, 10, 15, 15), 0, 0));

                    //---- lblName ----
                    lblName.setText("Name:");
                    lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    lblName.setHorizontalAlignment(SwingConstants.RIGHT);
                    lblName.setName("lblName");
                    pnlGeneral.add(lblName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(10, 0, 5, 5), 0, 0));

                    //---- lblNameValue ----
                    lblNameValue.setText("Thomas Gates");
                    lblNameValue.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    lblNameValue.setHorizontalAlignment(SwingConstants.LEFT);
                    lblNameValue.setName("lblNameValue");
                    pnlGeneral.add(lblNameValue, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(10, 0, 5, 5), 0, 0));

                    //---- lblAge ----
                    lblAge.setText("Age:");
                    lblAge.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    lblAge.setHorizontalAlignment(SwingConstants.RIGHT);
                    lblAge.setName("lblAge");
                    pnlGeneral.add(lblAge, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- lblAgeValue ----
                    lblAgeValue.setText("12");
                    lblAgeValue.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    lblAgeValue.setHorizontalAlignment(SwingConstants.LEFT);
                    lblAgeValue.setName("lblAgeValue");
                    pnlGeneral.add(lblAgeValue, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                }
                tbpDetails.addTab("General", pnlGeneral);
            }
            panel1.add(tbpDetails, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 10, 10), 0, 0));
        }
        contentPane.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JMenuBar menuBar1;
    private JButton btnNewScout;
    private JScrollPane scrollPane1;
    private JList listScouts;
    private JTabbedPane tbpDetails;
    private JPanel pnlGeneral;
    private JLabel lblCurrenctBadge;
    private JLabel lblName;
    private JLabel lblNameValue;
    private JLabel lblAge;
    private JLabel lblAgeValue;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
