package oopdProject;

/**
 * @brief userinfo class to store user details and have their getter and setters.
 * 		  

 */
public class userinfo {
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
