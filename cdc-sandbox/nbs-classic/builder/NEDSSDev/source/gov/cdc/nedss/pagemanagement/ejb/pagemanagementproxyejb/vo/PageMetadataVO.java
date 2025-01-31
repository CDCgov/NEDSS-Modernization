package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo;


import java.util.Collection;

import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.util.AbstractVO;

public class PageMetadataVO extends AbstractVO {
	
//	private WaTemplateDT waTemplateDT;
//	private Collection<Object> waPageCondMappingDTColl;
	private Collection<Object> pageMetadataColl;//rdb metadata related to the page
	private Collection<Object> pageVocabularyColl;//vocabulary related to the page
	private Collection<Object> pageQuestionVocabularyColl;//question and vocabulary related to the page
	


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

	public Collection<Object> getPageVocabularyColl() {
		return pageVocabularyColl;
	}

	public void setPageVocabularyColl(Collection<Object> pageVocabularyColl) {
		this.pageVocabularyColl = pageVocabularyColl;
	}

	public Collection<Object> getPageQuestionVocabularyColl() {
		return pageQuestionVocabularyColl;
	}

	public void setPageQuestionVocabularyColl(
			Collection<Object> pageQuestionVocabularyColl) {
		this.pageQuestionVocabularyColl = pageQuestionVocabularyColl;
	}


	public Collection<Object> getPageMetadataColl() {
		return pageMetadataColl;
	}

	public void setPageMetadataColl(Collection<Object> pageMetadataColl) {
		this.pageMetadataColl = pageMetadataColl;
	}

}
