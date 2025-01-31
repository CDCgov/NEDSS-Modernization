package gov.cdc.nedss.pagemanagement.wa.xml.util;

import gov.cdc.nedss.util.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


/**
 * JspBeautifier - clean up white space in generated JSP pages
 * 	Call beautifyFile to cleanup a single JSP file.
 * @author gtucker
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: CSC for CDC</p>
 * JspBeautifier.java
 * March 24, 2010
 * @version 1.0
 */

public class JspBeautifier {
	int recursionFailsafe = 0;
	static final LogUtils logger = new LogUtils(JspBeautifier.class.getName());

	/*
	 * method: beautify file - creates a temporary JSP file with extension of clean 
	 * and then deletes the original and renames the new file to original JSP file.
	 * @param: Source File to beautify
	 * 
	 */
	public void beautifyFile(File sourceFile) {
		
		
		 if (!sourceFile.isFile()) {
			 logger.error("JspBeautifier parameter error - not a file?");
			 return;
		 }
		 if (!sourceFile.getName().contains(".jsp")) {
			 logger.error("JspBeautifier called with file not a jsp file??");
			 return;
		 }
		 
		 String absolutePath = sourceFile.getAbsolutePath();
		 
		 logger.debug("Beautifier cleaning " + absolutePath);
		 String tmpName = absolutePath + ".clean";
		  if (cleanupWhiteSpace(sourceFile, tmpName ))
		  {
	  			File newFile = new File(tmpName);
	  			if (newFile.exists()) {
	  				if (sourceFile.delete())
	  					newFile.renameTo(new File(absolutePath));
	  				else
	  					logger.error("JspBeautifier called - unable to delete original file");
	  			} else logger.error("JspBeautifier new file was not created?");
		  } else logger.error("JspBeautifier called - cleanup failed?");
		 
	}
	/*
	 * method: cleanupWhiteSpece - reads from source file and writes to destination file removing 
	 *    extra whitespace at the beginning and the end.
	 * @param: Source file
	 * @param: name (including path) for temp file
	 * 
	 */
	public boolean cleanupWhiteSpace (File sourceFile, String toFileNameAndPath) {
		 boolean retOK = true;
	     BufferedReader inStream = null;
	     BufferedWriter outStream = null;
	        
	        try {
	            //Construct the BufferedReader object
	            inStream = new BufferedReader(new FileReader(sourceFile));
	            outStream = new BufferedWriter(new FileWriter(toFileNameAndPath));
	            String curLine = null;
	            
	            while ((curLine = inStream.readLine()) != null) {
	            	String outLine = null;
	            	outLine = curLine.trim();
	            	if (outLine.isEmpty())
	            		continue;
	            	if (outLine.contains("<!--processing "))
	            		outStream.newLine(); //add a blank line
	            	if (outLine.contains("<!-- ######"))
	            		outStream.newLine(); //add a blank line 
	            	outStream.write(outLine);
	            	outStream.newLine();
	            }
	        } catch (FileNotFoundException ex) {
	            logger.error(ex.getMessage());
	            retOK = false;
	        } catch (IOException ex) {
	        	logger.error("JspBeautifier I/O error: " + ex.getMessage());
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
	            	logger.error("JspBeautifier Closing I/O error: " + ex.getMessage());
	                retOK = false;
	            }
	        }
	        return retOK;
	}
	
	public	static String replace(String str, String pattern, String replace) {
	    int s = 0;
	    int e = 0;
	    StringBuffer result = new StringBuffer();

	    while ((e = str.indexOf(pattern, s)) >= 0) {
	        result.append(str.substring(s, e));
	        result.append(replace);
	        s = e+pattern.length();
	    }
	    result.append(str.substring(s));
	    return result.toString();
	}

	
	/**
	 * Unit Test - update directory to process
	 * @param args None
	 */
	public static void main(String[] args) {
		JspBeautifier beautify = new JspBeautifier();
		File srcFile = new File("C:/wildfly-10.0.0.Final/nedssdomain/Nedss/pagemanagement/INV_10440/[Condition].jsp");
		beautify.beautifyFile(srcFile);
	}

} //class
