/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id2212.hw3.database;

import entity.FileEntity;
import entity.User;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alfredo
 */
public class DbWrapper {
    
    private DbWrapper instance =null;
    private Register regTable;
    private Files filesTable;
    private DataBase db;
    private DbWrapper(){
        
        //connect to the db, create the tables, initialize everything
        //take the data from some config or from static final fields of another class
        db = new DataBase();
        try {
            db.connectDatabase();
            regTable = db.getRegisterInstance();
            regTable.createTable();
            filesTable = db.getFilesInstance();
            filesTable.createTable();
        } catch (SQLException ex) {
            Logger.getLogger(DbWrapper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public DbWrapper getInstance(){
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
    
}
