package gov.cdc.nedss.nnd.nndbatchprocess;

import gov.cdc.nedss.nnd.dt.NETSSTransportQOutDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.rmi.PortableRemoteObject;

/**
 * NETSSMessageProcessor - Batch program to read the NBS_MSGOUTE.NETSS_TransportQ_out table
 * 	 and produce a NETSS file called STDNETSS.dat in the file specified by the NETSS_OUTPUT_FILE_LOCATION
 * 	 property.
 * 
 *   This was created as a stop gap measure because the message mapping guide for STD
 *   has not been implemented so the old legacy NETSS file approach is being utilized.
 *   This should be deleted when it is no longer needed.
 *   
 * @author TuckerG 
 * @since 2015-10-13
 *
 */

public class NETSSMessageProcessor {
	static final LogUtils logger = new LogUtils(NETSSMessageProcessor.class.getName());
	public static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	private final static String NETSSFileName = "STDNETSS.dat";  //output filename per requirements

	public static void main(String[] args)
	{
		
		String endingDateStr = null;

		String userName = null;
		Date specifiedEndingDate = null;
		String overrideFlag = null;
		try
		{
			
			if(args.length == 1){
				userName = args[0];
			} else if(args.length == 2){
				userName = args[0];
				endingDateStr = args[1];
			   
			    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			    try
			    {
			    	specifiedEndingDate = formatter.parse(endingDateStr);
			    }
			    catch (ParseException e)
			    {
					System.out.println("Usage :  NETSSMessageProcessor.bat [USER_ID] [Optional Ending Date in format MM/dd/yyyy] [Optional Override cutoff date to include previous year data?]");
					System.out.println("Error parsing date: " + endingDateStr + " format should be ex. 09/23/2015");
					System.exit(-1);
			    }
			    System.out.println("Specified ending date is: " + specifiedEndingDate.toString());
			}
			else if(args.length == 3){
				userName = args[0];
				endingDateStr = args[1];
				overrideFlag = args[2];
			   
			    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			    try
			    {
			    	specifiedEndingDate = formatter.parse(endingDateStr);
			    }
			    catch (ParseException e)
			    {
					System.out.println("Usage :  NETSSMessageProcessor.bat [USER_ID] [Optional Ending Date in format MM/dd/yyyy] [Optional Override cutoff date to include previous year data?]");
					System.out.println("Error parsing date: " + endingDateStr + " format should be ex. 09/23/2015");
					System.exit(-1);
			    }
			    if(overrideFlag!=null && !(overrideFlag.equals("T") || overrideFlag.equals("F"))) {
			    	System.out.println("Usage :  NETSSMessageProcessor.bat [USER_ID] [Optional Ending Date in format MM/dd/yyyy] [Optional Override cutoff date to include previous year data?]");
			    	System.out.println("Override flag should be a T or F value");
					System.exit(-1);
			    }
			    		
			    System.out.println("Specified ending date is: " + specifiedEndingDate.toString());
			    System.out.println("Override flag is: " + overrideFlag);
			}else {	
				System.out.println("Usage :  NETSSMessageProcessor.bat [USER_ID] [Optional Ending Date in format MM/dd/yyyy] [Optional Override cutoff date to include previous year data?]");
				System.exit(-1);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.fatal("Error: " + e.getMessage());
		}
		System.out.println("NETSSMessageProcessor batch process started.  Please wait..");
		if (processNETSSRequest(userName, specifiedEndingDate, overrideFlag))
			System.out.println("NETSSMessageProcessor completed successfully.");
		else 
			System.out.println("NETSSMessageProcessor failed to process.");
	}
	/**
	 * processNETSSRequest
	 * @param userName
	 * @param specifiedEndingDate
	 * @return true if sucess
	 */
	private static boolean processNETSSRequest(String userName, Date specifiedEndingDate, String overrideFlag) {
		Short mmwrYear = null;
		Short mmwrWeek = null;
		Boolean priorYear =  null;
		short[] mmwrVals = getMMWRAndPriorYearBoolean(specifiedEndingDate, overrideFlag);
		mmwrWeek = mmwrVals[0];
		mmwrYear = mmwrVals[1];
		if (mmwrVals[2] == 0)
			priorYear = false;
		else
			priorYear = true;
			
		Collection<Object> coll = getNETSSCases(userName, mmwrYear, mmwrWeek, priorYear);
		if (coll == null || coll.size() == 0) {
			System.out.println("NETSSMessageProcessor: No NETSS records found to process...");
			System.out.println("Check data in MsgOutMessage NETSS_TransportQ_out table.");
			return false;
		}
		
		Collection<Object> culledCollection = removeDupsFromNetssCollection(coll);
		
		if (!processAndWriteNETSSOutputFile(mmwrYear, culledCollection))
			return false;
		
		return true;
	}

    /**
     * If a case is updated, another NETSS record is written out. We were sending the earlier record and the 
     * later record. Resulting in a case being counted twice. Walk through the collection and only take the
     * last update of any that are in there more than once.
     * @param coll of NetssTransportQOutDT records in timeframe
     * @return coll without dups of NetssTransportQOutDT records
     */

	private static Collection<Object> removeDupsFromNetssCollection(
			Collection<Object> coll) {
		
		// //System.out.println("Event = " + netssTransportQOutDT.getPayload().substring(17,22));
		HashMap<String, Object> netssMsgMap = new HashMap<String, Object>();
		Iterator<Object> netssIter = coll.iterator();
		while (netssIter.hasNext()) {
			NETSSTransportQOutDT netssTransportQOutDT = (NETSSTransportQOutDT) netssIter.next();
			String caseReportId = netssTransportQOutDT.getPayload().substring(6,12); //CASE REPORT ID
			//System.out.println("           Processing CaseId: " +caseReportId);
			netssMsgMap.put(caseReportId, netssTransportQOutDT);
		}
		
		return netssMsgMap.values();
	}
	/**
	 * getMMWRAndPriorYearBoolean
	 * @param specifiedDate if present, null if not and defaults to today
	 *    We backup to last Saturday the end of the MMWR week.
	 * @return
	 */
	private static short[] getMMWRAndPriorYearBoolean(Date specifiedDate, String overrideFlag) {
		short[] returnArray = {0,0,0};
		Short mmwrWeekShort = null;
		Short mmwrYearShort = null;
		Boolean includePriorYear = false;
		Calendar cal = null;
		
		cal = Calendar.getInstance(); //todays date
		if (specifiedDate != null) 
			cal.setTime(specifiedDate);
	
		//backup to the previous saturday
		while (cal.get( Calendar.DAY_OF_WEEK ) != Calendar.SATURDAY)
		    cal.add( Calendar.DAY_OF_WEEK, -1 );
		Date lastSaturday = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		String dateLastSaturdayStr = format1.format(lastSaturday);
		//get the MMWR week and year for the previous Saturday
		System.out.println("Date previous Saturday was " + dateLastSaturdayStr);
		int[] wY = CalcMMWR(dateLastSaturdayStr);
		if (wY != null && wY.length > 0)
			{
			mmwrWeekShort = new Short((short) wY[0]);
			mmwrYearShort =  new Short((short) wY[1]);
			}
		System.out.println("MMWR Week and Year are: " + mmwrWeekShort + " " + mmwrYearShort);
		//find out if Saturday was after the cutoff
		String cutoffDateStr = "05/31/" + dateLastSaturdayStr.substring(dateLastSaturdayStr.length()-4,dateLastSaturdayStr.length());
		if(overrideFlag!=null && overrideFlag.equals("T"))
			System.out.println("  Cutoff date of " + cutoffDateStr+" is overridden");
		else
			System.out.println("  Cutoff date is: " + cutoffDateStr);
	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    Date cutoffDate = new Date();
	    try
	    {
	    	cutoffDate = formatter.parse(cutoffDateStr);
	    }
	    catch (ParseException e)
	    {
			System.out.println("Exception processing cutoff date??");
			System.exit(-1);
	    }

		
		if (lastSaturday.after(cutoffDate) && overrideFlag==null) {
			includePriorYear = false;
			System.out.println("  Past cutoff date.. prior year not included.");
		} else {
			includePriorYear = true;
			System.out.println("  Not past cutoff date.. prior year included.");
		}
	    returnArray[0] = mmwrWeekShort;
	    returnArray[1] = mmwrYearShort;
	    if (includePriorYear)
	    	returnArray[2] = 1; //true
	    else
	    	returnArray[2] = 0; //false
	    
	    return returnArray;
    }
	
	/**
	 * getNETSSCases - Call the backend NNDMessageProcessorEJB to get the collection of NETSS Records.
	 * @param userName
	 * @param mmwrYear
	 * @param mmwrWeek
	 * @param includePriorYear
	 * @return
	 */
	private static Collection<Object> getNETSSCases(String userName,
			Short mmwrYear, Short mmwrWeek, Boolean includePriorYear) {

		Collection<Object> coll = new ArrayList<Object>();
		NBSSecurityObj nbsSecurityObj = null;
		NedssUtils nedssUtils = new NedssUtils();
		//Object objref = nedssUtils.lookupBean("MainControl");
		MainSessionCommandHome msCommandHome;
		MainSessionCommand msCommand = null;
		try {

			//MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(objref, MainSessionCommandHome.class);
			//MainSessionCommand msCommand = home.create();
			//GST per Dhaval change to MAIN_CONTROL_EJB
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
            msCommandHome = (MainSessionCommandHome)PortableRemoteObject.narrow(nedssUtils.lookupBean(sBeanName), MainSessionCommandHome.class);
            msCommand = msCommandHome.create();
			logger.info("msCommand = " + msCommand.getClass());

			logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);

			nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
			String sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
			String sMethod = "getNETSSTransportQOutDTCollection";
			Object[] oParams = new Object[] {mmwrYear, mmwrWeek, includePriorYear};

			ArrayList<?>  arr  =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			coll= (Collection<Object>)arr.get(0);

			if(coll != null)
				System.out.println("NETSS Records returned :  "+coll.size());

		} catch (Exception  e) {
			e.printStackTrace();
		}
		return coll;
	}

	/**
     * CalcMMWR - MMWR Method from RulesEngineUtil.java
     * This method was in the Web tier so we needed to duplicate
     * it here for this batch process.
     
	 * @param pDate
	 * @return int[] mmwrWeek and Year
	 */
	private static int[] CalcMMWR(String pDate)
	{
	    //  Create return variable.
	    int[] r = {0,0};
	    try{
	    //  Define constants.
	    int SECOND = 1000;
	    int MINUTE = 60 * SECOND;
	    int HOUR = 60 * MINUTE;
	    int DAY = 24 * HOUR;
	    int WEEK = 7 * DAY;
	    //  Convert to date object.
	    Date varDate = new SimpleDateFormat("MM/dd/yyyy").parse(pDate);
	    Calendar cal = Calendar.getInstance();
		cal.setTime(varDate);
	    long varTime = cal.getTimeInMillis();
	    //  Get January 1st of given year.

	    Date varJan1Date = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/"+cal.get(Calendar.YEAR));
	    Calendar calJan1 = Calendar.getInstance();
	    calJan1.setTime(varJan1Date);
	    int varJan1Day = calJan1.get(Calendar.DAY_OF_WEEK);
	    long varJan1Time = calJan1.getTimeInMillis();
	    //  Create temp variables.
	    long t = varJan1Time;
	    Date d = null;
	    int h = 0;
	    //  MMWR Year.
	    int y = calJan1.get(Calendar.YEAR);
	    //  MMWR Week.
	    int w = 0;
	    //  Find first day of MMWR Year.
	    if(varJan1Day < 5)
	    {
	        //  If SUN, MON, TUE, or WED, go back to nearest Sunday.
	        t -= ((varJan1Day-1) * DAY);
	        //  Loop through each week until we reach the given date.
	        while(t < varTime)
	        {
	            //  Increment the week counter.
	            w++;
	            t += WEEK;
	            //  Adjust for daylight savings time as necessary.
	            d = new Date(t);
	            Calendar cal1 = Calendar.getInstance();
	            cal1.setTime(d);
	            h = cal1.get(Calendar.HOUR);
	            if(h == 1)
	            {
	                t -= HOUR;
	            }
	            if(h == 23 || h == 11)
	            {
	                t += HOUR;
	            }
	        }
	        //  If at end of year, move on to next year if this week has
	        //  more days from next year than from this year.
	        if(w == 53)
	        {
	        	Date varNextJan1Date = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/"+(cal.get(Calendar.YEAR)+1));
	        	Calendar varNextJan1Cal = Calendar.getInstance();
	        	varNextJan1Cal.setTime(varNextJan1Date);
	        	int varNextJan1Day = varNextJan1Cal.get(Calendar.DAY_OF_WEEK);
	            if(varNextJan1Day < 5)
	            {
	                y++;
	                w = 1;
	            }
	        }
	    }
	    else
	    {
	        //  If THU, FRI, or SAT, go forward to nearest Sunday.
	        t += ((7 - (varJan1Day-1)) * DAY);
	        //  Loop through each week until we reach the given date.
	        while(t <= varTime)
	        {
	            //  Increment the week counter.
	            w++;
	            d = new Date(t);

	            //  Move on to the next week.
	            t += WEEK;
	            //  Adjust for daylight savings time as necessary.
	            d = new Date(t);
	            Calendar dCal = Calendar.getInstance();
	            dCal.setTime(d);
	            h = dCal.get(Calendar.HOUR);
	            if(h == 1)
	            {
	                t -= HOUR;
	            }
	            if(h == 23)
	            {
	                t += HOUR;
	            }
	        }
	        //  If at beginning of year, move back to previous year if this week has
	        //  more days from last year than from this year.

	        if(w == 0)
	        {
	            d = new Date(t);
	            Calendar dCal1 = Calendar.getInstance();
	            dCal1.setTime(d);
	            if( (dCal1.get(Calendar.MONTH) == 0) && (dCal1.get(Calendar.DAY_OF_WEEK) <= 5) )
	            {
	                y--;
	                int a[] = CalcMMWR("12/31/" + y);
	                w = a[0];
	            }
	        }
	    }

	    //  Assemble result.
	    r[0] = w;
	    r[1] = y;
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	    //  Return result.
	    return r;
	}
	/**
	 * processAndWriteNETSSOutputFile - Check the directory and file and write the records
	 * @param mmwrYear
	 * @param coll
	 * @return true if successful
	 */
	private static boolean processAndWriteNETSSOutputFile(Short mmwrYear, Collection<Object> coll) {
		//Get the directory from the property file
		if (propertyUtil.getNETSSOutputFileLocation() == null) {
			System.out.println("NETSS_OUTPUT_FILE_LOCATION not found in NEDSS.properties file. Processing can not continue...");
			return false;
		}
		String filePath = propertyUtil.getNETSSOutputFileLocation();

        
		File dirToWriteTo = new File(filePath);
        if (!dirToWriteTo.exists())
        	dirToWriteTo.mkdirs(); // make the directory if it does not exist
        
		 if (!dirToWriteTo.isDirectory()) {
			 logger.error("NETSS_OUTPUT_FILE_LOCATION in NEDSS.properties file is not a directory?");
			 System.out.println("NETSS_OUTPUT_FILE_LOCATION in NEDSS.properties file is not a directory?");
			 return false;
		 }
		 File netssFile = new File(dirToWriteTo, NETSSFileName);
		 
		 return writeNETSSOutputFile(netssFile, coll, mmwrYear);
	}
	/**
	 * writeNETSSOutputFile - Write the file and the verification records	 
	 * @param netssFile
	 * @param coll
	 * @param mmwrYear
	 * @return true if successful
	 */
	private static boolean writeNETSSOutputFile(File netssFile, Collection<Object> coll, Short mmwrYear) {		 

		//counters
		int totalRecordsWritten = 0;
		int verificationRecordsWritten = 0;
		Map<String, Integer>  countersMap = new HashMap<String, Integer>();
		String formattedCount = "00000";
		String blanks44 = "                                            ";
		String stateCCD = null;

		//condition for switch
		/* per Christi 10-26-15 - allow for any condition
		final int chancroid10273Event  	= 10273; 
		final int chlamydia10274Event   = 10274;
		final int gonorrhea10280Event  	= 10280;
		final int syphilis10311Event 	= 10311; //primary
		final int syphilis10312Event  	= 10312; //secondary
		final int syphilis10313Event  	= 10313; //early latent
		final int syphilis10314Event  	= 10314; // late latent
		final int syphilis10319Event  	= 10319; //late with clinical manifestations
	    */
		PrintWriter netssWriter = null;

		try
		{
			if (mmwrYear > 2000)
				mmwrYear = (short) (mmwrYear - 2000);
			netssWriter = new PrintWriter(new FileWriter(netssFile, false)); //overwrite existing
		

			Iterator<Object> netssIter = coll.iterator();
			while (netssIter.hasNext()) {
				NETSSTransportQOutDT netssTransportQOutDT = (NETSSTransportQOutDT) netssIter.next();
				//System.out.println("Event = " + netssTransportQOutDT.getPayload().substring(17,22));
				String thisConditionCd = netssTransportQOutDT.getEvent();
				//System.out.println("Condition is: " + thisConditionCd);
				if (thisConditionCd != null) {
					if (stateCCD == null) {
						stateCCD = netssTransportQOutDT.getPayload().substring(2,4);
						//System.out.println("State CCD is: " + stateCCD);
					}
					//write the record to the file
					netssWriter.println(netssTransportQOutDT.getPayload());
					++totalRecordsWritten;
					//increment the correct condition counter
					if (!countersMap.containsKey(thisConditionCd))
						countersMap.put(thisConditionCd, new Integer(1));
					else {
						Integer thisCounter = countersMap.get(thisConditionCd);
						thisCounter = new Integer(thisCounter.intValue() + 1);
						countersMap.put(thisConditionCd, thisCounter);
					}
				}
			} //end of recs
			//System.out.println("Counter Map size: " + countersMap.size());
			//Write verification records at end of file
			Iterator iterMap = countersMap.keySet().iterator();
			while (iterMap.hasNext()) {
				String thisStr  = (String)iterMap.next();
				Integer thisInt =  (Integer) countersMap.get(thisStr);
				//System.out.println("stateCCD mmwrYear: " + stateCCD + " " + mmwrYear);
				//System.out.println("Condition: " + thisStr + " = " + thisInt);
				formattedCount= String.format("%05d", thisInt.intValue());
				//formattedCount= formattedCount.format("%05d", pair.getValue());
				netssWriter.println("V" + stateCCD + thisStr+ formattedCount + mmwrYear + blanks44);
				++verificationRecordsWritten;
			} 
			netssWriter.flush();            
			netssWriter.close();

		} catch(Exception e) {
			System.out.println("Exception caught writing to NETSS file: "+e.getMessage());
		} finally {
			netssWriter.flush();            
			netssWriter.close();
		}

		if (totalRecordsWritten > 0) {
			System.out.println("NETSS Case Records written to file = "+totalRecordsWritten);
			System.out.println("NETSS Verification Records written to file = "+verificationRecordsWritten);
			return true;
		}
		else
			return false;
	}	

}
