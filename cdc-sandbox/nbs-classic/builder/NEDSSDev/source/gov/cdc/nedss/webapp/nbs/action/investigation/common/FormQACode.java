package gov.cdc.nedss.webapp.nbs.action.investigation.common;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.util.NEDSSConstants;

public class FormQACode
{

   static final LogUtils logger = new LogUtils(FormQACode.class.getName());
   public FormQACode()
   {
   }
   public  static void putQuestionCode(ObservationDT observationDT)
  {
      //set some constant values to observationDT

                  if(observationDT != null)
                  {
                    observationDT.setCdSystemDescTxt(NEDSSConstants.NEDSS_BASE_SYSTEM);
                    observationDT.setCdSystemCd(NEDSSConstants.NBS);
                    observationDT.setCdVersion(NEDSSConstants.VERSION);

                  }


  }//putQuestionCode



}