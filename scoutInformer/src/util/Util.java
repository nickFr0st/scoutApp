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
}
