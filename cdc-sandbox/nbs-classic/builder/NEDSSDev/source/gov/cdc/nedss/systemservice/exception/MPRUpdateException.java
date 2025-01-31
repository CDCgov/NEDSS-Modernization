//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\gov\\cdc\\nedss\\systemservice\\mprupdateengine\\exception\\MPRUpdateException.java

package gov.cdc.nedss.systemservice.exception;


/**
This is the root exception class for MPR Update Engine component.
 */
public class MPRUpdateException extends Exception 
{
	private static final long serialVersionUID = 1L;
   /**
   This is a constructor for the class.
   @param e
    */
   public MPRUpdateException(String e) //NBS1.1 uses jdk1.3.1 
   {
		super(e);    
   }

   public MPRUpdateException(String e, Exception x) //NBS1.1 uses jdk1.3.1 
   {
		super(e, x);    
   }
   
   /**
   This is the default constructor for the class.
    */
   public MPRUpdateException() 
   {
		super();    
   }
}
