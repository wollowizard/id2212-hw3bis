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
public class Files {

    
    private Connection conn;
    private Statement statement;
    private PreparedStatement insert;
    private PreparedStatement delete;
    private PreparedStatement update;
    private PreparedStatement select;
    private PreparedStatement selectConected;
    
    public static final String DB_NAME = "Files";
    
    public Files(Connection conn, Statement s) {
        this.conn=conn;
        this.statement=s;
    }
    
     public void createTable() throws Exception {
        ResultSet result = conn.getMetaData().getTables(null, null, DB_NAME, null);
        if (result.next()) {
            dropTable();
        }
        createRegister();
        System.out.println();
        System.out.println("table created...");
    }
    
    public void createRegister() throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE "+DB_NAME+" (name VARCHAR(255) NOT NULL PRIMARY KEY, "
                + "size int, owner VARCHAR(255) NOT NULL, privacy boolean, time datetime, permission BOOLEAN");
        insert=conn.prepareStatement("INSERT INTO "+DB_NAME+" (name,passwd,state) VALUES (?, ?, ?)");
        update=conn.prepareStatement("UPDATE "+DB_NAME+" SET state=? WHERE name=?");
        select=conn.prepareStatement("SELECT * FROM "+DB_NAME+" WHERE name=?");
        selectConected = conn.prepareStatement("SELECT * FROM "+DB_NAME+" WHERE state=?");
        delete=conn.prepareStatement("DELETE FROM "+DB_NAME+" WHERE name=?");
    }
    
    public void insertRegister(String name, String passwd, String state) throws SQLException {
        insert.setString(1, name);
        insert.setString(2, passwd);
        insert.setString(3, state);
        
        int noOfAffectedRows = insert.executeUpdate();
        System.out.println();
        System.out.println("Register inserted, changes made = " + noOfAffectedRows + " row(s).");
    }
    
    public void updateRegister(String name, String state) throws Exception {
        update.setString(1, state);
        update.setString(2, name);
        int noOfAffectedRows = update.executeUpdate();
        System.out.println();
        System.out.println("Register update, changes made = " + noOfAffectedRows + " row(s).");
    }
    
    public ResultSet selectRegister(String name) throws Exception {
        select.setString(1, name);
        return select.executeQuery();
    }
    
    public ResultSet selectByState(String state) throws SQLException {
        select.setString(1, state);
        return select.executeQuery();
    }
    
    public void deleteRegister(String name) throws Exception {
        delete.setString(1, name);
        int noOfAffectedRows = delete.executeUpdate();
        System.out.println();
        System.out.println("Register update, changes made = " + noOfAffectedRows + " row(s).");
    }

    public ResultSet selectAll(String name) throws Exception {
        return statement.executeQuery("SELECT * FROM "+name);
    }

    public void dropTable() throws Exception {
        int NoOfAffectedRows = statement.executeUpdate("DROP TABLE "+DB_NAME);
        System.out.println();
        System.out.println("Table dropped, " + NoOfAffectedRows + " row(s) affected");
    }
    
}
