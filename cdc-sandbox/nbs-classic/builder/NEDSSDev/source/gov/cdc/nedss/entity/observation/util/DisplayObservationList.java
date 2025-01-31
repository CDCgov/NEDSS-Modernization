//Source file: C:\\Rational_development\\source\\gov\\cdc\\nedss\\cdm\\helpers\\DisplayPersonList.java

package gov.cdc.nedss.entity.observation.util;
import java.util.*;
import java.io.*;
import gov.cdc.nedss.util.DisplayList;

public class DisplayObservationList extends DisplayList implements Serializable
{
   private int totalCountOfPersonLists;
   private int firstPersonListOfList;
   private int countOfPersonListsInList;
   private ArrayList<Object> personListsInList;

   /**
    * @param totalCount
    * @param personList
    * @param startIndex
    * @param listCount
    * @roseuid 3C1653B10020
    */
   public DisplayObservationList(int totalCount, java.util.ArrayList<Object> personList, int startIndex, int listCount)
   {
          this.totalCountOfPersonLists = totalCount;
          this.personListsInList = personList;
          this.firstPersonListOfList = startIndex;
          this.countOfPersonListsInList = listCount;
   }

   /**
    * @roseuid 3C1653B10016
    */
   public DisplayObservationList()
   {

   }

   /**
    * @return int
    * @roseuid 3C1653B100A2
    */
   public int getTotalCounts()
   {
        return totalCountOfPersonLists;
   }

   /**
    * @return java.util.ArrayList
    * @roseuid 3C1653B100AC
    */
   public ArrayList<Object> getList()
   {
        return personListsInList;
   }

   /**
    * @return int
    * @roseuid 3C1653B10142
    */
   public int getListCounts()
   {
        return countOfPersonListsInList;
   }

   /**
    * @return int
    * @roseuid 3C1653B10156
    */
   public int getFirstElementIndex()
   {
        return firstPersonListOfList;
   }

public ArrayList<Object> getPersonListsInList() {
	return personListsInList;
}

public void setPersonListsInList(ArrayList<Object> personListsInList) {
	this.personListsInList = personListsInList;
}
}
/**
 *
 *
 * end of DisplayPersonList class
 *
 *
 */
