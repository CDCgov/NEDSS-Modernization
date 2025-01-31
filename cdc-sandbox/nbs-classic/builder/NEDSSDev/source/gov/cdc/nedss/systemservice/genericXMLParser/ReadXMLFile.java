package gov.cdc.nedss.systemservice.genericXMLParser;

import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class ReadXMLFile {

	static String value ="";
	static boolean found = false;
	static final LogUtils logger = new LogUtils(ReadXMLFile.class.getName());
	static final String CODED_SEPARATOR="\\^";
	static final String TAG_SEPARATOR="/";
	static final String TAG_SEPARATOR_AUX="-AUX-";
	static final String TAG_EQUAL="=";
	static final String ATTRIBUTE_TEXT="text";
	static final String ATTRIBUTE_SEPARATOR="@";
	static final String SIBLING_SEPARATOR="*";
	static final String PARENT_SEPARATOR="..";
	static final HashMap<String,HashMap<String, String>> elementAttributeMaps = new HashMap<String,HashMap<String, String>>();
	
	/**
	 * convertPathInArray: this method converts the tagsPath in a String[]
	 * @param tagsPath
	 * @return
	 */
	private String[] convertPathInArray(String tagsPath){
		
		String[] locations;
		tagsPath=tagsPath.replace("(\"@\")", "(AT)");
		if(tagsPath.contains(ATTRIBUTE_SEPARATOR)){//If contains @, change the / symbol after @ by an auxiliar symbol in order to not confusing the split method
			String tagsPathAux=tagsPath.substring(tagsPath.lastIndexOf(ATTRIBUTE_SEPARATOR)).replace(TAG_SEPARATOR,TAG_SEPARATOR_AUX);
			tagsPathAux=tagsPath.substring(0,tagsPath.lastIndexOf(ATTRIBUTE_SEPARATOR))+tagsPathAux;
			tagsPathAux=tagsPathAux.replace("(AT)","(\"@\")");
			locations=tagsPathAux.split(TAG_SEPARATOR);
		}
		else
			locations = tagsPath.split(TAG_SEPARATOR);
		
		return locations;
	}
	
	/**
	 * findValue: this method returns the value in the document for the path tagsPath
	 * @param xmlLocation
	 * @param tagsPath
	 * @return
	 */
	public String findValue (Document doc, String tagsPath, boolean isStoreNullasNAInd, boolean multiValueInd){
		
		ArrayList<Object> nodes= new ArrayList<Object>();
		
		try{
			value="";
			found=false;
			
			String[] locations = convertPathInArray(tagsPath);//convert the path in String[]
			
			//Nodes with name locations[0]
			NodeList nList = doc.getElementsByTagName(locations[0]);
			
			//for (int i = 0; nList!=null && i < nList.getLength(); i++){
			if(nList!=null && nList.getLength()>0){//the root is only 1 element
	            Node nNode = nList.item(0);
	            Element eElement = (Element) nNode;
	            
	            findNodes(doc, eElement, locations, 1, nodes);//nodes are all the nodes that match the path (without the attribute yet)
	            value="";
	            String lastTag = locations[locations.length-1];
	            String nodeName="";
	           
	            for(int i=0; nodes!=null && i<nodes.size(); i++){
	            	Element node = (Element)nodes.get(i);
	           
	            	if(!isStoreNullasNAInd)
	            		ignoreBlankAttributes(node);//ND-22533
	            	
	            	if(node.getNodeName().equals(nodeName) && (value.length()>0 || multiValueInd)){//ND-22917: Fatima. If we leave it as && instead of || we will see this issue: MinniesotaXFnullMinnieXF. && was changed as part of SVN Revision #11555
	            		value=value+"|";
	            	}

	            	storeValueFromNode(lastTag,isStoreNullasNAInd, node);
	            	nodeName = node.getNodeName();
	            }
	            
	       }

		} catch (Exception e) {
			logger.error("Error in file ReadXMLFile, method findValue");
			e.printStackTrace();
		}
		
		return value;
		
		
	}
	
	/**
	 * getTagWithoutAttributes: this method gets the tags name from the nodeName without the attributes (anything after @)
	 * @param nodeName
	 * @return
	 */
	private static String getTagWithoutAttributes(String nodeName){
		
		if(nodeName.contains(ATTRIBUTE_SEPARATOR))
			nodeName=nodeName.substring(0, nodeName.indexOf(ATTRIBUTE_SEPARATOR));
		
		return nodeName;
		
	}
	
	/**
	 * findNodes: this method stores in nodes all the nodes that match the tagsPath.
	 * @param eElement
	 * @param tagsPath
	 * @param index
	 * @param nodes
	 */
	
	private static void findNodes(Document doc,Element eElement, String[] tagsPath, int index, ArrayList<Object> nodes){
		
		if(index<=tagsPath.length-1){
			String nodeName = tagsPath[index];

			if(index==tagsPath.length-1)//if it's the last tag
				nodeName=getTagWithoutAttributes(nodeName);
	
			int length = getChildrenByTagNameOneLevel(doc, eElement, nodeName).size();//returns 1 level
	
			if(length!=0)
				for(int i=0; i<length; i++){//if length>1, read the corresponding
					
					if(isLastTag(nodeName, tagsPath)){//If it's the last element in our path
						Node node = getChildrenByTagNameOneLevel(doc, eElement, nodeName).get(i);
						if(!existingNode(nodes,node))
							nodes.add(getChildrenByTagNameOneLevel(doc, eElement, nodeName).get(i));
					}else{
						Element nElement = getChildrenByTagNameOneLevel(doc, eElement, nodeName).get(i);
						findNodes(doc, nElement, tagsPath, index+1, nodes);
					}
				}
		}
	}
	
	
	private static boolean existingNode(ArrayList<Object> nodes, Node node){
		
		boolean existing = false;
		
		if(nodes!=null)
		for(int i=0; i<nodes.size() && !existing; i++){
			if(nodes.get(i)==node)
				existing=true;
		}
		
		return existing;	
				
	}
	
	/**
	 * ignoreBlankAttributes: this method was created to provide a solution to the user story ND-22533.
	 * if any of the attributes is blank, like displayName = "", it will be ignored (removed) so it won't be included in the
	 * payload column from nbs_interface table. That way, we will avoid causing any error from PHCRImporter process.
	 * @param node
	 */
	
	

	public static void ignoreBlankAttributes(Element node){
		
		int numberOfAttributes = 0;
		if(node!=null)
			numberOfAttributes = node.getAttributes().getLength();
		
		
				
    	if(numberOfAttributes>0)
    		for(int j=0; j<numberOfAttributes; j++){
    			
    			if(node.getAttributes().item(j)!=null){
	    			String nodeValue = node.getAttributes().item(j).getNodeValue();
	    			if(nodeValue==null || nodeValue.isEmpty()){//Empty Attribute
	    				String nodeNameToRemove = node.getAttributes().item(j).getNodeName();
	    				node.getAttributes().removeNamedItem(nodeNameToRemove);
	    			
	    			}
    			}
    		}
    
    	
	}
	
	
	
	/**
	 * ND-23590: this method will check if the node is one of the special ones, and if so, it will check if all the required
	 * attributes have a value, and if not, it will set the correct value. In addition to that, it will insert the attribute and/or
	 * the value (xsi:type="CD") for the element value
	 * @param node
	 * @return
	 */
	
	public static void validateSpecialElementAttribute(Document doc, Node node){

	//	boolean isSpecialElementNode = false;
		NamedNodeMap attributes = node.getAttributes();
		String elementNodeName = node.getNodeName();


		//ND-23590: we need to make sure if it is one of the special elements/attributes that we need to replace with a
		//specific value or not
		
		Map<String, String> elementHashMap = elementAttributeMaps.get(elementNodeName);
		if(elementHashMap!=null){//It's one of the special Elements
			//isSpecialElementNode = true;
			
			Iterator it = elementHashMap.entrySet().iterator();
		    while (it.hasNext()) {
		
				Map.Entry pair = (Map.Entry)it.next();
				String attributeName = (String)pair.getKey();//The name of the attribute
				String attributeValue = (String)pair.getValue();//The value of the attribute that it should be in case is null or empty
				

				//In order to implement ND-23591, we need to use the following lines for <value xsi_type = "CD">
				//Fatima: if, in future, we want to allow the system to ADD any missing special attribute (noy onlt the value),
				//we just need to remove "&& elementNodeName.equalsIgnoreCase("value")" from the following IF
				if(attributes.getNamedItem(attributeName)==null && elementNodeName!=null
					&& elementNodeName.equalsIgnoreCase("value")){//if we cannot find it in the XML, we need to add it

			        Node newNode = doc.createAttribute("xsi:type");
			        newNode.setNodeValue(attributeValue);
					attributes.setNamedItem(newNode);

				}else
				if(attributes!=null && attributes.getNamedItem(attributeName)!=null && (attributes.getNamedItem(attributeName).getNodeValue()==null || attributes.getNamedItem(attributeName).getNodeValue().isEmpty())){//The attribute exists but is empty
					//We need to add the value
						attributes.getNamedItem(attributeName).setNodeValue(attributeValue);
					}//else the value exists, and we don't need to modify it.
		
			}
		}
		//return isSpecialElementNode;
	}
	
	/**
	 * initializeElementAttributeValues: this methods initialize the hashmap that contains the values that blank attributes should be replaced by for the specific elements.
	 * @param elementAttributeMaps
	 */

	public static void initializeElementAttributeValues (){
		
		if(elementAttributeMaps==null || elementAttributeMaps.size()==0){//First time initializing
			
		//ND-23590
		HashMap<String, String> hashMapAct = new HashMap<String, String>();
		hashMapAct.put("classCode", "ACT");
		hashMapAct.put("moodCode", "EVN");
		elementAttributeMaps.put("act", hashMapAct);
		
		
		HashMap<String, String> hashMapDocument = new HashMap<String, String>();
		hashMapDocument.put("classCode", "DOC");
		hashMapDocument.put("moodCode", "EVN");
		elementAttributeMaps.put("document", hashMapDocument);
		
		
		HashMap<String, String> hashMapEncounter = new HashMap<String, String>();
		hashMapEncounter.put("classCode", "ENC");
		hashMapEncounter.put("moodCode", "EVN");
		elementAttributeMaps.put("encounter", hashMapEncounter);
		
		
		HashMap<String, String> hashMapObservation = new HashMap<String, String>();
		hashMapObservation.put("classCode", "OBS");
		hashMapObservation.put("moodCode", "EVN");
		elementAttributeMaps.put("observation", hashMapObservation);
		
		
		HashMap<String, String> hashMapOrganizer = new HashMap<String, String>();
		hashMapOrganizer.put("classCode", "BATTERY");
		hashMapOrganizer.put("moodCode", "EVN");
		elementAttributeMaps.put("organizer", hashMapOrganizer);
		
		
		HashMap<String, String> hashMapManufacturer = new HashMap<String, String>();
		hashMapManufacturer.put("classCode", "MANU");
		hashMapManufacturer.put("moodCode", "EVN");
		elementAttributeMaps.put("manufacturedProduct", hashMapManufacturer);
		
		HashMap<String, String> hashMapSubstance = new HashMap<String, String>();
		hashMapSubstance.put("classCode", "SBADM");
		hashMapSubstance.put("moodCode", "EVN");
		elementAttributeMaps.put("substanceAdministration", hashMapSubstance);
		
		HashMap<String, String> hashMapParticipantRole = new HashMap<String, String>();
		hashMapParticipantRole.put("typeCode", "LOC");
		elementAttributeMaps.put("participantRole", hashMapParticipantRole);

		HashMap<String, String> hashMapParticipant = new HashMap<String, String>();
		hashMapParticipant.put("typeCode", "LOC");
		elementAttributeMaps.put("participant", hashMapParticipant);
		
		HashMap<String, String> hashMapEntryRelationship = new HashMap<String, String>();
		hashMapEntryRelationship.put("typeCode", "COMP");
		elementAttributeMaps.put("entryRelationship", hashMapEntryRelationship);
		
		HashMap<String, String> hashMapEntry = new HashMap<String, String>();
		hashMapEntry.put("typeCode", "COMP");
		elementAttributeMaps.put("entry", hashMapEntry);
		
		//ND-23591
		HashMap<String, String> hashMapValue = new HashMap<String, String>();
		hashMapValue.put("xsi:type", "CD");
		elementAttributeMaps.put("value", hashMapValue);
		
		}
	}
	
	
	public static void ignoreBlankAttributes(Document doc, Node node){
			
			int numberOfAttributes = 0;	
			
			initializeElementAttributeValues();//This will be executed just once, to initialize the HashMap
				
			
			if(node!=null && node.getAttributes()!=null)
				numberOfAttributes = node.getAttributes().getLength();
			

			validateSpecialElementAttribute(doc, node);
			
			//After handling empty or missing values for the special attributes, we will continue removing any empty attribute.

	    	if(numberOfAttributes>0)
	    		for(int j=0; j<numberOfAttributes; j++){
	    			
	    			if(node.getAttributes().item(j)!=null){
		    			String nodeValue = node.getAttributes().item(j).getNodeValue();
		    			if(nodeValue==null || nodeValue.isEmpty()){//Empty Attribute
		    				String nodeNameToRemove = node.getAttributes().item(j).getNodeName();
		    				node.getAttributes().removeNamedItem(nodeNameToRemove);
		    			
		    			}
	    			}
	    		}
	    	
	    	//Process child nodes
	    	if(node != null && node.getChildNodes()!=null){
		    	int numberChildNodes = node.getChildNodes().getLength();
		    
		    	if(numberChildNodes>0)
		    		for(int k=0; k<numberChildNodes; k++){
		    			Node nodeChild = node.getChildNodes().item(k);
		    			ignoreBlankAttributes(doc, nodeChild);
		    		
		    		}
	    	
	    	}
	    	
		}
		


	/**
	 * isLastTag: this methods returns true if the nodeName is the last one in the tagsPath array regardless of the attributes
	 * @param nodeName
	 * @param tagsPath
	 * @return
	 */
	
	private static boolean isLastTag(String nodeName, String[] tagsPath){
		
		boolean lastTag = false;
		
		if(getTagWithoutAttributes(tagsPath[tagsPath.length-1]).equals(nodeName))
			lastTag = true;
		
		return lastTag;
	}

	/**
	 * storeValueFromNode: 
	 * @param lastTagName
	 * @param eElement
	 */
	private static void storeValueFromNode(String lastTagName, boolean isStoreNullasNAInd,  Element eElement){
		
		String[] tagNames  = lastTagName.split(CODED_SEPARATOR);
		String seperator = null;
		if(tagNames!=null && tagNames.length>1)
			seperator="^";
		
		for(String tagName:tagNames){
		
		if(tagName.contains(ATTRIBUTE_SEPARATOR)){//if it has an attribute from where we need to read
			ArrayList<String> nodeAttribute = new ArrayList<String>();
			
			nodeAttribute.add(tagName.substring(0,tagName.indexOf(ATTRIBUTE_SEPARATOR)));
			nodeAttribute.add(tagName.substring(tagName.indexOf(ATTRIBUTE_SEPARATOR)+1));
			String name=nodeAttribute.get(0);
			String attribute=nodeAttribute.get(1);
			
			if(attribute.contains(TAG_SEPARATOR_AUX)){//like given@qualifier=IN/text
				String[] attributeValue = attribute.split(TAG_SEPARATOR_AUX);
				
				String valueAttribute =attributeValue[0].split(TAG_EQUAL)[1];
				attribute =attributeValue[0].split(TAG_EQUAL)[0];
				String fieldToRead = attributeValue[1];
	
					if(getAttributeValueFromElement(eElement, name, attribute).equals(valueAttribute)){
						if(fieldToRead.trim().equalsIgnoreCase(ATTRIBUTE_TEXT)){
							String value2 = getValueFromAttributeText(eElement);
							storeValue(value2,seperator);
							
						}
						else
							if(fieldToRead!=null && !fieldToRead.isEmpty() && fieldToRead.contains("contains")){
								
							
								String attribute2 = fieldToRead.substring(0,fieldToRead.indexOf("["));
								String valueFromAttribute = fieldToRead.substring(fieldToRead.indexOf("(\"")+2,fieldToRead.indexOf("\")"));
								if((getAttributeValueFromElement(eElement, name, attribute2).contains(valueFromAttribute) && fieldToRead.contains("!contains")) ||
									(!getAttributeValueFromElement(eElement, name, attribute2).contains(valueFromAttribute) && (!fieldToRead.contains("!contains")))){
									String value2="";
									storeValue(value2,seperator);
								}
								else{
									String value2 = getAttributeValueFromElement(eElement, name, attribute2);
									if(isStoreNullasNAInd && (value2==null || value2.equals("")|| value2.equals("null")))	{
										value2 ="NA";
									//	ignoreBlankAttributes(eElement);	
									}
									storeValue(value2,seperator);
									
								}
							}
							
						else{
							String value2 = getAttributeValueFromElement(eElement, name, fieldToRead);
							if(isStoreNullasNAInd && (value2==null || value2.equals("")|| value2.equals("null"))){
								value2 ="NA";
								//ignoreBlankAttributes(eElement);	
							}
							storeValue(value2,seperator);
						}
					
					}			
			}else{ //Reads the information from the attribute
				if(attribute.contains("contains")){//For Email, URL

					String containsValue = attribute.substring(attribute.indexOf("\"")+1,attribute.indexOf(")")-1);
					String value2="";
					attribute=attribute.substring(0,attribute.indexOf("["));
						value2 = getAttributeValueFromElement(eElement, name, attribute);
						if(attribute.contains("!contains") && !value2.contains(containsValue) ||//delete in case we are not using telecom@use=WP/value[!contains("@")], 
						(!attribute.contains("!contains") && value2.contains(containsValue)))
							storeValue(value2,seperator);
						else
							value2="";
				}else{
					if(attribute.contains("!")){//like given@!qualifier, meaning, it doesn't contain the attribute qualifier
						attribute=attribute.replace("!","");
						String value2 = getValueFromElementWithoutAttribute(eElement, name, attribute);
						storeValue(value2,seperator);
						
					}else{
						String value2 = getAttributeValueFromElement(eElement, name, attribute);
						if(isStoreNullasNAInd && (value2==null || value2.equals("")|| value2.equals("null"))){
							value2 ="NA";
							
							//ignoreBlankAttributes(eElement);
						}
						storeValue(value2,seperator);
					}
				}
			}
		}else{//reads from the value
			String value2=getValueFromAttributeText(eElement);
			storeValue(value2,seperator);
		}
		}

	}
	
	/**
	 * getChildrenByTagNameOneLevel: this method returns a list with all the children nodes (one level) with that name
	 * @param parent
	 * @param name
	 * @return
	 */
	private static List<Element> getChildrenByTagNameOneLevel(Document doc, Element parent, String name) {
		String attribute ="";
		String[] nameAttribute;
		String value = "";
		String[] attributeValue;
		
		if(name.contains(ATTRIBUTE_SEPARATOR)){//like observation@negationInd=true in observation@negationInd/templateId@root
			nameAttribute=name.split(ATTRIBUTE_SEPARATOR);
			name=nameAttribute[0];
			attribute=nameAttribute[1];		
			attributeValue=attribute.split("=");
			attribute=attributeValue[0];
			value=attributeValue[1];
		}
	    List<Element> nodeList = new ArrayList<Element>();
	    
	    if(name.contains(SIBLING_SEPARATOR)){//It's a sibling
	    	name=name.replace(SIBLING_SEPARATOR,"");
	    	for (Node sibling = parent.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
			      if (sibling.getNodeType() == Node.ELEMENT_NODE && 
			          name.equals(sibling.getNodeName()) && (value.isEmpty() || getAttributeValueFromElement(sibling, name, attribute).equalsIgnoreCase(value))) {
			    	  
			        nodeList.add((Element) sibling);
			      }
			    }
	    
	    }else
	    	if(name.contains(PARENT_SEPARATOR)){//It's a sibling
		    	nodeList.add((Element)parent.getParentNode());
	    	}
	    	else{
		    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
		      if (child.getNodeType() == Node.ELEMENT_NODE && 
		          name.equals(child.getNodeName()) && (value.isEmpty() || getAttributeValueFromElement(child, name, attribute).equalsIgnoreCase(value))) {
		    	  
		    	  ignoreBlankAttributes(doc,child);//remove any attribute like codeSystemName = "" to avoid errors in Rhapsody
		      
		    	  nodeList.add((Element) child);
		      }
		    }
	    }

	    return nodeList;
	  }
	
	
	/**
	 * getAttribute: get the value of the attribute from the eElement
	 * @param index
	 * @param eElement
	 * @param name
	 * @param attribute
	 * @return
	 */
	private static String getAttributeValueFromElement(Element eElement,
			String name, String attribute) {
		String value = "";

		if (attribute.equalsIgnoreCase("text"))
			value = getValueFromAttributeText(eElement);
		else if (attribute.equalsIgnoreCase("node")) {

			Document document = eElement.getOwnerDocument();
			DOMImplementationLS domImplLS = (DOMImplementationLS) document
					.getImplementation();
			LSSerializer serializer = domImplLS.createLSSerializer();
			value = serializer.writeToString(eElement);
			//trim out '<?xml version="1.0" encoding="UTF-16"?>'
					
					
			if (value != null && value.length() > 39)
				value = value
						.substring(39, value.length())
						.replaceAll("sdtc:", "sdt:")
						.replaceAll("<table>",
								"<br/><table  styleCode=\"subSect2\">");
		} else {
			if (eElement != null
					&& eElement.getAttributes().getNamedItem(attribute) != null)
				value = eElement.getAttributes().getNamedItem(attribute)
						.getFirstChild().getNodeValue();
			if (attribute.contains("!contains") && attribute.contains(value))
				value = "";
		}
		return value;
	}
	
	private static String getAttributeValueFromElement(Node eElement, String name, String attribute){
		String value = "";

		if(eElement!=null && eElement.getAttributes().getNamedItem(attribute)!=null)
			value=eElement.getAttributes().getNamedItem(attribute).getFirstChild().getNodeValue();
		
		return value;
	}
	
	/**
	 * getValueFromElementWithoutAttribute: this method returns the value of the element that doesn't contain the attribute attribute
	 * @param eElement
	 * @param name
	 * @param attribute
	 * @return
	 */
	
	private static String getValueFromElementWithoutAttribute(Element eElement, String name, String attribute){
		String value = "";

		if(eElement!=null && eElement.getAttributes().getNamedItem(attribute)==null)
			value=getValueFromAttributeText(eElement);
			//value=eElement.getAttributes().getNamedItem(attribute).getFirstChild().getNodeValue();
		
		return value;
	}
	
	/**
	 * getValueFromAttributeText: get the value of the node (the one between tags <> </>)
	 * @param index
	 * @param eElement
	 * @param name
	 * @return
	 */
	private static String getValueFromAttributeText(Element eElement){
		String value = "";
		
		NodeList nodes = eElement.getChildNodes();
		
		for(int i=0; i<nodes.getLength(); i++){
		Node node = nodes.item(i);
			if(node!=null && node.getNodeValue()!=null){
				value+=node.getNodeValue();
				if(i<nodes.getLength()-1)
					value+=" ";
			}
		}
		return value;
	}
	
	private static void storeValue(String value2, String Separator){
		
		if(value2!=null && !value2.equalsIgnoreCase("null") && !value2.isEmpty()){//ND-22916: Fatima: !value2.equalsIgnoreCase("null") fixes the issue: A71^null^ICD-10^2.16.840.1.113883.6.90
			if(value.isEmpty())
				value=value2;
			else if(value2!=null && value.endsWith("|")){
				value+=value2;
			}
			else if(value2!=null)
				value+=Separator+value2;
		}
		
	}
	
	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

}
