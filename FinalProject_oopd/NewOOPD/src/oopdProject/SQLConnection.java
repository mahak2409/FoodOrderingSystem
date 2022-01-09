package oopdProject;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * @brief SQLConnection class makes sql connection and have functions to perform different operation on database.
 * 		  

 */
public class SQLConnection {
	
	/**
	 * @brief getConnecion is a static function establishes connection with the database.
	 * @return Connection 
	 * @param void
	 * 		  

	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
	
	Connection con = null;	
    try{

    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
    	String connectionUrl = "jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true" ; 
    	con = DriverManager.getConnection(connectionUrl);  
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
	
    return con;
	}
	
	/**
	 * @brief getResultSet is a static function which gets data from the database for the given sql query.
	 * @return ResultSet 
	 * @param Connection object, sql query, sql message
	 * 		  

	 */
	public static ResultSet getResultSet(Connection con, String sQuery , String sMessage){
		
		ResultSet resultSet = null;
	    try{	    
	    	Statement statement = con.createStatement();
	        resultSet = statement.executeQuery(sQuery);
//	        while (resultSet.next()) {
//	            System.out.println("Username: "+resultSet.getString(1) +" ,userpassword: "+resultSet.getString(2) + " ,userphone: " + resultSet.getString(3));
//	        }
	    }
	    catch (SQLException e) {
	       sMessage = e.getMessage();
	    }
		
	    return resultSet;
		}
	
	/**
	 * @brief runUpdateQuery is a static function which updates data in the database for the given sql query.
	 * @return String 
	 * @param Connection object, sql query, sql success message and fail message
	 * 		  

	 */
	public static String runUpdateQuery(String sQuery,String sSuccessMsg, String sFailMsg,Connection con) {
		
	  String sError ="" ;	
	  try { 	
  	  Statement statement = con.createStatement();
  	  int ans = statement.executeUpdate(sQuery);
  	  if(ans ==1)
  		  System.out.println(sSuccessMsg);
  	  else
  		  System.out.println(sFailMsg);
	  }
	  catch (Exception e)
	  {
		  sError = e.getMessage();
	  }
	  
	  return sError;	
	}
	
	
	
	
}
