/**
* Name:		Home interface for PatientEncounter Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/
package gov.cdc.nedss.act.patientencounter.ejb.bean;

import gov.cdc.nedss.act.patientencounter.vo.PatientEncounterVO;
import gov.cdc.nedss.exception.NEDSSSystemException;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


public interface PatientEncounterHome
    extends EJBHome {

    /**
     *
     * @param pvo
     * @return
     * @throws RemoteException
     * @throws CreateException
     * @throws DuplicateKeyException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public PatientEncounter create(PatientEncounterVO pvo)
                            throws RemoteException, CreateException, DuplicateKeyException, EJBException, NEDSSSystemException;

    /**
     *
     * @param pk
     * @return
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public PatientEncounter findByPrimaryKey(Long pk)
                                      throws RemoteException, FinderException, EJBException, NEDSSSystemException;
} // end of EBPatientEncounterHome interface