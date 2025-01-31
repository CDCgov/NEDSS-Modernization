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
public class EditDataSource extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(EditDataSource.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    /**
     * DAO for Data_Source.
     */
    private static final DataSourceDAO daoDataSource = new DataSourceDAO();

    /**
     * Constructor.
     */
    public EditDataSource()
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

        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report EditDataSource: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }

}
