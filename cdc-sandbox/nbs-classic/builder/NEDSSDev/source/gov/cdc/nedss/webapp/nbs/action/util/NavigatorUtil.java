package gov.cdc.nedss.webapp.nbs.action.util;

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import org.apache.log4j.Logger;

public class NavigatorUtil {
	public static final String ANCHORSTART = "<a href='";

	public static final String TAGCLOSER = ">";

	public static final String ANCHOREND = "</a>";

	public static final String ANCHORSTART_JS = "<a href=\"";

	public static final String JAVA_SCRIPT_POPUP = "javascript:popUp('";

	public static final String TAGCLOSER_JS = "\">";

	public static final String FRONTSLASH = "/";

	public static final String APPLICATION_WAR_VALUE = "nbs";

	public static final String QUESTIONMARK = "?";

	public static final String EQUALS = "=";

	public static final String AMPERSAND = "&";

	public static final String SINGLEQUOTE = "'";
	
	private final static Logger logger = Logger.getLogger(NavigatorUtil.class);

	/**
	 * Gets the Action Name for the passed ModuleName. Deletes all the spaces in
	 * the Module Name.
	 *
	 * @param moduleName
	 *            String
	 * @return String
	 */
	public static String getActionNameForModuleName(String moduleName) {
		StringBuffer theModuleName = new StringBuffer(moduleName);

		for (int i = 0; i < theModuleName.length(); i++) {
			if (theModuleName.charAt(i) == ' ')
				theModuleName.deleteCharAt(i);

		}
		
		return theModuleName.toString();

	}

	public static String getLink(String actionPath, String displayName,
			HashMap parameterMap) {
		StringBuffer sb = new StringBuffer();
		if (parameterMap != null) {
			Set keySey = parameterMap.keySet();
			Iterator i = keySey.iterator();
			String key = null;
			String value = null;
			sb.append(ANCHORSTART);
			buildHREF(actionPath, displayName, parameterMap, sb, i);
			sb.append(TAGCLOSER);
			sb.append(displayName);
			sb.append(ANCHOREND);
		}
		logger.debug("getLink : " + sb);
		return sb.toString();
	}

	public static String getPopUpLink(String actionPath, String displayName,
			HashMap parameterMap, String windowCharacteristics) {
		StringBuffer sb = new StringBuffer();
		if (parameterMap != null) {
			Set keySey = parameterMap.keySet();
			Iterator i = keySey.iterator();
			sb.append(ANCHORSTART_JS);
			sb.append(JAVA_SCRIPT_POPUP);
			buildHREF(actionPath, displayName, parameterMap, sb, i);
			sb.append(" , '");
			sb.append(windowCharacteristics);
			sb.append("' )");
			sb.append(TAGCLOSER_JS);
			sb.append(displayName);
			sb.append(ANCHOREND);
		}
		return sb.toString();
	}

	//<A HREF="javascript:popUp('/pemsr1/RetrieveAnnouncements.do?announcementId=35&method=read'  )">Tuesday Test</A>

	public static String getLink(String actionPath, String displayName,
			HashMap parameterMap, String javascriptString) {
		StringBuffer sb = new StringBuffer();
		if (parameterMap != null) {
			Set keySey = parameterMap.keySet();
			Iterator i = keySey.iterator();
			String key = null;
			String value = null;
			sb.append(ANCHORSTART);
			buildHREF(actionPath, displayName, parameterMap, sb, i);
			sb.append(" ");
			sb.append(javascriptString);
			sb.append(TAGCLOSER);
			sb.append(displayName);
			sb.append(ANCHOREND);
		}
		logger.debug("getLink : " + sb);
		return sb.toString();
	}

	public static String getLink(String displayName, String javascriptString) {
		StringBuffer sb = new StringBuffer();
		sb.append(ANCHORSTART_JS);
		sb.append(javascriptString);
		sb.append(TAGCLOSER);
		sb.append(displayName);
		sb.append(ANCHOREND);
		logger.debug("getLink : " + sb);
		return sb.toString();
	}

	/**
	 * @param actionPath
	 * @param displayName
	 * @param parameterMap
	 * @param sb
	 * @param i
	 */
	private static void buildHREF(String actionPath, String displayName,
			HashMap parameterMap, StringBuffer sb,Iterator<Object>  i) {
		String key;
		String value;
		sb.append(FRONTSLASH + APPLICATION_WAR_VALUE);
		sb.append(FRONTSLASH + actionPath);
		sb.append(QUESTIONMARK);
		while (i.hasNext()) {
			key = (String) i.next();
			value = (String) parameterMap.get(key);
			sb.append(key);
			sb.append(EQUALS);
			sb.append(value);
			sb.append(AMPERSAND);
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(SINGLEQUOTE);
	}

	public static void main(String[] a) {
		System.out.println(getLink("abc", "javascript:alert('hi')"));

	}
}
