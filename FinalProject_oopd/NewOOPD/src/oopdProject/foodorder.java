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

/**
 * @brief Payment class used for making payment of the food item

 */

class Order {

/**
* @brief Array list is created to add item name and item quantity from the cartDetails table 

*/

 ArrayList<Object> arrlist = new ArrayList<Object>();
 ArrayList<Integer> priceArr=new ArrayList<Integer>();
 String userId;
String RestaurantId;
   
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
 
public  void viewCart(String uId, String selectedVendor) throws ClassNotFoundException, SQLException  {
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
    String connectionUrl = "jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true";
    Connection con = DriverManager.getConnection(connectionUrl);

 userId=uId;
 RestaurantId=selectedVendor;
 
 String query = "SELECT itemNameSelected,itemQtySelected FROM cartDetails where vendorIdSelected = ?";
 PreparedStatement pstmt = con.prepareStatement(query);
 pstmt.setString(1,RestaurantId);
 ResultSet resultSet11 = pstmt.executeQuery();

//resultSet = statement.executeQuery(selectSql);

 
 
 while (resultSet11.next()) {
    arrlist.add(resultSet11.getString(1));
    arrlist.add(resultSet11.getInt(2));
 }

}

public static void viewWishlist() {
   
}
}

/**
 * @brief User class used for calculating total bill after adding items in the array list 
 
 */

class User extends Order {
   
    String userEmail;
    String userContact;
   
    
    
    double total_bill;
    String[] promo = new String[2];
//    String userLocation="south delhi";
    String name;
    int quantity;
    int size1;
    ArrayList<Object> arrlist1=new ArrayList<Object>();
    User() {}

    User(String itemName,int quantity){
   
    this.name=itemName;
    this.quantity=quantity;
   
   
    }
 
    Scanner sc=new Scanner(System.in);  
    /**
   	 * @brief This function is for getting values from array list and calculating total bill and applying coupons
   	 * @return void
   	 */
    
    public  void usr (String uId,String RId) throws ClassNotFoundException, SQLException {
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
    String connectionUrl = "jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true";
    Connection con = DriverManager.getConnection(connectionUrl);  
   userId=uId;
   RestaurantId=RId;
    ResultSet resultSet = null;
   
    /**
     * @brief Order object to access the the view cart from Order class
     
     */
    Order o=new Order();
    o.viewCart(userId,RestaurantId);
   
     arrlist1 =o.arrlist;
   

   int Base_Price=0;
   
   for(int i=0;i<arrlist1.size();i++) {
       
	   System.out.print(" "+ arrlist1.get(i));
   }  
   
   
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
        	
        	System.out.println("Item price "+resultSet1.getInt(1));
        	
        	 System.out.println("Name " +(String) arrlist1.get(i)+" Quantity "+arrlist1.get(i+1));
        	 
        	priceArr.add(resultSet1.getInt(1));
           Base_Price+=resultSet1.getInt(1)*(int)arrlist1.get(i+1);
           
        //   System.out.println(Base_Price);
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
       	String  uD="",vD="";
    	
        String  uDist= "SELECT userarea FROM userdetails where userId= ?";
        PreparedStatement pu = con.prepareStatement(uDist);
        pu.setString(1,userId);
     
        ResultSet resultSetpu = pu.executeQuery();
        while (resultSetpu.next()) {
        	
            uD=resultSetpu.getString(1);
        }
        String  vDist= "SELECT vendorarea FROM vendordetails where vendorid= ?";
        PreparedStatement pv = con.prepareStatement(vDist);
        pv.setString(1,RestaurantId);
        
        ResultSet resultSetpv = pv.executeQuery();
        while (resultSetpv.next()) {
        	
            vD=resultSetpv.getString(1);
        }
 //       System.out.println("Restaurant ID is " + RestaurantId );
  //      System.out.println(" uD "+ uD +" vD "+ vD +"User ID" + userId);
        
        String q = "SELECT distance FROM distancetable where location1 = ? and location2=?";
        PreparedStatement p = con.prepareStatement(q);
        p.setString(1,uD);
        p.setString(2,vD);
        ResultSet resultSettp = p.executeQuery();
        
        while (resultSettp.next()) {
        	System.out.println(resultSettp.getInt(1));
            total_bill+=(int) resultSettp.getInt(1)*8;
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
    
    /**
	 * @brief Payment Class object is invoked to make payment after calculating total bill
	 
	 */
    Payment py=new Payment();
    py.getDetails(userId,arrlist1,priceArr,RestaurantId,(float) total_bill);
 
    }
   
   
}
    
   
    /**
   	 * @brief These functions helps to achieve Polymorphism and applying discount coupons
   	 * @return void
   	 */
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
      System.out.print(userId);
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

/**
* @brief Payment Class used for Payment

*/
class Payment extends Order{

long CardNumber;
int CVV;
String ExpiryMonth="",ExpiryYear="";
String UPI;






/**
* @brief This function checks if the entered details by user for payment is valid or not
* by checking in the database
* @return void
*/

public void getDetails(String uId,ArrayList<Object> arrlist1,ArrayList<Integer> priceArr1, String vId,float tb) throws ClassNotFoundException, SQLException {
	//User u2=new User();
	
Scanner sc = new Scanner(System.in);
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
   String connectionUrl = "jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true";
   Connection con = DriverManager.getConnection(connectionUrl);  
   
   userId=uId;
   
   
   ResultSet resultSet2 = null;
System.out.println("Select the option for payment");
System.out.println("1. By Credit or Debit Card");
System.out.println("2. By UPI ID");
   int number;
   
   number=sc.nextInt();
 
   int si=arrlist1.size();
   for(int i=priceArr1.size();i<8;i++)
	   priceArr1.add(0);
   
   
   for(int i=si;i<16;i++) {
	   if(i%2==0)
	   arrlist1.add("none");
	   else
	   arrlist1.add(0);
	   
//   priceArr1.add(0);
   }


   
   
   switch(number){  
   //Case statements  
   case 1:
   
     
//         System.out.println("Enter your Aadhar Number");
    System.out.println("Enter Card Details");
    CardNumber=sc.nextLong();
    System.out.println("Enter Expiry month of the  card ");
    ExpiryMonth=sc.next();
    System.out.println("Enter Expiry year of the  card  ");
    ExpiryYear=sc.next();
    System.out.println("Enter CVV ");
    CVV=sc.nextInt();
   
   
   
//        ResultSet resultSet3 = null;
       
       try (Statement statement = con.createStatement();) {
       

           String query = "SELECT cardnumber,expiry_month,expiry_year,cvv FROM UserBankdetails where userId=? ";
   
           
           PreparedStatement pstmt2 = con.prepareStatement(query);
           pstmt2.setString(1,userId);
         
           resultSet2 = pstmt2.executeQuery();


               while (resultSet2.next()) {
                 
 
              if(resultSet2.getLong(1)==CardNumber && resultSet2.getString(2).equals(ExpiryMonth) && resultSet2.getString(3).equals(ExpiryYear) && resultSet2.getInt(4)==CVV ) {
           	      
            	  String up = "INSERT INTO orderdetails ( item1, itemqty1,itemprice1,item2, itemqty2,itemprice2,item3, itemqty3,itemprice3,"
            			  + "item4, itemqty4,itemprice4,item5, itemqty5,itemprice5,item6, itemqty6,itemprice6,"
            			  + "item7, itemqty7,itemprice7,item8, itemqty8,itemprice8, userid, vendorid, totalbill,paymentmode"
            			            + ") "
            			            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"; 
                 PreparedStatement q2 = con.prepareStatement(up);
                 q2.setString(25,userId);
                 q2.setString(26,vId);
                 q2.setFloat(27,tb);
                 q2.setString(28,"Card");
                 int t=0;
                 int s=1;
                 for(int k=0;k<16;k=k+2) {
                 q2.setString(s++,(String) arrlist1.get(k));  q2.setInt(s++,(int) arrlist1.get(k+1)); q2.setInt(s++,priceArr1.get(t++));
                 }
                
                 q2.executeUpdate();
                 
                 String que = "DELETE FROM cartDetails where userId = ?";
                 PreparedStatement pstmt = con.prepareStatement(que);
                 pstmt.setString(1,userId);
                 pstmt.executeUpdate();
                 
                  
                  
                  
                  
               
               
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


//    ResultSet resultSet3 = null;
   
   try (Statement statement = con.createStatement();) {
   

       String query = "SELECT upiid FROM UserBankdetails where userId=? ";

     //  System.out.print(userId); 
       PreparedStatement pstmt2 = con.prepareStatement(query);
       pstmt2.setString(1,userId);
     
       resultSet2 = pstmt2.executeQuery();

           while (resultSet2.next()) {
             
            if(resultSet2.getString(1).equals(UPI)) {
            	//System.out.print(userId);
         
            	  String up = "INSERT INTO orderdetails ( item1, itemqty1,itemprice1,item2, itemqty2,itemprice2,item3, itemqty3,itemprice3,"
            			  + "item4, itemqty4,itemprice4,item5, itemqty5,itemprice5,item6, itemqty6,itemprice6,"
            			  + "item7, itemqty7,itemprice7,item8, itemqty8,itemprice8, userid, vendorid, totalbill,paymentmode"
            			            + ") "
            			            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"; 
                 PreparedStatement q2 = con.prepareStatement(up);
                 q2.setString(25,userId);
                 q2.setString(26,vId);
                 q2.setFloat(27,tb);
                 q2.setString(28,"UPI");
                 int t=0;
                 int s=1;
                 for(int k=0;k<16;k=k+2) {
                 q2.setString(s++,(String) arrlist1.get(k));  q2.setInt(s++,(int) arrlist1.get(k+1)); q2.setInt(s++,priceArr1.get(t++));
                 }
                
                 q2.executeUpdate();
                 
                 String que = "DELETE FROM cartDetails where userId = ?";
                 PreparedStatement pstmt = con.prepareStatement(que);
                 pstmt.setString(1,userId);
                 pstmt.executeUpdate();
                 
           
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

}

}

/**
* @brief foodorder class used to start the project.

*/

public class foodorder {
public static void main(String args[]) throws ClassNotFoundException, SQLException {
String user1 = null;

Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
String connectionUrl ="jdbc:sqlserver://DESKTOP-CJI4JJ5;database=OOPD;integratedSecurity=true";
Connection con = DriverManager.getConnection(connectionUrl);  


ResultSet resultSet = null;
startProject sp=new startProject();
sp.main(args);


}
}