package gov.cdc.nedss.pagemanagement.wa.xml.util;

import gov.cdc.nedss.util.LogUtils;

import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


 
public class FixedRuleEventBuilder {
	static final LogUtils logger = new LogUtils(FixedRuleEventBuilder.class
			.getName());

	public static final String FIXED_RULE_XML_FILE = System.getProperty("nbs.dir")
			+ System.getProperty("file.separator") + "Properties"
			+ System.getProperty("file.separator") + "NBSFixedRule.xml";

	public static HashMap<String,FixedRuleEvent> fixedRuleMap; 
	
	
	/**
	 * Constructor reads the NBSFixedRule.xml into a hashmap
	 */
	public FixedRuleEventBuilder(String busObjType) {

		fixedRuleMap = new HashMap<String,FixedRuleEvent>();
		try {
			initializeFixedRuleEventMap(busObjType);
		} catch (Exception ex) {
			logger.warn(
					"WARNING: unable to initialize fixed rule event mapping map from " + FIXED_RULE_XML_FILE,
					ex);
		}
	}	
	
	private void initializeFixedRuleEventMap(String busObjType) throws Exception{
		
		//Initialize the fixed rule event map
		Document doc;
		//fixedRuleMap = new HashMap<String,FixedRuleEvent>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		logger.debug("Reading and parsing fixed rule event file.." + FIXED_RULE_XML_FILE);
		doc = builder.parse(FIXED_RULE_XML_FILE);
		logger.debug("..done parsing!");

		//Get the root
		Element root = doc.getDocumentElement();	
		//Get form child nodes - currently only one 
		NodeList formCodeNodes = root.getChildNodes();
		String busObjStr = "";
		for (int i = 0; i < formCodeNodes.getLength(); i++) {
			Node formCodes = formCodeNodes.item(i);
			if(formCodes!=null && formCodes.getAttributes()!=null){
				Node busObjNode = formCodes.getAttributes().getNamedItem("type");
				if(busObjNode!=null)
					busObjStr = busObjNode.getNodeValue();
			}
			//load fixedRuleMap for specific Business Object Type only.
			if(busObjStr!=null && busObjStr.equals(busObjType)){
				//Check if this node has any child; go to the next node if no child
				if (formCodes.getFirstChild() == null) 
						continue;
				NodeList questionIdList = formCodes.getChildNodes();
				//Get "QuestionId" nodes
				for (int j = 0; j < questionIdList.getLength(); j++) {
					Node questionIdNode = questionIdList.item(j);
					if (questionIdNode.getFirstChild() == null) 
						continue;
					if (questionIdNode.getNodeType() == questionIdNode.ELEMENT_NODE) {
						Element questionIdElement = (Element) questionIdNode;
						if (questionIdElement.getAttribute("questionId") != null) {
							String quesId = questionIdElement.getAttribute("questionId");
							NodeList fixedRuleList = questionIdNode.getChildNodes();
							for (int k = 0; k < fixedRuleList.getLength(); ++k) {
								Node fixedRuleNode = fixedRuleList.item(k);
								if (fixedRuleNode.getNodeType() == fixedRuleNode.ELEMENT_NODE) {
									Element fixedRuleElement = (Element) fixedRuleNode;
									FixedRuleEvent fixedRuleEvent = new FixedRuleEvent();
									fixedRuleEvent.setQuestionId(quesId);
									if (fixedRuleElement.getAttribute("onLoad") != null)
										fixedRuleEvent.setOnLoadEvent(fixedRuleElement.getAttribute("onLoad"));
									if (fixedRuleElement.getAttribute("onSubmit") != null)
										fixedRuleEvent.setOnSubmitEvent(fixedRuleElement.getAttribute("onSubmit"));
									if (fixedRuleElement.getAttribute("onSubmitErrMsg") != null)
										fixedRuleEvent.setOnSubmitErrorMsg(fixedRuleElement.getAttribute("onSubmitErrMsg"));							
									if (fixedRuleElement.getAttribute("onChange") != null)
										fixedRuleEvent.setOnChangeEvent(fixedRuleElement.getAttribute("onChange"));
									if (fixedRuleElement.getAttribute("onKeyUp") != null)
										fixedRuleEvent.setOnKeyUpEvent(fixedRuleElement.getAttribute("onKeyUp"));
									if (fixedRuleElement.getAttribute("onKeyDown") != null)
										fixedRuleEvent.setOnKeyDownEvent(fixedRuleElement.getAttribute("onKeyDown"));
									if (fixedRuleElement.getAttribute("onBlur") != null)
										fixedRuleEvent.setOnBlurEvent(fixedRuleElement.getAttribute("onBlur"));	
									fixedRuleMap.put(quesId,fixedRuleEvent);
								}
							} //for k
						}// quesId not null
					}
				}//for questionIdList
			}
		} //for i
		
		
		
	} // initializeFixedRuleEventMap()
	
	/*
	 * Get any onload functions that are present (in the questionMap)
	 */
	
	public String getFixedRuleOnloadFunctions(HashMap<String, PageElementType> questionMap) {
		String onLoadStr = "";
		if (fixedRuleMap == null)
			return onLoadStr;
		Iterator<String> fixedRuleIter = fixedRuleMap.keySet().iterator();
		while (fixedRuleIter.hasNext()) {
			String quesId = fixedRuleIter.next();
			//see if the question id is in our map for the form
			if (questionMap.get((Object)quesId) != null) {
				FixedRuleEvent fixedRuleEvent = fixedRuleMap.get(quesId);
				if (fixedRuleEvent.getOnLoadEvent() != null && !fixedRuleEvent.getOnLoadEvent().isEmpty())
					onLoadStr = onLoadStr.concat(fixedRuleEvent.getOnLoadEvent() + ";");
			}
		}
		return onLoadStr;
	}

	/*
	 * Look for questions in the NBSFixedRule.xml file that are in the page and have onSubmit functions
	 * Add them to the page if present...
	 */
	public void getFixedRuleOnSubmitFunctions(HashMap<String, PageElementType> questionMap, PageType page) {
		if (fixedRuleMap == null)
			return;
		Iterator<String> fixedRuleIter = fixedRuleMap.keySet().iterator();
		while (fixedRuleIter.hasNext()) {
			String quesId = fixedRuleIter.next();
			//see if the question id is in our map for the form
			if (questionMap.get((Object)quesId) != null) {
				FixedRuleEvent fixedRuleEvent = fixedRuleMap.get(quesId);
				if (fixedRuleEvent.getOnSubmitEvent() != null && !fixedRuleEvent.getOnSubmitEvent().isEmpty()) {
					String onSubmitStr = fixedRuleEvent.getOnSubmitEvent();
					OnSubmitFunctionType onSub = page.addNewOnSubmitFunction();
					onSub.setFunctionName(onSubmitStr);
					String onSubmitErrorMsg = fixedRuleEvent.getOnSubmitErrorMsg();
					if (onSubmitErrorMsg != null && !onSubmitErrorMsg.isEmpty())
						onSub.setErrorMessage(onSubmitErrorMsg);
					else
						onSub.setErrorMessage("none");
				}
			}
		}
		
	}
    /*
     * Add any onBlur or onChange to the element in question
     * Note that not all element types are currently supported.
     */
	public void setFixedRuleDynamicEventFunctionsOnPage(
			HashMap<String, PageElementType> questionMap, PageType page) {
		
		if (fixedRuleMap == null)
			return;
		Iterator<String> fixedRuleIter = fixedRuleMap.keySet().iterator();
		while (fixedRuleIter.hasNext()) {
			String quesId = fixedRuleIter.next();
			//see if the question id is in our map for the form
			if (questionMap.get((Object)quesId) != null) {
				PageElementType element = null;
				element = questionMap.get(quesId);
				if (element == null){
					logger.warn("Marshalling Page Rules: The Rule source question id <" + quesId + "> not found in the map.");
					return;
				}
				FixedRuleEvent fixedRuleEvent = fixedRuleMap.get(quesId);
				if (fixedRuleEvent.getOnChangeEvent() != null && !fixedRuleEvent.getOnChangeEvent().isEmpty()) {
					String onChangeStr = fixedRuleEvent.getOnChangeEvent();

					//find out the type of question and add the onblur/onchange
					if (element.getCodedQuestion() != null) {
						String curOnChange = element.getCodedQuestion().getOnChange();
						if (curOnChange == null || curOnChange.isEmpty())
								element.getCodedQuestion().setOnChange(onChangeStr);
						else
								element.getCodedQuestion().setOnChange(curOnChange + ";" + onChangeStr);
					} else if (element.getDateQuestion() != null) {
						String curOnChange = element.getDateQuestion().getOnChange();
						if (curOnChange == null || curOnChange.isEmpty())
							element.getDateQuestion().setOnChange(onChangeStr);
						else
							element.getDateQuestion().setOnChange(curOnChange + ";" + onChangeStr);
					} else if (element.getParticipationQuestion() != null) {
						String curOnChange = element.getParticipationQuestion().getOnChange();
						if (curOnChange == null || curOnChange.isEmpty())
								element.getParticipationQuestion().setOnChange(onChangeStr);
						else
								element.getParticipationQuestion().setOnChange(curOnChange + ";" + onChangeStr);
					}
						
				} //on change
				if (fixedRuleEvent.getOnBlurEvent() != null && !fixedRuleEvent.getOnBlurEvent().isEmpty()) {
					String onBlurStr = fixedRuleEvent.getOnBlurEvent();
					if 	(element.getTextQuestion() != null) {
						String curOnBlur = element.getTextQuestion().getOnBlur();
						//STD has MinMaxDefault logic
						if (curOnBlur.contains("CheckFieldMinMax") && (onBlurStr.contains("CheckFieldMinMax")))
							curOnBlur = "";
						if (curOnBlur == null || curOnBlur.isEmpty())
							element.getTextQuestion().setOnBlur(onBlurStr);
						else
							element.getTextQuestion().setOnBlur(curOnBlur + ";" + onBlurStr);
					} else if (element.getDateQuestion() != null) {
						String curOnBlur = element.getDateQuestion().getOnBlur();
						if (curOnBlur == null || curOnBlur.isEmpty())
							element.getDateQuestion().setOnBlur(onBlurStr);
						else 
							element.getDateQuestion().setOnBlur(curOnBlur + ";" + onBlurStr);
					} else if (element.getNumericQuestion() != null) {
						String curOnBlur = element.getNumericQuestion().getOnBlur();
						if (curOnBlur == null || curOnBlur.isEmpty())
							element.getNumericQuestion().setOnBlur(onBlurStr);
						else
							element.getNumericQuestion().setOnBlur(curOnBlur + ";" + onBlurStr);
					} else if (element.getParticipationQuestion() != null) {
						String curOnBlur = element.getParticipationQuestion().getOnBlur();
						if (curOnBlur == null || curOnBlur.isEmpty())
								element.getParticipationQuestion().setOnBlur(onBlurStr);
						else
								element.getParticipationQuestion().setOnBlur(curOnBlur + ";" + onBlurStr);
					}
				} //on blur event
			} //ques on page
		} //iter has next
	} //setFixedRuleDynamicFunctionsOnPage
		
	
}
