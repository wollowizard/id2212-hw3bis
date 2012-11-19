package serv;

import entity.FileEntity;
import entity.User;
import id2212.hw3.database.DbWrapper;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.jar.Attributes;

@SuppressWarnings("serial")
public class ServerImpl extends UnicastRemoteObject implements Server {

    private String bankName;

    public ServerImpl(String bankName) throws RemoteException {
        super();
        this.bankName = bankName;
    }

    @Override
    public void login(String username, String password) throws RemoteException {
        try {
            DbWrapper.getInstance().loadUser(new User(username, password));
            
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    @Override
    public void register(String username, String password) throws RemoteException {
        if(password.length()<5){
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
    public void uploadFile(String filename, String owner, String access, byte[] content) throws RemoteException {
        DbWrapper.getInstance().storeFileEntity(new FileEntity(filename, owner, access, content));
    }
}
