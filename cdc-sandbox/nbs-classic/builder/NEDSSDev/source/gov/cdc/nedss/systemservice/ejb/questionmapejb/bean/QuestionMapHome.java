package gov.cdc.nedss.systemservice.ejb.questionmapejb.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface QuestionMapHome extends EJBHome {

   public QuestionMap create()
    throws RemoteException, CreateException;
}
