package gov.cdc.nedss.util;

import org.owasp.encoder.Encode;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import gov.cdc.nedss.webapp.nbs.action.pam.PamAction;

public class HTMLEncoder {
	static final LogUtils logger = new LogUtils(HTMLEncoder.class.getName());
	public static PropertyUtil pu = PropertyUtil.getInstance();
	
	public static String encodeHtml(String input) {
		if (null == input || "".equals(input))  return input;
		if(NEDSSConstants.YES.equals(pu.getHTMLEncodingEnabled())){
			return Encode.forHtml(String.valueOf(input));
		}else {
			return input;
		}
	}
	
	
	public static String encodeJavaScript(String input) {
		if(NEDSSConstants.YES.equals(pu.getHTMLEncodingEnabled())){
			return Encode.forJavaScript(String.valueOf(input));
		}else {
			return input;
		}
	}
	
	public static String encodeJavaScriptBlock(String input) {
		if(NEDSSConstants.YES.equals(pu.getHTMLEncodingEnabled())){
			return Encode.forJavaScriptBlock(String.valueOf(input));
		}else {
			return input;
		}
	}
	
	public static String encodeHtmlAttribute(String input) {
		if(NEDSSConstants.YES.equals(pu.getHTMLEncodingEnabled())){
			return Encode.forHtmlAttribute(String.valueOf(input));
		}else {
			return input;
		}
	}
	
	public static String encodeUriComponent(String input) {
		if(NEDSSConstants.YES.equals(pu.getHTMLEncodingEnabled())){
			return Encode.forUriComponent(String.valueOf(input));
		}else {
			return input;
		}
	}
	
	public static String encodeHtmlContent(String input) {
		if(NEDSSConstants.YES.equals(pu.getHTMLEncodingEnabled())){
			return Encode.forHtmlContent(String.valueOf(input));
		}else {
			return input;
		}
	}
	
	public static String sanitizeHtml(String html) {
		PolicyFactory policy = new HtmlPolicyBuilder()
				.allowElements("b", "i" ,"u", "br", "html", "head", "title", "body", "select","SCRIPT", "a").allowAttributes("name","id").onElements("select")
				.allowUrlProtocols("javascript").allowAttributes("href").onElements("a")
				.allowAttributes("Type","Language","SRC").onElements("SCRIPT")
				.allowTextIn("SCRIPT")
				.allowAttributes("lang").onElements("html").toFactory();
		return policy.sanitize(html).replace("%28", "(").replace("%29",")").replace("%20", " ").replace("&#39;", "'").replace("&#61;","=").replace("&#34;", "\"").replace("&#64;", "@").replace("&amp;", "&");
	}
	
	public static String sanitizeHtmlForSpecialCharacters(String html) {
		PolicyFactory policy = new HtmlPolicyBuilder()
				.allowElements("b", "i" ,"u", "br", "html", "head", "title", "body", "select","SCRIPT", "a").allowAttributes("name","id").onElements("select")
				.allowUrlProtocols("javascript").allowAttributes("href").onElements("a")
				.allowAttributes("Type","Language","SRC").onElements("SCRIPT")
				.allowTextIn("SCRIPT")
				.allowAttributes("lang").onElements("html").toFactory();
		return policy.sanitize(html).replace("%28", "(").replace("%29",")").replace("%20", " ").replace("&#39;", "'").replace("&#61;","=").replace("&#34;", "\"").replace("&#64;", "@")
				.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">").replace("&quot;","\"").replace("&#35;", "#").replace("&#33;", "!").replace("&#36;", "$").replace("&#42;", "*")
				.replace("&#43;", "+").replace("&#44;", ",").replace("&#45;", "-").replace("&#46;", ".").replace("&#46;", "/").replace("&#58;", ":").replace("&#59;", ";").replace("&#63;", "?")
				.replace("&#37;", "%").replace("&#123;", "{").replace("&#125;", "}").replace("&#124;", "|").replace("&#126;", "~");
	}
		
}
