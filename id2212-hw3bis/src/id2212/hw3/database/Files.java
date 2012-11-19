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
    private PreparedStatement updateSize;
    private PreparedStatement updatePrivacy;
    private PreparedStatement updatePermission;
    private PreparedStatement selectByName;
    private PreparedStatement selectBySize;
    private PreparedStatement selectByOwner;
    private PreparedStatement selectByPrivacy;
    private PreparedStatement selectByNameAndOwner;
    
    public static final String DB_NAME = "Files";
    
    
    public Files(Connection conn, Statement s) {
        this.conn=conn;
        this.statement=s;
    }
    
     public void createTable() throws SQLException {
        ResultSet result = conn.getMetaData().getTables(null, null, DB_NAME, null);
        if (result.next()) {
            dropTable();
        }
        createFiles();
        System.out.println();
        System.out.println("table created...");
    }
    
    public void createFiles() throws SQLException {
        statement.executeUpdate(
                "CREATE TABLE "+DB_NAME+" (name VARCHAR(255) NOT NULL PRIMARY KEY, "
                + "size int, owner VARCHAR(255) NOT NULL, privacy boolean, time datetime DEFAULT(getdate()), permission BOOLEAN");
        insert=conn.prepareStatement("INSERT INTO "+DB_NAME+" (name,size,owner,privacy,premission)"
                + " VALUES (?, ?, ?, ?, ?)");
        updateSize=conn.prepareStatement("UPDATE "+DB_NAME+" SET size=? AND time=DEFAULT WHERE name=?");
        updatePrivacy=conn.prepareStatement("UPDATE "+DB_NAME+" SET privacy=? AND time=DEFAULT WHERE name=?");
        updatePermission=conn.prepareStatement("UPDATE "+DB_NAME+" SET permission=? AND time=DEFAULT WHERE name=?");
        selectByName=conn.prepareStatement("SELECT * FROM "+DB_NAME+" WHERE name=?");
        selectByOwner=conn.prepareStatement("SELECT * FROM "+DB_NAME+" WHERE owner=?");
        selectByNameAndOwner=conn.prepareStatement("SELECT * FROM "+DB_NAME+" WHERE name=? AND owner=?");
        selectByPrivacy=conn.prepareStatement("SELECT * FROM "+DB_NAME+" WHERE privacy=?");
        delete=conn.prepareStatement("DELETE FROM "+DB_NAME+" WHERE name=?");
    }
    
    public void insertFile(String name, int size, String owner, boolean privacy, boolean permission) throws SQLException {
        insert.setString(1, name);
        insert.setInt(2, size);
        insert.setString(3, owner);
        insert.setBoolean(4,privacy);
        insert.setBoolean(5,permission);
        
        int noOfAffectedRows = insert.executeUpdate();
        System.out.println();
        System.out.println("Register inserted, changes made = " + noOfAffectedRows + " row(s).");
    }
    
    public void updateSize(String name, int size) throws SQLException {
        updateSize.setInt(1, size);
        updateSize.setString(2, name);
        
        int noOfAffectedRows = updateSize.executeUpdate();
        System.out.println();
        System.out.println("Register update, changes made = " + noOfAffectedRows + " row(s).");
    }
    
    public void updatePrivacy(String name, boolean p) throws SQLException {
        updatePrivacy.setBoolean(1, p);
        updatePrivacy.setString(2, name);
        
        int noOfAffectedRows = updatePrivacy.executeUpdate();
        System.out.println();
        System.out.println("Register update, changes made = " + noOfAffectedRows + " row(s).");
    }
    
    public void updatePermission(String name, boolean p) throws SQLException {
        updatePermission.setBoolean(1, p);
        updatePermission.setString(2, name);
        
        int noOfAffectedRows = updatePermission.executeUpdate();
        System.out.println();
        System.out.println("Register update, changes made = " + noOfAffectedRows + " row(s).");
    }
    
    public ResultSet selectByName(String name) throws SQLException {
        selectByName.setString(1, name);
        return selectByName.executeQuery();
    }
    
    public ResultSet selectByOwner(String owner) throws SQLException {
        selectByOwner.setString(1, owner);
        return selectByOwner.executeQuery();
    }
    
    public ResultSet selectByNameAndOwner(String name, String owner) throws SQLException {
        selectByNameAndOwner.setString(1, name);
        selectByNameAndOwner.setString(2, owner);
        return selectByNameAndOwner.executeQuery();
    }
    
    public void deleteFile(String name) throws SQLException {
        delete.setString(1, name);
        int noOfAffectedRows = delete.executeUpdate();
        System.out.println();
        System.out.println("Register update, changes made = " + noOfAffectedRows + " row(s).");
    }

    public ResultSet selectAll() throws SQLException {
        return statement.executeQuery("SELECT * FROM "+DB_NAME);
    }

    public void dropTable() throws SQLException {
        int NoOfAffectedRows = statement.executeUpdate("DROP TABLE "+DB_NAME);
        System.out.println();
        System.out.println("Table dropped, " + NoOfAffectedRows + " row(s) affected");
    }
    
}
