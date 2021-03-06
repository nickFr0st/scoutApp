/*
 * Created by JFormDesigner on Wed Oct 22 22:21:45 MDT 2014
 */

package scout.clientPnls.IEPnls;

import au.com.bytecode.opencsv.CSVReader;
import constants.ModuleTypeConst;
import constants.RequirementTypeConst;
import constants.ScoutTypeConst;
import guiUtil.CustomChooser;
import guiUtil.JTextFieldDefaultText;
import scout.dbObjects.*;
import util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author User #2
 */
public class ImportDialog extends JDialog {
    private int importTypeId;

    public ImportDialog(Frame owner, int importTypeId) {
        super(owner, true);
        initComponents();
        this.importTypeId = importTypeId;

        switch (importTypeId) {
            case ModuleTypeConst.ADVANCEMENT:
                setTitle("Advancement Import");
                setTxtImportInstructions(getClass().getResource("/instructions/IEAdvancementInstructions.html").toString());
                break;
            case ModuleTypeConst.MERIT_BADGE:
                setTitle("Merit Badge Import");
                setTxtImportInstructions(getClass().getResource("/instructions/ImportMeritBadgeInstructions.html").toString());
                break;
            case ModuleTypeConst.OTHER:
                setTitle("Other Award Import");
                setTxtImportInstructions(getClass().getResource("/instructions/ImportAwardInstructions.html").toString());
                break;
            case ModuleTypeConst.SCOUT:
                setTitle("Scout Import");
                setTxtImportInstructions(getClass().getResource("/instructions/ImportScoutInstructions.html").toString());
                break;
            case ModuleTypeConst.CAMP_OUT:
                setTitle("Camp Import");
                setTxtImportInstructions(getClass().getResource("/instructions/ImportCampInstructions.html").toString());
                break;
        }
    }

    public void setTxtImportInstructions(String url) {
        try {
            txtImportInstructions.setPage(url);
            txtImportInstructions.setEditable(false);
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void btnImportMouseClicked() {
        if (txtImportPath.isMessageDefault()) {
            JOptionPane.showMessageDialog(this, "Please specify a file that you wish to import.", "No File Selected", JOptionPane.ERROR_MESSAGE);
            txtImportPath.requestFocus();
            return;
        }

        boolean success = false;
        Util.processBusy(btnImport, true);

        switch (importTypeId) {
            case ModuleTypeConst.ADVANCEMENT:
                success = handleAdvancementImport(txtImportPath.getText());
                break;
            case ModuleTypeConst.MERIT_BADGE:
                success = handleMeritBadgeImport(txtImportPath.getText());
                break;
            case ModuleTypeConst.OTHER:
                success = handleAwardImport(txtImportPath.getText());
                break;
            case ModuleTypeConst.SCOUT:
                success = handleScoutImport(txtImportPath.getText());
                break;
            case ModuleTypeConst.CAMP_OUT:
                success = handleCampImport(txtImportPath.getText());
                break;
        }

        Util.processBusy(btnImport, false);
        if (success) {
            dispose();
        }
    }

    private boolean handleCampImport(String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            Map<Camp, List<Integer>> importMap = new HashMap<Camp, List<Integer>>();

            boolean getCamp = true;
            Camp camp = null;
            java.util.List<Integer> scoutIdList = new ArrayList<Integer>();

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[0].equals("Camp Name") || record[0].equals("Scout Name")) {
                    continue;
                }

                if (record[0].isEmpty()) {
                    getCamp = true;

                    if (camp != null) {
                        if (!checkForErrors(errors)) {
                            return false;
                        }

                        importMap.put(camp, scoutIdList);

                        camp = null;
                        scoutIdList = new ArrayList<Integer>();
                    }

                    continue;
                }

                if (getCamp) {
                    getCamp = false;

                    if (record.length < 4) {
                        errors.append("There are too few values for the camp. ").append(errorLine);
                        continue;
                    }


                    camp = new Camp();

                    String campName = record[0];
                    if (Util.isEmpty(campName)){
                        errors.append("Camp name is missing. ").append(errorLine);
                    } else if (campName.length() > Camp.COL_NAME_LENGTH) {
                        errors.append("Camp name is too long. ").append(errorLine);
                    }
                    camp.setName(campName);

                    String campLocation = record[1];
                    if (Util.isEmpty(campLocation)){
                        errors.append("Camp location is missing. ").append(errorLine);
                    } else if (campLocation.length() > Camp.COL_LOCATION_LENGTH) {
                        errors.append("Camp location is too long. ").append(errorLine);
                    }
                    camp.setLocation(campLocation);

                    String campDuration = record[2];
                    if (Util.isEmpty(campDuration)){
                        errors.append("Camp duration is missing. ").append(errorLine);
                    } else if (!campDuration.matches("[1-9]+")) {
                        errors.append("Camp duration must be a whole number greater than zero. ").append(errorLine);
                    }
                    camp.setDuration(Integer.parseInt(campDuration));

                    String campDate = record[3].trim();
                    if (!Util.validateDisplayDateFormat(campDate)) {
                        errors.append("invalid date: ").append(campDate).append(". Valid date format is (MM/DD/YYYY). ").append(errorLine);
                        continue;
                    } else {

                        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
                        Matcher matcher = pattern.matcher(campDate);

                        if (matcher.find()) {
                            int month = Integer.parseInt(matcher.group(1));
                            int day = Integer.parseInt(matcher.group(2));
                            int year = Integer.parseInt(matcher.group(3));

                            Calendar revDate = Calendar.getInstance();
                            //noinspection MagicConstant
                            revDate.set(year, month - 1, day);

                            camp.setDate(revDate.getTime());
                        } else {
                            camp.setDate(new Date());
                        }
                    }

                    if (record.length == 4) {
                        continue;
                    }

                    String campNotes = record[4];
                    if (!Util.isEmpty(campNotes)){
                        camp.setNote(campNotes);
                    }
                    continue;
                }

                String scoutName = record[0];
                Scout scout = LogicScout.findByName(scoutName);

                if (scout == null) {
                    errors.append("Scout '" + scoutName + "' doesn't exists. ").append(errorLine);
                    continue;
                }

                scoutIdList.add(scout.getId());
            }

            reader.close();

            if (!checkForErrors(errors)) {
                return false;
            }

            importMap.put(camp, scoutIdList);

            for (Camp campOut : importMap.keySet()) {
                Camp existingCamp = LogicCamp.findByName(campOut.getName());

                if (existingCamp != null) {
                    LogicScoutCamp.deleteAllByCampId(existingCamp.getId());

                    campOut.setNote(existingCamp.getNote());
                    campOut.setId(existingCamp.getId());
                    LogicCamp.update(campOut);
                } else {
                    LogicCamp.save(campOut);
                }

                List<Integer> scoutIds = importMap.get(campOut);
                if (!Util.isEmpty(scoutIds)) {
                    LogicScoutCamp.save(campOut.getId(), scoutIds);
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        JOptionPane.showMessageDialog(this, "Your camp(s) have been successfully imported.", "Import Successful", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private boolean handleScoutImport(String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            List<Scout> importList = new ArrayList<Scout>();

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[0].equals("Scout Name")) {
                    continue;
                }

                Scout scout = new Scout();

                String scoutName = record[0];
                if (Util.isEmpty(scoutName)) {
                    errors.append("Scout name is missing. ").append(errorLine);
                } else if (scoutName.length() > Scout.COL_NAME_LENGTH) {
                    errors.append("Scout name is too long. ").append(errorLine);
                }
                scout.setName(scoutName);

                String birthDate = record[1].trim();
                if (Util.isEmpty(birthDate)) {
                    errors.append("Birth date is missing. ").append(errorLine);
                } else if (!Util.validateDisplayDateFormat(birthDate)) {
                    errors.append("invalid date: ").append(birthDate).append(". Valid date format is (MM/DD/YYYY). ").append(errorLine);
                } else {

                    Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
                    Matcher matcher = pattern.matcher(birthDate);

                    if (matcher.find()) {
                        int month = Integer.parseInt(matcher.group(1));
                        int day = Integer.parseInt(matcher.group(2));
                        int year = Integer.parseInt(matcher.group(3));

                        Calendar date = Calendar.getInstance();
                        //noinspection MagicConstant
                        date.set(year, month - 1, day);

                        scout.setBirthDate(date.getTime());
                    } else {
                        scout.setBirthDate(new Date());
                    }
                }

                String currentRank = record[2].trim();
                if (Util.isEmpty(currentRank)) {
                    errors.append("Current rank is missing. ").append(errorLine);
                } else {
                    Advancement advancement = LogicAdvancement.findByName(currentRank);
                    if (advancement == null) {
                        errors.append("advancement ").append(currentRank).append(" does not exists. ").append(errorLine);
                    } else {
                        scout.setCurrentAdvancementId(advancement.getId());
                    }
                }

                String rankDate = record[3].trim();
                if (Util.isEmpty(rankDate)) {
                    errors.append("Current rank date is missing. ").append(errorLine);
                } else if (!Util.validateDisplayDateFormat(rankDate)) {
                    errors.append("invalid date: ").append(rankDate).append(". Valid date format is (MM/DD/YYYY). ").append(errorLine);
                } else {

                    Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
                    Matcher matcher = pattern.matcher(rankDate);

                    if (matcher.find()) {
                        int month = Integer.parseInt(matcher.group(1));
                        int day = Integer.parseInt(matcher.group(2));
                        int year = Integer.parseInt(matcher.group(3));

                        Calendar date = Calendar.getInstance();
                        //noinspection MagicConstant
                        date.set(year, month - 1, day);

                        scout.setAdvancementDate(date.getTime());
                    } else {
                        scout.setAdvancementDate(new Date());
                    }
                }

                if (!checkForErrors(errors)) {
                    return false;
                }

                importList.add(scout);
            }

            reader.close();

            for (Scout importedScout : importList) {
                Scout existingScout = LogicScout.findByName(importedScout.getName());

                if (existingScout != null) {
                    existingScout.setName(importedScout.getName());
                    existingScout.setBirthDate(importedScout.getBirthDate());
                    existingScout.setCurrentAdvancementId(importedScout.getCurrentAdvancementId());
                    existingScout.setAdvancementDate(importedScout.getAdvancementDate());

                    LogicScout.update(existingScout);
                } else {
                    importedScout.setTypeId(ScoutTypeConst.BOY_SCOUT.getId());
                    LogicScout.save(importedScout);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        JOptionPane.showMessageDialog(this, "Your scouts have been successfully imported.", "Import Successful", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private boolean handleMeritBadgeImport(String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            Map<MeritBadge, MeritBadgeImport> importMap = new HashMap<MeritBadge, MeritBadgeImport>();

            boolean getMeritBadge = true;
            MeritBadge meritBadge = null;
            java.util.List<Requirement> requirementList = new ArrayList<Requirement>();
            java.util.List<Counselor> counselorList = new ArrayList<Counselor>();

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[0].equals("Merit Badge Name") || record[0].equals("Counselor Name") || record[0].equals("Requirement Name")) {
                    continue;
                }

                if (record[0].isEmpty()) {
                    getMeritBadge = true;

                    if (meritBadge != null) {
                        if (!checkForErrors(errors)) {
                            return false;
                        }

                        MeritBadgeImport badgeImport = new MeritBadgeImport();
                        badgeImport.setRequirementsList(requirementList);
                        badgeImport.setCounselorList(counselorList);

                        importMap.put(meritBadge, badgeImport);

                        meritBadge = null;
                        requirementList = new ArrayList<Requirement>();
                        counselorList = new ArrayList<Counselor>();
                    }

                    continue;
                }

                if (getMeritBadge) {
                    getMeritBadge = false;

                    if (record.length < 3) {
                        errors.append("There are too few values for the merit badge. ").append(errorLine);
                        continue;
                    }


                    meritBadge = new MeritBadge();
                    String badgeName = record[0];

                    if (Util.isEmpty(badgeName)){
                        errors.append("Merit badge name is missing. ").append(errorLine);
                    } else if (badgeName.length() > MeritBadge.COL_NAME_LENGTH) {
                        errors.append("Merit badge name is too long. ").append(errorLine);
                    }
                    meritBadge.setName(badgeName);

                    if (!(checkForBoolean(record[1].trim()))) {
                        errors.append("invalid value: ").append(record[1]).append(". Accepted values are 'true' or 'false'. ").append(errorLine);
                        meritBadge.setRequiredForEagle(false);
                        continue;
                    } else {
                        meritBadge.setRequiredForEagle(Boolean.parseBoolean(record[1].trim()));
                    }

                    String revisionDate = record[2].trim();
                    if (!Util.validateDisplayDateFormat(revisionDate)) {
                        errors.append("invalid date: ").append(record[2]).append(". Valid date format is (MM/DD/YYYY). ").append(errorLine);
                        continue;
                    } else {

                        Pattern pattern = Pattern.compile(Util.DATE_PATTERN);
                        Matcher matcher = pattern.matcher(revisionDate);

                        if (matcher.find()) {
                            int month = Integer.parseInt(matcher.group(1));
                            int day = Integer.parseInt(matcher.group(2));
                            int year = Integer.parseInt(matcher.group(3));

                            Calendar revDate = Calendar.getInstance();
                            //noinspection MagicConstant
                            revDate.set(year, month - 1, day);

                            meritBadge.setRevisionDate(revDate.getTime());
                        } else {
                            meritBadge.setRevisionDate(new Date());
                        }
                    }

                    if (record.length == 3) {
                        continue;
                    }

                    String advancementImgPath = record[3];
                    if (Util.isEmpty(advancementImgPath)){
                        errors.append("Merit badge image path is missing. ").append(errorLine);
                    } else if (advancementImgPath.length() > MeritBadge.COL_IMG_PATH_LENGTH) {
                        errors.append("Merit badge image path is too long. ").append(errorLine);
                    }
                    meritBadge.setImgPath(advancementImgPath);
                    continue;
                }

                if (record[0].startsWith("*")) {
                    if (record.length < 2) {
                        errors.append("Counselors needs both a name and a phone number.").append(errorLine);
                        continue;
                    }

                    Counselor counselor = new Counselor();
                    String counselorName = record[0];
                    if (Util.isEmpty(counselorName)){
                        errors.append("Counselor name is missing. ").append(errorLine);
                    } else if (counselorName.length() > Counselor.COL_NAME_LENGTH) {
                        errors.append("Counselor name is too long. ").append(errorLine);
                    }
                    counselor.setName(counselorName.substring(1, counselorName.length()));

                    String phoneNumber = record[1];
                    if (Util.isEmpty(phoneNumber)){
                        errors.append("Phone number is missing. ").append(errorLine);
                    } else if (counselorName.length() > Counselor.COL_PHONE_NUMBER_LENGTH) {
                        errors.append("Phone number is too long. ").append(errorLine);
                    }

                    if (!Util.validatePhoneNumber(phoneNumber)) {
                        errors.append("Phone number format is incorrect. ").append(errorLine);
                    }

                    counselor.setPhoneNumber(phoneNumber);
                    counselorList.add(counselor);
                    continue;
                }

                if (record.length < 2) {
                    errors.append("Requirements needs both a name and a description.").append(errorLine);
                    continue;
                }

                Requirement requirement = new Requirement();
                String reqName = record[0];
                if (Util.isEmpty(reqName)){
                    errors.append("Requirement name is missing. ").append(errorLine);
                } else if (reqName.length() > Requirement.COL_NAME_LENGTH) {
                    errors.append("Requirement name is too long. ").append(errorLine);
                }
                requirement.setName(reqName);

                String reqDesc = record[1];
                if (Util.isEmpty(reqDesc)){
                    errors.append("Requirement description is missing. ").append(errorLine);
                }
                requirement.setDescription(reqDesc);
                requirement.setTypeId(RequirementTypeConst.MERIT_BADGE.getId());

                requirementList.add(requirement);
            }

            reader.close();

            if (!checkForErrors(errors)) {
                return false;
            }

            MeritBadgeImport badgeImport = new MeritBadgeImport();
            badgeImport.setCounselorList(counselorList);
            badgeImport.setRequirementsList(requirementList);
            importMap.put(meritBadge, badgeImport);

            for (MeritBadge badge : importMap.keySet()) {
                MeritBadge existingBadge = LogicMeritBadge.findByName(badge.getName());

                if (existingBadge != null) {
                    LogicCounselor.deleteAllByBadgeId(existingBadge.getId());
                    LogicRequirement.deleteAllByParentIdAndTypeId(existingBadge.getId(), RequirementTypeConst.MERIT_BADGE.getId());
                }

                badge = LogicMeritBadge.importBadge(badge);

                MeritBadgeImport listContainer = importMap.get(badge);
                java.util.List<Counselor> counselors = listContainer.getCounselorList();
                if (!Util.isEmpty(counselors)) {
                    for (Counselor counselor : counselors) {
                        counselor.setBadgeId(badge.getId());
                    }

                    LogicCounselor.saveList(counselors);
                }

                java.util.List<Requirement> reqList = listContainer.getRequirementsList();
                if (!Util.isEmpty(reqList)) {
                    for (Requirement req : reqList) {
                        req.setParentId(badge.getId());
                    }

                    LogicRequirement.saveList(reqList);
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        JOptionPane.showMessageDialog(this, "Your merit badges have been successfully imported.", "Import Successful", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private boolean checkForBoolean(String arg) {
        return !Util.isEmpty(arg) && ("true".equalsIgnoreCase(arg) || "false".equalsIgnoreCase(arg));
    }

    private boolean handleAwardImport(String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            Map<OtherAward, java.util.List<Requirement>> importMap = new HashMap<OtherAward, java.util.List<Requirement>>();

            boolean getAward = true;
            OtherAward otherAward = null;
            java.util.List<Requirement> requirementList = new ArrayList<Requirement>();

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[0].equals("Award Name") || record[0].equals("Requirement Name")) {
                    continue;
                }

                if (record[0].isEmpty()) {
                    getAward = true;

                    if (otherAward != null) {
                        if (!checkForErrors(errors)) {
                            return false;
                        }

                        importMap.put(otherAward, requirementList);
                        otherAward = null;
                        requirementList = new ArrayList<Requirement>();
                    }

                    continue;
                }

                if (getAward) {
                    getAward = false;

                    otherAward = new OtherAward();
                    String awardName = record[0];

                    if (Util.isEmpty(awardName)){
                        errors.append("Award name is missing. ").append(errorLine);
                    } else if (awardName.length() > OtherAward.COL_NAME_LENGTH) {
                        errors.append("Award name is too long. ").append(errorLine);
                    }
                    otherAward.setName(awardName);

                    if (record.length == 1 || Util.isEmpty(record[1])) {
                        continue;
                    }

                    String awardImgPath = record[1];
                    if (awardImgPath.length() > OtherAward.COL_IMG_PATH_LENGTH) {
                        errors.append("Award image path is too long. ").append(errorLine);
                    }
                    otherAward.setImgPath(awardImgPath);

                    continue;
                }

                if (record.length < 2) {
                    errors.append("Requirements needs both a name and a description.").append(errorLine);
                    continue;
                }

                Requirement requirement = new Requirement();
                String reqName = record[0];
                if (Util.isEmpty(reqName)){
                    errors.append("Requirement name is missing. ").append(errorLine);
                } else if (reqName.length() > Requirement.COL_NAME_LENGTH) {
                    errors.append("Requirement name is too long. ").append(errorLine);
                }
                requirement.setName(reqName);

                String reqDesc = record[1];
                if (Util.isEmpty(reqDesc)){
                    errors.append("Requirement description is missing. ").append(errorLine);
                }
                requirement.setDescription(reqDesc);
                requirement.setTypeId(RequirementTypeConst.OTHER.getId());

                requirementList.add(requirement);
            }

            reader.close();

            if (!checkForErrors(errors)) {
                return false;
            }

            importMap.put(otherAward, requirementList);

            for (OtherAward award : importMap.keySet()) {
                OtherAward existingAward = LogicOtherAward.findByName(award.getName());

                if (existingAward != null) {
                    LogicRequirement.deleteAllByParentIdAndTypeId(existingAward.getId(), RequirementTypeConst.OTHER.getId());
                }

                award = LogicOtherAward.importAward(award);

                java.util.List<Requirement> reqList = importMap.get(award);
                if (Util.isEmpty(reqList)) {
                    continue;
                }

                for (Requirement req : reqList) {
                    req.setParentId(award.getId());
                }

                LogicRequirement.saveList(reqList);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        JOptionPane.showMessageDialog(this, "Your award(s) have been successfully imported.", "Import Successful", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private boolean handleAdvancementImport(String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            Map<Advancement, java.util.List<Requirement>> importMap = new HashMap<Advancement, java.util.List<Requirement>>();

            boolean getAdvancement = true;
            Advancement advancement = null;
            java.util.List<Requirement> requirementList = new ArrayList<Requirement>();

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[0].equals("Advancement Name") || record[0].equals("Requirement Name")) {
                    continue;
                }

                if (record[0].isEmpty()) {
                    getAdvancement = true;

                    if (advancement != null) {
                        if (!checkForErrors(errors)) {
                            return false;
                        }

                        importMap.put(advancement, requirementList);
                        advancement = null;
                        requirementList = new ArrayList<Requirement>();
                    }

                    continue;
                }

                if (getAdvancement) {
                    getAdvancement = false;

                    advancement = new Advancement();
                    String advancementName = record[0];

                    if (Util.isEmpty(advancementName)){
                        errors.append("Advancement name is missing. ").append(errorLine);
                    } else if (advancementName.length() > Advancement.COL_NAME_LENGTH) {
                        errors.append("Advancement name is too long. ").append(errorLine);
                    }
                    advancement.setName(advancementName);

                    if (record.length == 1) {
                        continue;
                    }

                    String advancementImgPath = record[1];
                    if (Util.isEmpty(advancementImgPath)){
                        errors.append("Advancement image path is missing. ").append(errorLine);
                    } else if (advancementImgPath.length() > Advancement.COL_IMG_PATH_LENGTH) {
                        errors.append("Advancement image path is too long. ").append(errorLine);
                    }
                    advancement.setImgPath(advancementImgPath);

                    continue;
                }

                if (record.length < 2) {
                    errors.append("Requirements needs both a name and a description.").append(errorLine);
                    continue;
                }

                Requirement requirement = new Requirement();
                String reqName = record[0];
                if (Util.isEmpty(reqName)){
                    errors.append("Requirement name is missing. ").append(errorLine);
                } else if (reqName.length() > Requirement.COL_NAME_LENGTH) {
                    errors.append("Requirement name is too long. ").append(errorLine);
                }
                requirement.setName(reqName);

                String reqDesc = record[1];
                if (Util.isEmpty(reqDesc)){
                    errors.append("Requirement description is missing. ").append(errorLine);
                }
                requirement.setDescription(reqDesc);
                requirement.setTypeId(RequirementTypeConst.ADVANCEMENT.getId());

                requirementList.add(requirement);
            }

            reader.close();

            if (!checkForErrors(errors)) {
                return false;
            }

            importMap.put(advancement, requirementList);

            for (Advancement adv : importMap.keySet()) {
                Advancement existingAdv = LogicAdvancement.findByName(adv.getName());

                if (existingAdv != null) {
                    LogicRequirement.deleteAllByParentIdAndTypeId(existingAdv.getId(), RequirementTypeConst.ADVANCEMENT.getId());
                }

                adv = LogicAdvancement.importAdv(adv);
                if (adv == null) {
                    continue;
                }

                java.util.List<Requirement> reqList = importMap.get(adv);
                if (Util.isEmpty(reqList)) {
                    continue;
                }

                for (Requirement req : reqList) {
                    req.setParentId(adv.getId());
                }

                LogicRequirement.saveList(reqList);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        JOptionPane.showMessageDialog(this, "Your advancements have been successfully imported.", "Import Successful", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private boolean checkForErrors(StringBuilder errors) {
        if (errors.length() <= 0) {
            return true;
        }

        String errorHeaderMessage = "Please fix the following issues and try again.\n\n";
        errors.insert(0, errorHeaderMessage);

        JOptionPane.showMessageDialog(this, errors, "Import Errors", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    private void btnBrowseMouseClicked() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");

        CustomChooser chooser = new CustomChooser();
        chooser.setDialogTitle("Select a file to import");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);
        int returnValue = chooser.showOpenDialog(this);
        chooser.resetLookAndFeel();

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            txtImportPath.setText(file.getPath());
        }
    }

    private void btnCancelMouseClicked() {
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        JLabel lblIntro = new JLabel();
        txtImportPath = new JTextFieldDefaultText();
        JButton btnBrowse = new JButton();
        JLabel lblIntro2 = new JLabel();
        JScrollPane scrollPane1 = new JScrollPane();
        txtImportInstructions = new JEditorPane();
        JPanel buttonBar = new JPanel();
        btnImport = new JButton();
        JButton btnCancel = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(625, 465));
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBackground(Color.white);
            dialogPane.setName("dialogPane");
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setMinimumSize(new Dimension(600, 400));
                contentPanel.setPreferredSize(new Dimension(600, 400));
                contentPanel.setBackground(Color.white);
                contentPanel.setName("contentPanel");
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {305, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 55, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};

                //---- lblIntro ----
                lblIntro.setText("Please Select a file to import");
                lblIntro.setFont(new Font("Vijaya", Font.PLAIN, 20));
                lblIntro.setForeground(new Color(51, 102, 153));
                lblIntro.setName("lblIntro");
                contentPanel.add(lblIntro, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- txtImportPath ----
                txtImportPath.setDefaultText("Import file path");
                txtImportPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtImportPath.setName("txtImportPath");
                contentPanel.add(txtImportPath, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- btnBrowse ----
                btnBrowse.setText("Browse");
                btnBrowse.setBackground(new Color(51, 102, 153));
                btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnBrowse.setForeground(Color.white);
                btnBrowse.setFocusPainted(false);
                btnBrowse.setName("btnBrowse");
                btnBrowse.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnBrowseMouseClicked();
                    }
                });
                contentPanel.add(btnBrowse, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblIntro2 ----
                lblIntro2.setText("Import file instructions");
                lblIntro2.setFont(new Font("Vijaya", Font.PLAIN, 20));
                lblIntro2.setForeground(new Color(51, 102, 153));
                lblIntro2.setName("lblIntro2");
                contentPanel.add(lblIntro2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== scrollPane1 ========
                {
                    scrollPane1.setName("scrollPane1");

                    //---- txtImportInstructions ----
                    txtImportInstructions.setBackground(Color.white);
                    txtImportInstructions.setName("txtImportInstructions");
                    scrollPane1.setViewportView(txtImportInstructions);
                }
                contentPanel.add(scrollPane1, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setBackground(Color.white);
                buttonBar.setName("buttonBar");
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).rowHeights = new int[] {50};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- btnImport ----
                btnImport.setText("Import");
                btnImport.setBackground(new Color(51, 156, 229));
                btnImport.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnImport.setForeground(Color.white);
                btnImport.setFocusPainted(false);
                btnImport.setName("btnImport");
                btnImport.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnImportMouseClicked();
                    }
                });
                buttonBar.add(btnImport, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnCancel ----
                btnCancel.setText("Cancel");
                btnCancel.setForeground(Color.white);
                btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnCancel.setBackground(new Color(206, 0, 0));
                btnCancel.setFocusPainted(false);
                btnCancel.setName("btnCancel");
                btnCancel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnCancelMouseClicked();
                    }
                });
                buttonBar.add(btnCancel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextFieldDefaultText txtImportPath;
    private JEditorPane txtImportInstructions;
    private JButton btnImport;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
