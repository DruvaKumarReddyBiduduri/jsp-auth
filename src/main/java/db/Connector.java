package db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;


public class Connector {

    public static Connection conn=null;
    private static final String url="jdbc:mysql://localhost:3306/auth";
    private static final String name="root";
    private static final String password="";


    public static Connection getConn(){
        if(conn!=null){
            return conn;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection(url,name,password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

}
