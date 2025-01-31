package gov.cdc.nedss.webapp.nbs.action.ldf;

import org.apache.struts.action.*;

import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

import java.io.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.ldf.dt.*;

import java.util.*;

import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.*;
import gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup;
import gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupHome;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.exception.*;

import javax.naming.*;
import javax.rmi.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.*;

import gov.cdc.nedss.ldf.vo.LdfBaseVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.*;

import java.lang.reflect.*;

import gov.cdc.nedss.webapp.nbs.action.ldf.helper.LdfHelper;
import gov.cdc.nedss.webapp.nbs.action.ldf.helper.SubformHelper;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.form.util.CommonForm;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class BaseLdf extends Action {

	public static final String SPECIAL_CHARS = "^^^^^^";
	public static final String NEXT_LDF_GROUP_INDEX = "nextLDFGroupIndex";
	private static final String GROPUTAG = "<group name=\"Custom Fields\">";

	//For logging
	static final LogUtils logger = new LogUtils(BaseLdf.class.getName());
	static Hashtable<Object, Object> htblLDF_INDEXES = null;
	private int conditionVal = 300;

	static {

		// Initialize hashmap that is used to build start indexes.
		htblLDF_INDEXES = new Hashtable<Object, Object>();
		htblLDF_INDEXES.put(NEDSSConstants.PATIENT_LDF, new Integer(1));
		htblLDF_INDEXES.put(
			NEDSSConstants.PATIENT_EXTENDED_LDF,
			new Integer(2));
		htblLDF_INDEXES.put(NEDSSConstants.VACCINATION_LDF, new Integer(3));
		htblLDF_INDEXES.put(NEDSSConstants.TREATMENT_LDF, new Integer(4));
		htblLDF_INDEXES.put(NEDSSConstants.ORGANIZATION_LDF, new Integer(5));
		htblLDF_INDEXES.put(NEDSSConstants.PROVIDER_LDF, new Integer(6));
		htblLDF_INDEXES.put(NEDSSConstants.LABREPORT_LDF, new Integer(7));
		htblLDF_INDEXES.put(NEDSSConstants.MORBREPORT_LDF, new Integer(8));
		htblLDF_INDEXES.put(NEDSSConstants.INVESTIGATION_LDF, new Integer(9));
		htblLDF_INDEXES.put(
			NEDSSConstants.INVESTIGATION_HEP_LDF,
			new Integer(10));
		htblLDF_INDEXES.put(
			NEDSSConstants.INVESTIGATION_BMD_LDF,
			new Integer(11));
		htblLDF_INDEXES.put(
			NEDSSConstants.INVESTIGATION_NIP_LDF,
			new Integer(12));

	}

	public BaseLdf() {
	}

	/**
	 *
	 * @param anObject
	 * @param strPath
	 * @return
	 * @throws IOException
	 */

	private Collection<Object>  _explore(Object anObject, String strPath)
		throws IOException {
		ArrayList<Object> mList = new ArrayList<Object> ();
		Method[] meths = anObject.getClass().getDeclaredMethods();
		String getMethodName = null;
		// System.out.println("the meths is :" + meths);
		if (meths != null) {
			for (int i = 0; i < meths.length; i++) {
				try {
					Method method = meths[i];
					if (method.getParameterTypes().length > 0)
						continue;
					Class<?> retClass = method.getReturnType();

					// does it return a String, Integer, Short, or Long?
					if (retClass.getName().equalsIgnoreCase("java.lang.String")
						|| retClass.getName().equalsIgnoreCase(
							"java.lang.Integer")
						|| retClass.getName().equalsIgnoreCase("java.lang.Short")
						|| retClass.getName().equalsIgnoreCase(
							"java.lang.Long")) {
						Object returnVal = method.invoke(anObject, (Object[])null);
						if (returnVal != null) {
							//   System.out.println(strPath + ".test.:" +method.getName()+  ":=" + returnVal.toString());
							getMethodName =
								method.getName().substring(3, 4).toLowerCase()
									+ method.getName().substring(4);
							//    System.out.println(getMethodName);
							if (getMethodName.equalsIgnoreCase("CODESETNM")
									|| getMethodName
											.equalsIgnoreCase("LABELTXT")
									|| getMethodName
											.equalsIgnoreCase("ADMINCOMMENT"))
								mList.add("\n\t\t\t<" + getMethodName
										+ "><![CDATA[" + returnVal.toString()
										+ "]]></" + getMethodName + ">");
							else
								mList.add(
									"\n\t\t\t<"
										+ getMethodName
										+ ">"
										+ returnVal.toString()
										+ "</"
										+ getMethodName
										+ ">");
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return mList;
	}

	/**
	 * 1. Retrieve LDF metadata for the specified business object.
	 * 2. Retrieve the LDF values from the request.
	 * 3. Add the XSP to the request.
	 *
	 * TODO : See if XSP can be pregenerated and cached. Use string manipulation logic to
	 * insert LDF values into pregenerated XSP.
	 *
	 * @param metaDataColl
	 * @return
	 * @throws NEDSSConcurrentDataException
	 * @throws IOException
	 * @throws Exception
	 */
	public void createXSP(
		String busObjNm,
		LdfBaseVO businessObjectVO,
		String condition,
		HttpServletRequest request) {

		createXSP(busObjNm, null, businessObjectVO, condition, request);
	}

	public void createXSP(
		String busObjNm,
		Long busObjUID,
		LdfBaseVO businessObjectVO,
		String condition,
		HttpServletRequest request) {

		long startTime = System.currentTimeMillis();
		HashMap<Object, Object> ldfValueMap = null;
		Hashtable<Object, Object> xspPageCache = null;

		DefinedFieldSubformGroup group = getGroup(busObjUID, request);
		String cacheKey = LdfHelper.generateXSPPageCacheKey(busObjNm, busObjUID, group, null, request);
		logger.debug("---- In createXSP: cacheKey is " + cacheKey);
		String conditionCacheKey =
			LdfHelper.generateXSPPageCacheKey(busObjNm, busObjUID, group, condition, request);
		logger.debug("---- In createXSP: conditionCacheKey is " + conditionCacheKey);

		if (busObjNm == null || busObjNm.trim().equals(""))
			return;

		if (businessObjectVO != null)
			ldfValueMap =
				_buildValueMap(businessObjectVO, busObjNm, condition, request);
		int i = _getNextLDFGroupIndex(request);

		// Scenario 1 : HEPS
		// Handle HEPs separately - HEPs require two xsp chunks one for all HEP and one for disease specific.
		if (busObjNm.equals(NEDSSConstants.INVESTIGATION_HEP_LDF)) {

			//if ldfExists session attribute exists, remove it first !!
			request.getSession().removeAttribute("ldfExists");
			// HEP Core only
			String extXSP1 = LdfHelper.getFromXSPPageCache(cacheKey, request.getSession());

			if (extXSP1 == null) {
				Collection<?>  coll1 =
					_getLDFMetaData(busObjNm, busObjUID, null, false, request);
				extXSP1 = _createXSP(businessObjectVO, coll1, ldfValueMap);
				LdfHelper.putInXSPPageCache(cacheKey, extXSP1, request.getSession());
			}
			extXSP1 = _putLDFDataInXSP(extXSP1, ldfValueMap);
			extXSP1 = _rebuildLDFIndexes(extXSP1, i);
			getCustomJavaScript(busObjNm, null, request);
			request.setAttribute(busObjNm + "extXSP", extXSP1);
			//This logic extracts Subforms for the particular NBS business object and renders the page with appropriate Subforms
			try {
				SubformHelper helper = SubformHelper.getInstance();
				helper.extractSubforms(
					businessObjectVO,
					busObjNm,
					busObjUID,
					null,
					request);
			} catch (Exception e) {
				//e.printStackTrace();
			}

			// HEP Cond specific only
			if (condition != null
				&& condition.trim().length() != 0
				&& !condition.trim().equalsIgnoreCase("999999")) {
				String extXSP2 =
					LdfHelper.getFromXSPPageCache(conditionCacheKey, request.getSession());
				if (extXSP2 == null) {
					Collection<?>  coll2 =
						_getLDFMetaData(
							busObjNm,
							busObjUID,
							condition,
							false,
							request);
					extXSP2 = _createXSP(businessObjectVO, coll2, ldfValueMap);
					//extXSP2 = replaceXSPForEmptyCondition(extXSP2);
					LdfHelper.putInXSPPageCache(conditionCacheKey, extXSP2, request.getSession());
				}
				if (extXSP2 != null
					&& extXSP2.indexOf(NEDSSConstants.BLANK_XSPTAG_LDF) == -1) {
					//boolean showLDF = false;
					// if (request.getAttribute("showConditionSpecificLDF") != null)
					//  showLDF = ( (Boolean) request.getAttribute(
					//      "showConditionSpecificLDF")).booleanValue();
					// else
					//   showLDF = false;
					//if (showLDF) {
					extXSP2 = replaceXSPForCondition(extXSP2);
					//}
					extXSP2 = _putLDFDataInXSP(extXSP2, ldfValueMap);
					i = _getNextLDFGroupIndex(request);
					extXSP2 =
						_rebuildLDFIndexes(
							extXSP2,
							_getNextLDFGroupIndex(request));
					if (extXSP2 != null
						&& extXSP2.indexOf(
							NEDSSConstants.SPECIAL_CHARACTER_OPENING)
							> 0) {
						Boolean ldfExists = new Boolean(true);
						//LDF Hepatitis sepcific changes
						request.setAttribute("ldfExists", ldfExists);
						request.getSession().setAttribute(
							"ldfExists",
							ldfExists);

					}

					request.setAttribute("conditionextXSP", extXSP2);
				}
				//This logic extracts Subforms for the particular NBS business object and renders the page with appropriate Subforms
				try {
					SubformHelper helper = SubformHelper.getInstance();
					helper.extractSubforms(
						businessObjectVO,
						busObjNm,
						busObjUID,
						null,
						request);
					helper.extractSubforms(
						businessObjectVO,
						busObjNm,
						busObjUID,
						condition,
						request);
				} catch (Exception e) {
					//e.printStackTrace();
				}

			}
			getCustomJavaScript(busObjNm, condition, request);
			// No further processing necessary for HEP... just return.
			logger.debug(
					"---- In createXSP: The method costs "
						+ (System.currentTimeMillis() - startTime)
						+ " milliseconds.");
			return;
		}

		// Scenario 2 : All condition specific LDfs....
		// One XSP including both cond and non-cond LDFs.
		if (condition != null) {
			String extXSP1 = LdfHelper.getFromXSPPageCache(conditionCacheKey, request.getSession());
			if (extXSP1 == null) {
				Collection<?>  coll1 =
					_getLDFMetaData(
						busObjNm,
						busObjUID,
						condition,
						false,
						request);
				extXSP1 = _createXSP(businessObjectVO, coll1, ldfValueMap);
				extXSP1 = replaceXSPForCondition(extXSP1);
				//extXSP1 = replaceXSPForEmptyCondition(extXSP1);
				LdfHelper.putInXSPPageCache(conditionCacheKey, extXSP1, request.getSession());
			}
			extXSP1 = _putLDFDataInXSP(extXSP1, ldfValueMap);
			extXSP1 =
				_rebuildLDFIndexes(extXSP1, _getNextLDFGroupIndex(request));
			request.setAttribute(busObjNm + "conditionextXSP", extXSP1);
			getCustomJavaScript(busObjNm, condition, request);
			//return;
			//This logic extracts Subforms for the particular NBS business object and renders the page with appropriate Subforms
			try {
				SubformHelper helper = SubformHelper.getInstance();
				helper.extractSubforms(
					businessObjectVO,
					busObjNm,
					busObjUID,
					condition,
					request);
			} catch (Exception e) {
				//e.printStackTrace();
			}

		}

		// Scenario 3 :  Plain business object, not condition specific - such as entities.
		String extXSP1 = LdfHelper.getFromXSPPageCache(cacheKey, request.getSession());
		if (extXSP1 == null) {
			Collection<?>  coll1 =
				_getLDFMetaData(busObjNm, busObjUID, null, false, request);
			extXSP1 = _createXSP(businessObjectVO, coll1, ldfValueMap);
			LdfHelper.putInXSPPageCache(cacheKey, extXSP1, request.getSession());
		}
		extXSP1 = _putLDFDataInXSP(extXSP1, ldfValueMap);
		extXSP1 = _rebuildLDFIndexes(extXSP1, _getNextLDFGroupIndex(request));
		request.setAttribute(busObjNm + "extXSP", extXSP1);
		getCustomJavaScript(busObjNm, null, request);
		//This logic extracts Subforms for the particular NBS business object and renders the page with appropriate Subforms
		try {
			SubformHelper helper = SubformHelper.getInstance();
			helper.extractSubforms(
				businessObjectVO,
				busObjNm,
				busObjUID,
				null,
				request);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		logger.debug(
			"---- In createXSP: The method costs "
				+ (System.currentTimeMillis() - startTime)
				+ " milliseconds.");
		return;

	}

	/**
	 * 1. Construct an XML representation of LDF metadata( passed in as an argument )
	 * 2. Add LDF values to the XML when available.
	 * 3. Transform the XML to XSP
	
	 * @param businessObjectVO
	 * @param ldfMetadataCollection
	 * @param ldfValueMap
	 * @return
	 */
	protected String _createXSP(
		LdfBaseVO businessObjectVO,
		Collection<?>  ldfMetadataCollection,
		HashMap<?, ?> ldfValueMap) {
		if (ldfMetadataCollection  == null || ldfMetadataCollection.isEmpty())
			return NEDSSConstants.BLANK_XSPTAG_LDF;
		try {
			Iterator<?> it = ldfMetadataCollection.iterator();
			int curIndex = 1;
			/* for multiuser enviornment, create seperate file for each user. */
			// XZ (NBS1.1.3SP1) replace string concatenation with StringBuffer append.
			// This can improve performance quite much for pages with lots of LDFs/CDFs.
			StringBuffer ldfXML = new StringBuffer();
			ldfXML.append(
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\t<root>");
			while (it.hasNext()) {
				ldfXML.append("\n\t\t<StateDefinedDT>");
				StateDefinedFieldMetaDataDT sdfMetaDataDT =
					(StateDefinedFieldMetaDataDT) it.next();
				Collection<Object>  collection = _explore(sdfMetaDataDT, "test");
				Iterator<Object> iter = collection.iterator();
				while (iter.hasNext()) {
					String xmlBuilder = iter.next().toString();
					logger.debug(xmlBuilder);
					ldfXML.append(xmlBuilder);
				}
				ldfXML.append("<ldfValue>");
				ldfXML.append(SPECIAL_CHARS);
				ldfXML.append(sdfMetaDataDT.getLdfUid());
				ldfXML.append(SPECIAL_CHARS);
				ldfXML.append("</ldfValue>");
				ldfXML.append("\n\t\t</StateDefinedDT>");
			}
			ldfXML.append("\n</root>");
			/*** old code using String concatenation
			String ldfXML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
			ldfXML += "\n\t" + "<root>";
			while (it.hasNext()) {
				ldfXML += "\n\t\t" + "<StateDefinedDT>";
				StateDefinedFieldMetaDataDT sdfMetaDataDT =
					(StateDefinedFieldMetaDataDT) it.next();
				Collection<Object>  collection = _explore(sdfMetaDataDT, "test");
				Iterator iter = collection.iterator();
				while (iter.hasNext()) {
					String xmlBuilder = iter.next().toString();
					logger.debug(xmlBuilder);
					ldfXML += xmlBuilder;
				}
				if (businessObjectVO != null && ldfValueMap.size() > 0)
					// && ldfValueMap.get(sdfMetaDataDT.getLdfUid()) != null)
					ldfXML += "<ldfValue>"
						+ SPECIAL_CHARS
						+ sdfMetaDataDT.getLdfUid()
						+ SPECIAL_CHARS
						+ "</ldfValue>";
			
				else // && ldfValueMap.get(sdfMetaDataDT.getLdfUid()) != null)
					ldfXML += "<ldfValue>"
						+ SPECIAL_CHARS
						+ sdfMetaDataDT.getLdfUid()
						+ SPECIAL_CHARS
						+ "</ldfValue>";
			
				ldfXML += "\n\t\t" + "</StateDefinedDT>";
			}
			ldfXML += "\n</root>";
			**/

			//  =========================================================================
			// 1. Instantiate a TransformerFactory.
			javax.xml.transform.TransformerFactory tFactory =
				javax.xml.transform.TransformerFactory.newInstance();

			// 2. Use the TransformerFactory to process the stylesheet Source and
			//    generate a Transformer.
		
			String stylesheet = PropertyUtil.propertiesDir + "ldfToXSP.xsl" ; 
			
			logger.debug("The web path for ldfToXSP.xsl is :"+ stylesheet ); //to check for the correct path of web application
			
			javax.xml.transform.Transformer transformer =
				tFactory.newTransformer(
					new javax.xml.transform.stream.StreamSource( stylesheet ));

			// 3. Use the Transformer to transform an XML Source and send the
			//    output to a Result object.

			//stores the transformed XML
			java.io.StringWriter sXSPOutput = new java.io.StringWriter();

			transformer.transform(
				new javax.xml.transform.stream.StreamSource(
					new StringReader(ldfXML.toString())),
				new javax.xml.transform.stream.StreamResult(sXSPOutput));

			String extXSP = sXSPOutput.toString().substring(sXSPOutput.toString().indexOf("<group"));

			if (extXSP == null || extXSP.trim().equals(""))
				extXSP = NEDSSConstants.BLANK_XSPTAG_LDF;

			logger.debug("extXSP  = " + extXSP);
			return extXSP;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	protected Collection<?>  _getLDFMetaData(
		String busObjNm,
		Long busObjUid,
		String condCd,
		boolean includeNonCondLDF,
		HttpServletRequest request) {

		Collection<?>  list = new ArrayList<Object> ();
		StringBuffer customJavaScriptList = new StringBuffer();
		try {

			list =
				getDefinedFieldMetadataCollection(
					busObjNm,
					busObjUid,
					condCd,
					includeNonCondLDF,
					request);

			/* NedssUtils nu = new NedssUtils();
			StringBuffer customJavaScriptList = new StringBuffer();
			Object objref = nu.lookupBean(JNDINames.LDFMetaData_EJB);
			LDFMetaDataHome home = (LDFMetaDataHome) PortableRemoteObject.narrow(objref, LDFMetaDataHome.class);
			LDFMetaData mData = home.create();
			Collection<Object>  list = new ArrayList<Object> ();
			if (condCd == null)
			        list = mData.getLDFMetaData(busObjNm);
			else
			        list = mData.getLDFMetaData(busObjNm, condCd, includeNonCondLDF); */

			if (list.size() > 0) {
				Iterator<?> it = list.iterator();
				while (it.hasNext()) {
					StateDefinedFieldMetaDataDT metaDT =
						(StateDefinedFieldMetaDataDT) it.next();
					if (metaDT.getCodeSetNm() != null) {
						String options = getCodedValues(metaDT.getCodeSetNm());
						metaDT.setCodeSetNm(options);
					}
					if (metaDT.getValidationJscriptTxt() != null) {
						customJavaScriptList.append("\n");
						customJavaScriptList.append(
							metaDT.getValidationJscriptTxt());
						metaDT.setJavaScriptFunctionName(
							getJSFunctionName(
								metaDT.getValidationJscriptTxt()));
						metaDT.setValidationTxt("custom");
					} else
						metaDT.setJavaScriptFunctionName("ldfNullValue");

				}

			}
			ServletContext context = request.getSession().getServletContext();
			if (condCd != null && condCd != "")
				context.setAttribute(
					busObjNm + condCd,
					customJavaScriptList.toString());
			else
				context.setAttribute(busObjNm, customJavaScriptList.toString());
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HashMap<Object,Object> _buildValueMap(
		LdfBaseVO ldfBaseVO,
		String busObjNm,
		String condition,
		HttpServletRequest request) {

		ArrayList<Object> arr =
			(ArrayList<Object> ) ldfBaseVO.getTheStateDefinedFieldDataDTCollection();
		HashMap<Object, Object> map = new HashMap<Object,Object>();
		if (arr != null && arr.size() > 0) {
			Iterator<Object> it = arr.iterator();
			while (it.hasNext()) {
				StateDefinedFieldDataDT ldfDT =
					(StateDefinedFieldDataDT) it.next();
				map.put(ldfDT.getLdfUid(), ldfDT);
			}
		}
		request.setAttribute("LdfData", map);
		return map;
	}

	private String _putLDFDataInXSP(String extXSP, HashMap<Object,Object> ldfMap) {
		if (extXSP.equalsIgnoreCase(NEDSSConstants.BLANK_XSPTAG_LDF))
			return extXSP;
		StringTokenizer st = new StringTokenizer(extXSP, SPECIAL_CHARS);
		String arr[] = new String[st.countTokens()];
		StringBuffer buff = new StringBuffer();
		boolean primeNumber = false;
		for (int i = 0; st.hasMoreTokens(); i++) {
			primeNumber = !primeNumber;
			arr[i] = st.nextToken();
			if (primeNumber) {
				buff.append(arr[i]);
			} else {
				if (ldfMap != null) {
					if (ldfMap.get(new Long(arr[i])) != null) {
						StateDefinedFieldDataDT stateDT =
							(StateDefinedFieldDataDT) ldfMap.get(
								new Long(arr[i]));
						if (stateDT != null
							&& stateDT.getLdfValue() != null
							&& stateDT.getLdfValue().indexOf(
								NEDSSConstants.SPECIAL_CHARACTER_OPENING)
								== -1)
							stateDT.setLdfValue(
								NEDSSConstants.SPECIAL_CHARACTER_OPENING
									+ stateDT.getLdfValue()
									+ NEDSSConstants.SPECIAL_CHARACTER_CLOSING);

						buff.append(stateDT.getLdfValue());
					}
				}
			}
		}

		return buff.toString();
	}

	protected String getCodedValues(String type) {
		CachedDropDownValues srtStatic = new CachedDropDownValues();
		//GetSRTCodes srtStatic = new GetSRTCodes();

		//PHC_TYPE - Condition_Code
		if (type.equalsIgnoreCase("PHC_TYPE"))
			return srtStatic.getConditionCode();
		//P_RACE_CAT - ROOT Parent_cd  P_RACE - Sub categories
		else if (
			type.equalsIgnoreCase("P_RACE")
				|| type.equalsIgnoreCase("P_RACE_CAT"))
			return srtStatic.getRaceCodesByCodeSet(type);
		//P_LANG - Language_Code
		else if (type.equalsIgnoreCase("P_LANG"))
			return srtStatic.getLanguageCode();
		//O_NAICS - NAICS_industry_code
		else if (type.equalsIgnoreCase("O_NAICS"))
			return srtStatic.getNAICSGetIndustryCode();
		//P_OCCUP Occupation_code
		else if (type.equalsIgnoreCase("P_OCCUP"))
			return srtStatic.getOccupationCode();
		//PLS_CNTRY - country code
		else if (type.equalsIgnoreCase("PSL_CNTRY"))
			return srtStatic.getCountryCodesAsString();
		//STATE_CCD - state codes
		else if (type.equalsIgnoreCase("STATE_CCD"))
			return srtStatic.getStateCodes("USA");
		else {
			String retStr = null;
			retStr = srtStatic.getSAICDefinedCodedValues(type);
			if (retStr != null && retStr.trim().length() != 0)
				return retStr;
			else
				return srtStatic.getCodedValues(type);
		}

	}

	/**
	 * Return the next index to be used as the ldf array subscript.
	 * Since an NBS page can have multiple business objects, each having their own set of LDFs,
	 * the array indexes used in XSP generation should be ordered in a consecutive manner. For
	 * example, the lab entry page deals with the patient object and the lab object and both
	 * can have their own set of LDFs. We need to make sure the array indexes used in the lab
	 * XSP do not overlap, but start from the last index for the patient. We generalize this
	 * scenario and provide a facility for a page dealing with an arbitrary number of business
	 * objects, each with their own set of LDFs.
	 *
	 * Every time createXSP is called, the generated XSP will only contain symbolic references
	 * to startIndex. Once the XSP is generated( or retrieved from the cache, ) we calculate what
	 * the actual value for the startIndex should be, by looking at all request attributes that
	 * end with "XSP" - thereby considering all previously generated extXSPs for this request.
	 * For each such attribute we count the number of LDFs by looking for <line> element in the
	 * XSP. The cumulative total of such items will give the number of LDFs across all business
	 * objects in the request. We simply increment that number by 1 to retrieve the startIndex
	 * for the current XSP.
	 *
	 * @author Ajith Kallambella.
	 * @param request - HTTP request object
	 * @return
	 */
	private int _getNextLDFGroupIndex(HttpServletRequest request) {

		int index = -1;
		Enumeration<String> parameterList = request.getAttributeNames();
		try {
			while (parameterList.hasMoreElements()) {
				String pName = parameterList.nextElement().toString();
				if (pName.endsWith("XSP")) {
					String extXSPString =
						request.getAttribute(pName).toString();
					do {
						if (extXSPString.indexOf("ldfValue") > 0) {
							extXSPString =
								extXSPString.substring(
									extXSPString.indexOf("ldfValue")
										+ "ldfValue".length());
							index++;
						}
					} while ((extXSPString.indexOf("ldfValue") > 0));

				}
			}
		} catch (Exception ex) {
		}
			return (index > -1 ? index + 1 : 0);
	}

	/**
	 * Search through the argument string and _stringReplace all occurances of ldf[startIndex + nn]
	 * ldf[nnn] where nnn = Integer(replaceString) + nn
	 * This method is used by createXSP to _stringReplace the startIndex vaule with appropriate
	 * ldf start index returned by _getNextLDFGroupIndex.
	 * ldf[startIndex + 0]
	 *
	 * @author Ajith Kallambella.
	 * * @param str
	 * @param pattern
	 * @param _stringReplace
	 * @return
	 */
	private String _rebuildLDFIndexes(String xspStr, int beginIndex) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();
		String pattern = "ldf[";

		while ((e = xspStr.indexOf(pattern, s)) >= 0) {
			int pos1 = e + pattern.length();
			result.append(xspStr.substring(s, pos1));

			int pos2 = xspStr.indexOf("]", pos1);

			try {
				int i =
					Integer.parseInt(xspStr.substring(pos1, pos2).trim())
						+ beginIndex;
				result.append(i);
			} catch (NumberFormatException ex) {
				// do nothing.
			}
			s = pos2;
		}
		result.append(xspStr.substring(s));
		return result.toString();
	}

	private void getCustomJavaScript(
		String busObjNm,
		String condCd,
		HttpServletRequest request) {
		String customJavaScript = "";
		ServletContext context = request.getSession().getServletContext();
		if (condCd != null && condCd != "") {
			if (context.getAttribute(busObjNm + condCd) != null)
				customJavaScript =
					(String) context.getAttribute(busObjNm + condCd);
			request.setAttribute("customJavaScript", customJavaScript);
		} else {
			context.getAttribute(busObjNm);
			customJavaScript = (String) context.getAttribute(busObjNm);
			request.setAttribute(
				busObjNm + "customJavaScript",
				customJavaScript);
		}
	}

	private String getJSFunctionName(String jsFunction) {

		if (jsFunction != null) {
			StringTokenizer tokens = new StringTokenizer(jsFunction);
			String arr[] = new String[tokens.countTokens()];
			for (int i = 0; tokens.hasMoreTokens(); i++) {
				arr[i] = tokens.nextToken();
				if (i == 1) {
					StringTokenizer st = new StringTokenizer(arr[i], "(");
					while (st.hasMoreTokens()) {
						return st.nextToken();
					}
				}
			}
		}
		return null;
	}

	private String replaceXSPForCondition(String extendedXSP) {

		//check if any LDF/CDF for display exists, if not dont create a group name
		if (extendedXSP.indexOf("label=") == -1) {
			System.out.println("NO LDFs/CDFs for display !!!!");
			return extendedXSP;
		}	
		
		if (extendedXSP.indexOf("<group>") == 0) {
			//we know that we have empty <group> tag

			extendedXSP =
				extendedXSP.substring(
					("<group>").length(),
					extendedXSP.length());
			extendedXSP =
				"<group name=\"Condition Specific Custom Fields\">"
					+ extendedXSP;

		} else if (
			extendedXSP.indexOf("<group name=\"Custom Fields\">") == 0) {

			extendedXSP =
				extendedXSP.substring(
					("<group name=\"Custom Fields\">").length(),
					extendedXSP.length());
			extendedXSP =
				"<group name=\"Condition Specific Custom Fields\">"
					+ extendedXSP;
		}
		//ie. if just an empty group tag exists without defined field xsp, we dont need to display jumper and group in the xsp pages
		if (extendedXSP
			.equalsIgnoreCase("<group name=\"Condition Specific Custom Fields\"></group>"))
			return "<group></group>";
		else
			return extendedXSP;

	}

	public Long updateBusinessObjectGroupRelationship(
		Long businessUid,
		List<Object> ldfUids,
		HttpServletRequest request) {

		MainSessionCommand msCommand = null;
		Long retVal = null;
		try {

			String sBeanJndiName = JNDINames.DEFINED_FIELD_SUBFORMGROUP_EJB;
			String sMethod = "updateBusinessObjectGroupRelationship";
			Object[] oParams = { businessUid, ldfUids };
			logger.debug(
				"getting  updateBusinessObjectGroupRelationship from DefinedFieldSubformGroupEJB, via mainsession");

			HttpSession session = request.getSession(true);
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			logger.info("mscommand in BaseLDF class is: " + msCommand);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			resultUIDArr =
				msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				retVal = (Long) resultUIDArr.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	public List<Object> getDefinedFieldUids(
		String busObjNm,
		Long busObjUid,
		String condCd,
		boolean includeNonCondLDF,
		HttpServletRequest request) {

		List<?> aList =
			getDefinedFieldMetadataCollection(
				busObjNm,
				busObjUid,
				condCd,
				includeNonCondLDF,
				request);
		List<Object> retVal = new ArrayList<Object> ();

		if (aList.size() > 0) {
			Iterator<?> it = aList.iterator();
			while (it.hasNext()) {
				StateDefinedFieldMetaDataDT metaDT =
					(StateDefinedFieldMetaDataDT) it.next();
				retVal.add(metaDT.getLdfUid());

			}
		}
		return retVal;
	}

	public List<Object> getDefinedFieldMetadataCollection(
		String busObjNm,
		Long busObjUid,
		String condCd,
		boolean includeNonCondLDF,
		HttpServletRequest request) {

		MainSessionCommand msCommand = null;
		List<Object> list = new ArrayList<Object> ();
		Boolean includeNonCondLDFObj = new Boolean(includeNonCondLDF);
		try {

			String sBeanJndiName = JNDINames.LDFMetaData_EJB;
			String sMethod = "";
			Object[] oParams = {
			};
			logger.debug(
				"getting  ldfMetaData from LDFMetaDataEJB, via mainsession");
			if (condCd == null) {
				Object[] oParams1 = { busObjNm, busObjUid };
				oParams = oParams1;
				sMethod = "getLDFMetaDataByBusObjNm";
			} else {
				Object[] oParams2 =
					{ busObjNm, busObjUid, condCd, includeNonCondLDFObj };
				oParams = oParams2;
				sMethod = "getLDFMetaDataByBusObjNmAndConditionCd";

			}

			HttpSession session = request.getSession(true);
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			logger.info("mscommand in BaseLDF class is: " + msCommand);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			resultUIDArr =
				msCommand.processRequest(sBeanJndiName, sMethod, oParams);

			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				list = (List<Object>) resultUIDArr.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean checkCoditionCd(
		StateDefinedFieldDataDT stateDT,
		String conditionCd) {
		if (stateDT == null) {
			return false;
		}
		String tmpCode = stateDT.getConditionCd();
		if (conditionCd == null && tmpCode == null)
			return true;
		else if (tmpCode != null && conditionCd.equalsIgnoreCase(tmpCode))
			return true;

		return false;
	}

	/**
	 * @param busObjUID
	 * @return
	 */
	private DefinedFieldSubformGroup getGroup(
		Long busObjUID,
		HttpServletRequest request) {

		MainSessionCommand msCommand = null;
		DefinedFieldSubformGroup group = null;
		try {

			String sBeanJndiName = JNDINames.DEFINED_FIELD_SUBFORMGROUP_EJB;
			String sMethod = "getGroup";
			Object[] oParams = { busObjUID };
			logger.debug(
				"getting  getGroup from DefinedFieldSubformGroupEJB, via mainsession");

			HttpSession session = request.getSession(true);
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			logger.info("mscommand in BaseLDF class is: " + msCommand);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			resultUIDArr =
				msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				group = (DefinedFieldSubformGroup) resultUIDArr.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}
	
	// this method is added to handle multiselect ldf values.  this is a quick and dirty fix.  
	// we probably need to investigate better solution in the long run.
	// xz (01/05/2005)
	private void updateMultiselectLdfValues(Collection<Object> col, HttpServletRequest request){
		
		if(col == null){
			return;
		}
		
		// going through all parameters in the request and find entries with multiple 
		// values
		Enumeration<String> parameters = request.getParameterNames();
		while(parameters.hasMoreElements()){
			String parameter = (String)parameters.nextElement();
			if(parameter != null && parameter.endsWith("].ldfValue")){	
				String[] values = request.getParameterValues(parameter);
				if(values != null && values.length > 1){
					parameter = parameter.substring(0,parameter.length()-8);
					parameter = parameter +"ldfUid";		
					String uid = request.getParameter(parameter);
					if(uid != null){
						for (Iterator<Object> iter = col.iterator(); iter.hasNext();) {
							StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) iter.next();
							if(stateDT != null && stateDT.getLdfUid() != null && stateDT.getLdfUid().equals(new Long(uid))){
								StringBuffer value = new StringBuffer();
								for (int j = 0; j < values.length; j++) {
									if(values[j]!=null && !values[j].trim().equals("")){
										value.append(values[j]);
										value.append(NEDSSConstants.BATCH_LINE);
									}
								}
								stateDT.setLdfValue(value.toString());
							}
						}				
						
					}
				}
				
			}
		}		
	}
	

	protected ArrayList<Object> extractLdfDataCollection(CommonForm form, HttpServletRequest request){
		ArrayList<Object> coll = form.getLdfCollection();	
		updateMultiselectLdfValues(coll, request);
		return coll;
	}
	protected ArrayList<Object> extractLdfDataCollection(BaseForm form, HttpServletRequest request){
		ArrayList<Object> coll = form.getLdfCollection();	
		updateMultiselectLdfValues(coll, request);
		return coll;
	}
	
	/**
	 * Creates only Links, SubHeaders and Comments for Home Page 
	 * @param request
	 */
  	public void createLDFsForHomePage(HttpServletRequest request, Collection<Object>  list, String type) {
  		
  		if(list == null) {
	  		list = new ArrayList<Object> ();
	  		list = this.getDefinedFieldMetadataCollection(NEDSSConstants.HOME_PAGE_LDF, null, null, false, request);
  		}

  		StringBuffer sb = new StringBuffer();  		
		
  		if (list.size() > 0) {

			sb.append("<line>");
			sb.append("<element type=\"raw\" align=\"left\">");
			sb.append("<span>");
			sb.append("<br/>");
			sb.append("<br/>");
			sb.append("<img class=\"DarkGray\" border=\"0\" height=\"3\" width=\"100%\" src=\"tb_topstrip.jpg\" alt=\"\"/>");
			
			Iterator<Object> it = list.iterator();
			
			while (it.hasNext()) {

				sb.append("<br/>");
				sb.append("<br/>");

				StateDefinedFieldMetaDataDT metaDT = (StateDefinedFieldMetaDataDT) it.next();
				String dataType = metaDT.getDataType();
				String label_txt = metaDT.getLabelTxt();
				
				if(dataType != null && dataType.equalsIgnoreCase("LNK")) {
					
					int begin = label_txt.indexOf("(http");
					int end = label_txt.lastIndexOf(")");
					
					if(begin == -1 || end == -1)
						sb.append("<a href=\"#\"><![CDATA[").append(label_txt).append("]]></a>");		
					else
						sb.append("<a href=\"").append(label_txt.substring(begin+1, end)).append("\"  target=\"_blank\"><![CDATA[").append(label_txt.substring(0, begin)).append("]]></a>");
					
				} else if(dataType != null && dataType.equalsIgnoreCase("SUB")) {
					
					sb.append("<span class=\"boldTwelveDkBlue\"><![CDATA[").append(label_txt).append("]]></span>");
					sb.append("<br/>");
					sb.append("<img class=\"DarkGray\" border=\"0\" height=\"2\" width=\"100%\" src=\"transparent.gif\" alt=\"\"/>");
					
					
				} else if(dataType != null && dataType.equalsIgnoreCase("COM")) {
					sb.append("<![CDATA[").append(label_txt).append("]]>");
				}
			}
			sb.append("<br/>");
			sb.append("</span>");
			sb.append("</element>");
			sb.append("</line>");

  		} else {
  			sb.append("<line></line>");
		}
  		if(type.equals("extXSP"))
  			request.setAttribute(NEDSSConstants.HOME_PAGE_LDF + type, sb.toString());
  		else
  			request.setAttribute("previewXSP", sb.toString());	
  			
  	}
	
}
