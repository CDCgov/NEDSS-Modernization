package gov.cdc.nedss.webapp.nbs.action.report;

import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

import java.io.*;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;

import javax.naming.*;
import javax.rmi.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

/**
 *  Submit class for reports.xsp.
 *  @author Ed Jenkins
 */
public class ReportsSubmit extends Action
{



    public ReportsSubmit()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        return mapping.findForward("basic");
    }

}
