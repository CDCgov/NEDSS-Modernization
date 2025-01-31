package gov.cdc.nedss.webapp.nbs.form.observation;

import java.util.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;

import org.apache.struts.action.ActionForm;

public class ObservationGeneralForm extends ActionForm {

    // LAB ORDER INFORMATION

    private ObservationVO orderedTest;
    private PersonVO orderingProvider;
    private OrganizationVO orderingFacility;
    private OrganizationVO reportingLab;
    private MaterialVO specimen;
    private PersonVO subject;
    private ObservationVO ageAtSpecimenCollection;

	//RESULTS
    private ResultedTestVO theResultedTestVO;

    private Boolean loadedFromDB = new Boolean(false);
    private LabResultProxyVO proxy;
    private ArrayList<Object> theResultedTestVOCollection; // collection of ResultedTestVOs
    private ArrayList<Object> theOldResultedTestVOCollection;


    static final LogUtils logger = new LogUtils(ObservationGeneralForm.class.getName());
    /**
     * Construct a new dependent object instance.
     */
    public ObservationGeneralForm() {
    }
    public void reset() {
		orderedTest         = null;
		orderingProvider    = null;
		orderingFacility    = null;
		reportingLab       = null;
		specimen            = null;
		ageAtSpecimenCollection  = null;
		subject             = null;
		//theResultedTestVO = null;
		theResultedTestVOCollection  = null;
		theOldResultedTestVOCollection  = null;
		proxy = null;

    }

	public void setTheResultedTestVO(ResultedTestVO aTheResultedTestVO) {
		theResultedTestVO = aTheResultedTestVO;
	}

	public boolean hasResultedTestVOCollection() {
		if(this.theResultedTestVOCollection  == null)
			return false;
		else
			return true;
	}

	public void setResultedTestVOCollection(ArrayList<Object>  resultedTestVOCollection) {
		this.theResultedTestVOCollection  = resultedTestVOCollection;
	}

	public void setOldResultedTestVOCollection(ArrayList<Object>  resultedTestVOCollection) {
		this.theOldResultedTestVOCollection  = resultedTestVOCollection;
	}

    public ArrayList<Object> getResultedTestVOCollection() {
      return theResultedTestVOCollection;
    }

    public ArrayList<Object> getOldResultedTestVOCollection() {
      return theOldResultedTestVOCollection;
    }

    public PersonVO getSubject() {
       if (this.subject == null)
	this.subject = new PersonVO();

      return this.subject;
    }
    public void setSubject(PersonVO subject) {
      this.subject = subject;
    }

    public ObservationVO getOrderedTest() {
	if (this.orderedTest == null)
	   this.orderedTest = new ObservationVO();
	return this.orderedTest;
    }
    public void setOrderedTest(ObservationVO orderedTest) {

	     this.orderedTest = orderedTest;

    }

    public PersonVO getOrderingProvider() {
	if (this.orderingProvider == null)
	   this.orderingProvider = new PersonVO();
	return this.orderingProvider;
    }
    public void setOrderingProvider(PersonVO orderingProvider) {

      this.orderingProvider = orderingProvider;

    }

    public OrganizationVO getOrderingFacility() {
	if (this.orderingFacility == null)
	   this.orderingFacility = new OrganizationVO();
	return this.orderingFacility;
    }
    public void setOrderingFacility(OrganizationVO orderingFacility) {

	     this.orderingFacility = orderingFacility;

    }

    public OrganizationVO getReportingLab() {
	if (this.reportingLab == null)
	   this.reportingLab = new OrganizationVO();
	return this.reportingLab;
    }
    public void setReportingLab(OrganizationVO reportingLab) {

	     this.reportingLab = reportingLab;

    }

    public MaterialVO getSpecimen() {
	if (this.specimen == null)
	   this.specimen = new MaterialVO();
	return this.specimen;
    }
    public void setSpecimen(MaterialVO specimen) {

	     this.specimen = specimen;

    }

	public ObservationVO getAgeAtSpecimenCollection() {
		if(this.ageAtSpecimenCollection  == null)
			this.ageAtSpecimenCollection  = new ObservationVO();
		return this.ageAtSpecimenCollection;
	}
	public void setAgeAtSpecimenCollection(ObservationVO ageAtSpecimenCollection) {

			this.ageAtSpecimenCollection  = ageAtSpecimenCollection;

	}

    public boolean hasSubject() {
      if (this.subject == null)
	return false;
      else
	return true;
    }
    public boolean hasOrderedTest() {
	 if (this.orderedTest == null)
	    return false;
	 else
	    return true;
    }
    public boolean hasOrderingProvider() {
	 if (this.orderingProvider == null)
	    return false;
	 else
	    return true;
    }
    public boolean hasOrderingFacility() {
	 if (this.orderingFacility == null)
	    return false;
	 else
	    return true;
    }
    public boolean hasReportingLab() {
	 if (this.reportingLab == null)
	    return false;
	 else
	    return true;
    }
    public boolean hasSpecimen() {
	 if (this.specimen == null)
	    return false;
	 else
	    return true;
    }
	public boolean hasAgeAtSpecimenCollection() {
		if(this.ageAtSpecimenCollection  == null)
			return false;
		else
			return true;
	}

	public boolean hasResultedTestVO() {
		if(this.theResultedTestVO == null)
			return false;
		else
		return true;
	}
    public boolean hasProxy() {
      if (proxy == null)
	return false;
      else
	return true;
    }

    public LabResultProxyVO getProxy() {
      if (this.proxy == null)
	    this.proxy = new LabResultProxyVO();
	return this.proxy;
    }
    public void setProxy(LabResultProxyVO newVal) {
	this.proxy = newVal;
    }

    public boolean isLoadedFromDB() {
      return (this.loadedFromDB.booleanValue());
    }
    public void setLoadedFromDB(boolean newVal) {
      this.loadedFromDB = new Boolean(newVal);
    }

	public ResultedTestVO getResultedTest(int index) {
	// this should really be in the constructor
	if (this.getResultedTestVOCollection() == null)
	    this.theResultedTestVOCollection  = new ArrayList<Object> ();

	int currentSize = this.theResultedTestVOCollection.size();

	// check if we have a this many DTs
	if (index < currentSize) {
	  try {
	    Object[] tempArray = this.theResultedTestVOCollection.toArray();

	    Object tempObj  = tempArray[index];

	    ResultedTestVO tempVO = (ResultedTestVO) tempObj;

	    return tempVO;
	  } catch (Exception e) {
	     //##!! System.out.println(e);
		  } // do nothing just continue
	}//if
	ResultedTestVO tempVO = null;
	for (int i = currentSize; i < index+1; i++) {
	    tempVO = new ResultedTestVO();
	    tempVO.setItNew(true);  // this should be done in the constructor of the DT
	    this.theResultedTestVOCollection.add(tempVO);
	}
	return tempVO;

    }//getResultedTest

    public ResultedTestVO getOldResultedTest(int index) {
	// this should really be in the constructor
	if (this.getOldResultedTestVOCollection() == null)
	    this.theOldResultedTestVOCollection  = new ArrayList<Object> ();

	int currentSize = this.theOldResultedTestVOCollection.size();

	// check if we have a this many DTs
	if (index < currentSize) {
	  try {
	    Object[] tempArray = this.theOldResultedTestVOCollection.toArray();

	    Object tempObj  = tempArray[index];

	    ResultedTestVO tempVO = (ResultedTestVO) tempObj;

	    return tempVO;
	  } catch (Exception e) {
	     //##!! System.out.println(e);
		  } // do nothing just continue
	}//if
	ResultedTestVO tempVO = null;
	for (int i = currentSize; i < index+1; i++) {
	    tempVO = new ResultedTestVO();
	    tempVO.setItNew(true);  // this should be done in the constructor of the DT
	    this.theOldResultedTestVOCollection.add(tempVO);
	}
	return tempVO;

    }//getResultedTest

}//ObservationGeneralForm