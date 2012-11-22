package serv;

import entity.FileEntity;
import entity.FileEntityDescription;
import entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Server extends Remote {
    
    public void register(String username, String password)throws RemoteException;
    public void unregister(String username, String password)throws RemoteException;
    
    public void uploadFile(FileEntity file)throws RemoteException;
    
    public void login(String username, String password)throws RemoteException;
    
    public ArrayList<FileEntityDescription> loadAllFilesof(String username)throws RemoteException;
    public ArrayList<FileEntityDescription> loadFiles(String filter, String clientName)throws RemoteException;

    public void deleteFile(String username, String filename)throws RemoteException;

    public FileEntity getCompleteFile(String clientName, String filename)throws RemoteException;

    public void updateFile(String clientname, String file, byte[] data)throws RemoteException;

    public void logout(User u)throws RemoteException;
    
    public String getStatistics(String clientname)throws RemoteException;
    
}
