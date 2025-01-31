package gov.cdc.nedss.webapp.nbs.action.nbssecurity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.PermissionSet;
import gov.cdc.nedss.systemservice.nbssecurity.RealizedRole;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.XMLRequestHelper;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;
import gov.cdc.nedss.webapp.nbs.form.nbssecurity.UserForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class UserLoad
    extends Action
{

  static final LogUtils logger = new LogUtils(UserLoad.class.getName());
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

    
  public UserLoad()
  {
  }

  private void initDebug()
  {

  }

  public ActionForward execute(ActionMapping mapping,
			       ActionForm form,
			       HttpServletRequest request,
			       HttpServletResponse response) throws IOException,
      ServletException
  {
	  String strType = "";
	Collection<?>  permSetColl =null;
    ErrorMessageHelper.setErrMsgToRequest(request, "ps143");
    UserForm userForm = (UserForm) form;
    HttpSession session = request.getSession(false);
    NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
    boolean isUserMSA = secObj.isUserMSA();
    boolean isUserPAA = secObj.isUserPAA();
    request.setAttribute("IsUserMSA", new Boolean(isUserMSA));
    request.setAttribute("IsUserPAA", new Boolean(isUserPAA));

    if(isUserPAA)
    {
      String paaProgramAreas = secObj.getTheUserProfile().getTheUser().getPaaProgramArea();
      StringBuffer sbsPaaProgramArea = new StringBuffer();

      for(int i = 0, j = 0;i < paaProgramAreas.length();)
      {
	j = paaProgramAreas.indexOf("|", i);
	String paaProgramAreaCd = paaProgramAreas.substring(i, j);
	String programName = getProgramAreaDescTxt(paaProgramAreaCd);
	sbsPaaProgramArea.append(paaProgramAreaCd).append("$").append(programName).append("|");
	i=j+1;
      }
      request.setAttribute("paaProgramAreaList", sbsPaaProgramArea.toString());
    }

    try
    {

      MainSessionCommand msCommand = null;
      MainSessionCommand msCommand1 = null;
  	String sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;
      String sMethod = "getPermissionSetList";

      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, null);
      Collection<?>   allPermsColl = (Collection<?>) arr.get(0);

      StringBuffer sb = new StringBuffer();
     Iterator<?>  iter = allPermsColl.iterator();

      String permsStr = "";

      while (iter.hasNext())
      {
	permsStr = (String) iter.next();
	sb.append(permsStr).append("$").append(permsStr).append("|");
	logger.info(permsStr + " => " + permsStr);
      }
      permsStr = sb.toString();
      request.setAttribute("AllPermissionSetsStr", permsStr);

      //--- getting the permissionSets 
      String sBeanJndiName1 = JNDINames.NBS_DB_SECURITY_EJB;
      
      String sMethod1 = "getPermissionSets";
      MainSessionHolder mainSessionHolder = new MainSessionHolder();	
      msCommand1 = mainSessionHolder.getMainSessionCommand(session);
      ArrayList<?> arr1 = (ArrayList<?> ) msCommand1.processRequest(sBeanJndiName1,
	  sMethod1, null);
      permSetColl = (Collection<?>) arr1.get(0);

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    strType = request.getParameter("OperationType");
    String strUID = request.getParameter("userID");
    if (strUID == null)
    {
      strUID = (String) request.getAttribute("userID");
    }
    //  VIEW action
    if (strType.equalsIgnoreCase(NEDSSConstants.VIEW))
    {
      UserProfile userProfile = getOldUserProfile(strUID, userForm, session);

      //check security for buttons
      boolean strTest = false;
      userForm.reset();
      if (strTest == false)
      {
	if (userProfile != null)
	{
	  convertUserToRequestObj(userProfile, secObj,request,   permSetColl ,   strType  );
	  if (userProfile.getTheUser_s().getReportingFacilityUid() != null)
	  {
	    OrganizationVO orgVO = getFacilityDetails(userProfile.
		getTheUser_s().getReportingFacilityUid(), session, request);
	    if (orgVO !=null){
	      OrganizationNameDT orgName = (OrganizationNameDT) orgVO.
		  getTheOrganizationNameDTCollection().iterator().next();
	      convertOrganizationToRequestObj(orgVO, request);
	    }
	  }
	  if (userProfile.getTheUser_s().getProviderUid() != null)
	  {
	    PersonVO prvVO = getProviderDetails(userProfile.
		getTheUser_s().getProviderUid(), session, request);
	    if (prvVO !=null){
	      convertProviderToRequestObj(prvVO, request);
	    }
	  }
	  userForm.setUserProfile(userProfile);
	}
	else
	{
	  request.setAttribute("doesnotexist", "true");
	  return mapping.findForward("nousererror");
	}
      }
      else
      {
    	  throw new ServletException();
      }

      String sPaaProgramArea = userProfile.getTheUser().getPaaProgramArea();

      StringBuffer sbsPaaProgramArea = new StringBuffer();
      for(int i = 0, j = 0;i < sPaaProgramArea.length();)
      {
	j = sPaaProgramArea.indexOf("|", i);
	String paaProgramAreaCd = sPaaProgramArea.substring(i, j);
	String programName = getProgramAreaDescTxt(paaProgramAreaCd);
	sbsPaaProgramArea.append(programName);

	if(j != (sPaaProgramArea.length()-1))
	   sbsPaaProgramArea.append(", ");

	i=j+1;
      }
      request.setAttribute("paaProgramArea", sbsPaaProgramArea.toString());
      return mapping.findForward("view");
    }
    //  CREATE
    else if (strType.equalsIgnoreCase(NEDSSConstants.CREATE))
    {

      String duperror = (String) session.getAttribute("duperrorvalue");
      //check to see if duplicate error in which case the form needs to be re-populated with old values

      if (duperror == null)
      {
	userForm.reset();

	// need to put something in the drop down -- dont know what this means
	request.setAttribute("Jurisdiction", "+|");
	request.setAttribute("ProgramArea", "+|");
	request.setAttribute("PermissionSet", "+|");

	session.setAttribute("duperrorvalue", null);
      } //end of if
      else
      {
	UserProfile userProfile = userForm.getUserProfile();
	if (userProfile != null)
	{
	  convertUserToRequestObj(userProfile, secObj,request, permSetColl ,   strType );
	  userForm.setUserProfile(userProfile);
	} //end of if
	session.setAttribute("duperrorvalue", null);

      } //end of else

      return mapping.findForward("create");
    } //end of if
    //  EDIT
    else if (strType.equalsIgnoreCase(NEDSSConstants.EDIT))
    {
      //System.out.println("YOU ARE TRYING TO EDIT");
      userForm.reset();
      // need to set all the roles to dirty after getting the collection

      if (!strUID.equals(null))
      {
	UserProfile userProfile = getOldUserProfile(strUID, userForm, session);

	convertUserToRequestObj(userProfile, secObj, request,  permSetColl ,   strType  );
	//get the facility details

	if (userProfile != null &&
	    userProfile.getTheUser_s().getReportingFacilityUid() != null)
	{
	  OrganizationVO orgVO = getFacilityDetails(userProfile.getTheUser_s().
	      getReportingFacilityUid(), session, request);
	  if (orgVO !=null){
		  OrganizationNameDT orgName = (OrganizationNameDT) orgVO.
	      getTheOrganizationNameDTCollection().iterator().next();
	  //System.out.println("orgName in EDIT is: " + orgName.getNmTxt());
		  convertOrganizationToRequestObj(orgVO, request);
	  }
	}
	if (userProfile.getTheUser_s().getProviderUid() != null)
	  {
	    PersonVO prvVO = getProviderDetails(userProfile.
		getTheUser_s().getProviderUid(), session, request);
	    if (prvVO !=null){
	      convertProviderToRequestObj(prvVO, request);
	    }
	  }

	userForm.setUserProfile(userProfile);
	if(secObj.isUserMSA())
	{
	  String sPaaProgramArea = userProfile.getTheUser().getPaaProgramArea();
	  request.setAttribute("paaProgramArea", sPaaProgramArea);
	}
	else
	{
	  String sPaaProgramArea = userProfile.getTheUser().getPaaProgramArea();

	  StringBuffer sbsPaaProgramArea = new StringBuffer();
	  for (int i = 0, j = 0; i < sPaaProgramArea.length(); )
	  {
	    j = sPaaProgramArea.indexOf("|", i);
	    String paaProgramAreaCd = sPaaProgramArea.substring(i, j);
	    String programName = getProgramAreaDescTxt(paaProgramAreaCd);
	    sbsPaaProgramArea.append(programName);

	    if (j != (sPaaProgramArea.length() - 1))
	      sbsPaaProgramArea.append(", ");

	    i = j + 1;
	  }
	  request.setAttribute("paaProgramArea", sbsPaaProgramArea.toString());
	}

	return mapping.findForward("edit");

      }
      else
      {
	logger.error("!!!!!!!!!!!!    no organization UID for edit");
	throw new ServletException("!!!!!!!!!!!!    no organization UID for edit");
      }

    }
    //  NO OPERATION TYPE
    else
    {
      return (mapping.findForward("error"));
    }

  }

  private UserProfile getOldUserProfile(String strUID, UserForm form,
					HttpSession session)
  {
    UserProfile userProfile = null;
    MainSessionCommand msCommand = null;

    if (strUID != null)
    {
      try
      {

	//System.out.println("Looking up user: " + strUID);
    String sBeanJndiName = JNDINames.NBS_DB_SECURITY_EJB;	
	String sMethod = "getUser";
	Object[] oParams = new Object[]
	    {
	    strUID};

	MainSessionHolder holder = new MainSessionHolder();
	msCommand = holder.getMainSessionCommand(session);

	//System.out.println("StrUID " + strUID);

	ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
						 oParams);

	userProfile = (UserProfile) arr.get(0);
	// To make sure LDAP jurisdictionCode 'All' letter case matches with the Dropdown letter case
	 
	if (userProfile != null
						&& userProfile.getTheRealizedRoleCollection() != null
						&& userProfile.getTheRealizedRoleCollection().size() > 0) {
					Iterator<Object> ite = ((Collection<Object>) userProfile
							.getTheRealizedRoleCollection()).iterator();
					while (ite.hasNext()) {
						RealizedRole rRole = (RealizedRole) ite.next();
						if (rRole.getJurisdictionCode() != null
								&& rRole.getJurisdictionCode()
										.equalsIgnoreCase(NEDSSConstants.ALL)) {
							rRole.setJurisdictionCode(NEDSSConstants.ALL);
						}
					}
				}
	//System.out.println("%%%%%UserProfile: " + userProfile);
	//System.out.println("$$$$FacilityID: " + userProfile.getTheUser_s().getReportingFacilityUid());
      }
      catch (Exception ex)
      {
	if (session == null)
	{
	  logger.error("Error: no session, please login");
	}
	logger.fatal("getOldUserProfile(): ", ex);
      }
    }
    return userProfile;
  }

  /**
   * Formats the TimeStamp to MM/DD/YYYY form
   * @param timestamp    the java.sql.Timestamp
   * @return    the String of Date in MM/DD/YYYY format
   */
  private String formatDate(java.sql.Timestamp timestamp)
  {

    Date date = null;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    logger.info(
	"InvestigationMeaslesPreAction.formatDate: date: " +
	timestamp);

    if (timestamp != null)
    {
      date = new Date(timestamp.getTime());

    }
    logger.info("InvestigationMeaslesPreAction.formatDate: date: " +
		date);

    if (date == null)
    {

      return "";
    }
    else
    {

      return formatter.format(date);
    }
  }

  /**
   * Converts the values of OrganizationVO to  HttpServletRequest
   * @param organizationVO   the OrganizationVO
   * @param request     the HttpServletRequest
   */
  private void convertOrganizationToRequestObj(OrganizationVO organizationVO,
					       HttpServletRequest request)
  {

    Long organizationUID = null;
    String localID = "";
    String organizationName = "";
    String orgNmUseCd = "";
    Integer orgNmSeq = null;
    logger.info("organizationVO class is - " + organizationVO);
    String reportingSourceDetails = "";

    if (organizationVO != null &&
	organizationVO.getTheOrganizationDT() != null)
    {

      //logger.debug("organizationVO class is - " + organizationVO);
      OrganizationDT organization = organizationVO.getTheOrganizationDT();

      //for the top bar
      request.setAttribute("reportingOrgUID",
			   organization.getOrganizationUid().toString());
      localID = organization.getLocalId();

      //to persist this information for query string or input element
      request.setAttribute("organizationUID",
			   String.valueOf(organization.getUid()));
      organizationUID = organization.getUid();

      request.setAttribute("recordStatusCd",
			   organization.getRecordStatusCd());

      Collection<Object>  names = organizationVO.getTheOrganizationNameDTCollection();

      if (names == null)
      {
	logger.debug("Names = null");
      }

      if (names != null)
      {
	Iterator<Object> iter = names.iterator();
	StringBuffer sNamesCombined = new StringBuffer("");

	while (iter.hasNext())
	{

	  OrganizationNameDT name = (OrganizationNameDT) iter.next();
	  if (name != null)
	  {
	    if (name.getNmTxt() != null)
	    {
	      reportingSourceDetails = reportingSourceDetails + name.getNmTxt();
	    }
	    logger.debug("Inside name dt");
	    if (name.getNmTxt() != null)
	    {
	      organizationName = name.getNmTxt(); //organizationName

	      if (name.getNmUseCd() != null)
	      {
		orgNmUseCd = name.getNmUseCd();
		request.setAttribute("orgNmUseCd", orgNmUseCd);
	      }

	      if (name.getOrganizationNameSeq() != null)
	      {
		orgNmSeq = name.getOrganizationNameSeq();
		request.setAttribute("orgNmSeq", orgNmSeq);
	      }
	    }
	  }
	}
      }

      StringBuffer sParsedAddresses = new StringBuffer("");
      StringBuffer sParsedTeles = new StringBuffer("");
      StringBuffer sParsedLocators = new StringBuffer("");
      Collection<Object>  addresses = organizationVO.
	  getTheEntityLocatorParticipationDTCollection();
      if (addresses != null)
      {

	Iterator<Object> iter = addresses.iterator();

	while (iter.hasNext())
	{

	  EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
	      iter.next();

	  if (elp != null)
	  {

	    if (elp.getRecordStatusCd() != null &&
		elp.getClassCd() != null &&
		elp.getClassCd().equals("PST"))
	    {

	      PostalLocatorDT postal = elp.getThePostalLocatorDT();

	      logger.info("Creating parsed string for addresses");

	      ArrayList<Object> stateList = new ArrayList<Object> ();
	      if (postal.getStreetAddr1() != null)
	      {
		reportingSourceDetails = reportingSourceDetails + "<br/>" +
		    postal.getStreetAddr1();
		//request.setAttribute("street1", postal.getStreetAddr1());
	      }

	      if (postal.getStreetAddr2() != null)
	      {
		reportingSourceDetails = reportingSourceDetails + "<br/>" +
		    postal.getStreetAddr2();
		//request.setAttribute("street2", postal.getStreetAddr2());
	      }

	      if (postal.getCityDescTxt() != null)
	      {
		reportingSourceDetails = reportingSourceDetails + "<br/>" +
		    postal.getCityDescTxt() + ", ";
		//request.setAttribute("reportingCity", postal.getCityDescTxt());
	      }

	      if (postal.getStateCd() != null)
	      {
		reportingSourceDetails = reportingSourceDetails +
		    getStateDescTxt(postal.getStateCd() + " ");
		//request.setAttribute("reportingState", postal.getStateCd());
	      }

	      if (postal.getZipCd() != null)
	      {
		reportingSourceDetails = reportingSourceDetails +
		    postal.getZipCd();
		//request.setAttribute("reportingZip", postal.getZipCd());
	      }

	    }

	    //create the telephone parsed string
	    else if (elp.getRecordStatusCd() != null &&
		     elp.getClassCd() != null &&
		     elp.getClassCd().equals("TELE"))
	    {

	      TeleLocatorDT tele = elp.getTheTeleLocatorDT();

	      //Set values to request attributes
	      if (tele.getPhoneNbrTxt() != null)
	      {
		reportingSourceDetails = reportingSourceDetails + "<br/>" +
		    tele.getPhoneNbrTxt() + " ";
		//request.setAttribute("reportingPhone", tele.getPhoneNbrTxt());
	      }

	      if (tele.getExtensionTxt() != null)
	      {
		reportingSourceDetails = reportingSourceDetails +
		    "<b>Ext. </b>" + tele.getExtensionTxt();
		//request.setAttribute("reportingExt", tele.getExtensionTxt());
	      }

	    }
	  }
	}

	request.setAttribute("reportingSourceDetails", reportingSourceDetails);
	request.setAttribute("addresses", sParsedAddresses.toString());
	request.setAttribute("parsedTelephoneString",
			     sParsedTeles.toString());
	request.setAttribute("parsedLocatorsString",
			     sParsedLocators.toString());
      }
    }
  }
  
  /**
   * Converts the values of providerVO to  HttpServletRequest
   * @param providerVO   the providerVO
   * @param request     the HttpServletRequest
   */
  private void convertProviderToRequestObj(PersonVO providerVO,
					       HttpServletRequest request)
  {

    logger.info("personVO class is - " + providerVO);
    String providerDetails = "";

    if (providerVO != null &&
    		providerVO.getThePersonDT() != null)
    {
    	QuickEntryEventHelper helper =  new QuickEntryEventHelper();
    	providerDetails = helper.makePRVDisplayString(providerVO);
    	request.setAttribute("investigatorDetails", providerDetails);
    	request.setAttribute("providerUID",providerVO.getThePersonDT().getPersonUid());
    }
  }

  private String getStateDescTxt(String sStateCd)
  {

    CachedDropDownValues srtValues = new CachedDropDownValues();
    TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
    String desc = "";

    if (sStateCd != null && treemap.get(sStateCd) != null)
    {
      desc = (String) treemap.get(sStateCd);

    }
    return desc;
  }

  private String getProgramAreaDescTxt(String sProgAreaCd)
  {

    CachedDropDownValues srtValues = new CachedDropDownValues();
    TreeMap<Object,Object> treemap = srtValues.getProgramAreaCodedValues();
    String desc = "";

    if (sProgAreaCd != null && treemap.get(sProgAreaCd) != null)
    {
      desc = (String) treemap.get(sProgAreaCd);

    }
    return desc;
  }

  private void convertUserToRequestObj(UserProfile userProfile, NBSSecurityObj secObj,
				       HttpServletRequest request , Collection<?>  permSetColl , String strType  )
  {
    String strUID = null;
    String jurisdictionCd = null;
    String programareaCd = null;

    if (userProfile != null && userProfile.getTheUser() != null)
    {
      request.setAttribute("userID", userProfile.getTheUser_s().getUserID());
      String userID = userProfile.getTheUser_s().getUserID();

      request.setAttribute("userID",
			   String.valueOf(userProfile.getTheUser_s().getUserID()));

      request.setAttribute("FirstName",
			   String.valueOf(userProfile.getTheUser_s().
					  getFirstName()));
      request.setAttribute("LastName",
			   String.valueOf(userProfile.getTheUser_s().
					  getLastName()));
      request.setAttribute("Status",
			   String.valueOf(userProfile.getTheUser_s().getStatus()));

      String adminUserType = userProfile.getTheUser_s().getAdminUserType();
      adminUserType.equalsIgnoreCase("");
      if (adminUserType != null)
      {
	if (adminUserType.length() > 3)
	{
	  request.setAttribute("msaString","Y");
	  request.setAttribute("paaString","Y");
	}
	else if (adminUserType.startsWith("M"))
	{
	  request.setAttribute("msaString","Y");
	}
	else if(adminUserType.startsWith("P"))
	  request.setAttribute("paaString","Y");
      }

      if (userProfile.getTheUser_s().getUserType().equals(NEDSSConstants.
	  SEC_USERTYPE_EXTERNAL))
      {
    	  request.setAttribute("ExternalString", "Y");
      }

      if (userProfile.getTheUser_s().getUserType().equals(NEDSSConstants.
	  SEC_USERTYPE_INTERNAL))
      {
	request.setAttribute("ExternalString", "N");
      }
      
      if(userProfile.getTheUser_s().getJurisdictionDerivationInd()!=null)
    	  request.setAttribute("jurisdictionDerivationString", userProfile.getTheUser_s().getJurisdictionDerivationInd());
      
      Collection<Object>  roles = userProfile.getTheRealizedRoleCollection();

      setRoleList(roles, request,permSetColl );

      if (!strType.equalsIgnoreCase(NEDSSConstants.VIEW))
      {
	if (roles != null)
	{
	 Iterator<Object>  iter = roles.iterator();
	  StringBuffer sNamesCombined = new StringBuffer("");

	  int increment = 1;

	  while (iter.hasNext())
	  {
	    RealizedRole name = (RealizedRole) iter.next();
	    String recordStatus = null;
	    if(matchProgramArea(secObj.getTheUserProfile().getTheUser().getPaaProgramArea(), name.getProgramAreaCode()))
	      recordStatus = "A";
	    else
	      recordStatus = "R";

	    if (name != null)
	    {
	      sNamesCombined.append(
		  "userProfile.theRealizedRole_s[i].jurisdictionCode").append(
		  NEDSSConstants.BATCH_PART);
	      if (name.getJurisdictionCode() != null)
	      {
		sNamesCombined.append(name.getJurisdictionCode());
	      }
	      sNamesCombined.append(NEDSSConstants.BATCH_SECT);
	      sNamesCombined.append(
		  "userProfile.theRealizedRole_s[i].programAreaCode").append(
		  NEDSSConstants.BATCH_PART);
	      if (name.getProgramAreaCode() != null)
	      {
		sNamesCombined.append(name.getProgramAreaCode());

	      }
	      sNamesCombined.append(NEDSSConstants.BATCH_SECT);
	      sNamesCombined.append("userProfile.theRealizedRole_s[i].roleName").
		  append(NEDSSConstants.BATCH_PART);
	      if (name.getRoleName() != null)
	      {
		logger.info("name.getRoleName " + name.getRoleName());
		sNamesCombined.append(XMLRequestHelper.urlDecode(name.
		    getRoleName()));
	      }

	      sNamesCombined.append(NEDSSConstants.BATCH_SECT);
	      sNamesCombined.append("userProfile.theRealizedRole_s[i].seqNum").
		  append(NEDSSConstants.BATCH_PART);
	      sNamesCombined.append(increment++);

	      sNamesCombined.append(NEDSSConstants.BATCH_SECT);
	      sNamesCombined.append("userProfile.theRealizedRole_s[i].statusCd").
		  append(NEDSSConstants.BATCH_PART);
	      sNamesCombined.append(recordStatus);
	      name.setStatusCd(recordStatus);

	      sNamesCombined.append(NEDSSConstants.BATCH_SECT);
	      sNamesCombined.append(
		  "userProfile.theRealizedRole_s[i].oldJurisdictionCode").
		  append(NEDSSConstants.BATCH_PART);
	      sNamesCombined.append(name.getJurisdictionCode());

	      sNamesCombined.append(NEDSSConstants.BATCH_SECT);
	      sNamesCombined.append(
		  "userProfile.theRealizedRole_s[i].oldProgramAreaCode").append(
		  NEDSSConstants.BATCH_PART);
	      sNamesCombined.append(name.getProgramAreaCode());

	      sNamesCombined.append(NEDSSConstants.BATCH_SECT);
	      sNamesCombined.append(
		  "userProfile.theRealizedRole_s[i].guestString").append(
		  NEDSSConstants.BATCH_PART);

	      if (name.getGuest())
	      {
		sNamesCombined.append("Y");
	      }
	      sNamesCombined.append("^|");
	    }
	  }
	  request.setAttribute("realizedroles", sNamesCombined.toString());
	}
      }
    }

  }

  private OrganizationVO getFacilityDetails(Long facilityUid,
					    HttpSession session,
					    HttpServletRequest request)
  {

    MainSessionCommand msCommand = null;
    OrganizationVO orgVO = null;
    try
    {
      String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
      String sMethod = "getOrganization";
      logger.debug("calling getOrganization on EntityProxyEJB, via mainsession");

      Object[] oParams =
	  {
	  facilityUid};

      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);

      logger.info("mscommand in InvestigationMeaslesStore is: " + msCommand);
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
      {
	logger.info("GetOrganization work!!!  ");
	orgVO = (OrganizationVO) resultUIDArr.get(0);
      }
    }
    catch (Exception e)
    {
      logger.fatal("ERROR calling mainsession control", e);
    }
    return orgVO;
  }
  
	private PersonVO getProviderDetails(Long providerUid,
			HttpSession session, HttpServletRequest request) {

		MainSessionCommand msCommand = null;
		PersonVO prvVO = null;
		try {
			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "getProvider";
			logger.debug("calling getProvider on EntityProxyEJB, via mainsession");

			Object[] oParams = { providerUid };

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			logger.info("mscommand in InvestigationMeaslesStore is: "
					+ msCommand);
			ArrayList<?> resultUIDArr = new ArrayList<Object>();
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				logger.info("GetProvider work!!!  ");
				prvVO = (PersonVO) resultUIDArr.get(0);
			}
		} catch (Exception e) {
			logger.fatal("ERROR calling mainsession control", e);
		}
		return prvVO;
	}

  private void setRoleList(Collection<Object> roles, HttpServletRequest request,Collection<?>  permSetColl )
  {
    StringBuffer strRoleList = new StringBuffer("");
    String rolename = "";
    MainSessionCommand msCommand = null;
    String sBeanJndiName = "";
    String sMethod = "";

    if (roles == null)
    {
      logger.debug("roles collection is null");
    }
    else
    {
     Iterator<Object>  itr = roles.iterator();
      while (itr.hasNext())
      {
	// reset the variables for not giving edit access for permission sets that are system defined
    String strReadOnly  = "true";
	boolean readOnly = true;

	RealizedRole rr = (RealizedRole) itr.next();

	if (rr.getGuest())
	{
	  rr.setGuestString("Y");
	}
	else
	{
	  rr.setGuestString("");

	}
	if (rr != null)
	{
	  // translate the jurisdiction code to text
	  CachedDropDownValues cash = new CachedDropDownValues();
	  TreeMap<Object,Object> tmap = cash.getJurisdictionCodedValuesWithAll();

	  String jurCode = (String) tmap.get(rr.getJurisdictionCode());
          if(jurCode == null && rr.getJurisdictionCode()!= null && rr.getJurisdictionCode().equalsIgnoreCase(NEDSSConstants.ALL))
            jurCode = NEDSSConstants.ALL;

	  rolename = rr.getRoleName();

	  if (permSetColl != null && permSetColl.size() != 0)
	  {
	    logger.debug("PERMSET COLL IS NOT NULL");
	  }

	 Iterator<?>  permiter = permSetColl.iterator();
	  while (permiter.hasNext())
	  {
	    PermissionSet perm = (PermissionSet) permiter.next();
	    String rolename1 = (String) perm.getRoleName();
	    if (rolename.equals(rolename1))
	    {
	      if (!perm.getReadOnly())
	      {
		readOnly = false;
		strReadOnly = "false";
	      }
	    }

	  }

	  strRoleList.append( (rr.getJurisdictionCode() == null) ? "" : jurCode).
	      append("$");
	  strRoleList.append( (rr.getProgramAreaCode() == null) ? "" :
			     getProgramAreaDescTxt(rr.getProgramAreaCode())).
	      append("$");
	  strRoleList.append("--").append( (rr.getRoleName() == null) ? "" :
					  rr.getRoleName()).append("--").append(
	      "$");
	  strRoleList.append( (rr.getGuestString() == null) ? "" :
			     rr.getGuestString()).append("$");
	  strRoleList.append("**").append( (rr.getRoleName() == null) ? "" :
					  rr.getRoleName()).append("&ReadOnly=").
	      append(strReadOnly).append("**");

	  strRoleList.append("|");
	}
      }
    }
    request.setAttribute("roleList", strRoleList.toString());
  }

  /**
   * Sets all the counties in the specific state to the request attribute
   * @param request    the HttpServletRequest
   */
  private void prepareAddressCounties(HttpServletRequest request,
				      ArrayList<Object> stateList)
  {

    if (stateList != null)
    {

      StringBuffer totalCounties = new StringBuffer();
      List<Object> unique = new ArrayList<Object> ();
     Iterator<Object>  i = stateList.iterator();

      while (i.hasNext())
      {

	Object current = i.next();

	if (!unique.contains(current))
	{
	  unique.add(current);
	}
      }

     Iterator<Object>  states = unique.iterator();

      while (states.hasNext())
      {
	totalCounties.append(getCountiesByState( (String) states.next()));
      }
      request.setAttribute("addressCounties", totalCounties.toString());
    }
  }

  /**
   * Gets all the counties from the database based on the stateCd
   * @param stateCd   the String
   * @return    the String
   */
  private String getCountiesByState(String stateCd)
  {

    StringBuffer parsedCodes = new StringBuffer("");

    if (stateCd != null)
    {

      //SRTValues srtValues = new SRTValues();
      CachedDropDownValues srtValues = new CachedDropDownValues();
      TreeMap<?,?> treemap = null;
      treemap = srtValues.getCountyCodes(stateCd);

      if (treemap != null)
      {
	Set<?> set = treemap.keySet();
	Iterator<?> itr = set.iterator();

	while (itr.hasNext())
	{

	  String key = (String) itr.next();
	  String value = (String) treemap.get(key);
	  parsedCodes.append(key.trim()).append(
	      NEDSSConstants.SRT_PART).append(value.trim()).append(
	      NEDSSConstants.SRT_LINE);
	}
      }
    }
    return parsedCodes.toString();
  }

  private boolean matchProgramArea(String loginPaaProgramArea, String userProgramAreaCode)
  {
    if(loginPaaProgramArea != null)
    {
      StringBuffer sb = new StringBuffer();

      for(int i = 0, j = 0;i < loginPaaProgramArea.length();)
      {
	j = loginPaaProgramArea.indexOf("|", i);
	String paaProgramAreaCd = loginPaaProgramArea.substring(i, j);
	if(userProgramAreaCode.equalsIgnoreCase(paaProgramAreaCd))
	  return true;
	i=j+1;
      }
    }
    return false;
  }
}
