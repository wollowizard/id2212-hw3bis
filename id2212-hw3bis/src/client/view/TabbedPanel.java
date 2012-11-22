/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.ClientImpl;
import client.EventEnum;
import client.MyObserver;
import entity.FileEntity;
import entity.FileEntityDescription;
import entity.User;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfredo
 */
public class TabbedPanel extends javax.swing.JPanel implements MyObserver {

    private ClientImpl client;
    private TabbedPanel thispanel = this;
    private MainFrame parent;

    /**
     * Creates new form TabbedPanel
     */
    public TabbedPanel(ClientImpl c, MainFrame parent) {
        initComponents();
        client = c;
        client.addObserver(this);
        this.parent = parent;

        welcomeLabel.setText("Welcome " + client.clientName);

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
                if (index == 1) {
                    try {
                        client.yourfiles = client.servObj.loadAllFilesof(client.clientName);
                        client.notifyAll(EventEnum.YOURFILE);

                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(jPanel1, ex.getMessage());
                    }
                }

            }
        };
        this.jTabbedPane1.addChangeListener(changeListener);
        this.yourFilesTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Left mouse click
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Do something
                } // Right mouse click
                else if (SwingUtilities.isRightMouseButton(e)) {
                    // get the coordinates of the mouse click
                    Point p = e.getPoint();

                    // get the row index that contains that coordinate
                    int rowNumber = yourFilesTable.rowAtPoint(p);

                    // Get the ListSelectionModel of the JTable
                    ListSelectionModel model = yourFilesTable.getSelectionModel();

                    // set the selected interval of rows. Using the "rowNumber"
                    // variable for the beginning and end selects only that one row.
                    model.setSelectionInterval(rowNumber, rowNumber);
                    String fname = (String) yourFilesTable.getValueAt(rowNumber, 0);
                    PopUpMenu menu = new PopUpMenu(client, fname, PopUpMenu.FROMYOURFILES, thispanel);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        this.allFilesTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Left mouse click
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Do something
                } // Right mouse click
                else if (SwingUtilities.isRightMouseButton(e)) {
                    // get the coordinates of the mouse click
                    Point p = e.getPoint();

                    // get the row index that contains that coordinate
                    int rowNumber = allFilesTable.rowAtPoint(p);

                    // Get the ListSelectionModel of the JTable
                    ListSelectionModel model = allFilesTable.getSelectionModel();

                    // set the selected interval of rows. Using the "rowNumber"
                    // variable for the beginning and end selects only that one row.
                    model.setSelectionInterval(rowNumber, rowNumber);
                    String fname = (String) allFilesTable.getValueAt(rowNumber, 0);
                    PopUpMenu menu = new PopUpMenu(client, fname, PopUpMenu.FROMALLFILES, thispanel);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });



    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        uploadFile = new javax.swing.JButton();
        privacyComboBox = new javax.swing.JComboBox();
        permissionComboBox = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        fileNameLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        yourFilesTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        filterTextField = new javax.swing.JTextField();
        SearchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        allFilesTable = new javax.swing.JTable();
        welcomeLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();

        uploadFile.setText("Choose");
        uploadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadFileActionPerformed(evt);
            }
        });

        privacyComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Public", "Private"}));

        permissionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Read-only", "Editable" }));

        jLabel2.setText("Choose file to update");

        fileNameLabel.setText("--------------------------------------------");

        jLabel4.setText("Choose privacy and permissions");

        jLabel5.setText("Upload your file");

        jButton1.setText("Upload!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(34, 34, 34)
                        .addComponent(uploadFile)
                        .addGap(86, 86, 86)
                        .addComponent(fileNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(privacyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80)
                                .addComponent(permissionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jButton1)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uploadFile)
                    .addComponent(jLabel2)
                    .addComponent(fileNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(privacyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(permissionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Upload", jPanel1);

        yourFilesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Filename", "Permisson", "Privacy", "Size"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(yourFilesTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Your remote files", jPanel2);

        SearchButton.setText("Search");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });

        allFilesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Filename", "Owner", "Permission", "Privacy", "Size"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(allFilesTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(filterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(SearchButton)
                .addGap(0, 225, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("All Files", jPanel3);

        welcomeLabel.setText("Welcome ");

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(logoutButton)
                .addGap(75, 75, 75))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(welcomeLabel)
                    .addComponent(logoutButton))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateYourFiles() {
        System.out.println("Updating your files");
        DefaultTableModel model = (DefaultTableModel) yourFilesTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        for (FileEntityDescription fe : client.yourfiles) {
            System.out.println(fe.name);

            model.addRow(new Object[]{fe.name, fe.writepermission, fe.privateFile, fe.size.toString()});
            //model.getValueAt(0, 0).addMouseListener(new PopClickListener());
        }
        
        System.out.println("Number of rows in table" + model.getRowCount());
        
    }

    private void updateAllFiles() {
        System.out.println("Updating all files, new list:");

        DefaultTableModel model = (DefaultTableModel) allFilesTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        for (FileEntityDescription fe : client.allfiles) {
            System.out.println(fe.name);

            model.addRow(new Object[]{fe.name, fe.ownerName, fe.writepermission, fe.privateFile, fe.size.toString()});
        }
    }

    private void uploadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadFileActionPerformed
        // TODO add your handling code here:

        //Create a file chooser
        final JFileChooser fc = new JFileChooser();

        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            Path path = Paths.get(file.getAbsolutePath());
            try {
                byte[] data = Files.readAllBytes(path);
                client.currentFileToUpload.setContent(data);
                client.currentFileToUpload.getDescription().size = data.length;
                client.currentFileToUpload.getDescription().name = file.getName();
                this.fileNameLabel.setText(file.getName());


            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Invalid file");

        }

    }//GEN-LAST:event_uploadFileActionPerformed

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        this.search();


    }//GEN-LAST:event_SearchButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            FileEntity file = client.currentFileToUpload;
            boolean privateFile = true;
            boolean writepermission = true;
            if (permissionComboBox.getSelectedIndex() == 0) {
                writepermission = false;
            }
            if (privacyComboBox.getSelectedIndex() == 0) {
                privateFile = false;
            }
            file.getDescription().ownerName = client.clientName;
            file.getDescription().writepermission = writepermission;
            file.getDescription().privateFile = privateFile;

            client.validateFileToUpload();

            client.servObj.uploadFile(file);


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }



    }//GEN-LAST:event_jButton1ActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed

        try {
            // TODO add your handling code here:

            client.servObj.logout(new User(client.clientName, client.clientPasswd));
        } catch (RemoteException ex) {
            Logger.getLogger(TabbedPanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            new SignFrame(client).setVisible(true);
            parent.dispose();

        }

    }//GEN-LAST:event_logoutButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SearchButton;
    private javax.swing.JTable allFilesTable;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JTextField filterTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JComboBox permissionComboBox;
    private javax.swing.JComboBox privacyComboBox;
    private javax.swing.JButton uploadFile;
    private javax.swing.JLabel welcomeLabel;
    private javax.swing.JTable yourFilesTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Object o, Object x) {
        updateYourFiles();
    }

    public void search() {
        try {
            client.allfiles = client.servObj.loadFiles(filterTextField.getText());

            updateAllFiles();
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
