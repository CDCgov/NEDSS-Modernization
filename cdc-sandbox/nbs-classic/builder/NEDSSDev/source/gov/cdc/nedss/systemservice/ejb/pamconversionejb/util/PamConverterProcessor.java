package gov.cdc.nedss.systemservice.ejb.pamconversionejb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.bean.PamConversionHome;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionConditionDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMasterDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;



public class PamConverterProcessor {
	static final LogUtils logger = new LogUtils(PamConverterProcessor.class.getName());
	/**
	 * Main Process for Batch Program
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("PamConverter batch process  started");
		try
		{
			String isPreRun = null;
			String userName = null;
			String ReConvert = null;
			String conditionCd = null;

			//Check for the user input
			 
			if(args.length == 3){
				userName = args[0];
				isPreRun = args[1];
				conditionCd = args[2];
			} else {
				System.out.println("Usage :  PamConverter.bat [PAM_CONVERTER_USER_ID] [production/prerun] [condition code]"); 
				System.exit(-1); 
			}
						
			
			if(isPreRun != null){
				if(isPreRun.equalsIgnoreCase("production") || 	isPreRun.equalsIgnoreCase("prerun")){
					//ok
				}
			}else {
				System.out.println("Second parameter must be prerun (for test) or production (for an actual conversion)");
				System.out.println("Usage :  PamConverter.bat [PAM_CONVERTER_USER_ID] [production/prerun] [condition code]");  
				System.exit(-1); 
			}
			

			Boolean isPrerunBoolean = new Boolean(false);
			if(isPreRun.equalsIgnoreCase("production"))
				isPrerunBoolean= new Boolean(false);
			else if(isPreRun.equalsIgnoreCase("prerun"))
				isPrerunBoolean= new Boolean(true);
			else{
				System.out.println("PamConverter failed:Usage: PamConverter.bat [PAM_CONVERTER_USER_ID] [production/prerun] [condition code] ");
				throw new Exception("Batch file Usage: PamConverterProcessor.bat [PAM_CONVERTER_USER_ID] [production/prerun] [condition code]");
			}
			 HashMap<?,?> map=checkMappingErrors( isPrerunBoolean,userName, conditionCd, PortPageUtil.PORT_LEGACY);
			 Integer totalNumberOfCases=(Integer)map.get("NUMBER_OF_CASES");
			int errors = 0;
			if (map.get("NO_COND_FOUND")!=null){;
				 System.out.println(map.get("NO_COND_FOUND"));
				 return;
			}
			if( map.get("NO_PAGE_FOUND")!=null){
				 NBSConversionMasterDT nbsConversionMasterDT= (NBSConversionMasterDT)map.get("NO_PAGE_FOUND");
				 System.out.println(nbsConversionMasterDT.getProcessMessageTxt());
				 return;
			 }
			else if( map.get("mapping_error")!=null){
				 errors =( (Integer) map.get("mapping_error")).intValue();
			 }
			 
			int numberOfCases=0;
			if(!isPrerunBoolean.booleanValue() && totalNumberOfCases.intValue()>=0 && errors==0){	    
					// System.out.println(totalNumberOfCases.intValue());
				Long conditionCdGroupId = (Long) map.get("CONDITION_CD_GROUP_ID");
			 numberOfCases=userInputForNumberOfCases();
			 if(numberOfCases>0 || totalNumberOfCases==0)
			 		pamDataConverter(userName,  numberOfCases, conditionCd, conditionCdGroupId);
				}
				 if(totalNumberOfCases==0){
						System.out.println("No Investigation case require conversion. Condition Code table updated.");
				}
				 else if(numberOfCases==totalNumberOfCases){
						System.out.println("All Investigation case(s) converted. Condition Code table updated.");
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.fatal("Error: " + e.getMessage());
		}

		System.out.println("PamConverterProcessor Complete!!!");
	} 
	
	/**
	 * Call data_converter
	 * @param userName
	 * @param numberOfCases
	 * @param conditionCd
	 * @param conditionCdGroupId
	 */
	public static void pamDataConverter(String userName, int numberOfCases, String conditionCd, Long conditionCdGroupId){
		NBSSecurityObj nbsSecurityObj = null;
		NedssUtils nedssUtils = new NedssUtils();
		//Create a MainSessionCommand bean instance
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
		Object objref = nedssUtils.lookupBean(sBeanName);
		
		try {
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());

			logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
			
			
			nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);
			logger.debug("nbsSecurityObj is "+nbsSecurityObj);

			HashMap<?,?> map = dataConverter(numberOfCases, conditionCd,   conditionCdGroupId, msCommand);
	        
	        if(map.get("ERROR")!=null){
	        	String errrorCode= map.get("ERROR").toString();
	        	System.out.println(errrorCode);
	        }
	        else{
		        Integer numberTranslated= (Integer)map.get(conditionCd);
		 	    System.out.println("For condition code "+ conditionCd+" : " + numberTranslated + "  case(s) converted to new Page. Please see NBS_conversion_master table for detail");
		    }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Call the EJB to check for errors in the nbs_conversion_mapping.
	 * @param isPrerun
	 * @param userName
	 * @param conditionCd
	 * @return
	 */
	private static HashMap<?,?> checkMappingErrors(Boolean isPrerun, String userName, String conditionCd, String coversionType) {
		HashMap<?,?> returnMap = new HashMap<Object,Object>();
		NedssUtils nedssUtils = new NedssUtils();
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
		Object objref = nedssUtils.lookupBean(sBeanName);

		try {
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());

			logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
			
			
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
			String sBeanJndiName = JNDINames.PAM_CONVERSION_EJB;
			String sMethod = "checkMappingErrors";
			Object[] oParams = new Object[] {isPrerun, conditionCd, coversionType};
			ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			returnMap = (HashMap<?,?>)arr.get(0);
	        
	        if(returnMap.get("ERROR")!=null){
	        	String errrorCode= returnMap.get("ERROR").toString();
	        	System.out.println(errrorCode);
	        }
	 	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnMap;
	}
	
	/**
	 * Get input form the user for the number of cases they want to process.
	 * @return
	 */
	private static int userInputForNumberOfCases(){
		int numberOfCases=0;
	  String casesEnteredByUser = null; 
		try { 
			System.out.print("Please enter number of cases you want to migrate in number format(for example 100) : "); 
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		    casesEnteredByUser = br.readLine(); 
	  	  	numberOfCases = Integer.parseInt(casesEnteredByUser);
	  	  	if(numberOfCases==-1){
	  	  	numberOfCases=-1;
	  	  	}
	    } catch (IOException ioe) { 
	       System.out.println("IO error!"); 
	       System.exit(1); 
	    }
	    catch(NumberFormatException ex){
	    	 System.out.println(casesEnteredByUser +" is not a valid format.Please enter number of cases as numeric value(for example 100) and try again.");
	    		try { 
	    			System.out.print("Please enter number of cases you want to migrate in number format(for example 100) : "); 
	    		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
	    		    casesEnteredByUser = br.readLine(); 
	    	  	  	numberOfCases = Integer.parseInt(casesEnteredByUser);
	    	  	  	if(numberOfCases==-1){
	    	  	  	numberOfCases=-1;
	    	  	  	}
	    	    } catch (IOException ioe) { 
	    	       System.out.println("IO error!"); 
	    	       System.exit(1); 
	    	    }
	    	    catch(NumberFormatException ex1){
	    	    	 System.out.println(casesEnteredByUser +" is not a valid format.Please enter number of cases as numeric value(for example 100) and try again.");
	    	    }
	    }
	    //System.out.println("number of cases transferred:"+numberOfCases);
		return numberOfCases;
	}
	
	
	/**
	 * dataConverter - Calls EJB to convert the next case until the count is satisfied.
	 * @param userDefinedCasesToBeTranslatedInt
	 * @param conditionCd
	 * @param condGroupId
	 * @param msCommand
	 * @return
	 * @throws RemoteException
	 */
	public static HashMap<Object,Object>  dataConverter( int userDefinedCasesToBeTranslatedInt, String conditionCd, Long condGroupId, MainSessionCommand msCommand) throws RemoteException{
		HashMap<Object,Object> returnMap = new HashMap<Object,Object>();
		NedssUtils nedssUtils = new NedssUtils();
		//Create a MainSessionCommand bean instance
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
		Object objref = nedssUtils.lookupBean(sBeanName);

		try {
			String sBeanJndiName = JNDINames.PAM_CONVERSION_EJB;

			String cdToBeTranslated  = conditionCd;
			NBSConversionMasterDT nBSConversionMasterDT = null;
			if(userDefinedCasesToBeTranslatedInt==0){
					try {
							//update condition code table
							String sMethodUpdateCondition = "updateCondition";
							Object[] oParamsConditionCode = new Object[]{cdToBeTranslated};
							msCommand.processRequest(sBeanJndiName, sMethodUpdateCondition, oParamsConditionCode);
							//store log message
							java.util.Date dateTime = new java.util.Date();
							Timestamp startTime = new Timestamp(dateTime.getTime());
							nBSConversionMasterDT = new NBSConversionMasterDT();
							nBSConversionMasterDT.setStartTime(startTime);
							nBSConversionMasterDT.setProcessTypeInd("Production");
							nBSConversionMasterDT.setStatusCd("UpdatePass");
							java.util.Date endDate= new java.util.Date();
							Timestamp endTime = new Timestamp(endDate.getTime());
							nBSConversionMasterDT.setEndTime(endTime);
							nBSConversionMasterDT.setProcessMessageTxt("No Investigation exists that require conversion. Condition Code table has been updated.");
							String sMethod = "writeToLogMaster";
							Object[] oParams = new Object[] {(nBSConversionMasterDT) };
							ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
						} catch (Exception e) {
							System.out.println("Error! Updating condition or calling writeToLogMaster(). Pleace check!"+ e);
							logger.error("Error! Pleace check!"+ e);
						}

						//writeToLogMaster(nBSConversionMasterDT);
				} //user defined number of cases = 0;
				String sMethod = "getNumberOfCasesToTransfer";
				Object[] oParams = new Object[] {cdToBeTranslated, PortPageUtil.PORT_LEGACY };
				ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				Integer noCasesToBeTransferred= (Integer)arr.get(0);
				int noCasesToBeTransferredPrimInt=noCasesToBeTransferred.intValue();
				InvestigationProxyVO investigationProxyVO =null;
				returnMap.put(cdToBeTranslated, new Integer(0));
				int caseTranslated = 0;
				if(noCasesToBeTransferred.intValue()>0){
					for(int i=0; (i< noCasesToBeTransferred.intValue() && i<userDefinedCasesToBeTranslatedInt); i++){
							String sMethodProxy = "getOldInvestigationProxyVO";
							Object[] oParamsProxy = new Object[] {cdToBeTranslated };
							ArrayList<?> arrProxy =msCommand.processRequest(sBeanJndiName, sMethodProxy, oParamsProxy);
							investigationProxyVO= (InvestigationProxyVO)arrProxy.get(0);
							try {
								String sMethodProxyVO = "convertToNewStructure";
								Object[] oParamsProxyVO = new Object[] {investigationProxyVO};
								ArrayList<?> arrProxyVO =msCommand.processRequest(sBeanJndiName, sMethodProxyVO, oParamsProxyVO);
								InvestigationProxyVO invProxyVO =(InvestigationProxyVO)arrProxyVO.get(0);
								noCasesToBeTransferredPrimInt-- ;
								returnMap.put(cdToBeTranslated, new Integer(i+1));
								logger.debug("caseTranslated "+caseTranslated);
								logger.debug("userDefinedCasesToBeTranslatedInt "+userDefinedCasesToBeTranslatedInt);
								logger.debug("Is caseTranslated>= userDefinedCasesToBeTranslatedInt?: "+(caseTranslated>= userDefinedCasesToBeTranslatedInt));
							} catch (Exception e) {
								System.out.println("Error thrown  at dataConverter:"+e);
								logger.error("Error thrown at dataConverter:"+e);
								writeToLog(investigationProxyVO, e, msCommand);
							}
					} //for
				}else{
						returnMap.put(cdToBeTranslated, new Integer(0));
				}
				if(noCasesToBeTransferredPrimInt==0){
						logger.debug("Inside loop");
						//logically delete the State Defined LDFs
						String sMethodRemoveMetadata = "removeMetadata";
						java.util.Date dateTime = new java.util.Date();
						Timestamp lastChgTime = new Timestamp(dateTime.getTime());
						
						Object[] oParamsRemoveMetadata = new Object[] {cdToBeTranslated,lastChgTime};
						ArrayList<?> arrRemoveMetadata =msCommand.processRequest(sBeanJndiName, sMethodRemoveMetadata, oParamsRemoveMetadata);
						logger.debug("removeMetadata called");

						String sMethodWaTemplateDT = "getWaTemplateByConditionCd";
						Object[] oParamsWaTemplateDT = new Object[]{cdToBeTranslated};
						ArrayList<?> arrWaTemplateDT=msCommand.processRequest(sBeanJndiName, sMethodWaTemplateDT, oParamsWaTemplateDT);
						WaTemplateDT waTemplateDT = (WaTemplateDT)arrWaTemplateDT.get(0);
						String sMethodUpdateCondition = "updateConditionCode";
						Object[] oParamsConditionCode = new Object[]{cdToBeTranslated,waTemplateDT, lastChgTime};
						msCommand.processRequest(sBeanJndiName, sMethodUpdateCondition, oParamsConditionCode);
						
						//Post conversion mapping correction, helps to generate correct back translated master message.
						
						String sMethodUpdateMapping = "postConversionMappingCorrections";
						Object[] oParamsUpdateMapping = new Object[]{cdToBeTranslated};
						msCommand.processRequest(sBeanJndiName, sMethodUpdateMapping, oParamsUpdateMapping);
						
						logger.debug("updateConditionCode called");
						return returnMap;
				}
				return returnMap;
		} catch (Exception e) {
			System.out.println("Data Converter Exception\n");
			logger.fatal("Exception thrown at dataConverter with Error:"+e.toString());
			throw new RemoteException(e.toString(), e);
		}

	}

	/**
	 * Write message to nbs_conversion_master log table
	 * @param investigationProxyVO
	 * @param ex
	 * @param msCommand
	 */
	private static void writeToLog(InvestigationProxyVO investigationProxyVO,  Exception ex,MainSessionCommand msCommand){
		try {
			NBSConversionMasterDT nBSConversionMasterDT = new NBSConversionMasterDT();
			java.util.Date endDate= new java.util.Date();
			Timestamp endTime = new Timestamp(endDate.getTime());
			nBSConversionMasterDT.setStartTime(endTime);
			nBSConversionMasterDT.setEndTime(endTime);
			nBSConversionMasterDT.setStatusCd("Fail");
			nBSConversionMasterDT.setProcessTypeInd("Production");
			nBSConversionMasterDT.setProcessMessageTxt(ex.toString());
			nBSConversionMasterDT.setActUid(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
			String sBeanJndiName = JNDINames.PAM_CONVERSION_EJB;
			String sMethod = "writeToLogMaster";
			Object[] oParams = new Object[] {nBSConversionMasterDT };
			ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		} catch (Exception e) {
			System.out.println("Error thrown  at writeToLog:"+e);
			logger.error("Error thrown at writeToLog:"+e.getMessage(), e);

		}
	}
}
