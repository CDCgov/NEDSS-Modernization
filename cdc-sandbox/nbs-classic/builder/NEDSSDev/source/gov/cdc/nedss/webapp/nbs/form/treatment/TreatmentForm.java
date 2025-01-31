/**
 *  Name: TreatmentForm.java
 *  Struts action form for Treatment.
 *  @author
 */
package gov.cdc.nedss.webapp.nbs.form.treatment;


import java.util.*;

import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.treatment.vo.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.*;
import gov.cdc.nedss.webapp.nbs.form.util.*;

import org.apache.struts.action.ActionForm;

public class TreatmentForm extends CommonForm {

    private TreatmentProxyVO treatmentProxy;
    private PersonVO treatmentGiver;
    private PersonVO patient;
    private PersonVO oldPatient;

    private ObservationVO treatmentObservation;
    private OrganizationVO organizationTreatmentGiver;
    private TreatmentVO treatmentVO;

    private PersonVO subject;

    private Collection<?>  treatmentSummaryVOColl;
    private TreatmentProxyVO proxy = null;
    private TreatmentProxyVO old = null;
    private String coinfectionAssocList = null;

    /**
     * Construct a new dependent object instance.
     */
    public TreatmentForm() {
    }

    public void reset()
    {
      proxy = null;
      old = null;
      oldPatient = null;
      treatmentProxy = null;
      treatmentGiver = null;
      treatmentObservation = null;
      //treatment = null;
      patient = null;
      treatmentVO = null;
      organizationTreatmentGiver = null;
      //organizationManufacturer = null;
      subject = null;
      treatmentSummaryVOColl = null;
      coinfectionAssocList = null;
    super.resetLDF();
    }




/**
 * gets old TreatmentProxyVO
 */
    public TreatmentProxyVO getOldProxy()
    {
      if (old == null)
	old = new TreatmentProxyVO();

	return this.old;
    }

    /**
     * sets Old TreatmentProxyVO
     */

    public void setOldProxy(TreatmentProxyVO old)
    {
	this.old = old;
    }


    /*
    public ObservationVO getTreatmentObservation() {
      if (this.treatmentObservation == null)
	   this.treatmentObservation = new ObservationVO();
	return this.treatmentObservation;
    }
	*/

    /**
     * getter method for TreatmentGiver
     */
    public PersonVO getTreatmentGiver() {
	if (this.treatmentGiver == null)
	   this.treatmentGiver = new PersonVO();
	return this.treatmentGiver;
    }


    public PersonVO getOldPatient() {
       if (this.oldPatient == null)
          this.oldPatient = new PersonVO();
       return this.oldPatient;
   }


    /**
     * getter method for treatment giver organization
     */
    public OrganizationVO getOrganizationTreatmentGiver() {
	if (this.organizationTreatmentGiver == null)
	   this.organizationTreatmentGiver = new OrganizationVO();
	return this.organizationTreatmentGiver;
    }

    /**

    public OrganizationVO getOrganizationManufacturer() {
	if (this.organizationManufacturer == null)
	   this.organizationManufacturer = new OrganizationVO();
	return this.organizationManufacturer;
    }
	*/

    /**
     * getter method for Treatment
     */
     public TreatmentVO getTreatmentVO() {
	if (this.treatmentVO == null)
	   this.treatmentVO = new TreatmentVO();
	return this.treatmentVO;
    }

    /**
     * getter method for subject(person)
     */
    public PersonVO getSubject() {
       if (this.subject == null)
	this.subject = new PersonVO();
      return this.subject;
    }

    /**
     * getter method for subject(person)
     */
    public PersonVO getPatient() {
       if (this.patient == null)
        this.patient = new PersonVO();
      return this.patient;
    }


    /**
     * getter method for TreatmentProxyVO
     */
    public TreatmentProxyVO getProxy() {
       if (this.treatmentProxy == null)
	this.treatmentProxy = new TreatmentProxyVO();
      return this.treatmentProxy;
    }

    /**
     * getter method for getTreatmentSummaryVOColl
     */
    public Collection<?>  getTreatmentSummaryVOColl() {

      return this.treatmentSummaryVOColl;
    }



    /**
     * setter method for treatment giver(person)
     */
    public void setTreatmentGiver(PersonVO person)
    {
      this.treatmentGiver = person;
    }


    /**
     * setter method for treatment giver organization
     */
    public void setOrganizationTreatmentGiver(OrganizationVO organizationTreatmentGiver)
    {
      this.organizationTreatmentGiver = organizationTreatmentGiver;
    }

    /**
     * setter method for manufacturer organization

    public void setOrganizationManufacturer(OrganizationVO organizationManufacturer)
    {
      this.organizationManufacturer = organizationManufacturer;
    } */

    /**
     * setter method for Treatment
     */
    public void setTreatmentVO(TreatmentVO treatmentVO)
    {
      this.treatmentVO = treatmentVO;
    }

    /**
     * setter method for subject(person)
     */
    public void setSubject(PersonVO subject)
    {
      this.subject = subject;
    }

    /**
    * setter method for subject(person)
    */
   public void setPatient(PersonVO patient)
   {
     this.patient = patient;
   }

   public void setOldPatient(PersonVO oldPatient)
  {
    this.oldPatient = oldPatient;
  }

    /**
     * setter method for subject(person)
     */
    public void setProxy(TreatmentProxyVO proxy)
    {
      this.treatmentProxy = proxy;
    }

    /**
     * setter method for TreatmentSummaryVOColl
     */
    public void setTreatmentSummaryVOColl(Collection<?> aTreatmentSummaryVOColl)
    {
      this.treatmentSummaryVOColl = aTreatmentSummaryVOColl;
    }
    
    /**
     * Also associate this list to other coinfections.
     * ex. 10067002:10066014
     * @return
     */
	public String getCoinfectionAssocList() {
		return coinfectionAssocList;
	}

	public void setCoinfectionAssocList(String coinfectionAssocList) {
		this.coinfectionAssocList = coinfectionAssocList;
	}

}