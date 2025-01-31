//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\webapp\\nbs\\util\\DeDuplicationQueueHelper.java

package gov.cdc.nedss.webapp.nbs.util;

import javax.servlet.http.HttpSessionBindingListener;
import java.util.HashMap;
import java.sql.Timestamp;
import javax.servlet.http.HttpSessionBindingEvent;

public class DeDuplicationQueueHelper implements HttpSessionBindingListener
{
   private static HashMap<Object,Object> dedupAvailableQueue = new HashMap<Object,Object>();
	private static HashMap<Object,Object> dedupAvailableQueueSkipped = new HashMap<Object,Object>();

   private static HashMap<Object,Object> dedupTakenQueue = new HashMap<Object,Object>();
   private static HashMap<Object,Object> dedupSessionQueue = new HashMap<Object,Object>();
   private static Timestamp lastBatchProcessTime;

   /**
    * @roseuid 3E92EDD7032C
    */
   public DeDuplicationQueueHelper()
   {

   }

   public void resetSkippedQueue(){
	   
	   dedupAvailableQueueSkipped = new HashMap<Object,Object>();
	   
   }

   public static void setLastBatchProcessTime(Timestamp aLastBatchProcessTime) {
     lastBatchProcessTime = aLastBatchProcessTime;
   }

   public static Timestamp getLastBatchProcessTime() {
     return lastBatchProcessTime;
   }

   /**
    * Access method for the DedupAvailableQueue property.
    *
    * @return   the current value of the DedupAvailableQueue property
    */
   public static HashMap<Object,Object> getDedupAvailableQueue()
   {
      return dedupAvailableQueue;
   }

   /**
    * Sets the value of the DedupAvailableQueue property.
    *
    * @param aDedupAvailableQueue the new value of the DedupAvailableQueue property
    */
   public static void setDedupAvailableQueue(HashMap<Object,Object> aDedupAvailableQueue)
   {
      dedupAvailableQueue = aDedupAvailableQueue;
   }

   /**
    * Access method for the DedupTakenQueue property.
    *
    * @return   the current value of the DedupTakenQueue property
    */
   public static HashMap<Object,Object> getDedupTakenQueue()
   {
      return dedupTakenQueue;
   }

   /**
    * Sets the value of the DedupTakenQueue property.
    *
    * @param aDedupTakenQueue the new value of the DedupTakenQueue property
    */
   public static void setDedupTakenQueue(HashMap<Object,Object> aDedupTakenQueue)
   {
      dedupTakenQueue = aDedupTakenQueue;
   }

   /**
    * Access method for the DedupSessionQueue property.
    *
    * @return   the current value of the DedupSessionQueue property
    */
   public static HashMap<Object,Object> getDedupSessionQueue()
   {
      return dedupSessionQueue;
   }

   /**
    * Sets the value of the DedupSessionQueue property.
    *
    * @param aDedupSessionQueue the new value of the DedupSessionQueue property
    */
   public static void setDedupSessionQueue(HashMap<Object,Object> aDedupSessionQueue)
   {
      dedupSessionQueue = aDedupSessionQueue;
   }

   /**
    * @param httpsessionbindingevent
    * @roseuid 3E92EEF203A9
    */
   public void valueBound(HttpSessionBindingEvent httpsessionbindingevent)
   {

   }

   /**
    * @param httpsessionbindingevent
    * @roseuid 3E92EEF300AB
    */
   public void valueUnbound(HttpSessionBindingEvent httpsessionbindingevent)
   {
     if(dedupSessionQueue.containsKey(httpsessionbindingevent.getSession().getId())) {
             dedupAvailableQueue.put(dedupSessionQueue.get(httpsessionbindingevent.getSession().getId()), httpsessionbindingevent.getSession().getAttribute("groupDate"));
             dedupTakenQueue.remove(httpsessionbindingevent.getSession().getId());
             dedupSessionQueue.remove(httpsessionbindingevent.getSession().getId());
           }//end of if

   }

public static HashMap<Object, Object> getDedupAvailableQueueSkipped() {
	return dedupAvailableQueueSkipped;
}

public static void setDedupAvailableQueueSkipped(
		HashMap<Object, Object> dedupAvailableQueueSkipped) {
	DeDuplicationQueueHelper.dedupAvailableQueueSkipped = dedupAvailableQueueSkipped;
}
}
