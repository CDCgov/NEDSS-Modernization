/* ---------------------------------------------------------------------------------------------------
 * Emergint
 * 501 East Broadway
 * Suite 250, Louisville, KY 40202
 * Copyright © 2002
 *  -----------------------------------------------------------------------------------------------------
 *  Author:
 *  Description :
 *-------------------------------------------------------------------------------------------------------
 */
package nndm;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;


/** This class serves as a nndmManager initializer/starter. The class is compiled
 *  as a servlet, and is configured to start when Tomcat starts. The run method
 *  begins the processing. The class is totally decoupled from other classes,
 *  meaning removal of this class will not have an impact on operation of other
 *  functionality provided by the Notification Manager (nndmManager) class.
 */
public class nndmMgrDaemon extends HttpServlet implements Runnable
{
   /**
    * The daemon thread instance to perform the repeated actions.
    */
   private Thread daemonThread = null;

   /**
    * The time to sleep (polling interval) between calls to process messages.
    */
   private volatile long sleepTime;

   /**
    * Instance of the Notification Manager that manages workflow in nndm Messaging
    * for specifing disease-specific messages.
    */
	private nndmManager manager = null;

   /**
    * Instance of the PHMTS Manager that manages workflow in the nndm process
    * for watching the PHMTS process and keeping status on the transportation of
    * a disease message.
    */
	private phmtsManager transportManager = null;

    /**
     * The current status of this objects state.
     */
    private String state = "NOTIFICATION MANAGER stopped ...";

   /**
    * Constructs this Manager Daemon.  It is called at Tomcat startup via
    * Tomcat's ...webapps/nndm/WEB-INF/web.xml settings.
    */
	public nndmMgrDaemon()
	{
		super();
	}

   /** The run method spawns a worker thread, with a sleep time that is configurable
    * and maintained in nndmConfig.properties, and interfaced from nndmConfig.class.
    * Default sleep time will be set 5000ms and used unless overridden in the
    * nndmConfig.properties "POLLING_INTERVAL" property setting.
    * At each interval, the daemon makes a call to the Notification Manager to batch
    * process notifications (processAll), which begins synchronous notification
    * transformations.
    * Unhandled exceptions are bubbled to CATALINA_NNDM_ERROR.log
    *
    * @see nndm.nndmConfig#DAEMON_POLLING_TIME_PROPERTY_NAME
    */
	public void run()
	{
        Thread myThread = Thread.currentThread();
        while (daemonThread == myThread) {
            // call the nndmManager to do its work
            //process notification records at each iteration manager.processAll()
            manager.processAll();
            transportManager.processAll();
            try {
                Thread.sleep(getSleepTime());
            } catch (InterruptedException e){
              // do nothing here, the VM doesn't want us to sleep anymore, get back to work
            }
        }
	}


   /**
    * Initializes this servlet at time of Tomcat startup.  Here, the MgrDaemon will
    * schedule the repeating actions to process messages.
    * @exception ServletException if an exception occurs that interrupts this servlet's
    * normal operation
    */
   public void init() throws ServletException {

      // once the config file stuff is set, instantiate our member
      // objects, which are dependant on the config file settings.
      manager = new nndmManager();
      transportManager = new phmtsManager();
      //Set up the polling interval, only done once.
      long sleepTime;
      try {
         sleepTime = Long.parseLong(nndmConfig.getNndmConfigurations(nndmConfig.DAEMON_POLLING_TIME_PROPERTY_NAME));
      } catch (NumberFormatException nfe){
         Exception e = new nndmException("Unable to set the polling interval to the new given value -  it cannot be parsed as a long number. "
                  + "Invalid config entry [" + nndmConfig.DAEMON_POLLING_TIME_PROPERTY_NAME  + "] \n Example, for 8000 millisecond interval, use "
                           + nndmConfig.DAEMON_POLLING_TIME_PROPERTY_NAME
                           + "=8000",nfe);
         sleepTime = nndmConfig.DEFAULT_DAEMON_POLLING_TIME;
      }
      setSleepTime(sleepTime);
      // setup daemon thread, only once ,spawn the thread
      if (daemonThread == null) {
         daemonThread = new Thread(this, "nndmDaemon");
         //the daemon thread is to run as long as Tomcat is running.
         daemonThread.setDaemon(true);
         daemonThread.start();
      }
      this.state = " NOTIFICATION MANAGER running ...";
      System.out.println(this.state);
   }

   /**
    * Set the sleep time (polling interval) for this Manager Daemon.
    * @param newTime the time in milliseconds.
    */
   public void setSleepTime(long newTime){
      sleepTime = newTime;
   }

   /**
    * Get the sleep time (polling interval) for this Manager Daemon.
    * @return sleep time in milliseconds.
    */
   public long getSleepTime(){
      return sleepTime;
   }

   /**
   * Get method, used for tesing.
   */
   public void doGet(HttpServletRequest req, HttpServletResponse resp)
     throws ServletException, IOException {
         PrintWriter out = resp.getWriter();
         resp.setContentType("text/html");
         out.println("<html lang=\"en\">");
         out.println("NNDM Manager 1.0 <br>");
         out.println(this.state);
         out.println("Centers for Disease Control and Prevention<br>");
         out.println("</html>");
   }



}
/* END CLASS DEFINITION nndmMgrDaemon */
