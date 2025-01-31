package gov.cdc.nedss.ldf.group;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.ldf.group.ejb.dao.*;
import gov.cdc.nedss.ldf.group.ejb.dt.*;
import java.util.*;

import gov.cdc.nedss.util.*;
import gov
	.cdc
	.nedss
	.ldf
	.ejb
	.ldfmetadataejb
	.dao
	.StateDefinedFieldMetaDataDAOImpl;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;

public class DefinedFieldSubformGroupHelper {

	/**
	 * @param businessUid
	 * @return
	 */
	public DefinedFieldSubformGroup getGroup(Long businessUid)
		throws NEDSSAppException {
		// TODO
		// 1. Use DAO to query groupName throw JOIN between
		// BUS_OBJ_DF_SF_MDATA_GROUP and DF_SF_METADATA_GROUP
		// tables
		// 2. return the group based on the name
		DefinedFieldSubformGroupDAOImpl dao =
			new DefinedFieldSubformGroupDAOImpl();
		DfSfMetadataGroupDT dFsFMetadataGroupDT =
			dao.selectDfSfMetadataGroupDTByBusinessUid(businessUid);
		gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup group =
			new gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup(
				dFsFMetadataGroupDT.getGroupName());
		return group;
	}

	/**
	 * @param businessUid
	 * @param group
	 * @return
	 */
	public Long createBusinessObjectGroupRelationship(
		Long businessUid,
		List<Object> ldfUids)
		throws NEDSSAppException {

		Long groupUid =	persistGroup(ldfUids);

		DefinedFieldSubformGroupDAOImpl dao =
			new DefinedFieldSubformGroupDAOImpl();
		try {
			dao.insertBusObjDfSfMdataGroup(businessUid, groupUid);
		} catch (NEDSSDBUniqueKeyViolation e) {
			// do nothing

		} catch (Exception e) {
			throw new NEDSSAppException(e.getMessage());

		}
		return groupUid;
	}
	/**
	 * @param ldfUids
	 */
	private Long persistGroup(List<Object>ldfUids) throws NEDSSAppException {

		DefinedFieldSubformGroup group = generateGroup(ldfUids);

		DefinedFieldSubformGroupDAOImpl dao =
			new DefinedFieldSubformGroupDAOImpl();
		try {
			// we do this step since SQL server cannot add unique
			// constraint to column larger than 900 characters
			DfSfMetadataGroupDT dFSfMetadataGroupDT =
				dao.selectDfSfMetadataGroupDTByGroupName(group.getName());
			if(dFSfMetadataGroupDT != null && dFSfMetadataGroupDT.getUid() != null) {
				return dFSfMetadataGroupDT.getUid();
			}

			// try to insert group first, if exists already, will get into
			// first catch NEDSSDBUniqueKeyViolation block
			Long uid = dao.insertDfSfMetadataGroup(group.getName());

			// insert into field group table
			List definedFieldUidList = group.getDefinedFieldUids();
			Iterator definedFieldUidListIter = definedFieldUidList.iterator();
			while (definedFieldUidListIter.hasNext()) {
				Long definedFieldUid = (Long) definedFieldUidListIter.next();
				dao.insertDfSfMdataFieldGroup(
					uid,
					definedFieldUid,
					NEDSSConstants.DF_DATA_TYPE);
			}

			List subformUidList = group.getSubformUids();
			Iterator subformUidListIter = subformUidList.iterator();
			while (subformUidListIter.hasNext()) {
				Long subformUid = (Long) subformUidListIter.next();
				dao.insertDfSfMdataFieldGroup(
					uid,
					subformUid,
					NEDSSConstants.SF_DATA_TYPE);
			}
			return uid;
		} catch (NEDSSDBUniqueKeyViolation e) {
			// somebody just created the group
			try {
				DfSfMetadataGroupDT dFSfMetadataGroupDT =
					dao.selectDfSfMetadataGroupDTByGroupName(group.getName());
				return dFSfMetadataGroupDT.getUid();
			} catch (Exception e2) {
				throw new NEDSSAppException(e.getMessage());
			}

		} catch (Exception e) {
			throw new NEDSSAppException(e.getMessage());

		}

	}

	private DefinedFieldSubformGroup generateGroup(List<Object>ldfUids)
		throws NEDSSAppException {


		List<Object> definedFieldUids = new ArrayList<Object> ();
		List<Object> subformUids = new ArrayList<Object> ();
		Iterator<Object> iter = ldfUids.iterator();
		while (iter.hasNext()) {
			Long uidTemp = (Long) iter.next();
			StateDefinedFieldMetaDataDAOImpl sdfmd =
				new StateDefinedFieldMetaDataDAOImpl();
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				sdfmd.getMetaDataByUid(uidTemp);

			if (stateDefinedFieldMetaDataDT.getLdfOid() != null
				&& stateDefinedFieldMetaDataDT.getCustomSubformMetadataUid()
					== null && !definedFieldUids.contains(uidTemp)){
						definedFieldUids.add(uidTemp);
					}
			else if (
				stateDefinedFieldMetaDataDT.getLdfOid() != null
					&& stateDefinedFieldMetaDataDT.getCustomSubformMetadataUid()
						!= null
						&& !subformUids.contains(stateDefinedFieldMetaDataDT.getCustomSubformMetadataUid())){

							subformUids.add(stateDefinedFieldMetaDataDT.getCustomSubformMetadataUid());

						}
		}

		gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup group =
			new gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup(
				definedFieldUids,
				subformUids);
		return group;
	}

	/**
	 * @param businessUid
	 * @param ldfUids
	 */
	public Long updateBusinessObjectGroupRelationship(Long businessUid, List<Object> ldfUids) throws NEDSSAppException {

		Long groupUid =	persistGroup(ldfUids);

		DefinedFieldSubformGroupDAOImpl dao =
			new DefinedFieldSubformGroupDAOImpl();
		try {
			dao.updateBusObjDfSfMdataGroup(businessUid, groupUid);
		}  catch (Exception e) {
			throw new NEDSSAppException(e.getMessage());

		}
		return groupUid;

	}

	/**
	 * @param businessUid
	 * @param groupUid
	 * @return
	 */
	public Long createBusinessObjectGroupRelationship(Long businessUid, Long groupUid) throws NEDSSAppException{

		DefinedFieldSubformGroupDAOImpl dao =
			new DefinedFieldSubformGroupDAOImpl();
		DfSfMetadataGroupDT dt = dao.selectDfSfMetadataGroupDTByGroupUid(groupUid);
		if(dt == null || dt.getUid() == null){
			throw new NEDSSAppException("The group " + groupUid + " does not exist.");
		}
		try {
			dao.insertBusObjDfSfMdataGroup(businessUid, groupUid);
		} catch (NEDSSDBUniqueKeyViolation e) {
			// do nothing

		} catch (Exception e) {
			throw new NEDSSAppException(e.getMessage());

		}
		return groupUid;
	}

	/**
	 * @param businessUid
	 * @param ldfUid
	 */
	public void updateBusinessObjectGroupRelationship(Long businessUid, Long groupUid) throws NEDSSAppException{

		DefinedFieldSubformGroupDAOImpl dao =
			new DefinedFieldSubformGroupDAOImpl();
		DfSfMetadataGroupDT dt = dao.selectDfSfMetadataGroupDTByGroupUid(groupUid);
		if(dt == null || dt.getUid() == null){
			throw new NEDSSAppException("The group " + groupUid + " does not exist.");
		}
		try {
			dao.updateBusObjDfSfMdataGroup(businessUid, groupUid);
		}  catch (Exception e) {
			throw new NEDSSAppException(e.getMessage());

		}
	}

	/**
	 * @param ldfUids
	 */
	public Long createGroup(List<Object>ldfUids) throws NEDSSAppException{
		return persistGroup(ldfUids);

	}
}
