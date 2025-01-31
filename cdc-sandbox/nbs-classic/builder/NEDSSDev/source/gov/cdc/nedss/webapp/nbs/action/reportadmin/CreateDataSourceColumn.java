package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceColumnDT;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.util.ArrayList;

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
 * Creates records in the Data_source_column table.
 * @author Ed Jenkins
 */
public class CreateDataSourceColumn extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(CreateDataSourceColumn.class);

    /**
     * DAO for Data_source_column.
     */
    private static final DataSourceColumnDAO daoDataSourceColumn = new DataSourceColumnDAO();

    /**
     * Constructor.
     */
    public CreateDataSourceColumn()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        String strRedirect = "";
        try
        {
            // Return Link
            String strLinkName = "Return to View Data Source";
            String direction = (String) session.getAttribute("direction");
            String sortMethod = (String) session.getAttribute("sortMethod");
            String strLinkAddr = "/nbs/ViewDataSource.do?data_source_uid=" + request.getParameter("data_source_uid") + "&contextAction=Sort&direction=" + direction + "&sortMethod=" + sortMethod;

            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            long next_column_uid = daoDataSourceColumn.getNextUID();
            strRedirect = "/ViewDataSourceColumn.do?data_source_uid=" + request.getParameter("data_source_uid") + "&column_uid=" + next_column_uid;
            //DataSourceColumnDT dtDataSourceColumn = (DataSourceColumnDT)request.getAttribute("ReportAdmin.dtDataSourceColumn");
            DataSourceColumnDT dtDataSourceColumn = new DataSourceColumnDT();
            dtDataSourceColumn.setColumnUID(next_column_uid);
            dtDataSourceColumn.setColumnMaxLen(request.getParameter("column_max_len"));
            dtDataSourceColumn.setColumnName(request.getParameter("column_name"));
            dtDataSourceColumn.setColumnTitle(request.getParameter("column_title"));
            dtDataSourceColumn.setColumnTypeCode(request.getParameter("column_type_code"));
            dtDataSourceColumn.setDataSourceUID(request.getParameter("data_source_uid"));
            dtDataSourceColumn.setDescTxt(request.getParameter("desc_txt"));
            dtDataSourceColumn.setCodeDescCd(request.getParameter("CodeOrDesc"));

            String srtTableNm= request.getParameter("srtTableNm") == null ? "" : request.getParameter("srtTableNm");
            String codeSetNm = request.getParameter("codesetNm") == null ? "" : request.getParameter("codesetNm");

            if(srtTableNm.trim().length() > 0 && codeSetNm.trim().length() > 0)
            	dtDataSourceColumn.setCodesetNm(srtTableNm + "." + codeSetNm);
            else if(srtTableNm.trim().length() > 0 && codeSetNm.trim().length() == 0)
            	dtDataSourceColumn.setCodesetNm(srtTableNm);
            else
            	dtDataSourceColumn.setCodesetNm(null);

            if(request.getParameter("displayable") == null)
            {
                dtDataSourceColumn.setDisplayable("N");
            }
            else
            {
                dtDataSourceColumn.setDisplayable("Y");
            }
            if(request.getParameter("filterable") == null)
            {
                dtDataSourceColumn.setFilterable("N");
            }
            else
            {
                dtDataSourceColumn.setFilterable("Y");
            }
            //dtDataSourceColumn.validate();
            daoDataSourceColumn.add(dtDataSourceColumn);

            request.setAttribute("ReportAdmin.dtDataSourceColumn", dtDataSourceColumn);
        }
        catch(Exception ex)
        {
        	 logger.error("Error in Report CreateDataSourceColumn: " +ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }

        ActionForward af = new ActionForward();
        af.setName("ViewDataSourceColumn");
        af.setPath(strRedirect);
        af.setRedirect(true);
        return af;
        //return mapping.findForward("default");
    }

}
