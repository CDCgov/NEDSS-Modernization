package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceColumnDT;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Prepares a blank record for the Data_source_column table.
 * @author Ed Jenkins
 */
public class NewDataSourceColumn extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(NewDataSourceColumn.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    /**
     * DAO for Data_Source.
     */
    private static final DataSourceColumnDAO daoDataSourceColumn = new DataSourceColumnDAO();

    /**
     * Column Type Code option string.
     */
    private static String strColumnTypeCode = null;

    /**
     * Constructor.
     */
    public NewDataSourceColumn()
    {
        // ReportAdmin.ColumnTypeCodeSRT
        TreeMap<Object,Object> tmColumnTypeCode = new TreeMap<Object,Object>();
        tmColumnTypeCode.put("STRING", "String");
        tmColumnTypeCode.put("INTEGER", "Number");
        tmColumnTypeCode.put("DATETIME", "Date/Time");
        StringBuffer sbColumnTypeCode = new StringBuffer(STRING_BUFFER_SIZE);
        Set<Object> setColumnTypeCode = tmColumnTypeCode.keySet();
       Iterator<Object>  iColumnTypeCode = setColumnTypeCode.iterator();
        while(iColumnTypeCode.hasNext())
        {
            String k = (String)iColumnTypeCode.next();
            String v = (String)tmColumnTypeCode.get(k);
            sbColumnTypeCode.append(k);
            sbColumnTypeCode.append("$");
            sbColumnTypeCode.append(v);
            sbColumnTypeCode.append("|");
        }
        strColumnTypeCode = sbColumnTypeCode.toString();
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        try
        {
            session.setAttribute("ReportAdmin.ColumnTypeCodeSRT", strColumnTypeCode);
            DataSourceDT dtDataSource = (DataSourceDT)session.getAttribute("ReportAdmin.dtDataSource");
            long data_source_uid = dtDataSource.getDataSourceUID();
            DataSourceColumnDT dtDataSourceColumn = new DataSourceColumnDT();
            dtDataSourceColumn.setDataSourceUID(data_source_uid);
            dtDataSourceColumn.setColumnTypeCode("STRING");
            dtDataSourceColumn.setDisplayable("Y");
            dtDataSourceColumn.setFilterable("Y");
            request.setAttribute("ReportAdmin.dtDataSourceColumn", dtDataSourceColumn);
            // Return Link
            String strLinkName = "Return to View Data Source";
            String direction = (String) session.getAttribute("direction");
            String sortMethod = (String) session.getAttribute("sortMethod");
            String strLinkAddr = "/nbs/ViewDataSource.do?data_source_uid=" + data_source_uid + "&contextAction=Sort&direction=" + direction + "&sortMethod=" + sortMethod;
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            CachedDropDownValues cache = new CachedDropDownValues();
            request.setAttribute("SrtDropDownsSelects",cache.getLDFDropdowns());


        	StringBuffer availabeList = new StringBuffer();
        	StringBuffer maxLengthList = new StringBuffer();
            ArrayList<Object> dsColumnsFromDb = daoDataSourceColumn.getDSMetaData(dtDataSource);
        	ArrayList<Object> existingDsColumns = (ArrayList<Object> ) session.getAttribute("ReportAdmin.alDataSourceColumn");

        	if(existingDsColumns != null) {

        		int size = dsColumnsFromDb.size();
        		int existingSize = existingDsColumns.size();

        		if(existingSize < size) {
        			Iterator<Object> outerIter = dsColumnsFromDb.iterator();
        			while(outerIter.hasNext()) {
        				boolean found = false;
        				DataSourceColumnDT dt = (DataSourceColumnDT) outerIter.next();
        				String colNameToFind = dt.getColumnName();
        				Iterator<Object> innerIter = existingDsColumns.iterator();
        				while(innerIter.hasNext()) {
        					DataSourceColumnDT iDsColumn = (DataSourceColumnDT) innerIter.next();
        					if(iDsColumn.getColumnName().equalsIgnoreCase(colNameToFind)) {
        						found = true;
        					}
        				}
        				if(found == false) {
        					availabeList.append(colNameToFind).append("$").append(colNameToFind).append("|");
        					maxLengthList.append(colNameToFind).append("$").append(dt.getColumnMaxLen_s()).append("|");
        				}
        			}

        			session.setAttribute("ReportAdmin.AvailabeDSColumnList", availabeList.toString());
        			request.setAttribute("availabeDSColumnMaxLength", maxLengthList.toString());
        		}
        	}
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report NewDataSourceCol: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            throw new ServletException(ex.getMessage(),ex);
        }
        return mapping.findForward("default");
    }



}
