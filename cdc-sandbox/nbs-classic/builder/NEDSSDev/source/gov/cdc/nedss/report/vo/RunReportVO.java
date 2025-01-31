package gov.cdc.nedss.report.vo;

import  gov.cdc.nedss.util.*;

public class RunReportVO extends AbstractVO
{

    private String host;
    private int port;
    private String exportType;

    /**
    * @roseuid 3C22C40703DF
    */
    public RunReportVO()
    {
    }

    /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C22C408000B
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        return true;
    }

    /**
    * Sets the value of the exportType property.
    *
    * @param aExportType the new value of the exportType property
    */
    public void setExportType(String aExportType)
    {
        exportType = aExportType;
    }

    /**
    * Access method for the exportType property.
    *
    * @return   the current value of the exportType property
    */
    public String getExportType()
    {
        return exportType;
    }

    /**
    * Sets the value of the host property.
    *
    * @param aHost the new value of the host property
    */
    public void setHost(String aHost)
    {
        host = aHost;
    }

    /**
    * Access method for the host property.
    *
    * @return   the current value of the host property
    */
    public String getHost()
    {
        return host;
    }

    /**
    * @param itDelete
    * @roseuid 3C22C4080083
    */
    public void setItDelete(boolean itDelete)
    {
    }

    /**
    * @return boolean
    * @roseuid 3C22C40800A1
    */
    public boolean isItDelete()
    {
        return true;
    }

    /**
    * @param itDirty
    * @roseuid 3C22C408003D
    */
    public void setItDirty(boolean itDirty)
    {
    }

    /**
    * @return boolean
    * @roseuid 3C22C4080051
    */
    public boolean isItDirty()
    {
        return true;
    }

    /**
    * @param itNew
    * @roseuid 3C22C4080065
    */
    public void setItNew(boolean itNew)
    {
    }

    /**
    * @return boolean
    * @roseuid 3C22C4080079
    */
    public boolean isItNew()
    {
        return true;
    }

    /**
    * Sets the value of the port property.
    *
    * @param aPort the new value of the port property
    */
    public void setPort(int aPort)
    {
        port = aPort;
    }

    /**
    * Access method for the port property.
    *
    * @return   the current value of the port property
    */
    public int getPort()
    {
        return port;
    }

}
