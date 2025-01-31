package gov.cdc.nedss.webapp.nbs.util.taglib;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.util.IteratorAdapter;
import org.apache.struts.util.MessageResources;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import java.lang.reflect.InvocationTargetException;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

public class NedssOptionsCollectionTag extends TagSupport {
    // ----------------------------------------------------- Instance Variables

	/**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(Constants.Package
            + ".LocalStrings");

    // ------------------------------------------------------------- Properties

    /**
     * Should the label values be filtered for HTML sensitive characters?
     */
    protected boolean filter = true;

    /**
     * The name of the bean property containing the label.
     */
    protected String label = "label";

    /**
     * The name of the bean containing the values collection.
     */
    protected String name = Constants.BEAN_KEY;

    /**
     * The name of the property to use to build the values collection.
     */
    protected String property = null;

    /**
     * The style associated with this tag.
     */
    private String style = null;

    /**
     * The named style class associated with this tag.
     */
    private String styleClass = null;

    /**
     * The name of the bean property containing the value.
     */
    protected String value = "value";
    
    protected String statusCd = "statusCd";
    protected String effectiveToTime = "effectiveToTime";
    
    public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getEffectiveToTime() {
		return effectiveToTime;
	}

	public void setEffectiveToTime(String effectiveToTime) {
		this.effectiveToTime = effectiveToTime;
	}

    public boolean getFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Process the start of this tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        // Acquire the select tag we are associated with
        SelectTag selectTag =
            (SelectTag) pageContext.getAttribute(Constants.SELECT_KEY);

        if (selectTag == null) {
            JspException e =
                new JspException(messages.getMessage(
                        "optionsCollectionTag.select"));

            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        // Acquire the collection containing our options
        Object collection =
            TagUtils.getInstance().lookup(pageContext, name, property, null);

        if (collection == null) {
            JspException e =
                new JspException(messages.getMessage(
                        "optionsCollectionTag.collection"));

            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        // Acquire an iterator over the options collection
        Iterator iter = getIterator(collection);

        StringBuffer sb = new StringBuffer();

        // Render the options
        while (iter.hasNext()) {
            Object bean = iter.next();
            Object beanLabel = null;
            Object beanValue = null;
            Object beanStatusCd = null;
            Object beanEffectiveToTime = null;

            // Get the label for this option
            try {
                beanLabel = PropertyUtils.getProperty(bean, label);

                if (beanLabel == null) {
                    beanLabel = "";
                }
            } catch (IllegalAccessException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.access",
                            label, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                JspException jspe =
                    new JspException(messages.getMessage("getter.result",
                            label, t.toString()));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (NoSuchMethodException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.method",
                            label, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            }

            // Get the value for this option
            try {
                beanValue = PropertyUtils.getProperty(bean, value);

                if (beanValue == null) {
                    beanValue = "";
                }
            } catch (IllegalAccessException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.access",
                            value, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                JspException jspe =
                    new JspException(messages.getMessage("getter.result",
                            value, t.toString()));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (NoSuchMethodException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.method",
                            value, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            }
            
         // Get the statusCd for this option
            try {
                beanStatusCd = PropertyUtils.getProperty(bean, statusCd);

                if (beanStatusCd == null) {
                	beanStatusCd = "";
                }
            } catch (IllegalAccessException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.access",
                    		statusCd, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                JspException jspe =
                    new JspException(messages.getMessage("getter.result",
                    		statusCd, t.toString()));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (NoSuchMethodException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.method",
                    		statusCd, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            }
            
         // Get the effectiveToTime for this option
            try {
            	beanEffectiveToTime = PropertyUtils.getProperty(bean, effectiveToTime);
            } catch (IllegalAccessException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.access",
                    		effectiveToTime, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                JspException jspe =
                    new JspException(messages.getMessage("getter.result",
                    		effectiveToTime, t.toString()));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            } catch (NoSuchMethodException e) {
                JspException jspe =
                    new JspException(messages.getMessage("getter.method",
                    		effectiveToTime, bean));

                TagUtils.getInstance().saveException(pageContext, jspe);
                throw jspe;
            }

            String stringLabel = beanLabel.toString();
            String stringValue = beanValue.toString();
            String stringStatusCd = beanStatusCd.toString();
            Timestamp timestamp = null;
            Timestamp statusTime = null;
            		
            Object timeStampObj = pageContext.getRequest().getAttribute("addTime");
            if(timeStampObj != null)
            	timestamp = (Timestamp)timeStampObj;
            if(beanEffectiveToTime != null)
            	statusTime = (Timestamp)beanEffectiveToTime;
            
            if(stringLabel.equals("") && stringValue.equals(""))
            {
            	addOption(sb, stringLabel, stringValue,
                        selectTag.isMatched(stringValue));
            }  
            else if("A".equals(stringStatusCd))
			{
            	addOption(sb, stringLabel, stringValue,
                        selectTag.isMatched(stringValue));
		
			}else
			{
				if(timestamp != null && statusTime != null && statusTime.getTime() > timestamp.getTime())
				{
					addOption(sb, stringLabel, stringValue,
	                        selectTag.isMatched(stringValue));
				}else
				if(timestamp==null){//if addTime is null, it shows everything
					
					addOption(sb, stringLabel, stringValue,
	                        selectTag.isMatched(stringValue));
					
				}
			}
        }

        TagUtils.getInstance().write(pageContext, sb.toString());

        return SKIP_BODY;
    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
        filter = true;
        label = "label";
        name = Constants.BEAN_KEY;
        property = null;
        style = null;
        styleClass = null;
        value = "value";
        statusCd = "statusCd";
        effectiveToTime = "effectiveToTime";
    }

    // ------------------------------------------------------ Protected Methods

    /**
     * Add an option element to the specified StringBuffer based on the
     * specified parameters. <p> Note that this tag specifically does not
     * support the <code>styleId</code> tag attribute, which causes the HTML
     * <code>id</code> attribute to be emitted.  This is because the HTML
     * specification states that all "id" attributes in a document have to be
     * unique.  This tag will likely generate more than one
     * <code>option</code> element element, but it cannot use the same
     * <code>id</code> value.  It's conceivable some sort of mechanism to
     * supply an array of <code>id</code> values could be devised, but that
     * doesn't seem to be worth the trouble.
     *
     * @param sb      StringBuffer accumulating our results
     * @param value   Value to be returned to the server for this option
     * @param label   Value to be shown to the user for this option
     * @param matched Should this value be marked as selected?
     */
    protected void addOption(StringBuffer sb, String label, String value,
        boolean matched) {
        sb.append("<option value=\"");

        if (filter) {
            sb.append(TagUtils.getInstance().filter(value));
        } else {
            sb.append(value);
        }

        sb.append("\"");

        if (matched) {
            sb.append(" selected=\"selected\"");
        }

        if (style != null) {
            sb.append(" style=\"");
            sb.append(style);
            sb.append("\"");
        }

        if (styleClass != null) {
            sb.append(" class=\"");
            sb.append(styleClass);
            sb.append("\"");
        }

        sb.append(">");

        if (filter) {
            sb.append(TagUtils.getInstance().filter(label));
        } else {
            sb.append(label);
        }

        sb.append("</option>\r\n");
    }

    /**
     * Return an iterator for the options collection.
     *
     * @param collection Collection to be iterated over
     * @throws JspException if an error occurs
     */
    protected Iterator getIterator(Object collection)
        throws JspException {
        if (collection.getClass().isArray()) {
            collection = Arrays.asList((Object[]) collection);
        }

        if (collection instanceof Collection) {
            return (((Collection) collection).iterator());
        } else if (collection instanceof Iterator) {
            return ((Iterator) collection);
        } else if (collection instanceof Map) {
            return (((Map) collection).entrySet().iterator());
        } else if (collection instanceof Enumeration) {
            return new IteratorAdapter((Enumeration) collection);
        } else {
            throw new JspException(messages.getMessage(
                    "optionsCollectionTag.iterator", collection.toString()));
        }
    }
}