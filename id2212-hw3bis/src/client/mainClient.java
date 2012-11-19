/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.rmi.RemoteException;

/**
 *
 * @author Marcel
 */
public class mainClient {

    private static String CLIENT = "Marcel";
    private static String SERVER = "EBAY";
    private static final String USAGE = "java client <ClientName> <ServerName>";

    public static void main(String[] args) throws RemoteException {

        if (args.length > 2 || (args.length > 1 && args[0].equalsIgnoreCase("-h"))) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String clientName = null;
        String srvName = null;

        if (args.length == 2) {
            clientName = args[0];
            srvName = args[1];

        } else {
            clientName = CLIENT;
            srvName = SERVER;
        }

        ClientImpl c = null;

        c = new ClientImpl(srvName);
                
       

    }
}
