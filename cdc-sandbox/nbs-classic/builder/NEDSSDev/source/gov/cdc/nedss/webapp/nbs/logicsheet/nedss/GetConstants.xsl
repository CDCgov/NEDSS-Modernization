<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0"
>

    <!-- Gets constants from a class.  Caches them in the servlet context. -->
    <!-- The way this is currently designed, the constants must be in a class, not an interface. -->
    <!-- And the class has to have already been loaded by the classloader. -->
    <xsl:template match="nedss:GetConstants">
        <xsl:if test="@class">
            <xsp:logic>
                //  Name of class to read.  Can be a class name only or a full path name.
                String str<xsl:value-of select="@class"/>_class = "nedss:GetConstants[<xsl:value-of select="@class"/>]";
                //  Full path name of class.
//              String str<xsl:value-of select="@class"/>_classname = null;
                //  List of constants in that class.
                String str<xsl:value-of select="@class"/>_const = (String)context.getAttribute(str<xsl:value-of select="@class"/>_class);
                if(str<xsl:value-of select="@class"/>_const == null)
                {
                    //  If it's not in the cache yet, load it and put it in the cache.
                    <xsl:value-of select="@class"/> obj<xsl:value-of select="@class"/> = new <xsl:value-of select="@class"/>();
                    Class cls<xsl:value-of select="@class"/> = obj<xsl:value-of select="@class"/>.getClass();
//                  str<xsl:value-of select="@class"/>_classname = cls<xsl:value-of select="@class"/>.getName();
                    str<xsl:value-of select="@class"/>_const = XMLRequestHelper.getConstants(cls<xsl:value-of select="@class"/>);
                    context.setAttribute(str<xsl:value-of select="@class"/>_class, str<xsl:value-of select="@class"/>_const);
                }
            </xsp:logic>
<!--        <xsl:comment> Constants from <xsp:expr>str<xsl:value-of select="@class"/>_classname</xsp:expr> </xsl:comment> -->
            <xsl:comment> Constants from <xsl:value-of select="@class"/> </xsl:comment>
            <xsp:expr>str<xsl:value-of select="@class"/>_const</xsp:expr>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
