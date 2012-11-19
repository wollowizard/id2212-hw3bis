/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id2212.hw2.server;

import id2212.hw2.bank.Account;
import id2212.hw2.bank.Bank;
import id2212.hw2.bank.RejectedException;
import id2212.hw2.item.Item;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcel
 */
public class ServerImpl extends UnicastRemoteObject implements Server  {

    private String servName;
    private String bankName;
    private Bank objBank;
    private Map<String, Account> listAccounts = new ConcurrentHashMap<String, Account>();
    private Map<Item, String> listItems = new  ConcurrentHashMap<Item, String>();
    
    
    public ServerImpl(String sname, String bname) throws RemoteException {
        super();
        this.servName = sname;
        this.bankName = bname;
        try {
            objBank = (Bank)Naming.lookup(this.bankName);
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to bank: " + this.bankName);
    }
    
    @Override
    public void registerClient(String name) throws RemoteException {
        try {
            listAccounts.put(name, this.objBank.newAccount(name));
        } catch (RejectedException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void unregisterClient(String name) throws RemoteException {
        ArrayList<Item> tmp = new ArrayList<Item>();
        for (Map.Entry<Item, String> entry: listItems.entrySet()) {
            if (entry.getValue().compareTo(name)==0) {
                tmp.add(entry.getKey());
            }
        }
        for (Item i : tmp) {
            listItems.remove(i);
        }
        
        this.objBank.deleteAccount(name);
        this.listAccounts.remove(name);
    }

    @Override
    public void sellItem(Item it, String name) throws RemoteException {
        listItems.put(it, name);
    }

    @Override
    public void buyItem(Item it, String name) throws RemoteException {
        Account buyAcc = listAccounts.get(name);
        Account sellAcc = listAccounts.get(listItems.get(it));
        try {
            buyAcc.withdraw(it.price);
            sellAcc.deposit(it.price);
            listItems.remove(it);
        } catch (RejectedException ex) {
            System.out.println("not engouht money");
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Item> inspectItem() throws RemoteException {
        ArrayList<Item> tmp = new ArrayList<Item>();
        for (Map.Entry<Item, String> entry: listItems.entrySet()) {
            tmp.add(entry.getKey());
        }
        return tmp;
    }

    @Override
    public void wishItem(Item it, String name) throws RemoteException {
        
    }
    
}
