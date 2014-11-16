/*
 * Created by JFormDesigner on Thu Oct 16 21:50:20 MDT 2014
 */

package scout.clientPnls.configPnls;

import constants.RequirementTypeConst;
import guiUtil.CustomChooser;
import guiUtil.JTextFieldDefaultText;
import guiUtil.PnlRequirement;
import scout.clientPnls.IEPnls.ExportDialog;
import scout.clientPnls.IEPnls.ImportDialog;
import scout.clientPnls.PnlBadgeConf;
import scout.dbObjects.OtherAward;
import scout.dbObjects.Requirement;
import util.LogicOtherAward;
import util.LogicRequirement;
import util.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author User #2
 */
public class PnlOtherAwards extends JPanel implements Configuration {
    private int grid;
    private final ImageIcon noImageIcon = new ImageIcon(getClass().getResource("/images/no_image.png"));
    private PnlBadgeConf pnlBadgeConf;

    public PnlOtherAwards() {
        initComponents();
    }

    public PnlOtherAwards(PnlBadgeConf pnlBadgeConf) {
        this.pnlBadgeConf = pnlBadgeConf;
        initComponents();
        scrollPane2.getVerticalScrollBar().setUnitIncrement(18);
        onShow();
    }

    @Override
    public void onShow() {
        clearData();
    }

    private void populateAwardNameList() {
        List<String> awardNameList = LogicOtherAward.getNameList();

        if (awardNameList != null) {
            listAwardNames.setListData(awardNameList.toArray());
        }

        listAwardNames.revalidate();
    }

    @Override
    public void onHide() {

    }

    @Override
    public void createNew() {
        clearErrors();
        lblImage.setIcon(noImageIcon);
        txtImagePath.setDefault();
        txtAwardName.setDefault();

        pnlRequirements.removeAll();

        addNoRequirementsLabel();

        pnlRequirements.revalidate();
        pnlRequirements.repaint();

        pnlBadgeConf.getBtnSave().setVisible(true);
        pnlBadgeConf.getBtnUpdate().setVisible(false);
        pnlBadgeConf.getBtnDelete().setVisible(false);

        txtAwardName.requestFocus();
    }

    private void txtSearchNameKeyReleased() {
        List<String> awardNameList = LogicOtherAward.getNameList();
        if (awardNameList == null) {
            return;
        }

        if (txtSearchName.isMessageDefault()) {
            listAwardNames.setListData(awardNameList.toArray());
            listAwardNames.revalidate();
            return;
        }

        List<String> filteredList = new ArrayList<String>();
        for (String awardName : awardNameList) {
            if (awardName.toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(awardName);
            }
        }

        listAwardNames.setListData(filteredList.toArray());
        listAwardNames.revalidate();
    }

    private void listBadgeNamesMouseClicked() {
        if (listAwardNames.getSelectedValue() == null) {
            return;
        }

        clearErrors();

        OtherAward otherAward = LogicOtherAward.findByName(listAwardNames.getSelectedValue().toString());

        if (otherAward == null) {
            return;
        }

        pnlBadgeConf.getBtnSave().setVisible(false);
        pnlBadgeConf.getBtnUpdate().setVisible(true);
        pnlBadgeConf.getBtnDelete().setVisible(true);

        txtImagePath.setText(otherAward.getImgPath());
        txtAwardName.setText(otherAward.getName());

        URL imgPath = getClass().getResource(otherAward.getImgPath());
        if (imgPath == null) {
            ImageIcon tryPath = new ImageIcon(otherAward.getImgPath());
            if (tryPath.getImageLoadStatus() < MediaTracker.COMPLETE) {
                lblImage.setIcon(noImageIcon);
            } else {
                setImage(otherAward.getImgPath());
            }
        } else {
            setImage(imgPath.getPath());
        }

        resetPnlRequirements();
        grid = 0;

        List<Requirement> requirementList = LogicRequirement.findAllByParentIdTypeId(otherAward.getId(), RequirementTypeConst.OTHER.getId());
        if (!Util.isEmpty(requirementList)) {
            for (Requirement requirement : requirementList) {
                PnlRequirement pnlRequirement = new PnlRequirement(requirement.getName(), requirement.getDescription(), grid++ > 0, requirement.getId());
                pnlRequirements.add(pnlRequirement);
            }
        } else {
            addNoRequirementsLabel();
        }

        pnlRequirements.revalidate();
        pnlRequirements.repaint();
    }

    private void resetPnlRequirements() {
        pnlRequirements.removeAll();
        pnlRequirements.setLayout(new BoxLayout(pnlRequirements, BoxLayout.Y_AXIS));
    }

    private void listBadgeNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listBadgeNamesMouseClicked();
        }
    }

    private void btnNewRequirementMouseClicked() {
        if (!btnNewRequirement.isEnabled()) {
            return;
        }

        if (pnlRequirements.getComponentCount() > 0 && grid < 0) {
            resetPnlRequirements();
            pnlRequirements.revalidate();
            grid = 0;
        } else if (pnlRequirements.getComponentCount() <= 0) {
            resetPnlRequirements();
            grid = 0;
        }

        // take note when saving that an id of -1 is a new requirement
        PnlRequirement pnlRequirement = new PnlRequirement("[num]", "[Description]", grid++ > 0, -1);
        pnlRequirements.add(pnlRequirement);

        pnlRequirement.getTxtReqName().requestFocus();

        pnlRequirements.revalidate();
    }

    private void btnDeleteRequirementMouseClicked() {
        if (!btnDeleteRequirement.isEnabled() || KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == null) {
            return;
        }

        if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getParent() instanceof PnlRequirement) {
            pnlRequirements.remove(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getParent());
            grid--;

            if (grid <= 0) {
                pnlRequirements.removeAll();
                addNoRequirementsLabel();
            }

            pnlRequirements.revalidate();
            pnlRequirements.repaint();
        }
    }

    private void btnBrowseImgPathMouseClicked() {
        if (!btnBrowseImgPath.isEnabled()) {
            return;
        }

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");

        CustomChooser chooser = new CustomChooser();
        chooser.setDialogTitle("Select an image");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);
        int returnValue = chooser.showOpenDialog(this);
        chooser.resetLookAndFeel();

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            setImage(file.getPath());
        }
    }

    private void addNoRequirementsLabel() {
        resetPnlRequirements();
        grid = -1;
        JLabel lblNoRequirements = new JLabel();
        lblNoRequirements.setText("No Requirements");
        lblNoRequirements.setHorizontalAlignment(SwingConstants.CENTER);
        lblNoRequirements.setFont(new Font("Tahoma", Font.PLAIN, 16));

        pnlRequirements.add(lblNoRequirements);
        pnlRequirements.revalidate();
        pnlRequirements.repaint();
    }

    private void setImage(String imgPath) {
        try {
            BufferedImage img = ImageIO.read(new File(imgPath));

            int height = img.getHeight() > lblImage.getHeight() ? lblImage.getHeight() : img.getHeight();
            int width = img.getWidth() > lblImage.getWidth() ? lblImage.getWidth() : img.getWidth();

            lblImage.setIcon(new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
            txtImagePath.setText(imgPath);
        } catch (IOException ignore) {
        }
    }

    public void save() {
        clearErrors();

        if (!validateName()) {
            return;
        }

        OtherAward otherAward = new OtherAward();
        otherAward.setName(txtAwardName.getText());

        if (!txtImagePath.isMessageDefault()) {
            otherAward.setImgPath(txtImagePath.getText());
        } else {
            otherAward.setImgPath("");
        }

        List<Requirement> requirementList = validateRequirements(-1);
        if (requirementList == null) return;

        LogicOtherAward.save(otherAward);

        for (Requirement requirement : requirementList) {
            requirement.setParentId(otherAward.getId());
            requirement.setTypeId(RequirementTypeConst.OTHER.getId());
        }

        LogicRequirement.saveList(requirementList);

        populateAwardNameList();

        listAwardNames.setSelectedValue(txtAwardName.getText(), true);
        reloadData();
    }

    private List<Requirement> validateRequirements(int parentId) {
        List<Requirement> requirementList = new ArrayList<Requirement>();

        Set<String> reqNameSet = new HashSet<String>();

        if (grid > 0) {
            for (Component component : pnlRequirements.getComponents()) {
                if (component instanceof PnlRequirement) {

                    String reqName = ((PnlRequirement)component).getName().trim();

                    if (reqName.isEmpty()) {
                        Util.setError(lblReqError, "Requirement name cannot be left blank");
                        return null;
                    }

                    if (!reqNameSet.add(reqName)) {
                        Util.setError(lblReqError, "Requirement name '" + reqName + "' already exists");
                        component.requestFocus();
                        return null;
                    }

                    if (((PnlRequirement)component).getDescription().trim().isEmpty()) {
                        Util.setError(lblReqError, "Requirement description cannot be left blank");
                        return null;
                    }

                    Requirement requirement = new Requirement();
                    if (parentId > 0) {
                        requirement.setParentId(parentId);
                    }
                    requirement.setName(((PnlRequirement)component).getName());
                    requirement.setDescription(((PnlRequirement) component).getDescription());
                    requirement.setId(((PnlRequirement) component).getReqId());
                    requirement.setTypeId(RequirementTypeConst.OTHER.getId());

                    requirementList.add(requirement);
                }
            }
        }
        return requirementList;
    }

    private boolean validateName() {
        if (txtAwardName.isMessageDefault() || txtAwardName.getText().isEmpty()) {
            Util.setError(lblNameError, "Award name cannot be left blank");
            return false;
        }

        for (int i = 0; i < listAwardNames.getModel().getSize(); ++i) {
            String otherAwardName = (String) listAwardNames.getModel().getElementAt(i);
            if (otherAwardName.equalsIgnoreCase(txtAwardName.getText())) {
                Util.setError(lblNameError, "Award name already exists");
                return false;
            }
        }
        return true;
    }

    public void delete() {
        if (listAwardNames.getSelectedValue() == null) {
            return;
        }

        // (do this then scouts have been added to the program)
        // before deleting check to see if the award is used on any scouts

        List<Integer> requirementIdList = new ArrayList<Integer>();
        if (grid > 0) {
            for (Component component : pnlRequirements.getComponents()) {
                if (component instanceof PnlRequirement) {

                    if (((PnlRequirement)component).getReqId() < 0) {
                        continue;
                    }

                    requirementIdList.add(((PnlRequirement)component).getReqId());
                }
            }
        }

        LogicRequirement.deleteList(requirementIdList);
        LogicOtherAward.delete(listAwardNames.getSelectedValue().toString());

        clearData();
    }

    public void update() {
        if (listAwardNames.getSelectedValue() == null) {
            return;
        }

        clearErrors();

        if (txtAwardName.isMessageDefault() || txtAwardName.getText().isEmpty()) {
            Util.setError(lblNameError, "Award name cannot be left blank");
            return;
        }

        for (int i = 0; i < listAwardNames.getModel().getSize(); ++i) {
            String otherAwardName = (String) listAwardNames.getModel().getElementAt(i);
            if (otherAwardName.equalsIgnoreCase(txtAwardName.getText()) && !txtAwardName.getText().equals(listAwardNames.getSelectedValue().toString())) {
                Util.setError(lblNameError, "Award name already exists");
                return;
            }
        }

        OtherAward otherAward = LogicOtherAward.findByName(listAwardNames.getSelectedValue().toString());
        if (otherAward == null) {
            return;
        }

        if (txtImagePath.isMessageDefault()) {
            otherAward.setImgPath("");
        } else {
            otherAward.setImgPath(txtImagePath.getText());
        }
        otherAward.setName(txtAwardName.getText());

        List<Requirement> requirementList = validateRequirements(otherAward.getId());
        if (requirementList == null) return;


        // when editing requirements may need to check who is using them

        LogicRequirement.updateList(requirementList, otherAward.getId(), RequirementTypeConst.OTHER.getId());
        LogicOtherAward.update(otherAward);

        reloadData();
    }

    public void export() {
        ExportDialog dialog = new ExportDialog((JFrame) SwingUtilities.getWindowAncestor(this), PnlBadgeConf.OTHER);
        dialog.setVisible(true);
    }

    public void importData() {
        ImportDialog dialog = new ImportDialog((JFrame) SwingUtilities.getWindowAncestor(this), PnlBadgeConf.OTHER);
        dialog.setVisible(true);

        reloadData();
        populateAwardNameList();
    }

    private void reloadData() {
        if (listAwardNames.getSelectedValue() == null) {
            return;
        }

        int index = listAwardNames.getSelectedIndex();
        String name = txtAwardName.getText();

        clearData();

        for (int i = 0; i < listAwardNames.getModel().getSize(); ++i) {
            if (listAwardNames.getModel().getElementAt(i).toString().equals(name)) {
                index = i;
                break;
            }
        }

        listAwardNames.setSelectedIndex(index);
        listBadgeNamesMouseClicked();
    }

    private void clearData() {
        clearErrors();
        populateAwardNameList();
        lblImage.setIcon(noImageIcon);
        txtImagePath.setDefault();
        txtAwardName.setDefault();
        addNoRequirementsLabel();

        pnlBadgeConf.getBtnDelete().setVisible(false);
        pnlBadgeConf.getBtnUpdate().setVisible(false);
        pnlBadgeConf.getBtnSave().setVisible(false);

        revalidate();
    }

    private void clearErrors() {
        lblNameError.setText("");
        lblNameError.setVisible(false);

        lblReqError.setText("");
        lblReqError.setVisible(false);

        revalidate();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblListName = new JLabel();
        lblGeneralInfo = new JLabel();
        pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        pnlGeneralInfo = new JPanel();
        lblImagePath = new JLabel();
        txtImagePath = new JTextFieldDefaultText();
        btnBrowseImgPath = new JButton();
        lblBadgeName = new JLabel();
        txtAwardName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        pnlSelectedImage = new JPanel();
        lblImage = new JLabel();
        scrollPane3 = new JScrollPane();
        listAwardNames = new JList();
        hSpacer2 = new JPanel(null);
        lblRequirements = new JLabel();
        btnNewRequirement = new JLabel();
        btnDeleteRequirement = new JLabel();
        lblReqError = new JLabel();
        scrollPane2 = new JScrollPane();
        pnlRequirements = new JPanel();

        //======== this ========
        setBackground(Color.white);
        setMaximumSize(new Dimension(1000, 670));
        setMinimumSize(new Dimension(1000, 670));
        setPreferredSize(new Dimension(1000, 670));
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {240, 33, 128, 30, 30, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {45, 0, 152, 45, 399, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- lblListName ----
        lblListName.setText("Awards");
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
        add(lblGeneralInfo, new GridBagConstraints(2, 0, 4, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

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

        //======== pnlGeneralInfo ========
        {
            pnlGeneralInfo.setBackground(Color.white);
            pnlGeneralInfo.setName("pnlGeneralInfo");
            pnlGeneralInfo.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWidths = new int[] {0, 344, 100, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- lblImagePath ----
            lblImagePath.setText("Image Path:");
            lblImagePath.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblImagePath.setForeground(new Color(51, 102, 153));
            lblImagePath.setName("lblImagePath");
            pnlGeneralInfo.add(lblImagePath, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- txtImagePath ----
            txtImagePath.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtImagePath.setMinimumSize(new Dimension(14, 40));
            txtImagePath.setPreferredSize(new Dimension(14, 40));
            txtImagePath.setDefaultText("Path");
            txtImagePath.setDisabledTextColor(Color.gray);
            txtImagePath.setName("txtImagePath");
            pnlGeneralInfo.add(txtImagePath, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
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
            pnlGeneralInfo.add(btnBrowseImgPath, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblBadgeName ----
            lblBadgeName.setText("Name:");
            lblBadgeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblBadgeName.setForeground(new Color(51, 102, 153));
            lblBadgeName.setName("lblBadgeName");
            pnlGeneralInfo.add(lblBadgeName, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- txtAwardName ----
            txtAwardName.setPreferredSize(new Dimension(14, 40));
            txtAwardName.setMinimumSize(new Dimension(14, 40));
            txtAwardName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtAwardName.setDefaultText("Award Name");
            txtAwardName.setName("txtAwardName");
            pnlGeneralInfo.add(txtAwardName, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblNameError ----
            lblNameError.setText("* Error Message");
            lblNameError.setForeground(Color.red);
            lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblNameError.setVisible(false);
            lblNameError.setName("lblNameError");
            pnlGeneralInfo.add(lblNameError, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 0, 10), 0, 0));
        }
        add(pnlGeneralInfo, new GridBagConstraints(5, 1, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 0, 5, 0), 0, 0));

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
        add(pnlSelectedImage, new GridBagConstraints(2, 1, 2, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane3 ========
        {
            scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane3.setName("scrollPane3");

            //---- listAwardNames ----
            listAwardNames.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            listAwardNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
            listAwardNames.setName("listAwardNames");
            listAwardNames.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    listBadgeNamesMouseClicked();
                }
            });
            listAwardNames.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    listBadgeNamesKeyReleased(e);
                }
            });
            scrollPane3.setViewportView(listAwardNames);
        }
        add(scrollPane3, new GridBagConstraints(0, 2, 1, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- hSpacer2 ----
        hSpacer2.setBackground(Color.white);
        hSpacer2.setName("hSpacer2");
        add(hSpacer2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblRequirements ----
        lblRequirements.setText("Requirements");
        lblRequirements.setVerticalAlignment(SwingConstants.BOTTOM);
        lblRequirements.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblRequirements.setForeground(new Color(51, 102, 153));
        lblRequirements.setName("lblRequirements");
        add(lblRequirements, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
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
        btnNewRequirement.setToolTipText("Add new requirement");
        btnNewRequirement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNewRequirement.setName("btnNewRequirement");
        btnNewRequirement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnNewRequirementMouseClicked();
            }
        });
        add(btnNewRequirement, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
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
        add(btnDeleteRequirement, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblReqError ----
        lblReqError.setText("* Error Message");
        lblReqError.setForeground(Color.red);
        lblReqError.setFont(new Font("Tahoma", Font.ITALIC, 11));
        lblReqError.setVerticalAlignment(SwingConstants.BOTTOM);
        lblReqError.setVisible(false);
        lblReqError.setName("lblReqError");
        add(lblReqError, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 20, 5, 10), 0, 0));

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
        add(scrollPane2, new GridBagConstraints(2, 4, 4, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblListName;
    private JLabel lblGeneralInfo;
    private JPanel pnlSearch;
    private JTextFieldDefaultText txtSearchName;
    private JPanel pnlGeneralInfo;
    private JLabel lblImagePath;
    private JTextFieldDefaultText txtImagePath;
    private JButton btnBrowseImgPath;
    private JLabel lblBadgeName;
    private JTextFieldDefaultText txtAwardName;
    private JLabel lblNameError;
    private JPanel pnlSelectedImage;
    private JLabel lblImage;
    private JScrollPane scrollPane3;
    private JList listAwardNames;
    private JPanel hSpacer2;
    private JLabel lblRequirements;
    private JLabel btnNewRequirement;
    private JLabel btnDeleteRequirement;
    private JLabel lblReqError;
    private JScrollPane scrollPane2;
    private JPanel pnlRequirements;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
