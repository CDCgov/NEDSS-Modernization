//Source file: C:\\Rational_development\\gov\\cdc\\nedss\\helpers\\UidGeneratorHelper.java

package gov.cdc.nedss.systemservice.uidgenerator;

import javax.rmi.*;
import java.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.uidgenerator.bean.*;

public class UidGeneratorHelper extends BMPBase
{
   private static Map<Object,Object> uidTable = Collections.synchronizedMap(new HashMap<Object,Object>());
   private StringUtils parser = new StringUtils();
   private short cacheCount = 1000;  //The size of each uid batch in the uid tracker
   private int tryCount = 5;

   protected class UidTracker
   {
      long currentUID = 0;
      long maxUID = 0;
      String uidPrefixCd = null;
      String uidSuffixCd = null;
   }

   //For logging
   static final LogUtils logger = new LogUtils(UidGeneratorHelper.class.getName());
   /**
    * The default constructor
    * @throws java.lang.Exception
    * @roseuid 3C0FEB3A03D6
    */
   public UidGeneratorHelper() throws Exception
   {
   }


   /**
    * The main for testing
    * @param args
    * @roseuid 3C0FEB3A00DD
    */
   public static void main(String[] args)
   {
    try
    {
      logger.setLogLevel(5);
      UidGeneratorHelper test = new UidGeneratorHelper();

      logger.info( "Local Person ID: " + test.getLocalID( "PERSON" ) );
      logger.info( "NBS Person ID: " + test.getNbsID( "TN" ) );
    }
    catch( Exception e )
    {
      logger.error( "Exception Thrown: " + e.getMessage() );
      logger.error( e.fillInStackTrace() );
    }
   }

   /**
    * Retrieves a local id
    * @param theClass the entity/act class type
    * @return java.lang.String the local id
    * @throws java.lang.Exception
    * @roseuid 3C0FEB3A010F
    */
   public String getLocalID(java.lang.String theClass) throws Exception
   {
    String strID = null;

    try
    {
      UidTracker tracker = (UidTracker)uidTable.get(theClass);

      if(tracker == null)
      {
      NedssUtils nedssUtils = new NedssUtils();
      String sBeanJndiName = JNDINames.UIDGENERATOR_EJB;
      Object objref = nedssUtils.lookupBean(sBeanJndiName);
      UidgeneratorHome home = (UidgeneratorHome)PortableRemoteObject.narrow(objref, gov.cdc.nedss.systemservice.ejb.uidgenerator.bean.UidgeneratorHome.class);
      Uidgenerator uidgenerator = home.create();
          //Initialize a key/value pair in the map
          for(int j =1; j <= tryCount; j++)
          {
            try
            {
                tracker = new UidTracker();
                HashMap<Object,Object> hashMap = uidgenerator.getLocalID(theClass, cacheCount);
                tracker.currentUID = parser.stringToLong((hashMap.get("currentUID").toString()).trim()).longValue();
                tracker.maxUID = parser.stringToLong((hashMap.get("maxUID").toString()).trim()).longValue();
                tracker.uidPrefixCd = hashMap.get("uidPrefixCd").toString();
                tracker.uidSuffixCd = hashMap.get("uidSuffixCd").toString();
                //Creates a new record for the uid in the map
                uidTable.put(theClass, tracker);
                 break;
            }
            catch(Exception ex)
            {
                logger.fatal("Unable to initialize a UID tracker for " + theClass);
                  if(j == tryCount)
                  {
                      logger.fatal("Unable to initialize a UID tracker for " + theClass);
                      throw new Exception(ex.toString(), ex);
                  }
                  else
                  {
                      continue;
                  }
            }
          }

      }
      else if(tracker.currentUID == tracker.maxUID)
      {
          NedssUtils nedssUtils = new NedssUtils();
          String sBeanJndiName = JNDINames.UIDGENERATOR_EJB;
          Object objref = nedssUtils.lookupBean(sBeanJndiName);
          UidgeneratorHome home = (UidgeneratorHome)PortableRemoteObject.narrow(objref, gov.cdc.nedss.systemservice.ejb.uidgenerator.bean.UidgeneratorHome.class);
          Uidgenerator uidgenerator = home.create();
          //Recharge the uid tracker in the map
          for(int j = 1; j <= tryCount; j++)
          {
            try
            {
                HashMap<Object,Object> hashMap = uidgenerator.getLocalID(theClass, cacheCount);
                tracker.currentUID = parser.stringToLong((hashMap.get("currentUID").toString()).trim()).longValue();
                tracker.maxUID = parser.stringToLong((hashMap.get("maxUID").toString()).trim()).longValue();
                 break;
            }
            catch(Exception ex)
            {
                logger.fatal("Unable to initialize a UID tracker for " + theClass);
                  if(j == tryCount)
                  {
                      logger.fatal("Unable to initialize a UID tracker for " + theClass);
                      throw new Exception(ex.toString(), ex);
                  }
                  else
                  {
                      continue;
                  }
            }
          }
      }

      //Returns a uid as a String
      synchronized(tracker){
      strID = (tracker.uidPrefixCd.trim() + (tracker.currentUID++) + tracker.uidSuffixCd.trim());
      }
      logger.info("Available uid range is: " + tracker.currentUID + " to " + tracker.maxUID + " for " + theClass);
    }
    catch( Exception e )
    {
       logger.fatal("Error while attempting to provide a local uid", e);
       throw new Exception( e.toString(), e );
    }
    logger.info("Returned local uid is: " + strID);
    return strID;
   }

   /**
    * Retrieves a NBS uid
    * @param theClass the entity/act class type
    * @return java.lang.String the nbs uid retrieved
    * @throws java.lang.Exception
    * @roseuid 3C0FEB3A021D
    */
   public String getNbsID(java.lang.String theClass) throws Exception
   {
    String strID = null;
    //long actMaxUid;
    //long entityMaxUid ;
    try
    {
      UidTracker tracker = (UidTracker)uidTable.get(theClass);

      if(tracker == null)
      {
          //actMaxUid = getMaxActUid();
          //entityMaxUid = getMaxEntityUid();

          //logger.debug("MAX act uid is: " + actMaxUid );
          //logger.debug("MAX entity uid is: " + entityMaxUid );
          NedssUtils nedssUtils = new NedssUtils();
          String sBeanJndiName = JNDINames.UIDGENERATOR_EJB;
          Object objref = nedssUtils.lookupBean(sBeanJndiName);
          UidgeneratorHome home = (UidgeneratorHome)PortableRemoteObject.narrow(objref, gov.cdc.nedss.systemservice.ejb.uidgenerator.bean.UidgeneratorHome.class);
          Uidgenerator uidgenerator = home.create();
          //Initialize a key/value pair in the map
          for(int j =1; j <= tryCount; j++)
          {

            try
            {
                tracker = new UidTracker();
                HashMap<Object,Object> hashMap = uidgenerator.getLocalID(theClass, cacheCount);
                tracker.currentUID = parser.stringToLong((hashMap.get("currentUID").toString()).trim()).longValue();
                tracker.maxUID = parser.stringToLong((hashMap.get("maxUID").toString()).trim()).longValue();
                //Creates a new record for the uid in the map
                uidTable.put(theClass, tracker);
                break;
            }
            catch(Exception ex)
            {
                logger.fatal("Unable to initialize a UID tracker for " + theClass);
                  if(j == tryCount)
                  {
                      logger.fatal("Unable to initialize a UID tracker for " + theClass);
                      throw new Exception(ex.toString(), ex);
                  }
                  else
                  {
                      continue;
                  }
            }
          }
      }
      if(tracker.currentUID == tracker.maxUID)
      {
          NedssUtils nedssUtils = new NedssUtils();
          String sBeanJndiName = JNDINames.UIDGENERATOR_EJB;
          Object objref = nedssUtils.lookupBean(sBeanJndiName);
          UidgeneratorHome home = (UidgeneratorHome)PortableRemoteObject.narrow(objref, gov.cdc.nedss.systemservice.ejb.uidgenerator.bean.UidgeneratorHome.class);
          Uidgenerator uidgenerator = home.create();

          //Recharge the uid tracker in the map
          for(int j = 1; j <= tryCount; j++)
          {
              //actMaxUid = getMaxActUid();
              //entityMaxUid = getMaxEntityUid();

            try
            {
                HashMap<Object,Object> hashMap = uidgenerator.getLocalID(theClass, cacheCount);
                tracker.currentUID = parser.stringToLong((hashMap.get("currentUID").toString()).trim()).longValue();
                tracker.maxUID = parser.stringToLong((hashMap.get("maxUID").toString()).trim()).longValue();
                 break;
            }
            catch(Exception ex)
            {
                logger.fatal("Unable to initialize a UID tracker for " + theClass);
                  if(j == tryCount)
                  {
                      logger.fatal("Unable to initialize a UID tracker for " + theClass);
                      throw new Exception(ex.toString(), ex);
                  }
                  else
                  {
                      continue;
                  }
            }
          }
      }

      //Returns a uid as a String
      synchronized(tracker){
        strID = new Long(tracker.currentUID++).toString();
      }
    }
    catch( Exception e )
    {
       logger.error("Error while attempting to provide a NBS uid", e);
       throw new Exception( e.toString(), e );
    }

    return strID;
   }

   /**
    * Retrieves a NBS uid
    * @param theClass the entity/act class type
    * @return Long the nbs uid retrieved
    * @throws java.lang.Exception
    * @roseuid 3C0FEB3A0304
    */
   public Long getNbsIDLong(java.lang.String theClass) throws Exception
   {
		return Long.valueOf( getNbsID( theClass ) );
   }
}
