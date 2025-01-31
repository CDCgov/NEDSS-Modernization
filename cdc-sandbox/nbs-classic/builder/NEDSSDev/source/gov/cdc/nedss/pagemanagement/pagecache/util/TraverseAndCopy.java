package gov.cdc.nedss.pagemanagement.pagecache.util;

import gov.cdc.nedss.util.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;



/**
 * TraverseAndCopy - copy JSP files over
 * @author gtucker
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: CSC for CDC</p>
 * JspBeautifier.java
 * March 31, 2010
 * @version 1.0
 */

public class TraverseAndCopy {

	static final LogUtils logger = new LogUtils(TraverseAndCopy.class.getName());
	int recursionFailSafe = 0;
	public boolean traverseAndCopy(File sourceDir, File destinationDir,String invFormCd) {

		 if (!sourceDir.isDirectory()) {
			 logger.error("source not a directory?");
			 return false;
		 }
		 if (!destinationDir.exists())
			  destinationDir.mkdir();
		

		String sourceAbsolutePath = sourceDir.getAbsolutePath();
		String destAbsolutePath = destinationDir.getAbsolutePath();
		logger.debug("Traverse and copy ->" + sourceAbsolutePath + " to " + destAbsolutePath);
	    String fileNames[] = sourceDir.list();
	    for (int i=0; i<fileNames.length; ++i) {
	    		  File fileToRead = new File(sourceAbsolutePath, fileNames[i]);
	    		  File fileToWrite = new File(destAbsolutePath, fileNames[i]);
	    		  if (fileToRead.isDirectory())
	    			  if (++recursionFailSafe < 300)
	    				 traverseAndCopy(fileToRead, fileToWrite,invFormCd);
	    			  else
	    				  continue; //for safety
	    		  //beautify the file if it is a JSP file
	    		  if (fileNames[i].contains(".jsp"))
	    			  	if (!copyJSPFile(fileToRead, fileToWrite,invFormCd))
	    			  		return false;
	    } //for loop
	    return true;
	}

	public boolean copyJSPFile (File fromFile, File toFile,String invFormCd) {
		 boolean retOK = true;
	     BufferedReader inStream = null;
	     BufferedWriter outStream = null;

	        try {
	            if(invFormCd.equals("") || (!invFormCd.equals("") && new Date(fromFile.lastModified()).after(new Date(toFile.lastModified())))){
	            	
	        	//Construct the BufferedReader object
	            inStream = new BufferedReader(new FileReader(fromFile));
	            outStream = new BufferedWriter(new FileWriter(toFile));
	            String curLine = null;
	            while ((curLine = inStream.readLine()) != null) {
	            	outStream.write(curLine);
	            	outStream.newLine();
	               }
	            }

	        } catch (FileNotFoundException ex) {
	        	logger.error("traverse: file not found?");
	            ex.printStackTrace();
	            retOK = false;
	        } catch (IOException ex) {
	        	logger.error("traverse: i/o exception?" + ex.getMessage());
	            ex.printStackTrace();
	            retOK = false;
	        } finally {
	            //Close the Buffered Reader, Writer
	            try {
	                if (inStream != null)
	                    inStream.close();
	                if (outStream != null) {
	                	outStream.flush();
	                	outStream.close();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	                retOK = false;
	            }
	        }
	        return retOK;
	}
	
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File startingDir = new File("C:/wildfly-10.0.0.Final/nedssdomain/Nedss/pagemanagement/INV_10055");
		File endingDir = new File("C:/wildfly-10.0.0.Final/nedssdomain/tmp/deploy/tmp892720772589492857nbs.ear-contents/nbs-exp.war/pagemanagement/rendering/INV_10057");
		TraverseAndCopy travCopy = new TraverseAndCopy();
		travCopy.traverseAndCopy(startingDir, endingDir,"");
	}

}
