package oopdProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @brief Cart class used for performing different operations on the user cart for the food item

 */
public class Cart {
	
	HashMap<String,Integer> order = new HashMap<String, Integer>();
	Connection connection =null;
	static String suserID ;
	Logger logger;
	
	/**
	 * @brief Parameterized Cart class constructor
	 * @param Connection object, userId, logger object

	 */
	public Cart(Connection con,String userID,Logger logger1) {
		this.connection = con;
		this.suserID = userID;	
		this.logger = logger1;
	}
	
	/**
	 * @brief Process Function gives user actions to be performed on cart.
	 * Actions on cart: add,delete an item,delete cart,proceed to payment, modify item quantity , view modified cart options
	 * @return void

	 */
	
	Scanner sc = new Scanner(System.in);
	public void Process() {
		String selectedVendor="";
		
		
		//printing view data
		boolean bValue = viewCart(this.connection,this.suserID);
	    if(!bValue) { 	
	    boolean bflag = true;
		while(bflag) {
		System.out.println("Choose option to perform on Cart");	
		System.out.println("1.Add item to Cart \n 2.Delete item from Cart \n 3.Empty Cart \n 4.Proceed to payment \n 5.Modify ItemQuantity \n 6.View Modified Cart \n 7.Move Back");
		
		int cartOption = sc.nextInt();
		
		if(cartOption == 1) {
             
			FrontPage fp = new FrontPage();
			fp.displayRestaurantDetails(sc, this.connection, this.logger, this.suserID);
			
		}
		else if(cartOption == 2) 
		{ 
		 System.out.println("which item yo want to delete?");
		 viewCart(this.connection,this.suserID);
		 int optionSelect = sc.nextInt();		 
		 String sQuery1 = "select ROW_NUMBER() OVER(ORDER BY userid) AS RowNum,* from cartDetails where userid='"+this.suserID+"'";
		 String sErrMsg="";
		 try {
				ResultSet resultSet1 = SQLConnection.getResultSet(this.connection, sQuery1, sErrMsg);
				 while(resultSet1.next()) 
				 {  
					 if(Integer.parseInt(resultSet1.getString(1)) == optionSelect) 
				 	{
					   String sQuery2 = "delete from cartDetails where userId = '"+this.suserID+"' and itemNameSelected ='"+resultSet1.getString(3)+"' and vendorIdSelected='"+resultSet1.getString(2)+"'";
					  
					   String sErrorMsg2 = "",sSuccessMsg = "Item deleted from cart successfully",sFailMsg = "Item can't deleted from cart successfully";	
		        		sErrorMsg2 = SQLConnection.runUpdateQuery(sQuery2, sSuccessMsg, sFailMsg, this.connection);
		        		
		        				if(!sErrorMsg2.isBlank())
		        					{
		        			          System.out.println("Error Message in cart.java in deleting item from cart:"+ sErrorMsg2);
		        					}
					  				   
				 	}
				 }
				
			}
				
	 catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
		 
		}
		else if(cartOption == 3) //empty cart
		{
			String sQuery = "delete from cartdetails where userId='"+this.suserID+"'";
			String sErrorMsg2 = "",sSuccessMsg = "Cart emptied successfully",sFailMsg = "Cart can't be emptied successfully";	
    		sErrorMsg2 = SQLConnection.runUpdateQuery(sQuery, sSuccessMsg, sFailMsg, this.connection);	
    				if(!sErrorMsg2.isBlank())
    					{
    			          System.out.println("Error Message in emptying cart in cart.java:"+ sErrorMsg2);
    					}	
		} 
		else if(cartOption == 4)
		{
			
			
			
			int deliveryTime=1;
	       	String  uD="",vD="";
	    	
	       	try {
	       		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	       		String connectionUrl ="jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true";
	       		Connection con = DriverManager.getConnection(connectionUrl);  

    	    	
    	        String  uDist= "SELECT userarea FROM userdetails where userId= ?";
    	        PreparedStatement pu = con.prepareStatement(uDist);
    	        pu.setString(1,suserID);
    	     
    	        ResultSet resultSetpu = pu.executeQuery();
    	        while (resultSetpu.next()) {
    	        	
    	            uD=resultSetpu.getString(1);
    	        }
    	        
    	        String sQuery1 = "select vendorIdSelected from cartdetails where userId=?";
    	        PreparedStatement pq = con.prepareStatement(sQuery1);
    	        pq.setString(1,suserID); 
    	        ResultSet resultSetpq = pq.executeQuery();
    	        while(resultSetpq.next()) {
    	        	selectedVendor=resultSetpq.getString(1);
    	        }
    	        
    	        String  vDist= "SELECT vendorarea FROM vendordetails where vendorid= ?";
    	        PreparedStatement pv = con.prepareStatement(vDist);
    	        pv.setString(1,selectedVendor);
    	        
    	        ResultSet resultSetpv = pv.executeQuery();
    	        while (resultSetpv.next()) {
    	        	
    	            vD=resultSetpv.getString(1);
    	        }
    	     
    	        String q = "SELECT distance FROM distancetable where location1 = ? and location2=?";
    	        PreparedStatement p = con.prepareStatement(q);
    	        p.setString(1,uD);
    	        p.setString(2,vD);
    	        ResultSet resultSet2 = p.executeQuery();
    	        
    	        while (resultSet2.next()) {
    	        	System.out.println(resultSet2.getInt(1));
    	        	deliveryTime=(int)resultSet2.getInt(1)*5;
    	         }
    	        
    	        
    	        System.out.println("Delivery time will be " +deliveryTime +  selectedVendor + " "+ "And your OrderID is " + suserID);
    	    	//Payment related code
    	    	User u=new User();
    	    	u.usr(suserID,selectedVendor);

    	    	int orderId=0;
    	    	   String coupon = "SELECT orderid  FROM orderdetails  where userid= ? ORDER BY orderid DESC ,dateandtime DESC ";
    	           PreparedStatement promo1 = con.prepareStatement(coupon);
    	         promo1.setString(1,suserID);
    	           ResultSet resultSet3 = promo1.executeQuery();
    	    	
    	    	while(resultSet3.next()) {
    	    		   
    	    		orderId=  resultSet3.getInt(1);
    	    	}
    	    	
    			System.out.println("Delivery time will be " + deliveryTime + " "+ "And your OrderID is " + orderId);
    			
	       		
	       		
	       		

			
			tracking objTrack = new tracking();
			ordered objOrder = new ordered();
			objOrder.orderAssign(this.connection, orderId, this.suserID, deliveryTime);
			objTrack.orderTracking(objOrder,this.connection);
			
			bflag = false;
	       	}
			catch(Exception e)
	       	{
				System.out.println("Exception in cart.java in payment option:"+e.getMessage());
	       	}
			
		}
		else if(cartOption == 5)
		{    
			 System.out.println("which item quantity you modify?");
			 viewCart(this.connection,this.suserID);
			 int optionSelect = sc.nextInt();
			 System.out.println("Value of new quantity?");
			 int optionSelect1 = sc.nextInt();
			 String sQuery1 = "select ROW_NUMBER() OVER(ORDER BY userid) AS RowNum,* from cartDetails where userid='"+this.suserID+"'";
			 String sErrMsg="";
			 try {
					ResultSet resultSet1 = SQLConnection.getResultSet(this.connection, sQuery1, sErrMsg);
				
					 while(resultSet1.next()) 
					 {  
						 if(Integer.parseInt(resultSet1.getString(1)) == optionSelect) 
					 	{
						   String sQuery2 = "update cartDetails set itemQtySelected='"+optionSelect1+"'  where vendorIdSelected='"+resultSet1.getString(2)+"' and itemNameSelected='"+resultSet1.getString(3)
						        +"' and userId='"+this.suserID+"'";
						   String sQuery3 = "update cartDetails set totalItemPrice= (itemQtySelected * unitItemPrice) where vendorIdSelected='"+resultSet1.getString(2)+"' and itemNameSelected='"+resultSet1.getString(3)+"' and userId='"+this.suserID+"'";
						 
						   String sErrorMsg2 = "",sSuccessMsg = "Item quantity modified in cart successfully",sFailMsg = "Item quantity can't modified in cart successfully";	
			        		sErrorMsg2 = SQLConnection.runUpdateQuery(sQuery2, sSuccessMsg, sFailMsg, this.connection);
			        		
			        				if(!sErrorMsg2.isBlank())
			        					{
			        			          System.out.println("Error Message in cart.java in modifying item quantity cart details:"+ sErrorMsg2);
			        					}
						   
			        				sErrorMsg2 = "";sSuccessMsg = "Item totalPrice modified in cart successfully";sFailMsg = "Item totalPrice can't modified in cart successfully";	
					        		sErrorMsg2 = SQLConnection.runUpdateQuery(sQuery3, sSuccessMsg, sFailMsg, this.connection);
					        		
					        				if(!sErrorMsg2.isBlank())
					        					{
					        			          System.out.println("Error Message in cart.java in modifying item totalPrice cart details:"+ sErrorMsg2);
					        					}				   
					 	}
					 }
					
				}
					
		 catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
			 
		}
		else if(cartOption == 6)
		{
			
			viewCart(this.connection,this.suserID);
		}
		else if(cartOption == 7)
		{
			bflag = false;
		}
		else
		{
			System.out.println("You might have pressed wrong key. Try it once again.");
		}
	  }   
	 }	
	}
	
	/**
	 * @brief viewCart Function shows the user item present in the cart
	 * @param Connection object and userId
	 * @return boolean

	 */	
 public static boolean viewCart(Connection con,String sUserId)	
 {
	 Scanner sc = new Scanner(System.in);
	 System.out.println("Welcome in Cart!!");
		String sSql = "select * from cartdetails where userId='"+sUserId+"'";
		String sErrMsg = "";
		try {
		ResultSet resultSet = SQLConnection.getResultSet(con, sSql, sErrMsg);
		
		if (!resultSet.isBeforeFirst() ) {    
		    System.out.println("Oops, Your cart is empty");
		    return false;
		} 
		else {
			System.out.println("Your cart contains");
			System.out.println("S.No\t VendorName\t Item Name\t ItemQty\t Unit Price\t Item Price");
			
		
		 int i=1;	
		 while(resultSet.next()) 
		 {
			 System.out.println(i+"\t\t"+resultSet.getString(1)+"\t\t "+resultSet.getString(2)+"\t\t "+resultSet.getString(3)+"\t\t "+resultSet.getString(5)+"\t\t "+resultSet.getString(6));
			 i++;
		 }
		
		 System.out.println("Proceed to payment ? Y/N ");
		 String s1=sc.next();
		 if(s1.equals("y") || s1.equals("Y")) {
			 
				int deliveryTime=1;
		       	String  uD="",vD="";
		        String selectedVendor="";
		       	try {
		       	

	    	    	
	    	        String  uDist= "SELECT userarea FROM userdetails where userId= ?";
	    	        PreparedStatement pu = con.prepareStatement(uDist);
	    	        pu.setString(1,suserID);
	    	     
	    	        ResultSet resultSetpu = pu.executeQuery();
	    	        while (resultSetpu.next()) {
	    	        	
	    	            uD=resultSetpu.getString(1);
	    	        }
	    	        
	    	        String sQuery1 = "select vendorIdSelected from cartdetails where userId=?";
	    	        PreparedStatement pq = con.prepareStatement(sQuery1);
	    	        pq.setString(1,suserID); 
	    	        ResultSet resultSetpq = pq.executeQuery();
	    	       
					while(resultSetpq.next()) {
	    	        	selectedVendor=resultSetpq.getString(1);
	    	        }
	    	        
	    	        String  vDist= "SELECT vendorarea FROM vendordetails where vendorid= ?";
	    	        PreparedStatement pv = con.prepareStatement(vDist);
	    	        pv.setString(1,selectedVendor);
	    	        
	    	        ResultSet resultSetpv = pv.executeQuery();
	    	        while (resultSetpv.next()) {
	    	        	
	    	            vD=resultSetpv.getString(1);
	    	        }
	    	     
	    	        String q = "SELECT distance FROM distancetable where location1 = ? and location2=?";
	    	        PreparedStatement p = con.prepareStatement(q);
	    	        p.setString(1,uD);
	    	        p.setString(2,vD);
	    	        ResultSet resultSet2 = p.executeQuery();
	    	        
	    	        while (resultSet2.next()) {
	    	        	System.out.println(resultSet2.getInt(1));
	    	        	deliveryTime=(int)resultSet2.getInt(1)*5;
	    	         }
	    	        
	    	        
	    	        System.out.println("Delivery time will be " +deliveryTime +  selectedVendor + " "+ "And your OrderID is " + suserID);
	    	    	//Payment related code
	    	    	User u=new User();
	    	    	u.usr(suserID,selectedVendor);

	    	    	int orderId=0;
	    	    	   String coupon = "SELECT orderid  FROM orderdetails  where userid= ? ORDER BY orderid DESC ,dateandtime DESC ";
	    	           PreparedStatement promo1 = con.prepareStatement(coupon);
	    	         promo1.setString(1,suserID);
	    	           ResultSet resultSet3 = promo1.executeQuery();
	    	    	
	    	    	while(resultSet3.next()) {
	    	    		   
	    	    		orderId=  resultSet3.getInt(1);
	    	    	}
	    	    	
	    			System.out.println("Delivery time will be " + deliveryTime + " "+ "And your OrderID is " + orderId);
	    			
		       		
		       		
		       		

				
				tracking objTrack = new tracking();
				ordered objOrder = new ordered();
				objOrder.orderAssign(con, orderId, suserID, deliveryTime);
				objTrack.orderTracking(objOrder,con);
				boolean bflag=true;
				bflag = false;
		       	}
				catch(Exception e)
		       	{
					System.out.println("Exception in cart.java in payment option:"+e.getMessage());
		       	}
		 }
		 
		 
		 }
		
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return true;
		
		
 }
	
 }


