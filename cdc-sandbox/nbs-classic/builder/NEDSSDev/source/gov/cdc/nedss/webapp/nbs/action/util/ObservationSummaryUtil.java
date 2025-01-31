package gov.cdc.nedss.webapp.nbs.action.util;



import java.util.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

/**
 * Title:        ObservationSummaryUtil
 * Description:  This action class is called to help build the summary strings
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Chris Hanosn
 * @version      1.0
 */

public class ObservationSummaryUtil
{

    static final LogUtils logger = new LogUtils(ObservationSummaryUtil.class.getName());

    /**
     * Takes a collection of resulted tests and formats the resulted tests with
     * various delimters to create the proper alignment
     *
     * @param resultedTestVOColl Collection
     * @param radioButtons boolean
     * @return String
     */
    public String convertResultedTest(Collection<Object> resultedTestVOColl, boolean radioButtons)
    {
      	StringBuffer buf1 = new StringBuffer(" ");
        StringBuffer buf2 = new StringBuffer(" ");
        StringBuffer finalBuf = new StringBuffer(" ");
        boolean firstIter = true;
        try {
        buf1.append("[[$");
	Iterator it  = resultedTestVOColl.iterator();
	while (it.hasNext()) {
          ResultedTestSummaryVO rvo = (ResultedTestSummaryVO) it.next();
          if (firstIter){
                  if (radioButtons)
                    buf1.append("~~");
                  else
                    buf1.append("~");
                  firstIter = false;
          }
          else{
                  buf1.append("~");
                  buf1.append("$");
                  if (radioButtons)
                   buf1.append("~~");
                 else
                   buf1.append("~");
          }
          // Resulted Tests
          buf1.append(rvo.getResultedTest()==null?"":rvo.getResultedTest() + "~");

          if (rvo.getCodedResultValue()!=null && rvo.getCtrlCdUserDefined1() != null && rvo.getCtrlCdUserDefined1().equals("N")){
                  buf1.append(rvo.getCodedResultValue()==null?"":(rvo.getCodedResultValue() + " "));
          }else if (rvo.getOrganismName() != null ){
                  buf1.append(rvo.getOrganismName()==null?"":(rvo.getOrganismName() + " "));
          }else if (rvo.getNumericResultCompare() != null || rvo.getNumericResultValue1() != null){
                  buf1.append(rvo.getNumericResultCompare()==null?"":(rvo.getNumericResultCompare()));
                  buf1.append(rvo.getNumericResultValue1()==null?"":(rvo.getNumericResultValue1().toString()));
                  buf1.append(rvo.getNumericResultSeperator()==null?"":(rvo.getNumericResultSeperator()));
                  buf1.append(rvo.getNumericResultValue2()==null?"":(rvo.getNumericResultValue2().toString()));
                  buf1.append(rvo.getNumericResultUnits()==null?"":(rvo.getNumericResultUnits() + " "));
          }else if (rvo.getTextResultValue() != null){
                  String resultValue = rvo.getTextResultValue();
                  String concatString = "";
                  int strLength = resultValue.length();
                  if (strLength <= 25)
                  {
                    concatString = resultValue;
                  }else
                  {
                    concatString = resultValue.substring(0,25);
                    concatString += "...";
                  }
                  buf1.append(rvo.getTextResultValue()==null?"":concatString);
          }

          // Susceptibilities
          if(rvo.getTheSusTestSummaryVOColl()!= null)
          {
           Iterator<Object>  susIter = rvo.getTheSusTestSummaryVOColl().iterator();
            while (susIter.hasNext())
            {
              ResultedTestSummaryVO rtsVO = (ResultedTestSummaryVO) susIter.next();
              buf1.append("~$~");

              if (radioButtons)
                buf1.append("~");

              buf1.append(rtsVO.getResultedTest()==null?"":rtsVO.getResultedTest());
              buf1.append("~");
              if (rtsVO.getCodedResultValue() != null ){
                buf1.append(rtsVO.getCodedResultValue()==null?"":(rtsVO.getCodedResultValue() + " "));
              }else if(rtsVO.getOrganismName() != null){
                buf1.append(rtsVO.getOrganismName()==null?"":(rtsVO.getOrganismName() + " "));
              }else if (rtsVO.getNumericResultCompare() != null || rtsVO.getNumericResultValue1() != null){
                buf1.append((rtsVO.getNumericResultCompare()==null ||rtsVO.getNumericResultCompare().equalsIgnoreCase("<NULL>"))?"":(rtsVO.getNumericResultCompare()));
                buf1.append(rtsVO.getNumericResultValue1()==null ?"":(rtsVO.getNumericResultValue1().toString()));
                buf1.append((rtsVO.getNumericResultSeperator()==null ||rtsVO.getNumericResultSeperator().equalsIgnoreCase("<NULL>"))?"":(rtsVO.getNumericResultSeperator()));
                buf1.append(rtsVO.getNumericResultValue2()==null?"":(rtsVO.getNumericResultValue2().toString()));
                buf1.append(rtsVO.getNumericResultUnits()==null?"":(rtsVO.getNumericResultUnits() + " "));
              }else if (rtsVO.getTextResultValue() != null){
                String resultValue = rtsVO.getTextResultValue();
                String concatString = "";
                int strLength = resultValue.length();
                if (strLength <= 25)
                {
                        concatString = resultValue;
                }else{
                        concatString = resultValue.substring(0,25);
                        concatString += "...";
                }
                buf1.append(rtsVO.getTextResultValue()==null?"":concatString);
              }
            }// while
          }// if(rvo.getTheSusTestSummaryVOColl()!= null)
        }// while
        finalBuf.append(buf1.toString().trim()).append("~$");
        finalBuf.append("]]");
     	}catch (Exception e) {
    		logger.error("Error occurred in convertResultedTest: " + e.getMessage());
    		e.printStackTrace();
    	}  
	return finalBuf.toString();
    }

    /**
     * Takes a collection of resulted tests and formats the resulted tests with
     * various delimters to create the proper alignment within a Morbidity report
     *
     * @param morbReportSummaryVO MorbReportSummaryVO
     * @param displayTreatments boolean
     * @return String
     */
    public String convertMorbResultedTest(MorbReportSummaryVO morbReportSummaryVO, boolean displayTreatments)
    {
      StringBuffer buf1 = new StringBuffer(" ");
      StringBuffer buftreatment = new StringBuffer(" ");
      StringBuffer buf2 = new StringBuffer(" ");
      StringBuffer finalBuf = new StringBuffer(" ");
      boolean firstIter = true;
      boolean mrbFirstIter = true;

      try {
      if(morbReportSummaryVO.getTheLabReportSummaryVOColl() != null && morbReportSummaryVO.getTheLabReportSummaryVOColl().size() > 0)
      {
       Iterator<Object>  iter = morbReportSummaryVO.getTheLabReportSummaryVOColl().iterator();
        while (iter.hasNext())
        {
          LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO)iter.next();
          if (labReportSummaryVO.getTheResultedTestSummaryVOCollection() != null)
          {
            mrbFirstIter = false;
           Iterator<Object>  it  = labReportSummaryVO.getTheResultedTestSummaryVOCollection().iterator();
            while (it.hasNext())
            {
              if (firstIter){
                 buf1.append("~");
              }
              else{
                buf1.append("$");
                buf1.append("~");
              }

              ResultedTestSummaryVO rvo = (ResultedTestSummaryVO) it.next();
              // Resulted Tests
              buf1.append(rvo.getResultedTest()==null?"Resulted Test":rvo.getResultedTest().trim()==" "?"Resulted Test":rvo.getResultedTest() + "~");

              // Resulted Test Data
              if (rvo.getCodedResultValue()!=null && rvo.getCtrlCdUserDefined1() != null && rvo.getCtrlCdUserDefined1().equals("N")){
                buf1.append(rvo.getCodedResultValue()==null?"":(rvo.getCodedResultValue() + " "));
              }else if (rvo.getOrganismName() != null){
                buf1.append(rvo.getOrganismName()==null?"":(rvo.getOrganismName() + " "));
              }else if (rvo.getNumericResultCompare() != null || rvo.getNumericResultValue1() != null){
                buf1.append(rvo.getNumericResultCompare()==null?"":(rvo.getNumericResultCompare()));
                buf1.append(rvo.getNumericResultValue1()==null?"":(rvo.getNumericResultValue1().toString()));
                buf1.append(rvo.getNumericResultSeperator()==null?"":(rvo.getNumericResultSeperator()));
                buf1.append(rvo.getNumericResultValue2()==null?"":(rvo.getNumericResultValue2().toString()));
                buf1.append(rvo.getNumericResultUnits()==null?"":(rvo.getNumericResultUnits() + " "));
              }else if (rvo.getTextResultValue() != null){
                String resultValue = rvo.getTextResultValue();
                String concatString = "";
                int strLength = resultValue.length();
                if (strLength <= 25)
                {
                        concatString = resultValue;
                }else{
                        concatString = resultValue.substring(0,25);
                        concatString += "...";
                }
                buf1.append(rvo.getTextResultValue()==null?"":concatString);
              }

              if (firstIter){
                      buf1.append("~");
                      firstIter = false;
              }
              else{
                      buf1.append("~");
              }
            }// while
          }// if
        }// while
      }

      if (displayTreatments)
      {
        CachedDropDownValues cddv = new CachedDropDownValues();
        String sTreatments = cddv.getTreatmentDesc();
        if (morbReportSummaryVO.getTheTreatmentSummaryVOColl() != null && morbReportSummaryVO.getTheTreatmentSummaryVOColl().size() > 0)
        {
         Iterator<Object>  iter = morbReportSummaryVO.getTheTreatmentSummaryVOColl().iterator();
          while (iter.hasNext())
          {
            if (!mrbFirstIter)
            {
              if (!firstIter){
                      buftreatment.append("~");
                      buftreatment.append("$");
              }
              buftreatment.append("~");
              firstIter = false;
              mrbFirstIter = false;
            }else
            {
              if (firstIter){
                      buftreatment.append("~");
              }else{
                      buftreatment.append("~");
                      buftreatment.append("$");
                      buftreatment.append("~");
              }
              firstIter = false;
            }
            TreatmentSummaryVO treatmentSummaryVO = (TreatmentSummaryVO)iter.next();
            buftreatment.append( (treatmentSummaryVO.getTreatmentNameCode().equals("OTH")) ? treatmentSummaryVO.getCustomTreatmentNameCode():StringUtils.DescriptionForCode(sTreatments,treatmentSummaryVO.getTreatmentNameCode()));
         }// while
       }// If
     }// If

      finalBuf.append("[[");
      finalBuf.append(buf1.toString().trim());
      if (buftreatment.toString() != null && !buftreatment.toString().trim().equals(""))
      {
              finalBuf.append(buftreatment.toString().trim()).append("~");
      }
      finalBuf.append("~$]]");
 	}catch (Exception e) {
		logger.error("Error occurred in convertMorbResultedTest: " + e.getMessage());
		e.printStackTrace();
	} 
      return finalBuf.toString();
   }

   /**
    * Takes a collection of resulted tests on morbidity reports and formats the resulted tests with
    * various delimters to create the proper alignment in the lab report section of the observation
    * summary
    *
    * @param labReportSummaryVOColl Collection
    * @param condition String
    * @param morbUid Long
    * @param radioButtons boolean
    * @return String
    */
   public String convertMorbInLab(String currentTask, Collection<Object>  labReportSummaryVOColl, String condition, Long morbUid, boolean radioButtons)
    {
      LabReportSummaryVO labReportSummaryVO = null;
      Collection<Object>  susColl = null;
     Iterator<Object>  susItor = null;
      StringBuffer sb = new StringBuffer();
      if(labReportSummaryVOColl != null && labReportSummaryVOColl.size() > 0)
      {
        sb.append("[[");
        if (radioButtons)
          sb.append("~");
        	sb.append("2--");
        sb.append(condition);
        sb.append("--");
        //append all LAB220 and LAB110
       Iterator<Object>  itor = labReportSummaryVOColl.iterator();
        while (itor.hasNext()) {
          labReportSummaryVO = (LabReportSummaryVO) itor.next();
          sb.append("~");
          sb.append(this.lab220lab110(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
          sb.append("~");
          //append values
          Collection<Object>  resultedTestVOColl = labReportSummaryVO.getTheResultedTestSummaryVOCollection();
          if(resultedTestVOColl != null)
          {
            ResultedTestSummaryVO resSummaryVO = null;
           Iterator<Object>  itorRT = resultedTestVOColl.iterator();
            while (itorRT.hasNext()) {
              resSummaryVO = (ResultedTestSummaryVO) itorRT.next();
              sb.append(this.appendResTestValues(resSummaryVO));
            }
          }
          //takes to the next row of resulted test and values
          sb.append("~");
          sb.append("(2)**").append(morbUid).append("**");
          sb.append("$");
          if(itor.hasNext()){
            if (radioButtons)
              sb.append("~");
          }
        }

        sb.append("]]");
        return sb.toString();
      }
      else
       return ""; //return empty string when ResultedTestSummaryVO collection is empty
    }

    /**
     * Formats the ordered test section of the lab report
     *
     * @param resColl Collection
     * @return String
     */
    private String lab220lab110(Collection<Object> resColl)
    {
      ResultedTestSummaryVO resVO = null;
      ResultedTestSummaryVO susVO = null;
     Iterator<Object>  susItor = null;
      //  ResultedTestSummaryVO will have ResultedTest and or SusTestSummaryVOColl
      StringBuffer sb = new StringBuffer();
     Iterator<Object>  itor = resColl.iterator();
      while(itor.hasNext())
      {
        resVO = (ResultedTestSummaryVO)itor.next();
        if (resVO.getResultedTest() != null && !resVO.getResultedTest().trim().equals(""))
          sb.append(resVO.getResultedTest()).append("!");
        if (resVO.getTheSusTestSummaryVOColl() != null && resVO.getTheSusTestSummaryVOColl().size() > 0)
        {

          susItor = resVO.getTheSusTestSummaryVOColl().iterator();
          while (susItor.hasNext())
          {
            susVO = (ResultedTestSummaryVO) susItor.next();
            sb.append(this.replaceNull(resVO.getResultedTest())).append("!");
          } //inner while
         }
       }//outer while


       return sb.toString();
  }

  /**
   * Formats the resulted test section of the morbidity report that appears in the lap report summary
   *
   * @param resVO ResultedTestSummaryVO
   * @return String
   */
  private String appendResTestValues(ResultedTestSummaryVO resVO)
  {
    //  ResultedTestSummaryVO will have ResultedTest and or SusTestSummaryVOColl
   StringBuffer sb = new StringBuffer();

   sb.append(this.putValues(resVO));
   if(resVO.getTheSusTestSummaryVOColl() != null && resVO.getTheSusTestSummaryVOColl().size() > 0)
   {
     ResultedTestSummaryVO susVO = null;
    Iterator<Object>  itor = resVO.getTheSusTestSummaryVOColl().iterator();
     while(itor.hasNext())
     {
       susVO = (ResultedTestSummaryVO)itor.next();
       sb.append(this.putValues(susVO));
     }
   }

    return sb.toString();

  }

  /**
   * Formats the extra fields for lap report summary section.
   *
   * @param resVO ResultedTestSummaryVO
   * @return String
   */
  private String putValues(ResultedTestSummaryVO resVO)
  {
     StringBuffer sb = new StringBuffer();

     if(resVO.getCodedResultValue() != null && !resVO.getCodedResultValue().equals("") && resVO.getCtrlCdUserDefined1() != null &&  resVO.getCtrlCdUserDefined1().equals("N"))
        {
          //LAB121
          sb.append(resVO.getCodedResultValue()).append("!");
        }
      else if(resVO.getOrganismName() != null && !resVO.getOrganismName().equals(""))
        {
          //LAB278
          sb.append(resVO.getOrganismName()).append("!");
        }

       else if(resVO.getNumericResultCompare() != null)
         {
          //LAB113 114 115 116 117 115
          sb.append(this.replaceNull(resVO.getNumericResultCompare()));
          sb.append(this.replaceNull(resVO.getNumericResultValue1()));
          sb.append(this.replaceNull(resVO.getNumericResultSeperator()));
          sb.append(this.replaceNull(resVO.getNumericResultValue2()));
          sb.append(this.replaceNull(resVO.getNumericResultUnits())).append("!");
         }
         else if(resVO.getTextResultValue() != null && !resVO.getTextResultValue().equals(""))
          {
           //LAB208
           String resultValue = resVO.getTextResultValue();
           String concatString = "";
           int strLength = resultValue.length();
           if (strLength <= 25)
           {
                   concatString = resultValue;
           }else{
                   concatString = resultValue.substring(0,25);
                   concatString += "...";
           }
           sb.append(resVO.getTextResultValue()==null?"":concatString).append("!");
          }
    return sb.toString();
  }

  /**
   * Replaces the null values in the formatted string
   *
   * @param nullObj Object
   * @return String
   */
  private String replaceNull(Object nullObj)
  {
     String returnStr = "";
    if(nullObj instanceof String)
    {
      returnStr = (nullObj == null) ? "" : (String) nullObj;
    }
    else if(nullObj instanceof Long)
    {
      if(nullObj == null)
        returnStr = "";
      else
        returnStr = ((Long)nullObj).toString();
    }
    else if(nullObj instanceof Double)
    {
      if(nullObj == null)
        returnStr = "";
      else
        returnStr = ((Double)nullObj).toString();
    }
    else if(nullObj instanceof Integer)
    {
      if(nullObj == null)
        returnStr = "";
      else
        returnStr = ((Integer)nullObj).toString();
    }

    else
      returnStr = "";

      return returnStr;

  }
}
