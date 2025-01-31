package gov.cdc.nedss.nnd.util;

import java.util.*;
import java.sql.*;
import java.lang.reflect.*;
import java.text.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.nnd.exception.NNDException;


/**
 * Title:        NEDSS1.0
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author CSC EMPLOYEE
 * @version 1.0
 */

public class NNDUtils {

  public NNDUtils() {
  }

  static final LogUtils logger = new LogUtils((NNDUtils.class).getName());//Used for logging
      /**
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


                                   ArrayList<Object> objList = new ArrayList<Object> ();
                                   Object returnedObj = getMethod.invoke(fromObject, (Object[])null );


                                 if (NNDConstantUtil.notNull(returnedObj)&& (!returnedObj.toString().trim().equals("")))
                                 {
                                 if(returnedObj.toString().trim().equals(""))
                                 {
                                   logger.debug("setMethod = " + setMethod.toString());
                                   logger.debug("returnedObj = *" + returnedObj + "*");
                                  }
                                       Class returnedClass = getMethod.getReturnType();

                                       if (returnedObj instanceof Timestamp)
                                       {

                                          objList.add(new java.util.Date( ( (Timestamp)returnedObj).getTime() ));
					 //  objList.add(getGMTDate((Timestamp)returnedObj));


                                       }
                                       else{

                                          returnedObj = encodeCDATA(returnedObj.toString());
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

            e.printStackTrace();
             NNDException nndOther = new  NNDException ("Exception in copyObjects " + e.getMessage());
             nndOther.setModuleName("NNDUtils.copyObjects");
             throw nndOther;
            }
               return toObject;



    }

/*
    public java.util.Date getGMTDate(Timestamp timeStamp) throws NNDException
{


  String formattedDate = null;
  DateFormat dfGMT = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL );
  SimpleDateFormat sdf = new SimpleDateFormat( NNDConstantUtil.JDBC_TIME_STAMP_FORMAT);

  TimeZone tmz  = TimeZone.getTimeZone(NNDConstantUtil.GMT_TIME_ZONE);
  java.util.Date returnDate = new java.util.Date(timeStamp.getTime());
  sdf.setTimeZone(tmz);
  try {

         Timestamp gmtTimestamp = Timestamp.valueOf(sdf.format(returnDate));
         returnDate.setTime(gmtTimestamp.getTime());
         formattedDate =   dfGMT.format(returnDate);


   }

 catch (Exception e) {
      e.printStackTrace();
      NNDException nnde = new NNDException("Error while formatting GMTDATE " + e.getMessage());
      nnde.setModuleName("NNDUtils.getGMTDate");
      logger.debug("Error while parsing " + e.getMessage());
      throw nnde;

   }


   return  returnDate;
} //end method

*/

  /*
     From the given class find all getter methods.
     @param Class
     @return Map<Object,Object> of method names
  */

  public String encodeCDATA(String strToEncode) throws NNDException {
 //        logger.info("Starts encodeCDATA.");
         StringBuffer stringBuffer = new StringBuffer();
try {
         String encodeKey = "";
         StringTokenizer tokenizer =  new StringTokenizer(strToEncode, NNDConstantUtil.delimiters, true);



         while (tokenizer.hasMoreTokens() )
         {


          String extractStr = tokenizer.nextToken();


          if (NNDConstantUtil.encodeMap.containsKey(extractStr))

             stringBuffer.append(NNDConstantUtil.encodeMap.get(extractStr));
          else
             stringBuffer.append(extractStr);

          }


 }

 catch (Exception e)
 {
  e.printStackTrace();
  NNDException nnde = new NNDException("Error in Encode Data " + e.getMessage());
  nnde.setModuleName("NNDUtils.encodeCDATA");
  throw nnde;

 }


  return stringBuffer.toString();
 }
  /*
     From the given class find all getter methods.
     @param Class
     @return Map<Object,Object> of method names
  */

  public Map<Object,Object> getGettingMethods(Class beanClass) throws NNDException {
//  logger.info("Starts getGettingMethods()...");
                Map<Object,Object> resultMap = new HashMap<Object,Object>();
  try{
                Method[] gettingMethods = beanClass.getDeclaredMethods();

                for(int i = 0; i < gettingMethods.length; i++) {
                        Method method = (Method)gettingMethods[i];
                        String methodName = method.getName();

                        if(methodName.startsWith("get")) {



                                methodName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);

                                resultMap.put(methodName, method);
                        }
                }
  //logger.info("Done getGettingMethods() - return: " + resultMap.toString());
  }
  catch(Exception e)

  {
    e.printStackTrace();
    NNDException nnde = new NNDException("Exception in getGettingMethods " + e.getMessage());
    nnde.setModuleName("NNDUtils.getGettingMethods");
    throw nnde;

  }
                return resultMap;
        }
     /*
     From the given class find all setter methods.
     @param Class
     @return Map<Object,Object> of method names
  */

  public Map<Object,Object> getSettingMethods(Class beanClass) throws NNDException {
 // logger.info("Starts getSettingMethods()...");
                  Map<Object,Object> resultMap = new HashMap<Object,Object>();
  try
  {
                Method[] gettingMethods = beanClass.getDeclaredMethods();

                for(int i = 0; i < gettingMethods.length; i++) {
                        Method method = (Method)gettingMethods[i];
                        String methodName = method.getName();
                        if(methodName.startsWith("set")) {
                                methodName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
                                resultMap.put(methodName, method);
                        }
                }
  //logger.info("Done getSettingMethods() - return: " + resultMap.toString());
 }
  catch(Exception e)

  {
    e.printStackTrace();
    NNDException nnde = new NNDException("Exception in getSettingMethods " + e.getMessage());
    nnde.setModuleName("NNDUtils.getSettingMethods");
    throw nnde;

  }
                return resultMap;
        }

}