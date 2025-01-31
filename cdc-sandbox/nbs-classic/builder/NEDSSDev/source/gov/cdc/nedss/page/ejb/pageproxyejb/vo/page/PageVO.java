package gov.cdc.nedss.page.ejb.pageproxyejb.vo.page;



import gov.cdc.nedss.pam.vo.PamVO;
/**
 * Parent VO class that  represent specialized behavior of dynamic pages
 * @author Pradeep Kumar Sharma
 *
 */
public class PageVO extends PamVO{
	
private boolean isCurrInvestgtrDynamic;


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */


	public boolean isCurrInvestgtrDynamic() {
		return isCurrInvestgtrDynamic;
	}


	public void setCurrInvestgtrDynamic(boolean isCurrInvestgtrDynamic) {
		this.isCurrInvestgtrDynamic = isCurrInvestgtrDynamic;
	}
	
}
