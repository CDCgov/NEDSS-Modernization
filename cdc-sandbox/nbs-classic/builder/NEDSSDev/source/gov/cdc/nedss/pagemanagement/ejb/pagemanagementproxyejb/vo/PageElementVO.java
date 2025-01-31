package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo;


import gov.cdc.nedss.pagemanagement.wa.dt.WaNndMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaRdbMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.util.AbstractVO;

import java.util.Collection;

/**
 *Name: PageElementVO.java
 *Description: PageManagementProxyVO Object for PageManagement Utility
 *Copyright(c) 2010 Company: Computer Sciences Corporation
 *@since: NBS4.0
 */

public class PageElementVO extends AbstractVO {
	private static final long serialVersionUID = 1L;

	private Long pageElementUid;
	private WaUiMetadataDT waUiMetadataDT;
	private WaRdbMetadataDT waRdbMetadataDT;
	private WaNndMetadataDT waNndMetadataDT;
	private WaQuestionDT waQuestionDT;
	
	public Long getPageElementUid() {
		return pageElementUid;
	}
	public void setPageElementUid(Long pageElementUid) {
		this.pageElementUid = pageElementUid;
	}
	public WaUiMetadataDT getWaUiMetadataDT() {
		return waUiMetadataDT;
	}
	public void setWaUiMetadataDT(WaUiMetadataDT waUiMetadataDT) {
		this.waUiMetadataDT = waUiMetadataDT;
	}
	public WaRdbMetadataDT getWaRdbMetadataDT() {
		return waRdbMetadataDT;
	}
	public void setWaRdbMetadataDT(WaRdbMetadataDT waRdbMetadataDT) {
		this.waRdbMetadataDT = waRdbMetadataDT;
	}
	public WaNndMetadataDT getWaNndMetadataDT() {
		return waNndMetadataDT;
	}
	public void setWaNndMetadataDT(WaNndMetadataDT waNndMetadataDT) {
		this.waNndMetadataDT = waNndMetadataDT;
	}
	public WaQuestionDT getWaQuestionDT() {
		return waQuestionDT;
	}
	public void setWaQuestionDT(WaQuestionDT waQuestionDT) {
		this.waQuestionDT = waQuestionDT;
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
}