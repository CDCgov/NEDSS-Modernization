package gov.cdc.nedss.pagemanagement.wa.xml;
import gov.cdc.nedss.pagemanagement.wa.xml.util.*; //Note: xmlbeans jar file maps here..

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlString;


/**
 * MarshallPageXML - Create XML for a DMB Page using the UI metadata
 *  passed in the PageManagementProxyVO. Also write out XML for Export.
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: CSC</p>
 * MarshallPageXML.java
 * Feb 03, 2010
 * @version 0.9
 */

public class GenerateExportImportXML {

	static final LogUtils logger = new LogUtils(GenerateExportImportXML.class.getName());
	private static final String STRUCTURED_NUMERIC_STYLE_CLASS = "structuredNumericField";
	private static final String RELATED_UNITS_STYLE_CLASS = "relatedUnitsField";
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	
	

	/**
	 * GeneratePageXMLFile from Page Management Proxy VO
	 * This method uses the xmlbeans jar generated from the schema.
	 * The schema jar dmbPageSchema.jar maps to pagemanagement.util.
	 * This method assumes the collection is in the logical order:
	 *   i.e. page - section - subsection - element.
	 * Note that setting an element to null will cause xmlbeans to
	 * generate the element with a null indicator: i.e.
	 * <Required xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
	 * Thus, the checks for null before setting optional elements.
	 *
	 * @param PageManagementProxyVO containing page elements
	 * @param String file to make
	 * @return empty string if successful, otherwise error message suitable for display
	 *
	 */
	public TemplateExportType GenerateExportImportXML(Map<Object,Object> inpMap,Map<Object,Object> qMap,Map<Object,Object> uiMap,Map<Object,Object> nndMap,Map<Object,Object> rdbMap, 
			Map<Object,Object> cdGrpMetaMap,Map<Object,Object> cdMap,Map<Object,Object> cdValGen,Map<Object,Object> rMap,String fileToMake) {
		
		//define the xmlbeans generated attributes..		
		TemplateExportType  templateExport = null;
		String isPublished="";		
		
		//get a blank new XML document
		templateExport = TemplateExportType.Factory.newInstance();
		//metaInsert = templateExport.getQuestionInsertArray();
		// add the header to the xml
		
		
		Iterator<?> itQ = qMap.entrySet().iterator();
	    while (itQ.hasNext()) {
	        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)itQ.next();
	      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
		    if (pairs == null) {
		   		logger.warn("Marshaller: Unexpected null ? ");
	    		break;
		    }
		    addWaQuestionToXML(templateExport,pairs);
	    }
		
		// add the page components to the xml
		
		Iterator<?> it = inpMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)it.next();
	      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
		    if (pairs == null) {
		   		logger.warn("Marshaller: Unexpected null ? ");
	    		break;
		    }
		    addWaTemaplateToXML(templateExport,pairs);
		    addHeaderToXML(templateExport,pairs);
	    }
	    Iterator<?> itUi = uiMap.entrySet().iterator();
	    while (itUi.hasNext()) {
	        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)itUi.next();
	      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
		    if (pairs == null) {
		   		logger.warn("Marshaller: Unexpected null ? ");
	    		break;
		    }
		    addWaUiMetadataToXML(templateExport,pairs);
	    }
	    Iterator<?> itNNd = nndMap.entrySet().iterator();
	    while (itNNd.hasNext()) {
	        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)itNNd.next();
	      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
		    if (pairs == null) {
		   		logger.warn("Marshaller: Unexpected null ? ");
	    		break;
		    }
		    addWaNndMetadataToXML(templateExport,pairs);
	    }
	    Iterator<?> itRdb = rdbMap.entrySet().iterator();
	    while (itRdb.hasNext()) {
	        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)itRdb.next();
	      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
		    if (pairs == null) {
		   		logger.warn("Marshaller: Unexpected null ? ");
	    		break;
		    }
		    addWaRdbMetadataToXML(templateExport,pairs);
	    }	
	    
	    Iterator<?> itRMeta = rMap.entrySet().iterator();
	    while (itRMeta.hasNext()) {
	        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)itRMeta.next();
	      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
		    if (pairs == null) {
		   		logger.warn("Marshaller: Unexpected null ? ");
	    		break;
		    }
		    addWaRuleMetadataToXML(templateExport,pairs);
	    }	
	  
	    addVocabToXML(templateExport,cdMap,cdGrpMetaMap,cdValGen);
	    
			/*XmlOptions opts = new XmlOptions(); 
		    if (!fileToMake.isEmpty()) {
			File outFile = null;
			outFile = new File(fileToMake);		
			opts.setSavePrettyPrint();
			opts.setSavePrettyPrintIndent(4);
			try {
				templateExport.save(outFile, opts);
			} catch (IOException e) {
				logger.error("Marshaller error writing XML to <" +fileToMake + "> output file? " +e.getMessage());
				//return "Error writing XML file.";
			}
		}*/
		//if no file to make, set the XML into the wa_template
		//Using document builder or Oracle takes away line returns
		/*if (fileToMake.isEmpty()) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				templateExport.save(baos, opts);
			} catch (IOException e) {
				logger.error("Marshaller error writing XML to output stream? " +e.getMessage());
				//return "Error writing XML file.";
			}
			//waTemp.setXmlPayload(baos.toString());
	
		  } //fileToMake.isEmpty()*/
	    
		return templateExport;	//return null string if successful (or error msg if not)
	}//GeneratePageXMLFile

	
	/**
	 * addUIMetadataToXML
	 * Add each UI Metadata used in the page to the UI MetaData list in the XML in case of Export.
	 * UI Metadata is a 'customization' of the base Question. It also holds page, section and
	 * subsection information.
	 *
	 * @param PageInfo - root page element
	 * @param WaUiMetadataDT - UI Metadata
	 *
	 */
	
	private void addHeaderToXML(TemplateExportType  templateExport, Map.Entry<Object, Object> pair)
	{
		//Add a new UI Metadata entry to the XML		
		ExportHeaderType header = templateExport.addNewExportHeader();
		if (pair.getKey() != null)
			header.setTemplateNm(pair.getKey().toString());
		
		    header.setExportedDateTime(Calendar.getInstance());
		    header.setExportedFromState(propertyUtil.getMsgSendingFacility());
		
	}
	private void addWaTemaplateToXML(TemplateExportType  templateExport, Map.Entry<Object, Object> pair)
	{
		//Add a new UI Metadata entry to the XML		
		TemplateInsertType newTempInsert = templateExport.addNewTemplateInsert();	
		if (pair.getKey() != null)
			newTempInsert.setTemplateName(pair.getKey().toString());
		if (pair.getValue() != null)
			newTempInsert.setInsertStatement(pair.getValue().toString());
	}
	private void addWaQuestionToXML(TemplateExportType  templateExport, Map.Entry<Object, Object> pair)
	{
		//Add a new UI Metadata entry to the XML		
		QuesInsertType newQuesInsert = templateExport.addNewQuestionInsert();
		if (pair.getKey() != null){
		String key = pair.getKey().toString().substring(0, pair.getKey().toString().indexOf(":QT:"));
		String type = pair.getKey().toString().substring(pair.getKey().toString().indexOf(":QT:")+4,pair.getKey().toString().indexOf(":QN:"));
		String uniqueNm = pair.getKey().toString().substring(pair.getKey().toString().indexOf(":QN:")+4);
		
			newQuesInsert.setQuestionIdentifier(key);
		    newQuesInsert.setType(type);
		    newQuesInsert.setUniqueName(uniqueNm);
		}
		if (pair.getValue() != null)
			newQuesInsert.setInsertStatement(pair.getValue().toString());
	}
	
	private void addWaUiMetadataToXML(TemplateExportType  templateExport, Map.Entry<Object, Object> pair)
	{
		//Add a new UI Metadata entry to the XML		
		MetaInsertType newMetaInsert = templateExport.addNewUIMetadataInsert();		
		if (pair.getKey() != null)
			newMetaInsert.setQuestionIdentifier(pair.getKey().toString());
		if (pair.getValue() != null)
			newMetaInsert.setInsertStatement(pair.getValue().toString());
	}
	
	private void addWaNndMetadataToXML(TemplateExportType  templateExport, Map.Entry<Object, Object> pair)
	{
		//Add a new UI Metadata entry to the XML		
		MetaInsertTypeArray newMetaInsert = templateExport.addNewNNDInsert();		
		if (pair.getKey() != null)
			newMetaInsert.setQuestionIdentifier(pair.getKey().toString());
		if (pair.getValue() != null)
			newMetaInsert.addNewInsertStatement().setStringValue(pair.getValue().toString());
			//newMetaInsert.setInsertStatement(pair.getValue().toString());
	}
	
	private void addWaRdbMetadataToXML(TemplateExportType  templateExport, Map.Entry<Object, Object> pair)
	{
		//Add a new UI Metadata entry to the XML		
		MetaInsertTypeArray newMetaInsert = templateExport.addNewRDBInsert();	
		if (pair.getKey() != null)
			newMetaInsert.setQuestionIdentifier(pair.getKey().toString());
		if (pair.getValue() != null)
			newMetaInsert.addNewInsertStatement().setStringValue(pair.getValue().toString());
			//newMetaInsert.setInsertStatement(pair.getValue().toString());
	}
	
	private void addWaRuleMetadataToXML(TemplateExportType  templateExport, Map.Entry<Object, Object> pair)
	{
		//Add a new UI Metadata entry to the XML		
		XmlString newRuleInsert = templateExport.addNewRuleInsert();	
		if (pair.getKey() != null)			
			newRuleInsert.setStringValue(pair.getValue().toString());
	}
	
	private void addVocabToXML(TemplateExportType  templateExport, Map<Object,Object> cdMap,Map<Object,Object> cdGrpMetaMap,Map<Object,Object> cdValGen)
	{
		//Add a new UI Metadata entry to the XML		
		
		 Iterator<?> itcdMap = cdMap.entrySet().iterator();
		 int i=0;
		 boolean codeSetNmExists=false;
		 String[] str = null;
		 boolean codeExists= false;
		 while (itcdMap.hasNext()) {
			 
		        Map.Entry<Object,Object> pairs = (Map.Entry<Object,Object>)itcdMap.next();		
		 
		 Iterator<?> itcdValGen = cdValGen.entrySet().iterator();		
		 while (itcdValGen.hasNext()) {
			
		        Map.Entry<Object,Object> pairs1 = (Map.Entry<Object,Object>)itcdValGen.next();
		        int count = ((ArrayList<Object>)pairs1.getValue()).size();
		        str = new String[count];		        
		        if ((pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:"))).equals(pairs1.getKey())){		        	
		        	for(int j=0;j<count;j++)
		        	str[j]=	((ArrayList)pairs1.getValue()).get(j).toString();	
		        	CodesetInsertType cdSetInsert = templateExport.addNewCodesetInsert();
		        	if (pairs.getKey() != null){
						cdSetInsert.setCodesetNm(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:")));
		        	    cdSetInsert.setValueSetNm(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":VSN:")+5,pairs.getKey().toString().indexOf(":CT:")));
		        	    cdSetInsert.setType(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":CT:")+4));
		        	}
					    if (pairs.getValue() != null)
						cdSetInsert.setCodesetInsert(pairs.getValue().toString());
					
					    Iterator<?> itcdGrpMetaMap = cdGrpMetaMap.entrySet().iterator();		
					     while (itcdGrpMetaMap.hasNext()) {
					        Map.Entry<Object,Object> pairs2 = (Map.Entry<Object,Object>)itcdGrpMetaMap.next();
					        if ((pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:"))).equals(pairs2.getKey())){
					        	 cdSetInsert.setCodesetGroupMetadataInsert(pairs2.getValue().toString());
					             break;
					        }
					    }
					     codeExists = true;
					     cdSetInsert.setCodeValuetInsertArray(str);
		        //	codeSetNmExists =  true;
		           break;
		        }
		    }
		 //if the codes doesnt exists in code_value_general....
		 if(!codeExists){
			 CodesetInsertType cdSetInsert = templateExport.addNewCodesetInsert();
			 if (pairs.getKey() != null){
					cdSetInsert.setCodesetNm(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:")));
	        	    cdSetInsert.setValueSetNm(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":VSN:")+5,pairs.getKey().toString().indexOf(":CT:")));
	        	    cdSetInsert.setType(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":CT:")+4));
	        	}
				    if (pairs.getValue() != null)
					cdSetInsert.setCodesetInsert(pairs.getValue().toString());
				
				    Iterator<?> itcdGrpMetaMap = cdGrpMetaMap.entrySet().iterator();		
				     while (itcdGrpMetaMap.hasNext()) {
				        Map.Entry<Object,Object> pairs2 = (Map.Entry<Object,Object>)itcdGrpMetaMap.next();
				        if ((pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:"))).equals(pairs2.getKey())){
				        	 cdSetInsert.setCodesetGroupMetadataInsert(pairs2.getValue().toString());
				             break;
				        }
				    }
			 
			 
		 }
		 codeExists=false;
		 /* if(codeSetNmExists){
			        if (pairs.getKey() != null)
					cdSetInsert.setCodesetNm(pairs.getKey().toString());
				    if (pairs.getValue() != null)
					cdSetInsert.setCodesetInsert(pairs.getValue().toString());
				
				    Iterator<?> itcdGrpMetaMap = cdGrpMetaMap.entrySet().iterator();		
				     while (itcdGrpMetaMap.hasNext()) {
				        Map.Entry<Object,Object> pairs1 = (Map.Entry<Object,Object>)itcdGrpMetaMap.next();
				        if (pairs.getKey().equals(pairs1.getKey())){
				        	 cdSetInsert.setCodesetGroupMetadataInsert(pairs1.getValue().toString());
				             break;
				        }
				    }
				     cdSetInsert.setCodeValuetInsertArray(str);
				     codeSetNmExists = false;
		       }*/
		 }
	}
	
/*public ArrayList<Object> readExportImportXML(String fileToMake,String Vocab)throws IOException,XmlException {
		
	ArrayList<Object> aList = new ArrayList<Object>();
	ArrayList<Object> vList = new ArrayList<Object>();
		
		 File inputXMLFile = new File(fileToMake);
		 TemplateExportType templateExport = TemplateExportType.Factory.parse(inputXMLFile);		
		
		ExportHeaderType header = templateExport.getExportHeader();		
		// add the page components to the xml	
		aList =  readWaQuestionToImport(templateExport,Vocab);		
		return aList;	//return null string if successful (or error msg if not)
	}//GeneratePageXMLFile
*/
public ArrayList<Object> readExportImportXML(File  inputXMLFile,String Vocab)throws IOException,XmlException {
	
	ArrayList<Object> aList = new ArrayList<Object>();
	ArrayList<Object> vList = new ArrayList<Object>();
		
		//File inputXMLFile = new File(filetoImport); 
		 TemplateExportType templateExport = TemplateExportType.Factory.parse(inputXMLFile);
		
		//ExportHeaderType header = templateExport.getExportHeader();		
		// add the page components to the xml	
		aList =  readWaQuestionToImport(templateExport,Vocab);		
		return aList;	//return null string if successful (or error msg if not)
	}

private ArrayList<Object> readWaQuestionToImport(TemplateExportType  templateExport, String readVocab)
{
	//Add a new UI Metadata entry to the XML
	ArrayList<Object> aList = new ArrayList<Object>();
	Map<Object, Object> aMap = new HashMap<Object, Object>();
	Map<Object,Object> uiMap = new HashMap<Object,Object>();
	Map<Object,Object> nndMap = new HashMap<Object,Object>();
	Map<Object,Object> rdbMap = new HashMap<Object,Object>();
	Map<Object,Object> cdGrpMetaMap = new HashMap<Object,Object>();
	Map<Object,Object> cdMap = new HashMap<Object,Object>();
	Map<Object,Object> cdValGen = new HashMap<Object,Object>();
	Map<Object, Object> qMap = new HashMap<Object, Object>();
	Map<Object, Object> ruleMap = new HashMap<Object, Object>();
	
	ExportHeaderType header = templateExport.getExportHeader();
	String exportedFromState = header.getExportedFromState(); //Source Name
	
	MetaInsertType[] newMetaInsert = null;
	QuesInsertType[] newQuesInsert = null;
	MetaInsertTypeArray[] newMetaInsertArray = null;
	TemplateInsertType templateInsert = templateExport.getTemplateInsert();
	CodesetInsertType[] cdSetInsert = templateExport.getCodesetInsertArray();	
	if(readVocab == null || readVocab.equals("")){
	if (templateInsert != null){		
			String templateName=templateInsert.getTemplateName();
			String insertSql=templateInsert.getInsertStatement();
			if (exportedFromState !=null && !exportedFromState.isEmpty())
				insertSql = insertSql.replace("ReplaceSrcName", "'" + exportedFromState + "'");
			aMap.put(templateName, insertSql);
	}
	newQuesInsert = templateExport.getQuestionInsertArray();
	if (newQuesInsert != null){
		for(int i=0;i<newQuesInsert.length;i++){
			String QIdentifier=newQuesInsert[i].getQuestionIdentifier();
			String insertSql=newQuesInsert[i].getInsertStatement();
			if (exportedFromState !=null && !exportedFromState.isEmpty())
				insertSql = insertSql.replace("ReplaceSrcName", "'" + exportedFromState + "'");
			qMap.put(QIdentifier+":QT:"+ newQuesInsert[i].getType()+":QN:"+ newQuesInsert[i].getUniqueName(), insertSql);
		}
		
	}
	newMetaInsert = templateExport.getUIMetadataInsertArray();
	if (newMetaInsert != null){
		for(int i=0;i<newMetaInsert.length;i++){
			String QIdentifier=newMetaInsert[i].getQuestionIdentifier();
			String insertSql=newMetaInsert[i].getInsertStatement();
			uiMap.put(QIdentifier, insertSql);
		}
		
	}
	newMetaInsertArray = templateExport.getNNDInsertArray();
	if (newMetaInsert != null){
		for(int i=0;i<newMetaInsertArray.length;i++){
			String QIdentifier=newMetaInsertArray[i].getQuestionIdentifier();
			String insertSql[]=newMetaInsertArray[i].getInsertStatementArray();
			nndMap.put(QIdentifier, insertSql);
			
		}
	}
	newMetaInsertArray = templateExport.getRDBInsertArray();
	if (newMetaInsert != null){
		for(int i=0;i<newMetaInsertArray.length;i++){
			String QIdentifier=newMetaInsertArray[i].getQuestionIdentifier();
			String insertSql[]=newMetaInsertArray[i].getInsertStatementArray();
			rdbMap.put(QIdentifier, insertSql);
			
		}
		
	}
	
	String[] ruleInsert = templateExport.getRuleInsertArray();
	if (ruleInsert != null){		
		ruleMap.put("TemplateUid",ruleInsert);
	}
		
	
	aList.add(qMap);
	aList.add(aMap);
	aList.add(uiMap);
	aList.add(nndMap);
	aList.add(rdbMap);
	aList.add(ruleMap);
	
	}else if(readVocab != null && readVocab.equals("Vocab")){
	
	for(int i=0;i<cdSetInsert.length;i++){
		String CdSetNm= cdSetInsert[i].getCodesetNm();
		cdMap.put(CdSetNm+":VSN:"+ cdSetInsert[i].getValueSetNm()+":CT:"+cdSetInsert[i].getType(), cdSetInsert[i].getCodesetInsert());
		cdGrpMetaMap.put(CdSetNm+":VSN:"+ cdSetInsert[i].getValueSetNm()+":CT:"+cdSetInsert[i].getType(), cdSetInsert[i].getCodesetGroupMetadataInsert());
		cdValGen.put(CdSetNm+":VSN:"+ cdSetInsert[i].getValueSetNm()+":CT:"+cdSetInsert[i].getType(), cdSetInsert[i].getCodeValuetInsertArray());
		
	}
	aList.add(cdMap);
	aList.add(cdGrpMetaMap);
	aList.add(cdValGen);
	}
	
	return aList;
		

}

public static String convertforXML(String aText){
    final StringBuilder result = new StringBuilder();
    final StringCharacterIterator iterator = new StringCharacterIterator(aText);
    char character =  iterator.current();
    while (character != CharacterIterator.DONE ){
      if (character == '<') {
        result.append("&lt;");
      }
      else if (character == '>') {
        result.append("&gt;");
      }
      else if (character == '\"') {
        result.append("&quot;");
      }
      else if (character == '\'') {
        result.append("&#039;");
      }
      else if (character == '&') {
         result.append("&amp;");
      }
      else {
        //the char is not a special one
        //add it to the result as is
        result.append(character);
      }
      character = iterator.next();
    }
    return result.toString();
  }


	


	
}
