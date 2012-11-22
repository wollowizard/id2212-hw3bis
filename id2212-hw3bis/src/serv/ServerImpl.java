package serv;

import entity.FileEntity;
import entity.FileEntityDescription;
import entity.User;
import id2212.hw3.database.DbWrapper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class ServerImpl extends UnicastRemoteObject implements Server {

    private String bankName;
    private String FOLDERNAME = "FILES";

    public ServerImpl(String bankName) throws RemoteException {
        super();
        this.bankName = bankName;
    }

    @Override
    public void login(String username, String password) throws RemoteException {
        try {
            DbWrapper.getInstance().validateUser(new User(username, password));

        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        
    }

    @Override
    public void register(String username, String password) throws RemoteException {
        if (password.length() < 5) {
            throw new RemoteException("Password must be at least 5 characters");
        }

        try {
            DbWrapper.getInstance().storeUser(new User(username, password));
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    public void unregister(String username, String password) throws RemoteException {
        try {
            DbWrapper.getInstance().deleteUser(new User(username, password));
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    @Override
    public void uploadFile(FileEntity file) throws RemoteException {
        try {
            this.storeFileOnDisk(file);
            DbWrapper db = DbWrapper.getInstance();
            db.storeFileEntity(file, getPathOfFileGivenName(file.getDescription().name));
            db.uploadUploadCounter(file.getDescription().ownerName);
        } catch (IOException | SQLException ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    @Override
    public ArrayList<FileEntityDescription> loadAllFilesof(String username) throws RemoteException {


        ArrayList<FileEntityDescription> toreturn;
        try {
            toreturn = DbWrapper.getInstance().loadAllFilesof(username);
        } catch (SQLException ex) {
            throw new RemoteException(ex.getMessage());
        }
        return toreturn;

    }

    @Override
    public ArrayList<FileEntityDescription> loadFiles(String filter, String clientName) throws RemoteException {


        ArrayList<FileEntityDescription> toreturn;
        try {
            toreturn = DbWrapper.getInstance().loadFiles(filter, clientName);
        } catch (SQLException ex) {
            throw new RemoteException(ex.getMessage());
        }

        return toreturn;
    }

    @Override
    public void deleteFile(String username, String filename) throws RemoteException {
        DbWrapper db = DbWrapper.getInstance();
        FileEntityDescription fd;
        try {
            fd = db.loadFileEntity(filename);
        } catch (SQLException ex) {
            throw new RemoteException(ex.getMessage());
        }
        System.out.println(username + " trying to delete " + fd.name + " of " + fd.ownerName + "which is private=" + fd.privateFile + " and writepermission " + fd.writepermission);



        if (fd.privateFile) {
            //private file
            if (username.compareTo(fd.ownerName) != 0) {
                //user different from owner
                throw new RemoteException("You are not allowed to delete this file, since it is private and you are not the owner");
            }
        } else {


            if ((!fd.writepermission) && username.compareTo(fd.ownerName) != 0) {

                throw new RemoteException("You are not allowed to delete this file, since it is public but you don't have write permission on it");
            }
        }
        try {
            String abspath=getPathOfFileGivenName(filename);
            
            System.out.println("Abs path: "  + abspath);
            DbWrapper.getInstance().deleteFile(filename);
            
            File f = new File(abspath);
            if (!f.exists()) {
                throw new IllegalArgumentException(
                        "Delete: no such file or directory: " + abspath);
            }

            if (!f.canWrite()) {
                throw new IllegalArgumentException("Delete: write protected: "
                        + abspath);
            }
            f.delete();

        } catch (IOException | SQLException ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    @Override
    public FileEntity getCompleteFile(String username, String filename) throws RemoteException {
        DbWrapper db = DbWrapper.getInstance();

        FileEntityDescription fd;
        try {
            fd = db.loadFileEntity(filename);
        } catch (SQLException ex) {
            throw new RemoteException(ex.getMessage());
        }

        System.out.println(username + " trying to download " + fd.name + " of " + fd.ownerName + "which is private=" + fd.privateFile + " and writepermission " + fd.writepermission);



        if (fd.privateFile) {
            //private file
            if (username.compareTo(fd.ownerName) != 0) {
                //user different from owner
                throw new RemoteException("You are not allowed to download this file, since it is private and you are not the owner");

            }
        }
        try {
            db.uploadDownloadCounter(username);
            return db.loadCompleteFile(filename);
        } catch (SQLException | IOException ex) {
            throw new RemoteException(ex.getMessage());
        }

    }

    @Override
    public void updateFile(String clientname, String file, byte[] data) throws RemoteException {


        try {
            DbWrapper db = DbWrapper.getInstance();
            FileEntityDescription fd = db.loadFileEntity(file);
            if (fd.privateFile) {
                //private file
                if (clientname.compareTo(fd.ownerName) != 0) {
                    //user different from owner
                    throw new RemoteException("You are not allowed to update this file, since it is private and you are not the owner");
                }
            } else {
                if ((!fd.writepermission) && clientname.compareTo(fd.ownerName) != 0) {
                    throw new RemoteException("You are not allowed to update this file, since it is public but you don't have write permission on it");
                }
            }

            db.editFileLastModified(file);
            db.editFileSize(file, data.length);

            String path = db.getFileLocation(file);
            FileOutputStream out = new FileOutputStream(path);
            out.write(data);
            out.close();

        } catch (SQLException | IOException ex) {
            throw new RemoteException(ex.getMessage());
        }

    }

    public void storeFileOnDisk(FileEntity completeFile) throws IOException {


        String separator = System.getProperty("file.separator");
        String completePath = new java.io.File(".").getCanonicalPath();
        if (!completePath.endsWith(separator)) {
            completePath += separator;
        }
        completePath += this.FOLDERNAME;

        //create dir if does not exist
        File directory = new File(completePath);
        directory.mkdir();


        //creare file path
        if (!completePath.endsWith(separator)) {
            completePath += separator;
        }
        completePath += completeFile.getDescription().name;

        File file = new File(completePath);

        boolean created = file.createNewFile();
        if (!created) {
            throw new IOException("file already exists");
        } else {
            FileOutputStream fos = new FileOutputStream(file);

            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(completeFile.getContent());
            bos.flush();
            bos.close();
        }
    }

    @Override
    public void logout(User u) throws RemoteException {
        try {
            DbWrapper.getInstance().logoutUser(u);
        } catch (SQLException ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    public String getPathOfFileGivenName(String filename) throws IOException {


        String separator = System.getProperty("file.separator");

        String completePath = new java.io.File(".").getCanonicalPath();
        if (!completePath.endsWith(separator)) {
            completePath += separator;
        }
        completePath += this.FOLDERNAME;


        //creare file path
        if (!completePath.endsWith(separator)) {
            completePath += separator;
        }
        completePath += filename;
        return completePath;

    }

    @Override
    public String getStatistics(String clientname) throws RemoteException {
        try {
            
            return DbWrapper.getInstance().getStatistics(clientname);
        } catch (SQLException ex) {
            throw new RemoteException(ex.getMessage());
        }
        
    }
}
