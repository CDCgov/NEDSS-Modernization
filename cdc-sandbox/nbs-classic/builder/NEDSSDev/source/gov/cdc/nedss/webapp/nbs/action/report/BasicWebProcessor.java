package gov.cdc.nedss.webapp.nbs.action.report;

import gov.cdc.nedss.util.*;

import java.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.report.vo.*;
import gov.cdc.nedss.report.dt.*;
import gov.cdc.nedss.report.util.*;
import gov.cdc.nedss.reportadmin.dt.ReportSectionDT;

public class BasicWebProcessor
{

    static final LogUtils logger = new LogUtils((BasicWebProcessor.class).getName());  //Used for logging
    protected static boolean DEBUG_MODE = false;  // Used for debugging

    public BasicWebProcessor()
    {
    }

    public static String setCounties(ReportVO reportVO, ReportFilterDT reportFilterDT, String[] values, String currentState, String operator)
    {
        reportFilterDT = setFilterValues(reportVO, reportFilterDT, values, operator);
        return getCounties(reportFilterDT, currentState);
    }

    public static String getCounties(ReportFilterDT filterDT, String currentState)
    {

        StringBuffer sb = new StringBuffer();
        CachedDropDownValues cdv = new CachedDropDownValues();
        TreeMap<?,?> map = cdv.getCountyCodesAsTreeMap(currentState);
        return getFilterValues(sb, map, filterDT);
    }

    public static String setDiseases(ReportVO reportVO, ReportFilterDT reportFilterDT, String[] values, String operator)
    {
        reportFilterDT = setFilterValues(reportVO, reportFilterDT, values, operator);
        return getDiseases(reportFilterDT);
    }
    
    public static String setCodedValues(ReportVO reportVO, ReportFilterDT reportFilterDT, String[] values, String operator)
    {
        reportFilterDT = setFilterValues(reportVO, reportFilterDT, values, operator);
        return getCodedValues(reportFilterDT);
    }
    
    public static ReportFilterDT setTextValue(ReportVO reportVO, ReportFilterDT reportFilterDT, String value, String operator)
    {
    	if((value != null && value.length() > 0))
        {
            ArrayList<Object> filterValues = (ArrayList<Object> )reportFilterDT.getTheFilterValueDTCollection();
            filterValues.clear();
            ArrayList<Object> newFilterValues = new ArrayList<Object> ();
                FilterValueDT fvDT1 = new FilterValueDT();
                fvDT1.setReportFilterUid(reportFilterDT.getReportFilterUid());
                fvDT1.setValueType("code");
                fvDT1.setValueTxt(value);
                fvDT1.setFilterValueOperator(operator);
                newFilterValues.add(fvDT1);
            
                reportFilterDT.setTheFilterValueDTCollection(newFilterValues);
        }  //setTextValue
    	else{
    		reportFilterDT.setTheFilterValueDTCollection(new ArrayList<Object> ());
    	}
        return reportFilterDT;
    }
    
    public static String getDiseases(ReportFilterDT filterDT)
    {

        StringBuffer sb = new StringBuffer();
        CachedDropDownValues cdv = new CachedDropDownValues();
        TreeMap<Object,Object> map = cdv.getReportBasicPageConditionMap();
        return getFilterValues(sb, map, filterDT);
    }
    
    public static String getCodedValues(ReportFilterDT filterDT)
    {
        StringBuffer sb = new StringBuffer();
        CachedDropDownValues cdv = new CachedDropDownValues();
        String codeSetNm = filterDT.getTheFilterCodeDT().getFilterCodeSetNm();
        TreeMap<?,?> map = null;
        if(codeSetNm!=null && codeSetNm.equals("STD_HIV_WRKR")){
        	map = cdv.getSTDHIVWorkersTreeMap();
        }
        else if(codeSetNm!=null && codeSetNm.equals("PLACE_LIST")){
        	map = cdv.getPlacesWithQEC();
        }
        else
        	map = cdv.getCodedValuesAsTreeMap(filterDT.getTheFilterCodeDT().getFilterCodeSetNm());
        
        return getFilterValues(sb, map, filterDT);
    }
    
    public static String setRegions(ReportVO reportVO, ReportFilterDT reportFilterDT, String[] values, String operator)
    {
        reportFilterDT = setFilterValues(reportVO, reportFilterDT, values, operator);
        return getRegions(reportFilterDT);
    }

    public static String getRegions(ReportFilterDT filterDT)
    {

        StringBuffer sb = new StringBuffer();
        CachedDropDownValues cdv = new CachedDropDownValues();
        TreeMap<?,?> map = cdv.getRegionCodes();
        return getFilterValues(sb, map, filterDT);
    }

    public static String setStates(ReportVO reportVO, ReportFilterDT reportFilterDT, String[] values, String operator)
    {
        reportFilterDT = setFilterValues(reportVO, reportFilterDT, values, operator);
        return getStates(reportFilterDT);
    }

    public static String getStates(ReportFilterDT filterDT)
    {

        StringBuffer sb = new StringBuffer();
        CachedDropDownValues cdv = new CachedDropDownValues();
        TreeMap<?,?> map = cdv.getStateCodes1("STATE_CCD");
        return getFilterValues(sb, map, filterDT);
    }

    /**
     * SET TIME PERIOD
     */
    public static String[] setTimePeriod(ReportVO reportVO, ReportFilterDT reportFilterDT, String from, String to, String operator)
    {
        String[] timePeriod = new String[2];
        logger.info(" BWP -- 118 PERIOD - From: " + from);
        logger.info(" BWP -- 119 PERIOD - To:   " + to);
        reportFilterDT = setTimeValues(reportVO, reportFilterDT, ReportConstantUtil.BEGIN_RANGE, ReportConstantUtil.END_RANGE, from, to, operator);
        timePeriod[0] = getTimeValues(reportFilterDT, ReportConstantUtil.TIME_PERIOD_CODE, ReportConstantUtil.BEGIN_RANGE);
        timePeriod[1] = getTimeValues(reportFilterDT, ReportConstantUtil.TIME_PERIOD_CODE, ReportConstantUtil.END_RANGE);
        return timePeriod;
    }

    public static String getTimePeriodFrom(ReportFilterDT rfDT)
    {
        return getTimeValues(rfDT, ReportConstantUtil.TIME_PERIOD_CODE, ReportConstantUtil.BEGIN_RANGE);
    }

    public static String getTimePeriodTo(ReportFilterDT rfDT)
    {
        return getTimeValues(rfDT, ReportConstantUtil.TIME_PERIOD_CODE, ReportConstantUtil.END_RANGE);
    }

    /**
     * SET TIME RANGE
     */
    public static String[] setTimeRange(ReportVO reportVO, ReportFilterDT reportFilterDT, String from, String to, String operator)
    {
        String[] timeRange = new String[2];
        logger.info(" BWP -- 128 RANGE - From: " + from);
        logger.info(" BWP -- 129 RANGE - To:   " + to);
        reportFilterDT = setTimeValues(reportVO, reportFilterDT, ReportConstantUtil.BEGIN_RANGE, ReportConstantUtil.END_RANGE, from, to, operator);
        timeRange[0] = getTimeValues(reportFilterDT, ReportConstantUtil.TIME_RANGE_CODE, ReportConstantUtil.BEGIN_RANGE);
        timeRange[1] = getTimeValues(reportFilterDT, ReportConstantUtil.TIME_RANGE_CODE, ReportConstantUtil.END_RANGE);
        return timeRange;
    }

    public static String getTimeRangeFrom(ReportFilterDT rfDT)
    {
        return getTimeValues(rfDT, ReportConstantUtil.TIME_RANGE_CODE, ReportConstantUtil.BEGIN_RANGE);
    }
    
    public static String getTimeRangeTo(ReportFilterDT rfDT)
    {
        return getTimeValues(rfDT, ReportConstantUtil.TIME_RANGE_CODE, ReportConstantUtil.END_RANGE);
    }
    
    public static String[] setMonthYearRange(ReportVO reportVO, ReportFilterDT reportFilterDT, String from, String to, String operator)
    {
        String[] timeRange = new String[2];
        logger.info(" BWP -- 128 RANGE - From: " + from);
        logger.info(" BWP -- 129 RANGE - To:   " + to);
        reportFilterDT = setTimeValues(reportVO, reportFilterDT, ReportConstantUtil.BEGIN_RANGE, ReportConstantUtil.END_RANGE, from, to, operator);
        timeRange[0] = getTimeValues(reportFilterDT, ReportConstantUtil.MONTH_YEAR_RANGE_CODE, ReportConstantUtil.BEGIN_RANGE);
        timeRange[1] = getTimeValues(reportFilterDT, ReportConstantUtil.MONTH_YEAR_RANGE_CODE, ReportConstantUtil.END_RANGE);
        return timeRange;
    }
    public static String getMonthYearRangeFrom(ReportFilterDT rfDT)
    {
        return getTimeValues(rfDT, ReportConstantUtil.MONTH_YEAR_RANGE_CODE, ReportConstantUtil.BEGIN_RANGE);
    }
    public static String getMonthYearRangeTo(ReportFilterDT rfDT)
    {
        return getTimeValues(rfDT, ReportConstantUtil.MONTH_YEAR_RANGE_CODE, ReportConstantUtil.END_RANGE);
    }

    /**
    * method getTimeValues() -- to get timeRange or timePeriods (begin or end)
    */
    public static String getTimeValues(ReportFilterDT rfDT, String mainType, String subType)
    {
        String time = null;
        FilterCodeDT fcDT = (FilterCodeDT)rfDT.getTheFilterCodeDT();
        logger.info("BWP 246 --> fcDT.getFilterCode() " + fcDT.getFilterCode());
        logger.info("BWP 247 --> mainType " + mainType);
        if(fcDT.getFilterCode().startsWith(mainType))
        {
            logger.info(" -- 249 -- ");
            ArrayList<Object> fvList = (ArrayList<Object> )rfDT.getTheFilterValueDTCollection();
           Iterator<Object>  fvIterator = null;
            for(fvIterator = fvList.iterator(); fvIterator.hasNext();)
            {
                FilterValueDT fvDT = (FilterValueDT)fvIterator.next();
                if(fvDT.getValueType().equalsIgnoreCase(subType))
                {
                    logger.info("--- 255 ---");
                    time = fvDT.getValueTxt();
                    break;
                }
            }
        }
        logger.info(" Time is -- BWP 260:" + time);
        return time;
    }  //getTimeRangeFrom
    
	public static String getTextValue(ReportFilterDT rfDT) {
		String textValue = null;
		FilterCodeDT fcDT = (FilterCodeDT) rfDT.getTheFilterCodeDT();
		logger.info("Filter Code --> fcDT.getFilterCode() "
				+ fcDT.getFilterCode());
		ArrayList<Object> fvList = (ArrayList<Object>) rfDT
				.getTheFilterValueDTCollection();
		Iterator<Object> fvIterator = null;
		for (fvIterator = fvList.iterator(); fvIterator.hasNext();) {
			FilterValueDT fvDT = (FilterValueDT) fvIterator.next();
			textValue = fvDT.getValueTxt();
			break;
		}
		logger.info("Text Value:" + textValue);
		return textValue;
	} // getTextValue
	
	public static String getDaysValue(ReportFilterDT rfDT) {
		String textValue = null;
		FilterCodeDT fcDT = (FilterCodeDT) rfDT.getTheFilterCodeDT();
		logger.info("Filter Code --> fcDT.getFilterCode() "
				+ fcDT.getFilterCode());
		ArrayList<Object> fvList = (ArrayList<Object>) rfDT
				.getTheFilterValueDTCollection();
		Iterator<Object> fvIterator = null;
		for (fvIterator = fvList.iterator(); fvIterator.hasNext();) {
			FilterValueDT fvDT = (FilterValueDT) fvIterator.next();
			textValue = fvDT.getValueTxt();
			break;
		}
		logger.info("Days Value:" + textValue);
		return textValue;
	} // getDaysValue

    public static String fetchCounties(String[] stateCode)
    {

        StringBuffer sb = new StringBuffer();
        CachedDropDownValues cdv = new CachedDropDownValues();
        TreeMap<?,?> map = cdv.getCountyCodesAsTreeMap(stateCode[0]);
        return getCounties(sb, map);
    }

    /**
      * Method to return XML String of counties
      */
    private static String getCounties(StringBuffer sb, TreeMap<?,?> treeMap)
    {
        if(treeMap != null)
        {
            sb.append("<table role=\"presentation\">");
            Set<?> set = treeMap.keySet();
           Iterator<?>  itr = set.iterator();
            while(itr.hasNext())
            {
                sb.append("<record>");
                String key = (String)itr.next();
                String value = (String)treeMap.get(key);
                sb.append("<field>");
                sb.append(XMLRequestHelper.xmlEncode(value.trim()));
                sb.append("</field>");
                sb.append("<field>");
                sb.append(XMLRequestHelper.xmlEncode(key.trim()));
                sb.append("</field>");
                sb.append("<field>");
                sb.append("false");
                sb.append("</field>");
                sb.append("</record>");
            }  //while
            sb.append("</table>");
        }
        return sb.toString();
    }  //fetchCounties()

    /**
      * setFilterValues
      */
    private static ReportFilterDT setFilterValues(ReportVO reportVO, ReportFilterDT reportFilterDT, String[] values, String operator)
    {
        if(values != null)
        {
            ArrayList<Object> filterValues = (ArrayList<Object> )reportFilterDT.getTheFilterValueDTCollection();
            filterValues.clear();
            ArrayList<Object> newFilterValues = new ArrayList<Object> ();
            int y = values.length;
            for(int x = 0; x < y; x++)
            {
                logger.info("BWP setFilterValues -- 89: " + y);
                FilterValueDT filterValueDT = new FilterValueDT();
                filterValueDT.setReportFilterUid(reportFilterDT.getReportFilterUid());
                filterValueDT.setValueType("code");
                filterValueDT.setFilterValueOperator(operator);
                filterValueDT.setValueTxt(values[x]);
                logger.info("inside set Function : " + values[x]);
                newFilterValues.add(filterValueDT);
            }
            reportFilterDT.setTheFilterValueDTCollection(newFilterValues);
        }
        //handle specifically if values are not selected/entered, but INCLUDE_NULLS is checked on UI
        if(values == null && (operator!=null && operator.equalsIgnoreCase(ReportConstantUtil.ALLOW_NULLS)) ) {
        	ArrayList<Object> newFilterValues = new ArrayList<Object> ();
            FilterValueDT filterValueDT = new FilterValueDT();
            filterValueDT.setReportFilterUid(reportFilterDT.getReportFilterUid());
            filterValueDT.setValueType("none");
            filterValueDT.setFilterValueOperator(operator);
            filterValueDT.setValueTxt("");
            newFilterValues.add(filterValueDT);
            reportFilterDT.setTheFilterValueDTCollection(newFilterValues);
        }
        return reportFilterDT;
    }

    /**
      * Method to return XML String for filters
      */
    private static String getFilterValues(StringBuffer sb, TreeMap<?,?> treeMap, ReportFilterDT rfDT)
    {
        if(treeMap != null)
        {
            sb.append("<table role=\"presentation\">");
            Set<?> set = treeMap.keySet();
           Iterator<?>  itr = set.iterator();
            while(itr.hasNext())
            {
                sb.append("<record>");
                String key = (String)itr.next();
                String value = (String)treeMap.get(key);
                sb.append("<field>");
                sb.append(XMLRequestHelper.xmlEncode(value.trim()));
                sb.append("</field>");
                sb.append("<field>");
                sb.append(XMLRequestHelper.xmlEncode(key.trim()));
                sb.append("</field>");
                ArrayList<Object> filterValues = (ArrayList<Object> )rfDT.getTheFilterValueDTCollection();
                int i = filterValues.size();
               Iterator<Object>  iValues = null;
                boolean bFound = false;
                if(i > 0)
                {
                    for(iValues = filterValues.iterator(); iValues.hasNext();)
                    {
                        FilterValueDT filterValueDT = (FilterValueDT)iValues.next();
                        if(filterValueDT.getValueTxt().equalsIgnoreCase(value))
                        {
                            logger.info("BWP -- 132: " + filterValueDT.getValueTxt());
                            sb.append("<field>");
                            sb.append("true");
                            sb.append("</field>");
                            bFound = true;
                            break;
                        }
                    }
                }
                if(!bFound)
                {
                    sb.append("<field>");
                    sb.append("false");
                    sb.append("</field>");
                }
                sb.append("</record>");
            }  //while
            sb.append("</table>");
        }
        return sb.toString();
    }  //getFilterValues()
    
    public static String getReportSections(ArrayList<Object>  reportSections)
    {
    	StringBuffer sb = new StringBuffer("");
        if(reportSections != null)
        {
            sb.append("<table role=\"presentation\">");
            sb.append("<record>");
            sb.append("<field>");
            sb.append("");
            sb.append("</field>");
            sb.append("<field>");
            sb.append("");
            sb.append("</field>");
            sb.append("<field>");
            sb.append("</field>");
            sb.append("</record>");
            
           Iterator<Object>  itr = reportSections.iterator();
            while(itr.hasNext())
            {
				ReportSectionDT  rsDT = (ReportSectionDT)itr.next();
				sb.append("<record>");
				String key = (String)rsDT.getSectionCd_s();
				String value = (String)rsDT.getSectionDescTxt();
				sb.append("<field>");
				sb.append(XMLRequestHelper.xmlEncode(key.trim()));
				sb.append("</field>");
				sb.append("<field>");
				sb.append(XMLRequestHelper.xmlEncode(value.trim()));
				sb.append("</field>");
				sb.append("<field>");
				//sb.append("true");
				sb.append("</field>");
				sb.append("</record>");
            }  //while
            sb.append("</table>");
        }
        return sb.toString();
    }  //getFilterValues()


    /**
     * setTimeValues to set timeRange or timePeriods (begin or end)
     */
    private static ReportFilterDT setTimeValues(ReportVO rVO, ReportFilterDT rfDT, String beginRange, String endRange, String from, String to, String operator)
    {
        
    	if((from != null && from.length() > 0) || (to != null && to.length() > 0))
        {
            ArrayList<Object> filterValues = (ArrayList<Object> )rfDT.getTheFilterValueDTCollection();
            filterValues.clear();
            ArrayList<Object> newFilterValues = new ArrayList<Object> ();
            if(from != null)
            {
                logger.info("BWP 116 from!= null");
                FilterValueDT fvDT1 = new FilterValueDT();
                fvDT1.setReportFilterUid(rfDT.getReportFilterUid());
                fvDT1.setValueType(beginRange);
                fvDT1.setValueTxt(from);
                //fvDT1.setFilterValueOperator("GE");
                //commented out storing GE for Basic Date Field, making use of BEGIN_RANGE(part of value_type column) in building sql
                fvDT1.setFilterValueOperator(operator);
                newFilterValues.add(fvDT1);
            }
            if(to != null)
            {
                logger.info("BWP 124 to!= null");
                FilterValueDT fvDT2 = new FilterValueDT();
                fvDT2.setReportFilterUid(rfDT.getReportFilterUid());
                fvDT2.setValueType(endRange);
                fvDT2.setValueTxt(to);
                //fvDT2.setFilterValueOperator("LE");
                //commented out storing LE for Basic Date Field, making use of END_RANGE(part of value_type column) in building sql
                fvDT2.setFilterValueOperator(operator);
                newFilterValues.add(fvDT2);
            }
            rfDT.setTheFilterValueDTCollection(newFilterValues);
        }  //setTimeValues
        return rfDT;
    }  //setTimeValues

}
