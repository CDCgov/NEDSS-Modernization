package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.UserProfileDAO;
import gov.cdc.nedss.reportadmin.dt.UserProfileDT;

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
 * Report Administration.
 * @author Ed Jenkins
 */
public class ReportAdministration extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportAdministration.class);

    /**
     * Constructor.
     */
    public ReportAdministration()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        //clear off session variables explicitly set for Report Admin
        session.removeAttribute("direction");
        session.removeAttribute("sortMethod");

        return mapping.findForward("default");
    }

}
