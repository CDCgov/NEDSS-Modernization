package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceColumnDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.io.IOException;

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
 * Edits records in the Data_source_column table.
 * @author Ed Jenkins
 */
public class UpdateDataSourceColumn extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(UpdateDataSourceColumn.class);

    /**
     * DAO for Data_source_column.
     */
    private static final DataSourceColumnDAO daoDataSourceColumn = new DataSourceColumnDAO();

    /**
     * Constructor.
     */
    public UpdateDataSourceColumn()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        try
        {
            // Return Link
            String strLinkName = "Return to Data Sources";
            String strLinkAddr = "/nbs/ListDataSource.do";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);

            String column_uid_s = request.getParameter("column_uid");
            long column_uid = Long.parseLong(column_uid_s);
            DataSourceColumnDT dtDataSourceColumn = daoDataSourceColumn.view(column_uid);

            dtDataSourceColumn.setColumnMaxLen(request.getParameter("column_max_len"));
            //dtDataSourceColumn.setColumnName(request.getParameter("column_name"));
            dtDataSourceColumn.setColumnTitle(request.getParameter("column_title"));
            dtDataSourceColumn.setColumnTypeCode(request.getParameter("column_type_code"));
            dtDataSourceColumn.setDataSourceUID(request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim());
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
            daoDataSourceColumn.edit(dtDataSourceColumn);
            request.setAttribute("ReportAdmin.dtDataSourceColumn", dtDataSourceColumn);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report UpdateDataSourceCol: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }

}
