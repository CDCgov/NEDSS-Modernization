package gov.cdc.nedss.webapp.nbs.util.taglib;

import gov.cdc.nedss.util.LogUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import gov.cdc.nedss.pagemanagement.wa.dt.PageCondMappingDT;

import javax.servlet.http.HttpServletRequest;

public class ConditionDisplayTag extends TagSupport
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils(SimpleRssFeedTag.class.getName());
	private Collection<Object> conditionList = new ArrayList<Object>();
	private String conditionLabel= new String();	
	private String name = new String();
	private String toolTip = new String();
	private String busObjType="";
	
	/**
	 * @return the busObjType
	 */
	public String getBusObjType() {
		return busObjType;
	}


	/**
	 * @param busObjType the busObjType to set
	 */
	public void setBusObjType(String busObjType) {
		this.busObjType = busObjType;
	}


	public Collection<Object> getConditionList() {
		return conditionList;
	}


	public void setConditionList(Collection<Object> conditionList) {
		this.conditionList = conditionList;
	}


	public String getConditionLabel() {
		return conditionLabel;
	}


	public void setConditionLabel(String conditionLabel) {
		this.conditionLabel = conditionLabel;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {

		// ok, assuming this is the name of the object
        this.name = (String) value;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        ArrayList<Object> arrayList = request.getAttribute(value)!=null?(ArrayList<Object>)request.getAttribute(value):new ArrayList<Object>();
        conditionList = arrayList;
	}


	public String getToolTip() {
		return toolTip;
	}


	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}


	public int doEndTag() throws JspException
	{	 
		
		try{ 
			StringBuffer sb = new StringBuffer();
			boolean flag = true;
			sb.append("<tr> <td align='right' title='").append(toolTip).append("' > <b>");
			sb.append(conditionLabel);
			sb.append(":</b>&nbsp;</td> <td>");
			if(conditionList!=null && conditionList.size()>0){
				Iterator<Object> condIt = conditionList.iterator();
				while(condIt.hasNext()){
					PageCondMappingDT pageConDt = (PageCondMappingDT)condIt.next();
	          	    if(flag){
	          	    	if(busObjType!=null && busObjType.equals("INV") && pageConDt.getPortReqIndCd()!=null && pageConDt.getPortReqIndCd().equalsIgnoreCase("T")){
	          	    		sb.append("<font color ='#FF6600'><b><i> ");
	          	    		sb.append(pageConDt.getConditionDesc());
	          	    		sb.append(" (").append(pageConDt.getConditionCd()).append(")");
	          	    		sb.append("</i></b> </font color>");
	          	    	}else{
	          	    		//sb.append(pageConDt.getConditionDesc());
	          	    		//sb.append(" (").append(pageConDt.getConditionCd()).append(")");
	          	    		
	          	    		if(pageConDt.getPortReqIndCd()!=null && pageConDt.getPortReqIndCd().equalsIgnoreCase("T")){
		          	    		sb.append("<font color ='#ad0505'><b><i> ");
		          	    		sb.append(pageConDt.getConditionDesc());
		          	    		sb.append(" (").append(pageConDt.getConditionCd()).append(")");
		          	    		sb.append("</i></b> </font color>");
		          	    	}else{
		          	    		sb.append(pageConDt.getConditionDesc());
		          	    		sb.append(" (").append(pageConDt.getConditionCd()).append(")");
		          	    	}
	          	    		
	          	    		
	          	    	}
	          	    	sb.append("</td></tr>");
	          	    	flag = false;
	          	    }
	          	    else{
			          	sb.append("<tr><td>&nbsp;</td>");
			          	sb.append("<td>");
			          	if(pageConDt.getPortReqIndCd()!=null && pageConDt.getPortReqIndCd().equalsIgnoreCase("T")){
	          	    		sb.append("<font color ='#ad0505'><b><i> ");
	          	    		sb.append(pageConDt.getConditionDesc());
	          	    		sb.append(" (").append(pageConDt.getConditionCd()).append(")");
	          	    		sb.append("</i></b> </font color>");
	          	    	}else{
	          	    		sb.append(pageConDt.getConditionDesc());
	          	    		sb.append(" (").append(pageConDt.getConditionCd()).append(")");
	          	    	}
			          	sb.append("</td> </tr>");
	          	    }
				}
			}
			else{
				sb.append("&nbsp;</td> </tr>");
			}
			pageContext.getOut().write(sb.toString());       
		}        
		catch (Exception e) {
			e.printStackTrace();
			logger.error("An exception occured while handling the Condition Description display." + e);
		}
		
		return EVAL_PAGE;
	}



}