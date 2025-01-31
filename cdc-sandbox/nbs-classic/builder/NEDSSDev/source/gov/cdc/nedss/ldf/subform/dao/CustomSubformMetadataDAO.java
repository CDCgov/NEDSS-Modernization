package gov.cdc.nedss.ldf.subform.dao;

import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.LdfPageSetDT;
import gov.cdc.nedss.ldf.subform.dt.CustomSubformMetadataDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;




/**
 *
 * <p>Title: CustomSubformMetadataDAO</p>
 * <p>Description: DataAccessObject to perform dml and ddl operations on CustomSubformMetaData table(new for R1.1.3)</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Narendra Mallela
 * @version 1.0
 */
public class CustomSubformMetadataDAO
    extends DAOBase {

  private static final LogUtils logger = new LogUtils(CustomSubformMetadataDAO.class.
      getName());
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  private final static String selectStmtByBo =

      "SELECT " +
      "csm1.CUSTOM_SUBFORM_METADATA_UID \"customSubformMetadataUid\", " +
      "csm1.ADD_TIME \"addTime\", " +
      "csm1.ADMIN_COMMENT \"adminComment\", " +
      "csm1.BUSINESS_OBJECT_NM \"businessObjectNm\", " +
      "csm1.CLASS_CD \"classCd\", " +
      "csm1.CONDITION_CD \"conditionCd\", " +
      "csm1.CONDITION_DESC_TXT \"conditionDescTxt\", " +
      "csm1.DISPLAY_ORDER_NBR \"displayOrderNbr\", " +
      "csm1.HTML_DATA \"htmlData\", " +
      "csm1.IMPORT_VERSION_NBR \"importVersionNbr\", " +
      "csm1.DEPLOYMENT_CD \"deploymentCd\", " +
      "csm1.PAGE_SET_ID \"pageSetId\", " +
      "csm1.RECORD_STATUS_CD \"recordStatusCd\", " +
      "csm1.RECORD_STATUS_TIME \"recordStatusTime\", " +
      "csm1.STATE_CD \"stateCd\", " +
      "csm1.STATUS_CD \"statusCd\", " +
      "csm1.SUBFORM_OID \"subformOid\", " +
      "csm1.SUBFORM_NM \"subformNm\", " +
      "csm1.VERSION_CTRL_NBR \"versionCtrlNbr\" " +
      "FROM CUSTOM_SUBFORM_METADATA csm1, " +
      "(select  csm2.subform_oid , max( csm2.IMPORT_VERSION_NBR) IMPORT_VERSION_NBR " +
      "from CUSTOM_SUBFORM_METADATA csm2 " +
      "where BUSINESS_OBJECT_NM = ? " +
      "and condition_cd is null " +
      "group by  csm2.subform_oid) csm3 " +
      "where " +
      "csm1.subform_oid =csm3.subform_oid " +
      "and csm1.IMPORT_VERSION_NBR  = csm3.IMPORT_VERSION_NBR " +
      "and csm1.BUSINESS_OBJECT_NM = ? " +
      "and csm1.status_cd = 'A' order by csm1.display_order_nbr";

  private final static String selectStmtByBoAndConditionCd =

      "SELECT " +
      "csm1.CUSTOM_SUBFORM_METADATA_UID \"customSubformMetadataUid\", " +
      "csm1.ADD_TIME \"addTime\", " +
      "csm1.ADMIN_COMMENT \"adminComment\", " +
      "csm1.BUSINESS_OBJECT_NM \"businessObjectNm\", " +
      "csm1.CLASS_CD \"classCd\", " +
      "csm1.CONDITION_CD \"conditionCd\", " +
      "csm1.CONDITION_DESC_TXT \"conditionDescTxt\", " +
      "csm1.DISPLAY_ORDER_NBR \"displayOrderNbr\", " +
      "csm1.HTML_DATA \"htmlData\", " +
      "csm1.IMPORT_VERSION_NBR \"importVersionNbr\", " +
      "csm1.DEPLOYMENT_CD \"deploymentCd\", " +
      "csm1.PAGE_SET_ID \"pageSetId\", " +
      "csm1.RECORD_STATUS_CD \"recordStatusCd\", " +
      "csm1.RECORD_STATUS_TIME \"recordStatusTime\", " +
      "csm1.STATE_CD \"stateCd\", " +
      "csm1.STATUS_CD \"statusCd\", " +
      "csm1.SUBFORM_OID \"subformOid\", " +
      "csm1.SUBFORM_NM \"subformNm\", " +
      "csm1.VERSION_CTRL_NBR \"versionCtrlNbr\" " +
      "FROM CUSTOM_SUBFORM_METADATA csm1, " +
      "(select  csm2.subform_oid , max( csm2.IMPORT_VERSION_NBR) IMPORT_VERSION_NBR " +
      "from CUSTOM_SUBFORM_METADATA csm2 " +
      "where BUSINESS_OBJECT_NM = ? " +
      "and condition_cd = ? " +
      "group by  csm2.subform_oid) csm3 " +
      "where " +
      "csm1.subform_oid =csm3.subform_oid " +
      "and csm1.IMPORT_VERSION_NBR  = csm3.IMPORT_VERSION_NBR " +
      "and csm1.BUSINESS_OBJECT_NM = ? " +
      "and csm1.condition_cd = ? " +
      "and csm1.status_cd = 'A' order by csm1.display_order_nbr";

  private final static String selectStmt =

      "SELECT " +
      "CUSTOM_SUBFORM_METADATA_UID \"customSubformMetadataUid\", " +
      "ADD_TIME \"addTime\", " +
      "ADMIN_COMMENT \"adminComment\", " +
      "BUSINESS_OBJECT_NM \"businessObjectNm\", " +
      "CLASS_CD \"classCd\", " +
      "CONDITION_CD \"conditionCd\", " +
      "CONDITION_DESC_TXT \"conditionDescTxt\", " +
      "DISPLAY_ORDER_NBR \"displayOrderNbr\", " +
      "HTML_DATA \"htmlData\", " +
      "IMPORT_VERSION_NBR \"importVersionNbr\", " +
      "DEPLOYMENT_CD \"deploymentCd\", " +
      "PAGE_SET_ID \"pageSetId\", " +
      "RECORD_STATUS_CD \"recordStatusCd\", " +
      "RECORD_STATUS_TIME \"recordStatusTime\", " +
      "STATE_CD \"stateCd\", " +
      "STATUS_CD \"statusCd\", " +
      "SUBFORM_OID \"subformOid\", " +
      "SUBFORM_NM \"subformNm\", " +
      "VERSION_CTRL_NBR \"versionCtrlNbr\" " +
      "FROM CUSTOM_SUBFORM_METADATA";

  public final static String SELECT_METADATA_BY_UID = selectStmt +
      " where custom_subform_metadata_uid = ? " ;

  public final static String selectBySubformUid = selectStmt +
      " WHERE CUSTOM_SUBFORM_METADATA_UID = ?";

  public final static String selectBySubformOid = selectStmt +
      " WHERE Subform_oid = ? and import_version_nbr = ? ";

  private final static String updateStmt =
      " update CUSTOM_SUBFORM_METADATA set " +
      "ADD_TIME =?, " +
      "ADMIN_COMMENT =?, " +
      "BUSINESS_OBJECT_NM =?, " +
      "CLASS_CD =?, " +
      "CONDITION_CD =?, " +
      "CONDITION_DESC_TXT =?, " +
      "DISPLAY_ORDER_NBR =?, " +
      "HTML_DATA =?, " +
      "IMPORT_VERSION_NBR =?, " +
      "DEPLOYMENT_CD =?, " +
      "PAGE_SET_ID =?, " +
      "RECORD_STATUS_CD =?, " +
      "RECORD_STATUS_TIME =?, " +
      "STATE_CD =?, " +
      "STATUS_CD =?, " +
      "SUBFORM_OID =?, " +
      "SUBFORM_NM =?, " +
      "VERSION_CTRL_NBR =? " +
      " WHERE " +
      "CUSTOM_SUBFORM_METADATA_UID=?";

  private final static String deleteStmt =
      "delete from CUSTOM_SUBFORM_METADATA where CUSTOM_SUBFORM_METADATA_UID = ? ";

  private final static String deleteStmtByImportVersionNbr =
      "delete from CUSTOM_SUBFORM_METADATA where Import_version_nbr = ? ";

 

  private final static String SQL_INSERT =
      " insert into CUSTOM_SUBFORM_METADATA(" +
      "CUSTOM_SUBFORM_METADATA_UID ," +
      "ADD_TIME , " +
      "ADMIN_COMMENT , " +
      "BUSINESS_OBJECT_NM , " +
      "CLASS_CD , " +
      "CONDITION_CD , " +
      "CONDITION_DESC_TXT , " +
      "DISPLAY_ORDER_NBR , " +
      "HTML_DATA , " +
      "IMPORT_VERSION_NBR , " +
      "DEPLOYMENT_CD , " +
      "PAGE_SET_ID , " +
      "RECORD_STATUS_CD , " +
      "RECORD_STATUS_TIME , " +
      "STATE_CD , " +
      "STATUS_CD , " +
      "SUBFORM_OID , " +
      "SUBFORM_NM , " +
      "VERSION_CTRL_NBR  " +
      ")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  //Narendra (05/16/05) - Query to retrieve LDFPageSetID & description
  private final static String SELECT_PAGESET_SQL =
		"select ldf_page_id \"ldfPageId\", code_short_desc_txt \"codeShortDescTxt\" from nbs_srte..LDF_page_set where status_cd='A' order by 2";

  private final static String SELECT_MAX_IMPORT_VERSION = "select max(import_version_nbr) from custom_subform_metadata";

  /**
   * Method to retrieve CustomSubformMetaData collection based on CustomSubformMetaDataUid
   * @param subformUid
   * @return java.lang.Object
   * @throws NEDSSDAOSysException
   */
  @SuppressWarnings("unchecked")
public Object select(Long subformUid) throws NEDSSDAOSysException {
    CustomSubformMetadataDT customSubformMetadata = new CustomSubformMetadataDT();

    ArrayList<Object> arrayList = new ArrayList<Object> ();

    arrayList.add(subformUid);

    try {
      arrayList = (ArrayList<Object> ) preparedStmtMethod(customSubformMetadata,
                                                 arrayList,
                                                 selectBySubformUid,
                                                 NEDSSConstants.SELECT);
    }
    catch (Exception ex) {
      logger.fatal("subformUid: "+subformUid+" Exception in CustomSubformMetadata.select(): Error = " + ex.getMessage(), ex);
      throw new NEDSSDAOSysException(ex.getMessage());
    }
    if (arrayList.size() > 0) {
      for (Iterator<Object> anIter = arrayList.iterator(); anIter.hasNext(); ) {
        CustomSubformMetadataDT dt = (CustomSubformMetadataDT) anIter.next();
        return dt;
      }
    }
    return null;
  }

  /**
  * Method to retrieve CustomSubformMetaData collection based on CustomSubformMetaDataUid
  * @param subformUid
  * @return java.lang.Object
  * @throws NEDSSDAOSysException
  */
 @SuppressWarnings("unchecked")
public Object selectBySubformOid(String subformOid, Long importVersionNbr) throws NEDSSDAOSysException {
   CustomSubformMetadataDT customSubformMetadata = new CustomSubformMetadataDT();

   ArrayList<Object> arrayList = new ArrayList<Object> ();

   arrayList.add(subformOid);
   arrayList.add(importVersionNbr);

   try {
     arrayList = (ArrayList<Object> ) preparedStmtMethod(customSubformMetadata,
                                                arrayList,
                                                selectBySubformOid,
                                                NEDSSConstants.SELECT);

     if (arrayList.size() > 0)
     {
      for (Iterator<Object> anIter = arrayList.iterator(); anIter.hasNext(); ) {
        CustomSubformMetadataDT dt = (CustomSubformMetadataDT) anIter.next();
        return dt;
      }
     }
   }
   catch (Exception ex) {
     logger.fatal("subformOid: "+subformOid+" Exception in CustomSubformMetadata.select(): Error = " + ex.getMessage(), ex);
     throw new NEDSSDAOSysException(ex.getMessage());
   }

   return null;
 }


  /**
       * Method to retrieve CustomSubformMetaData collection based on businessObject
   * @param businessObjName
   * @return java.util.Collection
   * @throws NEDSSDAOSysException
   */
  @SuppressWarnings("unchecked")
public Collection<Object>  select(String businessObjName) throws NEDSSDAOSysException {
    CustomSubformMetadataDT customSubformMetadata = new CustomSubformMetadataDT();

    ArrayList<Object> arrayList = new ArrayList<Object> ();

    arrayList.add(businessObjName);
    arrayList.add(businessObjName);

    //logger.debug("selectStmt = \n\n" + this.selectStmtByBo);
    try {
      arrayList = (ArrayList<Object> ) preparedStmtMethod(customSubformMetadata,
                                                 arrayList, selectStmtByBo,
                                                 NEDSSConstants.SELECT);
    }
    catch (Exception ex) {
      logger.fatal("businessObjName: "+businessObjName+" Exception in CustomSubformMetadata.select(): Error = " + ex.getMessage(), ex);
      throw new NEDSSDAOSysException(ex.getMessage());
    }
    return arrayList;
  }

  /**
   * Method to retrieve CustomSubformMetaData collection based on businessObject and ConditionCode
   * @param businessObjName
   * @param conditionCd
   * @return java.util.Collection
   * @throws NEDSSDAOSysException
   */
  @SuppressWarnings("unchecked")
public Collection<Object>  select(String businessObjName, String conditionCd) throws
      NEDSSDAOSysException {
    CustomSubformMetadataDT customSubformMetadata = new CustomSubformMetadataDT();

    ArrayList<Object> arrayList = new ArrayList<Object> ();

    arrayList.add(businessObjName);
    arrayList.add(conditionCd);
    arrayList.add(businessObjName);
    arrayList.add(conditionCd);

    //logger.debug("selectStmt = \n\n" + this.selectStmtByBoAndConditionCd);

    try {
      arrayList = (ArrayList<Object> ) preparedStmtMethod(customSubformMetadata,
                                                 arrayList,
                                                 selectStmtByBoAndConditionCd,
                                                 NEDSSConstants.SELECT);
    }
    catch (Exception ex) {
      logger.fatal("businessObjName: "+businessObjName+" Exception in CustomSubformMetadata.select(): Error = " + ex.getMessage(), ex);
      throw new NEDSSDAOSysException(ex.getMessage());
    }
    return arrayList;
  }

  /**
   *
   * @param customSubformMetadata
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   */
  public void update(CustomSubformMetadataDT customSubformMetadata) throws
      NEDSSSystemException, NEDSSConcurrentDataException {

    ArrayList<Object> valueList = new ArrayList<Object> ();
    try {
      if (customSubformMetadata != null) {

        valueList.add(customSubformMetadata.getAddTime());
        valueList.add(customSubformMetadata.getAdminComment());
        valueList.add(customSubformMetadata.getBusinessObjectNm());
        valueList.add(customSubformMetadata.getClassCd());
        valueList.add(customSubformMetadata.getConditionCd());
        valueList.add(customSubformMetadata.getConditionDescTxt());
        valueList.add(customSubformMetadata.getDisplayOrderNbr());
        valueList.add(customSubformMetadata.getHtmlData());
        valueList.add(customSubformMetadata.getImportVersionNbr());
        valueList.add(customSubformMetadata.getDeploymentCd());
        valueList.add(customSubformMetadata.getPageSetId());
        valueList.add(customSubformMetadata.getRecordStatusCd());
        valueList.add(customSubformMetadata.getRecordStatusTime());
        valueList.add(customSubformMetadata.getStateCd());
        valueList.add(customSubformMetadata.getStatusCd());
        valueList.add(customSubformMetadata.getSubformOid());
        valueList.add(customSubformMetadata.getSubformNm());
        valueList.add(customSubformMetadata.getVersionCtrlNbr());
        valueList.add(customSubformMetadata.getCustomSubformMetadataUid());

        int resultCount = ( (Integer) preparedStmtMethod(null, valueList,
            updateStmt, NEDSSConstants.UPDATE)).intValue();
        if (resultCount != 1) {
          throw new NEDSSException("Update failed");
        }
      }
    }
    catch (Exception e) {
    	logger.fatal("Exception = "+e.getMessage(), e);
      throw new NEDSSSystemException(e.toString());
    }
  }

  /**
   * Method to delete CustomSubformMetaData record based on CustomSubformMetaDataUid
   * @param subformUid
   * @throws NEDSSDAOSysException
   */
  public void delete(Integer subformUid) throws NEDSSDAOSysException {
    CustomSubformMetadataDT customSubformMetadata = new CustomSubformMetadataDT();
    ArrayList<Object> arrayList = new ArrayList<Object> ();
    arrayList.add(subformUid);
    try {
      preparedStmtMethod(customSubformMetadata, arrayList, deleteStmt,
                         NEDSSConstants.UPDATE);
    }
    catch (Exception ex) {
      logger.fatal("Exception in CustomSubformMetadata.delete(): Error = " + ex.getMessage(), ex);
      throw new NEDSSDAOSysException(ex.getMessage());
    }
  }

  /**
   * Method to delete CustomSubformMetaData record based on ImportVersionNbr
   * @param importVersionNbr
   * @throws NEDSSDAOSysException
   */
  public void deleteByImportVersionNbr(Long importVersionNbr) throws
      NEDSSDAOSysException {
    CustomSubformMetadataDT customSubformMetadata = new CustomSubformMetadataDT();
    ArrayList<Object> arrayList = new ArrayList<Object> ();
    arrayList.add(importVersionNbr);
    try {
      preparedStmtMethod(customSubformMetadata, arrayList,
                         deleteStmtByImportVersionNbr,
                         NEDSSConstants.UPDATE);
    }
    catch (Exception ex) {
      logger.fatal("Exception in CustomSubformMetadata.delete(): Error = " + ex.getMessage(), ex);
      throw new NEDSSDAOSysException(ex.getMessage());
    }
  }


  /**
   * Method to create CustomSubformMetaData record by passing the built CustomSubformMetaDataDT as Object
   * @param obj
   * @return
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  public long create(Object obj) throws NEDSSDAOSysException,
      NEDSSSystemException {
	  try{
	    long subformUID = -1;
	    subformUID = insert( (CustomSubformMetadataDT) obj);
	    return subformUID;
	  }catch(NEDSSDAOSysException ex){
		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		  throw new NEDSSDAOSysException(ex.toString());
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }

  } //create

  /**
   * Method to create CustomSubformMetaData record by passing the built CustomSubformMetaDataDT
   * @param customSubformMetadata
   * @return
   * @throws NEDSSSystemException
   */
  public long insert(CustomSubformMetadataDT dt) throws NEDSSDAOSysException,
      NEDSSSystemException {

    PropertyUtil propUtil = PropertyUtil.getInstance();

      //for SQLServer
      Connection dbConnection = null;
      PreparedStatement preparedStmt = null;
      long uid = 0;

      try {
        dbConnection = getConnection();
        UidGeneratorHelper helper = new UidGeneratorHelper();
        uid = helper.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();

        preparedStmt = dbConnection.prepareStatement(SQL_INSERT);

        int i = 1;
        preparedStmt.setLong(i++, uid); // set message_uid
        preparedStmt.setTimestamp(i++, dt.getAddTime());
        preparedStmt.setString(i++, dt.getAdminComment());
        preparedStmt.setString(i++, dt.getBusinessObjectNm());
        preparedStmt.setString(i++, dt.getClassCd());
        preparedStmt.setString(i++, dt.getConditionCd());
        preparedStmt.setString(i++, dt.getConditionDescTxt());

        if (dt.getDisplayOrderNbr() == null) {
          preparedStmt.setNull(i++, Types.BIGINT);
        }
        else {
          preparedStmt.setLong(i++, dt.getDisplayOrderNbr().longValue());

        }
        //write HTML_DATA in SQL Server Specific manner

        String loadJSFunction = "loadData";
        String saveJSFunction = "saveData";
        String validateJSFunction = "validateData";
        String disableJSFunction = "disableData";
        String enableJSFunction = "enableData";
        String clearJSFunction = "clearData";
        String validateCondEntryJSFunction = "validateCondEntry";
        String subformUidI = "uid=\"\"";
        String subformUidF = "uid=\"" + uid + "\"";

        String htmlDataChange1 = dt.getHtmlData().replaceAll(loadJSFunction, (loadJSFunction + "_" + uid));
        String htmlDataChange2 = htmlDataChange1.replaceAll(saveJSFunction, (saveJSFunction + "_" + uid));
        String htmlDataChange3 = htmlDataChange2.replaceAll(validateJSFunction, (validateJSFunction + "_" + uid));
        String htmlDataChange4 = htmlDataChange3.replaceAll(disableJSFunction, (disableJSFunction + "_" + uid));
        String htmlDataChange5 = htmlDataChange4.replaceAll(enableJSFunction, (enableJSFunction + "_" + uid));
        String htmlDataChange6 = htmlDataChange5.replaceAll(clearJSFunction, (clearJSFunction + "_" + uid));
        String htmlDataChange7 = htmlDataChange6.replaceAll(validateCondEntryJSFunction, (validateCondEntryJSFunction + "_" + uid));

        String XMLString = htmlDataChange7.replaceAll(subformUidI, subformUidF);

        preparedStmt.setAsciiStream(i++,
                                    new ByteArrayInputStream(XMLString.getBytes()),
                                    XMLString.length());


        if (dt.getImportVersionNbr() == null) {
          preparedStmt.setNull(i++, Types.BIGINT);
        }
        else {
          preparedStmt.setLong(i++, dt.getImportVersionNbr().longValue());

        }
        preparedStmt.setString(i++, dt.getDeploymentCd());
        preparedStmt.setString(i++, dt.getPageSetId());
        preparedStmt.setString(i++, dt.getRecordStatusCd());
        preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());
        preparedStmt.setString(i++, dt.getStateCd());
        preparedStmt.setString(i++, dt.getStatusCd());
        preparedStmt.setString(i++, dt.getSubformOid());
        preparedStmt.setString(i++, dt.getSubformNm());

        if (dt.getVersionCtrlNbr() == null) {
          preparedStmt.setNull(i++, Types.BIGINT);
        }
        else {
          preparedStmt.setLong(i++, dt.getVersionCtrlNbr().longValue());

        }
        preparedStmt.executeUpdate();
        return uid;
      }
      catch (SQLException sqlex) {
        logger.fatal("SQLException while inserting Subform MetaData record ",
                     sqlex);
        throw new NEDSSDAOSysException(
            "Insert Subform MetaData table failed !!"+sqlex.getMessage());
      }
      catch (Exception ex) {
    	logger.fatal("Insert into table Subform MetaData Failed !!"+ex.getMessage(),ex);
        throw new NEDSSSystemException(
            "Insert into table Subform MetaData Failed !!"+ex.getMessage());
      }
      finally {
        closeStatement(preparedStmt);
        releaseConnection(dbConnection);
      }

    
  }

  /**
   * selectPageSet method returns a HashMap<Object,Object> comprising LDFPageSetId as Key and ShortDescTxt as value
   * @return java.util.HashMap
   * @throws NEDSSDAOSysException
   */
  @SuppressWarnings("unchecked")
public HashMap<Object,Object> selectPageSet() throws NEDSSDAOSysException {

  	String sqlStmt = null;
  	LdfPageSetDT ldfPageSetDT = new LdfPageSetDT();

  	sqlStmt = SELECT_PAGESET_SQL;
    
    ArrayList<Object> list = (ArrayList<Object> ) preparedStmtMethod(ldfPageSetDT, null, sqlStmt, NEDSSConstants.SELECT);

    HashMap<Object,Object> map = new HashMap<Object,Object>();
    for (int count = 0; count < list.size(); count++) {
		LdfPageSetDT dt = (LdfPageSetDT) list.get(count);
		map.put(dt.getLdfPageId(), dt.getCodeShortDescTxt());
    }
	return map;
  }

  public long selectMaxImportVersion() throws NEDSSDAOSysException {

    Connection dbConnection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    long maxVersion = 0;
    try {
        dbConnection = getConnection();
    	statement = dbConnection.createStatement();
    	resultSet = statement.executeQuery(SELECT_MAX_IMPORT_VERSION);
    	while(resultSet.next()) {
    		maxVersion = resultSet.getLong(1);
    	}

    } catch(Exception e) {
    	logger.fatal("Exception = "+e.getMessage(),e);
    	throw new NEDSSDAOSysException(e.getMessage());
    } finally {
		closeStatement(statement);
		releaseConnection(dbConnection);
	}
    return maxVersion;
  }

}