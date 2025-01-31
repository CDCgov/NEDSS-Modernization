package gov.cdc.nedss.nnd.util;
import java.util.*;
import java.sql.*;
import java.lang.reflect.*;
import java.text.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.nnd.util.*;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.nnd.exception.NNDException;


/**
 * Title:        NNDUtilsReceiveCopyObj
 * Description:  Extends the CopyObject method for the Receive side. Must decode and not encode
 *         and handle conversion of String to Long, Integer, Double and Timestamp.
 *         Also contains decodeCDATA method.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC for CDC NEDSS Project
 * @author       Greg Tucker
 * @version 1.0
 */

public class NNDUtilsReceiveCopyObj extends NNDUtils {

  public NNDUtilsReceiveCopyObj() {
  }

  static final LogUtils logger = new LogUtils((NNDUtils.class).getName());//Used for logging
      /**
     *
     * Override the copyObjects class from NNDUtils for the receive environment
     * In this we have to go from string to Long, string to Double, and string to Integer.
     * Also we have to go from Date to timestamp. And we have to decode instead of encode strings.
     * Copies two bean object with same attributes and methods
     *
     * @param from and two objects one with value and the other with no values
     *
     * @return  Object with values
     */
  public Object  copyObjects(Object fromObject, Object toObject) throws NNDException

    {

    try {
               Map<Object,Object> fromObjectMap = getGettingMethods(fromObject.getClass());
               Map<Object,Object> toObjectMap =   getSettingMethods(toObject.getClass());
               Set<Object> fromKeySet = fromObjectMap.keySet();
              Iterator<Object>  fromItr = fromKeySet.iterator();
               while (fromItr.hasNext())
               {

                     String keyValue =  (String) fromItr.next();

                    if (  NNDConstantUtil.notNull(fromObjectMap.get(keyValue)   ))


                                  if (toObjectMap.containsKey(keyValue))
                     {


                                   Method setMethod =  (Method)  toObjectMap.get(keyValue);

                                   Method getMethod =  (Method) fromObjectMap.get(keyValue);

                                   Class argType[] = setMethod.getParameterTypes();
                                   ArrayList<Object> objList = new ArrayList<Object> ();
                                   Object returnedObj = getMethod.invoke(fromObject, (Object[])null );
                                   logger.debug("receiveCopyObjects for " + setMethod.toString());

                                 if (NNDConstantUtil.notNull(returnedObj)&& (!returnedObj.toString().trim().equals("")))
                                 {
                                 if(returnedObj.toString().trim().equals(""))
                                 {
                                    logger.debug("setMethod = " + setMethod.toString());
                                    logger.debug("returnedObj = *" + returnedObj + "*");
                                  }
                                       Class returnedClass = getMethod.getReturnType();

                                       if ((returnedObj instanceof java.util.Date) &&
                                           (argType[0].equals(java.sql.Timestamp.class)))
                                       {
                                           long time = ((java.util.Date)  returnedObj).getTime();
                                           java.sql.Timestamp t = new java.sql.Timestamp(time);
                                           objList.add(t);
                                       }
                                       else if ( argType[0].equals(Long.class) )
                                       {
                                           Long l = new Long(returnedObj.toString());
                                           objList.add(l);
                                       }
                                       else if ( argType[0].equals(Double.class) )
                                       {
                                           Double d = new Double(returnedObj.toString());
                                           objList.add(d);
                                       }
                                       else if (argType[0].equals(Integer.class) )
                                       {
                                           Integer i = new Integer(returnedObj.toString());
                                           objList.add(i);
                                       }

                                       else{
                                          returnedObj = decodeCDATA(returnedObj.toString());
                                          objList.add(returnedObj.toString());
                                        }
                                           if (objList != null)
                                           {
                                              setMethod.invoke(toObject, objList.toArray());
                                           }

                                 }
                     }
               }
            }
            catch(NNDException nnde)

            {

             throw nnde;

            }

            catch (Exception e)
            {

             NNDException nndOther = new  NNDException ("Exception in copyObjects " + e.getMessage());
             nndOther.setModuleName("NNDUtils.copyObjects");
             throw nndOther;
            }
               return toObject;



    }




  /*
     Decode any encoded data in the passed string. i.e. &gt becomes >
     See the TreeMap<Object,Object> decodeMap in NNDConstantUtil for the list of items to decode.
     @param String toDecode
     @return decoded String
  */

  public String decodeCDATA(String strToDecode) throws NNDException {
         logger.info("Starts decodeCDATA.");
      //if no ampersand - no decoding necessary..return
      if (strToDecode.indexOf("&") == -1)
              return(strToDecode);

      String text1 = new String(strToDecode);
    //  text1.concat(strToDecode);
      String text2 = new String();
      String value = new String();
      boolean neverModified = true;
try {
     Iterator<Object>  keyIter =  NNDConstantUtil.decodeMap.keySet().iterator();

      while (keyIter.hasNext())
        {
        String key = (String) keyIter.next();
        boolean searchAgain = true;  //same delimiter could be on line more than once
        while (searchAgain)
          {
          int index = 0;
          index = text1.indexOf(key);
          if (index != -1)
            {
            text2 = text1.substring(0,index);
            value = (String) NNDConstantUtil.decodeMap.get(key);
            text2 = text2.concat(value);
            text2 = text2.concat(text1.substring(index + key.length()));
            text1 = text2; //start with new for next key
            searchAgain = true;
            neverModified = false;
            }
          else
            searchAgain = false; //substring not found
          } //searchAgain
        } //iter
    }
    catch (Exception e)
    {
    NNDException nnde = new NNDException("Error in Decode Data " + e.getMessage());
    nnde.setModuleName("NNDUtils.decodeCDATA");
    throw nnde;
    }
  if (neverModified)
    return strToDecode;
  else
    return text2;
 }

} //class
