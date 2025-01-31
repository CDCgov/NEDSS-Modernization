package gov.cdc.nedss.webapp.nbs.action.contacttracing.CtContactClientVO;

import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

public class CTContactClientVO extends ClientVO{
	
	private CTContactProxyVO oldCtContactProxyVO;
	
	public CTContactProxyVO getOldCtContactProxyVO() {
		return oldCtContactProxyVO;
	}
	public void setOldCtContactProxyVO(CTContactProxyVO oldCtContactProxyVO) {
		this.oldCtContactProxyVO = oldCtContactProxyVO;
	}

}
