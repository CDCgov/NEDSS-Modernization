package gov.cdc.nedss.webapp.nbs.util.taglib;

import gov.cdc.nedss.util.LogUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import de.nava.informa.core.ChannelIF;
import de.nava.informa.core.ItemIF;
import de.nava.informa.core.ParseException;
import de.nava.informa.core.UnsupportedFormatException;
import de.nava.informa.impl.basic.ChannelBuilder;
import de.nava.informa.parsers.FeedParser;

public class SimpleRssFeedTag extends TagSupport
{
	private String uri;
	static final LogUtils logger = new LogUtils(SimpleRssFeedTag.class.getName());
	private Exception exception;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int doEndTag() throws JspException
	{
		boolean error = false;
		try {
			JspWriter out = pageContext.getOut();
			URL url = null;
			
			try {
				url = new URL(getUri());
				ChannelIF channel = FeedParser.parse(new ChannelBuilder(), url);
				out.print("<b>" + channel.getTitle() + "</b> <br />");
				
				if (channel.getItems().size() > 0) {
					out.println("<ul style=\"margin-left:0.75em;\">");
					for (Iterator iter = channel.getItems().iterator(); iter.hasNext();)
					{
						ItemIF item = (ItemIF) iter.next();
						out.print("<li style=\"list-style:circle; margin-bottom:6px;\"> <a target=\"_blank\" href=\"" + 
								item.getLink() + "\">" + item.getTitle() + "</a> </li>");
					}
					out.println("</ul>");
				}
			} catch (MalformedURLException mue) {
				error = true;
				exception = mue;
			} catch (UnsupportedFormatException ufe) {
				error = true;
				exception = ufe;
			} catch (ParseException pe) {
				error = true;
				exception = pe;
			} catch (IOException e) {
				error = true;
				exception = e;
			}

			// display the error message if feed could not be read for some unknown reason
			if (error && url != null) {
				out.print("<ul style=\"color:red; margin-left:0.75em;\"> <li style=\"list-style:none; margin-bottom:6px;\">");
				out.println("News feeds from <br/> " + uri + " cannot be loaded at this time. " +
						"Please try again later.");
				out.print("</li> </ul>");
				
				throw exception; 
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("An exception occured while handling the RSS feed." + e);
		}
		
		return EVAL_PAGE;
	}
}