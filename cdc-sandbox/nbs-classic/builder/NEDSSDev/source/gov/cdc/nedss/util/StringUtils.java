/**
 * Title: String utility class.
     * Description: Some string utility methods, which are not part of standard Java.
 * Same is also used by ResultSetUtils.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author sdesai
 * @version 1.0
 */

package gov.cdc.nedss.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import gov.cdc.nedss.exception.NEDSSSystemException;

public class StringUtils {

   //For logging
   static final LogUtils logger = new LogUtils(StringUtils.class.getName());

   private static final String GET_PREFIX = "get";
   private static final String SET_PREFIX = "set";
   //private static final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM/dd/yyyy");
   
   public static void main ( String[] args){
	   StringUtils su = new StringUtils();
	   Timestamp abc = StringUtils.stringToStrutsTimestamp(StringUtils
				.formatDate(new Timestamp((new Date()).getTime())));
	   System.out.println(abc.toString());
	   String[] pa = {"1"};
	   String result = ("" + Arrays.asList(pa)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'" );
	   System.out.print(result);
   }
   /**
    * Parse a String into a BigDecimal
    * @param value The string value of a number
    * @return A new BigDecimal or null if the value can not be parsed.
    */
   public BigDecimal parseBigDecimal(String value) {
      try {
         return new BigDecimal(value);
      }
      catch (NumberFormatException nfe) {
         logger.fatal("Value string could not be parsed: " + value, nfe);
      }
      return null;
   }

   /**
    * Parse a percentage String into a BigDecimal value. The percentage is converted into a decimal value.
    * @param value The string value of a percentage
    * @return A new BigDecimal or null if the value can not be parsed.
    */
   public BigDecimal parseBigDecimalFromPercentage(String value) {
      BigDecimal bd = parseBigDecimal(value);
      if (bd == null) {
         return bd;
      }
      return bd.movePointLeft(2).setScale(5, BigDecimal.ROUND_HALF_UP);
   }

   /**
    * Parse a String into a Timestamp. String format must be dd.MMM.yyyy
    * @param dateStr A String in the format dd.MMM.yyyy to be converted to a Timestamp
    * @return The new Timestamp or null if the dateStr can not be parsed.
    */
   public Timestamp parseTimestamp(String dateStr) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MMM.yyyy");
      try {
         java.util.Date d = sdf.parse(dateStr, new ParsePosition(0));
         if (d != null) {
            return new Timestamp(d.getTime());
         }
      }
      catch (Exception e) {
         logger.fatal("Date string could not be parsed: " + dateStr, e);
      }
      return null;
   }

   /**
    * Parse a String into a Timestamp with given format
    * @param dateStr A String in proper format
    * @param format from which to convert to a Timestamp
    * @return The new Timestamp or null if the dateStr can not be parsed.
    */
   public Timestamp parseTimestamp(String dateStr, String format) {
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      //try {
         java.util.Date d = sdf.parse(dateStr, new ParsePosition(0));
         //if (d != null) {
         String s = formatDate(new Timestamp(d.getTime()));
         //System.out.println(s);
         return new Timestamp(d.getTime());
         //}
      //}
      //catch (Exception e) {
         //logger.fatal("Date string could not be parsed: " + dateStr);
      //}
      //return null;
   }

   /**
    * Replaces all occurences of a char with a string.
    * @param str The string that needs a char replaced.
    * @param replace The character to replace.
    * @param with The string to replace the character with.
    */
   public String replace(String str, char replace, String with) {

      if (str == null || (str.indexOf(replace) < 0)) {
         return str;
      }

      String replaceCharAsString = String.valueOf(replace);
      StringTokenizer st = new StringTokenizer(str, replaceCharAsString, true);
      StringBuffer sb = new StringBuffer();
      while (st.hasMoreTokens()) {
         String token = st.nextToken();
         if (token.equals(replaceCharAsString)) {
            sb.append(with);
         }
         else {
            sb.append(token);
         }
      }
      return sb.toString();
   }

   /**
    * Return a string that is formatted in USD currency format without the dollar sign.
    *
    * @param   amount  the amount that needs to be formatted.
    * @return  the string in USD currency format
    *
    */
   public String toCurrencyString(double amount) {
      return toCurrencyString(new BigDecimal(amount));
   }

   /**
    * Return a string that is formatted in USD currency format without the dollar sign.
    *
    * @param       amount  the amount that needs to be formatted.
    * @return  the string in USD currency format
    *
    */
   public String toCurrencyString(BigDecimal amount) {
      NumberFormat formatter = NumberFormat.getInstance();
      if (formatter instanceof DecimalFormat) {
          ( (DecimalFormat) formatter).setDecimalSeparatorAlwaysShown(true);
      }
       ( (DecimalFormat) formatter).setGroupingSize(3); formatter.
          setMaximumFractionDigits(2);
      formatter.setMinimumFractionDigits(2);
      return formatter.format(amount == null ? 0 :
                              (amount.setScale(2, BigDecimal.ROUND_UP).
                               floatValue()));
   }

   /**
        * Get the correctly formatted string for the object type to display in HTML.
    * If the object is null, return the alternate string.
    * @param obj the obj to display as a string
    * @return The string representation of the object, or alternate if null
    */
   public String toHTMLString(Object obj, String alt) {
      if (obj == null) { //Null? return alt
         return alt;
      }
      if (obj instanceof String) { //String obj
         String strObj = (String) obj;
         if (strObj.trim().equals("")) { //Empty string? return alt
            return alt;
         }
         else if (strObj.length() == 1) { //Single char? check for yes no
            char c = Character.toUpperCase(strObj.charAt(0));
            if (c == 'Y') {
               return "Yes";
            }
            if (c == 'N') {
               return "No";
            }
         }
         return strObj; //If none of the above, just return the string
      }
      if (obj instanceof java.util.Date) { //Date? format appropriately
         SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MMM.yyyy");
         return dateFormatter.format( (java.util.Date) obj);
      }
      return obj.toString(); //If none of the above, return toString()
   }

   /**
    * Get the correctly formatted string for the object type to display in HTML
    * @param obj The object to display as a string.
    * @return The string representation of the object or &quot;&amp;nbsp;&quot; if the object is null or equal to an empty string.
    */
   public String toHTMLString(Object obj) {
      if (obj == null ||
          (obj instanceof String && ( (String) obj).trim().equals(""))) {
         return "&nbsp;";
      }
      if (obj instanceof java.util.Date) {
         SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MMM.yyyy");
         return dateFormatter.format( (java.util.Date) obj);
      }
      if (obj instanceof String && ( (String) obj).length() == 1) {
         char c = Character.toUpperCase( ( (String) obj).charAt(0));
         if (c == 'Y') {
            return "Yes";
         }
         if (c == 'N') {
            return "No";
         }
      }
      return obj.toString();
   }

   /**
    *
    * @param db A BigDecimal.
    * @return The String of the value as a percentage, or an empty String if the argument is null;
    */
   public String toPercentage(BigDecimal bd) {
      if (bd == null) {
         return "";
      }
      return bd.movePointRight(2).setScale(3, BigDecimal.ROUND_HALF_UP).
          toString();
   }

   /**
    * Get the correctly formatted string for the object type to display in HTML
    * @param str The string to decapitalize.
    * @return The string with all but the first character changed to lowercase or &quot;&amp;nbsp;&quot; if the object is null or equal to an empty string.
    *
    */
   public String toStdCapHTMLString(String str) {
      if (str == null || (str.trim().equals(""))) {
         return "&nbsp;";
      }
      return str.charAt(0) + str.substring(1).toLowerCase();
   }

   /**
    * Format a Date for display in a text box; Returns "" if Date is null
    * @param date The Date to display in DD.Mmm.YYYY format.
    */
   public String toTextDateString(java.util.Date date) {

      if (date == null) {
         return "";
      }
      SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MMM.yyyy");
      return dateFormatter.format(date);
   }

   /**
    * Ensures null isn't printed in text areas
    * @param obj
    * @return obj.toString() or empty string if the object is null.
    */
   public String toTextString(Object obj) {
      if (obj == null) {
         return "";
      }
      return obj.toString();
   }

   // Methods to create variables and corresponding getter and setter method names.

   public String getterMethodName(String name) {
      return GET_PREFIX + toMethodName(name);
   }

   public String setterMethodName(String name) {
      return SET_PREFIX + toMethodName(name);
   }

   public String toMethodName(String name) {
      String methodName = "";
      StringTokenizer tokenizer = new StringTokenizer(name.toLowerCase(), "_");
      while (tokenizer.hasMoreTokens()) {
         String token = tokenizer.nextToken();
         methodName = methodName + Character.toUpperCase(token.charAt(0)) +
                      token.substring(1);
      }
      return methodName;
   }

   public String toVariableName(String name) {
      String methodName = toMethodName(name);
      return Character.toLowerCase(methodName.charAt(0)) +
          methodName.substring(1);
   }

   public String stringToHtmlForCombo(Map<?,?> optionValues, String selected) {
      String str = stringToHtmlForComboNoBlank(optionValues, selected);
      if (selected == null) {
         return str + "<Option selected value=\"  \" >    "; // spaces
      }
      else {
         return str + "<Option value=\"  \" >    "; // spaces
      }

   }

   public String stringToHtmlForComboNoBlank(Map<?,?> optionValues, String selected) {
      try {
         if (optionValues == null || optionValues.size() == 0) {
            return null;
         }
         StringBuffer sb = new StringBuffer();
         Set<?> set = optionValues.keySet();
        Iterator<?>  itr = set.iterator();
         while (itr.hasNext()) {
            String key = (String) itr.next();
            String value = (String) optionValues.get(key);
            sb.append(" <Option ");
            if (selected != null) {
               if (key.equalsIgnoreCase(selected)) {
                  sb.append(" selected ");
               }
            }
            sb.append(" value=");
            sb.append("\"");
            sb.append(key.trim());
            sb.append("\"");
            sb.append(">");
            sb.append(value.trim());
         }
         return sb.toString();
      }
      catch (Exception e) {
         logger.fatal("", e);
      }
      return null;
   }

   /**
    * Parse a String into a Timestamp. String format must be dd.MMM.yyyy
    * @param dateStr A String in the format dd.MMM.yyyy to be converted to a Timestamp
    * @return The new Timestamp or null if the dateStr can not be parsed.
    */
   public Timestamp convertTimestamp(String dateStr) throws Exception {
      SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
 //     try {
         java.util.Date d = sdf.parse(dateStr, new ParsePosition(0));
         if (d != null) {
            return new Timestamp(d.getTime());
         }
         else
            throw new Exception("Can not convert " + dateStr +"  to date");
 //     }
//      catch (Exception e) {
//         logger.fatal("Date string could not be parsed: " + dateStr, e);
//      }
     // return null;
   }

   /**
    * Parse a String into an Integer.
    * @param str A String to be converted to a Integer
    * @return The new Integer or null if the str can not be parsed.
    */
   public Integer stringToInteger(String str) {
 //     try {
         return new Integer(str);
 //     }
 //     catch (Exception ex) {
 //        logger.fatal("The string could not be converted: " + str, ex);
 //     }
 //     return null;
   }

    /**
     * Parse a String into an Integer.
     * @param inString A String to be converted to a Integer
     * @return The new Integer or null if the str can not be parsed.
     */
    public Boolean stringToBoolean(String inString)
    {
        if(inString.equals("true")) return new Boolean(true);
        if(inString.equals("false")) return new Boolean(false);

        return new Boolean(false);
    }

   /**
    * Parse a String into a Long.
    * @param str A String to be converted to a Long
    * @return The new Long or null if the str can not be parsed.
    */
   public Long stringToLong(String str) {
 //     try {
         return new Long(str);
 //     }
 //     catch (Exception ex) {
 //        logger.fatal("The string could not be converted: " + str, ex);
 //     }
 //     return null;
   }

    /**
     * Parse a String into a Long.
     * @param str A String to be converted to a Long
     * @return The new Long or null if the str can not be parsed.
     */
    public Double stringToDouble(String str) {
     //  try {
          return new Double(str);
    //   }
    //   catch (Exception ex) {
    //      logger.fatal("The string could not be converted: " + str, ex);
    //   }
    //   return null;
    }

   public static java.sql.Timestamp stringToStrutsTimestamp(String strTime) {

      String input = strTime;
      java.util.Date t;
      try {
    	  java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM/dd/yyyy");
         if (input != null && input.trim().length() > 0) {
            t = formatter.parse(input);
            logger.debug(t);
            java.sql.Timestamp ts = new java.sql.Timestamp(t.getTime());
            return ts;
         }
         else {
            return null;
         }
      }
      catch (Exception e) {
         logger.info("string could not be parsed into time");
         return null;
      }
   }

   public static String formatDate(Date date) {
	   java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM/dd/yyyy");
      if (date == null) {
         return new String("");
      }
      else {
         return formatter.format(date);
      }

   }

   public static String formatDate(java.sql.Timestamp timestamp) {
      java.util.Date date = null;
      java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM/dd/yyyy");
      if (timestamp != null) {
         date = new java.util.Date(timestamp.getTime());

      }
      if (date == null) {
         return new String("");
      }
      else {
         return formatter.format(date);
      }

   }

   /**
    * DescriptionForCode returns description text for a given code and parsed string of codes and
    * descriptions
    * @param String parsed string of codes and descriptions
    * @param String code that needs to be translated
    * @return : Description
    */
   public static String DescriptionForCode(String parsedString, String code) {
      String description = null;
      if (parsedString != null && code != null) {
         StringTokenizer stCodeDesc = new StringTokenizer(parsedString, NEDSSConstants.SRT_LINE);
         while(stCodeDesc.hasMoreElements()){
            StringTokenizer stNV = new StringTokenizer((String)stCodeDesc.nextElement(), NEDSSConstants.SRT_PART);
            String sCode = new String(stNV.nextToken());
            String sDesc = new String(stNV.nextToken());
            if(sCode.equals(code.trim())){
               description = sDesc;
               break;
            }
         }
      }
      return description;
   }

   /**
    * replaceNull takes any wrapper object like Integer, Long, Double, String and return a string
    * if the object is null it returns empty string this is specially used in formation of long strings
    * for XSP tables, manage obesrvation, file, my investigations
    * @param Object nullObj takes any wrapper object, like Interger, Long, Double, String
    * @return : String
    */
   public static String replaceNull(Object nullObj)
   {
      String returnStr = "";
     if(nullObj instanceof String)
     {
       returnStr = (nullObj == null) ? "" : (String) nullObj;
     }
     else if(nullObj instanceof Long)
     {
       if(nullObj == null)
         returnStr = "";
       else
         returnStr = ((Long)nullObj).toString();
     }
     else if(nullObj instanceof Double)
     {
       if(nullObj == null)
         returnStr = "";
       else
         returnStr = ((Double)nullObj).toString();
     }
     else if(nullObj instanceof Integer)
     {
       if(nullObj == null)
         returnStr = "";
       else
         returnStr = ((Integer)nullObj).toString();
     }

     else
       returnStr = "";

       return returnStr;

   }
   
   public static String formatDatewithHrMinSec(java.sql.Timestamp timestamp) {
	      java.util.Date date = null;
	      if (timestamp != null) {
	         date = new java.util.Date(timestamp.getTime());

	      }
	       final java.text.SimpleDateFormat formatterHrMinSec = new java.text.
	       SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
	      if (date == null) {
	         return new String("");
	      }
	      else {
	         return formatterHrMinSec.format(date);
	      }

	   }
   public static String formatDatewithHrMin(java.sql.Timestamp timestamp) {
	      java.util.Date date = null;
	      if (timestamp != null) {
	         date = new java.util.Date(timestamp.getTime());
	      }
	       final java.text.SimpleDateFormat formatterHrMin = new java.text.
	       SimpleDateFormat("h:mm a");
	      if (date == null) {
	         return new String("");
	      }
	      else {
	         return formatterHrMin.format(date);
	      }

	   }
   
   public static Date formatStringToDatewithHrMin(String date, String hrMin) throws ParseException {
	   DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm");
	   String datetimeString = date;
	   if(hrMin == null || hrMin.equals(""))
		   datetimeString = datetimeString + " " + "00:00";
	   else
		   datetimeString = datetimeString + " " + hrMin;
	   
	   return df.parse(datetimeString);
	   }
   public static String blobToString(Blob blob) throws NEDSSSystemException{
	   String data = "";
	   if(blob!=null){
		   byte[] bdata = null;
		try {
			 bdata = blob.getBytes(1, (int)blob.length());
			   data = new String(bdata);
	    }catch (Exception ex) {
	        logger.error("Exception while converting blob to string : " + ex.toString());
	        throw new NEDSSSystemException(ex.getMessage());
	    }			
	    return data;
		 	
	}else 
		return null;
   }

   public static String clobToString(Clob data) throws NEDSSSystemException{
		StringBuilder sb = new StringBuilder();
		try {
			Reader reader = data.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);
			String line;
			while (null != (line = br.readLine())) {
				sb.append(line);
			}
			br.close();
		} catch (SQLException e) {
			 logger.error("SQLException while converting clob to string : " + e.toString());
			 throw new NEDSSSystemException(e.getMessage());
		} catch (IOException e) {
	        logger.error("IOException while converting clob to string : " + e.toString());
	        throw new NEDSSSystemException(e.getMessage());
		}
		return sb.toString();
	}
    
    public static String combine(String[] arStr, String comb)
    {
        StringBuilder sb = new StringBuilder();
        boolean apend = false;

        for (int i = 0; i < arStr.length; i++)
        {
            if (arStr[i] != null && arStr[i].trim().length() > 0)
            {
                if (apend)
                {
                    sb.append(comb);
                }
                sb.append(arStr[i]);
                apend = true;
            }
        }
        return sb.toString();
    }
    
    public static boolean isEmpty(String str)
    {
        if( str != null && str.trim().length() > 0 )
            return false;
        else
            return true;
    }

	/**
	 * Based on org.apache.commons.lang.StringEscapeUtils.java 's escapeSql method (Apache Commons-Lang library)
	 * @param inputStr
	 * @return String
	 */
	public static String escapeSql(String inputStr) {
		return inputStr!=null?inputStr.replaceAll("'", "''"):inputStr;
	}
}