/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entity.FileEntity;
import entity.FileEntityDescription;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import serv.Server;

/**
 *
 * @author Marcel
 */
public class ClientImpl extends UnicastRemoteObject implements Client {

    public String clientName;
    public String clientPasswd;
    
    
    public Server servObj;
    private String servName;
    public ArrayList<FileEntityDescription> yourfiles = new ArrayList<>();
    public ArrayList<FileEntityDescription> allfiles = new ArrayList<>();
    public FileEntity currentFileToUpload = new FileEntity(new FileEntityDescription("", 0, "", true, true,new Date()), null);
    
    public ArrayList<MyObserver> observers = new ArrayList<>();
    public String downloadFolder=".";

    public ClientImpl(String sname) throws RemoteException {
        this.servName = sname;
        try {

            servObj = (Server) Naming.lookup("rmi://localhost/fileserver");
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to bank: " + this.servName);
    }

    public void validateFileToUpload() throws Exception {
        FileEntity f = this.currentFileToUpload;
        if (f.getContent() == null) {
            throw new Exception("File content not valid");
        }
    }

    public void addObserver(MyObserver o) {
        this.observers.add(o);
    }

    public void notifyAll(Object x) {
        ClientImpl y = this;
        for (MyObserver o : observers) {
            o.update(y, x);
        }
    }

    public void storeFileOnDisk(FileEntity completeFile) throws IOException {
        String separator = System.getProperty("file.separator");
        String CompleteFilePath=this.downloadFolder;
        if(!downloadFolder.endsWith(separator)){
            CompleteFilePath+=separator;
        }
        CompleteFilePath+=completeFile.getDescription().name;
        
        File file = new File(CompleteFilePath);

        boolean created = file.createNewFile();
        if (!created) {
            System.out.println("File already exists.");
        } else {
            FileOutputStream fos = new FileOutputStream(file);
            
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(completeFile.getContent());
            bos.flush();
            bos.close();
        }
    }
}
