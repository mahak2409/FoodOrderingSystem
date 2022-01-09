
package oopdProject;
import java.io.*;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.util.logging.FileHandler;

import java.util.logging.SimpleFormatter;

class Restaurants {
public static void viewRestaurants () {

}
}

 class VendorSignup extends Vendor{
	   Connection con;
	   Logger logger;	
	   Scanner sc;	
	   
	   VendorSignup( Connection con1,Logger logger1,Scanner sc1) 	
	   {
		   this.con = con1;
		   this.logger = logger1;
		   this.sc = sc1;
	   }
		
		boolean VendorsignupInfo() {	
		System.out.println("Welcome to Vendor Signup page!!");	
		String sVar; long lVal;
		
		System.out.println("Enter Vendor name");
		sVar = sc.next();
		setVendorName(sVar);
		
		System.out.println("Enter Vendor username");
		sVar = sc.next();
		setVendorUserName(sVar);
		
		System.out.println("Enter Area");
		sVar= sc.nextLine();
		setVendorArea(sVar);
		
		System.out.println("Enter State");
		sVar= sc.nextLine();
		setVendorState(sVar);
		
		System.out.println("Enter Email");
		sVar= sc.nextLine();
		setVendorEmail(sVar);
		
		System.out.println("Enter vendorID");
		sVar= sc.nextLine();
		setVendorId(sVar);
		
		boolean bflag = true,bflag1;
		while(bflag) {
		System.out.println("Enter Password");
		sVar= sc.nextLine();
		setVendorPwd(sVar);
		
		System.out.println("Enter Password Again:");
		sVar= sc.nextLine();
		setVendorCnfmPwd(sVar);
		
		bflag1 = passwordMatching(this.getVendorPwd(),this.getVendorCnfmPwd());
		if(bflag1)
		   bflag = false;
		}
		
		System.out.println("Enter Adhaar Number:");
		sVar= sc.nextLine();
		setVendorAdhaar(sVar);
		
		System.out.println("Enter Contact Number:");
		lVal = sc.nextLong();
		setVendorPhone(lVal);
		
		System.out.println("Enter Alternate Contact:");
		lVal = sc.nextLong();;
		setVendorAltNo(lVal);
		
		System.out.println("Enter Pincode:");
		lVal = sc.nextLong();
		setPincode(lVal);
		
		System.out.println("Enter Estimated cooking time:");
		int iVal = sc.nextInt();
		setVendorCookingTime(iVal);
		
		String sValues = "'" + this.getVendorName() + "','" +this.getVendorUserName()+ "','" + this.getVendorPhone() + "','" + this.getVendorEmail()+ "','" + this.getVendorAdhaar()+ "','" + this.getVendorArea() + "','"+
		                 this.getVendorState()+ "','" + this.getVendorId() + "','" + this.getVendorPwd() + "','" + this.getPincode() + "','" + this.getVendorAltNo() + "','"+ this.getVendorCookingTime()+"'";
		
		
		String sQuery = "insert into vendordetails(vendorname,vendorusername,vendorphone,vendoremail,vendoradhaar,vendorarea,vendorstate,vendorid,vendorpwd,vendorpincode,vendoraltcontact,vendorCookingtime) values ("+ sValues +")";
		String sSucccessMsg = "New vendor details has been entered in the system.Thanking you for signup onto our portal.";
		String sfailMsg = "Error in inserting the Vendor details.";
		String sError = SQLConnection.runUpdateQuery(sQuery,sSucccessMsg,sfailMsg,con);
		  if(!sError.isBlank())
			  {logger.info("Error Message in VendorSignup.java in inserting vendor details options :"+sError);
			     return false;
			  }
		  else
			 return true; 
		  
		} 		
		
		
	}


class VendorLogin {
	Connection con;
	Logger logger;
	boolean bflag1;
	Scanner sc;
	
	VendorLogin(Connection con1,Logger logger1,Scanner sc1){
		this.bflag1 = false;
		this.logger = logger1;
		this.con = con1;
		this.sc = sc1;
	}
	
	public boolean VendorLoginDisplay()
	{   
		boolean bflag = true;
		String selectSql ;
		String sErrorMsg ;
		ResultSet resultSet = null;
		sc = new Scanner(System.in);
		
		while(bflag) {
		System.out.println("Welcome to Vendor Login Page");
		System.out.println("Kindly enter options from following to proceed further");
		System.out.println("1.Enter Vendor username and password to Login\n2.Forgot vendorID\n3.Forgot password\n4.Move Back\n5.Exit");
		
		int ioptionSelected = sc.nextInt();
		if(ioptionSelected == 1 )
		{
			System.out.println("Enter your vendorId:");
			String sUserId = sc.next();
			System.out.println("Enter your password:");
			String sPwd = sc.next();
			

	        selectSql = "select case  when count (vendorid) = 1 Then 'true' else 'false' end as result from vendordetails where vendorid='"+
	        sUserId+"' and vendorpwd= '"+sPwd+"'";
	        
	        try {
	        sErrorMsg = "";
	        resultSet = SQLConnection.getResultSet(con, selectSql, sErrorMsg);

	        	if(sErrorMsg.isBlank()) {
	        		while (resultSet.next()) {
	        			if(resultSet.getString(1).equals("true"))
	        			{
	        				//Login to the front page of application.
	            	      System.out.println("Congo, you are authenticated.");
	        			}
	        			else
	        			{
	        				System.out.println("You are not authenticated.You might have entered either wrong vendorId or password.");
	        			}	
	        		}
	        	}
	        	else
	        	{
		        // the following statement is used to log any messages  
		        logger.info("Error Message in VendorLogin.java in Logging option :"+sErrorMsg);  
	        	}
	        }
			catch(Exception e)
	        {		
				logger.info("Exception Message in VendorLogin.java in Login option :"+ e.getMessage());	
	        }
	        
		}
		else if(ioptionSelected == 2 )
		{
			System.out.println("Enter your vendorname:");
			String svendorName = sc.next();
			System.out.println("Enter your vendor Username:");
			String sUsername = sc.next();
			System.out.println("Enter your Vendor Email:");
			String sMail = sc.next();
			System.out.println("Enter your phone number:");
			long sPhone = sc.nextLong();
			System.out.println("Enter your adhaar number:");
			String sAdhaar = sc.next();
			
			selectSql = "select vendorid,case  when count (vendorid) = 1 Then 'true' else 'false' end as result from vendordetails "+
						"where vendorusername='"+sUsername+"' and vendorphone= '"+sPhone+"' and vendoremail = '"+sMail+"' and vendoradhaar = '"+sAdhaar+"' and vendorname = '"+ svendorName +"'group by vendorid";
			        
			        try {
			        sErrorMsg = "";
			        resultSet = SQLConnection.getResultSet(con, selectSql, sErrorMsg);

			        	if(sErrorMsg.isBlank()) {
			        		while (resultSet.next()) {
			        			if(resultSet.getString(2).equals("true"))
			        			{
			            	      System.out.println("Your vendorid :"+ resultSet.getString(1)+". Go for Login again.");
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
				        logger.info("Error Message in VendorLogin.java in forgetting vendorID option :"+sErrorMsg);  
			        	}
			        }
					catch(Exception e)
			        {		
						logger.info("Exception Message in VendorLogin.java in forgetting vendorID option :"+ e.getMessage());	
			        }		
			
		}
		else if(ioptionSelected == 3 )
		{
			System.out.println("Enter your vendorname:");
			String svendorName = sc.next();
			System.out.println("Enter your vendor username:");
			String sUsername = sc.next();
			System.out.println("Enter your vendorID:");
			String sUserID = sc.next();
			System.out.println("Enter your vendor email:");
			String sMail = sc.next();
			System.out.println("Enter your vendor phone number:");
			long sPhone = sc.nextLong();
			System.out.println("Enter your adhaar number:");
			String sAdhaar = sc.next();
			
			selectSql = "select case  when count (vendorid) = 1 Then 'true' else 'false' end as result from vendordetails "+
						"where vendorusername='"+sUsername+"' and vendorphone= '"+sPhone+"' and vendoremail = '"+sMail+"' and vendoradhaar = '"+sAdhaar+
						"' and vendorid = '"+sUserID+"' and vendorname = '"+svendorName+"'";
			        
			        try {
			        sErrorMsg = "";
			        resultSet = SQLConnection.getResultSet(con, selectSql, sErrorMsg);

			        	if(sErrorMsg.isBlank()) {
			        		while (resultSet.next()) {
			        			if(resultSet.getString(1).equals("true"))
			        			{ System.out.println("You are seem to be an authenticated vendor.");
			            	      boolean bVal = true;
			        			  while(bVal) {
			        			  System.out.println("Enter new password:");
			            	      String sPwd1 = sc.next();
			            	      System.out.println("Enter new password again:");
			            	      String sPwd2 = sc.next();
			            	      if(sPwd1.equals(sPwd2))
			            	      {
			            	    	  String query = "update vendordetails set vendorpwd = '"+sPwd1+"' where vendorid = '"+sUserID+"'";
			            	    	  String sSucccessMsg = "Your password has been updated.";
			            	    	  String sfailMsg = "Error in updating the password.";
			            	    	  String sError = SQLConnection.runUpdateQuery(query,sSucccessMsg,sfailMsg,con);
			            	    	  if(!sError.isBlank())
			            	    		  logger.info("Error Message in VendorLogin.java in updating password option :"+sError);
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
				        logger.info("Error Message in VendorLogin.java in forgetting password option :"+sErrorMsg);  
			        	}
			        }
					catch(Exception e)
			        {		
						logger.info("Exception Message in VendorLogin.java in forgetting password option :"+ e.getMessage());	
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






 class Vendor {

	private String vendorName;
	private String vendorUsername;
	private long vendorPhone;	
	private long vendorAltcontactno; 
	private String vendorArea; 
	private String vendorState; 
	private String vendorId; 
	private String vendorPwd; 
	private String vendorCnfmPwd; 
	private String vendorEmail; 
	private String vendorAdhaar;
	private int vendorCookingTime; 
	private long vendorpincode;

	 public String getVendorName()
	 {
		 return this.vendorName;
	 }
	 public void setVendorName( String sName)
	 {
		 this.vendorName = sName;	 
	 }
	 
	
	 public String getVendorUserName()
	 {
		 return this.vendorUsername;
	 }
	 public void setVendorUserName( String sUserName)
	 {
		 this.vendorUsername = sUserName;	 
	 }
	 
	 public String getVendorArea()
	 {
		 return this.vendorArea;
	 }
	 public void setVendorArea( String sArea)
	 {
		 this.vendorArea = sArea;	 
	 }
	 
	 public String getVendorState()
	 {
		 return this.vendorState;
	 }
	 public void setVendorState( String sState)
	 {
		 this.vendorState = sState;	 
	 }
	 
	 public String getVendorEmail()
	 {
		 return this.vendorEmail;
	 }
	 public void setVendorEmail( String sEmail)
	 {
		 this.vendorEmail = sEmail;
	 }
	 
	 public String getVendorId()
	 {
		 return this.vendorId;
	 }
	 public void setVendorId( String sId)
	 {
		 this.vendorId = sId;	 
	 }
	 
	 public String getVendorCnfmPwd()
	 {
		 return this.vendorCnfmPwd;
	 }
	 public void setVendorCnfmPwd( String sCnfmPwd)
	 {
		 this.vendorCnfmPwd = sCnfmPwd;	 
	 }
	 
	 public String getVendorPwd()
	 {
		 return this.vendorPwd;
	 }
	 public void setVendorPwd( String sPwd)
	 {
		 this.vendorPwd = sPwd;	 
	 }
	 
	 public String getVendorAdhaar()
	 {
		 return this.vendorAdhaar;
	 }
	 public void setVendorAdhaar( String sAdhaar)
	 {
		 this.vendorAdhaar = sAdhaar;	 
	 }
	 
	 public long getPincode()
	 {
		 return this.vendorpincode;
	 }
	 public void setPincode( long lPincode)
	 {
		 this.vendorpincode = lPincode;	 
	 }
	 
	 public long getVendorAltNo()
	 {
		 return this.vendorAltcontactno;
	 }
	 public void setVendorAltNo( long luserAltNo)
	 {
		 this.vendorAltcontactno = luserAltNo;	 
	 }
	 
	 public long getVendorPhone()
	 {
		 return this.vendorPhone;
	 }
	 public void setVendorPhone( long lUserPhone)
	 {
		 this.vendorPhone = lUserPhone;	 
	 }
	 
	 public int getVendorCookingTime()
	 {
		 return this.vendorCookingTime;
	 }
	 public void setVendorCookingTime( int cookTime)
	 {
		 this.vendorPhone = vendorCookingTime;	 
	 }
	 
	 public boolean passwordMatching(String sPwd1, String sPwd2)
	 {
		 return sPwd1.equals(sPwd2);
	 }

	
}



 class startProject {
	
	public static void main(String args[]) throws SQLException, ClassNotFoundException {
	
		Logger logger = Logger.getLogger("FoodDeliveryLog");  
	    FileHandler fh;  
		Connection con = SQLConnection.getConnection();
    	Scanner sc = new Scanner(System.in); 
	    try{
	    	
	    	// This block configure the logger with handler and formatter  
	        fh = new FileHandler("C:\\Users\\IIITD\\Desktop\\kitten.png");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	        
	        //Start of Application
	        startapplication(con,logger,sc); 
   	
//	        String selectSql = "SELECT * from dummytb";
//	        String sErrorMsg = "";
//	        ResultSet resultSet = SQLConnection.getResultSet(con, selectSql, sErrorMsg);
//
//	        // Print results from select statement
//	        if(sErrorMsg.isBlank()) {
//	        while (resultSet.next()) {
//	            System.out.println("Username: "+resultSet.getString(1) +" ,userpassword: "+resultSet.getString(2) + " ,userphone: " + resultSet.getString(3));
//	        	}
//	        }
//	        else
//	        {
//		        // the following statement is used to log any messages  
//		        logger.info("Error Message in startProject.java in getting resultset object :"+sErrorMsg);  
//	        }
	       
	        sc.close();
	    }
	      
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    finally {
	    	 con.close();
	    }
	   
	    
		}
	
	
	public static void startapplication(Connection con,Logger logger,Scanner sc)
	{
		System.out.println("WELCOME TO BHOOKAD ONLINE FOOD DELIVERY SYSTEM !!!\n");
		boolean bflag = true;
		
		//Scanner sc = new Scanner(System.in);
		while(bflag) {
		System.out.println("Enter from following different options to continue further");
		System.out.println("1.Login\n2.Sign-up\n3.Exit");
		
		
		int ioptionSelected = sc.nextInt();
		
		if(ioptionSelected == 1)
		{
			
			System.out.println("Enter from following different options to continue further");
			System.out.println("1.Login as User\n2.Login as Vendor");
			
			int optionSelect = sc.nextInt();
			
			if(optionSelect == 1) {
			boolean btempflag = false;
			Login lgobj = new Login(con,logger,sc);
			btempflag = lgobj.loginDisplay();
			if(btempflag)
			{
				bflag = false;
			}
		  }
			else if(optionSelect == 2) {
				boolean btempflag = false;
				VendorLogin vlgobj = new VendorLogin(con,logger,sc);
				btempflag = vlgobj.VendorLoginDisplay();
				if(btempflag)
				{
					bflag = false;
				}
			} 	
			
			else {
				
				System.out.println("Wrong option entered. Try it once again.");
			} 
		}
		else if(ioptionSelected == 2)
		{

			System.out.println("Enter from following different options to continue further");
			System.out.println("1.Signup as User\n2.Signup as Vendor");
			
			int optionSelect = sc.nextInt();
			
			if(optionSelect == 1) {
				boolean btempflag = false;
				Signup signupobj = new Signup(con,logger,sc);
				btempflag = signupobj.signupInfo();
				if(!btempflag)
				{
					System.out.println("Error in Signing up the user");;
				}
		     }
			else if(optionSelect == 2) {
				boolean btempflag = false;
				VendorSignup vsignupobj = new VendorSignup(con,logger,sc);
				btempflag = vsignupobj.VendorsignupInfo();
				if(!btempflag)
				{
					System.out.println("Error in Signing up the vendor");;
				}
			} 	
			
			else {
				
				System.out.println("Wrong option entered. Try it once again.");
			} 
			
			
			
		}
		else if(ioptionSelected == 3)
			{bflag = false;}
		else
		{
			System.out.println("Wrong option entered. Try it once again.");
		}
		
	}
		
		
		System.out.println("Thank you for using BHOOKAD ONLINE FOOD DELIVERY SYSTEM.\nWe hope that you give us more chances to serve you in a more better or pleasant way.!!");
	    
	}

}


 class SQLConnection {
	
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



 class userinfo {
	 private	String userName;
	 private	String userArea;
	 private	String userState;
	 private	String userEmail;
	 private	String userId;
	 private	String userPwd;
	 private	String userCnfmPwd;
	 private	String userAdhaar; 
	 private	long pincode;
	 private	long userAltNo;
	 private	long userPhone;
		
	 
	 public String getUserName()
	 {
		 return this.userName;
	 }
	 public void setUserName( String sName)
	 {
		 this.userName = sName;	 
	 }
	 
	 public String getUserArea()
	 {
		 return this.userArea;
	 }
	 public void setUserArea( String sArea)
	 {
		 this.userArea = sArea;	 
	 }
	 
	 public String getUserState()
	 {
		 return this.userState;
	 }
	 public void setUserState( String sState)
	 {
		 this.userState = sState;	 
	 }
	 
	 public String getUserEmail()
	 {
		 return this.userEmail;
	 }
	 public void setUserEmail( String sEmail)
	 {
		 this.userEmail = sEmail;
	 }
	 
	 public String getUserId()
	 {
		 return this.userId;
	 }
	 public void setUserId( String sId)
	 {
		 this.userId = sId;	 
	 }
	 
	 public String getUserCnfmPwd()
	 {
		 return this.userCnfmPwd;
	 }
	 public void setUserCnfmPwd( String sCnfmPwd)
	 {
		 this.userCnfmPwd = sCnfmPwd;	 
	 }
	 
	 public String getUserPwd()
	 {
		 return this.userPwd;
	 }
	 public void setUserPwd( String sPwd)
	 {
		 this.userPwd = sPwd;	 
	 }
	 
	 public String getUserAdhaar()
	 {
		 return this.userAdhaar;
	 }
	 public void setUserAdhaar( String sAdhaar)
	 {
		 this.userAdhaar = sAdhaar;	 
	 }
	 
	 public long getPincode()
	 {
		 return this.pincode;
	 }
	 public void setPincode( long lPincode)
	 {
		 this.pincode = lPincode;	 
	 }
	 
	 public long getUserAltNo()
	 {
		 return this.userAltNo;
	 }
	 public void setUserAltNo( long luserAltNo)
	 {
		 this.userAltNo = luserAltNo;	 
	 }
	 
	 public long getUserPhone()
	 {
		 return this.userPhone;
	 }
	 public void setUserPhone( long lUserPhone)
	 {
		 this.userPhone = lUserPhone;	 
	 }
	 
	 public boolean passwordMatching(String sPwd1, String sPwd2)
	 {
		 return sPwd1.equals(sPwd2);
	 }
		
	}







class Signup extends userinfo{
	   Connection con;
	   Logger logger;	
	   Scanner sc;	
	   
	   Signup( Connection con1,Logger logger1,Scanner sc1) 	
	   {
		   this.con = con1;
		   this.logger = logger1;
		   this.sc = sc1;
	   }
		
		boolean signupInfo() {	
		System.out.println("Welcome to Signup page!!");	
		String sVar; long lVal;
		
		System.out.println("Enter username");
		String sVar1 = sc.nextLine();
		setUserName(sVar1);
		
		System.out.println("Enter Area");
		sVar= sc.nextLine();
		setUserArea(sVar);
		
		System.out.println("Enter State");
		sVar= sc.nextLine();
		setUserState(sVar);
		
		System.out.println("Enter Email");
		sVar= sc.nextLine();
		setUserEmail(sVar);
		
		System.out.println("Enter UserID");
		sVar= sc.nextLine();
		setUserId(sVar);
		
		boolean bflag = true,bflag1;
		while(bflag) {
		System.out.println("Enter Password");
		sVar= sc.nextLine();
		setUserPwd(sVar);
		
		System.out.println("Enter Password Again:");
		sVar= sc.nextLine();
		setUserCnfmPwd(sVar);
		
		bflag1 = passwordMatching(this.getUserPwd(),this.getUserCnfmPwd());
		if(bflag1)
		   bflag = false;
		}
		
		System.out.println("Enter Adhaar Number:");
		sVar= sc.nextLine();
		setUserAdhaar(sVar);
		
		System.out.println("Enter Contact Number:");
		lVal = sc.nextLong();
		setUserPhone(lVal);
		
		System.out.println("Enter Alternate Contact:");
		lVal = sc.nextLong();;
		setUserAltNo(lVal);
		
		System.out.println("Enter Pincode:");
		lVal = sc.nextLong();
		setPincode(lVal);
		
		
		String  sValues = "'" +this.getUserName()+ "','" + this.getUserPhone() + "','" + this.getUserEmail()+ "','" + this.getUserAdhaar()+ "','" + this.getUserArea() + "','"+
		                 this.getUserState()+ "','" + this.getUserId() + "','" + this.getUserPwd() + "','" + this.getPincode() + "','" + this.getUserAltNo() + "'";
		
	

//		
		String sQuery = "insert into userdetails(username,userphone,useremail,useradhaar,userarea,userstate,userid,userpwd,userpincode,uaeralternateno) values ("+ sValues +")";
		String sSucccessMsg = "New user details has been entered in the system.Thanking you for signup onto our portal.";
		String sfailMsg = "Error in inserting the User details.";
		String sError = SQLConnection.runUpdateQuery(sQuery,sSucccessMsg,sfailMsg,con);
		  if(!sError.isBlank())
			  {logger.info("Error Message in Signup.java in inserting user details options :"+sError);
			     return false;
			  }
		  else
			 return true; 
		  
		} 		
		
		
	}


 class Login {
	Connection con;
	Logger logger;
	boolean bflag1;
	Scanner sc;
	
	Login(Connection con1,Logger logger1,Scanner sc1){
		this.bflag1 = false;
		this.logger = logger1;
		this.con = con1;
		this.sc = sc1;
	}
	
	public boolean loginDisplay()
	{   
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
	            	      System.out.println("Congo, you are authenticated.");
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




















/**
 * @brief Payment class used for making payment of the food item 

 */

class Payment extends Order{
	String userID="abc12";
	long CardNumber;
	int CVV;
	int ExpiryDate;
	String UPI;
	Order o2=new Order();
	ArrayList<Object> arrlist3 =o2.arrlist;
	/**
	 * @brief This function checks if the entered details by user for payment is valid or not
	 * by checking in the database 
	 * @return void
	 */	
	
	public void getDetails() throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
	    String connectionUrl = "jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true";
	    Connection con = DriverManager.getConnection(connectionUrl);  
	    
	    ResultSet resultSet2 = null;
		System.out.println("Select the option for payment");
		System.out.println("1. By Credit or Debit Card");
		System.out.println("2. By UPI ID");
	    int number;
	     
	    number=sc.nextInt();
	  
	    switch(number){  
	    //Case statements  
	    case 1: 
	    	
	    	   
	    	      System.out.println("Enter your Aadhar Number");
	    			System.out.println("Enter Card Details");
	    			CardNumber=sc.nextLong();
	    			System.out.println("Enter Expiry Date of the  card in format Month-Year ");
	    			ExpiryDate=sc.nextInt();
	    			System.out.println("Enter CVV ");
	    			CVV=sc.nextInt();
	    			
	    			
	    		
//	    		    ResultSet resultSet3 = null;
	    		    
	    		    try (Statement statement = con.createStatement();) {
	    		    	 

	    		        String query = "SELECT cardnumber,expirydate,cvv ,upiid FROM UserBankdetails where userId=? ";
	     
	    		        
	    		        PreparedStatement pstmt2 = con.prepareStatement(query);
	    		        pstmt2.setString(1,userID);
	    		      
	    		        resultSet2 = pstmt2.executeQuery();

	    		            while (resultSet2.next()) {
	    		               
	    		            	if(resultSet2.getLong(1)==CardNumber && resultSet2.getInt(2)==ExpiryDate && resultSet2.getInt(3)==CVV  && resultSet2.getString(4).equals(UPI)) {
	                		  
	    		   String updatequery = "INSERT INTO orderdetails ( itemname1, itemqty1,itemprice1,itemname2, itemqty2,itemprice2,itemname3, itemqty3,itemprice3,"
	    		   	+ "itemname4, itemqty4,itemprice4,itemname5, itemqty5,itemprice5,itemname6, itemqty6,itemprice6,"
	    		   	+ "itemname7, itemqty7,itemprice7,itemname8, itemqty8,itemprice8"
	    		            		 		+ ") "
	    		            		 		+ "VALUES (?,?,?,?,?,?);";
	    		            	
//	    		            		PreparedStatement pstmt3 = con.prepareStatement(updatequery);
//	    		       		        pstmt3.setString(1,arrlist3.get(0));
//	    		       		      
//	    		       		        resultSet3 = pstmt3.executeQuery();
//	    		            		 
	    		            		 
	    		            		 
	    		            		System.out.println("Payment Successful");
	    		            	}
	    		            	else {
	    		            		
	    		            		System.out.println("Payment Failed");
	    		            		
	    		            	}
	    		            }

	    		     }    
	    		       
	    		        catch (SQLException e) {
	    		            e.printStackTrace();
	    		        }

	       
	    break;  
	    case 2:
	             
		

		System.out.println("Enter Your UPI ID");
		UPI=sc.next();
		
	
//	    ResultSet resultSet3 = null;
	    
	    try (Statement statement = con.createStatement();) {
	    	 

	        String query = "SELECT upiid FROM UserBankdetails where userId=? ";

	        
	        PreparedStatement pstmt2 = con.prepareStatement(query);
	        pstmt2.setString(1,userID);
	      
	        resultSet2 = pstmt2.executeQuery();

	            while (resultSet2.next()) {
	               
	            	if(resultSet2.getString(1).equals(UPI)) {
        		  
	   String updatequery = "INSERT INTO orderdetails ( itemname1, itemqty1,itemprice1,itemname2, itemqty2,itemprice2,itemname3, itemqty3,itemprice3,"
	   	+ "itemname4, itemqty4,itemprice4,itemname5, itemqty5,itemprice5,itemname6, itemqty6,itemprice6,"
	   	+ "itemname7, itemqty7,itemprice7,itemname8, itemqty8,itemprice8"
	            		 		+ ") "
	            		 		+ "VALUES (?,?,?,?,?,?);";
	            	
//	            		PreparedStatement pstmt3 = con.prepareStatement(updatequery);
//	       		        pstmt3.setString(1,arrlist3.get(0));
//	       		      
//	       		        resultSet3 = pstmt3.executeQuery();
//	            		 
	            		 
	            		 
	            		System.out.println("Payment Successful");
	            	}
	            	else {
	            		
	            		System.out.println("Payment Failed");
	            		
	            	}
	            }

	     }    
	       
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    break;
	    }
	    
	    
		/**
		 * @brief Connecting with  UserBankdetails table in database
		
		 */
		
		

	}
	
	
	
}


class Order {

	
	/**
	 * @brief Array list created to get the items added by user in the cart
	 
	 */
	
 ArrayList<Object> arrlist = new ArrayList<Object>();
   
 private  String itemName;
     
     private  int quantity;
     

     public String getName() {
     return itemName;
  }

  public int getQuantity() {
     return quantity;
  }

  public void setName(String Name) {
  itemName= Name;
  }

  public void setQuantity( int number) {
     quantity = number;
  }
 
 

 

public  void viewCart() {


  arrlist.add("cheese burger");
  arrlist.add(2);
  arrlist.add("aloo burger");
  arrlist.add(1);
   
}

public static void viewWishlist() {
   
}
}


/**
 * @brief User class used for calculating total bill after adding items in the cart 
 
 */


class User extends Order {
    String userId="abc12";
    String RestaurantId="burgerking_nd";
    String userEmail;
    String userContact;
    
    double total_bill;
    String[] promo = new String[2];
//    String userLocation="south delhi";
    String name;
    int quantity;
    int size1;
    User() {}

    User(String itemName,int quantity){
   
    this.name=itemName;
    this.quantity=quantity;
   
   
    }
 
    Scanner sc=new Scanner(System.in);  
    public  void usr () throws ClassNotFoundException, SQLException {
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
    String connectionUrl = "jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true";
    Connection con = DriverManager.getConnection(connectionUrl);  
   
    ResultSet resultSet = null;
   
    /**
     * @brief Order object to access the the view cart from Order class
     
     */
    
    Order o=new Order();
    o.viewCart();
   
    ArrayList<Object> arrlist1 =o.arrlist;

   int Base_Price=0;
    for(int i=0;i<arrlist1.size();i=i+2) {
    ResultSet resultSet1=null;
    try (Statement statement = con.createStatement();) {
 
    	/**
    	 * @brief Selecting the itemPrice from menu table of a particular item that user has added to cart 
    	 * to calculate the final bill 
    	 
    	 */

    String query = "SELECT itemPrice FROM menudetails where vendorid = ? and itemName=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1,RestaurantId);
        pstmt.setString(2,(String) arrlist1.get(i));
        resultSet1 = pstmt.executeQuery();
 
//       resultSet = statement.executeQuery(selectSql);

        while (resultSet1.next()) {
           Base_Price+=resultSet1.getInt(1)*(int)arrlist1.get(i+1);
        }

 }    
   
    catch (SQLException e) {
        e.printStackTrace();
    }
   }
    System.out.println("Base bill is:- "+Base_Price);
    
    
    /**
     * @brief total_bill is used to calculate the final payment need to be done including the items price
     * that user has added + the delivery charge that needs to be applied
     
     */

     total_bill=Base_Price;
    if(Base_Price<100) {
    System.out.println("Sorry Order cannot be placed");
    }
    else {
    	
    	/**
    	 * @brief Calculating delivery fees by calculating the distance between user location  and the restaurant location
    	 
    	 */

    	
    String q = "SELECT distance FROM distancetable where location1 = ? and location2=?";
        PreparedStatement p = con.prepareStatement(q);
        p.setString(1,userId);
        p.setString(2,RestaurantId);
        ResultSet resultSet2 = p.executeQuery();
        while (resultSet2.next()) {
            total_bill+=resultSet2.getInt(1)*8;
         }
       
        System.out.println("Total BILL:-"+total_bill);
       
        
        int count=0;
        
        /**
         * @brief Applying Promo code i.e. SAVE50 and SAVE20 for getting discount on the total_bill 
         * and these coupons are valid for a user only once 
         
         */

        /**
         * @brief Applying SAVE50
         
         */

        String coupon = "SELECT promo1 FROM userdetails where userid = ?";
        PreparedStatement promo1 = con.prepareStatement(coupon);
        promo1.setString(1,userId);
        ResultSet resultSet3 = promo1.executeQuery();
        while (resultSet3.next()) {
            if(resultSet3.getInt(1)==0) {
            promo[count++]="SAVE50";
            }
         }
        
        /**
         * @brief Applying SAVE20
         
         */

       
        
        String coupon1 = "SELECT promo2 FROM userdetails where userid = ?";
        PreparedStatement promo2 = con.prepareStatement(coupon1);
        promo2.setString(1,userId);
        ResultSet resultSet4 = promo2.executeQuery();
        while (resultSet4.next()) {
            if(resultSet4.getInt(1)==0) {
            promo[count++]="SAVE20";
           
            }
         }
     
        if(count!=0) {
       
        if(count==1) {
        System.out.println("You can apply Coupon "+promo[0]+" Enter y to apply or n for not applying");
        String ch=sc.next();
        if(ch.equals("Y") || ch.equals("y")) {
        if(promo[0].equals("SAVE50")) {
       
        	applyCoupon("SAVE50");

             
        }
        else if(promo[0].equals("SAVE20")) {
        	applyCoupon("SAVE20");
        }        
        }
        }    
        else {
        	applyCoupon("SAVE50","SAVE20");   
        }
        }
    System.out.println("Total price will be:- "+total_bill);
    }
   
   
}
    
    
    private void applyCoupon(String string) throws ClassNotFoundException, SQLException {
    	// TODO Auto-generated method stub
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
    	   String connectionUrl ="jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true";
    	   Connection con = DriverManager.getConnection(connectionUrl);
    	   if(string.equals("SAVE50")) {
    	total_bill/=2;
    	//updating user details
    	String q1 = "UPDATE userdetails SET promo1=1 where userid = ?";
    	        PreparedStatement qw1 = con.prepareStatement(q1);
    	        qw1.setString(1,userId);
    	        qw1.executeUpdate();
    	        }
    	   else {
    	    double r=(0.2*total_bill);
    	total_bill-=r;
    	String q2 = "UPDATE userdetails SET promo2=1 where userid = ?";
    	       PreparedStatement qw2 = con.prepareStatement(q2);
    	       qw2.setString(1,userId);
    	       qw2.executeUpdate();
    	   }

    	}

    	protected void applyCoupon(String string, String string2) throws ClassNotFoundException, SQLException {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
    	   String connectionUrl = "jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true";
    	   Connection con = DriverManager.getConnection(connectionUrl);
    	// TODO Auto-generated method stub


    	System.out.print("You can apply Coupon "+promo[0]+" and "+promo[1]+"Enter\n1. TO APPLY SAVE50 \n2. TO APPLY SAVE20 \n3. FOR NONE");
    	int op=sc.nextInt();
    	 if(op==1)
    	 {
    	total_bill/=2;
    	//updating user details
    	String q1 = "UPDATE userdetails SET promo1=1 where userid = ?";
    	       PreparedStatement qw1 = con.prepareStatement(q1);
    	       qw1.setString(1,userId);
    	       qw1.executeUpdate();
    	 }
    	 else if(op==2) {
    	total_bill-=(0.2*total_bill);
    	String q2 = "UPDATE userdetails SET promo2=1 where userid = ?";
    	       PreparedStatement qw2 = con.prepareStatement(q2);
    	       qw2.setString(1,userId);
    	       qw2.executeUpdate();
    	  }

    	}

}




public class foodorder {
public static void main(String args[]) throws ClassNotFoundException, SQLException {
String user1 = null;

Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
String connectionUrl ="jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true";
Connection con = DriverManager.getConnection(connectionUrl);  


ResultSet resultSet = null;
startProject sp=new startProject();
sp.main(args);
User u=new User();
u.usr();
Payment p= new Payment();
p.getDetails();
}
}