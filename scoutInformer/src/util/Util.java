package util;

import guiUtil.JPasswordFieldDefaultText;
import guiUtil.JTextFieldDefaultText;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Malloch on 6/29/14
 */
public class Util {
    public static final String DATE_PATTERN = "(\\d{2})/(\\d{2})/(\\d{4})";

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static void saveProperties(Properties properties, String propertyFileName) {
        try {
            File f = new File(propertyFileName.substring(propertyFileName.indexOf("/") + 1));
            OutputStream out = new FileOutputStream(f);
            properties.store(out, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setError(JLabel lblNewUserError, String errorMessage) {
        lblNewUserError.setText("* " + errorMessage);
        lblNewUserError.setVisible(true);
    }

    public static String getTxtFieldString(JTextField textField) {
        if (textField instanceof JTextFieldDefaultText) {
            return ((JTextFieldDefaultText) textField).isMessageDefault() ? "" : textField.getText();
        } else if (textField instanceof JPasswordFieldDefaultText) {
            return ((JPasswordFieldDefaultText) textField).isMessageDefault() ? "" : textField.getText();
        } else {
            return textField.getText();
        }
    }

    public static String listToString(List list) {
        StringBuilder sb = new StringBuilder();

        if (isEmpty(list)) {
            return sb.toString();
        }

        boolean firstLine = true;
        for (Object obj : list) {
            if (firstLine) {
                sb.append(obj);
                firstLine = false;
            } else {
                sb.append(",");
                sb.append(obj);
            }
        }

        return sb.toString();
    }

    public static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{3}\\)[-\\s]\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;

    }

    public static boolean validateEmail(String email) {
        String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(pattern);
    }
}
