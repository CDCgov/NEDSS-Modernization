package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.systemservice.dt.XSSFilterPatternDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

	private static String PATTERN_FLAGS_1 = "Pattern.CASE_INSENSITIVE|Pattern.MULTILINE|Pattern.DOTALL";
	private static String PATTERN_FLAGS_2 = "Pattern.CASE_INSENSITIVE";
	private static PropertyUtil pu = PropertyUtil.getInstance();
	public static ArrayList<Pattern> patterns;
	static final LogUtils logger = new LogUtils(XSSRequestWrapper.class.getName());
	static {
		patterns = getPatternList();
	}
	public XSSRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			if(super.getQueryString()!=null && super.getQueryString().contains(parameter)){
				encodedValues[i] =  htmlEncode(values[i]);
			}else {
				encodedValues[i] = stripXSS(values[i]);
			}
		}
		return encodedValues;
	}
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if(super.getQueryString()!=null && super.getQueryString().contains(parameter)){
			return htmlEncode(value);
		}
		return stripXSS(value);
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		return stripXSS(value);

	}

	private static ArrayList<Pattern> getPatternList() {
		if(pu.getXSSFilterEnabled()==null || !pu.getXSSFilterEnabled().equals(NEDSSConstants.YES))
			return new ArrayList<Pattern>();
		ArrayList<Object> filterList = CachedDropDowns
				.getXSSFilterPatternList();
		ArrayList<Pattern> returnList = new ArrayList<Pattern>();
		if (filterList != null && filterList.size() > 0) {
			for (Object filterDT : filterList) {
				String flag = ((XSSFilterPatternDT) filterDT).getFlag();
				String regExp = ((XSSFilterPatternDT) filterDT).getRegExp();
				if (regExp != null) {
					if (flag != null && flag.equals(PATTERN_FLAGS_1)) {
						returnList.add(Pattern.compile(regExp,
								Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
										| Pattern.DOTALL));
					} else if (flag != null && flag.equals(PATTERN_FLAGS_2)) {
						returnList.add(Pattern.compile(regExp,
								Pattern.CASE_INSENSITIVE));
					} else {
						returnList.add(Pattern.compile(regExp,
								Pattern.CASE_INSENSITIVE));
					}
				}
			}
		}
		return returnList;
	}

	private String stripXSS(String value) {
		if (value != null) {
			value = value.replaceAll("\0", "");
			// Remove all sections that match a pattern
			for (Pattern scriptPattern : patterns) {
				//logger.info("Value Before XSS Filter Strip: "+value);
				value = scriptPattern.matcher(value).replaceAll("");
				//logger.info("Value After XSS Filter Strip: "+value);
			}
		}
		return value;
	}
	
	private String htmlEncode(String value) {
		if (value != null) {
			value = value.replaceAll("\0", "");
			value = StringUtils.escapeSql(value);
			return HTMLEncoder.encodeHtml(value);
		}
		return value;
	}
	
}
