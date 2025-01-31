//
// -- Java Code Generation Process --
/**
* Name:		Remote interface for Organization Enterprise Bean
* Description:	The bean is an entity bean for identifying a Organization
* Copyright:	Copyright (c) 2002
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.entity.organization.ejb.bean;

// Import Statements
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.exception.*;

public interface Organization extends javax.ejb.EJBObject
{
    /**
     * @roseuid 3BD02E730077
     * @J2EE_METHOD  --  getOrganizationVO
     * @return OrganizationVO
     * @throws RemoteException
     */
    public OrganizationVO getOrganizationVO    () throws java.rmi.RemoteException;
     /**
     * @roseuid 3BD02EC30216
     * @J2EE_METHOD  --  setOrganizationVO
     * @param organizationVO  OrganizationVO
     * @throws NEDSSConcurrentDataException
     * @throws RemoteException
     */
    public void setOrganizationVO    (OrganizationVO organizationVO) throws NEDSSConcurrentDataException, java.rmi.RemoteException;
}