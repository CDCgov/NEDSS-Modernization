package gov.cdc.nedss.pagemanagement.pagecache.util;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PageBuilder.java
 * Jan 13, 2010
 * @version 1.0
 */
public class PageBuilder {

    private static final Logger logger = Logger.getLogger(PageBuilder.class);

    private static final Long PAGE = 1002L;
    private static final Long TAB= 1010L;
    private static final Long SECTION = 1015L;
    private static final Long SUBSECTION = 1016L; 
    private static final Long INPUT = 1008L;
    private static final Long SINGLESELECT = 1007L;
    private static final Long MULTISELECT = 1013L;
    private static final Long TEXTAREA = 1009L;
    private static final Long CHECKBOX = 1001L;
    private static final Long RADIO = 1006L;
    private static final Long BUTTON = 1000L;
    private static final Long HYPERLINK = 1003L;
    private static final Long SUBHEADING = 1011L;
    private static final Long LINESEPARATOR = 1012L;
    private static final Long READONLY = 1014L;
    private static final Long PARTICIPATION = 1017L;
    private static final Long TABLE = 1022L;
    private static final Long INFORMATIONBAR = 1023L;
    private static final Long SINGLESELECT_READONLY_SAVE = 1024L;
    private static final Long MULTISELECT_READONLY_SAVE = 1025L;
    private static final Long INPUT_READONLY_SAVE = 1026L;
    private static final Long SINGLESELECT_READONLY_NOSAVE = 1027L;
    private static final Long MULTISELECT_READONLY_NOSAVE = 1028L;
    private static final Long INPUT_READONLY_NOSAVE = 1029L;    
    private static final String SECTION_END_TAG = "</nedss:container>";
    private static final String SUBSECTION_END_TAG = "</nedss:container>";
    private static final String SECTIONS_REPLACER_STRING_IN_TAB = "$$$$REPLACE_WITH_SECTIONS$$$$";
    
    private static final String NEWLINE = "\n";
    
	private static PageBuilder instance;
	
	private PageBuilder(){
		
	}
	
	public static synchronized PageBuilder getInstance() {
		if(instance == null)
			instance = new PageBuilder();
		return instance;
	}    
    
	public void writeJspContentToFile(ArrayList<Object> list) throws Exception {
		
		writeToJSP(list, NEDSSConstants.CREATE);
		writeToJSP(list, NEDSSConstants.VIEW);
		
	}
	
	/**
	 * Builds JSP File(s) and writes it to the <nbs.dir> System property dir structure 
	 * @param ArrayList<NbsQuestionMetadata>
	 */
	public void writeToJSP(ArrayList<Object> list, String mode) throws Exception {
		
		//First element SHOULD always be of Type 'Page'
		NbsQuestionMetadata dt = (NbsQuestionMetadata) list.get(0);
		if(dt.getNbsUiComponentUid() != null && dt.getNbsUiComponentUid().compareTo(PAGE) == 0) {
			String pageJSP = "";
			if(!mode.equals(NEDSSConstants.VIEW))
				pageJSP = dt.getJspSnippetCreateEdit();
			else if(mode.equals(NEDSSConstants.VIEW))
				pageJSP = dt.getJspSnippetView();
			
			//write the TopLevel JSP to output stream (file name replacement need to happen)
			writeIndexFileJSP(pageJSP, mode);			
		} else {
			throw new NEDSSSystemException("'PAGE' UIComponent is NOT FOUND !!! for NbsUiMetadataUid: " +  dt.getNbsUiMetadataUid());
		}
		String tab = "";
		String section = "";
		String subSection = "";
		StringBuffer sb = new StringBuffer();
		String currentTabNm = "";
		ArrayList<Object> sectionList = new ArrayList<Object>();
		ArrayList<Object> subSectionList = new ArrayList<Object>();
		
		for (int i=1; i<list.size(); i++) {
			dt = (NbsQuestionMetadata) list.get(i);
			Long cUid = dt.getNbsUiComponentUid();
			String jspSnippet = !mode.equals(NEDSSConstants.VIEW) ? dt.getJspSnippetCreateEdit() : dt.getJspSnippetView();
			
			//Check for Tabs
			if(cUid != null && cUid.compareTo(TAB) == 0) {
				//For each Tab, create a new JSP
				if(tab == "") {
					tab = jspSnippet;
				} else {
					String tabJSP = _prepareTabJSP(tab, section, subSection, sb, sectionList);
					writeTabFileJSP(currentTabNm, tabJSP, mode);
					tab = "";
					section = "";
					sectionList = new ArrayList<Object>();
					subSectionList = new ArrayList<Object>();
					subSection = "";
					currentTabNm = "";
					sb = new StringBuffer();
					tab = jspSnippet;
				}
				currentTabNm = dt.getQuestionLabel();
				
			} else if(cUid != null && cUid.compareTo(SECTION) == 0) {
				if(section == "") {
					section = jspSnippet;
				}else {
					String secJsp = _prepareSectionJSP(section, subSectionList);
					sectionList.add(secJsp);
					section = "";
					subSection = "";
					subSectionList = new ArrayList<Object>();
					sb = new StringBuffer();
					section = jspSnippet;
				}
			} else if(cUid != null && cUid.compareTo(SUBSECTION) == 0) {
				if(subSection == "") {
					subSection = jspSnippet;
				} else {
					String subSecJsp = _prepareSubSectionJSP(subSection, sb);
					sectionList.add(subSecJsp);
					subSection = "";
					sb = new StringBuffer();
					subSection = jspSnippet;
				}
			} else if(cUid.compareTo(INPUT) == 0 || cUid.compareTo(INPUT_READONLY_SAVE) == 0 || cUid.compareTo(INPUT_READONLY_NOSAVE) == 0 || 
					cUid.compareTo(SINGLESELECT) == 0 || cUid.compareTo(SINGLESELECT_READONLY_SAVE) == 0 || cUid.compareTo(SINGLESELECT_READONLY_NOSAVE) == 0 ||
					cUid.compareTo(MULTISELECT) == 0 || cUid.compareTo(MULTISELECT_READONLY_SAVE) == 0 || cUid.compareTo(MULTISELECT_READONLY_NOSAVE) == 0  || 
					cUid.compareTo(CHECKBOX) == 0 ||  cUid.compareTo(READONLY) == 0) {
				String qSnippet = jspSnippet == null ? "<!--" + dt.getQuestionLabel() + "-->" : jspSnippet;
				sb.append(qSnippet).append(NEWLINE);
			}
		}
		//When no more tabs are available, prepare tab for the final TAB
		String tabJSP = _prepareTabJSP(tab, section, subSection, sb, sectionList);
		writeTabFileJSP(currentTabNm, tabJSP, mode);

	}
	
	@SuppressWarnings("unchecked")
	private String _prepareTabJSP(String tab, String section, String subSection, StringBuffer qs, ArrayList sectionList) {
		
		String returnSt = "";
		if(tab != null && tab.length() > 0) {
			
			StringBuffer sb = new StringBuffer();
			if(sectionList.size() > 0) {
				Iterator iter = sectionList.iterator();
				while(iter.hasNext()) {
					sb.append(iter.next());
				}				
			} else {
				String ssJsp = _prepareSubSectionJSP(subSection, qs);
				ArrayList ssList = new ArrayList();
				ssList.add(ssJsp);
				String secJsp = _prepareSectionJSP(section, ssList);
				sb.append(secJsp);
			}
			
			int start = tab.indexOf(SECTIONS_REPLACER_STRING_IN_TAB);
			String startJSP = tab.substring(0,start);
			int end = start + SECTIONS_REPLACER_STRING_IN_TAB.length();
			String endJSP = tab.substring(end);
			returnSt = startJSP + sb.toString() + endJSP;
		}
		return returnSt;
	}		
	
	@SuppressWarnings("unchecked")
	private String _prepareSectionJSP(String section, ArrayList subSectionList) {
		
		String returnSt = "";
		if(section != null && section.length() > 0) {
			
			StringBuffer sb = new StringBuffer();
			Iterator iter = subSectionList.iterator();
			while(iter.hasNext()) {
				sb.append(iter.next());
			}
			
			int start = section.indexOf(SECTION_END_TAG);
			returnSt = section.substring(0,start) + sb.toString() + SECTION_END_TAG;
		}
		return returnSt;
	}	
	
	
	private String _prepareSubSectionJSP(String subSection, StringBuffer sb) {
		String returnSt = "";
		if(subSection != null && subSection.length() > 0) {
			int start = subSection.indexOf(SUBSECTION_END_TAG);
			returnSt = subSection.substring(0,start) + sb.toString() + SUBSECTION_END_TAG;
		}
		return returnSt;
	}
	
	

    private void writeTabFileJSP(String tabNm, String sb, String mode) throws Exception {

    	String directory = "pagemanagement";
    	String folderNm = "10470"; 
    	if(mode.equals(NEDSSConstants.VIEW))
    		folderNm = "10470" + File.separator + "view";
		String fileName = tabNm + ".jsp";
		File nbsDirectoryPath = new File(PropertyUtil.nedssDir + directory + File.separator + folderNm + File.separator);

		File tabFile = new File(nbsDirectoryPath, fileName);
		
		FileWriter jspOut = new FileWriter(tabFile);
		if (sb != null)
			jspOut.write(sb);

		jspOut.close(); 
     }		
	
    private void writeIndexFileJSP(String sb, String mode) throws Exception {

    	String directory = "pagemanagement";
    	String folderNm = "10470"; 
    	if(mode.equals(NEDSSConstants.VIEW))
    		folderNm = "10470" + File.separator + "view";
    	
		String fileName = "index.jsp";
		File nbsDirectoryPath = new File(PropertyUtil.nedssDir + directory + File.separator + folderNm + File.separator);

		if (!nbsDirectoryPath.exists())
			nbsDirectoryPath.mkdirs(); // make the directory if it does not exist

		File indexFile = new File(nbsDirectoryPath, fileName);
		
		FileWriter jspOut = new FileWriter(indexFile);
		if (sb != null)
			jspOut.write(sb);

		jspOut.close(); 
     }	
	
	
	
}
