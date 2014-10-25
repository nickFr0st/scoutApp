/*
 * Created by JFormDesigner on Sat Oct 25 11:30:02 MDT 2014
 */

package scout.clientPnls.configPnls;

import guiUtil.JTextFieldDefaultText;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class PnlMeritBadges extends JPanel implements Configuration {
    public PnlMeritBadges() {
        initComponents();
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() {

    }

    @Override
    public void createNew() {

    }

    private void txtSearchNameKeyReleased() {
        // TODO add your code here
    }

    private void listBadgeNamesMouseClicked() {
        // TODO add your code here
    }

    private void listBadgeNamesKeyReleased(KeyEvent e) {
        // TODO add your code here
    }

    private void btnBrowseImgPathMouseClicked() {
        // TODO add your code here
    }

    private void btnNewRequirementMouseClicked() {
        // TODO add your code here
    }

    private void btnDeleteRequirementMouseClicked() {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblListName = new JLabel();
        lblGeneralInfo = new JLabel();
        lblRequirements = new JLabel();
        btnNewRequirement = new JLabel();
        btnDeleteRequirement = new JLabel();
        pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        pnlSelectedImage = new JPanel();
        lblImage = new JLabel();
        scrollPane2 = new JScrollPane();
        pnlRequirements = new JPanel();
        scrollPane3 = new JScrollPane();
        listBadgeNames = new JList();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        pnlGeneralInfo = new JPanel();
        txtImagePath = new JTextFieldDefaultText();
        btnBrowseImgPath = new JButton();
        txtBadgeName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        chkReqForEagle = new JCheckBox();
        lblMBCoiunselors = new JLabel();
        btnNewCounselor = new JLabel();
        btnDeleteCounselor = new JLabel();
        scrollPane1 = new JScrollPane();
        tblCounselors = new JTable();

        //======== this ========
        setMaximumSize(new Dimension(1000, 670));
        setMinimumSize(new Dimension(1000, 670));
        setPreferredSize(new Dimension(1000, 670));
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {240, 33, 158, 44, 30, 30, 30, 33, 132, 30, 30, 177, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {45, 0, 152, 152, 45, 239, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- lblListName ----
        lblListName.setText("Merit Badges");
        lblListName.setVerticalAlignment(SwingConstants.BOTTOM);
        lblListName.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblListName.setForeground(new Color(51, 102, 153));
        lblListName.setName("lblListName");
        add(lblListName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblGeneralInfo ----
        lblGeneralInfo.setText("General Information");
        lblGeneralInfo.setVerticalAlignment(SwingConstants.BOTTOM);
        lblGeneralInfo.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblGeneralInfo.setForeground(new Color(51, 102, 153));
        lblGeneralInfo.setName("lblGeneralInfo");
        add(lblGeneralInfo, new GridBagConstraints(2, 0, 5, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblRequirements ----
        lblRequirements.setText("Requirements");
        lblRequirements.setVerticalAlignment(SwingConstants.BOTTOM);
        lblRequirements.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblRequirements.setForeground(new Color(51, 102, 153));
        lblRequirements.setName("lblRequirements");
        add(lblRequirements, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- btnNewRequirement ----
        btnNewRequirement.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
        btnNewRequirement.setPreferredSize(new Dimension(24, 24));
        btnNewRequirement.setMinimumSize(new Dimension(24, 24));
        btnNewRequirement.setMaximumSize(new Dimension(24, 24));
        btnNewRequirement.setHorizontalAlignment(SwingConstants.CENTER);
        btnNewRequirement.setBackground(Color.white);
        btnNewRequirement.setOpaque(true);
        btnNewRequirement.setToolTipText("Add New Requirement");
        btnNewRequirement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNewRequirement.setName("btnNewRequirement");
        btnNewRequirement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnNewRequirementMouseClicked();
            }
        });
        add(btnNewRequirement, new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- btnDeleteRequirement ----
        btnDeleteRequirement.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
        btnDeleteRequirement.setPreferredSize(new Dimension(24, 24));
        btnDeleteRequirement.setMinimumSize(new Dimension(24, 24));
        btnDeleteRequirement.setMaximumSize(new Dimension(24, 24));
        btnDeleteRequirement.setHorizontalAlignment(SwingConstants.CENTER);
        btnDeleteRequirement.setBackground(Color.white);
        btnDeleteRequirement.setOpaque(true);
        btnDeleteRequirement.setToolTipText("Delete selected requirement");
        btnDeleteRequirement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDeleteRequirement.setName("btnDeleteRequirement");
        btnDeleteRequirement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnDeleteRequirementMouseClicked();
            }
        });
        add(btnDeleteRequirement, new GridBagConstraints(10, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== pnlSearch ========
        {
            pnlSearch.setBackground(Color.white);
            pnlSearch.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            pnlSearch.setName("pnlSearch");
            pnlSearch.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlSearch.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)pnlSearch.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)pnlSearch.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)pnlSearch.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- txtSearchName ----
            txtSearchName.setDefaultText("Search by Name");
            txtSearchName.setBorder(null);
            txtSearchName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtSearchName.setMinimumSize(new Dimension(0, 20));
            txtSearchName.setPreferredSize(new Dimension(101, 20));
            txtSearchName.setName("txtSearchName");
            txtSearchName.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    txtSearchNameKeyReleased();
                }
            });
            pnlSearch.add(txtSearchName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 0, 0), 0, 0));
        }
        add(pnlSearch, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 6), 0, 0));

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
        add(pnlSelectedImage, new GridBagConstraints(2, 1, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane2 ========
        {
            scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane2.setAutoscrolls(true);
            scrollPane2.setName("scrollPane2");

            //======== pnlRequirements ========
            {
                pnlRequirements.setBackground(Color.white);
                pnlRequirements.setMinimumSize(new Dimension(20, 387));
                pnlRequirements.setName("pnlRequirements");
                pnlRequirements.setLayout(new GridBagLayout());
                ((GridBagLayout)pnlRequirements.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)pnlRequirements.getLayout()).rowHeights = new int[] {358, 0};
                ((GridBagLayout)pnlRequirements.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)pnlRequirements.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
            }
            scrollPane2.setViewportView(pnlRequirements);
        }
        add(scrollPane2, new GridBagConstraints(8, 1, 4, 5, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane3 ========
        {
            scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane3.setName("scrollPane3");

            //---- listBadgeNames ----
            listBadgeNames.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            listBadgeNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
            listBadgeNames.setName("listBadgeNames");
            listBadgeNames.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    listBadgeNamesMouseClicked();
                }
            });
            listBadgeNames.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    listBadgeNamesKeyReleased(e);
                }
            });
            scrollPane3.setViewportView(listBadgeNames);
        }
        add(scrollPane3, new GridBagConstraints(0, 2, 1, 4, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer1 ----
        hSpacer1.setOpaque(false);
        hSpacer1.setName("hSpacer1");
        add(hSpacer1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer2 ----
        hSpacer2.setOpaque(false);
        hSpacer2.setName("hSpacer2");
        add(hSpacer2, new GridBagConstraints(7, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== pnlGeneralInfo ========
        {
            pnlGeneralInfo.setBackground(Color.white);
            pnlGeneralInfo.setName("pnlGeneralInfo");
            pnlGeneralInfo.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWidths = new int[] {205, 100, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- txtImagePath ----
            txtImagePath.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtImagePath.setMinimumSize(new Dimension(14, 40));
            txtImagePath.setPreferredSize(new Dimension(14, 40));
            txtImagePath.setDefaultText("Path");
            txtImagePath.setDisabledTextColor(Color.gray);
            txtImagePath.setName("txtImagePath");
            pnlGeneralInfo.add(txtImagePath, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- btnBrowseImgPath ----
            btnBrowseImgPath.setText("Browse");
            btnBrowseImgPath.setBackground(new Color(51, 102, 153));
            btnBrowseImgPath.setForeground(Color.white);
            btnBrowseImgPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnBrowseImgPath.setFocusPainted(false);
            btnBrowseImgPath.setName("btnBrowseImgPath");
            btnBrowseImgPath.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnBrowseImgPathMouseClicked();
                }
            });
            pnlGeneralInfo.add(btnBrowseImgPath, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- txtBadgeName ----
            txtBadgeName.setPreferredSize(new Dimension(14, 40));
            txtBadgeName.setMinimumSize(new Dimension(14, 40));
            txtBadgeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtBadgeName.setDefaultText("Badge Name");
            txtBadgeName.setName("txtBadgeName");
            pnlGeneralInfo.add(txtBadgeName, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblNameError ----
            lblNameError.setText("* Error Message");
            lblNameError.setForeground(Color.red);
            lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblNameError.setName("lblNameError");
            pnlGeneralInfo.add(lblNameError, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- chkReqForEagle ----
            chkReqForEagle.setText("Required for Eagle");
            chkReqForEagle.setBackground(Color.white);
            chkReqForEagle.setFont(new Font("Tahoma", Font.PLAIN, 14));
            chkReqForEagle.setForeground(new Color(51, 102, 153));
            chkReqForEagle.setName("chkReqForEagle");
            pnlGeneralInfo.add(chkReqForEagle, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(pnlGeneralInfo, new GridBagConstraints(2, 3, 5, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 0, 5, 5), 0, 0));

        //---- lblMBCoiunselors ----
        lblMBCoiunselors.setText("Merit Badge Counselors");
        lblMBCoiunselors.setVerticalAlignment(SwingConstants.BOTTOM);
        lblMBCoiunselors.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblMBCoiunselors.setForeground(new Color(51, 102, 153));
        lblMBCoiunselors.setName("lblMBCoiunselors");
        add(lblMBCoiunselors, new GridBagConstraints(2, 4, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- btnNewCounselor ----
        btnNewCounselor.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
        btnNewCounselor.setPreferredSize(new Dimension(24, 24));
        btnNewCounselor.setMinimumSize(new Dimension(24, 24));
        btnNewCounselor.setMaximumSize(new Dimension(24, 24));
        btnNewCounselor.setHorizontalAlignment(SwingConstants.CENTER);
        btnNewCounselor.setBackground(Color.white);
        btnNewCounselor.setOpaque(true);
        btnNewCounselor.setToolTipText("Add New Requirement");
        btnNewCounselor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNewCounselor.setName("btnNewCounselor");
        btnNewCounselor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnNewRequirementMouseClicked();
            }
        });
        add(btnNewCounselor, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- btnDeleteCounselor ----
        btnDeleteCounselor.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
        btnDeleteCounselor.setPreferredSize(new Dimension(24, 24));
        btnDeleteCounselor.setMinimumSize(new Dimension(24, 24));
        btnDeleteCounselor.setMaximumSize(new Dimension(24, 24));
        btnDeleteCounselor.setHorizontalAlignment(SwingConstants.CENTER);
        btnDeleteCounselor.setBackground(Color.white);
        btnDeleteCounselor.setOpaque(true);
        btnDeleteCounselor.setToolTipText("Delete selected requirement");
        btnDeleteCounselor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDeleteCounselor.setName("btnDeleteCounselor");
        btnDeleteCounselor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnDeleteRequirementMouseClicked();
            }
        });
        add(btnDeleteCounselor, new GridBagConstraints(5, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setName("scrollPane1");

            //---- tblCounselors ----
            tblCounselors.setName("tblCounselors");
            scrollPane1.setViewportView(tblCounselors);
        }
        add(scrollPane1, new GridBagConstraints(2, 5, 5, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblListName;
    private JLabel lblGeneralInfo;
    private JLabel lblRequirements;
    private JLabel btnNewRequirement;
    private JLabel btnDeleteRequirement;
    private JPanel pnlSearch;
    private JTextFieldDefaultText txtSearchName;
    private JPanel pnlSelectedImage;
    private JLabel lblImage;
    private JScrollPane scrollPane2;
    private JPanel pnlRequirements;
    private JScrollPane scrollPane3;
    private JList listBadgeNames;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JPanel pnlGeneralInfo;
    private JTextFieldDefaultText txtImagePath;
    private JButton btnBrowseImgPath;
    private JTextFieldDefaultText txtBadgeName;
    private JLabel lblNameError;
    private JCheckBox chkReqForEagle;
    private JLabel lblMBCoiunselors;
    private JLabel btnNewCounselor;
    private JLabel btnDeleteCounselor;
    private JScrollPane scrollPane1;
    private JTable tblCounselors;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
