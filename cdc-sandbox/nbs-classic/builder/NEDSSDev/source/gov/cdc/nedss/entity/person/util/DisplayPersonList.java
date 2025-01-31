//Source file: C:\\Rational_development\\source\\gov\\cdc\\nedss\\cdm\\helpers\\DisplayPersonList.java

package gov.cdc.nedss.entity.person.util;
import java.util.*;
import java.io.*;
import gov.cdc.nedss.util.DisplayList;

public class DisplayPersonList extends DisplayList implements Serializable
{
   private int totalCountOfPersonLists;
   private int firstPersonListOfList;
   private int countOfPersonListsInList;
   private int totalCountOfDistinctPersonLists;
   private ArrayList<Object> personListsInList;

   /**
    * @param totalCount
    * @param personList
    * @param startIndex
    * @param listCount
    * @roseuid 3C1653B10020
    */
   public DisplayPersonList(int totalCount, java.util.ArrayList<Object> personList, int startIndex, int listCount)
   {
          this.totalCountOfPersonLists = totalCount;
          this.personListsInList = personList;
          this.firstPersonListOfList = startIndex;
          this.countOfPersonListsInList = listCount;
   }

   /**
    * @roseuid 3C1653B10016
    */
   public DisplayPersonList()
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

public int getTotalCountOfDistinctPersonLists() {
	return totalCountOfDistinctPersonLists;
}

public void setTotalCountOfDistinctPersonLists(int totalCountOfDistinctPersonLists) {
	this.totalCountOfDistinctPersonLists = totalCountOfDistinctPersonLists;
}
}
/**
 *
 *
 * end of DisplayPersonList class
 *
 *
 */
