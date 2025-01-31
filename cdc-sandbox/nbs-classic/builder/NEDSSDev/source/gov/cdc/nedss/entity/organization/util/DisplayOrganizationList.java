/**
 * Title: DisplayOrganizationList helper class.
 * Description:	This class provides a list of organizations returned to the web tier
 * in response to a search request.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.organization.util;

import java.util.*;
import java.io.*;
import gov.cdc.nedss.util.DisplayList;

public class DisplayOrganizationList extends DisplayList implements Serializable
{
   private int totalCountOfOrganizationLists;
   private int firstOrganizationListOfList;
   private int countOfOrganizationListsInList;
   private ArrayList<Object> organizationListsInList;

  /**
   * Sets the values of the organization search result
   * @param totalCount the value of the number of records found
   * @param organizationList the list of the organizations found
   * @param startIndex the start index value of the organizations found in a list
   * @param listCount  the total number of organization records in a list
   */
   public DisplayOrganizationList(int totalCount, java.util.ArrayList<Object> organizationList, int startIndex, int listCount)
   {
          this.totalCountOfOrganizationLists = totalCount;
          this.organizationListsInList = organizationList;
          this.firstOrganizationListOfList = startIndex;
          this.countOfOrganizationListsInList = listCount;
   }

   /**
    *
    */
   public DisplayOrganizationList()
   {
   }

   /**
    * Access method for the totalCountOfOrganizationLists.
    * @return the current value of the totalCountOfOrganizationLists
    */
   public int getTotalCounts()
   {
        return totalCountOfOrganizationLists;
   }

  /**
    * Access method for the organizationListsInList.
    * @return the current value of the organizationListsInList
    */
   public ArrayList<Object> getList()
   {
        return organizationListsInList;
   }

  /**
    * Access method for the countOfOrganizationListsInList.
    * @return the current value of the countOfOrganizationListsInList
    */
   public int getListCounts()
   {
        return countOfOrganizationListsInList;
   }

  /**
    * Access method for the firstOrganizationListOfList.
    * @return the current value of the firstOrganizationListOfList
    */
   public int getFirstElementIndex()
   {
        return firstOrganizationListOfList;
   }
}
/**
 *
 *
 * end of DisplayOrganizationList class
 *
 *
 */
