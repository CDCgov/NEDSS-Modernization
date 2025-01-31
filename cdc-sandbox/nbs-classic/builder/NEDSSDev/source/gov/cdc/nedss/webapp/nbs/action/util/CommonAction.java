package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueDateDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.SRTCacheManager;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.SRTCacheManagerHome;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTCode;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper.SRTFilterKeys;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.ObservationUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.FormQACode;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.util.BatchEntryHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * <p>Title: CommonAction</p>
 * <p>Description: This class has method to prepare LDF values for request object.
 * It is common for all action class that uses LDF </P>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: CSC</p>
 * @author Shailesh Desai
 * @version 1.0
 */

public class CommonAction
    extends BaseLdf
{
  static final LogUtils logger = new LogUtils(CommonAction.class.
                                              getName());
  /**
   * a static method to remove unanswerd observation questions
   * @param obsVO
   * @return boolean
   */
  protected boolean checkForEmptyObs(ObservationVO obsVO, HttpServletRequest request)
  {
	  try{
	    Collection<Object>  obsValueCodedDTCollection  = obsVO.
	        getTheObsValueCodedDTCollection();
	    if (obsValueCodedDTCollection  != null && obsValueCodedDTCollection.size()>0)
	    {
	      for (Iterator<Object> iter = obsValueCodedDTCollection.iterator(); iter.hasNext(); )
	      {
	        ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) iter.next();
	        if(obsValueCodedDT!=null){
		        if (obsValueCodedDT.getCode() != null)
		        {
		          if (!obsValueCodedDT.getCode().trim().equals(""))
		          {
		            return true;
		          }
		        }
		        // added check for organism in batch entry
		        if (obsValueCodedDT.getDisplayName() != null)
		        {
		          if (!obsValueCodedDT.getDisplayName().trim().equals(""))
		          {
		            return true;
		          }
		        }
		        if (obsValueCodedDT.getSearchResultRT() != null)
		        {
		          if (!obsValueCodedDT.getSearchResultRT().trim().equals(""))
		          {
		            return true;
		          }
		        }
		      }
	      }
	    }
	    else if(obsVO.getTheObservationDT().getCd()!=null && request.getParameterValues(obsVO.getTheObservationDT().getCd()) != null)
	    {
	       String[] array = request.getParameterValues(obsVO.getTheObservationDT().getCd());
	       if((array.length == 1) && (array[0].trim().equals("")))
	         return false;
	       else
	         return true;
	    }
	
	
	    Collection<Object>  obsValueDateDTCollection  = obsVO.getTheObsValueDateDTCollection();
	    if (obsValueDateDTCollection  != null && obsValueDateDTCollection.size()>0)
	    {
	      for (Iterator<Object> iter = obsValueDateDTCollection.iterator(); iter.hasNext(); )
	      {
	        ObsValueDateDT obsValueDateDT = (ObsValueDateDT) iter.next();
	
	        if ((obsValueDateDT.getFromTime() != null))
	        {
	            return true;
	        }
	        if(obsValueDateDT.getDurationAmt() != null)
	        {
	          if(!obsValueDateDT.getDurationAmt().trim().equals(""))
	          {
	            return true;
	          }
	        }
	      }
	    }
	
	    Collection<Object>  obsValueTxtDTCollection  = obsVO.getTheObsValueTxtDTCollection();
	    if (obsValueTxtDTCollection  != null && obsValueTxtDTCollection.size()>0)
	    {
	      for (Iterator<Object> iter = obsValueTxtDTCollection.iterator(); iter.hasNext(); )
	      {
	        ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT) iter.next();
	        if (obsValueTxtDT.getValueTxt() != null)
	        {
	          if (!obsValueTxtDT.getValueTxt().trim().equals(""))
	          {
	            return true;
	          }
	        }
	      }
	    }
	
	    Collection<Object>  obsValueNumericDTCollection  = obsVO.getTheObsValueNumericDTCollection();
	    if (obsValueNumericDTCollection  != null && obsValueNumericDTCollection.size()>0)
	    {
	      for (Iterator<Object> iter = obsValueNumericDTCollection.iterator(); iter.hasNext(); )
	      {
	        ObsValueNumericDT obsValueNumericDT = (ObsValueNumericDT) iter.next();
	        if (obsValueNumericDT.getNumericValue() != null || obsValueNumericDT.getNumericValue1() != null || obsValueNumericDT.getNumericValue2() != null)
	        {
	          return true;
	        }
	      }
	    }

	  }catch(Exception ex){
		  logger.error("Exception in checkForEmptyObs ="+ex.getMessage(), ex);
	  }
    return false;
  }

  /**
   * setObservationForEdit
   *
   * four possibilities
   *
   * if old is null and new is null - do not persist
   * if old is null and new exist - set to new
   * if old exist and new is null - set to delete
   * if old exist and new exist - set to dirty
   *
   * @param inColl          // the Observation Collection<Object>  off the page
   * @param outColl         // collection for the modified (prepared) Observations to persist
   * @param treeMap         // the treeMap of old Observations
       * @param sharedInd       // read only field so comes off page null and gets lost
   * @param oldActColl      // the old ActRelationshipCollection   for delete
   * @param actrelations    // modified ActRelationshipCollection   must be put at the proxy level
   * @param targetObsUid    // needed to find ActRelationship
   */
  protected TreeMap<Object,Object> setObservations(Collection<ObservationVO> inColl, // obs from page
                                 TreeMap<Object,Object> treeMap, // oldObsVO
                                 String sharedInd, // read only
                                 Collection<Object>  oldActColl, // needed for delete
                                 Long targetObsUid, String typeCd,
                                 HttpServletRequest request, int i) // needed for new

  {
    ArrayList<Object> outColl = new ArrayList<Object> (); // obs to be persisted
    ArrayList<Object> actrelations = new ArrayList<Object> (); // needed for new and delete
    TreeMap<Object,Object> retMap = new TreeMap(); // return collection for above

    //int i = -10; // for new objects

    if (inColl != null)
    {
      for (Iterator<ObservationVO> itor = inColl.iterator(); itor.hasNext(); )
      {
        ObservationVO obsVO = (ObservationVO) itor.next();
        if (obsVO != null)
        {
          ObservationDT obsDT = obsVO.getTheObservationDT();

          String cd = obsDT.getCd();

          // first check to see if the value comming off the page is empty
          boolean hasAnswer = checkForEmptyObs(obsVO, request);

          ObservationVO oldObsVO = null;
          try
          {
            oldObsVO = (ObservationVO) treeMap.get(cd);
          }
          catch (NullPointerException npe)
          {
            //consume null pointer exception here
          }

          // if old is null and hasAnswer is true
          // - set to new
          if (oldObsVO == null && hasAnswer)
          {
            i = setObservationForCreate(obsVO, request, i);
            ActRelationshipDT newActDT = setQuestionActForNew(obsVO,
                targetObsUid, typeCd);
            actrelations.add(newActDT);
            outColl.add(obsVO);
          }

          // old exist and hasAnswer is false
          // - set to delete
          else if (oldObsVO != null && !hasAnswer)
          {
            Long oldUid = oldObsVO.getTheObservationDT().getObservationUid();
            ActRelationshipDT oldActDT = setQuestionActForDelete(oldActColl,
                oldUid);
            oldObsVO.setItDelete(true);
            actrelations.add(oldActDT);
            outColl.add(oldObsVO);
          }

          // if old exist and hasAnswer is true
          // - set to dirty
          else if (oldObsVO != null && hasAnswer)
          {
            setObservationForEdit(obsVO, oldObsVO, sharedInd, request);
            outColl.add(obsVO);

          }
        } //end of while loop
      } // end of if (obsColl != null)
    }
    retMap.put("observations", outColl);
    retMap.put("actrelations", actrelations);
    retMap.put("tempID", new Integer(i));
    return retMap;
  }

  /**
   * sets negative tempID and newFlag to true for observations
   * @param inColl -- the Collection<Object>  passed in to set tempID and flags
       * @param outColl -- the collection came out after setting the tempIDs and flags
   * @param tempID -- the temperary UID for Main observation
   * @return tempID -- the integer value of temp UID
   */
  private int setObservationForCreate(ObservationVO obsVO, HttpServletRequest request, int tempID)
  {
    if (obsVO != null)
    {
      ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);

      ObservationDT obsDT = obsVO.getTheObservationDT();

      if (obsVO.getTheObsValueCodedDTCollection() != null)
      {
       Iterator<Object>  it = obsVO.getTheObsValueCodedDTCollection().iterator();
        ArrayList<Object> obsValueCodedDTColl = new ArrayList<Object> ();
        while (it.hasNext())
        {
          ObsValueCodedDT obsValDT = (ObsValueCodedDT) it.next();
          obsValDT.setItNew(true);
          obsValDT.setItDirty(false);
        }
      }
      else if(request.getParameterValues(obsDT.getCd()) != null)
      {
        this.setMultipleToObservation(request.getParameterValues(obsDT.getCd()), obsDT.getCd(), obsVO, null);
      }


      if (obsVO.getTheObsValueDateDTCollection() != null)
      {
       Iterator<Object>  it = obsVO.getTheObsValueDateDTCollection().iterator();
        while (it.hasNext())
        {
          ObsValueDateDT obsValDT = (ObsValueDateDT) it.next();
          obsValDT.setItNew(true);
          obsValDT.setItDirty(false);
        }
      }

      if (obsVO.getTheObsValueNumericDTCollection() != null)
      {
       Iterator<Object>  it = obsVO.getTheObsValueNumericDTCollection().iterator();

        while (it.hasNext())
        {
          ObsValueNumericDT obsValDT = (ObsValueNumericDT) it.next();
          obsValDT.setItNew(true);
          obsValDT.setItDirty(false);
        }
      }

      if (obsVO.getTheObsValueTxtDTCollection() != null)
      {
       Iterator<Object>  it = obsVO.getTheObsValueTxtDTCollection().iterator();

        while (it.hasNext())
        {
          ObsValueTxtDT obsValDT = (ObsValueTxtDT) it.next();
          obsValDT.setItNew(true);
          obsValDT.setItDirty(false);
        }
      }

      obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
      obsDT.setStatusCd("D");

      String strCode = obsDT.getCd();
      obsDT.setObservationUid(new Long(tempID--));
      FormQACode.putQuestionCode(obsDT);
      obsDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      obsDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
      obsDT.setItNew(true);
      obsDT.setItDirty(false);
      obsVO.setItNew(true);
      obsVO.setItDirty(false);
    } // end of if (obsColl != null)
    return tempID;
  }

  protected ActRelationshipDT setQuestionActForNew(ObservationVO obsVO,
                                                Long mainObsUid, String typeCd)
  {
    ActRelationshipDT act = new ActRelationshipDT();
    act.setSourceActUid(obsVO.getTheObservationDT().getObservationUid());
    act.setSourceClassCd("OBS");
    act.setTargetActUid(mainObsUid);
    act.setTargetClassCd("OBS");
    act.setTypeCd(typeCd);
    act.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
    act.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
    act.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
    act.setItNew(true);
    act.setItDirty(false);
    return act;
  }

  private void setObservationForEdit(ObservationVO obsVO,
                                     ObservationVO oldObsVO,
                                     String sharedInd, HttpServletRequest request)
  {
    {
      ObservationDT obsDT = null;
      if (obsVO != null)
      {
        obsDT = obsVO.getTheObservationDT();
      }

      obsDT.setVersionCtrlNbr(oldObsVO.getTheObservationDT().getVersionCtrlNbr());
      Long obsUid = oldObsVO.getTheObservationDT().getObservationUid();
      obsDT.setObservationUid(obsUid);
      String programArea = oldObsVO.getTheObservationDT().getProgAreaCd();
      String jurisdiction = oldObsVO.getTheObservationDT().getJurisdictionCd();
      Long oid = oldObsVO.getTheObservationDT().getProgramJurisdictionOid();
      obsDT.setSharedInd(sharedInd);
      obsDT.setProgAreaCd(programArea);
      obsDT.setJurisdictionCd(jurisdiction);
      obsDT.setProgramJurisdictionOid(oid);
      obsDT.setCdDescTxt(oldObsVO.getTheObservationDT().getCdDescTxt());
      FormQACode.putQuestionCode(obsDT);

      String obsLocalId = oldObsVO.getTheObservationDT().getLocalId();
      obsDT.setLocalId(obsLocalId);
      if (obsVO.getTheObsValueCodedDTCollection() != null)
      {
       Iterator<Object>  it = obsVO.getTheObsValueCodedDTCollection().iterator();

        ArrayList<Object> obsValueCodedDTColl = new ArrayList<Object> ();
        while (it.hasNext())
        {
          ObsValueCodedDT obsValDT = (ObsValueCodedDT) it.next();
          obsValDT.setObservationUid(obsUid);
          //obsValDT.setItNew(false);
          obsValDT.setItDirty(true);
          if (obsValDT.getDisplayName() != null)
          {
            obsValDT.setCode("NI");
            obsValueCodedDTColl.add(obsValDT);
          }
          else if (obsValDT.getCode() != null &&
                   !obsValDT.getCode().trim().equals(""))
          {
            obsValueCodedDTColl.add(obsValDT);
          }
        }
        obsVO.setTheObsValueCodedDTCollection(obsValueCodedDTColl);
      }
      else if(request.getParameterValues(obsDT.getCd()) != null)
      {
        this.setMultipleToObservation(request.getParameterValues(obsDT.getCd()), obsDT.getCd(), obsVO, oldObsVO);
      }


      if (obsVO.getTheObsValueDateDTCollection() != null)
      {

       Iterator<Object>  it = obsVO.getTheObsValueDateDTCollection().iterator();

        while (it.hasNext())
        {

          ObsValueDateDT obsValDT = (ObsValueDateDT) it.next();
          if ( (obsDT != null) && (obsValDT.getObservationUid() == null))
          {
            obsValDT.setObservationUid(obsDT.getObservationUid());
          }
          obsValDT.setObservationUid(obsUid);
          obsValDT.setItNew(false);
          obsValDT.setItDirty(true);
        }
      }

      if (obsVO.getTheObsValueNumericDTCollection() != null)
      {

       Iterator<Object>  it = obsVO.getTheObsValueNumericDTCollection().iterator();

        while (it.hasNext())
        {

          ObsValueNumericDT obsValDT = (ObsValueNumericDT) it.next();
          obsValDT.setObservationUid(obsUid);
          obsValDT.setItNew(false);
          obsValDT.setItDirty(true);
        }
      }

      if (obsVO.getTheObsValueTxtDTCollection() != null)
      {
       Iterator<Object>  it = obsVO.getTheObsValueTxtDTCollection().iterator();

        while (it.hasNext())
        {

          ObsValueTxtDT obsValDT = (ObsValueTxtDT) it.next();
          obsValDT.setObservationUid(obsUid);
          obsValDT.setItNew(false);
          obsValDT.setItDirty(true);
        }
      }

      obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
      obsDT.setStatusCd("D");

      obsDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      obsDT.setRecordStatusTime(new java.sql.Timestamp(new Date().
          getTime()));
      obsDT.setItNew(false);
      obsDT.setItDirty(true);
      obsVO.setItNew(false);
      obsVO.setItDirty(true);
    } // end of setting it dirty
  }

  protected ActRelationshipDT setQuestionActForDelete(Collection<Object> actColl, Long uid)
  {
    ActRelationshipDT actRelationshipDT = null;
    for (Iterator<Object> it = actColl.iterator(); it.hasNext(); )
    {
      actRelationshipDT = (ActRelationshipDT) it.next();
      if (actRelationshipDT.getSourceActUid().equals(uid))
      {
        actRelationshipDT.setStatusTime(new java.sql.Timestamp(new Date().
            getTime()));
        actRelationshipDT.setRecordStatusTime(new java.sql.Timestamp(new Date().
            getTime()));
        actRelationshipDT.setItNew(false);
        actRelationshipDT.setItDirty(false);
        actRelationshipDT.setItDelete(true);
        return actRelationshipDT;
      }
    }
    return null;
  }

  protected void mapObsQAToRequest(Collection<ObservationVO> observationVOcoll,
                                HttpServletRequest request)
  {
    for (Iterator<ObservationVO> observationVOit = observationVOcoll.iterator();
         observationVOit.hasNext(); )
    {
      ObservationVO obsVO = (ObservationVO) observationVOit.next();
      ObservationDT obsDT = obsVO.getTheObservationDT();
      String mappingCd = obsDT.getCd();
      if (obsDT != null && mappingCd != null)
      {
        Collection<Object>  obsValueCdDTcoll = obsVO.getTheObsValueCodedDTCollection();
        if (obsValueCdDTcoll != null)
        {
          for (Iterator<Object> obsValueCdDTit = obsValueCdDTcoll.iterator();
               obsValueCdDTit.hasNext(); )
          {
            ObsValueCodedDT obsValueCdDT = (ObsValueCodedDT) obsValueCdDTit.
                next();
            String codeValue = obsValueCdDT.getCode();
            request.setAttribute(mappingCd, codeValue == null ? " " : codeValue);
          }
        }
        Collection<Object>  obsValueDateDTcoll = obsVO.getTheObsValueDateDTCollection();
        if (obsValueDateDTcoll != null)
        {
          for (Iterator<Object> obsValueDateDTit = obsValueDateDTcoll.iterator();
               obsValueDateDTit.hasNext(); )
          {
            ObsValueDateDT obsValueDateDT = (ObsValueDateDT) obsValueDateDTit.
                next();
            String fromTime = StringUtils.formatDate(obsValueDateDT.getFromTime());
            request.setAttribute(mappingCd, fromTime == null ? " " : fromTime);
          }
        }
        Collection<Object>  obsValueTxtDTcoll = obsVO.getTheObsValueTxtDTCollection();
        if (obsValueTxtDTcoll != null)
        {
          for (Iterator<Object> obsValueTxtDTit = obsValueTxtDTcoll.iterator();
               obsValueTxtDTit.hasNext(); )
          {
            ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT) obsValueTxtDTit.next();
            String valueTxt = obsValueTxtDT.getValueTxt();
            request.setAttribute(mappingCd, valueTxt == null ? " " : valueTxt);
          }
        }
      }
    }
  }

  protected TreeMap<Object,Object> mapObsQA(Collection<ObservationVO> observationVOcoll)
  {
    TreeMap<Object,Object> treeMap = new TreeMap();

    for (Iterator<ObservationVO> it = observationVOcoll.iterator(); it.hasNext(); )
    {
      ObservationVO obsVO = (ObservationVO) it.next();
      ObservationDT obsDT = obsVO.getTheObservationDT();
      String mappingCd = obsDT.getCd();
      if (obsDT != null && mappingCd != null)
      {
        if (mappingCd.equalsIgnoreCase(obsDT.getCd()))
        {
          //this is to avoid duplicate keys, as in case of batchEntry
          if(!treeMap.containsKey(mappingCd))
          treeMap.put(mappingCd, obsVO);
        }
      }
    }
    return treeMap;
  }


  private boolean compareObsValueDT(Collection<Object> obsColl, Collection<Object>  oldObsColl)
  {
    NedssUtils utils = new NedssUtils();
    boolean investigationProxyVODirty = false;
    if (obsColl != null && oldObsColl != null)
    {
     Iterator<Object>  itor = obsColl.iterator();
     Iterator<Object>  oldItor = oldObsColl.iterator();
      while (itor.hasNext() && oldItor.hasNext())
      {
        ObservationVO obsVO = (ObservationVO) itor.next();
        ObservationVO oldObsVO = (ObservationVO) oldItor.next();

        ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);

        if (obsVO.getTheObsValueCodedDTCollection() != null && oldObsVO.getTheObsValueCodedDTCollection() != null)
        {
           Iterator<Object>  it = obsVO.getTheObsValueCodedDTCollection().iterator();
           Iterator<Object>  oldIt = oldObsVO.getTheObsValueCodedDTCollection().iterator();
            while (it.hasNext() && oldIt.hasNext())
            {
              ObsValueCodedDT obsValDT = (ObsValueCodedDT) it.next();
              ObsValueCodedDT oldObsValDT = (ObsValueCodedDT) oldIt.next();

              if(obsValDT.getCode() != null && obsValDT.getCode().length() == 1)
              obsVO.getTheObservationDT().setYnuCd(obsValDT.getCode());
                if(!utils.equals(obsValDT, oldObsValDT, ObsValueCodedDT.class))
                {
                  investigationProxyVODirty = true;
                  obsValDT.setItDirty(true);
                  obsValDT.setItNew(false);
                  obsVO.setItDirty(true);
                  obsVO.setItNew(false);
                }

            }
        }

        if (obsVO.getTheObsValueDateDTCollection() != null && oldObsVO.getTheObsValueDateDTCollection() != null)
        {
           Iterator<Object>  it = obsVO.getTheObsValueDateDTCollection().iterator();
           Iterator<Object>  oldit = oldObsVO.getTheObsValueDateDTCollection().iterator();
            while (it.hasNext() && oldit.hasNext())
            {
              ObsValueDateDT obsValDT = (ObsValueDateDT) it.next();
              ObsValueDateDT oldObsValDT = (ObsValueDateDT) oldit.next();
                if(!utils.equals(obsValDT, oldObsValDT, ObsValueDateDT.class))
                {

                  investigationProxyVODirty = true;
                  obsValDT.setItDirty(true);
                  obsValDT.setItNew(false);
                  obsVO.setItDirty(true);
                  obsVO.setItNew(false);
                }

            }
        }

        if (obsVO.getTheObsValueNumericDTCollection() != null && oldObsVO.getTheObsValueNumericDTCollection() != null)
        {
           Iterator<Object>  it = obsVO.getTheObsValueNumericDTCollection().iterator();
           Iterator<Object>  oldit = oldObsVO.getTheObsValueNumericDTCollection().iterator();
            while (it.hasNext() && oldit.hasNext())
            {
              ObsValueNumericDT obsValDT = (ObsValueNumericDT) it.next();
              ObsValueNumericDT oldObsValDT = (ObsValueNumericDT) oldit.next();
                if(!utils.equals(obsValDT, oldObsValDT, ObsValueNumericDT.class))
                {
                  investigationProxyVODirty = true;
                  if(obsValDT.getNumericValue1() == null && obsValDT.getNumericValue2() == null)
                  obsValDT.setNumericUnitCd(null);
                  obsValDT.setItDirty(true);
                  obsValDT.setItNew(false);
                  obsVO.setItDirty(true);
                  obsVO.setItNew(false);
                }
            }
        }

        if (obsVO.getTheObsValueTxtDTCollection() != null && oldObsVO.getTheObsValueTxtDTCollection() != null)
        {
           Iterator<Object>  it = obsVO.getTheObsValueTxtDTCollection().iterator();
           Iterator<Object>  oldit = oldObsVO.getTheObsValueTxtDTCollection().iterator();

            while (it.hasNext() && oldit.hasNext())
            {
              //logger.debug("old cd - " + oldObsVO.getTheObservationDT().getCd());
              //logger.debug("new cd - " + obsVO.getTheObservationDT().getCd());

              ObsValueTxtDT obsValDT = (ObsValueTxtDT) it.next();


              ObsValueTxtDT oldObsValDT = (ObsValueTxtDT) oldit.next();
              //logger.debug("old cd - " + oldObsVO.getTheObservationDT().getCd() + "  value - " + obsValDT.getValueTxt() );
              //logger.debug("new cd - " + obsVO.getTheObservationDT().getCd()  + "  value - " +  oldObsValDT.getValueTxt());
              //logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");


                if(!utils.equals(obsValDT, oldObsValDT, ObsValueTxtDT.class))
                {
                  //logger.debug("@@@@@@@@@@@@@@@code ----   " + obsVO.getTheObservationDT().getCd()  + "    this is from Edit Sumit obsValDT " + obsValDT.getValueTxt()  );
                  investigationProxyVODirty = true;
                  obsValDT.setItDirty(true);
                  obsValDT.setItNew(false);
                  obsVO.setItDirty(true);
                  obsVO.setItNew(false);
                }
            }
        }

        ObservationDT obsDT = obsVO.getTheObservationDT();
        String strCode = obsDT.getCd();
        if ((strCode == null) || (strCode.length() == 0))
        logger.error("Page submitted an observation where obsDT.CD was not set.");
      }
    }

    return investigationProxyVODirty;
  }

  protected TreeMap<Object,Object> setBatchObservations(BatchEntryHelper aBatch, ObservationVO formObs, String cntrlCd, HttpServletRequest request, int i)
  {
    Collection<ObservationVO>  obsCollection  = aBatch.getObservationVOCollection();
    Collection<Object>  newObsColl = new ArrayList<Object> ();
    Collection<Object>  newActColl = new ArrayList<Object> ();
    TreeMap<Object,Object> treeMap = new TreeMap();
    //this.setObservations(obsCollection, aBatch.getObsTreeMap(), "T", rObservationVO.getTheActRelationshipDTCollection(), rowObservationVO.getTheObservationDT().getObservationUid(), "ITEMTOROW", i);
    if(aBatch.getTreeMap() == null && aBatch.getStatusCd().equals("A"))
    {
       //new row of batchEntry create the observations and actRelationship
      Iterator<ObservationVO>  anIterator = obsCollection.iterator();
        boolean rowObsAdded = false;
        ObservationVO rowObservationVO = null;
        while(anIterator.hasNext())
        {
            ObservationVO itemObsVO = (ObservationVO)anIterator.next();
            boolean hasAnswer = this.checkForEmptyObs(itemObsVO, request);
            if(hasAnswer)
            {
              if(!(rowObsAdded))
              {
                //add rowObservation
                rowObservationVO = this.rowObservation(cntrlCd);
                rowObservationVO.getTheObservationDT().setObservationUid(new Long(i--));
                newObsColl.add(rowObservationVO);
                ActRelationshipDT rowAct = this.setQuestionActForNew(rowObservationVO, formObs.getTheObservationDT().getObservationUid(), NEDSSConstants.INV_FRM_Q);
                newActColl.add(rowAct);
                rowObsAdded = true;
              }
               i = this.setObservationForCreate(itemObsVO,  request, i);
               newObsColl.add(itemObsVO);
               if(rowObsAdded)
               {
                 ActRelationshipDT rowActRelationshipDT = this.setQuestionActForNew(itemObsVO, rowObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.ITEM_TO_ROW);
                 ActRelationshipDT itemActRelationship = this.setQuestionActForNew(itemObsVO, formObs.getTheObservationDT().getObservationUid(), NEDSSConstants.INV_FRM_Q);
                 newActColl.add(rowActRelationshipDT);
                 newActColl.add(itemActRelationship);
               }

            }
        }
    }
    if(aBatch.getObsTreeMap() != null && aBatch.getStatusCd().equals("I"))
    {
       //delete the row of batchEntry

       ObservationVO rowObs = (ObservationVO)aBatch.getObsTreeMap().get(cntrlCd);
       Collection<Object>  valueColl = aBatch.getObsTreeMap().values();
      Iterator<Object>  anIterator = valueColl.iterator();
       while(anIterator.hasNext())
        {
            ObservationVO itemObsVO = (ObservationVO)anIterator.next();
            itemObsVO.setItDelete(true);
            itemObsVO.setItDirty(false);
            newObsColl.add(itemObsVO);
            ActRelationshipDT rowActRelationshipDT = this.setQuestionActForDelete(formObs.getTheActRelationshipDTCollection(), itemObsVO.getTheObservationDT().getObservationUid());
            ActRelationshipDT itemActRelationshipDT = this.setQuestionActForDelete(rowObs.getTheActRelationshipDTCollection(), itemObsVO.getTheObservationDT().getObservationUid());
            if(rowActRelationshipDT != null)
            newActColl.add(rowActRelationshipDT);
            if(itemActRelationshipDT != null)
            newActColl.add(itemActRelationshipDT);

        }
        this.setQuestionActForDelete(formObs.getTheActRelationshipDTCollection(), formObs.getTheObservationDT().getObservationUid());

    }

    if(aBatch.getObsTreeMap() != null && aBatch.getStatusCd().equals("A") && obsCollection  != null)
    {
       //edit row of batchEntry
      Iterator<ObservationVO>  anIterator = obsCollection.iterator();
        boolean rowObsAdded = false;
        ObservationVO rObservationVO = (ObservationVO)aBatch.getObsTreeMap().get(cntrlCd);

        while(anIterator.hasNext())
        {
            ObservationVO obsVO = (ObservationVO)anIterator.next();

            //only for itemObs check for edit, else if rowObs add this to the newObsColl
            if(obsVO.getTheObservationDT().getCd() != null)
            {
              ObservationVO oldObsVO = (ObservationVO) aBatch.getObsTreeMap().get(obsVO.getTheObservationDT().getCd());
              boolean hasAnswer = checkForEmptyObs(obsVO, request);
              if(hasAnswer)
              {
                if(oldObsVO != null)
                {
                  this.setObservationForEdit(obsVO, oldObsVO, "T", request);
                }
                else
                {
                  i = this.setObservationForCreate(obsVO,  request, i);
                  newObsColl.add(obsVO);
                  ActRelationshipDT rowActRelationshipDT = this.setQuestionActForNew(obsVO, rObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.ITEM_TO_ROW);
                  ActRelationshipDT itemActRelationship = this.setQuestionActForNew(obsVO, formObs.getTheObservationDT().getObservationUid(), NEDSSConstants.INV_FRM_Q);
                  newActColl.add(rowActRelationshipDT);
                  newActColl.add(itemActRelationship);
                }
                newObsColl.add(obsVO);
              }
              else
              {
                if(oldObsVO != null)
                {
                  oldObsVO.setItDelete(true);
                  oldObsVO.setItDirty(false);
                  newObsColl.add(oldObsVO);
                  ActRelationshipDT rowActRelationshipDT = this.setQuestionActForDelete(formObs.getTheActRelationshipDTCollection(), oldObsVO.getTheObservationDT().getObservationUid());
                  ActRelationshipDT itemActRelationshipDT = this.setQuestionActForDelete(rObservationVO.getTheActRelationshipDTCollection(), oldObsVO.getTheObservationDT().getObservationUid());
                  if(rowActRelationshipDT != null)
                  newActColl.add(rowActRelationshipDT);
                  if(itemActRelationshipDT != null)
                  newActColl.add(itemActRelationshipDT);
                }

              }
            }
        }
    }
    treeMap.put("observations", newObsColl);
    treeMap.put("actrelations", newActColl);
    treeMap.put("tempID", new Integer(i));

    return treeMap;
  }

  private ObservationVO  rowObservation(String cntrlCdDisplyForm)
 {
      ObservationVO rowObservationVO = new ObservationVO();

      ObservationDT rowObservationDT = new ObservationDT();

      rowObservationDT.setCd("ItemToRow");
      rowObservationDT.setCdSystemCd("NBS");
      rowObservationDT.setGroupLevelCd("L2");
      rowObservationDT.setObsDomainCd("CLN");
      rowObservationDT.setCtrlCdDisplayForm(cntrlCdDisplyForm);
      rowObservationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
      rowObservationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      rowObservationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      rowObservationDT.setItNew(true);
      rowObservationDT.setItDirty(false);

      rowObservationVO.setTheObservationDT(rowObservationDT);
      rowObservationVO.setItNew(true);
      rowObservationVO.setItDirty(false);
      return rowObservationVO;
 }

 protected TreeMap<Object,Object> mapBatchEntryQAValue(Collection<Object> antObservationVOCollection)
 {
   TreeMap<Object,Object> treeMap = new TreeMap();
   Collection<Object>  obsValueColl = null;
  Iterator<Object>  subItor = null;
   ObsValueCodedDT obsValue = null;
   ObservationVO obsVO = null;
  Iterator<Object>  itor = antObservationVOCollection.iterator();
   while(itor.hasNext())
   {
      obsVO = (ObservationVO)itor.next();
      if(obsVO != null && obsVO.getTheObservationDT().getCd() != null)
      {
        obsValueColl = obsVO.getTheObsValueCodedDTCollection();
        if(obsValueColl != null)
        {
          subItor = obsValueColl.iterator();
          if(subItor.hasNext())
          {
            obsValue = (ObsValueCodedDT)subItor.next();
            treeMap.put(obsVO.getTheObservationDT().getCd(), obsValue.getCode());
          }

        }

        obsValueColl = obsVO.getTheObsValueDateDTCollection();
        if(obsValueColl != null)
        {
          subItor = obsValueColl.iterator();
          if(subItor.hasNext())
          {
            ObsValueDateDT obsValueDateDT = (ObsValueDateDT)subItor.next();
            treeMap.put(obsVO.getTheObservationDT().getCd(), obsValueDateDT.getFromTime());
          }

        }

        obsValueColl = obsVO.getTheObsValueNumericDTCollection();
        if(obsValueColl != null)
        {
          subItor = obsValueColl.iterator();
          if(subItor.hasNext())
          {
            ObsValueNumericDT obsValueNum = (ObsValueNumericDT)subItor.next();
            treeMap.put(obsVO.getTheObservationDT().getCd(), obsValueNum.getNumericValue1());
          }

        }

        obsValueColl = obsVO.getTheObsValueTxtDTCollection();
        if(obsValueColl != null)
        {
          subItor = obsValueColl.iterator();
          if(subItor.hasNext())
          {
            ObsValueTxtDT obsValueTxt = (ObsValueTxtDT)subItor.next();
            treeMap.put(obsVO.getTheObservationDT().getCd(), obsValueTxt.getValueTxt());
          }

        }
      }

   }

   return treeMap;
 }


 protected boolean createOrDeleteParticipation(String newEntityUid, Long publicHealthCaseUID, Collection<Object>  newParCollection, Collection<Object>  oldParCollection, String typeCd, String classCd, boolean investigationProxyVODirty)
 {

     logger.debug(" newEntityUid = " + newEntityUid + " old");
     ParticipationDT oldParticipationDT = this.getParticipation(typeCd, classCd, oldParCollection);
     if(oldParticipationDT == null)
     {
         if(newEntityUid != null && !newEntityUid.trim().equals(""))
         {
             logger.info("old par in null and create new only: " + newEntityUid);
             newParCollection.add(this.createParticipation(publicHealthCaseUID, newEntityUid, classCd, typeCd));
             investigationProxyVODirty = true;
         }
     }
     else
     {
         Long oldEntityUid = oldParticipationDT.getSubjectEntityUid();
         if(newEntityUid != null && !newEntityUid.trim().equals("") && !newEntityUid.equals(oldEntityUid.toString()))
         {
             logger.info("participation changed newEntityUid: " + newEntityUid + " for typeCd " + typeCd);
             newParCollection.add(this.deleteParticipation(typeCd, classCd, oldEntityUid, oldParCollection));
             newParCollection.add(this.createParticipation(publicHealthCaseUID, newEntityUid, classCd, typeCd));
             investigationProxyVODirty = true;
         }
         else if((newEntityUid == null || newEntityUid.trim().equals("")))
         {
             logger.warn("no EntityUid (is cleared) or not set for typeCd: " + typeCd);
             newParCollection.add(this.deleteParticipation(typeCd, classCd, oldEntityUid, oldParCollection));
             investigationProxyVODirty = true;
         }
     }

     return investigationProxyVODirty;
 }  //scope

 protected ParticipationDT getParticipation(String type_cd, String classCd, Collection<Object>  participationDTCollection)
 {

     ParticipationDT participationDT = null;
     if(participationDTCollection  != null && participationDTCollection.size() > 0)
     {
        Iterator<Object>  anIterator = null;
         for(anIterator = participationDTCollection.iterator(); anIterator.hasNext();)
         {
             participationDT = (ParticipationDT)anIterator.next();
             if(participationDT.getSubjectEntityUid() != null && classCd.equals(participationDT.getSubjectClassCd()) && type_cd.equals(participationDT.getTypeCd()))
             {
                 logger.debug("participation exist for investigation: " + participationDT.getActUid() + " subjectEntity: " + participationDT.getSubjectEntityUid());

                 return participationDT;
             }
             else
             {
                 continue;
             }
         }
     }

     return null;
 }

 private ParticipationDT deleteParticipation(String typeCd, String classCd, Long oldUid, Collection<Object>  oldParticipationDTCollection)
 {

     ParticipationDT participationDT = null;
     if(oldParticipationDTCollection  != null && oldParticipationDTCollection.size() > 0)
     {
        Iterator<Object>  anIterator = null;
         for(anIterator = oldParticipationDTCollection.iterator(); anIterator.hasNext();)
         {
             participationDT = (ParticipationDT)anIterator.next();
             if(participationDT.getTypeCd().trim().equals(typeCd) && participationDT.getSubjectClassCd().trim().equals(classCd) && participationDT.getSubjectEntityUid().toString().equals(oldUid.toString()))
             {
                 logger.debug("deleting participation for typeCd: " + typeCd + " for investigation: " + participationDT.getActUid());
                 participationDT.setItDelete(true);
                 participationDT.setItDirty(false);
                 participationDT.setItNew(false);

                 return participationDT;
             }
             else
             {
                 continue;
             }
         }
     }

     return participationDT;
 }

 protected ParticipationDT createParticipation(Long actUid, String subjectEntityUid, String subjectClassCd, String typeCd)
 {
     CachedDropDownValues srtc = new CachedDropDownValues();

     logger.debug(" participation for typeCd: " + typeCd + " and " + subjectEntityUid);
     ParticipationDT participationDT = new ParticipationDT();
     participationDT.setActClassCd(NEDSSConstants.CLASS_CD_CASE);
     participationDT.setActUid(actUid);
     participationDT.setSubjectClassCd(subjectClassCd);
     participationDT.setSubjectEntityUid(new Long(subjectEntityUid.trim()));
     participationDT.setTypeCd(typeCd.trim());
     participationDT.setTypeDescTxt(srtc.getDescForCode("PAR_TYPE",typeCd.trim()));
     participationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
     participationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
     participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
     participationDT.setItNew(true);
     participationDT.setItDirty(false);

     return participationDT;
 }


 protected void  setMultipleToObservation(String[] array, String code,  ObservationVO obsVO, ObservationVO oldObsVO)
 {
       logger.debug("setting multiple observations");
       ObsValueCodedDT obsValueCodedDT = null;

       if((array != null) && (array.length == 1 && array[0].trim().equals("")))
       array = null;
       //dirty
       if((array != null) && (oldObsVO != null))
       {

         //obsVO = ObservationUtils.findObservationByCode(obsColl, code);
           if(obsVO != null)
           {
               Collection<Object>  obsValueCodedColl = new ArrayList<Object> ();
               //If the observation is not new, add the delete
               if(oldObsVO.getTheObsValueCodedDTCollection() != null)
               {
                  Iterator<Object>  itor = oldObsVO.getTheObsValueCodedDTCollection().iterator();
                   while(itor.hasNext())
                   {
                   ObsValueCodedDT obsValueCodedDTdel = (ObsValueCodedDT)itor.next();
                   obsValueCodedDTdel.setItNew(false);
                   obsValueCodedDTdel.setItDirty(false);
                   obsValueCodedDTdel.setItDelete(true);
                   obsValueCodedColl.add(obsValueCodedDTdel);
                   }

                   for(int i = 0; i < array.length; i++)
                   {
                       //Build the ObsValueCodedDT object for insert
                       if(!array[i].trim().equals(""))
                       {
                       obsValueCodedDT = new ObsValueCodedDT();
                       obsValueCodedDT.setObservationUid(oldObsVO.getTheObservationDT().getUid());
                       obsValueCodedDT.setCode(array[i]);
                       obsValueCodedDT.setItNew(true);
                       obsValueCodedDT.setItDirty(false);
                       obsValueCodedDT.setItDelete(false);
                       obsValueCodedColl.add(obsValueCodedDT);
                      }
                 }

               }
            if(obsValueCodedColl.size() > 0)
            obsVO.setTheObsValueCodedDTCollection(obsValueCodedColl);
           }
           //obsColl.add(oldObsVO);
       }

       //for new obsevation
       if((array != null) && (oldObsVO == null))
       {

           if(obsVO != null)
           {
             obsVO.getTheObservationDT().setCdSystemDescTxt(NEDSSConstants.NEDSS_BASE_SYSTEM);
             obsVO.getTheObservationDT().setCdSystemCd(NEDSSConstants.NBS);
             obsVO.getTheObservationDT().setCdVersion(NEDSSConstants.VERSION);
             obsVO.getTheObservationDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));
             obsVO.getTheObservationDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
             obsVO.getTheObservationDT().setItNew(true);
             obsVO.getTheObservationDT().setItDirty(false);

             //obsVO.getTheObservationDT().setObservationUid(new Long(tempID--));
             obsVO.setItNew(true);
             obsVO.setItDirty(false);

               Collection<Object>  obsValueCodedColl = new ArrayList<Object> ();
               for(int i = 0; i < array.length; i++)
               {
                   //Build the ObsValueCodedDT object for insert
                   if(!array[i].trim().equals(""))
                   {
                   obsValueCodedDT = new ObsValueCodedDT();
                   obsValueCodedDT.setObservationUid(obsVO.getTheObservationDT().getObservationUid());
                   obsValueCodedDT.setCode((String)array[i]);
                   obsValueCodedDT.setItNew(true);
                   obsValueCodedDT.setItDirty(false);
                   obsValueCodedDT.setItDelete(false);
                   obsValueCodedColl.add(obsValueCodedDT);
                  }
             }


               obsVO.setTheObsValueCodedDTCollection(obsValueCodedColl);
               ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
           }
       }

       //for delete
       if((array == null) && (oldObsVO != null))
       {

            oldObsVO.setItDelete(true);
            oldObsVO.setItDirty(false);
       }

 }

 protected void copyPublicHealthCaseDTValues(InvestigationProxyVO investigationProxyVO, InvestigationProxyVO oldInvestigationProxyVO)
 {
   PublicHealthCaseDT oldPHCDT = oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
   PublicHealthCaseDT newPHCDT = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
   oldPHCDT.setActivityDurationAmt(newPHCDT.getActivityDurationAmt());
   oldPHCDT.setActivityDurationUnitCd(newPHCDT.getActivityDurationUnitCd());
   oldPHCDT.setActivityFromTime(newPHCDT.getActivityFromTime());
   oldPHCDT.setActivityToTime(newPHCDT.getActivityToTime());
   oldPHCDT.setCaseClassCd(newPHCDT.getCaseClassCd());
   //oldPHCDT.setCaseTypeCd(newPHCDT.getCaseTypeCd());
   oldPHCDT.setCd(newPHCDT.getCd());
   oldPHCDT.setCdDescTxt(newPHCDT.getCdDescTxt());
   oldPHCDT.setConfidentialityCd(newPHCDT.getConfidentialityCd());
   oldPHCDT.setConfidentialityDescTxt(newPHCDT.getConfidentialityDescTxt());
   oldPHCDT.setDetectionMethodCd(newPHCDT.getDetectionMethodCd());
   oldPHCDT.setDetectionMethodDescTxt(newPHCDT.getDetectionMethodDescTxt());
   oldPHCDT.setDiagnosisTime(newPHCDT.getDiagnosisTime());
   oldPHCDT.setDiseaseImportedCd(newPHCDT.getDiseaseImportedCd());
   oldPHCDT.setDiseaseImportedDescTxt(newPHCDT.getDiseaseImportedDescTxt());
   oldPHCDT.setEffectiveDurationAmt(newPHCDT.getEffectiveDurationAmt());
   oldPHCDT.setEffectiveDurationUnitCd(newPHCDT.getEffectiveDurationUnitCd());
   oldPHCDT.setEffectiveFromTime(newPHCDT.getEffectiveFromTime());
   oldPHCDT.setEffectiveToTime(newPHCDT.getEffectiveToTime());
   //oldPHCDT.setGroupCaseCnt(newPHCDT.getGroupCaseCnt());
   oldPHCDT.setInvestigationStatusCd(newPHCDT.getInvestigationStatusCd());
   oldPHCDT.setJurisdictionCd(newPHCDT.getJurisdictionCd());
   oldPHCDT.setMmwrWeek(newPHCDT.getMmwrWeek());
   oldPHCDT.setMmwrYear(newPHCDT.getMmwrYear());
   oldPHCDT.setOutbreakFromTime(newPHCDT.getOutbreakFromTime());
   oldPHCDT.setOutbreakInd(newPHCDT.getOutbreakInd());
   oldPHCDT.setOutbreakName(newPHCDT.getOutbreakName());
   oldPHCDT.setOutbreakToTime(newPHCDT.getOutbreakToTime());
   oldPHCDT.setOutcomeCd(newPHCDT.getOutcomeCd());
   oldPHCDT.setPatAgeAtOnset(newPHCDT.getPatAgeAtOnset());
   oldPHCDT.setPatAgeAtOnsetUnitCd(newPHCDT.getPatAgeAtOnsetUnitCd());
   oldPHCDT.setPatientGroupId(newPHCDT.getPatientGroupId());
   oldPHCDT.setProgAreaCd(newPHCDT.getProgAreaCd());
   oldPHCDT.setRepeatNbr(newPHCDT.getRepeatNbr());
   oldPHCDT.setRptCntyCd(newPHCDT.getRptCntyCd());
   oldPHCDT.setRptFormCmpltTime(newPHCDT.getRptFormCmpltTime());
   oldPHCDT.setRptSourceCd(newPHCDT.getRptSourceCd());
   oldPHCDT.setRptSourceCdDescTxt(newPHCDT.getRptSourceCdDescTxt());
   oldPHCDT.setRptToCountyTime(newPHCDT.getRptToCountyTime());
   oldPHCDT.setRptToStateTime(newPHCDT.getRptToStateTime());
   oldPHCDT.setSharedInd(newPHCDT.getSharedInd());
   oldPHCDT.setTransmissionModeCd(newPHCDT.getTransmissionModeCd());
   oldPHCDT.setTransmissionModeDescTxt(newPHCDT.getTransmissionModeDescTxt());
   oldPHCDT.setTxt(newPHCDT.getTxt());
   oldPHCDT.setItDirty(true);

   investigationProxyVO.setAssociatedNotificationsInd(oldInvestigationProxyVO.getAssociatedNotificationsInd());
   investigationProxyVO.setBusinessObjNm(oldInvestigationProxyVO.getBusinessObjNm());


 }

 public void setMultiSelectReload(HttpServletRequest request)
 {

   String paramName = "";
   String[] array = null;

   Enumeration paramNameEnu = request.getParameterNames();

   while(paramNameEnu.hasMoreElements())
   {
     paramName = (String)paramNameEnu.nextElement();
     array = request.getParameterValues(paramName);
     if(array != null && array.length > 1)
     {
       StringBuffer sb = new StringBuffer();
       for (int i = 0; i < array.length; i++)
       {
          //don't store empty strings in parameter string array
          if (!(array[i].trim()).equals(""))
          {
            sb.append(array[i]).append("|");
          }
      }
      if(sb.length() > 0)
      request.setAttribute(paramName, sb.toString());
     }
     else 
    	 request.setAttribute(paramName, request.getParameter(paramName)); 

    }//end of while
 }

	protected String getInvestigationFormCd(InvestigationProxyVO investigationProxyVO) {
		ProgramAreaVO programAreaVO = null;
		String programAreaCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
		String conditionCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
		CachedDropDownValues cdv = new CachedDropDownValues();

		programAreaVO = cdv.getProgramAreaCondition("('" + programAreaCd + "')", conditionCd);
		if (programAreaVO == null) // level 2 condition for Hepatitis Diagnosis
			programAreaVO = cdv.getProgramAreaCondition("('" + programAreaCd + "')", 2, conditionCd);

		if (programAreaVO != null)
			return programAreaVO.getInvestigationFormCd();
		else
			return null;

	}
 
	public String getInvestigationFormCd(String conditionCd, String programAreaCd) {
		ProgramAreaVO programAreaVO = null;
		CachedDropDownValues cdv = new CachedDropDownValues();

		programAreaVO = cdv.getProgramAreaCondition("('" + programAreaCd + "')", conditionCd);
		if (programAreaVO == null) // level 2 condition for Hepatitis Diagnosis
			programAreaVO = cdv.getProgramAreaCondition("('" + programAreaCd + "')", 2, conditionCd);
		if (programAreaVO != null)
			return programAreaVO.getInvestigationFormCd();
		else
			return null;

	}
 
 public HashMap<?,?> getPHCConditionAndProgArea(Long investigationUid,
			HttpSession session) {
		MainSessionCommand msCommand = null;
		HashMap<?,?> invInfo = null;

		try {
			String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
			String sMethod = "getPHCConditionAndProgArea";
			Object[] oParams = new Object[] { investigationUid };

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);

			invInfo = (HashMap<?,?>) arr.get(0);
		} catch (Exception ex) {
			logger.error(ex.toString());
			throw new NEDSSSystemException(
					"Can not retrieve condition and prog area for Inv.");
		}

		return invInfo;

	}

 /**
	 * convertReportingSourcesToRequest method generates the drop down list for
	 * Reporting source for a given condition code.
	 * 
	 * @param type :
	 *            String type valid values are CREATE, EDIT, VIEW
	 * @param code :
	 *            String code
	 * @param conditionCd :
	 *            String condition code
	 * @return : String value
	 */
 public String convertReportingSourcesToRequest(String type, String code, String conditionCd) {
   StringBuffer sDropDownList = new StringBuffer("");
   Collection<Object>  list = null;
   String key = null;
   String val = null;
   SRTCacheManager srtManager = null;
   Map<Object,Object> map = new HashMap<Object,Object>();
   //boolean isCodeInvalid = false;
   boolean isCodeValid = false;
   if (conditionCd != null)
     map.put(SRTFilterKeys.CONDITION_CODE, conditionCd);
   try {
     NedssUtils nedssUtils = new NedssUtils();
     Object objref = nedssUtils.lookupBean(JNDINames.SRT_CACHEMANAGER_EJB);
     SRTCacheManagerHome home = (SRTCacheManagerHome)
                                javax.rmi.PortableRemoteObject.narrow(objref, SRTCacheManagerHome.class);
     srtManager = home.create();
     if (type.equalsIgnoreCase(NEDSSConstants.REPORTING_SOURCE_CREATE)
         || type.equalsIgnoreCase(NEDSSConstants.REPORTING_SOURCE_EDIT)) {
       list = (ArrayList<Object> ) srtManager.getInvestigationReportingSource(map);
       if (list != null) {
        Iterator<Object>  itr = list.iterator();
         while (itr.hasNext()) {
           SRTCode sRTLabTestDT = (SRTCode) itr.next();
           key = sRTLabTestDT.getCode();
           val = sRTLabTestDT.getDesc();
           if (code != null && code.equalsIgnoreCase(key))
           {
             isCodeValid = true;
           }
           if (val != null) {
             sDropDownList.append(key).append(NEDSSConstants.SRT_PART).append(val)
                 .append(NEDSSConstants.SRT_LINE);
           }
         }
         if (code != null && !isCodeValid && type.equalsIgnoreCase(NEDSSConstants.REPORTING_SOURCE_EDIT) && code != null) {
           list = (ArrayList<Object> ) srtManager.getInvestigationReportingSource(new HashMap<Object,Object>());
           if (list != null) {
            Iterator<Object>  itrator = list.iterator();
             while (itr.hasNext()) {
               SRTCode sRTLabTestDT = (SRTCode) itr.next();
               key = sRTLabTestDT.getCode();
               val = sRTLabTestDT.getDesc();
               if (code.equalsIgnoreCase(key)) {
                 //key found
                 if (val != null) {
                   sDropDownList.append(key).append(NEDSSConstants.SRT_PART).append(val)
                       .append(NEDSSConstants.SRT_LINE);
                 }
               }
             }
           }
         }
       }
     }
     else if (type.equalsIgnoreCase(NEDSSConstants.REPORTING_SOURCE_VIEW)) {
       list = (ArrayList<Object> ) srtManager.getInvestigationReportingSource(new HashMap<Object,Object>());
       if (list != null) {
        Iterator<Object>  itr = list.iterator();
         while (itr.hasNext()) {
           SRTCode sRTLabTestDT = (SRTCode) itr.next();
           key = sRTLabTestDT.getCode();
           val = sRTLabTestDT.getDesc();
           if (code != null && code.equalsIgnoreCase(key))
           if (val != null) {
             sDropDownList.append(key).append(NEDSSConstants.SRT_PART).append(val)
                 .append(NEDSSConstants.SRT_LINE);
           }
         }
       }
     }

   }
   catch (javax.ejb.CreateException e) {
     logger.error("CommonInvestigationUtil:CreateException thrown from SRT CachemanageEjb " + e);
   }
   catch (java.rmi.RemoteException e) {
     logger.error("CommonInvestigationUtil:RemoteException thrown from SRT CachemanageEjb " + e);
   }
   catch (gov.cdc.nedss.systemservice.exception.SRTCacheManagerException e) {
     logger.error("CommonInvestigationUtil:SRTCacheManagerException thrown from SRT CachemanageEjb " + e);
   }
   return sDropDownList.toString();
 }

	/**
	 * Check if the program area/condition(s) associated with the Lab or Morb are STD
	 * Options differ depending on whether Syphilis is in the list(Mark as Reviewed and associate Investigation scenario)
	 * @param programArea - the progArea of the Lab or Morb
	 * @param conditionList - the list of conditions (usually only one) assoc with the lab or morb
	 */
	public String  checkIfSyphilisIsInConditionList(String programArea, ArrayList conditionList, String eventType) {
		PropertyUtil properties = PropertyUtil.getInstance();
		//Not STD program area - no processing decision is needed (program area should always be set)
		if ((programArea == null) || (programArea.isEmpty()) || !PropertyUtil.isStdOrHivProgramArea(programArea)) {  
			return NEDSSConstants.STD_PROCESSING_DECISION_NOT_APPLICABLE;
		}
		//STD but no condition list available - show all choices
		if ((conditionList == null) || conditionList.isEmpty()) {
			//STD prog area but can't derive conditions - show the full list
		    return NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS;
		}
		// Check the condition list
		// See if the condition list has Syphilis and/or non-Syphilis conditions
		ArrayList<Object> syphilisConditionList = new ArrayList<Object>();
		syphilisConditionList = new CachedDropDownValues().getCodedValuesList("STD_SYPHILIS_CODE_LIST");
		boolean hasSyphilisCondition = false;
		boolean hasNonSyphilisCondition = false;
	    Iterator<Object>  condIter = conditionList.iterator();
	    while (condIter.hasNext()) {
	    	String conditionCd = (String) condIter.next();
	    	Iterator<Object> syphIter = syphilisConditionList.iterator();
	    	boolean syphFound = false;
	    	while (syphIter.hasNext()) {
	    		DropDownCodeDT dropDownDT = (DropDownCodeDT) syphIter.next();
	    		if (conditionCd.equalsIgnoreCase(dropDownDT.getKey()))
	    				syphFound = true;
	    	}
	    	if (syphFound)
	    		hasSyphilisCondition = true;
	    	else
	    		hasNonSyphilisCondition = true;
	    }
	    if (hasSyphilisCondition && !hasNonSyphilisCondition && eventType!=null && eventType.contains(NEDSSConstants.LAB_REPORT))
	    	return NEDSSConstants.STD_PROCESSING_DECISION_LIST_LAB_SYPHILIS;
	    else if (hasSyphilisCondition && !hasNonSyphilisCondition && eventType!=null && eventType.contains(NEDSSConstants.MORBIDITY_REPORT))
	    	return NEDSSConstants.STD_PROCESSING_DECISION_LIST_MORB_SYPHILIS;
	    else if (hasSyphilisCondition && hasNonSyphilisCondition)
	    	return NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS;
	    else if (!hasSyphilisCondition && hasNonSyphilisCondition)
	    	return NEDSSConstants.STD_PROCESSING_DECISION_LIST_NON_SYPHILIS;
	    return NEDSSConstants.STD_PROCESSING_DECISION_NOT_APPLICABLE;
	}
	
	/**
	 * Check if the program area/condition(s) associated with the Lab or Morb are STD
	 * Options differ depending on whether Syphilis is in the list(Create Investigation scenario)
	 * @param programArea - the progArea of the Lab or Morb
	 * @param conditionList - the list of conditions (usually only one) assoc with the lab or morb
	 */
	public String  checkIfSyphilisIsInConditionListForCreateInv(String programArea, ArrayList conditionList, String eventType) {
		PropertyUtil properties = PropertyUtil.getInstance();

		//Not STD program area - no processing decision is needed (program area should always be set)
		if ((programArea == null) || (programArea.isEmpty()) || !PropertyUtil.isStdOrHivProgramArea(programArea)) {  
			return NEDSSConstants.STD_PROCESSING_DECISION_NOT_APPLICABLE;
		}
		//STD but no condition list available - show all choices
		if ((conditionList == null) || conditionList.isEmpty()) {
			//STD prog area but can't derive conditions - show the full list
		    return NEDSSConstants.STD_CREATE_INV_LAB_UNKCOND_PROC_DECISION;
		}
		// Check the condition list
		// See if the condition list has Syphilis and/or non-Syphilis conditions
		ArrayList<Object> syphilisConditionList = new ArrayList<Object>();
		syphilisConditionList = new CachedDropDownValues().getCodedValuesList("STD_SYPHILIS_CODE_LIST");
		boolean hasSyphilisCondition = false;
	    Iterator<Object>  condIter = conditionList.iterator();
	    while (condIter.hasNext()) {
	    	String conditionCd = (String) condIter.next();
	    	Iterator<Object> syphIter = syphilisConditionList.iterator();
	    	boolean syphFound = false;
	    	while (syphIter.hasNext()) {
	    		DropDownCodeDT dropDownDT = (DropDownCodeDT) syphIter.next();
	    		if (conditionCd.equalsIgnoreCase(dropDownDT.getKey()))
	    				syphFound = true;
	    	}
	    	if (syphFound)
	    		hasSyphilisCondition = true;
	    }
	    if (hasSyphilisCondition && eventType!=null && eventType.contains(NEDSSConstants.LAB_REPORT))
	    	return NEDSSConstants.STD_CREATE_INV_LAB_SYPHILIS_PROC_DECISION;
	    else if (hasSyphilisCondition && eventType!=null && eventType.contains(NEDSSConstants.MORBIDITY_REPORT))
	    	return NEDSSConstants.STD_CREATE_INV_MORB_SYPHILIS_PROC_DECISION;
	    else if (!hasSyphilisCondition && eventType!=null && (eventType.contains(NEDSSConstants.LAB_REPORT) || eventType.contains(NEDSSConstants.MORBIDITY_REPORT)))
		    	return NEDSSConstants.STD_CREATE_INV_LABMORB_NONSYPHILIS_PROC_DECISION;
	    else 
	    	return NEDSSConstants.STD_CR_PROC_DECISION_NOINV;
	}

	public void setSTDandHIVPAsToRequest(HttpServletRequest request) {
		PropertyUtil properties = PropertyUtil.getInstance();
		String STDPAs = properties.getSTDProgramAreas();
		String HIVPAs = properties.getHIVProgramAreas();
		if(HIVPAs == null && STDPAs == null)
			return;
		else if(HIVPAs == null && STDPAs != null)
			request.setAttribute(NEDSSConstants.STD_PA_LIST, STDPAs);
		else if(STDPAs == null && HIVPAs != null)
			request.setAttribute(NEDSSConstants.STD_PA_LIST, HIVPAs);
		else{
			String STDHIVPAs = STDPAs+","+HIVPAs;
			request.setAttribute(NEDSSConstants.STD_PA_LIST, STDHIVPAs);
		}
	}
	
}