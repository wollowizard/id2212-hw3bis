package serv;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class ServerImpl extends UnicastRemoteObject implements Server {

    private String bankName;

    public ServerImpl(String bankName) throws RemoteException {
        super();
        this.bankName = bankName;
    }
}
