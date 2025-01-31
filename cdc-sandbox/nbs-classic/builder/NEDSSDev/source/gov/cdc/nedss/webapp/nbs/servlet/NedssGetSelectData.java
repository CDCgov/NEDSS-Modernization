package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.StringUtils;

/**
 *  Used for dynamic listboxes.
 *  @author Jay Kim
 */
public class NedssGetSelectData
    extends HttpServlet {

   private static final String CONTENT_TYPE = "text/html";

   public void destroy() {
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws
       ServletException, IOException {
      response.setContentType(CONTENT_TYPE);
      request.setCharacterEncoding("UTF-8");
      String sElementName = HTMLEncoder.encodeHtml(request.getParameter("elementName"));
      String sInputCd = request.getParameter("inputCd");
      
      if(sInputCd==null)
    	  sInputCd = request.getParameter("amp;inputCd");
      
      /*
      String fatima = (String)request.getAttribute("inputCd");
      String fatima1 = request.getHeader("inputCd");
      String fatima3="";
      String fatima2=request.getParameter("amp;inputCd");
      String fatima2=request.getParameter("amp;inputCd");*/
      
      /*
	  	Enumeration en=request.getParameterNames();
	  	 
		while(en.hasMoreElements())
		{
			Object objOri=en.nextElement();
			String param=(String)objOri;
			String value=request.getParameter(param);
			fatima3+="Parameter Name is '"+param+"' and Parameter Value is '"+value+"'";
		}	*/	
	
	
		
		
		
      
      
      
      //String sBatchName = request.getParameter("batchName");

      String sCountyOptions = getCountyOptions(StringUtils.escapeSql(sInputCd));

      StringBuffer sbHTML = new StringBuffer("");
      //StringBuffer select = new StringBuffer("");
 /*
      select.append("var sOpts = '<SELECT name=\"").append(sElementName).append(
          "\" id=\"").append(sElementName);

      if (!sBatchName.equals("")) {
         select.append("\" parent=\"").append(sBatchName).append(
             "\" isNested=\"yes");
      }

      select.append("\">';");
*/
      PrintWriter out = response.getWriter();
      sbHTML.append("<html lang=\"en\"><head><title>data getter</title>");

      /////////////////////////////////////////////////////////////////////////
      HttpSession session = request.getSession(true);
      NBSSecurityObj nbsSecurityObject = (NBSSecurityObj) session.getAttribute(
          "NBSSecurityObject");

      if (nbsSecurityObject == null) {
         sbHTML.append(
             "\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">");
         sbHTML.append("\n parent.opener.location=\"/nbs/timeout\";");
         sbHTML.append("window.close();");
         sbHTML.append("</SCRIPT>");
         sbHTML.append("</head>");
         sbHTML.append("<body>");
         sbHTML.append("</body></html>");
         out.println(sbHTML.toString());
         return;
      }


      sbHTML.append(
          "\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">");
      /////////////////////////////////////////////////////////////////////////
      sbHTML.append("  function createDropDown(options, node, parentDoc){");

       sbHTML.append("var optionItems;");
       sbHTML.append("if(options!=null && options!=\"\")");
       sbHTML.append("optionItems = options.split(\"|\");");


        sbHTML.append("   var x=0; var y=0; ");
        /*
        sbHTML.append("   if(node!=null) { ");

        sbHTML.append("           y = node.options.length;   ");
        sbHTML.append(" node.length=0;");
        sbHTML.append("           for(x=y-1; x>0; x--){if(node.options[x]!=null)node.remove(x);}");
        sbHTML.append("   }");
*/
        sbHTML.append("if(optionItems!=null && optionItems.length>0 && node!=null){ ");
        sbHTML.append("var line=\"\";  var nameVal=\"\"; var cd=\"\"; var desc=\"\";var elem;var tnode;");
        sbHTML.append("y = optionItems.length; ");
        sbHTML.append("     for(x=0; x<y; x++){ ");
        sbHTML.append("           if(optionItems[x]!=\"\"){");
        sbHTML.append("                line=optionItems[x];");
        sbHTML.append("                nameVal=line.split(\"$\");	        ");
        sbHTML.append("                cd= nameVal[0];");
        sbHTML.append("                desc= nameVal[1];");
        sbHTML.append("                elem = parentDoc.createElement(\"option\");");
        sbHTML.append("                elem.setAttribute(\"value\", cd);");
        sbHTML.append("                tnode= parentDoc.createTextNode(desc);");
        sbHTML.append("                elem.appendChild(tnode);");
        sbHTML.append("                node.appendChild(elem);");
        sbHTML.append("            }");
        sbHTML.append("      }");
        sbHTML.append("    }       ");

        sbHTML.append("}");




      /////////////////////////////////////////////////////////////////////////


      sbHTML.append("function goBack(){");
      sbHTML.append("var win = window.opener;var doc = win.document;");
      sbHTML.append("var updateNode = doc.getElementById(\"").append(sElementName).append("\");");
      sbHTML.append("if (updateNode==null || updateNode == undefined)");
      sbHTML.append("updateNode = doc.getElementsByName(\"").append(sElementName).append("\")[0];");
      sbHTML.append("var optionList = \"").append(sCountyOptions).append("\";");

      
      
      
      sbHTML.append("createDropDown(optionList,updateNode,doc);");
      /*
      sbHTML.append("var payload = getElementByIdOrByNameNode(\"payload\");",window.document);
      sbHTML.append("var options = payload.getElementsByTagName(\"option\");");
      sbHTML.append(select.toString());
      sbHTML.append("for (var i=0;i<options.length;i++)");
      sbHTML.append("{");
      sbHTML.append("sOpts += '<OPTION VALUE=\"' + options.item(i).value + '\">' + options.item(i).text + '</OPTION>';");
      sbHTML.append("}");
      sbHTML.append("updateNode.outerHTML = sOpts  + \"</SELECT>\";");
      */
      sbHTML.append("window.close();");
      sbHTML.append("}");



      sbHTML.append("</SCRIPT>");
      sbHTML.append("</head>");
      sbHTML.append("<body>");
      /*
      sbHTML.append("<select name=\"payload\" id=\"payload\">").append(
          sCountyOptions).append("</select>");
      */
        sbHTML.append("<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">goBack();</SCRIPT>");

      sbHTML.append("</body></html>");
      out.println(HTMLEncoder.sanitizeHtml(sbHTML.toString()));
   }

   public void init() throws ServletException {
   }





   private String getCountyOptions(String stateCd) {
      StringBuffer sbCountyOptions = new StringBuffer("");
      CachedDropDownValues srtValues = new CachedDropDownValues();
      String sCounties="";
      sCounties = srtValues.getCountiesByStateString(stateCd);

      return sCounties;
   }

}
