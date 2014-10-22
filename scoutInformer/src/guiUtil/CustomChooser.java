package guiUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Locale;

/**
 * Created by Nathanael on 10/19/2014
 */
public class CustomChooser extends JFileChooser {
    private LookAndFeel old;

    public CustomChooser() {
        super();

        old = UIManager.getLookAndFeel();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable th) {
            th.printStackTrace();
        }

        super.updateUI();
    }

    public CustomChooser(String title, int selectionMode) {
        this();

        setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                String name = f.getName().toLowerCase(Locale.US);
                return name.endsWith(".csv") || f.isDirectory();
            }
            public String getDescription() {
                return "CSV delimited text files";
            }
        });
        setDialogTitle(title);
        setFileSelectionMode(selectionMode);
    }

    public void resetLookAndFeel() {
        try {
            UIManager.setLookAndFeel(old);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
