package gov.cdc.nedss.systemservice.ejb.casenotificationejb.bean;

import gov.cdc.nedss.systemservice.ejb.questionmapejb.bean.QuestionMap;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface CaseNotificationHome extends EJBHome {
	   public CaseNotification create()
	    throws RemoteException, CreateException;

}
