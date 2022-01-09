
package oopdProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @brief foodwiseOrder class used to order food items from the restaurants for the user choice food 

 */
public class foodwiseOrder {
	
	/**
	 * @brief View function shows the restaurants of the chosen food item. 
	 * 		  Then asks user which restaurant he will choose and what will be the quantity of the item.
	 * 		  Add item to  the cart table and then in the end it asks user if he wants to proceed for the payment or not
	 * 
	 * @param userId,Connection object,logger object, chosenfood(user input)
	 * 			

	 */
	public static void View(String userId,Connection con,Logger logger,String chosenfood) {
		  int itemid;
		String selectsql ;
		String sErrorMsg ;
		ResultSet resultSet = null;
		Scanner sc = new Scanner(System.in);
        ArrayList<String> availableVendorId = new ArrayList<String>();
        String selectedVendor=null;
        //ArrayList<String> availableItemId = new ArrayList<String>();
       			
		selectsql = "select distinct t1.vendorId,t2.vendorname from menudetails t1 inner join vendordetails t2 on t1.vendorId = t2.vendorid where itemName = '"+chosenfood+"'";
			 try {
			        sErrorMsg = "";
			        resultSet = SQLConnection.getResultSet(con, selectsql, sErrorMsg);
			        int i=1;
			        System.out.println("Select your Restaurant from the following");
			        	if(sErrorMsg.isBlank()) {
			        		while (resultSet.next()) {
			        			System.out.println(i+"."+resultSet.getString(2));
			        			availableVendorId.add(resultSet.getString(1));  i++; 			
			        		}
			        		
			        		System.out.println("Enter your option");
			        		int Vendor = sc.nextInt();
			        		selectedVendor = availableVendorId.get(Vendor-1);//Get Selected vendor id
			        	}
			        	else
			        	{
				        // the following statement is used to log any messages  
				        logger.info("Error Message in retrieving restaurants in Vendorwise.java :"+sErrorMsg);  
			        	}
			        }
					catch(Exception e)
			        {		
						logger.info("Exception Message in retrieving restaurants in Vendorwise.java  :"+ e.getMessage());	
			        }
			 
		
			 if(selectedVendor!=null) {
					String selectsql1 = "select * from menudetails where vendorId='"+selectedVendor+"' and itemName='"+ chosenfood +"'";
					
					try {
					       sErrorMsg = "";
					        resultSet = SQLConnection.getResultSet(con, selectsql1, sErrorMsg);
					        int i=1;
					        System.out.println("Select your order");
					        	if(sErrorMsg.isBlank()) {
					        		while (resultSet.next()) {
					        			System.out.println(i+".Item name: "+resultSet.getString(2)+" Price: "+resultSet.getString(4));
					        			itemid = Integer.parseInt(resultSet.getString(5));
					        			i++;
					        		}
					        		
					          		
						        		System.out.println("Select your item quantity");
						        		int qty = sc.nextInt();
						        							        		
					        		
					        		System.out.println("Choose any of the given options:");
					        		System.out.println("1.Add to Cart");
					        		System.out.println("2.Add to Wishlist");
					        		
					        		int userOption = sc.nextInt();
					        		
					        		if(userOption == 1) {
					        		     	    
					        	     String sQuery1 = "select itemprice,itemname from menudetails where itemName = '"+ chosenfood +"' and vendorid = '"+selectedVendor+"'";   
					        	     String sErrorMsg1 = "";
								     ResultSet resultSet1 = SQLConnection.getResultSet(con, sQuery1, sErrorMsg1); 
					        	     
								     int iPrice = 0;
								     String itemNameSelected="";
								     if(sErrorMsg1.isBlank()) {
							        		while (resultSet1.next()) {
							        			 iPrice = Integer.parseInt(resultSet1.getString(1)); 
							        			 itemNameSelected = resultSet1.getString(2);
							        		}
							       
							        		
							        if(iPrice !=0) { 		
					        		String sQuery = "INSERT INTO cartDetails (vendorIdSelected,itemNameSelected,itemQtySelected,userId,unitItemPrice,totalItemPrice) "
					        				+ "VALUES ('"+selectedVendor+"','"+itemNameSelected+"','"+qty+"','"+userId+"',"+iPrice+","+(qty * iPrice)+") "; 	
					        		String sErrorMsg2 = "",sSuccessMsg = "Item entered in cart successfully",sFailMsg = "Item can't entered in cart successfully";	
					        		sErrorMsg2 = SQLConnection.runUpdateQuery(sQuery, sSuccessMsg, sFailMsg, con);
					        		
					        				if(!sErrorMsg2.isBlank())
					        					{
					        			          System.out.println("Error Message in foodwiseOrder.java in entering cart details:"+ sErrorMsg2);
					        					}
					        				}
					        		
								     	}
							        			        		
					        	    System.out.println("Do you wish to Proceed for Payment? Press 'y' for 'Yes' and 'n' for 'No'");
					        	    String sAns = sc.next();
					        	    
					        	    if(sAns.equals("y") || sAns.equals("Y"))
					        	    {
					        	    	System.out.println("Inside 2");
					        	    	
					        	    	int deliveryTime=1;
					        	       	String  u="",v="";
					        	    	
					        	       	try {
					        	        String  uDist= "SELECT userarea FROM userdetails where userId= ?";
					        	        PreparedStatement pu = con.prepareStatement(uDist);
					        	        pu.setString(1,userId);
					        	     
					        	        ResultSet resultSetpu = pu.executeQuery();
					        	        while (resultSetpu.next()) {
					        	        	
					        	            u=resultSetpu.getString(1);
					        	        }
					        	        String  vDist= "SELECT vendorarea FROM vendordetails where vendorid= ?";
					        	        PreparedStatement pv = con.prepareStatement(vDist);
					        	        pv.setString(1,selectedVendor);
					        	        
					        	        ResultSet resultSetpv = pv.executeQuery();
					        	        while (resultSetpv.next()) {
					        	        	
					        	            v=resultSetpv.getString(1);
					        	        }
					        	        System.out.println("u " + u+" v "+ v);
					        	     
					        	        String q = "SELECT distance FROM distancetable where location1 = ? and location2=?";
					        	        PreparedStatement p = con.prepareStatement(q);
					        	        p.setString(1,u);
					        	        p.setString(2,v);
					        	        ResultSet resultSet2 = p.executeQuery();
					        	        
					        	       
					        	        while (resultSet2.next()) {
					        	        	
					        	        	System.out.println("Insise while \n");
					        	        	System.out.println(resultSet2.getInt(1));
					        	        	
					        	        	deliveryTime=(int)resultSet2.getInt(1)*5;
					        	         }
					        	        
					        	        
					        	    	
					        	    	//Payment related code
					        	    	User u1=new User();
					        	    	u1.usr(userId,selectedVendor);

					        	    	int orderId=0;
					        	    	   String coupon = "SELECT orderid  FROM orderdetails  where userid= ? ORDER BY orderid DESC ,dateandtime DESC ";
					        	           PreparedStatement promo1 = con.prepareStatement(coupon);
					        	         promo1.setString(1,userId);
					        	           ResultSet resultSet3 = promo1.executeQuery();
					        	    	
					        	    	while(resultSet3.next()) {
					        	    		   
					        	    		orderId=  resultSet3.getInt(1);
					        	    	}
					        			
					        			System.out.println("Delivery time will be " + deliveryTime + " "+ "And your OrderID is " + orderId);
					        			
					        			
					        			tracking objTrack = new tracking();
					        			ordered objOrder = new ordered();
					        			objOrder.orderAssign(con, orderId, userId, deliveryTime);
					        			objTrack.orderTracking(objOrder,con);
					        			
					        	       	}
					        			catch(Exception e)
					        	       	{
					        				System.out.println("Exception in foodwise.java in payment option:"+e.getMessage());
					        	       	}
					        	 	
					        	    }
					        	        	    
					        		}
					        		else if (userOption == 2){//Query how to do or necessary to do or not???????????????????s
					        			// code for wishlist
					        		}	
					        		
					        	}
					        	else
					        	{
						        // the following statement is used to log any messages  
						        logger.info("Error Message in retrieving items from restaurants in foodwise.java :"+sErrorMsg);  
					        	}
					        }
							catch(Exception e)
					        {		
								logger.info("Exception Message in retrieving items from restaurants i foodwise.java :"+ e.getMessage());	
					        }
					 			 
			 }
		
		}
		
}