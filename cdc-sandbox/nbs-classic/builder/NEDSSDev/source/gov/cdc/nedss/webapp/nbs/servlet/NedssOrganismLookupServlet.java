package gov.cdc.nedss.webapp.nbs.servlet;

/**
 *
 * <p>Title: NedssOrganismLookupServlet</p>
 * <p>Description: This Servlet was initially coded to handle Organism Lookup whenever
 * there is a change in resulted Test name.
 * But for 1.1.3 functionality, this will also capture the other screen behavior
 * changes(Organism Name changes, Numeric Result Value changes, Coded Result Value changes
 * that should change with resulted Test Name change</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper.*;

public class NedssOrganismLookupServlet
    extends HttpServlet {
  static final LogUtils logger = new LogUtils(NedssOrganismLookupServlet.class.getName());
  private static final String CONTENT_TYPE = "text/html";

  /**
   * This is a servlet method. Not implemented.
   * @throws ServletException
   */
  public void init() throws ServletException {
  }

  
  /**
   * getCorrectParameter(): new function to resolve issue with other browsers. The paramname is amp;paramname
   * 
   */
  
  public String getCorrectParameter(HttpServletRequest request, String parameter){
	  
	  String param = request.getParameter(parameter);
	  
	  if(param == null)
		  param=request.getParameter("amp;"+parameter);
	  
	  return param;
	    
  }
  
  
  //Process the HTTP Get request
  /**
   * This is the main method that is called during HTTP get method.
   * @param request : HttpServletRequest
   * @param response : HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Long organizationUID = null;
    String testNameCondition = getCorrectParameter(request,"testNameCondition");
    if (testNameCondition == null) {
      logger.error("NedssOrganismLookupServlet: The testNameCondition is null!");
    }

    String labClia = getCorrectParameter(request,"labClia");
    if (labClia == null) {
      logger.error("NedssOrganismLookupServlet: The labClia is null!");
    }

    String labUid = getCorrectParameter(request,"labUid");
    if (labUid == null || labUid == "" || labClia.equalsIgnoreCase(NEDSSConstants.DEFAULT)) {
      logger.debug("NedssOrganismLookupServlet: The labUid is null!");
    }
    else {
      organizationUID = new Long(labUid);
    }

    if (labClia.equals("")) {
      labClia = getCliaValue(organizationUID.toString(), request.getSession());
    }

    String progAreaCd = getCorrectParameter(request,"progAreaCd");
    if (progAreaCd == null) {
      logger.error("NedssOrganismLookupServlet: The progAreaCd is null!");
    }
    String type = getCorrectParameter(request,"morbtype");
    if (progAreaCd.equalsIgnoreCase(NEDSSConstants.PROGRAM_AREA_NONE) || progAreaCd.equalsIgnoreCase("null")) {
      progAreaCd = null;
    }
    boolean labIndicator = organisnRequiredIndicator(testNameCondition, labClia, organizationUID, progAreaCd, request.getSession());

    response.setContentType(CONTENT_TYPE);

    StringBuffer sbHTML = new StringBuffer("");
    StringBuffer select = new StringBuffer("");

    PrintWriter out = response.getWriter();
    sbHTML.append("<html lang=\"en\"><head><title>Organism Lookup</title>");

    /////////////////////////////////////////////////////////////////////////
    HttpSession session = request.getSession(true);
    NBSSecurityObj nbsSecurityObject = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");

    if (nbsSecurityObject == null) {
      sbHTML.append("\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">");
      sbHTML.append("\n parent.opener.location=\"/nbs/timeout\";");
      sbHTML.append("window.close();");
      sbHTML.append("</SCRIPT>");
      sbHTML.append("</head>");
      sbHTML.append("<body>");
      sbHTML.append("</body></html>");
      out.println(sbHTML.toString());
      return;
    }
    /////////////////////////////////////////////////////////////////////////
    sbHTML.append("\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\" SRC=\"Globals.js\">");
    sbHTML.append("\n</SCRIPT>");
    sbHTML.append("\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">");

    /////////////////////////////////////////////////////////////////////////
      sbHTML.append("  function createDropDown(options, node, parentDoc){");

       sbHTML.append("var optionItems;");
       sbHTML.append("if(options!=null && options!=\"\")");
       sbHTML.append("optionItems = options.split(\"|\");");
       sbHTML.append("node.className=\"none\";");
        sbHTML.append("   var x=0; var y=0; ");
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



    sbHTML.append("\nfunction goBack(){");
    sbHTML.append("\nvar win = window.opener;\n var document = win.document;");

    Map<Object,Object> map = null;
    if(type!= null && type.equalsIgnoreCase("MorbType"))
     { 
       // commented out on Feb 12 map = resultedTestForMorbDropDowns(labClia, testNameCondition, request, response);
        SRTCode srtCode = NedssCodeLookupServlet.getUniqueDTForCodeValueLookup(labClia, NEDSSConstants.RESULTED_TEST_LOOKUP, null, null, testNameCondition, request);
     
        sbHTML.append(resultedTestHiddenValueChangeMorb(srtCode.getDesc(), srtCode.getCode()));
        sbHTML.append("var testName= getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code\", document);");
       
        sbHTML.append("var testNameAttribute = testName.getAttribute(\"name\");");
       
        sbHTML.append("document.getElementsByName(testNameAttribute + \"_textbox\")[0].value=\"\";");
        
        sbHTML.append("testName.value = \"\";");
        sbHTML.append("getElementByIdOrByNameNode(testNameAttribute + \"\", document).value=\"\";");
        sbHTML.append("testName.className= \"none\";");
        sbHTML.append("var TestNameText = getElementByIdOrByNameNode(\"OrganismDisplay\", document);");
        sbHTML.append("TestNameText.className =\"\";");
  

     }
     else
     { 
        // commented out on Feb 12 map = resultedTestDropDowns(labClia, testNameCondition, request, response);
        SRTCode srtCode = NedssCodeLookupServlet.getUniqueDTForCodeValueLookup(labClia, NEDSSConstants.RESULTED_TEST_LOOKUP, null, null, testNameCondition, request);
        sbHTML.append(resultedTestHiddenValueChange(srtCode.getDesc(), srtCode.getCode()));

      }
    if(map!= null)
      sbHTML.append(map.get("javaScript").toString());
    if (labIndicator) {
      sbHTML.append("\nvar codeResultInd = getElementByIdOrByNameNode(\"codedResultIndicator\", document);");
      sbHTML.append("\n codeResultInd.className = \"none\";");

       sbHTML.append("\nvar codedResultIndicatorMessage = getElementByIdOrByNameNode(\"codedResultIndicatorMessage\", document);");
      sbHTML.append("\n codedResultIndicatorMessage.className = \"none\";");

      sbHTML.append("\n  if(getElementByIdOrByNameNode(\"susceptiblityInd\", document))");
      sbHTML.append("\n    { ");
      sbHTML.append("\nvar susceptiblityIndicator = getElementByIdOrByNameNode(\"susceptiblityInd\", document);");
      sbHTML.append("\n susceptiblityIndicator.className = \"visible\";");
      sbHTML.append("\n     }");
      //initialize the ctrl variable
      sbHTML.append("\n     if(getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1\", document))");
      sbHTML.append("\n    { ");
      sbHTML.append("\nvar ctrlCdUserDefined = getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1\", document);");
      sbHTML.append("\n ctrlCdUserDefined.value = \"Y\";");
      sbHTML.append("\n     }");
      //initialize the ctrl variable from MORB
      sbHTML.append("\n     if(getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1\", document))");
      sbHTML.append("\n    { ");
      sbHTML.append("\nvar ctrlCdUserDefined = getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1\", document);");
      sbHTML.append("\n ctrlCdUserDefined.value = \"Y\";");
      sbHTML.append("\n     }");

      sbHTML.append("\n     if(getElementByIdOrByNameNode(\"susceptiblityInd\", document))");
      sbHTML.append("\n    { ");
      sbHTML.append("\nvar susceptiblityIndicator = getElementByIdOrByNameNode(\"susceptiblityInd\", document);");
      sbHTML.append("\n susceptiblityIndicator.className = \"visible\";");
      sbHTML.append("\n     }");

      sbHTML.append("\nvar organismInd = getElementByIdOrByNameNode(\"organismIndicator\", document);");
      sbHTML.append("\n organismInd.className = \"visible\";");
      sbHTML.append("var IsolateTrackInd = getElementByIdOrByNameNode(\"IsolateTrackInd\", document) == null ? \"\" : getElementByIdOrByNameNode(\"IsolateTrackInd\", document);");
      sbHTML.append("IsolateTrackInd.className= \"visible\";");
    
      //don't show the listbox part of type ahead
      if(type!= null && type.equalsIgnoreCase("MorbType"))
      {
        sbHTML.append("\n getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName\", document).className=\"none\";");
      }
      else
      {
        sbHTML.append("\n getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName\", document).className=\"none\";");
      }
      //clear out the code result value
      sbHTML.append("\n     if(getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code\", document))");
      sbHTML.append("\n    { ");
      sbHTML.append("\n getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code\", document).selectedIndex = 0;");
      sbHTML.append("\n     }");

    }
    else {
      sbHTML.append("\n     if(getElementByIdOrByNameNode(\"susceptiblityInd\", document))");
      sbHTML.append("\n    { ");
      sbHTML.append("\nvar susceptiblityIndicator = getElementByIdOrByNameNode(\"susceptiblityInd\", document);");
      sbHTML.append("\n susceptiblityIndicator.className = \"none\";");
      sbHTML.append("\n     }");

      //initialize the ctrl variable
      sbHTML.append("\n     if(getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1\", document))");
      sbHTML.append("\n    { ");
      sbHTML.append("\nvar ctrlCdUserDefined = getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.theObservationDT.ctrlCdUserDefined1\", document);");
      sbHTML.append("\n ctrlCdUserDefined.value = \"N\";");
      sbHTML.append("\n     }");
      //initialize the ctrl variable from MORB
      sbHTML.append("\n     if(getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1\", document))");
      sbHTML.append("\n    { ");
      sbHTML.append("\nvar ctrlCdUserDefined = getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1\", document);");
      sbHTML.append("\n ctrlCdUserDefined.value = \"N\";");
      sbHTML.append("\n     }");

      sbHTML.append("\nvar organismInd = getElementByIdOrByNameNode(\"organismIndicator\", document);");
      sbHTML.append("\n organismInd.className = \"none\";");
      sbHTML.append("\nvar codeResultInd = getElementByIdOrByNameNode(\"codedResultIndicator\", document);");
      sbHTML.append("\n codeResultInd.className = \"visible\";");
      sbHTML.append("var IsolateTrackInd = getElementByIdOrByNameNode(\"IsolateTrackInd\", document) == null ? \"\" : getElementByIdOrByNameNode(\"IsolateTrackInd\", document);");
      sbHTML.append("IsolateTrackInd.className= \"none\";");
    

       sbHTML.append("\nvar codedResultIndicatorMessage = getElementByIdOrByNameNode(\"codedResultIndicatorMessage\", document);");
      sbHTML.append("\n codedResultIndicatorMessage.className = \"visible\";");

      sbHTML.append("\n var organismDisp = getElementByIdOrByNameNode(\"OrganismDisplay\", document);");
      sbHTML.append("\n organismDisp.className = \"none\";");
      sbHTML.append("\n organismDisp.value = \"\";");

      sbHTML.append("\n var hiddenCd = getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].hiddenCd\", document);");
      //sbHTML.append("\n hiddenCd.className = \"none\";");
      sbHTML.append("\n if(hiddenCd != null){ ");
      sbHTML.append("\n hiddenCd.value = \"\";");
      sbHTML.append("\n }");

      sbHTML.append("\n var searchResultRT = getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].searchResultRT\", document);");
      sbHTML.append("\n if(searchResultRT != null){");
      //sbHTML.append("\n searchResultRT.className = \"none\";");
      sbHTML.append("\n searchResultRT.value = \"\";");
      sbHTML.append("\n }");

      sbHTML.append("\n var cdSystemCdRT = getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].cdSystemCdRT\", document);");
      sbHTML.append("\n if(cdSystemCdRT != null){");
      //sbHTML.append("\n cdSystemCdRT.className = \"none\";");
      sbHTML.append("\n cdSystemCdRT.value = \"\";");
      sbHTML.append("\n }");

      // clear out the value in organism name
      if(type!= null && type.equalsIgnoreCase("MorbType"))
      {
        sbHTML.append("\n     if(getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName\", document))");
        sbHTML.append("\n    { ");
        sbHTML.append("\ngetElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName\", document).selectedIndex = 0;");
        sbHTML.append("\n     }");
      }
      else
      {
        sbHTML.append("\n     if(getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName\", document))");
        sbHTML.append("\n    { ");
        sbHTML.append("\ngetElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName\", document).selectedIndex = 0;");
        sbHTML.append("\n     }");

      }
    }
    sbHTML.append("\nwindow.close();");
    sbHTML.append("\n}");
    sbHTML.append("</SCRIPT>");
    sbHTML.append("</head>");
    sbHTML.append("<body>");
    if(map!=null)
      sbHTML.append(map.get("htmlBody").toString());
    sbHTML.append("<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">goBack();</SCRIPT>");
    sbHTML.append("</body></html>");
    //System.out.println("\n\n\n\n@@@@sbHTML to String is :" + sbHTML.toString());
    out.println(sbHTML.toString());

  }

  //Clean up resources
  public void destroy() {
  }

  private boolean organisnRequiredIndicator(String testNameCondition, String labClia, Long labUid, String progAreaCd, HttpSession session) {
    MainSessionCommand msCommand = null;
    boolean labIndicator = false;


    //civil00012111 - BB - the method called from this doesn't use CLIA or the Lab UID, but in the future
    //if it does and the CLIA and lab uid are not available, then the DEFAULT clia should be used
    if ( labUid == null && labClia == null )
      labClia = NEDSSConstants.DEFAULT;

    //civil00012111 - BB - reverse order, check for null labUid first - if it is null, then
    //CLIA cannot be null as last conditional would have caught that and set CLIA to DEFAULT.
    if ( labUid != null || labClia.equalsIgnoreCase(NEDSSConstants.DEFAULT) ) {
      try {
        logger.debug("observationUID inside organisnRequiredIndicator method is: " + labUid);

        String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
        String sMethod = "getOrganismReqdIndicatorForResultedTest";
        Object[] oParams = new Object[] {testNameCondition, labClia, labUid, progAreaCd};
        MainSessionHolder holder = new MainSessionHolder();
        msCommand = holder.getMainSessionCommand(session);

        ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
        labIndicator = ( (Boolean) arr.get(0)).booleanValue();
      }
      catch (Exception ex) {

        if (session == null) {
          logger.debug("Error: no session, please login");

        }
        logger.fatal("getLabResultProxyVO: ", ex);
      }
    }
    else {
      logger.error("NedssOrganismLookupServlet: observationUID is null, and labId is not DEFAULT");
      return labIndicator;
    }
    return labIndicator;
  }

  public String getCliaValue(String uid, HttpSession session) {
    Long orgUIDLong = new Long(uid);
    MainSessionCommand msCommand = null;
    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
    String sMethod = "organizationCLIALookup";
    Object[] oParams = new Object[] {orgUIDLong};

    String CliaNumber = null;
    try {
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      CliaNumber = (String) arr.get(0);
    }
    catch (Exception ex) {
      logger.error("There was some error in getting clia number from the database");
    }
    return CliaNumber;

  }

  /**
   * This method is used to get the Speciment Site and Speciment Source data from the
   * Backend whenever the Resulted Test changes for Lab. Presently not being used.
   * @param labId : String
   * @param resulted : String
   * @param request : HttpServletRequest
   * @param response : HttpServletResponse
   * @return Map
   * @throws IOException
   */
  private Map<Object,Object> resultedTestDropDowns(String labId, String resulted, HttpServletRequest request, HttpServletResponse response) throws IOException {
    StringBuffer sbHTML = new StringBuffer("");
    StringBuffer sbHTMLBody = new StringBuffer();
    boolean isLAB113TO117Visible = false;
    //StringBuffer select = new StringBuffer("");
    Collection<Object>  coll = getDisplayableElements(labId, resulted, request);
     Iterator<Object>  it = coll.iterator();
      while(it.hasNext())
      {
        SRTDisplayCodeDT displayDT = (SRTDisplayCodeDT)it.next();
        //System.out.println("code is :" +displayDT.getCode());
        //System.out.println("desc is :" +displayDT.getDesc());
        //System.out.println("displayCd is :" +displayDT.getDisplayCd());

        if(displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB208))
        {
          sbHTML.append("var textResult = getElementByIdOrByNameNode(\"textResult\", document);");
          sbHTML.append("textResult.className= \"visible\";");

        }
        if(displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB119)
           ||displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB120))
        {
          sbHTML.append("var referenceRange = getElementByIdOrByNameNode(\"referenceRange\", document);");
          sbHTML.append("referenceRange.className= \"visible\";");

        }
        if(displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB222))
        {
          sbHTML.append("var orderedTestNameInd = getElementByIdOrByNameNode(\"organismIndicator\", document);");
          sbHTML.append("orderedTestNameInd.className= \"visible\";");
          sbHTML.append("var IsolateTrackInd = getElementByIdOrByNameNode(\"IsolateTrackInd\", document) == null ? \"\" : getElementByIdOrByNameNode(\"IsolateTrackInd\", document);");
          sbHTML.append("IsolateTrackInd.className= \"visible\";");
        

        }
        if(displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB105))
        {
          sbHTML.append("var orderedTestNameInd = getElementByIdOrByNameNode(\"organismIndicator\", document);");
          sbHTML.append("orderedTestNameInd.className= \"visible\";");
          sbHTML.append("var IsolateTrackInd = getElementByIdOrByNameNode(\"IsolateTrackInd\", document) == null ? \"\" : getElementByIdOrByNameNode(\"IsolateTrackInd\", document);");
          sbHTML.append("IsolateTrackInd.className= \"visible\";");
        

        }
        if(displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB110))
        {
          sbHTML.append("var drugName = getElementByIdOrByNameNode(\"drugName\", document);");
          sbHTML.append("drugName.className= \"visible\";");
          //don't show the listbox part of type ahead

          sbHTML.append("\n getElementByIdOrByNameNode(\"resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt\", document).className=\"none\";");


        }
        if(displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB118))
        {
          sbHTML.append("var interpretiveFlag = getElementByIdOrByNameNode(\"interpretiveFlag\", document);");
          sbHTML.append("interpretiveFlag.className= \"visible\";");

        }
         String numericResultValues = "";
         String codedResultValues = "";
         String organismValues = "";
        //NedssCodeLookupServlet codeLookupServlet = new NedssCodeLookupServlet();
         numericResultValues = NedssCodeLookupServlet.cachedValues(labId, NEDSSConstants.NUMERIC_RESULT_LOOKUP, null, null, null, resulted,  request);
         codedResultValues = NedssCodeLookupServlet.cachedValues(labId, NEDSSConstants.CODED_RESULT_LOOKUP, null, null, null, resulted, request);
         organismValues = NedssCodeLookupServlet.cachedValues(labId, NEDSSConstants.ORGANISM_LOOKUP, null, null, null,resulted,  request);
        //System.out.println("The code is :"+srtCode.getCode());
        //System.out.println("The value is :"+srtCode.getDesc());

        ////////////////Organism Name changes are handled here//////////////////////////
        if(displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB278))
        {
           sbHTML.append("var orderedTestNameInd = getElementByIdOrByNameNode(\"organismIndicator\", document);");
           sbHTML.append("orderedTestNameInd.className= \"visible\";");
           sbHTML.append("var IsolateTrackInd = getElementByIdOrByNameNode(\"IsolateTrackInd\", document) == null ? \"\" : getElementByIdOrByNameNode(\"IsolateTrackInd\", document);");
           sbHTML.append("IsolateTrackInd.className= \"visible\";");
          //sbHTML.append("var sOptsOrg = '<SELECT id=\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName");
          //sbHTML.append("\">';");
           sbHTML.append("var sOptsOrg = '<SELECT entitysearch=\"yes\" id=\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName\" name=\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName\" header=\"Result Value\" parent=\"Test Result\" isNested=\"yes\" validate=\"required\" fieldLabel=\"Organism Name\"");
           sbHTML.append("\">';");
           sbHTML.append("\nvar updateNodeOrg = getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].displayName").append("\", document);");
           sbHTML.append("\nvar payloadOrg = getElementByIdOrByNameNode(\"payloadOrg\", window);");
           sbHTML.append("\nvar optionOrg = payloadOrg.getElementsByTagName(\"option\");");
          //sbHTML.append("\nvar optionsNRSV = payLoadCRV.getElementsByTagName(\"option\");");
          //sbHTML.append(select.toString());

          /////////////////////////////////
           sbHTML.append("createDropDown(\"").append(organismValues).append("\",updateNodeOrg,document);");
           sbHTMLBody.append("<select name=\"payloadOrg\" id=\"payloadOrg\">").append(organismValues).append("</select>");
          /////////////////////////////////

          /*
              sbHTML.append("\nfor (var i=0;i<optionOrg.length;i++)");
               sbHTML.append("\n{");
               sbHTML.append("\nsOptsOrg += '<OPTION VALUE=\"' + optionOrg.item(i).value + '\">' + optionOrg.item(i).text + '</OPTION>';");
               sbHTML.append("\n\n}");
               sbHTML.append("\nupdateNodeOrg.outerHTML = sOptsOrg  + \"</SELECT>\";");
           */
        }
        ////////////////Numeric Result Value changes are handled here//////////////////////////
        if((displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB114) ||
            displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB115) ||
            displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB116) ||
            displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB117) ||
            displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB118)) &&
           !isLAB113TO117Visible )
        {
           isLAB113TO117Visible = true;
           sbHTML.append("var numericResult = getElementByIdOrByNameNode(\"numericResult\", document);");
           sbHTML.append("numericResult.className= \"visible\";");
          //sbHTML.append("var sOptsNRSV = '<SELECT id=\"resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd");
          //sbHTML.append("\">';");
           sbHTML.append("var sOptsNRSV = '<SELECT partner=\"yes\" id=\"resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd\" name=\"resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd\" header=\"Result Value\" parent=\"Test Result\" isNested=\"yes\"");
           sbHTML.append("\">';");

          //sbHTML.append("\nvar updateNodeNRSV = getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd").append("\", document);");
           sbHTML.append("\nvar updateNodeNRSV = getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueNumericDT_s[0].numericUnitCd").append("\", document);");
           sbHTML.append("\nvar payloadNRSV = getElementByIdOrByNameNode(\"payloadNRSV\", window);");
           sbHTML.append("\nvar optionNRSV = payloadNRSV.getElementsByTagName(\"option\");");
          //sbHTML.append("\nvar optionsNRSV = payLoadCRV.getElementsByTagName(\"option\");");
          //sbHTML.append(select.toString());
          /////////////////////////////////
           sbHTML.append("createDropDown(\"").append(numericResultValues).append("\",updateNodeNRSV,document);");
           sbHTMLBody.append("<select name=\"payloadNRSV\" id=\"payloadNRSV\">").append(numericResultValues).append("</select>");
          /////////////////////////////////
          /*
               sbHTML.append("\nfor (var i=0;i<optionNRSV.length;i++)");
               sbHTML.append("\n{");
               sbHTML.append("\nsOptsNRSV += '<OPTION VALUE=\"' + optionNRSV.item(i).value + '\">' + optionNRSV.item(i).text + '</OPTION>';");
               sbHTML.append("\n\n}");
               sbHTML.append("\nupdateNodeNRSV.outerHTML = sOptsNRSV  + \"</SELECT>\";");
           */
        }
        ////////////////Coded Result Value changes are handled here//////////////////////////
        if(displayDT.getCode().equalsIgnoreCase(NEDSSConstants.LAB121))
        {

          //String codedResultVal = "\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code\" header=\"Result Value\" parent=\"Test Result\" isNested=\"yes\" fieldLabel=\"Coded Result Value\";";
           sbHTML.append("var codedResultIndicator = getElementByIdOrByNameNode(\"codedResultIndicator\", document);");
           sbHTML.append("codedResultIndicator.className= \"visible\";");


           sbHTML.append("var codedResultIndicatorMessage = getElementByIdOrByNameNode(\"codedResultIndicatorMessage\", document);");
           sbHTML.append("codedResultIndicatorMessage.className= \"visible\";");

           sbHTML.append("var sOptionsCRV = '<SELECT id=\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code\" name=\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code\" header=\"Result Value\" parent=\"Test Result\" isNested=\"yes\" fieldLabel=\"Coded Result Value\"");
           sbHTML.append("\">';");

          //sbHTML.append("var sOptionsCRV = '<SELECT id=\"").append("resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code").append("\" name=").append(codedResultVal);
          //sbHTML.append("\">';");

           sbHTML.append("\nvar updateNodeCRV = getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.obsValueCodedDT_s[0].code\"").append(", document);");
           sbHTML.append("\nvar payLoadCRV = getElementByIdOrByNameNode(\"payLoadCRV\", window);");
           sbHTML.append("\nvar optionsCRV = payLoadCRV.getElementsByTagName(\"option\");");
          /////////////////////////////////
           sbHTML.append("createDropDown(\"").append(codedResultValues).append("\",updateNodeCRV,document);");
           sbHTMLBody.append("<select name=\"payLoadCRV\" id=\"payLoadCRV\">").append(codedResultValues).append("</select>");
          /////////////////////////////////
          /*
               sbHTML.append("\nfor (var i=0;i<optionsCRV.length;i++)");
               sbHTML.append("\n{");
               sbHTML.append("\nsOptionsCRV += '<OPTION VALUE=\"' + optionsCRV.item(i).value + '\">' + optionsCRV.item(i).text + '</OPTION>';");
               sbHTML.append("\n\n}");
               sbHTML.append("\nupdateNodeCRV.outerHTML = sOptionsCRV  + \"</SELECT>\";");
           */
        }
      }
      ////////////////DrugName changes are handled here//////////////////////////
   String numericResultValues = "";
      numericResultValues = NedssCodeLookupServlet.cachedValues(labId, NEDSSConstants.DRUG_LOOKUP, null, null, null, resulted,  request);
      /*
      sbHTML.append("var sOptsDrug = '<select entitysearch=\"yes\" id=\"resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt\" name=\"resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt\" header=\"Drug Name\" size=\"5\" class=\"none\" onclick=\"AutocompleteComboBox(\\\'resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt_textbox\\\',\\\'resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt\\\',true,true);\" onkeyup=\"AutocompleteComboBox(\\\'resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt_textbox\\\',\\\'resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt\\\',true,true);\" onblur=\"this.className=\\\'none\\\';\" typeahead=\"true\" parent=\"Susceptibility\" isNested=\"yes\" validate=\"required\" fieldLabel=\"Drug Name\" errorCode=\"ERR001\"");
        sbHTML.append("\">';");
        sbHTML.append("\nvar updateNodeDrug = getElementByIdOrByNameNode(\"resultedTest[i].susceptibility[j].theObservationDT.cdDescTxt").append("\", document);");
        sbHTML.append("\nvar payloadDrug = getElementByIdOrByNameNode(\"payloadDrug\", window);");
        sbHTML.append("\nvar optionDrug = payloadDrug.getElementsByTagName(\"option\");");
*/
        /////////////////////////////////
   //    sbHTML.append("createDropDown(\"").append(numericResultValues).append("\",updateNodeDrug,document);");
   //    sbHTMLBody.append("<select name=\"payloadDrug\" id=\"payloadDrug\">").append(numericResultValues).append("</select>");
        /////////////////////////////////
        SRTCode srtCode = NedssCodeLookupServlet.getUniqueDTForCodeValueLookup(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, null, null, resulted, request);
        sbHTML.append(resultedTestHiddenValueChange(srtCode.getDesc(), srtCode.getCode()));

        Map<Object,Object> map = new HashMap<Object,Object>();
      map.put("javaScript", sbHTML.toString());
      map.put("htmlBody", sbHTMLBody.toString());

      //System.out.println("\n\n\n\n@@@@sbHTML to progAreaDropDowns String is :" + sbHTML.toString());
      //System.out.println("\n\n\n\n@@@@sbHTMLBody to progAreaDropDowns String is :" + sbHTMLBody.toString());
      return map;
    }

  /**
   * This method is used to get the Speciment Site and Speciment Source data from the
   * Backend whenever the Resulted Test changes for Morb. Presently not being used
   * @param labId : String
   * @param resulted : String
   * @param request : HttpServletRequest
   * @param response : HttpServletResponse
   * @return Map
   * @throws IOException
   */
  private Map<Object,Object> resultedTestForMorbDropDowns(String labId, String resulted, HttpServletRequest request, HttpServletResponse response) throws IOException {
    StringBuffer sbHTML = new StringBuffer("");
    //StringBuffer select = new StringBuffer("");

     String numericResultValues = "";
     String codedResultValues = "";
     String organismValues = "";
    //NedssCodeLookupServlet codeLookupServlet = new NedssCodeLookupServlet();
     numericResultValues = NedssCodeLookupServlet.cachedValues(labId, NEDSSConstants.NUMERIC_RESULT_LOOKUP, null, null, null, resulted,  request);
     codedResultValues = NedssCodeLookupServlet.cachedValues(labId, NEDSSConstants.CODED_RESULT_LOOKUP, null, null, null, resulted, request);
     organismValues = NedssCodeLookupServlet.cachedValues(labId, NEDSSConstants.ORGANISM_LOOKUP, null, null, null,resulted,  request);
    SRTCode srtCode = NedssCodeLookupServlet.getUniqueDTForCodeValueLookup(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, null, null, resulted, request);
    ////////////////Organism Name changes are handled here//////////////////////////
     sbHTML.append("var sOptsOrg = '<SELECT entitysearch=\"yes\" id=\"labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName\" name=\"labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName\" header=\"Result Value(s)\" parent=\"Lab Report\" isNested=\"yes\" fieldLabel=\"Organism Name\"");
     sbHTML.append("\">';");
     sbHTML.append("\nvar updateNodeOrg = getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName").append("\", document);");
    sbHTML.append(resultedTestHiddenValueChangeMorb(srtCode.getDesc(), srtCode.getCode()));
     sbHTML.append("\nvar payloadOrg = getElementByIdOrByNameNode(\"payloadOrg\", window);");
     sbHTML.append("\nvar optionOrg = payloadOrg.getElementsByTagName(\"option\");");
    //sbHTML.append("\nvar optionsNRSV = payLoadCRV.getElementsByTagName(\"option\");");
    //sbHTML.append(select.toString());

    /////////////////////////////////
     sbHTML.append("createDropDown(\"").append(organismValues).append("\",updateNodeOrg,document);");
    /////////////////////////////////
/*
    sbHTML.append("\nfor (var i=0;i<optionOrg.length;i++)");
    sbHTML.append("\n{");
    sbHTML.append("\nsOptsOrg += '<OPTION VALUE=\"' + optionOrg.item(i).value + '\">' + optionOrg.item(i).text + '</OPTION>';");
    sbHTML.append("\n\n}");
    sbHTML.append("\nupdateNodeOrg.outerHTML = sOptsOrg  + \"</SELECT>\";");
*/
    ////////////////Numeric Result Value changes are handled here//////////////////////////
     sbHTML.append("var sOptsNRSV = '<SELECT  partner=\"yes\" id=\"labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd\" name=\"labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd\" header=\"Result Value(s)\" parent=\"Lab Report\" isNested=\"yes\"");
     sbHTML.append("\">';");
     sbHTML.append("\nvar updateNodeNRSV = getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd").append("\", document);");
    //sbHTML.append("\nvar updateNodeNRSV = getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd").append("\", document);");
     sbHTML.append("\nvar payloadNRSV = getElementByIdOrByNameNode(\"payloadNRSV\", window);");
     sbHTML.append("\nvar optionNRSV = payloadNRSV.getElementsByTagName(\"option\");");
    //sbHTML.append("\nvar optionsNRSV = payLoadCRV.getElementsByTagName(\"option\");");
    //sbHTML.append(select.toString());
    /////////////////////////////////
     sbHTML.append("createDropDown(\"").append(numericResultValues).append("\",updateNodeNRSV,document);");
    /////////////////////////////////
/*
    sbHTML.append("\nfor (var i=0;i<optionNRSV.length;i++)");
    sbHTML.append("\n{");
    sbHTML.append("\nsOptsNRSV += '<OPTION VALUE=\"' + optionNRSV.item(i).value + '\">' + optionNRSV.item(i).text + '</OPTION>';");
    sbHTML.append("\n\n}");
    sbHTML.append("\nupdateNodeNRSV.outerHTML = sOptsNRSV  + \"</SELECT>\";");
*/
    ////////////////Coded Result Value changes are handled here//////////////////////////
     sbHTML.append("var sOptionsCRV = '<SELECT id=\"labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code\" name=\"labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code\" header=\"Result Value(s)\" parent=\"Lab Report\" isNested=\"yes\" fieldLabel=\"Coded Result Value\"");
     sbHTML.append("\">';");
     sbHTML.append("\nvar updateNodeCRV = getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code\"").append(", document);");
    //sbHTML.append("\nvar updateNodeCRV = getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd\"", document).append(");");
     sbHTML.append("\nvar payLoadCRV = getElementByIdOrByNameNode(\"payLoadCRV\", window);");
     sbHTML.append("\nvar optionsCRV = payLoadCRV.getElementsByTagName(\"option\");");
    /////////////////////////////////
     sbHTML.append("createDropDown(\"").append(codedResultValues).append("\",updateNodeCRV,document);");
    /////////////////////////////////
/*
    sbHTML.append("\nfor (var i=0;i<optionsCRV.length;i++)");
    sbHTML.append("\n{");
    sbHTML.append("\nsOptionsCRV += '<OPTION VALUE=\"' + optionsCRV.item(i).value + '\">' + optionsCRV.item(i).text + '</OPTION>';");
    sbHTML.append("\n\n}");
    sbHTML.append("\nupdateNodeCRV.outerHTML = sOptionsCRV  + \"</SELECT>\";");
    */
    StringBuffer sbHTMLBody = new StringBuffer();

     sbHTMLBody.append("<select name=\"payloadOrg\" id=\"payloadOrg\">").append(organismValues).append("</select>");
     sbHTMLBody.append("<select name=\"payloadNRSV\" id=\"payloadNRSV\">").append(numericResultValues).append("</select>");
     sbHTMLBody.append("<select name=\"payLoadCRV\" id=\"payLoadCRV\">").append(codedResultValues).append("</select>");
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("javaScript", sbHTML.toString());
    map.put("htmlBody", sbHTMLBody.toString());

    // System.out.println("\n\n\n\n@@@@sbHTML to progAreaDropDowns String is :" + sbHTML.toString());
    //System.out.println("\n\n\n\n@@@@sbHTMLBody to progAreaDropDowns String is :" + sbHTMLBody.toString());
    return map;
  }

  /**
   * This method populates hidden values for Lab
   * @param value : String
   * @param key : String
   * @return String
   */
  private String resultedTestHiddenValueChange(String value, String key){
    String text = "getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.theObservationDT.searchResultRT\", document).value = \"" + value +"\";" +
    "getElementByIdOrByNameNode(\"resultedTest[i].theIsolateVO.theObservationDT.hiddenCd\", document).value = \"" + key +"\";";

     return text;
   }

   /**
    * This method populates hidden values for Morb
    * @param value : String
    * @param key : String
    * @return String
    */
   private String resultedTestHiddenValueChangeMorb(String value, String key){
     String text = "getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].theObservationDT.searchResultRT\", document).value = \"" + value +"\";" +
     "getElementByIdOrByNameNode(\"labResults_s[i].observationVO_s[1].theObservationDT.hiddenCd\", document).value = \"" + key +"\";";

      return text;
    }


    /**
     * Gets the displayble elements
     * @param labId : String
     * @param resultedTestCode : String
     * @param request : HttpServletRequest object
     * @return Collection
     */
    private Collection<Object>  getDisplayableElements(String labId, String resultedTestCode, HttpServletRequest request){
      Map<String,String> map = new HashMap<String,String>();
      map.put(SRTFilterKeys.REPORTING_FACILITY_ID, labId);
      map.put(SRTFilterKeys.RESULTED_TEST_CODE, resultedTestCode);

      Collection<Object>  coll = NedssCodeLookupServlet.getObjectListFromProxy(map, NEDSSConstants.NBS_ELEMENTSUID_LOOKUP, request);
     Iterator<Object>  it = coll.iterator();
      while(it.hasNext())
      {
        SRTDisplayCodeDT displayDT = (SRTDisplayCodeDT)it.next();
        //System.out.println("code is :" +displayDT.getCode());
        //System.out.println("desc is :" +displayDT.getDesc());
        //System.out.println("displayCd is :" +displayDT.getDisplayCd());


      }
      return coll;



    }

}
