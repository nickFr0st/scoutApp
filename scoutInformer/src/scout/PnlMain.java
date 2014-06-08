/*
 * Created by JFormDesigner on Tue Jun 03 18:59:11 MDT 2014
 */

package scout;

import constants.RankConst;
import constants.TenderfootReqConst;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
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
        enableControls(false);
    }

    private void enableControls(boolean enable) {
        txtAge.setEnabled(enable);
        txtName.setEnabled(enable);
        cboRank.setEnabled(enable);
        if (!enable) {
            lblCurrentBadge.setIcon(new ImageIcon(getClass().getResource(RankConst.NEW_SCOUT.getImgPath())));
        }
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
            enableControls(true);
            String scoutName = listScouts.getSelectedValue().toString();
            String[] split = scoutName.split(", ");

            Scout scout = getScoutInfo(split);
            Rank rank = getRankInfo(scout.getId());

            // main general area
            txtName.setText(scout.getName());
            txtAge.setText(Integer.toString(scout.getAge()));
            lblCurrentBadge.setIcon(new ImageIcon(getClass().getResource(rank.getImgPath())));
            cboRank.setSelectedItem(RankConst.getConstById(rank.getRankId()).getName());

            // populate next rank requirements
            if (rank.getCompletedRequirements() != null) {
                List<String> reqList = new ArrayList<String>();
                reqList.addAll(Arrays.asList(rank.getCompletedRequirements().split(",")));
                int rankId = rank.getRankId();

                if (rankId == RankConst.TENDERFOOT.getId()) {
                    populateTenderfootRequirements(reqList);
                }
            }

        }
    }

    private void populateTenderfootRequirements(List<String> completedRequirements) {
        for (TenderfootReqConst regConst : TenderfootReqConst.values()) {
            // todo: add componants to the scroll pane
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
                rank.setCompletedRequirements(rs.getString(Rank.COMPLETED_RANK_REQUIREMENTS));
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

    private void cboRankItemStateChanged() {
        String rankName = cboRank.getSelectedItem().toString();
        lblCurrentBadge.setIcon(new ImageIcon(getClass().getResource(RankConst.getConstByName(rankName).getImgPath())));
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
        lblCurrentBadge = new JLabel();
        lblName = new JLabel();
        txtName = new JTextField();
        lblAge = new JLabel();
        txtAge = new JTextField();
        lblRank = new JLabel();
        cboRank = new JComboBox(RankConst.getConstList());
        pnlNewRankRequirements = new JPanel();
        lblNextRank = new JLabel();
        lblNextRankValue = new JLabel();
        pnlRequirements = new JPanel();
        scrollPane2 = new JScrollPane();

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
                    ((GridBagLayout)pnlGeneral.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
                    ((GridBagLayout)pnlGeneral.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
                    ((GridBagLayout)pnlGeneral.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

                    //---- lblCurrentBadge ----
                    lblCurrentBadge.setIcon(new ImageIcon(getClass().getResource("/images/badge_new_scout.png")));
                    lblCurrentBadge.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    lblCurrentBadge.setMinimumSize(new Dimension(132, 143));
                    lblCurrentBadge.setMaximumSize(new Dimension(132, 143));
                    lblCurrentBadge.setPreferredSize(new Dimension(132, 143));
                    pnlGeneral.add(lblCurrentBadge, new GridBagConstraints(0, 0, 1, 4, 0.0, 0.0,
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

                    //---- cboRank ----
                    cboRank.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            cboRankItemStateChanged();
                        }
                    });
                    pnlGeneral.add(cboRank, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //======== pnlNewRankRequirements ========
                    {
                        pnlNewRankRequirements.setBorder(new TitledBorder(new EtchedBorder(), "Requirements for Next Rank", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                            new Font("Tahoma", Font.PLAIN, 16), Color.blue));
                        pnlNewRankRequirements.setLayout(new GridBagLayout());
                        ((GridBagLayout)pnlNewRankRequirements.getLayout()).columnWidths = new int[] {76, 0, 0};
                        ((GridBagLayout)pnlNewRankRequirements.getLayout()).rowHeights = new int[] {0, 0, 0};
                        ((GridBagLayout)pnlNewRankRequirements.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                        ((GridBagLayout)pnlNewRankRequirements.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

                        //---- lblNextRank ----
                        lblNextRank.setText("Next Rank:");
                        lblNextRank.setFont(new Font("Tahoma", Font.PLAIN, 16));
                        pnlNewRankRequirements.add(lblNextRank, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 5, 0, 0), 0, 0));

                        //---- lblNextRankValue ----
                        lblNextRankValue.setFont(new Font("Tahoma", Font.PLAIN, 16));
                        pnlNewRankRequirements.add(lblNextRankValue, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //======== pnlRequirements ========
                        {
                            pnlRequirements.setLayout(new GridBagLayout());
                            ((GridBagLayout)pnlRequirements.getLayout()).columnWidths = new int[] {0, 0};
                            ((GridBagLayout)pnlRequirements.getLayout()).rowHeights = new int[] {0, 0};
                            ((GridBagLayout)pnlRequirements.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                            ((GridBagLayout)pnlRequirements.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};
                            pnlRequirements.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                        }
                        pnlNewRankRequirements.add(pnlRequirements, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(5, 5, 5, 5), 0, 0));
                    }
                    pnlGeneral.add(pnlNewRankRequirements, new GridBagConstraints(0, 4, 6, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
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
    private JLabel lblCurrentBadge;
    private JLabel lblName;
    private JTextField txtName;
    private JLabel lblAge;
    private JTextField txtAge;
    private JLabel lblRank;
    private JComboBox cboRank;
    private JPanel pnlNewRankRequirements;
    private JLabel lblNextRank;
    private JLabel lblNextRankValue;
    private JPanel pnlRequirements;
    private JScrollPane scrollPane2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
