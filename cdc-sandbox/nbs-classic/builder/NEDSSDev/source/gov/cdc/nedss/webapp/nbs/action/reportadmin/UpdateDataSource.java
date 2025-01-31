package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dao.DataSourceDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

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
 * Edits records in the Data_Source table.
 * @author Ed Jenkins
 */
public class UpdateDataSource extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(UpdateDataSource.class);

    /**
     * DAO for Data_Source.
     */
    private static final DataSourceDAO daoDataSource = new DataSourceDAO();

    /**
     * Constructor.
     */
    public UpdateDataSource()
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
            String strLinkName = "Return to Data Sources";
            String strLinkAddr = "/nbs/ListDataSource.do";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            DataSourceDT dtDataSource = (DataSourceDT)session.getAttribute("ReportAdmin.dtDataSource");
            strRedirect = "ViewDataSource.do?data_source_uid=" + dtDataSource.getDataSourceUID();
            //dtDataSource.setDataSourceName(request.getParameter("data_source_name"));
            dtDataSource.setDataSourceTitle(request.getParameter("data_source_title"));
            dtDataSource.setDescTxt(request.getParameter("desc_txt"));
            if(request.getParameter("jurisdiction_security") == null)
            {
                dtDataSource.setJurisdictionSecurity("N");
            }
            else
            {
                dtDataSource.setJurisdictionSecurity("Y");
            }
            if(request.getParameter("reporting_facility_security") == null)
            {
                dtDataSource.setReporting_facility_security("N");
            }
            else
            {
                dtDataSource.setReporting_facility_security("Y");
            }
            dtDataSource.validate();
            daoDataSource.validate(dtDataSource);
            daoDataSource.edit(dtDataSource);
            session.setAttribute("ReportAdmin.dtDataSource", dtDataSource);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report UpdateDataSource: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        //return mapping.findForward("default");
        ActionForward af = new ActionForward();
        af.setName("ViewDataSource");
        af.setPath(strRedirect);
        af.setRedirect(true);
        return af;
    }

}
