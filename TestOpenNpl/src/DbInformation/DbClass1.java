/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbInformation;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 *
 * @author power
 */
public class DbClass1 {
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
Statement stmt;
ResultSet rs;

public void setDefaultParameters(){//working
    /*
     * ie this method sets the variables to the database 
     * for which, this class was actually created
     */
 dbUrl="jdbc:mysql://localhost/nepalwatch";
 beClass="com.mysql.jdbc.Driver";
 //String query="Select * from rss";   
 dbUserName="nepalwatch";
 dbPassword="nepalwatch";
 
}

public boolean setConnection(boolean option){//working
  //  System.out.println("the not yet");
    this.setDefaultParameters();
    try{
        if(option){
            /**
             * if true create the connection to the database
             */
            //System.out.println("the option is true");
            Class.forName(beClass);
            con= (Connection) DriverManager.getConnection (dbUrl,dbUserName,dbPassword);
            stmt=(Statement) con.createStatement();
            //rs=stmt.executeQuery(query);
            //System.out.println("the connection to the database is established");
                
        }else{
            /**
             * if option=false, close the connection to the database
             */
            con.close();
        }
        return true;
    }
    catch(Exception e){
        //the database is not created
        return false;
    }
}
public boolean executeQuery(String q){//working
    /*
     * This class is used for inserting and deleting 
     * from inside this class not outside;
     */
        try{
            System.out.println(q);
            int a=stmt.executeUpdate(q);
            
            if(a>0){
                return true;
            }else{
               // System.out.println("The value of a <0 DbClass1");
                return false;
            }//else
    }//try
    catch(Exception e){
       //System.out.println("the query returned 0 result catch block  DbClass1,");
       System.err.println(e.getMessage());
        return false;
    }//catch
}//function
public ResultSet executeAndReturnResult(String q){//working
     try{
        //System.out.println(q);
        rs=stmt.executeQuery(q);
        return rs;
    }
    catch(Exception e){
        //System.out.println("the result was null ok_from the catch block");
        System.err.println(q);
        System.err.println("dbclass1 error catch block "+e.getMessage());
        return null;
    }
}

}//end of class
