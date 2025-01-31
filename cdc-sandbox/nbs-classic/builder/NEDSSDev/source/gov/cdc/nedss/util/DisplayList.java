/**
* Name:        DisplayList.java
* Description:    This abstract class provides a list of objects returned to the web tier
*               in response to a search request.  Can be subclassed to
*               be used in search for Person, Observation, .....
* Copyright:    Copyright (c) 2001
* Company:     Computer Sciences Corporation
* @author    Brent Chen & NEDSS Development Team
* @version    1.0
*/
package gov.cdc.nedss.util;

import java.io.*;

import java.util.*;


public class DisplayList
    implements Serializable
{
	private static final long serialVersionUID = 1L;


    private int totalCountOfElementsInResultSet;
    private ArrayList<Object> elementsInList;
    private int firstElementOfList;
    private int countOfElementsInList;

    /**
     * Creates a new DisplayList object.
     */
    public DisplayList()
    {
    }

    /**
     * Creates a new DisplayList object.
     *
     * @param totalCount
     * @param list
     * @param startIndex
     * @param listCount
     */
    public DisplayList(int totalCount, ArrayList<Object> list, int startIndex,
                       int listCount)
    {
        this.totalCountOfElementsInResultSet = totalCount;
        this.elementsInList = list;
        this.firstElementOfList = startIndex;
        this.countOfElementsInList = listCount;
    }

    /**
     * Retrieves total search result counts
     * @return  the total counts
     */
    public int getTotalCounts()
    {

        return totalCountOfElementsInResultSet;
    }

    /**
     * Retrieves the search result list
     * @return  the list
     */
    public ArrayList<Object> getList()
    {

        return elementsInList;
    }

    /**
     * Retrieves the number of elements in the returned list
     * @return  an int number
     */
    public int getListCounts()
    {

        return countOfElementsInList;
    }

    /**
     * Retrieves the first element index in the list
     * @return
     */
    public int getFirstElementIndex()
    {

        return firstElementOfList;
    }
} //end of DisplayList class