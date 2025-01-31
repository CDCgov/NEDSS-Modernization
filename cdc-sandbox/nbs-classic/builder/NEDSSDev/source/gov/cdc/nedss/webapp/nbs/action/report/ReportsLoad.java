package gov.cdc.nedss.webapp.nbs.action.report;

import gov.cdc.nedss.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

/**
 *  Load class for reports.xsp.
 *  @author Ed Jenkins
 */
public class ReportsLoad extends Action
{

    public ReportsLoad()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        return mapping.findForward("reports");
    }

}
