/*
 * Created by JFormDesigner on Sat Oct 18 16:54:05 MDT 2014
 */

package scout.clientPnls.IEPnls;

import scout.clientPnls.PnlBadgeConf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class ExportDialog extends JDialog {
    private JPanel wiz;

    public ExportDialog(Frame owner) {
        super(owner, true);
        initComponents();
    }

    public ExportDialog(Frame owner, int exportTypeId) {
        super(owner, true);
        initComponents();
        wiz = contentPanel;

        switch (exportTypeId) {
            case PnlBadgeConf.ADVANCEMENT:
                updateConfigPnl(new ExportAdvancements());
                break;
            case PnlBadgeConf.MERIT_BAGDGE:
                break;
            case PnlBadgeConf.OTHER:
                break;
        }
    }

    private void updateConfigPnl(JPanel newPanel) {
        dialogPane.remove(wiz);
        dialogPane.add(newPanel, BorderLayout.CENTER);
        wiz = newPanel;
    }

    private void cancelButtonMouseClicked() {
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
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
                contentPanel.setName("contentPanel");
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
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

                //---- okButton ----
                okButton.setText("Export");
                okButton.setForeground(Color.white);
                okButton.setBackground(new Color(51, 156, 229));
                okButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
                okButton.setFocusPainted(false);
                okButton.setName("okButton");
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.setForeground(Color.white);
                cancelButton.setBackground(new Color(206, 0, 0));
                cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
                cancelButton.setFocusPainted(false);
                cancelButton.setName("cancelButton");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelButtonMouseClicked();
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
