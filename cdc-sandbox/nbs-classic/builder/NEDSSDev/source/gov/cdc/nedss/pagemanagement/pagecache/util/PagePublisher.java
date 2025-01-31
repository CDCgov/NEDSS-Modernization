package gov.cdc.nedss.pagemanagement.pagecache.util;
import gov.cdc.nedss.localfields.dt.NbsPageDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;

import gov.cdc.nedss.util.NedssUtils;

/**
 * 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PagePublisher.java
 * Jan 15, 2010
 * @version
 */
public class PagePublisher {

	static final LogUtils logger = new LogUtils(PagePublisher.class.getName());	
	private static final String NBS_TMP_DEPLOYMENT_CONTAINS = "deployment";
	private static final String NBS_TMP_WAR_NAME_CONTAINS = "nbs.war-";
	private static final String SOURCE_FOLDER_IN_WAR = File.separator + "pagemanagement" + File.separator;
	private static final String TARGET_FOLDER_IN_WAR = File.separator + "pagemanagement" + File.separator + "rendering";
	
	
	private static PagePublisher instance;
	
	private PagePublisher(){
		
	}
	
	public static synchronized PagePublisher getInstance() {
		if(instance == null)
			instance = new PagePublisher();
		return instance;
	}
	
	/**
	 * Publishes the PageBuilder built JSP pages to NBS TMP war Deployment folder structure
	 */
/*	public boolean publishPage() throws IOException, InterruptedException {
		
		boolean success = false;
		
		class NbsFolderFilter implements FilenameFilter {
		    public boolean accept(File dir, String name) {
		        return (name.contains(NBS_TMP_EAR_CONTAINS));
		    }
		}
		
		String nbsTmpEar = "";
		//Retrieve the tmp deploy location and get hold of tmpxxxxnbs.ear-contents
		String tmpDir = System.getProperty("jboss.server.temp.dir");
		File nbsDirectoryPath = new File(tmpDir + File.separator + "deploy" + File.separator);
		if(nbsDirectoryPath.exists()) {
			FilenameFilter filter = new NbsFolderFilter();
			String [] folders = nbsDirectoryPath.list(filter);
			for (int i = 0; i < folders.length;) {
				String folder = folders[i++];
				if(folder != null && folder.contains(NBS_TMP_EAR_CONTAINS)) {
					nbsTmpEar = folder;
					break;
				}
			}
			logger.info("NBS temp ear directory :"+nbsTmpEar);
			//Once the tmpEar Folder Name is found, explore to "nbs-exp.war" and to pageManagement eventually
			if(nbsTmpEar.length() > 0) {
				nbsDirectoryPath = new File(tmpDir + File.separator + "deploy" + File.separator + nbsTmpEar + File.separator + NBS_TMP_WAR_NAME);
				logger.info("NBS Directory path:"+nbsDirectoryPath);
				// Source Dir 
				File srcDir = new File(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator ) ;
				logger.info("Source Directory for page publish :"+srcDir);
				// Destination Dir
				File destDir = new File(nbsDirectoryPath + TARGET_FOLDER_IN_WAR + File.separator ); 	
				logger.info("Destination Directory for page publish :"+destDir);
				//if(!destDir.exists())
				//	destDir.mkdir();
				//	_copyProcess(srcDir, destDir);
				//success = true;
			
				PageManagementActionUtil util = new PageManagementActionUtil();
				Collection<Object> frmCdList = new ArrayList<Object> ();
				frmCdList = util.getInvestigationFormCodeonServerStartup();
				Iterator<Object> it = frmCdList.iterator();
				TraverseAndCopy travCopy = new TraverseAndCopy();
			    while(it.hasNext()){
			    	String strFrmCd = it.next().toString();
			    	File formCdDir = new File(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator + strFrmCd + File.separator) ;
			    	if(!formCdDir.exists()){
			    		 if(!srcDir.exists())
			    		    srcDir.mkdir();
			    		 formCdDir.mkdir();
			             new File(formCdDir.toString()+ File.separator + "view").mkdir();
			             
			             jspFilesList =  util.getJspFiles(invFormCd,session);
							Iterator<Object> it = jspFilesList.iterator();
							    while(it.hasNext())
								nbsPageDT = (NbsPageDT)it.next();
							    byte[] jsppayLoad = nbsPageDT.getJspPayload();
							    try{
							    	JspPayLoad = new File(srcDir.toString()+ File.separator + invFormCd+".zip");
							    	OutputStream out = new FileOutputStream(JspPayLoad);
							    	out.write(jsppayLoad);
							    	out.close();
							         }catch (IOException ex) {
									  logger.fatal("Error in writing to the Jsp zip File from database in publishPage method: ", ex.getMessage());								
								  }	
			             
					
			    	}//
			    }
			    success = travCopy.traverseAndCopy(srcDir, destDir);
				
			}
			
			
		}
		return success;
	}*/
	
	/**
	 * Publishes the PageBuilder built JSP pages to NBS TMP war Deployment folder structure
	 */
	public boolean publishPage(String invFormCd) throws IOException, InterruptedException {
		
		boolean success = false;
		String returnValue ="";
		
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
		logger.debug("publishPage() tmpDir -------------------------------------------------- "+tmpDir);
		File nbsDirectoryPath = new File(tmpDir + File.separator + "vfs" + File.separator + "deployment" + File.separator);
		File srcDir;
		File destDir;
		File zipFile = null;
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
			
			File nbsDirectoryForWARPath = new File(tmpDir + File.separator + "vfs" + File.separator + "deployment" + File.separator + nbsTmpDeploy + File.separator);
			FilenameFilter warFilter = new NbsFolderWARFilter();
			String [] warFolders = nbsDirectoryForWARPath.list(warFilter);
			String nbsTmpExpWAR="";
			for (int i = 0; i < warFolders.length;) {
				String folder = warFolders[i++];
				if(folder != null && folder.contains(NBS_TMP_WAR_NAME_CONTAINS)) {
					nbsTmpExpWAR = folder;
					logger.debug("publishPage() nbsTmpExpWAR----------------------------"+nbsTmpExpWAR);
					break;
				}
			}
			
			logger.info("NBS temp ear directory :"+nbsTmpDeploy);
			//Once the tmpEar Folder Name is found, explore to "nbs-exp.war" and to pageManagement eventually
			if(nbsTmpDeploy.length() > 0) {
				//String filename = FilenameUtils.normalize(tmpDir + File.separator + "vfs" + File.separator + "deployment" + File.separator + nbsTmpDeploy + File.separator + nbsTmpExpWAR);
				nbsDirectoryPath = new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(tmpDir + File.separator + "vfs" + File.separator + "deployment" + File.separator + nbsTmpDeploy + File.separator + nbsTmpExpWAR)));
				logger.debug("nbsDirectoryPath ------------------------------------- "+nbsDirectoryPath);

				//String fileName1 = FilenameUtils.normalize(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator);
				File pagemanagementdir = new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator))) ;
				// Source Dir 
				if(invFormCd.equals("")) {
					//String fileName = FilenameUtils.normalize(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator);
					srcDir = new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator) )) ;
				}
				else{
					//String srcDirPath = FilenameUtils.normalize(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator + invFormCd + File.separator);
				    srcDir = new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator + invFormCd + File.separator))) ;
				    //String zipFilePath = FilenameUtils.normalize(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator +invFormCd + File.separator + invFormCd+".zip");
				    zipFile =  new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator +invFormCd + File.separator + invFormCd+".zip")));
				}
				    logger.info("Source Directory for page publish :"+srcDir);
				// Destination Dir
				if(invFormCd.equals("")) {
					//String destDirPath = FilenameUtils.normalize(nbsDirectoryPath + TARGET_FOLDER_IN_WAR + File.separator );
					destDir = new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(nbsDirectoryPath + TARGET_FOLDER_IN_WAR + File.separator )));
				}
				else {
					//String destDirPath = FilenameUtils.normalize(nbsDirectoryPath + TARGET_FOLDER_IN_WAR + File.separator + invFormCd + File.separator);
				   destDir = new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(nbsDirectoryPath + TARGET_FOLDER_IN_WAR + File.separator + invFormCd + File.separator))); 	
				}
				logger.info("Destination Directory for page publish :"+destDir);
				//if(!destDir.exists())
				//	destDir.mkdir();
				//	_copyProcess(srcDir, destDir);
				//success = true;
				if(invFormCd.equals("")){
				PageManagementActionUtil util = new PageManagementActionUtil();
				Collection<Object> frmCdList = new ArrayList<Object> ();
				frmCdList = util.getInvestigationFormCodeonServerStartup();
				Iterator<Object> it = frmCdList.iterator();
				TraverseAndCopy travCopy = new TraverseAndCopy();
				 if(!pagemanagementdir.exists())
		        	 pagemanagementdir.mkdir();
		             
			    while(it.hasNext()){
			    	NbsPageDT nbsPageDT1 = new NbsPageDT();
			    	nbsPageDT1 = (NbsPageDT)it.next();
			    	String scrDirPath = FilenameUtils.normalize(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator + nbsPageDT1.getFormCd() + File.separator);
			    	srcDir = new File(scrDirPath) ;
			    	
			    	//if the jsp files for the condition doesnt exist on the file system.
			    	if(!srcDir.exists()){
			    		
			    	srcDir.mkdir();			    	
			    	new File(srcDir.toString()+ File.separator + NEDSSConstants.VIEW).mkdir();
			    	
			    	Collection<Object> jspFilesList = new ArrayList<Object> ();
					NbsPageDT nbsPageDT = new NbsPageDT();
					File  JspPayLoad = new File("");
					try{
						jspFilesList =  util.getJspFiles(nbsPageDT1.getFormCd());
						Iterator<Object> it1 = jspFilesList.iterator();
						    while(it1.hasNext())
							nbsPageDT = (NbsPageDT)it1.next();
						    byte[] jsppayLoad = nbsPageDT.getJspPayload();
						    try{
						    	JspPayLoad = new File(srcDir.toString()+ File.separator + nbsPageDT1.getFormCd()+".zip");
						    	OutputStream out = new FileOutputStream(JspPayLoad);
						    	out.write(jsppayLoad);
						    	out.close();
						         }catch (IOException ex) {
								  logger.fatal("Error in writing to the Jsp zip File from database in publishPage method: "+ ex.getMessage(), ex);								
							  }	
						     			             
						             returnValue=  unzipfiles(JspPayLoad) ;   
						    	

					 }catch (Exception ex) {
							logger.fatal("Error in getting the Jsp File from database in publishPage method: "+ ex.getMessage(), ex);
							
					  }	
			    	}//if the jsp files for the condition doesnt exist on the file system.
					
			    	
			        }//while ends
			        srcDir = pagemanagementdir;
				}
				
				try {
					PageManagementActionUtil util = new PageManagementActionUtil();
		    		NbsPageDT pageDT = util.getNbsPageDetailsExceptJspPayloadByFormCd(invFormCd);
		    		if(pageDT!=null && pageDT.getLastChgTime()!=null) {
			    		logger.debug("pageDT.getLastChgTime().getTime() ------- "+pageDT.getLastChgTime().getTime());
			    		//In case of load balancer, if page is published on one node and other node does not have changes, 
			    		//then before accessing page check if Nedss\pagemanagement time is older then time in Nbs_page table.
			    		if(srcDir.exists() && srcDir.lastModified()<pageDT.getLastChgTime().getTime()) {
			    			deleteDirectory(srcDir, zipFile, invFormCd);
			    			QuestionsCache.getDMBQuestionMapAfterPublish();
			    		}
		    		}
		    	}catch(Exception ex) {
		    		logger.fatal("Error while getting getNbsPageDTByFormCd:"+invFormCd+" --------- "+ ex.getMessage(), ex);
		    	}
				
				if(!srcDir.exists() && !zipFile.exists() && !invFormCd.equals("")){ //copy the jsp contents from database and unzip them .
					PageManagementActionUtil util = new PageManagementActionUtil();
					Collection<Object> jspFilesList = new ArrayList<Object> ();
					NbsPageDT nbsPageDT = new NbsPageDT();
					File  JspPayLoad = new File("");
					try{
						jspFilesList =  util.getJspFiles(invFormCd);
						Iterator<Object> it = jspFilesList.iterator();
						
						if(!pagemanagementdir.exists())
				        	 pagemanagementdir.mkdir();
				             srcDir.mkdir();
				             new File(srcDir.toString()+ File.separator + NEDSSConstants.VIEW).mkdir();
				             
						    while(it.hasNext())
							nbsPageDT = (NbsPageDT)it.next();
						    byte[] jsppayLoad = nbsPageDT.getJspPayload();
						    try{
						    	JspPayLoad = new File(NedssUtils.CleanStringPath(srcDir.toString()+ File.separator + invFormCd+".zip"));
						    	OutputStream out = new FileOutputStream(JspPayLoad);
						    	out.write(jsppayLoad);
						    	out.close();
						         }catch (IOException ex) {
								  logger.fatal("Error in writing to the Jsp zip File from database in publishPage method: "+ ex.getMessage(), ex);								
							  }	
						            returnValue=  unzipfiles(JspPayLoad) ;   
						    	

					 }catch (Exception ex) {
							logger.fatal("Error in getting the Jsp File from database in publishPage method: "+ ex.getMessage(), ex);
							
					  }		
					
					
				}
					
				if(invFormCd.equals("")||(!returnValue.equals("error"))){
				TraverseAndCopy travCopy = new TraverseAndCopy();
				success = travCopy.traverseAndCopy(srcDir, destDir,invFormCd);
				}
			}
			
			
		}
		return success;
	}
	/**
	 * Copies src file to dst file. If the dst file does not exist, it is created 
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	private void copyProcess(File src, File dst) throws IOException { 
		
		InputStream in = new FileInputStream(src); 
		OutputStream out = new FileOutputStream(dst); 
		// Transfer bytes from in to out 
		byte[] buf = new byte[1024]; 
		int len; 
		while ((len = in.read(buf)) > 0) { 
			out.write(buf, 0, len); 
		} 
		in.close(); 
		out.close(); 
	}
	
	/**
	 * uses Java RunTime to Copy Folders from Src to Dst (Note: Regular Copy does not work if the SRC Folder is a read-only since java IO cannot read contents of a readonly InputStream 
	 * @param src0
	 * @param dst0
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void _copyProcess(File src0, File dst0) throws IOException, InterruptedException { 
		
		String src = src0.getAbsolutePath();
		String dst = dst0.getAbsolutePath();

		String[] args = {"CMD", "/C", "XCOPY", "/S","/Y","/I", src, dst};
		logger.info("Initiated copying the file to temp directry");
		Process p = Runtime.getRuntime().exec(args);
		//logger.info("The process exit with success:"+p.exitValue());
		logger.info("Done copying the file to temp directry");
	} 	
	public String  unzipfiles(File jspPayLoad){
		Enumeration entries;
	    ZipFile zipFile;

		try {	
	
		      zipFile = new ZipFile(jspPayLoad);

		      entries = zipFile.entries();

		      while(entries.hasMoreElements()) {
		        ZipEntry entry = (ZipEntry)entries.nextElement();

		        if(entry.isDirectory()) {
		          // Assume directories are stored parents first then children.
		          logger.debug("Extracting directory: " + entry.getName());
		          // This is not robust, just for demonstration purposes.
		          (new File(entry.getName())).mkdir();
		          continue;
		        }
		        if(entry.getName().contains(".zip"))
		        	continue;

		        logger.debug("Extracting file: " + entry.getName());
		        copyInputStream(zipFile.getInputStream(entry),
		           new BufferedOutputStream(new FileOutputStream(replaceDriveName(entry.getName()))));
		      }

		      zipFile.close();
		    } catch (IOException ioe) {
		    	logger.error("Unhandled exception:"+ioe);
		      ioe.printStackTrace();
		      return("error");
		    }
		    return("");
	}
	
	public static final void copyInputStream(InputStream in, OutputStream out)
	  throws IOException
	  {
	    byte[] buffer = new byte[1024];
	    int len;

	    while((len = in.read(buffer)) >= 0)
	      out.write(buffer, 0, len);

	    in.close();
	    out.close();
	  }
	
	public static String getSourceDirectoryPath(String pgFormCode)throws IOException, InterruptedException {
		File srcDir = new File(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator + pgFormCode) ;
		return(srcDir.toString());
		
	}
	
	public String replaceDriveName(String path){
		String drive = System.getProperty("nbs.dir").toString().intern();
		StringBuffer updatedPath= new StringBuffer("");
		if(path!=null){
			updatedPath.append(drive.substring(0,2)).append(path.substring(2));
		}
		return updatedPath.toString();
	}

	private void deleteFilesWithinDirectory(File directoryName) {
		String[] entries = directoryName.list();
		for(String s: entries){
		    File currentFile = new File(directoryName.getPath(),s);
		    currentFile.delete();
		}
	}
	
	private void deleteDirectory(File srcDir, File zipFile, String invFormCd) {
		zipFile.delete();
		
		deleteFilesWithinDirectory(srcDir);
		
		File viewDir = new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(System.getProperty("nbs.dir") + SOURCE_FOLDER_IN_WAR + File.separator + invFormCd + File.separator+ NEDSSConstants.VIEW + File.separator))) ;
		deleteFilesWithinDirectory(viewDir);
		
		viewDir.delete();
		srcDir.delete();
	}
	

	
}
