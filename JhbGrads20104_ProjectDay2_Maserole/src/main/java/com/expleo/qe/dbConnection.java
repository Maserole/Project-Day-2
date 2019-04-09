package com.expleo.qe;
import java.sql.*;

public class dbConnection {

    private static Connection conn;

    public static Connection getConnection(){

        String url ="jdbc:sqlite:C:\\Users\\7390\\IdeaProjects\\JhbGrads20104_ProjectDay2_Maserole\\Files\\chinook.db";

            try {
                conn= DriverManager.getConnection(url);

        } catch (SQLException e) {

            System.out.println("Error, "+e.getMessage());

        }
        return conn;
    }
}
