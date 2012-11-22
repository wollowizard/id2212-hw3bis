/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id2212.hw3.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Marcel
 */
public class Register {
    
    private Connection conn;
    private Statement statement;
    private PreparedStatement insert;
    private PreparedStatement delete;
    private PreparedStatement update;
    private PreparedStatement select;
    private PreparedStatement selectConected;
    
    public static final String DB_NAME = "Register";
    
    public static final String CONNECTED = "connected";
    
    public static final String DISCONNECTED = "disconnected";
    private PreparedStatement updateUpload;
    private PreparedStatement updateDownload;
    
    
    public Register(Connection c, Statement s) {
        this.conn=c;
        this.statement=s;
    }
    
    public void createTable() throws SQLException {
        ResultSet result = conn.getMetaData().getTables(null, null, DB_NAME, null);
        if (result.next()) {
            //dropTable();
            System.out.println("table exist...");
        }
        else {
            statement.executeUpdate(
                "CREATE TABLE "+DB_NAME+" (name VARCHAR(255) NOT NULL PRIMARY KEY, "
                + "passwd VARCHAR(255) NOT NULL, state VARCHAR(255), "
                + "upload INTEGER, download INTEGER)");
        }
        createRegister();
        System.out.println();
        System.out.println("table created...");
    }
    
    public void createRegister() throws SQLException {
        
        insert=conn.prepareStatement("INSERT INTO "+DB_NAME+" (name,passwd,state,upload,download) VALUES (?, ?, ?, ?, ?)");
        update=conn.prepareStatement("UPDATE "+DB_NAME+" SET state=? WHERE name=? AND passwd=?");
        updateUpload=conn.prepareStatement("UPDATE "+DB_NAME+" SET upload=? WHERE name=?");
        updateDownload=conn.prepareStatement("UPDATE "+DB_NAME+" SET download=? WHERE name=?");
        select=conn.prepareStatement("SELECT * FROM "+DB_NAME+" WHERE name=?");
        selectConected = conn.prepareStatement("SELECT * FROM "+DB_NAME+" WHERE state=?");
        delete=conn.prepareStatement("DELETE FROM "+DB_NAME+" WHERE name=?");
    }
    
    public void insertRegister(String name, String passwd, String state) throws SQLException {
        insert.setString(1, name);
        insert.setString(2, passwd);
        insert.setString(3, state);
        insert.setInt(4, 0);
        insert.setInt(5, 0);
        
        int noOfAffectedRows = insert.executeUpdate();
        System.out.println();
        System.out.println("Register inserted, changes made = " + noOfAffectedRows + " row(s).");
    }
    
    public void updateRegister(String name, String passwd, String state) throws SQLException {
        update.setString(1, state);
        update.setString(2, name);
        update.setString(3, passwd);
        
        int noAffectedRows = update.executeUpdate();
        if (noAffectedRows==0) throw new SQLException("USER NOT FOUND");
        System.out.println();
        System.out.println("Register update, changes made = " + noAffectedRows + " row(s).");
    }
    
    public void updateNumUploads(String name, Integer n) throws SQLException {
        updateUpload.setInt(1, n);
        updateUpload.setString(2, name);
        
        int noAffectedRows = updateUpload.executeUpdate();
        if (noAffectedRows==0) throw new SQLException("USER NOT FOUND");
        System.out.println();
        System.out.println("Register update, changes made = " + noAffectedRows + " row(s).");
    }
    
    public void updateNumDownloads(String name, Integer n) throws SQLException {
        updateDownload.setInt(1, n);
        updateDownload.setString(2, name);
        
        int noAffectedRows = updateDownload.executeUpdate();
        if (noAffectedRows==0) throw new SQLException("USER NOT FOUND");
        System.out.println();
        System.out.println("Register update, changes made = " + noAffectedRows + " row(s).");
    }
    
    public ResultSet selectRegister(String name) throws SQLException {
        select.setString(1, name);
        return select.executeQuery();
    }
    
    public ResultSet selectByState(String state) throws SQLException {
        select.setString(1, state);
        return select.executeQuery();
    }
    
    public void deleteRegister(String name) throws SQLException {
        delete.setString(1, name);
        int noOfAffectedRows = delete.executeUpdate();
        System.out.println();
        System.out.println("Register update, changes made = " + noOfAffectedRows + " row(s).");
    }

    public ResultSet selectAll(String name) throws SQLException {
        return statement.executeQuery("SELECT * FROM "+name);
    }

    public void dropTable() throws SQLException {
        int NoOfAffectedRows = statement.executeUpdate("DROP TABLE "+DB_NAME);
        System.out.println();
        System.out.println("Table dropped, " + NoOfAffectedRows + " row(s) affected");
    }
    
}
