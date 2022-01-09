package oopdProject;

/**
 * @brief Vendor class to store vendor details and have their getter and setters.
 * 		  

 */
public class Vendor {

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
