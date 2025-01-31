package gov.cdc.nedss.nnd.helper;

import java.io.*;
import java.rmi.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.ejb.*;
import javax.rmi.*;
import javax.swing.*;

import java.text.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.nnd.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.nnd.dt.*;
import gov.cdc.nedss.ldf.subform.dt.*;
/**
 * <p>Title: NND LDF Extraction</p>
 * <p>Description: This class controls the migration of nnd ldf extraction</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Joe Daughtery
 */

public class NNDLDFExtraction {
  private MainSessionCommand mainSessionCommand;
  private ArrayList<Object>  nndActivityLogList;
  private ArrayList<?>  transportQOutDtList;
  private NBSSecurityObj securityObj;
  private Timestamp time;
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  /**
   * default constructor which establishes a new ArrayList.
   */
  public NNDLDFExtraction() {
    nndActivityLogList = new ArrayList<Object> ();
  }
  
  /**
   * Kicks off the nnd ldf extraction.
   * @param args String[]
   */
  public final static void main(String[] args) {
    NNDLDFExtraction ldfExt = new NNDLDFExtraction();
    String stateCode = propertyUtil.getStateSiteCode();

    if(stateCode == null || stateCode.equals("") || stateCode.equals(" ")) {
      JOptionPane.showMessageDialog(null, NNDConstantUtil.STATE_SITE_CODE_NOT_SET + new Timestamp((new Date()).getTime()), "alert", JOptionPane.ERROR_MESSAGE);
      System.out.println(NNDConstantUtil.STATE_SITE_CODE_NOT_SET_PRINT_OUT + new Timestamp((new Date()).getTime()));
      System.exit(0);
    }
    ldfExt.processLDFMetaData(stateCode);
  }//end of main

  /**
   * This method kick starts the NND LDF Extraction.  It will result in the selection of
   * multiple records in the ODS database, migrate those records to the MsgOut database,
   * create activity log for this migration, and update existing activity logs based on
   * the PHINMS migration of NND LDF extraction records in the MsgOut database.
   * @param stateCode String
   */
  public void processLDFMetaData(String stateCode) {
    try {
      mainSessionCommand = getMainSessionCommand();

      String jndiName = JNDINames.LDFMetaData_EJB;
      String methodName = NNDConstantUtil.LDF_METADATA_EJB_METHOD_GET_ALL_LDF_META_DATA;
      Object[] params = new Object[] {};
      Collection<?> list = (Collection <?>)mainSessionCommand.processRequest(jndiName, methodName, params).get(0);
      prepareNNDLDFData(list, stateCode);
      //see javadoc for checkTransportQOutDone()
      checkTransportQOutDone();
      //writeNNDActivityLog throws exception so it will skip updateTransportQOutDone()
      writeNNDActivityLog();
      //see javadoc for checkTransportQOutDone();
      if(transportQOutDtList.size() > 0)
       updateTransportQOutDone();
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if(mainSessionCommand != null)
          mainSessionCommand.remove();
      } catch(Exception e) {
        e.printStackTrace();
      }//end
    }
  }

  /**
   * Obtains all ldf meta data records available for extraction.
   * @param coll
   * @return HashMap
   * @throws Exception
   */
  private HashMap<Object, Object> getSubformMetaData(Collection<Object> coll) throws Exception{
    Iterator<Object> it = coll.iterator();
    HashMap<Object, Object> subformOidMap = new HashMap<Object, Object>();
    ArrayList<Long> list = new ArrayList<Long>();
    try {
    if(coll != null && coll.size() > 0) {
      while (it.hasNext()) {
        StateDefinedFieldMetaDataDT dt = (StateDefinedFieldMetaDataDT) it.next();
        if(dt.getCustomSubformMetadataUid() != null && !subformOidMap.containsKey(dt.getCustomSubformMetadataUid())) {
          subformOidMap.put(dt.getCustomSubformMetadataUid(),
                            dt.getCustomSubformMetadataUid());
          list.add(dt.getCustomSubformMetadataUid());
        }//end of if
      }//end of while
      if(subformOidMap.size() > 0) {
        if (mainSessionCommand == null)
          mainSessionCommand = getMainSessionCommand();
        String jndiName = JNDINames.SUBFORMMetaDataEJB;
        String methodName = NNDConstantUtil.OBTAIN_SUBFORM_DATA_METHOD;
        //Collection<Object>  oidColl = subformOidMap.values();
        Object[] params = new Object[] {list};
        Collection<?> customSubformMetaDataDtColl = (Collection<?>)
            mainSessionCommand.processRequest(jndiName, methodName, params).
            get(0);
        Iterator<?> itI = customSubformMetaDataDtColl.iterator();
        while (itI.hasNext()) {
          CustomSubformMetadataDT dt = (CustomSubformMetadataDT) itI.next();
          subformOidMap.put(dt.getCustomSubformMetadataUid(),
                            dt.getSubformOid());
        } //end of while
      }//end of if
    }//end of if
    } catch(Exception e) {
      e.printStackTrace();
      prepareNNDActivityLog(e, (ArrayList<Object> )coll);
      //write the log and exit.
      writeNNDActivityLog();
      System.exit(0);
    }//end of catch
    return subformOidMap;
  }

  /**
   * Updates the ldf meta data records which were selected for migration to
   * ldf_processed so that they are not selected for subsequent migrations.
   * @throws Exception
   */
  private void updateTransportQOutDone() throws Exception{
    if(mainSessionCommand == null)
      mainSessionCommand = getMainSessionCommand();

    String jndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
    String methodName = NNDConstantUtil.SET_TRANSPORTQ_OUT;

    for(int k = 0; k < transportQOutDtList.size(); k++) {
      TransportQOutDT dt = (TransportQOutDT)transportQOutDtList.get(k);
      dt.setApplicationStatus(NNDConstantUtil.LDF_LOGGED);
      dt.setItDirty(true);
      dt.setItNew(false);
      Object[] params = new Object[] {dt};
      mainSessionCommand.processRequest(jndiName, methodName, params);
    }//end of for
  }


  /**
   * Creates records in the NNDActivityLog table regarding the success or failure of
   * the migration process.  Also results in the update of existing records to indicate that
   * the transportQOut record associated with the nndActivityLog record has been succesfully or
   * failed to be migrated by PHINMS.
   * @throws Exception This should be thrown such that calling logic will skip
   * over updateTransportQOutDone
   */
  private void writeNNDActivityLog()throws Exception {
    if(mainSessionCommand == null) {
      mainSessionCommand = getMainSessionCommand();
    }
    String jndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
    String methodName = NNDConstantUtil.PERSIST_NND_ACTIVITY;
    Object[] params = new Object[] {nndActivityLogList};

    mainSessionCommand.processRequest(jndiName, methodName, params);

  }

  /**
   * Called by this.processLDFMetaData(...)
   * This method will obtain all TransportQOut records where the
   * PROCESSINGSTATUS is set to "done".  The method will result in the
   * creation of an NNDActivityLogDt for each and add the dt to the collection
   * to be saved to the nnd activity log table.
   *
   * If an exception is thrown this method will catch and recover from the exception to
   * allow the system to log a success or failure of the NNDLDFExtraction process. This
   * method simply looks to see if the records created in the TransportQ_Out have been
   * processed by PHINMS.
   *
   * The only Exception that will be thrown will be where we attempt to recreate the
   * stub for the mainSessionCommand.  It will be corrupted as a result of an exception being thrown.
   * Since we are wanting to recover, we need to recreate the stub.
   */
  private void checkTransportQOutDone() throws Exception{
    try {
      if(time == null)
      time = new Timestamp((new Date()).getTime());
      if(mainSessionCommand == null)
        mainSessionCommand = getMainSessionCommand();
      String jndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
      String methodName = NNDConstantUtil.CHECK_TRANSPORT_Q_OUT_DONE;
      Object[] params = new Object[] {propertyUtil.getNBSLDFVersion()};
      transportQOutDtList = (ArrayList<?> )mainSessionCommand.processRequest(jndiName, methodName, params).get(0);
      for(int k = 0; k < transportQOutDtList.size(); k++) {
        TransportQOutDT outDt = (TransportQOutDT)transportQOutDtList.get(k);
        NNDActivityLogDT dt = new NNDActivityLogDT();
        String message = outDt.getPayloadContent();
        dt.setItDelete(false);
        dt.setItDirty(true);
        dt.setItNew(false);
        dt.setLocalId(outDt.getRecordId().toString());
        dt.setRecordStatusTime(time);

        dt.setNndActivityLogSeq(new Integer(0));
        dt.setErrorMessageTxt(NNDConstantUtil.TRANSPORT_Q_OUT_SUCCESSFUL_NNDACTIVITYLOG_CREATE_FAIL);
        dt.setService(propertyUtil.getNBSLDFVersion());

        dt.setStatusCd(outDt.getTransportStatus().equals(NNDConstantUtil.PHINMS_SUCCESS)?NNDConstantUtil.NND_ACTIVITY_LOG_STATUS_CD_SUCCESS:NNDConstantUtil.NND_ACTIVITY_LOG_STATUS_CD_FAILURE);
        dt.setStatusTime(time);

        dt.setRecordStatusCd(outDt.getTransportStatus().equals(NNDConstantUtil.PHINMS_SUCCESS)?NNDConstantUtil.TRANSPORT_COMPLETE:NNDConstantUtil.TRANSPORT_FAIL);


        nndActivityLogList.add(dt);
      }//end of for

    } catch(Exception e) {
      e.printStackTrace();
      //Since we want to recover and continue, the exception thrown resulted in
      //corruption of the existing stub for the mainSessionCommand, so we
      //must recreat it.
      mainSessionCommand = getMainSessionCommand();

     //do not throw the exception.  We want to recover and write the
     //nndActivityLogList to the nnd activity log table
    }
  }

  /**
   * Selects those records which are not set to "LDF_PROCESSED" and calles supporting methods
   * to prepare and store the records to msgOut.
   * @param coll
   * @param stateCd
   * @throws Exception
   */
  private void prepareNNDLDFData(Collection<?> coll, String stateCd)throws Exception {
    Iterator<?> it = coll.iterator();
    ArrayList<Object>  list = new ArrayList<Object> ();
    while(it.hasNext()) {
      StateDefinedFieldMetaDataDT dt = (StateDefinedFieldMetaDataDT)it.next();
      if(!dt.getRecordStatusCd().equalsIgnoreCase(NNDConstantUtil.LDF_PROCESSED)) {
        list.add(dt);
      }//end of if
    }//end of while
    if(list.size() > 0) {
      HashMap<Object, Object> subformOidMap = getSubformMetaData(list);
      if(writeData(list, stateCd, subformOidMap))
        updateRecordStatusCd(list, NNDConstantUtil.LDF_PROCESSED);
    }//end of if
  }
  /**
   * Results in the update of the RecordStatusCd of the state defined field meta data records.
   * @param list
   * @param processCd
   * @throws Exception
   */
  private void updateRecordStatusCd(ArrayList<Object>  list, String processCd) throws Exception {
    if(list.size() > 0) {
      if(mainSessionCommand == null)
        mainSessionCommand = getMainSessionCommand();
      String jndiName = JNDINames.LDFMetaData_EJB;
      String methodName = NNDConstantUtil.LDF_METADATA_EJB_METHOD_SET_NND_LDF_PROCESS;
      Object[] params = new Object[] {list};
      for(int k = 0; k < list.size(); k++) {
        StateDefinedFieldMetaDataDT dt = (StateDefinedFieldMetaDataDT)list.get(k);

        dt.setRecordStatusCd(processCd);
        dt.setItDelete(false);
        dt.setItDirty(true);
        dt.setItNew(false);
      }//end of for
      try {
        mainSessionCommand.processRequest(jndiName, methodName, params);
      } catch(Exception e) {
        e.printStackTrace();
        //do not throw the Exception.  We want to continue processing the
        //the nndActivityLog records.
        mainSessionCommand = getMainSessionCommand();
      }
    }//end of if
  }

  /**
   * This will return a String with the following format
   * yyyy-mm-dd HH:mm:ss.0
   * @param time Timestamp
   * @return String
   */
  private String formatDateTime(Timestamp time) {
    String newFormat = null;
    DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.0");
    newFormat = df.format(time);

    return newFormat.trim();
  }
  /**
   * Prepares the data for inserting intot he msgOut and writes to the drive.
   * @param list
   * @param stateCd
   * @param subformOidMap
   * @return
   */
  private boolean writeData(ArrayList<Object>  list, String stateCd, HashMap<Object,Object> subformOidMap) {

    StringBuffer sbMain =  new StringBuffer();
    Iterator<Object> it = list.iterator();
    while(it.hasNext()) {
    StringBuffer sb = new StringBuffer();
      StateDefinedFieldMetaDataDT dt = (StateDefinedFieldMetaDataDT)it.next();
      sb.append(dt.getLdfUid() +"|"); //1
      //System.out.println("ldfuid = " + dt.getLdfUid());
      sb.append(stateCd == null?"NI|":stateCd +"|"); //2
     // System.out.println("state Cd " + stateCd);
      sb.append(dt.getActiveInd() == null?"NI|":dt.getActiveInd() +"|");//3
     // System.out.println("Active Ind " + dt.getActiveInd());
      sb.append(dt.getAddTime() == null?"NI|":formatDateTime(dt.getAddTime()) +"|");//4
      //System.out.println( "add time " + formatDateTime(dt.getAddTime()));
      sb.append(dt.getAdminComment() == null?"NI|":dt.getAdminComment() +"|");//5
     // System.out.println("adminComment " + dt.getAdminComment());
      sb.append(dt.getBusinessObjNm() == null?"NI|":dt.getBusinessObjNm() +"|");//6
     // System.out.println("business object " + dt.getBusinessObjNm());
      sb.append(dt.getCategoryType() == null?"NI|":dt.getCategoryType() +"|");//7
     // System.out.println("category type " + dt.getCategoryType());
      sb.append(dt.getCdcNationalId() == null?"NI|":dt.getCdcNationalId() +"|");//8
     // System.out.println("CDC Nat ID " + dt.getCdcNationalId());
      sb.append(dt.getClassCd() == null?"NI|":dt.getClassCd() +"|");//9
     // System.out.println("Class Cd " + dt.getClassCd());
      sb.append(dt.getCodeSetNm() == null?"NI|":dt.getCodeSetNm() +"|");//10
      //System.out.println("Code Set Nm " + dt.getCodeSetNm());
      sb.append(dt.getConditionCd() == null?"NI|":dt.getConditionCd() +"|");//11
     // System.out.println("Condition Cd " + dt.getConditionCd());
      sb.append(dt.getConditionDescTxt() == null?"NI|":dt.getConditionDescTxt() +"|");//12
      //System.out.println("Condition Desc " + dt.getConditionDescTxt());
      sb.append(dt.getDataType() == null?"NI|":dt.getDataType() +"|");//13
     // System.out.println("Data Type  " + dt.getDataType());
      // this is for deploymentCd
      sb.append("NI|");//14
     // System.out.println("Deployment CD " + dt.getDeploymentCd());
      sb.append(dt.getDisplayOrderNbr() == null?"NI|":dt.getDisplayOrderNbr() +"|");//15
      //System.out.println("Display Order nbr " + dt.getDisplayOrderNbr());
      sb.append(dt.getFieldSize() == null?"NI|":dt.getFieldSize() +"|");//16
      //System.out.println("Field Size  " + dt.getFieldSize());
      sb.append(dt.getLabelTxt() == null?"NI|":dt.getLabelTxt() +"|");//17
     // System.out.println("Label Txt " + dt.getLabelTxt());
      sb.append(dt.getLdfPageId() == null?"NI|":dt.getLdfPageId() +"|");//18
      //System.out.println("Ldf Page Id " + dt.getLdfPageId());
      sb.append(dt.getRequiredInd() == null?"NI|":dt.getRequiredInd() +"|");//19
      //System.out.println("Reguired Ind " + dt.getRequiredInd());
      sb.append(dt.getValidationTxt() == null?"NI|":dt.getValidationTxt() +"|");//20
      //System.out.println("Validation Txt " + dt.getValidationTxt());
      //the validationJScriptTxt field has carriage returns that are causing problems
      //with the display in the spreadsheet.  So we are not using it.
      //sb.append(dt.getValidationJscriptTxt() == null?"NI|":dt.getValidationJscriptTxt() +"|");
      sb.append("NI|");//SEE NOTE TWO LINES UP. LINE 141.//21
     // System.out.println("Valdiation Jscript Txt " + dt.getValidationJscriptTxt());
      sb.append(dt.getRecordStatusTime() == null?"NI|":formatDateTime(dt.getRecordStatusTime()) +"|");//22
     // System.out.println("Record Status Time  " + formatDateTime(dt.getRecordStatusTime()));
      sb.append(dt.getRecordStatusCd() == null?"NI|":dt.getRecordStatusCd() +"|");//23
     // System.out.println( "Record Status Cd " + dt.getRecordStatusCd());
      sb.append(dt.getCustomSubformMetadataUid() == null?"NI|":dt.getCustomSubformMetadataUid() +"|");//24
     // System.out.println("Custom Sub Form Meta Data Uid " + dt.getCustomSubformMetadataUid());
      sb.append(dt.getHtmlTag() == null?"NI|":dt.getHtmlTag() + "|");//25
     // System.out.println("Html Tag " + dt.getHtmlTag() );
      sb.append(dt.getImportVersionNbr() == null?"NI|":dt.getImportVersionNbr()+"|");//26
     // System.out.println("Import Version Nbr " + dt.getImportVersionNbr());
      sb.append(dt.getNndInd() == null?"NI|":dt.getNndInd()+"|");//27
      //System.out.println("NND IND  " + dt.getNndInd());
      sb.append(dt.getLdfOid() == null?"NI|":dt.getLdfOid()+"|");//28
      //System.out.println("LDF OID " + dt.getLdfOid());
      sb.append(dt.getVersionCtrlNbr() == null?"NI|":dt.getVersionCtrlNbr()+"|");//29
      //System.out.println("Version Ctrl Nbr " + dt.getVersionCtrlNbr());
      sb.append(subformOidMap.get(dt.getCustomSubformMetadataUid()) == null?"NI|":(subformOidMap.get(dt.getCustomSubformMetadataUid())) + "|");//30
      //System.out.println("subformoid " + subformOidMap.get(dt.getCustomSubformMetadataUid()) );
//      sb.append("\n");
     //System.out.println( "Before replacing carriage return " + sb.toString());
      sb =  new StringBuffer( this.replaceCarriageReturn(sb.toString()));
      sb.append("\n");
      sbMain.append(sb);
     //System.out.println( "After replacing carriage return " + sb.toString());


    }//end of while

    if(sbMain.length() > 0) {
     // System.out.println("String Buffer  " + sb.toString());
      File fileName = null;
      try {
        writeToDB(sbMain, list);
        try {
          fileName = writeToFile(sbMain);
        } catch(Exception e) {
          //even if we can not create the flat file, we still want to
          //keep the database insert.
          e.printStackTrace();
        }
        return true;
      } catch(Exception e) {
        e.printStackTrace();
        prepareNNDActivityLog(e, list);
        try {
          mainSessionCommand = getMainSessionCommand();
        } catch(Exception ex) {
          e.printStackTrace();
        }
        return false;
      }//end of catch
    } else {
      //do not create an nndActivityLog if no records to migrate from
      //ods to msgOut.transportQ_Out
      return false;
    }
  }//end of writeData

  /**
   * This method is called with a successfull nnd ldf extraction has occurred.
   * The recordId passed in will be that of the TransportQ_Out table.
   * @param recordId Long
   */
  private void prepareNNDActivityLog(Long recordId, ArrayList<Object> list) {
    StringBuffer message = new StringBuffer();
    message.append(NNDConstantUtil.NND_ACTIVITY_LOG_LDF_ERROR_MESSAGE_TXT_PREFIX+" =");
    NNDActivityLogDT dt = new NNDActivityLogDT();
    if(time == null)
      time = new Timestamp((new Date()).getTime());
    dt.setRecordStatusCd(NNDConstantUtil.LDF_PROCESSED);
    dt.setNndActivityLogSeq(new Integer(0));
    dt.setRecordStatusTime(time);
    dt.setItDelete(false);
    dt.setItDirty(false);
    dt.setItNew(true);
    dt.setLocalId(recordId.toString());
    dt.setStatusTime(time);
    dt.setStatusCd(NNDConstantUtil.NND_ACTIVITY_LOG_STATUS_CD_SUCCESS);
    dt.setService(propertyUtil.getNBSLDFVersion());
    for(int k = 0; k < list.size(); k++) {
      StateDefinedFieldMetaDataDT sdt = (StateDefinedFieldMetaDataDT)list.get(k);
      //if(k != (list.size() - 1)) {
        //2000 is the column limit in database
        if(message.length() < 2000 && (message.length() + String.valueOf(sdt.getLdfUid()).length() + 2) < 2000) {
          if(k != (list.size() - 1))
            message.append(sdt.getLdfUid() + ", ");
          else
            message.append(sdt.getLdfUid());
        } else {
          String msg = message.toString();
          msg = msg.trim();

          String strComma = msg.substring(msg.length() -1);

          if(strComma.equals(","))
            msg = msg.substring(0, (msg.length() - 1));
          dt.setErrorMessageTxt(msg.toString());
          nndActivityLogList.add(dt);
          prepareNNDActivityLog(NNDConstantUtil.LDF_PROCESSED, k, list, time, dt.getNndActivityLogSeq(), null, recordId.toString());
          return;
        }
      //}//end of if
    }//end of for
    String msg = message.toString();
        msg = msg.trim();
        String strComma = msg.substring(msg.length() -1);

    if(strComma.equals(","))
          msg = msg.substring(0, (msg.length() - 1));

    dt.setErrorMessageTxt(message.toString());

    nndActivityLogList.add(dt);
  }
  /**
   * Called when a failure to write to transportQ_Out.
   * @param e Exception
   * @param list ArrayList
   */
  private void prepareNNDActivityLog(Exception e, ArrayList<Object>  list) {
    StringBuffer message = new StringBuffer();
    message.append(NNDConstantUtil.NND_ACTIVITY_LOG_LDF_ERROR_MESSAGE_TXT_PREFIX+" =");
    NNDActivityLogDT dt = new NNDActivityLogDT();
    if(time == null)
      time = new Timestamp((new Date()).getTime());
    dt.setRecordStatusCd(NNDConstantUtil.EXTRACTION_FAILURE);
    dt.setRecordStatusTime(time);
    dt.setItDelete(false);
    dt.setItDirty(false);
    dt.setItNew(true);
    dt.setLocalId("1");
    dt.setStatusTime(time);
    dt.setStatusCd(NNDConstantUtil.NND_ACTIVITY_LOG_STATUS_CD_FAILURE);
    dt.setNndActivityLogSeq(new Integer(0));
    dt.setService(propertyUtil.getNBSLDFVersion());
    for(int k = 0; k < list.size(); k++) {
      StateDefinedFieldMetaDataDT sdt = (StateDefinedFieldMetaDataDT)list.get(k);
      //if(k != (list.size() - 1)) {
        //2000 is the column limit in database
        if(message.length() < 2000 && (message.length() + String.valueOf(sdt.getLdfUid()).length() + 2) < 2000) {
          if(k != (list.size() - 1))
            message.append(sdt.getLdfUid() + ", ");
          else
            message.append(sdt.getLdfUid());
        } else {
          String msg = message.toString();
          msg = msg.trim();
          String strComma = msg.substring(msg.length() -1);

          if(strComma.equals(","))
            msg = msg.substring(0, (msg.length() - 1));

          dt.setErrorMessageTxt(msg.toString());
          nndActivityLogList.add(dt);
          prepareNNDActivityLog(NNDConstantUtil.EXTRACTION_FAILURE, k, list, time, dt.getNndActivityLogSeq(), e, "1");
          return;
        }
      //} else {

      //}
    }//end of for
    String msg = message.toString();
    msg = msg.trim();

    String strComma = msg.substring(msg.length() -1);

    if(strComma.equals(","))
      msg = msg.substring(0, (msg.length() - 1));
    if(e != null)
      message.append("\n\nERROR_MESSAGE:\n" + e.getMessage());

    dt.setErrorMessageTxt(msg.toString());
    nndActivityLogList.add(dt);
  }

  /**
   * Only to be called when the ErrorMessageTxt field is more than 2000 characters.
   * @param index int
   * @param list ArrayList
   * @param time Timestamp
   * @param logSequence Integer
   * @param e Exception
   */
  private void prepareNNDActivityLog(String recordStatusCd, int index, ArrayList<Object> list, Timestamp time, Integer logSequence, Exception e, String localId) {
    StringBuffer message = new StringBuffer();
    message.append(NNDConstantUtil.NND_ACTIVITY_LOG_LDF_ERROR_MESSAGE_TXT_PREFIX+" =");
    NNDActivityLogDT dt = new NNDActivityLogDT();
    dt.setRecordStatusCd(recordStatusCd);
    dt.setRecordStatusTime(time);
    dt.setItDelete(false);
    dt.setItDirty(false);
    dt.setItNew(true);
    dt.setLocalId(localId != null?localId:NNDConstantUtil.DEFAULT_LOCAL_ID);
    dt.setStatusTime(time);
    dt.setStatusCd(e == null?NNDConstantUtil.NND_ACTIVITY_LOG_STATUS_CD_SUCCESS:NNDConstantUtil.NND_ACTIVITY_LOG_STATUS_CD_FAILURE);
    dt.setNndActivityLogSeq(new Integer(logSequence.intValue()+1));
    dt.setService(propertyUtil.getNBSLDFVersion());
    for(int k = index; k < list.size(); k++) {
      StateDefinedFieldMetaDataDT sdt = (StateDefinedFieldMetaDataDT)list.get(k);
      //if(k != (list.size() - 1)) {
        //2000 is the column limit in database
        if(message.length() < 2000 && (message.length() + String.valueOf(sdt.getLdfUid()).length() + 2) < 2000) {
          if(k != (list.size() - 1))
            message.append(sdt.getLdfUid() + ", ");
          else
            message.append(sdt.getLdfUid());
        } else {
          String msg = message.toString();
          msg = msg.trim();

          String strComma = msg.substring(msg.length() -1);

          if(strComma.equals(","))
            msg = msg.substring(0, (msg.length() - 1));
          dt.setErrorMessageTxt(msg.toString());
          nndActivityLogList.add(dt);
          prepareNNDActivityLog(recordStatusCd, k, list, time, dt.getNndActivityLogSeq(), e, dt.getLocalId());
          return;
        }//end of else

      //}//end of else

    }//end of for
    String msg = message.toString();
    msg = msg.trim();

    String strComma = msg.substring(msg.length() -1);

    if(strComma.equals(","))
      msg = msg.substring(0, (msg.length() - 1));
    if(e != null)
        message.append("\n\nERROR_MESSAGE:\n" + e.getMessage());
    dt.setErrorMessageTxt(msg.toString());
    nndActivityLogList.add(dt);
  }

  /**
   * This method will replace the '\r' with a ' '.
   * @param strData String
   * @return String
   */
  private String replaceCarriageReturn(String strData) {

  char[] ch = null;
  StringBuffer sb = new StringBuffer();
  if(strData != null && !strData.equals(" ") ) {
    ch = strData.toCharArray();
    for(int k = 0; k < ch.length; k++) {
      if(ch[k] == '\r' || ch[k] == '\n') {
        sb.append(' ');
      } else {
        sb.append(ch[k]);
      }
    }//end of for
  }//end of if
  if(sb.length() > 0) {
    return sb.toString();
  } else {
    return strData;
  }
}

  /**
   * Results in the insertion of a record into the MsgOut.TransportQ_Out table.
   * This should return null if
   * @param sb
   * @return
   * @throws Exception
   */
  private boolean writeToDB(StringBuffer sb, ArrayList<Object> list) throws Exception {
    //TransportQOutDAOImpl dao = new TransportQOutDAOImpl();
    TransportQOutDT dt = new TransportQOutDT();
    dt.setPayloadContent(sb.toString());
    dt.setRouteInfo(propertyUtil.getRouteInfo());
    dt.setService(propertyUtil.getNBSLDFVersion());
    dt.setAction(propertyUtil.getAction());
    dt.setEncryption(propertyUtil.getEncryption());
    dt.setSignature(propertyUtil.getSignature());
    dt.setPublicKeyLdapAddress(propertyUtil.getPublicKeyLDAPAddress());
    dt.setPublicKeyLdapBaseDN(propertyUtil.getPublicKeyLDAPBaseDN());
    dt.setPublicKeyLdapDN(propertyUtil.getPublicKeyLDAPDN());
    dt.setProcessingStatus(NNDConstantUtil.PROCESSING_STATUS);
    dt.setPriority(new Integer(1));
    dt.setDestinationFilename(propertyUtil.getDestinationFileName());
    dt.setItNew(true);
    dt.setItDirty(false);
    dt.setItDelete(false);
    if(mainSessionCommand == null)
        mainSessionCommand = getMainSessionCommand();
      String jndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
      String methodName = NNDConstantUtil.SET_TRANSPORTQ_OUT;
      Object[] params = new Object[] {dt};
      prepareNNDActivityLog((Long)mainSessionCommand.processRequest(jndiName, methodName, params).get(0), list);

    return true;
  }

  /**
   * Writes a "|" delimeted text file to the hard drive.
   * @param sb
   * @return
   * @throws Exception
   */
  private File writeToFile(StringBuffer sb)throws Exception {
    String fileName = NNDConstantUtil.NND_LDF_MESSAGE_FILE_NAME_PREFIX + (new Timestamp((new Date()).getTime()).toString());
    fileName = formatName(fileName);
    String fileLocationName = NNDConstantUtil.NND_LDF_ARCHIVE_DIRECTORY + fileName +".txt";
    File file = new File(fileLocationName);
    File dir = new File(NNDConstantUtil.NND_LDF_ARCHIVE_DIRECTORY);
    if(!dir.exists())
      dir.mkdirs();
    int k = 1;
    while(file.exists()) {
      Integer versionNbr = new Integer(k++);
      fileName = NNDConstantUtil.NND_LDF_MESSAGE_FILE_NAME_PREFIX + StringUtils.formatDate(new Timestamp((new Date()).getTime())) + "_Version_" + versionNbr.toString();
      fileName = formatName(fileName);
      fileLocationName = NNDConstantUtil.NND_LDF_ARCHIVE_DIRECTORY + fileName +".txt";
      file = new File(fileLocationName);
    }//end of while
    file.createNewFile();
    FileWriter fw = new FileWriter(file);

    PrintWriter out = new PrintWriter(new BufferedWriter(fw));
    out.write(sb.toString());
    out.flush();
    return file;
  }

  /**
   * Formats the fileName of the file being written to the harddrive.
   * @param fileName
   * @return
   */
  private String formatName(String fileName) {

    StringTokenizer toKen = new StringTokenizer(fileName);
    String newFileName = toKen.nextToken();
    newFileName = newFileName + "_" + toKen.nextToken();
    toKen = new StringTokenizer(newFileName, ":");
    for(int k = 0; toKen.hasMoreTokens(); k++) {
      if(k == 0)
        newFileName = toKen.nextToken();
      else
        newFileName = newFileName+"_" + toKen.nextToken();
    }


    toKen = new StringTokenizer(newFileName, ".");
    for(int x = 0; toKen.hasMoreTokens(); x++) {
      if(x == 0)
        newFileName = toKen.nextToken();
      else
        newFileName = newFileName+"_" + toKen.nextToken();
    }

    return newFileName;
  }

  /**
   * Establishes a connection to the MainSessionCommand and provides login data.
   * @return
   * @throws Exception
   */
  private MainSessionCommand getMainSessionCommand() throws Exception
        {
            NedssUtils nedssUtils = new NedssUtils();

            //if(mainSessionCommand != null) return mainSessionCommand;

            MainSessionCommandHome msCommandHome;

            try
            {
            	String sBeanName = JNDINames.MAIN_CONTROL_EJB;
                msCommandHome = (MainSessionCommandHome)PortableRemoteObject.narrow(nedssUtils.lookupBean(sBeanName), MainSessionCommandHome.class);
                mainSessionCommand = msCommandHome.create();
                securityObj = mainSessionCommand.nbsSecurityLogin(NNDConstantUtil.NND_BATCH_TO_CDC, NNDConstantUtil.NND_BATCH_TO_CDC);
                boolean check1 = securityObj.getPermission(NBSBOLookup.SYSTEM,
                NBSOperationLookup.LDFADMINISTRATION);
                if (check1 == false) {
                     throw new NEDSSSystemException("don't have permission for LDF Administration");
}

                return mainSessionCommand;
            }
            catch (ClassCastException e)
            {
                throw new Exception(e.toString());  //To change body of catch statement use Options | File Templates.
            }
            catch (NEDSSSystemException e)
            {
                throw new Exception(e.toString());  //To change body of catch statement use Options | File Templates.
            }
            catch (RemoteException e)
            {
                throw new Exception(e.toString());  //To change body of catch statement use Options | File Templates.
            }
            catch (CreateException e)
            {
                throw new Exception(e.toString());  //To change body of catch statement use Options | File Templates.
            }
        }

  public final static void runNNDLDFExtProcessor() {
	    NNDLDFExtraction ldfExt = new NNDLDFExtraction();
	    String stateCode = propertyUtil.getStateSiteCode();

	    if(stateCode == null || stateCode.equals("") || stateCode.equals(" ")) {
	      JOptionPane.showMessageDialog(null, NNDConstantUtil.STATE_SITE_CODE_NOT_SET + new Timestamp((new Date()).getTime()), "alert", JOptionPane.ERROR_MESSAGE);
	      System.out.println(NNDConstantUtil.STATE_SITE_CODE_NOT_SET_PRINT_OUT + new Timestamp((new Date()).getTime()));
	      System.exit(0);
	    }
	    ldfExt.processLDFMetaData(stateCode);
	  }//end of main


}
