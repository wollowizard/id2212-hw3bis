package id2212.hw3.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Marcel
 */
public class DataBase {
    
    private Connection conn;
    private Statement statement;
    private String datasource = "catalogdatabase";
    private String user = "root";
    private String passwd = "root";
    private boolean initialized=false;
    
    
    public DataBase() {
    }
    
    public void connectDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + datasource, user, passwd);
	statement = conn.createStatement();
        initialized=true;
	System.out.println("Connected to database..." + conn.toString());
    }
    
    public void close() throws Exception {
	if (initialized) {
            conn.close();
            initialized=false;
        }
        System.out.println();
        System.out.println("Connection closed...");
    }
    
    public Register getRegisterInstance() {
        return new Register(conn, statement);
    }
    
    public Files getFilesInstance() {
        return new Files(conn,statement);
    }
    
}
