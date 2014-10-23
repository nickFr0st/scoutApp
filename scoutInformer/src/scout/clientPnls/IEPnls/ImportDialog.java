/*
 * Created by JFormDesigner on Wed Oct 22 22:21:45 MDT 2014
 */

package scout.clientPnls.IEPnls;

import scout.clientPnls.PnlBadgeConf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author User #2
 */
public class ImportDialog extends JDialog {
    private JPanel wiz;
    private int importTypeId;

    public ImportDialog(Frame owner) {
        super(owner);
        initComponents();
    }

    public ImportDialog(Frame owner, int exportTypeId) {
        super(owner, true);
        initComponents();
        wiz = contentPanel;
        this.importTypeId = exportTypeId;

        switch (exportTypeId) {
            case PnlBadgeConf.ADVANCEMENT:
                setTitle("Advancements Import");
//                updateConfigPnl(new ImportAdvancements());
                break;
            case PnlBadgeConf.MERIT_BAGDGE:
                break;
            case PnlBadgeConf.OTHER:
                break;
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        buttonBar = new JPanel();
        btnImport = new JButton();
        btnCancel = new JButton();

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
                contentPanel.setName("contentPanel");
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
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
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel buttonBar;
    private JButton btnImport;
    private JButton btnCancel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
