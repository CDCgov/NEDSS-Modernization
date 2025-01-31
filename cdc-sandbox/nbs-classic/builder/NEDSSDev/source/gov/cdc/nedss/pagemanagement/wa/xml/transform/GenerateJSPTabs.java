/*
 * @author: Gregory Tucker, Deepak Raj
 * <p>Copyright 2010 CSC</p>
 * <p>Company: CSC</p>
 * GenerateJSPTabs.java
 * Feb 1, 2010
 * @version 0.9
 */

package gov.cdc.nedss.pagemanagement.wa.xml.transform;

import gov.cdc.nedss.pagemanagement.wa.xml.util.JspBeautifier;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

import org.apache.xpath.XPathAPI;
import org.jfree.util.Log;
import org.w3c.dom.*;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamResult;


public class GenerateJSPTabs {
	
	static final LogUtils logger = new LogUtils(GenerateJSPTabs.class.getName());
	private static final String NBS_TMP_DEPLOYMENT_CONTAINS = "deployment";
	private static final String NBS_TMP_WAR_NAME_CONTAINS = "nbs.war-";
	private static final String SOURCE_FOLDER_IN_WAR = File.separator + "pagemanagement" + File.separator;
	private static final String TARGET_FOLDER_IN_WAR = File.separator + "pagemanagement" + File.separator + "rendering"+ File.separator + "preview";

	
	
	
	
	/**
	 * processIndex
	 *    Generate the index.jsp file from the XML
	 * @param xmlInputFile
	 * @param xpath
	 * 
	 */
	public void processIndex (String xmlInputFile, String xpath, String xsltFile,String mode, String dir,String Jsptype, String businessObjectType)throws Exception {

		javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource(xmlInputFile);
		javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource(xsltFile);
		
	    DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
	    dfactory.setNamespaceAware(true);
	    Document xmlDoc = null;
	    try {
	    xmlDoc = dfactory.newDocumentBuilder().parse(xmlInputFile);
	    } catch (SAXException ex ){	
	    	logger.error("processIndex: SAX Exception: " + ex.getMessage());
	    } catch (IOException ex) {
	    	logger.error("processIndex: SIOException: " + ex.getMessage());
		} catch (ParserConfigurationException ex) {
			logger.error("processIndex: SParserConfigurationException: " + ex.getMessage());
		}
		

        
	     // Setup the xsl transformer
		TransformerFactory transFact = null;
		Transformer trans = null;
		try {
	      transFact = TransformerFactory.newInstance( );
	      trans = transFact.newTransformer(xsltSource);
	      
	      if (trans!=null && businessObjectType!= null && businessObjectType.equalsIgnoreCase(NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE) && Jsptype.equals(NEDSSConstants.PUBLISH)) {
			  trans.setParameter("the_form", "PageSubForm");
	      	  trans.setParameter("the_prop", "pageClientVO");
		  } 
	      if (trans!=null && businessObjectType!= null && businessObjectType.equalsIgnoreCase(NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE) && Jsptype.equals(NEDSSConstants.PUBLISH)) {
			  trans.setParameter("the_form", "PageSubForm");
	      	  trans.setParameter("the_prop", "pageClientVO");
		  } 
	      
	      
	      
		} catch (TransformerConfigurationException ex) {
			logger.error("TransformerConfigurationException: " + ex.getMessage());
		}

	    //get the page node to process
	  	NodeIterator nodeIterator = null;
		try {
		       nodeIterator = XPathAPI.selectNodeIterator(xmlDoc, xpath);
			} catch (TransformerException ex) {
				logger.error("TransformerException: " + ex.getMessage());
			}

	    Node node;
	    while ((node = nodeIterator.nextNode())!= null)
	      {
	    	//open a buffered output for the result
	    	FileOutputStream fos = null;
	    	OutputStreamWriter outIndex = null;
	    	//perform the transform to process the node
	    	javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(outIndex);
	    	
	    	try {
	    		writeIndexFileJSP("index", node,mode,result,trans,dir,Jsptype);	
				try {
				//	boolean success = publisher.publishPage();
				} catch (Exception e) {
					logger.error("Error writing XML " + e.getMessage());
					e.printStackTrace();
				}
			} catch (TransformerException ex) {
				logger.error("TransformerException: " + ex.getMessage());
			}
	      }
		 
	    
       
        
        
	} //processIndex
	   

	/**
	 * processTabs
	 *    Generate the <tabName>.jsp file from the XML for each tab in the xml source
	 * @param xmlInputFile
	 * @param xpath to tab
	 * @param xslt file to use
	 * @param mode - create or view
	 * 
	 */
	public void processTabs (String xmlInputFile, String xpath, String xsltFile,String mode,String dir,String Jsptype, String businessObjectType)throws Exception {

		javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource(xmlInputFile);
		javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource(xsltFile);
		
	    DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
	    dfactory.setNamespaceAware(true);
	    Document xmlDoc = null;
	    try {
	    xmlDoc = dfactory.newDocumentBuilder().parse(xmlInputFile);
	    } catch (SAXException ex ){	
	    	logger.error("SAX Exception: " + ex.getMessage());
	    } catch (IOException ex) {
	    	logger.error("IOException: " + ex.getMessage());
		} catch (ParserConfigurationException ex) {
			logger.error("ParserConfigurationException: " + ex.getMessage());
		}
		//get a list of tab names to use for the file names
	  	NodeList tabList = xmlDoc.getElementsByTagName("TabName");
	  	String[] outTabFileList = new String[tabList.getLength()];
	  	for (int i = 0; i < tabList.getLength(); ++i) {
	  		Element tabElement = (Element) tabList.item(i);
	  		NodeList tmpList = tabElement.getChildNodes();
	  		outTabFileList[i] = (tmpList.item(0).getNodeValue());
	  	}
	     // Setup the xsl transformer
		TransformerFactory transFact = null;
		Transformer trans = null;
		try {
	      transFact = TransformerFactory.newInstance( );
	      trans = transFact.newTransformer(xsltSource);
	      trans.setParameter("bus_obj", businessObjectType);
	      if (tabList.getLength() > 1)
	    	  trans.setParameter("mult_tab", "Y");
	      else
	    	  trans.setParameter("mult_tab", "N");
	      
	    	  trans.setParameter("the_form", "PageForm");
	      	  trans.setParameter("the_prop", "pageClientVO");
          if (businessObjectType.equalsIgnoreCase(NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE) && Jsptype.equals(NEDSSConstants.PUBLISH)) {
			  trans.setParameter("the_form", "contactTracingForm");
	      	  trans.setParameter("the_prop", "cTContactClientVO");
		  } 
          
          if (businessObjectType.equalsIgnoreCase(NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE) && Jsptype.equals(NEDSSConstants.PUBLISH)) {
			  trans.setParameter("the_form", "PageSubForm");
	      	  trans.setParameter("the_prop", "pageClientVO");
		  } 
          
          if (businessObjectType.equalsIgnoreCase(NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE) && Jsptype.equals(NEDSSConstants.PUBLISH)) {
			  trans.setParameter("the_form", "PageSubForm");
	      	  trans.setParameter("the_prop", "pageClientVO");
		  } 
          
		} catch (TransformerConfigurationException ex) {
			logger.error("TransformerConfigurationException: " + ex.getMessage());
		}

	    //get the list of tab nodes to process
	  	NodeIterator nodeIterator = null;
		try {
		       nodeIterator = XPathAPI.selectNodeIterator(xmlDoc, xpath);
			} catch (TransformerException ex) {
				logger.error("TransformerException: " + ex.getMessage());
			}
		//pass the number of tabs, if it is just one we don't need prev/next
		
	    Node node;
	    int i =0;
	    while ((node = nodeIterator.nextNode())!= null)
	      {
	    	//open a buffered output for the result
	    	FileOutputStream fos = null;
	    	OutputStreamWriter outTab = null;

	    	//perform the transform to process the node
	    	javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(outTab);
	    	
	    	try {
	    	 //System.out.println("transforming node");
	    		writeTabFileJSP(removeSpaces(outTabFileList[i++]), node,mode,result,trans,dir,Jsptype);	
	          //  trans.transform(new DOMSource(node), result);	
	          //  PagePublisher publisher = PagePublisher.getInstance();
				try {
				//	boolean success = publisher.publishPage();
				} catch (Exception e) {
					logger.error("Error writing tab xml " + e.getMessage());
				}
			} catch (TransformerException ex) {
				logger.error("TransformerException: " + ex.getMessage());
			}
		  }//while
	        //System.out.println("while node iterator");
	} //processTabs
	   
	/**
	 * processTabs
	 *    Generate 2 compare/merge files from the XML combining each tab in the xml source
	 * @param xmlInputFile
	 * @param xpath to tab
	 * @param xslt file to use
	 * @param mode - compare
	 * 
	 */
	public void processTabsCompare (String xmlInputFile, String xpath, String xsltFile,String xsltFile2,String mode,String dir,String Jsptype, String businessObjectType)throws Exception {

		javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource(xmlInputFile);
		javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource(xsltFile);
		javax.xml.transform.Source xsltSource2 = new javax.xml.transform.stream.StreamSource(xsltFile2);

		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		dfactory.setNamespaceAware(true);
		Document xmlDoc = null;
		try {
			xmlDoc = dfactory.newDocumentBuilder().parse(xmlInputFile);
		} catch (SAXException ex ){	
			logger.error("SAX Exception: " + ex.getMessage());
		} catch (IOException ex) {
			logger.error("IOException: " + ex.getMessage());
		} catch (ParserConfigurationException ex) {
			logger.error("ParserConfigurationException: " + ex.getMessage());
		}
		//get a list of tab names to use for the file names
		NodeList tabList = xmlDoc.getElementsByTagName("Page");
		String[] outTabFileList = new String[tabList.getLength()];
		for (int i = 0; i < tabList.getLength(); ++i) {
			Element tabElement = (Element) tabList.item(i);
			NodeList tmpList = tabElement.getChildNodes();
			outTabFileList[i] = (tmpList.item(0).getNodeValue());
		}
		// Setup the xsl transformer
		TransformerFactory transFact = null;
		Transformer trans = null;
		try {
			transFact = TransformerFactory.newInstance( );
			trans = transFact.newTransformer(xsltSource);
			trans.setParameter("bus_obj", businessObjectType);

			FileOutputStream fos = null;
			OutputStreamWriter outTab = null;

			//perform the transform to process the node
			javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(outTab);
			String fileName = "";
			String directory = "pagemanagement";
			String folderNm = dir; 

			/* create JSP files with different names based on mode (compare or merge) */
			if(mode != null && mode.equalsIgnoreCase("compare"))
	    	{
				folderNm = folderNm + File.separator  + "view";
				fileName = "compare.jsp";
	    	}
			else
				 fileName = "merge.jsp";
	    	
		//	folderNm = folderNm + File.separator  + "view";
			File nbsDirectoryPath = new File("");


			nbsDirectoryPath = new File(PropertyUtil.nedssDir + directory + File.separator + folderNm + File.separator);

			if (!nbsDirectoryPath.exists())
				nbsDirectoryPath.mkdirs();
			File tabFile = new File(nbsDirectoryPath, fileName);	
			FileWriter jspOut = new FileWriter(tabFile);
			result = new StreamResult(jspOut);
			//InputStream is = new ByteArrayInputStream(xmlSource);
			trans.setParameter("mult_tab", "N");
			trans.setParameter("the_form", "PageForm");
			trans.setParameter("the_prop", "pageClientVO");
			
			trans.transform(xmlSource,result);
			jspOut.close(); 
			tabFile = new File(nbsDirectoryPath, fileName); 
			JspBeautifier beautifier = new JspBeautifier();
			beautifier.beautifyFile(tabFile);
			
			/* create JSP files with different names based on mode (compare or merge) */
			if(mode != null && mode.equalsIgnoreCase("compare"))
				fileName = "compare2.jsp";
			else 
				 fileName = "merge2.jsp";
			
				tabFile = new File(nbsDirectoryPath, fileName);	
				jspOut = new FileWriter(tabFile);
				result = new StreamResult(jspOut);
				
				trans = transFact.newTransformer(xsltSource2);
				trans.setParameter("bus_obj", businessObjectType);
				//InputStream is = new ByteArrayInputStream(xmlSource);
				trans.setParameter("mult_tab", "N");
				trans.setParameter("the_form", "PageForm");
				trans.setParameter("the_prop", "pageClientVO2");
				
				trans.transform(xmlSource,result);
	
				jspOut.close(); 
				//too much space in the tab file jsp - cleanup the output of tab files 
				tabFile = new File(nbsDirectoryPath, fileName); 
				beautifier = new JspBeautifier();
				beautifier.beautifyFile(tabFile);
		
		} catch (TransformerException ex) {
			logger.error("TransformerException: " + ex.getMessage());
		}catch (Exception e) {
			logger.error("Error writing tab xml " + e.getMessage());
		}
		//while
		//System.out.println("while node iterator");
	} //processTabs
	   

	
	/**
	 * writeTabFileJSP - write the file(s) to the correct directory
	 * @param xml file 
	 * @param node in xml for each tab /util:PageInfo/Page/PageTab
	 * @param xsl file to use
	 * @param Create or View
	 * @param dir for output
	 * @param publish or preview
	 */
	 private void writeTabFileJSP(String tabNm, Node sb,String mode,javax.xml.transform.Result result,Transformer trans, String dir,String Jsptype) throws Exception {

		 File nbsDirectoryPath = new File("");
	    	if(Jsptype.equalsIgnoreCase(NEDSSConstants.PREVIEW)){	
	    			
	    		class NbsFolderFilter implements FilenameFilter {
	    		    public boolean accept(File dir, String name) {
	    		        return (name.contains(NBS_TMP_DEPLOYMENT_CONTAINS));
	    		    }
	    		}
	    		
	    		class NbsFolderWARFilter implements FilenameFilter {
	    		    public boolean accept(File dir, String name) {
	    		        return (name.contains(NBS_TMP_WAR_NAME_CONTAINS));
	    		    }
	    		}
	    		
	    		String nbsTmpDeploy = "";
	    		//Retrieve the tmp deploy location and get hold of tmpxxxxnbs.ear-contents
	    		String tmpDir = System.getProperty("jboss.server.temp.dir");
	    		logger.debug("writeTabFileJSP() --- tmpDir -------------------------------------------------- "+tmpDir);
	    		 nbsDirectoryPath = new File(tmpDir + File.separator + "vfs/deployment" + File.separator);
	    		if(nbsDirectoryPath.exists()) {
	    			long lastMod = Long.MIN_VALUE;
	    			FilenameFilter filter = new NbsFolderFilter();
	    			File [] folders = nbsDirectoryPath.listFiles(filter);
	    			for (int i = 0; i < folders.length;) {
	    				File folder = folders[i++];
	    				if(folder != null && folder.getName().contains(NBS_TMP_DEPLOYMENT_CONTAINS) && (folder.lastModified() > lastMod)) {
	    					nbsTmpDeploy = folder.getName();
	    					lastMod = folder.lastModified();
	    					logger.debug("writeTabFileJSP() --- nbsTmpDeploy-----------------------------------"+nbsTmpDeploy);
	    					//break;
	    				}
	    			}
	    			
	    			File nbsDirectoryForWARPath = new File(tmpDir + File.separator + "vfs/deployment" + File.separator + nbsTmpDeploy + File.separator);
	    			FilenameFilter warFilter = new NbsFolderWARFilter();
	    			String [] warFolders = nbsDirectoryForWARPath.list(warFilter);
	    			String nbsTmpExpWAR="";
	    			for (int i = 0; i < warFolders.length;) {
	    				String folder = warFolders[i++];
	    				if(folder != null && folder.contains(NBS_TMP_WAR_NAME_CONTAINS)) {
	    					nbsTmpExpWAR = folder;
	    					logger.debug("writeTabFileJSP() --- nbsTmpExpWAR----------------------------"+nbsTmpExpWAR);
	    					break;
	    				}
	    			}
	    			
	    			if(mode != null && mode.equalsIgnoreCase(NEDSSConstants.VIEW))
			    	{
	    				tabNm = "view"+tabNm;
			    		dir = dir + File.separator  + "view";
			    		//tabNm = "view"+tabNm;
			    		
			    	}
	    			
	    			//Once the tmpEar Folder Name is found, explore to "nbs-exp.war" and to pageManagement eventually
	    			if(nbsTmpDeploy.length() > 0) {
	    				nbsDirectoryPath = new File(tmpDir + File.separator + "vfs/deployment" + File.separator + nbsTmpDeploy + File.separator + nbsTmpExpWAR);
	    				// Destination Dir
	    				 nbsDirectoryPath  = new File(nbsDirectoryPath + TARGET_FOLDER_IN_WAR + File.separator + dir + File.separator); 
	    			}
	    		}
	    		
	    	}else{
			    String directory = "pagemanagement";
		    	String folderNm = dir; 
		    	if(mode != null && mode.equalsIgnoreCase(NEDSSConstants.VIEW))
		    	{
		    		tabNm = "view"+tabNm;
		    		folderNm = folderNm + File.separator  + "view";
		    	}
		    	
				
				 nbsDirectoryPath = new File(PropertyUtil.nedssDir + directory + File.separator + folderNm + File.separator);
	    	}
	    	String fileName = tabNm + ".jsp";
			if (!nbsDirectoryPath.exists())
				nbsDirectoryPath.mkdirs();
			//	File nbsDirectoryPath = new File("C:\\wildfly-10.0.0.Final\\bin\\..\\nedssdomain\\Nedss\\pagemanagement\\10470");
			File tabFile = new File(nbsDirectoryPath, fileName);	    
		    
			FileWriter jspOut = new FileWriter(tabFile);
			result = new StreamResult(jspOut);
			if (sb != null)
		     trans.transform(new DOMSource(sb), result);

			jspOut.close(); 
			//too much space in the tab file jsp - cleanup the output of tab files 
			tabFile = new File(nbsDirectoryPath, fileName); 
			JspBeautifier beautifier = new JspBeautifier();
			beautifier.beautifyFile(tabFile);
			
		
	     }	
	 
		/**
		 * writeIndexFileJSP - write the index file to the correct directory
		 * TBD: will change to storing in template table
		 * @param hardcoded
		 */
	 private void writeIndexFileJSP(String tabNm, Node sb,String mode,javax.xml.transform.Result result,Transformer trans,String dir,String Jsptype) throws Exception {
		 File nbsDirectoryPath = new File("");
	    	if(Jsptype.equalsIgnoreCase(NEDSSConstants.PREVIEW)){	    		
	    
	    		File destDir = new File("");	    		
	    		
	    		class NbsFolderFilter implements FilenameFilter {
	    		    public boolean accept(File dir, String name) {
	    		        return (name.contains(NBS_TMP_DEPLOYMENT_CONTAINS));
	    		    }
	    		}
	    		
	    		class NbsFolderWARFilter implements FilenameFilter {
	    		    public boolean accept(File dir, String name) {
	    		        return (name.contains(NBS_TMP_WAR_NAME_CONTAINS));
	    		    }
	    		}
	    		
	    		String nbsTmpDeploy = "";
	    		//Retrieve the tmp deploy location and get hold of tmpxxxxnbs.ear-contents
	    		String tmpDir = System.getProperty("jboss.server.temp.dir");
	    		logger.debug("--- tmpDir-----------------------------------"+tmpDir);
	    		nbsDirectoryPath = new File(tmpDir + File.separator + "vfs/deployment" + File.separator);
	    		logger.debug("--- nbsDirectoryPath----------------------------------------"+nbsDirectoryPath);
	    		if(nbsDirectoryPath.exists()) {
	    			long lastMod = Long.MIN_VALUE;
	    			FilenameFilter filter = new NbsFolderFilter();
	    			File [] folders = nbsDirectoryPath.listFiles(filter);
	    			for (int i = 0; i < folders.length;) {
	    				File folder = folders[i++];
	    				if(folder != null && folder.getName().contains(NBS_TMP_DEPLOYMENT_CONTAINS) && (folder.lastModified() > lastMod)) {
	    					nbsTmpDeploy = folder.getName();
	    					lastMod = folder.lastModified();
	    					logger.debug("writeTabFileJSP() --- nbsTmpDeploy-----------------------------------"+nbsTmpDeploy);
	    					//break;
	    				}
	    			}
	    			
	    			File nbsDirectoryForWARPath = new File(tmpDir + File.separator + "vfs/deployment" + File.separator + nbsTmpDeploy + File.separator);
	    			FilenameFilter warFilter = new NbsFolderWARFilter();
	    			String [] warFolders = nbsDirectoryForWARPath.list(warFilter);
	    			String nbsTmpExpWAR="";
	    			for (int i = 0; i < warFolders.length;) {
	    				String folder = warFolders[i++];
	    				if(folder != null && folder.contains(NBS_TMP_WAR_NAME_CONTAINS)) {
	    					nbsTmpExpWAR = folder;
	    					logger.debug("writeIndexFileJSP() --- nbsTmpExpWAR----------------------------"+nbsTmpExpWAR);
	    					break;
	    				}
	    			}
	    			
	    			if(mode != null && mode.equalsIgnoreCase(NEDSSConstants.VIEW))
			    	{
			    		//tabNm = "view"+tabNm;
			    		dir = dir + File.separator  + "view";
			    	}
	    			//Once the tmpEar Folder Name is found, explore to "nbs-exp.war" and to pageManagement eventually
	    			if(nbsTmpDeploy.length() > 0) {
	    				nbsDirectoryPath = new File(tmpDir + File.separator + "vfs/deployment" + File.separator + nbsTmpDeploy + File.separator + nbsTmpExpWAR);
	    				// Destination Dir
	    				 nbsDirectoryPath  = new File(nbsDirectoryPath + TARGET_FOLDER_IN_WAR + File.separator + dir + File.separator); 	
	    			}
	    			
	    			
	    		}
	    		
	    	}else{
				    String directory = "pagemanagement";
			    	String folderNm = dir; 
			    	if(mode != null && mode.equalsIgnoreCase(NEDSSConstants.VIEW))
			    	{
			    		//tabNm = "view"+tabNm;
			    		folderNm = folderNm + File.separator  + "view";
			    	}
			    	
			    	//Creates indexCompare.jsp for compare investigation 
			    	if(mode != null && mode.equalsIgnoreCase("compare"))
			    	{
			    		tabNm = tabNm+"Compare";
			    		folderNm = folderNm + File.separator  + "view";
			    	}
			    	
					 nbsDirectoryPath = new File(PropertyUtil.nedssDir + directory + File.separator + folderNm + File.separator);
	    	}
	    	
	    	//Creates indexMerge.jsp for compare investigation 
	    	if(mode != null && mode.equalsIgnoreCase("merge"))
	    	{
	    		tabNm = tabNm+"Merge";
	    	}
	    	String fileName = tabNm + ".jsp";
	    	
		//	if (!nbsDirectoryPath.exists())
	    	if (nbsDirectoryPath.exists() && mode.equalsIgnoreCase(NEDSSConstants.CREATE))
	    		nbsDirectoryPath.delete();
	    	    
	    	if (!nbsDirectoryPath.exists())
				nbsDirectoryPath.mkdirs();
			//	File nbsDirectoryPath = new File("C:\\wildfly-10.0.0.Final\\bin\\..\\nedssdomain\\Nedss\\pagemanagement\\10470");
			File tabFile = new File(nbsDirectoryPath, fileName);	    
		    
			FileWriter jspOut = new FileWriter(tabFile);
			result = new StreamResult(jspOut);

			if (sb != null)
				trans.transform(new DOMSource(sb), result);

			jspOut.close(); 
	  
	}
	 
	 public String removeSpaces(String s) {
		  StringTokenizer st = new StringTokenizer(s," ",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
		}

}
