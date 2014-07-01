/*
 * Created by JFormDesigner on Sun Jun 29 18:20:11 MDT 2014
 */

package scout;

import scout.clientPnls.PnlHome;
import scout.clientPnls.PnlSignIn;

import javax.swing.*;
import java.awt.*;

/**
 * @author User #2
 */
public class SignIn extends JFrame implements GuiManager {
    private PnlSignIn stepSignIn;
    private PnlHome stepHome;
    private int stepCount;

    public SignIn() {
        initComponents();

        stepSignIn = new PnlSignIn(this);
        stepHome = new PnlHome(this);

        this.add(stepSignIn);

//        DBConnector connector = new DBConnector();
//        if (!connector.checkForDBConnection()) {
//            // todo: get a connection.
//        }
    }

    @Override
    public void nextStep() {
        switch (stepCount) {
            case 0:
                this.remove(stepSignIn);
                this.add(stepHome);
                stepCount++;
        }
    }

    @Override
    public void previousStep() {
        switch (stepCount) {
            case 1:
                this.remove(stepHome);
                this.add(stepSignIn);
                stepCount--;
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents

        //======== this ========
        setMinimumSize(new Dimension(1200, 800));
        setResizable(false);
        setIconImage(new ImageIcon(getClass().getResource("/images/flurDeLis16.png")).getImage());
        setTitle("BSA Database");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
