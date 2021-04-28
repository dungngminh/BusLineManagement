package Util;
import Model.*;
import Services.DAL;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DB_Helper {
    private static DB_Helper instance;
    private Connection conn;
    private DB_Helper() throws SQLException, ClassNotFoundException {
        ConnectSQLServer();

    }

    public static DB_Helper getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new DB_Helper();
        }
        return instance;
    }

    public void ConnectSQLServer() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://34.72.153.240;database=QuanLyNhaXeKhach",
                    "sqlserver", "123456");
        }
        catch (Exception err) {
            System.out.println("Error" + err);
        }


    }

    public void ExecuteDB(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);

    }

    public void getRecordAccount(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()) {
            String username = rs.getString("UserName");
            String password = rs.getString("Password");

            DAL.getInstance().setAccount(new Account(username, password));

        }
        rs.close();
        stmt.close();

    }

    public void getRecord(String query, String nameTable) throws SQLException {
        // create statement
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // get metadata from rs
        ResultSetMetaData rsmd = rs.getMetaData();

        // Record column names to list
        int columnCount = rsmd.getColumnCount();
        List<String> nameColumns = new ArrayList<>();

        for(int i = 1; i <= columnCount; ++i) {
            nameColumns.add(rsmd.getColumnName(i));

        }
        switch(nameTable) {
            case "Account":
            {
                while(rs.next()) {
                    int idUser = Integer.parseInt(rs.getString(nameColumns.get(0)));
                    String username = rs.getString(nameColumns.get(1));
                    String password = rs.getString(nameColumns.get(2));
                    DAL.getInstance().setAccount(new Account(username, password));
                }
            }
            break;
            case "Bus":
            {

            }
            break;

            default:
                break;
        }
        rs.close();
        stmt.close();

    }

}