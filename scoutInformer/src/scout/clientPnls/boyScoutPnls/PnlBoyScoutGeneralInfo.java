/*
 * Created by JFormDesigner on Mon Oct 27 22:51:15 MDT 2014
 */

package scout.clientPnls.boyScoutPnls;

import constants.ContactTypeConst;
import constants.ScoutTypeConst;
import guiUtil.JTextFieldDefaultText;
import scout.dbObjects.Advancement;
import scout.dbObjects.Contact;
import scout.dbObjects.Scout;
import util.LogicAdvancement;
import util.LogicContact;
import util.LogicScout;
import util.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author User #2
 */
@SuppressWarnings("MagicConstant")
public class PnlBoyScoutGeneralInfo extends JPanel {
    private final ImageIcon NO_IMAGE_ICON = new ImageIcon(getClass().getResource("/images/no_image.png"));
    private final Integer MIN_AGE = 11;
    private final Integer MAX_AGE = 18;
    private final Integer DEFAULT_TIME = 0;

    private final int COL_TYPE = 0;
    private final int COL_NAME = 1;
    private final int COL_RELATION = 2;
    private final int COL_DATA = 3;

    private DefaultTableModel tableModelContacts;
    private Scout scout;
    private SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public PnlBoyScoutGeneralInfo() {
        initComponents();
    }

    public void init() {
        clearErrors();

        lblAgeValue.setText(DEFAULT_TIME.toString());
        lblRankTimeValue.setText(DEFAULT_TIME.toString());
        lblPositionTimeValue.setText(DEFAULT_TIME.toString());

        List<Advancement> advancementList = LogicAdvancement.findAllAdvancements();
        if (!Util.isEmpty(advancementList)) {
            for (Advancement advancement : advancementList) {
                cboCurrentRank.addItem(advancement.getName());
            }
        }

        if (scout == null || scout.getId() < 0) {
            clearData();
            return;
        }

        loadData();

        revalidate();
        repaint();
    }

    private void loadData() {
        if (!Util.isEmpty(scout.getPosition())) {
            txtPositionName.setText(scout.getPosition());
        } else {
            txtPositionName.setDefault();
        }
        updatePositionTime();

        txtName.setText(scout.getName());

        txtBirthDate.setText(df.format(scout.getBirthDate()));
        updateAge();

        Advancement advancement = LogicAdvancement.findById(scout.getCurrentAdvancementId());
        if (advancement != null) {
            updateRankTime();
            cboCurrentRank.setSelectedItem(advancement.getName());
            loadImage(advancement.getImgPath());
        } else {
            lblImage.setIcon(NO_IMAGE_ICON);
        }

        updateContactTable();
        cboCurrentRank.setSelectedItem(LogicAdvancement.findById(scout.getCurrentAdvancementId()).getName());
    }

    private void updatePositionTime() {
        if (scout.getPostionDate() == null) {
            lblPositionTimeValue.setText(DEFAULT_TIME.toString());
            txtPositionDate.setDefault();
            return;
        }

        Calendar a = getCalendar(scout.getPostionDate());
        Calendar b = getCalendar(new Date());

        if (b.getTimeInMillis() - a.getTimeInMillis() <= 0) {
            lblPositionTimeValue.setText(DEFAULT_TIME.toString());
            txtPositionDate.setDefault();
            return;
        }

        Integer diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        diff = diff * 12 + b.get(Calendar.MONTH) - a.get(Calendar.MONTH);

        if (a.get(Calendar.DATE) > b.get(Calendar.DATE)) {
            diff--;
        }

        lblPositionTimeValue.setText(diff.toString());
        txtPositionDate.setText(Util.DISPLAY_DATE_FORMAT.format(scout.getPostionDate()));
    }

    private void updateRankTime() {
        Calendar a = getCalendar(scout.getAdvancementDate());
        Calendar b = getCalendar(new Date());

        if (b.getTimeInMillis() - a.getTimeInMillis() <= 0) {
            lblRankTimeValue.setText(DEFAULT_TIME.toString());
            txtRankDate.setDefault();
            return;
        }

        Integer diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        diff = diff * 12 + b.get(Calendar.MONTH) - a.get(Calendar.MONTH);

        if (a.get(Calendar.DATE) > b.get(Calendar.DATE)) {
            diff--;
        }

        lblRankTimeValue.setText(diff.toString());
        txtRankDate.setText(Util.DISPLAY_DATE_FORMAT.format(scout.getAdvancementDate()));
    }

    public Scout getScout() {
        return scout;
    }

    private void clearData() {
        txtName.setDefault();
        txtBirthDate.setDefault();
        if (cboCurrentRank.getItemCount() > 0) {
            cboCurrentRank.setSelectedIndex(0);
        }
        lblAgeValue.setText(MIN_AGE.toString());

        clearErrors();
        clearContactTable();
        lblAgeValue.setText(DEFAULT_TIME.toString());
        lblRankTimeValue.setText(DEFAULT_TIME.toString());
        lblPositionTimeValue.setText(DEFAULT_TIME.toString());

        txtRankDate.setDefault();
        txtPositionName.setDefault();
        txtPositionDate.setDefault();

        txtName.requestFocus();
    }

    private void updateContactTable() {
        if (scout == null) {
            return;
        }

        clearContactTable();

        List<Contact> contactList = LogicContact.findAllByScoutId(scout.getId());
        for (Contact contact : contactList) {
            Object[] row = new Object[]{ContactTypeConst.getNameById(contact.getTypeId()), contact.getName(), contact.getRelation(), contact.getData()};
            tableModelContacts.addRow(row);
        }
    }

    private void updateAge() {
        Calendar a = getCalendar(scout.getBirthDate());
        Calendar b = getCalendar(new Date());

        Integer diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        lblAgeValue.setText(diff.toString());
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    private void clearContactTable() {
        if (tableModelContacts.getRowCount() > 0) {
            int rowCount = tableModelContacts.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                tableModelContacts.removeRow(i);
            }
        }
    }

    public void setScout(Scout scout) {
        this.scout = scout;
    }

    private void setImage(String imgPath) {
        try {
            BufferedImage img = ImageIO.read(new File(imgPath));

            int height = img.getHeight() > lblImage.getHeight() ? lblImage.getHeight() : img.getHeight();
            int width = img.getWidth() > lblImage.getWidth() ? lblImage.getWidth() : img.getWidth();

            if (width == 0 || height == 0) {
                width = 128;
                height = 128;
            }

            lblImage.setIcon(new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        } catch (IOException ignore) {
        }
    }

    private void loadImage(String imagePath) {
        URL imgPath = getClass().getResource(imagePath);
        if (imgPath == null) {
            ImageIcon tryPath = new ImageIcon(imagePath);
            if (tryPath.getImageLoadStatus() < MediaTracker.COMPLETE) {
                lblImage.setIcon(NO_IMAGE_ICON);
            } else {
                setImage(imagePath);
            }
        } else {
            setImage(imgPath.getPath());
        }
    }

    public void clearErrors() {
        clearBirthDateErrors();
        clearNameErrors();
        clearContactErrors();
        clearRankErrors();
        clearPositionErrors();
    }

    public void clearPositionErrors() {
        lblPositionTimeError.setText("");
        lblPositionTimeError.setVisible(false);
    }

    public void clearRankErrors() {
        lblRankTimeError.setText("");
        lblRankTimeError.setVisible(false);
    }

    public void clearContactErrors() {
        lblContactError.setText("");
        lblContactError.setVisible(false);
    }

    public void clearBirthDateErrors() {
        lblBirthDateError.setText("");
        lblBirthDateError.setVisible(false);
    }

    public void clearNameErrors() {
        lblNameError.setText("");
        lblNameError.setVisible(false);
    }

    private void createUIComponents() {
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
        tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        tableModelContacts = new DefaultTableModel(new Object[] {"Type", "Name", "Relation", "Data"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return true;
            }
        };

        tblContacts = new JTable();
        tblContacts.setBackground(Color.WHITE);


        JTableHeader headerContacts = tblContacts.getTableHeader();
        headerContacts.setBackground(new Color(51, 102, 153));
        headerContacts.setForeground(Color.WHITE);
        headerContacts.setFont(new Font("Tahoma", Font.PLAIN, 14));

        tblContacts.setModel(tableModelContacts);
        for (int i = 0; i < 4; ++i) {
            tblContacts.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
        }

        cboCurrentRank = new JComboBox();
        List<Advancement> advancementList = LogicAdvancement.findAllAdvancements();
        if (!Util.isEmpty(advancementList)) {
            for (Advancement advancement : advancementList) {
                cboCurrentRank.addItem(advancement.getName());
            }
        }
    }

    private void btnNewContactMouseClicked() {
        ContactDialog dialog = new ContactDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);

        if (dialog.getBtnChoice() == ContactDialog.BTN_OK) {
            Contact contact = dialog.getContact();
            Object[] row = new Object[] {ContactTypeConst.getNameById(contact.getTypeId()), contact.getName(), contact.getRelation(), contact.getData()};
            tableModelContacts.addRow(row);
        }
    }

    private void btnDeleteContactMouseClicked() {
        if (tblContacts.getSelectedRow() < 0) {
            return;
        }

        tableModelContacts.removeRow(tblContacts.getSelectedRow());
    }

    private void cboCurrentRankActionPerformed() {
        Advancement advancement = LogicAdvancement.findByName(cboCurrentRank.getSelectedItem().toString());
        loadImage(advancement.getImgPath());
        scout.setCurrentAdvancementId(advancement.getId());
    }

    public boolean validateInfo() {
        clearErrors();
        boolean hasErrors = false;

        if (!validateName()) {
            hasErrors = true;
        }

        if (!validateBirthDate()) {
            hasErrors = true;
        }

        if (!validateContacts()) {
            hasErrors = true;
        }

        if (!validateRankDate()) {
            hasErrors = true;
        }

        if (!validatePositionDate()) {
            hasErrors = true;
        }

        return !hasErrors;
    }

    private boolean validateContacts() {
        clearContactErrors();

        if (tableModelContacts.getRowCount() <= 0) {
            return true;
        }

        int count = 1;

        List<Contact> contactList = new ArrayList<Contact>();
        for (int rowIndex = 0; rowIndex < tableModelContacts.getRowCount(); ++rowIndex) {
            String errorLine = " line: " + count;

            String type = (String)tableModelContacts.getValueAt(rowIndex, COL_TYPE);
            String name = (String)tableModelContacts.getValueAt(rowIndex, COL_NAME);
            String relation = (String)tableModelContacts.getValueAt(rowIndex, COL_RELATION);
            String data = (String)tableModelContacts.getValueAt(rowIndex, COL_DATA);

            if (Util.isEmpty(name)) {
                Util.setError(lblContactError, "Cannot leave name blank." + errorLine);
                return false;
            }

            if (Util.isEmpty(relation)) {
                Util.setError(lblContactError, "Cannot leave relation blank." + errorLine);
                return false;
            }

            if (Util.isEmpty(data)) {
                Util.setError(lblContactError, "Cannot leave data blank." + errorLine);
                return false;
            }

            if (type.equals(ContactTypeConst.EMAIL.getName())) {
                if (!Util.validateEmail(data)) {
                    Util.setError(lblContactError, "invalid email format." + errorLine);
                    return false;
                }
            } else {
                if (!Util.validatePhoneNumber(data)) {
                    Util.setError(lblContactError, "invalid phone number format." + errorLine);
                    return false;
                }
            }

            Contact contact = new Contact();
            contact.setScoutId(scout.getId());
            contact.setName(name);
            contact.setRelation(relation);
            contact.setTypeId(ContactTypeConst.getIdByName(type));
            contact.setData(data);

            contactList.add(contact);
            count++;
        }

        int outLineItemCount = 1;
        for (Contact c1 : contactList) {
            int innerLineItemCount = 1;
            for (Contact c2: contactList) {
                if (outLineItemCount == innerLineItemCount) {
                    continue;
                }

                if (c1.getName().equals(c2.getName()) && c1.getTypeId() == c2.getTypeId()) {
                    Util.setError(lblContactError, "Cannot have duplicate contact names of the same type");
                    return false;
                }
                innerLineItemCount++;
            }
            outLineItemCount++;
        }

        return true;
    }

    private void checkRankDate() {
        if(!validateRankDate()) {
            return;
        }

        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(txtRankDate.getText());

        if (matcher.find()) {
            int month = Integer.parseInt(matcher.group(1));
            int day = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            Calendar a = Calendar.getInstance();
            a.set(year, month - 1, day);
            Calendar b = getCalendar(new Date());

            if (b.getTimeInMillis() - a.getTimeInMillis() <= 0) {
                lblRankTimeValue.setText(DEFAULT_TIME.toString());
                return;
            }

            Integer diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
            diff = diff * 12 + b.get(Calendar.MONTH) - a.get(Calendar.MONTH);

            if (a.get(Calendar.DATE) > b.get(Calendar.DATE)) {
                diff--;
            }

            lblRankTimeValue.setText(diff.toString());
        }
    }

    private boolean validateRankDate() {
        clearRankErrors();

        if (txtRankDate.isMessageDefault() || Util.isEmpty(txtRankDate.getText())) {
            Util.setError(lblRankTimeError, "Cannot leave rank date blank");
            return false;
        }

        if (!txtRankDate.getText().matches(Util.DATE_PATTERN)) {
            Util.setError(lblRankTimeError, "invalid format");
            return false;
        }

        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(txtRankDate.getText());

        if (matcher.find()) {
            int month = Integer.parseInt(matcher.group(1));
            int day = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            if (month > 12 || month < 1) {
                Util.setError(lblRankTimeError, "invalid month");
                return false;
            }

            if (Util.isThirtyOneDayMonth(month)) {
                if (day > 31 || month < 0) {
                    Util.setError(lblRankTimeError, "invalid day");
                    return false;
                }
            } else if (Util.isThirtyDayMonth(month)) {
                if (day > 30 || month < 0) {
                    Util.setError(lblRankTimeError, "invalid day");
                    return false;
                }
            } else {
                boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));

                if (isLeapYear) {
                    if (day > 29 || month < 0) {
                        Util.setError(lblRankTimeError, "invalid day");
                        return false;
                    }
                } else {
                    if (day > 28 || month < 0) {
                        Util.setError(lblRankTimeError, "invalid day");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void checkPositionDate() {
        if(!validatePositionDate()) {
            return;
        }

        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(txtPositionDate.getText());

        if (matcher.find()) {
            int month = Integer.parseInt(matcher.group(1));
            int day = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            Calendar a = Calendar.getInstance();
            a.set(year, month - 1, day);
            Calendar b = getCalendar(new Date());

            if (b.getTimeInMillis() - a.getTimeInMillis() <= 0) {
                lblPositionTimeValue.setText(DEFAULT_TIME.toString());
                return;
            }

            Integer diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
            diff = diff * 12 + b.get(Calendar.MONTH) - a.get(Calendar.MONTH);

            if (a.get(Calendar.DATE) > b.get(Calendar.DATE)) {
                diff--;
            }

            lblPositionTimeValue.setText(diff.toString());
        }
    }

    private boolean validatePositionDate() {
        clearPositionErrors();

        if (txtPositionName.isMessageDefault() || Util.isEmpty(txtPositionName.getText())) {
            lblPositionTimeValue.setText(DEFAULT_TIME.toString());
            return true;
        }

        if (txtPositionDate.isMessageDefault() || Util.isEmpty(txtPositionDate.getText())) {
            Util.setError(lblPositionTimeError, "Cannot leave position date blank");
            return false;
        }

        if (!txtPositionDate.getText().matches(Util.DATE_PATTERN)) {
            Util.setError(lblPositionTimeError, "invalid format");
            return false;
        }

        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(txtPositionDate.getText());

        if (matcher.find()) {
            int month = Integer.parseInt(matcher.group(1));
            int day = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            if (month > 12 || month < 1) {
                Util.setError(lblPositionTimeError, "invalid month");
                return false;
            }

            if (Util.isThirtyOneDayMonth(month)) {
                if (day > 31 || month < 0) {
                    Util.setError(lblPositionTimeError, "invalid day");
                    return false;
                }
            } else if (Util.isThirtyDayMonth(month)) {
                if (day > 30 || month < 0) {
                    Util.setError(lblPositionTimeError, "invalid day");
                    return false;
                }
            } else {
                boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));

                if (isLeapYear) {
                    if (day > 29 || month < 0) {
                        Util.setError(lblPositionTimeError, "invalid day");
                        return false;
                    }
                } else {
                    if (day > 28 || month < 0) {
                        Util.setError(lblPositionTimeError, "invalid day");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean validateBirthDate() {

        if (txtBirthDate.isMessageDefault() || Util.isEmpty(txtBirthDate.getText())) {
            Util.setError(lblBirthDateError, "Cannot leave birth date blank");
            return false;
        }

        if (!txtBirthDate.getText().matches(Util.DATE_PATTERN)) {
            Util.setError(lblBirthDateError, "invalid format");
            return false;
        }

        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(txtBirthDate.getText());

        if (matcher.find()) {
            int month = Integer.parseInt(matcher.group(1));
            int day = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            if (month > 12 || month < 1) {
                Util.setError(lblBirthDateError, "invalid month");
                return false;
            }

            if (Util.isThirtyOneDayMonth(month)) {
                if (day > 31 || month < 0) {
                    Util.setError(lblBirthDateError, "invalid day");
                    return false;
                }
            } else if (Util.isThirtyDayMonth(month)) {
                if (day > 30 || month < 0) {
                    Util.setError(lblBirthDateError, "invalid day");
                    return false;
                }
            } else {
                boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));

                if (isLeapYear) {
                    if (day > 29 || month < 0) {
                        Util.setError(lblBirthDateError, "invalid day");
                        return false;
                    }
                } else {
                    if (day > 28 || month < 0) {
                        Util.setError(lblBirthDateError, "invalid day");
                        return false;
                    }
                }
            }

            Calendar a = Calendar.getInstance();
            a.set(year, month - 1, day);
            Calendar b = getCalendar(new Date());

            Integer diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
            if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                    (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
                diff--;
            }

            if (diff > MAX_AGE) {
                Util.setError(lblBirthDateError, "birth date is invalid as the scout will be too old. age: " + diff);
                return false;
            } else if (diff <= 0) {
                Util.setError(lblBirthDateError, "birth date is invalid as the scout will not have been born yet.");
                return false;
            } else if (diff < MIN_AGE) {
                Util.setError(lblBirthDateError, "birth date is invalid as the scout will be too young. age: " + diff);
                return false;
            }
        }

        return true;
    }

    private boolean validateName() {
        if (txtName.isMessageDefault()) {
            Util.setError(lblNameError, "Cannot leave name blank");
            return false;
        }
        return true;
    }

    public void delete() {
        if (scout == null) {
            return;
        }

        List<Integer> contactIdList = new ArrayList<Integer>();
        if (tableModelContacts.getRowCount() > 0) {
            for (int i = 0; i < tblContacts.getRowCount(); ++i) {
                String name = (String) tableModelContacts.getValueAt(i, COL_NAME);
                int typeId = ContactTypeConst.getIdByName((String)tableModelContacts.getValueAt(i, COL_TYPE));
                contactIdList.add(LogicContact.findByNameTypeIdAndScoutId(name, typeId, scout.getId()));
            }
        }

        LogicContact.deleteList(contactIdList);
        LogicScout.delete(scout.getId());

        clearData();
    }

    public void update(JList nameList, String scoutName) {
        // should run validateInfo() before this method

        for (int i = 0; i < nameList.getModel().getSize(); ++i) {
            String advancementName = (String) nameList.getModel().getElementAt(i);
            if (advancementName.equalsIgnoreCase(txtName.getText()) && !txtName.getText().equals(nameList.getSelectedValue().toString())) {
                Util.setError(lblNameError, "Scout name already exists");
                return;
            }
        }

        Calendar birthDate;
        if ((birthDate = convertDateStringToCalendar(txtBirthDate.getText())) == null) {
            return;
        }

        Calendar rankDate;
        if ((rankDate = convertDateStringToCalendar(txtRankDate.getText())) == null) {
            return;
        }

        scout.setName(txtName.getText());
        scout.setBirthDate(birthDate.getTime());
        scout.setCurrentAdvancementId(LogicAdvancement.findByName(cboCurrentRank.getSelectedItem().toString()).getId());
        scout.setAdvancementDate(rankDate.getTime());
        scout.setTypeId(ScoutTypeConst.BOY_SCOUT.getId());

        if (!Util.isEmpty(txtPositionName)) {
            Calendar positionDate;
            if ((positionDate = convertDateStringToCalendar(txtPositionDate.getText())) == null) {
                return;
            }

            scout.setPosition(txtPositionName.getText());
            scout.setPostionDate(positionDate.getTime());
        }

        Scout tempScout = LogicScout.findByName(scoutName);
        scout.setId(tempScout.getId());
        LogicScout.update(scout);

        List<Contact> contactList = new ArrayList<Contact>();
        if (tableModelContacts.getRowCount() > 0) {
            for (int i = 0; i < tblContacts.getRowCount(); ++i) {
                Contact contact = new Contact();
                contact.setTypeId(ContactTypeConst.getIdByName((String)tableModelContacts.getValueAt(i, COL_TYPE)));
                contact.setName((String) tableModelContacts.getValueAt(i, COL_NAME));
                contact.setRelation((String) tableModelContacts.getValueAt(i, COL_RELATION));
                contact.setData((String) tableModelContacts.getValueAt(i, COL_DATA));
                contact.setScoutId(scout.getId());
                contactList.add(contact);
            }
        }

        LogicContact.updateList(contactList, scout.getId());
    }

    public void save() {
        // should run validateInfo() before this method

        Scout existingScout = LogicScout.findByName(txtName.getText());
        if (existingScout != null) {
            Util.setError(lblNameError, "This scout name already exists");
            return;
        }

        Calendar birthDate;
        if ((birthDate = convertDateStringToCalendar(txtBirthDate.getText())) == null) {
            return;
        }

        Calendar rankDate;
        if ((rankDate = convertDateStringToCalendar(txtRankDate.getText())) == null) {
            return;
        }

        scout.setName(txtName.getText());
        scout.setBirthDate(birthDate.getTime());
        scout.setCurrentAdvancementId(LogicAdvancement.findByName(cboCurrentRank.getSelectedItem().toString()).getId());
        scout.setAdvancementDate(rankDate.getTime());
        scout.setTypeId(ScoutTypeConst.BOY_SCOUT.getId());

        if (!txtPositionName.isMessageDefault() && !Util.isEmpty(txtPositionName.getText())) {
            Calendar positionDate;
            if ((positionDate = convertDateStringToCalendar(txtPositionDate.getText())) == null) {
                return;
            }

            scout.setPosition(txtPositionName.getText());
            scout.setPostionDate(positionDate.getTime());
        }

        LogicScout.save(scout);

        List<Contact> contactList = new ArrayList<Contact>();
        if (tableModelContacts.getRowCount() > 0) {
            for (int i = 0; i < tblContacts.getRowCount(); ++i) {
                Contact contact = new Contact();
                contact.setTypeId(ContactTypeConst.getIdByName((String)tableModelContacts.getValueAt(i, COL_TYPE)));
                contact.setName((String) tableModelContacts.getValueAt(i, COL_NAME));
                contact.setRelation((String) tableModelContacts.getValueAt(i, COL_RELATION));
                contact.setData((String) tableModelContacts.getValueAt(i, COL_DATA));
                contact.setScoutId(scout.getId());
                contactList.add(contact);
            }
        }

        LogicContact.saveList(contactList);
    }

    private Calendar convertDateStringToCalendar(String dateString) {
        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(dateString);

        if (!matcher.find()) {
            return null;
        }

        int month = Integer.parseInt(matcher.group(1));
        int day = Integer.parseInt(matcher.group(2));
        int year = Integer.parseInt(matcher.group(3));

        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day);

        return date;
    }

    private void checkBirthDate() {
        clearBirthDateErrors();

        if(!validateBirthDate()) {
            return;
        }

        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
        Matcher matcher = pattern.matcher(txtBirthDate.getText());

        if (matcher.find()) {
            int month = Integer.parseInt(matcher.group(1));
            int day = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            Calendar a = Calendar.getInstance();
            a.set(year, month - 1, day);
            Calendar b = getCalendar(new Date());

            Integer diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
            if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                    (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
                diff--;
            }

            lblAgeValue.setText(diff.toString());
        }
    }

    private void txtNameKeyReleased() {
        clearNameErrors();
        validateName();
    }

    private void txtNameFocusLost() {
        clearNameErrors();
        validateName();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        createUIComponents();

        pnlSelectedImage = new JPanel();
        lblImage = new JLabel();
        pnlGeneralInfo = new JPanel();
        lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblBirthDate = new JLabel();
        txtBirthDate = new JTextFieldDefaultText();
        lblAge = new JLabel();
        lblAgeValue = new JLabel();
        lblBirthDateError = new JLabel();
        vSpacer1 = new JPanel(null);
        panel3 = new JPanel();
        lblCurrentRank = new JLabel();
        lblRankDate = new JLabel();
        txtRankDate = new JTextFieldDefaultText();
        lblRankTime = new JLabel();
        lblRankTimeValue = new JLabel();
        lblRankTimeError = new JLabel();
        lblPosition = new JLabel();
        txtPositionName = new JTextFieldDefaultText();
        lblPositionDate = new JLabel();
        txtPositionDate = new JTextFieldDefaultText();
        lblPositionTime = new JLabel();
        lblPositionTimeValue = new JLabel();
        lblPositionTimeError = new JLabel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        label2 = new JLabel();
        btnNewContact = new JLabel();
        btnDeleteContact = new JLabel();
        lblContactError = new JLabel();
        scrollPane1 = new JScrollPane();

        //======== this ========
        setMaximumSize(new Dimension(770, 654));
        setMinimumSize(new Dimension(770, 654));
        setPreferredSize(new Dimension(770, 654));
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {158, 585, 40, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {181, 15, 0, 0, 219, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};

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
        add(pnlSelectedImage, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== pnlGeneralInfo ========
        {
            pnlGeneralInfo.setBackground(Color.white);
            pnlGeneralInfo.setName("pnlGeneralInfo");
            pnlGeneralInfo.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWidths = new int[] {0, 335, 85, 62, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 35, 0};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)pnlGeneralInfo.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- lblName ----
            lblName.setText("Name:");
            lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblName.setForeground(new Color(51, 102, 153));
            lblName.setName("lblName");
            pnlGeneralInfo.add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- txtName ----
            txtName.setPreferredSize(new Dimension(14, 40));
            txtName.setMinimumSize(new Dimension(14, 40));
            txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtName.setDefaultText("Scout Name");
            txtName.setName("txtName");
            txtName.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    txtNameKeyReleased();
                }
            });
            txtName.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    txtNameFocusLost();
                }
            });
            pnlGeneralInfo.add(txtName, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 10), 0, 0));

            //---- lblNameError ----
            lblNameError.setText("* Error Message");
            lblNameError.setForeground(Color.red);
            lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblNameError.setName("lblNameError");
            pnlGeneralInfo.add(lblNameError, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 5, 10), 0, 0));

            //---- lblBirthDate ----
            lblBirthDate.setText("Birth Date:");
            lblBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblBirthDate.setForeground(new Color(51, 102, 153));
            lblBirthDate.setName("lblBirthDate");
            pnlGeneralInfo.add(lblBirthDate, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- txtBirthDate ----
            txtBirthDate.setPreferredSize(new Dimension(14, 40));
            txtBirthDate.setMinimumSize(new Dimension(14, 40));
            txtBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtBirthDate.setDefaultText("MM/DD/YYYY");
            txtBirthDate.setName("txtBirthDate");
            txtBirthDate.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    checkBirthDate();
                }
            });
            txtBirthDate.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    checkBirthDate();
                }
            });
            pnlGeneralInfo.add(txtBirthDate, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblAge ----
            lblAge.setText("Age:");
            lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblAge.setForeground(new Color(51, 102, 153));
            lblAge.setName("lblAge");
            pnlGeneralInfo.add(lblAge, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblAgeValue ----
            lblAgeValue.setText("11");
            lblAgeValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblAgeValue.setForeground(new Color(51, 102, 153));
            lblAgeValue.setName("lblAgeValue");
            pnlGeneralInfo.add(lblAgeValue, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblBirthDateError ----
            lblBirthDateError.setText("* Error Message");
            lblBirthDateError.setForeground(Color.red);
            lblBirthDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblBirthDateError.setName("lblBirthDateError");
            pnlGeneralInfo.add(lblBirthDateError, new GridBagConstraints(1, 3, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 5, 10), 0, 0));
        }
        add(pnlGeneralInfo, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- vSpacer1 ----
        vSpacer1.setOpaque(false);
        vSpacer1.setName("vSpacer1");
        add(vSpacer1, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel3 ========
        {
            panel3.setBackground(Color.white);
            panel3.setName("panel3");
            panel3.setLayout(new GridBagLayout());
            ((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 179, 0, 198, 0, 0, 0};
            ((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {35, 0, 40, 0, 0, 0};
            ((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- lblCurrentRank ----
            lblCurrentRank.setText("Current Rank:");
            lblCurrentRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblCurrentRank.setForeground(new Color(51, 102, 153));
            lblCurrentRank.setName("lblCurrentRank");
            panel3.add(lblCurrentRank, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- cboCurrentRank ----
            cboCurrentRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
            cboCurrentRank.setBackground(Color.white);
            cboCurrentRank.setName("cboCurrentRank");
            cboCurrentRank.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cboCurrentRankActionPerformed();
                }
            });
            panel3.add(cboCurrentRank, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblRankDate ----
            lblRankDate.setText("Rank Date:");
            lblRankDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblRankDate.setForeground(new Color(51, 102, 153));
            lblRankDate.setName("lblRankDate");
            panel3.add(lblRankDate, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0, 10, 5, 5), 0, 0));

            //---- txtRankDate ----
            txtRankDate.setPreferredSize(new Dimension(14, 35));
            txtRankDate.setMinimumSize(new Dimension(14, 35));
            txtRankDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtRankDate.setDefaultText("MM/DD/YYYY");
            txtRankDate.setName("txtRankDate");
            txtRankDate.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    checkRankDate();
                }
            });
            txtRankDate.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    checkRankDate();
                }
            });
            panel3.add(txtRankDate, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblRankTime ----
            lblRankTime.setText("Time as Rank:");
            lblRankTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblRankTime.setForeground(new Color(51, 102, 153));
            lblRankTime.setName("lblRankTime");
            panel3.add(lblRankTime, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0, 10, 5, 5), 0, 0));

            //---- lblRankTimeValue ----
            lblRankTimeValue.setText("11");
            lblRankTimeValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblRankTimeValue.setForeground(new Color(51, 102, 153));
            lblRankTimeValue.setToolTipText("Number of months in this rank");
            lblRankTimeValue.setName("lblRankTimeValue");
            panel3.add(lblRankTimeValue, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblRankTimeError ----
            lblRankTimeError.setText("* Error Message");
            lblRankTimeError.setForeground(Color.red);
            lblRankTimeError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblRankTimeError.setName("lblRankTimeError");
            panel3.add(lblRankTimeError, new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 5, 15), 0, 0));

            //---- lblPosition ----
            lblPosition.setText("Position:");
            lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblPosition.setForeground(new Color(51, 102, 153));
            lblPosition.setName("lblPosition");
            panel3.add(lblPosition, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0, 5, 5, 5), 0, 0));

            //---- txtPositionName ----
            txtPositionName.setDefaultText("Position Name");
            txtPositionName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtPositionName.setName("txtPositionName");
            panel3.add(txtPositionName, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblPositionDate ----
            lblPositionDate.setText("Position Date:");
            lblPositionDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblPositionDate.setForeground(new Color(51, 102, 153));
            lblPositionDate.setName("lblPositionDate");
            panel3.add(lblPositionDate, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 5, 5), 0, 0));

            //---- txtPositionDate ----
            txtPositionDate.setPreferredSize(new Dimension(14, 35));
            txtPositionDate.setMinimumSize(new Dimension(14, 35));
            txtPositionDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtPositionDate.setDefaultText("MM/DD/YYYY");
            txtPositionDate.setName("txtPositionDate");
            txtPositionDate.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    checkPositionDate();
                }
            });
            txtPositionDate.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    checkPositionDate();
                }
            });
            panel3.add(txtPositionDate, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblPositionTime ----
            lblPositionTime.setText("Time in Position:");
            lblPositionTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblPositionTime.setForeground(new Color(51, 102, 153));
            lblPositionTime.setName("lblPositionTime");
            panel3.add(lblPositionTime, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 5, 5), 0, 0));

            //---- lblPositionTimeValue ----
            lblPositionTimeValue.setText("11");
            lblPositionTimeValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblPositionTimeValue.setForeground(new Color(51, 102, 153));
            lblPositionTimeValue.setToolTipText("Number of months in this position");
            lblPositionTimeValue.setName("lblPositionTimeValue");
            panel3.add(lblPositionTimeValue, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- lblPositionTimeError ----
            lblPositionTimeError.setText("* Error Message");
            lblPositionTimeError.setForeground(Color.red);
            lblPositionTimeError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblPositionTimeError.setName("lblPositionTimeError");
            panel3.add(lblPositionTimeError, new GridBagConstraints(3, 3, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 5, 15), 0, 0));
        }
        add(panel3, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {353, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 216, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //======== panel2 ========
            {
                panel2.setBackground(Color.white);
                panel2.setName("panel2");
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- label2 ----
                label2.setText("Contact Information");
                label2.setFont(new Font("Vijaya", Font.PLAIN, 24));
                label2.setForeground(new Color(51, 102, 153));
                label2.setName("label2");
                panel2.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 5, 0, 5), 0, 0));

                //---- btnNewContact ----
                btnNewContact.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
                btnNewContact.setPreferredSize(new Dimension(24, 24));
                btnNewContact.setMinimumSize(new Dimension(24, 24));
                btnNewContact.setMaximumSize(new Dimension(24, 24));
                btnNewContact.setHorizontalAlignment(SwingConstants.CENTER);
                btnNewContact.setBackground(Color.white);
                btnNewContact.setOpaque(true);
                btnNewContact.setToolTipText("Add new contact information");
                btnNewContact.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnNewContact.setName("btnNewContact");
                btnNewContact.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnNewContactMouseClicked();
                    }
                });
                panel2.add(btnNewContact, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnDeleteContact ----
                btnDeleteContact.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
                btnDeleteContact.setPreferredSize(new Dimension(24, 24));
                btnDeleteContact.setMinimumSize(new Dimension(24, 24));
                btnDeleteContact.setMaximumSize(new Dimension(24, 24));
                btnDeleteContact.setHorizontalAlignment(SwingConstants.CENTER);
                btnDeleteContact.setBackground(Color.white);
                btnDeleteContact.setOpaque(true);
                btnDeleteContact.setToolTipText("Delete selected contact information");
                btnDeleteContact.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnDeleteContact.setName("btnDeleteContact");
                btnDeleteContact.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnDeleteContactMouseClicked();
                    }
                });
                panel2.add(btnDeleteContact, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- lblContactError ----
                lblContactError.setText("* Error Message");
                lblContactError.setForeground(Color.red);
                lblContactError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblContactError.setName("lblContactError");
                panel2.add(lblContactError, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 10, 0, 10), 0, 0));
            }
            panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== scrollPane1 ========
            {
                scrollPane1.setName("scrollPane1");

                //---- tblContacts ----
                tblContacts.setFont(new Font("Tahoma", Font.PLAIN, 14));
                tblContacts.setBackground(Color.white);
                tblContacts.setFillsViewportHeight(true);
                tblContacts.setForeground(Color.black);
                tblContacts.setName("tblContacts");
                scrollPane1.setViewportView(tblContacts);
            }
            panel1.add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 5, 0, 5), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel pnlSelectedImage;
    private JLabel lblImage;
    private JPanel pnlGeneralInfo;
    private JLabel lblName;
    private JTextFieldDefaultText txtName;
    private JLabel lblNameError;
    private JLabel lblBirthDate;
    private JTextFieldDefaultText txtBirthDate;
    private JLabel lblAge;
    private JLabel lblAgeValue;
    private JLabel lblBirthDateError;
    private JPanel vSpacer1;
    private JPanel panel3;
    private JLabel lblCurrentRank;
    private JComboBox cboCurrentRank;
    private JLabel lblRankDate;
    private JTextFieldDefaultText txtRankDate;
    private JLabel lblRankTime;
    private JLabel lblRankTimeValue;
    private JLabel lblRankTimeError;
    private JLabel lblPosition;
    private JTextFieldDefaultText txtPositionName;
    private JLabel lblPositionDate;
    private JTextFieldDefaultText txtPositionDate;
    private JLabel lblPositionTime;
    private JLabel lblPositionTimeValue;
    private JLabel lblPositionTimeError;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel label2;
    private JLabel btnNewContact;
    private JLabel btnDeleteContact;
    private JLabel lblContactError;
    private JScrollPane scrollPane1;
    private JTable tblContacts;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
