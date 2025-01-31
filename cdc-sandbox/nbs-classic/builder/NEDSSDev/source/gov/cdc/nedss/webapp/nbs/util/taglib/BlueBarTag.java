
package gov.cdc.nedss.webapp.nbs.util.taglib;

import gov.cdc.nedss.util.NEDSSConstants;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.taglib.TagUtils;

/**
 * This tag implementation is used for rendering sections and subsections. Subsections
 * in its current state is a collection of rows of 2 cells.
 * (sections and subsections are HTML container classes that are
*  defined in the common.css file present under the '/nbs/resource/style/recent'
*  directory).
*
*  An example of a JSP page that uses sections and subsections is given below:
*
*   <div class="sect">
*   	<table class="sectHeader" id="section1">
*   		<tr>
*   			<td class="sectName">
*   				<a class="toggleIconHref" href="javascript:toggleSectionDisplay('section1')">
*   					<img border="0" src="minus_sign.gif" alt="Minimize" title="Collapse Block">
*   				</a>
*   				<a class="anchor" name="section1">
*   					SECTION 1
*   				</a>
*   			</td>
*   			<td style="text-align:right;">
*   				<a href="#pageTop" style="font-weight:normal; text-decoration:underline;">
*						Back to top
*					</a>
*				</td>
*   		</tr>
*   	</table>
*   	<div class="sectBody">
*   		<table class="subSectionsToggler" >
*   			<tr>
*   				<td>
*   					<a class="toggleHref" href="javascript:toggleAllSubSectionsDisplay('section1')>
*   						Collapse Subsections
*   					</a>
*   				</td>
*   			</tr>
*   		</table>
*   		<table class="subSect" id="subSection1">
*   			<thead>
*   				<tr>
*   					<th colspan="2">
*   						<a class="toggleIconHref" href="javascript:toggleSubSectionDisplay('')">
*   							<img border="0" src="plus_sign.gif" alt="Minimize" title="Collapse Block">
*   						</a>
*   						SUBSECTION 1
*   					</th>
*   				</tr>
*   			</thead>
*   			<tbody>
*   				<tr>
*   					<td class=fieldName> Field Name : </td>
*   					</td> Fiedl Value </td>
*   				</tr>
*   				<tr>
*   					<td> Field Name : </td>
*   					</td> Fiedl Value </td>
*   				</tr>
*   			</tbody>
*   		</table>
*   	</div>
*   </div>
*/

public class BlueBarTag extends TagSupport
{
    protected String name;
    protected String id;
    protected String classType;
    protected String displayImg;
    protected String displayLink;
    protected String defaultDisplay;
    protected String includeBackToTopLink;
    protected String isHidden;
    protected String addedClass;
    protected String count;
    protected String buttonLabel;
    protected String buttonLabel1;
    protected String buttonPermission;
    protected String buttonPermission1;
    public String getButtonPermission() {
		return buttonPermission;
	}

	public void setButtonPermission(String buttonPermission) {
		this.buttonPermission = buttonPermission;
	}

	protected String buttonJSFunction;
	protected String buttonJSFunction1;
	
    public BlueBarTag()
    {
        name = null;
        id = null;
        classType = null;
        displayImg = null;
        displayLink = null;
        defaultDisplay = null;
        includeBackToTopLink = "yes";
        isHidden = NEDSSConstants.FALSE;
        addedClass = null;
        count = null;
        buttonLabel = null;
        buttonLabel1 = null;
        buttonPermission = null;
        buttonPermission1 = null;
        buttonJSFunction = null;
        buttonJSFunction1 = null;
    }

   public String getIsHidden() {
        return isHidden;
   }

   public void setIsHidden(String isHidden) {
       this.isHidden = isHidden;
   }

   public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

   public String getAddedClass() {
       return addedClass;
  }

  public void setAddedClass(String addedClass) {
      this.addedClass = addedClass;
  }

  public String getButtonLabel() {
		return buttonLabel;
	}

	public void setButtonLabel(String buttonLabel) {
		this.buttonLabel = buttonLabel;
	}

	public String getButtonLabel1() {
		return buttonLabel1;
	}

	public void setButtonLabel1(String buttonLabel1) {
		this.buttonLabel1 = buttonLabel1;
	}

	public String getButtonJSFunction() {
		return buttonJSFunction;
	}
	public void setButtonJSFunction(String buttonJSFunction) {
		this.buttonJSFunction = buttonJSFunction;
	}

	public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int doStartTag() throws JspException {
        TagUtils.getInstance().write(this.pageContext,
            this.renderContainerElement());

        return EVAL_BODY_INCLUDE;
    }

    /**
     * Renders an &lt;html&gt; element with appropriate language attributes.
     *
     * @since Struts 1.2
     */
    protected String renderContainerElement()
    {
    	StringBuffer sb = new StringBuffer("");
    	boolean addButton = false;
    	boolean compare = false;

    	if(buttonLabel!=null && buttonPermission!=null && buttonPermission.equals("true") && buttonJSFunction!=null)
    		addButton=true;
    	
    	if(buttonLabel1!=null && buttonPermission1!=null && buttonPermission1.equals("true") && buttonJSFunction1!=null)
    		compare=true;
    	// sb.append("<div><table><tr><td height=\"02 px\"></td></tr></table></div>");
    	// section
    	if(classType!=null && classType.equals("bluebarsect"))
    	{
    	    // handle the property to show or hide the section.
    	    if (isHidden != null && isHidden.equals(NEDSSConstants.TRUE)) {
    	        sb.append("<div class=\"bluebarsect\" style=\"display:none;\" id=\"");
    	    }
    	    else {
    	        sb.append("<div class=\"bluebarsect\" id=\"");
    	    }
	        sb.append(this.id);

	        sb.append("\">");

	        // section header
	        if(this.id.equalsIgnoreCase("patSearch2") ||this.id.equalsIgnoreCase("patSearch1"))
	        sb.append("<table role=\"presentation\" class=\"bluebarsectHeader\"><tr>");
	        else
	        sb.append("<table role=\"presentation\" class=\"bluebarsectHeader\"><tr>");
	        sb.append("<td width=\"3 px\" class=\"bluebarsectName\">");

	        // section header - toggle link and name
	        //displayImg to control plus/minus sign in the section header. if null it will display the image.
	        if(displayImg==null)
	        {
		        sb.append("<a class=\"toggleIconHref\" href=\"#\" onclick=\"return toggleSectionDisplay1('");
		        sb.append(this.getId()).append("');\">");
		        if(this.defaultDisplay!=null && "Collase".equalsIgnoreCase(this.defaultDisplay)){
		        	sb.append("<img border=\"0\" src=\"section_collapse.gif\" alt=\"Minimize\" title=\"Collapse Block\">");
		        }else if(this.defaultDisplay!=null && "expand".equalsIgnoreCase(this.defaultDisplay)){
		        	sb.append("<img border=\"0\" src=\"section_expand.gif\" alt=\"Maximize\" title=\"Expand Block\">");
		        }else{
		        	sb.append("<img border=\"0\" src=\"section_collapse.gif\" alt=\"Minimize\" title=\"Collapse Block\">");
		        }
		        sb.append("</a>");
	        }
	        sb.append("</td><td class=\"bluebarsectName\">");
	        sb.append("<a class=\"anchor\" tabindex=-1 name=\"");
	        sb.append(this.id).append("\">").append(this.name).append("</a>");
	        if(this.count != null)
	        	sb.append(" (").append(this.count).append(")");
	 	        //sb.append("</td><td height=\"20 px\">&nbsp</td>");
	        sb.append("</td>");

	        // section header - back to top link
	        if (includeBackToTopLink.equalsIgnoreCase("yes") || addButton || compare ) {
	        	sb.append("<td style=\"text-align:right;\" class=\"bluebarsectName\">");
	        	if(includeBackToTopLink.equalsIgnoreCase("yes") && addButton  && compare){
	        		sb.append("<input type=\"button\" name=\"Compare\" value=\""+buttonLabel1+"\" onclick=\""+buttonJSFunction1+"\">&nbsp;");
	        		sb.append("<input type=\"button\" name=\"Add\" value=\""+buttonLabel+"\" onclick=\""+buttonJSFunction+"\">&nbsp;");
        			sb.append("<a href=\"#pageTop\" tabindex=0 class=\"backToTopLinkforViewFile\">Back to top</a>&nbsp;");
	        	}
	        	else if(includeBackToTopLink.equalsIgnoreCase("yes") && addButton){
	        		sb.append("<input type=\"button\" name=\"Add\" value=\""+buttonLabel+"\" onclick=\""+buttonJSFunction+"\">&nbsp;");
        			sb.append("<a href=\"#pageTop\" tabindex=0 class=\"backToTopLinkforViewFile\">Back to top</a>&nbsp;");
	        	}
	        	else if(includeBackToTopLink.equalsIgnoreCase("yes"))
	        			sb.append("<a href=\"#pageTop\" tabindex=0 class=\"backToTopLinkforViewFile\">Back to top</a>&nbsp;");
	        	else if(addButton)
	        		sb.append("<input type=\"button\" name=\"Add\" value=\" "+buttonLabel+" \" onclick=\""+buttonJSFunction+"\">&nbsp;");
	        	else if(compare)
	        		sb.append("<input type=\"button\" name=\"Add\" value=\" "+buttonLabel1+" \" onclick=\""+buttonJSFunction1+"\">&nbsp;");
				sb.append("</td>");
	        }

			// end of section header
	        sb.append("</tr></table>");


	        // section body
	        sb.append("<div class=\"sectBody\">");

	        // section body - subSections toggler
	        //displayLink to control the "Collapse Subsections"/"Expand Subsections" link. if null it will display the link.
	      if(displayLink==null)
	        {
	    	   /* sb.append("<table class=\"subSectionsToggler\" >");
		        sb.append("<tr>");
		        sb.append("<td>");
		        sb.append("<a class=\"toggleHref\" href=\"#\" tabindex=-1 onclick=\"return toggleAllSubSectionsDisplay('");
		        sb.append(this.id).append("');\">");

		        defaultDisplay to control which link we need to display. if in the section tag defaultDisplay
		         * is "collapse" than in the subSection tag defaultDisplay should be null or "expand". The result
		         *  will be all expanded subsections with the "Collapse Subsections" link
		        if(defaultDisplay !=null && defaultDisplay.equalsIgnoreCase("collapse")) {
		        	sb.append("Expand Subsections");
		        }
		        else if (defaultDisplay == null || defaultDisplay.equalsIgnoreCase("expand")) {
		        	sb.append("Collapse Subsections");
		        }

		        sb.append("</a>");
				sb.append("</td>");
				sb.append("</tr>");
				sb.append("</table>");*/
	        }
    	}
    	// subsection
    	else if(classType!=null && classType.equals("bluebarsubSect"))
    	{
    	    // handle the property to show or hide the subsection.
            if (isHidden != null && isHidden.equals(NEDSSConstants.TRUE)) {
                sb.append("<table role=\"presentation\" class=\"bluebarsubSect\" style=\"display:none;\" id=\"");
            }
            else {
                sb.append("<table role=\"presentation\" class=\"bluebarsubSect");
    	        if (addedClass != null)
    	        	sb.append(" " + addedClass);
                sb.append("\" id=\"");
            }
			sb.append(this.id).append("\">");

			if(displayImg!=null && (name==null || name=="")){
				sb.append("<thead>");
				sb.append("<tr>");
				sb.append("<td colspan=\"4\" >");
				// end of subsection
				sb.append("</td> </tr></thead>");
			}else{
    		// subsection header
			sb.append("<thead>");
			sb.append("<tr>");
			sb.append("<th colspan=\"2\" >");

			// subsection header - toggler link
			if(displayImg==null){
				sb.append("<a class=\"toggleIconHref\" href=\"#\" onclick=\"return toggleSubSectionDisplay('");
				sb.append(this.id).append("');\">");
				if(defaultDisplay !=null && defaultDisplay.equalsIgnoreCase("expand")) {
					sb.append("<img border=\"0\" src=\"section_collapse.gif\" alt=\"Minimize\" title=\"Collapse Block\">");
				}
				else if (defaultDisplay != null && defaultDisplay.equalsIgnoreCase("collapse")) {
					sb.append("<img border=\"0\" src=\"section_expand.gif\" alt=\"Maximize\" title=\"Expand Block\">");
				}else {
					sb.append("<img border=\"0\" src=\"section_collapse.gif\" alt=\"Minimize\" title=\"Collapse Block\">");
				}
				sb.append("</a>");
			}

			// subsection header - name
			if(name!=null) {
				sb.append(this.name);
			}
			if(count != null){
				sb.append(" (").append(this.count).append(")");
			}
			// end of subsection
			sb.append("</th> </tr> </thead>");
    	}
			// set the initial state of the subsection (collapse/expanded) depending on the
			// defaultDisplay attribute value
			if(defaultDisplay !=null && defaultDisplay.equalsIgnoreCase("collapse")) {
				sb.append("<tbody style=\"display:none;\">");
			}
		    else if (defaultDisplay != null && defaultDisplay.equalsIgnoreCase("expand")) {
		    	sb.append("<tbody>");
		    }else {
		    	sb.append("<tbody>");
		    }
    	}
	    return sb.toString();
    }

    /**
	 * @return the buttonPermission1
	 */
	public String getButtonPermission1() {
		return buttonPermission1;
	}

	/**
	 * @param buttonPermission1 the buttonPermission1 to set
	 */
	public void setButtonPermission1(String buttonPermission1) {
		this.buttonPermission1 = buttonPermission1;
	}

	/**
	 * @return the buttonJSFunction1
	 */
	public String getButtonJSFunction1() {
		return buttonJSFunction1;
	}

	/**
	 * @param buttonJSFunction1 the buttonJSFunction1 to set
	 */
	public void setButtonJSFunction1(String buttonJSFunction1) {
		this.buttonJSFunction1 = buttonJSFunction1;
	}

	/**
     * Process the end of this tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
    	if(classType!=null && classType.equals("bluebarsect"))
    		TagUtils.getInstance().write(pageContext, "</div></div>");
        else if(classType!=null && classType.equals("bluebarsubSect"))
        	TagUtils.getInstance().write(pageContext, "</tbody></table>");
    	TagUtils.getInstance().write(pageContext,"<div style=\"width:98%;\"><table><tr><td height=\"12 px\"></td></tr></table></div>");
        // Evaluate the remainder of this page
        return (EVAL_PAGE);
    }


    public void release()
    {
        super.release();
        name = null;
        id = null;
        classType = null;
        displayImg = null;
        displayLink = null;
        defaultDisplay = null;
        count = null;
    }


	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getDisplayImg() {
		return displayImg;
	}

	public void setDisplayImg(String displayImg) {
		this.displayImg = displayImg;
	}

	public String getDisplayLink() {
		return displayLink;
	}

	public void setDisplayLink(String displayLink) {
		this.displayLink = displayLink;
	}

	public String getDefaultDisplay() {
		return defaultDisplay;
	}

	public void setDefaultDisplay(String defaultDisplay) {
		this.defaultDisplay = defaultDisplay;
	}

	public String getIncludeBackToTopLink() {
		return includeBackToTopLink;
	}

	public void setIncludeBackToTopLink(String includeBackToTopLink) {
		this.includeBackToTopLink = includeBackToTopLink;
	}
}