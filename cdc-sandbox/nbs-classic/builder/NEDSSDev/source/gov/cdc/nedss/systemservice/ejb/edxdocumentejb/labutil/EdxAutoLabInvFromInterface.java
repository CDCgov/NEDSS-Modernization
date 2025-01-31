package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NedssAppLogException;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean.EdxPHCRDocument;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean.EdxPHCRDocumentHome;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

/**ELR Importer code in updated to reflect the Performance related changes to process thousands of ELR per hour
 * @author Pradeep Kumar Sharma
 * @since 2012 
 * Updated to fix ELR import performance issues
 * @author Pradeep.Sharma
 * @Updated Release 5.4.6/6.0.5
 * ELR Importer parallel processing of ELR with a cap of concurrent thread processing
 * @Updated Release 5.4.7
 * 
 */
public class EdxAutoLabInvFromInterface {
	static final LogUtils logger = new LogUtils(EdxAutoLabInvFromInterface.class.getName());

	public static void main(String[] args) { 
		try {
			runSingleLERImportProcess();
			String flag = "";
			if(args.length>0)
				flag = args[0];

			if(flag==null || (flag.trim().equals("") ||flag.equalsIgnoreCase(EdxELRConstants.DEBUG))) {
				if(args.length==2) {
					flag=args[1];
				}
				
				getSecurityObj(NEDSSConstants.ELR_LOAD_USER_ACCOUNT, flag);
			}else{
				System.out.println("ERROR: ELRImporter " + flag +" is not a valid paramter for running ELRImporter.");
				throw new NedssAppLogException();
		/*	
				int numberOfConcurrentThreads=Integer.parseInt(args[0]);
					if(numberOfConcurrentThreads ==0) {
						System.out.println("ERROR: Number of concurrent threads entered is 0. Please use number of threads >0.");
						throw new NedssAppLogException();
					}else if(numberOfConcurrentThreads >10) {
						System.out.println("ERROR: Number of concurrent threads entered is >10. Please use number of threads >0 and <10.");
						throw new NedssAppLogException();
					}
					
					System.out.println("import all messages");
				importAllMessages(numberOfConcurrentThreads,NEDSSConstants.ELR_LOAD_USER_ACCOUNT);
				*/
			}
		} catch (NumberFormatException e) {
			//System.out.println("************************************************************************");
			//System.out.println("* USAGE: ELRImporter [NO_OF_THREADS]                                   *");
			//System.out.println("* NO_OF_THREADS should be a number <11                                 *");
			System.out.println("************************************************************************");
			System.out.println(" USAGE: ELRImporter                                             *");
			System.out.println("This will Process ELR's in silent mode(in single threaded manner). *");
			System.out.println("************************************************************************");
			System.out.println("* OR USAGE: ELRImporter DEBUG                                          *");
			System.out.println("* Process ELR's in verbose mode(in single threaded manner).            *");
			System.out.println("************************************************************************");
					System.out.println("Error"+ e);
		} catch (NedssAppLogException e) {
			//System.out.println("************************************************************************");
			//System.out.println("* USAGE: ELRImporter [NO_OF_THREADS]                                   *");
			//System.out.println("* NO_OF_THREADS should be a number <11                                 *");
			System.out.println("************************************************************************");
			System.out.println(" USAGE: ELRImporter                                             *");
			System.out.println("This will Process ELR's in silent mode(in single threaded manner). *");
			System.out.println("************************************************************************");
			System.out.println("* OR USAGE: ELRImporter DEBUG                                          *");
			System.out.println("* Process ELR's in verbose mode(in single threaded manner).            *");
			System.out.println("************************************************************************");
			//System.out.println("\n\n Number of concurrent threads cannot be more that 10. Please try again!!!");
		}catch (NEDSSAppException e) {
			System.out.println("Another ELRImporter.bat process is in progress. Please try after some time.");
		}catch (Exception e) {
			//System.out.println("************************************************************************");
			//System.out.println("* USAGE: ELRImporter [NO_OF_THREADS]                                   *");
			//System.out.println("* NO_OF_THREADS should be a number <11                                 *");
			System.out.println("************************************************************************");
			System.out.println("* USAGE: ELRImporter                                                *");
			System.out.println("* This will Process ELR's in silent mode(in single threaded manner).   *");
			System.out.println("************************************************************************");
			System.out.println("* OR USAGE: ELRImporter DEBUG                                          *");
			System.out.println("* Process ELR's in verbose mode(in single threaded manner).            *");
			System.out.println("************************************************************************");
			System.out.println("Error"+ e);
			System.out.println("ELRImporter.main Method Exception thrown " + e);
			e.printStackTrace();	
		}
	}
		
	public static void nbsCaseImportScheduler(String userName, String showLogForAllElrs) {
		try {
			getSecurityObj(userName, showLogForAllElrs);
		} catch (Exception e) {
			System.out.println("EdxAutoLabInvFromInterface.nbsCaseImportScheduler Method Exception thrown " + e.getMessage());
			e.printStackTrace();
		}
	} 

	private static void processMessageColl(String showLogForAllElrs, NBSSecurityObj nbsSecurityObj) {
		try {
			NedssUtils nedssUtils = new NedssUtils();
			Object phcrLookedUpObject = nedssUtils.lookupBean(JNDINames.EDX_PHCR_DOCUMENT_EJB);
			EdxPHCRDocumentHome edxPHCRDocumentHome = (EdxPHCRDocumentHome) PortableRemoteObject.narrow(phcrLookedUpObject, EdxPHCRDocumentHome.class);
			EdxPHCRDocument edxPHCRDocument = edxPHCRDocumentHome.create();
			
			ArrayList<Object> elrLiist = edxPHCRDocument.getUnProcessedElrDTList(nbsSecurityObj);
			EdxAutoLabInvFromInterface nbsInterface= new EdxAutoLabInvFromInterface();
			nbsInterface.processElrList(showLogForAllElrs, elrLiist, nbsSecurityObj);
		} catch (Exception e) {
			System.out.println("EdxAutoInvFromInterface.prcoessMessageColl Exception thrown " + e.getMessage());
			e.printStackTrace();
		}

	}
	
	private void  processElrList(String showLogForAllElrs, ArrayList<Object> elrLiist, NBSSecurityObj nbsSecurityObj) {
		try {
			NedssUtils nedssUtils = new NedssUtils();
			Object phcrLookedUpObject = nedssUtils.lookupBean(JNDINames.EDX_PHCR_DOCUMENT_EJB);
			EdxPHCRDocumentHome edxPHCRDocumentHome = (EdxPHCRDocumentHome) PortableRemoteObject.narrow(phcrLookedUpObject, EdxPHCRDocumentHome.class);
			EdxPHCRDocument edxPHCRDocument = edxPHCRDocumentHome.create();

			int successCounter = 0;
			int failureCounter = 0;
			EdxLabInformationDT edxLabInformationDT =null;
			int numberOfPendingELRCases  = elrLiist.size();
			System.out.println("numberOfPendingELRCases: "+elrLiist.size());
			
			if (numberOfPendingELRCases ==0) {
				//System.out.println("numberOfPendingELRCases =0 ");
			}else{
				edxPHCRDocument.resetElrAlgorithmCache(nbsSecurityObj);//in case stale
				for(int i=0; i<numberOfPendingELRCases; i++){
					try {
						Long nbsInterfaceUid = (Long)elrLiist.get(i);
						edxLabInformationDT =edxPHCRDocument.processedSingleELR(nbsInterfaceUid, nbsSecurityObj);
						if(showLogForAllElrs.equalsIgnoreCase(EdxELRConstants.DEBUG)) {
						System.out.println("\n\nProcessed case #"+ (i+1) +" of "+numberOfPendingELRCases+": "+edxLabInformationDT.getLocalId()+" - "+edxLabInformationDT.getStatus());
							System.out.println("Nbs_interface_uid  : "+edxLabInformationDT.getEdxActivityLogDT().getSourceUid());
							System.out.println("Message Control Id : "+edxLabInformationDT.getMessageControlID());
							System.out.println("Filler Number :      "+ edxLabInformationDT.getFillerNumber());
							System.out.println("Patient Name :       "+edxLabInformationDT.getEntityName());
							System.out.println("Final Output :       "+edxLabInformationDT.getEdxActivityLogDT().getException());
						}
						if((i+1)%1000==0) {
							System.out.println("Processed 1000 ELRs");
						}
					} catch (Exception e) {
						System.out.println("Processed case #"+ (i+1) +" of "+numberOfPendingELRCases+": "+edxLabInformationDT.getLocalId()+" - "+edxLabInformationDT.getStatus());
						System.out.println("Nbs_interface_uid  : "+edxLabInformationDT.getEdxActivityLogDT().getSourceUid());
						System.out.println("Message Control Id : "+edxLabInformationDT.getMessageControlID());
						System.out.println("Filler Number :      "+ edxLabInformationDT.getFillerNumber());
						System.out.println("Patient Name :       "+edxLabInformationDT.getEntityName());
						System.out.println("Final Output :       "+edxLabInformationDT.getEdxActivityLogDT().getException());
						System.out.println("Error :"+ e);
						System.out.println("");
					}
				}
			}
			if (successCounter > 0) {
				System.out.println(successCounter + " case(s) imported successfully.");
			}
			if (failureCounter > 0) {
				System.out.println(edxLabInformationDT.toString());
				System.out.println(failureCounter + " case(s) could not be imported. Please check EDX_activity_log table for details.");
			}
		} catch (Exception e) {
			System.out.println("EdxAutoInvFromInterface.processElrList Exception caught:"+ e.getMessage()+ "\n"+ e.getCause());
		}
	}
	private static void getSecurityObj(String userName, String showLogForAllElrs) throws Exception {
			NedssUtils nedssUtils = new NedssUtils();
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
			Object objref = nedssUtils.lookupBean(sBeanName);
			MainSessionCommandHome home = (MainSessionCommandHome) PortableRemoteObject.narrow(objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();
			NBSSecurityObj nbsSecurityObj = msCommand.nbsSecurityLogin(userName, userName);
			processMessageColl(showLogForAllElrs, nbsSecurityObj);
	}
	
	public static void runSingleLERImportProcess() throws NEDSSAppException {
		String userHome = System.getProperty("user.home");
		File file = new File(userHome, "elr.lock");
		try {
		    FileChannel fc = FileChannel.open(file.toPath(),
		            StandardOpenOption.CREATE,
		            StandardOpenOption.WRITE);
		    FileLock lock = fc.tryLock();
		    if (lock == null) {
		    	throw new NEDSSAppException("ELRImporter.bat : Please check another instance of ELRImporter process is running.");
		    }
		} catch (IOException e) {
		    throw new NEDSSAppException("Another instance of ELRImporter process is running");
		}
	}
	
	public static void importAllMessages(int numberOfConcurrentThreads,String userName) { 
		System.out.println("ELRImporter.bat process started");
		
		try {
			if(numberOfConcurrentThreads==0) {
				System.out.println("Usage: Following will use 10 concurrent users for NBS:  ELRImporter.bat 10"); 
			}
			else{
				
				NedssUtils nedssUtils = new NedssUtils();
				String sBeanName = JNDINames.MAIN_CONTROL_EJB;
				Object objref = nedssUtils.lookupBean(sBeanName);
				MainSessionCommandHome home = (MainSessionCommandHome) PortableRemoteObject.narrow(objref, MainSessionCommandHome.class);
				MainSessionCommand msCommand = home.create();
				NBSSecurityObj nbsSecurityObj = msCommand.nbsSecurityLogin(userName, userName);

				Object phcrLookedUpObject = nedssUtils.lookupBean(JNDINames.EDX_PHCR_DOCUMENT_EJB);
				EdxPHCRDocumentHome edxPHCRDocumentHome = (EdxPHCRDocumentHome) PortableRemoteObject.narrow(phcrLookedUpObject, EdxPHCRDocumentHome.class);
				EdxPHCRDocument edxPHCRDocument = edxPHCRDocumentHome.create();
				Map<Integer,ArrayList<Object>> elrUnprocessedMap = edxPHCRDocument.getUnProcessedElrDTMap(numberOfConcurrentThreads, nbsSecurityObj);
				ArrayList<Object> updateELRList = null;
				if((elrUnprocessedMap.get(EdxELRConstants.UPDATED_LAT_LIST))!=null) {
					updateELRList = elrUnprocessedMap.get(EdxELRConstants.UPDATED_LAT_LIST);
					elrUnprocessedMap.remove(EdxELRConstants.UPDATED_LAT_LIST);
				}
				Set<Integer> setlist = elrUnprocessedMap.keySet();
				if(setlist.size()>0) {
					for (int i = 0; i < numberOfConcurrentThreads; i++) {
						if(i>=setlist.size()) {
							break;
						}
						ArrayList<Object> list = elrUnprocessedMap.get(i+1);
						
						MultiThreadELRImporter r1= new MultiThreadELRImporter("Thread started:"+i,list, nbsSecurityObj);
						r1.start();
						System.out.println("Thread started ");
					}			        			    
				}
				//boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
				System.out.println("All Threads finished. ");

				
				if(updateELRList!=null && updateELRList.size()>0 ) {
					System.out.println("Processing updated Labs. ");
					EdxAutoLabInvFromInterface edxAutoLabInvFromInterface = new EdxAutoLabInvFromInterface();
					edxAutoLabInvFromInterface.processElrList("", updateELRList, nbsSecurityObj);
				}
			}
			System.out.println("ELRImporter batch process  Completed");
		}catch (Exception e) {
			System.out.println("ELRImporter Method Exception thrown " + e.getMessage());
			e.printStackTrace();	
		}
	}
}
	/**
	 * Internal class to create multi threads for ELRImporter
	 * @author Pradeep.Sharma
	 *
	 */
	class MultiThreadELRImporter implements Runnable {
		private Thread t;
		String threadName;
		ArrayList<Object> elrLiist = new ArrayList<Object>();
		NBSSecurityObj nbsSecurityObj = null;
		
		MultiThreadELRImporter(String threadName, ArrayList<Object> list, NBSSecurityObj nbsSecurityObj) {
			this.elrLiist =list;
			this.threadName = threadName;
			this.nbsSecurityObj = nbsSecurityObj;
			System.out.println("MultiThreadELRImporter current thread:"+ list.size());
		}

		public void run() {
			try {
				NedssUtils nedssUtils = new NedssUtils();
				Object phcrLookedUpObject = nedssUtils.lookupBean(JNDINames.EDX_PHCR_DOCUMENT_EJB);
				EdxPHCRDocumentHome edxPHCRDocumentHome = (EdxPHCRDocumentHome) PortableRemoteObject.narrow(phcrLookedUpObject, EdxPHCRDocumentHome.class);
				EdxPHCRDocument edxPHCRDocument = edxPHCRDocumentHome.create();
				System.out.println("Number of ELR's in current thread:"+ elrLiist.size());
				EdxLabInformationDT edxLabInformationDT = new EdxLabInformationDT();
				
				for(int i=0; i<elrLiist.size(); i++){
					try {
						Long nbsInterfaceUid = (Long)elrLiist.get(i);
						edxLabInformationDT =edxPHCRDocument.processedSingleELR(nbsInterfaceUid, nbsSecurityObj);
						System.out.println("Processing ELR in Thread in threadName:"+threadName);
						if((i+1)%1000==0) {
							System.out.println("Processed 1000 ELRs");
						}
					} catch (Exception e) {
						System.out.println("Processed case #"+ (i+1) +" of "+elrLiist.size()+": "+edxLabInformationDT.getLocalId()+" - "+edxLabInformationDT.getStatus());
						System.out.println("Nbs_interface_uid  : "+edxLabInformationDT.getEdxActivityLogDT().getSourceUid());
						System.out.println("Message Control Id : "+edxLabInformationDT.getMessageControlID());
						System.out.println("Filler Number :      "+ edxLabInformationDT.getFillerNumber());
						System.out.println("Patient Name :       "+edxLabInformationDT.getEntityName());
						System.out.println("Final Output :       "+edxLabInformationDT.getEdxActivityLogDT().getException());
						System.out.println("Error :"+ e);
						System.out.println("");
					}
					System.out.println("FINISHED ELR in Thread in threadName:"+threadName);
				}
				
			} catch (Exception e) {
				System.out.println("MultiThreadELRImporter.run process interrupted!! exiting with exception"+ e);
			}
		}

		public void start() {
			//System.out.println("Starting " + threadName);
			if (t == null) {
				t = new Thread(this, threadName);
				t.start();
			}
		}
	}
