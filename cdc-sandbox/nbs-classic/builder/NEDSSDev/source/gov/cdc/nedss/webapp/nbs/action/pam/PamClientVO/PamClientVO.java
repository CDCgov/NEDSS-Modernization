package gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO;


import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;


/**
 * 
 * @author Pradeep Sharma
 *
 */
public class PamClientVO extends ClientVO{

	
	private PamProxyVO oldPamProxyVO;
	
	public PamProxyVO getOldPamProxyVO() {
		return oldPamProxyVO;
	}
	public void setOldPamProxyVO(PamProxyVO oldPamProxyVO) {
		this.oldPamProxyVO = oldPamProxyVO;
	}


}
