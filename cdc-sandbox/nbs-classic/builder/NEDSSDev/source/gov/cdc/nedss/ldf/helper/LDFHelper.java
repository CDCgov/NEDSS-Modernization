package gov.cdc.nedss.ldf.helper;

import java.util.*;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;


import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;

import javax.naming.*;
import javax.rmi.*;

import gov.cdc.nedss.ldf.dao.*;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.ldf.group.DefinedFieldSubformGroupHelper;



public class LDFHelper
{
   static final LogUtils logger = new LogUtils (LDFHelper.class.getName());
   private static gov.cdc.nedss.ldf.helper.LDFHelper instance = new LDFHelper();
   private StateDefinedFieldDataDAOImpl sdfDAO;
   /**
    * @roseuid 3EB809990331
    */
   private LDFHelper()
   {

   }



   public static LDFHelper getInstance()
   {
		return instance;
   }

   private DefinedFieldSubformGroupHelper dfsgHelper = new DefinedFieldSubformGroupHelper();
   /**
    * @param businessObjName
    * @param busObjectUid
    * @param conditionCode
    */
   public Collection<Object> getLDFCollection(Long busObjectUid, String conditionCode, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException, NEDSSConcurrentDataException, NEDSSAppException
   {
     if (nbsSecurityObj == null) {
                 logger.info("nbsSecurityObj is null ");
                 throw new NEDSSSystemException("nbsSecurityObj is null");
                }

     if(sdfDAO == null)
       sdfDAO = (StateDefinedFieldDataDAOImpl)NEDSSDAOFactory.getDAO("gov.cdc.nedss.ldf.dao.StateDefinedFieldDataDAOImpl");
       return sdfDAO.getLDFCollection(busObjectUid,conditionCode);
  }



   public void setLDFCollection(Collection<Object> stateDefinedFieldDataCollection, Collection<Object> ldfUids, String businessObjNm,  Integer versionCtrlNbr, Long businessObjUid, NBSSecurityObj nbsSecurityObj)throws  NEDSSSystemException, NEDSSConcurrentDataException
   {
     if (nbsSecurityObj == null) {
                 logger.info("nbsSecurityObj is null ");
                 throw new NEDSSSystemException("nbsSecurityObj is null");
                }

     if(sdfDAO == null)
       sdfDAO = (StateDefinedFieldDataDAOImpl)NEDSSDAOFactory.getDAO("gov.cdc.nedss.ldf.dao.StateDefinedFieldDataDAOImpl");

     sdfDAO.setLDFData(stateDefinedFieldDataCollection,businessObjNm,  versionCtrlNbr, businessObjUid);

 //uncomment this once the ldfgroup functionality unit tested

     if(ldfUids != null) {
       List<Object> ldfUidList = new ArrayList<Object> ();
       ldfUidList.addAll(ldfUids);
       try {
        dfsgHelper.createBusinessObjectGroupRelationship(businessObjUid,ldfUidList);
       }
       catch (Exception e) {
         //throw new NEDSSSystemException(e.toString());
       }
     }

   }

 }