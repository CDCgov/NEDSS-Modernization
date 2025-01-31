package gov.cdc.nedss.webapp.nbs.form.observation;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.webapp.nbs.form.util.*;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */



/**
 *
 * This is class help to create VOs anf DTs when labreport form submit.
 */
public class ObservationForm extends CommonForm  {

  /**This may change when we get the documents from design
   * form loads values to the MorbidityProxyVO objects
   */
  private LabResultProxyVO proxy = null;

  /**
   * form loads old values to the LabReportProxyVO objects
   */
  private LabResultProxyVO oldProxy = null;

  /**
   * this field is used to set all person values
   */
  private PersonVO patient = null;


  /**
   * this field is used to set all person values
   */
  private PersonVO patientRevision = null;


  /**
   * this field is used to set double batch entry value
   */
  private ResultedTestVO resultedTestVO = null;


/**
 * this field is used to set double batch entry value
 */
  private ResultedTestVO oldResultedTestVO = null;


  /**
   * this field is used to set material values
   */
  private MaterialVO materialVO = null;
  
  private String programArea = null;
  
  private String processingDecisionLogic = "none";

  private boolean isStdOrHivProgArea =false;
  
private Collection messageLogDTCollection =null;




public Collection getMessageLogDTCollection() {
	return messageLogDTCollection;
}


public void setMessageLogDTCollection(Collection messageLogDTCollection) {
	this.messageLogDTCollection = messageLogDTCollection;
}


/**
 * @return the processingDecisionLogic
 */
public String getProcessingDecisionLogic() {
	return processingDecisionLogic;
}


/**
 * @param processingDecisionLogic the processingDecisionLogic to set
 */
public void setProcessingDecisionLogic(String processingDecisionLogic) {
	this.processingDecisionLogic = processingDecisionLogic;
}


/**
 * @return the programArea
 */
public String getProgramArea() {
	return programArea;
}


/**
 * @param programArea the programArea to set
 */
public void setProgramArea(String programArea) {
	this.programArea = programArea;
}
/**
   *collection of ResultedTestVOs
   */
   private Collection<Object>  theResultedTestVOCollection  = null;


   /**
      *collection of ResultedTestVOs
      */
      private Collection<Object>  theOldResultedTestVOCollection  = null;

      private ObservationVO patientStatusVO = null;
      private ObservationVO oldPatientStatusVO = null;
      
  /**
   * This no argument constructor
   */
    public ObservationForm() {
    }


  /**
   * Access method for the proxy property.
   *
   * @return  LabResultProxyVO the current value of the LabResultProxyVO property
   */

  public LabResultProxyVO getProxy() {
    if (proxy == null)
      proxy = new LabResultProxyVO();

    return this.proxy;
  }


  /**
  * Access method for proxy object exists.
  *
  * @return   boolean values
  */
  public boolean hasProxy()
  {
    if (proxy == null)
      return false;
    else
      return true;
  }


  /**
   * Sets the value of the proxy property.
   *
   * @param the new value of the ObservationProxyVO property
   */
  public void setProxy(LabResultProxyVO newVal) {
    this.proxy = newVal;
  }


  /**
   * Sets the value of the proxy property.
   *
   * @param the new value of the ObservationProxyVO property
   */
  public void setOldProxy(LabResultProxyVO oldVal) {
    this.oldProxy = oldVal;
  }

  /**
   * Access method for the proxy property.
   *
   * @return  LabResultProxyVO the current value of the LabResultProxyVO property
   */

  public LabResultProxyVO getOldProxy() {
    return this.oldProxy;
  }


  /**
   * this method is help to create PersonVO object
   */
  public PersonVO getPatient(){
    if(this.patient == null)
      this.patient = new PersonVO();
    return this.patient;
  }

  public void setPatient(PersonVO patient) {
        this.patient = patient;

      }

  /**
   * this method is help to create PersonVO object
   */
    public PersonVO getPatientRevision(){

      return this.patientRevision;
    }

    public void setPatientRevision(PersonVO patientRevision) {
          this.patientRevision = patientRevision;

    }


  /**
   * This method is help to create ResultedTestVO object
   */
  public ResultedTestVO getResultedTestVO(){
    if(this.resultedTestVO == null)
      this.resultedTestVO = new ResultedTestVO();
    return this.resultedTestVO;
  }

  public ResultedTestVO getResultedTest(int index) {
  // this should really be in the constructor
    if (this.getResultedTestVOCollection() == null)
      this.theResultedTestVOCollection  = new ArrayList<Object> ();
    int currentSize = this.theResultedTestVOCollection.size();
//System.out.println("size of collection is currentSize" + currentSize);
    // check if we have a this many DTs
    if (index < currentSize) {
      try {
        Object[] tempArray = this.theResultedTestVOCollection.toArray();

        Object tempObj = tempArray[index];

        ResultedTestVO tempVO = (ResultedTestVO) tempObj;

        return tempVO;
      }
      catch (Exception e) {    } // do nothing just continue
    } //if
    ResultedTestVO tempVO = null;
    for (int i = currentSize; i < index + 1; i++) {
      tempVO = new ResultedTestVO();
      tempVO.setItNew(true); // this should be done in the constructor of the DT
      this.theResultedTestVOCollection.add(tempVO);
    }
    return tempVO;

  }//getResultedTest

  public ArrayList<Object> getResultedTestVOCollection() {
  return (ArrayList<Object> )theResultedTestVOCollection;
  }

  public void setResultedTestVOCollection(ArrayList<Object>  resultedTestVOCollection) {
    if(theResultedTestVOCollection  != null)

        this.theResultedTestVOCollection  = resultedTestVOCollection;
  }


  public ArrayList<Object> getOldResultedTestVOCollection() {
    return (ArrayList<Object> )theOldResultedTestVOCollection;
    }

    public void setOldResultedTestVOCollection(ArrayList<Object>  oldResultedTestVOCollection) {

          this.theOldResultedTestVOCollection  = oldResultedTestVOCollection;
    }



  /**
   *  This mathod is help you to create MaterialVO object
   */
  public MaterialVO getMaterialVO(){
    if(this.materialVO == null)
      this.materialVO = new MaterialVO();
    return this.materialVO;
  }


	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}
      /**
      * Access method for the reset form objects.
      *
      */
      public void reset()
      {
    	isStdOrHivProgArea=false;
		oldResultedTestVO=null;
		messageLogDTCollection=null;
		processingDecisionLogic="none";
	    //System.out.println("this is from reset value of form");
   	    oldProxy= null;
        proxy = null;
        oldPatientStatusVO=null;
        patientRevision=null;
        patientStatusVO=null;
        materialVO = null;
        patient = null;
        resultedTestVO = null;
        if(theResultedTestVOCollection  != null){
          for (int i = 0; theResultedTestVOCollection.size() > i; i++) {
            theResultedTestVOCollection.clear();
          }
          System.out.println("theResultedTestVOCollection.isEmpty()" +
                             theResultedTestVOCollection.isEmpty());
        }
        theResultedTestVOCollection  = null;
        super.resetLDF();
      }


	public ObservationVO getPatientStatusVO() {
		if(this.patientStatusVO==null)
			this.patientStatusVO = new ObservationVO();
		return patientStatusVO;
	}


	public void setPatientStatusVO(ObservationVO patientStatusVO) {
		this.patientStatusVO = patientStatusVO;
	}


	public ObservationVO getOldPatientStatusVO() {
		if(this.oldPatientStatusVO==null)
			this.oldPatientStatusVO = new ObservationVO();
		return oldPatientStatusVO;
	}


	public void setOldPatientStatusVO(ObservationVO oldPatientStatusVO) {
		this.oldPatientStatusVO = oldPatientStatusVO;
	}

	public Object getCodedValue(String codeSetNm) {
		return CachedDropDowns.getCodedValueForType(codeSetNm);
	}
	public Object getProcessingDecisionList(){
		return CachedDropDowns.getCodedValueForType(getProcessingDecisionLogic());
	}

}