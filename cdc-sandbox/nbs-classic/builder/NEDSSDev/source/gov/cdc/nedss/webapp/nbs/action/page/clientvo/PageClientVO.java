package gov.cdc.nedss.webapp.nbs.action.page.clientvo;

import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
/**
 *Name: PageClientVO.java 
 *Description: ClientVO class for Case Object(for Dynamic Pages)
 *Copyright(c) 2010 
 *Company: CSC
 *@since: NBS4.0
 * @author Pradeep Sharma
 */
public class PageClientVO extends ClientVO{
	private PageProxyVO oldPageProxyVO;


	public PageProxyVO getOldPageProxyVO() {
		return oldPageProxyVO;
	}

	public void setOldPageProxyVO(PageProxyVO oldPageProxyVO) {
		this.oldPageProxyVO = oldPageProxyVO;
	}
	
	
}
