/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id2212.hw3.database;

import entity.FileEntity;
import entity.FileEntityDescription;
import entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alfredo
 */
public class DbWrapper {

    private static DbWrapper instance = null;
    private Register regTable;
    private Files filesTable;
    private DataBase db;

    private DbWrapper() {

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

    public static DbWrapper getInstance() {
        if (instance == null) {
            instance = new DbWrapper();
        }
        return instance;
    }

    public void storeUser(User u) throws SQLException {
        //throw exception if could not store
        regTable.insertRegister(u.name, u.pwd, Register.CONNECTED);
    }

    public void validateUser(User u) throws SQLException {
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

    public void storeFileEntity(FileEntity fe) throws SQLException {
        //throw exception if could not store
        //add date
        
        FileEntityDescription f = fe.getDescription();
        f.lastModified=new Date();
        
        Date d = new Date();
        filesTable.insertFile(f.name, f.size, f.ownerName, f.privateFile, 
                f.writepermission, "", String.valueOf(d.getTime())); //insert path of file
    }

    public FileEntityDescription loadFileEntity(String name) throws SQLException {
        //throw exception if could not load
        //else return a FileEntity with fields set
        ResultSet r = filesTable.selectByName(name);
        if (r.next()) {
            return new FileEntityDescription(r.getString("name"), r.getInt("size"), r.getString("owner"), r.getBoolean("privacy"), r.getBoolean("permission"),r.getDate("date"));
        }
        return null;
    }

    public void deleteUser(User user) throws SQLException {
        regTable.deleteRegister(user.name);
    }

    public ArrayList<FileEntityDescription> loadFiles(String filter) {
        //implement
        //Note that if a file is marked as private, it can be listed only for its owner
        
        
        ArrayList<FileEntityDescription> toreturn=new ArrayList<>();
        toreturn.add(new FileEntityDescription("Acquisti.filename", 123, "user", true, true, new Date()));
        return toreturn;
    }

    public ArrayList<FileEntityDescription> loadAllFilesof(String username) {
        
        //implement
        
        ArrayList<FileEntityDescription> toreturn=new ArrayList<>();
        toreturn.add(new FileEntityDescription("Acquisti.filename", 123, "user", false, true, new Date()));
        return toreturn;
    }

    public void deleteFile(String filename) throws SQLException {
        
        //implement
        filesTable.deleteFile(filename);
    }

  

    public FileEntity loadCompleteFile(String filename) {
        //implement
        return new FileEntity(new FileEntityDescription(filename, Integer.SIZE, filename, true, true, new Date()), null);
    }

    public String getFileLocation(String file) throws SQLException {
        //implement
        ResultSet r = filesTable.selectByName(file);
        return r.getString("path");
    }

    public void editFileLastModified(String file) throws SQLException {
        //implement
        Date d = new Date();
        filesTable.updateDate(file, String.valueOf(d.getTime()));
    }

    public void editFileSize(String file, int length) throws SQLException {
        //implement
        filesTable.updateSize(file, length);
    }
}
