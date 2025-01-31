<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0">

	 <xsl:template match="nedss:writeErrorMessages">
	 	<xsp:logic><![CDATA[
	 		ArrayList errorMessages = (ArrayList)request.getAttribute("ErrorMessages");
	 		//System.out.println("errorMessages inside xsl = \n\n\n" + errorMessages);
		    String loadSubformJS = (String)request.getAttribute("loadSubformJS");
		    String saveSubformJS = (String)request.getAttribute("saveSubformJS");
		    String validateSubformJS = (String)request.getAttribute("validateSubformJS");

		    if(validateSubformJS != null && validateSubformJS.trim().length() > 0) {
			    int lastAppnd = validateSubformJS.lastIndexOf("&");
			    if(lastAppnd != -1)
			    validateSubformJS = validateSubformJS.substring(0,lastAppnd) + "; }";
		    }
		    
		    //System.out.println("-- validateSubformJS  inside xsl = \n\n\n" + validateSubformJS );

		    String disableSubformJS = (String)request.getAttribute("disableSubformJS");
		    String enableSubformJS = (String)request.getAttribute("enableSubformJS");
		    String clearSubformJS = (String) request.getAttribute("clearSubformJS");
	 	]]></xsp:logic>		

		<script>
			<xsp:expr>XMLRequestHelper.xmlEncodeArrayListToJavaScript(errorMessages, NEDSSConstants.ERROR_MESSAGES)</xsp:expr>
			<xsp:expr>loadSubformJS</xsp:expr>
			<xsp:expr>saveSubformJS</xsp:expr>
			<xsp:expr>validateSubformJS</xsp:expr>
			<xsp:expr>disableSubformJS</xsp:expr>
			<xsp:expr>enableSubformJS</xsp:expr>
			<xsp:expr>clearSubformJS</xsp:expr>
		</script>					
	 </xsl:template>
 </xsl:stylesheet>
