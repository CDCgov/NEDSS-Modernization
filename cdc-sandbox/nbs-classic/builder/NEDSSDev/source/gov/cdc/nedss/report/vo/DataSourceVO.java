package gov.cdc.nedss.report.vo;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.report.dt.*;

import java.util.Collection;

public class DataSourceVO extends AbstractVO implements java.io.Serializable
{

    protected DataSourceDT theDataSourceDT;
    protected Collection<Object>  theDataSourceColumnDTCollection;

    /**
    * @roseuid 3C17F7280263
    */
    public DataSourceVO()
    {
    }

    /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C17F7280277
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        return true;
    }

    /**
    * @param itDelete
    * @roseuid 3C17F72803E0
    */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7290048
    */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
    * @param itDirty
    * @roseuid 3C17F7280321
    */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7280367
    */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
    * @param itNew
    * @roseuid 3C17F7280385
    */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
    * @return boolean
    * @roseuid 3C17F72803CC
    */
    public boolean isItNew()
    {
        return itNew;
    }

    /**
    * Sets the value of the theDataSourceColumnDTCollection  property.
    *
    * @param aTheDataSourceColumnDTCollection  the new value of the theDataSourceColumnDTCollection  property
    */
    public void setTheDataSourceColumnDTCollection(Collection<Object> aTheDataSourceColumnDTCollection)
    {
        theDataSourceColumnDTCollection  = aTheDataSourceColumnDTCollection;
        setItDirty(true);
    }

    /**
    * Access method for the theDataSourceColumnDTCollection  property.
    *
    * @return   the current value of the theDataSourceColumnDTCollection  property
    */
    public Collection<Object>  getTheDataSourceColumnDTCollection()
    {
        return theDataSourceColumnDTCollection;
    }

    /**
    * Sets the value of the theDataSourceDT property.
    *
    * @param aTheDataSourceDT the new value of the theDataSourceDT property
    */
    public void setTheDataSourceDT(DataSourceDT aTheDataSourceDT)
    {
        theDataSourceDT = aTheDataSourceDT;
        setItDirty(true);
    }

    /**
    * Access method for the theDataSourceDT property.
    *
    * @return   the current value of the theDataSourceDT property
    */
    public DataSourceDT getTheDataSourceDT()
    {
        return theDataSourceDT;
    }

}
