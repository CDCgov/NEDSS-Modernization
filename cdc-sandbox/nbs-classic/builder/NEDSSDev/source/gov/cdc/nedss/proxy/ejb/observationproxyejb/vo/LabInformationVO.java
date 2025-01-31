package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.util.AbstractVO;
import java.util.Collection;
import java.util.Map;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;

public class LabInformationVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   private Collection<Object>  theObservationVOCollection;
   private OrganizationVO theReportingFacility;
   private OrganizationVO theOrderingFacility;
   private PersonVO theOrderingProvider;
   private Map<Object, Object> discriptionText;
   /**
    * @roseuid 3EB5609800FC
    */
   public LabInformationVO()
   {

   }

   /**
    * Access method for the theObservationVOCollection  property.
    *
    * @return   the current value of the theObservationVOCollection  property
    */
   public Collection<Object>  getTheObservationVOCollection()
   {
      return theObservationVOCollection;
   }

   /**
    * Sets the value of the theObservationVOCollection  property.
    *
    * @param aTheObservationVOCollection  the new value of the theObservationVOCollection  property
    */
   public void setTheObservationVOCollection(Collection<Object> aTheObservationVOCollection)
   {
      theObservationVOCollection  = aTheObservationVOCollection;
   }

   /**
    * Access method for the theReportingFacility property.
    *
    * @return   the current value of the theReportingFacility property
    */
   public OrganizationVO getTheReportingFacility()
   {
      return theReportingFacility;
   }

   /**
    * Sets the value of the theReportingFacility property.
    *
    * @param aTheReportingFacility the new value of the theReportingFacility property
    */
   public void setTheReportingFacility(OrganizationVO aTheReportingFacility)
   {
      theReportingFacility = aTheReportingFacility;
   }

   /**
    * Access method for the theOrderingFacility property.
    *
    * @return   the current value of the theOrderingFacility property
    */
   public OrganizationVO getTheOrderingFacility()
   {
      return theOrderingFacility;
   }

   /**
    * Sets the value of the theOrderingFacility property.
    *
    * @param aTheOrderingFacility the new value of the theOrderingFacility property
    */
   public void setTheOrderingFacility(OrganizationVO aTheOrderingFacility)
   {
      theOrderingFacility = aTheOrderingFacility;
   }

   /**
    * Access method for the theOrderingProvider property.
    *
    * @return   the current value of the theOrderingProvider property
    */
   public PersonVO getTheOrderingProvider()
   {
      return theOrderingProvider;
   }

   /**
    * Sets the value of the theOrderingProvider property.
    *
    * @param aTheOrderingProvider the new value of the theOrderingProvider property
    */
   public void setTheOrderingProvider(PersonVO aTheOrderingProvider)
   {
      theOrderingProvider = aTheOrderingProvider;
   }

   /**
    * Access method for the theOrderingProvider property.
    *
    * @return   the current value of the theOrderingProvider property
    */
   public Map<Object, Object> getDescriptionText() {
     return discriptionText;
   }

   /**
    * Sets the value of the theOrderingProvider property.
    *
        * @param aTheOrderingProvider the new value of the theOrderingProvider property
    */
   public void setDescriptionText(Map<Object, Object> aDiscriptionText) {
     discriptionText = aDiscriptionText;
   }


   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3EB5616F001F
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3EB5616F012D
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3EB5616F019C
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3EB5616F01C4
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3EB5616F023C
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3EB5616F0264
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3EB5616F02DC
    */
   public boolean isItDelete()
   {
    return true;
   }
}
