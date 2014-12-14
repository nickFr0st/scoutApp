/*
 * Created by JFormDesigner on Thu Oct 16 21:50:20 MDT 2014
 */

package scout.clientPnls.configPnls;

import constants.ModuleTypeConst;
import guiUtil.JTextFieldDefaultText;
import scout.clientPnls.IEPnls.ExportDialog;
import scout.clientPnls.IEPnls.ImportDialog;
import scout.clientPnls.PnlBadgeConf;
import scout.dbObjects.Camp;
import scout.dbObjects.ScoutCamp;
import util.LogicCamp;
import util.LogicScout;
import util.LogicScoutCamp;
import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author User #2
 */
@SuppressWarnings("MagicConstant")
public class PnlCamp extends JPanel implements Configuration {
    private PnlBadgeConf pnlBadgeConf;
    private List<String> availableScoutList;
    private List<String> participatedScoutList;
    private Camp camp;

    public PnlCamp() {
        initComponents();
    }

    public PnlCamp(PnlBadgeConf pnlBadgeConf) {
        this.pnlBadgeConf = pnlBadgeConf;
        camp = new Camp();
        initComponents();
        availableScoutList = new ArrayList<String>();
        participatedScoutList = new ArrayList<String>();

        onShow();
    }

    @Override
    public void onShow() {
        clearData();
    }

    private void populateCampNameList() {
        List<String> campNameList = LogicCamp.getNameList();

        if (campNameList != null) {
            listCampNames.setListData(campNameList.toArray());
        }

        listCampNames.revalidate();
    }

    @Override
    public void onHide() {

    }

    @Override
    public void createNew() {
        clearErrors();
        clearData();

        camp = new Camp();

        pnlBadgeConf.getBtnSave().setVisible(true);

        availableScoutList = LogicScout.getNameList();
        if (availableScoutList == null) {
            availableScoutList = new ArrayList<String>();
        }

        listAvailable.setListData(availableScoutList.toArray());

        revalidate();
        repaint();

        txtCampName.requestFocus();
    }

    private void txtSearchNameKeyReleased() {
        List<String> campNameList = LogicCamp.getNameList();
        if (campNameList == null) {
            return;
        }

        if (txtSearchName.isMessageDefault()) {
            listCampNames.setListData(campNameList.toArray());
            listCampNames.revalidate();
            return;
        }

        List<String> filteredList = new ArrayList<String>();
        for (String campName : campNameList) {
            if (campName.toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(campName);
            }
        }

        listCampNames.setListData(filteredList.toArray());
        listCampNames.revalidate();
    }

    private void listCampNamesMouseClicked() {
        if (listCampNames.getSelectedValue() == null) {
            return;
        }

        clearErrors();

        camp = LogicCamp.findByName(listCampNames.getSelectedValue().toString());
        resetParticipatedList();

        if (camp == null) {
            camp = new Camp();
            return;
        }

        pnlBadgeConf.getBtnSave().setVisible(false);
        pnlBadgeConf.getBtnUpdate().setVisible(true);
        pnlBadgeConf.getBtnDelete().setVisible(true);

        txtCampName.setText(camp.getName());
        txtCampLocation.setText(camp.getLocation());
        txtCampDate.setText(Util.DISPLAY_DATE_FORMAT.format(camp.getDate()));
        txtCampDuration.setText(String.valueOf(camp.getDuration()));
        txtNotes.setText(camp.getNote());

        availableScoutList = LogicScout.getNameList();
        if (availableScoutList == null) {
            availableScoutList = new ArrayList<String>();
        }

        List<ScoutCamp> scoutCampList = LogicScoutCamp.findAllByCampId(camp.getId());
        if (!Util.isEmpty(scoutCampList)) {
            List<Integer> scoutIdList = new ArrayList<Integer>();
            for (ScoutCamp scoutCamp : scoutCampList) {
                scoutIdList.add(scoutCamp.getScoutId());
            }

            participatedScoutList.addAll(LogicScout.findAllNamesByIdList(scoutIdList));

            listParticipated.setListData(participatedScoutList.toArray());
            listParticipated.revalidate();
            listParticipated.repaint();

            availableScoutList.removeAll(participatedScoutList);
        }

        listAvailable.setListData(availableScoutList.toArray());
        listAvailable.revalidate();
        listAvailable.repaint();
    }

    private void listCampNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listCampNamesMouseClicked();
        }
    }

    public void save() {
        clearErrors();

        if (!validateName() || !validateLocation() || !validateDate() || !validateDuration()) {
            return;
        }

        camp.setName(txtCampName.getText());
        camp.setLocation(txtCampLocation.getText());
        setCampDate();
        camp.setDuration(Integer.parseInt(txtCampDuration.getText()));
        camp.setNote(txtNotes.getText());

        List<Integer> scoutIdList = LogicScout.findAllIdsByNameList(participatedScoutList);

        LogicCamp.save(camp);

        if (!Util.isEmpty(scoutIdList)) {
            LogicScoutCamp.save(camp.getId(), scoutIdList);
        }

        populateCampNameList();

        listCampNames.setSelectedValue(txtCampDate.getText(), true);
        reloadData();
    }

    private void setCampDate() {
        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(txtCampDate.getText());

        if (!matcher.find()) {
            return;
        }

        int month = Integer.parseInt(matcher.group(1));
        int day = Integer.parseInt(matcher.group(2));
        int year = Integer.parseInt(matcher.group(3));

        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day);

        camp.setDate(date.getTime());
    }

    private boolean validateName() {
        clearNameErrors();

        if (txtCampName.isMessageDefault() || txtCampName.getText().isEmpty()) {
            Util.setError(lblNameError, "Camp name cannot be left blank");
            return false;
        }

        if (camp.getId() < 0) {
            for (int i = 0; i < listCampNames.getModel().getSize(); ++i) {
                String campName = (String) listCampNames.getModel().getElementAt(i);
                if (campName.equalsIgnoreCase(txtCampName.getText())) {
                    Util.setError(lblNameError, "Camp name already exists");
                    return false;
                }
            }
        } else {
            for (int i = 0; i < listCampNames.getModel().getSize(); ++i) {
                String campName = (String) listCampNames.getModel().getElementAt(i);
                if (campName.equalsIgnoreCase(txtCampName.getText()) && !txtCampName.getText().equals(listCampNames.getSelectedValue().toString())) {
                    Util.setError(lblNameError, "Camp name already exists");
                    return false;
                }
            }
        }

        return true;
    }

    private boolean validateLocation() {
        clearLocationErrors();

        if (txtCampLocation.isMessageDefault() || txtCampLocation.getText().isEmpty()) {
            Util.setError(lblLocationError, "Camp location cannot be left blank");
            return false;
        }

        return true;
    }

    private boolean validateDate() {
        clearDateErrors();

        if (txtCampDate.isMessageDefault() || Util.isEmpty(txtCampDate.getText())) {
            Util.setError(lblDateError, "Cannot leave camp date blank");
            return false;
        }

        if (!txtCampDate.getText().matches(Util.DATE_PATTERN)) {
            Util.setError(lblDateError, "invalid date format");
            return false;
        }

        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(lblDateError.getText());

        if (matcher.find()) {
            int month = Integer.parseInt(matcher.group(1));
            int day = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            if (month > 12 || month < 1) {
                Util.setError(lblDateError, "invalid month");
                return false;
            }

            if (Util.isThirtyOneDayMonth(month)) {
                if (day > 31 || month < 0) {
                    Util.setError(lblDateError, "invalid day");
                    return false;
                }
            } else if (Util.isThirtyDayMonth(month)) {
                if (day > 30 || month < 0) {
                    Util.setError(lblDateError, "invalid day");
                    return false;
                }
            } else {
                boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));

                if (isLeapYear) {
                    if (day > 29 || month < 0) {
                        Util.setError(lblDateError, "invalid day");
                        return false;
                    }
                } else {
                    if (day > 28 || month < 0) {
                        Util.setError(lblDateError, "invalid day");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean validateDuration() {
        clearDurationErrors();

        if (txtCampDuration.isMessageDefault() || txtCampDuration.getText().isEmpty()) {
            Util.setError(lblDurationError, "Night count cannot be left blank");
            return false;
        }

        if (!txtCampDuration.getText().matches("[1-9]+")) {
            Util.setError(lblDurationError, "Night count must be a numeric value");
            return false;
        }

        return true;
    }

    public void delete() {
        if (listCampNames.getSelectedValue() == null) {
            return;
        }

        if (JOptionPane.showConfirmDialog((JFrame)getTopLevelAncestor(), "Are you sure you want to delete the selected camp(s)?", "Delete Camp", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }

        Util.processBusy(pnlBadgeConf.getBtnUpdate(), true);

        for (String campName : (List<String>) listCampNames.getSelectedValuesList()) {
            Camp camp = LogicCamp.findByName(campName);

            LogicScoutCamp.deleteAllByCampId(camp.getId());
            LogicCamp.delete(camp.getId());
        }

        Util.processBusy(pnlBadgeConf.getBtnUpdate(), false);
        clearData();
    }

    public void update() {
        if (listCampNames.getSelectedValue() == null) {
            return;
        }

        clearErrors();

        Util.processBusy(pnlBadgeConf.getBtnUpdate(), true);

        if (!validateName() || !validateLocation() || !validateDate() || !validateDuration()) {
            return;
        }

        camp.setName(txtCampName.getText());
        camp.setLocation(txtCampLocation.getText());
        setCampDate();
        camp.setDuration(Integer.parseInt(txtCampDuration.getText()));
        camp.setNote(txtNotes.getText());

        LogicScoutCamp.deleteAllByCampId(camp.getId());

        List<Integer> scoutIdList = LogicScout.findAllIdsByNameList(participatedScoutList);

        LogicCamp.update(camp);

        if (!Util.isEmpty(scoutIdList)) {
            LogicScoutCamp.save(camp.getId(), scoutIdList);
        }

        Util.processBusy(pnlBadgeConf.getBtnUpdate(), false);

        reloadData();
    }

    public void export() {
        ExportDialog dialog = new ExportDialog((JFrame) SwingUtilities.getWindowAncestor(this), ModuleTypeConst.CAMP_OUT);
        dialog.setVisible(true);
    }

    public void importData() {
        ImportDialog dialog = new ImportDialog((JFrame) SwingUtilities.getWindowAncestor(this), ModuleTypeConst.CAMP_OUT);
        dialog.setVisible(true);

        reloadData();
        populateCampNameList();
    }

    private void reloadData() {
        if (listCampNames.getSelectedValue() == null) {
            return;
        }

        int index = listCampNames.getSelectedIndex();
        String name = txtCampName.getText();

        clearData();

        for (int i = 0; i < listCampNames.getModel().getSize(); ++i) {
            if (listCampNames.getModel().getElementAt(i).toString().equals(name)) {
                index = i;
                break;
            }
        }

        listCampNames.setSelectedIndex(index);
        listCampNamesMouseClicked();
    }

    private void clearData() {
        clearErrors();
        populateCampNameList();

        txtCampName.setDefault();
        txtCampLocation.setDefault();
        txtCampDate.setDefault();
        txtCampDuration.setDefault();
        txtNotes.setText("");

        availableScoutList.clear();
        participatedScoutList.clear();

        listAvailable.setListData(availableScoutList.toArray());
        listParticipated.setListData(participatedScoutList.toArray());

        pnlBadgeConf.getBtnDelete().setVisible(false);
        pnlBadgeConf.getBtnUpdate().setVisible(false);
        pnlBadgeConf.getBtnSave().setVisible(false);

        revalidate();
    }

    private void clearErrors() {
        lblNameError.setText("");
        lblNameError.setVisible(false);

        lblLocationError.setText("");
        lblLocationError.setVisible(false);

        lblDateError.setText("");
        lblDateError.setVisible(false);

        lblDurationError.setText("");
        lblDurationError.setVisible(false);

        revalidate();
    }

    private void clearNameErrors() {
        lblNameError.setText("");
        lblNameError.setVisible(false);
        revalidate();
    }

    private void clearLocationErrors() {
        lblLocationError.setText("");
        lblLocationError.setVisible(false);
        revalidate();
    }

    private void clearDateErrors() {
        lblDateError.setText("");
        lblDateError.setVisible(false);
        revalidate();
    }

    private void clearDurationErrors() {
        lblDurationError.setText("");
        lblDurationError.setVisible(false);
        revalidate();
    }

    private void btnAddActionPerformed() {
        if (listAvailable.getSelectedValue() == null) {
            return;
        }

        List<String> nameList = listAvailable.getSelectedValuesList();
        availableScoutList.removeAll(nameList);

        for (String name : nameList) {
            participatedScoutList.add(name.toString());
        }

        populateAvailableList();
        populateParticipatedList();
    }

    private void populateAvailableList() {
        listAvailable.setListData(availableScoutList.toArray());
        listAvailable.revalidate();
        listAvailable.repaint();
    }

    private void resetParticipatedList() {
        participatedScoutList.clear();
        listParticipated.setListData(participatedScoutList.toArray());
        listParticipated.revalidate();
    }

    private void populateParticipatedList() {
        listParticipated.setListData(participatedScoutList.toArray());
        listParticipated.revalidate();
        listParticipated.repaint();
    }

    private void btnRemoveActionPerformed() {
        if (listParticipated.getSelectedValue() == null) {
            return;
        }

        List<String> nameList = listParticipated.getSelectedValuesList();
        participatedScoutList.removeAll(nameList);

        for (String name : nameList) {
            availableScoutList.add(name.toString());
        }

        populateAvailableList();
        populateParticipatedList();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblListName = new JLabel();
        lblGeneralInfo = new JLabel();
        pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        panel1 = new JPanel();
        lblCampName = new JLabel();
        txtCampName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblCampLocation = new JLabel();
        txtCampLocation = new JTextFieldDefaultText();
        lblLocationError = new JLabel();
        lblCampDate = new JLabel();
        txtCampDate = new JTextFieldDefaultText();
        lblDateError = new JLabel();
        lblCampDuration = new JLabel();
        txtCampDuration = new JTextFieldDefaultText();
        lblDurationError = new JLabel();
        panel2 = new JPanel();
        lblAvailable = new JLabel();
        lblWhoCame = new JLabel();
        scrollPane1 = new JScrollPane();
        listAvailable = new JList();
        btnAdd = new JButton();
        scrollPane2 = new JScrollPane();
        listParticipated = new JList();
        btnRemove = new JButton();
        lblNotes = new JLabel();
        scrollPane4 = new JScrollPane();
        txtNotes = new JTextArea();
        scrollPane3 = new JScrollPane();
        listCampNames = new JList();
        hSpacer2 = new JPanel(null);

        //======== this ========
        setBackground(Color.white);
        setMaximumSize(new Dimension(1000, 670));
        setMinimumSize(new Dimension(1000, 670));
        setPreferredSize(new Dimension(1000, 670));
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {240, 33, 20, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {45, 0, 152, 45, 404, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- lblListName ----
        lblListName.setText("Campouts");
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
        add(lblGeneralInfo, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {122, 128, 152, 123, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {45, 45, 45, 45, 21, 222, 45, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

            //---- lblCampName ----
            lblCampName.setText("Camp Name:");
            lblCampName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblCampName.setForeground(new Color(51, 102, 153));
            lblCampName.setName("lblCampName");
            panel1.add(lblCampName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- txtCampName ----
            txtCampName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtCampName.setDefaultText("Name");
            txtCampName.setName("txtCampName");
            txtCampName.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    validateName();
                }
            });
            txtCampName.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    validateName();
                }
            });
            panel1.add(txtCampName, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblNameError ----
            lblNameError.setText("* Error Message");
            lblNameError.setForeground(Color.red);
            lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblNameError.setVerticalAlignment(SwingConstants.BOTTOM);
            lblNameError.setName("lblNameError");
            panel1.add(lblNameError, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- lblCampLocation ----
            lblCampLocation.setText("Camp Location:");
            lblCampLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblCampLocation.setForeground(new Color(51, 102, 153));
            lblCampLocation.setName("lblCampLocation");
            panel1.add(lblCampLocation, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- txtCampLocation ----
            txtCampLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtCampLocation.setDefaultText("Location");
            txtCampLocation.setName("txtCampLocation");
            txtCampLocation.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    validateLocation();
                }
            });
            txtCampLocation.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    validateLocation();
                }
            });
            panel1.add(txtCampLocation, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblLocationError ----
            lblLocationError.setText("* Error Message");
            lblLocationError.setForeground(Color.red);
            lblLocationError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblLocationError.setVerticalAlignment(SwingConstants.BOTTOM);
            lblLocationError.setName("lblLocationError");
            panel1.add(lblLocationError, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- lblCampDate ----
            lblCampDate.setText("Camp Start Date:");
            lblCampDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblCampDate.setForeground(new Color(51, 102, 153));
            lblCampDate.setName("lblCampDate");
            panel1.add(lblCampDate, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- txtCampDate ----
            txtCampDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtCampDate.setDefaultText("MM/DD/YYYY");
            txtCampDate.setName("txtCampDate");
            txtCampDate.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    validateDate();
                }
            });
            txtCampDate.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    validateDate();
                }
            });
            panel1.add(txtCampDate, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblDateError ----
            lblDateError.setText("* Error Message");
            lblDateError.setForeground(Color.red);
            lblDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblDateError.setVerticalAlignment(SwingConstants.BOTTOM);
            lblDateError.setName("lblDateError");
            panel1.add(lblDateError, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- lblCampDuration ----
            lblCampDuration.setText("Number of Nights:");
            lblCampDuration.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblCampDuration.setForeground(new Color(51, 102, 153));
            lblCampDuration.setName("lblCampDuration");
            panel1.add(lblCampDuration, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- txtCampDuration ----
            txtCampDuration.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtCampDuration.setDefaultText("Night count");
            txtCampDuration.setName("txtCampDuration");
            txtCampDuration.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    validateDuration();
                }
            });
            txtCampDuration.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    validateDuration();
                }
            });
            panel1.add(txtCampDuration, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblDurationError ----
            lblDurationError.setText("* Error Message");
            lblDurationError.setForeground(Color.red);
            lblDurationError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblDurationError.setVerticalAlignment(SwingConstants.BOTTOM);
            lblDurationError.setName("lblDurationError");
            panel1.add(lblDurationError, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 5, 5), 0, 0));

            //======== panel2 ========
            {
                panel2.setBackground(Color.white);
                panel2.setName("panel2");
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {265, 90, 260, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 83, 0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

                //---- lblAvailable ----
                lblAvailable.setText("Available Scouts");
                lblAvailable.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblAvailable.setForeground(new Color(51, 102, 153));
                lblAvailable.setName("lblAvailable");
                panel2.add(lblAvailable, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- lblWhoCame ----
                lblWhoCame.setText("Who Came");
                lblWhoCame.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblWhoCame.setForeground(new Color(51, 102, 153));
                lblWhoCame.setName("lblWhoCame");
                panel2.add(lblWhoCame, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== scrollPane1 ========
                {
                    scrollPane1.setName("scrollPane1");

                    //---- listAvailable ----
                    listAvailable.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listAvailable.setName("listAvailable");
                    scrollPane1.setViewportView(listAvailable);
                }
                panel2.add(scrollPane1, new GridBagConstraints(0, 1, 1, 2, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnAdd ----
                btnAdd.setText("Add");
                btnAdd.setMinimumSize(new Dimension(81, 40));
                btnAdd.setPreferredSize(new Dimension(81, 40));
                btnAdd.setMaximumSize(new Dimension(81, 40));
                btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnAdd.setForeground(Color.white);
                btnAdd.setBackground(new Color(51, 156, 229));
                btnAdd.setFocusPainted(false);
                btnAdd.setName("btnAdd");
                btnAdd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnAddActionPerformed();
                    }
                });
                panel2.add(btnAdd, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== scrollPane2 ========
                {
                    scrollPane2.setName("scrollPane2");

                    //---- listParticipated ----
                    listParticipated.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listParticipated.setName("listParticipated");
                    scrollPane2.setViewportView(listParticipated);
                }
                panel2.add(scrollPane2, new GridBagConstraints(2, 1, 1, 2, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- btnRemove ----
                btnRemove.setText("Remove");
                btnRemove.setPreferredSize(new Dimension(82, 40));
                btnRemove.setMinimumSize(new Dimension(82, 40));
                btnRemove.setMaximumSize(new Dimension(82, 40));
                btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnRemove.setForeground(Color.white);
                btnRemove.setBackground(new Color(206, 0, 0));
                btnRemove.setFocusPainted(false);
                btnRemove.setName("btnRemove");
                btnRemove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnRemoveActionPerformed();
                    }
                });
                panel2.add(btnRemove, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 5), 0, 0));
            }
            panel1.add(panel2, new GridBagConstraints(0, 5, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblNotes ----
            lblNotes.setText("Notes:");
            lblNotes.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblNotes.setForeground(new Color(51, 102, 153));
            lblNotes.setName("lblNotes");
            panel1.add(lblNotes, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 5), 0, 0));

            //======== scrollPane4 ========
            {
                scrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane4.setName("scrollPane4");

                //---- txtNotes ----
                txtNotes.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtNotes.setName("txtNotes");
                scrollPane4.setViewportView(txtNotes);
            }
            panel1.add(scrollPane4, new GridBagConstraints(0, 7, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(2, 1, 1, 4, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 10, 5, 10), 0, 0));

        //======== scrollPane3 ========
        {
            scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane3.setName("scrollPane3");

            //---- listCampNames ----
            listCampNames.setBorder(new LineBorder(new Color(51, 102, 153), 2));
            listCampNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
            listCampNames.setName("listCampNames");
            listCampNames.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    listCampNamesMouseClicked();
                }
            });
            listCampNames.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    listCampNamesKeyReleased(e);
                }
            });
            scrollPane3.setViewportView(listCampNames);
        }
        add(scrollPane3, new GridBagConstraints(0, 2, 1, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- hSpacer2 ----
        hSpacer2.setBackground(Color.white);
        hSpacer2.setName("hSpacer2");
        add(hSpacer2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblListName;
    private JLabel lblGeneralInfo;
    private JPanel pnlSearch;
    private JTextFieldDefaultText txtSearchName;
    private JPanel panel1;
    private JLabel lblCampName;
    private JTextFieldDefaultText txtCampName;
    private JLabel lblNameError;
    private JLabel lblCampLocation;
    private JTextFieldDefaultText txtCampLocation;
    private JLabel lblLocationError;
    private JLabel lblCampDate;
    private JTextFieldDefaultText txtCampDate;
    private JLabel lblDateError;
    private JLabel lblCampDuration;
    private JTextFieldDefaultText txtCampDuration;
    private JLabel lblDurationError;
    private JPanel panel2;
    private JLabel lblAvailable;
    private JLabel lblWhoCame;
    private JScrollPane scrollPane1;
    private JList listAvailable;
    private JButton btnAdd;
    private JScrollPane scrollPane2;
    private JList listParticipated;
    private JButton btnRemove;
    private JLabel lblNotes;
    private JScrollPane scrollPane4;
    private JTextArea txtNotes;
    private JScrollPane scrollPane3;
    private JList listCampNames;
    private JPanel hSpacer2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
