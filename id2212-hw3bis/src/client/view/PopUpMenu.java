/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.ClientImpl;
import client.EventEnum;
import entity.FileEntity;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author alfredo
 */
class PopUpMenu extends JPopupMenu {

    public static final String FROMYOURFILES = "A";
    public static final String FROMALLFILES = "B";
    JMenuItem downloadItem;
    JMenuItem deleteItem;
    JMenuItem updateItem;
    ClientImpl client;
    String filename;
    TabbedPanel parentPanel;

    public PopUpMenu(ClientImpl c, final String file, final String fromtable, TabbedPanel tabbed) {
        client = c;
        filename = file;
        parentPanel = tabbed;

        downloadItem = new JMenuItem("Download");
        add(downloadItem);
        downloadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileEntity completeFile = client.servObj.getCompleteFile(client.clientName, filename);
                    client.storeFileOnDisk(completeFile);
                    parentPanel.updateStatistics();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(downloadItem, ex.getMessage());
                }
            }
        });

        deleteItem = new JMenuItem("Delete");
        add(deleteItem);
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.servObj.deleteFile(client.clientName, filename);
                    if (fromtable.equals(FROMYOURFILES)) {
                        client.yourfiles = client.servObj.loadAllFilesof(client.clientName);
                        client.notifyAll(EventEnum.YOURFILE);
                        parentPanel.updateStatistics();
                    } else {
                        parentPanel.search();
                    }
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(downloadItem, ex.getMessage());
                }
            }
        });


        updateItem = new JMenuItem("Update");
        add(updateItem);
        updateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    final JFileChooser fc = new JFileChooser();

                    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int returnVal = fc.showOpenDialog(parentPanel);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File chosen = fc.getSelectedFile();
                        if (!chosen.getName().equals(file)) {
                            JOptionPane.showMessageDialog(parentPanel, "Select a file with the same name as the one you are updating");
                            return;
                        }

                        Path path = Paths.get(chosen.getAbsolutePath());
                        try {
                            byte[] data = Files.readAllBytes(path);
                            client.servObj.updateFile(client.clientName,file, data);
                            parentPanel.updateStatistics();

                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(parentPanel, ex.getMessage());
                        }

                    } else {
                        JOptionPane.showMessageDialog(parentPanel, "Invalid file selected");
                    }


                    if (fromtable.equals(FROMYOURFILES)) {
                        client.yourfiles = client.servObj.loadAllFilesof(client.clientName);
                        client.notifyAll(EventEnum.YOURFILE);
                    } else {
                        parentPanel.search();
                    }
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(downloadItem, ex.getMessage());
                }
            }
        });

    }
}
