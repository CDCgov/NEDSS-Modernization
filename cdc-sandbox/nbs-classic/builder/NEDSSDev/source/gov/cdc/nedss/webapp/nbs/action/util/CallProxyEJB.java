package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
/**
 * @author:Pradeep Sharma
 * Descrition: Generic method to call the ProxyEJB 
 * 
 */

public class CallProxyEJB {
	static final LogUtils logger = new LogUtils(CallProxyEJB.class.getName());
public static Object callProxyEJB(Object[] oParams, String sBeanJndiName, String sMethod , HttpSession session) 
	{

		Object object = null;
		MainSessionCommand msCommand = null;
	
		try {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
	
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			object  =  arr.get(0);
		} catch (Exception ex) {

			if (session == null) {
				logger.error("Error: no session, please login");
			}
	
			logger.fatal("Error in callProxyEJB: "+ ex.getMessage(),ex);
			ex.printStackTrace();
			throw new NEDSSSystemException(ex.toString(),ex);
		}

		return object;
} 
}




