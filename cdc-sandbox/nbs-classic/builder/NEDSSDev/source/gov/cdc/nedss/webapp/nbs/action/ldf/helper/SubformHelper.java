package gov.cdc.nedss.webapp.nbs.action.ldf.helper;

import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.ldf.vo.LdfBaseVO;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.*;
import gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupHome;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.ldf.subform.ejb.subformmetadataejb.bean.*;
import gov.cdc.nedss.ldf.subform.dt.CustomSubformMetadataDT;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.act.observation.vo.ObservationVO;

import java.io.*;
import java.util.*;

import javax.rmi.*;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 
 * <p>
 * Title: SubformHelper
 * </p>
 * <p>
 * Description: Helper class to extract appropriate Subforms in Add Edit Page
 * display
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: CSC
 * </p>
 * 
 * @author Narendra Mallela
 * @version 1.0
 */
public class SubformHelper {

	/**
	 * 
	 * @param businessObjectName
	 * @param bVO
	 * @param conditionCd
	 * @param request
	 * @throws Exception
	 */
	private static SubformHelper sInstance = null;

	/**
	 * 
	 * @return SubformHelper
	 */
	public static synchronized SubformHelper getInstance() {

		if (sInstance == null) {
			sInstance = new SubformHelper();
		}

		return sInstance;
	}

	/**
	 * 
	 * @param businessObjectName
	 * @param bVO
	 * @param conditionCd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Collection<Object>  extractSubforms(LdfBaseVO boVO,
			String businessObjectName, Long boUid, String conditionCd,
			HttpServletRequest request) throws Exception {

		if (boUid == null) {

			Collection<Object>  list = this.retriveSubforms(businessObjectName,
					conditionCd, request);
			if (list.size() > 0) {
				if (conditionCd == null || conditionCd.trim().length() == 0)
					request.setAttribute(businessObjectName + "SubformList",
							list);
				else
					request.setAttribute(businessObjectName
							+ "conditionSubformList", list);
				renderJSFunctions(list, request);
			}
			return list;
		} else {

			MainSessionCommand msCommand = null;
			Collection<Object>  list = new ArrayList<Object> ();
			String sBeanJndiName = JNDINames.DEFINED_FIELD_SUBFORMGROUP_EJB;
			String sMethod = "getGroup";
			Object[] oParams = { boUid };

			HttpSession session = request.getSession(true);
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);

			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {

				gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup group = (gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup) resultUIDArr
						.get(0);

				if (group.getSubformUids() != null
						&& group.getSubformUids().size() > 0) {

					putSubformMetaDataInRequest(boVO, group.getSubformUids(),
							businessObjectName, conditionCd, request);
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param businessObjectName
	 * @param boUid
	 * @param conditionCd
	 * @param request
	 * @return java.util.Collection
	 * @throws Exception
	 */
	public Collection<Object>  extractSubforms(String businessObjectName, Long boUid,
			String conditionCd, HttpServletRequest request) throws Exception {

		return extractSubforms(null, businessObjectName, boUid, conditionCd,
				request);

	}

	/**
	 * 
	 * @param businessObjectName
	 * @param conditionCd
	 * @param request
	 * @throws Exception
	 */
	private Collection<Object>  retriveSubforms(String businessObjectName,
			String conditionCd, HttpServletRequest request) throws Exception {
		MainSessionCommand msCommand = null;
		Collection<Object>  list = new ArrayList<Object> ();
		String sBeanJndiName = JNDINames.SUBFORMMetaDataEJB;
		String sMethod = "";
		Object[] oParams = {};

		if (conditionCd != null && conditionCd.trim().length() > 0
				&& !conditionCd.equalsIgnoreCase("null")) {
			Object oParams1[] = { businessObjectName, conditionCd };
			oParams = oParams1;
			sMethod = "getSubformMetaDataByBusinessObjAndCondition";
		} else {
			Object oParams2[] = { businessObjectName };
			oParams = oParams2;
			sMethod = "getSubformMetaDataByBusinessObject";
		}

		HttpSession session = request.getSession(true);
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		resultUIDArr = msCommand
				.processRequest(sBeanJndiName, sMethod, oParams);

		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			list = (Collection<Object>) resultUIDArr.get(0);
		}
		return list;
	}

	/**
	 * 
	 * @param boUid
	 * @param businessObjectName
	 * @param request
	 * @throws Exception
	 */
	private void putSubformMetaDataInRequest(LdfBaseVO bVO,
			Collection<Long>  subformUids, String businessObjectName,
			String conditionCd, HttpServletRequest request) throws Exception {

		//if the contextAction is printPage, we need to replace xhtml with xsp
		// to display in PDF
		String contextAction = request.getParameter("ContextAction");
		if (contextAction == null) {
			contextAction = (String) request.getAttribute("ContextAction");
		}

		MainSessionCommand msCommand = null;
		Collection<Object>  list = new ArrayList<Object> ();
		String sBeanJndiName = JNDINames.SUBFORMMetaDataEJB;
		String sMethod = "getSubformMetaDataByUidsAndCondition";
		Object[] oParams = { subformUids, conditionCd };

		HttpSession session = request.getSession(true);
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		resultUIDArr = msCommand
				.processRequest(sBeanJndiName, sMethod, oParams);

		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			list = (Collection<Object>) resultUIDArr.get(0);
		}
		//System.out.println("list size from
		// putSubformMetaDataForBusinessObject = " + list.size());
		if (list.size() > 0)
			renderJSFunctions(list, request);

		//for Print PDF
		if (contextAction != null
				&& contextAction.trim().equalsIgnoreCase("PrintPage")
				&& list.size() > 0) {
			makeSubformXSPForPrintPDF(bVO, businessObjectName, conditionCd,
					list, request);
		} else {

			if (conditionCd == null)
				request.setAttribute(businessObjectName + "SubformList", list);
			else
				request.setAttribute(businessObjectName
						+ "conditionSubformList", list);
		}

	}

	public void makeSubformTabsForAdminPage(String boName, String conditionCd,
			HttpServletRequest request) throws Exception {

		//retrieve the subformList by business Object or by BO and ConditionCd
		Collection<Object>  subformList = retriveSubforms(boName, conditionCd, request);
		request.setAttribute("subformMetaDataList", subformList);

		for (Iterator<Object> anIter = subformList.iterator(); anIter.hasNext();) {
			CustomSubformMetadataDT dt = (CustomSubformMetadataDT) anIter
					.next();
			String subformName = dt.getSubformNm();

			//now, retrieve the ldfMetaDataCollection  for the subform by
			// subformUid
			Collection<?>  ldfMetaDataList = getLDFMetaDataBySubformUid(dt
					.getCustomSubformMetadataUid(), request);

			//we know for sure, subform has its metadata in LDFMetaData table,
			// so now build batch-entry string and put it in request
			if (ldfMetaDataList.size() > 0) {
				makeBatchEntryListForRequest(subformName, ldfMetaDataList,
						request);

			}
		}

	}

	public void updateSubformMetaDataForPreview(String boName,
			String conditionCd, Collection<?>  metaDataColl,
			HttpServletRequest request) throws Exception {

		Collection<Object>  subformList = retriveSubforms(boName, conditionCd, request);

		for (Iterator<?> anIter = subformList.iterator(); anIter.hasNext();) {
			CustomSubformMetadataDT dt = (CustomSubformMetadataDT) anIter
					.next();
			String subformName = dt.getSubformNm();
			//now, retrieve the ldfMetaDataCollection  for the subform by
			// subformUid
			Collection<?>  ldfMetaDataList = getLDFMetaDataBySubformUid(dt
					.getCustomSubformMetadataUid(), request);
			//metaDataColl.addAll(ldfMetaDataList);
			metaDataColl.addAll(new ArrayList(ldfMetaDataList));
		}
	}

	private Collection<?>  getLDFMetaDataBySubformUid(Long subformUid,
			HttpServletRequest request) throws Exception {

		MainSessionCommand msCommand = null;
		Collection<?>  list = new ArrayList<Object> ();
		String sBeanJndiName = JNDINames.LDFMetaData_EJB;
		String sMethod = "getLDFMetaDataBySubformUid";
		Object[] oParams = { subformUid };

		HttpSession session = request.getSession(true);
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		resultUIDArr = msCommand
				.processRequest(sBeanJndiName, sMethod, oParams);

		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			list = (Collection<?>) resultUIDArr.get(0);
			return list;
		}
		return null;
	}

	private void makeBatchEntryListForRequest(String name,
			Collection<?>  ldfMetaData, HttpServletRequest request) {

		StringBuffer metaDataBatchEntry = new StringBuffer("");

		for (Iterator<?> anIter = ldfMetaData.iterator(); anIter.hasNext();) {
			StateDefinedFieldMetaDataDT metaDataDT = (StateDefinedFieldMetaDataDT) anIter
					.next();
			metaDataBatchEntry.append(buildBatchString(name, metaDataDT));
		}
		//System.out.println("metaDataBatchEntry for " + name + "== \n\n\n" +
		// metaDataBatchEntry.toString());

		request.setAttribute(name + "_subformMetaData", metaDataBatchEntry
				.toString());
	}

	private String buildBatchString(String name,
			StateDefinedFieldMetaDataDT metaDataDT) {

		StringBuffer singleMetaDataBS = new StringBuffer("");
		//label
		singleMetaDataBS.append(name).append("LDF100").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getLabelTxt() == null ? ""
				: metaDataDT.getLabelTxt());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//display width
		singleMetaDataBS.append(name).append("LDF105").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getFieldSize() == null ? ""
				: metaDataDT.getFieldSize());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//type
		singleMetaDataBS.append(name).append("LDF101").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getDataType() == null ? ""
				: metaDataDT.getDataType());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//SRT code set
		singleMetaDataBS.append(name).append("LDF102").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getCodeSetNm() == null ? ""
				: metaDataDT.getCodeSetNm());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//Validation type
		singleMetaDataBS.append(name).append("LDF103").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getValidationTxt() == null ? ""
				: metaDataDT.getValidationTxt());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//required
		singleMetaDataBS.append(name).append("LDF104").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getRequiredInd() == null ? ""
				: metaDataDT.getRequiredInd());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//Display order
		singleMetaDataBS.append(name).append("LDF108").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS
				.append(metaDataDT.getDisplayOrderNbr() == null ? new Integer(0)
						: metaDataDT.getDisplayOrderNbr());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//Administrative Comments
		singleMetaDataBS.append(name).append("LDF106").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getAdminComment() == null ? ""
				: metaDataDT.getAdminComment());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//Source
		singleMetaDataBS.append(name).append("LDF115").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getClassCd() == null ? ""
				: metaDataDT.getClassCd());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//National Identifier
		singleMetaDataBS.append(name).append("LDF116").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getCdcNationalId() == null ? ""
				: metaDataDT.getCdcNationalId());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//Object Identifier (OID)
		singleMetaDataBS.append(name).append("LDF127").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getLdfOid() == null ? ""
				: metaDataDT.getLdfOid());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		//NND message indicator
		singleMetaDataBS.append(name).append("LDF128").append(
				NEDSSConstants.BATCH_PART);
		singleMetaDataBS.append(metaDataDT.getNndInd() == null ? ""
				: metaDataDT.getNndInd());
		singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

		// end of row delimiter
		singleMetaDataBS.append(NEDSSConstants.BATCH_LINE);
		return singleMetaDataBS.toString();

	}

	private void renderJSFunctions(Collection<Object>  list, HttpServletRequest request) {

		StringBuffer uidBuffer = new StringBuffer();
		for (Iterator<Object> anIter = list.iterator(); anIter.hasNext();) {
			CustomSubformMetadataDT dt = (CustomSubformMetadataDT) anIter
					.next();
			uidBuffer.append(dt.getCustomSubformMetadataUid()).append("|");
		}

		String loadSubformJS = (String) request.getAttribute("loadSubformJS");
		String saveSubformJS = (String) request.getAttribute("saveSubformJS");
		String validateSubformJS = (String) request
				.getAttribute("validateSubformJS");
		String disableSubformJS = (String) request
				.getAttribute("disableSubformJS");
		String enableSubformJS = (String) request
				.getAttribute("enableSubformJS");
		String clearSubformJS = (String) request.getAttribute("clearSubformJS");

		if (loadSubformJS != null || saveSubformJS != null
				|| validateSubformJS != null || disableSubformJS != null
				|| enableSubformJS != null || clearSubformJS != null) {

			//load
			StringTokenizer loadSt = new StringTokenizer(uidBuffer.toString(),
					"|");
			while (loadSt.hasMoreTokens()) {
				loadSubformJS = addString(loadSubformJS, "\n", makeUidFunction(
						loadSt.nextToken(), "loadData_"));
			}
			request.setAttribute("loadSubformJS", loadSubformJS);

			//save
			StringTokenizer saveSt = new StringTokenizer(uidBuffer.toString(),
					"|");
			while (saveSt.hasMoreTokens()) {
				saveSubformJS = addString(saveSubformJS, "\n", makeUidFunction(
						saveSt.nextToken(), "saveData_"));
			}
			request.setAttribute("saveSubformJS", saveSubformJS);

			//validate
			StringTokenizer validateSt = new StringTokenizer(uidBuffer
					.toString(), "|");
			while (validateSt.hasMoreTokens()) {
				validateSubformJS = addString(validateSubformJS, "\n",
						makeValidateUidFunction(validateSt.nextToken(),
								"validateData_"));
			}
			request.setAttribute("validateSubformJS", validateSubformJS);

			//disable
			StringTokenizer disableSt = new StringTokenizer(uidBuffer
					.toString(), "|");
			while (disableSt.hasMoreTokens()) {
				disableSubformJS = addString(disableSubformJS, "\n",
						makeUidFunction(disableSt.nextToken(), "disableData_"));
			}
			request.setAttribute("disableSubformJS", disableSubformJS);

			//enable
			StringTokenizer enableSt = new StringTokenizer(
					uidBuffer.toString(), "|");
			while (enableSt.hasMoreTokens()) {
				enableSubformJS = addString(enableSubformJS, "\n",
						makeUidFunction(enableSt.nextToken(), "enableData_"));
			}
			request.setAttribute("enableSubformJS", enableSubformJS);

			//clear
			StringTokenizer clearSt = new StringTokenizer(uidBuffer.toString(),
					"|");
			while (clearSt.hasMoreTokens()) {
				clearSubformJS = addString(clearSubformJS, "\n",
						makeUidFunction(clearSt.nextToken(), "clearData_"));
			}
			request.setAttribute("clearSubformJS", clearSubformJS);

		} else {

			StringBuffer loadSB = new StringBuffer();
			StringBuffer saveSB = new StringBuffer();
			StringBuffer validateSB = new StringBuffer();
			StringBuffer disableSB = new StringBuffer();
			StringBuffer enableSB = new StringBuffer();
			StringBuffer clearSB = new StringBuffer();

			//load function
			loadSB.append("function callLoadData() { ");
			StringTokenizer st = new StringTokenizer(uidBuffer.toString(), "|");
			while (st.hasMoreTokens()) {
				loadSB.append(makeUidFunction(st.nextToken(), "loadData_"));
			}
			loadSB.append("}");

			//save function
			saveSB.append("function callSaveData() { ");
			StringTokenizer st1 = new StringTokenizer(uidBuffer.toString(), "|");
			while (st1.hasMoreTokens()) {
				saveSB.append(makeUidFunction(st1.nextToken(), "saveData_"));
			}
			saveSB.append("}");

			//validate function
			validateSB.append("function callValidateData() { ");
			validateSB.append("return ");
			StringTokenizer st2 = new StringTokenizer(uidBuffer.toString(), "|");
			while (st2.hasMoreTokens()) {
				//validateSB.append("alert('------------->>>' +
				// (validateData_314370032()&&validateData_314378038()));");
				validateSB.append(makeValidateUidFunction(st2.nextToken(),
						"validateData_"));
			}

			validateSB.append("}");

			//disable function
			disableSB.append("function callDisableData() { ");
			StringTokenizer st3 = new StringTokenizer(uidBuffer.toString(), "|");
			while (st3.hasMoreTokens()) {

				disableSB.append(makeUidFunction(st3.nextToken(),
						"disableData_"));
			}
			disableSB.append("}");

			//enable function
			enableSB.append("function callEnableData() { ");
			StringTokenizer st4 = new StringTokenizer(uidBuffer.toString(), "|");
			while (st4.hasMoreTokens()) {

				enableSB
						.append(makeUidFunction(st4.nextToken(), "enableData_"));
			}
			enableSB.append("}");

			//clear function
			clearSB.append("function callClearData() { ");
			StringTokenizer st5 = new StringTokenizer(uidBuffer.toString(), "|");
			while (st5.hasMoreTokens()) {

				clearSB.append(makeUidFunction(st5.nextToken(), "clearData_"));
			}
			clearSB.append("}");

			request.setAttribute("loadSubformJS", loadSB.toString());
			request.setAttribute("saveSubformJS", saveSB.toString());
			request.setAttribute("validateSubformJS", validateSB.toString());
			request.setAttribute("disableSubformJS", disableSB.toString());
			request.setAttribute("enableSubformJS", enableSB.toString());
			request.setAttribute("clearSubformJS", clearSB.toString());

		}
	}

	private String makeValidateUidFunction(String subformUid, String param1) {

		String separator = "\n";
		return (param1.concat(subformUid.toString()).concat("() & ")
				.concat(separator));

	}

	private String makeUidFunction(String subformUid, String param1) {

		String separator = "\n";
		return (param1.concat(subformUid.toString()).concat("();")
				.concat(separator));

	}

	private void makeSubformXSPForPrintPDF(LdfBaseVO bVO, String boName,
			String condCd, Collection<Object>  list, HttpServletRequest request)
			throws Exception {

		Collection<Object>  ldfData = bVO.getTheStateDefinedFieldDataDTCollection();

		ArrayList<Object> printSubformGroups = new ArrayList<Object> ();
		for (Iterator<Object> anIter = list.iterator(); anIter.hasNext();) {
			CustomSubformMetadataDT dt = (CustomSubformMetadataDT) anIter
					.next();
			StringBuffer sb = new StringBuffer();
			sb.append("\n<group name=\"");
			sb.append(dt.getSubformNm());
			sb.append("\">\n");
			//once u display the group, get the metadata by subform UID and
			// build xsp
			Collection<?>  ldfMetaData = getLDFMetaDataBySubformUid(dt
					.getCustomSubformMetadataUid(), request);
			for (Iterator<?> ldfIter = ldfMetaData.iterator(); ldfIter.hasNext();) {
				StateDefinedFieldMetaDataDT sdfmDT = (StateDefinedFieldMetaDataDT) ldfIter
						.next();
				String value = "";
				for (Iterator<Object> ldfDIter = ldfData.iterator(); ldfDIter.hasNext();) {
					StateDefinedFieldDataDT dataDT = (StateDefinedFieldDataDT) ldfDIter
							.next();
					if (sdfmDT.getLdfUid().compareTo(dataDT.getLdfUid()) == 0) {
						value = dataDT.getLdfValue();
						if (value.endsWith("|]]>")) {
							value = this.replaceString(value, "|]]>", "]]>");
						}
					}
				}
				sb.append("<line>\n<element label=\"");
				sb.append(sdfmDT.getLabelTxt());
				sb.append("\" id=\"");
				sb.append(sdfmDT.getLdfOid());
				sb.append("\"");
				sb.append(" type=\"");
				if (sdfmDT.getDataType() != null
						&& sdfmDT.getDataType().equalsIgnoreCase("CV"))
					sb.append("subform-select");
				else
					sb.append("subform-text");
				sb.append("\">\n<value>");
				sb.append(value);
				sb.append("</value>\n</element>\n</line>\n");
			}

			sb.append("</group>");
			printSubformGroups.add(sb.toString());
		}
		if (condCd == null || condCd.trim().length() == 0)
			request
					.setAttribute(boName + "printSubformXSP",
							printSubformGroups);
		else
			request.setAttribute(boName + "printConditionSubformXSP",
					printSubformGroups);

	}

	private static String addString(String str, String find, String add) {
		StringBuffer sb = new StringBuffer(str);
		;
		int pos = str.length();
		while (pos > -1) {
			pos = str.lastIndexOf(find, pos);
			if (pos > -1) {
				//sb.append(add);
				sb.replace(pos, pos + find.length(), add);
			}
			pos = pos - find.length();
		}
		return sb.toString();
	}

	private static String replaceString(String str, String find, String replace) {
		StringBuffer sb = new StringBuffer(str);
		;
		int pos = str.length();
		while (pos > -1) {
			pos = str.lastIndexOf(find, pos);
			if (pos > -1)
				sb.replace(pos, pos + find.length(), replace);
			pos = pos - find.length();
		}
		return sb.toString();
	}

}