package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.AjaxFilter;
import org.directwebremoting.AjaxFilterChain;

public class XSSFilter implements Filter, AjaxFilter  {
	
	static final LogUtils logger = new LogUtils(XSSFilter.class.getName());
	private static PropertyUtil pu = PropertyUtil.getInstance();
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)

	throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");

		HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
		
	    if(NEDSSConstants.YES.equals(pu.getRefererHeaderCheck())) {
	    	String requestUrl = httpServletRequest.getRequestURL().toString();
			String expectedRefererStartsWith = httpServletRequest.getRequestURL().substring(0, requestUrl.indexOf('/', 9));
		    String referer = httpServletRequest.getHeader("referer");
		    
		    logger.debug("requestUrl------------------------"+requestUrl);
		    logger.debug("expectedRefererStartsWith------------------------"+expectedRefererStartsWith);
		    logger.debug("referer------------------------"+referer);
		    
	    	if((referer != null && referer.startsWith(expectedRefererStartsWith)) || referer == null) {
	    		dwrRequestDispatch(httpServletRequest, request, response);
		    	chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
	    	}else {
		    	String refererUrl = referer;
		    	if(referer.indexOf('/', 9)>0) {
		    		refererUrl = referer.substring(0, referer.indexOf('/', 9));
		    	}
		    	
		    	logger.debug("refererUrl------------------------"+refererUrl);
		    	
		    	if(pu.getValidRefererHeaderList()!=null && refererUrl!=null && pu.getValidRefererHeaderList().contains(refererUrl)) {
		    		dwrRequestDispatch(httpServletRequest, request, response);
		    		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
		    	}else {
			    	HttpServletResponse httpResp = (HttpServletResponse) response;
			    	httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Validation: REFERER request header does  not start with requestUrl so request was blocked!");
		            return;
		    	}
		    }
	    }else {
	    	dwrRequestDispatch(httpServletRequest, request, response);
	    	chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
	    }
	    
	}

	@Override
	public Object doFilter(Object obj, Method method, Object[] params,
			AjaxFilterChain chain) throws Exception {
		// XSSRequestWrapper wrapper = new XSSRequestWrapper
		if(pu.getXSSFilterEnabled()!=null && pu.getXSSFilterEnabled().equals(NEDSSConstants.YES)){
		try {
			for (Object params1 : params) {
				if (params1 instanceof BatchEntry) {
					for (Entry<String, String> entry : ((BatchEntry) params1)
							.getAnswerMap().entrySet()) {
						((BatchEntry) params1).getAnswerMap().put(
								entry.getKey(), stripXSS(entry.getValue()));
					}
				}
			}
		} catch (Exception ex) {
			logger.debug("Could not strip XSS String");
		}
		}
		return chain.doFilter(obj, method, params);
	}

	private String stripXSS(String value) {

		if (value != null) {
			ArrayList<Pattern> patterns = XSSRequestWrapper.patterns;
			value = value.replaceAll("\0", "");
			// Remove all sections that match a pattern
			for (Pattern scriptPattern : patterns) {
				//logger.info("Value Before XSS Filter Strip - XSSFilter: "
				//		+ value);
				value = scriptPattern.matcher(value).replaceAll("");
				//logger.info("Value After XSS Filter Strip - XSSFilter: "
				//		+ value);
			}
		}
		return value;
	}
	
	private void dwrRequestDispatch(HttpServletRequest httpServletRequest, ServletRequest request, ServletResponse response) throws ServletException, IOException {
		String requestURI = httpServletRequest.getRequestURI();
		if (requestURI != null && requestURI.contains("dwr/interface")) {
			request.getRequestDispatcher(stripXSS(requestURI).replace("/nbs", "")).forward(request, response);
		}
	}
	
}
