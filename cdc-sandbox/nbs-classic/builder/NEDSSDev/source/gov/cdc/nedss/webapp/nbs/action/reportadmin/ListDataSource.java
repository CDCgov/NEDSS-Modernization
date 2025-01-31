package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DataSourceDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.observation.labreport.EditLabReportLoad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.util.LogUtils;


/**
 * Lists records in the Data_Source table.
 * @author Ed Jenkins
 */
public class ListDataSource extends Action
{

    /**
     * Logger.
     */
    //For logging
    static final LogUtils logger = new LogUtils(ListDataSource.class.getName());

    /**
     * DAO for Data_Source.
     */
    private static final DataSourceDAO daoDataSource = new DataSourceDAO();

    /**
     * Constructor.
     */
    public ListDataSource()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    { 
        HttpSession session = request.getSession(true);
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        try
        {
            // Return Link
            String strLinkName = "Return to System Management Main Menu";
            String strLinkAddr = "/nbs/ReportAdministration.do?focus=systemAdmin5";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            ArrayList<Object> alDataSource = daoDataSource.list();

            String contextAction = request.getParameter("ContextAction");

            //sorting
            boolean invDirectionFlag = false;
            String sortMethod = request.getParameter("sortMethod");

            if (sortMethod == null || sortMethod.equals(""))
				sortMethod = "getDataSourceName";

            String invSortDirection = request.getParameter("direction");

            session.setAttribute("sortMethod", sortMethod);
            session.setAttribute("direction", invSortDirection);

			if (invSortDirection != null && invSortDirection.equals("false"))
				invDirectionFlag = false;
			else
				invDirectionFlag = true;

			if (contextAction != null && contextAction.equals("Sort") && invDirectionFlag) {
				request.setAttribute("sortDirection", new Boolean(false));

			} else if (contextAction != null && contextAction.equals("Sort") && !invDirectionFlag)
					request.setAttribute("sortDirection", new Boolean(true));
			else
				request.setAttribute("sortDirection", new Boolean(invDirectionFlag));

			if (sortMethod != null && alDataSource != null && alDataSource.size() > 0) {
				NedssUtils util = new NedssUtils();
				util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) alDataSource,invDirectionFlag);
			}

            session.setAttribute("ReportAdmin.alDataSource", alDataSource);
            DataSourceDT dataSourceDT = new DataSourceDT();
            //session.setAttribute("DataSourceDT", dataSourceDT);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report ListDataSource: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            throw new ServletException(ex.getMessage(),ex);
        }
        return mapping.findForward("default");
    }

}
