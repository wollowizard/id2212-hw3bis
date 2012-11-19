package serv;

import entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    
    public void register(String username, String password)throws RemoteException;
    public void unregister(String username, String password)throws RemoteException;
    
    public void uploadFile(String filename, String owner, String access, byte[] content)throws RemoteException;
    
    public void login(String username, String password)throws RemoteException;
    
    
}
