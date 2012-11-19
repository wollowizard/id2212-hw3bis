/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id2212.hw3.database;

import entity.FileEntity;
import entity.User;

/**
 *
 * @author alfredo
 */
public class DbWrapper {
    
    private static DbWrapper instance =null;
    private DbWrapper(){
        
        //connect to the db, create the tables, initialize everything
        //take the data from some config or from static final fields of another class
        
        
    }
    
    public static DbWrapper getInstance(){
        if( instance==null){
            instance=new DbWrapper();
        }
        
        return instance;
    }
    
    public void storeUser(User u){
        //throw exception if could not store
        
    }
    
    public User loadUser(User u){
        //throw exception if could not load
        //else return a User with username and pwd set
        return null;
    }
    
    public void storeFileEntity(FileEntity f){
        //throw exception if could not store
        
    }
    
    public FileEntity loadFileEntity(FileEntity fe){
        //throw exception if could not load
        //else return a FileEntity with fields set
        return null;
    }

    public void deleteUser(User user) {
        
    }
    
}
