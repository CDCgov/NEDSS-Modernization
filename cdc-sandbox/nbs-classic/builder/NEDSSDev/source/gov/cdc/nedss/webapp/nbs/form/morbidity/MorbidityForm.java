package gov.cdc.nedss.webapp.nbs.form.morbidity;

import java.util.*;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.webapp.nbs.util.*;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.webapp.nbs.form.util.*;

public class MorbidityForm
    extends CommonForm
{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private MorbidityProxyVO morbidityProxy = null;
  private MorbidityProxyVO oldProxy = null;
  private ObservationVO morbidityReport = null;
  private Collection<Object>  labResultsCollection  = null;
  private Collection<Object>  treatmentCollection  = null;
  private Collection<Object>  attachmentCollection  = null;
  private FormFile fileAttachment = null;
  private FormFile fileAttachment0 = null;
  private FormFile fileAttachment1 = null;
  private FormFile fileAttachment2 = null;
  private FormFile fileAttachment3 = null;
  private FormFile fileAttachment4 = null;
  private FormFile fileAttachment5 = null;
  private FormFile fileAttachment6 = null;
  private FormFile fileAttachment7 = null;
  private FormFile fileAttachment8 = null;
  private FormFile fileAttachment9 = null;
  private FormFile fileAttachment10 = null;
  
  
  private PersonVO patient = null;
  private TreeMap<Object,Object> qaTreemap = null;
  private HashMap<String, FormFile> forms = new HashMap<String, FormFile>();

  /******************************************
   * RESET METHOD
   */
  public void reset()
  {
    morbidityProxy = null;
    morbidityReport = null;
    labResultsCollection  = null;
    treatmentCollection  = null;
    patient = null;
    qaTreemap = null;
    oldProxy = null;
    super.resetLDF();
  }

  /********************************************
   * GETTERS
   */
  public MorbidityProxyVO getMorbidityProxy()
  {

    if (this.morbidityProxy == null)
    {
      this.morbidityProxy = new MorbidityProxyVO();
    }

    return this.morbidityProxy;
  }

  //  the root observation
  public ObservationVO getMorbidityReport()
  {

    if (this.morbidityReport == null)
    {
      this.morbidityReport = new ObservationVO();
    }

    return this.morbidityReport;
  }

  public Collection<Object>  getLabResultsCollection()
  {
    return this.labResultsCollection;
  }

  public Collection<Object>  getTreatmentCollection()
  {
    return this.treatmentCollection;
  }

  /***********************************************
   * SETTERS
   */
  public void setMorbidityProxy(MorbidityProxyVO proxy)
  {
    this.morbidityProxy = proxy;
  }

  public void setMorbidityReport(ObservationVO obsVO)
  {
    this.morbidityReport = obsVO;
  }

  public void setLabResultsCollection(Collection<Object> labs)
  {
    this.labResultsCollection  = labs;
  }

  public void setTreatmentsCollection(Collection<Object> treatments)
  {
    this.treatmentCollection  = treatments;
  }

  /************************************************
   * BATCH entry for observations
   */
  public BatchEntryHelper getLabResults_s(int index)
  {

    // this should really be in the constructor
    if (this.labResultsCollection  == null)
    {
      this.labResultsCollection  = new ArrayList<Object> ();
    }

    int currentSize = this.labResultsCollection.size();

    // check if we have a this many personNameDTs
    if (index < currentSize)
    {

      try
      {

        Object[] tempArray = this.labResultsCollection.toArray();
        Object tempObj = tempArray[index];
        BatchEntryHelper temp = (BatchEntryHelper) tempObj;

        return temp;
      }
      catch (Exception e)
      {
        //##!! System.out.println(e); // do nothing just continue
      }
    }

    BatchEntryHelper temp = null;

    for (int i = currentSize; i < index + 1; i++)
    {
      temp = new BatchEntryHelper();
      this.labResultsCollection.add(temp);
    }

    return temp;
  }

  /************************************************
   * BATCH entry for Treatments
   */
  public BatchEntryHelper getTreatment_s(int index)
  {
    if (this.treatmentCollection  == null)
    {
      this.treatmentCollection  = new ArrayList<Object> ();
    }

    int currentSize = this.treatmentCollection.size();

    if (index < currentSize)
    {
      try
      {
        Object[] tempArray = this.treatmentCollection.toArray();
        Object tempObj = tempArray[index];
        BatchEntryHelper temp = (BatchEntryHelper) tempObj;

        return temp;
      }
      catch (Exception e)
      {
        // do nothing just continue
      }
    }

    BatchEntryHelper temp = null;

    for (int i = currentSize; i < index + 1; i++)
    {
      temp = new BatchEntryHelper();
      this.treatmentCollection.add(temp);
    }

    return temp;
  }
  
  /************************************************
   * BATCH entry for Treatments
   */
  public BatchEntryHelper getAttachment_s(int index)
  {
    if (this.attachmentCollection  == null)
    {
      this.attachmentCollection  = new ArrayList<Object> ();
    }

    int currentSize = this.attachmentCollection.size();

    if (index < currentSize)
    {
      try
      {
        Object[] tempArray = this.attachmentCollection.toArray();
        Object tempObj = tempArray[index];
        BatchEntryHelper temp = (BatchEntryHelper) tempObj;

        return temp;
      }
      catch (Exception e)
      {
        // do nothing just continue
      }
    }

    BatchEntryHelper temp = null;

    for (int i = currentSize; i < index + 1; i++)
    {
      temp = new BatchEntryHelper();
      this.attachmentCollection.add(temp);
    }

    return temp;
  }

  /**
   * getPatient to retrieve revision patient for Invg.
   */
  public PersonVO getPatient()
  {
    if (this.patient == null)
    {
      this.patient = new PersonVO();
    }
    return this.patient;
  }

  /**
   * setPatient to persist revision patient for Invg.
   */
  public void setPatient(PersonVO patient)
  {
    this.patient = patient;
  }

  public void setQaTreeMap(TreeMap<Object,Object> aTreeMap)
  {
    this.qaTreemap = aTreeMap;
  }

  public TreeMap<Object,Object> getQaTreeMap()
  {
    return this.qaTreemap;
  }

  public void setOldProxy(MorbidityProxyVO aOldProxy)
  {
    this.oldProxy = aOldProxy;
  }

  public MorbidityProxyVO getOldProxy()
  {
    return this.oldProxy;
  }

public Collection<Object> getAttachmentCollection() {
	return attachmentCollection;
}

public void setAttachmentCollection(Collection<Object> attachmentCollection) {
	this.attachmentCollection = attachmentCollection;
}

public FormFile getFileAttachment() {
	return fileAttachment;
}

public void setFileAttachment(FormFile fileAttachment) {
	this.fileAttachment = fileAttachment;
}


public Object alertMethod(String a){
	
	
	return (String)"Hello";
}

public Object storeFileJava(){
//	forms.put("1",(FormFile)file);
	return (String)"Hello";
}

public FormFile getFileAttachment0() {
	return fileAttachment0;
}

public void setFileAttachment0(FormFile fileAttachment0) {
	this.fileAttachment0 = fileAttachment0;
}

public FormFile getFileAttachment1() {
	return fileAttachment1;
}

public void setFileAttachment1(FormFile fileAttachment1) {
	this.fileAttachment1 = fileAttachment1;
}

public FormFile getFileAttachment2() {
	return fileAttachment2;
}

public void setFileAttachment2(FormFile fileAttachment2) {
	this.fileAttachment2 = fileAttachment2;
}

public FormFile getFileAttachment3() {
	return fileAttachment3;
}

public void setFileAttachment3(FormFile fileAttachment3) {
	this.fileAttachment3 = fileAttachment3;
}

public FormFile getFileAttachment4() {
	return fileAttachment4;
}

public void setFileAttachment4(FormFile fileAttachment4) {
	this.fileAttachment4 = fileAttachment4;
}

public FormFile getFileAttachment5() {
	return fileAttachment5;
}

public void setFileAttachment5(FormFile fileAttachment5) {
	this.fileAttachment5 = fileAttachment5;
}

public FormFile getFileAttachment6() {
	return fileAttachment6;
}

public void setFileAttachment6(FormFile fileAttachment6) {
	this.fileAttachment6 = fileAttachment6;
}

public FormFile getFileAttachment7() {
	return fileAttachment7;
}

public void setFileAttachment7(FormFile fileAttachment7) {
	this.fileAttachment7 = fileAttachment7;
}

public FormFile getFileAttachment8() {
	return fileAttachment8;
}

public void setFileAttachment8(FormFile fileAttachment8) {
	this.fileAttachment8 = fileAttachment8;
}

public FormFile getFileAttachment9() {
	return fileAttachment9;
}

public void setFileAttachment9(FormFile fileAttachment9) {
	this.fileAttachment9 = fileAttachment9;
}

public FormFile getFileAttachment10() {
	return fileAttachment10;
}

public void setFileAttachment10(FormFile fileAttachment10) {
	this.fileAttachment10 = fileAttachment10;
}

}