package gov.cdc.nedss.webapp.nbs.action.report;

import gov.cdc.nedss.exception.*;
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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpSession;

import org.w3c.dom.*;

public class AdvanceWebProcessor
{

    static final LogUtils logger = new LogUtils((AdvanceWebProcessor.class).getName());  //Used for logging
    protected static boolean DEBUG_MODE = false;  // Used for debugging
    private static CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
    
    /**
     * constructor
     */
    public AdvanceWebProcessor()
    {
    }

    /**
     * name: set Method
     */
    public static void setWhereClauseBuilder(ReportVO reportVO, String criteria) throws NEDSSException
    {
        ArrayList<Object> filtersDTCollection  = (ArrayList<Object> )reportVO.getTheReportFilterDTCollection();
       Iterator<Object>  iFilters = filtersDTCollection.iterator();
        outer: while(iFilters.hasNext())
        {
            ReportFilterDT reportFilterDT = (ReportFilterDT)iFilters.next();
            FilterCodeDT filterCodeDT = reportFilterDT.getTheFilterCodeDT();
            if(filterCodeDT.getFilterCode().equalsIgnoreCase("A_W01"))
            {
                if(criteria != null)
                {
                    ArrayList<Object> filterValues = (ArrayList<Object> )reportFilterDT.getTheFilterValueDTCollection();
                    if(filterValues.size() > 0)
                    {
                        filterValues.clear();
                    }
                    ArrayList<Object> newFilterValues = new ArrayList<Object> ();
                    ArrayList<Object> list = XMLRequestHelper.getTable(criteria);
                    logger.info("******* list  is: " + list.toString());
                   Iterator<Object>  iterator = null;
                    for(iterator = list.iterator(); iterator.hasNext();)
                    {
                        ArrayList<?> list1 = (ArrayList<?> )iterator.next();
                        FilterValueDT filterValueDT = new FilterValueDT();
                        filterValueDT.setReportFilterUid(reportFilterDT.getReportFilterUid());
                        filterValueDT.setSequenceNbr(new Integer((String)list1.get(0)));
                        String valueType = (String)list1.get(1);
                        filterValueDT.setValueType(valueType);
                        if(valueType.equalsIgnoreCase("CLAUSE"))
                        {
                            logger.info("**** valueType is CLAUSE ****");
                            filterValueDT.setColumnUid(new Long((String)list1.get(2)));
                            filterValueDT.setFilterValueOperator((String)list1.get(3));
                            
                            String operator = (String) list1.get(3);
                            String value = "";
                            if(operator.equalsIgnoreCase("BW")) {
                            	value = list1.get(4) + "," + list1.get(5);
                            } else {
                            	value = (String)list1.get(4);
                            }
                            
                            filterValueDT.setValueTxt(value);
                            
                        }
                        else if(valueType.equalsIgnoreCase("OPERATOR"))
                        {
                            logger.info("~~ **** valueType is OPERATOR ****");
                            filterValueDT.setFilterValueOperator((String)list1.get(3));
                        }
                        newFilterValues.add(filterValueDT);
                    }  //for
                    reportFilterDT.setTheFilterValueDTCollection(newFilterValues);
                    break outer;
                }  //criteria != null
            }
            else
            {
                continue outer;
            }
        }  //while
    }  //setWhereClause

    /**
     * method: get Method
     */
    public static String getWhereClauseBuilder(ReportVO reportVO) throws NEDSSException
    {
        ArrayList<Object> filtersDTCollection  = (ArrayList<Object> )reportVO.getTheReportFilterDTCollection();
        int capacity = filtersDTCollection.size();
        //logger.info("capacity is: "+capacity);
       Iterator<Object>  iFilters = filtersDTCollection.iterator();
        StringBuffer values = new StringBuffer();
        values.append("<table role=\"presentation\">");
        outer: while(iFilters.hasNext())
        {
            ReportFilterDT reportFilterDT = (ReportFilterDT)iFilters.next();
            FilterCodeDT filterCodeDT = reportFilterDT.getTheFilterCodeDT();
            if(filterCodeDT.getFilterCode().equalsIgnoreCase("A_W01"))
            {
                ArrayList<Object> filterValues = (ArrayList<Object> )reportFilterDT.getTheFilterValueDTCollection();
               Iterator<Object>  iValues = filterValues.iterator();
                while(iValues.hasNext())
                {
                    FilterValueDT filterValueDT = (FilterValueDT)iValues.next();
                    values.append("<record>");
                    values.append("<field>");
                    if(filterValueDT.getSequenceNbr() != null)
                    {
                        values.append(filterValueDT.getSequenceNbr());
                        values.append("</field>");
                    }
                    else
                    {
                        values.append("</field>");
                    }
                    values.append("<field>");
                    if(filterValueDT.getValueType() != null)
                    {
                        values.append(XMLRequestHelper.xmlEncode(filterValueDT.getValueType()));
                        values.append("</field>");
                    }
                    else
                    {
                        values.append("</field>");
                    }
                    values.append("<field>");
                    if(filterValueDT.getColumnUid() != null)
                    {
                        values.append(filterValueDT.getColumnUid());
                        values.append("</field>");
                    }
                    else
                    {
                        values.append("</field>");
                    }
                    values.append("<field>");
                    if(filterValueDT.getFilterValueOperator() != null)
                    {
                        values.append(XMLRequestHelper.xmlEncode(filterValueDT.getFilterValueOperator()));
                        values.append("</field>");
                    }
                    else
                    {
                        values.append("</field>");
                    }
                    values.append("<field>");
                    if(filterValueDT.getValueTxt() != null)
                    {	
                    	if(filterValueDT.getFilterValueOperator().equalsIgnoreCase("BW")) {
                    		//get From and To Values
                    		StringTokenizer st = new StringTokenizer(filterValueDT.getValueTxt(),",");
                    		values.append(st.nextToken());
                    		values.append("</field>");
                    		values.append("<field>");
                    		values.append(st.nextToken());
                    	} else {
                            values.append(XMLRequestHelper.xmlEncode(filterValueDT.getValueTxt()));                    		
                    	}
                        values.append("</field>");
                    }
                    else
                    {
                        values.append("</field>");
                    }
                    values.append("</record>");
                }
            }
            else
            {
                continue outer;
            }
        }
        values.append("</table>");
        return values.toString();
    }
    
    /**
     * 
     * @param sb
     * @param treeMap
     * @return
     */
    private static String getXMLString(StringBuffer sb, TreeMap<?,?> treeMap)
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
				if (value.contains("&"))
					sb.append(value.trim());
				else
					sb.append(XMLRequestHelper.xmlEncode(value.trim()));
                sb.append("</field>");
                sb.append("<field>");
				if (key.contains("&"))
					sb.append(key.trim());
				else
					sb.append(XMLRequestHelper.xmlEncode(key.trim()));
                sb.append("</field>");
                sb.append("<field>");
                sb.append("false");
                sb.append("</field>");
                sb.append("</record>");
            } 
            sb.append("</table>");
        }
        return sb.toString();
    }  
    
    public static String  fetchCodedValues(String paramValue, ReportVO reportVO, HttpSession session) throws Exception
    {
    	StringTokenizer st = new StringTokenizer(paramValue, "|");
    	
    	Long columnUid = Long.valueOf(st.nextToken());
    	String codesetNm = st.nextToken();
    	String codeOrDesc = st.nextToken();
    	
    	StringBuffer sb = new StringBuffer();
    	TreeMap<?,?> map = new TreeMap<Object,Object>();
    	
    	if(codeOrDesc != null && codeOrDesc.equalsIgnoreCase("h")) {
    		
    		map = fetchHardCodedValues(columnUid, reportVO, session);
    		
    	} else {
    		
        	if(codesetNm.indexOf(".") == -1) {
        		
        		if(codesetNm.trim().equalsIgnoreCase("PHC_TYPE") || codesetNm.trim().equalsIgnoreCase("Condition_code")) {
        			
        			map = cachedDropDownValues.getReportBasicPageConditionMap();
        			
        		} else if(codesetNm.trim().equalsIgnoreCase("State_code")) {
        			
        			map = cachedDropDownValues.getStateCodes1("STATE_CCD");
        			
        		} else if(codesetNm.trim().equalsIgnoreCase("COUNTY_CCD") || codesetNm.trim().equalsIgnoreCase("State_county_code_value")) {
        			
        			String state = PropertyUtil.getInstance().getNBS_STATE_CODE();
        			map = cachedDropDownValues.getCountyCodesAsTreeMap(state);
        		
        		} else if(codesetNm.trim().equalsIgnoreCase("S_JURDIC_C") || codesetNm.trim().equalsIgnoreCase("JURISDICTION_CODE")) {//NBSCentral defect #12524
        			
        			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getJurisdictionCodedValues());
        			
        		} else if(codesetNm.trim().equalsIgnoreCase("Program_area_code") || codesetNm.trim().equalsIgnoreCase("S_PROGRA_C")) {
        			
        			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getProgramAreaCodedValues());
        			
        		} else if(codesetNm.trim().equalsIgnoreCase("NAICS_Industry_code")) {
        			
        			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getNAICSGetIndustryCodeAsTreeMap());

        		} else if(codesetNm.trim().equalsIgnoreCase("Language_code")) {
        			
        			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getLanguageCodeAsTreeMap());

        		} else if(codesetNm.trim().equalsIgnoreCase("country_code")) {
        			
        			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getCountryCodesAsTreeMap());
        		}
        		
        		else if(codesetNm.trim().equalsIgnoreCase("PLACE_LIST")) {
        			
        			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getPlacesWithQEC());
        		}
				else if(codesetNm.trim().equalsIgnoreCase("Race_Code")) {
        			
        			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getRaceCodes());
        		}
        		
        	} else {
        		String srtTable = codesetNm.substring(0,codesetNm.indexOf("."));        		       		
        		codesetNm = codesetNm.substring(codesetNm.indexOf(".")+1);
        		
        		if(srtTable.equalsIgnoreCase("CODE_VALUE_GENERAL"))
        			map =cachedDropDownValues.getCodedValuesAsTreeMap(codesetNm);
        		
        		else if(srtTable.equalsIgnoreCase("CODE_VALUE_CLINICAL"))
        			map = cachedDropDownValues.getSAICDefinedCodedValuesAsTreeMap(codesetNm);        		
        	}
    		
    	}
    	return getXMLString(sb, map);
    }    
    
    private static TreeMap<Object,Object> fetchHardCodedValues(Long columnUid, ReportVO reportVO, HttpSession session) throws Exception {
    	
        MainSessionCommand msCommand = null;
        MainSessionHolder mainSessionHolder = new MainSessionHolder();
        
    	Long dataSourceUid = reportVO.getTheReportDT().getDataSourceUid();
    	DataSourceVO dataSourceVO = getDataSource(dataSourceUid, session);    	
    	DataSourceColumnDT dscDT = getDataSourceColumnDT(columnUid, dataSourceVO);
    	String columnNm = dscDT.getColumnName();
    	String dataSourceNm = dataSourceVO.getTheDataSourceDT().getDataSourceName();
    	StringTokenizer st = new StringTokenizer(dataSourceNm,".");
    	String dbNm = st.nextToken();
    	String tableNm = st.nextToken();
    	String schema = NEDSSConstants.ODS;
    	if(dbNm.endsWith("rdb"))
    		schema = NEDSSConstants.RDB;
    	
    	String sqlQuery = "SELECT DISTINCT " + columnNm + " FROM " + tableNm +  " WHERE " + columnNm +  "  IS NOT NULL ORDER BY " + columnNm;
    	
    	String sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
    	String sMethod = "getDistinctColumnValues";
    	
    	Object[] oParams = new Object[] {schema, sqlQuery};
    	
        if (msCommand == null) {
          msCommand = mainSessionHolder.getMainSessionCommand(session);
        }
        TreeMap<Object,Object> valueMap = (TreeMap<Object,Object>) msCommand.processRequest(sBeanJndiName, sMethod, oParams).get(0);

    	return valueMap;
    	
    }
    
    private static DataSourceVO getDataSource(Long dataSourceUid, HttpSession session) throws Exception {
      
      MainSessionCommand msCommand = null;
      MainSessionHolder mainSessionHolder = new MainSessionHolder();
        
      DataSourceVO dataSourceVO = null;
      String sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
      String sMethod = "getDataSource";
      Object[] oParams = new Object[] {dataSourceUid};
	
	  if (msCommand == null) {
	    msCommand = mainSessionHolder.getMainSessionCommand(session);
	  }
	
	  ArrayList<Object> dsVOList = (ArrayList<Object> ) msCommand.processRequest(
	      sBeanJndiName, sMethod, oParams);
	  dataSourceVO = (DataSourceVO) dsVOList.get(0);
	  
	  return dataSourceVO;

} //getDataSource

    private static DataSourceColumnDT getDataSourceColumnDT(Long colUid, DataSourceVO dataSourceVO) throws Exception
    {
        ArrayList<Object> arDSVO = (ArrayList<Object> )dataSourceVO.getTheDataSourceColumnDTCollection();
        DataSourceColumnDT dscDT = null;
        for(int k = 0; k < arDSVO.size(); k++)
        {
            DataSourceColumnDT dscDTtemp = (DataSourceColumnDT)arDSVO.get(k);
            if(colUid.equals(dscDTtemp.getColumnUid()))
            {
                dscDT = dscDTtemp;
                break;
            }
        }
        return dscDT;
    }
    

}
