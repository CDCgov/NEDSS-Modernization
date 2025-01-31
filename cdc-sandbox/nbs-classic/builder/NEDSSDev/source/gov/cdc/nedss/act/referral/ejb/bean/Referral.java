/**
* Name:		Remote interface for Referral Enterprise Bean
* Description:	The bean is an entity bean for identifying a referral
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.referral.ejb.bean;

import gov.cdc.nedss.act.referral.dt.ReferralDT;
import gov.cdc.nedss.act.referral.vo.ReferralVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;


public interface Referral extends EJBObject
{
    /**
     * Gets and sets ReferralVO containing parameters mapping to all referral's BMP fields.
     */

    public ReferralVO getReferralVO() throws RemoteException;
    public void setReferralVO(ReferralVO pvo) throws RemoteException,NEDSSConcurrentDataException;

    /**
     * Gets ReferralInfoVO containing parameters mapping to referral table.
     */

    public ReferralDT getReferralInfo() throws RemoteException;

    /**
     * Gets locators
     */
    public Collection<Object>  getLocators() throws RemoteException;


    /**
     * Gets a collection of referral ids
     */
    public Collection<Object>  getReferralIDs() throws RemoteException;

}//end of Referral interface
