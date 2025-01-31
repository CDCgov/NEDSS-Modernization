package gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.DefinedFieldMetaDataDisplayOrderComparator;
import gov.cdc.nedss.ldf.dt.LdfPageSetDT;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup;
import gov.cdc.nedss.ldf.group.DefinedFieldSubformGroupHelper;
import gov.cdc.nedss.ldf.importer.dt.CdfSubformImportLogDT;
import gov.cdc.nedss.ldf.subform.dao.CustomSubformMetadataDAO;
import gov.cdc.nedss.ldf.subform.dt.CustomSubformMetadataDT;
import gov.cdc.nedss.systemservice.sqlscript.DefinedFieldSQLQuery;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 *
 */
public class StateDefinedFieldMetaDataDAOImpl extends DAOBase {

	  static final LogUtils logger = new LogUtils(StateDefinedFieldMetaDataDAOImpl.class.getName());

	/**
	 * No arg constructor
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public StateDefinedFieldMetaDataDAOImpl()
		throws NEDSSDAOSysException, NEDSSSystemException {
	}

	/**
	 *
	 * @param java.lang.Object obj
	 * @return java.lang.long
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 * @throws NEDSSSystemException
	 * @throws NEDSSSystemException
	 */
	public long create(Object obj)
		throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			long ldfUID = -1;
			ldfUID = insertMetaData((StateDefinedFieldMetaDataDT) obj);
			((StateDefinedFieldMetaDataDT) obj).setItNew(false);
			((StateDefinedFieldMetaDataDT) obj).setItDirty(false);
			return ldfUID;
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	} //create

	/**
	 *
	 * @param java.lang.Object obj
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 */
	public void store(Object obj)
		throws NEDSSSystemException,
			NEDSSConcurrentDataException {
		try{
			StateDefinedFieldMetaDataDT sdfMData =
				(StateDefinedFieldMetaDataDT) obj;
			if (sdfMData.itDirty()) {
				updateMetaData(sdfMData);
	
			}
		}catch(NEDSSConcurrentDataException ex){
			logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
			throw new NEDSSConcurrentDataException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	} //store

	/**
	 *
	 * @param StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 */
	private void updateMetaData(StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT)
		throws NEDSSSystemException, NEDSSConcurrentDataException {

		try {

			if (stateDefinedFieldMetaDataDT != null) {

				// increase the version control number by 1
				Integer versionCtrlNbr =
					stateDefinedFieldMetaDataDT.getVersionCtrlNbr() == null
						? new Integer(1)
						: new Integer(
							stateDefinedFieldMetaDataDT
								.getVersionCtrlNbr()
								.intValue()
								+ 1);
				stateDefinedFieldMetaDataDT.setVersionCtrlNbr(versionCtrlNbr);

				ArrayList<Object> ldfMetaUpdList = new ArrayList<Object> ();
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getActiveInd());
				//1
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getAdminComment());
				//2
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getBusinessObjNm());
				//3
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getCategoryType());
				//4
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getCdcNationalId());
				//5
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getClassCd());
				//6
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getCodeSetNm());
				//7
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getConditionCd());
				//8
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getConditionDescTxt());
				//9
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getDataType());
				//10
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getDisplayOrderNbr());
				//11
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getFieldSize());
				//12
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getLabelTxt());
				//13
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getValidationTxt());
				//14
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getValidationJscriptTxt());
				//14
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getLdfPageId());
				//15
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getRequiredInd());
				//16

				java.util.Date dateTime = new java.util.Date();
				Timestamp systemTime = new Timestamp(dateTime.getTime());
				ldfMetaUpdList.add(systemTime); //17

				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getRecordStatusCd());
				//18

				//Added following two lines 11.25.03
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getDeploymentCd());
				//19
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getStateCd());
				//20

				//below 6 new attributes added to MetaDataTable to support CDFs and CustomSubForms (01/12/04)
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getCustomSubformMetadataUid());
				//21
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getHtmlTag());
				//22
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getImportVersionNbr());
				//23
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getNndInd());
				//24
				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getLdfOid());
				//25
				ldfMetaUpdList.add(
					stateDefinedFieldMetaDataDT.getVersionCtrlNbr());
				//26

				ldfMetaUpdList.add(stateDefinedFieldMetaDataDT.getLdfUid());
				//21
				ldfMetaUpdList.add(
					new Integer(
						stateDefinedFieldMetaDataDT
							.getVersionCtrlNbr()
							.intValue()
							- 1));

				Integer resultCount =
					(Integer) preparedStmtMethod(stateDefinedFieldMetaDataDT,
						ldfMetaUpdList,
						DefinedFieldSQLQuery.UPDATE_METADATA,
						"UPDATE");
				logger.debug("updated " + resultCount + " records.");
				logger.debug(
					"Done updating stateDefinedField = "
						+ (stateDefinedFieldMetaDataDT.getLdfUid().longValue()));

				if (resultCount.longValue() < 0) {
					//logger.error("Error: none or more than one place updated at a time, " + "resultCount = " + resultCount);
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: StateDefinedFieldDAO. UpdatePlace The data have been modified by other user, please verify!");
				}
			}

		} catch (Exception sqle) {
			logger.fatal("Exception = "+sqle.getMessage(), sqle);
			throw new NEDSSSystemException(sqle.toString());

		}
	} //updateLDF

	/**
	 *
	 * @param StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT
	 * @return java.lang.long
	 * @throws NEDSSSystemException
	 * @throws NEDSSSystemException
	 */
	private long insertMetaData(StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT)
		throws NEDSSSystemException, NEDSSSystemException {

		long ldfUID = -1;
		UidGeneratorHelper uidGen = null;
		Integer resultCount = null;
		if (stateDefinedFieldMetaDataDT.getActiveInd() == null
			|| stateDefinedFieldMetaDataDT.getActiveInd().trim().equalsIgnoreCase(
				""))
			stateDefinedFieldMetaDataDT.setActiveInd("Y");
		StateDefinedFieldMetaDataDT sdm = new StateDefinedFieldMetaDataDT();
		//get LdfPageSetDT and set busObjNm and condCd to the stateDefinedMetaDataDT
		LdfPageSetDT ldfPageSetDT =
			this.getLdfPageSetDT(stateDefinedFieldMetaDataDT.getLdfPageId());
		sdm.setBusinessObjNm(ldfPageSetDT.getBusinessObjNm());
		sdm.setConditionCd(ldfPageSetDT.getConditionCd());
		try {
			uidGen = new UidGeneratorHelper();
			ldfUID =
				uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
			stateDefinedFieldMetaDataDT.setLdfUid(new Long(ldfUID));
			stateDefinedFieldMetaDataDT.setVersionCtrlNbr(new Integer(1));

			ArrayList<Object> LDFInsertList = new ArrayList<Object> ();
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getLdfUid()); //1
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getActiveInd()); //2
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getAdminComment());
			//3
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getBusinessObjNm());
			//4
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getCategoryType());
			//5
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getCdcNationalId());
			//6
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getClassCd()); //7
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getCodeSetNm()); //8
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getConditionCd());
			//9
			LDFInsertList.add(
				stateDefinedFieldMetaDataDT.getConditionDescTxt());
			//10
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getDataType()); //11
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getDisplayOrderNbr());
			//12
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getFieldSize()); //13
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getLabelTxt()); //14
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getValidationTxt());
			//15
			LDFInsertList.add(
				stateDefinedFieldMetaDataDT.getValidationJscriptTxt());
			//16
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getLdfPageId()); //15
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getRequiredInd());
			//16

			java.util.Date dateTime = new java.util.Date();
			Timestamp systemTime = new Timestamp(dateTime.getTime());
			LDFInsertList.add(systemTime); //17
			LDFInsertList.add(systemTime); //18
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getRecordStatusCd());
			//Added following two lines 11.25.03
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getDeploymentCd());
			//19
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getStateCd()); //20

			//below 6 new attributes added to MetaDataTable to support CDFs and CustomSubForms (01/12/04)
			LDFInsertList.add(
				stateDefinedFieldMetaDataDT.getCustomSubformMetadataUid());
			//21
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getHtmlTag()); //22
			LDFInsertList.add(
				stateDefinedFieldMetaDataDT.getImportVersionNbr());
			//23
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getNndInd()); //24
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getLdfOid()); //25
			LDFInsertList.add(stateDefinedFieldMetaDataDT.getVersionCtrlNbr());
			//26

			resultCount =
				(Integer) preparedStmtMethod(sdm,
					LDFInsertList,
					DefinedFieldSQLQuery.INSERT_METADATA,
					"INSERT");

		} catch (Exception ex) {

			throw new NEDSSSystemException(ex.toString());
		}
		return resultCount.longValue();
	} //insertLDF

	/**
	 *
	 * @param java.lang.Long ldfUID
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void remove(Long ldfUID)
		throws NEDSSDAOSysException, NEDSSSystemException {
		removeMetaData(ldfUID);
	}

	/**
	 *
	 * @param java.lang.Long importVersionNbr
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSAppException
	 */
	public void deleteByImportVersionNbr(Long importVersionNbr)
		throws NEDSSDAOSysException, NEDSSAppException {
		CdfSubformImportLogDT cdfSubformImportLog = new CdfSubformImportLogDT();
		ArrayList<Object> arrayList = new ArrayList<Object> ();
		arrayList.add(importVersionNbr);
		try {
			preparedStmtMethod(
				cdfSubformImportLog,
				arrayList,
				DefinedFieldSQLQuery.DELETE_BY_IMPORT_VERSION_NBR,
				NEDSSConstants.UPDATE);
		} catch (Exception ex) {
			throw new NEDSSAppException(ex.toString());
		}

	}

	/**
	 *
	 * @param java.lang.Long ldfUID
	 * @throws NEDSSSystemException
	 * @throws NEDSSSystemException
	 */
	private void removeMetaData(Long ldfUID)
		throws NEDSSSystemException, NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			//logger.debug("SQLException while obtaining database connection for deleting MetaDatas = " + nsex);
			throw new NEDSSSystemException(nsex.getMessage());
		}
		try {
			preparedStmt =
				dbConnection.prepareStatement(
					DefinedFieldSQLQuery.DELETE_METADATA);
			preparedStmt.setLong(1, ldfUID.longValue());
			resultCount = preparedStmt.executeUpdate();

			if (resultCount != 1) {
				//logger.debug("Error: cannot delete ldf from STATE_DEFINED_FIELD table!! resultCount = " + resultCount);
				throw new NEDSSSystemException("Record Not found in STATE_DEFINED_FIELD_META_DATA table to delete !!!");
			}
		} catch (SQLException sex) {
			//logger.debug("SQLException while removing metadata = " + ldfUID);
			throw new NEDSSSystemException(sex.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	} //end of removing places

	/**
	 *
	 */
	public Collection<Object>  loadMetaData(String pageID)
		throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> ldfCDFcoll = new ArrayList<Object> ();
		Collection<Object>  ldfColl = selectMetaDataByPageIdforLDF(pageID);
		Collection<Object>  cdfColl = selectMetaDataByPageIdForCDF(pageID);
		ldfCDFcoll.addAll(ldfColl);
		ldfCDFcoll.addAll(cdfColl);
		return ldfCDFcoll;
	}

	/**
	 *
	 * @param java.lang.String businessObjNm
	 * @return java.util.Collection
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByBusObjNm(String businessObjNm)
		throws NEDSSSystemException {
		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		stateDefinedFieldList.add(businessObjNm);
		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(businessObjNm);

			ArrayList<Object> ldfDmdfMetaDataList =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_LDF_AND_DMDF_METADATA_BY_BO,
					NEDSSConstants.SELECT);

			inputArgs.add(businessObjNm);
			ArrayList<Object> cdfMetaDataList =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_CDF_METADATA_BY_BO,
					NEDSSConstants.SELECT);
			CustomSubformMetadataDAO subformDAO =
				new CustomSubformMetadataDAO();
			Collection<Object>  subforms = subformDAO.select(businessObjNm);
			ArrayList<Object> subformMetaDataList = new ArrayList<Object> ();
			for (Iterator<Object> subformIterator = subforms.iterator();
				subformIterator.hasNext();
				) {
				CustomSubformMetadataDT subform =
					(CustomSubformMetadataDT) subformIterator.next();
				Long subformUid = subform.getCustomSubformMetadataUid();
				inputArgs.clear();
				inputArgs.add(subformUid);

				ArrayList<Object> sfMetaData =
					(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
						inputArgs,
						DefinedFieldSQLQuery.SELECT_METADATA_BY_SUBFORM_UID,
						NEDSSConstants.SELECT);
				if (sfMetaData != null && sfMetaData.size() > 0) {
					for (Iterator<Object> iter = sfMetaData.iterator();
						iter.hasNext();
						) {
						StateDefinedFieldMetaDataDT dt =
							(StateDefinedFieldMetaDataDT) iter.next();
						if (dt != null
							&& dt.getBusinessObjNm() != null
							&& dt.getBusinessObjNm().equalsIgnoreCase(
								businessObjNm)
							&& dt.getConditionCd() == null) {
							subformMetaDataList.add(dt);
						}

					}
				}
			}
			list.addAll(ldfDmdfMetaDataList);
			list.addAll(cdfMetaDataList);
			list.addAll(subformMetaDataList);
			Collections.sort(
				list,
				new DefinedFieldMetaDataDisplayOrderComparator());

		} catch (Exception ex) {
			//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;
	} //end of selecting place


	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByBusObjUidAndBusObjNm(
		Long busObjUID,
		String businessObjNm) {
		if (busObjUID == null)
			return selectMetaDataByBusObjNm(businessObjNm);

		//if busObjUID is not null, then execute below		
		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		DefinedFieldSubformGroupHelper helper =
			new DefinedFieldSubformGroupHelper();
		stateDefinedFieldList.add(businessObjNm);
		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(businessObjNm);
			ArrayList<Object> ldfDmdfMetaDataList =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_LDF_AND_DMDF_METADATA_BY_BO,
					NEDSSConstants.SELECT);

			DefinedFieldSubformGroup dfsGroup = helper.getGroup(busObjUID);
			
			Collection<Object>  cdfMetaDataList = new ArrayList<Object> ();
			Collection<Long>  cdfUids = dfsGroup.getDefinedFieldUids();
			
			List dts = (List)getMetaDataByUids(cdfUids);
			for (Iterator<Object> iter = dts.iterator(); iter.hasNext();) {
				StateDefinedFieldMetaDataDT dt = (StateDefinedFieldMetaDataDT) iter.next();
				if (dt != null
					&& dt.getBusinessObjNm() != null
					&& dt.getBusinessObjNm().equalsIgnoreCase(businessObjNm)
					&& dt.getConditionCd() == null) {
					cdfMetaDataList.add(dt);
				}
				
			}
			/*
			if (cdfUids != null) {
				for (Iterator<Object> anIter = cdfUids.iterator(); anIter.hasNext();) {
					Long ldfUid = (Long) anIter.next();
					StateDefinedFieldMetaDataDT dt = getMetaDataByUid(ldfUid);
					if (dt != null
						&& dt.getBusinessObjNm() != null
						&& dt.getBusinessObjNm().equalsIgnoreCase(businessObjNm)
						&& dt.getConditionCd() == null) {
						cdfMetaDataList.add(dt);
					}
				}
			}
			*/

			Collection<Long>  subformUids = dfsGroup.getSubformUids();
			ArrayList<Object> subformMetaDataList = new ArrayList<Object> ();

			if (subformUids != null) {

				for (Iterator<Long> anIter = subformUids.iterator();
					anIter.hasNext();
					) {
					inputArgs.clear();
					inputArgs.add(anIter.next());
					ArrayList<Object> sfMetaData =
						(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
							inputArgs,
							DefinedFieldSQLQuery.SELECT_METADATA_BY_SUBFORM_UID,
							NEDSSConstants.SELECT);

					if (sfMetaData != null && sfMetaData.size() > 0) {
						for (Iterator<Object> iter = sfMetaData.iterator();
							iter.hasNext();
							) {
							StateDefinedFieldMetaDataDT dt =
								(StateDefinedFieldMetaDataDT) iter.next();
							if (dt != null
								&& dt.getBusinessObjNm() != null
								&& dt.getBusinessObjNm().equalsIgnoreCase(
									businessObjNm)
								&& dt.getConditionCd() == null) {
								subformMetaDataList.add(dt);
							}

						}
					}
				}
			}

			list.addAll(ldfDmdfMetaDataList);
			list.addAll(cdfMetaDataList);
			list.addAll(subformMetaDataList);
			Collections.sort(
				list,
				new DefinedFieldMetaDataDisplayOrderComparator());

		} catch (Exception ex) {
			//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByBusObjUidAndBusObjNmAndCondition(
		Long busObjUID,
		String businessObjNm,
		String conditionCd) {
		if (busObjUID == null)
			return selectMetaDataByBusObjAndCondition(
				businessObjNm,
				conditionCd);

		//if busObjUID is not null, then execute below
		
		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		DefinedFieldSubformGroupHelper helper =
			new DefinedFieldSubformGroupHelper();
		stateDefinedFieldList.add(businessObjNm);
		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(businessObjNm);
			inputArgs.add(conditionCd);
			ArrayList<Object> ldfDmdfMetaDataList =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery
						.SELECT_LDF_AND_DMDF_METADATA_BY_BO_AND_CONDITION,
					NEDSSConstants.SELECT);

			DefinedFieldSubformGroup dfsGroup = helper.getGroup(busObjUID);
			
			Collection<Object>  cdfMetaDataList = new ArrayList<Object> ();
			Collection<Long>  cdfUids = dfsGroup.getDefinedFieldUids();
			
			List dts = (List)getMetaDataByUids(cdfUids);
			for (Iterator<Object> iter = dts.iterator(); iter.hasNext();) {
				StateDefinedFieldMetaDataDT dt = (StateDefinedFieldMetaDataDT) iter.next();
				if (dt != null
					&& dt.getBusinessObjNm() != null
					&& dt.getBusinessObjNm().equalsIgnoreCase(businessObjNm)
					&& dt.getConditionCd() != null
					&& dt.getConditionCd().equalsIgnoreCase(conditionCd)) {
					cdfMetaDataList.add(dt);
				}
				
			}
			/*
			if (cdfUids != null) {
				for (Iterator<Object> anIter = cdfUids.iterator(); anIter.hasNext();) {
					Long ldfUid = (Long) anIter.next();
					StateDefinedFieldMetaDataDT dt = getMetaDataByUid(ldfUid);
					if (dt != null
						&& dt.getBusinessObjNm() != null
						&& dt.getBusinessObjNm().equalsIgnoreCase(businessObjNm)
						&& dt.getConditionCd() != null
						&& dt.getConditionCd().equalsIgnoreCase(conditionCd)) {
						cdfMetaDataList.add(dt);
					}
				}
			}
			*/

			Collection<Long>  subformUids = dfsGroup.getSubformUids();
			ArrayList<Object> subformMetaDataList = new ArrayList<Object> ();

			if (subformUids != null) {

				for (Iterator<Long> anIter = subformUids.iterator();
					anIter.hasNext();
					) {
					inputArgs.clear();
					inputArgs.add(anIter.next());
					ArrayList<Object> sfMetaData =
						(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
							inputArgs,
							DefinedFieldSQLQuery.SELECT_METADATA_BY_SUBFORM_UID,
							NEDSSConstants.SELECT);
					if (sfMetaData != null && sfMetaData.size() > 0) {
						for (Iterator<Object> iter = sfMetaData.iterator();
							iter.hasNext();
							) {
							StateDefinedFieldMetaDataDT dt =
								(StateDefinedFieldMetaDataDT) iter.next();
							if (dt != null
								&& dt.getBusinessObjNm() != null
								&& dt.getBusinessObjNm().equalsIgnoreCase(
									businessObjNm)
								&& dt.getConditionCd() != null
								&& dt.getConditionCd().equalsIgnoreCase(
									conditionCd)) {
								subformMetaDataList.add(dt);
							}

						}
					}
				}
			}

			list.addAll(ldfDmdfMetaDataList);
			list.addAll(cdfMetaDataList);
			list.addAll(subformMetaDataList);
			Collections.sort(
				list,
				new DefinedFieldMetaDataDisplayOrderComparator());

		} catch (Exception ex) {
			//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByBusObjUidAndBusObjNmAndConditionInclusive(
		Long busObjUID,
		String businessObjNm,
		String conditionCd) {
		if (busObjUID == null)
			return selectMetaDataByBusObjAndCondition(
				businessObjNm,
				conditionCd);

		if (conditionCd == null) {
			return selectMetaDataByBusObjNm(businessObjNm);
		}

		//if busObjUID is not null, then execute below
		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		DefinedFieldSubformGroupHelper helper =
			new DefinedFieldSubformGroupHelper();
		stateDefinedFieldList.add(businessObjNm);
		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(businessObjNm);
			inputArgs.add(businessObjNm);
			inputArgs.add(conditionCd);
			ArrayList<Object> ldfDmdfMetaDataList =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery
						.SELECT_CDF_METADATA_BY_BO_AND_CONDITION_INCLUSIVE,
					NEDSSConstants.SELECT);

			DefinedFieldSubformGroup dfsGroup = helper.getGroup(busObjUID);
			
			Collection<Object>  cdfMetaDataList = new ArrayList<Object> ();
			Collection<Long>  cdfUids = dfsGroup.getDefinedFieldUids();
			
			List dts = (List)getMetaDataByUids(cdfUids);
			for (Iterator<Object> iter = dts.iterator(); iter.hasNext();) {
				StateDefinedFieldMetaDataDT dt = (StateDefinedFieldMetaDataDT) iter.next();
				if (dt != null
					&& dt.getBusinessObjNm() != null
					&& dt.getBusinessObjNm().equalsIgnoreCase(businessObjNm)
					&& (dt.getConditionCd() == null
						|| (dt.getConditionCd() != null
							&& dt.getConditionCd().equalsIgnoreCase(
								conditionCd)))) {
					cdfMetaDataList.add(dt);
				}
				
			}
			/*
			for (Iterator<Object> anIter = cdfUids.iterator(); anIter.hasNext();) {
				Long ldfUid = (Long) anIter.next();
				StateDefinedFieldMetaDataDT dt = getMetaDataByUid(ldfUid);
				if (dt != null
					&& dt.getBusinessObjNm() != null
					&& dt.getBusinessObjNm().equalsIgnoreCase(businessObjNm)
					&& (dt.getConditionCd() == null
						|| (dt.getConditionCd() != null
							&& dt.getConditionCd().equalsIgnoreCase(
								conditionCd)))) {
					cdfMetaDataList.add(dt);
				}
			}
			*/

			Collection<Long>  subformUids = dfsGroup.getSubformUids();
			ArrayList<Object> subformMetaDataList = new ArrayList<Object> ();
			for (Iterator<Long> anIter = subformUids.iterator(); anIter.hasNext();) {
				inputArgs.clear();
				inputArgs.add(anIter.next());
				ArrayList<Object> sfMetaData =
					(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
						inputArgs,
						DefinedFieldSQLQuery.SELECT_METADATA_BY_SUBFORM_UID,
						NEDSSConstants.SELECT);
				if (sfMetaData != null && sfMetaData.size() > 0) {
					for (Iterator<Object> iter = sfMetaData.iterator();
						iter.hasNext();
						) {
						StateDefinedFieldMetaDataDT dt =
							(StateDefinedFieldMetaDataDT) iter.next();
						if (dt != null
							&& dt.getBusinessObjNm() != null
							&& dt.getBusinessObjNm().equalsIgnoreCase(
								businessObjNm)
							&& (dt.getConditionCd() == null
								|| (dt.getConditionCd() != null
									&& dt.getConditionCd().equalsIgnoreCase(
										conditionCd)))) {
							subformMetaDataList.add(dt);
						}

					}
				}
			}

			list.addAll(ldfDmdfMetaDataList);
			list.addAll(cdfMetaDataList);
			list.addAll(subformMetaDataList);
			Collections.sort(
				list,
				new DefinedFieldMetaDataDisplayOrderComparator());

		} catch (Exception ex) {
			//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;

	}

	/**
	 *
	 * @return java.util.Collection
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectAllLDFMetaData() throws NEDSSSystemException {
		ArrayList<Object> list = new ArrayList<Object> ();
		ArrayList<Object> nbsQuestionLDFList = new ArrayList<Object> ();
		String aQuery="";
		
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();

			list =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_ALL_METADATA,
					NEDSSConstants.SELECT);
			
			//R2.0 - need to add retrieval of LDF for PAM's from NBS_Question tables
			aQuery = DefinedFieldSQLQuery.SELECT_ALL_LDF_METADATA_NBS_QUESTION_SQL;
			
			nbsQuestionLDFList =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					aQuery,
					NEDSSConstants.SELECT);

			Iterator it = nbsQuestionLDFList.iterator();
		    //Iterate through, translate as necessary 
		    if(nbsQuestionLDFList != null && nbsQuestionLDFList.size() > 0) {
		    	while (it.hasNext()) {
		            StateDefinedFieldMetaDataDT dt = (StateDefinedFieldMetaDataDT) it.next();
		    		
		            //Set active indicator properly (expected to be "Y" or "N"
		            if (dt.getActiveInd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE))
		            	dt.setActiveInd(NEDSSConstants.YES);
		            else
		            	dt.setActiveInd(NEDSSConstants.NO);
		            //Translate NBS_Question.data_type to state_defined_field_metadata.data_type format
		            //TODO:  Create NEDSSConstants for these once file is available to check out
		            if(dt.getDataType() != null){
		            	if (dt.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE))
		            		dt.setDataType(NEDSSConstants.LDF_DATATYPE_STRING);
		            	else if (dt.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE) || 
		            		 dt.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATETIME) || 
		            		 dt.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC) ||
		                     dt.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT))
		            	 	 dt.setDataType(NEDSSConstants.LDF_DATATYPE_STRING);
		            	else if (dt.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXTAREA))
		            		dt.setDataType(NEDSSConstants.LDF_DATATYPE_LIST_STRING);
		            	else if (dt.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_SUBHEADING))
		            	   dt.setDataType(NEDSSConstants.LDF_DATATYPE_SUBHEADING);
		            }
		            //Flag this as a new NBS_Question/NBS_Answer LDF field
		            dt.setNbsQuestionInd(NEDSSConstants.YES);
		    	}
		    }
						
			list.addAll(nbsQuestionLDFList);
		} catch (Exception ex) {
			//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;
	} //end of selecting place


	/**
	 *
	 * @param java.lang.String busObjNm
	 * @param java.lang.String conditionCd
	 * @return java.util.Collection
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByBusObjAndCondition(
		String busObjNm,
		String conditionCd)
		throws NEDSSSystemException {

		if (conditionCd == null) {
			return selectMetaDataByBusObjNm(busObjNm);
		} else {
			ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
			stateDefinedFieldList.add(conditionCd);
			ArrayList<Object> list = new ArrayList<Object> ();
			try {
				StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
					new StateDefinedFieldMetaDataDT();
				ArrayList<Object> inputArgs = new ArrayList<Object> ();
				inputArgs.add(busObjNm);
				inputArgs.add(conditionCd);

				ArrayList<Object> ldfDmdfMetaDataList =
					(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
						inputArgs,
						DefinedFieldSQLQuery
							.SELECT_LDF_AND_DMDF_METADATA_BY_BO_AND_CONDITION,
						NEDSSConstants.SELECT);

				inputArgs.clear();
				inputArgs.add(busObjNm);
				inputArgs.add(busObjNm);
				inputArgs.add(conditionCd);

				ArrayList<Object> cdfMetaDataList =
					(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
						inputArgs,
						DefinedFieldSQLQuery
							.SELECT_CDF_METADATA_BY_BO_AND_CONDITION,
						NEDSSConstants.SELECT);

				CustomSubformMetadataDAO subformDAO =
					new CustomSubformMetadataDAO();
				Collection<Object>  subforms = subformDAO.select(busObjNm, conditionCd);

				ArrayList<Object> subformMetaDataList = new ArrayList<Object> ();

				for (Iterator<Object> subformIterator = subforms.iterator();
					subformIterator.hasNext();
					) {

					CustomSubformMetadataDT subform =
						(CustomSubformMetadataDT) subformIterator.next();
					Long subformUid = subform.getCustomSubformMetadataUid();
					inputArgs.clear();
					inputArgs.add(subformUid);

					ArrayList<Object> sfMetaData =
						(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
							inputArgs,
							DefinedFieldSQLQuery.SELECT_METADATA_BY_SUBFORM_UID,
							NEDSSConstants.SELECT);
					if (sfMetaData != null && sfMetaData.size() > 0) {
						for (Iterator<Object> iter = sfMetaData.iterator();
							iter.hasNext();
							) {
							StateDefinedFieldMetaDataDT dt =
								(StateDefinedFieldMetaDataDT) iter.next();
							if (dt != null
								&& dt.getBusinessObjNm() != null
								&& dt.getBusinessObjNm().equalsIgnoreCase(
									busObjNm)
								&& dt.getConditionCd() != null
								&& dt.getConditionCd().equalsIgnoreCase(
									conditionCd)) {
								subformMetaDataList.add(dt);
							}

						}
					}
				}
				list.addAll(ldfDmdfMetaDataList);
				list.addAll(cdfMetaDataList);
				list.addAll(subformMetaDataList);
				Collections.sort(
					list,
					new DefinedFieldMetaDataDisplayOrderComparator());
			} catch (Exception ex) {
				//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
				throw new NEDSSSystemException(ex.getMessage());
			}
			return list;
		}

	} //end of selecting place

	/**
	 *
	 * @param java.lang.String busObjNm
	 * @param java.lang.String conditionCd
	 * @return java.util.Collection
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByBusObjAndConditionInclusive(
		String busObjNm,
		String conditionCd)
		throws NEDSSSystemException {

		if (conditionCd == null) {
			return selectMetaDataByBusObjNm(busObjNm);
		} else {
			ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
			stateDefinedFieldList.add(conditionCd);
			ArrayList<Object> list = new ArrayList<Object> ();
			try {
				StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
					new StateDefinedFieldMetaDataDT();
				ArrayList<Object> inputArgs = new ArrayList<Object> ();
				inputArgs.add(busObjNm);
				inputArgs.add(conditionCd);
				ArrayList<Object> ldfDmdfMetaDataList =
					(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
						inputArgs,
						DefinedFieldSQLQuery
							.SELECT_LDF_AND_DMDF_METADATA_BY_BO_AND_CONDITION_INCLUSIVE,
						NEDSSConstants.SELECT);

				ArrayList<Object> cdfMetaDataList =
					(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
						inputArgs,
						DefinedFieldSQLQuery
							.SELECT_CDF_METADATA_BY_BO_AND_CONDITION_INCLUSIVE,
						NEDSSConstants.SELECT);

				CustomSubformMetadataDAO subformDAO =
					new CustomSubformMetadataDAO();
				Collection<Object>  subforms = subformDAO.select(busObjNm, conditionCd);

				ArrayList<Object> subformMetaDataList = new ArrayList<Object> ();

				for (Iterator<Object> subformIterator = subforms.iterator();
					subformIterator.hasNext();
					) {

					CustomSubformMetadataDT subform =
						(CustomSubformMetadataDT) subformIterator.next();
					Long subformUid = subform.getCustomSubformMetadataUid();
					inputArgs.clear();
					inputArgs.add(subformUid);

					ArrayList<Object> sfMetaData =
						(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
							inputArgs,
							DefinedFieldSQLQuery.SELECT_METADATA_BY_SUBFORM_UID,
							NEDSSConstants.SELECT);
					if (sfMetaData != null && sfMetaData.size() > 0) {
						for (Iterator<Object> iter = sfMetaData.iterator();
							iter.hasNext();
							) {
							StateDefinedFieldMetaDataDT dt =
								(StateDefinedFieldMetaDataDT) iter.next();
							if (dt != null
								&& dt.getBusinessObjNm() != null
								&& dt.getBusinessObjNm().equalsIgnoreCase(
									busObjNm)
								&& (dt.getConditionCd() == null
									|| (dt.getConditionCd() != null
										&& dt.getConditionCd().equalsIgnoreCase(
											conditionCd)))) {
								subformMetaDataList.add(dt);
							}

						}
					}
				}
				list.addAll(ldfDmdfMetaDataList);
				list.addAll(cdfMetaDataList);
				list.addAll(subformMetaDataList);
				Collections.sort(
					list,
					new DefinedFieldMetaDataDisplayOrderComparator());
			} catch (Exception ex) {
				//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
				throw new NEDSSSystemException(ex.getMessage());
			}
			return list;
		}

	} //end of selecting place

	/**
	 *
	 * @param classCd - is a state specific or CDC specific identifier
	 * @param activeInd - indicator for display of LDFs on the UI
	 * @return Collection<Object>  of StateDefinedFieldMetaData based on the specific condition.
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByClassCdAndActiveInd(
		String classCd,
		String activeInd)
		throws NEDSSSystemException {

		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(classCd);
			inputArgs.add(activeInd);

			list =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery
						.SELECT_METADATA_BY_CLASS_CD_AND_ACTIVE_IND,
					NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.debug(
				"Exception inside selectMetaDataByClassCdAndActiveInd = "
					+ ex.toString());
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;

	} //end of selecting place

	/**
	 *
	 * @param classCd - is a state specific or CDC specific identifier
	 * @return Collection<Object>  of StateDefinedFieldMetaData based on the specific condition.
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByClassCd(String classCd)
		throws NEDSSSystemException {

		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(classCd);

			list =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_METADATA_BY_CLASS_CD,
					NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.debug(
				"Exception inside selectMetaDataByClassCdAndActiveInd = "
					+ ex.toString());
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;

	} //end of selecting place

	/**
	   *
	   * @param java.lang.String pageId
	   * @return java.util.Collection
	   * @throws NEDSSSystemException
	   */
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByPageIdForCDF(String pageId)
		throws NEDSSSystemException {
		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		stateDefinedFieldList.add(pageId);
		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(pageId);
			//preparedStmtMethod(stateDefinedFieldMetaDataDT, stateDefinedFieldList, SELECT_METADATA, NEDSSConstants.SELECT);
			list =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_METADATA_BY_PAGEID_CDF,
					NEDSSConstants.SELECT);
		} catch (Exception ex) {
			//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;
	} //end of selecting place

	/**
	 *
	 * @param java.lang.String pageId
	 * @return java.util.Collection
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataByPageIdforLDF(String pageId)
		throws NEDSSSystemException {
		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		stateDefinedFieldList.add(pageId);
		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(pageId);
			//preparedStmtMethod(stateDefinedFieldMetaDataDT, stateDefinedFieldList, SELECT_METADATA, NEDSSConstants.SELECT);
			list =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_METADATA_BY_PAGEID,
					NEDSSConstants.SELECT);
		} catch (Exception ex) {
			//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;
	} //end of selecting place

	/**
	 *
	 * @param java.lang.String pageId
	 * @return java.lang.Integer
	 * @throws NEDSSSystemException
	 */
	public Integer selectCountByPageId(String pageId)
		throws NEDSSSystemException {
		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		stateDefinedFieldList.add(pageId);
		ArrayList<Object> list = new ArrayList<Object> ();
		Integer ldfDmdfcount = null;
		Integer cdfCount = null;
		Integer subformCdfCount = null;
		int count = 0;
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(pageId);

			//For new LDFs, do the count by querying the NBSQuestionMetadata tables
			  if(pageId.equals("53") || pageId.equals("33") || pageId.equals("300")) {
				  String sql = "SELECT count(*) FROM NBS_UI_Metadata WHERE ldf_page_id = ? AND record_status_cd='Active' ";
				  Integer localFieldCount =  (Integer) preparedStmtMethod(stateDefinedFieldMetaDataDT,inputArgs,sql, NEDSSConstants.SELECT_COUNT);
				  return localFieldCount;
			  }
			
			//ldf - dmdf
			ldfDmdfcount =
				(Integer) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery
						.SELECT_LDF_DMDF_METADATA_COUNT_BY_PAGEID,
					NEDSSConstants.SELECT_COUNT);
			//cdf
			cdfCount =
				(Integer) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_CDF_METADATA_COUNT_BY_PAGEID,
					NEDSSConstants.SELECT_COUNT);

			//subform cdf
			inputArgs.add(pageId);
			subformCdfCount =
				(Integer) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery
						.SELECT_SUBFORM_CDF_METADATA_COUNT_BY_PAGEID,
					NEDSSConstants.SELECT_COUNT);

			count =
				ldfDmdfcount.intValue()
					+ cdfCount.intValue()
					+ subformCdfCount.intValue();

		} catch (Exception ex) {
			//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return new Integer(count);
	} //end of selecting place

	/**
	 *
	 * @param java.lang.String ldfPageId
	 * @return LdfPageSetDT
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public LdfPageSetDT getLdfPageSetDT(String ldfPageId)
		throws NEDSSSystemException {
		ArrayList<Object> arrayList = new ArrayList<Object> ();
		arrayList.add(ldfPageId);
		ArrayList<Object> list = new ArrayList<Object> ();

		try {
			LdfPageSetDT dt = new LdfPageSetDT();
			String aQuery = null;
			aQuery = DefinedFieldSQLQuery.SELECT_LDF_PAGE_SET_SQL;
			
			//preparedStmtMethod(stateDefinedFieldMetaDataDT, stateDefinedFieldList, SELECT_METADATA, NEDSSConstants.SELECT);
			arrayList =
				(ArrayList<Object> ) preparedStmtMethod(dt,
					arrayList,
					aQuery,
					NEDSSConstants.SELECT);
			if (arrayList.size() < 1) {
				throw new NEDSSSystemException(
					"no matching ldf_page_set record for page_id in system reference table:"
						+ ldfPageId);
			}
		} catch (Exception ex) {
			//logger.debug("Exception while selecting  StateDefinedField id = " + ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return (LdfPageSetDT) arrayList.get(0);
	} //end of selecting place

	/**
	 * selectMetaDataBySubformUid returns a Collection<Object>  of LDF MetaData for a given CUSTOM_SUBFORM_METADATA_UID
	 * @param java.lang.Long subformUid
	 * @return java.util.Collection
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  selectMetaDataBySubformUid(Long subformUid)
		throws NEDSSSystemException {
		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		stateDefinedFieldList.add(subformUid);
		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(subformUid);

			list =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_METADATA_BY_SUBFORM_UID,
					NEDSSConstants.SELECT);
		} catch (Exception ex) {
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;
	} //end of selecting place

	/**
	  * selectMetaDataBySubformUid returns a Collection<Object>  of LDF MetaData for a given CUSTOM_SUBFORM_METADATA_UID
	  * @param subformUid
	  * @return
	  * @throws NEDSSSystemException
	  */
	@SuppressWarnings("unchecked")
	public StateDefinedFieldMetaDataDT selectMetaDataByLdfOid(
		String ldfOid,
		Long importVersionNbr)
		throws NEDSSSystemException {
		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(ldfOid);
			inputArgs.add(importVersionNbr);
			list =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_METADATA_BY_LDF_OID,
					NEDSSConstants.SELECT);
			if (list != null && list.size() >= 1) {
				stateDefinedFieldMetaDataDT =
					(StateDefinedFieldMetaDataDT) list.get(0);
				return stateDefinedFieldMetaDataDT;
			}
		} catch (Exception ex) {
			throw new NEDSSSystemException(ex.getMessage());
		}
		return null;
	} //end of selecting place

	@SuppressWarnings("unchecked")
	public StateDefinedFieldMetaDataDT getMetaDataByUid(Long ldfUid)
		throws NEDSSSystemException {

		ArrayList<Object> stateDefinedFieldList = new ArrayList<Object> ();
		ArrayList<Object> list = new ArrayList<Object> ();
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			inputArgs.add(ldfUid);

			list =
				(ArrayList<Object> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					DefinedFieldSQLQuery.SELECT_METADATA_BY_LDF_UID,
					NEDSSConstants.SELECT);
			if (list != null && list.size() >= 1) {
				stateDefinedFieldMetaDataDT =
					(StateDefinedFieldMetaDataDT) list.get(0);
				return stateDefinedFieldMetaDataDT;
			}
		} catch (Exception ex) {
			throw new NEDSSSystemException(ex.getMessage());
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public Collection<Long>  getMetaDataByUids(Collection<Long> ldfUids)
		throws NEDSSSystemException {
		
		
		ArrayList<Long> list = new ArrayList<Long> ();
		if(ldfUids == null || ldfUids.size()==0){
			return list;
		}
		try {
			StateDefinedFieldMetaDataDT stateDefinedFieldMetaDataDT =
				new StateDefinedFieldMetaDataDT();
			ArrayList<Object> inputArgs = new ArrayList<Object> ();
			
			StringBuffer query = new StringBuffer(DefinedFieldSQLQuery.SELECT_ALL_METADATA);
			query.append(" where ldf_uid in (");
			int index = 0;
			int numberOfUids = ldfUids.size();
			for (Iterator<Long> iter = ldfUids.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				query.append(element);
				index ++;
				if(index == 999 && index < numberOfUids){
					// XZ, Oracle has a limitation so the size of ldfUids can not exceed 999.
					query.append(") or ldf_uid in (");
					index = 0;
					numberOfUids = numberOfUids - 999;
				}
				else if (index == numberOfUids){
					query.append(")");
				}
				else {
					query.append(",");
				}
			}

			list =
				(ArrayList<Long> ) preparedStmtMethod(stateDefinedFieldMetaDataDT,
					inputArgs,
					query.toString(),
					NEDSSConstants.SELECT);
		} catch (Exception ex) {
			throw new NEDSSSystemException(ex.getMessage());
		}
		return list;

	}

} //StateDefinedFieldDAO
