package gov.cdc.nedss.ldf.group.ejb.dao;
/**
 * <p>Title: DefinedFieldSubformGroupDAOImpl</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Mark Hankey
 * @version 1.0
 */

import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDBUniqueKeyViolation;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.group.ejb.dt.BusObjDfSfMdataGroupDT;
import gov.cdc.nedss.ldf.group.ejb.dt.DfSfMdataFieldGroupDT;
import gov.cdc.nedss.ldf.group.ejb.dt.DfSfMetadataGroupDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;

public class DefinedFieldSubformGroupDAOImpl extends DAOBase {
	static final LogUtils logger =
		new LogUtils((DefinedFieldSubformGroupDAOImpl.class).getName());

	public static final String INSERT_DF_SF_MDATA_FIELD_GROUP =
		"insert into df_sf_mdata_field_group "
			+ " (df_sf_metadata_group_uid, "
			+ " field_uid ,"
			+ " field_type ,"
			+ " version_ctrl_nbr ) "
			+ " values "
			+ "(?,?,?,?)";

	public static final String INSERT_DF_SF_METATATA_GROUP =
		"insert into df_sf_metadata_group "
			+ " (df_sf_metadata_group_uid, "
			+ " group_name ,"
			+ " version_ctrl_nbr ) "
			+ " values "
			+ "(?,?,?)";

	public static final String INSERT_BUS_OBJ_DF_SF_MDATA_GROUP =
		"insert into bus_obj_df_sf_mdata_group "
			+ " (business_object_uid, "
			+ " version_ctrl_nbr ,"
			+ " df_sf_metadata_group_uid ) "
			+ " values "
			+ "(?,?,?)";
	public static final String SELECT_BUS_OBJ_DF_SF_MDATA_GROUP =
		" select business_object_uid \"businessObjectUid\" ,"
			+ " version_ctrl_nbr \"versionCtrlNbr\" ,"
			+ " df_sf_metadata_group_uid \"dfsfMetadataGroupUid\" "
			+ " from bus_obj_df_sf_mdata_group "
			+ " where business_object_uid = ?";

	public static final String SELECT_DF_SF_METADATA_GROUP =
		" select "
			+ " mg.df_sf_metadata_group_uid \"dfsfMetadataGroupUid\" , "
			+ " mg.group_name \"groupName\" , "
			+ " mg.version_ctrl_nbr \"versionCtrlNbr\" "
			+ " from bus_obj_df_sf_mdata_group bomg, "
			+ " df_sf_metadata_group mg "
			+ " where bomg.df_sf_metadata_group_uid = mg.df_sf_metadata_group_uid and "
			+ " bomg.business_object_uid = ? ";

	public static final String SELECT_DF_SF_METADATA_GROUP_BY_GROUP_NAME =
		" select "
			+ " df_sf_metadata_group_uid \"dfsfMetadataGroupUid\" , "
			+ " group_name \"groupName\" , "
			+ " version_ctrl_nbr \"versionCtrlNbr\" "
			+ " from df_sf_metadata_group "
			+ " where group_name = ?";

	public static final String SELECT_DF_SF_METADATA_GROUP_BY_GROUP_UID =
		" select "
			+ " df_sf_metadata_group_uid \"dfsfMetadataGroupUid\" , "
			+ " group_name \"groupName\" , "
			+ " version_ctrl_nbr \"versionCtrlNbr\" "
			+ " from df_sf_metadata_group "
			+ " where df_sf_metadata_group_uid = ?";

	public static final String UPDATE_BUS_GROUP_RELATIONSHIP =
		" update bus_obj_df_sf_mdata_group set "
			+ " df_sf_metadata_group_uid = ?, "
			+ " version_ctrl_nbr = ? "
			+ " where business_object_uid = ? "
			+ " and version_ctrl_nbr = ? ";

	public DefinedFieldSubformGroupDAOImpl() {
	}

	public Long insertDfSfMetadataGroup(String groupName)
		throws NEDSSDBUniqueKeyViolation {

		Integer resultCount = null;
		Long uid = null;

		try {
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
			ArrayList<Object> insertDfSfMetadataGroupList = new ArrayList<Object> ();
			uid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE);
			insertDfSfMetadataGroupList.add(uid);
			insertDfSfMetadataGroupList.add(groupName);
			insertDfSfMetadataGroupList.add(new Long(1));

			DfSfMetadataGroupDT dFSfMetadataGroupDT = new DfSfMetadataGroupDT();
			resultCount =
				(Integer) preparedStmtMethod(dFSfMetadataGroupDT,
					insertDfSfMetadataGroupList,
					INSERT_DF_SF_METATATA_GROUP,
					"INSERT");
			if (resultCount.longValue() != 1)
				throw new NEDSSSystemException("insert failed for DfSfMetadataGroup");
		} catch (Exception ex) {
			DfSfMetadataGroupDT dFSfMetadataGroupDT =
				selectDfSfMetadataGroupDTByGroupName(groupName);
			if (dFSfMetadataGroupDT != null) {
				logger.fatal("Group name "+ groupName+ " already exists in df_sf_metadata_group table."+ex.getMessage(), ex);
				throw new NEDSSDBUniqueKeyViolation(
					"Group name "
						+ groupName
						+ " already exists in df_sf_metadata_group table.");
			} else {
				logger.fatal("Exception  = "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
		}
		return uid;

	}

	public Long insertDfSfMdataFieldGroup(
		Long dfSfMetadataGroupUid,
		Long fieldUid,
		String fieldType) {

		Integer resultCount = null;
		try {
			ArrayList<Object> insertDfSfMetadataFieldGroupList = new ArrayList<Object> ();

			insertDfSfMetadataFieldGroupList.add(dfSfMetadataGroupUid);
			insertDfSfMetadataFieldGroupList.add(fieldUid);
			insertDfSfMetadataFieldGroupList.add(fieldType);
			insertDfSfMetadataFieldGroupList.add(new Long(1));

			DfSfMdataFieldGroupDT dFSfMdataFieldGroupDT =
				new DfSfMdataFieldGroupDT();
			resultCount =
				(Integer) preparedStmtMethod(dFSfMdataFieldGroupDT,
					insertDfSfMetadataFieldGroupList,
					INSERT_DF_SF_MDATA_FIELD_GROUP,
					"INSERT");
			if (resultCount.longValue() != 1)
				throw new NEDSSSystemException("insert failed for DfSfMetadataGroup");
		} catch (Exception ex) {
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return new Long(1);
	}

	public Long insertBusObjDfSfMdataGroup(
		Long businessObjectUid,
		Long dfSfMetadataGroupUid)
		throws NEDSSDBUniqueKeyViolation {

		Integer resultCount = null;
		BusObjDfSfMdataGroupDT dt = new BusObjDfSfMdataGroupDT();
		try {
			ArrayList<Object> insertDfSfMetadataFieldGroupList = new ArrayList<Object> ();
			insertDfSfMetadataFieldGroupList.add(businessObjectUid);
			insertDfSfMetadataFieldGroupList.add(new Long(1));
			insertDfSfMetadataFieldGroupList.add(dfSfMetadataGroupUid);

			resultCount =
				(Integer) preparedStmtMethod(dt,
					insertDfSfMetadataFieldGroupList,
					this.INSERT_BUS_OBJ_DF_SF_MDATA_GROUP,
					"INSERT");
			if (resultCount.longValue() != 1)
				throw new NEDSSSystemException("insert failed for DfSfMetadataGroup");
		} catch (Exception ex) {
			BusObjDfSfMdataGroupDT temp =
				this.selectBusObjDfSfMdataGroup(businessObjectUid);
			if (temp != null){
				//gst: 2017 - change to warn, occurs too many times in log and is expected
				logger.warn("Business object uid already exists in bus_obj_df_sf_mdata_group table. "+ex.getMessage(), ex);
				throw new NEDSSDBUniqueKeyViolation("Business object uid already exists in bus_obj_df_sf_mdata_group table.");
			}
			else {
				logger.fatal("Exception  = "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
		}
		return new Long(1);

	}

	@SuppressWarnings("unchecked")
	public DfSfMetadataGroupDT selectDfSfMetadataGroupDTByBusinessUid(Long businessObjectUid) {
		
		String query = SELECT_DF_SF_METADATA_GROUP;
		DfSfMetadataGroupDT dFsFMetadataGroupDT = new DfSfMetadataGroupDT();
		try{
			ArrayList<Object> bindVarList = new ArrayList<Object> ();
			bindVarList.add(businessObjectUid);
			
	
			ArrayList<Object> list =
				(ArrayList<Object> ) preparedStmtMethod(dFsFMetadataGroupDT,
					bindVarList,
					query,
					NEDSSConstants.SELECT);
	
			if (list.size() > 0)
				dFsFMetadataGroupDT = (DfSfMetadataGroupDT) list.get(0);
		}catch(Exception ex){
			logger.error("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return dFsFMetadataGroupDT;

	}

	@SuppressWarnings("unchecked")
	public DfSfMetadataGroupDT selectDfSfMetadataGroupDTByGroupName(String groupName) {
		String query = SELECT_DF_SF_METADATA_GROUP_BY_GROUP_NAME;
		DfSfMetadataGroupDT dFsFMetadataGroupDT = new DfSfMetadataGroupDT();
		try{
			ArrayList<Object> bindVarlist = new ArrayList<Object> ();
			bindVarlist.add(groupName);
			
			ArrayList<Object> list =
				(ArrayList<Object> ) preparedStmtMethod(dFsFMetadataGroupDT,
					bindVarlist,
					query,
					NEDSSConstants.SELECT);
	
			if (list.size() > 0)
				dFsFMetadataGroupDT = (DfSfMetadataGroupDT) list.get(0);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return dFsFMetadataGroupDT;

	}

	@SuppressWarnings("unchecked")
	public DfSfMetadataGroupDT selectDfSfMetadataGroupDTByGroupUid(Long uid) {
		String query = SELECT_DF_SF_METADATA_GROUP_BY_GROUP_UID;
		DfSfMetadataGroupDT dFsFMetadataGroupDT = new DfSfMetadataGroupDT();
		try{
			ArrayList<Object> bindVarlist = new ArrayList<Object> ();
			bindVarlist.add(uid);
			
			ArrayList<Object> list =
				(ArrayList<Object> ) preparedStmtMethod(dFsFMetadataGroupDT,
					bindVarlist,
					query,
					NEDSSConstants.SELECT);
	
			if (list.size() > 0)
				dFsFMetadataGroupDT = (DfSfMetadataGroupDT) list.get(0);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return dFsFMetadataGroupDT;

	}

	@SuppressWarnings("unchecked")
	public BusObjDfSfMdataGroupDT selectBusObjDfSfMdataGroup(Long businessObjectUid) {
		BusObjDfSfMdataGroupDT busObjDfSfMdataGroupDT = new BusObjDfSfMdataGroupDT();
		String query = SELECT_BUS_OBJ_DF_SF_MDATA_GROUP;
		try{
			ArrayList<Object> bindVarlist = new ArrayList<Object> ();
			bindVarlist.add(businessObjectUid);
			
			ArrayList<Object> list =
				(ArrayList<Object> ) preparedStmtMethod(busObjDfSfMdataGroupDT,
					bindVarlist,
					query,
					NEDSSConstants.SELECT);
	
			if (list.size() > 0)
				busObjDfSfMdataGroupDT = (BusObjDfSfMdataGroupDT) list.get(0);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return busObjDfSfMdataGroupDT;

	}

	/**
	 * @param businessUid
	 * @param groupUid
	 */
	public void updateBusObjDfSfMdataGroup(Long businessUid, Long groupUid)
		throws NEDSSConcurrentDataException {

		ArrayList<Object> valueList = new ArrayList<Object> ();
		try {
			// select first
			BusObjDfSfMdataGroupDT dt = selectBusObjDfSfMdataGroup(businessUid);
			if (dt.getBusinessObjectUid() == null) {
				// since we do not support delete, there are no data concurrency issue
				// we treat it as a insert
				try {
					insertBusObjDfSfMdataGroup(businessUid, groupUid);
					return;
				} catch (NEDSSDBUniqueKeyViolation ue) {
					// Sorry, some one beats you to it.
					logger.fatal("The group relationship was already created"+ue.getMessage(), ue);
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");

				}
			}

			Integer versionCtrlNbr =
				new Integer(dt.getVersionCtrlNbr().intValue() + 1);

			valueList.add(groupUid);
			valueList.add(versionCtrlNbr);
			valueList.add(businessUid);
			valueList.add(dt.getVersionCtrlNbr());
			int resultCount =
				((Integer) preparedStmtMethod(null,
					valueList,
					UPDATE_BUS_GROUP_RELATIONSHIP,
					NEDSSConstants.UPDATE))
					.intValue();
			if (resultCount != 1) {
				logger.error(
					"Error: none or more than one CdfSubformImportLog updated at a time, "
						+ "resultCount = "
						+ resultCount);
				throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");

			}
		} catch (Exception e) {
			logger.fatal(
				"Exception in CdfSubformImportLog.update(): Error = " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}

	}

}