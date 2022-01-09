package oopdProject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @brief startProject class is the main class of the food ordering system.
 * 		  

 */
public class startProject {
	
	/**
	 * @brief main method of the system.
	 * 		  

	 */
	public static void main(String args[]) throws SQLException, ClassNotFoundException {
//		System.out.println("WELCOME new code\n");
		Logger logger = Logger.getLogger("FoodDeliveryLog");  
	    FileHandler fh;  
		Connection con = SQLConnection.getConnection();
    	Scanner sc = new Scanner(System.in); 
	    try{
	    	
	    	// This block configure the logger with handler and formatter  
	        fh = new FileHandler("C:\\Users\\IIITD\\Desktop\\OOPD_Project\\File.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	        
	        //Start of Application
	        startapplication(con,logger,sc);	       
	        sc.close();
	    }
	      
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    finally {
	    	 con.close();
	    }
	   
	    
		}
	
	/**
	 * @brief startapplication is the method which shows user main page of the app and how he wants to continue by login or signup
	 * @return void
	 * @param Connection object, logger object, Scanner object
	 * 		  

	 */
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
