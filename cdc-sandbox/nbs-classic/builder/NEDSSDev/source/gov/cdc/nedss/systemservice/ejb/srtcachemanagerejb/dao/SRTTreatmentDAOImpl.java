package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dao;
/**
 *
 * <p>Title: SRTTreatmentDAOImpl </p>
 * <p>Description: Data Access Object to the treatment srt data.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Mark Hankey
 * @version 1.0
 */

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dao.StateDefinedFieldDataDAOImpl;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTTreatmentConditionDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Collection;



public class SRTTreatmentDAOImpl extends DAOBase {
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  static final LogUtils logger = new LogUtils((SRTTreatmentDAOImpl.class).getName());
 
  private String treatmentMapSQL =
      " select c.PROG_AREA_CD \"progAreaCd\" ,"+
      " tcm.CONDITION_CD \"conditionCd\" ,"+
      " t.TREATMENT_CD \"treatmentCd\" ,"+
      " t.TREATMENT_DESC_TXT \"treatmentDescTxt\" "+
      " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..treatment_condition tcm, "+
      " "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..treatment_code t, "+
      " "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..condition_code c "+
      " where c.CONDITION_CD = tcm.CONDITION_CD and "+
      " tcm.TREATMENT_CD = t.TREATMENT_CD and "+
      " t.TREATMENT_TYPE_CD = 'C' "+
      " order by t.TREATMENT_DESC_TXT ";

  private String treatmentAllSQL =
    " select t.TREATMENT_CD \"treatmentCd\" ,"+
    " t.TREATMENT_DESC_TXT \"treatmentDescTxt\" "+
    " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..treatment_code t "+
    " where t.TREATMENT_TYPE_CD = 'C' "+
    " order by t.TREATMENT_DESC_TXT ";


  private String drugMapSQL =
      " select c.PROG_AREA_CD \"progAreaCd\" ,"+
      " tcm.CONDITION_CD \"conditionCd\" ,"+
      " t.TREATMENT_CD \"treatmentCd\" ,"+
      " t.TREATMENT_DESC_TXT \"treatmentDescTxt\" "+
      " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..treatment_condition tcm, "+
      " "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..treatment_code t, "+
      " "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..condition_code c "+
      " where c.CONDITION_CD = tcm.CONDITION_CD and "+
      " tcm.TREATMENT_CD = t.TREATMENT_CD and "+
      " t.TREATMENT_TYPE_CD = 'D' "+
      " order by t.TREATMENT_DESC_TXT ";

 
  private String drugAllSQL =
    " select t.TREATMENT_CD  \"treatmentCd\" ,"+
    " t.TREATMENT_DESC_TXT  \"treatmentDescTxt\" "+
    " from "+NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..treatment_code t "+
    " where t.TREATMENT_TYPE_CD = 'D' "+
    " order by t.TREATMENT_DESC_TXT ";





  public SRTTreatmentDAOImpl() {
  }



  /*Returns a collection of TreatmentDTs*/
  /**
   *
   * @return Collection
   */
  @SuppressWarnings("unchecked")
public Collection<Object>  getTreatments(){
	ArrayList<Object> list = new ArrayList<Object>();
    String query = null;
    try{
	   query = treatmentMapSQL;
	   ///end of else
	    SRTTreatmentConditionDT srtTreatmentConditionDT = new SRTTreatmentConditionDT();
	    list = (ArrayList<Object> ) preparedStmtMethod(srtTreatmentConditionDT, null,
	    		query, NEDSSConstants.SELECT);
    }catch(Exception ex){
    	logger.fatal("Exception = "+ex.getMessage(), ex);
    	throw new NEDSSSystemException(ex.toString());
    }
    return list;
  }
  /*Returns a collection of TreatmentDTs*/
  /**
   *
   * @return Collection
   */
  @SuppressWarnings("unchecked")
public Collection<Object>  getAllTreatments(){
  String query = null;
  ArrayList<Object> list = new ArrayList<Object>();
  try{
	 query = treatmentAllSQL;
	 ///end of else
	  SRTTreatmentConditionDT srtTreatmentConditionDT = new SRTTreatmentConditionDT();
	  list = (ArrayList<Object> ) preparedStmtMethod(srtTreatmentConditionDT, null,
	      query, NEDSSConstants.SELECT);
  }catch(Exception ex){
  	logger.fatal("Exception = "+ex.getMessage(), ex);
  	throw new NEDSSSystemException(ex.toString());
  }
  return list;
}





  /*Returns a collection of TreatmentDTs*/
  /**
   *
   * @return Collection
   */
  @SuppressWarnings("unchecked")
public Collection<Object>  getDrugs(){
  String query = null;
  ArrayList<Object> list = new ArrayList<Object>();
  try{
	  query = drugMapSQL;
	   ///end of else
	  SRTTreatmentConditionDT srtTreatmentConditionDT = new SRTTreatmentConditionDT();
	  list = (ArrayList<Object> ) preparedStmtMethod(srtTreatmentConditionDT, null,
	      query, NEDSSConstants.SELECT);
  }catch(Exception ex){
  	  logger.fatal("Exception = "+ex.getMessage(), ex);
  	throw new NEDSSSystemException(ex.toString());
  }
  return list;
}

	/*Returns a collection of TreatmentDTs*/
	/**
	 *
	 * @return Collection
	 */
	public Collection<Object>  getAllDrugs(){
		ArrayList<Object> list = new ArrayList<Object>();
		String query = null;
		try{
			query = drugAllSQL;
			 ///end of else
			SRTTreatmentConditionDT srtTreatmentConditionDT = new SRTTreatmentConditionDT();
			list = (ArrayList<Object> ) preparedStmtMethod(srtTreatmentConditionDT, null,
			    query, NEDSSConstants.SELECT);
		}catch(Exception ex){
		    logger.fatal("Exception = "+ex.getMessage(), ex);
		    throw new NEDSSSystemException(ex.toString());
		}
		return list;
	}

}