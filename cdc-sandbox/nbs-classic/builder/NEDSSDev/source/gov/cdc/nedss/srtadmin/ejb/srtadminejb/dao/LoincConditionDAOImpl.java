package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.LoincConditionDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class LoincConditionDAOImpl extends DAOBase{

	private static final LogUtils logger = new LogUtils(LoincConditionDAOImpl.class
			.getName());

	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	private static final String GET_EXISTING_LIONC_CONDITION_LINK_SQL = "SELECT"
		+ " condition_cd \"conditionCd\","
		+ " loinc_cd \"loincCd\","
		+ " disease_nm  \"diseaseNm\" "
		+ " FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE
		+ "..loinc_condition";

	private static final String CREATE_LIONCCOND_LINK_SQL = "INSERT INTO "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
		+ "..loinc_condition(loinc_cd, "
		+ "condition_cd, disease_nm, effective_from_time, status_cd, status_time ) values(?,?,?,?,?,?)";



	@SuppressWarnings("unchecked")
	public Collection<Object> findLoincConditionLink(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {

		String codeSql =GET_EXISTING_LIONC_CONDITION_LINK_SQL;

        codeSql = codeSql + whereClause;

        LoincConditionDT loincConditionDT = new LoincConditionDT();
		ArrayList<Object>  loincConditionDTCollection = new ArrayList<Object> ();

		try {
			loincConditionDTCollection = (ArrayList<Object> ) preparedStmtMethod(loincConditionDT, loincConditionDTCollection, codeSql, NEDSSConstants.SELECT, NEDSSConstants.SRT);

		} catch (Exception ex) {
			logger.fatal("whereClause: "+whereClause+" Exception in findLocallyDefinedTest: ERROR = " + ex.getMessage() ,ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return loincConditionDTCollection;

	}

	public void createLoincConditionLink(LoincConditionDT loincConditionDT) throws NEDSSDAOSysException, NEDSSSystemException {

		String codeSql = CREATE_LIONCCOND_LINK_SQL;

	   
	     ArrayList<Object>  paramList = new ArrayList<Object> ();
		 paramList.add(loincConditionDT.getLoincCd());
		 paramList.add(loincConditionDT.getConditionCd());
		 paramList.add(loincConditionDT.getDiseaseNm());
		 paramList.add(new Timestamp(new Date().getTime()));
		 paramList.add(NEDSSConstants.STATUS_ACTIVE);
		 paramList.add(new Timestamp(new Date().getTime()));

		 try {
				preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.CREATE, NEDSSConstants.SRT);

			} catch (Exception ex) {
				logger.fatal("Exception in createLoincConditionLink: ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}

	}


}
