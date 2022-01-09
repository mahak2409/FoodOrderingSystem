package oopdProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

public class FrontPage {
	
	public boolean frontPageStart(Connection con,String userid,Logger logger) {
		
		String selectsql ;
		String sErrorMsg="" ;
		ResultSet resultSet = null;
		try {
		boolean bVar = true;
		while(bVar) {	
		String sQuery = "select * from userdetails where userid='"+ userid+"'";
		ResultSet rsobj = SQLConnection.getResultSet(con, sQuery, sErrorMsg);
		while(rsobj.next()) 
		
		System.out.println("Hello, "+ rsobj.getString(1) +"\n Select any of the options below:");	
		System.out.println("1. See Restaurant Details");
		System.out.println("2. See User Details");
		System.out.println("3. See Order Details");
		System.out.println("4. Go into Cart");
		System.out.println("5. Move Back");
		System.out.println("6. Logout");
		
		Scanner sc = new Scanner(System.in);
		int option = sc.nextInt();
		
		
		if(option == 1) {
			
		boolean bVal =	displayRestaurantDetails(sc,con,logger,userid);	
		if(!bVal) 
		{
			System.out.println("Error in display Restaurant details");
		}
		
		}
		else if(option == 2 ) {
				try {
					sErrorMsg = "";
					 ResultSetMetaData rsmd = rsobj.getMetaData();
					 
					int i=1;
		        		if(sErrorMsg.isBlank()) {
		        			while (i<=12) {
		        				
		        				if(i!=7) {       //password is not going to be printed
		        				String name = rsmd.getColumnName(i);
		        				System.out.println(name + " : "+rsobj.getString(i));
		        				++i;
		        				}
		        			}
		        		}
		        		else
		        		{
		        			// the following statement is used to log any messages  
		        			logger.info("Error Message in retrieving user details in FrontPage.java :"+sErrorMsg);  
		        		}
		        	}
					catch(Exception e)
		        	{		
						logger.info("Exception Message in retrieving user details in  FrontPage.java  :"+ e.getMessage());	
		        	}
		}
		else if (option == 3 ) {
			
			selectsql = "select * from orderdetails where userid='"+ userid+"'";
				try {
					sErrorMsg = "";
					resultSet = SQLConnection.getResultSet(con, selectsql, sErrorMsg);
					ResultSetMetaData rsmd = rsobj.getMetaData();
					
					if(sErrorMsg.isBlank()) {
						while (resultSet.next()) {
							int i=1;
							while (i<=28) {
							if(i<4 && i>18) {	
							String name = rsmd.getColumnName(i);
							System.out.println(name + " : "+resultSet.getString(i));
							++i;}
							}
						}
					}
					else
					{
        			// the following statement is used to log any messages  
						logger.info("Error Message in retrieving order details in  FrontPage.java :"+sErrorMsg);  
					}
				}
				catch(Exception e)
					{		
						logger.info("Exception Message in retrieving order details in FrontPage.java  :"+ e.getMessage());	
					}
		}
		else if (option == 4 ) { 
			    Cart objCart = new Cart(con,userid,logger);
			    objCart.Process();
			
			    
		}
		else if (option == 5 ) { 
			bVar = false;
		}
		else if (option == 6 ) { 
			System.out.println("Exiting...");
			return false;
		}
		else
		{
			System.out.println("Wrong key pressed...");
		}
		}
		
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}	
		return true;
	
	}
	
	public boolean displayRestaurantDetails(Scanner sc,Connection con,Logger logger,String userid)	
	{
		System.out.println("Hello,What do you want to see:");
		boolean bVar = true;
		
		while(bVar) {
		System.out.println("1.Locality-wise restaurant details\n2.Food-wise restaurant details\n3.Move Back");	
	    int optionSelected =  sc.nextInt(); 

	    if(optionSelected == 1)
	    {
	    	System.out.println("Select places from the following:");	
	    	try {
	    	
	    	boolean bflag = true;
	    	while(bflag) {	
	    	String sQuery = "select distinct vendorarea from vendordetails";
	    	String sError="";
	    	ResultSet rsObj = SQLConnection.getResultSet(con, sQuery, sError);
	    	int count =1;
	    	HashMap<Integer,String> places = new HashMap<Integer,String>();
	    	
	    	
	        while (rsObj.next()) {
            System.out.println(count+": "+rsObj.getString(1));
            count++;
            places.put(count,rsObj.getString(1));
	        }
	        
	        int option = sc.nextInt();
	        
	        	if (places.containsKey(option)) {
	        	
	            String sarea = places.get(option);
	            VendorwiseOrder.View(userid,con,logger,sarea);
	            bflag = false; 
	        	}
	        	else
	        	{
	        	System.out.println("wrong option selected. Try once again");
	        	}
	    	}
	        
	        
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.println(e.getMessage());    		
	    	}
	    	   	
	    }
	    else if (optionSelected == 2)
	    {
	    	System.out.println("Select food items from the following:");	
	    	try {
	    	
	    	boolean bflag = true;
	    	while(bflag) {	
	    	String sQuery = "select distinct itemName from menudetails";
	    	String sError="";
	    	ResultSet rsObj = SQLConnection.getResultSet(con, sQuery, sError);
	    	int count =1;
	    	HashMap<Integer,String> foods = new HashMap<Integer,String>();
	    		
	        while (rsObj.next()) {
            System.out.println(count+": "+rsObj.getString(1));
            count++;
            foods.put(count,rsObj.getString(1));
	        }
	        
	        int option = sc.nextInt();
	        
	        	if (foods.containsKey(option)) {
	        	
	            String sFood = foods.get(option);
	            foodwiseOrder.View(userid,con,logger,sFood);
	            bflag = false; 
	        	}
	        	else
	        	{
	        	System.out.println("wrong option selected. Try once again");
	        	}
	    	}
	        
	        
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.println(e.getMessage());    		
	    	}
	    	   	
	    }
	    else
	    {
	    	bVar = false;
	    }
	}
	    return true;
	}	

}
