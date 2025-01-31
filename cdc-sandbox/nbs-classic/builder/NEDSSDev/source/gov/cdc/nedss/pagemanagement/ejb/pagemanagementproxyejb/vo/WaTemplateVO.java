package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo;


import java.util.Collection;

import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.util.AbstractVO;

public class WaTemplateVO extends AbstractVO {
	
	private WaTemplateDT waTemplateDT;
	private Collection<Object> waPageCondMappingDTColl;
	private Collection<Object> waRdbMetadataDTColl;//rdb metadata related to the page

	
	public Collection<Object> getWaRdbMetadataDTColl() {
		return waRdbMetadataDTColl;
	}

	public void setWaRdbMetadataDTColl(Collection<Object> waRdbMetadataDTColl) {
		this.waRdbMetadataDTColl = waRdbMetadataDTColl;
	}

	public WaTemplateDT getWaTemplateDT() {
		return waTemplateDT;
	}

	public void setWaTemplateDT(WaTemplateDT waTemplateDT) {
		this.waTemplateDT = waTemplateDT;
	}

	public Collection<Object> getWaPageCondMappingDTColl() {
		return waPageCondMappingDTColl;
	}

	public void setWaPageCondMappingDTColl(Collection<Object> waPageCondMappingDTColl) {
		this.waPageCondMappingDTColl = waPageCondMappingDTColl;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}
	

}
