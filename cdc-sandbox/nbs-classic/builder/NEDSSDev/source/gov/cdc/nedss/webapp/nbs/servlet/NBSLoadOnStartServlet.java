package gov.cdc.nedss.webapp.nbs.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import  javax.naming.*;
import javax.ejb.*;

import java.io.*;
import java.util.*;
import javax.rmi.*;
import java.rmi.*;

import gov.cdc.nedss.systemservice.ejb.dbauthejb.dao.DbAuthDAOImpl;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.scheduler.alertemail.alertemailmanager.AlertEmailManager;
import gov.cdc.nedss.systemservice.scheduler.cdfsubformimport.cdfsubformimportmanager.CdfSubformImportManager;
import gov.cdc.nedss.systemservice.scheduler.deduplicationprocess.deduplicationprocessmanager.DeduplicationProcessManager;
import gov.cdc.nedss.systemservice.scheduler.elr.elrmanager.ELRManager;
import gov.cdc.nedss.systemservice.scheduler.ldfsrtreport.ldfsrtreportmanager.LdfSrtReportManager;
import gov.cdc.nedss.systemservice.scheduler.masteretl.masteretlmanager.MasterEtlManager;
import gov.cdc.nedss.systemservice.scheduler.msgout.msgoutmanager.MsgOutManager;
import gov.cdc.nedss.systemservice.scheduler.nndldfextraction.nndldfextractionmanager.NndLdfExtractionManager;
import gov.cdc.nedss.systemservice.scheduler.phcmartetl.phcmartetlmanager.PhcmartEtlManager;
import gov.cdc.nedss.systemservice.scheduler.undocdfimport.undocdfimportmanager.UndoCdfImportManager;
import gov.cdc.nedss.systemservice.scheduler.userprofile.userprofilemanager.UserProfileManager;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.systemservice.exception.*;
import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.*;
import gov.cdc.nedss.systemservice.scheduler.geocodingprocess.geocodingprocessmanager.GeoCodingProcessManager;
import gov.cdc.nedss.systemservice.scheduler.importprocess.importprocessmanager.PHCRImporterManager;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

public class NBSLoadOnStartServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  static final PropertyUtil propUtil = PropertyUtil.getInstance();
  static final LogUtils logger = new LogUtils(NBSLoadOnStartServlet.class.getName());
  String strdedupstatus="before_start";
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
	  //1
	  try {
			UserProfileManager userprofileManager = new UserProfileManager();
			userprofileManager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks UserProfileManager" + ex);
		}
		//2
		try {
			ELRManager elrManager = new ELRManager();
			elrManager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks ELRManager" + ex);
		}
		//3
		try {
			PhcmartEtlManager phcMartManager = new PhcmartEtlManager();
			phcMartManager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks PhcmartEtlManager" + ex);
		}
		//4
		/*try {
			CdfSubformImportManager manager = new CdfSubformImportManager();
			manager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks PhcmartEtlManager" + ex);
		}*/
		//5
		/*try {
			DeduplicationProcessManager manager = new DeduplicationProcessManager();
			manager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks PhcmartEtlManager" + ex);
		}*/
		//5
		try {
			DeduplicationProcessManager manager = new DeduplicationProcessManager();
			manager.sameschedule();
			strdedupstatus = "sameschedule";
		//	manager.similarschedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks DeduplicationProcessManager" + ex);
		}
		//5

		try {

			   DeduplicationProcessManager manager = new DeduplicationProcessManager();
				//manager.sameschedule();
				manager.similarschedule(strdedupstatus);

			} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks DeduplicationProcessManager" + ex);
	    	}
		//6
		/*try {
			LdfSrtReportManager manager = new LdfSrtReportManager();
			manager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks PhcmartEtlManager" + ex);
		}*/
		//7
		try {
			MasterEtlManager manager = new MasterEtlManager();
			manager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks PhcmartEtlManager" + ex);
		}
		//8
		try {
			MsgOutManager manager = new MsgOutManager();
			manager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks PhcmartEtlManager" + ex);
		}
		//9
		/*try {
			UndoCdfImportManager manager = new UndoCdfImportManager();
			manager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks PhcmartEtlManager" + ex);
		}*/
		//10
		try {
			NndLdfExtractionManager manager = new NndLdfExtractionManager();
			manager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks PhcmartEtlManager" + ex);
		}
		//11 import process
		try {
			PHCRImporterManager importProcessManager = new PHCRImporterManager();
			importProcessManager.schedule();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks importProcessManager" + ex);
		}
		try {
				GeoCodingProcessManager manager = new GeoCodingProcessManager();
				manager.schedule();
			} catch (Exception ex) {
				logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks GeoCodingProcessManager" + ex);
		}
		try {
			//AlertEmailManager manager = new AlertEmailManager();
			//	manager.schedule();
			} catch (Exception ex) {
				logger.error("NBSLoadOnStartServlet: problem in intializing the schedued tasks AlertEmailManager" + ex);
		}

		//All the Summary Collection<Object>  needs to be loaded even before QuestionCache is loaded so that 1-Many relation between Question & AggregateSummary can be established.	
		try {
			QuestionsCache.getSummaryCollection();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in  QuestionsCache.getSummaryCollection:"+ ex);
		}
			
			/*
			 * Loads the Questions metadata for PAM forms(Labels, Tooltips
			 * ,rules etc) from database and cache them for use in rendering the
			 * JSPs
			 */
		
		try {
			QuestionsCache.getQuestionMap();
		} catch (Exception ex) {
				logger.error("NBSLoadOnStartServlet: problem in QuestionsCache.getQuestionMap:"+ ex);
		}
		
		
		/*
		 * Loads the Questions metadata for DMB forms(Labels, Tooltips
		 * ,rules etc) from database and cache them for use in rendering the
		 * JSPs
		 */
		
		//String serverRestart = propUtil.getServerRestart();
		//if (serverRestart != null && serverRestart.equalsIgnoreCase(NEDSSConstants.FALSE)){
			try {
				QuestionsCache.getDMBQuestionMap();
			} catch (Exception ex) {
				logger.error("NBSLoadOnStartServlet: problem in QuestionsCache.getDMBQuestionMap():"+ ex);
			}
		//}


		/*
		 * Loads the Questions metadata for PAM forms(Labels, Tooltips
		 * ,rules etc) from database and cache them for use in rendering the
		 * JSPs
		 */
		try {
			QuestionsCache.getContactQuestionMap();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in QuestionsCache.getContactQuestionMap():"+ ex);
		}
		
		try {
			QuestionsCache.getRuleMap();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in QuestionsCache.getRuleMap():"+ ex);
		}
		try {
			QuestionsCache.getFormRuleMap();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in QuestionsCache.getFormRuleMap():"+ ex);
		}
		// Rules for Contacts 
		try {
			QuestionsCache.getContactRuleMap();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet- Contact: problem in QuestionsCache.getContactRuleMap():"+ ex);
		}
		try {
			QuestionsCache.getContactFormRuleMap();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet:- Contact problem in QuestionsCache.getContactFormRuleMap() :"+ ex);
		}
		
		try {
			QuestionsCache.getNBSDocMetadataMap();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in QuestionsCache.getNBSDocMetadataMap():"+ ex);
		}
		try {
			QuestionsCache.getNBSDocMetadataMapBySchemaLocation();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in QuestionsCache.getNBSDocMetadataMapBySchemaLocation():"+ ex);
		}
		
		try {
			PageManagementActionUtil util = new PageManagementActionUtil();
			util.updateConditionCode();
		} catch (Exception ex) {
			logger.error("NBSLoadOnStartServlet: problem in PageManagementActionUtil util.updateConditionCode() :"+ ex);
		}
		

	  if (propUtil.getLoadOnStartupLabCache() != null &&
        propUtil.getLoadOnStartupLabCache().equalsIgnoreCase(NEDSSConstants.LOAD_ON_STARTUP)) {
      try {
        NedssUtils nedssUtils = new NedssUtils();
        Object objref = nedssUtils.lookupBean(JNDINames.SRT_CACHEMANAGER_EJB);
        SRTCacheManager srtManager = null;
        SRTCacheManagerHome home = (SRTCacheManagerHome) PortableRemoteObject.narrow(objref, SRTCacheManagerHome.class);
        srtManager = home.create();
       // Date date = new Date();
       // long time1 = date.getTime();
       // System.out.println("time1 is :" + time1);

        srtManager.initializeSRTCache();
       // Date date2 = new Date();
       // long time2 = date2.getTime();
       // System.out.println("time2 is :" + time2);

       // System.out.println("Total time taken:" + (time2 - time1));

      }
      catch (CreateException e) {
        logger.error("NBSLoadOnStartServlet:CreateException thrown from SRT CachemanageEjb " + e);
      }
      catch (RemoteException e) {
        logger.error("NBSLoadOnStartServlet:RemoteException thrown from SRT CachemanageEjb " + e);
      }
      catch (Exception e) {
        logger.error("NBSLoadOnStartServlet: Exception thrown from SRT CachemanageEjb " + e);
      }
    }
  }

//Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
     ServletException, IOException {
   }

  //Clean up resources
  public void destroy() {
  }


}