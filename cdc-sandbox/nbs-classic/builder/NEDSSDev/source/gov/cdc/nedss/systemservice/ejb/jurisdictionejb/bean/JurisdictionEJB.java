/**
 * Title:        Jurisdiction EJB
 * Description:  NEDSS Jurisdiction Session Bean EJB
 *
 * Copyright:    Copyright (c) 2001-2002
 * Company: 	 Computer Sciences Corporation
 * @author       03/12/2001 Chris Hanson & NEDSS Development Team
 * @modified     03/12/2002 Chris Hanson
 * @version      1.0.0
 */

package gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean;

// Import Statements
import gov.cdc.nedss.elr.ejb.msginprocessor.helper.ELRConstants;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
// gov.cdc.nedss.* imports
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class JurisdictionEJB
    implements SessionBean
{

  /**
   * Logger for this class
   */
  static final LogUtils logger = new LogUtils( (JurisdictionEJB.class).getName()); // Added for the logger
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  /**
   * Session Context.
   */
  private SessionContext sessionContext;

  /**
   * Creates a new Jursidiction
   *
   * @exception RemoteException EJB Remote Exception
   * @exception CreateException EJB Create Exception
   */
  public void ejbCreate()
  {
    logger.debug("*** ejbCreate ...");
  }

  /**
   * Removes the Jurisidiction
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void ejbRemove() throws RemoteException
  {
    logger.debug("*** ejbRemove ...");
  }

  /**
   * Is called whenever Jurisiction becomes activated.
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void ejbActivate() throws RemoteException
  {
    logger.debug("*** ejbActivate ...");
  }

  /**
   * Is called whenever Jurisidiction becomes pasivated.
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void ejbPassivate() throws RemoteException
  {
    logger.debug("*** ejbPassivate ...");
  }

  /**
   * Is called whenever Session Context is needed to be set.
   *
   * @exception RemoteException EJB Remote Exception
   */
  public void setSessionContext(SessionContext sessionContext) throws
      RemoteException
  {
    try {
		this.sessionContext = sessionContext;
		logger.debug("*** setSessionContext ...");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("JurisdictionEJB.setSessionContext: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  /**
   * @methodname resolveLabReportJurisdiction
   * Resolves what the Jurisdiction is for a give subject or provider for a the stated lab report.
   * @param PersonVO subject
   * @param PersonVO provider
   * @return HashMap<Object, Object> a hashhamp with the proper messages.
   * @exception RemoteException EJB Remote Exception
   */
  public HashMap<Object, Object> resolveLabReportJurisdiction(PersonVO subject,
                                              PersonVO provider)
  {

    try {
		Collection<Object> subjectColl = null;
		Collection<Object> providerColl = null;
		HashMap<Object, Object> map = new HashMap<Object, Object>();

		logger.info("**** calling subject juris **** >");

		subjectColl = findJurisdictionForPatient(subject);

		logger.info("**** subjec collection size **** >" + subjectColl.size());
		// Check to see the subject size.  Only proceed if the subject size is not greater than 1.
		if (subjectColl.size() <= 1)
		{

		  // Check the result to make sure that there is a value for the subject's jurisdiction.
		  // If not then go and find the jurisdiction based on the provider
		  if (subjectColl.size() == 1)
		  {

		    Iterator<Object> iter = subjectColl.iterator();
		    String jurisdiction = (String) iter.next();
		    map.put(ELRConstants.JURISDICTION_HASHMAP_KEY, jurisdiction);
		  }
		  else
		  {

		    if (provider != null)
		    {
		      providerColl = findJurisdictionForProvider(provider);
		    }

		  }

		  if (providerColl != null)
		  {
		    if (providerColl.size() <= 1)
		    {
		      if (providerColl.size() == 1)
		      {

		        Iterator<Object> iter = providerColl.iterator();
		        String jurisdiction = (String) iter.next();
		        map.put(ELRConstants.JURISDICTION_HASHMAP_KEY, jurisdiction);
		      }
		    }
		   }
		}
		//return null;
		return map;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("JurisdictionEJB.resolveLabReportJurisdiction: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  /**
   * @methodname findJurisdictionForPatient
   * Resolves the Jurisdiction for a Subject
   * @param PersonVO subject
   * @return Collectoin a collection that contains the jurisdiction that is assigned.
   * @exception RemoteException EJB Remote Exception
   */
  public Collection<Object> findJurisdictionForPatient(PersonVO subject)
  {
    try {
		logger.info(
		    "<***************************SUBJECT***************************>");
		return findJurisdiction(subject, "H", "PST");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("JurisdictionEJB.findJurisdictionForPatient: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  } // End of findJusridiction

  /**
   *
   * @param subject
   * @param nbsSecurityObj
   * @return Collection<Object>
   * @throws RemoteException
   *
   */

  public Collection<Object> dmFindJurisdictionForPatient(PersonVO subject,
                                                 NBSSecurityObj nbsSecurityObj) throws
      RemoteException
  {
    try {
		boolean check = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
		                                             NBSOperationLookup.MERGE);

		if (check == false)
		{
		  throw new NEDSSSystemException(
		      "don't have permission to invoke jurisdiction");
		}

		return dmFindJurisdictionForPatient(subject);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("JurisdictionEJB.dmFindJurisdictionForPatient: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  /**
   *
   * @param personVO
   * @return Collection<Object>
   * This method spcifically writen jurisdiction from person object.
   *
   */
  private Collection<Object> dmFindJurisdictionForPatient(PersonVO personVO)
  {
    try {
		ArrayList<Object>  aList = new ArrayList<Object> ();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		SRTMapDAOImpl dao = new SRTMapDAOImpl();
		PostalLocatorDT postalDt = null;
		Collection<Object> coll = null;
		Collection<Object> elrActivityLogDTCollection = null;

		String useCd = "H";
		String classCd = "PST";

		if (personVO.getTheEntityLocatorParticipationDTCollection() != null)
		{
		  Collection<Object> subjectColl = personVO.
		      getTheEntityLocatorParticipationDTCollection();
		  if (subjectColl != null && subjectColl.size() != 0)
		  {
		    Iterator<Object> it = subjectColl.iterator();
		    while (it.hasNext())
		    {
		      EntityLocatorParticipationDT dt = (EntityLocatorParticipationDT) it.
		          next();
		      if (dt.getUseCd() != null && dt.getUseCd().equals(useCd) &&
		          dt.getClassCd() != null && dt.getClassCd().equals(classCd) &&
		          dt.getRecordStatusCd() != null &&
		          dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE))
		      {

		        postalDt = dt.getThePostalLocatorDT();
		        if (postalDt != null)
		        {
		          String searchZip = postalDt.getZipCd();
		          if (searchZip != null && searchZip.length() > 5)
		          {
		            searchZip = parseZip(postalDt.getZipCd());
		          }
		          try
		          {
		            String sqlQuery = null;
		            String sqlCityQuery = null;
		              sqlQuery = ELRConstants.JURISDICTION_SELECT_SQL;
		              sqlCityQuery = ELRConstants.JURISDICTION_CITY_SELECT_SQL_DM;
		            coll = dao.findJurisdiction(searchZip, sqlQuery, "Z");
		            if (coll.size() < 1)
		            {
		              if (postalDt.getCityDescTxt() != null)
		              {
		                coll = dao.dmFindJurisdictionForCity(postalDt.
		                    getCityDescTxt(),
		                    sqlCityQuery, "C");
		              } // End of postalDt.getCityDescTxt() != null)
		              if (coll.size() < 1)
		              {
		                coll = dao.findJurisdiction(postalDt.getCntyCd(),
		                                            sqlQuery, "N");
		              }
		            } // End of if(coll.size())
		          }
		          catch (Exception e)
		          {
		            e.printStackTrace();
		          }
		        }
		      } // End of if(dt.getUsedCd())
		    } // End of While(it.hasNext())
		  } // End of if(subjectColl != null && subjectColl.size() != 0)
		} // End of (personVO.getTheEntityLocatorParticipationDTCollection() != null)

		if (coll == null)
		{
		  coll = (Collection<Object>)new Vector<Object>();
		}
		logger.info("\n<----ending findJurisdiction-->" + coll);
		return coll;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("JurisdictionEJB.dmFindJurisdictionForPatient: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  /**
   * @methodname findJurisdictionForProvider
   * Resolves the Jurisdiction for a Provider
   * @param PersonVO provider
   * @return Collectoin a collection that contains the jurisdiction that is assigned.
   * @exception RemoteException EJB Remote Exception
   */
  public Collection<Object> findJurisdictionForProvider(PersonVO provider)
  {
    try {
		logger.info(
		    "<***************************PROVIDER***************************>");
		return findJurisdiction(provider, "WP", "PST");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("JurisdictionEJB.findJurisdictionForProvider: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  /**
   * @methodname findJurisdiction
   * Resolves the Jurisdiction for a Provider or Subject.
   * @param PersonVO provider
   * @return Collectoin a collection that contains the jurisdiction that is assigned.
   * @exception RemoteException EJB Remote Exception
   */
  private Collection<Object> findJurisdiction(PersonVO personVO, String useCd,
                                      String classCd)
  {
    try {
		logger.info("<----in find jurisdiction-->");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		SRTMapDAOImpl dao = new SRTMapDAOImpl();
		PostalLocatorDT postalDt = null;
		Collection<Object> coll = null;
		Collection<Object> elrActivityLogDTCollection = null;

		// Check to make sure that you are able to get a locator participation
		if (personVO.getTheEntityLocatorParticipationDTCollection() != null)
		{
		  Collection<Object> subjectColl = personVO.
		      getTheEntityLocatorParticipationDTCollection();

		  // If there is a locator participation then proceed.
		  if (subjectColl != null && subjectColl.size() != 0)
		  {

		    Iterator<Object> it = subjectColl.iterator();
		    while (it.hasNext())
		    {
		      logger.info("1");
		      EntityLocatorParticipationDT dt = (EntityLocatorParticipationDT) it.
		          next();
		      logger.info("use cd-->" + dt.getUseCd());
		      logger.info("cls cd-->" + dt.getClassCd());

		      // for subject the use code	= "H" class cd = "PST"
		      // for provider the use code = "W" class cd = "PST"
		      if (dt.getUseCd() != null && dt.getUseCd().equals(useCd) &&
		          dt.getClassCd() != null && dt.getClassCd().equals(classCd) &&
		          dt.getRecordStatusCd() != null &&
		          dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE))
		      {
		        postalDt = dt.getThePostalLocatorDT();

		        // Parse the zip if is valid.
		        if (postalDt != null)
		        {

		          String searchZip = postalDt.getZipCd();
		          if (searchZip != null && searchZip.length() > 5)
		          {
		            searchZip = parseZip(postalDt.getZipCd());
		          }
		          try
		          {
		            logger.info("zip code -->" + searchZip);
		            String sqlQuery = null;
		            String sqlCityQuery = null;
		            // Get the sql string
		            logger.info("<------getting the database for --->");
		              sqlQuery = ELRConstants.JURISDICTION_SELECT_SQL;
		              sqlCityQuery = ELRConstants.JURISDICTION_CITY_SELECT_SQL;
		            		            // Attempt to find the jurisicition by zip code.  if you do not retrieve any
		            // data then attempt to retriece by county.  If no data then retrieve
		            // by city.
		            logger.info("<------going to the database --->");

		            coll = dao.findJurisdiction(searchZip, sqlQuery, "Z");
		            logger.info("ZIP CODE ------------->" + searchZip);
		            if (coll.size() < 1)
		            {
		              //postalDt.setCityCd("A");
		              //postalDt.setCityDescTxt("Albion");
		              //postalDt.setStateCd("31");
		              logger.info("CITY CODE 1------------->" + postalDt.getCityCd());
		              if (postalDt.getCityDescTxt() != null)
		              {

		                coll = dao.findJurisdictionForCity(postalDt.getCityDescTxt(),
		                    postalDt.getStateCd(), sqlCityQuery, "C");
		                //coll = dao.findJurisdiction(postalDt.getCityCd(), sqlQuery, "C");
		              }
		              logger.info("CITY collection ----->" + coll);

		              if (coll.size() < 1)
		              {

		                coll = dao.findJurisdiction(postalDt.getCntyCd(), sqlQuery,
		                                            "N");

		              }

		            }
		          }
		          catch (NEDSSSystemException ned)
		          {
		            logger.info("No Database connection in Juridiction");
		            throw new NEDSSSystemException(ned.getMessage(), ned);
		          } //end of catch
		        } //end of if
		      } //end of if
		    } //end of while
		  } //end of if
		}

		if (coll == null)
		{
		  coll = (Collection<Object>)new Vector<Object>();
		}
		logger.info("<----ending findJurisdiction-->" + coll);
		return coll;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("JurisdictionEJB.findJurisdiction: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  }

  /**
   * Uses a StringTokenizer to parse a zip code using "-" as a delimeter.
   * Returns the first token of the zip.
   * @param searchZip  String
   * @return String
   */
  private String parseZip(String searchZip)
  {
    try {
		String toReturn = null;
		StringTokenizer ken = new StringTokenizer(new String(searchZip), "-");
		toReturn = ken.nextToken().trim();
		return toReturn;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("JurisdictionEJB.parseZip: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
  } //end of parseZip

}
