package oopdProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @brief login class used for user login into the system

 */
public class Login {
	Connection con;
	Logger logger;
	boolean bflag1;
	Scanner sc;
	
	/**
	 * @brief Parameterized login class constructor
	 * @param Connection object, logger object, Scanner object

	 */
	Login(Connection con1,Logger logger1,Scanner sc1){
		this.bflag1 = false;
		this.logger = logger1;
		this.con = con1;
		this.sc = sc1;
	}
	
	/**
	 * @brief loginDisplay function gives options to user at the time of login such as: 
	 * 			Enter username and password for login
	 * 			Forgot userId
	 * 			Reset password
	 * 			Move back and exit
	 * 
	 * @return boolean
	 * 			

	 */
	public boolean loginDisplay(){   
		boolean bflag = true;
		String selectSql ;
		String sErrorMsg ;
		ResultSet resultSet = null;
		sc = new Scanner(System.in);
		
		while(bflag) {
		System.out.println("Welcome to Login Page");
		System.out.println("Kindly enter options from following to proceed further");
		System.out.println("1.Enter username and password to Login\n2.Forgot userID\n3.Forgot password\n4.Move Back\n5.Exit");
		
		int ioptionSelected = sc.nextInt();
		if(ioptionSelected == 1 )
		{
			System.out.println("Enter your userid:");
			String sUserId = sc.next();
			System.out.println("Enter your password:");
			String sPwd = sc.next();
			

	        selectSql = "select case  when count (userid) = 1 Then 'true' else 'false' end as result from userdetails where userid='"+
	        sUserId+"' and userpwd= '"+sPwd+"'";
	        
	        try {
	        sErrorMsg = "";
	        resultSet = SQLConnection.getResultSet(con, selectSql, sErrorMsg);

	        	if(sErrorMsg.isBlank()) {
	        		while (resultSet.next()) {
	        			if(resultSet.getString(1).equals("true"))
	        			{
	        				
	        				
	        				//Login to the front page of application.
	            	        FrontPage objfp = new FrontPage();
	            	        objfp.frontPageStart(con, sUserId, logger);
//	            	        System.out.println("Enter \n 1. For Food Wise Order \n 2. For Vendor Wise Order");
	            	     
	            	        	
	            	        	
	            	        
	            	        
	            	        
	            	       
	            	        
	            	        
	        			}
	        			else
	        			{
	        				System.out.println("You are not authenticated.You might have entered either wrong userId or password.");
	        			}	
	        		}
	        	}
	        	else
	        	{
		        // the following statement is used to log any messages  
		        logger.info("Error Message in Login.java in Logging option :"+sErrorMsg);  
	        	}
	        }
			catch(Exception e)
	        {		
				logger.info("Exception Message in Login.java in Login option :"+ e.getMessage());	
	        }
	        
		}
		else if(ioptionSelected == 2 )
		{
			System.out.println("Enter your username:");
			String sUsername = sc.next();
			System.out.println("Enter your email:");
			String sMail = sc.next();
			System.out.println("Enter your phone number:");
			long sPhone = sc.nextLong();
			System.out.println("Enter your adhaar number:");
			String sAdhaar = sc.next();
			
			selectSql = "select userid,case  when count (userid) = 1 Then 'true' else 'false' end as result from userdetails "+
						"where username='"+sUsername+"' and userphone= '"+sPhone+"' and useremail = '"+sMail+"' and useradhaar = '"+sAdhaar+"' group by userid";
			        
			        try {
			        sErrorMsg = "";
			        resultSet = SQLConnection.getResultSet(con, selectSql, sErrorMsg);

			        	if(sErrorMsg.isBlank()) {
			        		while (resultSet.next()) {
			        			if(resultSet.getString(2).equals("true"))
			        			{
			            	      System.out.println("Your userid :"+ resultSet.getString(1)+". Go for Login again.");
			        			}
			        			else
			        			{
			        				System.out.println("You details are not matched. Requested you to try it once again.");
			        			}	
			        		}
			        	}
			        	else
			        	{
				        // the following statement is used to log any messages  
				        logger.info("Error Message in Login.java in forgetting userID option :"+sErrorMsg);  
			        	}
			        }
					catch(Exception e)
			        {		
						logger.info("Exception Message in Login.java in forgetting userID option :"+ e.getMessage());	
			        }		
			
		}
		else if(ioptionSelected == 3 )
		{
			System.out.println("Enter your username:");
			String sUsername = sc.next();
			System.out.println("Enter your userID:");
			String sUserID = sc.next();
			System.out.println("Enter your email:");
			String sMail = sc.next();
			System.out.println("Enter your phone number:");
			long sPhone = sc.nextLong();
			System.out.println("Enter your adhaar number:");
			String sAdhaar = sc.next();
			
			selectSql = "select case  when count (userid) = 1 Then 'true' else 'false' end as result from userdetails "+
						"where username='"+sUsername+"' and userphone= '"+sPhone+"' and useremail = '"+sMail+"' and useradhaar = '"+sAdhaar+
						"' and userid = '"+sUserID+"'";
			        
			        try {
			        sErrorMsg = "";
			        resultSet = SQLConnection.getResultSet(con, selectSql, sErrorMsg);

			        	if(sErrorMsg.isBlank()) {
			        		while (resultSet.next()) {
			        			if(resultSet.getString(1).equals("true"))
			        			{ System.out.println("You are seem to be an authenticated user.");
			            	      boolean bVal = true;
			        			  while(bVal) {
			        			  System.out.println("Enter new password:");
			            	      String sPwd1 = sc.next();
			            	      System.out.println("Enter new password again:");
			            	      String sPwd2 = sc.next();
			            	      if(sPwd1.equals(sPwd2))
			            	      {
			            	    	  String query = "update userdetails set userpwd = '"+sPwd1+"' where userid = '"+sUserID+"'";
			            	    	  String sSucccessMsg = "Your password has been updated.";
			            	    	  String sfailMsg = "Error in updating the password.";
			            	    	  String sError = SQLConnection.runUpdateQuery(query,sSucccessMsg,sfailMsg,con);
			            	    	  if(!sError.isBlank())
			            	    		  logger.info("Error Message in Login.java in updating password option :"+sError);
			            	    	  else
			            	    	  {
			            	    		  bVal = false;
			            	    	  }  
			            	      }
			            	      else
			            	      {
			            	    	  System.out.println("Password and confirmed password didn't match. Try it once again");
			            	      }
			        			 }
			        			}
			        			else
			        			{
			        				System.out.println("You details are not matched. Requested you to try it once again.");
			        			}	
			        		}
			        	}
			        	else
			        	{
				        // the following statement is used to log any messages  
				        logger.info("Error Message in Login.java in forgetting password option :"+sErrorMsg);  
			        	}
			        }
					catch(Exception e)
			        {		
						logger.info("Exception Message in Login.java in forgetting password option :"+ e.getMessage());	
			        }	
		}
		
		else if(ioptionSelected == 4 )
		{
			System.out.println("We are going back to main page..."); bflag1 = false;bflag = false;
		}
		else if(ioptionSelected == 5 )
		{
			System.out.println("We are exiting from system..."); bflag1 = true;bflag = false;
		}
		else
		{
			System.out.println("You have entered wrong input.");
		}
		
	  }
		
		
	
		return bflag1;	
	}
	
	

}
