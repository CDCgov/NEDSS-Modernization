<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0"
>

    <!-- Load up a bunch of common things that are needed in nearly every page. -->
    <xsl:template match="nedss:init">
        <xsp:logic>
        <![CDATA[

            //  Start services.
             javax.servlet.http.HttpServletResponse httpResponse =	(javax.servlet.http.HttpServletResponse)this.objectModel.get(HttpEnvironment.HTTP_RESPONSE_OBJECT);

	    NEDSSConstants ncu = new NEDSSConstants();
            ReportConstantUtil rcu = new ReportConstantUtil();
            LogUtils logger = new LogUtils((this.getClass()).getName());
            NBSSecurityObj so = (NBSSecurityObj)getData("NBSSecurityObject", true);
            //CachedDropDownValues cdv = (CachedDropDownValues)context.getAttribute("CachedDropDownValues");
            boolean booServer = isServer();

            //  Read properties.
            PropertyUtil pu = PropertyUtil.getInstance();
            
            String strMIN_MMWR_YEAR  = pu.getMIN_MMWR_YEAR();
            String strNBS_STATE_CODE = pu.getNBS_STATE_CODE();

            //  Read request attributes that pertain to context management.
            String strContextAction = (String)request.getAttribute("ContextAction");
            String strCurrentTask = (String)request.getAttribute("CurrentTask");

            //  Read parameters.
            String strBorder = request.getParameter("border");
            if(strBorder == null)
            {
                strBorder = "0";
            }
            String strUIDs = request.getParameter("uids");
            if(strUIDs == null)
            {
                strUIDs = "false";
            }
            String strUser = getUser();
            String strUserMode = request.getParameter("mode");
            if(strUserMode == null)
            {
                strUserMode = (String)request.getSession().getAttribute("mode");
            }
            strUserMode = XMLRequestHelper.getMode(strUserMode);
            String strVerbose = request.getParameter("verbose");
            if(strVerbose == null)
            {
                strVerbose = "false";
            }

        ]]>
        </xsp:logic>
    </xsl:template>

</xsl:stylesheet>

