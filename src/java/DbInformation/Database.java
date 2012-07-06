/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author power
 */
public class Database {

    /**
     * This class is not the actual class that is to be used
     * this is designed for the purpose of being used by dBaseClass2
     * to connect to the minorProject
     */
    String dbUrl;//="jdbc:mysql://localhost:8080/nepalwatch";
    String beClass;//="com.mysql.jdbc.Driver";
    String dbUserName;//="nepalwatch";
    String dbPassword;//="nepalwatch";
//String dbtime="";
    Connection con;
    
    public void setDefaultParameters() {//working
    /*
         * ie this method sets the variables to the database 
         * for which, this class was actually created
         */
        dbUrl = "jdbc:mysql://localhost/nepalwatch";
        beClass = "com.mysql.jdbc.Driver";
        //String query="Select * from rss";   
        dbUserName = "nepalwatch";
        dbPassword = "nepalwatch";

    }

    public Connection getConnection() {
        return con;
    }

    public boolean setConnection(boolean option) {//working
        this.setDefaultParameters();
        try {
            if (option) {
                /**
                 * if true create the connection to the database
                 */
                Class.forName(beClass);
                con = (Connection) DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
                

            } else {
                /**
                 * if option=false, close the connection to the database
                 */
                con.close();
            }
            return true;
        } catch (Exception e) {
            //the database is not created
            return false;
        }
    }

    public boolean executeQuery(String q) {//working
    /*
         * This class is used for inserting and deleting 
         * from inside this class not outside;
         */
        try {
            Statement stmt;
            stmt = con.createStatement();
            int a = stmt.executeUpdate(q);

            if (a > 0) {
                return true;
            } else {
                return false;
            }//else
        }//try
        catch (Exception e) {
            System.out.println("The query " + q + " could not be executed ");
            System.out.println(e.getMessage());
            return false;
        }//catch
    }//function
    
    public ResultSet executeAndReturnResult(String q) {//working
        try {
            Statement stmt;
            stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(q);
            return rs;
        } catch (Exception e) {
            // System.out.print("the result was null ok_from the catch block");
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}//end of class
