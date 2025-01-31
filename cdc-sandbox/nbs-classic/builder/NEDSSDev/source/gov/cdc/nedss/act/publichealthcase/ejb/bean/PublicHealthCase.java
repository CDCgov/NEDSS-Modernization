//
// -- Java Code Generation Process --

package gov.cdc.nedss.act.publichealthcase.ejb.bean;

// Import Statements
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

public interface PublicHealthCase extends javax.ejb.EJBObject
{

    /**
     * @roseuid 3BD03902016E
     * @J2EE_METHOD  --  getPublicHealthCaseVO
     */
    public PublicHealthCaseVO getPublicHealthCaseVO() throws java.rmi.RemoteException;
    /**
     * @roseuid 3BD03922017E
     * @J2EE_METHOD  --  setPublicHeathCaseVO
     */
    public void setPublicHealthCaseVO (PublicHealthCaseVO publicHealthCaseVO) throws java.rmi.RemoteException, NEDSSConcurrentDataException;
   }