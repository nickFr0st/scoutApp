/*
 * Created by JFormDesigner on Wed Oct 22 22:21:45 MDT 2014
 */

package scout.clientPnls.IEPnls;

import au.com.bytecode.opencsv.CSVReader;
import guiUtil.CustomChooser;
import guiUtil.JTextFieldDefaultText;
import scout.clientPnls.PnlBadgeConf;
import scout.dbObjects.Advancement;
import scout.dbObjects.Requirement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public ImportDialog(Frame owner, int importTypeId) {
        super(owner, true);
        initComponents();
        wiz = contentPanel;
        this.importTypeId = importTypeId;

        switch (importTypeId) {
            case PnlBadgeConf.ADVANCEMENT:
                setTitle("Advancements Import");
                setTxtImportInstructions(getClass().getResource("/instructions/IEAdvancementInstructions.html").toString());
                break;
            case PnlBadgeConf.MERIT_BAGDGE:
                break;
            case PnlBadgeConf.OTHER:
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
            return;
        }

        switch (importTypeId) {
            case PnlBadgeConf.ADVANCEMENT:
                handleAdvancementImport(txtImportPath.getText());
                break;
            case PnlBadgeConf.MERIT_BAGDGE:
                break;
            case PnlBadgeConf.OTHER:
                break;
        }
    }

    private void handleAdvancementImport(String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            Map<Advancement, java.util.List<Requirement>> importMap = new HashMap<Advancement, java.util.List<Requirement>>();

            boolean getAdvancement = true;
            Advancement advancement = null;
            java.util.List<Requirement> requirementList = new ArrayList<Requirement>();

            String[] record;
            int line = 1;
            while ((record = reader.readNext()) != null) {

                // todo: validate data lengths

                // check for the headers
                if (record[0].equals("Advancement Name") || record[0].equals("Requirement Name")) {
                    continue;
                }

                if (record[0].isEmpty()) {
                    getAdvancement = true;

                    if (advancement != null) {
                        importMap.put(advancement, requirementList);
                        advancement = null;
                        requirementList = new ArrayList<Requirement>();
                    }
                }

                if (getAdvancement) {
                    advancement = new Advancement();
                    String advancementName = record[0];
                    getAdvancement = false;
                    advancement.setName(advancementName);
                    continue;
                }

                if (record.length < 2) {
                    JOptionPane.showMessageDialog(this, "Requirements on line: " + line + "needs both a name and a description.");
                    return;
                }

                Requirement requirement = new Requirement();
                requirement.setName(record[0]);
                requirement.setDescription(record[1]);
                requirementList.add(requirement);

                ++line;
            }

            importMap.put(advancement, requirementList);

            // todo: save down items

            reader.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
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
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        lblIntro = new JLabel();
        txtImportPath = new JTextFieldDefaultText();
        btnBrowse = new JButton();
        lblIntro2 = new JLabel();
        scrollPane1 = new JScrollPane();
        txtImportInstructions = new JEditorPane();
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
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel lblIntro;
    private JTextFieldDefaultText txtImportPath;
    private JButton btnBrowse;
    private JLabel lblIntro2;
    private JScrollPane scrollPane1;
    private JEditorPane txtImportInstructions;
    private JPanel buttonBar;
    private JButton btnImport;
    private JButton btnCancel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
