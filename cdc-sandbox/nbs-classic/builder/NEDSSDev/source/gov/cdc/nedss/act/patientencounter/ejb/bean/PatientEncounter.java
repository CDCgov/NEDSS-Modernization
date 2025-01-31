package gov.cdc.nedss.act.patientencounter.ejb.bean;

import gov.cdc.nedss.act.patientencounter.dt.PatientEncounterDT;
import gov.cdc.nedss.act.patientencounter.vo.PatientEncounterVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;

/**
* Name:		Remote interface for PatientEncounter Enterprise Bean
* Description:	The bean is an entity bean for identifying a patientencounter
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

public interface PatientEncounter
    extends EJBObject {

    /**
     *
     * @return
     * @throws RemoteException
     */
    public PatientEncounterVO getPatientEncounterVO()
                                             throws RemoteException;

    /**
     *
     * @param pvo
     * @throws RemoteException
     * @throws NEDSSConcurrentDataException
     */
    public void setPatientEncounterVO(PatientEncounterVO pvo)
                               throws RemoteException, NEDSSConcurrentDataException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public PatientEncounterDT getPatientEncounterInfo()
                                               throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public Collection<Object> getLocators()
                           throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public Collection<Object> getPatientEncounterIDs()
                                      throws RemoteException;
} //end of PatientEncounter interface