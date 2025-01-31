//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\gov\\cdc\\nedss\\entity\\person\\vo\\PatientRevisionSrchResultVO.java

package gov.cdc.nedss.entity.person.vo;

import java.util.*;

public class PatientRevisionSrchResultVO extends PersonSrchResultVO
{

   /**
    * @roseuid 3E7F2F22036B
    */
   public PatientRevisionSrchResultVO()
   {

   }

   /**
   * An inner class
   */
  public static Comparator PatientRevisionSrchRsltVOComparator = new Comparator()
   {
    public int compare(Object patientRevision1, Object patientRevision2)
    {
      if (!(patientRevision1 instanceof PatientRevisionSrchResultVO)
          || !(patientRevision2 instanceof PatientRevisionSrchResultVO))
      {
        throw new ClassCastException("Error: a PatientRevisionSrchResultVO object expected.");
      }

      String lastName1 = ((PatientRevisionSrchResultVO)patientRevision1).getLastName();
      lastName1 = lastName1 != null ? lastName1:"";
      lastName1=lastName1.toUpperCase();

      String firstName1 = ((PatientRevisionSrchResultVO)patientRevision1).getFirstName();
      firstName1 = firstName1 !=null? firstName1:"";
      firstName1=firstName1.toUpperCase();

      String birthTimeCalc1 = ((PatientRevisionSrchResultVO)patientRevision1).getPersonDOB();
      birthTimeCalc1 = birthTimeCalc1 !=null? birthTimeCalc1:"";
      birthTimeCalc1=birthTimeCalc1.toUpperCase();

      String lastName2 = ((PatientRevisionSrchResultVO) patientRevision2).getLastName();
      lastName2 = lastName2 !=null? lastName2:"";
      lastName2=lastName2.toUpperCase();

      String firstName2 = ((PatientRevisionSrchResultVO) patientRevision2).getFirstName();
      firstName2 = firstName2!=null? firstName2:"";
      firstName2=firstName2.toUpperCase();

      String birthTimeCalc2 = ((PatientRevisionSrchResultVO)patientRevision2).getPersonDOB();
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
