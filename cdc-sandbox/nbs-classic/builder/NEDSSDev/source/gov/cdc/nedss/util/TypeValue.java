/**
 * Title: TypeValue helper class.
 * Description: A helper class for setting up its properties
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.util;

public class TypeValue
{
    public String type;
    public String value1;
    public String value2;
    public String value3;
    public String value4;
    public String value5;

   /**
    * Sets the values for all TypeValue class properties.
    * @param type the new value of the type property
    * @param value1 the new value of the value1 property
    * @param value2 the new value of the value2 property
    * @param value3 the new value of the value3 property
    */
    public TypeValue(String type, String value1, String value2, String value3)
    {
      this.type = type;
      this.value1 = value1;
      this.value2 = value2;
      this.value3 = value3;
    }

   /**
    * Sets the values for both type and value1 properties.
    * @param type the new value of the type property
    * @param value1 the new value of the value1 property
    */
    public TypeValue(String type, String value1)
    {
      this.type = type;
      this.value1 = value1;
    }
    /**
    * Sets the values for both type, value1, value2 properties.
    * @param type the new value of the type property
    * @param value1 the new value of the value1 property
    */
    public TypeValue(String type, String value1, String value2)
    {
      this.type = type;
      this.value1 = value1;
      this.value2 = value2;
    }
    /**
    * Sets the values for all TypeValue class properties.
    * @param type the new value of the type property
    * @param value1 the new value of the value1 property
    * @param value2 the new value of the value2 property
    * @param value3 the new value of the value3 property
    */
    public TypeValue(String type, String value1, String value2, String value3,String value4,String value5)
    {
      this.type = type;
      this.value1 = value1;
      this.value2 = value2;
      this.value3 = value3;
      this.value4 = value4;
      this.value5 = value5;
    }


}