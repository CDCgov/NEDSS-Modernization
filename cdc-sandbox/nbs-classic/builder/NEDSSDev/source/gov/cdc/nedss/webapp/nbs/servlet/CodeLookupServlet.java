package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.util.DisplayOrganizationList;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationSrchResultVO;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
import gov.cdc.nedss.entity.person.vo.ProviderSrchResultVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nmallela
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CodeLookupServlet extends HttpServlet {

	static final LogUtils logger = new LogUtils(CodeLookupServlet.class
			.getName());

	static final PropertyUtil propUtil = PropertyUtil.getInstance();

	/**
	 * servlet defined method, hence no implemetations.
	 */
	public void destroy() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String CONTENT_TYPE = "";
		CONTENT_TYPE = "text/html;charset=UTF-8";

		response.setContentType(CONTENT_TYPE);
		String name = request.getParameter("name");
		String entityType = request.getParameter("entityType");
		
		if(entityType==null)
			entityType = request.getParameter("amp;entityType");
		
		String quickCode = request.getParameter("quickCode");
		if(quickCode==null)
			quickCode = request.getParameter("amp;quickCode");
		
		String uid = request.getParameter("UID");
		if(uid==null)
			uid=request.getParameter("amp;UID");
		boolean found = false;

		String result = "";
		String uidValue = "";

		String hiddenFld = name.replaceAll("entity-table-","");
		hiddenFld = HTMLEncoder.encodeHtml(hiddenFld.concat("-values"));

		if (entityType!= null && entityType.equalsIgnoreCase("organization")) {
			HashMap<Object, Object> map = null;
			try {
				map = (HashMap<Object, Object>) getOrganization(quickCode, request.getSession());
			} catch (Exception e) {
				logger.error("Error in  finding ORG: " + e);
				e.printStackTrace();
			}
			String uidString = "";
			if (map.get("UID") != null) {
				uidString = map.get("UID").toString();
				uidValue = HTMLEncoder.encodeHtml(((Long) map.get("UID")).toString());
			}
			result = map.get("result").toString();
		}

		if (entityType!= null && entityType.equalsIgnoreCase("provider")) {

			HashMap<Object, Object> map = null;
			try {
				map = (HashMap<Object, Object>) getProvider(quickCode, request.getSession());
			} catch (Exception e) {
				logger.error("Error in  finding PRV: " + e);
				e.printStackTrace();
			}
			if (map.get("UID") != null) {
				uidValue = HTMLEncoder.encodeHtml(((Long) map.get("UID")).toString());
			}
			result = map.get("result").toString();
		}

		if ((result.length() == 0) || (result.trim().length() == 0)) {
			result = "<font color=red><b>Code Lookup of '"
					+ HTMLEncoder.encodeHtml(quickCode)
					+ "' does not match any found in the system. Please modify the entry and try again, or use the Search function to find the data you need.</b></font>";

		} else {
			found = true;
		}

		StringBuffer sbHTML = new StringBuffer("");

		PrintWriter out = response.getWriter();
		sbHTML.append("<html lang=\"en\"><head><title>Code Lookup</title>");

		HttpSession session = request.getSession(true);
		NBSSecurityObj nbsSecurityObject = (NBSSecurityObj) session
				.getAttribute("NBSSecurityObject");

		if (nbsSecurityObject == null) {
			sbHTML
					.append("\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">");
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

		sbHTML
				.append("\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\" SRC=\"\\nbs\\resource\\javascript\\validate.js\">");
		sbHTML.append("\n</SCRIPT>");

		sbHTML
				.append("\n<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">");

		sbHTML.append("\nfunction goBack(){");
		sbHTML.append("\nvar win = window.opener;\nvar doc = win.document;");

		sbHTML.append("\nvar uidHolder = doc.getElementById(" + "\"" + HTMLEncoder.encodeHtml(uid) + "\"" + ");");
		sbHTML.append("\n if(uidHolder!= null){");
		sbHTML.append("\n uidHolder.value = " + "\"" + uidValue + "\"" + ";");
		sbHTML.append("\n }");

		sbHTML.append("\nvar entityTable = doc.getElementById(" + "\"" + HTMLEncoder.encodeHtml(name)
				+ "\"" + ");");
		//check for displaying Date Assigned to Investigator
		if(name != null && name.equalsIgnoreCase("entity-table-investigator.personUid")) {
			sbHTML.append("\nvar dateAssignedNode = doc.getElementById(\"dateAssignedToInvestigation\");");
			sbHTML.append("\nif(dateAssignedNode != null) {");
			sbHTML.append("\nif(dateAssignedNode.value.length > 0)");
			sbHTML.append("\ndateAssignedNode.value=\"\";");
			sbHTML.append("\n}");
			sbHTML.append("\nvar trNode = doc.getElementById(\"dateAssignedToInvestigation-tdCell\");");
			sbHTML.append("\nif(trNode != null){");
			if (found == false) {			
				sbHTML.append("\ntrNode.className=\"none\";");
			} else {
				sbHTML.append("\ntrNode.className=\"visible\";");
			}
			sbHTML.append("\n}");

		}

		sbHTML
				.append("\nvar spans = entityTable.getElementsByTagName(\"span\");");
		sbHTML.append("\nfor(var i=0;i<spans.length;i++){");

		sbHTML.append("\n{");
		sbHTML
				.append("spans.item(i).innerHTML = " + "\"" + result + "\""
						+ ";");

		sbHTML.append("\nvar hfHolder = doc.getElementById(" + "\"" + hiddenFld
				+ "\"" + ");");
		sbHTML.append("\n if(hfHolder!= null){");
		sbHTML.append("\n hfHolder.value = " + "\"" + result + "\"" + ";");
		sbHTML.append("\n }");

		sbHTML.append("\n}");
		sbHTML.append("\n}");
		sbHTML.append("window.close();");
		sbHTML.append("\n}");
		sbHTML.append("</SCRIPT>");
		sbHTML.append("</head>");
		sbHTML.append("<body>");

		sbHTML
				.append("<SCRIPT Type=\"text/javascript\" Language=\"JavaScript\">goBack();</SCRIPT>");
		sbHTML.append("</body></html>");
		out.println(HTMLEncoder.sanitizeHtml(sbHTML.toString()));
	}

	/**
	 * Servlet defined method
	 *
	 * @throws ServletException
	 */
	public void init() throws ServletException {
	}

	/**
	 *
	 * @param code :
	 *            String
	 * @param session :
	 *            HttpSession
	 * @return Map
	 * @throws Exception
	 */
	private Map<Object,Object> getOrganization(String code, HttpSession session) throws Exception {
		Map<Object, Object> returnMap = new HashMap<Object,Object>();
		Long organizationUID = null;
		//String CliaWithConditions = "";
		StringBuffer result = new StringBuffer("");
		OrganizationSearchVO orgSearchVO = new OrganizationSearchVO();
		orgSearchVO.setRootExtensionTxt(code);
		orgSearchVO.setTypeCd("QEC");
		ArrayList<?> alist = null;
		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
		String sMethod = "findOrganization";
		Object[] oParams = new Object[] { orgSearchVO,
				new Integer(propUtil.getNumberOfRows()), new Integer(0) };
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
				oParams);
		alist = (ArrayList<?> ) arr.get(0);
		Iterator<?> it = alist.iterator();
		if (alist != null) {
			while (it.hasNext()) {
				DisplayOrganizationList displayOrganizationList = (DisplayOrganizationList) it
						.next();
				if (displayOrganizationList != null) {
					ArrayList<Object> list = displayOrganizationList.getList();
					if (list != null) {
						Iterator<Object> orgSearchResultIt = list.iterator();
						while (orgSearchResultIt.hasNext()) {
							OrganizationSrchResultVO srchResultVO = (OrganizationSrchResultVO) orgSearchResultIt
									.next();
							organizationUID = srchResultVO.getOrganizationUID();
							if (srchResultVO.getOrganizationNameColl() != null) {
								Iterator<Object> orgNameIt = srchResultVO
										.getOrganizationNameColl().iterator();
								while (orgNameIt.hasNext()) {
									OrganizationNameDT orgName = (OrganizationNameDT) orgNameIt
											.next();
									result.append(HTMLEncoder.encodeHtml(orgName.getNmTxt()));
								}
							}
							if (srchResultVO.getOrganizationLocatorsColl() != null) {
								if (srchResultVO.getOrganizationLocatorsColl() != null) {
									Iterator<Object> orgLocatorIt = srchResultVO
											.getOrganizationLocatorsColl()
											.iterator();
									while (orgLocatorIt.hasNext()) {
										EntityLocatorParticipationDT entityLocatorDT = (EntityLocatorParticipationDT) orgLocatorIt
												.next();
										CachedDropDownValues cachedValues = new CachedDropDownValues();
										String codeTranslated = cachedValues
												.getCodeDescTxt(NEDSSConstants.WORK_PHONE);
										if (entityLocatorDT.getCd() != null
												&& entityLocatorDT
														.getCd()
														.equalsIgnoreCase(
																NEDSSConstants.CODE_O)
												&& entityLocatorDT.getClassCd() != null
												&& entityLocatorDT
														.getClassCd()
														.equalsIgnoreCase(
																NEDSSConstants.POSTAL)
												&& entityLocatorDT
														.getCdDescTxt() != null
												&& entityLocatorDT
														.getCdDescTxt()
														.equalsIgnoreCase(
																NEDSSConstants.OFFICE)
												&& entityLocatorDT.getUseCd() != null
												&& entityLocatorDT.getUseCd()
														.equalsIgnoreCase(
																codeTranslated)) {
											if (entityLocatorDT != null) {
												PostalLocatorDT postaLocatorDT = entityLocatorDT
														.getThePostalLocatorDT();
												if (postaLocatorDT != null) {
													if (postaLocatorDT
															.getStreetAddr1() != null) {
														result
																.append("<br>")
																.append(
																		HTMLEncoder.encodeHtml(postaLocatorDT.getStreetAddr1()));
													}
													if (postaLocatorDT
															.getStreetAddr2() != null) {
														result
																.append("<br>")
																.append(
																		HTMLEncoder.encodeHtml(postaLocatorDT.getStreetAddr2()));
													}
													if (postaLocatorDT
															.getCityCd() != null) {
														result
																.append("<br>")
																.append(
																		HTMLEncoder.encodeHtml(postaLocatorDT.getCityCd()));

													}
													if (postaLocatorDT
															.getStateCd() != null) {

														result
																.append(", ")
																.append(
																		HTMLEncoder.encodeHtml(getStateDescTxt(postaLocatorDT.getStateCd())));

													}
													if (postaLocatorDT
															.getZipCd() != null) {
														result
																.append(" ")
																.append(
																		HTMLEncoder.encodeHtml(postaLocatorDT.getZipCd()));

													}
												}
											}
										}
										if (entityLocatorDT != null
												&& entityLocatorDT.getUseCd() != null
												&& entityLocatorDT.getUseCd()
														.trim() != "") {
											codeTranslated = cachedValues
													.getCodeDescTxt(NEDSSConstants.WORK_PHONE);
											if (entityLocatorDT.getUseCd()
													.equalsIgnoreCase(
															codeTranslated)
													&& entityLocatorDT
															.getClassCd() != null
													&& entityLocatorDT
															.getClassCd()
															.equalsIgnoreCase(
																	NEDSSConstants.TELE)
													&& entityLocatorDT
															.getUseCd() != null
													&& entityLocatorDT
															.getUseCd()
															.equalsIgnoreCase(
																	codeTranslated)) {

												TeleLocatorDT telelocatorDT = entityLocatorDT
														.getTheTeleLocatorDT();
												if (telelocatorDT != null) {
													if (telelocatorDT
															.getPhoneNbrTxt() != null) {
														result
																.append("<br>")
																.append(
																		HTMLEncoder.encodeHtml(telelocatorDT.getPhoneNbrTxt()));
													}
													if (telelocatorDT
															.getExtensionTxt() != null) {
														result
																.append(
																		" <b>Ext. </b>")
																.append(
																		HTMLEncoder.encodeHtml(telelocatorDT.getExtensionTxt()));
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
		returnMap.put("result", result.toString());

		return returnMap;

	}

	/**
	 * For a given Quick entry code, gets the person uid and result from the
	 * backend. Type defines the different types of person that returns
	 * different types of data from the backend.
	 *
	 * @param type :
	 *            String
	 * @param code :
	 *            String
	 * @param session :
	 *            HttpSession
	 * @return : Map
	 * @throws Exception
	 */
	public Map<Object,Object> getProvider(String code, HttpSession session)
			throws Exception {
		Map<Object, Object> returnMap = new HashMap<Object,Object>();
		Long personUID = null;
		StringBuffer result = new StringBuffer("");
		ProviderSearchVO provSearchVO = new ProviderSearchVO();
		provSearchVO.setRootExtensionTxt(code);
		provSearchVO.setTypeCd("QEC");
		ArrayList<?> alist = null;
		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
		String sMethod = "findProvider";
		Object[] oParams = new Object[] { provSearchVO,
				new Integer(propUtil.getNumberOfRows()), new Integer(0) };
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
				oParams);
		alist = (ArrayList<?> ) arr.get(0);
		Iterator<?> it = alist.iterator();
		if (alist != null) {
			while (it.hasNext()) {
				DisplayPersonList displayPersonList = (DisplayPersonList) it
						.next();
				if (displayPersonList != null) {
					ArrayList<Object> list = displayPersonList.getList();
					if (list != null) {
						Iterator<Object> provSearchResultIt = list.iterator();
						while (provSearchResultIt.hasNext()) {
							ProviderSrchResultVO srchResultVO = (ProviderSrchResultVO) provSearchResultIt
									.next();
							personUID = srchResultVO.getPersonUID();
							if (srchResultVO.getPersonNameColl() != null) {
								Iterator<Object> provNameIt = srchResultVO
										.getPersonNameColl().iterator();
								while (provNameIt.hasNext()) {
									PersonNameDT provName = (PersonNameDT) provNameIt
											.next();
									if (provName.getFirstNm() != null) {
										result.append(HTMLEncoder.encodeHtml(provName.getFirstNm()));
									}
									if (provName.getLastNm() != null) {
										result.append(" ").append(
												HTMLEncoder.encodeHtml(provName.getLastNm()));
									}
									if (provName.getNmSuffix() != null) {
										result.append(", ").append(
												HTMLEncoder.encodeHtml(provName.getNmSuffix()));
									}
									if (provName.getNmDegree() != null) {
										result.append(", ").append(
												HTMLEncoder.encodeHtml(provName.getNmDegree()));
									}
								}
							}
							if (srchResultVO.getPersonLocatorsColl() != null) {
								if (srchResultVO.getPersonLocatorsColl() != null) {
									Iterator<Object> provLocatorIt = srchResultVO
											.getPersonLocatorsColl().iterator();
									while (provLocatorIt.hasNext()) {
										CachedDropDownValues cachedValues = new CachedDropDownValues();
										String codeTranslated = cachedValues
												.getCodeDescTxt(NEDSSConstants.WORK_PHONE);
										EntityLocatorParticipationDT entityLocatorDT = (EntityLocatorParticipationDT) provLocatorIt
												.next();
										if (entityLocatorDT.getCd() != null
												&& entityLocatorDT
														.getCd()
														.equalsIgnoreCase(
																NEDSSConstants.CODE_O)
												&& entityLocatorDT.getClassCd() != null
												&& entityLocatorDT
														.getClassCd()
														.equalsIgnoreCase(
																NEDSSConstants.POSTAL)
												&& entityLocatorDT.getUseCd() != null
												&& entityLocatorDT.getUseCd()
														.equalsIgnoreCase(
																codeTranslated))

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

						                            result.append(", ").append(getStateDescTxt(HTMLEncoder.encodeHtml(postaLocatorDT.getStateCd())));
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
						}
					}
				}
			}
		}
		returnMap.put("UID", personUID);
		returnMap.put("result", result.toString());
		return returnMap;
	}

	private String getStateDescTxt(String sStateCd) {

		String desc = "";
		if (sStateCd != null && !sStateCd.trim().equals("")) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap<Object, Object> treemap = srtValues.getStateCodes2("USA");
			if (treemap != null) {
				if (sStateCd != null && treemap.get(sStateCd) != null) {
					desc = (String) treemap.get(sStateCd);
				}
			}
		}

		return desc;
	}
}