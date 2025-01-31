/**
* Name:		    Remote interface for Interview EJB
* Description:	The bean is an entity bean for identifying a interview
* Copyright:	Copyright (c) 2013
* Company: 	    Leidos
* @author	    Nedss Team
* @version	    1.0
*/
package gov.cdc.nedss.act.interview.ejb.bean;

import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;



public interface Interview extends EJBObject
{

     /**
     * Gets and sets Interview containing parameters mapping to all interview's BMP fields.
     */

    public InterviewVO getInterviewVO() throws RemoteException;
    public void setInterviewVO(InterviewVO ivo) throws RemoteException, NEDSSConcurrentDataException;
    public InterviewDT getInterviewInfo() throws RemoteException;

}