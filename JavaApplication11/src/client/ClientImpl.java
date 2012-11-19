/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import bank.Server;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author Marcel
 */
public class ClientImpl extends UnicastRemoteObject implements Client {

    public String clientName;
    public Server servObj;
    private String servName;
    
    
    public String clientPasswd;

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
}
