package gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.LoincDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

public class ManageLoincsDAOImpl extends DAOBase{
	private static final LogUtils logger = new LogUtils(ManageLoincsDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();



	private static final String UPDATE_LOINC_SQL = "UPDATE "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..loinc_code"
		//+ " SET	seq_num=?, "
		+ " SET component_name=?,"
		+ " property=?, " + "time_aspect=?,"
		+ " system_cd=?, " + "scale_type=?,"
		+ " method_type=?, " + "display_nm=?,"
		+ " related_class_cd=?, " + "pa_derivation_exclude_cd=? "
		+ " WHERE loinc_cd=?";

	private static final String CREATE_LOINC_SQL = "INSERT INTO "
		+ NEDSSConstants.SYSTEM_REFERENCE_TABLE
		+ "..loinc_code(loinc_cd, component_name,"
		+ " property, time_aspect,"
		+ " system_cd, scale_type,"
	    + " method_type, display_nm, "
	    + " related_class_cd,  effective_from_time,"
	    + " pa_derivation_exclude_cd "
	    + " ) Values(?,?,?,?,?,?,?,?,?,?,?)";



public void updateLoincs(LoincDT loincDT) throws NEDSSDAOSysException, NEDSSSystemException {

	String codeSql =UPDATE_LOINC_SQL;

    String paDerExcludeCd = loincDT.getPaDerivationExcludeCd();

	ArrayList<Object>  paramList = new ArrayList<> ();
	paramList.add(loincDT.getComponentName());
	paramList.add(loincDT.getProperty());
	paramList.add(loincDT.getTime_aspect());
	paramList.add(loincDT.getSystem_cd());
	paramList.add(loincDT.getScale_type());
	paramList.add(loincDT.getMethod_type());
	paramList.add(loincDT.getDisplay_name());
	paramList.add(loincDT.getRelated_class_cd());
	if(paDerExcludeCd.equals("1")) paramList.add("Y");
	else  paramList.add("N");
	paramList.add(loincDT.getLoincCd());

	try {
		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);

	} catch (Exception ex) {
		logger.fatal("Exception in updateLoincs: ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}
}

public void createLoincs(LoincDT loincDT){

	String codeSql = CREATE_LOINC_SQL;

	 String paDevExcludCd = loincDT.getPaDerivationExcludeCd() == null ? "" : loincDT.getPaDerivationExcludeCd();
	 ArrayList<Object>  paramList = new ArrayList<> ();
	    paramList.add(loincDT.getLoincCd());
	    paramList.add(loincDT.getComponentName());
		paramList.add(loincDT.getProperty());
		paramList.add(loincDT.getTime_aspect());
		paramList.add(loincDT.getSystem_cd());
		paramList.add(loincDT.getScale_type());
		paramList.add(loincDT.getMethod_type());
		paramList.add(loincDT.getDisplay_name());
		paramList.add(loincDT.getRelated_class_cd());
		paramList.add(new Timestamp(new Date().getTime()));
		if(paDevExcludCd.equals("1"))paramList.add("Y");
		else  paramList.add("N");



	 try {
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.CREATE, NEDSSConstants.SRT);

		} catch (Exception ex) {
			logger.fatal("Exception in createLoincs: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
}
}
