package gov.cdc.nedss.systemservice.mprupdateengine;
/**
 * <p>Title: MultiUpdateFieldElement</p>
 * <p>Description: This is a helper class that defines the data structure
 * that contains information about a sub-field of a multi-value MPR field
 * to be updated.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender
 * @version 1.0
 */

class MultiUpdateFieldElement {

	private String fieldName;
	private String containerFieldName;

       /**
        * Returns the method name for the container field getter.
        * @return String
        */
	public String getContainerFieldName() {
		return containerFieldName;
	}
       /**
        * Returns the method name for the container field setter.
        * @return String
        */
	public String getFieldName() {
		return fieldName;
	}
       /**
        * This method initializes the MPR update handler with an Action composite.
        * The Action composite contains all the actions available to the update handler.
        * @param actions
        * @return void
        * @roseuid 3E49146000BB
        */
	/**
	Sets the container field name.
	 */
	public void setContainerFieldName(String containerFieldName) {
		this.containerFieldName = containerFieldName;
	}

	/**
	Sets the field name.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


   /**
   Returns the method name for the field getter.
    */
   public String fieldGetMethodName()
   {
		return "get" + fieldName;
   }

   /**
   Returns the method name for the field setter.
    */
   public String fieldSetMethodName()
   {
		return "set" + fieldName;
   }
   /**
   Returns the method name for the container field getter.
    */
   public String containerFieldGetMethodName()
   {
   	if(containerFieldName == null) {
   		return null;
   	}
		return "get" + containerFieldName;
   }

   /**
   Returns the method name for the container field setter.
    */
   public String containerFieldSetMethodName()
   {
   	if(containerFieldName == null) {
   		return null;
   	}
		return "set" + containerFieldName;
   }
}
