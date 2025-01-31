
package gov.cdc.nedss.webapp.nbs.util.taglib;

import gov.cdc.nedss.util.HTMLEncoder;
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

public class NEDSSContainerTag extends TagSupport
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
   

    public NEDSSContainerTag()
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
       
    }
    
   public String getIsHidden() {
        return isHidden;
   }

   public void setIsHidden(String isHidden) {
       this.isHidden = isHidden;
   }

   

public String getAddedClass() {
       return addedClass;
  }

  public void setAddedClass(String addedClass) {
      this.addedClass = addedClass;
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
            renderContainerElement());

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
    	
    	// section
    	if(classType!=null && classType.equals("sect"))
    	{
    	    // handle the property to show or hide the section.
    	    if (isHidden != null && isHidden.equals(NEDSSConstants.TRUE)) {
    	        sb.append("<div class=\"sect\" style=\"display:none;\" id=\"");
    	    }
    	    else {
    	        sb.append("<div class=\"sect\" id=\"");
    	    }
	        sb.append(HTMLEncoder.encodeHtml(this.id));
	        sb.append("\">");

	        // section header
	        sb.append("<table role = \"presentation\" class=\"sectHeader\"><tr>");
	        sb.append("<td class=\"sectName\">");
	        
	        // section header - toggle link and name
	        //displayImg to control plus/minus sign in the section header. if null it will display the image.
	        if(displayImg==null)
	        {
		        sb.append("<a class=\"toggleIconHref\" href=\"#\" tabindex=0 onclick=\"return toggleSectionDisplay('");
		        sb.append(HTMLEncoder.encodeHtml(this.getId())).append("');\">");
		        sb.append("<img border=\"0\" src=\"minus_sign.gif\" alt=\"Minimize\" title=\"Collapse Block\">");
		        sb.append("</a>");
	        }
	        sb.append("<a class=\"anchor\" tabindex=-1 name=\"");
	        sb.append(HTMLEncoder.encodeHtml(this.id)).append("\">").append(HTMLEncoder.encodeHtml(this.name)).append("</a>");
	        
	        sb.append("</td>");
	        
	        // section header - back to top link
	        if (includeBackToTopLink.equalsIgnoreCase("yes")) {
	        	sb.append("<td style=\"text-align:right;\"><a href=\"#pageTop\" tabindex=0 class=\"backToTopLink\">");
				sb.append("Back to top </a> </td>");
	        }
			
			// end of section header
	        sb.append("</tr></table>");
	        
	        // section body
	        sb.append("<div class=\"sectBody\">");
	        
	        // section body - subSections toggler
	        //displayLink to control the "Collapse Subsections"/"Expand Subsections" link. if null it will display the link. 
	        if(displayLink==null)
	        {
		        sb.append("<table class=\"subSectionsToggler\" >");
		        sb.append("<tr>");
		        sb.append("<td>");
		        //Added for 508 Compliance. Empty <a> was giving errors in case defaultDisplay = F
		        if((defaultDisplay!=null && defaultDisplay.equalsIgnoreCase("F"))){
		        	sb.append("<a style=\"display:none\" class=\"toggleHref\" href=\"#\" tabindex=0 onclick=\"return toggleAllSubSectionsDisplay('");
			        sb.append(HTMLEncoder.encodeHtml(this.id)).append("');\">");
		        	
			        sb.append("Hidden Link");
			        sb.append("</a>");
			        
		        }
		        else{
			        sb.append("<a class=\"toggleHref\" href=\"#\" tabindex=0 onclick=\"return toggleAllSubSectionsDisplay('");
			        sb.append(HTMLEncoder.encodeHtml(this.id)).append("');\">");
	
			        /*defaultDisplay to control which link we need to display. if in the section tag defaultDisplay
			         * is "collapse" than in the subSection tag defaultDisplay should be null or "expand". The result
			         *  will be all expanded subsections with the "Collapse Subsections" link*/
			        if(defaultDisplay !=null && defaultDisplay.equalsIgnoreCase("collapse")) {
			        	sb.append("Expand Subsections");
			        }
			        else if (defaultDisplay == null || defaultDisplay.equalsIgnoreCase("expand")) {
			        	sb.append("Collapse Subsections");
			        }
			       
			        sb.append("</a>");
		        }
				sb.append("</td>");
				sb.append("</tr>");
				sb.append("</table>");
	        }
    	}
    	// subsection
    	else if(classType!=null && classType.equals("subSect"))
    	{
    	    // handle the property to show or hide the subsection.
            if (isHidden != null && isHidden.equals(NEDSSConstants.TRUE)) {
                sb.append("<table class=\"subSect\" style=\"display:none;\" id=\"");
            }
            else {
                sb.append("<table class=\"subSect");
    	        if (addedClass != null)
    	        	sb.append(" " + addedClass);
                sb.append("\" id=\"");
            }
			sb.append(HTMLEncoder.encodeHtml(this.id)).append("\">");
    		
			
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
				sb.append("<a class=\"toggleIconHref\" href=\"#\" tabindex=0 onclick=\"return toggleSubSectionDisplay('");
				sb.append(HTMLEncoder.encodeHtml(this.id)).append("');\">");
				if(defaultDisplay !=null && defaultDisplay.equalsIgnoreCase("collapse")) {
					sb.append("<img border=\"0\" src=\"plus_sign.gif\" alt=\"Maximize\" title=\"Expand Block\">");
				}
				else if (defaultDisplay == null || defaultDisplay.equalsIgnoreCase("expand")) {
					sb.append("<img border=\"0\" src=\"minus_sign.gif\" alt=\"Minimize\" title=\"Collapse Block\">");
				}
				sb.append("</a>");
			}
			
			// subsection header - name
			if(name!=null) {
				sb.append(HTMLEncoder.encodeHtml(this.name));
			}
				
			// end of subsection
			sb.append("</th> </tr> </thead>");
			}

			// set the initial state of the subsection (collapse/expanded) depending on the
			// defaultDisplay attribute value
			if(defaultDisplay !=null && defaultDisplay.equalsIgnoreCase("collapse")) {
				sb.append("<tbody style=\"display:none;\">");
			}
		    else if (defaultDisplay == null || defaultDisplay.equalsIgnoreCase("expand")) {
		    	sb.append("<tbody>");
		    }
    	}
	    return sb.toString();
    }

    /**
     * Process the end of this tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
    	if(classType!=null && classType.equals("sect"))
    		TagUtils.getInstance().write(pageContext, "</div></div>");
        else if(classType!=null && classType.equals("subSect"))
        	TagUtils.getInstance().write(pageContext, "</tbody></table>");
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