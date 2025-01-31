

/**
 * Title:
 * Description:  An attempt to go from objects -> standard out
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
package gov.cdc.nedss.util;

import java.util.*;
import java.lang.reflect.*;
import java.io.*;

public class VOTester {
  static final LogUtils logger = new LogUtils("VOTester");
      static private String GET = "get";
      static private String GETTHE = "getThe";
      static private String COLLECTION = "Collection";
      static private String _S = "_s";

  private VOTester(javax.servlet.http.HttpServletRequest request) {
  }

  public static synchronized void createReport(Object anObject, String desc)
  {
     // Excute only if logger level is info.
      if ( logger.getLogLevelName().equalsIgnoreCase("Info"))
      {

        try {
          logger.debug("Generating Proxy Report: " + desc);
          PrintStream oldOut = System.out;
          long timeStamp = (new Date()).getTime();
          Date start = new Date();
          VOTester.explore(anObject, "");
          Date end = new Date();
          System.setOut(oldOut);
          long difference = start.getTime() - end.getTime();
          logger.info("reflection took: " + new Long(difference));
        }
        catch (Exception e)
        {
	   logger.info("Error generating Proxy Report: " + e);
        }
      }
  }

  private static void explore(Object anObject, String strPath)
  {
    //logger.info("exploring: " +anObject.getClass().getName());
    Method[] meths = anObject.getClass().getMethods();

    //1. get all get**** methods with 0 parameters

    for (int i=0; i < meths.length; i++)
    {
       try {
	 Method method = meths[i];

	 if (method.getName().startsWith("isIt"))
	 {
	    Object returnVal = method.invoke(anObject, (Object[])null);
	    if (returnVal != null)
	      logger.info(strPath + "." + method.getName() + "=" + returnVal.toString());
	    continue;
	 }
/*
	 if (method.getName().equalsIgnoreCase("setItDirty"))
	 {
	    Object[] oParams = {new Boolean(false)};

	    Object returnVal = method.invoke(anObject, oParams );
	 }
*/
	 if (! method.getName().startsWith("get"))
	    continue;

	 if ( method.getParameterTypes().length > 0)
	    continue;

	 // if method returns a String, Int, Long, or Timestamp, convert to
	 //   String & output.
	 Class<?> retClass = method.getReturnType();

	 // does it return a String, Integer, Short, or Long?
	 if (retClass.getName().equalsIgnoreCase("java.lang.String") ||
	     retClass.getName().equalsIgnoreCase("java.lang.Integer") ||
	     retClass.getName().equalsIgnoreCase("java.lang.Short") ||
	     retClass.getName().equalsIgnoreCase("java.lang.Long"))
	 {
	    Object returnVal = method.invoke(anObject, (Object[])null);
	    if (returnVal != null)
	      logger.info(strPath + "." + convertMethodNameToStruts(method.getName()) + "=" + returnVal.toString());
	 }
	 // does it return a Timestamp?
	 else if (retClass.getName().equalsIgnoreCase("java.sql.Timestamp"))
	 {
	   // convert the timestamp to the format we display it.
	    Object returnVal = method.invoke(anObject, (Object[])null);
	    if (returnVal != null)
	      logger.info(strPath + "." + convertMethodNameToStruts(method.getName()) + "=" + returnVal.toString());
	 }
	 // if it returns an object of type ***DT or ***VO, explore it, creating a new tag.
	 else if ( (retClass.getName().endsWith("DT") || retClass.getName().endsWith("VO")) &&
			(! method.getName().endsWith("_s")) )
	  {
	      Object returnVal = method.invoke(anObject,(Object[])null);
	      if (returnVal == null)
		continue;
	      explore(returnVal, strPath + "." + convertMethodNameToStruts(method.getName()));
	  }

	else if (retClass.getName().equalsIgnoreCase("java.util.Collection"))
	{
	    Object returnVal = method.invoke(anObject, (Object[])null);
	    if (returnVal == null)
	      continue;
	    Collection<Object>  aCollection  = (java.util.Collection) returnVal;

	   Iterator<Object>  itor = aCollection.iterator();

	    int count = 0;
	    while (itor.hasNext())
	    {
	      Object exploreObj = itor.next();
	      explore(exploreObj, strPath + "." + convertCollectionMethodNameToStruts(method.getName()) + "[" + new Integer(count++) + "]");
	    }
	}
	}
	catch (Exception e)
	{
	  e.printStackTrace();
	}
      }
    }


    // get all get*** methods with a single parameter of type int.
    //   If it returns an object of type ***DT or ***VO, explore it one at a time,
    //   using the size of the collection
    //    (will need to create another method to get the size of a collection)
    //  getObjectDT_s(int i)  <-->  getObjectDTsize()

    /**
     * Given a method with name getAppleName(), returns the string appleName
     */
    private static String convertMethodNameToStruts(String strMethod)
    {

      // only handle a method that starts with get
      if (! strMethod.startsWith(GET))
	return strMethod;

      // or if the strMethod is not longer than the word get
      if (! (strMethod.length() > GET.length()))
	return strMethod;

      // return the modified string.
      return strMethod.substring(3, 4).toLowerCase() + strMethod.substring(4);
    }

    /**
     * Convert
     *
     * getTheEntityLocatorParticipationDTCollection
     *
     * to
     *
     * entityLocatorParticipationDT_s
     */
    private static String convertCollectionMethodNameToStruts(String strMethod)
    {
      // only handle a method that starts with get
      if (! strMethod.startsWith(GETTHE))
	return "ERROR";

      if (! strMethod.endsWith(COLLECTION))
	return "ERROR";

      // or if the strMethod is not longer than the word get
      if (! (strMethod.length() > GETTHE.length()+COLLECTION.length() ))
	return "ERROR";

      // return the modified string.
      String strMinusGETTHE = strMethod.substring(6, 7).toLowerCase() + strMethod.substring(7, strMethod.length() - COLLECTION.length()) + _S;

      return strMinusGETTHE;
      }

}