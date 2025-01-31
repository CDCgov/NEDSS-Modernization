/**
 * Title: PageConstants utility class.
 * Description: A utility which defines NEDSS file directory and other constants
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.util;

public class PageConstants {

  public final static String ERRORPAGE = "/nbs/error";
  public final static String PERSONSEARCHRESULTPAGE = "/nbs/cdm/search_results?pageName=demographics";
  public final static String DEMOGRAPHICSPAGE = "/nbs/start";
  public final static String FRONTCONTROLLERPAGE = "/nbs/servlet/gov.cdc.nedss.web.servlet.NedssFrontController";
  public final static String PERSONVIEWPAGE = "/nbs/cdm/person_view";
  public final static String PERSONEDITPAGE = "/nbs/PersonEdit";
  public final static String PERSONSEARCHPAGE = "/nbs/PersonSearch";
  public final static String LOGINPAGE = "/nbs/Login";
  public final static String LOGOUTPAGE = "/nbs/logOut";
  public final static String LOGINERRORPAGE = "/nbs/unauthorizedUser";
  public final static String ROLEPAGE = "/nbs/Role";
  public final static String INVESTIGATIONPAGE = "/nbs/investigation";
  public final static String INVESTIGATIONMEASLESVIEWPAGE = "/nbs/wum_investigation_measles_view";
  public final static String VIEWWORKUPPAGE = "/nbs/file/view_workup";
  public final static String REPORTPAGE = "/nbs/report/reports";
  public final static String CREATENOTIFICATION = "/nbs/notification/createNotification";
  public final static String MANAGENOTIFICATION = "/nbs/notification/manageNotification";
  public final static String HOMEPAGE = "/nbs/servlet/nfc?ObjectType=" +NEDSSConstants.TASKLIST+"&amp;OperationType="+NEDSSConstants.GET_TASKLIST_ITEMS;
  public final static String TIMEOUTPAGE = "/nbs/timeout";

  public final static String OBJECTTYPE= "ObjectType";
  public final static String OPERATIONTYPE= "OperationType";
  public final static String QMARK= "?";
  public final static String AMP= "&";
  public final static String EQ= "=";
  public PageConstants() {
  }
}