package gov.cdc.nedss.systemservice.mprupdateengine;
/**
 * <p>Title: AbstractActionImpl</p>
 * <p>Description: This is the abstract base class that implements
 * the Action interface.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender
 * @version 1.0
 */

abstract class AbstractActionImpl implements Action {

   /**
    * This method initializes the Action object.  The parameter specifies the
    * location of the configuration file for the Action object.
    * @param configFileName
    * @return void
    */
	public void init(String configFileName) {
		//template method, by default doing nothing
		System.out.println(this);
		System.out.println(configFileName);
		System.out.println("I am in AbstractActionImpl.init");
	}

}
