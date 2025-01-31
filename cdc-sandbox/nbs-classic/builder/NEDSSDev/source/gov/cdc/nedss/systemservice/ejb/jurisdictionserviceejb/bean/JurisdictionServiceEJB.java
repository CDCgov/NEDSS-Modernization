/**
 * Title:        Jurisdiction Service EJB
 * Description:  NEDSS Jurisdiction Session Bean EJB
 *
 * Copyright:    Copyright (c) 2001-2002
 * Company: 	 Computer Sciences Corporation
 * @author       03/12/2001 Chris Hanson & NEDSS Development Team
 * @modified     03/12/2002 Chris Hanson
 * @version      1.0.0
 */

package gov.cdc.nedss.systemservice.ejb.jurisdictionserviceejb.bean;

// Import Statements
import java.rmi.*;

import javax.ejb.*;

import java.util.*;

// gov.cdc.nedss.* imports
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.elr.ejb.msginprocessor.helper.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class JurisdictionServiceEJB
    implements SessionBean
{
	private static final long serialVersionUID = 1L;
  /**
   * Logger for this class
   */
  static final LogUtils logger = new LogUtils( (JurisdictionServiceEJB.class).
                                              getName()); // Added for the logger
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
  /**
   * Session Context.
   */
  private SessionContext sessionContext;

  private StringBuffer detailError= null;

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
    detailError = null;
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
    this.sessionContext = sessionContext;
    logger.debug("*** setSessionContext ...");
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
                                              PersonVO provider, OrganizationVO organizationVO)
  {
	  return resolveLabReportJurisdiction(subject,provider,organizationVO,null);
  }
  
  /**
   * @methodname resolveLabReportJurisdiction
   * Resolves what the Jurisdiction is for a give subject or provider for a the stated lab report.
   * @param PersonVO subject
   * @param PersonVO provider
   * @param OrganizationVO organizationVO -- ordering Facility
   * @param OrganizationVO organizationVO2 -- reporting FAcility
   * @return HashMap<Object, Object> a hashhamp with the proper messages.
   * @exception RemoteException EJB Remote Exception
   */
  public HashMap<Object, Object> resolveLabReportJurisdiction(PersonVO subject,
                                              PersonVO provider, OrganizationVO organizationVO, OrganizationVO organizationVO2)
  {

    try {
		Collection<Object> subjectColl = null;
		Collection<Object> providerColl = null;
		Collection<Object> organizationColl = null;
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		detailError = new StringBuffer();
		String jurisdiction =null;
		subjectColl = findJurisdictionForPatient(subject);

		// Check to see the subject size.  Only proceed if the subject size is not greater than 1.
		if (subjectColl.size() <= 1)
		{
		  // Check the result to make sure that there is a value for the subject's jurisdiction.
		  // If not then go and find the jurisdiction based on the provider
		  if (subjectColl.size() == 1)
		  {
		    Iterator<Object> iter = subjectColl.iterator();
		    jurisdiction = (String) iter.next();
		    map.put(ELRConstants.JURISDICTION_HASHMAP_KEY, jurisdiction);
		  }
		  if (jurisdiction==null && provider!=null)
		  {
			   providerColl = findJurisdictionForProvider(provider);
		       if(!(providerColl.size()==0))

		    // Check to see the provider size.  Only proceed if the provider size is not greater than 1.
		    if (providerColl.size() <= 1)
		    {
		      // Check the result to make sure that there is a value for the provider's jurisdiction.
		      // If not then go and find the jurisdiction based on the provider
		      if (providerColl.size() == 1)
		      {

		        Iterator<Object> iter = providerColl.iterator();
		        jurisdiction = (String) iter.next();
		        map.put(ELRConstants.JURISDICTION_HASHMAP_KEY, jurisdiction);
		      }
		      
		    }
		  }
		   if(jurisdiction==null){
		    if (organizationVO != null)
		      {
		        organizationColl = findJurisdictionForOrganization(organizationVO);
		      }
		    if (organizationColl != null)
		    {
		    	
		     // Check to see the organization size.  Only proceed if the organization size is not greater than 1.
		    	if (organizationColl.size() <= 1)
		        {
		          // Check the result to make sure that there is a value for the organization's jurisdiction.
		          // If not then go and find the jurisdiction based on the organization
		          if (organizationColl.size() == 1)
		          {

		            Iterator<Object> iter = organizationColl.iterator();
		            jurisdiction = (String) iter.next();
		            map.put(ELRConstants.JURISDICTION_HASHMAP_KEY, jurisdiction);
		          }
		        }
		    }
		    }
		   
				if (jurisdiction == null) {
					organizationColl = null;
					if (organizationVO2 != null) {
						organizationColl = findJurisdictionForOrganization(organizationVO2);
					} 
					if (organizationColl != null) {

						// Check to see the organization size. Only proceed if the
						// organization size is not greater than 1.
						if (organizationColl.size() <= 1) {
							// Check the result to make sure that there is a value
							// for the organization's jurisdiction.
							// If not then go and find the jurisdiction based on the
							// organization
							if (organizationColl.size() == 1) {

								Iterator<Object> iter = organizationColl.iterator();
								jurisdiction = (String) iter.next();
								map.put(ELRConstants.JURISDICTION_HASHMAP_KEY,
										jurisdiction);
							} 
						} 
					}
				}
		}
		
		detailError= null;
		return map;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("SubjectUid: " + subject.thePersonDT.getPersonUid() + ", ProviderUid: " + provider.thePersonDT.getPersonUid() + ", " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
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
		detailError.append("Patient: ");
		return findJurisdiction(subject.getTheEntityLocatorParticipationDTCollection(), "H", "PST");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("SubjectUid: " + subject.thePersonDT.getPersonUid() + ", " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  } // End of findJusridiction

  /**
   * @methodname findJurisdictionForProvider
   * Resolves the Jurisdiction for a Provider
   * @param PersonVO provider
   * @return Collectoin a collection that contains the jurisdiction that is assigned.
   * @exception RemoteException EJB Remote Exception
   */
  public Collection<Object> findJurisdictionForProvider(PersonVO provider)
  {
    logger.info(
        "<***************************PROVIDER***************************>");
    detailError.append("Provider: ");
    return findJurisdiction(provider.getTheEntityLocatorParticipationDTCollection(), "WP", "PST");
  }

  public Collection<Object> findJurisdictionForOrganization(OrganizationVO organizationVO)
  {
    logger.info(
        "<***************************Ordering Facility ***************************>");
    detailError.append("Ordering Facility: ");
    return findJurisdiction(organizationVO.getTheEntityLocatorParticipationDTCollection(), "WP", "PST");
  }
 /**
   * @methodname findJurisdiction
   * Resolves the Jurisdiction for a Provider or Subject.
   * @param PersonVO provider
   * @return Collectoin a collection that contains the jurisdiction that is assigned.
   * @exception RemoteException EJB Remote Exception
   */
  private Collection<Object> findJurisdiction(Collection<Object> entityLocatorPartColl, String useCd,
                                      String classCd)
  {
    try {
		SRTMapDAOImpl dao = new SRTMapDAOImpl();
		PostalLocatorDT postalDt = null;
		Collection<Object> coll = null;

		// Check to make sure that you are able to get a locator participation
		if (entityLocatorPartColl != null)
		{
		  Collection<Object> subjectColl = entityLocatorPartColl;

		  // If there is a locator participation then proceed.
		  if (subjectColl != null && subjectColl.size() != 0)
		  {

		    Iterator<Object> it = subjectColl.iterator();
		    while (it.hasNext())
		    {
		      logger.info("1");
		      EntityLocatorParticipationDT dt = (EntityLocatorParticipationDT) it.next();

		      // for subject the use code	= "H" class cd = "PST"
		      // for provider the use code = "W" class cd = "PST"
		      if (dt.getUseCd().equals(useCd) && dt.getClassCd().equals(classCd))
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
		          if(searchZip == null)
		            searchZip="NO ZIP";
		          detailError.append(searchZip);
		          detailError.append(", ");
		          try
		          {
		            String sqlQuery = null;
		            String sqlCityQuery = null;
		            // Get the sql string
		           
		              sqlQuery = ELRConstants.JURISDICTION_SELECT_SQL;
		              sqlCityQuery = ELRConstants.JURISDICTION_CITY_SELECT_SQL;
		           
		            // Attempt to find the jurisicition by zip code.  if you do not retrieve any
		            // data then attempt to retriece by county.  If no data then retrieve
		            // by city.

		            coll = dao.findJurisdiction(searchZip, sqlQuery, "Z");
		            if (coll.size() < 1)
		            {
		              String cityDesc = postalDt.getCityDescTxt();
		              if(cityDesc == null)
		                cityDesc = "NO CITY";
		              detailError.append(cityDesc);
		              detailError.append(", ");
		              if (postalDt.getCityDescTxt() != null)
		              {

		                coll = dao.findJurisdictionForCity(postalDt.getCityDescTxt(),
		                    postalDt.getStateCd(), sqlCityQuery, "C");
		              }
		              String countyDesc = postalDt.getCntyDescTxt();
		              if (countyDesc == null)
		                countyDesc = "NO COUNTY";
		              detailError.append(countyDesc);
		              detailError.append(", ");

		              if (coll.size() < 1)
		              {
		                coll = dao.findJurisdiction(postalDt.getCntyCd(), sqlQuery, "N");
		              }
		            }
		          }
		          catch (NEDSSSystemException ned)
		          {
		            logger.info("No Database connection in Juridiction");
		          }
		        }
		      }
		    }
		  }
		}
		if(!detailError.toString().equals("Provider: ") && !detailError.toString().equals("Provider: ")) {
		  //this will remove the trailing ","
		  String detail = detailError.toString().substring(0,(detailError.toString().length() - 2));
		  detail = detail + " ";
		  detailError = new StringBuffer(detail);
		}
		if (coll == null)
		{
		  coll = (Collection<Object>)new Vector<Object>();
		}
		logger.info("<----ending findJurisdiction-->" + coll);
		return coll;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("UseCd: " + useCd + ", classCd: " + classCd + ", " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
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
		if(searchZip!=null && searchZip.trim().length()>5)
		{
		String toReturn = searchZip.substring(0, 5);
		return toReturn;
		}
		else
		return searchZip;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("Search Zip: " + searchZip + ", " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }

}