/*
 * Created by JFormDesigner on Sun Jul 06 13:16:43 MDT 2014
 */

package scout.clientPnls;

import guiUtil.JTextFieldDefaultText;
import guiUtil.SelectionBorder;
import scout.dbObjects.Advancement;
import util.DBConnector;
import util.LogicAdvancement;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        enableComponents(false);
    }

    private void enableComponents(boolean enable) {
        btnImport.setEnabled(enable);
        btnExport.setEnabled(enable);
        btnSave.setEnabled(enable);

        txtBadgeName.setEnabled(enable);
        txtImagePath.setEnabled(enable);
        btnBrowseImgPath.setEnabled(enable);
        chkReqForEagle.setVisible(enable);

        listRequirements.setEnabled(enable);
        listBadgeNames.setEnabled(enable);

        if (!enable) {
            txtBadgeName.setDefault();
            txtImagePath.setDefault();

            lblImage.setIcon(new ImageIcon(getClass().getResource("/images/no_image.png")));
            lblListName.setText("");
        }
    }

    @Override
    public void resetPanel() {
        enableComponents(false);
    }

    private void btnAdvancementsMouseClicked() {
        enableComponents(true);
        lblListName.setText("Advancements");
        chkReqForEagle.setVisible(false);

        listBadgeNames.setListData(connector.getAdvancementList().toArray());
    }

    private void listBadgeNamesMouseClicked(MouseEvent e) {
        if (listBadgeNames.getSelectedValue() != null) {
            Advancement advancement = LogicAdvancement.findAdvancementByName(listBadgeNames.getSelectedValue().toString());

            if (advancement == null) {
                return;
            }

            txtImagePath.setText(advancement.getImgPath());
            txtBadgeName.setText(advancement.getName());
            lblImage.setIcon(new ImageIcon(getClass().getResource(advancement.getImgPath())));

            // todo: need to add the requirements as well
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        btnAdvancements = new JButton();
        btnMeritBadges = new JButton();
        btnOther = new JButton();
        btnImport = new JButton();
        btnExport = new JButton();
        btnSave = new JButton();
        lblListName = new JLabel();
        lblRequirements = new JLabel();
        hSpacer1 = new JPanel(null);
        scrollPane1 = new JScrollPane();
        listBadgeNames = new JList();
        hSpacer2 = new JPanel(null);
        scrollPane2 = new JScrollPane();
        listRequirements = new JList();
        pnlSelectedImage = new JPanel();
        lblImage = new JLabel();
        vSpacer1 = new JPanel(null);
        pnlGeneralInfo = new JPanel();
        lblGeneralInfo = new JLabel();
        lblImagePath = new JLabel();
        txtImagePath = new JTextFieldDefaultText();
        btnBrowseImgPath = new JButton();
        lblBadgeName = new JLabel();
        txtBadgeName = new JTextFieldDefaultText();
        chkReqForEagle = new JCheckBox();

        //======== this ========
        setMinimumSize(new Dimension(1100, 800));
        setPreferredSize(new Dimension(1100, 800));
        setBorder(new SelectionBorder());
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {10, 22, 213, 59, 78, 115, 64, 498, 20, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {11, 0, 54, 131, 117, 118, 36, 166, 501, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {50, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- btnAdvancements ----
            btnAdvancements.setText("Advancements");
            btnAdvancements.setBackground(new Color(51, 102, 153));
            btnAdvancements.setForeground(Color.white);
            btnAdvancements.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnAdvancements.setFocusPainted(false);
            btnAdvancements.setName("btnAdvancements");
            btnAdvancements.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnAdvancementsMouseClicked();
                }
            });
            panel1.add(btnAdvancements, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnMeritBadges ----
            btnMeritBadges.setText("Merit Badges");
            btnMeritBadges.setBackground(new Color(51, 102, 153));
            btnMeritBadges.setForeground(Color.white);
            btnMeritBadges.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnMeritBadges.setFocusPainted(false);
            btnMeritBadges.setName("btnMeritBadges");
            panel1.add(btnMeritBadges, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnOther ----
            btnOther.setText("Other Awards");
            btnOther.setBackground(new Color(51, 102, 153));
            btnOther.setForeground(Color.white);
            btnOther.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnOther.setFocusPainted(false);
            btnOther.setName("btnOther");
            panel1.add(btnOther, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnImport ----
            btnImport.setText("Import");
            btnImport.setBackground(new Color(32, 154, 26));
            btnImport.setForeground(Color.white);
            btnImport.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnImport.setFocusPainted(false);
            btnImport.setName("btnImport");
            panel1.add(btnImport, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnExport ----
            btnExport.setText("Export");
            btnExport.setBackground(new Color(32, 154, 26));
            btnExport.setForeground(Color.white);
            btnExport.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnExport.setFocusPainted(false);
            btnExport.setName("btnExport");
            panel1.add(btnExport, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- btnSave ----
            btnSave.setText("Save");
            btnSave.setBackground(new Color(32, 154, 26));
            btnSave.setForeground(Color.white);
            btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnSave.setFocusPainted(false);
            btnSave.setName("btnSave");
            panel1.add(btnSave, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(1, 1, 7, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblListName ----
        lblListName.setText("Advancements");
        lblListName.setVerticalAlignment(SwingConstants.BOTTOM);
        lblListName.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblListName.setForeground(new Color(51, 102, 153));
        lblListName.setName("lblListName");
        add(lblListName, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblRequirements ----
        lblRequirements.setText("Requirements");
        lblRequirements.setVerticalAlignment(SwingConstants.BOTTOM);
        lblRequirements.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblRequirements.setForeground(new Color(51, 102, 153));
        lblRequirements.setName("lblRequirements");
        add(lblRequirements, new GridBagConstraints(7, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer1 ----
        hSpacer1.setBackground(Color.white);
        hSpacer1.setName("hSpacer1");
        add(hSpacer1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setName("scrollPane1");

            //---- listBadgeNames ----
            listBadgeNames.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            listBadgeNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
            listBadgeNames.setName("listBadgeNames");
            listBadgeNames.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    listBadgeNamesMouseClicked(e);
                }
            });
            scrollPane1.setViewportView(listBadgeNames);
        }
        add(scrollPane1, new GridBagConstraints(2, 3, 1, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer2 ----
        hSpacer2.setBackground(Color.white);
        hSpacer2.setName("hSpacer2");
        add(hSpacer2, new GridBagConstraints(3, 3, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane2 ========
        {
            scrollPane2.setName("scrollPane2");

            //---- listRequirements ----
            listRequirements.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            listRequirements.setName("listRequirements");
            scrollPane2.setViewportView(listRequirements);
        }
        add(scrollPane2, new GridBagConstraints(7, 3, 1, 5, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== pnlSelectedImage ========
        {
            pnlSelectedImage.setBackground(new Color(204, 204, 204));
            pnlSelectedImage.setBorder(new LineBorder(new Color(51, 102, 153), 2));
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
        add(pnlSelectedImage, new GridBagConstraints(4, 4, 2, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- vSpacer1 ----
        vSpacer1.setBackground(Color.white);
        vSpacer1.setName("vSpacer1");
        add(vSpacer1, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== pnlGeneralInfo ========
        {
            pnlGeneralInfo.setBackground(Color.white);
            pnlGeneralInfo.setName("pnlGeneralInfo");
            pnlGeneralInfo.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWidths = new int[] {0, 276, 105, 0, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- lblGeneralInfo ----
            lblGeneralInfo.setText("General Information");
            lblGeneralInfo.setVerticalAlignment(SwingConstants.BOTTOM);
            lblGeneralInfo.setFont(new Font("Vijaya", Font.PLAIN, 24));
            lblGeneralInfo.setForeground(new Color(51, 102, 153));
            lblGeneralInfo.setName("lblGeneralInfo");
            pnlGeneralInfo.add(lblGeneralInfo, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 15, 5), 0, 0));

            //---- lblImagePath ----
            lblImagePath.setText("Image Path:");
            lblImagePath.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblImagePath.setForeground(new Color(51, 102, 153));
            lblImagePath.setName("lblImagePath");
            pnlGeneralInfo.add(lblImagePath, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- txtImagePath ----
            txtImagePath.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtImagePath.setMinimumSize(new Dimension(14, 40));
            txtImagePath.setPreferredSize(new Dimension(14, 40));
            txtImagePath.setDefaultText("Path");
            txtImagePath.setName("txtImagePath");
            pnlGeneralInfo.add(txtImagePath, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- btnBrowseImgPath ----
            btnBrowseImgPath.setText("Browse");
            btnBrowseImgPath.setBackground(new Color(51, 102, 153));
            btnBrowseImgPath.setForeground(Color.white);
            btnBrowseImgPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnBrowseImgPath.setFocusPainted(false);
            btnBrowseImgPath.setName("btnBrowseImgPath");
            pnlGeneralInfo.add(btnBrowseImgPath, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblBadgeName ----
            lblBadgeName.setText("Name:");
            lblBadgeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblBadgeName.setForeground(new Color(51, 102, 153));
            lblBadgeName.setName("lblBadgeName");
            pnlGeneralInfo.add(lblBadgeName, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- txtBadgeName ----
            txtBadgeName.setPreferredSize(new Dimension(14, 40));
            txtBadgeName.setMinimumSize(new Dimension(14, 40));
            txtBadgeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtBadgeName.setDefaultText("Badge Name");
            txtBadgeName.setName("txtBadgeName");
            pnlGeneralInfo.add(txtBadgeName, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- chkReqForEagle ----
            chkReqForEagle.setText("Required For Eagle");
            chkReqForEagle.setFont(new Font("Tahoma", Font.PLAIN, 14));
            chkReqForEagle.setBackground(Color.white);
            chkReqForEagle.setForeground(new Color(51, 102, 153));
            chkReqForEagle.setName("chkReqForEagle");
            pnlGeneralInfo.add(chkReqForEagle, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
        }
        add(pnlGeneralInfo, new GridBagConstraints(2, 7, 5, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JButton btnAdvancements;
    private JButton btnMeritBadges;
    private JButton btnOther;
    private JButton btnImport;
    private JButton btnExport;
    private JButton btnSave;
    private JLabel lblListName;
    private JLabel lblRequirements;
    private JPanel hSpacer1;
    private JScrollPane scrollPane1;
    private JList listBadgeNames;
    private JPanel hSpacer2;
    private JScrollPane scrollPane2;
    private JList listRequirements;
    private JPanel pnlSelectedImage;
    private JLabel lblImage;
    private JPanel vSpacer1;
    private JPanel pnlGeneralInfo;
    private JLabel lblGeneralInfo;
    private JLabel lblImagePath;
    private JTextFieldDefaultText txtImagePath;
    private JButton btnBrowseImgPath;
    private JLabel lblBadgeName;
    private JTextFieldDefaultText txtBadgeName;
    private JCheckBox chkReqForEagle;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
