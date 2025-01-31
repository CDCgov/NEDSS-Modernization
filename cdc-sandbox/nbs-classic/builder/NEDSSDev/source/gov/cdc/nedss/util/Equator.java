package gov.cdc.nedss.util;

import  java.util.*;

/**
 *  Equator.
 *  @author Ed Jenkins
 */
public interface Equator
{

    /**
     *  Determines whether this object's key value is less than the specified object's key value.
     *  @param o the object to compare to.
     *  @return true if this is less than that or false if not.
     */
    public boolean isLessThan(Object o);

    /**
     *  Determines whether this object's key value is greater than the specified object's key value.
     *  @param o the object to compare to.
     *  @return true if this is greater than that or false if not.
     */
    public boolean isGreaterThan(Object o);

}
