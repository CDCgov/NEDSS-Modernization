<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0"
>

    <!-- Refactored the most common import statements so you don't have to write them in every page. -->
    <!-- Also include common functions that use the Cocoon environment, so they can't be in an external Java class.-->
    <xsl:template match="nedss:import">
        <xsp:structure>
            <xsp:include>java.io.*</xsp:include>
            <xsp:include>java.net.*</xsp:include>
            <xsp:include>java.text.*</xsp:include>
            <xsp:include>java.util.*</xsp:include>
            <xsp:include>gov.cdc.nedss.report.util.*</xsp:include>
            <xsp:include>gov.cdc.nedss.systemservice.nbscontext.*</xsp:include>
            <xsp:include>gov.cdc.nedss.systemservice.nbssecurity.*</xsp:include>
            <xsp:include>gov.cdc.nedss.util.*</xsp:include>
            <xsp:include>gov.cdc.nedss.webapp.nbs.logicsheet.helper.*</xsp:include>
             <xsp:include>gov.cdc.nedss.webapp.nbs.action.util.*</xsp:include>
            <xsp:include>org.apache.cocoon.environment.http.*</xsp:include>
        </xsp:structure>
        <xsp:logic>
        <![CDATA[
            /**
             *  Checks to see if the client and the server are both running on the same machine.
             *  @return true if the client and the server are the same or false if not.
             */
            private boolean isServer()
            {
                //  Get client's IP address.
                String strClient = request.getRemoteAddr();
                if(strClient == null)
                {
                    return false;
                }
                if(strClient.equals(""))
                {
                    return false;
                }
                //  Get server's host name.
                String strHost = null;
                try
                {
                    strHost = InetAddress.getLocalHost().getHostName();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    return false;
                }
                if(strHost == null)
                {
                    return false;
                }
                if(strHost.equals(""))
                {
                    return false;
                }
                //    Get all IP addresses for the server.
                InetAddress[] addresses = null;
                try
                {
                    addresses = InetAddress.getAllByName(strHost);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                if(addresses == null)
                {
                    return false;
                }
                //  Loop through all addresses.
                InetAddress address = null;
                String strServer = null;
                int x = 0;
                int y = addresses.length;
                boolean b = false;
                for(x=0; x<y; x++)
                {
                    //  Get an address.
                    address = addresses[x];
                    strServer = address.getHostAddress();
                    //  See if it matches the client's IP address.
                    if(strServer.equals(strClient))
                    {
                        //  If it does, then we are browsing from the server.
                        b = true;
                        break;
                    }
                }
                return b;
            }
        ]]>
        </xsp:logic>
        <xsp:logic>
        <![CDATA[
            /**
             *  Gets a request attribute or a session attribute.
             *  @param pName the name of the object to get.
             *  @param pSession true to get the object from session
             *  or false to get it from request.
             *  @return the object, if found, or null if not found.
             */
            private Object getData(String pName, boolean pSession)
            {
                Object o = null;
                if(pSession == true)
                {
                    o = request.getSession().getAttribute(pName);
                }
                else
                {
                    o = request.getAttribute(pName);
                }
                return o;
            }
        ]]>
        </xsp:logic>
        <xsp:logic>
        <![CDATA[
            /**
             *  Loads test data.
             *  @param pFilename the file containing data to be loaded.
             *  @param pName the name of the request or session variable to load into.
             *  @param pSession true to put in session or false to put in request.
             */
            private void setData(String pFilename, String pName, boolean pSession)
            {
                //  Verify parameters.
                if(pFilename == null)
                {
                    return;
                }
                if(pName == null)
                {
                    return;
                }
                StringBuffer sb = new StringBuffer();
                String s = null;
                try
                {
                    Class c = this.getClass();
                    InputStream is = c.getResourceAsStream(pFilename);
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    while(true)
                    {
                        s = br.readLine();
                        if(s == null)
                        {
                            break;
                        }
                        s = s.trim();
                        s += " ";
                        sb.append(s);
                    }
                }
                catch(Exception ex)
                {
                    return;
                }
                s = sb.toString();
                if(pSession == true)
                {
                    request.getSession().setAttribute(pName, s);
                }
                else
                {
                    request.setAttribute(pName, s);
                }
            }
        ]]>
        </xsp:logic>
        <xsp:logic>
        <![CDATA[
            /**
             *  Gets the user's full name.
             *  @return the user's full name.
             */
            private String getUser()
            {
                //  Create return variable.
                String s = "";
                //  Get the security object.
                NBSSecurityObj so = (NBSSecurityObj)getData("NBSSecurityObject", true);
                if(so == null)
                {
                    return s;
                }
                //  Get the user's full name.
                s = so.getFullName();
                //  Return value.
                return s;
            }
        ]]>
        </xsp:logic>
    </xsl:template>

</xsl:stylesheet>
