package oopdProject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Time;


/**
 * @brief ordered class creates order object to fill details in order details table after tracking 

 */
public class ordered {

int orderid;
String	userid;
String orderAccepted;
Time	expectedDeliveryTime;
Time	actualDeliveryTime;
String	vendorid;
int	customerRating;
int	appcustomerrating;
int cookingmin;	
int deliverymin;	//need to be fill


/**
 * @brief orderAssign function add order details to the orderdetails table
 * @return void
 * @param Connection object, orderId, userid, deliveryTime 

 */
public void orderAssign(Connection con, int orderId ,String userid,int deliveryTime) {
	
	try {
	String sQuery1 = "select orderid,userid,t1.vendorid,t2.vendorCookingtime from orderdetails t1 inner join vendordetails t2 on t1.vendorid = t2.vendorid "
			+ "where orderid='"+orderId+"' and userid='"+userid+"'";   
    String sErrorMsg1 = "";
    ResultSet resultSet1 = SQLConnection.getResultSet(con, sQuery1, sErrorMsg1); 	
    if(sErrorMsg1.isBlank()) {
		while (resultSet1.next()) {
			this.orderid = Integer.parseInt(resultSet1.getString(1));   		
			this.userid = resultSet1.getString(2);
			this.vendorid = resultSet1.getString(3);
			this.cookingmin = Integer.parseInt(resultSet1.getString(4));
			this.deliverymin = deliveryTime;
			
		}
	  }
    
	}
    catch(Exception e)
    {
    	System.out.println(e.getMessage()); 
    }
 
}
	
}
