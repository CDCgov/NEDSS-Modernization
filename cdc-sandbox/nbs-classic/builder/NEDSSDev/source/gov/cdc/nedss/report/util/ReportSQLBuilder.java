package gov.cdc.nedss.report.util;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.report.vo.*;
import gov.cdc.nedss.report.dt.*;
import gov.cdc.nedss.report.util.*;

import java.text.*;
import java.util.*;
//Commented out while upgrading to JDK1.5
//import com.sun.rsasign.al;

/**
 * A utility class that used for building sql statement for SAS
 */
public class ReportSQLBuilder
{

    static final LogUtils logger = new LogUtils((ReportSQLBuilder.class).getName());
    protected static final CachedDropDownValues srtmap = new CachedDropDownValues();
    protected static TreeMap<Object,Object> stateMap;
    protected static TreeMap<Object,Object> countyMap;
    protected static TreeMap<Object,Object> conditionMap;
    private static final String CONVERT_TO_SAS_DATE_ERROR = "Can't Convert Date!";
   // private static final String FILTER_VALUE_DATA_ERROR = "FilterValue Data Error!";
    private static final String COLUMN_UID_MISSING_ERROR = "Column Uid Missing!";
    private static final String UNEXPECTED_DATA_FORMAT = "Unexpected Data Format Encountered!";

    static
    {
        stateMap = srtmap.getStateCodes2("USA");
        countyMap = srtmap.getCountyCodes();
        conditionMap = srtmap.getConditionCodes();
        logger.debug("===static ===" + stateMap);
        logger.debug("===static ===" + countyMap);
        logger.debug("===static ===" + conditionMap);
    }

    public ReportSQLBuilder()
    {
    }

    /**
  *  get DataSource Columns for a given column uid.
  */
    private static DataSourceColumnDT getDataSourceColumnDT(Long colUid, DataSourceVO dataSourceVO) throws NEDSSException
    {
        ArrayList<Object> arDSVO = (ArrayList<Object> )dataSourceVO.getTheDataSourceColumnDTCollection();
        DataSourceColumnDT dscDT = null;
        for(int k = 0; k < arDSVO.size(); k++)
        {
            DataSourceColumnDT dscDTtemp = (DataSourceColumnDT)arDSVO.get(k);
            if(colUid != null && colUid.equals(dscDTtemp.getColumnUid()))
            {
                dscDT = dscDTtemp;
                break;
            }
        }
        if(dscDT == null)
        {
            throw new NEDSSException(COLUMN_UID_MISSING_ERROR);
        }
        return dscDT;
    }

    /**
   *  The displayed values are user friendly names for state, county and
   *  conditions,  etc.  They are mainly used in report footnotes.
   *  Basic filters except for time filter use coded value, not the names or
   *  description text, for data operation.
   *  If FilterValue table has ValueType = "CODE", then ValueTxt contains
   *  coded value (state and county fips or phc_code).  And we need to use
   *  the code to look up the appropriate code set table to get the displayed
   *  value.
   *
   */
    private static String getDisplayValue(String filterCode, String fvValueType, String fvValueTxt) throws NEDSSException
    {
        String displayVal = fvValueTxt;
        if(fvValueType.equalsIgnoreCase(ReportConstantUtil.VAL_TYPE_CODE))
        {
            if(filterCode.startsWith(ReportConstantUtil.STATE_CODE))
            {
                displayVal = (String)stateMap.get(fvValueTxt);
            }  //Basic County Filter
            else if(filterCode.startsWith(ReportConstantUtil.COUNTY_CODE))
            {
                displayVal = (String)countyMap.get(fvValueTxt);
                //} else if (filterCode.equalsIgnoreCase(ReportConstantUtil.REGION_CODE)) {  //Basic County Filter
                //  displayVal = (String)countyMap.get(fvValueTxt);
            }  //DE-11600 --> Cholera*
            else if(filterCode.startsWith(ReportConstantUtil.DISEASE_CODE))
            {
                displayVal = (String)conditionMap.get(fvValueTxt);
            }
            else
            {
                //No Lookup needed
            }
            logger.debug("displayVal for (" + fvValueTxt + ") = " + displayVal);
        }
        return displayVal;
    }

    /*TODO
     private static String getDisplayValue(FilterCodeDT filterCodeDT, String fvValueType, String fvValueTxt) throws NEDSSException
    {
        String displayVal = fvValueTxt;
        if(fvValueType.equalsIgnoreCase(ReportConstantUtil.VAL_TYPE_CODE))
        {
            TreeMap<Object,Object> treeMap = classMap.get(filterCodeDT.getCodeTable());
            if (treeMap == null) {
                //getCodeAndShortDescForTable need to be implemented in SRTMapEJB
                //to return a map with Code to Code_Short_Desc_Txt lookup
                treeMap = srtmap.getCodeAndShortDescForTable(filterCodeDT.getCodeTable());
                classMap.set(tableName,treeMap);
            }
            displayVal = (String)treeMap(fvValueTxt);
        }
        return displayVal;
    }
    */

    private static String getQuotedValue(String str)
    {
        if(str == null)
        {
            return str;
        }
        else if(str.indexOf("'") < 0)
        {
            return "'" + str + "'";
        }
        StringTokenizer sz = new StringTokenizer(str, "'");
        StringBuffer sb = new StringBuffer("'");
        while(sz.hasMoreTokens())
        {
            String token = sz.nextToken();
            sb.append(token);
            if(sz.hasMoreTokens())
            {
                sb.append("''");
            }
        }
        sb.append("'");
        return sb.toString();
    }

    /**
   * Constructing a sql statement to subset the datasource table based on user
   * input or stored report specifications.
   * @param libname sas library name
   * @param dsName data source name excluding the libname part
   * @param columns data source columns for the report
   * @param where   where clause
   * @return  complete sas sql statement
   */
    public static String getSubsetSql(String libname, String dsName, List<Object> columns, String where)
    {
        StringBuffer stmt = new StringBuffer();
        stmt.append("proc sql noprint; create table work.");
        stmt.append(dsName.trim());
        stmt.append(" as select ");
        if(columns.size() == 0) stmt.append(" * ");
       Iterator<Object>  coList = columns.iterator();
        while(coList.hasNext())
        {
            DisplayColumnDT disColumnDT = (DisplayColumnDT)coList.next();
            stmt.append(" ");
            stmt.append(disColumnDT.getTheDataSourceColumnDT().getColumnName());
            stmt.append(" Label = '" + disColumnDT.getTheDataSourceColumnDT().getColumnTitle());
            stmt.append("'");
            if(coList.hasNext())
            {
                stmt.append(", ");
            }
        }
        stmt.append(" from " + libname.trim() + "." + dsName.trim() + " " + where + ";quit;");
        logger.debug("===subset statement===" + stmt.toString());
        return stmt.toString();
    }

    /**
   *  Builds where clause for data subsetting.  Also construct footnote message
   *  for report
   */
    public static HashMap<Object,Object> buildWhereClause(ReportVO reportVO, DataSourceVO dataSourceVO) throws NEDSSException
    {
        HashMap<Object,Object> hmStrings = new HashMap<Object,Object>();
        SimpleDateFormat dtEndfmt = new SimpleDateFormat("''ddMMMyyyy:23:59:59'''dt'");
        SimpleDateFormat dtBeginfmt = new SimpleDateFormat("''ddMMMyyyy:00:00:00'''dt'");
        try
        {
            StringBuffer value = new StringBuffer();  //system where clause
            StringBuffer footer = new StringBuffer();
            StringBuffer timePeriod = new StringBuffer();
            ArrayList<Object> filtersDTCollection  = (ArrayList<Object> )reportVO.getTheReportFilterDTCollection();
           Iterator<Object>  iFilters = filtersDTCollection.iterator();
            boolean isLabResultVal = false;
            value.append("where ");
            logger.debug("==== Number of Report Filters ==== " + filtersDTCollection.size());
            logger.debug("==== dataSourceVO.getTheDataSourceColumnDTCollection().size()===" + dataSourceVO
                        .getTheDataSourceColumnDTCollection().size());
            boolean isFirstFilter = true;
            String newline = System.getProperty("line.separator");
            //Report Filters Loop
            while(iFilters.hasNext())
            {
                ReportFilterDT filterDT = (ReportFilterDT)iFilters.next();
                logger.debug("==== Current Filter ====" + filterDT.getTheFilterCodeDT().getFilterName());
                //A report filter may have filter values that are empty
                if(filterDT.getTheFilterValueDTCollection() == null || filterDT.getTheFilterValueDTCollection()
                        .size() == 0)
                {
                    logger.debug(filterDT.getTheFilterCodeDT().getFilterName() + " is skipped due to empty filter values.");
                    continue;
                }
                else if(filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_DAYS))
                	footer.append(newline);
                else
                {
                    if(isFirstFilter == false)
                    {
                        value.append(" and ");
                        footer.append(newline);
                    }
                    else
                    {
                        isFirstFilter = false;
                    }
                }
                
                // BASIC WHERE CLAUSE **********************************
                
                if(filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_DAYS)){
                	ArrayList<Object> filterValues = (ArrayList<Object> )filterDT.getTheFilterValueDTCollection();
                	for(Object filterValueDT : filterValues){
                		hmStrings.put(ReportConstantUtil.BAS_DAYS,((FilterValueDT)filterValueDT).getValueTxt());
                		footer.append(filterDT.getTheFilterCodeDT().getFilterName() + ": "+ ((FilterValueDT)filterValueDT).getValueTxt()+" Days");
                	break;
                	}
                }
                else if((filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_CON_LIST)) || (filterDT
                        .getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_JUR_LIST)) || (filterDT
                        .getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_TIM_LIST)) || (filterDT
                                .getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_CVG_LIST)) || (filterDT
                                        .getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_TXT)) || (filterDT
                                                .getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_STD_HIV_WRKR)))
                {
                    //each basic filter corespond to a column.
                    Long columnUID = filterDT.getColumnUid();
                    DataSourceColumnDT dataSourceColumnDT = getDataSourceColumnDT(columnUID, dataSourceVO);
                    String colName = dataSourceColumnDT.getColumnName();
                    String colTitle = dataSourceColumnDT.getColumnTitle();
                    String colType = dataSourceColumnDT.getColumnTypeCode();
                    FilterCodeDT filterCodeDT = filterDT.getTheFilterCodeDT();
                    String filterName = (String)filterCodeDT.getFilterName();
                    String filterCode = (String)filterCodeDT.getFilterCode();
                    value.append(" (");
                    footer.append(filterName + ": ");
                    int maxValue = filterDT.getMaxValueCnt().intValue();
                    int minValue = filterDT.getMinValueCnt().intValue();
                    logger.debug("==== Filter Info For: " + filterDT.getTheFilterCodeDT().getFilterName());
                    logger.debug("columnUID: " + columnUID.longValue());
                    logger.debug("colName: " + colName);
                    logger.debug("colTitle: " + colTitle);
                    logger.debug("colType: " + colType);
                    logger.debug("filterCode: " + filterCode);
                    logger.debug("maxValue: " + maxValue);
                    logger.debug("minValue: " + minValue);
                    ArrayList<Object> filterValues = (ArrayList<Object> )filterDT.getTheFilterValueDTCollection();
                   Iterator<Object>  iValues = filterValues.iterator();
                    boolean allowNulls = false;
                    boolean justIncludeNulls = includeNullsNoValues(filterDT);
                    if(maxValue != minValue)
                    {   
                    	if(!justIncludeNulls)
                    		value.append(colName + " in (");

                        while(iValues.hasNext())
                        {
                            FilterValueDT filterValueDT = (FilterValueDT)iValues.next();
                            if(filterValueDT.getFilterValueOperator() != null && filterValueDT.getFilterValueOperator().equals(ReportConstantUtil.ALLOW_NULLS) && !filterValueDT.getValueType().equalsIgnoreCase("none"))
                            	allowNulls = true;
                            
                            //handle specifically if values are not selected/entered, but INCLUDE_NULLS is checked
                            if(justIncludeNulls) {                            	
                            	value.append(colName + " is null ");
                            	footer.append(" (include NULLs)");
                            	continue;
                            }
                            
                            logger.debug("FilterValue.getValueTxt(): " + filterValueDT.getValueTxt());
                            value.append(formatedField(colType, filterValueDT.getValueTxt()));
                            footer.append(getDisplayValue(filterDT.getTheFilterCodeDT().getFilterCode(), filterValueDT.getValueType(), filterValueDT.getValueTxt()));
                            if(iValues.hasNext())
                            {
                                value.append(", ");  //space is important here
                                footer.append(" ");
                            }
                        }
                        if(!justIncludeNulls)
                        	value.append(")");
                    }
                    else if(maxValue == minValue)
                    {
                        value.append(colName + " EQ ");
                        FilterValueDT filterValueDT = (FilterValueDT)iValues.next();
                        if(filterValueDT.getFilterValueOperator() != null && filterValueDT.getFilterValueOperator().equals(ReportConstantUtil.ALLOW_NULLS) && !filterValueDT.getValueType().equalsIgnoreCase("none"))
                        	allowNulls = true;
                        logger.debug("FilterValue.getValueTxt(): " + filterValueDT.getValueTxt());
                        value.append(formatedField(colType, filterValueDT.getValueTxt()));
                        footer.append(getDisplayValue(filterDT.getTheFilterCodeDT().getFilterCode(), filterValueDT.getValueType(), filterValueDT.getValueTxt()));

                        //handle specifically if values are not selected/entered, but INCLUDE_NULLS is checked
                        if(justIncludeNulls) {                            	
                        	value.append(colName + " is null ");
                        	footer.append(" (include NULLs)");
                        	continue;
                        }                       
                    }
                    //check for nulls
                    if(allowNulls) {
                    	value.append(" or " + colName + " is null ");
                    	footer.append(" (include NULLs)");
                    }  
                    value.append(")");
                 
                    
                    
                }  // END BASIC WHERE CLAUSE **********************************
                // BASIC TIME RANGE **********************************************************
                else if(filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_TIM_RANGE) ||  
                		filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_TIM_RANGE_CUSTOM) || 
                		filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_TIM_RANGE_LIST)|| filterDT
                        .getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_MM_YYYY_RANGE))
                {
                    //FilterCodeDT filterCodeDT = filterDT.getTheFilterCodeDT();
                   // String filterName = (String)filterCodeDT.getFilterName();
                    Long columnUID = filterDT.getColumnUid();
                    DataSourceColumnDT dataSourceColumnDT = getDataSourceColumnDT(columnUID, dataSourceVO);
                    String colName = dataSourceColumnDT.getColumnName();
                    String colTitle = dataSourceColumnDT.getColumnTitle();
                    String colType = dataSourceColumnDT.getColumnTypeCode();
                    logger.debug("==== Filter Info For: " + filterDT.getTheFilterCodeDT().getFilterName());
                    logger.debug("columnUID: " + columnUID.longValue());
                    logger.debug("colName: " + colName);
                    logger.debug("colTitle: " + colTitle);
                    logger.debug("colType: " + colType);
                    logger.debug("filterCode: " + filterDT.getTheFilterCodeDT().getFilterCode());
                    value.append("( ");
                    footer.append(colTitle + ":");
                   // ArrayList<Object> arDSVO = (ArrayList<Object> )dataSourceVO.getTheDataSourceColumnDTCollection();
                    ArrayList<Object> al2 = (ArrayList<Object> )filterDT.getTheFilterValueDTCollection();
                   Iterator<Object>  iFilterValueDTCollection  = al2.iterator();
                    boolean allowNulls = false;
                    value.append("(");
                    while(iFilterValueDTCollection.hasNext())
                    {
                        FilterValueDT fvDT = (FilterValueDT)iFilterValueDTCollection.next();
                        
                        if(fvDT.getFilterValueOperator() != null && fvDT.getFilterValueOperator().equals(ReportConstantUtil.ALLOW_NULLS))
                        	allowNulls = true;
                        
                        logger.debug("fvDT.getValueType(): " + fvDT.getValueType());
                        logger.debug("fvDT.getFilterValueOperator(): " + fvDT.getFilterValueOperator());
                        logger.debug("fvDT.getValueTxt(): " + fvDT.getValueTxt());
                        // Basic time range always has two filterValue objects.
                        // But one of them, either the BEGIN_RANGE or END_RANGE could be empty (valueTxt  is null or 'N/A').
                        if(fvDT.getValueTxt() == null || fvDT.getValueTxt().trim().length() == 0 || fvDT
                            .getValueTxt().trim().equalsIgnoreCase("N/A"))
                        {
                            logger.debug("FilterValue Skipped: " + fvDT.getValueType());
                            continue;
                        }
                        if(filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_TIM_RANGE))
                        {
                            value.append(formatedBasicDateTimeRange(colName, fvDT));
                        }else if(filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_TIM_RANGE_CUSTOM))
                        {
                            value.append(formatedBasicDateTimeRange(colName, fvDT));
                            if(fvDT.getValueType().equalsIgnoreCase("BEGIN_RANGE"))
                            	hmStrings.put("BEGIN_RANGE", convertToSASDateTime(fvDT.getValueTxt(), dtBeginfmt));
                            if(fvDT.getValueType().equalsIgnoreCase("END_RANGE"))
                            	hmStrings.put("END_RANGE", convertToSASDateTime(fvDT.getValueTxt(), dtEndfmt));                        
                        }
                        else if(filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_TIM_RANGE_LIST))
                        {
                            value.append(formatedYearRange(colName, fvDT));
                        }
                        else if(filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.BAS_MM_YYYY_RANGE))
                        {
                            value.append(formatedBasicMonthYearRange(colName, fvDT));
                            if(fvDT.getValueType().equalsIgnoreCase("BEGIN_RANGE"))
                            	hmStrings.put("BEGIN_RANGE", convertToSASDateTime(fvDT.getValueTxt(), dtBeginfmt) );
                            if(fvDT.getValueType().equalsIgnoreCase("END_RANGE"))
                            	hmStrings.put("END_RANGE", (convertToSASDateTime(makeEndRange(fvDT.getValueTxt()), dtEndfmt)));
                        }
                        footer.append(formatedFooterTimeRange(fvDT));
                        timePeriod.append(formatedFooterTimeRange(fvDT));
                        if(iFilterValueDTCollection.hasNext())
                        {
                            value.append(" and ");
                        }
                    }
                    value.append(")");
                    //check for nulls
                    if(allowNulls) {
                    	value.append(" or " + colName + " is null ");
                    	footer.append(" (include NULLs)");
                    }
                    value.append(" )");
                }  // END BASIC TIME RANGE **********************************************
                // ADVANCED WHERE CLAUSE **********************************************
                else if(filterDT.getTheFilterCodeDT().getFilterType().equalsIgnoreCase(ReportConstantUtil.ADV_WCB))
                {
                    FilterCodeDT filterCodeDT = filterDT.getTheFilterCodeDT();
                    value.append(" (");
                    footer.append(filterCodeDT.getFilterName() + ": ");
                    ArrayList<Object> al2 = (ArrayList<Object> )filterDT.getTheFilterValueDTCollection();
                    for(int i = 0; i < al2.size(); i++)
                    {
                        FilterValueDT fvDT = (FilterValueDT)al2.get(i);
                        logger.debug("==== Filter Info For: " + filterDT.getTheFilterCodeDT().getFilterName());
                        logger.debug("Filter Value Type: " + fvDT.getValueType());
                        if(fvDT.getValueType().equalsIgnoreCase(ReportConstantUtil.VAL_TYPE_CLAUSE))
                        {
                            Long columnUID = fvDT.getColumnUid();
                            DataSourceColumnDT dataSourceColumnDT = getDataSourceColumnDT(columnUID, dataSourceVO);
                            String colName = dataSourceColumnDT.getColumnName();
                            String colTitle = dataSourceColumnDT.getColumnTitle();
                            String colType = "";
                            if(dataSourceColumnDT.getCodesetNm() != null && dataSourceColumnDT.getCodesetNm().length() > 0)
                             colType = "CODED";
                            else
                             colType = dataSourceColumnDT.getColumnTypeCode();
                            logger.debug("columnUID: " + columnUID.longValue());
                            logger.debug("colName: " + colName);
                            logger.debug("colTitle: " + colTitle);
                            logger.debug("colType: " + colType);
                            logger.debug("filterCode: " + filterDT.getTheFilterCodeDT().getFilterCode());
                            logger.debug("fvDT.getValueType(): " + fvDT.getValueType());
                            logger.debug("fvDT.getFilterValueOperator(): " + fvDT.getFilterValueOperator());
                            logger.debug("fvDT.getValueTxt(): " + fvDT.getValueTxt());
                            
                            String operator = fvDT.getFilterValueOperator();
                            
                            if( operator != null && (operator.equalsIgnoreCase("IN") || operator.equalsIgnoreCase("NN"))) 
                            {
                            	//To Do
                            	
                            } else if(fvDT.getValueTxt() == null || fvDT.getValueTxt().trim().length() == 0)
                            {
                                logger.debug("This Clause is skipped due to invalid valueTxt:" + fvDT.getValueTxt());
                                continue;
                            }

                            //check if the colName belongs to the nbs_rdb.Lab_Result_Val table
                            if (colName.equalsIgnoreCase("numeric_result_val") ||
                                colName.equalsIgnoreCase("ref_range_frm") ||
                                colName.equalsIgnoreCase("ref_range_to") ||
                                colName.equalsIgnoreCase("coded_result_val") ||
                                colName.equalsIgnoreCase("coded_result_val_desc") ||
                                colName.equalsIgnoreCase("text_result_val") ||
                                colName.equalsIgnoreCase("RESULT_UNITS")) {
                              isLabResultVal = true;
                            }

                            value.append(formatedCriteria(colName, colType, fvDT));
                            //Do the same for footer too ???
                            footer.append(formatedCriteria(colName, colType, fvDT));
                            /*
	                            footer.append(colTitle + " ");
	                            footer.append(fvDT.getFilterValueOperator() + " ");
	                            footer.append(fvDT.getValueTxt() + " ");
                            */
                        }
                        if(fvDT.getValueType().equalsIgnoreCase(ReportConstantUtil.VAL_TYPE_OPERATOR))
                        {
                            logger.debug("fvDT.getValueType(): " + fvDT.getValueType());
                            logger.debug("fvDT.getFilterValueOperator(): " + fvDT.getFilterValueOperator());
                            value.append(" " + fvDT.getFilterValueOperator() + " ");
                            footer.append(" " + fvDT.getFilterValueOperator() + " ");
                        }
                    }
                    value.append(")");

                }  // END ADVANCED WHERE CLAUSE **********************************************
                else
                {
                    logger.debug("Unknown Report Filter Type: " + filterDT.getTheFilterCodeDT().getFilterType());
                    throw new NEDSSException(UNEXPECTED_DATA_FORMAT);
                }
            }  // END Report Filters Loop

            //build a special where clause specific to nbs_rdb.Lab_Result_Val table columns
            if (isLabResultVal){
              value = buildLabResultsQuery(value);
            }
            
            //buildOderbyClause(reportVO,value);

            hmStrings.put("where", value);
            hmStrings.put("footer", footer);
            hmStrings.put("timePeriod", timePeriod);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return hmStrings;
    }
    
	public static String buildOderbyClause(ReportVO reportVO) {
		List<Object> aList = reportVO.getTheDisplayColumnDTList();
		StringBuffer value = new StringBuffer("");
		if (aList != null && aList.size() > 0) {
			Iterator<Object> ite = aList.iterator();
			DisplayColumnDT dcDT = null;
			while (ite.hasNext()) {
				dcDT = (DisplayColumnDT) ite.next();
				if (dcDT.getTheReportSortColumnDT() != null) {
					if (dcDT.getTheDataSourceColumnDT().getColumnTypeCode()
							.equals("STRING"))
						value.append(" Order by UPPER(");
					else
						value.append(" Order by ");
					value.append(dcDT.getTheDataSourceColumnDT()
							.getColumnName());
					if (dcDT.getTheDataSourceColumnDT().getColumnTypeCode()
							.equals("STRING"))
						value.append(") ");
					else
						value.append(" ");
					value.append(dcDT.getTheReportSortColumnDT() == null ? ""
							: dcDT.getTheReportSortColumnDT()
									.getReportSortOrderCode());
					break;
				}
			}
		}
		return value.toString();
	}

    private static String convertToSASDate(String sDate) throws NEDSSException
    {
        Date dt = null;
        SimpleDateFormat infmt1 = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat infmt2 = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat outfmt = new SimpleDateFormat("''ddMMMyyyy'''d'");
        try
        {
            dt = infmt1.parse(sDate);
        }
        catch(ParseException pe)
        {
            try
            {
                dt = infmt2.parse(sDate);
            }
            catch(ParseException pe2)
            {
                throw new NEDSSException(CONVERT_TO_SAS_DATE_ERROR);
            }
        }
        return outfmt.format(dt);
    }

    /**
  *  Convert a datetime value into SAS datetime constant.
  */
    private static String convertToSASDateTime(String sDateTime, SimpleDateFormat fmt) throws NEDSSException
    {
        Date dt = null;
        SimpleDateFormat infmt1 = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat infmt2 = new SimpleDateFormat("MM/yyyy");
        try
        {
            dt = infmt1.parse(sDateTime);
        }
        catch(ParseException pe)
        {
            try
            {
                dt = infmt2.parse(sDateTime);
            }
            catch(ParseException pe2)
            {
                throw new NEDSSException(CONVERT_TO_SAS_DATE_ERROR);
            }
        }
        return fmt.format(dt);
    }

    /**
  * format a record in filter value table into a where clause condition.
  *
  */
    private static String formatedCriteria(String colName, String colType, FilterValueDT fvDT) throws NEDSSException
    {
    	String criteria = "";
    	String operator = fvDT.getFilterValueOperator();
        SimpleDateFormat dtEndfmt = new SimpleDateFormat("''ddMMMyyyy:23:59:59'''dt'");
        SimpleDateFormat dtBeginfmt = new SimpleDateFormat("''ddMMMyyyy:00:00:00'''dt'");
    	if(colType!=null && colType.equals("CODED")) {
    		
    		if(operator.equalsIgnoreCase("EQ")) {
    			criteria = colName + " in(" + formatMutliValues(fvDT.getValueTxt()) + ")";
    		} else if(operator.equalsIgnoreCase("NE")) {
    			criteria = colName + " is null or " +  colName + " not in(" + formatMutliValues(fvDT.getValueTxt()) + ")";
    		} else if(operator.equalsIgnoreCase("IN")) {
    			criteria = colName + " is null";
    		} else if(operator.equalsIgnoreCase("NN")) {
    			criteria = colName + " is not null";
    		}
    		
    	} else {
    		
    		if(operator.equalsIgnoreCase("IN")) {
    			criteria = colName + " is null";
    			
    		} else if(operator.equalsIgnoreCase("NN")) {
    			criteria = colName + " is not null";
    			
    		} else if(operator.equalsIgnoreCase("SW")) {
    			criteria = colName + " like '" + fvDT.getValueTxt() + "%'";
    			
    		} else if(operator.equalsIgnoreCase("CO")) {
    			criteria = colName + " like '%" + fvDT.getValueTxt() + "%'";
    		
    		} else {
    			
    	        criteria = colName + " " + operator + " ";
    	        //if null operator is implemented, these two lines should be removed
    	        if(operator.equalsIgnoreCase("NE"))
    	        {
    	            criteria = colName + " is null or " + criteria;
    	        }
    	        if(operator.equalsIgnoreCase("BW"))
    	        {
    	        	String value = fvDT.getValueTxt();
    	        	StringTokenizer st = new StringTokenizer(value, ",");    
    	        	
    	        	if(colType != null && colType.equalsIgnoreCase("DATETIME")) {
    	        		
    	        		String beginDate = convertToSASDateTime(st.nextToken(), dtBeginfmt);
    	        		String endDate = convertToSASDateTime(st.nextToken(), dtEndfmt);
    	        		criteria = colName + " GE " + beginDate + " AND " + colName + " LE " + endDate;
    	        		
    	        	} else {
    	        		criteria = colName + " GE " + st.nextToken() + " AND " + colName + " LE " + st.nextToken();
    	        	}
    	        	
    	            criteria = "(" + criteria +  ")";
    	            return criteria;
    	        }
    	        
    	        if(colType.equalsIgnoreCase(ReportConstantUtil.COL_TYPE_STRING))
    	        {
    	            criteria += getQuotedValue(fvDT.getValueTxt());
    	        }
    	        else if(colType.equalsIgnoreCase(ReportConstantUtil.COL_TYPE_INTEGER) || colType.equalsIgnoreCase(ReportConstantUtil.COL_TYPE_NUMBER))
    	        {
    	            criteria += fvDT.getValueTxt();
    	        }
    	        else if(colType.equalsIgnoreCase(ReportConstantUtil.COL_TYPE_DATETIME))
    	        {
    	            criteria = formatedDateTimeRange(colName, fvDT);
    	        }
    	        else if(colType.equalsIgnoreCase(ReportConstantUtil.COL_TYPE_DATE))
    	        {
    	            criteria += convertToSASDate(fvDT.getValueTxt());
    	        }
    	        else
    	        {
    	            logger.debug("Unexpected Column Type: " + colType);
    	            throw new NEDSSException(UNEXPECTED_DATA_FORMAT);
    	        }
    			
    		}
    		
    	}
    	criteria = "(" + criteria +  ")";
        return criteria;
    }
    
    /**
     * 
     * @param colName
     * @param fvDT
     * @return
     * @throws NEDSSException
     */
    private static String formatedBasicDateTimeRange(String colName, FilterValueDT fvDT) throws NEDSSException {
    	
    	String dtSQL = colName + " ";
        SimpleDateFormat dtEndfmt = new SimpleDateFormat("''ddMMMyyyy:23:59:59'''dt'");
        SimpleDateFormat dtBeginfmt = new SimpleDateFormat("''ddMMMyyyy:00:00:00'''dt'");
    	if(fvDT.getValueType().equalsIgnoreCase("BEGIN_RANGE")) {
    		
    		dtSQL += "GE ";
            dtSQL += convertToSASDateTime(fvDT.getValueTxt(), dtBeginfmt);
            
        } else if(fvDT.getValueType().equalsIgnoreCase("END_RANGE")) {
        	
    		dtSQL += "LE ";
            dtSQL += convertToSASDateTime(fvDT.getValueTxt(), dtEndfmt);
            
        } else {
            logger.debug("Unexpected FilterValue Operator: " + fvDT.getFilterValueOperator());
            throw new NEDSSException(UNEXPECTED_DATA_FORMAT);
        }
        return dtSQL;    	
    }
    
    /**
  * Format the datetime value for basic time range filter
  *
  */
    private static String formatedDateTimeRange(String colName, FilterValueDT fvDT) throws NEDSSException
    {
        String dtSQL = colName + " " + fvDT.getFilterValueOperator() + " ";
        SimpleDateFormat dtEndfmt = new SimpleDateFormat("''ddMMMyyyy:23:59:59'''dt'");
        SimpleDateFormat dtBeginfmt = new SimpleDateFormat("''ddMMMyyyy:00:00:00'''dt'");
        if(fvDT.getFilterValueOperator().equalsIgnoreCase("LT") || fvDT.getFilterValueOperator().equalsIgnoreCase("GE") || fvDT.getValueType().equalsIgnoreCase("BEGIN_RANGE"))
        {
            dtSQL += convertToSASDateTime(fvDT.getValueTxt(), dtBeginfmt);
        }
        else if(fvDT.getFilterValueOperator().equalsIgnoreCase("GT") || fvDT.getFilterValueOperator()
            .equalsIgnoreCase("LE") || fvDT.getValueType().equalsIgnoreCase("END_RANGE"))
        {
            dtSQL += convertToSASDateTime(fvDT.getValueTxt(), dtEndfmt);
        }
        else if(fvDT.getFilterValueOperator().equalsIgnoreCase("EQ"))
        {
            dtSQL = "(" + colName + " GE " + convertToSASDateTime(fvDT.getValueTxt(), dtBeginfmt) + " and " + colName + " LE " + convertToSASDateTime(fvDT.getValueTxt(), dtEndfmt) + ")";
        }
        else if(fvDT.getFilterValueOperator().equalsIgnoreCase("NE"))
        {
            dtSQL = "(" + colName + " LT " + convertToSASDateTime(fvDT.getValueTxt(), dtBeginfmt) + " or " + colName + " GT " + convertToSASDateTime(fvDT.getValueTxt(), dtEndfmt) + " or " + colName + " is null )";
        }
        else
        {
            logger.debug("Unexpected FilterValue Operator: " + fvDT.getFilterValueOperator());
            throw new NEDSSException(UNEXPECTED_DATA_FORMAT);
        }
        return dtSQL;
    }

    /*
       public static String getDisplayValue(String filterCode, String fvValueType,String fvValueTxt) throws NEDSSException{
           String displayVal =  fvValueTxt;
           if (fvValueType.equalsIgnoreCase(ReportConstantUtil.VAL_TYPE_CODE)) {
             if (filterCode.equalsIgnoreCase(ReportConstantUtil.STATE_CODE)) {       //Basic State Filter J_S01
               displayVal = getKeyforValue(stateMap, fvValueTxt);
             } else if (filterCode.equalsIgnoreCase(ReportConstantUtil.COUNTY_CODE)) {  //Basic County Filter J_C01
               displayVal = getKeyforValue(countyMap, fvValueTxt);
             } else if (filterCode.equalsIgnoreCase(ReportConstantUtil.REGION_CODE)) {  //Basic Region Filter J_R01
               displayVal = getKeyforValue(countyMap, fvValueTxt);
             } else if (filterCode.equalsIgnoreCase(ReportConstantUtil.DISEASE_CODE)) {  //11600 --> Cholera* C_D01
               displayVal = getKeyforValue(conditionMap, fvValueTxt);
             } else {
               //No Lookup needed
               logger.debug("No Lookup Needed for FilterCode: "+ filterCode);
             }
           }
           return displayVal;
       }
     */

    /**
   *  State, County, Condition Maps all have one to one key/value(?)
   */

    /*
       public static String getKeyforValue(TreeMap treeMap,String fvValueTxt) throws NEDSSException{
         String name = null; //state, county, disease names
        Iterator<Object>  it = treeMap.keySet().iterator(); // names are the keys in map
         while (it.hasNext() ) {
           name = (String)it.next();
           String code = (String)treeMap.get(name);
           if ( code.equals(fvValueTxt) ) break;
         }
         return name;
       }
     */

    /**
  * format the valuetxt field for these basic fillters:
  * BAS_CON_LIST BAS_JUR_LIST BAS_TIM_LIST
  *
  */
    private static String formatedField(String type, String value) throws NEDSSException
    {
        String field = null;
        if(type.equalsIgnoreCase(ReportConstantUtil.COL_TYPE_STRING))
        {
            field = getQuotedValue(value);
        }
        else if(type.equalsIgnoreCase(ReportConstantUtil.COL_TYPE_DATE))
        {
            field = convertToSASDate(value);
        }
        else if(type.equalsIgnoreCase(ReportConstantUtil.COL_TYPE_INTEGER) || type.equalsIgnoreCase(ReportConstantUtil.COL_TYPE_NUMBER))
        {
            field = value;
        }
        else
        {
            logger.debug("Unexpected Column Type: " + type);
            throw new NEDSSException(UNEXPECTED_DATA_FORMAT);
        }
        return field;
    }

    /**
  * Construct footnote message for basic time range filter.
  */
    private static String formatedFooterTimeRange(FilterValueDT fvDT) throws NEDSSException
    {
        String timeRange = null;
        if(fvDT.getValueType().equalsIgnoreCase(ReportConstantUtil.BEGIN_RANGE) && fvDT.getValueTxt() != null)
        {
            timeRange = " From " + fvDT.getValueTxt();
        }
        else if(fvDT.getValueType().equalsIgnoreCase(ReportConstantUtil.END_RANGE) && fvDT.getValueTxt() != null)
        {
            timeRange = " To " + fvDT.getValueTxt();
        }
        else
        {
            logger.debug("Unexpected Filter Value Type: " + fvDT.getValueType());
            throw new NEDSSException(UNEXPECTED_DATA_FORMAT);
        }
        return timeRange;
    }

    /**
   *  Fromat the sql statement for T_T02 basic filter.
   */
    private static String formatedYearRange(String colName, FilterValueDT fvDT) throws NEDSSException
    {
        String yrRangeSQL = null;
        if(fvDT.getValueType().equalsIgnoreCase(ReportConstantUtil.BEGIN_RANGE))
        {
            yrRangeSQL = colName + " GE " + "'01Jan" + fvDT.getValueTxt().trim() + ":00:00:00'dt";
        }
        else if(fvDT.getValueType().equalsIgnoreCase(ReportConstantUtil.END_RANGE))
        {
            yrRangeSQL = colName + " LE " + "'31Dec" + fvDT.getValueTxt().trim() + ":23:59:59'dt";
        }
        else
        {
            logger.debug("Unexpected Filter Value Type: " + fvDT.getValueType());
            throw new NEDSSException(UNEXPECTED_DATA_FORMAT);
        }
        return yrRangeSQL;
    }
    
private static String formatedBasicMonthYearRange(String colName, FilterValueDT fvDT) throws NEDSSException {
    	
    	String dtSQL = colName + " ";
        SimpleDateFormat dtEndfmt = new SimpleDateFormat("''ddMMMyyyy:23:59:59'''dt'");
        SimpleDateFormat dtBeginfmt = new SimpleDateFormat("''ddMMMyyyy:00:00:00'''dt'");
    	if(fvDT.getValueType().equalsIgnoreCase("BEGIN_RANGE")) {
        	StringBuffer sb = new StringBuffer(fvDT.getValueTxt());
        	sb.insert(3, "01/");
    		dtSQL += "GE ";
            dtSQL += convertToSASDateTime(sb.toString(), dtBeginfmt);
            
        } else if(fvDT.getValueType().equalsIgnoreCase("END_RANGE")) {
        	StringBuffer sb = new StringBuffer(fvDT.getValueTxt());
        	sb.insert(3, "31/");
    		dtSQL += "LE ";
            dtSQL += convertToSASDateTime(sb.toString(), dtEndfmt);
            
        } else {
            logger.debug("Unexpected FilterValue Operator: " + fvDT.getFilterValueOperator());
            throw new NEDSSException(UNEXPECTED_DATA_FORMAT);
        }
        return dtSQL;    	
    }

    /**
     * Returns a StringBuffer object that contains a where clause specific to Lab_Test_Report view which
     * will return the associated ORDER, RESULT, R_RESULT rows. This will only occur if a column that belongs to Lab_Result_Val
     * table is selected in the Advanced Filter
     *
     * @param whereClause original value of the whereClause
     * @return StringBuffer
     */
    private static StringBuffer buildLabResultsQuery(StringBuffer whereClause){
      StringBuffer lab = new StringBuffer();
      String labResultValQuery = ReportConstants.WHERE_LAB_RESULT_VAL;
      lab.append(labResultValQuery);
      lab.append(whereClause);
      lab.append(")");

      return lab;
    }
    
    private static String formatMutliValues(String str)
    {
        if(str == null)
        {
            return str;
        }
        StringTokenizer sz = new StringTokenizer(str, "|");
        StringBuffer sb = new StringBuffer("'");
        while(sz.hasMoreTokens())
        {
            String token = sz.nextToken();
            sb.append(token);
            if(sz.hasMoreTokens())
            {
                sb.append("', '");
            }
        }
        sb.append("'");
        return sb.toString();
    }
    
    private static boolean includeNullsNoValues(ReportFilterDT filterDT) {
    	
    	ArrayList<Object> filterValues = (ArrayList<Object> )filterDT.getTheFilterValueDTCollection();
    	Iterator iValues = filterValues.iterator();
        while(iValues.hasNext())
        {
            FilterValueDT filterValueDT = (FilterValueDT)iValues.next();
            if(filterValueDT.getValueType() != null && filterValueDT.getValueType().equalsIgnoreCase("none")) {
            	return true;
            }
        }
    	
    	return false;    	
    }
    
    public static String makeEndRange(String endRange) {
    	
    	  int month = Integer.parseInt(endRange.substring(0, endRange.indexOf("/")));
    	  int year = Integer.parseInt(endRange.substring(endRange.indexOf("/")+1));
    	  GregorianCalendar c = new GregorianCalendar();
    	  int [] daysInMonths = {31,28,31,30,31,30,31,31,30,31,30,31};
    	  daysInMonths[1] += c.isLeapYear(year) ? 1 : 0;
    	  int aMonth = month-1; // To bring month days from array '0-11'
    	  int days = daysInMonths[aMonth];
    	  return month + "/" + days + "/" + year;
    	}
    

}
