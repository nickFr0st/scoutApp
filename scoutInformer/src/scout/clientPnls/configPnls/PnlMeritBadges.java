/*
 * Created by JFormDesigner on Sat Oct 25 11:30:02 MDT 2014
 */

package scout.clientPnls.configPnls;

import constants.RequirementTypeConst;
import guiUtil.CustomChooser;
import guiUtil.JTextFieldDefaultText;
import guiUtil.PnlRequirement;
import scout.clientPnls.IEPnls.ExportDialog;
import scout.clientPnls.IEPnls.ImportDialog;
import scout.clientPnls.PnlBadgeConf;
import scout.dbObjects.Counselor;
import scout.dbObjects.MeritBadge;
import scout.dbObjects.Requirement;
import util.LogicCounselor;
import util.LogicMeritBadge;
import util.LogicRequirement;
import util.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author User #2
 */
public class PnlMeritBadges extends JPanel implements Configuration {

    private int grid;
    private final ImageIcon noImageIcon = new ImageIcon(getClass().getResource("/images/no_image.png"));
    private PnlBadgeConf pnlBadgeConf;
    private DefaultTableModel tableModel;

    public PnlMeritBadges() {
        initComponents();
    }

    public PnlMeritBadges(PnlBadgeConf pnlBadgeConf) {
        this.pnlBadgeConf = pnlBadgeConf;
        initComponents();
        scrollPane2.getVerticalScrollBar().setUnitIncrement(18);
        onShow();
    }

    @Override
    public void onShow() {
        clearData();
    }

    @Override
    public void onHide() {

    }

    @Override
    public void createNew() {
        clearErrors();
        lblImage.setIcon(noImageIcon);
        txtImagePath.setDefault();
        txtBadgeName.setDefault();
        txtRevisionDate.setDefault();
        chkReqForEagle.setSelected(false);

        clearCounselorTable();

        pnlRequirements.removeAll();
        addNoRequirementsLabel();

        pnlRequirements.revalidate();
        pnlRequirements.repaint();

        pnlBadgeConf.getBtnSave().setVisible(true);
        pnlBadgeConf.getBtnUpdate().setVisible(false);
        pnlBadgeConf.getBtnDelete().setVisible(false);

        txtBadgeName.requestFocus();
    }

    private void clearData() {
        clearErrors();
        populateBadgeNameList();
        lblImage.setIcon(noImageIcon);
        txtImagePath.setDefault();
        txtBadgeName.setDefault();
        chkReqForEagle.setSelected(false);
        addNoRequirementsLabel();

        pnlBadgeConf.getBtnDelete().setVisible(false);
        pnlBadgeConf.getBtnUpdate().setVisible(false);
        pnlBadgeConf.getBtnSave().setVisible(false);

        chkShowReqOnly.setSelected(false);
        txtSearchName.setDefault();

        revalidate();
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

    private void resetPnlRequirements() {
        pnlRequirements.removeAll();
        pnlRequirements.setLayout(new BoxLayout(pnlRequirements, BoxLayout.Y_AXIS));
    }

    private void populateBadgeNameList() {
        List<String> filteredList = LogicMeritBadge.getFilteredList(txtSearchName.isMessageDefault() ? "" : txtSearchName.getText(), chkShowReqOnly.isSelected());

        if (filteredList != null) {
            listBadgeNames.setListData(filteredList.toArray());
        }

        listBadgeNames.revalidate();
    }

    private void clearErrors() {
        lblCounselorError.setText("");
        lblCounselorError.setVisible(false);

        lblNameError.setText("");
        lblNameError.setVisible(false);

        lblReqError.setText("");
        lblReqError.setVisible(false);

        lblRevsionDateError.setText("");
        lblRevsionDateError.setVisible(false);
    }

    private void txtSearchNameKeyReleased() {
        filterNameList();
    }

    private void filterNameList() {
        List<String> filteredList = LogicMeritBadge.getFilteredList(txtSearchName.isMessageDefault() ? "" : txtSearchName.getText(), chkShowReqOnly.isSelected());
        if (filteredList == null) {
            return;
        }

        listBadgeNames.setListData(filteredList.toArray());
        listBadgeNames.revalidate();
    }

    private void listBadgeNamesMouseClicked() {
        if (listBadgeNames.getSelectedValue() == null) {
            return;
        }

        clearErrors();

        MeritBadge meritBadge = LogicMeritBadge.findByName(listBadgeNames.getSelectedValue().toString());

        if (meritBadge == null) {
            return;
        }

        pnlBadgeConf.getBtnSave().setVisible(false);
        pnlBadgeConf.getBtnUpdate().setVisible(true);
        pnlBadgeConf.getBtnDelete().setVisible(true);

        txtImagePath.setText(meritBadge.getImgPath());
        txtBadgeName.setText(meritBadge.getName());
        chkReqForEagle.setSelected(meritBadge.isRequiredForEagle());
        txtRevisionDate.setText(Util.DISPLAY_DATE_FORMAT.format(meritBadge.getRevisionDate()));

        URL imgPath = getClass().getResource(meritBadge.getImgPath());
        if (imgPath == null) {
            ImageIcon tryPath = new ImageIcon(meritBadge.getImgPath());
            if (tryPath.getImageLoadStatus() < MediaTracker.COMPLETE) {
                lblImage.setIcon(noImageIcon);
            } else {
                setImage(meritBadge.getImgPath());
            }
        } else {
            setImage(imgPath.getPath());
        }

        resetPnlRequirements();
        grid = 0;

        java.util.List<Requirement> requirementList = LogicRequirement.findAllByParentIdTypeId(meritBadge.getId(), RequirementTypeConst.MERIT_BADGE.getId());
        if (!Util.isEmpty(requirementList)) {
            for (Requirement requirement : requirementList) {
                PnlRequirement pnlRequirement = new PnlRequirement(requirement.getName(), requirement.getDescription(), grid++ > 0, requirement.getId());
                pnlRequirements.add(pnlRequirement);
            }
        } else {
            addNoRequirementsLabel();
        }

        clearCounselorTable();

        java.util.List<Counselor> counselorList = LogicCounselor.findAllByBadgeId(meritBadge.getId());
        if (!Util.isEmpty(counselorList)) {
            for (Counselor counselor : counselorList) {
                Object[] rowData = new Object[]{counselor.getName(), counselor.getPhoneNumber()};
                tableModel.addRow(rowData);
            }
        }


        pnlRequirements.revalidate();
        pnlRequirements.repaint();
    }

    private void clearCounselorTable() {
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
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

    private void listBadgeNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listBadgeNamesMouseClicked();
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
        PnlRequirement pnlRequirement = new PnlRequirement("[name]", "[Description]", grid++ > 0, -1);
        pnlRequirements.add(pnlRequirement);

        pnlRequirement.getTxtReqName().requestFocus();

        pnlRequirements.revalidate();
    }

    private void btnDeleteRequirementMouseClicked() {
        if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == null) {
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

    public void importData() {
        ImportDialog dialog = new ImportDialog((JFrame) SwingUtilities.getWindowAncestor(this), PnlBadgeConf.MERIT_BADGE);
        dialog.setVisible(true);

        reloadData();
        populateBadgeNameList();
    }

    public void export() {
        ExportDialog dialog = new ExportDialog((JFrame) SwingUtilities.getWindowAncestor(this), PnlBadgeConf.MERIT_BADGE);
        dialog.setVisible(true);
    }

    public void delete() {
        if (listBadgeNames.getSelectedValue() == null) {
            return;
        }

        // (do this then scouts have been added to the program)
        // before deleting check to see if advancement is used on any scouts

        for (String badgeName : (List<String>) listBadgeNames.getSelectedValuesList()) {
            MeritBadge meritBadge = LogicMeritBadge.findByName(badgeName);

            List<Integer> requirementIdList = LogicRequirement.findIdsByParentIdTypeId(meritBadge.getId(), RequirementTypeConst.MERIT_BADGE.getId());
            List<Integer> counselorIdList = LogicCounselor.findAllIdsByBadgeId(meritBadge.getId());

            deleteRecords(meritBadge.getId(), requirementIdList, counselorIdList);
        }

        clearData();
    }

    private void deleteRecords(final int meritBadgeId, final List<Integer> requirementIdList, final List<Integer> counselorIdList) {
        Util.processBusy(pnlBadgeConf.getBtnDelete(), true);

        LogicCounselor.deleteList(counselorIdList);
        LogicRequirement.deleteList(requirementIdList);
        LogicMeritBadge.delete(meritBadgeId);

        Util.processBusy(pnlBadgeConf.getBtnDelete(), false);
    }

    public void save() {
        clearErrors();

        if (!validateName()) {
            return;
        }

        if (!validateRevisionDate()) {
            return;
        }

        MeritBadge meritBadge = new MeritBadge();
        meritBadge.setName(txtBadgeName.getText());

        meritBadge.setRequiredForEagle(chkReqForEagle.isSelected());

        if (!txtImagePath.isMessageDefault()) {
            meritBadge.setImgPath(txtImagePath.getText());
        } else {
            meritBadge.setImgPath("");
        }

        setRevisionDate(meritBadge);

        java.util.List<Counselor> counselorList = validateCounselors(-1);
        if (counselorList == null) return;

        java.util.List<Requirement> requirementList = validateRequirements(-1);
        if (requirementList == null) return;

        saveRecords(meritBadge, requirementList, counselorList);

        populateBadgeNameList();

        listBadgeNames.setSelectedValue(txtBadgeName.getText(), true);
        reloadData();
    }

    private void saveRecords(final MeritBadge meritBadge, final List<Requirement> requirementList, final List<Counselor> counselorList) {
        Util.processBusy(pnlBadgeConf.getBtnSave(), true);

        LogicMeritBadge.save(meritBadge);

        for (Counselor counselor : counselorList) {
            counselor.setBadgeId(meritBadge.getId());
        }

        LogicCounselor.saveList(counselorList);

        for (Requirement requirement : requirementList) {
            requirement.setParentId(meritBadge.getId());
        }

        LogicRequirement.saveList(requirementList);

        Util.processBusy(pnlBadgeConf.getBtnSave(), false);
    }

    private void setRevisionDate(MeritBadge meritBadge) {
        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(txtRevisionDate.getText());

        if (!matcher.find()) {
            return;
        }

        int month = Integer.parseInt(matcher.group(1));
        int day = Integer.parseInt(matcher.group(2));
        int year = Integer.parseInt(matcher.group(3));

        Calendar revisionDate = Calendar.getInstance();
        revisionDate.set(year, month - 1, day);

        meritBadge.setRevisionDate(revisionDate.getTime());
    }

    public void update() {
        if (listBadgeNames.getSelectedValue() == null) {
            return;
        }

        clearErrors();

        if (txtBadgeName.isMessageDefault() || txtBadgeName.getText().isEmpty()) {
            Util.setError(lblNameError, "Merit badge name cannot be left blank");
            return;
        }

        for (int i = 0; i < listBadgeNames.getModel().getSize(); ++i) {
            String badgeName = (String) listBadgeNames.getModel().getElementAt(i);
            if (badgeName.equalsIgnoreCase(txtBadgeName.getText()) && !txtBadgeName.getText().equals(listBadgeNames.getSelectedValue().toString())) {
                Util.setError(lblNameError, "Merit badge name " + badgeName + " already exists");
                return;
            }
        }

        MeritBadge meritBadge = LogicMeritBadge.findByName(listBadgeNames.getSelectedValue().toString());
        if (meritBadge == null) {
            return;
        }

        if (!validateRevisionDate()) {
            return;
        }

        meritBadge.setRequiredForEagle(chkReqForEagle.isSelected());

        if (txtImagePath.isMessageDefault()) {
            meritBadge.setImgPath("");
        } else {
            meritBadge.setImgPath(txtImagePath.getText());
        }
        meritBadge.setName(txtBadgeName.getText());

        setRevisionDate(meritBadge);

        List<Counselor> counselorList = validateCounselors(meritBadge.getId());
        if (counselorList == null) return;

        List<Requirement> requirementList = validateRequirements(meritBadge.getId());
        if (requirementList == null) return;

        updateRecords(meritBadge, requirementList, counselorList);

        reloadData();
    }

    private void updateRecords(final MeritBadge meritBadge, final List<Requirement> requirementList, final List<Counselor> counselorList) {
        Util.processBusy(pnlBadgeConf.getBtnUpdate(), true);

        LogicCounselor.updateList(counselorList, meritBadge.getId());
        LogicRequirement.updateList(requirementList, meritBadge.getId(), RequirementTypeConst.MERIT_BADGE.getId());
        LogicMeritBadge.update(meritBadge);

        Util.processBusy(pnlBadgeConf.getBtnUpdate(), false);
    }

    private List<Counselor> validateCounselors(int badgeId) {
        List<Counselor> counselorList = new ArrayList<Counselor>();

        Set<String> counselorNameSet = new HashSet<String>();

        if (tableModel.getRowCount() <= 0) {
            return counselorList;
        }

        for (int i = 0; i < tableModel.getRowCount(); ++i) {
            String counselorName = (String)tableModel.getValueAt(i, 0);

            if (Util.isEmpty(counselorName)) {
                Util.setError(lblCounselorError, "Counselor name cannot be left blank");
                return null;
            }

            if (!counselorNameSet.add(counselorName)) {
                Util.setError(lblCounselorError, "Counselor name '" + counselorName + "' already exists");
                return null;
            }

            String phoneNumber = (String)tableModel.getValueAt(i, 1);
            if (Util.isEmpty(phoneNumber)) {
                Util.setError(lblCounselorError, "Counselor phone number cannot be left blank");
                return null;
            }

            if (!Util.validatePhoneNumber(phoneNumber)) {
                Util.setError(lblCounselorError, "Invalid phone number format: " + phoneNumber);
                return null;
            }

            Counselor counselor = new Counselor();
            if (badgeId > 0) {
                counselor.setBadgeId(badgeId);
            }
            counselor.setName(counselorName);
            counselor.setPhoneNumber((String) tableModel.getValueAt(i, 1));

            counselorList.add(counselor);
        }

        return counselorList;
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
                    requirement.setTypeId(RequirementTypeConst.MERIT_BADGE.getId());

                    requirementList.add(requirement);
                }
            }
        }
        return requirementList;
    }

    private boolean validateName() {
        if (txtBadgeName.isMessageDefault() || txtBadgeName.getText().isEmpty()) {
            Util.setError(lblNameError, "Merit badge name cannot be left blank");
            return false;
        }

        for (String meritBadgeName : LogicMeritBadge.getNameList()) {
            if (meritBadgeName.equalsIgnoreCase(txtBadgeName.getText())) {
                Util.setError(lblNameError, "Merit badge name already exists");
                return false;
            }
        }
        return true;
    }

    private void reloadData() {
        if (listBadgeNames.getSelectedValue() == null) {
            return;
        }

        int index = listBadgeNames.getSelectedIndex();
        String name = txtBadgeName.getText();

        clearData();

        for (int i = 0; i < listBadgeNames.getModel().getSize(); ++i) {
            if (listBadgeNames.getModel().getElementAt(i).toString().equals(name)) {
                index = i;
                break;
            }
        }

        listBadgeNames.setSelectedIndex(index);
        listBadgeNamesMouseClicked();
    }

    private void createUIComponents() {
        tableModel = new DefaultTableModel(new Object[] {"Name", "Phone Number"}, 0);

        tblCounselors = new JTable();
        tblCounselors.setBackground(Color.WHITE);

        JTableHeader header = tblCounselors.getTableHeader();
        header.setBackground(new Color(51, 102, 153));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Tahoma", Font.PLAIN, 14));


        tblCounselors.setModel(tableModel);
        tblCounselors.setSurrendersFocusOnKeystroke(true);
    }

    private void btnNewCounselorMouseClicked() {
        CounselorDialog dialog = new CounselorDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);

        if (dialog.getBtnChoice() == CounselorDialog.BTN_OK) {
            Counselor counselor = dialog.getCounselor();
            Object[] row = new Object[] {counselor.getName(), counselor.getPhoneNumber()};
            tableModel.addRow(row);
        }
    }

    private void btnDeleteCounselorMouseClicked() {
        if (tblCounselors.getSelectedRow() < 0) {
            return;
        }

        tableModel.removeRow(tblCounselors.getSelectedRow());
    }

    private void chkShowReqOnlyActionPerformed() {
        filterNameList();
    }

    private void txtRevisionDateKeyReleased() {
        checkRevisionDate();
    }

    private void txtRevisionDateFocusLost() {
        checkRevisionDate();
    }

    private void checkRevisionDate() {
        clearRevisionDateErrors();
        validateRevisionDate();
    }

    private boolean validateRevisionDate() {

        if (txtRevisionDate.isMessageDefault() || Util.isEmpty(txtRevisionDate.getText())) {
            Util.setError(lblRevsionDateError, "Cannot leave revision date blank");
            return false;
        }

        if (!txtRevisionDate.getText().matches(Util.DATE_PATTERN)) {
            Util.setError(lblRevsionDateError, "invalid date format");
            return false;
        }

        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(txtRevisionDate.getText());

        if (matcher.find()) {
            int month = Integer.parseInt(matcher.group(1));
            int day = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            if (month > 12 || month < 1) {
                Util.setError(lblRevsionDateError, "invalid month");
                return false;
            }

            if (Util.isThirtyOneDayMonth(month)) {
                if (day > 31 || month < 0) {
                    Util.setError(lblRevsionDateError, "invalid day");
                    return false;
                }
            } else if (Util.isThirtyDayMonth(month)) {
                if (day > 30 || month < 0) {
                    Util.setError(lblRevsionDateError, "invalid day");
                    return false;
                }
            } else {
                boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));

                if (isLeapYear) {
                    if (day > 29 || month < 0) {
                        Util.setError(lblRevsionDateError, "invalid day");
                        return false;
                    }
                } else {
                    if (day > 28 || month < 0) {
                        Util.setError(lblRevsionDateError, "invalid day");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void clearRevisionDateErrors() {
        lblRevsionDateError.setText("");
        lblRevsionDateError.setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        createUIComponents();

        JLabel lblListName = new JLabel();
        JLabel lblGeneralInfo = new JLabel();
        JLabel lblRequirements = new JLabel();
        btnNewRequirement = new JLabel();
        btnDeleteRequirement = new JLabel();
        lblReqError = new JLabel();
        chkShowReqOnly = new JCheckBox();
        pnlSelectedImage = new JPanel();
        lblImage = new JLabel();
        scrollPane2 = new JScrollPane();
        pnlRequirements = new JPanel();
        pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        scrollPane3 = new JScrollPane();
        listBadgeNames = new JList();
        JPanel hSpacer1 = new JPanel(null);
        JPanel hSpacer2 = new JPanel(null);
        pnlGeneralInfo = new JPanel();
        txtImagePath = new JTextFieldDefaultText();
        btnBrowseImgPath = new JButton();
        txtBadgeName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        txtRevisionDate = new JTextFieldDefaultText();
        lblRevsionDateError = new JLabel();
        chkReqForEagle = new JCheckBox();
        JLabel lblMBCounselors = new JLabel();
        btnNewCounselor = new JLabel();
        btnDeleteCounselor = new JLabel();
        lblCounselorError = new JLabel();
        scrollPane1 = new JScrollPane();

        //======== this ========
        setMaximumSize(new Dimension(1000, 670));
        setMinimumSize(new Dimension(1000, 670));
        setPreferredSize(new Dimension(1000, 670));
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {240, 33, 158, 44, 30, 30, 30, 33, 132, 30, 30, 177, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {45, 0, 30, 122, 179, 45, 0, 166, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

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

        //---- lblReqError ----
        lblReqError.setText("* Error Message");
        lblReqError.setForeground(Color.red);
        lblReqError.setFont(new Font("Tahoma", Font.ITALIC, 11));
        lblReqError.setVerticalAlignment(SwingConstants.BOTTOM);
        lblReqError.setName("lblReqError");
        add(lblReqError, new GridBagConstraints(11, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- chkShowReqOnly ----
        chkShowReqOnly.setText("Show Required Only");
        chkShowReqOnly.setFont(new Font("Tahoma", Font.PLAIN, 14));
        chkShowReqOnly.setBackground(Color.white);
        chkShowReqOnly.setForeground(new Color(51, 102, 153));
        chkShowReqOnly.setFocusPainted(false);
        chkShowReqOnly.setName("chkShowReqOnly");
        chkShowReqOnly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chkShowReqOnlyActionPerformed();
            }
        });
        add(chkShowReqOnly, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

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
        add(pnlSelectedImage, new GridBagConstraints(2, 1, 1, 3, 0.0, 0.0,
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
        add(scrollPane2, new GridBagConstraints(8, 1, 4, 7, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

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
        add(pnlSearch, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 6), 0, 0));

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
        add(scrollPane3, new GridBagConstraints(0, 3, 1, 5, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- hSpacer1 ----
        hSpacer1.setOpaque(false);
        hSpacer1.setName("hSpacer1");
        add(hSpacer1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer2 ----
        hSpacer2.setOpaque(false);
        hSpacer2.setName("hSpacer2");
        add(hSpacer2, new GridBagConstraints(7, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== pnlGeneralInfo ========
        {
            pnlGeneralInfo.setBackground(Color.white);
            pnlGeneralInfo.setName("pnlGeneralInfo");
            pnlGeneralInfo.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWidths = new int[] {205, 100, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

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

            //---- txtRevisionDate ----
            txtRevisionDate.setPreferredSize(new Dimension(14, 40));
            txtRevisionDate.setMinimumSize(new Dimension(14, 40));
            txtRevisionDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtRevisionDate.setDefaultText("Revision Date: ex.(MM/DD/YYYY)");
            txtRevisionDate.setToolTipText("Merit badge last revision date");
            txtRevisionDate.setName("txtRevisionDate");
            txtRevisionDate.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    txtRevisionDateKeyReleased();
                }
            });
            txtRevisionDate.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    txtRevisionDateFocusLost();
                }
            });
            pnlGeneralInfo.add(txtRevisionDate, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblRevsionDateError ----
            lblRevsionDateError.setText("* Error Message");
            lblRevsionDateError.setForeground(Color.red);
            lblRevsionDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblRevsionDateError.setName("lblRevsionDateError");
            pnlGeneralInfo.add(lblRevsionDateError, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 20, 5, 10), 0, 0));

            //---- chkReqForEagle ----
            chkReqForEagle.setText("Required for Eagle");
            chkReqForEagle.setBackground(Color.white);
            chkReqForEagle.setFont(new Font("Tahoma", Font.PLAIN, 14));
            chkReqForEagle.setForeground(new Color(51, 102, 153));
            chkReqForEagle.setName("chkReqForEagle");
            pnlGeneralInfo.add(chkReqForEagle, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(pnlGeneralInfo, new GridBagConstraints(2, 4, 5, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 0, 5, 5), 0, 0));

        //---- lblMBCounselors ----
        lblMBCounselors.setText("Merit Badge Counselors");
        lblMBCounselors.setVerticalAlignment(SwingConstants.BOTTOM);
        lblMBCounselors.setFont(new Font("Vijaya", Font.PLAIN, 24));
        lblMBCounselors.setForeground(new Color(51, 102, 153));
        lblMBCounselors.setName("lblMBCounselors");
        add(lblMBCounselors, new GridBagConstraints(2, 5, 2, 1, 0.0, 0.0,
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
        btnNewCounselor.setToolTipText("Add new counselor");
        btnNewCounselor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNewCounselor.setName("btnNewCounselor");
        btnNewCounselor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnNewCounselorMouseClicked();
            }
        });
        add(btnNewCounselor, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
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
        btnDeleteCounselor.setToolTipText("Delete selected counselor");
        btnDeleteCounselor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDeleteCounselor.setName("btnDeleteCounselor");
        btnDeleteCounselor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnDeleteCounselorMouseClicked();
            }
        });
        add(btnDeleteCounselor, new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblCounselorError ----
        lblCounselorError.setText("* Error Message");
        lblCounselorError.setForeground(Color.red);
        lblCounselorError.setFont(new Font("Tahoma", Font.ITALIC, 11));
        lblCounselorError.setVerticalAlignment(SwingConstants.BOTTOM);
        lblCounselorError.setName("lblCounselorError");
        add(lblCounselorError, new GridBagConstraints(2, 6, 5, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 20, 5, 15), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setBackground(Color.white);
            scrollPane1.setName("scrollPane1");

            //---- tblCounselors ----
            tblCounselors.setBackground(Color.white);
            tblCounselors.setFillsViewportHeight(true);
            tblCounselors.setFont(new Font("Tahoma", Font.PLAIN, 14));
            tblCounselors.setName("tblCounselors");
            scrollPane1.setViewportView(tblCounselors);
        }
        add(scrollPane1, new GridBagConstraints(2, 7, 5, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel btnNewRequirement;
    private JLabel btnDeleteRequirement;
    private JLabel lblReqError;
    private JCheckBox chkShowReqOnly;
    private JPanel pnlSelectedImage;
    private JLabel lblImage;
    private JScrollPane scrollPane2;
    private JPanel pnlRequirements;
    private JPanel pnlSearch;
    private JTextFieldDefaultText txtSearchName;
    private JScrollPane scrollPane3;
    private JList listBadgeNames;
    private JPanel pnlGeneralInfo;
    private JTextFieldDefaultText txtImagePath;
    private JButton btnBrowseImgPath;
    private JTextFieldDefaultText txtBadgeName;
    private JLabel lblNameError;
    private JTextFieldDefaultText txtRevisionDate;
    private JLabel lblRevsionDateError;
    private JCheckBox chkReqForEagle;
    private JLabel btnNewCounselor;
    private JLabel btnDeleteCounselor;
    private JLabel lblCounselorError;
    private JScrollPane scrollPane1;
    private JTable tblCounselors;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
