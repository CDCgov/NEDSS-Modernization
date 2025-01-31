package gov.cdc.nedss.report.ejb.reportcontrollerejb.bean;

import java.rmi.RemoteException;

import javax.ejb.*;
import javax.ejb.EJBHome;

public interface ReportControllerHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3C0D3AC400B0
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     */
    public ReportController create() throws java.rmi.RemoteException, javax.ejb.CreateException;

}
