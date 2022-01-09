package oopdProject;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Scanner;

/**
 * @brief Tracking class to track the order.
 * 		  

 */
public class tracking {
	
	/**
	 * @brief orderTracking function tracks the order after successful payment.
	 * @param order object and connection object
	 * @return boolean
	 * 		  

	 */
	public boolean orderTracking(ordered objOrder,Connection con) {
	
	  Calendar currentTimeNow = Calendar.getInstance();
	  Calendar currentTimeNow1 = Calendar.getInstance();
	  Calendar currentTimeNow2 = Calendar.getInstance();
	  System.out.println("Current time now : " + currentTimeNow.getTime());
	  
	  int expectedMin = objOrder.cookingmin + objOrder.deliverymin;
	  int maximumTime = (int) Math.ceil(1.1 * objOrder.deliverymin);
	  int actualMin = (int)(Math.random()*100);	  
	  
	  currentTimeNow.add(Calendar.MINUTE, expectedMin);
	  currentTimeNow1.add(Calendar.MINUTE, maximumTime);
	  currentTimeNow2.add(Calendar.MINUTE, actualMin);
	  
	  java.sql.Timestamp sqlDate1 = new java.sql.Timestamp(currentTimeNow1.getTimeInMillis());
	  java.sql.Timestamp sqlDate2 = new java.sql.Timestamp(currentTimeNow2.getTimeInMillis());
	  
	  
	  
	  Scanner sc = new Scanner(System.in);
	  boolean isOrderAccepted = true;
	  if(actualMin > maximumTime)
	  {     
		  System.out.println("Since your delivery reached more than than the expected time. We are very sorry for the inconvenience.");
		  
		  boolean bflag = true;
		  while(bflag) {
		  System.out.println("Do you want to cancel the order?\nPress 1. Cancel Order 2. Receive Order");
		  int option = sc.nextInt();
		  
		  if(option == 1)
		  {
			  System.out.println("You cancel the order successfully");
			  isOrderAccepted = false; bflag = false;
			  objOrder.appcustomerrating = objOrder.customerRating = 0;
			  objOrder.orderAccepted = "Rejected";	
			  //Updating the order details in DB
			  String sQuery = "update orderdetails set expectedDeliveryTime= CONVERT(DATETIME, '"+ sqlDate1 +"'), actualDeliveryTime = CONVERT(DATETIME,'"+
					  sqlDate2 +"'), customerRating='"+ 0 +"',appcustomerrating='"+ 0 +"',[accepted/rejected] = 'rejected' where orderid='"+objOrder.orderid+"'";
			  
			  String sError ="" ;	
			  try { 	
		  	  Statement statement = con.createStatement();
		  	  int ans = statement.executeUpdate(sQuery);
		  	  if(ans ==1)
		  		  System.out.println("update successful");
		  	  else
		  		  System.out.println("update fail");
			  }
			  catch (Exception e)
			  {
				  sError = e.getMessage();
				  System.out.println(sError);
			  }
			  	  
			  return false;
		  }
		  else if(option == 2)
		  {   
			  System.out.println("Thank you for trusting us and not cancelling the order.");
			  bflag = false;
		  }
		  else {
			  System.out.println("You might have entered wrong key.Please try it once again.");
		  	}
		  }
	  }
	  else
	  {
		  System.out.println("Your delivery reached in expected time. We hope you are happy from our service.");
	  }
	  
	  if(isOrderAccepted){
		  System.out.println("Thank you for accepting the order");
		  boolean bflag = true, bflag1 = true;
		  while(bflag) {
		  System.out.println("You are requested to kindly RATE the APP EXPERIENCE on the scale of 1-5. Enter your Rating");
		  objOrder.appcustomerrating = sc.nextInt();
		  if(objOrder.appcustomerrating > 5 || objOrder.appcustomerrating < 1)
		  	{
			  System.out.println("You provide rating outside the range. Kindly provide within the range.");
		  	}
		  else
		  	{bflag = false;} 
		  }
		  
		  while(bflag1) {
		  System.out.println("You are requested to kindly RATE the FOOD EXPERIENCE on the scale of 1-5. Enter your Rating");
		  objOrder.customerRating = sc.nextInt();
		  if(objOrder.customerRating > 5 || objOrder.customerRating < 1)
		  	{
			  System.out.println("You provide rating outside the range. Kindly provide within the range.");
		  	}
		  else
		  	{bflag1 = false;} 
		  
		  }
		  objOrder.orderAccepted = "accepted";
		  
		  // //Updating the order details in DB
		  String sQuery = "update orderdetails set expectedDeliveryTime= CONVERT(DATETIME,'"+ sqlDate1 +"'), actualDeliveryTime = CONVERT(DATETIME, '"+
				  sqlDate2 +"'), customerRating='"+ objOrder.customerRating +"',appcustomerrating='"+ objOrder.appcustomerrating +"',[accepted/rejected] = '"+
				  objOrder.orderAccepted+"'  where orderid='"+objOrder.orderid +"'";
		  
		  String sError ="" ;	
		  try { 	
	  	  Statement statement = con.createStatement();
	  	  int ans = statement.executeUpdate(sQuery);
	  	  if(ans ==1)
	  		  System.out.println("update successful");
	  	  else
	  		  System.out.println("update fail");
		  }
		  catch (Exception e)
		  {
			  sError = e.getMessage();
			  System.out.println(sError);
		  }
	  }
	  
	  return true;
	  
}
	
	
}
