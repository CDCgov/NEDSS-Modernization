//Source file: C:\\CDC\\Code Frameworks\\gov\\cdc\\nedss\\helpers\\WorkupProxyVO.java

package gov.cdc.nedss.proxy.ejb.workupproxyejb.vo;

import  gov.cdc.nedss.util.*;
import java.util.Collection;
import java.util.HashMap;

import gov.cdc.nedss.entity.person.vo.*;

public class WorkupProxyVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   public PersonVO thePersonVO;
   public Collection<Object>  theInvestigationSummaryVOCollection;
   public Collection<Object>  theMorbReportSummaryVOCollection;
   public Collection<Object>  theLabReportSummaryVOCollection;
	 public Collection<Object>  theUnprocessedMorbReportSummaryVOCollection;
   public Collection<Object>  theUnprocessedLabReportSummaryVOCollection;
   public HashMap<Object,Object> theVaccinationSummaryVOCollection;
   public HashMap<Object,Object> theTreatmentSummaryVOCollection;
   public Collection<Object>  theDocumentSummaryColl;
   private Collection<Object>  theUnprocessesDocumentColl;
   private Collection<Object>  theContactSummaryVOColl;
   private Collection<Object> activeRevisionUidsColl;
   

   
public Collection<Object> getActiveRevisionUidsColl() {
	return activeRevisionUidsColl;
}

public void setActiveRevisionUidsColl(Collection<Object> activeRevisionUidsColl) {
	this.activeRevisionUidsColl = activeRevisionUidsColl;
}

public Collection<Object> getTheContactSummaryVOColl() {
	return theContactSummaryVOColl;
}

public void setTheContactSummaryVOColl(
		Collection<Object> theContactSummaryVOColl) {
	this.theContactSummaryVOColl = theContactSummaryVOColl;
}

/**
    * @roseuid 3BFC2C0901F1
    */
   public WorkupProxyVO()
   {

   }

   /**
    * Access method for the thePersonVO property.
    *
    * @return   the current value of the thePersonVO property
    */
   public PersonVO getThePersonVO()
   {
      return thePersonVO;
   }

   /**
    * Sets the value of the thePersonVO property.
    *
    * @param aThePersonVO the new value of the thePersonVO property
    */
   public void setThePersonVO(PersonVO aThePersonVO)
   {
      thePersonVO = aThePersonVO;
   }

   /**
    * Access method for the theInvestigationSummaryVOCollection  property.
    *
    * @return   the current value of the theInvestigationSummaryVOCollection  property
    */
   public Collection<Object>  getTheInvestigationSummaryVOCollection()
   {
      return theInvestigationSummaryVOCollection;
   }

   /**
    * Sets the value of the theInvestigationSummaryVOCollection  property.
    *
    * @param aTheInvestigationSummaryVOCollection  the new value of the theInvestigationSummaryVOCollection  property
    */
   public void setTheInvestigationSummaryVOCollection(Collection<Object> aTheInvestigationSummaryVOCollection)
   {
      theInvestigationSummaryVOCollection  = aTheInvestigationSummaryVOCollection;
   }

   /**
    * Access method for the theLabReportSummaryVOCollection  property.
    *
    * @return   the current value of the theLabReportSummaryVOCollection  property
    */
   public Collection<Object>  getTheLabReportSummaryVOCollection()
   {
      return theLabReportSummaryVOCollection;
   }

   /**
    * Sets the value of the theLabReportSummaryVOCollection  property.
    *
    * @param aTheLabReportSummaryVOCollection  the new value of the theLabReportSummaryVOCollection  property
    */
   public void setTheLabReportSummaryVOCollection(Collection<Object> aTheLabReportSummaryVOCollection)
   {
      theLabReportSummaryVOCollection  = aTheLabReportSummaryVOCollection;
   }


 /**
    * Access method for the theLabReportSummaryVOCollection  property.
    *
    * @return   the current value of the theLabReportSummaryVOCollection  property
    */
   public Collection<Object>  getTheUnprocessedLabReportSummaryVOCollection()
   {
      return theUnprocessedLabReportSummaryVOCollection;
   }

   /**
    * Sets the value of the theLabReportSummaryVOCollection  property.
    *
    * @param aTheLabReportSummaryVOCollection  the new value of the theLabReportSummaryVOCollection  property
    */
   public void setTheUnprocessedLabReportSummaryVOCollection(Collection<Object> aTheUnprocessedLabReportSummaryVOCollection)
   {
      theUnprocessedLabReportSummaryVOCollection  = aTheUnprocessedLabReportSummaryVOCollection;
   }






   /**
    * Access method for the theMorbReportSummaryVOCollection  property.
    *
    * @return   the current value of the theMorbReportSummaryVOCollection  property
    */
   public Collection<Object>  getTheMorbReportSummaryVOCollection()
   {
      return theMorbReportSummaryVOCollection;
   }

   /**
    * Sets the value of the theMorbReportSummaryVOCollection  property.
    *
    * @param aTheMorbReportSummaryVOCollection  the new value of the theMorbReportSummaryVOCollection  property
    */
   public void setTheMorbReportSummaryVOCollection(Collection<Object> aTheMorbReportSummaryVOCollection)
   {
      theMorbReportSummaryVOCollection  = aTheMorbReportSummaryVOCollection;
   }


	/**
    * Access method for the theMorbReportSummaryVOCollection  property.
    *
    * @return   the current value of the theMorbReportSummaryVOCollection  property
    */
   public Collection<Object>  getTheUnprocessedMorbReportSummaryVOCollection()
   {
      return theUnprocessedMorbReportSummaryVOCollection;
   }

   /**
    * Sets the value of the theMorbReportSummaryVOCollection  property.
    *
    * @param aTheMorbReportSummaryVOCollection  the new value of the theMorbReportSummaryVOCollection  property
    */
   public void setTheUnprocessedMorbReportSummaryVOCollection(Collection<Object> aTheMorbReportSummaryVOCollection)
   {
      theUnprocessedMorbReportSummaryVOCollection  = aTheMorbReportSummaryVOCollection;
   }


   /**
    * Access method for the theVaccinationSummaryVOCollection  property.
    *
    * @return   the current value of the theVaccinationSummaryVOCollection  property
    */
   public HashMap<Object,Object> getTheVaccinationSummaryVOCollection()
   {
      return theVaccinationSummaryVOCollection;
   }

   /**
    * Sets the value of the theVaccinationSummaryVOCollection  property.
    *
    * @param aTheVaccinationSummaryVOCollection  the new value of the theVaccinationSummaryVOCollection  property
    */
   public void setTheVaccinationSummaryVOCollection(HashMap<Object,Object> aTheVaccinationSummaryVOCollection)
   {
      theVaccinationSummaryVOCollection  = aTheVaccinationSummaryVOCollection;
   }


   /**
    * Access method for the theTreatmentSummaryVOCollection  property.
    *
    * @return   the current value of the theTreatmentSummaryVOCollection  property
    */
   public HashMap<Object,Object> getTheTreatmentSummaryVOCollection()
   {
      return theTreatmentSummaryVOCollection;
   }

   /**
    * Sets the value of the theTreatmentSummaryVOCollection  property.
    *
    * @param aTheTreatmentSummaryVOCollection  the new value of the theTreatmentSummaryVOCollection  property
    */
   public void setTheTreatmentSummaryVOCollection(HashMap<Object,Object> aTheTreatmentSummaryVOCollection)
   {
      theTreatmentSummaryVOCollection  = aTheTreatmentSummaryVOCollection;
   }
   

   public Collection<Object>  getTheDocumentSummaryColl() {
	return theDocumentSummaryColl;
   }

   public void setTheDocumentSummaryColl(Collection<Object> theDocumentSummaryColl) {
	this.theDocumentSummaryColl = theDocumentSummaryColl;
   }	
   
   public Collection<Object>  getTheUnprocessesDocumentColl() {
		return theUnprocessesDocumentColl;
   }
	
   public void setTheUnprocessesDocumentColl(Collection<Object> theUnprocessesDocumentColl) {
		this.theUnprocessesDocumentColl = theUnprocessesDocumentColl;
   }

/**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3BFC2C090205
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3BFC2C090256
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3BFC2C090274
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3BFC2C09027E
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3BFC2C0902A6
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3C02DBCC030F
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C02DBCC034B
    */
   public boolean isItDelete()
   {
    return true;
   }
}
