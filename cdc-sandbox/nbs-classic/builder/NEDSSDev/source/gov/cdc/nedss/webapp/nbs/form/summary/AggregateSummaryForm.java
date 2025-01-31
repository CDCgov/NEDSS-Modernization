package gov.cdc.nedss.webapp.nbs.form.summary;

import gov.cdc.nedss.act.summaryreport.vo.SummaryReportProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.summary.util.CategoryTable;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

/**
 * Struts Form - Representation of Summary Aggregate Data
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * AggregateSummaryForm.java
 * Aug 6, 2009
 * @version
 */
public class AggregateSummaryForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	private Map<Object,Object> answerMap = new HashMap<Object,Object>();
	private Map<Object,Object> formFieldMap = new HashMap<Object,Object>();
	private String formCd;
    private String conditionCd;
	private SummaryReportProxyVO oldProxyVO;
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	
	ArrayList<Object> manageList = new ArrayList<Object> ();
	ArrayList<Object> oldManageList = new ArrayList<Object> ();
	Object selection = new Object();
	Object oldDT = new Object();
	private String returnToLink;
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	ArrayList<Object> categoryTableList = new ArrayList<Object> ();
	ArrayList<Object> mmwrYearList = new ArrayList<Object> ();
	private Map<Object,Object> mmwrWeekMap = new HashMap<Object,Object>();
	private ArrayList<Object> conditions = new ArrayList<Object> ();
	private String strProgramAreas;
	
	public Map<Object,Object> getSearchMap() {
		return searchMap;
	}
	public void setSearchMap(Map<Object,Object> searchMap) {
		this.searchMap = searchMap;
	}	
	
	public CategoryTable getCategoryTable(int index) {
		return (CategoryTable)categoryTableList.get(index);
	}
	
	public void setCategoryTable(CategoryTable categoryTable) {
		categoryTableList.add(categoryTable);
	}
	
	public Map<Object,Object> getAnswerMap() {
		return answerMap;
	}
	
	public void setAnswerMap(Map<Object,Object> answerMap) {
		this.answerMap = answerMap;
	}

	public String getAnswer(String key) {
		return (String)answerMap.get(key);
	}

	public void setAnswer(String key, String answer) {
		answerMap.put(key, answer);
	}
	
	public String getFormCd() {
		return formCd;
	}

	public void setFormCd(String formCd) {
		this.formCd = formCd;
	}

	public Map<Object,Object> getFormFieldMap() {
		return formFieldMap;
	}

	public void setFormFieldMap(Map<Object,Object> formFieldMap) {
		this.formFieldMap = formFieldMap;
	}

	public SummaryReportProxyVO getOldProxyVO() {
		return oldProxyVO;
	}

	public void setOldProxyVO(SummaryReportProxyVO oldProxyVO) {
		this.oldProxyVO = oldProxyVO;
	}
	
	public ArrayList<Object> getManageList() {
		return manageList;
	}
	public void setManageList(ArrayList<Object>  manageList) {
		this.manageList = manageList;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}
	public Object getSelection() {
		return selection;
	}
	public void setSelection(Object selection) {
		this.selection = copyObject(selection);
	}
	
	public void resetSelection() {
		this.selection = new Object();
		this.oldDT = new Object();
	}
	public String getReturnToLink() {
		return returnToLink;
	}
	public void setReturnToLink(String returnToLink) {
		this.returnToLink = returnToLink;
	}
	
	public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(Map<Object,Object> attributeMap) {
		this.attributeMap = attributeMap;
	}	
	
	public void clearSelections() {
		setSearchMap(new HashMap<Object,Object>());
		setManageList(new ArrayList<Object>());
		resetSelection();
		setAttributeMap(new HashMap<Object,Object>());
		setActionMode(null);
		setReturnToLink(null);
		setCategoryTableList(new ArrayList<Object>());
		
	}
	public Object getOldDT() {
		return oldDT;
	}
	public void setOldDT(Object oldDT) {
		this.oldDT = copyObject(oldDT);
	}
	
    private static Object copyObject(Object param) {
    	Object deepCopy = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(param);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			deepCopy= ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deepCopy;
	}	

	public ArrayList<Object> getOldManageList() {
		return oldManageList;
	}
	public void setOldManageList(ArrayList<Object>  oldManageList) {
		this.oldManageList = oldManageList;
	}
	
	public void setSearchCriteria(String key, String answer) {
		searchMap.put(key, answer);
	}

	public String getSearchCriteria(String key) {
		return (String) searchMap.get(key);
	}
	
	
	public Object getCodedValue(String key) {
		ArrayList<Object> aList = new ArrayList<Object> ();
		if (formFieldMap.containsKey(key)) {
			FormField fField = (FormField) formFieldMap.get(key);
			aList = CachedDropDowns.getCodedValueForType(fField
					.getCodeSetNm());
		}
		return aList;
	}
	public Object getConditions() {
		if(conditions.size() == 0) {
			conditions = CachedDropDowns.getAggregateSummaryReportConditionCode(strProgramAreas);
		}
		return conditions;
	}	
	
	
	public String getConditionCd() {
		return conditionCd;
	}
	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}
	public ArrayList<Object> getCategoryTableList() {
		return categoryTableList;
	}
	public void setCategoryTableList(ArrayList<Object>  categoryTableList) {
		this.categoryTableList = categoryTableList;
	}
	
	public ArrayList<Object> getDwrDefaultStateCounties() {
			return CachedDropDowns.getCountyCodesInclStateWide(PropertyUtil.getInstance().getNBS_STATE_CODE());
	}

	public String CalcMMWRWeekOnLoad() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		if(super.getActionMode() == null) {
			RulesEngineUtil util = new RulesEngineUtil();
			int [] wY = util.CalcMMWR(formatter.format(new Date()));
			if(wY != null && wY.length > 0) {
				return String.valueOf(wY[0]);
			}
		} else {
			return String.valueOf(searchMap.get("SUM102"));
		}
		return null;
	}	
	
	public ArrayList<Object> getMmwrYears() {
		if(mmwrYearList.size() == 0) {
			DropDownCodeDT dt = new DropDownCodeDT();
			dt.setKey(""); dt.setValue("");
			mmwrYearList.add(dt);
			String year = "";
	        int intFirstYear = 2000;
	        GregorianCalendar cal = new GregorianCalendar();
	        int intCurrentYear = cal.get(Calendar.YEAR);
	        for (int x = intCurrentYear; x >=intFirstYear ; x--) {
	            year = Integer.toString(x);
				dt = new DropDownCodeDT();
				dt.setKey(year); dt.setValue(year);
				mmwrYearList.add(dt);
	        }
		}
		return mmwrYearList;
	}
	
	public ArrayList<Object> getMmwrWeekList() {
		if(searchMap.get("SUM101") == null || searchMap.get("SUM101") == "") {
	        GregorianCalendar cal = new GregorianCalendar();
	        int yr = cal.get(Calendar.YEAR);
			return getMmwrWeeks(String.valueOf(yr));
		} else {
			return getMmwrWeeks(String.valueOf(searchMap.get("SUM101")));
		}
	}
	
	public ArrayList<Object> getMmwrWeeks(String pYear) {
		
		ArrayList<Object> mmwrWeekList = new ArrayList<Object> ();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			if(mmwrWeekMap.get(pYear) == null) {
				
				DropDownCodeDT dt = new DropDownCodeDT();
				dt.setKey(""); dt.setValue("");			
				mmwrWeekList.add(dt);
				
			    //  Define constants.
			    int SECOND = 1000;
			    int MINUTE = 60 * SECOND;
			    int HOUR = 60 * MINUTE;
			    int DAY = 24 * HOUR;
			    int SIX_DAYS = 6 * DAY;
			    int WEEK = 7 * DAY;
			    //  Convert to date object.
			    Date varDate = formatter.parse("12/31/" + pYear);
			    Calendar cal = Calendar.getInstance();
				cal.setTime(varDate);		    
			    long varTime = varDate.getTime();
			    long varDay = cal.get(Calendar.DAY_OF_WEEK);

			    //  Get January 1st of given year.
			    Date varJan1Date = formatter.parse("01/01/" + cal.get(Calendar.YEAR));
			    Calendar calJan1 = Calendar.getInstance();
			    calJan1.setTime(varJan1Date);
			    int varJan1Day = calJan1.get(Calendar.DAY_OF_WEEK);
			    long varJan1Time = calJan1.getTimeInMillis();
			    //  Create temp variables.
			    long t = varJan1Time;
			    long tSAT = 0;
			    Date d = null;
			    int h = 0;
			    String s = "";
			    long wTemp = 0;
			    //  MMWR Year.
			    int y = calJan1.get(Calendar.YEAR);
			    //  MMWR Week.
			    int w = 0;
			    //  Find first day of MMWR Year.
			   
			    if(varJan1Day <= 4)
			    {
			        //  If SUN, MON, TUE, or WED, go back to nearest Sunday.
			        t -= ((varJan1Day-1) * DAY);
			    } else
			    {
			        //  If THU, FRI, or SAT, go forward to nearest Sunday.
			        t += ((7 - (varJan1Day-1)) * DAY);
			    }
		        //  Loop through each week until we reach the given date.
			    while(t <= varTime) {
			        //  Increment the week counter.
			        w++;
			        //  Adjust for daylight savings time as necessary.
			        d = new Date(t);
			        Calendar cal1 = Calendar.getInstance();
			        cal1.setTime(d);
			        h = cal1.get(Calendar.HOUR);
			        if(h == 1)
			        {
			            t -= HOUR;
			        }
			        if(h == 23 || h == 11)
			        {
			            t += HOUR;
			        }			        s = "";
			        if(w < 10)
			        {
			            s += "0";
			        }
			        s += w + " (" + formatter.format(d);
			        tSAT = t + SIX_DAYS;
			        //  Adjust for daylight savings time as necessary.
			        d = new Date(tSAT);
			        cal1 = Calendar.getInstance();
			        cal1.setTime(d);
			        h = cal1.get(Calendar.HOUR);
			        if(h == 1)
			        {
			            tSAT -= HOUR;
			        }
			        if(h == 23 || h == 11)
			        {
			            tSAT += HOUR;
			        }
			        d = new Date(tSAT);
			        s += " - " + formatter.format(d) + ")";
			        //  Move on to the next week.
			        t += WEEK;
			        //  Adjust for daylight savings time as necessary.
			        d = new Date(t);
			        cal1 = Calendar.getInstance();
			        cal1.setTime(d);
			        h = cal1.get(Calendar.HOUR);	            
			        if(h == 1)
			        {
			            t -= HOUR;
			        }
			        if(h == 23 || h == 11)
			        {
			            t += HOUR;
			        }
			        //  Rule #4.
			        if( (w == 53) && (varDay < 3) )
			        {
			            break;
			        }
			        //  Zero pad left.
			        wTemp = (w < 10) ? (Integer.valueOf("0").intValue() + w) : w;
			        dt = new DropDownCodeDT();
			        dt.setKey(String.valueOf(w)); dt.setValue(s);
			        mmwrWeekList.add(dt);
			        
			        mmwrWeekMap.put(pYear, mmwrWeekList);
			    }
			    
			} else {
				return (ArrayList<Object> ) mmwrWeekMap.get(pYear);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mmwrWeekList;
	}
	public Map<Object,Object> getMmwrWeekMap() {
		return mmwrWeekMap;
	}
	public String getStrProgramAreas() {
		return strProgramAreas;
	}
	public void setStrProgramAreas(String strProgramAreas) {
		this.strProgramAreas = strProgramAreas;
	}
	
}
