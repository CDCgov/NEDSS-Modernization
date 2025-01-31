package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt;
/**
 * @WaUiMetadataSummaryDT: SummaryDT class for Rule management screens.
 * @author Pradeep Sharma
 * @since: Release 4.0
 */
import java.lang.reflect.Field;

import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.LogUtils;

public class WaUiMetadataSummaryDT extends AbstractVO {
	private String localId;
	
	private Long waUiMetadataUid;
	private String questionIdentifier;
	private String questionLabel;
	private String questionLabelIdentifier;
	private Long nbsUIComponentUid;
	private Integer orderNbr;
	private Long codeSetGroupId;
	private String componentName;
	
	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	private Integer  questionGroupSeqNbr; 
	
	public Long getCodeSetGroupId() {
		return codeSetGroupId;
	}

	public void setCodeSetGroupId(Long codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
	}

	public Integer getOrderNbr() {
		return orderNbr;
	}

	public void setOrderNbr(Integer orderNbr) {
		this.orderNbr = orderNbr;
	}

	static final LogUtils logger = new LogUtils(WaUiMetadataSummaryDT.class
			.getName());
	private static final long serialVersionUID = 1L;
	public Long getWaUiMetadataUid() {
		return waUiMetadataUid;
	}

	public void setWaUiMetadataUid(Long waUiMetadataUid) {
		this.waUiMetadataUid = waUiMetadataUid;
	}

	public String getQuestionIdentifier() {
		return questionIdentifier;
	}

	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}

	public String getQuestionLabelIdentifier() {
		if(questionIdentifier!=null && (questionLabel!=null || componentName !=null)){
			String label = questionLabel!=null?questionLabel:componentName;
			questionLabelIdentifier= label + " ("+ questionIdentifier+")";
		}
		return questionLabelIdentifier;
	}

	public void setQuestionLabelIdentifier(String questionLabelIdentifier) {
		this.questionLabelIdentifier = questionLabelIdentifier;
	}

	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	public Long getNbsUIComponentUid() {
		return nbsUIComponentUid;
	}

	public void setNbsUIComponentUid(Long nbsUIComponentUid) {
		this.nbsUIComponentUid = nbsUIComponentUid;
	}
	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		//  Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItDelete() {
		//  Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItDirty() {
		//  Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItNew() {
		//  Auto-generated method stub
		return false;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = WaUiMetadataSummaryDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	@Override
	public void setItDelete(boolean itDelete) {
		//  Auto-generated method stub
		
	}

	@Override
	public void setItDirty(boolean itDirty) {
		//  Auto-generated method stub
		
	}

	@Override
	public void setItNew(boolean itNew) {
		//  Auto-generated method stub
		
	}

	public Integer getQuestionGroupSeqNbr() {
		return questionGroupSeqNbr;
	}

	public void setQuestionGroupSeqNbr(Integer questionGroupSeqNbr) {
		this.questionGroupSeqNbr = questionGroupSeqNbr;
	}
	
	
}