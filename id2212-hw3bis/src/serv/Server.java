package serv;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    
    public void register(String username, String password)throws RemoteException;
    public void unregister(String username, String password)throws RemoteException;
    
    public void login(String username, String password)throws RemoteException;
    
    
}
