package gov.cdc.nedss.webapp.nbs.action.report;

import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.report.dt.DataSourceColumnDT;
import gov.cdc.nedss.report.dt.DisplayColumnDT;
import gov.cdc.nedss.report.dt.FilterCodeDT;
import gov.cdc.nedss.report.dt.FilterValueDT;
import gov.cdc.nedss.report.dt.ReportFilterDT;
import gov.cdc.nedss.report.util.ReportConstantUtil;
import gov.cdc.nedss.report.vo.DataSourceVO;
import gov.cdc.nedss.report.vo.ReportVO;
import gov.cdc.nedss.report.vo.RunReportVO;
import gov.cdc.nedss.report.dt.ReportDT;
import gov.cdc.nedss.report.ejb.reportcontrollerejb.bean.ReportControllerHome;
import gov.cdc.nedss.reportadmin.dao.ReportSectionDAO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.Command;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.RequestHelper;
import gov.cdc.nedss.util.XMLRequestHelper;
import gov.cdc.nedss.webapp.nbs.action.reportadmin.ReportAdminUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sas.servlet.util.SocketListener;

/**
 * ReportWebProcessor processes requests delegated by NedssFrontController
 *
 * @author  Narendra Mallela
 */
public class ReportWebProcessor
    implements Command {

  static final LogUtils logger = new LogUtils( (ReportWebProcessor.class).
                                              getName());
  protected static boolean DEBUG_MODE;
  protected NedssUtils nedssUtils = null;
  protected MainSessionCommand msCommand = null;
  private String sBeanJndiName = "";
  private String sMethod = "";
  private Object[] oParams = null;
  protected NBSSecurityObj secObj = null;
  private MainSessionHolder mainSessionHolder = new MainSessionHolder();
  private static CachedDropDownValues cdv = new CachedDropDownValues();
  private static String report_error = "The following error occurred while processing the report, please forward the below information to your NBS Administrator for resolution:";
  private static String report_tech_error = "There is a technical error. Please check with your sas administrator.";
  
  
  //private String currentState = null;

  /**
   * Constructor currently does nothing
   * @param no params
   */
  public ReportWebProcessor() {
  }

  /** This method compares old VO and new VO and copies new content to oldVO and returns oldVO
   *  @param ReportVO, dataSourceUID, reportUID
   */
  public ReportVO compareReportVO(ReportVO oldVO, ReportVO newVO,
                                  String dataSourceUID, String reportUID) {
    ArrayList<Object> oldReportFiltersDTCollection  = (ArrayList<Object> ) oldVO.
        getTheReportFilterDTCollection();
    ArrayList<Object> newReportFiltersDTCollection  = (ArrayList<Object> ) newVO.
        getTheReportFilterDTCollection();
   Iterator<Object>  oFilters = null;
   Iterator<Object>  nFilters = null;
    ArrayList<Object> oldFilterValues = null;
    ArrayList<Object> newFilterValues = null;
    for (oFilters = oldReportFiltersDTCollection.iterator(),
         nFilters = newReportFiltersDTCollection.iterator();
         oFilters.hasNext() && nFilters.hasNext(); ) {
      ReportFilterDT oldReportFilterDT = (ReportFilterDT) oFilters.next();
      ReportFilterDT newReportFilterDT = (ReportFilterDT) nFilters.next();
      oldFilterValues = (ArrayList<Object> ) oldReportFilterDT.
          getTheFilterValueDTCollection();
      newFilterValues = (ArrayList<Object> ) newReportFilterDT.
          getTheFilterValueDTCollection();
      logger.info("RWP -- 664: " + newFilterValues.size());
      logger.info("RWP -- 665: " +
                  newReportFilterDT.getTheFilterCodeDT().getFilterName());
     Iterator<Object>  oValues = null;
      for (oValues = oldFilterValues.iterator(); oValues.hasNext(); ) {
        FilterValueDT oldFilterValueDT = (FilterValueDT) oValues.next();
        oldFilterValueDT.setItDelete(true);
      }
     Iterator<Object>  nValues = null;
      for (nValues = newFilterValues.iterator(); nValues.hasNext(); ) {
        FilterValueDT newFilterValueDT = (FilterValueDT) nValues.next();
        newFilterValueDT.setItNew(true);
        oldFilterValues.add(newFilterValueDT);
      }
      oldReportFilterDT.setItDirty(true);
    } //for
    ArrayList<Object> oldDisplayColumnDTCollection  = (ArrayList<Object> ) oldVO.
        getTheDisplayColumnDTList();
    ArrayList<Object> newDisplayColumnDTCollection  = (ArrayList<Object> ) newVO.
        getTheDisplayColumnDTList();
   Iterator<Object>  oColumns = null;
   Iterator<Object>  nColumns = null;
    for (oColumns = oldDisplayColumnDTCollection.iterator(); oColumns.hasNext(); ) {
      DisplayColumnDT oldDisplayColumnDT = (DisplayColumnDT) oColumns.next();
      oldDisplayColumnDT.setItDelete(true);
    }
    for (nColumns = newDisplayColumnDTCollection.iterator(); nColumns.hasNext(); ) {
      DisplayColumnDT newDisplayColumnDT = (DisplayColumnDT) nColumns.next();
      newDisplayColumnDT.setItNew(true);
      //newDisplayColumnDT.setColumnUid(newDisplayColumnDT.getTheDataSourceColumnDT().getColumnUid());
      oldDisplayColumnDTCollection.add(newDisplayColumnDT);
    }
    ArrayList<Object> test1 = (ArrayList<Object> ) oldVO.getTheDisplayColumnDTList();
   Iterator<Object>  anIterator = null;
    for (anIterator = test1.iterator(); anIterator.hasNext(); ) {
      DisplayColumnDT dt = (DisplayColumnDT) anIterator.next();
      logger.debug("\n\nThe displayColumnUID is : " + dt.getDisplayColumnUid());
      logger.debug("\n\nThe displayColumnUID/ReportUID is : " + dt.getReportUid());
      logger.debug("\n\nThe displayColumn delete is : " + dt.isItDelete());
      logger.debug("\n\nThe displayColumn new is : " + dt.isItNew());
    }
    return oldVO;
  } //compareReportVO

  /**
   * Executes an operation wrapped in the request object by Delegating the
   * exection to the corresponding EJB in APPS
   *
   * @param   RequestHelper
   * @return  currrently, just returns an error page URL
   */
  public String execute(RequestHelper helper) throws java.lang.Exception {
    String errorPage = ReportConstantUtil.ERROR_PAGE; // put address of error page
    int operationType = NEDSSConstants.UNKNOWN_OPERATION;
    
    HttpServletRequest request = helper.getRequest();
    HttpServletResponse response = helper.getResponse();
    HttpSession session = helper.getSession();
    secObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
    //      logger.info("secObj state is: "+secObj.getStateMask());
    String sObjectType = request.getParameter(ReportConstantUtil.OBJECT_TYPE);
    logger.info("sObjectType: " + sObjectType);
    String sOperationType = request.getParameter(ReportConstantUtil.
                                                 OPERATION_TYPE);
    logger.info("sOperationType" + sOperationType);
    
    
    if(request.getParameter(ReportConstantUtil.DATASOURCE_UID)!=null && !request.getParameter(ReportConstantUtil.DATASOURCE_UID).trim().equals("")){
	    Long dataSourceUID2 = new Long(request.getParameter(ReportConstantUtil.DATASOURCE_UID));
	    Long reportUID2 = new Long(request.getParameter(ReportConstantUtil.REPORT_UID));
	    
	    sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
	    sMethod = "isDataMartFromDataSourceName";
	    oParams = new Object[] {reportUID2, dataSourceUID2};
	    if (msCommand == null) {
	      msCommand = mainSessionHolder.getMainSessionCommand(session);
	    }
	    ArrayList<Object> report2 = (ArrayList<Object> ) msCommand.processRequest(
	        sBeanJndiName, sMethod, oParams);
	    if(((boolean)report2.get(0))==false){
	         request.getSession().setAttribute("errorDataSource",NEDSSConstants.DATA_SOURCE_NOT_AVAILABLE);
	         return (ReportConstantUtil.REPORT_LIST_PAGE);
	    }
	    else{
	   	 request.getSession().removeAttribute("errorDataSource");
		 request.removeAttribute("errorDataSource");
	    }
		    
    }
    
    
    
    if (sOperationType != null) {
      try {
        operationType = (new Integer(sOperationType).intValue());
      }
      catch (Exception ne) {
        operationType = NEDSSConstants.UNKNOWN_COMMAND;
        logger.fatal("ReportWebProcessor.execute", ne);
      }
    }
    else {
      operationType = NEDSSConstants.REPORT_LIST;
    }
    switch (operationType) {
      case (NEDSSConstants.UNKNOWN_OPERATION):
        break;
      case (NEDSSConstants.REPORT_LIST): {
        try {
          removeSessionAttributes(session);
          makeReportList(request);

          session.setAttribute(ReportConstantUtil.MODE, "edit");
          
          
          return (ReportConstantUtil.REPORT_LIST_PAGE);
        }
        catch (Exception e) {
          if (session == null) {
            logger.debug("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.LOGIN_PAGE);
          }
          else {
            logger.fatal("ReportWebProcessor.execute", e);
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            return (ReportConstantUtil.ERROR_PAGE);
          }
        }
      } //case(NEDSSConstants.REPORT_LIST)
      case (NEDSSConstants.REPORT_BASIC): {
        try {
          if (session.getAttribute(ReportConstantUtil.RESULT) == null) { // if reportVO is null
            Long reportUID = new Long(request.getParameter(ReportConstantUtil.
                REPORT_UID));
            
            if(request.getParameter("cvg_select_all")==null)
            	session.setAttribute(ReportConstantUtil.SELECT_ALL_CVG, "false");
            
            session.setAttribute(ReportConstantUtil.REPORT_UID,
                                 reportUID.toString());
            Long dataSourceUID = new Long(request.getParameter(
                ReportConstantUtil.DATASOURCE_UID));

            
            
            
            session.setAttribute(ReportConstantUtil.DATASOURCE_UID,
                                 dataSourceUID.toString());
            logger.info(" ** reportUID is----->" + reportUID);
            logger.info("** dataSourceUID is----->" + dataSourceUID);
            sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
            sMethod = "getReport";
            oParams = new Object[] {
                reportUID, dataSourceUID};
            if (msCommand == null) {
              msCommand = mainSessionHolder.getMainSessionCommand(session);
            }
            ArrayList<Object> report = (ArrayList<Object> ) msCommand.processRequest(
                sBeanJndiName, sMethod, oParams);
            ReportVO oldReportVO = (ReportVO) report.get(0); //this is used only in compareVO() method while SAVE_REPORT
            ReportVO newReportVO = null;

            // temp fix when user runs a report that has been deleted
            try {
              newReportVO = new ReportVO(oldReportVO); //working VO between requests
            }
            catch (NullPointerException npe) {
              throw new NEDSSAppConcurrentDataException("Run Report Error!");
            }
            //this.getDateFieldType(newReportVO, session);
            session.setAttribute(ReportConstantUtil.OLD_RESULT, report);
            ArrayList<Object> newReportList = new ArrayList<Object> ();
            newReportList.add(newReportVO);
            session.setAttribute(ReportConstantUtil.RESULT, newReportList);
            ReportVO reportVO = (ReportVO) newReportList.get(0);
            ArrayList<Object> filtersDTCollection  = (ArrayList<Object> ) reportVO.
                getTheReportFilterDTCollection();
            int capacity = filtersDTCollection.size();
            logger.info("capacity is: " + capacity);
           Iterator<Object>  iFilters = filtersDTCollection.iterator();
            StringBuffer basicDisplay = new StringBuffer("<table role=\"presentation\">");
            while (iFilters.hasNext()) {
              ReportFilterDT filterDT = (ReportFilterDT) iFilters.next();
              FilterCodeDT filterCodeDT = (FilterCodeDT) filterDT.
                  getTheFilterCodeDT();
              //to display xml string for max values and min values
              basicPageMaker(basicDisplay, filterDT, filterCodeDT);
              if (filterCodeDT.getFilterCode().startsWith(
                  ReportConstantUtil.DISEASE_CODE)) {
                String diseases = BasicWebProcessor.getDiseases(filterDT);
                //logger.info("diseases:----> "+diseases);
                session.setAttribute(ReportConstantUtil.DISEASE_CODE, diseases);
                if(filterCodeDT.getFilterCode().equalsIgnoreCase(ReportConstantUtil.DISEASE_CODE_N))
                	setIncludeNullsInSession(filterDT, session, ReportConstantUtil.INCLUDE_NULL_DISEASE);
              }
              else if (filterCodeDT.getFilterCode().startsWith(
                  ReportConstantUtil.STATE_CODE)) {
                String states = BasicWebProcessor.getStates(filterDT);
                //logger.info("states:----> "+states);
                session.setAttribute(ReportConstantUtil.STATE_CODE, states);
                //also set a variable in session not to reload counties for saved reports
                //loadCountiesForNewReports(filterDT, session);
                if (filterCodeDT.getFilterCode().equalsIgnoreCase(ReportConstantUtil.STATE_CODE_N))
                	setIncludeNullsInSession(filterDT, session, ReportConstantUtil.INCLUDE_NULL_STATE);
              }
              
              else if (filterCodeDT.getFilterCode().startsWith(
                  ReportConstantUtil.COUNTY_CODE)) {
              	
            	  if(filterDT.getTheFilterValueDTCollection().size() > 0)
            		session.setAttribute(ReportConstantUtil.COUNTIES_SELECTED,"true");
              	
                String currentState = getCurrentState(reportVO);
                if (currentState == null) {
                  currentState = "01";
                }
                //logger.info("currentState: RWP 224 --> "+ currentState);
                session.setAttribute(ReportConstantUtil.CURRENT_STATE,
                                     currentState);
                String counties = BasicWebProcessor.getCounties(filterDT,
                    currentState);
                //logger.info("counties:----> "+counties);
                session.setAttribute(ReportConstantUtil.COUNTY_CODE, counties);
                if (filterCodeDT.getFilterCode().equalsIgnoreCase(ReportConstantUtil.COUNTY_CODE_N))
                	setIncludeNullsInSession(filterDT, session, ReportConstantUtil.INCLUDE_NULL_COUNTY);
                
              }
              else if (filterCodeDT.getFilterCode().startsWith(
                  ReportConstantUtil.REGION_CODE)) {
                String regions = BasicWebProcessor.getRegions(filterDT);
                //logger.info("regions:---->"+ regions);
                session.setAttribute(ReportConstantUtil.REGION_CODE, regions);
                if (filterCodeDT.getFilterCode().equalsIgnoreCase(ReportConstantUtil.REGION_CODE_N))
                	setIncludeNullsInSession(filterDT, session, ReportConstantUtil.INCLUDE_NULL_REGION);
                
              }
              else if (filterCodeDT.getFilterCode().startsWith(
                  ReportConstantUtil.TIME_RANGE_CODE)) {
                String timeRangeFrom = BasicWebProcessor.getTimeRangeFrom(
                    filterDT);
                if (timeRangeFrom == null) {
                  timeRangeFrom = "";
                }
                String timeRangeTo = BasicWebProcessor.getTimeRangeTo(filterDT);
                if (timeRangeTo == null) {
                  timeRangeTo = "";
                }
                logger.info("timeRangeFrom 239: " + timeRangeFrom);
                logger.info("timeRangeTo 240: " + timeRangeTo);
                session.setAttribute(ReportConstantUtil.TIME_RANGE_FROM,
                                     timeRangeFrom);
                session.setAttribute(ReportConstantUtil.TIME_RANGE_TO,
                                     timeRangeTo);
                session.setAttribute("dateType", getDateFieldType(filterDT,session));
                
                if(filterCodeDT.getFilterCode().equalsIgnoreCase(ReportConstantUtil.TIME_RANGE_CODE_N))                	
                	setIncludeNullsInSession(filterDT, session, ReportConstantUtil.INCLUDE_NULL_TIMERANGE);
              }
              else if (filterCodeDT.getFilterCode().startsWith(
                      ReportConstantUtil.MONTH_YEAR_RANGE_CODE)) {
                    String monthYearRangeFrom = BasicWebProcessor.getMonthYearRangeFrom(
                        filterDT);
                    if (monthYearRangeFrom == null) {
                    	monthYearRangeFrom = "";
                    }
                    String monthYearRangeTo = BasicWebProcessor.getMonthYearRangeTo(filterDT);
                    if (monthYearRangeTo == null) {
                    	monthYearRangeTo = "";
                    }
                    logger.info("monthYearRangeFrom 259: " + monthYearRangeFrom);
                    logger.info("monthYearRangeTo 260: " + monthYearRangeTo);
                    session.setAttribute(ReportConstantUtil.MONTH_YEAR_RANGE_FROM,
                    		monthYearRangeFrom);
                    session.setAttribute(ReportConstantUtil.MONTH_YEAR_RANGE_TO,
                    		monthYearRangeTo);
                    session.setAttribute("dateType", getDateFieldType(filterDT,session));
                    
                    if(filterCodeDT.getFilterCode().equalsIgnoreCase(ReportConstantUtil.MONTH_YEAR_RANGE_CODE_N))                	
                    	setIncludeNullsInSession(filterDT, session, ReportConstantUtil.INCLUDE_NULL_MONTHYEAR_RANGE);
                  }
              else if (filterCodeDT.getFilterCode().startsWith(
                  ReportConstantUtil.TIME_PERIOD_CODE)) {
                String timePeriodFrom = BasicWebProcessor.getTimePeriodFrom(
                    filterDT);
                String timePeriodTo = BasicWebProcessor.getTimePeriodTo(
                    filterDT);
                logger.info("timePeriodFrom: " + timePeriodFrom);
                logger.info("timePeriodTo: " + timePeriodTo);
                session.setAttribute(ReportConstantUtil.TIME_PERIOD_FROM,
                                     timePeriodFrom);
                session.setAttribute(ReportConstantUtil.TIME_PERIOD_TO,
                                     timePeriodTo);

                session.setAttribute("dateType", getDateFieldType(filterDT, session));

                if(filterCodeDT.getFilterCode().equalsIgnoreCase(ReportConstantUtil.TIME_PERIOD_CODE_N))                	
                	setIncludeNullsInSession(filterDT, session, ReportConstantUtil.INCLUDE_NULL_TIMEPERIOD);


              }
              
              else if (filterCodeDT.getFilterCode().startsWith(
                      ReportConstantUtil.CVG_CUSTOM)) {
            	  String codedValues = BasicWebProcessor.getCodedValues(filterDT);
                  //logger.info("codedValues:----> "+codedValues);
                  session.setAttribute(ReportConstantUtil.CVG_CUSTOM, codedValues);
                  session.setAttribute("filterField", getDateFieldType(filterDT,session));
                  if(filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.CVG_CUSTOM_N))
                  	setIncludeNullsInSession(filterDT, session, ReportConstantUtil.INCLUDE_NULL_CVG);
                  }
              else if (filterCodeDT.getFilterCode().startsWith(
                      ReportConstantUtil.TEXT_FILTER)) {
            	  String textValue = BasicWebProcessor.getTextValue(filterDT);
                  //logger.info("codedValues:----> "+codedValues);
                  session.setAttribute(ReportConstantUtil.TEXT_FILTER, textValue);
                  session.setAttribute("TextfilterField", getDateFieldType(filterDT,session));
                  }
              else if (filterCodeDT.getFilterCode().startsWith(
                      ReportConstantUtil.DAYS_FILTER)) {
            	  String daysValue = BasicWebProcessor.getDaysValue(filterDT);
                  //logger.info("codedValues:----> "+codedValues);
                  session.setAttribute(ReportConstantUtil.DAYS_FILTER, daysValue);
                  session.setAttribute("DaysfilterField", getDateFieldType(filterDT,session));
                  }
              else if (filterCodeDT.getFilterCode().startsWith(
                      ReportConstantUtil.STD_HIV_WRKR)) {
            	  String codedValues = BasicWebProcessor.getCodedValues(filterDT);
                  //logger.info("codedValues:----> "+codedValues);
                  session.setAttribute(ReportConstantUtil.STD_HIV_WRKR, codedValues);
                  session.setAttribute("filterFieldWRKR", getDateFieldType(filterDT,session));
              }
            } //while
            basicDisplay.append("</table>");
            //logger.info("MIN_MAX DISPLAY -- RWP 233: "+ basicDisplay.toString());
            session.setAttribute(ReportConstantUtil.FILTERS,
                                 basicDisplay.toString());
            String selectedColumns = ColumnWebProcessor.getSelectedColumns(reportVO,false);
            String criteriaList = AdvanceWebProcessor.getWhereClauseBuilder(newReportVO).toString();            
            String sortBy = ColumnWebProcessor.getSelectedColumns(reportVO,true);
            
            logger.info("Selected Columns: " + selectedColumns);
            session.setAttribute(ReportConstantUtil.SELECTED_COLUMNS,selectedColumns);
            session.setAttribute(ReportConstantUtil.SORT_BY,sortBy);
            session.setAttribute(ReportConstantUtil.CRITERIA_LIST, criteriaList);

            String sortOrderColumns = ColumnWebProcessor.getSortOrders(reportVO,ReportConstantUtil.SORT_ORDER,null);
            
            session.setAttribute(ReportConstantUtil.SORT_ORDER,sortOrderColumns);
          }
          else {
            if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
                ADVANCE_PAGE)) {
              String page = setAdvanceData(session, request);
              if (page != null) {
                return page;
              }
            }
            else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
                COLUMN_PAGE)) {
              String page = setColumnData(session, request);
              if (page != null) {
                return page;
              }
            }
          } //else
          String mode = request.getParameter(ReportConstantUtil.MODE);
          logger.info("mode in BASIC  - RWP 413: " + mode);
          session.setAttribute(ReportConstantUtil.MODE, mode);
          return (ReportConstantUtil.BASIC_PAGE);
        }
        catch (NEDSSAppConcurrentDataException nappe) {
          logger.debug("ReportWebProcessor.execute", nappe);
          if (session == null) {
            return (ReportConstantUtil.LOGIN_PAGE);
          }
          else {
            //mainSessionHolder.removeMainSessionCommand(session);
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            logger.fatal("Exception is: ", nappe);
            return ("dataerror");
          }
        }

        catch (Exception e) {
          logger.debug("ReportWebProcessor.execute", e);
          if (session == null) {
            return (ReportConstantUtil.LOGIN_PAGE);
          }
          else {
            //mainSessionHolder.removeMainSessionCommand(session);
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            logger.fatal("Exception is: ", e);
            return (ReportConstantUtil.ERROR_PAGE);
          }
        }
      } //BASIC
      // *** Operation = GET_COUNTIES ***
      case (NEDSSConstants.GET_COUNTIES): {
        try {
          //  Get input from proxy.
          String[] stateValues = new String[1];
          stateValues[0] = request.getParameter("proxyInput");
          //  Get new list of counties.
          String counties = BasicWebProcessor.fetchCounties(stateValues);
          //  Save states.
          ArrayList<Object> list = (ArrayList<Object> ) session.getAttribute(ReportConstantUtil.
              RESULT);
          ReportVO newReportVO = (ReportVO) list.get(0);
          ArrayList<Object> filtersDTCollection  = (ArrayList<Object> ) newReportVO.
              getTheReportFilterDTCollection();
         Iterator<Object>  iFilters = filtersDTCollection.iterator();
          while (iFilters.hasNext()) {
            ReportFilterDT filterDT = (ReportFilterDT) iFilters.next();
            FilterCodeDT filterCodeDT = (FilterCodeDT) filterDT.getTheFilterCodeDT();
            
            if(filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.DISEASE_CODE)) {
            	setIncludeNullsInSession(filterDT, session, ReportConstantUtil.INCLUDE_NULL_DISEASE);
            	
            } else if (filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.STATE_CODE)) {
            	
            	setIncludeNullsInSession(filterDT, session, "INCLUDE_NULL_STATE");                
            	String states = BasicWebProcessor.setStates(newReportVO, filterDT,stateValues, null);
            	session.setAttribute(ReportConstantUtil.STATE_CODE, states);
            	session.setAttribute(ReportConstantUtil.CURRENT_STATE, stateValues[0]);
            	
            } else if(filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.COUNTY_CODE)) {
           	
            	setIncludeNullsInSession(filterDT, session, "INCLUDE_NULL_COUNTY");

            } else if(filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.REGION_CODE)) {
            	
            	setIncludeNullsInSession(filterDT, session, "INCLUDE_NULL_REGION");            	
            }else if(filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.TIME_RANGE_CODE)) {
            	
            	setIncludeNullsInSession(filterDT, session, "INCLUDE_NULL_TIMERANGE");            	
            }else if(filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.TIME_PERIOD_CODE)) {
            	
            	setIncludeNullsInSession(filterDT, session, "INCLUDE_NULL_TIMEPERIOD");            	
            }
            else if(filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.MONTH_YEAR_RANGE_CODE)) {
            	
            	setIncludeNullsInSession(filterDT, session, ReportConstantUtil.INCLUDE_NULL_MONTHYEAR_RANGE);
            }
          }
          //  Save counties.
          session.setAttribute(ReportConstantUtil.COUNTY_CODE, counties);
          session.setAttribute("proxyInput", stateValues[0]);
          session.setAttribute("proxyOutput", counties);
          //  Save mode.
          String mode = request.getParameter(ReportConstantUtil.MODE);
          session.setAttribute(ReportConstantUtil.MODE, mode);
          //  Return restult.
          return (ReportConstantUtil.PROXY_PAGE);
        }
        catch (Exception e) {
          if (session == null) {
            logger.debug("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.LOGIN_PAGE);
          }
          else {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            logger.fatal("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.ERROR_PAGE);
          }
        }
      } 
      
      case (NEDSSConstants.GET_CODEDVALUES): {
    	  try {
              String[] codesetNm = new String[1];
              codesetNm[0] = request.getParameter("proxyInput");
              ArrayList<Object> report = (ArrayList<Object> ) session.getAttribute(ReportConstantUtil.RESULT);
              ReportVO newReportVO = (ReportVO) report.get(0);
              String codedValues = "";
              if(codesetNm[0] != null)
            	  codedValues = AdvanceWebProcessor.fetchCodedValues(codesetNm[0], newReportVO, session);
              //getSRTCache().
              session.setAttribute(ReportConstantUtil.CODED_VALUES, codedValues);
              session.setAttribute("proxyInput", codesetNm[0]);
              session.setAttribute("proxyOutput", codedValues);
              //  Save mode.
              String mode = request.getParameter(ReportConstantUtil.MODE);
              session.setAttribute(ReportConstantUtil.MODE, mode);
              //  Return restult.
              return (ReportConstantUtil.PROXY_PAGE);                
    	  }
          catch (Exception e) {
              if (session == null) {
                logger.debug("ReportWebProcessor.execute", e);
                return (ReportConstantUtil.LOGIN_PAGE);
              }
              else {
                msCommand = mainSessionHolder.getMainSessionCommand(session);
                logger.fatal("ReportWebProcessor.execute", e);
                return (ReportConstantUtil.ERROR_PAGE);
              }   
          }
        
      }
      
      case (NEDSSConstants.REPORT_ADVANCED): {
        try {
          String page = null;
          if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
              BASIC_PAGE)) {
            page = setBasicData(session, request);
            if (page != null) {
              return page;
            }
          }
          else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
              COLUMN_PAGE)) {
            page = setColumnData(session, request);
            if (page != null) {
              return page;
            }
          }
          
          //Load all the operators and put them in session          
          String filterOperators = cdv.getAllFilterOperators();
          
          session.setAttribute(ReportConstantUtil.ALL_FILTER_OPERATORS, filterOperators);
          
          if (session.getAttribute(ReportConstantUtil.FILTERABLE_COLUMNS) == null) {

            Long reportUID = new Long(session.getAttribute(ReportConstantUtil.
                REPORT_UID).toString());
            Long dataSourceUID = new Long(session.getAttribute(
                ReportConstantUtil.DATASOURCE_UID).toString());
            logger.info(" ** reportUID is----->" + reportUID);
            logger.info("** dataSourceUID is----->" + dataSourceUID);
            sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
            sMethod = "getFilterableColumns";
            oParams = new Object[] {
                dataSourceUID};
            if (msCommand == null) {
              msCommand = mainSessionHolder.getMainSessionCommand(session);
            }
            ArrayList<Object> report = (ArrayList<Object> ) session.getAttribute(
                ReportConstantUtil.RESULT);
            ReportVO newReportVO = (ReportVO) report.get(0);
            ArrayList<Object> filterableCol = (ArrayList<Object> ) msCommand.processRequest(
                sBeanJndiName, sMethod, oParams)
                .get(0);
            StringBuffer values = new StringBuffer();
           Iterator<Object>  filterableIt = null;
            values.append("<table role=\"presentation\">");
            for (filterableIt = filterableCol.iterator(); filterableIt.hasNext(); ) {
              values.append("<record>");
              DataSourceColumnDT dataSourceColumnDT = (DataSourceColumnDT)
                  filterableIt.next();
          	  String codeSet = dataSourceColumnDT.getCodesetNm();
          	  String codeDescCd = dataSourceColumnDT.getCodeDescCd();
              values.append("<field>");
              values.append(dataSourceColumnDT.getColumnUid());
              values.append("</field>");
              values.append("<field>");
              values.append(XMLRequestHelper.xmlEncode(dataSourceColumnDT.getColumnTitle() == null ? "" : dataSourceColumnDT.getColumnTitle().trim()));
              values.append("</field>");
              values.append("<field>");
              if(codeDescCd == null)
            	  values.append(XMLRequestHelper.xmlEncode(dataSourceColumnDT.getColumnTypeCode()));
              else
            	  values.append("CODED");
              values.append("</field>");
              values.append("<field>");
              values.append(dataSourceColumnDT.getColumnMaxLen());
              values.append("</field>");
              values.append("<field>");
              values.append(codeDescCd);
              values.append("</field>");
              values.append("<field>");
              values.append(codeSet);
              values.append("</field>");
              values.append("</record>");
            }
            values.append("</table>");
            session.setAttribute(ReportConstantUtil.FILTERABLE_COLUMNS,
                                 values.toString());
            String criteriaList = AdvanceWebProcessor.getWhereClauseBuilder(
                newReportVO)
                .toString();
            logger.info("criteriaList is: " + criteriaList);
            session.setAttribute(ReportConstantUtil.CRITERIA_LIST, criteriaList);
            ArrayList<Object> newReportList = new ArrayList<Object> ();
            newReportList.add(newReportVO);
            session.setAttribute(ReportConstantUtil.RESULT, newReportList);
          }
          else {

            if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
                BASIC_PAGE)) {
              page = setBasicData(session, request);
              if (page != null) {
                return page;
              }
            }
            else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
                COLUMN_PAGE)) {
              page = setColumnData(session, request);
              if (page != null) {
                return page;
              }
            }
          } //else
          String mode = request.getParameter(ReportConstantUtil.MODE);
          session.setAttribute(ReportConstantUtil.MODE, mode);
          return (ReportConstantUtil.ADVANCE_PAGE);
        }
        catch (Exception e) {
          if (session == null) {
            logger.debug("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.LOGIN_PAGE);
          }
          else {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            logger.fatal("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.ERROR_PAGE);
          }
        }
      } //ADVANCED
      // *** Operation = COLUMN ***
      case (NEDSSConstants.REPORT_COLUMN): {
        try {
          
          String page = null;
          if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
              BASIC_PAGE)) {
            page = setBasicData(session, request);
            if (page != null) {
              return page;
            }
          }
          else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
              ADVANCE_PAGE)) {
            logger.info("referrer is Advance Page *** ");
            page = setAdvanceData(session, request);
            if (page != null) {
              return page;
            }
          }
          if (session.getAttribute(ReportConstantUtil.AVAILABLE_COLUMNS) == null) {
            ArrayList<Object> report = (ArrayList<Object> ) session.getAttribute(
                ReportConstantUtil.RESULT);
            ReportVO reportVO = (ReportVO) report.get(0);
            Long reportUID = new Long(session.getAttribute(ReportConstantUtil.
                REPORT_UID).toString());
            Long dataSourceUID = new Long(session.getAttribute(
                ReportConstantUtil.DATASOURCE_UID).toString());
            logger.info(" REPORT_COLUMN ** reportUID is----->" + reportUID);
            logger.info(" REPORT_COLUMN ** dataSourceUID is----->" +
                        dataSourceUID);
            sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
            sMethod = "getDisplayableColumns";
            oParams = new Object[] {
                reportUID, dataSourceUID};
            if (msCommand == null) {
              msCommand = mainSessionHolder.getMainSessionCommand(session);
            }
            ArrayList<Object> availableCol = (ArrayList<Object> ) msCommand.processRequest(
                sBeanJndiName, sMethod, oParams)
                .get(0);
            //showDisplayColumnData(availableCol);
            //AVAILABLE_COLUMN_LIST is stored in session to retrieve back at later time, but not used on XSP directly
            session.setAttribute(ReportConstantUtil.AVAILABLE_COLUMN_LIST,
                                 availableCol);
            String selectedColumns = ColumnWebProcessor.getSelectedColumns(
                reportVO,false);
            String sortBy= ColumnWebProcessor.getSelectedColumns(
                    reportVO,true);
            logger.info("Selected Columns: " + selectedColumns);
            session.setAttribute(ReportConstantUtil.SELECTED_COLUMNS,
                                 selectedColumns);
            session.setAttribute(ReportConstantUtil.SORT_BY,
            		sortBy);
            String sortOrderColumns = ColumnWebProcessor.getSortOrders(reportVO,ReportConstantUtil.SORT_ORDER,null);
            
            session.setAttribute(ReportConstantUtil.SORT_ORDER,sortOrderColumns);
            StringBuffer values = new StringBuffer();
           Iterator<Object>  availableIt = null;
            values.append("<table role=\"presentation\">");
            ArrayList<Object> selectedList = (ArrayList<Object> ) reportVO.
                getTheDisplayColumnDTList();
            for (availableIt = availableCol.iterator(); availableIt.hasNext(); ) {
              DataSourceColumnDT dataSourceColumnDT = (DataSourceColumnDT)
                  availableIt.next();
              boolean selected = false;
             Iterator<Object>  selectedIt = null;
              for (selectedIt = selectedList.iterator(); selectedIt.hasNext(); ) {
                DisplayColumnDT selectedDisplayColumnDT = (DisplayColumnDT)
                    selectedIt.next();
                //if a match is found for the Column UID then the column is selected
                if (selectedDisplayColumnDT.getColumnUid() ==
                    dataSourceColumnDT.getColumnUid()) {
                  selected = true;
                  break;
                }
              }
              if (!selected) {
                values.append("<record>");
                values.append("<field>");
                values.append(dataSourceColumnDT.getColumnUid());
                values.append("</field>");
                values.append("<field>");
                values.append(XMLRequestHelper.xmlEncode(dataSourceColumnDT.
                    getColumnTitle().trim()));
                values.append("</field>");
                values.append("<field>");
                values.append("false");
                values.append("</field>");
                values.append("</record>");
              }
            }
            values.append("</table>");
            session.setAttribute(ReportConstantUtil.AVAILABLE_COLUMNS,
                                 values.toString());
          }
          else {
            if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
                BASIC_PAGE)) {
              page = setBasicData(session, request);
              if (page != null) {
                return page;
              }
            }
            else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
                ADVANCE_PAGE)) {
              page = setAdvanceData(session, request);
              if (page != null) {
                return page;
              }
            }
          } //else
          String mode = request.getParameter(ReportConstantUtil.MODE);
          session.setAttribute(ReportConstantUtil.MODE, mode);
          return (ReportConstantUtil.COLUMN_PAGE);
        }
        catch (Exception e) {
          if (session == null) {
            logger.debug("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.LOGIN_PAGE);
          }
          else {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            logger.fatal("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.ERROR_PAGE);
          }
        }
      } //COLUMN
      // *** Operation = DELETE REPORT ***
      case (NEDSSConstants.REPORT_DELETE): {
        try {
          Long reportUID = new Long(request.getParameter(ReportConstantUtil.
              REPORT_UID).toString());
          Long dataSourceUID = new Long(request.getParameter(ReportConstantUtil.
              DATASOURCE_UID).toString());
          logger.info(" ** reportUID is----->" + reportUID);
          logger.info("** dataSourceUID is----->" + dataSourceUID);
          sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
          sMethod = "deleteReport";
          oParams = new Object[] {
              reportUID, dataSourceUID};
          if (msCommand == null) {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
          }
          msCommand.processRequest(sBeanJndiName, sMethod, oParams);
          removeSessionAttributes(session);
          makeReportList(request);
          return (ReportConstantUtil.REPORT_LIST_PAGE);
        }
        catch (Exception e) {
          logger.debug("ReportWebProcessor.execute", e);
          throw new Exception(e.getMessage());
        }
      } //DELETE_REPORT
      case (NEDSSConstants.RUN_PAGE): {
        logger.info(
            "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2  inside RUN_PAGE -- RWP 556 ");

        //Will save the values in the fields if the run button is pressed from Basic Report
        if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.BASIC_PAGE)) {
          setBasicData(session, request);
        }
        else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
            ADVANCE_PAGE)) {
          setAdvanceData(session, request);
        }
        else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
            COLUMN_PAGE)) {
          setColumnData(session, request);
        }

        return (ReportConstantUtil.RUNPAGE);
      } //RUN_PAGE
      // *** Operation = RUN REPORT ***
      case (NEDSSConstants.RUN_REPORT): {
        try {
          ReportVO reportVO = null;
          ArrayList<Object> reportList = (ArrayList<Object> ) session.getAttribute(
              ReportConstantUtil.RESULT);
          reportVO = (ReportVO) reportList.get(0);
          String page = null;
          if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
              BASIC_PAGE)) {
            page = setBasicData(session, request);
            if (page != null) {
              return page;
            }
          }
          else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
              ADVANCE_PAGE)) {
            page = setAdvanceData(session, request);
            if (page != null) {
              return page;
            }
          }
          else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
              COLUMN_PAGE)) {
            page = setColumnData(session, request);
            if (page != null) {
              return page;
            }
          }
          logger.info("inside RUN_REPORT RWP - 639 ");
          SocketListener socket = new SocketListener();
          int port = socket.setup();
          String localhost = (InetAddress.getLocalHost()).getHostAddress();
          socket.start();
          RunReportVO runReportVO = new RunReportVO();
          runReportVO.setExportType("REPORT");
          runReportVO.setHost(localhost);
          runReportVO.setPort(port);
          showReportVO(reportVO);
          sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
          sMethod = "runReport";
          oParams = new Object[] {
              reportVO, runReportVO};
          //before running report...just print all filters and values....... just for TESTING.....
          logger.info(
              "before running report..................................>");
          ArrayList<Object> rfList = (ArrayList<Object> ) reportVO.
              getTheReportFilterDTCollection();
         Iterator<Object>  rfIterator = null;
          for (rfIterator = rfList.iterator(); rfIterator.hasNext(); ) {
            ReportFilterDT rfDT = (ReportFilterDT) rfIterator.next();
            logger.info("FilterName: " +
                        rfDT.getTheFilterCodeDT().getFilterName());
            ArrayList<Object> fvList = (ArrayList<Object> ) rfDT.getTheFilterValueDTCollection();
           Iterator<Object>  fvIterator = null;
            for (fvIterator = fvList.iterator(); fvIterator.hasNext(); ) {
              FilterValueDT fvDT = (FilterValueDT) fvIterator.next();
              logger.info("fvDT.getValueTxt() : " + fvDT.getValueTxt());
              logger.info("fvDT.getFilterValueOperator(): " +
                          fvDT.getFilterValueOperator());
            }
          }
          //before running report...after printing reportVO entities....... just for TESTING.....
          logger.info(
              "..................before running report..................................>");
          if (msCommand == null) {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
          }
          ArrayList<?> returnList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
          String errorMsg = (String)returnList.get(0);
          //See if the errorMsg has any SAS Errors for which the report doesnt run
          if(errorMsg != null && errorMsg.indexOf("The SAS System stopped processing this step because of errors") != -1) {
              socket.close();
              session.setAttribute("ErrorMessage",  errorMsg);
              return ReportConstantUtil.SAS_ERROR_PAGE;        	  
          }
          
          ByteArrayOutputStream bao = new ByteArrayOutputStream();
          socket.write( (OutputStream) bao);
          logger.info("ByteArrayOutputStream size: " + bao.toString().length());
          if (bao.toString().length() > 0) {
            //logger.info("***************** MIME TYPE: img/gif ******************");
            //if the content starts with G...., make the file name as ReportOutput.PDF
            if (bao.toString().indexOf("%PDF-1.4")== 0) { //report_type_code='PDF'
              //PDF output to open in new window
              response.setContentType("application/pdf");
              response.setHeader("Content-disposition",
                                 "inline;filename=ReportOutput.pdf");
              socket.write(response);
              
            }else if (bao.toString().charAt(0) == 'G') { //report_type_code='SAS_GRAPH'
                //img/gif MIME doesnt exist but to ensure it opens in new window, wrote code
                response.setContentType("img/gif");
                response.setHeader("Content-disposition",
                                   "inline;filename=ReportOutput.gif");
                socket.write(response);
                
              } else if(bao.toString().trim().equalsIgnoreCase(report_tech_error)) {
                socket.close();
                session.setAttribute("ErrorMessage",  errorMsg);
                return ReportConstantUtil.SAS_ERROR_PAGE;

            }
            else {
              logger.info(
                  "***************** MIME TYPE: text/html ******************");
              response.setContentType("text/html");
              javax.servlet.ServletOutputStream out = response.getOutputStream();
              
              //the session info is destroyed by the onunload event function in applet report
              //and user is logged out when the applet report window is closed or refreshed.
              //Untill we find fix from SAS with V9 tagsets,
              //the onunload event is removed. --George Chen --
              int x = bao.toString().indexOf("onunload");
              if (x == -1) {
                out.println(bao.toString());
              }
              else {
                StringBuffer sb = new StringBuffer(bao.toString());
                int x1 = sb.toString().indexOf('"', x);
                int x2 = sb.toString().indexOf('"', ++x1);
                sb.replace(x, ++x2, "");

                out.println(sb.toString());
              }
              out.flush();
              out.close();
            }
            socket.close();
            return (ReportConstantUtil.RUNPAGE);
          }
          else {
            socket.close();
            /*
            if(errorMsg == null || (errorMsg != null && errorMsg.trim().equals(""))) {
            	errorMsg = "Your report cannot be generated at this time.  Please check SAS configurations. Contact the Help Desk for further assistance.";
            }
            */
            session.setAttribute("ErrorMessage", errorMsg);            
            return ReportConstantUtil.SAS_ERROR_PAGE;
          }
        }
        catch (Exception e) {
          if (session == null) {
            logger.debug("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.LOGIN_PAGE);
          }
          else {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            logger.fatal("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.ERROR_PAGE);
          }
        }
      } //REPORT_RUN
      // *** Operation = SAVE PAGE ***
      case (NEDSSConstants.SAVE_PAGE): {
        logger.info("----RWP 573 SAVE PAGE ----");
        ReportSectionDAO daoReportSection = new ReportSectionDAO();
        session.setAttribute(ReportConstantUtil.REPORT_SECTION, BasicWebProcessor.getReportSections(daoReportSection.list()));
        return ReportConstantUtil.SAVEPAGE;
      }
      // *** Operation = SAVE AS NEW REPORT ***
      case (NEDSSConstants.SAVE_AS_NEW_REPORT): {
        try {
          ReportVO reportVO = null;
          ArrayList<Object> reportList = (ArrayList<Object> ) session.getAttribute(
              ReportConstantUtil.RESULT);
          reportVO = (ReportVO) reportList.get(0);
          showReportVO(reportVO);
          NBSSecurityObj securityObject = (NBSSecurityObj) session.getAttribute(
              ReportConstantUtil.SECURITY_OBJECT);
          //      Long security_userId = new Long(securityObject.getUserEntryId());
          //      Long security_userId = new Long(securityObject.getTheUserProfile().getTheUser().getEntryID());
          Long security_userId = new Long(securityObject.getEntryID());
          Long vo_userId = reportVO.getTheReportDT().getOwnerUid();
          String name = request.getParameter(ReportConstantUtil.REPORT_NAME);
          String description = request.getParameter(ReportConstantUtil.REPORT_DESCRIPTION);
          String reportSec = request.getParameter(ReportConstantUtil.REPORT_SECTION);
          String type = request.getParameter("type");
          logger.info("report Type -- RWP 621: " + type);
          logger.info("securityObject -- RWP 520: " + securityObject);
          logger.info("security_userId -- RWP 522: " + security_userId);
          logger.info("vo_userId   -- RWP 522: " + vo_userId);
          logger.info("************* name in SAVE_AS_NEW_REPORT: " + name);
          logger.info("************* description in SAVE_AS_NEW_REPORT: " +
                      description);
          if (vo_userId != security_userId) {
            reportVO.getTheReportDT().setOwnerUid(security_userId);
            if (type.equalsIgnoreCase("private")) {
              reportVO.getTheReportDT().setShared("P");
            } else if (type.equalsIgnoreCase("public")) {
              reportVO.getTheReportDT().setShared("S");
            } else if (type.equalsIgnoreCase("reportingFacility")) {
              reportVO.getTheReportDT().setShared("R");
            }
            
            reportVO.getTheReportDT().setReportTitle(name);
            reportVO.getTheReportDT().setDescTxt(description);
            reportVO.getTheReportDT().setStatusTime(new java.sql.Timestamp(new
                Date().getTime()));
            reportVO.getTheReportDT().setItDirty(true);
            reportVO.getTheReportDT().setSectionCd(reportSec);
          }
          showReportVO(reportVO);
          sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
          sMethod = "saveAsNewReport";
          oParams = new Object[] {
              reportVO};
          if (msCommand == null) {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
          }
          msCommand.processRequest(sBeanJndiName, sMethod, oParams);
          removeSessionAttributes(session);
          makeReportList(request);
          return (ReportConstantUtil.REPORT_LIST_PAGE);
        }
        catch (Exception e) {
          if (session == null) {
            logger.debug("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.LOGIN_PAGE);
          }
          else {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            logger.fatal("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.ERROR_PAGE);
          }
        }
      } //SAVE_AS_NEW_REPORT
      // *** Operation = EXPORT REPORT ***
      case (NEDSSConstants.EXPORT_REPORT): {
        try {
          String page = null;
          if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
              BASIC_PAGE)) {
            page = setBasicData(session, request);
            if (page != null) {
              return page;
            }
          }
          else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
              ADVANCE_PAGE)) {
            page = setAdvanceData(session, request);
            if (page != null) {
              return page;
            }
          }
          else if (getReferer(request).equalsIgnoreCase(ReportConstantUtil.
              COLUMN_PAGE)) {
            page = setColumnData(session, request);
            if (page != null) {
              return page;
            }
          }
          logger.info("inside Export Report");
          ReportVO reportVO = null;
          SocketListener socket = new SocketListener();
          int port = socket.setup();
          String localhost = (InetAddress.getLocalHost()).getHostAddress();
          socket.start();
          RunReportVO runReportVO = new RunReportVO();
          runReportVO.setExportType("EXPORT_ASCII_CSV");
          runReportVO.setHost(localhost);
          runReportVO.setPort(port);
          ArrayList<Object> reportList = (ArrayList<Object> ) session.getAttribute(
              ReportConstantUtil.RESULT);
          reportVO = (ReportVO) reportList.get(0);
          sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
          sMethod = "runReport";
          oParams = new Object[] {
              reportVO, runReportVO};
          if (msCommand == null) {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
          }
          msCommand.processRequest(sBeanJndiName, sMethod, oParams);
          ByteArrayOutputStream bao = new ByteArrayOutputStream();
          socket.write( (OutputStream) bao);
          if (bao.toString().length() > 0) {
            if (bao.toString().charAt(0) == '<') {
              //application/html MIME doesnt exist but to ensure it opens in new window, wrote code
              response.setContentType("application/html");
              response.setHeader("Content-disposition",
                                 "inline;filename=ReportOutput.html");
            }
            else {
              response.setContentType("application/csv");
              response.setHeader("Content-disposition",
                                 "inline;filename=ReportOutput.csv");
            }
          }
          if (bao.toString().length() > 0) {
            socket.write(response);
            socket.close();
            return (ReportConstantUtil.RUNPAGE);
          }
          else {
            socket.close();
            return ReportConstantUtil.SAS_ERROR_PAGE;
          }
        }
        catch (Exception e) {
          if (session == null) {
            logger.debug("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.LOGIN_PAGE);
          }
          else {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            logger.fatal("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.ERROR_PAGE);
          }
        }
      } //EXPORT_REPORT
      //break;
      // *** Operation = UPDATE REPORT (SET REPORT)***
      case (NEDSSConstants.UPDATE_REPORT): { //nothing but SAVE REPORT
        try {
          ArrayList<Object> oldReport = (ArrayList<Object> ) session.getAttribute(
              ReportConstantUtil.OLD_RESULT);
          ArrayList<Object> newReport = (ArrayList<Object> ) session.getAttribute(
              ReportConstantUtil.RESULT);
          ReportVO oldReportVO = (ReportVO) oldReport.get(0);
          ReportVO newReportVO = (ReportVO) newReport.get(0);
          ReportVO reportVO = (ReportVO) compareReportVO(oldReportVO,
              newReportVO,
              request.getParameter(ReportConstantUtil.DATASOURCE_UID),
              request.
              getParameter(ReportConstantUtil.
                           REPORT_UID));
          //checking finally if all values are set in reportVO to send to database for saving....

          /*
               logger.info("reportUID: "+ reportVO.getTheReportDT().getReportUid());
               logger.info("datasourceUID: "+ reportVO.getTheReportDT().getDataSourceUid());
               logger.info("reportType: "+ reportVO.getTheReportDT().getReportTypeCode());
               ArrayList<Object> rfList = (ArrayList<Object> )reportVO.getTheReportFilterDTCollection();
            Iterator<Object>  rfIterator = null;
             for(rfIterator = rfList.iterator(); rfIterator.hasNext();) {
                 ReportFilterDT rfDT = (ReportFilterDT)rfIterator.next();
                 FilterCodeDT fcDT = (FilterCodeDT)rfDT.getTheFilterCodeDT();
               logger.info("*************fcDT.getFilterCode(): "+fcDT.getFilterCode());
               logger.info("*************fcDT.getFilterName(): "+fcDT.getFilterName());
               logger.info("*************fcDT.getFilterName(): "+fcDT.getFilterName());
               logger.info("*************rfDT.getReportUid(): "+rfDT.getReportUid());
                 logger.info("*************rfDT.getReportFilterUid(): "+rfDT.getReportFilterUid());
                 logger.info("*************rfDT.isitNew: "+rfDT.isItNew());
               logger.info("*************rfDTIsItDelete: "+rfDT.isItDelete());
               logger.info("\n*************rfDTIsItDirtylete: "+rfDT.isItDirty());
               ArrayList<Object> fvList = (ArrayList<Object> )rfDT.getTheFilterValueDTCollection();
                Iterator<Object>  fvIterator = null;
                 for(fvIterator = fvList.iterator(); fvIterator.hasNext();) {
                     FilterValueDT fvDT = (FilterValueDT)fvIterator.next();
                     logger.info("*****************************************fvDT.getValueTxt(): "+ fvDT.getValueTxt());
                     logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@fvDT.getReportFilterUid(): "+ fvDT.getReportFilterUid());
                     logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@fvDT.NEW?????: "+ fvDT.isItNew());
                     logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@fvDT.DIRTY???????: "+ fvDT.isItDirty());
                     logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@fvDT.Delete?????????: "+ fvDT.isItDelete());
                 }
             }
             ArrayList<Object> test1 = (ArrayList<Object> )reportVO.getTheDisplayColumnDTList();
            Iterator<Object>  anIterator = null;
             for(anIterator = test1.iterator(); anIterator.hasNext();) {
                 DisplayColumnDT dt = (DisplayColumnDT)anIterator.next();
               logger.debug("The displayColumnUID is : "+dt.getDisplayColumnUid());
               logger.debug("The displayColumnUID/ReportUID is : "+dt.getReportUid());
               logger.debug("The displayColumn delete is : "+dt.isItDelete());
                 logger.debug("The displayColumn new is : "+dt.isItNew());
             }
           */
          sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
          sMethod = "setReport";
          oParams = new Object[] {
              reportVO};
          if (msCommand == null) {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
          }
          msCommand.processRequest(sBeanJndiName, sMethod, oParams);
          removeSessionAttributes(session);
          makeReportList(request);
          String mode = request.getParameter(ReportConstantUtil.MODE);
          session.setAttribute(ReportConstantUtil.MODE, mode);
          return (ReportConstantUtil.REPORT_LIST_PAGE);
        }
        catch (Exception e) {
          if (session == null) {
            logger.debug("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.LOGIN_PAGE);
          }
          else {
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            logger.fatal("ReportWebProcessor.execute", e);
            return (ReportConstantUtil.ERROR_PAGE);
          }
        }
      } //UPDATE_REPORT
      default:
    } //switch
    return errorPage;
  } //execute

  /**
   *  Main method of ReportWebProcessor
   *  @param args[]
   */
  public static void main(String[] args) {
  }

  /**
   * setAdvanceData - saves Advance Page contents whenever user switches between pages to session
   * @params
   */
  private String setAdvanceData(HttpSession session, HttpServletRequest request) {
    String page = null;
    boolean advanceData = false;
    try {
      logger.info("1259");
      ArrayList<Object> reportList = (ArrayList<Object> ) session.getAttribute(
          ReportConstantUtil.RESULT);
      logger.info("1261");
      ReportVO newReportVO = (ReportVO) reportList.get(0);
      logger.info("1263");
      String criteria = request.getParameter(ReportConstantUtil.CRITERIA);
      logger.info("~~RWP 1265: " + criteria);
      if (criteria != null) {
        //          criteria = URLDecoder.decode(criteria);
        logger.info("~~criteria from page to RWP: " + criteria);
        if(criteria.length() > 15) advanceData = true;
        
        AdvanceWebProcessor.setWhereClauseBuilder(newReportVO, criteria);
        String newCriteriaList = AdvanceWebProcessor.getWhereClauseBuilder(
            newReportVO);
        logger.info(" newCriteria List<Object> is: " + newCriteriaList);
        session.setAttribute(ReportConstantUtil.CRITERIA_LIST, newCriteriaList);
        ArrayList<Object> newReportList = new ArrayList<Object> ();
        newReportList.add(newReportVO);
        session.setAttribute(ReportConstantUtil.RESULT, newReportList);
      }
      session.setAttribute(ReportConstantUtil.ADVANCE_FILTERS_ENTERED, String.valueOf(advanceData));
    }
    catch (Exception e) {
      if (session == null) {
        logger.debug("ReportWebProcessor.setAdvanceData", e);
        page = ReportConstantUtil.LOGIN_PAGE;
      }
      else {
        try {
          msCommand = mainSessionHolder.getMainSessionCommand(session);
          logger.fatal("ReportWebProcessor.setAdvanceData", e);
          page = ReportConstantUtil.ERROR_PAGE;
        }
        catch (Exception e1) {
          logger.fatal("ReportWebProcessor.setAdvanceData", e1);
        }
      }
    }
    return page;
  } //setAdvanceData

  /**
   * setBasicData - saves Basic Page contents whenever user switches between pages to session
   * @params
   */
  private String setBasicData(HttpSession session, HttpServletRequest request) {
    String page = null;
    boolean basicData = false;
    try {
      ArrayList<Object> reportList = (ArrayList<Object> ) session.getAttribute(
          ReportConstantUtil.RESULT);
      ReportVO newReportVO = (ReportVO) reportList.get(0);
      //start test code to see the filters & values.............................................
      String[] diseaseValues = (String[]) request.getParameterValues(ReportConstantUtil.DISEASE);
      if(diseaseValues != null && diseaseValues.length > 0)
    	  basicData = true;
      /*
      if (diseaseValues != null) { //check condition for disease filter existance
        for (int i = 0; i < diseaseValues.length; i++) {
          logger.info("Diseases selected are: " + diseaseValues[i]);
        }
      }
      */
      //check selectAll Button (if present)      
      String selectAllDiseases = (String) request.getParameter("disease_select_all");
      if(selectAllDiseases != null && selectAllDiseases.equals("on")) {
    	  basicData = true;
    	  session.setAttribute(ReportConstantUtil.SELECT_ALL_DISEASES, "true");  
      } else {
    	  session.setAttribute(ReportConstantUtil.SELECT_ALL_DISEASES, "false");
      }
      
      //include null diseases       
      String includeNullDiseases = (String) request.getParameter("disease_include_nulls");
      if(includeNullDiseases != null && includeNullDiseases.equals("on")) {
    	  basicData = true;
    	  includeNullDiseases = ReportConstantUtil.ALLOW_NULLS;
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_DISEASE, "true");  
      } else {
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_DISEASE, "false");
      }
      
      
      
    //Custom Coded Filter
      String[] codedValues = (String[]) request.getParameterValues(ReportConstantUtil.CVG_CUSTOM);
      if(codedValues != null && codedValues.length > 0)
    	  basicData = true;
      //check selectAll Button (if present)      
      String selectAllCodedVAlues = (String) request.getParameter("cvg_select_all");
      if(selectAllCodedVAlues != null && selectAllCodedVAlues.equals("on")) {
    	  basicData = true;
    	  session.setAttribute(ReportConstantUtil.SELECT_ALL_CVG, "true");  
      } else {
    	  session.setAttribute(ReportConstantUtil.SELECT_ALL_CVG, "false");
      }
      
      
      
      //include null Coded Values       
      String includeNullCVG = (String) request.getParameter("cvg_include_nulls");
      if(includeNullCVG != null && includeNullCVG.equals("on")) {
    	  basicData = true;
    	  includeNullCVG = ReportConstantUtil.ALLOW_NULLS;
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_CVG, "true");  
      } else {
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_CVG, "false");
      }
      
      String textValue = (String) request.getParameter(ReportConstantUtil.TEXT_FILTER);
      if(textValue != null && textValue.trim().length() > 0)
    	  basicData = true;
      String daysValue = (String) request.getParameter(ReportConstantUtil.DAYS_FILTER);
      if(daysValue != null && daysValue.trim().length() > 0)
    	  basicData = true;
      //Worker Filter
      String[] wrkrValues = (String[]) request.getParameterValues(ReportConstantUtil.STD_HIV_WRKR);
      if(wrkrValues != null && wrkrValues.length > 0)
    	  basicData = true;
      //check selectAll Button (if present)      
      String selectAllWRKRValues = (String) request.getParameter("wrkr_select_all");
      if(selectAllWRKRValues != null && selectAllWRKRValues.equals("on")) {
    	  basicData = true;
    	  session.setAttribute(ReportConstantUtil.SELECT_ALL_WRKR, "true");  
      } else {
    	  session.setAttribute(ReportConstantUtil.SELECT_ALL_WRKR, "false");
      }
      
    
      String[] stateValues = (String[]) request.getParameterValues(ReportConstantUtil.STATE);
      if(stateValues != null && stateValues.length > 0)
    	  basicData = true;      
      /*
      if (stateValues != null) { //check condition for state filter existance
        for (int i = 0; i < stateValues.length; i++) {
          logger.info("States selected are: " + stateValues[i]);
        }
      }
      */
      //include null states       
      String includeNullStates = (String) request.getParameter("state_include_nulls");
      if(includeNullStates != null && includeNullStates.equals("on")) {
    	  basicData = true;
    	  includeNullStates = ReportConstantUtil.ALLOW_NULLS;
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_STATE, "true");
      } else {
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_STATE, "false");
      }
      
      String[] countyValues = (String[]) request.getParameterValues(ReportConstantUtil.COUNTY);
      if(countyValues != null && countyValues.length > 0)
    	  basicData = true;       
      /*
      if (countyValues != null) { //check condition for county filter existance
        for (int i = 0; i < countyValues.length; i++) {
          logger.info("Counties selected are: " + countyValues[i]);
        }
      }
      */
      //check selectAll Button (if present)      
      String selectAllCounties = (String) request.getParameter("county_select_all");
      if(selectAllCounties != null && selectAllCounties.equals("on")) {
    	  basicData = true;
    	  session.setAttribute(ReportConstantUtil.SELECT_ALL_COUNTIES, "true");  
      } else {
    	  session.setAttribute(ReportConstantUtil.SELECT_ALL_COUNTIES, "false");
      }
      
      //include null counties       
      String includeNullCounties = (String) request.getParameter("county_include_nulls");
      if(includeNullCounties != null && includeNullCounties.equals("on")) {
    	  basicData = true;
    	  includeNullCounties = ReportConstantUtil.ALLOW_NULLS;
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_COUNTY, "true");
      } else {
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_COUNTY, "false");
      }


      String[] regionValues = (String[]) request.getParameterValues(ReportConstantUtil.REGION);
      if(regionValues != null && regionValues.length > 0)
    	  basicData = true;          
      /*
      if (regionValues != null) { //check condition for region filter existance
        for (int i = 0; i < regionValues.length; i++) {
          logger.info("Counties selected are: " + regionValues[i]);
        }
      }
      */
      //include null regions       
      String includeNullRegions = (String) request.getParameter("region_include_nulls");
      if(includeNullRegions != null && includeNullRegions.equals("on")) {
    	  basicData = true;
    	  includeNullRegions = ReportConstantUtil.ALLOW_NULLS;
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_REGION, "true");
      } else {
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_REGION, "false");
      }
      
      String monthYearRangeFrom = request.getParameter(ReportConstantUtil.MONTH_YEAR_RANGE_FROM);
      String monthYearRangeTo = request.getParameter(ReportConstantUtil.MONTH_YEAR_RANGE_TO);
      if(monthYearRangeFrom != null && monthYearRangeFrom.length() > 0)
    	  basicData = true;  
      if(monthYearRangeTo != null && monthYearRangeTo.length() > 0)
    	  basicData = true;   
      
      //include null monthYearRange       
      String includeNullMonthYearRange = (String) request.getParameter("monthYearRange_include_nulls");
      if(includeNullMonthYearRange != null && includeNullMonthYearRange.equals("on")) {
    	  basicData = true;
    	  includeNullMonthYearRange = ReportConstantUtil.ALLOW_NULLS;
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_MONTHYEAR_RANGE, "true");
      } else {
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_MONTHYEAR_RANGE, "false");
      }
      
      String timeRangeFrom = request.getParameter(ReportConstantUtil.TIME_RANGE_FROM);
      String timeRangeTo = request.getParameter(ReportConstantUtil.TIME_RANGE_TO);
      if(timeRangeFrom != null && timeRangeFrom.length() > 0)
    	  basicData = true;  
      if(timeRangeTo != null && timeRangeTo.length() > 0)
    	  basicData = true;        
      
      //include null timeRange       
      String includeNullTimeRange = (String) request.getParameter("timeRange_include_nulls");
      if(includeNullTimeRange != null && includeNullTimeRange.equals("on")) {
    	  basicData = true;
    	  includeNullTimeRange = ReportConstantUtil.ALLOW_NULLS;
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_TIMERANGE, "true");
      } else {
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_TIMERANGE, "false");
      }
      
      String timePeriodFrom = request.getParameter(ReportConstantUtil.TIME_PERIOD_FROM);
      String timePeriodTo = request.getParameter(ReportConstantUtil.TIME_PERIOD_TO);
      if(timePeriodFrom != null && timePeriodFrom.length() > 0)
    	  basicData = true;  
      if(timePeriodTo != null && timePeriodTo.length() > 0)
    	  basicData = true; 
      
      //include null timePeriod       
      String includeNullTimePeriod = (String) request.getParameter("timePeriod_include_nulls");
      if(includeNullTimePeriod != null && includeNullTimePeriod.equals("on")) {
    	  basicData = true;
    	  includeNullTimePeriod = ReportConstantUtil.ALLOW_NULLS;
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_TIMEPERIOD, "true");
      } else {
    	  session.setAttribute(ReportConstantUtil.INCLUDE_NULL_TIMEPERIOD, "false");
      }
      
      logger.info("timePeriodTo 1123: " + timePeriodTo);
      //.........end test code to see the filters & values.............................................
      ArrayList<Object> filtersDTCollection  = (ArrayList<Object> ) newReportVO.
          getTheReportFilterDTCollection();
      //int capacity = filtersDTCollection.size();
      //logger.info("capacity is: "+capacity);
     Iterator<Object>  iFilters = filtersDTCollection.iterator();
      outer:while (iFilters.hasNext()) {
        ReportFilterDT filterDT = (ReportFilterDT) iFilters.next();
        FilterCodeDT filterCodeDT = (FilterCodeDT) filterDT.getTheFilterCodeDT();
        if (filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.
            DISEASE_CODE)) {
        	
          String diseases = BasicWebProcessor.setDiseases(newReportVO, filterDT,
              diseaseValues, includeNullDiseases);
          //logger.info("RWP 270 --- "+diseases);
          session.setAttribute(ReportConstantUtil.DISEASE_CODE, diseases);
          continue outer;
        }
        
        else if (filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.
        		CVG_CUSTOM)) {
            	
              String cvg = BasicWebProcessor.setCodedValues(newReportVO, filterDT,
            		  codedValues, includeNullCVG);
              //logger.info("RWP 270 --- "+diseases);
              session.setAttribute(ReportConstantUtil.CVG_CUSTOM, cvg);
              continue outer;
            }
        else if (filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.
        		TEXT_FILTER)) {
            	
              BasicWebProcessor.setTextValue(newReportVO, filterDT,
            		  textValue, includeNullCVG);
              //logger.info("RWP 270 --- "+diseases);
              session.setAttribute(ReportConstantUtil.TEXT_FILTER, textValue);
              continue outer;
            }
        else if (filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.
        		DAYS_FILTER)) {
              BasicWebProcessor.setTextValue(newReportVO, filterDT,
            		  daysValue, includeNullCVG);
              session.setAttribute(ReportConstantUtil.DAYS_FILTER, daysValue);
              continue outer;
            }
        else if (filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.STD_HIV_WRKR
        		)) {
            	
              String wrkr = BasicWebProcessor.setCodedValues(newReportVO, filterDT,
            		  wrkrValues, null);
              //logger.info("RWP 270 --- "+diseases);
              session.setAttribute(ReportConstantUtil.STD_HIV_WRKR, wrkr);
              continue outer;
            }
        else if (filterCodeDT.getFilterCode().startsWith(
            ReportConstantUtil.STATE_CODE)) {

          String states = BasicWebProcessor.setStates(newReportVO, filterDT,
              stateValues, includeNullStates);
          //logger.info("states:----> "+states);
          session.setAttribute(ReportConstantUtil.STATE_CODE, states);
          continue outer;
        }
        else if (filterCodeDT.getFilterCode().startsWith(
            ReportConstantUtil.COUNTY_CODE)) {

        	String countyCd = (String)request.getParameter(ReportConstantUtil.COUNTY_CODE);
        	
      	  if(filterDT.getTheFilterValueDTCollection().size() > 0 || (countyCd != null && countyCd.length() > 0))
      		session.setAttribute(ReportConstantUtil.COUNTIES_SELECTED,"true");
      	  
          String currentState = session.getAttribute(ReportConstantUtil.
              CURRENT_STATE).toString();
          String counties = BasicWebProcessor.setCounties(newReportVO, filterDT,
              countyValues, currentState, includeNullCounties);
          //logger.info("counties:----> "+counties);
          session.setAttribute(ReportConstantUtil.COUNTY_CODE, counties);
          continue outer;
        }
        else if (filterCodeDT.getFilterCode().startsWith(
            ReportConstantUtil.REGION_CODE)) {

          String regions = BasicWebProcessor.setRegions(newReportVO, filterDT,
              regionValues, includeNullRegions);
          //logger.info("regions:----> "+regions);
          session.setAttribute(ReportConstantUtil.REGION_CODE, regions);
          continue outer;
        }
        else if (filterCodeDT.getFilterCode().startsWith(
            ReportConstantUtil.TIME_RANGE_CODE)) {

          String[] timeRange = BasicWebProcessor.setTimeRange(newReportVO,
              filterDT, timeRangeFrom, timeRangeTo, includeNullTimeRange);
          logger.info("******** set -- timeRangeFrom: " + timeRange[0]);
          logger.info("******** set -- timeRangeTo: " + timeRange[1]);
          session.setAttribute(ReportConstantUtil.TIME_RANGE_FROM, timeRange[0]);
          session.setAttribute(ReportConstantUtil.TIME_RANGE_TO, timeRange[1]);
          continue outer;
        }
        else if (filterCodeDT.getFilterCode().startsWith(
                ReportConstantUtil.MONTH_YEAR_RANGE_CODE)) {

              String[] monthYearRange = BasicWebProcessor.setMonthYearRange(newReportVO,
                  filterDT, monthYearRangeFrom, monthYearRangeTo, includeNullTimeRange);
              logger.info("******** set -- monthYearRangeFrom: " + monthYearRange[0]);
              logger.info("******** set -- monthYearRangeTo: " + monthYearRange[1]);
              session.setAttribute(ReportConstantUtil.MONTH_YEAR_RANGE_FROM, monthYearRange[0]);
              session.setAttribute(ReportConstantUtil.MONTH_YEAR_RANGE_TO, monthYearRange[1]);
              continue outer;
            }
        else if (filterCodeDT.getFilterCode().startsWith(
            ReportConstantUtil.TIME_PERIOD_CODE)) {

          String[] timePeriod = BasicWebProcessor.setTimePeriod(newReportVO,
              filterDT, timePeriodFrom, timePeriodTo, includeNullTimePeriod);
          logger.info("set -- timePeriodFrom: " + timePeriod[0]);
          logger.info("set -- timePeriodTo: " + timePeriod[1]);
          session.setAttribute(ReportConstantUtil.TIME_PERIOD_FROM,
                               timePeriod[0]);
          session.setAttribute(ReportConstantUtil.TIME_PERIOD_TO, timePeriod[1]);
          continue outer;
        }
      } //outer while
      ArrayList<Object> newReportList = new ArrayList<Object> ();
      newReportList.add(newReportVO);
      session.setAttribute(ReportConstantUtil.RESULT, newReportList);
      session.setAttribute(ReportConstantUtil.BASIC_FILTERS_ENTERED, String.valueOf(basicData));
      String basicRequired = request.getParameter(ReportConstantUtil.BASIC_REQUIRED_ENTERED);
      session.setAttribute(ReportConstantUtil.BASIC_REQUIRED_ENTERED, basicRequired);
      String anyBasicRequired = request.getParameter(ReportConstantUtil.ANY_BASIC_REQUIRED);
      session.setAttribute(ReportConstantUtil.ANY_BASIC_REQUIRED, anyBasicRequired);
      String isBasicDataValid = request.getParameter(ReportConstantUtil.IS_BASICDATA_VALID);
      session.setAttribute(ReportConstantUtil.IS_BASICDATA_VALID, isBasicDataValid);      
      
    }
    catch (Exception e) {
      if (session == null) {
        logger.debug("ReportWebProcessor.setBasicData", e);
        page = ReportConstantUtil.LOGIN_PAGE;
      }
      else {
        try {
          msCommand = mainSessionHolder.getMainSessionCommand(session);
          logger.fatal("ReportWebProcessor.setBasicData", e);
          page = ReportConstantUtil.ERROR_PAGE;
        }
        catch (Exception e1) {
          logger.fatal("ReportWebProcessor.setBasicData", e1);
        }
      }
    }
    return page;
  } //setBasicData

  /**
   * setColumnData - saves Basic Page contents whenever user switches between pages to session
   * @params
   */
  private String setColumnData(HttpSession session, HttpServletRequest request) {
    String page = null;
    try {
      ArrayList<Object> reportList = (ArrayList<Object> ) session.getAttribute(
          ReportConstantUtil.RESULT);
      ReportVO newReportVO = (ReportVO) reportList.get(0);
      ArrayList<Object> availableColList = (ArrayList<Object> ) session.getAttribute(
          ReportConstantUtil.AVAILABLE_COLUMN_LIST);
      String selectedColumns = request.getParameter(ReportConstantUtil.
          SELECTED_COLUMNS);
      String availableColumns = request.getParameter(ReportConstantUtil.
          AVAILABLE_COLUMNS);
      // For Sort By column
      String sortBy = request.getParameter(ReportConstantUtil.SORT_BY);
      String sortColumn = request.getParameter(ReportConstantUtil.SORT_COLUMN);
      sortBy = XMLRequestHelper.updateDOMTable(sortBy,sortColumn);
      session.setAttribute(ReportConstantUtil.SORT_BY,
    		  sortBy);
      session.setAttribute(ReportConstantUtil.SORT_COLUMN,
    		  sortColumn);
      
//    For Sort Order column
      String sortOrder = request.getParameter(ReportConstantUtil.SORT_ORDER);
      String sortOrderColumns = ColumnWebProcessor.getSortOrders(newReportVO,ReportConstantUtil.SORT_ORDER,sortOrder);
      
      session.setAttribute(ReportConstantUtil.SORT_ORDER, sortOrderColumns);
      session.setAttribute(ReportConstantUtil.SELECTED_COLUMNS, selectedColumns);
      session.setAttribute(ReportConstantUtil.AVAILABLE_COLUMNS, availableColumns);
      
      if (selectedColumns != null) {
        ReportVO newReportVO1 = ColumnWebProcessor.setSelectedColumns(
            newReportVO, selectedColumns, availableColList,sortColumn,sortOrder);
        ArrayList<Object> newReportList = new ArrayList<Object> ();
        newReportList.add(newReportVO1);
        session.setAttribute(ReportConstantUtil.RESULT, newReportList);
      }
    }
    catch (Exception e) {
      if (session == null) {
        logger.debug("ReportWebProcessor.setColumnData", e);
        page = ReportConstantUtil.LOGIN_PAGE;
      }
      else {
        try {
          msCommand = mainSessionHolder.getMainSessionCommand(session);
          logger.fatal("ReportWebProcessor.setColumnData", e);
          page = ReportConstantUtil.ERROR_PAGE;
        }
        catch (Exception e1) {
          logger.debug("ReportWebProcessor.setColumnData", e1);
        }
      }
    }
    return page;
  } //setColumnData

  /**
   * getCurrentState returns the state code for use in different method calls
   */
  private String getCurrentState(ReportVO rVO) {
    String state = null;
    ArrayList<Object> rfList = (ArrayList<Object> ) rVO.getTheReportFilterDTCollection();
   Iterator<Object>  rfIterator = null;
    for (rfIterator = rfList.iterator(); rfIterator.hasNext(); ) {
      ReportFilterDT rfDT = (ReportFilterDT) rfIterator.next();
      FilterCodeDT filterCodeDT = rfDT.getTheFilterCodeDT();
      if (filterCodeDT.getFilterCode().startsWith(ReportConstantUtil.
          STATE_CODE)) {
        ArrayList<Object> fvList = (ArrayList<Object> ) rfDT.getTheFilterValueDTCollection();
       Iterator<Object>  fvIterator = null;
        for (fvIterator = fvList.iterator(); fvIterator.hasNext(); ) {
          FilterValueDT fvDT = (FilterValueDT) fvIterator.next();
          state = fvDT.getValueTxt();
        }
      }
    }
    return state;
  } //getCurrentState

  /**
   * getReferer() returns the page name from where you came from
   * @param HttpServletRequest
   */
  private String getReferer(HttpServletRequest request) {
    String s1 = "referer";
    String ref = request.getHeader(s1);
    StringTokenizer st = new StringTokenizer(ref, "/");
    String lastToken = null;
    while (st.hasMoreTokens()) {
      lastToken = st.nextToken();
    }
    //logger.info("~~~~~~~~~~Referer is: "+ ref);
    //logger.info("~~~~~~~~~~Last Token is: "+ lastToken);
    String page = ReportConstantUtil.REPORT_LIST_PAGE;
    if (lastToken.equalsIgnoreCase("reports")) {
      page = ReportConstantUtil.REPORT_LIST_PAGE;
    }
    else if (lastToken.equalsIgnoreCase("basic")) {
      page = ReportConstantUtil.BASIC_PAGE;
    }
    else if (lastToken.equalsIgnoreCase("advanced")) {
      page = ReportConstantUtil.ADVANCE_PAGE;
    }
    else if (lastToken.equalsIgnoreCase("column")) {
      page = ReportConstantUtil.COLUMN_PAGE;
    }
    else if (lastToken.equalsIgnoreCase("run")) {
      page = ReportConstantUtil.RUNPAGE;
    }
    else if (lastToken.equalsIgnoreCase("save")) {
      page = ReportConstantUtil.SAVEPAGE;
    }
    else if (lastToken.equalsIgnoreCase("error")) {
      page = ReportConstantUtil.ERROR_PAGE;
    }
    return page;
  } //getReferer

  /**
   * basicPageMaker - adds fileds to stringbuffer for MAX VAL, MIN VAL details etc...
   * @param StringBuffer, ReportFilterDT, FilterCodeDT
   */
  private void basicPageMaker(StringBuffer basicDisplay,
                              ReportFilterDT filterDT,
                              FilterCodeDT filterCodeDT) {
    basicDisplay.append("<record>");
    basicDisplay.append("<field>");
    basicDisplay.append(filterDT.getFilterUid());
    basicDisplay.append("</field>");
    basicDisplay.append("<field>");
    basicDisplay.append(XMLRequestHelper.xmlEncode(filterCodeDT.getFilterCode()));
    basicDisplay.append("</field>");
    basicDisplay.append("<field>");
    basicDisplay.append(XMLRequestHelper.xmlEncode(filterCodeDT.getFilterType()));
    basicDisplay.append("</field>");
    basicDisplay.append("<field>");
    basicDisplay.append(XMLRequestHelper.xmlEncode(filterCodeDT.getFilterName()));
    basicDisplay.append("</field>");
    basicDisplay.append("<field>");
    basicDisplay.append(filterDT.getMinValueCnt());
    basicDisplay.append("</field>");
    basicDisplay.append("<field>");
    basicDisplay.append(filterDT.getMaxValueCnt());
    basicDisplay.append("</field>");
    basicDisplay.append("<field>");
    basicDisplay.append(filterDT.getReportFilterInd() == null ? "N" : filterDT.getReportFilterInd().trim().toUpperCase());
    basicDisplay.append("</field>");
    basicDisplay.append("</record>");
  } //basicPageMaker

  /**
   * This Method deletes pvt, pub and template lists and creates new lists
   * @param HttSession
   */
  private void makeReportList(HttpServletRequest request) throws Exception {

    ArrayList<Object> reportList = null;
    ArrayList<Object> myReportList = null;
    ArrayList<Object> sharedReportList = null;
    ArrayList<Object> templateReportList = null;

    sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
    sMethod = "getReportList";
    oParams = new Object[] {};
    if (msCommand == null) {
      msCommand = mainSessionHolder.getMainSessionCommand(request.getSession());
    }
    //All my Report List, private, shared, template
    reportList = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod,
        oParams).get(0);
    myReportList = (ArrayList<Object> ) reportList.get(0);
    sharedReportList = (ArrayList<Object> ) reportList.get(1);
    templateReportList = (ArrayList<Object> ) reportList.get(2);
    logger.info("# of myReportList = " + myReportList.size() +
    		"; # of sharedReportList = " + sharedReportList.size() +
    		"; # of templateReportList = " + templateReportList.size());
    TreeMap<Object,Object> categorizedReports = categorizeReportsIntoSections(myReportList,sharedReportList,templateReportList);
    request.setAttribute("ReportsTree", categorizedReports);
 
    HttpSession session = request.getSession();
    //Private Report List
    if (myReportList != null && myReportList.size() > 0) {
        String privateReportList = ReportListWebProcessor.getPrivateReportList(myReportList).toString();
        session.setAttribute(ReportConstantUtil.PRIVATE_REPORT_LIST,privateReportList);
      } else {
        session.setAttribute(ReportConstantUtil.PRIVATE_REPORT_LIST, "");
      }
      //Public Report List
      if (sharedReportList != null && sharedReportList.size() > 0) {
        String publicReportList = ReportListWebProcessor.getPublicReportList(sharedReportList);
        session.setAttribute(ReportConstantUtil.PUBLIC_REPORT_LIST,publicReportList);
      } else {
        session.setAttribute(ReportConstantUtil.PUBLIC_REPORT_LIST, "");
      }
      //Template Report List
      if (templateReportList != null && templateReportList.size() > 0) {
        String reportTemplateList = ReportListWebProcessor.getReportTemplateList(templateReportList);
        session.setAttribute(ReportConstantUtil.TEMPLATE_REPORT_LIST, reportTemplateList);
      } else {
        session.setAttribute(ReportConstantUtil.TEMPLATE_REPORT_LIST, "");
      }
  } //makeReportList

  	/**
  	 * This method creates a single TreeMap<Object,Object> object that comprises of all report types namely :
  	 * private/my reports, public and template. 
  	 * @param myReportList
  	 * @param sharedReportList
  	 * @param templateReportList
  	 * @return TreeMap<Object,Object> object that holds all the reports categorized into different types. 
  	 */
  	private TreeMap<Object,Object> categorizeReportsIntoSections(ArrayList<Object>  myReportList, ArrayList<Object> sharedReportList, 
  			ArrayList<Object> templateReportList)
  	{
  		TreeMap<Object,Object> categorizedReportsTree = new TreeMap<Object,Object>();
  		TreeMap<Object,Object> myReportsTree = new TreeMap<Object,Object>();
  		TreeMap<Object,Object> publicReportsTree = new TreeMap<Object,Object>();
  		TreeMap<Object,Object> templateReportsTree = new TreeMap<Object,Object>();
  	
  		String privateSectionId = "private";
  		String publicSectionId = "public";
  		String templateSectionId = "template";
      
  		if (myReportList != null && myReportList.size() > 0) 
  		{
			Iterator<Object> reportsIter = myReportList.iterator();
			while (reportsIter.hasNext())
			{
				ReportDT singleReport = (ReportDT) reportsIter.next();
				ArrayList<Object> list = null;
				if (myReportsTree.containsKey(String.valueOf(singleReport
						.getSectionCd()))) {
					list = (ArrayList<Object> ) myReportsTree.get(String
							.valueOf(singleReport.getSectionCd()));
					list.add(singleReport);
				} else {
					list = new ArrayList<Object> ();
					list.add(singleReport);
				}
				myReportsTree.put(String.valueOf(singleReport
						.getSectionDescTxt()), list);
			}
		}
      
      if (sharedReportList != null && sharedReportList.size() > 0) {

			Iterator<Object> reportsIter = sharedReportList.iterator();

			while (reportsIter.hasNext()) {
				ReportDT singleReport = (ReportDT) reportsIter.next();

				ArrayList<Object> list = null;
				if (publicReportsTree.containsKey(String.valueOf(singleReport
						.getSectionCd()))) {
					list = (ArrayList<Object> ) publicReportsTree.get(String
							.valueOf(singleReport.getSectionCd()));
					list.add(singleReport);
				} else {
					list = new ArrayList<Object> ();
					list.add(singleReport);
				}
				publicReportsTree.put(String.valueOf(singleReport
						.getSectionDescTxt()), list);
			}
		}
      
      if (templateReportList != null && templateReportList.size() > 0) {

			Iterator<Object> reportsIter = templateReportList.iterator();

			while (reportsIter.hasNext()) {
				ReportDT singleReport = (ReportDT) reportsIter.next();

				ArrayList<Object> list = null;
				if (templateReportsTree.containsKey(String.valueOf(singleReport
						.getSectionCd()))) {
					list = (ArrayList<Object> ) templateReportsTree.get(String
							.valueOf(singleReport.getSectionCd()));
					list.add(singleReport);
				} else {
					list = new ArrayList<Object> ();
					list.add(singleReport);
				}
				templateReportsTree.put(String.valueOf(singleReport
						.getSectionDescTxt()), list);
			}
		}

  	categorizedReportsTree.put(privateSectionId, myReportsTree);
  	categorizedReportsTree.put(publicSectionId, publicReportsTree);
  	categorizedReportsTree.put(templateSectionId, templateReportsTree);
  	
  	return categorizedReportsTree;
  }

  /**
   * removeSesionAttributes - removes all other sessionAttributes excluding list attributes
   * @param HttpSession
   */
  public static void removeSessionAttributes(HttpSession session) {
    session.removeAttribute(ReportConstantUtil.OLD_RESULT);
    session.removeAttribute(ReportConstantUtil.RESULT);
    session.removeAttribute(ReportConstantUtil.REPORT_UID);
    session.removeAttribute(ReportConstantUtil.DATASOURCE_UID);
    session.removeAttribute(ReportConstantUtil.DISEASE_CODE);
    session.removeAttribute(ReportConstantUtil.STATE_CODE);
    session.removeAttribute(ReportConstantUtil.REGION_CODE);
    session.removeAttribute(ReportConstantUtil.COUNTY_CODE);
    session.removeAttribute(ReportConstantUtil.TIME_PERIOD_CODE);
    session.removeAttribute(ReportConstantUtil.TIME_RANGE_CODE);
    session.removeAttribute(ReportConstantUtil.MONTH_YEAR_RANGE_CODE);
    session.removeAttribute(ReportConstantUtil.CRITERIA_LIST);
    session.removeAttribute(ReportConstantUtil.FILTERS);
    session.removeAttribute(ReportConstantUtil.FILTERABLE_COLUMNS);
    session.removeAttribute(ReportConstantUtil.AVAILABLE_COLUMNS);
    session.removeAttribute(ReportConstantUtil.AVAILABLE_COLUMN_LIST);
    session.removeAttribute(ReportConstantUtil.SELECTED_COLUMNS);
    session.removeAttribute(ReportConstantUtil.CURRENT_STATE);
    session.removeAttribute(ReportConstantUtil.SELECT_ALL_DISEASES);
    session.removeAttribute(ReportConstantUtil.SELECT_ALL_COUNTIES);
    session.removeAttribute(ReportConstantUtil.COUNTIES_SELECTED);
    session.removeAttribute(ReportConstantUtil.BASIC_FILTERS_ENTERED);
    session.removeAttribute(ReportConstantUtil.ADVANCE_FILTERS_ENTERED);   
    session.removeAttribute(ReportConstantUtil.BASIC_REQUIRED_ENTERED);
    session.removeAttribute(ReportConstantUtil.ANY_BASIC_REQUIRED);
    session.removeAttribute(ReportConstantUtil.IS_BASICDATA_VALID); 
    session.removeAttribute(ReportConstantUtil.REPORT_SECTION); 
    
    session.removeAttribute(ReportConstantUtil.CVG_CUSTOM);
    session.removeAttribute(ReportConstantUtil.SELECT_ALL_CVG);
    session.removeAttribute(ReportConstantUtil.CVG_CUSTOM_N	);
    session.removeAttribute(ReportConstantUtil.STD_HIV_WRKR);
    session.removeAttribute(ReportConstantUtil.SELECT_ALL_WRKR);
    session.removeAttribute(ReportConstantUtil.DAYS_FILTER);
    
    session.removeAttribute("dateType");
  } //removeSessionAttribues

  /**
   * showReportVO- for unpacking & displaying VO elements
   * @param ReportVO
   */
  private void showReportVO(ReportVO rVO) {
    ArrayList<Object> rfList = (ArrayList<Object> ) rVO.getTheReportFilterDTCollection();
   Iterator<Object>  rfIterator = null;
    for (rfIterator = rfList.iterator(); rfIterator.hasNext(); ) {
      ReportFilterDT rfDT = (ReportFilterDT) rfIterator.next();
      FilterCodeDT fcDT = rfDT.getTheFilterCodeDT();
      logger.info("%%%%%%%%%%%%%%%%% fcDT.getFilterName(): --> " +
                  fcDT.getFilterName());
      ArrayList<Object> fvList = (ArrayList<Object> ) rfDT.getTheFilterValueDTCollection();
     Iterator<Object>  fvIterator = null;
      for (fvIterator = fvList.iterator(); fvIterator.hasNext(); ) {
        FilterValueDT fvDT = (FilterValueDT) fvIterator.next();
        logger.info("%%%%%%%%%%%%%%%%% fvDT.getValueType(): --> " +
                    fvDT.getValueType());
        logger.info("%%%%%%%%%%%%%%%%% fvDT.getValueTxt(): --> " +
                    fvDT.getValueTxt());
      }
    }
  } //showReportVO

  private String getDateFieldType(ReportFilterDT filterDT, HttpSession session) throws
      Exception {

    String dateType = "";
    Long dataSourceUid = filterDT.getDataSourceUid() ;
    Long columnUid = filterDT.getColumnUid();

    sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
    sMethod = "getDataSource";
    oParams = new Object[] {dataSourceUid};

    if (msCommand == null) {
      msCommand = mainSessionHolder.getMainSessionCommand(session);
    }

    ArrayList<Object> dsVOList = (ArrayList<Object> ) msCommand.processRequest(
        sBeanJndiName, sMethod, oParams);
    DataSourceVO dsVO = (DataSourceVO) dsVOList.get(0);
    ArrayList<Object> dataSourceColumnDTs = (ArrayList<Object> ) dsVO.
        getTheDataSourceColumnDTCollection();
    for (Iterator<Object> i = dataSourceColumnDTs.iterator(); i.hasNext(); ) {
      DataSourceColumnDT dataSourceColumnDT = (DataSourceColumnDT) i.next();
      if (dataSourceColumnDT.getColumnUid().equals(columnUid)) {
        dateType = dataSourceColumnDT.getColumnTitle();
        break;
      }
    }
    return dateType;

  } //getDateField
  
  private void setIncludeNullsInSession(ReportFilterDT filterDT, HttpSession session, String id) throws Exception {
	  
      //set the checkbox to include nulls
      ArrayList<Object> filterValues = (ArrayList<Object> )filterDT.getTheFilterValueDTCollection();
      if(filterValues.size()==0)
    	  session.setAttribute(id, "false");
      
     Iterator<Object>  iValues = null;
      for(iValues = filterValues.iterator(); iValues.hasNext();)
      {
          FilterValueDT filterValueDT = (FilterValueDT)iValues.next();
          if(filterValueDT.getFilterValueOperator() != null && filterValueDT.getFilterValueOperator().equalsIgnoreCase(ReportConstantUtil.ALLOW_NULLS))
          {
          	session.setAttribute(id, "true");
              break;
          } else {
          	session.setAttribute(id, "false");
          }
      }
  }

}


