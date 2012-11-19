package bank;

import java.rmi.registry.LocateRegistry;

public class MainServer {    
	private static final String USAGE = "java bankrmi.Server <bank_rmi_url>";
	private static final String SNAME = "fileserver";

	public MainServer(String bankName) {
		try {
			Server bankobj = new ServerImpl(bankName);
			// Register the newly created object at rmiregistry.
                        LocateRegistry.createRegistry(1099); 
			java.rmi.Naming.rebind("rmi://localhost/"+bankName, bankobj);
			System.out.println(bankobj + " is ready.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length > 1 || (args.length > 0 && args[0].equalsIgnoreCase("-h"))) {
			System.out.println(USAGE);
			System.exit(1);
		}

		String bankName = null;
		if (args.length > 0) {
			bankName = args[0];
		} else {
			bankName = SNAME;
		}

		new MainServer(bankName);
	}
}
