package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.util.DisplayOrganizationList;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationSrchResultVO;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PatientSearchResultsVO;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
import gov.cdc.nedss.entity.person.vo.ProviderSrchResultVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.SRTCacheManager;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.SRTCacheManagerHome;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTCode;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper.SRTFilterKeys;
import gov.cdc.nedss.systemservice.exception.SRTCacheManagerException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>Title: NedssCodeLookupServlet</p>
 * <p>Description: NedssCodeLookup servlet serves the purpose of dynamically populating
 * resulted Test Names, Ordered Test Names, Speciment Source, and Speciment Site
 * based on certain predefined rules</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:CSC </p>
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

public class NedssCodeLookupServlet
    extends HttpServlet {
  static final LogUtils logger = new LogUtils(NedssCodeLookupServlet.class.getName());
   static final PropertyUtil propUtil = PropertyUtil.getInstance();
 // private static final String  CONTENT_TYPE = null;

  /**
   * servlet defined method, hence no implemetations.
   */
  public void destroy() {
  }

  /**
   * getCorrectParameter(): new function to resolve issue with other browsers. The paramname is amp;paramname
   * 
   */
  
  public String getCorrectParameter(HttpServletRequest request, String parameter){
	  String param =request.getParameter(parameter);
	  if(param == null)  param=request.getParameter("amp;"+parameter);
	  return param;
	    
  }
  
  /**
   * doGet method is the entry method of the servlet and this method decides whether
   * to handle the state itself or delegate the responsibility to other methods.
   * Hence this is the method that decides the Dynamic population of resulted Test names and
   * Ordered Test Names.
   * @param request : HttpServletRequest object
   * @param response : HttpServletResponse object
   * @throws ServletException
   * @throws IOException
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String CONTENT_TYPE = "";
    CONTENT_TYPE = "text/html;charset=UTF-8";

    response.setContentType(CONTENT_TYPE);
    String target = HTMLEncoder.encodeHtml(getCorrectParameter(request,"target"));
    String targetOrdered =HTMLEncoder.encodeHtml(getCorrectParameter(request,"targetOrdered"));
    String targetResulted =HTMLEncoder.encodeHtml(getCorrectParameter(request,"targetResulted"));
    String type =HTMLEncoder.encodeHtml(getCorrectParameter(request,"type"));
    String code =StringUtils.escapeSql(getCorrectParameter(request,"code"));
    String reportUID =HTMLEncoder.encodeHtml(getCorrectParameter(request,"reportUID"));
    //String button = request.getParameter("button");
    String uid = StringUtils.escapeSql(getCorrectParameter(request,"UID"));
    String programAreaCode =HTMLEncoder.encodeHtml(getCorrectParameter(request,"programAreaCode"));
    String conditionCode =HTMLEncoder.encodeHtml(getCorrectParameter(request,"conditionCode"));
    String dropdownCheckerParam =HTMLEncoder.encodeHtml(getCorrectParameter(request,"dropdownChecker"));
    String result = "";
    boolean dropdownChecker = true;
    String uidValue = "";
    String CliaWithConditions = "";
    String sLabOptionsResulted = "";
    String sLabOptionsOrdered = "";
    String resultForSpecialCondition = "";

    if ( (reportUID != null && reportUID.trim().equalsIgnoreCase("")) && (programAreaCode != null && programAreaCode.trim().equalsIgnoreCase(""))) {
      dropdownChecker = false;
    }

    if (dropdownCheckerParam != null && dropdownCheckerParam.equals("false")) {
      dropdownChecker = false;
    }
    //////////////////while program area changes ///////////////////////////////
    if ( (type.indexOf("ProgramAreaCode") != -1)) {
      progAreaCodeDropDowns(code, uid, targetResulted, targetOrdered, target, request, response);
    }
    //////////////////while ordered test changes////////////////////////////////
    else if (type.indexOf("OrderedTestChange") != -1) {
      //orderedTestChanges(code, uid, targetResulted, target, request, response);
    }
    //////////entity search route///////////////////////////////////////////////
    else if (type.indexOf("entitySearchRoute") != -1) {
      try {
        String cliaNumber = "";
        if (target.indexOf("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt") > -1) {
          cliaNumber = NEDSSConstants.DEFAULT;
        }
        else {
          cliaNumber = getCliaValue(uid, request.getSession());
        }
        if (cliaNumber == null || cliaNumber == "") {
          cliaNumber = NEDSSConstants.DEFAULT;
        }
        entitySearchRoute(cliaNumber, targetResulted, targetOrdered, target, conditionCode, programAreaCode, dropdownChecker, request, response);
      }
      catch (Exception ex1) {
        logger.error("Exception raised while population values via entitySearchRoute");
      }
    }
    ///////////////handle the case under every other condition//////////////////
    else {
      try {
        if ( (type.indexOf("entity-table-Org") != -1)) {
          HashMap<Object,Object> map = (HashMap<Object,Object>) getOrgValue(code, request.getSession());
          String uidString = "";
          if (map.get("UID") != null) {
            uidString = map.get("UID").toString();
            uidValue = HTMLEncoder.encodeHtml(( (Long) map.get("UID")).toString());
            if (target.indexOf("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt") > -1) {
              CliaWithConditions = NEDSSConstants.DEFAULT;
            }
            else {
              CliaWithConditions = getCliaValue(uidString, request.getSession());
            }
            if (CliaWithConditions == null) {
              CliaWithConditions = NEDSSConstants.DEFAULT;
            }
          }
          result = map.get("result").toString();
          resultForSpecialCondition = map.get("result").toString();
        }
        if (type.indexOf("entity-table-Prov") != -1) {
          HashMap<Object,Object> map = (HashMap<Object,Object>) getPersonValue(type, code, request.getSession());
          if (map.get("UID") != null) {
            uidValue = HTMLEncoder.encodeHtml(( (Long) map.get("UID")).toString());
          }
          result = map.get("result").toString();
        }
        if ( (result.length() == 0) || (result.trim().length() == 0)) {
          result = "<font color=red><b>Code Lookup of " + code +
                   " does not match any found in the system. Please modify the entry and try again, or use the Search function to find the data you need.</b></font>";
          dropdownChecker = false;
        }

      }
      catch (Exception ex) {
        logger.error("there was error in getting values from findOrganization or findProvider:" + ex);
      }

      StringBuffer sbHTML = new StringBuffer("");
      StringBuffer select = new StringBuffer("");
      StringBuffer selectOptions = new StringBuffer("");
      if (dropdownChecker) {
        select.append("var sOpts = '<SELECT name=\"").append(target).append("\" id=\"").append(target);
        select.append("\">';");
        selectOptions.append("var sOptions = '<SELECT id=\"").append(targetOrdered).append( "\" name=\"").append(targetOrdered).append("\" onchange=\"getOrderedTestChange()\"; entitysearch=\"yes");
        selectOptions.append("\">';");
      }

      PrintWriter out = response.getWriter();
      sbHTML.append("<html lang=\"en\"><head><title>Code Lookup</title>");

      // logger.debug("selectOptions is :" + selectOptions.toString());
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

      sbHTML.append("\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\" SRC=\"\\nbs\\resource\\javascript\\validate.js\">");
      
      sbHTML.append("\n</SCRIPT>");
      
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
      sbHTML.append("\nvar win = window.opener;\nvar doc = win.document;");
      //Map map = null;
      if ( (type.indexOf("entity-table-Org-ReportingOrganizationUID") != -1)) {
        sbHTML.append("\nvar reportingSourceDetail = getElementByIdOrByNameNode(\"Org-ReportingOrganizationDetails\", doc);");
        sbHTML.append("\n if(reportingSourceDetail!= null)");
        sbHTML.append("\n { \nreportingSourceDetail.value = \"" + result + "\";\n}");
        sbHTML.append("\nvar labID = getElementByIdOrByNameNode(\"labId\", doc);");
        sbHTML.append("\nvar morbType = getElementByIdOrByNameNode(\"morbType\", doc);");
        sbHTML.append("\n     if(morbType!= null)");
        sbHTML.append("\n    { ");
        sbHTML.append("\nlabID.value = \"" + NEDSSConstants.DEFAULT + "\";");
        sbHTML.append("\n     }");
        sbHTML.append("\n     else ");
        sbHTML.append("\n    {");
        sbHTML.append("\nif(labID!=null)");
        sbHTML.append("\nlabID.value = \"" + HTMLEncoder.encodeHtml(CliaWithConditions) + "\";");
        sbHTML.append("\n     }");

        if (dropdownChecker) {
          sLabOptionsResulted = HTMLEncoder.encodeHtml(cachedValues(CliaWithConditions, NEDSSConstants.RESULTED_TEST_LOOKUP, conditionCode,programAreaCode, null, null, request));

          sbHTML.append("\n var resultedTestNameInd = getElementByIdOrByNameNode(\"resultedTestNameInd\", doc);");
          sbHTML.append("\n if(resultedTestNameInd!=null)resultedTestNameInd.className= \"visible\";");
          sbHTML.append("\nvar updateNode = getElementByIdOrByNameNode(\"").append(targetResulted).append("\", doc);");
          //sbHTML.append("\nvar labID = getElementByIdOrByNameNode(\"labId\", doc);");
          //sbHTML.append("\nlabID.value = \""+CliaWithConditions +"\";");
          sbHTML.append("\nvar payload = getElementByIdOrByNameNode(\"payload\", window.document);");
          sbHTML.append("\nvar options = payload.getElementsByTagName(\"option\");");
          sbHTML.append(select.toString());
          //sLabOptionsResulted = URLEncoder.encode(sLabOptionsResulted);
          //  logger.debug("encoded url for resulted tests is :" + sLabOptionsResulted);
          /////////////////////////////////
          sbHTML.append("createDropDown(\"").append(sLabOptionsResulted).append("\",updateNode,doc);");
          /////////////////////////////////
          /*
          sbHTML.append("\nfor (var i=0;i<options.length;i++)");
          sbHTML.append("\n{");
          sbHTML.append("\nsOpts += '<OPTION VALUE=\"' + options.item(i).value + '\">' + options.item(i).text + '</OPTION>';");
          sbHTML.append("\n\n}");
          sbHTML.append("\nupdateNode.outerHTML = sOpts  + \"</SELECT>\";");
*/
          if (targetOrdered != null && !targetOrdered.equals("")) {
            sLabOptionsOrdered = HTMLEncoder.encodeHtml(cachedValues(CliaWithConditions, NEDSSConstants.ORDERED_TEST_LOOKUP, conditionCode, programAreaCode, null, null, request));
            sbHTML.append("\n var specimenSource = getElementByIdOrByNameNode(\"specimenSource\", doc);");
            sbHTML.append("\n specimenSource.className= \"visible\";");
            sbHTML.append("\n var specimentSite = getElementByIdOrByNameNode(\"specimentSite\", doc);");
            sbHTML.append("\n specimentSite.className= \"visible\";");

            // commented on Feb 12 map = SpecimenChangeOnOT(CliaWithConditions, NEDSSConstants.LAB_TESTCODE_NI, request, response);
           // commented on Feb 12 if(map!=null)
           // commented on Feb 12  sbHTML.append(map.get("javaScript").toString());
            sbHTML.append("\n var orderedTestNameInd = getElementByIdOrByNameNode(\"orderedTestNameInd\", doc);");
            sbHTML.append("\n orderedTestNameInd.className= \"visible\";");

            sbHTML.append("\nvar updateNodeOrdered = getElementByIdOrByNameNode(\"").append(targetOrdered).append("\", doc);");
            sbHTML.append("\nvar payloadOrdered = getElementByIdOrByNameNode(\"payloadOrdered\", window.document);");
            sbHTML.append("\nvar optionsOrdered = payloadOrdered.getElementsByTagName(\"option\");");
            sbHTML.append(selectOptions.toString());

           // sLabOptionsOrdered = URLEncoder.encode(sLabOptionsOrdered);
           // logger.debug("encoded url is :" + sLabOptionsOrdered);
              /////////////////////////////////
            sbHTML.append("createDropDown(\"").append(sLabOptionsOrdered).append("\",updateNodeOrdered,doc);");
            /////////////////////////////////
/*
            sbHTML.append("\nfor (var i=0;i<optionsOrdered.length;i++)");
            sbHTML.append("\n{");
            sbHTML.append("\nsOptions += '<OPTION VALUE=\"' + optionsOrdered.item(i).value + '\">' + optionsOrdered.item(i).text + '</OPTION>';");
            sbHTML.append("\n\n}");
            sbHTML.append("\nupdateNodeOrdered.outerHTML = sOptions  + \"</SELECT>\";");
  */        }
        }
        sbHTML.append("\n var hiddenReportingDetails =  getElementByIdOrByNameNode(\"morbtype\", doc);");
        sbHTML.append("\n if(hiddenReportingDetails == null){");
        sbHTML.append("\n " + iflab277Checked(result, uidValue));
        sbHTML.append("\n }");
      }
      sbHTML.append("\nvar uidHolder = getElementByIdOrByNameNode(" + "\"" + HTMLEncoder.encodeHtml(uid) + "\"" + ", doc);");
      sbHTML.append("\n if(uidHolder!= null){");
      sbHTML.append("\n uidHolder.value = " + "\"" + uidValue + "\"" + ";");
      sbHTML.append("\n }");

      sbHTML.append("\nvar entityTable = getElementByIdOrByNameNode(" + "\"" + type + "\"" + ", doc);");

      sbHTML.append("\nvar spans = entityTable.getElementsByTagName(\"span\");");
      sbHTML.append("\nfor(var i=0;i<spans.length;i++){");

      if ( (type.indexOf("entity-codeLookupText-Org") != -1)) {
        sbHTML.append("\nif(spans.item(i).id==\"entity.completeOrgSearchResult\")");
      }
      else if (type.indexOf("entity-codeLookupText-Prov-entity.reporterUID") != -1) {
        sbHTML.append("\nif(spans.item(i).id==\"entity.reporterPersonSearchResult\")");
      }
      else if (type.indexOf("entity-codeLookupText-Prov") != -1) {
        sbHTML.append("\nif(spans.item(i).id==\"entity.completePersonSearchResult\")");

      }
      sbHTML.append("\n{");
      sbHTML.append("spans.item(i).innerHTML = " + "\"" + result + "\"" + ";");

      sbHTML.append("\n}");
      sbHTML.append("\n}");

      sbHTML.append("window.close();");
      sbHTML.append("\n}");
      sbHTML.append("</SCRIPT>");
      sbHTML.append("</head>");
      sbHTML.append("<body>");
      if ( (type.indexOf("entity-table-Org-ReportingOrganizationUID") != -1)) {
        if (dropdownChecker) {
          sbHTML.append("<select name=\"payload\" id=\"payload\">").append(sLabOptionsResulted).append("</select>");
          if (targetOrdered != null && targetOrdered.trim() != "") {
            sbHTML.append("<select name=\"payloadOrdered\" id=\"payloadOrdered\">").append(sLabOptionsOrdered).append("</select>");
          }
        }
      }
     //commented on Feb 12 if(map!=null)
     //commented on Feb 12   sbHTML.append(map.get("htmlBody").toString());
      sbHTML.append("<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">goBack();</SCRIPT>");
      sbHTML.append("</body></html>");
      // logger.debug("\n\n\n\n@@@@sbHTML to String is :" + sbHTML.toString());
      //String returnValue = URLDecoder.decode(sbHTML.toString());
      //out.println(returnValue);
      out.println(HTMLEncoder.sanitizeHtml(sbHTML.toString()));
    }
  }

  /**
   * Servlet defined method
   * @throws ServletException
   */
  public void init() throws ServletException {
  }

  /**
	 * buildOrganizationResult returna a HashMap<Object,Object> with Organization detail information to show
	 * @param list
	 * @param OrganizationUid
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static Map<Object,Object> buildOrganizationResult(ArrayList<?>  list, String organizationUid, HttpSession session) throws Exception {

		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		StringBuffer result = new StringBuffer("");
		Integer versionCtrlNbr = null;
		if(list != null && list.size() > 0) {
			Iterator<?> iter = list.iterator();
			while(iter.hasNext()) {
				OrganizationSrchResultVO srchResultVO = (OrganizationSrchResultVO) iter.next();
				String orgUid = srchResultVO.getOrganizationUID().toString();
				versionCtrlNbr = srchResultVO.getVersionCtrlNbr();
				if(orgUid.equalsIgnoreCase(organizationUid)) {
					//This is the Selected ResultVO, iterate and display
					if (srchResultVO.getOrganizationNameColl() != null) {
		               Iterator<Object>  orgNameIt = srchResultVO.getOrganizationNameColl().iterator();
		                while (orgNameIt.hasNext()) {
		                	OrganizationNameDT orgName = (OrganizationNameDT) orgNameIt.next();
		                  if (orgName.getNmTxt() != null) {
		                    result.append(orgName.getNmTxt());
		                  }

		                }
		              }
		              if (srchResultVO.getOrganizationLocatorsColl() != null) {
		                if (srchResultVO.getOrganizationLocatorsColl() != null) {
		                 Iterator<Object>  orgLocatorIt = srchResultVO.getOrganizationLocatorsColl().iterator();
		                  while (orgLocatorIt.hasNext()) {
		                	String codeTranslated = CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.WORK_PLACE, "EL_USE_PST_ORG");
		                    EntityLocatorParticipationDT entityLocatorDT = (EntityLocatorParticipationDT) orgLocatorIt.next();
		                    if(entityLocatorDT.getCd()!= null && entityLocatorDT.getCd().equalsIgnoreCase(NEDSSConstants.CODE_O)
		                       && entityLocatorDT.getClassCd()!= null && entityLocatorDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL)
		                       && entityLocatorDT.getUseCd()!= null && entityLocatorDT.getUseCd().equalsIgnoreCase(codeTranslated))

			                      if (entityLocatorDT != null) {
			                        PostalLocatorDT postaLocatorDT = entityLocatorDT.getThePostalLocatorDT();
			                        if (postaLocatorDT != null) {
			                          if (postaLocatorDT.getStreetAddr1() != null) {
			                            result.append("<br>").append(postaLocatorDT.getStreetAddr1());
			                          }
			                          if (postaLocatorDT.getStreetAddr2() != null) {
			                            result.append("<br>").append(postaLocatorDT.getStreetAddr2());
			                          }
			                          if (postaLocatorDT.getCityCd() != null) {
			                            result.append("<br>").append(postaLocatorDT.getCityDescTxt());
			                          }
			                          if (postaLocatorDT.getStateCd() != null) {

			                            result.append(", ").append(getStateDescTxt(postaLocatorDT.getStateCd()));
			                          }
			                          if (postaLocatorDT.getZipCd() != null) {
			                            result.append(" ").append(postaLocatorDT.getZipCd());

			                          }
			                        }
			                      }

		                    if(entityLocatorDT.getUseCd()!= null && entityLocatorDT.getUseCd().trim()!= "")
		                    {
		                      if(entityLocatorDT.getUseCd().equalsIgnoreCase(codeTranslated))
		                      {
		                        TeleLocatorDT telelocatorDT = entityLocatorDT.getTheTeleLocatorDT();
		                        if (telelocatorDT != null) {

									if(telelocatorDT.getEmailAddress() != null) {
										result.append("<br>").append(telelocatorDT.getEmailAddress());
									}
		                          if (telelocatorDT.getPhoneNbrTxt() != null) {
		                            result.append("<br>").append(telelocatorDT.getPhoneNbrTxt());
		                          }
		                          if (telelocatorDT.getExtensionTxt() != null) {
		                            result.append("<b> Ext.</b> ").append(telelocatorDT.getExtensionTxt());
		                          }
		                        }
		                      }
		                    }
		                  }
		                }
		              }
		              /*if(srchResultVO.getOrganizationIdColl()!=null && srchResultVO.getOrganizationIdColl().size()>0){
		            	  Iterator<Object> ite = srchResultVO.getOrganizationIdColl().iterator();
		            	  while(ite.hasNext()){
		            		  EntityIdDT entityId = (EntityIdDT)ite.next();
		            		  result.append("<br><b>").append(entityId.getTypeDescTxt()).append(":</b> ");
		            		  if(entityId.getRootExtensionTxt()!=null && entityId.getRootExtensionTxt().trim().length()>0)
		            			  result.append(entityId.getRootExtensionTxt()).append(" ");
		            		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
		            			  result.append("(").append(entityId.getAssigningAuthorityDescTxt()).append(" ");
		            		  if(entityId.getAssigningAuthorityCd() !=null && entityId.getAssigningAuthorityCd().trim().length()>0)
		            			  result.append("(").append(entityId.getAssigningAuthorityCd()).append(")");
		            		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
		            			  result.append(")");
		            	  }
		              }*/
				}//
			}
		}
		returnMap.put("result", result.toString());
		returnMap.put("versionCtrlNbr", versionCtrlNbr == null ? "" : versionCtrlNbr.toString());
		return returnMap;
	}




  /**
   *
   * @param code : String
   * @param session : HttpSession
   * @return Map
   * @throws Exception
   */
  public static Map<Object,Object> getOrgValue(String code, HttpSession session) throws Exception {
    Map<Object,Object> returnMap = new HashMap<Object,Object>();
    Long organizationUID = null;
    Integer versionCtrlNbr= null;
    //String CliaWithConditions = "";
    StringBuffer result = new StringBuffer("");
    OrganizationSearchVO orgSearchVO = new OrganizationSearchVO();
    orgSearchVO.setRootExtensionTxt(code);
    orgSearchVO.setTypeCd("QEC");
    ArrayList<?> alist = null;
    MainSessionCommand msCommand = null;
    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
    String sMethod = "findOrganization";
    Object[] oParams = new Object[] {orgSearchVO, new Integer(propUtil.getNumberOfRows()), new Integer(0)};
    MainSessionHolder holder = new MainSessionHolder();
    msCommand = holder.getMainSessionCommand(session);
    ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
    alist = (ArrayList<?> ) arr.get(0);
   Iterator<?>  it = alist.iterator();
    if (alist != null) {
      while (it.hasNext()) {
        DisplayOrganizationList displayOrganizationList = (DisplayOrganizationList) it.next();
        if (displayOrganizationList != null) {
          ArrayList<Object> list = displayOrganizationList.getList();
          if (list != null) {
           Iterator<Object>  orgSearchResultIt = list.iterator();
            while (orgSearchResultIt.hasNext()) {
              OrganizationSrchResultVO srchResultVO = (OrganizationSrchResultVO) orgSearchResultIt.next();
              versionCtrlNbr= srchResultVO.getVersionCtrlNbr();
              organizationUID = srchResultVO.getOrganizationUID();
              if (srchResultVO.getOrganizationNameColl() != null) {
               Iterator<Object>  orgNameIt = srchResultVO.getOrganizationNameColl().iterator();
                while (orgNameIt.hasNext()) {
                  OrganizationNameDT orgName = (OrganizationNameDT) orgNameIt.next();
                  result.append(orgName.getNmTxt()==null?"":orgName.getNmTxt());
                }
              }
              result.append("<br>") ;
              if (srchResultVO.getOrganizationLocatorsColl() != null) {
                if (srchResultVO.getOrganizationLocatorsColl() != null) {
                 Iterator<Object>  orgLocatorIt = srchResultVO.getOrganizationLocatorsColl().iterator();
                  while (orgLocatorIt.hasNext()) {
                    EntityLocatorParticipationDT entityLocatorDT = (EntityLocatorParticipationDT) orgLocatorIt.next();
                    String codeTranslated = CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.WORK_PHONE, "EL_USE_PST_ORG");
                    if(entityLocatorDT.getCd()!= null && entityLocatorDT.getCd().equalsIgnoreCase(NEDSSConstants.CODE_O) &&
                     entityLocatorDT.getClassCd()!= null && entityLocatorDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL) &&
                     entityLocatorDT.getCdDescTxt()!= null &&   entityLocatorDT.getCdDescTxt().equalsIgnoreCase(NEDSSConstants.OFFICE) &&
                     entityLocatorDT.getUseCd()!= null && entityLocatorDT.getUseCd().equalsIgnoreCase(codeTranslated))
                    {
                      if (entityLocatorDT != null) {
                        PostalLocatorDT postaLocatorDT = entityLocatorDT.getThePostalLocatorDT();
                        if (postaLocatorDT != null) {
                          if (postaLocatorDT.getStreetAddr1() != null) {
                            result.append(HTMLEncoder.encodeHtml(postaLocatorDT.getStreetAddr1())).append("<br>");
                          }
                          if (postaLocatorDT.getStreetAddr2() != null) {
                            result.append(HTMLEncoder.encodeHtml(postaLocatorDT.getStreetAddr2())).append("<br>");
                          }
                          if (postaLocatorDT.getCityCd() != null) {
                            result.append(HTMLEncoder.encodeHtml(postaLocatorDT.getCityCd())).append(", ");

                          }
                          if (postaLocatorDT.getStateCd() != null) {

                            result.append(HTMLEncoder.encodeHtml(getStateDescTxt(postaLocatorDT.getStateCd())));

                          }
                          if (postaLocatorDT.getZipCd() != null) {
                            result.append(" ").append(HTMLEncoder.encodeHtml(postaLocatorDT.getZipCd())).append(" ");

                          }
                        }

                      }
                    }
                    if(entityLocatorDT!= null && entityLocatorDT.getUseCd()!= null && entityLocatorDT.getUseCd().trim()!= "")
                    {
                    	if(entityLocatorDT.getUseCd().equalsIgnoreCase(codeTranslated))
                        {

                        TeleLocatorDT telelocatorDT = entityLocatorDT.getTheTeleLocatorDT();
                        if (telelocatorDT != null) {
	                          if(telelocatorDT.getEmailAddress() != null) {
	                              result.append("<br>").append(HTMLEncoder.encodeHtml(telelocatorDT.getEmailAddress()));
							   }
	                          if (telelocatorDT.getPhoneNbrTxt() != null) {
	                        	  result.append("<br>").append(HTMLEncoder.encodeHtml(telelocatorDT.getPhoneNbrTxt())).append(" ");
	                          }
	                          if (telelocatorDT.getExtensionTxt() != null && telelocatorDT.getPhoneNbrTxt() != null) {
	                            result.append(" <b>Ext. </b>").append(HTMLEncoder.encodeHtml(telelocatorDT.getExtensionTxt()));
	                          }
                          break;
                        }
                      }
                    }
                  }
                }
              }
              /*if(srchResultVO.getOrganizationIdColl()!=null && srchResultVO.getOrganizationIdColl().size()>0){
            	  Iterator<Object> ite = srchResultVO.getOrganizationIdColl().iterator();
            	  while(ite.hasNext()){
            		  EntityIdDT entityId = (EntityIdDT)ite.next();
            		  result.append("<br><b>").append(entityId.getTypeDescTxt()).append(":</b> ");
            		  if(entityId.getRootExtensionTxt()!=null && entityId.getRootExtensionTxt().trim().length()>0)
            			  result.append(entityId.getRootExtensionTxt()).append(" ");
            		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
            			  result.append("(").append(entityId.getAssigningAuthorityDescTxt()).append(" ");
            		  if(entityId.getAssigningAuthorityCd() !=null && entityId.getAssigningAuthorityCd().trim().length()>0)
            			  result.append("(").append(entityId.getAssigningAuthorityCd()).append(")");
            		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
            			  result.append(")");
            	  }
              }*/
              
            }
          }
        }
      }
    }
    returnMap.put("UID", organizationUID);
    returnMap.put("versionCtrlNbr", versionCtrlNbr == null ? "" : versionCtrlNbr.toString());
	
    returnMap.put("result", result.toString());

    return returnMap;

  }

  /**
   * This method gets the laboratory_id for for given organization uid(uid).
   * @param uid : String value representing the organization uid.
   * @param session : HttpSession
   * @return : String
   */
  public static String getCliaValue(String uid, HttpSession session) {
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
      logger.error("NedssCodeLookupServlet:There was some error in getting clia number from the database"+ ex);
    }
    return CliaNumber;

  }
  	/**
  	 * buildProviderResult returna a HashMap<Object,Object> with provider detail information to show
  	 * @param list
  	 * @param providerUid
  	 * @param session
  	 * @return
  	 * @throws Exception
  	 */
  	public static Map<Object,Object> buildProviderResult(ArrayList<?>  list, String providerUid, HttpSession session) throws Exception {

  		Map<Object,Object> returnMap = new HashMap<Object,Object>();
  		StringBuffer result = new StringBuffer("");
  		String investigatorLocalId = null;
  	    Integer versionCtrlNbr= null;
  		if(list != null && list.size() > 0) {
			Iterator<?> iter = list.iterator();
			while(iter.hasNext()) {
				ProviderSrchResultVO srchResultVO = (ProviderSrchResultVO) iter.next();
				String personUid = srchResultVO.getPersonUID().toString();
				versionCtrlNbr = srchResultVO.getVersionCtrlNbr();
				investigatorLocalId = srchResultVO.getPersonLocalID();
				if(personUid.equalsIgnoreCase(providerUid)) {
					//This is the Selected ResultVO, iterate and display
					if (srchResultVO.getPersonNameColl() != null) {
		               Iterator<Object>  provNameIt = srchResultVO.getPersonNameColl().iterator();
		                while (provNameIt.hasNext()) {
		                  PersonNameDT provName = (PersonNameDT) provNameIt.next();
		                  if (provName.getFirstNm() != null) {
		                    result.append(HTMLEncoder.encodeHtml(provName.getFirstNm()));
		                  }
		                  if (provName.getLastNm() != null) {
		                    result.append(" ").append(HTMLEncoder.encodeHtml(provName.getLastNm()));
		                  }
		                  if (provName.getNmSuffix() != null) {
		                    result.append(", ").append(HTMLEncoder.encodeHtml(provName.getNmSuffix()));
		                  }
		                  if (provName.getNmDegree() != null) {
		                    result.append(", ").append(HTMLEncoder.encodeHtml(provName.getNmDegree()));
		                  }
		                }
		              }
		              if (srchResultVO.getPersonLocatorsColl() != null) {
		                if (srchResultVO.getPersonLocatorsColl() != null) {
		                 Iterator<Object>  provLocatorIt = srchResultVO.getPersonLocatorsColl().iterator();
		                  while (provLocatorIt.hasNext()) {
		                	String codeTranslated = CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.WORK_PHONE, "EL_USE_PST_PRV");
		                    EntityLocatorParticipationDT entityLocatorDT = (EntityLocatorParticipationDT) provLocatorIt.next();
		                    if(entityLocatorDT.getCd()!= null && entityLocatorDT.getCd().equalsIgnoreCase(NEDSSConstants.CODE_O)
		                       && entityLocatorDT.getClassCd()!= null && entityLocatorDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL)
		                       && entityLocatorDT.getUseCd()!= null && entityLocatorDT.getUseCd().equalsIgnoreCase(codeTranslated))

		                    	if (entityLocatorDT != null && entityLocatorDT.getClassCd()!= null && entityLocatorDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL)) {
			                        PostalLocatorDT postaLocatorDT = entityLocatorDT.getThePostalLocatorDT();
			                        if (postaLocatorDT != null) {
			                          if (postaLocatorDT.getStreetAddr1() != null) {
			                            result.append("<br>").append(HTMLEncoder.encodeHtml(postaLocatorDT.getStreetAddr1()));
			                          }
			                          if (postaLocatorDT.getStreetAddr2() != null) {
			                            result.append("<br>").append(HTMLEncoder.encodeHtml(postaLocatorDT.getStreetAddr2()));
			                          }
			                          if (postaLocatorDT.getCityCd() != null) {
			                            result.append("<br>").append(HTMLEncoder.encodeHtml(postaLocatorDT.getCityDescTxt()));
			                          }
			                          if (postaLocatorDT.getStateCd() != null) {

			                            result.append(", ").append(HTMLEncoder.encodeHtml(getStateDescTxt(postaLocatorDT.getStateCd())));
			                          }
			                          if (postaLocatorDT.getZipCd() != null) {
			                            result.append(" ").append(HTMLEncoder.encodeHtml(postaLocatorDT.getZipCd()));

			                          }
			                        }
			                      }

		                    if (entityLocatorDT != null && entityLocatorDT.getClassCd()!= null 
		                    		  && entityLocatorDT.getClassCd().equalsIgnoreCase(NEDSSConstants.TELE)
		                    		  && entityLocatorDT.getUseCd()!= null && entityLocatorDT.getUseCd().equals(codeTranslated)
		                    		  && entityLocatorDT.getCd()!=null && entityLocatorDT.getCd().equals(NEDSSConstants.PHONE)) {      
		                  
		                    	  TeleLocatorDT telelocatorDT = entityLocatorDT.getTheTeleLocatorDT();
		                        if (telelocatorDT != null) {
		  							if(telelocatorDT.getEmailAddress() != null) {
		  								result.append("<br>").append(HTMLEncoder.encodeHtml(telelocatorDT.getEmailAddress()));
		  							}
		                          if (telelocatorDT.getPhoneNbrTxt() != null) {
		                            result.append("<br>").append(HTMLEncoder.encodeHtml(telelocatorDT.getPhoneNbrTxt()));
		                          }
		                          if (telelocatorDT.getExtensionTxt() != null) {
		                            result.append("<b> Ext.</b> ").append(HTMLEncoder.encodeHtml(telelocatorDT.getExtensionTxt()));
		                          }
		                        }
		                      }
		                  }
		                }
		              }
		              /*if(srchResultVO.getPersonIdColl()!=null && srchResultVO.getPersonIdColl().size()>0){
		            	  Iterator<Object> ite = srchResultVO.getPersonIdColl().iterator();
		            	  while(ite.hasNext()){
		            		  EntityIdDT entityId = (EntityIdDT)ite.next();
		            		  result.append("<br><b>").append(entityId.getTypeDescTxt()).append(":</b> ");
		            		  if(entityId.getRootExtensionTxt()!=null && entityId.getRootExtensionTxt().trim().length()>0)
		            			  result.append(entityId.getRootExtensionTxt()).append(" ");
		            		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
		            			  result.append("(").append(entityId.getAssigningAuthorityDescTxt()).append(" ");
		            		  if(entityId.getAssigningAuthorityCd() !=null && entityId.getAssigningAuthorityCd().trim().length()>0)
		            			  result.append("(").append(entityId.getAssigningAuthorityCd()).append(")");
		            		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
		            			  result.append(")");
		            	  }
		              }*/
				}//
			}
		}
  		returnMap.put("LOCALID", investigatorLocalId);
  		returnMap.put("result", result.toString());
  		returnMap.put("versionCtrlNbr", versionCtrlNbr == null ? "" : versionCtrlNbr.toString());
  		return returnMap;
  	}
	/**
  	 * buildPatientResult returns a HashMap<Object,Object> with contact patient detail information to show
  	 * @param list
  	 * @param patientUid
  	 * @param session
  	 * @return
  	 * @throws Exception
  	 */
  	public static Map<Object,Object> buildPatientResult(ArrayList<?>  list, String patientUid, HttpSession session) throws Exception {

  		Map<Object,Object> returnMap = new HashMap<Object,Object>();
  		StringBuffer result = new StringBuffer("");
  	    Integer versionCtrlNbr= null;
  		if(list != null && list.size() > 0) {
			Iterator<?> iter = list.iterator();
			while(iter.hasNext()) {
				PatientSearchResultsVO srchResultVO = (PatientSearchResultsVO) iter.next();
				String personUid = srchResultVO.getPersonUID().toString();
				//versionCtrlNbr = srchResultVO.getVersionCtrlNbr();
				if(personUid.equalsIgnoreCase(patientUid)) {
					//This is the Selected ResultVO, iterate and display
		               result.append(srchResultVO.getFullName());
		               if (srchResultVO.getAddress() != null)
		            	   result.append("<br>").append(HTMLEncoder.encodeHtml(srchResultVO.getAddress()));
		               if (srchResultVO.getTelephone()!= null)
		            	   result.append("<br>").append(HTMLEncoder.encodeHtml(srchResultVO.getTelephone()));  
		        } //if this person selected
		    }
		}
  		returnMap.put("result", result.toString());
  		//returnMap.put("versionCtrlNbr", versionCtrlNbr == null ? "" : versionCtrlNbr.toString());
  		return returnMap;
  	}  	
  	
  /**
   * For a given Quick entry code, gets the person uid and result from the backend.
   * Type defines the different types of person that returns different types of data
   * from the backend.
   * @param type : String
   * @param code : String
   * @param session : HttpSession
   * @return : Map
   * @throws Exception
   */
  public static Map<Object,Object> getPersonValue(String type, String code, HttpSession session) throws Exception {
    Map<Object,Object> returnMap = new HashMap<Object,Object>();
    Long personUID = null;
    String investigatorLocalId = null;
    Integer versionCtrlNbr= null;
    StringBuffer result = new StringBuffer("");
    ProviderSearchVO provSearchVO = new ProviderSearchVO();
    provSearchVO.setRootExtensionTxt(code);
    provSearchVO.setTypeCd("QEC");
    ArrayList<?> alist = null;
    MainSessionCommand msCommand = null;
    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
    String sMethod = "findProvider";
    Object[] oParams = new Object[] {provSearchVO, new Integer(propUtil.getNumberOfRows()), new Integer(0)};
    MainSessionHolder holder = new MainSessionHolder();
    msCommand = holder.getMainSessionCommand(session);
    ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
    alist = (ArrayList<?> ) arr.get(0);
   Iterator<?>  it = alist.iterator();
    if (alist != null) {
      while (it.hasNext()) {
        DisplayPersonList displayPersonList = (DisplayPersonList) it.next();
        if (displayPersonList != null) {
          ArrayList<Object> list = displayPersonList.getList();
          if (list != null) {
           Iterator<Object>  provSearchResultIt = list.iterator();
            while (provSearchResultIt.hasNext()) {
              ProviderSrchResultVO srchResultVO = (ProviderSrchResultVO) provSearchResultIt.next();
              personUID = srchResultVO.getPersonUID();
              investigatorLocalId = srchResultVO.getPersonLocalID();
              versionCtrlNbr= srchResultVO.getVersionCtrlNbr();
              if (srchResultVO.getPersonNameColl() != null) {
               Iterator<Object>  provNameIt = srchResultVO.getPersonNameColl().iterator();
                while (provNameIt.hasNext()) {
                  PersonNameDT provName = (PersonNameDT) provNameIt.next();
                  if (provName.getFirstNm() != null) {
                    result.append(HTMLEncoder.encodeHtml(provName.getFirstNm()));
                  }
                  if (provName.getLastNm() != null) {
                    result.append(" ").append(HTMLEncoder.encodeHtml(provName.getLastNm()));
                  }
                  if (provName.getNmSuffix() != null) {
                    result.append(", ").append(HTMLEncoder.encodeHtml(provName.getNmSuffix()));
                  }
                  if (provName.getNmDegree() != null) {
                    result.append(", ").append(HTMLEncoder.encodeHtml(provName.getNmDegree()));
                  }
                }
              }

             
              if (srchResultVO.getPersonLocatorsColl() != null) {
                if (srchResultVO.getPersonLocatorsColl() != null) {
                 Iterator<Object>  provLocatorIt = srchResultVO.getPersonLocatorsColl().iterator();
                  while (provLocatorIt.hasNext()) {
                    String codeTranslated = CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.WORK_PHONE, "EL_USE_PST_PRV");
                    EntityLocatorParticipationDT entityLocatorDT = (EntityLocatorParticipationDT) provLocatorIt.next();
                    if(entityLocatorDT.getCd()!= null && entityLocatorDT.getCd().equalsIgnoreCase(NEDSSConstants.CODE_O)
                       && entityLocatorDT.getClassCd()!= null && entityLocatorDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL)
                       && entityLocatorDT.getUseCd()!= null && entityLocatorDT.getUseCd().equalsIgnoreCase(codeTranslated))

                    	if (entityLocatorDT != null && entityLocatorDT.getClassCd()!= null && entityLocatorDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL)) {
	                        PostalLocatorDT postaLocatorDT = entityLocatorDT.getThePostalLocatorDT();
	                        if (postaLocatorDT != null) {
	                          if (postaLocatorDT.getStreetAddr1() != null) {
	                            result.append("<br>").append(HTMLEncoder.encodeHtml(postaLocatorDT.getStreetAddr1()));
	                          }
	                          if (postaLocatorDT.getStreetAddr2() != null) {
	                            result.append("<br>").append(HTMLEncoder.encodeHtml(postaLocatorDT.getStreetAddr2()));
	                          }
	                          if (postaLocatorDT.getCityCd() != null) {
	                            result.append("<br>").append(HTMLEncoder.encodeHtml(postaLocatorDT.getCityDescTxt()));
	                          }
	                          if (postaLocatorDT.getStateCd() != null) {

	                            result.append(", ").append(HTMLEncoder.encodeHtml(getStateDescTxt(postaLocatorDT.getStateCd())));
	                          }
	                          if (postaLocatorDT.getZipCd() != null) {
	                            result.append(" ").append(HTMLEncoder.encodeHtml(postaLocatorDT.getZipCd())).append(" ");

	                          }
	                        }
	                      }
                    if (entityLocatorDT != null && entityLocatorDT.getClassCd()!= null 
                    		  && entityLocatorDT.getClassCd().equalsIgnoreCase(NEDSSConstants.TELE)
                    		  && entityLocatorDT.getUseCd()!= null && entityLocatorDT.getUseCd().equals(codeTranslated)
                    		  && entityLocatorDT.getCd()!= null && entityLocatorDT.getCd().equals(NEDSSConstants.PHONE)) {      
                  
                    	  TeleLocatorDT telelocatorDT = entityLocatorDT.getTheTeleLocatorDT();
                        if (telelocatorDT != null) {
  							if(telelocatorDT.getEmailAddress() != null) {
  								result.append("<br>").append(HTMLEncoder.encodeHtml(telelocatorDT.getEmailAddress()));
  							}
                          if (telelocatorDT.getPhoneNbrTxt() != null) {
                            result.append("<br>").append(HTMLEncoder.encodeHtml(telelocatorDT.getPhoneNbrTxt())).append(" ");
                          }
                          if (telelocatorDT.getExtensionTxt() != null) {
                            result.append("<b> Ext.</b> ").append(HTMLEncoder.encodeHtml(telelocatorDT.getExtensionTxt()));
                          }
                        }
                      }
                  }
                }
              }
             /* if(srchResultVO.getPersonIdColl()!=null && srchResultVO.getPersonIdColl().size()>0){
            	  Iterator<Object> ite = srchResultVO.getPersonIdColl().iterator();
            	  while(ite.hasNext()){
            		  EntityIdDT entityId = (EntityIdDT)ite.next();
            		  result.append("<br><b>").append(entityId.getTypeDescTxt()).append(":</b> ");
            		  if(entityId.getRootExtensionTxt()!=null && entityId.getRootExtensionTxt().trim().length()>0)
            			  result.append(entityId.getRootExtensionTxt()).append(" ");
            		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
            			  result.append("(").append(entityId.getAssigningAuthorityDescTxt()).append(" ");
            		  if(entityId.getAssigningAuthorityCd() !=null && entityId.getAssigningAuthorityCd().trim().length()>0)
            			  result.append("(").append(entityId.getAssigningAuthorityCd()).append(")");
            		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
            			  result.append(")");
            	  }
              }*/
            }
          }
        }
      }
    }
    
    returnMap.put("UID", personUID);
    returnMap.put("LOCALID", investigatorLocalId);
    returnMap.put("versionCtrlNbr", versionCtrlNbr == null ? "" : versionCtrlNbr.toString());
    
    returnMap.put("result", result.toString());
    return returnMap;
  }

  /**
   * Generic method used by different methods that returns drop down list which
   * needs to be further tied to different elements.
   * @param labId : String
   * @param type : String
   * @param progAreaCode : String
   * @param orderedTestCode : String
   * @param resultedTestCode : String
   * @param conditionCode : String
   * @param request : HttpServletRequest
   * @return : String
   */
  public static String cachedValues(String labId, String type, String conditionCode, String progAreaCode, String orderedTestCode, String resultedTestCode, HttpServletRequest request) {
    Collection<Object>  alist = new ArrayList<Object> ();
    Map<String,String> map = new HashMap<String,String>();

    if (type.equals(NEDSSConstants.ORDERED_TEST_LOOKUP) || type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP)) {
      if ( (conditionCode != null && !conditionCode.trim().equalsIgnoreCase(""))
              && !conditionCode.equalsIgnoreCase("undefined")) {
           map.put(SRTFilterKeys.CONDITION_CODE, conditionCode);
         }
      else if ( (progAreaCode != null && progAreaCode.trim().equalsIgnoreCase(""))
           || (progAreaCode != null && progAreaCode.equalsIgnoreCase("undefined"))
           || (progAreaCode != null && progAreaCode.equalsIgnoreCase(NEDSSConstants.PROGRAM_AREA_NONE))) {
        progAreaCode = null;
      }
      else {
        map.put(SRTFilterKeys.PROGRAM_AREA_CODE, progAreaCode);
      }
    }
    if (!type.equals(NEDSSConstants.TREATMENTS_LOOKUP) || !type.equals(NEDSSConstants.TREATMENT_DRUGS_LOOKUP)) {
      if (labId != null && labId.trim().equalsIgnoreCase("")) {
        labId = null;
      }
      map.put(SRTFilterKeys.REPORTING_FACILITY_ID, labId);
    }
    if (type.equals(NEDSSConstants.SPECIMEN_SOURCE_LOOKUP) || type.equals(NEDSSConstants.SPECIMEN_SITE_LOOKUP)
        || type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP)) {
      if (orderedTestCode == null || (orderedTestCode != null && orderedTestCode.trim().equalsIgnoreCase(""))) {
        orderedTestCode = null;
      }
      if(orderedTestCode!= null && !orderedTestCode.trim().equalsIgnoreCase("")){
        map.put(SRTFilterKeys.ORDERED_TEST_CODE, orderedTestCode);
        if(map.get(SRTFilterKeys.PROGRAM_AREA_CODE)!= null && type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP))
          map.remove(SRTFilterKeys.PROGRAM_AREA_CODE);
    }
    // else
   //   map.put(SRTFilterKeys.ORDERED_TEST_CODE, NEDSSConstants.LAB_TESTCODE_NI);

    }
    if (type.equals(NEDSSConstants.ORGANISM_LOOKUP) || type.equals(NEDSSConstants.NUMERIC_RESULT_LOOKUP) || type.equals(NEDSSConstants.CODED_RESULT_LOOKUP)) {

      if (resultedTestCode != null && resultedTestCode.trim().equalsIgnoreCase("")) {
        resultedTestCode = null;
      }
      map.put(SRTFilterKeys.RESULTED_TEST_CODE, resultedTestCode);
    }
    if (type.equals(NEDSSConstants.DRUG_LOOKUP) ) {

      if (resultedTestCode != null && resultedTestCode.trim().equalsIgnoreCase("")) {
        resultedTestCode = null;
      }
       map.put(SRTFilterKeys.REPORTING_FACILITY_ID, labId);
      map.put(SRTFilterKeys.RESULTED_TEST_CODE, resultedTestCode);
    }

    alist = getObjectListFromProxy(map, type, request);

    ArrayList<Object> list = new ArrayList<Object> ();
    StringBuffer sLabOptions = new StringBuffer("");
    StringBuffer sLabOptionsNonIndent = new StringBuffer("");
    if (alist != null) {
     Iterator<Object>  itr = alist.iterator();
      sLabOptions.append("$|");
      sLabOptionsNonIndent.append("$|");
      int maxDropDownList = 0;
      if (propUtil.getMaxDropDownCount() != null )
      {
        maxDropDownList = new Integer(propUtil.getMaxDropDownCount()).intValue();
      }
      int i = 0;
      int j = 0;

      while (itr.hasNext()) {
        SRTCode sRTLabTestDT = (SRTCode) itr.next();
        //if(sRTLabTestDT.getIndentLevel()!= null && type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP))
       // {
       //   logger.debug("The resulted Test lookup is fine");
       // }
       String key = sRTLabTestDT.getCode();
       String res = sRTLabTestDT.getDesc();
       if((sRTLabTestDT.getIndentLevel()!= null)&& (sRTLabTestDT.getIndentLevel().compareTo(new Long(1)) == 0))
       {
            //logger.debug("indent lebvel nbr is :" +sRTLabTestDT.getIndentLevel());
            i++;
            if (res != null) {
              sLabOptions.append(key).append("$").append(res).append("|");

            }
            if (i == maxDropDownList)
            {
              logger.debug("The max count of indentLevelNbr has been reached :" + i);
              break;
            }list.add(sRTLabTestDT);
        }
        else
        {
          j++;
          if (j == maxDropDownList)
          {
            logger.debug("The maxcount has been reached:" + j);
            continue;
          }if (res != null) {
            sLabOptionsNonIndent.append(key).append("$").append(res).append("|");

          }

        }

      }

    }
    if(list.size()==0)
          sLabOptions = sLabOptionsNonIndent;
    return sLabOptions.toString();
  }

  /**
   * This method defines the behavior of the code whenever the program area changes,
   * the resulted tests and ordered tests needs to be changed
   * @param programAreaCode :String
   * @param labId : String
   * @param targetResulted : String
   * @param targetOrdered : String
   * @param target :String
   * @param request : HttpServletRequest
   * @param response : HttpServletRequest
   * @throws IOException
   */
  public void progAreaCodeDropDowns(String programAreaCode, String labId, String targetResulted, String targetOrdered, String target, HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
    StringBuffer sbHTML = new StringBuffer("");
     // StringBuffer select = new StringBuffer("");
   // StringBuffer selectOptions = new StringBuffer("");

    PrintWriter out = response.getWriter();
    String sLabOptionsOrdered = "";
    sbHTML.append("<html lang=\"en\"><head><title>data getter</title>");
    sbHTML.append("\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\" SRC=\"\\nbs\\resource\\javascript\\validate.js\">");
    sbHTML.append("\n</SCRIPT>");
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
    sbHTML.append("\nvar win = window.opener;\nvar doc = win.document;");
    sbHTML.append("\nvar morbType = getElementByIdOrByNameNode(\"morbType\", doc);");

    String conditionCode  =HTMLEncoder.encodeHtml(getCorrectParameter(request,"conditionCode"));
    String sLabOptionsResulted = null;
    if(conditionCode!= null && conditionCode.trim()!="" && !conditionCode.equalsIgnoreCase("undefined"))
    {
       //logger.debug("\n\n\n\nconditionCode is :" + conditionCode);
       sLabOptionsResulted = cachedValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, conditionCode, null, null, null, request);
    }
    else
      sLabOptionsResulted = cachedValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, null, programAreaCode, null, null, request);

    //sbHTML.append("\n var resultedTestNameInd = getElementByIdOrByNameNode(\"resultedTestNameInd\", doc);");
    //sbHTML.append("\n if(resultedTestNameInd!=null)resultedTestNameInd.className= \"visible\";");

    sbHTML.append("var sOpts = '<SELECT name=\"").append(target).append("\" id=\"").append(target);
    sbHTML.append("\">';");
    sbHTML.append("var sOptions = '<SELECT id=\"").append(targetOrdered).append( "\" name=\"").append(targetOrdered).append("\" onchange=\"getOrderedTestChange()\"; entitysearch=\"yes");
    sbHTML.append("\">';");
    sbHTML.append("\nvar updateNode = getElementByIdOrByNameNode(\"").append(targetResulted).append("\", doc);");
    sbHTML.append("\nvar payload = getElementByIdOrByNameNode(\"payload\", window.document);");
    sbHTML.append("\nvar options = payload.getElementsByTagName(\"option\");");
    //sbHTML.append(select.toString());

    /////////////////////////////////
    sbHTML.append("createDropDown(\"").append(sLabOptionsResulted).append("\",updateNode,doc);");
    /////////////////////////////////

    //commented out on Feb 20  sbHTML.append("\nvar morbType = getElementByIdOrByNameNode(\"morbType\", doc);");
    //commented out on Feb 20      sbHTML.append("\n     if(morbType!= null)");
    //commented out on Feb 20      sbHTML.append("\n    { ");


      //commented out on Feb 20  String sTreatmentList = convertTreatmentToRequest(NEDSSConstants.TREATMENT_SRT_VALUES, NEDSSConstants.TREATMENT_SRT_CREATE, null, conditionCode);
    //sbHTML.append("\n var resultedTestNameInd = getElementByIdOrByNameNode(\"resultedTestNameInd\", doc);");
    //sbHTML.append("\n if(resultedTestNameInd!=null)resultedTestNameInd.className= \"visible\";");

    //sbHTML.append("var sOptsTrt = '<SELECT id=\"treatment_s[i].treatmentVO_s[0].theTreatmentDT.cd\" name=\"treatment_s[i].treatmentVO_s[0].theTreatmentDT.cd\" header=\"Treatment\" size=\"5\" class=\"none\"");
    //sbHTML.append("\">';");
    //sbHTML.append("\nvar updateNodeTrt = getElementByIdOrByNameNode(\"", doc).append("treatment_s[i].treatmentVO_s[0].theTreatmentDT.cd").append("\");");
    //commented out on Feb 20  sbHTML.append("\nvar payloadTrt = getElementByIdOrByNameNode(\"payloadTrt\", window.document);");
    //commented out on Feb 20  sbHTML.append("\nvar optionsTrt = payload.getElementsByTagName(\"option\");");
    //sbHTML.append(select.toString());

    /////////////////////////////////
    //commented out on Feb 20  sbHTML.append("createDropDown(\"").append(sTreatmentList).append("\",updateNodeTrt,doc);");
    /////////////////////////////////

    //commented out on Feb 20  String sTreatmentDrugs = convertTreatmentToRequest(NEDSSConstants.TREATMENT_SRT_DRUGS, NEDSSConstants.TREATMENT_SRT_CREATE, null, conditionCode);
 //sbHTML.append("\n var resultedTestNameInd = getElementByIdOrByNameNode(\"resultedTestNameInd\", doc);");
 //sbHTML.append("\n if(resultedTestNameInd!=null)resultedTestNameInd.className= \"visible\";");

 //sbHTML.append("var sOpts = '<SELECT name=\"").append(target).append("\" id=\"").append(target);
 //sbHTML.append("\">';");
 //sbHTML.append("var sOptions = '<SELECT id=\"").append(targetOrdered).append( "\" name=\"").append(targetOrdered).append("\" onchange=\"getOrderedTestChange()\"; entitysearch=\"yes");
 //sbHTML.append("\">';");
 //commented out on Feb 20  sbHTML.append("\nvar updateNodeTrtDrg = getElementByIdOrByNameNode(\"", doc).append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].cd").append("\");");
 //commented out on Feb 20  sbHTML.append("\nvar payloadTrtDrg = getElementByIdOrByNameNode(\"payloadTrtDrg\", window.document);");
 //commented out on Feb 20  sbHTML.append("\nvar optionsTrtDrg = payload.getElementsByTagName(\"option\");");
 //sbHTML.append(select.toString());

 /////////////////////////////////
 //commented out on Feb 20  sbHTML.append("createDropDown(\"").append(sTreatmentDrugs).append("\",updateNodeTrtDrg,doc);");
 /////////////////////////////////

 //commented out on Feb 20    sbHTML.append("\n    } ");


/*
    sbHTML.append("\nfor (var i=0;i<options.length;i++)");
    sbHTML.append("\n{");
    sbHTML.append("\nsOpts += '<OPTION VALUE=\"' + options.item(i).value + '\">' + options.item(i).text + '</OPTION>';");
    sbHTML.append("\n\n}");
    sbHTML.append("\nupdateNode.outerHTML = sOpts  + \"</SELECT>\";");
*/
    if (targetOrdered != null && !targetOrdered.trim().equalsIgnoreCase("")) {
      sLabOptionsOrdered = cachedValues(labId, NEDSSConstants.ORDERED_TEST_LOOKUP, null, programAreaCode, null, null, request);
      //sbHTML.append("\n var specimenSource = getElementByIdOrByNameNode(\"specimenSource\", doc);");
      //sbHTML.append("\n specimenSource.className= \"visible\";");
      //sbHTML.append("\n var specimentSite = getElementByIdOrByNameNode(\"specimentSite\", doc);");
      //sbHTML.append("\n specimentSite.className= \"visible\";");
      //sbHTML.append("\n var orderedTestNameInd = getElementByIdOrByNameNode(\"orderedTestNameInd\", doc);");
      //sbHTML.append("\n orderedTestNameInd.className= \"visible\";");
      sbHTML.append("\nvar updateNodeOrdered = getElementByIdOrByNameNode(\"").append(targetOrdered).append("\", doc);");
      sbHTML.append("\nvar payloadOrdered = getElementByIdOrByNameNode(\"payloadOrdered\", window.document);");
      sbHTML.append("\nvar optionsOrdered = payloadOrdered.getElementsByTagName(\"option\", window.document);");
      //sbHTML.append(selectOptions.toString());
      /////////////////////////////////
      sbHTML.append("createDropDown(\"").append(sLabOptionsOrdered).append(
          "\",updateNodeOrdered,doc);");
    /////////////////////////////////
/*
      sbHTML.append("\nfor (var i=0;i<optionsOrdered.length;i++)");
      sbHTML.append("\n{");
      sbHTML.append("\nsOptions += '<OPTION VALUE=\"' + optionsOrdered.item(i).value + '\">' + optionsOrdered.item(i).text + '</OPTION>';");
      sbHTML.append("\n\n}");
      sbHTML.append("\nupdateNodeOrdered.outerHTML = sOptions  + \"</SELECT>\";");
  */  }
    sbHTML.append("\nwindow.close();");
    sbHTML.append("\n}");
    sbHTML.append("</SCRIPT>");
    sbHTML.append("</head>");
    sbHTML.append("<body>");
    sbHTML.append("<select name=\"payload\" id=\"payload\">").append(sLabOptionsResulted).append("</select>");
    if (targetOrdered != null && targetOrdered.trim() != "") {
      sbHTML.append("<select name=\"payloadOrdered\" id=\"payloadOrdered\">").append(sLabOptionsOrdered).append("</select>");
    }

    sbHTML.append("<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">goBack();</SCRIPT>");
    sbHTML.append("</body></html>");
    // logger.debug("\n\n\n\n@@@@sbHTML to progAreaDropDowns String is :" + sbHTML.toString());
    out.println(HTMLEncoder.sanitizeHtml(sbHTML.toString()));

  }

  /**
   * This method defines the behavior of the code when the entity search criteria is used
   * to populated the Ordered tests and resuled Tests from the backend.
   * @param labId :String
   * @param targetResulted : String
   * @param targetOrdered : String
   * @param target : String
   * @param programAreaCode : String
   * @param conditionCode : String
   * @param dropdownInd boolean value
   * @param request : HttpServletRequest
   * @param response : HttpServletResponse
   * @throws IOException
   */
  public void entitySearchRoute(String labId, String targetResulted, String targetOrdered, String target, String conditionCode, String programAreaCode, boolean dropdownInd, HttpServletRequest request, HttpServletResponse response) throws
      IOException {
    StringBuffer sbHTML = new StringBuffer("");
    StringBuffer select = new StringBuffer("");
    StringBuffer selectOptions = new StringBuffer("");
    if(dropdownInd)
    {
    select.append("var sOpts = '<SELECT name=\"").append(target).append("\" id=\"").append(target);
    select.append("\">';");
    selectOptions.append("var sOptions = '<SELECT id=\"").append(targetOrdered).append( "\" name=\"").append(targetOrdered).append("\" onchange=\"getOrderedTestChange()\"; entitysearch=\"yes");
    selectOptions.append("\">';");
    }
    PrintWriter out = response.getWriter();
    sbHTML.append("<html lang=\"en\"><head><title>data getter</title>");
    sbHTML.append("\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\" SRC=\"\\nbs\\resource\\javascript\\validate.js\">");
    sbHTML.append("\n</SCRIPT>");
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

    sbHTML.append("\nvar winChild = window.opener;");
    sbHTML.append("\nvar win = winChild.opener;");
    sbHTML.append("\nvar doc = win.document;");

    sbHTML.append("\nvar labID = getElementByIdOrByNameNode(\"labId\", doc);");
    sbHTML.append("\nvar morbType = getElementByIdOrByNameNode(\"morbType\", doc);");
    sbHTML.append("\n     if(morbType!= null)");
    sbHTML.append("\n    { ");
    sbHTML.append("\nlabID.value = \"" + NEDSSConstants.DEFAULT + "\";");
    sbHTML.append("\n     }");
    sbHTML.append("\n     else ");
    sbHTML.append("\n    {");
    sbHTML.append("\nif(labID!=null)");
    sbHTML.append("\nlabID.value = \"" + labId + "\";");
    sbHTML.append("\n     }");
    //commented on Feb 12 Map<Object,Object> map = SpecimenChangeOnOT(labId, NEDSSConstants.LAB_TESTCODE_NI, request, response);

    String sLabOptionsResulted = "";
    String sLabOptionsOrdered = "";
    if(dropdownInd)
    {
      if (!target.equals("")) {
        sLabOptionsResulted = cachedValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, conditionCode, programAreaCode, null, null, request);
        sbHTML.append("\n var resultedTestNameInd = getElementByIdOrByNameNode(\"resultedTestNameInd\", doc);");
        sbHTML.append("\n if(resultedTestNameInd!=null)resultedTestNameInd.className= \"visible\";");

        sbHTML.append("\nvar updateNode = getElementByIdOrByNameNode(\"").append(targetResulted).append("\", doc);");
        sbHTML.append("\nvar payload = getElementByIdOrByNameNode(\"payload\", window.document);");
        sbHTML.append("\nvar options = payload.getElementsByTagName(\"option\", window.document);");
        sbHTML.append(select.toString());

        /////////////////////////////////
        sbHTML.append("createDropDown(\"").append(sLabOptionsResulted).append("\",updateNode,doc);");
        /////////////////////////////////
        /*
               sbHTML.append("\nfor (var i=0;i<options.length;i++)");
               sbHTML.append("\n{");
               sbHTML.append("\nsOpts += '<OPTION VALUE=\"' + options.item(i).value + '\">' + options.item(i).text + '</OPTION>';");
               sbHTML.append("\n\n}");
               sbHTML.append("\nupdateNode.outerHTML = sOpts  + \"</SELECT>\";");
         */
        if (targetOrdered != null && !targetOrdered.trim().equalsIgnoreCase("")) {
          sLabOptionsOrdered = cachedValues(labId, NEDSSConstants.ORDERED_TEST_LOOKUP, conditionCode, programAreaCode, null, null, request);

          sbHTML.append("\n var specimenSource = getElementByIdOrByNameNode(\"specimenSource\", doc);");
          sbHTML.append("\n specimenSource.className= \"visible\";");
          sbHTML.append("\n var specimentSite = getElementByIdOrByNameNode(\"specimentSite\", doc);");
          sbHTML.append("\n specimentSite.className= \"visible\";");

          sbHTML.append("\n var orderedTestNameInd = getElementByIdOrByNameNode(\"orderedTestNameInd\", doc);");
          sbHTML.append("\n orderedTestNameInd.className= \"visible\";");
          // commented on Feb 12 if(map!=null)
          // commented on Feb 12 sbHTML.append(map.get("javaScript").toString());
          sbHTML.append("\nvar updateNodeOrdered = getElementByIdOrByNameNode(\"").append(targetOrdered).append("\", doc);");
          sbHTML.append("\nvar payloadOrdered = getElementByIdOrByNameNode(\"payloadOrdered\", window.document);");
          sbHTML.append("\nvar optionsOrdered = payloadOrdered.getElementsByTagName(\"option\", window.document);");
          sbHTML.append(selectOptions.toString());
          /////////////////////////////////
          sbHTML.append("createDropDown(\"").append(sLabOptionsOrdered).append("\",updateNodeOrdered,doc);");
          /////////////////////////////////
          /*
                   sbHTML.append("\nfor (var i=0;i<optionsOrdered.length;i++)");
                   sbHTML.append("\n{");
                   sbHTML.append("\nsOptions += '<OPTION VALUE=\"' + optionsOrdered.item(i).value + '\">' + optionsOrdered.item(i).text + '</OPTION>';");
                   sbHTML.append("\n\n}");
                   sbHTML.append("\nupdateNodeOrdered.outerHTML = sOptions  + \"</SELECT>\";");
           */
        }
    }
  }
    sbHTML.append("\nwindow.close();\nwinChild.close();");
    sbHTML.append("\n}");
    sbHTML.append("</SCRIPT>");
    sbHTML.append("</head>");
    sbHTML.append("<body>");
    if (!target.equals("")) {
      sbHTML.append("<select name=\"payload\" id=\"payload\">").append(sLabOptionsResulted).append("</select>");

      sbHTML.append("<select name=\"payloadOrdered\" id=\"payloadOrdered\">").append(sLabOptionsOrdered).append("</select>");
     // commented on Feb 12 if(map!=null)
     // commented on Feb 12  sbHTML.append(map.get("htmlBody").toString());
    }
    sbHTML.append("<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">goBack();</SCRIPT>");
    sbHTML.append("</body></html>");
    // logger.debug("\n\n\n\n@@@@sbHTML entitySearchRoute to String is :" + sbHTML.toString());
    out.println(HTMLEncoder.sanitizeHtml(sbHTML.toString()));

  }

  /**
   *This condition checks if the reporting facility data needs to be copied over to
   * Ordering facility and then copies the data over to Ordering facility.
   * @param reportOrg : String
   * @param UID : String
   * @return : StringBuffer
   */
  private StringBuffer iflab277Checked(String reportOrg, String UID) {
    StringBuffer sbuff = new StringBuffer();
    sbuff.append(" \n var lab277 = getElementByIdOrByNameNode(\"LAB277\", doc);");
    //sbuff.append(" \n alert(lab277.checked);");
    //sbuff.append(" \n alert(lab277);");
    sbuff.append(" \n if(lab277!=null && lab277.checked){");
    sbuff.append(" \n var etable = getElementByIdOrByNameNode(\"entity-table-Org-OrderingFacilityUID\", doc);");
    //sbuff.append(" \n alert(\"inside the check condition\");");
    sbuff.append(" \n var spans = etable.getElementsByTagName(\"span\");");
    sbuff.append(" \n    for(var i=0;i<spans.length;i++)");
    sbuff.append(" \n    {");
    sbuff.append(" \n            if(spans.item(i).id==\"entity.completeOrgSearchResult\")");
    sbuff.append(" \n            {	");
    sbuff.append(" \n                    var orgSearchResult = \"" + HTMLEncoder.encodeHtml(reportOrg) + "\";");
    sbuff.append(" \n                    spans.item(i).innerHTML = orgSearchResult;");
    sbuff.append(" \n            }");
    sbuff.append(" \n    }");
    sbuff.append(" \n    var inputs = etable.getElementsByTagName(\"input\");");
    sbuff.append(" \n    for(var i=0;i<inputs.length;i++){");
    sbuff.append(" \n            if(inputs.item(i).type==\"hidden\" && inputs.item(i).mode==\"uid\")");
    sbuff.append(" \n            {");
    sbuff.append(" \n                    inputs.item(i).value=\"" + UID + "\";");
    sbuff.append(" \n            }");
    sbuff.append(" \n    }");
    sbuff.append(" \n}");
    return sbuff;
  }

  /**
   * This method extracts the data from the backend based on the Map<Object,Object> with desired keys
   * and values. The filter decides which method to be called on the SRTCacheManagerEJB
   * to get the collection of objects.
   * @param map : Map
   * @param filter : String
   * @param request : HttpServletRequest
   * @return : Collection
   */
  public static Collection<Object>  getObjectListFromProxy(Map<String,String> map, String filter, HttpServletRequest request) {
    Collection<Object>  list = null;
    SRTCacheManager srtManager = null;
    //String filter = request.getParameter("Filter");
    try {
      NedssUtils nedssUtils = new NedssUtils();
      Object objref = nedssUtils.lookupBean(JNDINames.SRT_CACHEMANAGER_EJB);
      SRTCacheManagerHome home = (SRTCacheManagerHome) PortableRemoteObject.narrow(objref, SRTCacheManagerHome.class);
      srtManager = home.create();
      if (filter.equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_LOOKUP)) {
        //list = (ArrayList<Object> ) srtManager.getOrderedTests(map);
        list = srtManager.getResultedTests(map);
      }
      else if (filter.equalsIgnoreCase(NEDSSConstants.ORDERED_TEST_LOOKUP)) {

        list = srtManager.getOrderedTests(map);
        //list = (ArrayList<Object> )srtManager.getResultedTests(map);
      }
      else if (filter.equals(NEDSSConstants.NBS_ELEMENTSUID_LOOKUP)) {

        list = srtManager.getNBSDisplayElementIDs(map);
      }
      else if (filter.equals(NEDSSConstants.CODED_RESULT_LOOKUP)) {

        list = srtManager.getLabResults(map);
      }

      else if (filter.equals(NEDSSConstants.DRUG_LOOKUP)) {

         list = srtManager.getResultedDrugTests(map);
       }

     else if (filter.equals(NEDSSConstants.ORGANISM_LOOKUP)) {

        list = srtManager.getOrganisms(map);
      }
      else if (filter.equals(NEDSSConstants.SPECIMEN_SITE_LOOKUP)) {

        list = srtManager.getAnatomicSites(map);
      }
      else if (filter.equals(NEDSSConstants.SPECIMEN_SOURCE_LOOKUP)) {

        list = srtManager.getSpecimenSources(map);
      }
      else if (filter.equals(NEDSSConstants.NUMERIC_RESULT_LOOKUP)) {

        list = srtManager.getUnits(map);
      }
      else if (filter.equals(NEDSSConstants.INVESTIGATION_REPORTING_LOOKUP)) {

        list = srtManager.getInvestigationReportingSource(map);
      }
      else if (filter.equals(NEDSSConstants.TREATMENTS_LOOKUP)) {

        list = srtManager.getTreatments(map);
      }
      else if (filter.equals(NEDSSConstants.TREATMENT_DRUGS_LOOKUP)) {

        list = srtManager.getTreatmentDrugs(map);
      }

      else {
        logger.error("This Situation should not happen! Filter doesn't exist:" + filter);
      }
    }
    catch (CreateException e) {
    logger.error("NedssCodeLookupServlet:CreateException thrown from SRT CachemanageEjb " +e);
  }
  catch (RemoteException e) {
    logger.error("NedssCodeLookupServlet:RemoteException thrown from SRT CachemanageEjb " +e);
  }
  catch (SRTCacheManagerException e) {
    logger.error("NedssCodeLookupServlet:SRTCacheManagerException thrown from SRT CachemanageEjb " +e);
  }


    return list;
  }


  /**
   *
   * @param labId : Laboratory Id
   * @param type : type of dropdown List. It could be of OrderedTest, SpecimentSourceLookup,
   * SpecmentSiteLookup, NumericResultLookup, CodedResultLookup, OrganismLookup,
   * TreatmentsLookup, TreatmentDrugsLookup, NbsElementsUidLookup, UnitsLookup,
   * and InvReportingSourceLookup
   * @param conditionCode : Condition Code value
   * @param progAreaCode : Program Area Code value
   * @param orderedTestCode : Ordered Test code
   * @param resultedTestCode : Resulted Test code
   * @param request : HttpServletRequest
   * @return String value representing dorpdown list
   */
  public static String getDropDownValues(String labId, String type, String conditionCode, String progAreaCode, String orderedTestCode, String resultedTestCode, HttpServletRequest request) {
  Collection<Object>  alist = new ArrayList<Object> ();
  Map<String,String> map = new HashMap<String,String>();

  if (type.equals(NEDSSConstants.ORDERED_TEST_LOOKUP) || type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP)) {
    if(conditionCode!= null && !conditionCode.trim().equalsIgnoreCase(""))
    {
      map.put(SRTFilterKeys.CONDITION_CODE, conditionCode);
    }
    else{
      if( (progAreaCode != null && progAreaCode.trim().equalsIgnoreCase(""))
       || progAreaCode!= null &&  progAreaCode.equalsIgnoreCase("undefined")
       || progAreaCode!= null && progAreaCode.equalsIgnoreCase(NEDSSConstants.PROGRAM_AREA_NONE)) {
          progAreaCode = null;
      }
          else {
        map.put(SRTFilterKeys.PROGRAM_AREA_CODE, progAreaCode);
      }
    }
  }
  if (!type.equals(NEDSSConstants.TREATMENTS_LOOKUP) || !type.equals(NEDSSConstants.TREATMENT_DRUGS_LOOKUP)) {
    if (labId != null && labId.trim().equalsIgnoreCase("")) {
      labId = null;
    }
    map.put(SRTFilterKeys.REPORTING_FACILITY_ID, labId);
  }
  if (type.equals(NEDSSConstants.SPECIMEN_SOURCE_LOOKUP) || type.equals(NEDSSConstants.SPECIMEN_SITE_LOOKUP)
      || type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP)) {
    if (orderedTestCode == null || (orderedTestCode != null && orderedTestCode.trim().equalsIgnoreCase(""))) {
      orderedTestCode = null;
    }
    if(orderedTestCode!= null && !orderedTestCode.trim().equalsIgnoreCase("")){
      map.put(SRTFilterKeys.ORDERED_TEST_CODE, orderedTestCode);
      if (map.get(SRTFilterKeys.PROGRAM_AREA_CODE) != null && type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP))
        map.remove(SRTFilterKeys.PROGRAM_AREA_CODE);
    }
   // else
   //   map.put(SRTFilterKeys.ORDERED_TEST_CODE, NEDSSConstants.LAB_TESTCODE_NI);

      if(resultedTestCode!= null && resultedTestCode.trim().equalsIgnoreCase("")){
        map.put(SRTFilterKeys.RESULTED_TEST_CODE, orderedTestCode);
    }
  }
  if (type.equals(NEDSSConstants.ORGANISM_LOOKUP) || type.equals(NEDSSConstants.NUMERIC_RESULT_LOOKUP) || type.equals(NEDSSConstants.CODED_RESULT_LOOKUP)) {

    if (resultedTestCode != null && resultedTestCode.trim().equalsIgnoreCase("")) {
      resultedTestCode = null;
    }
    map.put(SRTFilterKeys.RESULTED_TEST_CODE, resultedTestCode);
  }
  alist = getObjectListFromProxy(map, type, request);

  StringBuffer sDropDownList = new StringBuffer("");
  StringBuffer sLabOptionsNonIndent = new StringBuffer("");
  ArrayList<Object> list = new ArrayList<Object> ();
  int maxDropDownList = 0;
  if (propUtil.getMaxDropDownCount() != null )
  {
     maxDropDownList = new Integer(propUtil.getMaxDropDownCount()).intValue();
  }
  int i = 0;
  int j = 0;
  if (alist != null) {
   Iterator<Object>  itr = alist.iterator();
    String key =null;
    String res = null;
    while (itr.hasNext()) {
      SRTCode sRTLabTestDT = (SRTCode) itr.next();
       key = HTMLEncoder.encodeHtml(sRTLabTestDT.getCode());
       res = HTMLEncoder.encodeHtml(sRTLabTestDT.getDesc());

      if((sRTLabTestDT.getIndentLevel()!= null)&& (sRTLabTestDT.getIndentLevel().compareTo(new Long(1)) == 0))
      {
           //logger.debug("indent lebvel nbr is :" +sRTLabTestDT.getIndentLevel());
           i++;
           if (res != null) {
             sDropDownList.append(key).append("$").append(res).append("|");

           }
           if (i == maxDropDownList)
           {
             logger.debug("The max count of indentLevelNbr has been reached :" + i);
             break;
           }
           list.add(sRTLabTestDT);
       }
       else
       {
         j++;
         if (j == maxDropDownList)
         {
           logger.debug("The maxcount has been reached:" + j);
           continue;
         }if (res != null) {
           sLabOptionsNonIndent.append(key).append("$").append(res).append("|");

         }

       }

     }

   }
   if(list.size()==0)
         sDropDownList = sLabOptionsNonIndent;
   return sDropDownList.toString();
 }

    /**
     *
     * @param labId : Laboratory Id
     * @param type : type of dropdown List. It could be of OrderedTest, SpecimentSourceLookup,
     * SpecmentSiteLookup, NumericResultLookup, CodedResultLookup, OrganismLookup,
     * TreatmentsLookup, TreatmentDrugsLookup, NbsElementsUidLookup, UnitsLookup,
     * and InvReportingSourceLookup
     * @param progAreaCode : Program Area Code value
     * @param orderedTestCode : Ordered Test code
     * @param resultedTestCode : Resulted Test code
     * @param request : HttpServletRequest
     * @return String value representing dorpdown list
     */
    public static SRTCode getUniqueDTForCodeValueLookup(String labId, String type, String progAreaCode, String orderedTestCode, String resultedTestCode, HttpServletRequest request) {
      SRTCode sRTLabTestDT = null;
      Collection<Object>  alist = new ArrayList<Object> ();
      Map<String,String> map = new HashMap<String,String>();
      if (type.equals(NEDSSConstants.ORDERED_TEST_LOOKUP) || type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP)) {
        if ( (progAreaCode != null && progAreaCode.trim().equalsIgnoreCase(""))
             || (progAreaCode != null && progAreaCode.equalsIgnoreCase("undefined"))
             || (progAreaCode != null && progAreaCode.equalsIgnoreCase(NEDSSConstants.PROGRAM_AREA_NONE)
             ||  progAreaCode==null  )) {
          progAreaCode = null;
        }
        else {
          map.put(SRTFilterKeys.PROGRAM_AREA_CODE, progAreaCode);
        }
      }
      if (!type.equals(NEDSSConstants.TREATMENTS_LOOKUP) || !type.equals(NEDSSConstants.TREATMENT_DRUGS_LOOKUP)) {
        if (labId != null && labId.trim().equalsIgnoreCase("")) {
          labId = null;
        }
        map.put(SRTFilterKeys.REPORTING_FACILITY_ID, labId);
      }
      if (type.equals(NEDSSConstants.SPECIMEN_SOURCE_LOOKUP) || type.equals(NEDSSConstants.SPECIMEN_SITE_LOOKUP)) {
        if (orderedTestCode != null && orderedTestCode.trim().equalsIgnoreCase("")) {
          orderedTestCode = null;
        }
        if(orderedTestCode!= null && orderedTestCode.trim().equalsIgnoreCase("")){
     map.put(SRTFilterKeys.ORDERED_TEST_CODE, orderedTestCode);
   }
   //else
   //  map.put(SRTFilterKeys.ORDERED_TEST_CODE, NEDSSConstants.LAB_TESTCODE_NI);

      }
      if (type.equals(NEDSSConstants.ORGANISM_LOOKUP) || type.equals(NEDSSConstants.NUMERIC_RESULT_LOOKUP) || type.equals(NEDSSConstants.CODED_RESULT_LOOKUP)) {

        if (resultedTestCode != null && resultedTestCode.trim().equalsIgnoreCase("")) {
          resultedTestCode = null;
        }
        map.put(SRTFilterKeys.RESULTED_TEST_CODE, resultedTestCode);
      }


      if (type.equals(NEDSSConstants.RESULTED_TEST_LOOKUP)) {

        if (resultedTestCode != null && resultedTestCode.trim().equalsIgnoreCase("")) {
          resultedTestCode = null;
        }
        map.put(SRTFilterKeys.RESULTED_TEST_CODE, resultedTestCode);
      }
      if (type.equals(NEDSSConstants.ORDERED_TEST_LOOKUP)) {

        if (orderedTestCode != null && orderedTestCode.trim().equalsIgnoreCase("")) {
          orderedTestCode = null;
        }
        map.put(SRTFilterKeys.ORDERED_TEST_CODE, orderedTestCode);
      }


      alist = getObjectListFromProxy(map, type, request);
      if(alist != null && alist.size()>1)
      {
        logger.error("NedssCodeLookupServlet:This condition should not happen: Returning multiple DTs for a combination of keys");
      }
      if (alist != null) {
       Iterator<Object>  itr = alist.iterator();
        while (itr.hasNext()) {
          sRTLabTestDT = (SRTCode) itr.next();
        }
      }
      return sRTLabTestDT;
    }


    /**
     * convertTreatmentToRequest method generates the drop down list forTreatments/Treatments Drugs
     * for a given condition code.
     * @param type : String type valid values are CREATE, EDIT, VIEW
     * @param code : String code
     * @param typeOfDropDowns : to specify whether it is Treatment Drugs or Treatments
     * @param conditionCd : String condition code
     * @return : String value
     */
    public static String convertTreatmentToRequest(String typeOfDropDowns, String type, String code, String conditionCd) {
      StringBuffer sDropDownList = new StringBuffer("");
      Collection<Object>  list = null;
      String key = null;
      String val = null;
      SRTCacheManager srtManager = null;
      Map<Object,Object> map = new HashMap<Object,Object>();
      boolean isCodeInvalid = false;
      boolean isCodeValid = false;
      if (conditionCd != null)
        map.put(SRTFilterKeys.CONDITION_CODE, conditionCd);
      try {
        NedssUtils nedssUtils = new NedssUtils();
        Object objref = nedssUtils.lookupBean(JNDINames.SRT_CACHEMANAGER_EJB);
        SRTCacheManagerHome home = (SRTCacheManagerHome)
                                   javax.rmi.PortableRemoteObject.narrow(objref, SRTCacheManagerHome.class);
        srtManager = home.create();
        if (type.equalsIgnoreCase(NEDSSConstants.TREATMENT_SRT_CREATE)
            || type.equalsIgnoreCase(NEDSSConstants.TREATMENT_SRT_EDIT)
            || type.equalsIgnoreCase(NEDSSConstants.TREATMENT_SRT_VIEW)) {
          if (typeOfDropDowns.equalsIgnoreCase(NEDSSConstants.TREATMENT_SRT_DRUGS))
            list = srtManager.getTreatmentDrugs(map);
          else if (typeOfDropDowns.equalsIgnoreCase(NEDSSConstants.TREATMENT_SRT_VALUES)) {
            list = srtManager.getTreatments(map);
          }
          else
            logger.error("Wrong condition:type of dropdown required in not valid");
          if (list != null) {
           Iterator<Object>  itr = list.iterator();
            int i = 0;
            int maxDropDownList= 0;
            if (propUtil.getMaxDropDownCount() != null )
            {
              maxDropDownList = new Integer(propUtil.getMaxDropDownCount()).intValue();
            }

            while (itr.hasNext()) {
              SRTCode sRTLabTestDT = (SRTCode) itr.next();
              key = sRTLabTestDT.getCode();
              val = sRTLabTestDT.getDesc();
              if (code != null && code.equalsIgnoreCase(key) && !isCodeValid) {
                isCodeValid = true;
              }
              if (code != null && !code.equalsIgnoreCase(key) && !isCodeInvalid) {
                isCodeInvalid = true;
              }
              if (val != null) {
                sDropDownList.append(key).append(NEDSSConstants.SRT_PART).append(val)
                    .append(NEDSSConstants.SRT_LINE);
              }
            }
            if (!isCodeValid && isCodeInvalid && type.equalsIgnoreCase(NEDSSConstants.TREATMENT_SRT_EDIT) && code != null) {
              list = srtManager.getInvestigationReportingSource(new HashMap<Object,Object>());
              if (list != null) {
               Iterator<Object>  itrator = list.iterator();
                while (itr.hasNext()) {
                  SRTCode sRTLabTestDT = (SRTCode) itr.next();
                  key = sRTLabTestDT.getCode();
                  val = sRTLabTestDT.getDesc();
                  if (code.equalsIgnoreCase(key)) {
                    //key found
                    if (val != null) {
                      sDropDownList.append(key).append(NEDSSConstants.SRT_PART).append(val)
                          .append(NEDSSConstants.SRT_LINE);
                    }
                    i++;
                    if(i == maxDropDownList )
                      break;
                  }
                }
              }
            }
          }
        }
        else if (type.equalsIgnoreCase(NEDSSConstants.TREATMENT_SRT_VIEW)) {
          if (typeOfDropDowns.equalsIgnoreCase(NEDSSConstants.TREATMENT_SRT_DRUGS))
            list = srtManager.getTreatmentDrugs(map);
          else if (typeOfDropDowns.equalsIgnoreCase(NEDSSConstants.TREATMENT_SRT_VALUES))
            list = srtManager.getTreatments(map);
          else
            logger.error("Wrong condition:type of dropdown required in not valid");

          if (list != null) {
           Iterator<Object>  itr = list.iterator();
            int i = 0;
            int maxDropDownList= 0;
            while (itr.hasNext()) {
              SRTCode sRTLabTestDT = (SRTCode) itr.next();
              key = sRTLabTestDT.getCode();
              val = sRTLabTestDT.getDesc();
              if (code != null && code.equalsIgnoreCase(key))
                if (val != null) {
                  sDropDownList.append(key).append(NEDSSConstants.SRT_PART).append(val)
                      .append(NEDSSConstants.SRT_LINE);
                }
              i++;
              if(i == maxDropDownList )
                break;
            }
          }
    }

  }
  catch (javax.ejb.CreateException e) {
    logger.error("NedssCodeLookupServlet:CreateException thrown from SRT CachemanageEjb " + e);
  }
  catch (java.rmi.RemoteException e) {
    logger.error("NedssCodeLookupServlet:RemoteException thrown from SRT CachemanageEjb " + e);
  }
  catch (gov.cdc.nedss.systemservice.exception.SRTCacheManagerException e) {
    logger.error("NedssCodeLookupServlet:SRTCacheManagerException thrown from SRT CachemanageEjb " + e);
  }
  return sDropDownList.toString();
}
	private static String getStateDescTxt(String sStateCd) {

		String desc = "";
		if (sStateCd != null && !sStateCd.trim().equals("")) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
			if (treemap != null) {
				if (sStateCd != null && treemap.get(sStateCd) != null) {
					desc = (String) treemap.get(sStateCd);
				}
			}
		}

		return desc;
	}


 }