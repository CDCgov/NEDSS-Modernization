package gov.cdc.nedss.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.act.observation.dt.*;

/**
 * Title:        Actions
 * Description:  This is definately some code
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author:      jay kim
 * @version 1.0
 */
public class ObservationUtil
{

  static final LogUtils logger = new LogUtils(ObservationUtil.class.getName());

  public ObservationUtil()
  {
  }

  public ObsValueNumericDT parseNumericResult(ObsValueNumericDT
                                              obsValueNumericDT)
  {

    if (obsValueNumericDT.getNumericValue() != null)
    {
      if (!obsValueNumericDT.getNumericValue().equals(""))
      {
        // Get the value that was entered as the numeric result.
        String parsingValue = "";
        String comparator = "";
        String comparatorPlus = "";
        String separator = "";
        String value1 = "";

        parsingValue = obsValueNumericDT.getNumericValue();
        obsValueNumericDT.setNumericValue("");
        comparator = parsingValue.substring(0, 1);

        // Get the comparator
        if (comparator.equals("=") || comparator.equals("<") ||
            comparator.equals(">"))
        {
          if (parsingValue.length() > 1)
          {
            comparatorPlus = parsingValue.substring(0, 2);
            if (comparatorPlus.equals("<>") || comparatorPlus.equals("<=") ||
                comparatorPlus.equals(">="))
            {
              obsValueNumericDT.setComparatorCd1(comparatorPlus);
              parsingValue = parsingValue.substring(2);
            }
            else
            {
              obsValueNumericDT.setComparatorCd1(comparator);
              parsingValue = parsingValue.substring(1);
            }
          }
          else
          {
            obsValueNumericDT.setComparatorCd1(comparator);
            parsingValue = parsingValue.substring(1);
          }
        }
        else
        {
          obsValueNumericDT.setComparatorCd1("=");
        }

        if (!parsingValue.equals(""))
        {
          if (parsingValue.indexOf("/") != -1)
          {
            separator = "/";
          }
          else if (parsingValue.indexOf("-") != -1)
          {
            separator = "-";
          }
          else if (parsingValue.indexOf(":") != -1)
          {
            separator = ":";
          }
          else if (parsingValue.indexOf("+") != -1)
          {
            separator = "+";
          }
          else
          {
            separator = "no";
          }
          if (separator.equals("no"))
          {
            obsValueNumericDT.setNumericValue1(new BigDecimal(parsingValue));
          }
          else
          {
            value1 = parsingValue.substring(0, parsingValue.indexOf(separator));
            parsingValue = parsingValue.substring(parsingValue.indexOf(
                separator) + 1);
            obsValueNumericDT.setNumericValue1(new BigDecimal(value1));
            obsValueNumericDT.setSeparatorCd(separator);

            if (!separator.equals("+"))
            {
              obsValueNumericDT.setNumericValue2(new BigDecimal(parsingValue));
            }
          }
        }else if(obsValueNumericDT.getNumericValue1()!=null){
        	 obsValueNumericDT.setNumericValue1(null);
             obsValueNumericDT.setNumericValue2(null);
        }
      }
      else
      {
        obsValueNumericDT.setComparatorCd1("");
        obsValueNumericDT.setNumericValue1(null);
        obsValueNumericDT.setNumericValue2(null);
        obsValueNumericDT.setSeparatorCd("");
      }
    }
    return obsValueNumericDT;
  }
  
	public ObsValueNumericDT parseNumericResultWithoutDefault(
			ObsValueNumericDT obsValueNumericDT) {

		if (obsValueNumericDT.getNumericValue() != null) {
			if (!obsValueNumericDT.getNumericValue().equals("")) {
				// Get the value that was entered as the numeric result.
				String parsingValue = "";
				String comparator = "";
				String comparatorPlus = "";
				String separator = "";
				String value1 = "";

				parsingValue = obsValueNumericDT.getNumericValue();
				obsValueNumericDT.setNumericValue("");
				comparator = parsingValue.substring(0, 1);

				// Get the comparator
				if (comparator.equals("=") || comparator.equals("<")
						|| comparator.equals(">")) {
					if (parsingValue.length() > 1) {
						comparatorPlus = parsingValue.substring(0, 2);
						if (comparatorPlus.equals("<>")
								|| comparatorPlus.equals("<=")
								|| comparatorPlus.equals(">=")) {
							obsValueNumericDT.setComparatorCd1(comparatorPlus);
							parsingValue = parsingValue.substring(2);
						} else {
							obsValueNumericDT.setComparatorCd1(comparator);
							parsingValue = parsingValue.substring(1);
						}
					} else {
						obsValueNumericDT.setComparatorCd1(comparator);
						parsingValue = parsingValue.substring(1);
					}
				} 

				if (!parsingValue.equals("")) {
					if (parsingValue.indexOf("/") != -1) {
						separator = "/";
					} else if (parsingValue.indexOf("-") != -1) {
						separator = "-";
					} else if (parsingValue.indexOf(":") != -1) {
						separator = ":";
					} else if (parsingValue.indexOf("+") != -1) {
						separator = "+";
					} else {
						separator = "no";
					}
					if (separator.equals("no")) {
						obsValueNumericDT.setNumericValue1(new BigDecimal(
								parsingValue));
					} else {
						value1 = parsingValue.substring(0,
								parsingValue.indexOf(separator));
						parsingValue = parsingValue.substring(parsingValue
								.indexOf(separator) + 1);
						obsValueNumericDT.setNumericValue1(new BigDecimal(
								value1));
						obsValueNumericDT.setSeparatorCd(separator);

						if (!separator.equals("+")) {
							obsValueNumericDT.setNumericValue2(new BigDecimal(
									parsingValue));
						}
					}
				}
			} else {
				obsValueNumericDT.setComparatorCd1("");
				obsValueNumericDT.setNumericValue1(null);
				obsValueNumericDT.setNumericValue2(null);
				obsValueNumericDT.setSeparatorCd("");
			}
		}
		return obsValueNumericDT;
	}

  /**
   * @method      : findPersonVOByPartType
   * @params      : Type Code, PersonVOCollection
   * @returnTyep  : PersonVO
   */
  public PersonVO findPersonVOByPartType(String type_cd,
                                         Collection<Object>  VOCollection)
  {
    //##!! System.out.println("inside findPersonVOByPartType method of :" + this.getClass());

    if (VOCollection  == null)
    {

      return null;
    }

   Iterator<Object>  itorVO = VOCollection.iterator();

    while (itorVO.hasNext())
    {

      PersonVO VO = (PersonVO) itorVO.next();
      Collection<Object>  partsColl = VO.getTheParticipationDTCollection();

      if (partsColl == null)
      {

        continue;
      }

     Iterator<Object>  itorParts = partsColl.iterator();

      while (itorParts.hasNext())
      {

        ParticipationDT part = (ParticipationDT) itorParts.next();

        if (part == null)
        {

          continue;
        }

        String typeCd = part.getTypeCd();

        if (typeCd == null)
        {

          continue;
        }

        if (typeCd.equalsIgnoreCase(type_cd))
        {

          return VO;
        }
      }
    }

    return null;
  } //findPersonVOByPartType

  /**
   * @method      : findAllPersonVOByPartType
   * @params      : TypeCode, PersonVOCollection
   * @returnType  : ArrayList
   */
  public ArrayList<Object> findAllPersonVOByPartType(String type_cd,
                                             Collection<Object>  VOCollection)
  {
    //##!! System.out.println("inside findAllPersonVOByPartType method of :" +this.getClass());

    ArrayList<Object> arraylist = new ArrayList<Object> ();

    if (VOCollection  == null)
    {

      return null;
    }

   Iterator<Object>  itorVO = VOCollection.iterator();

    while (itorVO.hasNext())
    {

      PersonVO VO = (PersonVO) itorVO.next();
      Collection<Object>  partsColl = VO.getTheParticipationDTCollection();

      if (partsColl == null)
      {

        continue;
      }

     Iterator<Object>  itorParts = partsColl.iterator();

      while (itorParts.hasNext())
      {

        ParticipationDT part = (ParticipationDT) itorParts.next();

        if (part == null)
        {

          continue;
        }

        String typeCd = part.getTypeCd();

        if (typeCd == null)
        {

          continue;
        }

        if (typeCd.equalsIgnoreCase(type_cd))
        {
          arraylist.add(VO);
        }
      }
    }

    return arraylist;
  } //findAllPersonVOByPartType

  /**
   * @method      : findAllObservationVOByPartType
   * @params      : TypeCode, ObservationVOCollection
   * @returnType  : ArrayList
   */
  public ArrayList<Object> findAllObservationVOByPartType(String type_cd,
                                                  Collection<Object>  VOCollection)
  {
    //##!! System.out.println("inside findAllObservationVOByPartType method of :" +	this.getClass());

    ArrayList<Object> arraylist = new ArrayList<Object> ();

    if (VOCollection  == null)
    {

      return null;
    }

   Iterator<Object>  itorVO = VOCollection.iterator();

    while (itorVO.hasNext())
    {

      ObservationVO VO = (ObservationVO) itorVO.next();
      Collection<Object>  partsColl = VO.getTheParticipationDTCollection();

      if (partsColl == null)
      {

        continue;
      }

     Iterator<Object>  itorParts = partsColl.iterator();

      while (itorParts.hasNext())
      {

        ParticipationDT part = (ParticipationDT) itorParts.next();

        if (part == null)
        {

          continue;
        }

        String typeCd = part.getTypeCd();

        if (typeCd == null)
        {

          continue;
        }

        if (typeCd.equalsIgnoreCase(type_cd))
        {
          arraylist.add(VO);
        }
      }
    }

    return arraylist;
  } //findAllObservationVOByPartType

  /**
   * @method      : findOrganizationVOByPartType
   * @params      : TypeCode, OrganizationVOCollection
   * @returnType  : OrganizationVO
   */
  public OrganizationVO findOrganizationVOByPartType(String type_cd,
      Collection<Object>  VOCollection)
  {

    if (VOCollection  == null)
    {

      return null;
    }

   Iterator<Object>  itorVO = VOCollection.iterator();

    while (itorVO.hasNext())
    {

      OrganizationVO VO = (OrganizationVO) itorVO.next();
      Collection<Object>  partsColl = VO.getTheParticipationDTCollection();

      if (partsColl == null)
      {

        continue;
      }

     Iterator<Object>  itorParts = partsColl.iterator();

      while (itorParts.hasNext())
      {

        ParticipationDT part = (ParticipationDT) itorParts.next();

        if (part == null)
        {

          continue;
        }

        String typeCd = part.getTypeCd();

        if (typeCd == null)
        {

          continue;
        }

        if (typeCd.equalsIgnoreCase(type_cd))
        {

          return VO;
        }
      }
    }

    return null;
  } //findOrganizationVOByPartType

  /**
   * @method      : findMaterialVOByPartType
   * @params      : TypeCode, MaterialVOCollection
   * @returnType  : MaterialVO
   */
  public MaterialVO findMaterialVOByPartType(String type_cd,
                                             Collection<Object>  VOCollection)
  {

    if (VOCollection  == null)
    {

      return null;
    }

   Iterator<Object>  itorVO = VOCollection.iterator();

    while (itorVO.hasNext())
    {

      MaterialVO VO = (MaterialVO) itorVO.next();
      Collection<Object>  partsColl = VO.getTheParticipationDTCollection();

      if (partsColl == null)
      {

        continue;
      }

     Iterator<Object>  itorParts = partsColl.iterator();

      while (itorParts.hasNext())
      {

        ParticipationDT part = (ParticipationDT) itorParts.next();

        if (part == null)
        {

          continue;
        }

        String typeCd = part.getTypeCd();

        if (typeCd == null)
        {

          continue;
        }

        if (typeCd.equalsIgnoreCase(type_cd))
        {

          return VO;
        }
      }
    }

    return null;
  } //findMaterialVOByPartType

  /**
   * @method      : findObservationVObyActType
   * @params      : TargetActUID, SourceActUID, ObservationVOCollection
   * @returnType  : ObservationVO
   */
  public ObservationVO findObservationVObyActType(Long targetUID,
                                                  String type_cd,
                                                  Collection<Object>  obsColl)
  {

    if (obsColl == null)
    {

      return null;
    }

   Iterator<Object>  itor = obsColl.iterator();

    while (itor.hasNext())
    {

      ObservationVO VO = (ObservationVO) itor.next();
      Long obsUID = VO.getTheObservationDT().getObservationUid();

      if (VO.getTheObservationDT().getObsDomainCdSt1() == null)
      {
        /**
                       //##!! System.out.println(
         "getNumericValue1: " +
         VO.getObsValueNumericDT_s(0).getNumericValue1());
           System.out.println(
         "getNumericUnitCd: " +
         VO.getObsValueNumericDT_s(0).getNumericUnitCd());
           System.out.println(
         "getEffectiveFromTime: " +
         VO.getTheObservationDT().getEffectiveFromTime());
           System.out.println(
         "getObservationUid: " +
         VO.getTheObservationDT().getObservationUid());
         */

        return VO;
      }
    }

    return null;
  } //findObservationVObyActType

  /**
   * @method      : findObservationVObyUID
   * @params      : ObservationUID, ObservationVOCollection
   * @returnType  : ObservationVO
   */
  public ObservationVO findObservationVObyUID(Long UID, Collection<ObservationVO>  obsColl)
  {
    //##!! System.out.println("inside findObservationVObyUID method of :" +	this.getClass());

    if (obsColl == null)
    {

      return null;
    }

   Iterator<ObservationVO>  itor = obsColl.iterator();
    //##!! System.out.println("obsColl size: " + obsColl.size());

    while (itor.hasNext())
    {

      ObservationVO obsVO = (ObservationVO) itor.next();
      //##!! System.out.println(    "obsVO.getTheObservationDT().getObservationUid(): " +  obsVO.getTheObservationDT().getObservationUid());
      //##!! System.out.println("UID: " + UID);

      if (obsVO.getTheObservationDT().getObservationUid().compareTo(UID) == 0)
      {
        //##!! System.out.println("equal");
        //##!! System.out.println(obsVO.getTheObservationDT().getProgAreaCd());
        //##!! System.out.println(obsVO.getTheObservationDT().getCd());

        return obsVO;
      }
    }

    return null;
  } //findObservationVObyUID

  /**
   * @method      : fetchObservationVO
   * @params      : ObservationUID, ObservationVOCollection
   * @returnType  : ObservationVO
   */
  public ObservationVO fetchObservationVO(Long sUID, Collection<Object>  obsColl)
  {

   Iterator<Object>  obsIter = obsColl.iterator();

    while (obsIter.hasNext())
    {

      ObservationVO observationVO = (ObservationVO) obsIter.next();

      if (observationVO.getTheObservationDT().getObservationUid().compareTo(
          sUID) == 0)
      {

        return observationVO;
      }
    }

    return null;
  } //fetchObservationVO

  /**
   * @method      : fetchOrganizationVO
   * @params      : OrganizationUID, OrganizationVOCollection
   * @returnType  : OrganizationVO
   */
  public OrganizationVO fetchOrganizationVO(Long oUID, Collection<Object>  orgColl)
  {

   Iterator<Object>  orgIter = orgColl.iterator();

    while (orgIter.hasNext())
    {

      OrganizationVO organizationVO = (OrganizationVO) orgIter.next();

      if (organizationVO.getTheOrganizationDT().getOrganizationUid().compareTo(
          oUID) == 0)
      {

        return organizationVO;
      }
    }

    return null;
  } //fetchOrganizationVO
  
	/*public Map<String, Object> createProcessingDecisionObservation(Long observationUid,
			String observationType, String processingDecision,
			HttpServletRequest request) throws IOException, ServletException {
		Map<String, Object> observationMap = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			NBSSecurityObj secObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			// set the observation for processing decision
			ObservationVO pDecisionVO = new ObservationVO();
			ObservationDT pDecisionDT = new ObservationDT();
			pDecisionDT.setObservationUid(new Long(-2));
			pDecisionDT.setCdSystemCd("NBS");
			pDecisionDT.setStatusCd("D");// changed as per WPD 8505
			pDecisionDT.setCdDescTxt("Processing Decision");
			pDecisionDT.setCdSystemDescTxt("NEDSS Base System");
			pDecisionDT.setAddUserId(new Long(secObj.getTheUserProfile()
					.getTheUser().getEntryID()));
			if (observationType.equals("Lab"))
				pDecisionDT.setCd(NEDSSConstants.LAB_235);
			else if (observationType.equals("Morb"))
				pDecisionDT.setCd(NEDSSConstants.MORB_235);
			pDecisionDT.setEffectiveFromTime(new java.sql.Timestamp(new Date()
					.getTime()));
			pDecisionDT.setActivityToTime(new java.sql.Timestamp(new Date()
					.getTime()));
			pDecisionDT.setRptToStateTime(new java.sql.Timestamp(new Date()
					.getTime()));
			pDecisionDT.setItNew(true);
			pDecisionDT.setItDirty(false);
			pDecisionVO.setTheObservationDT(pDecisionDT);

			// create obs value coded collection
			ObsValueCodedDT ovcDT = new ObsValueCodedDT();
			ovcDT.setObservationUid(pDecisionVO.getTheObservationDT()
					.getObservationUid());
			ovcDT.setItNew(true);
			ovcDT.setItDirty(false);
			ovcDT.setCode(processingDecision);
			ArrayList<Object> ovcList = new ArrayList<Object>();
			ovcList.add(ovcDT);
			// add that collection to the resulted test
			pDecisionVO.setItNew(true);
			pDecisionVO.setItDirty(false);
			pDecisionVO.setTheObsValueCodedDTCollection(ovcList);

			// create the act relationship between the ordered test and the
			// Processing decision observationVO
			ActRelationshipDT arDT = new ActRelationshipDT();
			arDT.setSourceActUid(pDecisionVO.getTheObservationDT()
					.getObservationUid());
			arDT.setTargetActUid(observationUid);
			if(observationType.equals("Morb"))
				arDT.setTypeCd("MorbFrmQ");
			else
				arDT.setTypeCd(NEDSSConstants.ACT_TYPE_PROCESSING_DECISION);
			arDT.setTypeDescTxt(NEDSSConstants.ACT_TYPE_PROCESSING_DECISION);
			arDT.setRecordStatusCd("ACTIVE");
			arDT.setStatusCd("A");// may not be in spec, but we prob need
			arDT.setSourceClassCd("OBS");
			arDT.setTargetClassCd("OBS");
			arDT.setItNew(true);
			arDT.setItDirty(false);

			// add the act relationship to the ar collection on the proxy
			Collection<Object> arDTColl = new ArrayList<Object>();
			arDTColl.add(arDT);
			observationMap.put("actColl", arDTColl);
			// add the processing decision observation vo to the collection
			Collection<Object> obsVOColl = new ArrayList<Object>();
			obsVOColl.add(pDecisionVO);
			observationMap.put("obsColl", obsVOColl);
		} catch (Exception e) {
			logger.error("error occurred in Mark as Reviewed. sendLabProxyToEJB");
			e.printStackTrace();
		}
		return observationMap;
	}
*/

}