
package gov.cdc.nedss.webapp.nbs.util.taglib;

import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

public class NEDSSViewTag extends TagSupport
{

	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils(NEDSSViewTag.class.getName());
	private static final String YES = "yes";
	private static final String NO = "No";
    public static final String SQL_TIMESTAMP_FORMAT_KEY = "org.apache.struts.taglib.bean.format.sql.timestamp";
    public static final String SQL_DATE_FORMAT_KEY = "org.apache.struts.taglib.bean.format.sql.date";
    public static final String SQL_TIME_FORMAT_KEY = "org.apache.struts.taglib.bean.format.sql.time";
    public static final String DATE_FORMAT_KEY = "org.apache.struts.taglib.bean.format.date";
    public static final String INT_FORMAT_KEY = "org.apache.struts.taglib.bean.format.int";
    public static final String FLOAT_FORMAT_KEY = "org.apache.struts.taglib.bean.format.float";
    protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.bean.LocalStrings");
    protected boolean filter;
    protected boolean ignore;
    protected String name;
    protected String property;
    protected String scope;
    protected String formatStr;
    protected String formatKey;
    protected String localeKey;
    protected String bundle;
    protected String codeSetNm;
    protected String methodNm;
    protected String methodParam;
    protected String mutliSelectResultInParanthesis = NO;

    public NEDSSViewTag()
    {
        filter = false;
        ignore = false;
        name = null;
        property = null;
        scope = null;
        formatStr = null;
        formatKey = null;
        localeKey = null;
        bundle = null;
        codeSetNm = null;
        methodNm = null;
        methodParam = null;
    }

    public boolean getFilter()
    {
        return filter;
    }

    public void setFilter(boolean filter)
    {
        this.filter = filter;
    }

    public boolean getIgnore()
    {
        return ignore;
    }

    public void setIgnore(boolean ignore)
    {
        this.ignore = ignore;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public String getFormat()
    {
        return formatStr;
    }

    public void setFormat(String formatStr)
    {
        this.formatStr = formatStr;
    }

    public String getFormatKey()
    {
        return formatKey;
    }

    public void setFormatKey(String formatKey)
    {
        this.formatKey = formatKey;
    }

    public String getLocale()
    {
        return localeKey;
    }

    public void setLocale(String localeKey)
    {
        this.localeKey = localeKey;
    }

    public String getBundle()
    {
        return bundle;
    }

    public void setBundle(String bundle)
    {
        this.bundle = bundle;
    }

    public String getMutliSelectResultInParanthesis() {
		return mutliSelectResultInParanthesis;
	}

	public void setMutliSelectResultInParanthesis(
			String mutliSelectResultInParanthesis) {
		this.mutliSelectResultInParanthesis = mutliSelectResultInParanthesis;
	}

	public int doStartTag()
        throws JspException
    {
        if(ignore && TagUtils.getInstance().lookup(pageContext, name, scope) == null)
        {
            return 0;
        }
         Object value = TagUtils.getInstance().lookup(pageContext, name, property, scope);
        if(value == null || value.equals(""))
        {
            return 0;
        }
        String output = formatValue(value);
        if(filter)
        {
            TagUtils.getInstance().write(pageContext, TagUtils.getInstance().filter(output));
        } else
        {
            TagUtils.getInstance().write(pageContext, output);
        }
        return 0;
    }

    protected String retrieveFormatString(String formatKey)
        throws JspException
    {
        String result = TagUtils.getInstance().message(pageContext, bundle, localeKey, formatKey);
        if(result != null && (!result.startsWith("???") || !result.endsWith("???")))
        {
            return result;
        } else
        {
            return null;
        }
    }

    protected String formatValue(Object valueToFormat)
        throws JspException
    {
        Format format = null;
        Object value = valueToFormat;
        java.util.Locale locale = TagUtils.getInstance().getUserLocale(pageContext, localeKey);
        boolean formatStrFromResources = false;
        String formatString = formatStr;
        if(value instanceof String || value instanceof Long)
        {
        	String output = "";
        	if (codeSetNm != null) {
        		output = getCodeDescTxtForCd(String.valueOf(value), codeSetNm);
        		if("".equals(output))
        			return HTMLEncoder.encodeHtml(String.valueOf(value));
        		else
        			return output;
    		} else if(methodNm != null) {
    			output = getCodeDescTxtForCdByMethod(String.valueOf(value), methodNm, methodParam);
    			if("".equals(output))
        			return String.valueOf(value);
        		else
        			return output;
    		}
        	else 
            return HTMLEncoder.encodeHtml(String.valueOf(value));
        }
        if(value instanceof Object[])
        {
        	String output = "";
        	if (codeSetNm != null) {
        		output = getCodeDescTxtForMultiSelect((Object[])value, codeSetNm);
        		return output;
    		} else if(methodNm != null) {
    			try {
					output = getCodeDescTxtForMultiSelectByMethod((Object[])value, methodNm, methodParam);
					return output;
    			} catch (Exception e) {
					logger.error("Error in getCodeDescTxtForMultiSelectByMethod of NEDSSViewTag for methodNm: " + methodNm);
				}	
    		}
        }
        if(formatString == null && formatKey != null)
        {
            formatString = retrieveFormatString(formatKey);
            if(formatString != null)
            {
                formatStrFromResources = true;
            }
        }
        if(value instanceof Number)
        {
            if(formatString == null)
            {
                if((value instanceof Byte) || (value instanceof Short) || (value instanceof Integer) || (value instanceof Long) || (value instanceof BigInteger))
                {
                    formatString = retrieveFormatString("org.apache.struts.taglib.bean.format.int");
                } else
                if((value instanceof Float) || (value instanceof Double) || (value instanceof BigDecimal))
                {
                    formatString = retrieveFormatString("org.apache.struts.taglib.bean.format.float");
                }
                if(formatString != null)
                {
                    formatStrFromResources = true;
                }
            }
            if(formatString != null)
            {
                try
                {
                    format = NumberFormat.getNumberInstance(locale);
                    if(formatStrFromResources)
                    {
                        ((DecimalFormat)format).applyLocalizedPattern(formatString);
                    } else
                    {
                        ((DecimalFormat)format).applyPattern(formatString);
                    }
                }
                catch(IllegalArgumentException e)
                {
                    JspException ex = new JspException(messages.getMessage("write.format", formatString));
                    TagUtils.getInstance().saveException(pageContext, ex);
                    throw ex;
                }
            }
        } else
        if(value instanceof Date)
        {
            if(formatString == null)
            {
                if(value instanceof Timestamp)
                {
                    formatString = retrieveFormatString("org.apache.struts.taglib.bean.format.sql.timestamp");
                } else
                if(value instanceof java.sql.Date)
                {
                    formatString = retrieveFormatString("org.apache.struts.taglib.bean.format.sql.date");
                } else
                if(value instanceof Time)
                {
                    formatString = retrieveFormatString("org.apache.struts.taglib.bean.format.sql.time");
                } else
                if(value instanceof Date)
                {
                    formatString = retrieveFormatString("org.apache.struts.taglib.bean.format.date");
                }
            }
            if(formatString != null)
            {
                format = new SimpleDateFormat(formatString, locale);
            }
        }
        if(format != null)
        {
            return HTMLEncoder.encodeHtml(String.valueOf(value));
        } else
        {
            return HTMLEncoder.encodeHtml(value.toString());
        }
    }

    public void release()
    {
        super.release();
        filter = false;
        ignore = false;
        name = null;
        property = null;
        scope = null;
        formatStr = null;
        formatKey = null;
        localeKey = null;
        bundle = null;
        codeSetNm = null;
    }

	public String getCodeSetNm() {
		return codeSetNm;
	}

	public void setCodeSetNm(String codeSetNm) {
		this.codeSetNm = codeSetNm;
	}

	private String getCodeDescTxtForCd(String code, String codeSetNm) {
		ArrayList<Object> aList = CachedDropDowns.getCachedDropDownList(codeSetNm);
		StringBuffer desc = new StringBuffer("");
			if (aList != null && aList.size() > 0) {
				Iterator ite = aList.iterator();
				while (ite.hasNext()) {
					DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
					if (ddcDT.getKey().equals(code)) {
						desc.append(ddcDT.getValue());
						break;
					}
				}
			}
			return desc.toString();
		} 
	

	private String getCodeDescTxtForCdByMethod(String code, String methodNm, String methodParam) {

		try {
			Class cdv = Class.forName("gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns");
			methodNm = "get" + methodNm;
			ArrayList<Object> aList = null;
			if(methodParam == null || (methodParam != null && methodParam.equals(""))) {
				Class parms[] = new Class[0];
				Method method = cdv.getMethod(methodNm, parms);
				ArrayList<Object> objAr = new ArrayList<Object> ();
				aList = (ArrayList<Object> ) method.invoke(cdv, objAr.toArray());				
			} else {
				Class parms[] = new Class[1];
				if(methodParam != null && methodParam.length() > 0)
					parms[0] = java.lang.String.class;			
				Method method = cdv.getMethod(methodNm, parms);			
		        Object[] insertMethodArguments = new String[1];	        
	        	insertMethodArguments[0] = methodParam;		
		        aList = (ArrayList<Object> ) method.invoke(cdv, insertMethodArguments);
			}
			
			StringBuffer desc = new StringBuffer("");
			if (aList != null && aList.size() > 0) {
				Iterator ite = aList.iterator();
				while (ite.hasNext()) {
					DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
					if (ddcDT.getKey().equals(code)) {
						desc.append(ddcDT.getValue());
						break;
					}
				}
			}
			return desc.toString();
		} catch (Exception e) {
			logger.error("Error in getCodeDescTxtForCdByMethod of NEDSSViewTag for Code: " + code + " and methodNm: " + methodNm);
		}
		return "";
	} 

	private String getCodeDescTxtForMultiSelectByMethod(Object[] codes, String methodNm, String methodParam) throws Exception  {
		
		Class cdv = Class.forName("gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns");
		methodNm = "get" + methodNm;
		ArrayList<Object> aList = null;
		if(methodParam == null || (methodParam != null && methodParam.equals(""))) {
			Class parms[] = new Class[0];
			Method method = cdv.getMethod(methodNm, parms);
			ArrayList<Object> objAr = new ArrayList<Object> ();
			aList = (ArrayList<Object> ) method.invoke(cdv, objAr.toArray());				
		} else {
			Class parms[] = new Class[1];
			if(methodParam != null && methodParam.length() > 0)
				parms[0] = java.lang.String.class;			
			Method method = cdv.getMethod(methodNm, parms);			
	        Object[] insertMethodArguments = new String[1];	        
        	insertMethodArguments[0] = methodParam;		
	        aList = (ArrayList<Object> ) method.invoke(cdv, insertMethodArguments);
		}
		StringBuffer desc = new StringBuffer("");
		String output = "";
		if (codes.length > 0) {
			for (int i = 0; i < codes.length; i++) {
				String cd = (String)codes[i];
				if (aList != null && aList.size() > 0) {
					Iterator ite = aList.iterator();
					while (ite.hasNext()) {
						DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
						if (ddcDT.getKey().equals(cd)) {
							desc.append(ddcDT.getValue());
							desc.append(", ");
						}
					}
				}
			}
			if(desc.length()>2)
			output=desc.substring(0, desc.length() - 2);
		}
		return output;
	}
	
	private String getCodeDescTxtForMultiSelect(Object[] codes, String codeSetNm) {
		ArrayList<Object> aList = CachedDropDowns.getCachedDropDownList(codeSetNm);
		StringBuffer desc = new StringBuffer("");
		String output = "";
		if (mutliSelectResultInParanthesis.equals(YES)) {
			output += " (";
		}
		if (codes.length > 0) {
			for (int i = 0; i < codes.length; i++) {
				String cd = (String)codes[i];
				if (aList != null && aList.size() > 0) {
					Iterator ite = aList.iterator();
					while (ite.hasNext()) {
						DropDownCodeDT ddcDT = (DropDownCodeDT) ite.next();
						if (ddcDT.getKey().equals(cd)) {
							desc.append(ddcDT.getValue());
							desc.append(", ");
						}
					}
				}
			}
			if(desc.length()>2)
			output += desc.substring(0, desc.length() - 2);
		}
		if (mutliSelectResultInParanthesis.equals(YES)) {
			output += ")";
		}
		return output;
	}

	public String getMethodNm() {
		return methodNm;
	}

	public void setMethodNm(String methodNm) {
		this.methodNm = methodNm;
	}

	public String getMethodParam() {
		return methodParam;
	}

	public void setMethodParam(String methodParam) {
		this.methodParam = methodParam;
	}	
}
