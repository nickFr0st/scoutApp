/*
 * Created by JFormDesigner on Tue Jun 03 18:59:11 MDT 2014
 */

package scout;

import constants.RankConst;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nmalloch
 */
public class PnlMain extends JFrame {
    public PnlMain() {
        initComponents();
        init();
    }

    private void init() {
        listScouts.setListData(getScoutList().toArray());
    }

    private List<String> getScoutList() {
        List<String> scoutList = new ArrayList<String>();

        try {
            DBConnector.connectToDB();
            Statement statement = DBConnector.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT firstName, lastName FROM scout");

            while (rs.next()) {
                scoutList.add(rs.getString(Scout.LAST_NAME) + ", " + rs.getString(Scout.FIRST_NAME));
            }

            DBConnector.closeConnection();
        } catch (SQLException sqle) {
            System.err.print("Invalid Sql Exception: ");
            System.err.println(sqle.getMessage());
        }

        return scoutList;
    }

    private void listScoutsMouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && listScouts.getSelectedValue() != null) {
            String scoutName = listScouts.getSelectedValue().toString();
            String[] split = scoutName.split(", ");

            Scout scout = getScoutInfo(split);
            Rank rank = getRankInfo(scout.getId());

            txtName.setText(scout.getName());
            txtAge.setText(Integer.toString(scout.getAge()));
            lblCurrenctBadge.setIcon(new ImageIcon(getClass().getResource(rank.getImgPath())));
            cboRank.setSelectedItem(RankConst.getConstNameById(rank.getRankId()));
        }
    }
    private Rank getRankInfo(int scoutId) {
        Rank rank = new Rank();

        try {
            DBConnector.connectToDB();
            Statement statement = DBConnector.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM rank WHERE scoutId = " + scoutId);

            while (rs.next()) {
                rank.setId(rs.getInt(Rank.ID));
                rank.setName(rs.getString(Rank.NAME));
                rank.setImgPath(rs.getString(Rank.IMG_PATH));
                rank.setScoutId(rs.getInt(Rank.SCOUT_ID));
                rank.setRankId(rs.getInt(Rank.RANK_ID));
            }

            DBConnector.closeConnection();
        } catch (SQLException sqle) {
            System.err.print("Invalid Sql Exception: ");
            System.err.println(sqle.getMessage());
        }

        return rank;
    }

    private Scout getScoutInfo(String[] split) {
        Scout scout = new Scout();

        try {
            DBConnector.connectToDB();
            Statement statement = DBConnector.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, age, suffix FROM scout WHERE firstName = '" + split[1] + "' AND lastName = '" + split[0] + "'");

            while (rs.next()) {
                scout.setId(rs.getInt(Scout.ID));
                scout.setAge(rs.getInt(Scout.AGE));
                scout.setFirstName(split[1]);
                scout.setLastName(split[0]);
                scout.setSuffix(rs.getString(Scout.SUFFIX));
            }

            DBConnector.closeConnection();
        } catch (SQLException sqle) {
            System.err.print("Invalid Sql Exception: ");
            System.err.println(sqle.getMessage());
        }

        return scout;
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
        txtName = new JTextField();
        lblAge = new JLabel();
        txtAge = new JTextField();
        lblRank = new JLabel();
        cboRank = new JComboBox(RankConst.getConstList());

        //======== this ========
        setMinimumSize(new Dimension(1200, 800));
        setBackground(new Color(51, 51, 51));
        setTitle("Scout Informer");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBackground(new Color(51, 51, 51));
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

            //======== menuBar1 ========
            {
                menuBar1.setBackground(new Color(195, 169, 117));

                //---- btnNewScout ----
                btnNewScout.setIcon(new ImageIcon(getClass().getResource("/images/new_scout.png")));
                btnNewScout.setHorizontalTextPosition(SwingConstants.CENTER);
                btnNewScout.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnNewScout.setBorder(null);
                btnNewScout.setOpaque(false);
                btnNewScout.setBackground(new Color(60, 63, 65, 0));
                btnNewScout.setToolTipText("New");
                menuBar1.add(btnNewScout);
            }
            panel1.add(menuBar1, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== scrollPane1 ========
            {
                scrollPane1.setPreferredSize(new Dimension(200, 600));
                scrollPane1.setMinimumSize(new Dimension(200, 600));

                //---- listScouts ----
                listScouts.setMinimumSize(new Dimension(20, 300));
                listScouts.setMaximumSize(new Dimension(20, 300));
                listScouts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                listScouts.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        listScoutsMouseClicked(e);
                    }
                });
                scrollPane1.setViewportView(listScouts);
            }
            panel1.add(scrollPane1, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(22, 10, 10, 15), 0, 0));

            //======== tbpDetails ========
            {

                //======== pnlGeneral ========
                {
                    pnlGeneral.setLayout(new GridBagLayout());
                    ((GridBagLayout)pnlGeneral.getLayout()).columnWidths = new int[] {0, 0, 59, 256, 0, 0, 0};
                    ((GridBagLayout)pnlGeneral.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
                    ((GridBagLayout)pnlGeneral.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
                    ((GridBagLayout)pnlGeneral.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

                    //---- lblCurrenctBadge ----
                    lblCurrenctBadge.setIcon(new ImageIcon(getClass().getResource("/images/badge_new_scout.png")));
                    lblCurrenctBadge.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    pnlGeneral.add(lblCurrenctBadge, new GridBagConstraints(0, 0, 1, 4, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(10, 10, 15, 15), 0, 0));

                    //---- lblName ----
                    lblName.setText("Name:");
                    lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    lblName.setHorizontalAlignment(SwingConstants.RIGHT);
                    pnlGeneral.add(lblName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(10, 0, 5, 5), 0, 0));

                    //---- txtName ----
                    txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    txtName.setHorizontalAlignment(SwingConstants.LEFT);
                    pnlGeneral.add(txtName, new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(10, 0, 5, 5), 0, 0));

                    //---- lblAge ----
                    lblAge.setText("Age:");
                    lblAge.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    lblAge.setHorizontalAlignment(SwingConstants.RIGHT);
                    pnlGeneral.add(lblAge, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- txtAge ----
                    txtAge.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    txtAge.setHorizontalAlignment(SwingConstants.LEFT);
                    pnlGeneral.add(txtAge, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- lblRank ----
                    lblRank.setText("Rank:");
                    lblRank.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    lblRank.setHorizontalAlignment(SwingConstants.RIGHT);
                    pnlGeneral.add(lblRank, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                    pnlGeneral.add(cboRank, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0,
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
    private JTextField txtName;
    private JLabel lblAge;
    private JTextField txtAge;
    private JLabel lblRank;
    private JComboBox cboRank;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
