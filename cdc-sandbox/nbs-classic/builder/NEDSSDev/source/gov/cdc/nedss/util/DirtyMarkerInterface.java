


package gov.cdc.nedss.util;



import java.io.*;

/**
 * Title:         DirtyMarkerInterface is an Interface
 * Description:   Implemented by Objects to mark an Object to modified or new
 * Copyright:     Copyright (c) 2001
 * Company:       CSC
 * @author        CSC EMPLOYEE
 * @version       1.0
 *
 */


public interface DirtyMarkerInterface{


   /**
    * Sets the value of the itDirty property.
    *
    * @param idDirty boolean the new value of the ItDirty property
    */
     public void setItDirty(boolean itDirty);

   /**
    * get the value of the boolean property.
    *
    * @return ItDirty the value of the boolean property
    */
    public boolean isItDirty();

   /**
    * Sets the value of the ItNew property.
    *
    * @param itNew boolean the new value of the boolean property
    */
    public void setItNew(boolean itNew);

   /**
    * get the value of the itNew property.
    *
    * @return itNew the value of the boolean property
    */
    public boolean isItNew();

   /**
    * Sets the value of the ItDelete property.
    *
    * @param itDelete boolean the value of the boolean property
    */
     public void setItDelete(boolean itDelete);

   /**
    * gets the value of the ItDelete property.
    *
    * @return ItDelete the new value of the boolean property
    */
    public boolean isItDelete();

}// End of Interface