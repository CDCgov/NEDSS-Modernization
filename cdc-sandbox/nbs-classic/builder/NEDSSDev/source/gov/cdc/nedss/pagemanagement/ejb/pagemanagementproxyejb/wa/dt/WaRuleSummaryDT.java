package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt;
/**
 * WaRuleSummaryDT: SummaryDT class for Rule management screen.
 * @author Pradeep Sharma
 * @since: Release 4.0
 */
import java.lang.reflect.Field;

import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.LogUtils;

public class WaRuleSummaryDT extends AbstractVO {
	static final LogUtils logger = new LogUtils(WaRuleSummaryDT.class.getName());
	private static final long serialVersionUID = 1L;
	
	private String ruleCd;
	private Long waRuleMetadataUid;
	private String ruleDescTxt;
	private String localId;
	private String viewLink;
	private String editLink;	

	private String sourceField;
	private String sourceValues;
	private String logicValues;
	private String targetField;
	private String userRuleId;	
	private String templateType;
	private String targetFieldForPrint;
	private String targetType;
	
	public String getTargetFieldForPrint() {
		return targetFieldForPrint;
	}
	public void setTargetFieldForPrint(String targetFieldForPrint) {
		this.targetFieldForPrint = targetFieldForPrint;
	}
	public String getLocalId() {
		return localId;
	}
	
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getRuleCd() {
		return ruleCd;
	}
	public void setRuleCd(String ruleCd) {
		this.ruleCd = ruleCd;
	}
	public Long getWaRuleMetadataUid() {
		return waRuleMetadataUid;
	}
	public void setWaRuleMetadataUid(Long waRuleMetadataUid) {
		this.waRuleMetadataUid = waRuleMetadataUid;
	}
	public String getRuleDescTxt() {
		return ruleDescTxt;
	}
	public void setRuleDescTxt(String ruleDescTxt) {
		this.ruleDescTxt = ruleDescTxt;
	}
	
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		//  Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItDelete() {
		// Auto-generated method stub
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
	@Override
	public void setItDelete(boolean itDelete) {
		//  Auto-generated method stub
		
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = WaRuleSummaryDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	@Override
	public void setItDirty(boolean itDirty) {
		//  Auto-generated method stub
		
	}
	@Override
	public void setItNew(boolean itNew) {
		//  Auto-generated method stub
		
	}
	public String getViewLink() {
		return viewLink;
	}
	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}
	public String getEditLink() {
		return editLink;
	}
	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}
	public String getSourceField() {
		return sourceField;
	}
	public void setSourceField(String sourceField) {
		this.sourceField = sourceField;
	}
	public String getSourceValues() {
		return sourceValues;
	}
	public void setSourceValues(String sourceValues) {
		this.sourceValues = sourceValues;
	}
	public String getLogicValues() {
		return logicValues;
	}
	public void setLogicValues(String logicValues) {
		this.logicValues = logicValues;
	}
	public String getTargetField() {
		return targetField;
	}
	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}
	public String getUserRuleId() {
		return userRuleId;
	}
	public void setUserRuleId(String userRuleId) {
		this.userRuleId = userRuleId;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	
	
}