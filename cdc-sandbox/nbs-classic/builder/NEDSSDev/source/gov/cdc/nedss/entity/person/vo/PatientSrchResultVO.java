//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\gov\\cdc\\nedss\\entity\\person\\vo\\PatientSrchResultVO.java

package gov.cdc.nedss.entity.person.vo;

import java.util.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.util.*;

public class PatientSrchResultVO extends PersonSrchResultVO
{
   private java.util.Collection<Object>  revisionColl;

   /**
    * @roseuid 3E7F427302DE
    */
   public PatientSrchResultVO()
   {

   }

   /**
    * Returns the revisionColl.
    * @return java.util.Collection
    * @roseuid 3E7F3EB002FD
    */
   public java.util.Collection<Object>  getRevisionColl()
   {
    return revisionColl;
   }

   /**
    * Sets the revisionColl.
    * @param revisionColl The revisionColl to set
    * @roseuid 3E7F3EB0031C
    */
   public void setRevisionColl(java.util.Collection<Object>  revisionColl)
   {
    this.revisionColl = revisionColl;
   }


  /**
   * An inner class
   */
  public static Comparator PatientSrchRsltVOComparator = new Comparator()
   {
    public int compare(Object patient1, Object patient2)
    {
      if (!(patient1 instanceof PatientSrchResultVO)
          || !(patient2 instanceof PatientSrchResultVO))
      {
        throw new ClassCastException("Error: a PatientSrchResultVO object expected.");
      }

      String lastName1 = ((PatientSrchResultVO)patient1).getLastName();
      lastName1 = lastName1 != null ? lastName1:"";
      lastName1=lastName1.toUpperCase();

      String firstName1 = ((PatientSrchResultVO)patient1).getFirstName();
      firstName1 = firstName1 !=null? firstName1:"";
      firstName1=firstName1.toUpperCase();

      String birthTimeCalc1 = ((PatientSrchResultVO)patient1).getPersonDOB();
      birthTimeCalc1 = birthTimeCalc1 !=null? birthTimeCalc1:"";
      birthTimeCalc1=birthTimeCalc1.toUpperCase();

      String lastName2 = ((PatientSrchResultVO) patient2).getLastName();
      lastName2 = lastName2 !=null? lastName2:"";
      lastName2=lastName2.toUpperCase();

      String firstName2 = ((PatientSrchResultVO) patient2).getFirstName();
      firstName2 = firstName2!=null? firstName2:"";
      firstName2=firstName2.toUpperCase();

      String birthTimeCalc2 = ((PatientSrchResultVO)patient2).getPersonDOB();
      birthTimeCalc2 = birthTimeCalc2!=null? birthTimeCalc2:"";
      birthTimeCalc2=birthTimeCalc2.toUpperCase();

      if (!(lastName1.equals(lastName2)))
        return lastName1.compareTo(lastName2);
      else
      {
        if (! (firstName1.equals(firstName2)))
          return firstName1.compareTo(firstName2);
        else
          return birthTimeCalc2.compareTo(birthTimeCalc1);
      }
    }
  };
}
