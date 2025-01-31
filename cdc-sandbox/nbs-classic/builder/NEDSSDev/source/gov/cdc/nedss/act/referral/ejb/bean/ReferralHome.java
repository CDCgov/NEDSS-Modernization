/**
* Name:		Home interface for Referral Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.referral.ejb.bean;


import gov.cdc.nedss.act.referral.vo.ReferralVO;
import gov.cdc.nedss.exception.NEDSSSystemException;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


public interface ReferralHome extends EJBHome
{
    /**
     * Creates a Referral object.
     */
    public Referral create(ReferralVO pvo)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException;


    /**
     * Finds by the primary key (the person unique NEDSS ID).
     */
    public Referral findByPrimaryKey(Long pk)
          throws RemoteException, FinderException, EJBException, NEDSSSystemException;

}// end of EBReferralHome interface
