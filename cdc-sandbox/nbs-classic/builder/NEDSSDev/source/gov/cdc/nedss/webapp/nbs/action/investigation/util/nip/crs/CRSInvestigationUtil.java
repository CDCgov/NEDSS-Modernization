
package gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.crs;



import java.util.*;

import javax.servlet.http.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.CommonInvestigationUtil;



import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;

/**
 * Title:         CRSInvestigationUtil is a class
 * Description:   this class retrieves data from EJB and puts them into request
 *                object for use in the xml file
 * Copyright:     Copyright (c) 2001
 * Company:       CSC
 * @author        Nedss Team
 * @version       1.0
 */

public class CRSInvestigationUtil extends CommonInvestigationUtil
{

   static final LogUtils logger = new LogUtils(CRSInvestigationUtil.class.getName());

    /**
     * This is constructor
     *
     */
   public CRSInvestigationUtil()
   {
   }

     /**
      * Get values from request object and put it to appropriate object
      * @param InvestigationProxyVO the investigationProxyVO
      * @param HttpServletRequest the request
      */

   public void setGenericRequestForView(InvestigationProxyVO investigationProxyVO, HttpServletRequest request) throws NEDSSAppException
   {
       this.displayRevisionPatient(investigationProxyVO,request);
       this.convertPublicHealthCaseToRequest(investigationProxyVO, request);
       this.convertPersonsToRequest(investigationProxyVO, request);
       this.convertOrganizationToRequest(investigationProxyVO, request);
       this.convertObservationsToRequest(investigationProxyVO, request);
       this.convertNotificationSummaryToRequest(investigationProxyVO, request);
       this.convertObservationSummaryToRequest(investigationProxyVO, request);
       this.convertVaccinationSummaryToRequest(investigationProxyVO, request);
       this.convertTreatmentSummaryToRequest(investigationProxyVO, request);
       this.convertDocumentSummaryToRequest(investigationProxyVO, request);
       
       getVaccinationSummaryRecords(getPersonVO(NEDSSConstants.PHC_PATIENT, investigationProxyVO).getThePersonDT().getPersonUid(), request);

   }



   /**
    * Get values from InvestigationForm object and put it to request object
    * @param InvestigationProxyVO the investigationProxyVO
    * @param HttpServletRequest the request
    */
   private void convertObservationsToRequest(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
   {
        Collection<ObservationVO>  obsColl = investigationProxyVO.getTheObservationVOCollection();
        if (obsColl != null)
        {
         Iterator<ObservationVO>  iter = obsColl.iterator();
          while (iter.hasNext())
          {
            ObservationVO obsVO = (ObservationVO) iter.next();

            String obsCode = obsVO.getTheObservationDT().getCd();
            if(obsCode != null)
            {
              //get the coded values
              Collection<Object>  codedColl = obsVO.getTheObsValueCodedDTCollection();
              if(codedColl!=null)
              {
               Iterator<Object>  codedIter = codedColl.iterator();
                if(codedIter.hasNext())
                {
                  ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)codedIter.next());
                  request.setAttribute(obsCode, obsValueCodedDT.getCode());
                  request.setAttribute(obsCode + "-originalTxt", obsValueCodedDT.getOriginalTxt());
                  //request.setAttribute(obsCode+"-desc", (obsValueCodedDT.getCodeSystemDescTxt()==null? "":obsValueCodedDT.getCodeSystemDescTxt()));
                  ////##!! System.out.println("obscode " + obsCode + " =   "+ obsValueCodedDT.getCode() );
                  ////##!! System.out.println("observation uid is " + obsValueCodedDT.getObservationUid()+ "\n\n " );
                  if(obsCode.equals("CRS162"))
                 {
                    String code =  obsValueCodedDT.getCode();
                    request.setAttribute("county", this.getCountiesByState(code));
                    ////##!! System.out.println("this.getCountiesByState(code)    " + this.getCountiesByState(code));
                    ////##!! System.out.println("observation uid is " + obsValueCodedDT.getObservationUid()+ "\n\n " );
                 }
                 if(obsCode.equals("CRS090"))
                {
                   String code =  obsValueCodedDT.getCode();
                   this.setMultiSelectToRequest(obsCode, codedColl, request);
                   ////##!! System.out.println("this.getCountiesByState(code)    " + this.getCountiesByState(code));
                   ////##!! System.out.println("observation uid is " + obsValueCodedDT.getObservationUid()+ "\n\n " );
                }

                }
              }
              //get date
              Collection<Object>  dateColl = obsVO.getTheObsValueDateDTCollection();
              if(dateColl!=null)
              {
               Iterator<Object>  dateIter = dateColl.iterator();
                if(dateIter.hasNext())
                {
                  ObsValueDateDT obsDateCodedDT = ((ObsValueDateDT)dateIter.next());
                  request.setAttribute(obsCode+"-fromTime", formatDate(obsDateCodedDT.getFromTime()));
                  request.setAttribute(obsCode+"-toTime", formatDate(obsDateCodedDT.getToTime()));
                  request.setAttribute(obsCode+"-durationAmt", (obsDateCodedDT.getDurationAmt()==null? "":obsDateCodedDT.getDurationAmt()));
                  request.setAttribute(obsCode+"-durationUnit", (obsDateCodedDT.getDurationUnitCd()==null? "":obsDateCodedDT.getDurationUnitCd()));
                }
              }
              //get text
              Collection<Object>  textColl = obsVO.getTheObsValueTxtDTCollection();
              if(textColl!=null)
              {
               Iterator<Object>  textIter = textColl.iterator();
                if(textIter.hasNext())
                {
                  ObsValueTxtDT obsTextCodedDT = ((ObsValueTxtDT)textIter.next());
                  request.setAttribute(obsCode+"-valueTxt", (obsTextCodedDT.getValueTxt()==null? "":obsTextCodedDT.getValueTxt()));
                  ////##!! System.out.println("obscode " + obsCode + " =   "+ obsTextCodedDT.getValueTxt() );
                  ////##!! System.out.println("observation uid is " + obsTextCodedDT.getObservationUid()+ "\n\n " );
                }
              }
              // get numeric
              Collection<Object>  numColl = obsVO.getTheObsValueNumericDTCollection();
              if(numColl!=null)
              {
               Iterator<Object>  numIter = numColl.iterator();
                if(numIter.hasNext())
                {
                  ObsValueNumericDT obsNumCodedDT = ((ObsValueNumericDT)numIter.next());
                  ////##!! System.out.println("obscode " + obsCode + " =   "+ obsNumCodedDT.getNumericValue1() );
                  ////##!! System.out.println("observation uid is " + obsNumCodedDT.getNumericValue1()+ "\n\n " );
                  request.setAttribute(obsCode+"-numericValue1", (obsNumCodedDT.getNumericValue1()==null? "":String.valueOf(obsNumCodedDT.getNumericValue1().intValue())));
                  request.setAttribute(obsCode+"-numericUnitCd", (obsNumCodedDT.getNumericUnitCd()==null?"":String.valueOf(obsNumCodedDT.getNumericUnitCd())));
                }
              }


            }
          }
        }

   }

     /**
      * Access to get Counties By State code from database
      * @param String the stateCd
      * @return String the value of the state code
      */
      private String getCountiesByState(String stateCd) {
      StringBuffer parsedCodes = new StringBuffer("");
      if (stateCd!=null){
	  //SRTValues srtValues = new SRTValues();
	  CachedDropDownValues srtValues = new CachedDropDownValues();
	  TreeMap<?,?> treemap = null;

	  treemap = srtValues.getCountyCodes(stateCd);

	  if (treemap != null){
	      Set<?> set = treemap.keySet();
	     Iterator<?>  itr = set.iterator();
	      while (itr.hasNext()){
		  String key = (String)itr.next();
		  String value = (String)treemap.get(key);
		      parsedCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim()).append(NEDSSConstants.SRT_LINE);
	       }
	   }
      }
      return parsedCodes.toString();
    }


      /**
      * Set Multiple selection  from requset object
      * @param HttpServletRequest  the request
      * @param Collection<Object>  the obsColl
      */
     public void setMultipleSelects(HttpServletRequest request, Collection<ObservationVO>  obsColl)
    {
        String[] arrayCRS090 = request.getParameterValues("CRS090");
        this.setMultipleToObservation(arrayCRS090, "CRS090", obsColl);
    }
    /**
    * Set Multiple selection  from requset object
    * @param HttpServletRequest  the request
    * @param Collection<Object>  the obsColl
    */
   public void setMultipleSelectForEdit(HttpServletRequest request, Collection<ObservationVO>  obsColl, TreeMap<Object,Object> obsMap)
  {
      String[] arrayCRS090 = request.getParameterValues("CRS090");
      super.setMultipleToObservationForEdit(arrayCRS090, "CRS090", obsColl, obsMap);

  }

         /**
      * Set Multiple selection  from collection object
      * @param String[]  the array
      * @param String the code
      * @param Collection<Object>  the obsColl
      */
    private void setMultipleToObservation(String[] array, String code, Collection<ObservationVO>  obsColl)
    {
          logger.debug("setting multiple observations");
          ObservationVO obsVO = null;
          ObsValueCodedDT obsValueCodedDT = null;
          if(array != null)
          {
              obsVO = ObservationUtils.findObservationByCode(obsColl, code);
              if(obsVO != null)
              {
                  List<Object> obsValueCoded = new ArrayList<Object> ();
                  for(int i = 0; i < array.length; i++)
                  {
                      //Build the ObsValueCodedDT object for insert
                      if(!array[i].trim().equals(""))
                      {
                      obsValueCodedDT = new ObsValueCodedDT();
                      obsValueCodedDT.setObservationUid(obsVO.getTheObservationDT().getUid());
                      obsValueCodedDT.setCode((String)array[i]);
                      obsValueCodedDT.setItNew(true);
                      obsValueCodedDT.setItDirty(false);
                      obsValueCodedDT.setItDelete(false);
                      obsValueCoded.add(obsValueCodedDT);
                      }
                }
                  //If the observation is not new, add the delete
                  if(!obsVO.getTheObservationDT().isItNew())
                  {
                      ObsValueCodedDT obsValueCodedDTdel = new ObsValueCodedDT();
                      obsValueCodedDTdel.setObservationUid(obsVO.getTheObservationDT().getUid());
                      obsValueCodedDTdel.setItNew(false);
                      obsValueCodedDTdel.setItDirty(false);
                      obsValueCodedDTdel.setItDelete(true);
                      /*Add it to the first position in the collection, so existing records
                         are deleted before new ones are inserted.*/
                      obsValueCoded.add(0, obsValueCodedDTdel);
                  }
                  obsVO.setTheObsValueCodedDTCollection(obsValueCoded);
                  ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
              }
          }
    }
    /**
     *
     */
    public void displayRevisionPatient(InvestigationProxyVO proxy, HttpServletRequest request)  throws NEDSSAppException{

      PersonVO revisionVO = this.getMPRevision(NEDSSConstants.PHC_PATIENT, proxy);
      //System.out.println("********* Code of revision Patient = " + revisionVO.getThePersonDT().getCd() );
      ArrayList<Object> stateList = new ArrayList<Object> ();
      PersonUtil.convertPersonToRequestObj(revisionVO, request,"AddPatientFromEvent",stateList);

    }//displayRevisionPatient
    /**
     *
     */
    public PersonVO getMPRevision(String type_cd, InvestigationProxyVO proxy) {
       logger.debug("Got into the persoVO finder");
       Collection<Object>  participationDTCollection  = null;
       Collection<Object>  personVOCollection  = null;
       ParticipationDT participationDT = null;
       PersonVO personVO = null;
       PublicHealthCaseVO phcVO = proxy.getPublicHealthCaseVO();
       participationDTCollection  = phcVO.getTheParticipationDTCollection();
       personVOCollection  = proxy.getThePersonVOCollection();
       //System.out.println("The collection Size is :"+personVOCollection.size());
       //System.out.println("The participation size is Size is :"+participationDTCollection.size());
       if (participationDTCollection  != null) {
        Iterator<Object>  anIterator1 = null;
        Iterator<Object>  anIterator2 = null;
         for (anIterator1 = participationDTCollection.iterator();
              anIterator1.hasNext(); ) {
           participationDT = (ParticipationDT) anIterator1.next();
           if (participationDT.getTypeCd() != null &&
               (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
             for (anIterator2 = personVOCollection.iterator(); anIterator2.hasNext(); ) {
               personVO = (PersonVO) anIterator2.next();
               //System.out.println("Out side if loop :"+personVO.getThePersonDT().getPersonUid() +"parent Uid is :"+personVO.getThePersonDT().getPersonParentUid());
               if (personVO.getThePersonDT().getPersonUid().longValue() ==
                   participationDT.getSubjectEntityUid().longValue()) {

                 return personVO;
               }
               else {
                 continue;
               }
             }
           }
           else {
             continue;
           }
         }
       }
       return null;
     } //getMPRevision

     private void setMultiSelectToRequest(String cd, Collection<Object>  codedColl,HttpServletRequest request)
     {
       StringBuffer sb = new StringBuffer();
      Iterator<Object>  itor = codedColl.iterator();
       while(itor.hasNext())
       {
           ObsValueCodedDT obsDT = (ObsValueCodedDT)itor.next();
           sb.append(obsDT.getCode());
           sb.append("|");
       }
       String multiString = sb.toString();
       if(multiString.length() > 1)
       request.setAttribute(cd, multiString);
     }

}