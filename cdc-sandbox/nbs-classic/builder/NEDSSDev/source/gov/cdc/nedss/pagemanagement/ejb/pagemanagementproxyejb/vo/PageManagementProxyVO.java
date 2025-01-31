package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.page.PageVO;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.util.AbstractVO;

import java.util.Collection;

/**
 *Name: PageManagementProxyVO.java
 *Description: PageManagementProxyVO Object for PageManagement Utility
 *Copyright(c) 2010 Company: Computer Sciences Corporation
 *@since: NBS4.0
 */

public class PageManagementProxyVO  extends AbstractVO {
	private static final long serialVersionUID = 1L;

	private WaTemplateDT waTemplateDT;
	private Collection<Object> pageCondMappingColl;
	private Collection<Object> thePageElementVOCollection;
	private Collection<Object> waRuleMetadataDTCollection;
	
	public WaTemplateDT getWaTemplateDT() {
		return waTemplateDT;
	}
	public void setWaTemplateDT(WaTemplateDT waTemplateDT) {
		this.waTemplateDT = waTemplateDT;
	}
	public Collection<Object> getThePageElementVOCollection() {
		return thePageElementVOCollection;
	}
	public void setThePageElementVOCollection(
			Collection<Object> thePageElementVOCollection) {
		this.thePageElementVOCollection = thePageElementVOCollection;
	}
	
	public Collection<Object> getWaRuleMetadataDTCollection() {
		return waRuleMetadataDTCollection;
	}
	public void setWaRuleMetadataDTCollection(
			Collection<Object> waRuleMetadataDTCollection) {
		this.waRuleMetadataDTCollection = waRuleMetadataDTCollection;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItDelete() {
		return itDelete;
	}
	@Override
	public boolean isItDirty() {
		return itDirty;
	}
	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return itNew;
	}
	@Override
	public void setItDelete(boolean aItDelete) {
		itDelete = aItDelete;
	
	}
	@Override
	public void setItDirty(boolean aItDirty) {
		itDirty = aItDirty;
		
	}
	@Override
	public void setItNew(boolean aItNew) {
		itNew = aItNew;
		
	}
	public Collection<Object> getPageCondMappingColl() {
		return pageCondMappingColl;
	}
	public void setPageCondMappingColl(Collection<Object> pageCondMappingColl) {
		this.pageCondMappingColl = pageCondMappingColl;
	}
	
	
}
