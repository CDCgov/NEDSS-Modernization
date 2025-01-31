package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo;

import java.util.ArrayList;

import gov.cdc.nedss.srtadmin.dt.CodeSetDT;
import gov.cdc.nedss.srtadmin.dt.CodeSetGpMetaDataDT;
import gov.cdc.nedss.util.AbstractVO;


public class PhinVadsSystemVO extends AbstractVO{
	private static final long serialVersionUID = 1L;
	
	private CodeSetGpMetaDataDT codeSetGpMetaDataDT;
	private ArrayList<Object> theCodeValueGenaralDtCollection;
	private CodeSetDT codeSetDT;
	
	
	public CodeSetDT getCodeSetDT() {
		return codeSetDT;
	}
	public void setCodeSetDT(CodeSetDT codeSetDT) {
		this.codeSetDT = codeSetDT;
	}
	public CodeSetGpMetaDataDT getCodeSetGpMetaDataDT() {
		return codeSetGpMetaDataDT;
	}
	public void setCodeSetGpMetaDataDT(CodeSetGpMetaDataDT codeSetGpMetaDataDT) {
		this.codeSetGpMetaDataDT = codeSetGpMetaDataDT;
	}
	public ArrayList<Object> getTheCodeValueGenaralDtCollection() {
		return theCodeValueGenaralDtCollection;
	}
	public void setTheCodeValueGenaralDtCollection(
			ArrayList<Object> theCodeValueGenaralDtCollection) {
		this.theCodeValueGenaralDtCollection = theCodeValueGenaralDtCollection;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}
	
	
}
