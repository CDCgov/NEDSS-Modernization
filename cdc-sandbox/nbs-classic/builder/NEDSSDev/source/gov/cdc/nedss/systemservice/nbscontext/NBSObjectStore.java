package gov.cdc.nedss.systemservice.nbscontext;

import javax.servlet.http.*;
import java.util.*;

import gov.cdc.nedss.util.*;

/**
 * <p>Title:NBSObjectStore </p>
 * <p>Description:This class will store objects and put them in session
 *  to be used by incomming pages. </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: CSC</p>
 * @author Shailesh Desai
 * @version 1.0
 */

public class NBSObjectStore extends TreeMap<Object,Object> implements HttpSessionBindingListener {
  static final LogUtils logger = new LogUtils(NBSContext.class.getName());
/* empty constructor */
  public NBSObjectStore() {
  }
 /** when this object is bound to session. It is only for debug purpose
    * @param   HttpSessionBindingEvent
    * @return  void
 */

  public void valueBound(HttpSessionBindingEvent parm1) {
    logger.debug ("bound object: " + parm1.getName());
  }
  /** when this object is unbound from session object. clear out all objects
      stored here. Also clear out any object left in session
    * @param   HttpSessionBindingEvent
    * @return  void
   */
  public void valueUnbound(HttpSessionBindingEvent parm1) {
    /*Set set = keySet();
    String [] arrkeys = (String []) set.toArray(new String [0]);
    for (int i=0; i<arrkeys.length; i++)
    {
      Object obj = remove(arrkeys[i]);
      logger.debug("Removed object: " + obj + " from object store.");
      obj= null;
    }
    HttpSession session = parm1.getSession();
    Enumeration enum = session.getAttributeNames();
    while (enum.hasMoreElements())
    {
      Object obj = enum.nextElement();
      logger.debug("Removed object: " + obj + " from session.");
      obj = null;
    }

    logger.debug ("UnBound object " +parm1.getName());
    */
  }

}