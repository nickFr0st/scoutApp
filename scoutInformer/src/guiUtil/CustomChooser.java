package guiUtil;

import javax.swing.*;

/**
 * Created by Nathanael on 10/19/2014.
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

    public void resetLookAndFeel() {
        try {
            UIManager.setLookAndFeel(old);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
