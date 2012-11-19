/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import client.Client;
import entity.FileEntity;

/**
 *
 * @author alfredo
 */
public class DbManager {

    public boolean storeClient(Client c) {

        //return true if store successful
        return false;
    }

    public Client loadClient(String username) {

        //return a client with username and pwd set
        return null;
    }
    
    
    public boolean storeFile(FileEntity f) {

        //return true if store successful
        return false;
    }

    public FileEntity loadFile(String filename) {

        //return a client with username and pwd set
        return null;
    }
    
    
}
