package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import javax.rmi.PortableRemoteObject;

public class RulesEngineUtilHelper {
	
	private static PamProxy pamProxy = null;
	static final LogUtils logger = new LogUtils(RulesEngineUtilHelper.class.getName());
	
	public PamProxy getPamProxyEJBRef() throws Exception {
		if (pamProxy == null) {
			
			try{
			
			NedssUtils nu = new NedssUtils();
			Object objref = nu.lookupBean(JNDINames.PAM_PROXY_EJB);
			if (objref != null) {
				PamProxyHome home = (PamProxyHome) PortableRemoteObject
				.narrow(objref, PamProxyHome.class);
				pamProxy = home.create();
			}
			}catch(Exception e){
				logger.error("Error from PAM_PROXY_EJB in RulesEngineUtilHelper: " + e.getMessage() );
				e.printStackTrace();
			}
		}
		return pamProxy;
	}
 

}
