//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\nedss\\NEDSSBusinessServicesLayer\\CDM\\MPRUpdateEngine\\MPRUpdateEngineDesign\\updatehandler\\SingleUpdateField.java

package gov.cdc.nedss.systemservice.mprupdateengine;



/**
 *
 * <p>Title: SingleUpdateField</p>
 * <p>Description: This is a helper class that defines the data structure needed for updating MPR
fields with single values.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author not attributable
 * @version 1.0
 */
class SingleUpdateField
{
   private String fieldName;
   private String AODFieldName;
   private String sourceID;
   private String AODSourceID;


   /**
    * Returns the name for the as-of-date field.
    * @return String
    */
   public String getAODFieldName()
   {
		return AODFieldName;
   }


   /**
    * Returns the name for the field.
    * @return String
    */
   public String getFieldName()
   {
		return fieldName;
   }


   /**
    * Sets the as-of-date field name.
    * @param aODMethodName
    */
   public void setAODFieldName(String aODMethodName)
   {
		AODFieldName = aODMethodName;
   }


   /**
    * Sets the field name.
    * @param methodName
    */
   public void setFieldName(String methodName)
   {
		this.fieldName = methodName;
   }


   /**
    * Returns the method name for the field getter.
    * @return String
    */
   public String fieldGetMethodName()
   {
		return "get" + fieldName;
   }


   /**
    * Returns the method name for the field setter.
    * @return String
    */
   public String fieldSetMethodName()
   {
		return "set" + fieldName;
   }


   /**
    * Returns the method name for the as-of-date field getter.
    * @return String
    */
   public String aODFieldGetMethodName()
   {
		return "get" + AODFieldName;
   }


   /**
    * Returns the method name for the as-of-date field setter.
    * @return String
    */
   public String aODFieldSetMethodName()
   {
		return "set" + AODFieldName;
   }

   /**
    * Returns the source ID for the field to be updated.
    * @return String
    */
public String getSourceID() {
	return sourceID;
}


/**
 * Sets the source ID for the field to be updated.
 * @param sourceID
 */
public void setSourceID(String sourceID) {
	this.sourceID = sourceID;
}


/**
 * Returns the source ID for the as-of-date field.
 * @return String
 */
public String getAODSourceID() {
	return AODSourceID;
}


/**
 * Sets the source ID for the as-of-date field.
 * @param aODSourceID
 */
public void setAODSourceID(String aODSourceID) {
	AODSourceID = aODSourceID;
}

}
