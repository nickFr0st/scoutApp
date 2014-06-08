/*
 * Created by JFormDesigner on Tue Jun 03 18:59:11 MDT 2014
 */

package scout;

import constants.RankConst;

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
        txtPosition.setEnabled(enable);
        if (!enable) {
            lblCurrentBadge.setIcon(null);
        }
    }

    private List<String> getScoutList() {
        List<String> scoutList = new ArrayList<String>();

        try {
            DBConnector.connectToDB();
            Statement statement = DBConnector.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM scout");

            while (rs.next()) {
                scoutList.add(rs.getString(Scout.NAME));
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

            Scout scout = getScoutInfo(scoutName);
            Rank rank = getRankInfo(scout.getCurrentRankId());

            if (rank.getImgPath() == null) {
                rank = new EmptyRank();
            }

            // main general area
            txtName.setText(scout.getName());
            txtAge.setText(Integer.toString(scout.getAge()));
            lblCurrentBadge.setIcon(new ImageIcon(getClass().getResource(rank.getImgPath())));
            cboRank.setSelectedItem(rank.getName());
            txtPosition.setText(scout.getPosition());

            // populate next rank requirements
            panel2.removeAll();

            int rankId = scout.getCurrentRankId();
            if (rankId >= RankConst.EAGLE.getId()) {
                lblNextRankValue.setText("  You have reached Eagle");

                JLabel lblComplete = new JLabel();
                lblComplete.setText("You have no more rank advancement requirements");
                lblComplete.setFont(new Font("Tahoma", Font.PLAIN, 16));
                lblComplete.setHorizontalAlignment(SwingConstants.RIGHT);
                panel2.add(lblComplete, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(10, 0, 5, 5), 0, 0));
            } else {
                Rank nextRank = getRankInfo(rank.getId() + 1);
                lblNextRankValue.setText("  " + nextRank.getName());
                populateRequirements(scout.getCompletedRequirements(), nextRank.getId());
            }
        }
    }

    private List<Requirement> getRequirementList(int parentId) {
        List<Requirement> requirementList = new ArrayList<Requirement>();

        try {
            DBConnector.connectToDB();
            Statement statement = DBConnector.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM requirement WHERE parentId = " + parentId);

            while (rs.next()) {
                Requirement requirement = new Requirement();
                requirement.setId(rs.getInt(Requirement.ID));
                requirement.setName(rs.getString(Requirement.NAME));
                requirement.setDescription(rs.getString(Requirement.DESCRIPTION));
                requirement.setType(rs.getInt(Requirement.TYPE));
                requirement.setParentId(parentId);
                requirementList.add(requirement);
            }

            DBConnector.closeConnection();
        } catch (SQLException sqle) {
            System.err.print("Invalid Sql Exception: ");
            System.err.println(sqle.getMessage());
        }

        return requirementList;
    }

    private void populateRequirements(List<String> completedRequirements, int rankId) {
        int grid = 0;

        List<Requirement> requirementList = getRequirementList(rankId);
        if (requirementList == null) {
            return;
        }

        for (Requirement reqConst : requirementList) {
            PnlRequirement pnlRequirement = new PnlRequirement("Requirement " + reqConst.getName() + ":", reqConst.getDescription());
            if (completedRequirements != null && completedRequirements.contains(reqConst.getName())) {
                pnlRequirement.getChkReq().setSelected(true);
            }

            panel2.add(pnlRequirement, new GridBagConstraints(0, grid++, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(5, 0, 0, 0), 0, 0));
        }
    }

    private Rank getRankInfo(int rankId) {
        Rank rank = new Rank();

        try {
            DBConnector.connectToDB();
            Statement statement = DBConnector.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM rank WHERE id = " + rankId);

            while (rs.next()) {
                rank.setId(rs.getInt(Rank.ID));
                rank.setName(rs.getString(Rank.NAME));
                rank.setImgPath(rs.getString(Rank.IMG_PATH));
            }

            DBConnector.closeConnection();
        } catch (SQLException sqle) {
            System.err.print("Invalid Sql Exception: ");
            System.err.println(sqle.getMessage());
        }

        return rank;
    }

    private Scout getScoutInfo(String scoutName) {
        Scout scout = new Scout();

        try {
            DBConnector.connectToDB();
            Statement statement = DBConnector.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scout WHERE name = '" + scoutName + "'");

            while (rs.next()) {
                scout.setId(rs.getInt(Scout.ID));
                scout.setAge(rs.getInt(Scout.AGE));
                scout.setName(scoutName);
                scout.setCurrentRankId(rs.getInt(Scout.CURRENT_RANK_ID));
                scout.setPosition(rs.getString(Scout.POSITION));

                String req = rs.getString(Scout.COMPLETED_REQUIREMENTS);
                if (req != null) {
                    List<String> reqList = new ArrayList<String>();
                    reqList.addAll(Arrays.asList(req.split(",")));
                    scout.setCompletedRequirements(reqList);
                }
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
        menuBar1 = new JMenuBar();
        btnNewScout = new JButton();
        pnlScoutModule = new JPanel();
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
        lblPosition = new JLabel();
        txtPosition = new JTextField();
        pnlNewRankRequirements = new JPanel();
        lblNextRank = new JLabel();
        lblNextRankValue = new JLabel();
        pnlRequirements = new JPanel();
        scrollPane2 = new JScrollPane();
        panel2 = new JPanel();

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
        setJMenuBar(menuBar1);

        //======== pnlScoutModule ========
        {
            pnlScoutModule.setBackground(new Color(51, 51, 51));
            pnlScoutModule.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlScoutModule.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)pnlScoutModule.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)pnlScoutModule.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)pnlScoutModule.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

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
            pnlScoutModule.add(scrollPane1, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(22, 10, 10, 15), 0, 0));

            //======== tbpDetails ========
            {

                //======== pnlGeneral ========
                {
                    pnlGeneral.setBackground(Color.white);
                    pnlGeneral.setLayout(new GridBagLayout());
                    ((GridBagLayout)pnlGeneral.getLayout()).columnWidths = new int[] {0, 0, 59, 256, 0, 0, 0};
                    ((GridBagLayout)pnlGeneral.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
                    ((GridBagLayout)pnlGeneral.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
                    ((GridBagLayout)pnlGeneral.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

                    //---- lblCurrentBadge ----
                    lblCurrentBadge.setIcon(new ImageIcon(getClass().getResource("/images/badge_new_scout.png")));
                    lblCurrentBadge.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    lblCurrentBadge.setMinimumSize(new Dimension(132, 143));
                    lblCurrentBadge.setMaximumSize(new Dimension(132, 143));
                    lblCurrentBadge.setPreferredSize(new Dimension(132, 143));
                    pnlGeneral.add(lblCurrentBadge, new GridBagConstraints(0, 0, 1, 5, 0.0, 0.0,
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
                    txtName.setBackground(Color.white);
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
                    txtAge.setBackground(Color.white);
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
                    cboRank.setBackground(Color.white);
                    cboRank.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            cboRankItemStateChanged();
                        }
                    });
                    pnlGeneral.add(cboRank, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- lblPosition ----
                    lblPosition.setText("Position:");
                    lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    lblPosition.setHorizontalAlignment(SwingConstants.RIGHT);
                    pnlGeneral.add(lblPosition, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- txtPosition ----
                    txtPosition.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    txtPosition.setHorizontalAlignment(SwingConstants.LEFT);
                    txtPosition.setBackground(Color.white);
                    pnlGeneral.add(txtPosition, new GridBagConstraints(2, 3, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //======== pnlNewRankRequirements ========
                    {
                        pnlNewRankRequirements.setBorder(new TitledBorder(new EtchedBorder(), "Requirements for Next Rank", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                            new Font("Tahoma", Font.PLAIN, 16), Color.blue));
                        pnlNewRankRequirements.setBackground(Color.white);
                        pnlNewRankRequirements.setLayout(new GridBagLayout());
                        ((GridBagLayout)pnlNewRankRequirements.getLayout()).columnWidths = new int[] {76, 0, 0};
                        ((GridBagLayout)pnlNewRankRequirements.getLayout()).rowHeights = new int[] {0, 0, 0};
                        ((GridBagLayout)pnlNewRankRequirements.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                        ((GridBagLayout)pnlNewRankRequirements.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

                        //---- lblNextRank ----
                        lblNextRank.setText("   Next Rank:");
                        lblNextRank.setFont(new Font("Tahoma", Font.PLAIN, 16));
                        lblNextRank.setOpaque(true);
                        lblNextRank.setBackground(new Color(153, 204, 255));
                        pnlNewRankRequirements.add(lblNextRank, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- lblNextRankValue ----
                        lblNextRankValue.setFont(new Font("Tahoma", Font.PLAIN, 16));
                        lblNextRankValue.setOpaque(true);
                        lblNextRankValue.setBackground(new Color(153, 204, 255));
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

                            //======== scrollPane2 ========
                            {
                                scrollPane2.setBorder(null);

                                //======== panel2 ========
                                {
                                    panel2.setBackground(Color.white);
                                    panel2.setLayout(new GridBagLayout());
                                    ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
                                    ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
                                    ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                                    ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};
                                }
                                scrollPane2.setViewportView(panel2);
                            }
                            pnlRequirements.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                        }
                        pnlNewRankRequirements.add(pnlRequirements, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(5, 5, 5, 5), 0, 0));
                    }
                    pnlGeneral.add(pnlNewRankRequirements, new GridBagConstraints(0, 5, 6, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                tbpDetails.addTab("General", pnlGeneral);
            }
            pnlScoutModule.add(tbpDetails, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 10, 10), 0, 0));
        }
        contentPane.add(pnlScoutModule, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JButton btnNewScout;
    private JPanel pnlScoutModule;
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
    private JLabel lblPosition;
    private JTextField txtPosition;
    private JPanel pnlNewRankRequirements;
    private JLabel lblNextRank;
    private JLabel lblNextRankValue;
    private JPanel pnlRequirements;
    private JScrollPane scrollPane2;
    private JPanel panel2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
