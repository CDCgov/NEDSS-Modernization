<%@ page language='java' session='false' import = "
javax.servlet.*,
gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.*,
gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*,
gov.cdc.nedss.ldf.dt.*,
gov.cdc.nedss.util.*,
java.sql.*,
java.util.*,
javax.naming.*,
javax.rmi.*"%>




  <content>
  <tab>
<%
//PropertyUtil PropertyUtil = PropertyUtil.getInstance();
SRTMap srtm = null;
Map map = new TreeMap();
NEDSSConstants Constants = new NEDSSConstants();
if(srtm == null)
        {
          NedssUtils nu = new NedssUtils();
	try
	{

	    Object objref = nu.lookupBean(Constants.SRT_MAP);

	    //logger.debug("objref " + objref);
	    SRTMapHome home = (SRTMapHome)PortableRemoteObject.narrow(objref,
								      SRTMapHome.class);
	    srtm = home.create();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
        }

ArrayList pages = srtm.getLDFPages();
Iterator it = pages.iterator();
System.out.println("Number of pages are:"  + pages.size());
out.println("    <table role="presentation">");


while (it.hasNext())
{

    StateDefinedFieldURLMapDT UrlMapDT = (StateDefinedFieldURLMapDT)it.next();
    System.out.println("page name is: " + UrlMapDT.getPageUrl());
    out.println("<tr><td><a href=\"/nbs/LDFLoad.do?page=" + UrlMapDT.getPageUrl() + "&amp;pageID=" +  UrlMapDT.getPageId() +"\">"+ UrlMapDT.getPageName()+"</a></td></tr>");
    //out.println("<tr><td>the type = " + UrlMapDT.getPageUrl() + "</td></tr>");
    out.println("<tr><td>Page ID = " + UrlMapDT.getPageId() + "</td></tr>");
}



out.println("  </table>");



%>

</tab>
</content>



