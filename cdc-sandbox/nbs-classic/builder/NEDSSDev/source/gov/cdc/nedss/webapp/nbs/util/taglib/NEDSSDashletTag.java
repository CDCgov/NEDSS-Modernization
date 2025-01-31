package gov.cdc.nedss.webapp.nbs.util.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.taglib.TagUtils;

/*
 * 						<div class="dashlet" style="text-align:center;" id="myCharts">
                            <b class="spiffy">
                                <b class="spiffy1"><b></b></b>
                                <b class="spiffy2"><b></b></b>
                                <b class="spiffy3"></b>
                                <b class="spiffy4"></b>
                                <b class="spiffy5"></b>
                            </b>
                            <div class="spiffyfg">
                                <div class="header dd-multi-handle-<bean:write name="colIndex" />" id="dd-handle-<bean:write name="colIndex" />_<bean:write name="rowIndex" />a">
                                    <h2 style="padding:3px;float:left; font-size:13px;"> <%=request.getAttribute("chartTitle") %> </h2>
                                    <div>
                                        <a style="padding:3px;float:right;" class="toggleIconHref" 
                                            href="#" tabindex=-1 
                                            onclick="return toggleDashletDisplay('myCharts');">
                                            <img border="0" src="minus_sign.gif" alt="Minimize" title="Collapse Block">
                                        </a>
                                    </div>
                                </div>
                                <div class="expander"> </div>
                                
                                <div class="content" style="height:300px; text-align:center;">
                                		<% if(filename != null && filename.equals("public_nodata.png")) { %>
                                		<img src="nodata.gif"/>
                                		<%} else {%>
											<img id="chartImg" src="<%= graphURL %>" width="285" height="250" border="0" useMap="#<%= filename %>">
											<%} %>
											<br/>
											<div align="center">
												<select id="charts" class="pullDown" onchange="refreshData();">
														<%				
															Iterator iter = chartList.listIterator();
															while (iter.hasNext()) {
																			DropDownCodeDT dt = (DropDownCodeDT)iter.next();
																			if (dt.getKey().equals(dKey)) { %>
																				<option value=<%= dKey %> selected><%= dt.getValue()%></option>
														<%					} else { %>
																				<option value=<%= dt.getKey() %>><%= dt.getValue()%></option>
														<%					} %>
														<%				} %>
												</select>
											</div>																																	
                                </div>
                            </div>
                            <b class="spiffy">
                                <b class="spiffy5"></b>
                                <b class="spiffy4"></b>
                                <b class="spiffy3"></b>
                                <b class="spiffy2"><b></b></b>
                                <b class="spiffy1"><b></b></b>
                            </b>
                        </div>
 */

public class NEDSSDashletTag extends TagSupport
{
	protected String name;
	protected String cssClass;
	protected String theme;
	protected String id;
	protected boolean isCollapsable;
	protected String contentStyle;
	
	// to support YUI drag and drop
	protected boolean isDraggable;
	protected String headerClass;
	protected String headerId;
	
    public NEDSSDashletTag()
    {
        name = null;
        cssClass = null;
        theme = null;
        id = null;
        isCollapsable = true;
        
        // for YUI drag and drop
        isDraggable = false;
        headerClass = null;
        headerId = null;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getIsCollapsable() {
		return isCollapsable;
	}

	public void setIsCollapsable(boolean isCollapsable) {
		this.isCollapsable = isCollapsable;
	}

	public boolean getIsDraggable() {
		return isDraggable;
	}

	public void setIsDraggable(boolean isDraggable) {
		this.isDraggable = isDraggable;
	}

	public String getHeaderClass() {
		return headerClass;
	}

	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}

	public String getHeaderId() {
		return headerId;
	}

	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	
	public String getContentStyle() {
		return contentStyle;
	}

	public void setContentStyle(String contentStyle) {
		this.contentStyle = contentStyle;
	}

	public int doStartTag() throws JspException {
        TagUtils.getInstance().write(this.pageContext,
            this.renderContainerElement());

        return EVAL_BODY_INCLUDE;
    }

    /**
     * Renders an &lt;html&gt; element with appropriate language attributes.
     * @since Struts 1.2
     */
    protected String renderContainerElement() 
    {
    	// set defaults
    	if (this.cssClass == null || (this.cssClass != null && this.cssClass.trim().equals(""))) {
    		this.cssClass = "dashlet";
    	}
    	if (this.theme == null || (this.theme != null && this.theme.trim().equals(""))) {
    		this.theme = "spiffy";
    	}
    	
    	StringBuffer sb = new StringBuffer("");
    	
    	sb.append("<div class=\"" + this.cssClass + "\" style=\"text-align:center;\" id=\"" + this.id + "\">");

    	// if the theme is 'spiffy' for rounded corner boxes.
    	if (this.theme.equals("spiffy")) {
        	sb.append("<b class=\"spiffy\">");
            sb.append("<b class=\"spiffy1\"><b></b></b>");
            sb.append("<b class=\"spiffy2\"><b></b></b>");
            sb.append("<b class=\"spiffy3\"></b>");    
            sb.append("<b class=\"spiffy4\"></b>");    
            sb.append("<b class=\"spiffy5\"></b>");   
            sb.append("</b>");
            sb.append("<div class=\"spiffyfg\">");
    	}
        
        // dashlet header
    	if (this.isDraggable) {
            sb.append("<div class=\"" + this.headerClass + "\" id=\"" + this.headerId + "\">");
            sb.append("<h2 style=\"padding:3px;float:left; font-size:13px;\">" + this.name + "</h2>");
            sb.append("<div>");
    	}
    	else {
            sb.append("<div class=\"header\">");
            sb.append("<h2 style=\"padding:3px;float:left; font-size:13px;\">" + this.name + "</h2>");
            sb.append("<div>");
    	}
        
        // check for hide/show option
        if (this.isCollapsable == true) {
            sb.append("<a style=\"padding:3px;float:right;\" class=\"toggleIconHref\" href=\"#\" onclick=\"return toggleDashletDisplay('" + this.id + "');\">");
            sb.append("<img border=\"0\" src=\"minus_sign.gif\" alt=\"Minimize\" title=\"Collapse Block\">");
            sb.append("</a>");
        }
        
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<div class=\"expander\"> </div>");
        
        // content beginning
        if (this.contentStyle != null &&  !this.contentStyle.trim().equals("")) {
        	sb.append("<div class=\"content\" style=\"" + this.contentStyle + "\">");
        }
        else {
        	sb.append("<div class=\"content\">");
        }
                    
	    return sb.toString();
    }

    /**
     * Process the end of this tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
    	// set defaults here
    	if (this.theme == null || (this.theme != null && this.theme.trim().equals(""))) {
    		this.theme = "spiffy";
    	}
    	
    	StringBuffer sb = new StringBuffer("");
    	sb.append("</div>");
    	sb.append("</div>");
    	
    	// if the theme is 'spiffy' for rounded corner boxes.
    	if (this.theme.equals("spiffy")) {
        	sb.append("<b class=\"spiffy\">");
        	sb.append("<b class=\"spiffy5\"></b>");
        	sb.append("<b class=\"spiffy4\"></b>");
        	sb.append("<b class=\"spiffy3\"></b>");
        	sb.append("<b class=\"spiffy2\"><b></b></b>");
        	sb.append("<b class=\"spiffy1\"><b></b></b>");
        	sb.append("</b>");
    	}
    	
    	sb.append("</div>");

    	TagUtils.getInstance().write(pageContext, sb.toString());
        return (EVAL_PAGE);
    }

    public void release()
    {
        super.release();
        name = null;
        cssClass = null;
        theme = null;
        id = null;
        isCollapsable = true;
        
        isDraggable = false;
        headerClass = null;
        headerId = null;
    }
}