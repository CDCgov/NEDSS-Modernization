//
// -- Java Code Generation Process --

package gov.cdc.nedss.act.publichealthcase.ejb.bean;

// Import Statements
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;

import javax.ejb.EJBException;

public interface PublicHealthCaseHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3BD027110285
     * @J2EE_METHOD  --  findByPrimaryKey
     * Called by the client to find an EJB bean instance, usually find by primary key.
     */
    public PublicHealthCase findByPrimaryKey    (Long primaryKey)
                throws java.rmi.RemoteException, javax.ejb.FinderException, EJBException;

    /**
     * @roseuid 3BD0271102DF
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     */
    public PublicHealthCase create    (PublicHealthCaseVO phcVO)
                throws java.rmi.RemoteException, javax.ejb.CreateException,  EJBException;
}
