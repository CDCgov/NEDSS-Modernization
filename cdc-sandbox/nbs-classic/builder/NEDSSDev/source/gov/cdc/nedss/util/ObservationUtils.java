/**
 * Title: ObservationUtils class.
 * Description: A utility class to work with Observations
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.util;

import java.sql.Timestamp;
import java.util.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;

/**
 *
 */
public class ObservationUtils
{
  static final LogUtils logger = new LogUtils(ObservationUtils.class.getName());

  /**
   * A private default Constructor
   */
  private ObservationUtils()
  {
  }

  /**
   * A static method that retrieves the first ObsValueCoded Data Table
   * by taking ObservtionVO as param
   * @param ObservationVO obsVO
   * @return ObsValueCodedDT
   */
  public static ObsValueCodedDT getFirstObsCodedVal(ObservationVO obsVO)
  {
    if (obsVO == null)
    {
      return null;
    }

    // get the first ObsValCodedDT
    ArrayList<Object> obsDTs = (ArrayList<Object> ) obsVO.getTheObsValueCodedDTCollection();
    if ( (obsDTs != null) && (obsDTs.size() > 0))
    {
      return (ObsValueCodedDT) obsDTs.get(0);
    }

    return null;
  }

  /**
   * A static method that retrieves the first ObsValueDate Data Table
   * by taking ObservtionVO as param
   * @param ObservationVO obsVO
   * @return ObsValueDateDT
   */

  public static ObsValueDateDT getFirstObsValueDate(ObservationVO obsVO)
  {
    if (obsVO == null)
    {
      return null;
    }

    // get the first ObsValCodedDT
    ArrayList<Object> obsDTs = (ArrayList<Object> ) obsVO.getTheObsValueDateDTCollection();
    if ( (obsDTs != null) && (obsDTs.size() > 0))
    {
      return (ObsValueDateDT) obsDTs.get(0);
    }

    return null;
  }

  /**
   * A static method that retrieves the first ObsValueTxt Data Table
   * by taking ObservtionVO as param
   * @param ObservationVO obsVO
   * @return ObsValueTxtDT
   */

  public static ObsValueTxtDT getFirstObsValueTxt(ObservationVO obsVO)
  {
    if (obsVO == null)
    {
      return null;
    }

    // get the first ObsValCodedDT
    ArrayList<Object> obsDTs = (ArrayList<Object> ) obsVO.getTheObsValueTxtDTCollection();
    if ( (obsDTs != null) && (obsDTs.size() > 0))
    {
      return (ObsValueTxtDT) obsDTs.get(0);
    }

    return null;
  }

  /**
   * A static method that retrieves the ObservationVO
   * by taking String code and String text as params
   * @param java.lang.String strCd and java.lang.String strTxt
   * @return ObservationVO
   */
  public static ObservationVO getNewObsValueTxt(String strCd, String strTxt)
  {
    if (strCd == null)
    {
      return null;
    }

    ObservationVO obsVO = new ObservationVO();
    obsVO.setItNew(true);

    ObservationDT obsDT = new ObservationDT();
    obsDT.setCd(strCd);
    obsVO.setTheObservationDT(obsDT);

    if (strTxt != null)
    {
      Collection<Object>  dtColl = new ArrayList<Object> ();
      ObsValueTxtDT obsValDT = new ObsValueTxtDT();
      obsValDT.setValueTxt(strTxt);
      obsValDT.setObsValueTxtSeq(new Integer(1));
      dtColl.add(obsValDT);
      obsVO.setTheObsValueTxtDTCollection(dtColl);
    }
    return obsVO;
  }

  /**
   * A static method that retrieves the ObservationVO
   * by taking String code and String text as params
   * @param java.lang.String strCd and java.lang.String strTxt
   * @return ObservationVO
   */

  public static ObservationVO getNewObsValueCoded(String strCd, String strCode)
  {
    if (strCd == null)
    {
      return null;
    }

    ObservationVO obsVO = new ObservationVO();
    obsVO.setItNew(true);

    ObservationDT obsDT = new ObservationDT();
    obsDT.setCd(strCd);
    obsVO.setTheObservationDT(obsDT);

    if (strCode != null)
    {
      Collection<Object>  dtColl = new ArrayList<Object> ();
      ObsValueCodedDT obsValDT = new ObsValueCodedDT();
      obsValDT.setCode(strCode);
      dtColl.add(obsValDT);
      obsVO.setTheObsValueCodedDTCollection(dtColl);
    }
    return obsVO;
  }

  /**
   * A static method that retrieves the ObservationVO
   * by taking String code and String text as params
   * @param java.lang.String strCd and java.lang.String strTxt
   * @return ObservationVO
   */

  public static ObservationVO getNewObsValueDate(String strCd, String strDate)
  {
    if (strCd == null)
    {
      return null;
    }

    ObservationVO obsVO = new ObservationVO();
    obsVO.setItNew(true);

    ObservationDT obsDT = new ObservationDT();
    obsDT.setCd(strCd);
    obsVO.setTheObservationDT(obsDT);

    Timestamp time = StringUtils.stringToStrutsTimestamp(strDate);
    if (time != null)
    {
      Collection<Object>  dtColl = new ArrayList<Object> ();
      ObsValueDateDT obsValDT = new ObsValueDateDT();
      obsValDT.setFromTime(time);
      obsValDT.setObsValueDateSeq(new Integer(1));
      dtColl.add(obsValDT);
      obsVO.setTheObsValueDateDTCollection(dtColl);
    }
    return obsVO;
  }


  /**
   * A static method that trims empty ObsValueCodedDT Collections
   * @param ObservationVO obsVO
   */
  public static void trimEmptyObsValueCodedDTCollections(ObservationVO obsVO)
  {
    if (obsVO.getTheObsValueCodedDTCollection() != null)
    {
      Collection<Object>  obsValColl = obsVO.getTheObsValueCodedDTCollection();

      // For each CodedValueDT, ensure that its code isn't null
     Iterator<Object>  it = obsValColl.iterator();
      while (it.hasNext())
      {
        ObsValueCodedDT obsValDT = (ObsValueCodedDT) it.next();

        String strCode = obsValDT.getCode();
        if ( (strCode == null) || (strCode.trim().length() == 0))
        {
          obsValDT.setCode("NI");
        }
      }
      // if we removed all the items from the DT collection.. continue
    } // end if hasObsValueCodedDTCollection()
  }


  /**
   * A static method that retrieves the ObservationVO
   * by taking Collection<Object>  - list of ObservationVOs and
   * String code - strCode - string with code to find an ObsVO as params
   * @param java.util.Collection, java.lang.String strCd
   * @return ObservationVO
   */

  public static ObservationVO findObservationByCode(Collection<ObservationVO> coll,
      String strCode)
  {
    if (coll == null)
    {
      return null;
    }

   Iterator<ObservationVO>  itor = coll.iterator();

    while (itor.hasNext())
    {
      ObservationVO obsVO = (ObservationVO) itor.next();
      ObservationDT obsDT = obsVO.getTheObservationDT();

      if (obsDT == null)
      {
        continue;
      }

      if (obsDT.getCd() == null)
      {
        continue;
      }
      if (obsDT.getCd().trim().equalsIgnoreCase(strCode.trim()))
      {
        return obsVO; // found it!
      }
    }

    // didn't find one
    return null;
  }
  /**
   * A static method that retrieves the ObservationVO
   * by taking Collection<Object>  - list of ObservationVOs and
   * String code - strCode - string with code to find an ObsVO as params
   * @param java.util.Collection, java.lang.String strCd
   * @return ObservationVO
   */

  public static ObservationVO findObservationByUid(Collection<ObservationVO> coll,Long uid)
  {
    if (coll == null)
    {
      return null;
    }

    ObservationVO obsVO = null;

   Iterator<ObservationVO>  itor = coll.iterator();
    while (itor.hasNext())
    {
      obsVO = (ObservationVO) itor.next();
      if(obsVO.getTheObservationDT().getObservationUid().equals(uid))
      return obsVO;
    }
    // didn't find one
    return null;
  }

}