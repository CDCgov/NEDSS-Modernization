package gov.cdc.nedss.webapp.nbs.action.report;

import gov.cdc.nedss.util.*;
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

import java.sql.Timestamp;
import java.util.*;

public class ColumnWebProcessor
{

    static final LogUtils logger = new LogUtils((ColumnWebProcessor.class).getName());  //Used for logging
    protected static boolean DEBUG_MODE = false;  // Used for debugging

    public ColumnWebProcessor()
    {
    }

    public static ReportVO setSelectedColumns(ReportVO reportVO, String columns, ArrayList<Object> availableColList, String sortColumn, String sortOrder)
    {
        ArrayList<Object> selectedList = (ArrayList<Object> )reportVO.getTheDisplayColumnDTList();
        selectedList.clear();
        if(columns != null)
        {
            //  StringBuffer columns1 = new StringBuffer();
            //  for(int i=0;i<columns.length;i++) {
            //      columns1.append(columns[i]);
            //  }
            ArrayList<Object> list = XMLRequestHelper.getTable(columns.toString());
           Iterator<Object>  iterator = null;
            int i = 0;
            for (iterator = list.iterator(); iterator.hasNext();) {
				i++;
				// Option option = (Option) iterator.next();
				// list1 contains uid, description & selected
				ArrayList<Object> list1 = (ArrayList<Object> ) iterator.next();
				DisplayColumnDT newDisplayColumnDT = new DisplayColumnDT();
				newDisplayColumnDT
						.setColumnUid(new Long((String) list1.get(0)));
				newDisplayColumnDT.setDataSourceUid(reportVO.getTheReportDT()
						.getDataSourceUid());
				newDisplayColumnDT.setReportUid(reportVO.getTheReportDT()
						.getReportUid());
				newDisplayColumnDT.setSequenceNbr(new Integer(i));
				newDisplayColumnDT.setStatusCd("A");
				Iterator<Object> aIterator = null;
				if (sortColumn != null && !sortColumn.trim().equals("")
						&& sortOrder != null && !sortOrder.trim().equals("")
						&& (newDisplayColumnDT.getColumnUid().longValue() == new Long(
								sortColumn).longValue())) {
					setReportColumnSort(newDisplayColumnDT, sortOrder);
				}
				for (aIterator = availableColList.iterator(); aIterator
						.hasNext();) {
					DataSourceColumnDT dataSourceColumnDT = (DataSourceColumnDT) aIterator
							.next();
					if (dataSourceColumnDT.getColumnUid().longValue() == newDisplayColumnDT
							.getColumnUid().longValue()) {
						newDisplayColumnDT
								.setTheDataSourceColumnDT(new DataSourceColumnDT(
										dataSourceColumnDT));
						break;
					}
				}
                selectedList.add(newDisplayColumnDT);
            }  // for
        }  // columns != null
        reportVO.setTheDisplayColumnDTList(selectedList);
        return reportVO;
    }  //setSelectedColumns

    
    private static void setReportColumnSort(DisplayColumnDT newDisplayColumnDT, String sortOder)
    {
    	ReportSortColumnDT rscDT = new ReportSortColumnDT();
    	rscDT.setColumnUid(newDisplayColumnDT.getColumnUid());
    	rscDT.setDataSourceUid(newDisplayColumnDT.getDataSourceUid());
    	rscDT.setReportSortOrderCode(sortOder);
    	rscDT.setReportSortSequenceNum(new Integer(1));
    	rscDT.setReportUid(newDisplayColumnDT.getReportUid());
    	rscDT.setStatusCd("A");
    	rscDT.setStatusTime(new Timestamp(new Date().getTime()));
    	
    	newDisplayColumnDT.setTheReportSortColumnDT(rscDT);
    	
    	
    }
    public static String getSelectedColumns(ReportVO reportVO,boolean selectSortColumn)
    {
        ArrayList<Object> selectedList = (ArrayList<Object> )reportVO.getTheDisplayColumnDTList();
       Iterator<Object>  selectedIt = null;
        StringBuffer values = new StringBuffer();
        values.append("<table role=\"presentation\">");
        for(selectedIt = selectedList.iterator(); selectedIt.hasNext();)
        {
            values.append("<record>");
            DisplayColumnDT displayColumnDT = (DisplayColumnDT)selectedIt.next();
            values.append("<field>");
            values.append(displayColumnDT.getColumnUid());
            values.append("</field>");
            values.append("<field>");
            values.append(XMLRequestHelper.xmlEncode(displayColumnDT.getTheDataSourceColumnDT().getColumnTitle().trim()));
            values.append("</field>");
            values.append("<field>");
            if(selectSortColumn && displayColumnDT.getTheReportSortColumnDT()!=null)
            	values.append("true");
            else
            values.append("false");
            values.append("</field>");
            values.append("</record>");
        }
        values.append("</table>");
        return values.toString();
    }  //getSelectedColumns
    
    public static String getSortOrders(ReportVO rVO, String codeSet, String currentState)
    {
        
        CachedDropDownValues cdv = new CachedDropDownValues();
        TreeMap<?,?> map = cdv.getCodedValuesAsTreeMap(codeSet);
        return getSortOrderValues(map, rVO,currentState);
    }
    
    /**
     * Method to return XML String for filters
     */
   private static String getSortOrderValues(TreeMap<?,?> treeMap, ReportVO rVO,
			String currentState) {
		StringBuffer sb = new StringBuffer();
		if (treeMap != null) {
			sb.append("<table role=\"presentation\">");
			Set<?> set = treeMap.keySet();
			Iterator<?> itr = set.iterator();
			while (itr.hasNext()) {
				sb.append("<record>");
				String key = (String) itr.next();
				String value = (String) treeMap.get(key);
				sb.append("<field>");
				sb.append(XMLRequestHelper.xmlEncode(value.trim()));
				sb.append("</field>");
				sb.append("<field>");
				sb.append(XMLRequestHelper.xmlEncode(key.trim()));
				sb.append("</field>");
				if (currentState != null) {
					if (value.equals(currentState)) {
						sb.append("<field>");
						sb.append("true");
						sb.append("</field>");
						sb.append("</record>");
					} else {
						sb.append("<field>");
						sb.append("false");
						sb.append("</field>");
						sb.append("</record>");
					}
				} else {
					ArrayList<Object> displayValues = (ArrayList<Object> ) rVO
							.getTheDisplayColumnDTList();
					int i = displayValues.size();
					Iterator<Object> iValues = null;
					boolean bFound = false;
					if (i > 0) {
						for (iValues = displayValues.iterator(); iValues
								.hasNext();) {
							DisplayColumnDT dcDT = (DisplayColumnDT) iValues
									.next();
							if (dcDT.getTheReportSortColumnDT() != null
									&& dcDT.getTheReportSortColumnDT()
											.getReportSortOrderCode()
											.equalsIgnoreCase(value)) {
								sb.append("<field>");
								sb.append("true");
								sb.append("</field>");
								bFound = true;
								break;
							}
						}
					}
					if (!bFound) {
						sb.append("<field>");
						sb.append("false");
						sb.append("</field>");
					}
					sb.append("</record>");
				}
			} // while

			sb.append("</table>");
		}
		return sb.toString();
	}

}
