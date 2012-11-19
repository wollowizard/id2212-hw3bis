/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id2212.hw3.database;

import entity.FileEntity;
import entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alfredo
 */
public class DbWrapper {
    
    private static DbWrapper instance =null;
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
    
    public static DbWrapper getInstance(){
        if( instance==null){
            instance=new DbWrapper();
        }   
        return instance;
    }
    
    public void storeUser(User u) throws SQLException{
        //throw exception if could not store
        regTable.insertRegister(u.name, u.pwd, Register.CONNECTED);
    }
    
    public void loadUser(User u) throws SQLException{
        //throw exception if could not load
        //else return a User with username and pwd set
        regTable.updateRegister(u.name, u.pwd, Register.CONNECTED);
       /* ResultSet r = regTable.selectRegister(name);
        if (r.next()) {
            return new User(r.getString("name"),r.getString("passwd"), r.getString("state"));
        }
        else return null;  */
    }
    
    public void logoutUser(User u) throws SQLException {
        regTable.updateRegister(u.name, u.pwd, Register.DISCONNECTED);
    }
    
    public void storeFileEntity(FileEntity f) throws SQLException{
        //throw exception if could not store
        filesTable.insertFile(f.name, f.size, f.ownerName, f.privacy, f.permission);
    }
    
    public FileEntity loadFileEntity(String name) throws SQLException{
        //throw exception if could not load
        //else return a FileEntity with fields set
        ResultSet r = filesTable.selectByName(name);
        if (r.next()) {
            return new FileEntity(r.getString("name"),r.getInt("size"),r.getString("owner"),r.getBoolean("privacy"),r.getBoolean("permission"));
        }
        return null;
    }

    public void deleteUser(User user) throws SQLException {
        regTable.deleteRegister(user.name);
    }
    
}
