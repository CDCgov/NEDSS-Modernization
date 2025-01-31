
package gov.cdc.nedss.proxy.util;

import java.util.*;

import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.entity.entityid.dao.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.*;

import java.util.Collection;

import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.util.LogUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import gov.cdc.nedss.util.BMPBase;
//import testbase.util.DBConnectionManager;

public class EntityProxyHelper extends BMPBase
{
    static final LogUtils logger = new LogUtils(EntityProxyHelper.class.getName());
    private static gov.cdc.nedss.proxy.util.EntityProxyHelper instance = new EntityProxyHelper ();

    /**
     * @roseuid 3E79CECC03D8
     */
    private EntityProxyHelper()
    {

    } 

   /**
    * @return gov.cdc.nedss.proxy.util.EntityProxyHelper
    * @roseuid 3E79D170000F
    */
   public static EntityProxyHelper getInstance()
   {
     return instance;
   }

   /**
    * @param personVO
    * @return gov.cdc.nedss.entity.person.vo.PersonVO
    * @throws gov.cdc.nedss.exception.NEDSSSystemException
    * @roseuid 3E79D170001F
    **/
   public PersonVO setCityCode(PersonVO personVO) throws NEDSSSystemException
   {
       return personVO;
   }

   /**
    * @param theOrganizationSearchVO
    * @param cacheNumber
    * @param fromIndex
    * @return java.util.ArrayList
    * @throws gov.cdc.nedss.exception.NEDSSSystemException
    * @roseuid 3E79D17001B5
    */
    public ArrayList<Object> findOrganizationsByKeyWords(OrganizationSearchVO theOrganizationSearchVO, int cacheNumber, int fromIndex) throws NEDSSSystemException
    {
        ArrayList<Object> displayOrganizationList = new ArrayList<Object> ();

        FindOrganizationDAOImpl findOrganizationDAOImpl = new FindOrganizationDAOImpl();
        displayOrganizationList = findOrganizationDAOImpl.findOrganizationsByKeyWords(theOrganizationSearchVO, cacheNumber, fromIndex);
        logger.debug("\n\n\n\ndisplayORganizationList.size()" + displayOrganizationList.size());

        return displayOrganizationList;
    }
   
    public ArrayList findPlacesByKeyWords(PlaceSearchVO thePlaceSearchVO, int cacheNumber, int fromIndex)
            throws NEDSSSystemException
    {
        ArrayList<Object> displayPlaceList = new ArrayList<Object>();

        FindPlaceDAOImpl findPlaceDAOImpl = new FindPlaceDAOImpl();
        displayPlaceList = findPlaceDAOImpl.findPlacesByKeyWords(thePlaceSearchVO, cacheNumber, fromIndex);
        logger.debug("\n\n\n\ndisplayORganizationList.size()" + displayPlaceList.size());

        return displayPlaceList;
    }

   /**
    * @param theProviderSearchVO
    * @param cacheNumber
    * @param fromIndex
    * @return java.util.ArrayList
    * @throws gov.cdc.nedss.exception.NEDSSSystemException
    * @roseuid 3E79D170033C
    */
    public ArrayList<Object> findProvidersByKeyWords(ProviderSearchVO theProviderSearchVO, int cacheNumber, int fromIndex) throws NEDSSSystemException
    {
        FindPersonDAOImpl findPersonDAOImpl = new FindPersonDAOImpl();
        ArrayList<Object> providerList = findPersonDAOImpl.findProvidersByKeyWords(theProviderSearchVO, cacheNumber, fromIndex);
        return providerList;
    }

   /**
    * @param thePatientSearchVO
    * @param cacheNumber
    * @param fromIndex
    * @return java.util.ArrayList
    * @throws gov.cdc.nedss.exception.NEDSSSystemException
    * @roseuid 3E79D17100EB
    */
    public ArrayList<Object> findPatientsByKeyWords(PatientSearchVO thePatientSearchVO, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
    {
    	FindPersonDAOImpl findPersonDAOImpl = new FindPersonDAOImpl();
    	FindInvestigationDAOImpl findInvestigationDAOImpl = new FindInvestigationDAOImpl();
    	FindLaboratoryReportDAOImpl findLaboratoryReportDAOImpl = new FindLaboratoryReportDAOImpl();
        
        if(thePatientSearchVO.getReportType()!=null && thePatientSearchVO.getReportType().equalsIgnoreCase("I"))
        	return findInvestigationDAOImpl.findPatientsByKeyWords(thePatientSearchVO, cacheNumber, fromIndex, nbsSecurityObj);
        else
        	if(thePatientSearchVO.getReportType()!=null && thePatientSearchVO.getReportType().equalsIgnoreCase("LR"))
        		return findLaboratoryReportDAOImpl.findPatientsByKeyWords(thePatientSearchVO, cacheNumber, fromIndex, nbsSecurityObj);
        	else		
        		return findPersonDAOImpl.findPatientsByKeyWords(thePatientSearchVO, cacheNumber, fromIndex, nbsSecurityObj);
    }
    
    /*
    * findPatientsByQuery: this is used from Home Page Custom Queues. While saving a Queue from Event Search, the query used it is stored in the DB.
    * Then, if user open the custom queue from Home Page, the query stored in the DB will be retrieved and executed during this method to get the list
    * of patients.
    */
    public ArrayList<Object> findPatientsByQuery(PatientSearchVO thePatientSearchVO, String finalQuery, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
    {
    	//FindPersonDAOImpl findPersonDAOImpl = new FindPersonDAOImpl();
    	FindInvestigationDAOImpl findInvestigationDAOImpl = new FindInvestigationDAOImpl();
    	FindLaboratoryReportDAOImpl findLaboratoryReportDAOImpl = new FindLaboratoryReportDAOImpl();
        
         if(thePatientSearchVO.getReportType()!=null && thePatientSearchVO.getReportType().equalsIgnoreCase(NEDSSConstants.LAB_REPORT_EVENT_ID))
        	 return findLaboratoryReportDAOImpl.findPatientsByQuery(thePatientSearchVO,finalQuery, cacheNumber, fromIndex, nbsSecurityObj);
         else
        	return findInvestigationDAOImpl.findPatientsByQuery(thePatientSearchVO, finalQuery, cacheNumber, fromIndex, nbsSecurityObj);
        /*else
        	if(thePatientSearchVO.getReportType()!=null && thePatientSearchVO.getReportType().equalsIgnoreCase("LR"))
        		return findLaboratoryReportDAOImpl.findPatientsByKeyWords(thePatientSearchVO, cacheNumber, fromIndex, nbsSecurityObj);
        	else		
        		return findPersonDAOImpl.findPatientsByKeyWords(thePatientSearchVO, cacheNumber, fromIndex, nbsSecurityObj);*/
    }


   /**
    * @param vo
    * @return boolean
    * @throws gov.cdc.nedss.exception.NEDSSAppException
    * @roseuid 3E8B47050399
   */

    public boolean isQuickCodeUnique(AbstractVO vo) throws NEDSSAppException
    {
        Collection<Object> entityIdCollection = new ArrayList<Object>();

        String quickCd = null;
        String classCd = null;
        Long entityUID = new Long(0);
        Long entityUIDSelected = null;
        boolean itDirty = true;
        boolean itNew = true;

        if (vo instanceof OrganizationVO)
        {
            OrganizationVO orgVO = (OrganizationVO) vo;
            entityIdCollection = orgVO.getTheEntityIdDTCollection();
            itDirty = orgVO.isItDirty();
            itNew = orgVO.isItNew();
            if (entityIdCollection != null && entityIdCollection.size() > 0)
            {
                Iterator<Object> iterator = entityIdCollection.iterator();
                while (iterator.hasNext())
                {
                    EntityIdDT entityIdDT = (EntityIdDT) iterator.next();
                    String typeCd = entityIdDT.getTypeCd();

                    if ((typeCd != null) && (typeCd.equalsIgnoreCase(NEDSSConstants.ENTITY_TYPECD_QEC)))
                    {

                        quickCd = entityIdDT.getRootExtensionTxt();
                        classCd = NEDSSConstants.ORGANIZATION_CLASS_CODE;
                        entityUID = entityIdDT.getEntityUid();
                    }

                }
            }
        }
        else if (vo instanceof PersonVO)
        {
            PersonVO perVO = (PersonVO) vo;
            itDirty = perVO.isItDirty();
            itNew = perVO.isItNew();
            entityIdCollection = perVO.getTheEntityIdDTCollection();
            if (entityIdCollection != null && entityIdCollection.size() > 0)
            {
                Iterator<Object> iterator = entityIdCollection.iterator();
                while (iterator.hasNext())
                {
                    EntityIdDT entityIdDT = (EntityIdDT) iterator.next();
                    String typeCd = entityIdDT.getTypeCd();
                    if ((typeCd != null) && (typeCd.equalsIgnoreCase(NEDSSConstants.ENTITY_TYPECD_QEC)))
                    {

                        quickCd = entityIdDT.getRootExtensionTxt();
                        classCd = NEDSSConstants.PERSON_CLASS_CODE;
                        entityUID = entityIdDT.getEntityUid();
                    }
                }
            }
        }
        else if (vo instanceof PlaceVO)
        {
            PlaceVO perVO = (PlaceVO) vo;
            itDirty = perVO.isItDirty();
            itNew = perVO.isItNew();
            entityIdCollection = perVO.getTheEntityIdDTCollection();
            if (entityIdCollection != null && entityIdCollection.size() > 0)
            {
                Iterator<Object> iterator = entityIdCollection.iterator();
                while (iterator.hasNext())
                {
                    EntityIdDT entityIdDT = (EntityIdDT) iterator.next();
                    String typeCd = entityIdDT.getTypeCd();
                    if ((typeCd != null) && (typeCd.equalsIgnoreCase(NEDSSConstants.ENTITY_TYPECD_QEC)))
                    {

                        quickCd = entityIdDT.getRootExtensionTxt();
                        classCd = NEDSSConstants.PLACE_CLASS_CODE;
                        entityUID = entityIdDT.getEntityUid();
                    }
                }
            }
        }        
        if ((quickCd == null) || (quickCd.equals("")))
            return true;

        try
        {
            EntityIdDAOImpl entityIdDAOImpl = new EntityIdDAOImpl();
            entityUIDSelected = entityIdDAOImpl.selectEntityUID(quickCd, classCd);
        }
        catch (Exception e)
        {
            logger.error("Error in calling  EntityDaoImpl " + e.toString());
        }
        boolean retValue = true;
        if (entityUIDSelected == null)
        {
            retValue = true;
        }
        else if ((entityUIDSelected.equals(entityUID)) && (itNew == false) && (itDirty == true))
        {
            retValue = true;
        }
        else
        {
            retValue = false;
        }
        return retValue;
    }

    public Collection<Object>  findActivePatientUidsByParentUid(Long personUid)
    {
         String aQuery = NEDSSSqlQuery.PATIENTUIDBYPARENTUID;
         logger.info("aQuery = " + aQuery);
         Connection dbConnection = null;
         PreparedStatement preparedStmt = null;
         ResultSet resultSet = null;
         ArrayList<Object> uidList = new ArrayList<Object> ();
         try
         {
             dbConnection = getConnection();
             preparedStmt = dbConnection.prepareStatement(aQuery);
             preparedStmt.setLong(1, personUid.longValue());
             resultSet = preparedStmt.executeQuery();
             Long uid = null;


             while ( resultSet.next() )
             {
                 uid = new Long(resultSet.getLong(1));
                 uidList.add(uid);
                 logger.debug("revision uid: " + uid);
             }

         }
         catch(Exception e)
         {
                 e.printStackTrace();

         }
         finally
         {
           closeResultSet(resultSet);
           closeStatement(preparedStmt);
           releaseConnection(dbConnection);
         }
         logger.debug("no of revision persons Uids: " + uidList);

       return uidList;
    }
    
    public Long findPatientParentUidByUid(Long personUid) throws NEDSSSystemException 
    {
        String query = NEDSSSqlQuery.PATIENTPARENTUID_BY_UID;

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        Long uid = null;
        
        try
        {
         dbConnection = getConnection();

/*
         Use below code to get connection when unit - testing !!!!
         ----------------------------------------
         DBConnectionManager manager = new DBConnectionManager();
         dbConnection = manager.getConnectionTo(DBConnectionManager.NEDSS_DATASOURCE);
*/
         preparedStmt = dbConnection.prepareStatement(query);
         preparedStmt.setLong(1, personUid.longValue());
         resultSet = preparedStmt.executeQuery();

         while ( resultSet.next() )
         {
             uid = new Long(resultSet.getLong(1));
             //System.out.println("PatientParentUID found !!!! UID = " + uid);
         }

     }
     catch(Exception e)
     {
             e.printStackTrace();
     }
     finally
     {
       closeResultSet(resultSet);
       closeStatement(preparedStmt);
       releaseConnection(dbConnection);
     }
     return uid;

   }//findPatientParentUidByUid

   /**
    * Look up reporting lab's clia number
    * @param organizationUid
    * @return String
    */
   public String organizationCLIALookup(Long organizationUid)
    {
         String aQuery = "SELECT root_extension_txt FROM ENTITY_ID WHERE entity_uid = ? AND assigning_authority_cd = ? AND type_cd = ? AND record_status_cd =?";
         Connection dbConnection = null;
         PreparedStatement preparedStmt = null;
         ResultSet resultSet = null;

         try
         {
             String assigningAuthCd = NEDSSConstants.LAB_TYPE_CLIA,
                 typeCd = NEDSSConstants.REPORTING_LAB_FI_TYPE,
                 recordStatusCd = NEDSSConstants.RECORD_STATUS_ACTIVE;
             dbConnection = getConnection();


             //Use below code to get connection when unit - testing !!!!
             //----------------------------------------
             //DBConnectionManager manager = new DBConnectionManager();
             //dbConnection = manager.getConnectionTo(DBConnectionManager.NEDSS_DATASOURCE);



             int i = 1;
             preparedStmt = dbConnection.prepareStatement(aQuery);
             preparedStmt.setLong(i++, organizationUid.longValue());
             preparedStmt.setString(i++, assigningAuthCd);
             preparedStmt.setString(i++, typeCd);
             preparedStmt.setString(i++, recordStatusCd);
             resultSet = preparedStmt.executeQuery();

             String cliaCd = null;
             short count  = 0;
             while ( resultSet.next() )
             {
               cliaCd = resultSet.getString(1);
               count++;
             }

             if(count <= 1)
             {
                 return cliaCd;
             }
             else
             {
               throw new NEDSSSystemException("Expected only one CLIA number for this organization: "+ organizationUid);
             }
         }
         catch(Exception e)
         {
                 e.printStackTrace();

         }
         finally
         {
           closeResultSet(resultSet);
           closeStatement(preparedStmt);
           releaseConnection(dbConnection);
         }
         return null;
   }


}//EntityProxyHelper
