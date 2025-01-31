package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.helper;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.RuleElementDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaRuleMetadataDT;
import gov.cdc.nedss.pagemanagement.util.RuleMetadataConstants;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class RuleParserUtil {
	 static final LogUtils logger = new LogUtils(RuleParserUtil.class.getName());

		/**
		 * Description: Generates the JavaScript date compare rule
		 * and stores it the metadataDT javaScript function field
		 * This is read and placed on the index page by DBM_Generate_Index_JSP.xsl.
		 * Date functions are processed on form submit.
		 * Note: The JavaScript Date can compare > or < but => and =< don't work
		 *   so we change the date to YYYYMMDD and do string compares
		 * @param WaRuleMetadataDT
		 */
	 public static WaRuleMetadataDT dateCompareJavaScript(WaRuleMetadataDT metadataDT) {

			Collection<RuleElementDT> ruleElementDTColl =metadataDT.getRuleElementMap().values();
			Iterator<RuleElementDT> emptyCheck = ruleElementDTColl.iterator();
			StringBuffer buffer=  new StringBuffer();
			StringBuffer firstSB=  new StringBuffer();
			StringBuffer secondSB=  new StringBuffer();
			logger.debug(ruleElementDTColl.size());
			String functionName= "ruleDComp"+metadataDT.getSourceQuestionIdentifierString()+metadataDT.getWaRuleMetadataUid();
			buffer.append("function "+ functionName +"() {\n");
			buffer.append("    var i = 0;\n    var errorElts = new Array(); \n    var errorMsgs = new Array(); \n");
			int i = 0;
			while(emptyCheck.hasNext()){
				RuleElementDT ruleElementDT = (RuleElementDT)emptyCheck.next();
				//return if source question ID is empty or not there
				if(i==0){
					firstSB.append("\n if ((getElementByIdOrByName(\""+ruleElementDT.getSourceQuestionIdentifier()+"\").value)==''){ \n return {elements : errorElts, labels : errorMsgs}; }");
					secondSB.append("\n var sourceStr =getElementByIdOrByName(\""+ruleElementDT.getSourceQuestionIdentifier()+"\").value;");
					secondSB.append("\n var srcDate = sourceStr.substring(6,10) + sourceStr.substring(0,2) + sourceStr.substring(3,5);");
					secondSB.append("\n var targetElt;\n var targetStr = ''; \n var targetDate = '';");
					i++;
				}


				Collection<String> coll = ruleElementDT.getTargetQuestionIdentifierColl();
				Iterator<String> iter = coll.iterator();
				String targetQuestionIdentifier="";
				while(iter.hasNext()){
					targetQuestionIdentifier= (String)iter.next();
				    //check for null just in case the target got deleted or is not visible except for edit
					secondSB.append("\n targetStr =getElementByIdOrByName(\""
							      +targetQuestionIdentifier.trim()
							      +"\") == null ? \"\" :getElementByIdOrByName(\""
							      +targetQuestionIdentifier.trim()
							      +"\").value;");
					secondSB.append("\n if (targetStr!=\"\") {");
					secondSB.append("\n    targetDate = targetStr.substring(6,10) + targetStr.substring(0,2) + targetStr.substring(3,5);");
					secondSB.append("\n if (!(srcDate ");
					secondSB.append(ruleElementDT.getComparator());
					secondSB.append(" targetDate)) {");
					secondSB.append("\n var srcDateEle=getElementByIdOrByName(\""+ruleElementDT.getSourceQuestionIdentifier()+"\");");
					secondSB.append("\n var targetDateEle=getElementByIdOrByName(\""+targetQuestionIdentifier.trim()+"\");");
					secondSB.append("\n var srca2str=buildErrorAnchorLink(srcDateEle," + "\"" + metadataDT.getLableMap().get(ruleElementDT.getSourceQuestionIdentifier()) + "\");");
					secondSB.append("\n var targeta2str=buildErrorAnchorLink(targetDateEle,\"" + metadataDT.getLableMap().get(targetQuestionIdentifier.trim()) + "\");");
					secondSB.append("\n    errorMsgs[i]=srca2str + \" must be " + ruleElementDT.getComparator() + " \" + targeta2str; ");
					secondSB.append("\n    colorElementLabelRed(srcDateEle); ");
					secondSB.append("\n    colorElementLabelRed(targetDateEle); \n");
					/*
					secondSB.append(metadataDT.getLableMap().get(ruleElementDT.getSourceQuestionIdentifier()));
					secondSB.append(" must be ").append(ruleElementDT.getComparator()).append(" ");
					secondSB.append(metadataDT.getLableMap().get(targetQuestionIdentifier));
					secondSB.append("\";\n");
					*/
					secondSB.append("errorElts[i++]=getElementByIdOrByName(\"" +targetQuestionIdentifier.trim()+"\"); \n");
					secondSB.append("}\n  }");
				}
		    }

			buffer.append(firstSB.toString());
			buffer.append(secondSB.toString());
			buffer.append("\n return {elements : errorElts, labels : errorMsgs}\n}");

			logger.debug(buffer.toString());
			metadataDT.setJavascriptFunction(buffer.toString());
			metadataDT.setJavascriptFunctionNm(functionName+"()");
			return metadataDT;
		}

	public static WaRuleMetadataDT enableDisableCompareJavaScript(WaRuleMetadataDT metadataDT, Map<String, Integer> questionComponentMap) {
			Collection<RuleElementDT> ruleElementDTColl = metadataDT.getRuleElementMap().values();
			Iterator<RuleElementDT> it = ruleElementDTColl.iterator();
			String ruleElementDTCollDescriptionString = metadataDT.getSourceValues();//new for business rule view
			List<String> ruleElementDTCollDescription = Arrays.asList(ruleElementDTCollDescriptionString.split(","));
			StringBuffer buffer=  new StringBuffer();
			logger.debug("Rule Element DT Collection has " + ruleElementDTColl.size() + " items ");
			String functionName="ruleEnDis"+metadataDT.getSourceQuestionIdentifierString()+metadataDT.getWaRuleMetadataUid();
			buffer.append("function "+functionName+"()\n{");
			String comparater="";
			
				while(it.hasNext()){
					RuleElementDT ruleElementDT = (RuleElementDT)it.next();	
					
					buffer.append("\n var foo = [];\n");
					buffer.append("$j('#").append(ruleElementDT.getSourceQuestionIdentifier()).append(" :selected').each(function(i, selected){");
					buffer.append("\n foo[i] = $j(selected).val();\n"); 
					buffer.append(" });\n");
		
					//These changes are for enable/disable elements on view. For now, it is not necessary but we can leave them here, because we are not adding this functionality
					//to the view.
					//buffer.append("if(foo=='' && ").append("$j('#").append(ruleElementDT.getSourceQuestionIdentifier()).append("').html()!=null){");//added for the business rule view
					//buffer.append("foo[0]=$j('#").append(ruleElementDT.getSourceQuestionIdentifier()).append("').html().replace(/^\\s+|\\s+$/g,'');}");//added for the business rule view
					
					
					
					if(ruleElementDT.getSourceQuestionIdentifier()!=null ){
						Collection<String> collSourceValue =ruleElementDT.getSourceValues();
						Iterator<String> iterSourceValue= collSourceValue.iterator();
						String[] str = new String[collSourceValue.size()];
						int ii =0;
						
						while (iterSourceValue.hasNext()){
							str[ii++] = iterSourceValue.next();
						}
						if (str.length==0 && (metadataDT.getSourceValues().equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE))
								|| str[0].equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE)) {
							buffer.append("if(foo.length>0 && foo[0] != '') {\n"); //anything selected 
						} else {						
							buffer.append("\n if(");
							for (int i = 0; i < str.length; i++) { 
								buffer.append("($j.inArray('").append(str[i]).append("',foo) > -1)");
								buffer.append(" || ($j.inArray('").append(ruleElementDTCollDescription.get(i)).append("'.replace(/^\\s+|\\s+$/g,''),foo) > -1)");//added for the business rule view
								if(str.length > (i+1)){
									buffer.append(" || ");
								}
							}
							buffer.append("){\n");
						}
						

	
						Collection coll = ruleElementDT.getTargetQuestionIdentifierColl();
						//For fixing issue where the rule_expresion doesn't contains the comparator = when the source value is Any Source value
						if(ruleElementDT.getComparator().equalsIgnoreCase("") && metadataDT.getSourceValues().equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE))
							ruleElementDT.setComparator("=");
							
						Iterator<String> iter = coll.iterator();
						String targetQuestionIdentifier="";
						//buffer.append("\n");
	
						if("Question".equalsIgnoreCase(metadataDT.getTargetType())){
							while(iter.hasNext()){
								
								targetQuestionIdentifier=iter.next().toString();
								if(targetQuestionIdentifier!=null){
									targetQuestionIdentifier=targetQuestionIdentifier.trim();
									int componentType = 0;
									if (questionComponentMap.containsKey(targetQuestionIdentifier))
									    componentType = questionComponentMap.get(targetQuestionIdentifier);
									if(metadataDT.getRuleCd().equalsIgnoreCase("Enable")){
										if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
											if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
												buffer.append("pgEnableParticipationElement('"+targetQuestionIdentifier+"');\n");
											else
												buffer.append("pgEnableElement('"+targetQuestionIdentifier+"');\n");
										}else{
											if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
												buffer.append("pgDisableParticipationElement('"+targetQuestionIdentifier+"');\n");
											else
												buffer.append("pgDisableElement('"+targetQuestionIdentifier+"');\n");	
										}
									}else{
										if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
											if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
												buffer.append("pgDisableParticipationElement('"+targetQuestionIdentifier+"');\n");
											else
												buffer.append("pgDisableElement('"+targetQuestionIdentifier+"');\n");
										}else{
											if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
												buffer.append("pgEnableParticipationElement('"+targetQuestionIdentifier+"');\n");
											else
												buffer.append("pgEnableElement('"+targetQuestionIdentifier+"');\n");
										}
								   } //else
								} //if
						   } //while
	
							buffer.append(" } else { \n");
							iter = coll.iterator();
		
							while(iter.hasNext()){
							int componentType = 0;
							targetQuestionIdentifier=iter.next().toString();
							if(targetQuestionIdentifier!=null){
								targetQuestionIdentifier=targetQuestionIdentifier.trim();
								if (questionComponentMap.containsKey(targetQuestionIdentifier))
								    componentType = questionComponentMap.get(targetQuestionIdentifier);
								if(metadataDT.getRuleCd().equalsIgnoreCase("Enable")){
									if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
										if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
											buffer.append("pgDisableParticipationElement('"+targetQuestionIdentifier+"');\n");
										else
											buffer.append("pgDisableElement('"+targetQuestionIdentifier+"');\n");
									}else{
										if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
											buffer.append("pgEnableParticipationElement('"+targetQuestionIdentifier+"');\n");
										else
											buffer.append("pgEnableElement('"+targetQuestionIdentifier+"');\n");
									}
								}else{
									if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
										if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
											buffer.append("pgEnableParticipationElement('"+targetQuestionIdentifier+"');\n");
										else
											buffer.append("pgEnableElement('"+targetQuestionIdentifier+"');\n");
									}else{
										if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
											buffer.append("pgDisableParticipationElement('"+targetQuestionIdentifier+"');\n");
										else
											buffer.append("pgDisableElement('"+targetQuestionIdentifier+"');\n");
									}
								}
							} //if
						   } //while
						   	buffer.append(" }");
					   	
					   	}else{
					   		while(iter.hasNext()){
								targetQuestionIdentifier=iter.next().toString();
								if(targetQuestionIdentifier!=null){									
									targetQuestionIdentifier=targetQuestionIdentifier.trim();
									int componentType = 0;
									if (questionComponentMap.containsKey(targetQuestionIdentifier))
									    componentType = questionComponentMap.get(targetQuestionIdentifier);
									if(metadataDT.getRuleCd().equalsIgnoreCase("Enable")){
										if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
											buffer.append("pgSubSectionEnabled('"+targetQuestionIdentifier+"');\n");
										}else{
											buffer.append("pgSubSectionDisabled('"+targetQuestionIdentifier+"');\n");
										}
									}else{
										if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
											buffer.append("pgSubSectionDisabled('"+targetQuestionIdentifier+"');\n");
										}else{
											buffer.append("pgSubSectionEnabled('"+targetQuestionIdentifier+"');\n");
										}
								   }
								}
					   		}
					   		buffer.append(" } else { \n");
					   		iter = coll.iterator();
					   		while(iter.hasNext()){
								targetQuestionIdentifier=iter.next().toString();
								if(targetQuestionIdentifier!=null){									
									targetQuestionIdentifier=targetQuestionIdentifier.trim();
									int componentType = 0;
									if (questionComponentMap.containsKey(targetQuestionIdentifier))
									    componentType = questionComponentMap.get(targetQuestionIdentifier);
									if(metadataDT.getRuleCd().equalsIgnoreCase("Enable")){
										if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
											buffer.append("pgSubSectionDisabled('"+targetQuestionIdentifier+"');\n");
										}else{
											buffer.append("pgSubSectionEnabled('"+targetQuestionIdentifier+"');\n");
										}
									}else{
										if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
											buffer.append("pgSubSectionEnabled('"+targetQuestionIdentifier+"');\n");
										}else{
											buffer.append("pgSubSectionDisabled('"+targetQuestionIdentifier+"');\n");
										}
								   }
								}
					   		}
					   		buffer.append(" }");
						}
					   	
					}
					
				}
				
			buffer.append("   \n}");
			String removeString = buffer.toString();
			metadataDT.setJavascriptFunction(buffer.toString());
			metadataDT.setJavascriptFunctionNm(functionName+"()");
			logger.debug(buffer.toString());
			return metadataDT;
		}

	/**
	 * Description: Generates the JavaScript Require If rule
	 * and stores it the metadataDT. See WA_rule_metadata table.
	 * This is read and placed on the index page by DBM_Generate_Index_JSP.xsl.
	 * pgCheckRequiredFields in DMBSpecific.js looks for the class requiredInputFields
	 * and flags any items which are not entered.
	 * @param WaRuleMetadataDT
	 * @param QuestionComponentMap
	 */
	public static WaRuleMetadataDT requireRuleJavaScript(WaRuleMetadataDT metadataDT, Map<String, Integer> questionComponentMap) {
		Collection<RuleElementDT> ruleElementDTColl = metadataDT.getRuleElementMap().values();
		Iterator<RuleElementDT> it = ruleElementDTColl.iterator();
		StringBuffer buffer=  new StringBuffer();
		logger.debug("Rule Element DT Collection has " + ruleElementDTColl.size() + " items ");
		String functionName="ruleRequireIf"+metadataDT.getSourceQuestionIdentifierString()+metadataDT.getWaRuleMetadataUid();
		buffer.append("function "+functionName+"()\n{");
		
			while(it.hasNext()){
				RuleElementDT ruleElementDT = (RuleElementDT)it.next();	
				
				buffer.append("\n var foo = [];\n");
				buffer.append("$j('#").append(ruleElementDT.getSourceQuestionIdentifier()).append(" :selected').each(function(i, selected){");
				buffer.append("\n foo[i] = $j(selected).val();\n"); 
				buffer.append(" });\n");
				
	
				if(ruleElementDT.getSourceQuestionIdentifier()!=null ){
					Collection<String> collSourceValue =ruleElementDT.getSourceValues();
					Iterator<String> iterSourceValue= collSourceValue.iterator();
					String[] str = new String[collSourceValue.size()];
					int ii =0;
					while (iterSourceValue.hasNext()){
						str[ii++] = iterSourceValue.next();
					}
					if (str.length==0 && (metadataDT.getSourceValues().equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE))
							|| str[0].equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE)) {
						buffer.append("if(foo.length>0 && foo[0] != '') {\n"); //anything selected 
					} else {
						buffer.append("if(foo.length==0) return;\n"); //nothing selected, nothing to do.
						buffer.append("\n if(");
						for (int i = 0; i < str.length; i++) { 
							buffer.append("($j.inArray('").append(str[i]).append("',foo) > -1)");
							if(str.length > (i+1)){
								buffer.append(" || ");
							}
						}
						buffer.append("){\n");
					}

					Collection coll = ruleElementDT.getTargetQuestionIdentifierColl();
					
					//For fixing issue where the rule_expresion doesn't contains the comparator = when the source value is Any Source value
					if(ruleElementDT.getComparator().equalsIgnoreCase("") && metadataDT.getSourceValues().equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE))
						ruleElementDT.setComparator("=");
					
					Iterator<String> iter = coll.iterator();
					String targetQuestionIdentifier="";
						
					while(iter.hasNext()){
							targetQuestionIdentifier=iter.next().toString();
							if(targetQuestionIdentifier!=null){
								targetQuestionIdentifier=targetQuestionIdentifier.trim();
								int componentType = 0;
								if (questionComponentMap.containsKey(targetQuestionIdentifier))
								    componentType = questionComponentMap.get(targetQuestionIdentifier);
								if(metadataDT.getRuleCd().equalsIgnoreCase("Require If")){
									if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
											if ((componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aMULTISELECT) ||
												(componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aSINGLESELECT) ||
												(componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION))
													buffer.append("pgRequireElement('"+targetQuestionIdentifier+"L');\n");
											else 
												buffer.append("pgRequireElement('"+targetQuestionIdentifier+"');\n");
									}else{
										if ((componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aMULTISELECT) ||
												(componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aSINGLESELECT) ||
												(componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION))
													buffer.append("pgRequireNotElement('"+targetQuestionIdentifier+"L');\n");
										else 
													buffer.append("pgRequireNotElement('"+targetQuestionIdentifier+"');\n");	
									}
								}
							} //if
					   } //while

						buffer.append(" } else { \n");
						iter = coll.iterator();
	
						while(iter.hasNext()){
						int componentType = 0;
						targetQuestionIdentifier=iter.next().toString();
						if(targetQuestionIdentifier!=null){
							targetQuestionIdentifier=targetQuestionIdentifier.trim();
							if (questionComponentMap.containsKey(targetQuestionIdentifier))
							    componentType = questionComponentMap.get(targetQuestionIdentifier);
							if(metadataDT.getRuleCd().equalsIgnoreCase("Require If")){
								if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
									if ((componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aMULTISELECT) ||
											(componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aSINGLESELECT) ||
											(componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION))
												buffer.append("pgRequireNotElement('"+targetQuestionIdentifier+"L');\n");
										else 
											buffer.append("pgRequireNotElement('"+targetQuestionIdentifier+"');\n");
								}else{
									if ((componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aMULTISELECT) ||
											(componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aSINGLESELECT) ||
											(componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION))
												buffer.append("pgRequireElement('"+targetQuestionIdentifier+"L');\n");
									else 
												buffer.append("pgRequireElement('"+targetQuestionIdentifier+"');\n");
								}
							}
						} //if
					   } //while
					   	buffer.append(" }");
				}
			}
			
		buffer.append("   \n}");
		metadataDT.setJavascriptFunction(buffer.toString());
		metadataDT.setJavascriptFunctionNm(functionName+"()");
		logger.debug(buffer.toString());
		return metadataDT;
	}
	
	public static WaRuleMetadataDT hideUnhideCompareJavaScript(WaRuleMetadataDT metadataDT, Map<String, Integer> questionComponentMap) {
		Collection<RuleElementDT> ruleElementDTColl = metadataDT.getRuleElementMap().values();
		String ruleElementDTCollDescriptionString = metadataDT.getSourceValues();//new for business rule view
		List<String> ruleElementDTCollDescription = Arrays.asList(ruleElementDTCollDescriptionString.split(","));
		
		//TODO: change to description
		
		Iterator<RuleElementDT> it = ruleElementDTColl.iterator();
		StringBuffer buffer=  new StringBuffer();
		logger.debug("Rule Element DT Collection has " + ruleElementDTColl.size() + " items ");
		String functionName="ruleHideUnh"+metadataDT.getSourceQuestionIdentifierString()+metadataDT.getWaRuleMetadataUid();
		buffer.append("function "+functionName+"()\n{");
		
		
		String comparater="";
		
			while(it.hasNext()){				
				
				RuleElementDT ruleElementDT = (RuleElementDT)it.next();	
				
				buffer=ruleLeftAndRightInvestigation(ruleElementDT, buffer, "", questionComponentMap, metadataDT, ruleElementDTCollDescription);
				buffer=ruleLeftAndRightInvestigation(ruleElementDT, buffer, "_2", questionComponentMap, metadataDT, ruleElementDTCollDescription);
				
				
			}
			
		buffer.append("   \n}");
		String removeString = buffer.toString();
		metadataDT.setJavascriptFunction(buffer.toString());
		metadataDT.setJavascriptFunctionNm(functionName+"()");
		logger.debug(buffer.toString());
		return metadataDT;
	}
	
	
		
	/**
	 * ruleLeftAndRightInvestigation(): this method creates the JavaScript functionality for hide/show rules. The suffix is used to be able to generate the functionality for left investigation and right investigation in case we are on Merge screen.
	 * In case it's Edit investigation screen, the behavior should be the same as it was.
	 * @param ruleElementDT
	 * @param buffer
	 * @param suffix
	 * @param questionComponentMap
	 * @param metadataDT
	 * @param ruleElementDTCollDescription
	 * @return
	 */
		
	private static StringBuffer ruleLeftAndRightInvestigation(RuleElementDT ruleElementDT, StringBuffer buffer, String suffix, Map<String, Integer> questionComponentMap, WaRuleMetadataDT metadataDT, List<String> ruleElementDTCollDescription){
		
		String sourceQuestion = ruleElementDT.getSourceQuestionIdentifier();
		ruleElementDT.setSourceQuestionIdentifier(sourceQuestion+suffix);
		
		
		buffer.append("\n var foo"+suffix+" = [];\n");
		buffer.append("$j('#").append(ruleElementDT.getSourceQuestionIdentifier()).append(" :selected').each(function(i, selected){");
		buffer.append("\n foo"+suffix+"[i] = $j(selected).val();\n");
		
		buffer.append(" });\n");
		buffer.append("if(foo"+suffix+"=='' && ").append("$j('#").append(ruleElementDT.getSourceQuestionIdentifier()).append("').html()!=null){");//added for the business rule view
		buffer.append("foo"+suffix+"[0]=$j('#").append(ruleElementDT.getSourceQuestionIdentifier()).append("').html().replace(/^\\s+|\\s+$/g,'');}");//added for the business rule view
		
		if(ruleElementDT.getSourceQuestionIdentifier()!=null ){
			Collection<String> collSourceValue =ruleElementDT.getSourceValues();
			Iterator<String> iterSourceValue= collSourceValue.iterator();
			String[] str = new String[collSourceValue.size()];
			int ii =0;
			while (iterSourceValue.hasNext()){
				str[ii++] = iterSourceValue.next();
			}
			if (str.length==0 && (metadataDT.getSourceValues().equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE))
					|| str[0].equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE)) {
				buffer.append("if(foo"+suffix+".length>0 && foo"+suffix+"[0] != '') {\n"); //anything selected 
			} else {						
				buffer.append("\n if(");
				for (int i = 0; i < str.length; i++) { 
					buffer.append("($j.inArray('").append(str[i]).append("',foo"+suffix+") > -1)");
					buffer.append(" || ($j.inArray('").append(ruleElementDTCollDescription.get(i)).append("'.replace(/^\\s+|\\s+$/g,''),foo"+suffix+") > -1");
					buffer.append(" || indexOfArray(foo,'").append(ruleElementDTCollDescription.get(i)).append("')==true)");//added for the business rule view
					if(str.length > (i+1)){
						buffer.append(" || ");
					}
				}
				
				buffer.append("){\n");
			}
			
		}
	
			Collection coll = ruleElementDT.getTargetQuestionIdentifierColl();
			
			//For fixing issue where the rule_expresion doesn't contains the comparator = when the source value is Any Source value
			if(ruleElementDT.getComparator().equalsIgnoreCase("") && metadataDT.getSourceValues().equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE))
				ruleElementDT.setComparator("=");
			
			Iterator<String> iter = coll.iterator();
			String targetQuestionIdentifier="";
			//buffer.append("\n");
	
			if("Question".equalsIgnoreCase(metadataDT.getTargetType())){
				while(iter.hasNext()){
					targetQuestionIdentifier=iter.next().toString();
					if(targetQuestionIdentifier!=null){
						targetQuestionIdentifier=targetQuestionIdentifier.trim();
						targetQuestionIdentifier+=suffix;
						int componentType = 0;
						if (questionComponentMap.containsKey(targetQuestionIdentifier))
						    componentType = questionComponentMap.get(targetQuestionIdentifier);
						if(metadataDT.getRuleCd().equalsIgnoreCase("Unhide")){
							if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
								if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
									buffer.append("pgUnhideParticipationElement('"+targetQuestionIdentifier+"');\n");
								else
									buffer.append("pgUnhideElement('"+targetQuestionIdentifier+"');\n");
							}else{
								if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
									buffer.append("pgHideParticipationElement('"+targetQuestionIdentifier+"');\n");
								else
									buffer.append("pgHideElement('"+targetQuestionIdentifier+"');\n");	
							}
						}else{
							if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
								if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
									buffer.append("pgHideParticipationElement('"+targetQuestionIdentifier+"');\n");
								else
									buffer.append("pgHideElement('"+targetQuestionIdentifier+"');\n");
							}else{
								if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
									buffer.append("pgUnhideParticipationElement('"+targetQuestionIdentifier+"');\n");
								else
									buffer.append("pgUnhideElement('"+targetQuestionIdentifier+"');\n");
							}
					   } //else
					} //if
			   } //while
	
				buffer.append(" } else { \n");
				iter = coll.iterator();
	
				while(iter.hasNext()){
				int componentType = 0;
				targetQuestionIdentifier=iter.next().toString();
				if(targetQuestionIdentifier!=null){
					targetQuestionIdentifier=targetQuestionIdentifier.trim();
					targetQuestionIdentifier+=suffix;
					if (questionComponentMap.containsKey(targetQuestionIdentifier))
					    componentType = questionComponentMap.get(targetQuestionIdentifier);
					if(metadataDT.getRuleCd().equalsIgnoreCase("Unhide")){
						if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
							if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
								buffer.append("pgHideParticipationElement('"+targetQuestionIdentifier+"');\n");
							else
								buffer.append("pgHideElement('"+targetQuestionIdentifier+"');\n");
						}else{
							if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
								buffer.append("pgUnhideParticipationElement('"+targetQuestionIdentifier+"');\n");
							else
								buffer.append("pgUnhideElement('"+targetQuestionIdentifier+"');\n");
						}
					}else{
						if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
							if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
								buffer.append("pgUnhideParticipationElement('"+targetQuestionIdentifier+"');\n");
							else
								buffer.append("pgUnhideElement('"+targetQuestionIdentifier+"');\n");
						}else{
							if (componentType == gov.cdc.nedss.pagemanagement.util.PageMetaConstants.aPARTICIPATION)
								buffer.append("pgHideParticipationElement('"+targetQuestionIdentifier+"');\n");
							else
								buffer.append("pgHideElement('"+targetQuestionIdentifier+"');\n");
						}
					}
				} //if
			   } //while
			   	buffer.append(" }");
		   	
		   	}else{  //Subsection
		   		while(iter.hasNext()){
					targetQuestionIdentifier=iter.next().toString();
					targetQuestionIdentifier+=suffix;
					if(targetQuestionIdentifier!=null){									
						targetQuestionIdentifier=targetQuestionIdentifier.trim();
						int componentType = 0;
						if (questionComponentMap.containsKey(targetQuestionIdentifier))
						    componentType = questionComponentMap.get(targetQuestionIdentifier);
						if(metadataDT.getRuleCd().equalsIgnoreCase("Unhide")){
							if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
								buffer.append("pgSubSectionShown('"+targetQuestionIdentifier+"');\n");
							}else{
								buffer.append("pgSubSectionHidden('"+targetQuestionIdentifier+"');\n");
							}
						}else{
							if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
								buffer.append("pgSubSectionHidden('"+targetQuestionIdentifier+"');\n");
							}else{
								buffer.append("pgSubSectionShown('"+targetQuestionIdentifier+"');\n");
							}
					   }
					}
		   		}
		   		buffer.append(" } else { \n");
		   		iter = coll.iterator();
		   		while(iter.hasNext()){
					targetQuestionIdentifier=iter.next().toString();
					targetQuestionIdentifier+=suffix;
					if(targetQuestionIdentifier!=null){									
						targetQuestionIdentifier=targetQuestionIdentifier.trim();
						int componentType = 0;
						if (questionComponentMap.containsKey(targetQuestionIdentifier))
						    componentType = questionComponentMap.get(targetQuestionIdentifier);
						if(metadataDT.getRuleCd().equalsIgnoreCase("Unhide")){
							if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
								buffer.append("pgSubSectionHidden('"+targetQuestionIdentifier+"');\n");
							}else{
								buffer.append("pgSubSectionShown('"+targetQuestionIdentifier+"');\n");
							}
						}else{
							if("=".equalsIgnoreCase(ruleElementDT.getComparator().trim())){
								buffer.append("pgSubSectionShown('"+targetQuestionIdentifier+"');\n");
							}else{
								buffer.append("pgSubSectionHidden('"+targetQuestionIdentifier+"');\n");
							}
					   }
					}
		   		}
		   		buffer.append(" }");
			}
			
			ruleElementDT.setSourceQuestionIdentifier(sourceQuestion);
			
			return buffer;
	}
			
	public static WaRuleMetadataDT metaDataCreater(WaRuleMetadataDT waRuleMetadataDT, String ruleExpression) {
			String sourceString = ruleExpression.substring(0, ruleExpression.indexOf("^"));
			String targetString = ruleExpression.substring(ruleExpression.indexOf("^")+1);
			String comparater = "";

			String trimmedSourceExp= sourceString.trim();
			if(ruleExpression.indexOf(RuleMetadataConstants.GT)!=-1)
				comparater= RuleMetadataConstants.GT;
			if(ruleExpression.indexOf(RuleMetadataConstants.LT)!=-1)
				comparater= RuleMetadataConstants.LT;
			if(ruleExpression.indexOf(RuleMetadataConstants.GTEQ)!=-1)
				comparater= RuleMetadataConstants.GTEQ;
			if(ruleExpression.indexOf(RuleMetadataConstants.LTEQ)!=-1)
				comparater= RuleMetadataConstants.LTEQ;
			if(ruleExpression.indexOf(RuleMetadataConstants.EQ)!=-1)
				comparater= RuleMetadataConstants.EQ;
			RuleElementDT ruleElementDT  = new RuleElementDT();
			Map<Integer, RuleElementDT> ruleElementMap=  new HashMap<Integer,RuleElementDT>();
			int i = 0;
			Collection<String> sourceValues = new ArrayList<String>();
			if((sourceString.indexOf(RuleMetadataConstants.CB)!=-1) && (sourceString.indexOf(RuleMetadataConstants.OB)!=-1)){
				String sourceValueExp= sourceString.substring(sourceString.indexOf("(" )+1, sourceString.indexOf(")" )).trim();
				StringTokenizer st = new StringTokenizer(sourceValueExp,RuleMetadataConstants.COM);
				while (st.hasMoreTokens()) {

					String token = st.nextToken();
					if(token.equalsIgnoreCase((RuleMetadataConstants.CB).trim()) || token.equalsIgnoreCase((RuleMetadataConstants.OB).trim()) )
						continue;
					if((token.indexOf(RuleMetadataConstants.GT)!=-1)||(token.indexOf(RuleMetadataConstants.LT)!=-1)
					||(token.indexOf(RuleMetadataConstants.GTEQ)!=-1)||(token.indexOf(RuleMetadataConstants.LTEQ)!=-1)
					||(token.indexOf(RuleMetadataConstants.EQ)!=-1) ||(token.indexOf(RuleMetadataConstants.COM)!=-1))
						continue;
					sourceValues.add(token.trim());
				}
			}
			ruleElementDT.setSourceValues(sourceValues);

			StringTokenizer st = new StringTokenizer(trimmedSourceExp,RuleMetadataConstants.CB);
			while (st.hasMoreTokens()) {

				String token = st.nextToken();
				if(token.equalsIgnoreCase((RuleMetadataConstants.CB).trim()) || token.equalsIgnoreCase((RuleMetadataConstants.OB).trim()) )
					break;
				if((token.indexOf(RuleMetadataConstants.GT)!=-1)||(token.indexOf(RuleMetadataConstants.LT.trim())!=-1)
				||(token.indexOf(RuleMetadataConstants.GTEQ)!=-1)||(token.indexOf(RuleMetadataConstants.LTEQ)!=-1)
				||(token.indexOf(RuleMetadataConstants.EQ)!=-1))
					continue;
				ruleElementDT.setSourceQuestionIdentifier(token);
				ruleElementDT.setComparator(comparater);
				Collection<String> sourceQuestionIdentifierDTColl=new ArrayList<String>();
				sourceQuestionIdentifierDTColl.add(ruleElementDT.getSourceQuestionIdentifier());
				waRuleMetadataDT.setSourceQuestionIdentifierDTColl(sourceQuestionIdentifierDTColl);
				break;
			}
			String comparator = targetString.substring(0,targetString.indexOf("(" )).trim();
			if(comparator.equalsIgnoreCase(RuleMetadataConstants.ENABLE)){
				waRuleMetadataDT.setRuleCd("Enable");
			}
			else if(comparator.equalsIgnoreCase(RuleMetadataConstants.DISABLE)){
				waRuleMetadataDT.setRuleCd("Disable");
			}
			else if(comparator.equalsIgnoreCase(RuleMetadataConstants.REQUIRE)){
				waRuleMetadataDT.setRuleCd("Require If");
			}			
			else if(comparator.equalsIgnoreCase(RuleMetadataConstants.HIDE)){
				waRuleMetadataDT.setRuleCd("Hide");
			}
			else if(comparator.equalsIgnoreCase(RuleMetadataConstants.UNHIDE)){
				waRuleMetadataDT.setRuleCd("Unhide");
			}
			String trimmedTargetExp= targetString.substring(targetString.indexOf("(" )+1, targetString.indexOf(")" )).trim();
			logger.debug("targetString :" +trimmedTargetExp);
			StringTokenizer targetst = new StringTokenizer(trimmedTargetExp,RuleMetadataConstants.COM);
			Collection<String> targetQuestionIdentifierDTColl=new ArrayList<String>();
			while (targetst.hasMoreTokens()) {
				String targetToken = targetst.nextToken();
				targetQuestionIdentifierDTColl.add(targetToken);
			}
			ruleElementDT.setTargetQuestionIdentifierColl(targetQuestionIdentifierDTColl);
			ruleElementMap.put(new Integer(i++), ruleElementDT);
			waRuleMetadataDT.setTargetQuestionIdentifierDTColl(targetQuestionIdentifierDTColl);
			waRuleMetadataDT.setRuleElementMap(ruleElementMap);
			return waRuleMetadataDT;
		}

}

