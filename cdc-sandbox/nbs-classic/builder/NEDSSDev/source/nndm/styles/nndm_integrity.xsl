<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:IM="http://www.cdc.gov/NEDSS">
  <xsl:output method="xml" omit-xml-declaration="yes" encoding="UTF-8" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <!--============================================================-->
  <!-- This style provides templates to include Minimum Structural Intregrity          -->
  <!-- Rules required by the CDC receiving system (ADS) to process the             -->
  <!-- Notification Message.                                                                                     -->
  <!-- These rules are defined in NotificationMasterMessageIntegrityRules.doc    -->
  <!-- maintained by the NEDSS Base System Team.                                              -->
  <!--  Author: Craig Stanley                                                                                    -->
  <!--                Emergint, Inc.                                                                                   -->
  <!--  Created: 8-14-2002                                                                                       -->
  <!--  Modified: 9-25-2002 Added additional integrity rules.                                   -->
  <!--  Modified: 10-4-2002 Moved static rules to nndm_integrity_static.xsl.          -->
  <!--  Modified: 10-9-2002 Added participationTypeCd to Rule 4.                          -->
  <!--  Modified: 10-10-2002 Added raceCategoryCd to Rule 8.                             -->
  <!--  Modified: 02-27-2004 (by CSC Jeffrey Piper) Removed scopingEntity (entity2) from test condition in RULE 3  -->
  <!--  Modified: 03-12-2004 BWP removed odd non UTF-8 "'" from comments                 -->
  <!--============================================================-->
  <!--============================================================-->
  <!--                           Start at the root to begin the processing.                            -->
  <!--============================================================-->
  <xsl:template match="/">
    <xsl:apply-templates select="*"/>
  </xsl:template>
  <!--============================================================-->
  <!--  Generic copy template to pass through the attributes,     -->
  <!--  text nodes to the destination output.                     -->
  <!--============================================================-->
  <xsl:template match="@*|*|text()|comment()">
    <xsl:copy>
      <xsl:apply-templates select="@*|*|text()|comment()"/>
    </xsl:copy>
  </xsl:template>

  <!--============================================================-->
  <!-- Enforce the specific integrity rules.                                                               -->
  <!-- Templates were purposely not combined to aid readability.                          -->
  <!--============================================================-->
  <!-- These two keys support integrity rules 1,2,3 -->
  <!-- Build a hash of all the Act and Entity keys that we are keeping. This will be used to conditionally include related elements -->
  <xsl:key name="Act-localIds" match="//IM:Act[.//*[@keep='1']]" use="IM:actTempId"/>
  <xsl:key name="Act-localIds" match="//IM:Act[@keep='1']" use="IM:actTempId"/>
  <xsl:key name="Entity-localIds" match="//IM:Entity[.//*[@keep='1']]" use="IM:entityTempId"/>
  <xsl:key name="Entity-localIds" match="//IM:Entity[@keep='1']" use="IM:entityTempId"/>

  <!--  RULE 1: All ActRelationships that need to be preserved should be passed in its entirety -->
  <!--  a. targetActTempId is explicitly located in the post-transformed message. -->
  <!--  b. sourceActTempId is explicitly located in the post-transformed message. -->
  <!--  c. ActRelationships that don't meet the explicit criteria should be removed. -->
  <xsl:template match="IM:ActRelationship">
    <xsl:variable name="act1" select="key('Act-localIds', IM:targetActTempId)"/>
    <xsl:variable name="act2" select="key('Act-localIds', IM:sourceActTempId)"/>
    <!-- The existence of both keys indicates a valid relationship that must be kept -->
    <xsl:if test="$act1 and $act2">
      <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
      </xsl:copy>
    </xsl:if>
  </xsl:template>

  <!--  RULE 2: All Participations that need to be preserved should be passed in its entirety -->
  <!--  a. SubjectEntityTempId is explicitly located in the post-transformed message. -->
  <!--  b. actTempId is explicitly located in the post-transformed message. -->
  <!--  c. Participations that don't meet the explicit criteria should be removed. -->
  <xsl:template match="IM:Participation">
    <xsl:variable name="act" select="key('Act-localIds', IM:actTempId)"/>
    <xsl:variable name="entity" select="key('Entity-localIds', IM:subjectEntityTempId)"/>
    <!-- The existence of both keys indicates a valid relationship that must be kept -->
    <xsl:if test="$act and $entity">
      <xsl:copy>
        <xsl:attribute name="keep">1</xsl:attribute>
        <xsl:apply-templates/>
      </xsl:copy>
    </xsl:if>
  </xsl:template>

  <!--  RULE 3: All Roles that need to be preserved should be passed in its entirety -->
  <!--  a. subjectEntityTempId is explicitly located in the post-transformed message. -->
  <!--  b. scopingEntityTempId is explicitly located in the post-transformed message. -->
  <!--  c. Roles that don't meet the explicit criteria should be removed. -->
  <!-- If the scoping entity is not populated, or the scoping entity is kept, then keep the Role-->
  <xsl:template match="IM:Role">
    <xsl:variable name="entity1" select="key('Entity-localIds', IM:subjectEntityTempId)"/>
    <xsl:variable name="entity2" select="key('Entity-localIds', IM:scopingEntityTempId)"/>
    <!-- The existence of both keys indicates a valid relationship that must be kept -->
    <!-- If the subjectEntity is kept, then conditionally keep the Role-->
    <xsl:if test="$entity1">
        <xsl:copy>
          <xsl:attribute name="keep">1</xsl:attribute>
          <xsl:apply-templates/>
        </xsl:copy>
    </xsl:if>
  </xsl:template>

  <!--  RULE 4: For every Entity, Act, Association(Role, Participation, ActRelationship or -->
  <!--  EntityLocatorParticipation) or SUB tables that are passed through, the following . -->
  <!--  attributes if they exist should be passed through. -->
  <!--  * NOTE:  Participation, Role, and ActRelationship are included by integrity rules 1, 2, and 3 -->
  <!-- a. recordStatusCd -->
  <!-- b. recordStatusTime-->
  <!-- c. statusCd -->
  <!-- d. statusTime -->
  <!-- e. participationTypeCd -->
  <!-- f. versionCtrlNbr -->
  <xsl:template match="*[.//*[@keep='1']]/IM:recordStatusCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="*[.//*[@keep='1']]/IM:recordStatusTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="*[.//*[@keep='1']]/IM:statusCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="*[.//*[@keep='1']]/IM:statusTime">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="*[.//*[@keep='1']]/IM:participationTypeCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="*[.//*[@keep='1']]/IM:versionCtrlNbr">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>


  <!--  RULE 5: Every instance of EntityLocatorParticipation should have an instance of a  -->
  <!--  locator table ie, Postal, Physical or Tele below it. If the Postal, Physical, or Tele tables -->
  <!--  are not passed., the EntityLocatorParticipation should be excluded. -->
  <!-- CONTAINED BY ANOTHER RULE -->

  <!--  RULE 6: for every Entity or Act that is passed through: The following attributes need to be preserved if they exist -->
  <!-- a. classCd-->
  <!-- b. cd-->
  <xsl:template match="*[.//*[@keep='1']]/IM:classCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="*[.//*[@keep='1']]/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>


  <xsl:template match="IM:Entity[.//*[@keep='1']]/IM:person/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="IM:Entity[.//*[@keep='1']]/IM:organization/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>



<!--
  <xsl:template match="//IM:Act[.//*[@keep='1']]/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="//IM:Entity[.//*[@keep='1']]/IM:cd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
-->
  <!--  RULE 7: for every Entity or Act that is passed through: The following attributes need to be preserved if they exist -->
  <!-- b. actTempId-->
  <!-- c. entityTempId-->
  <xsl:template match="//IM:Act[.//*[@keep='1']]/IM:actTempId">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="//IM:Entity[.//*[@keep='1']]/IM:entityTempId">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- RULE 8: -->
  <!-- For every PersonRace that's passed through,  -->
  <!-- RaceCd needs to be included -->
  <!-- raceCategoryCd needs to be included -->
  <xsl:template match="//IM:personRace [.//*[@keep='1']]/IM:raceCd ">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="//IM:personRace [.//*[@keep='1']]/IM:raceCategoryCd ">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- RULE 9: -->
  <!-- For every PersonEthnicGroup that's passed through,  -->
  <!-- EthnicGroupCd needs to be included -->
  <xsl:template match="//IM:personEthnicGroup [.//*[@keep='1']]/IM:ethnicGroupCd ">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- RULE 10: -->
  <!-- For every PersonName that's passed through,  -->
  <!-- PersonNameSeq needs to be included -->
  <xsl:template match="//IM:personName[.//*[@keep='1']]/IM:personNameSeq">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- RULE 11: -->
  <!-- For every EntityId that's passed through,  -->
  <!-- EntityIdSeq needs to be included -->
  <xsl:template match="//IM:entityId[.//*[@keep='1']]/IM:entityIdSeq">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- RULE 12: -->
  <!-- For every OrganizationName that's passed through,  -->
  <!-- OrganizationNameSeq needs to be included -->
  <xsl:template match="//IM:organizationName[.//*[@keep='1']]/IM:organizationNameSeq">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- RULE 13: -->
  <!-- For every ManufacturedMaterial that's passed through,  -->
  <!-- ManufacturedMaterialSeq needs to be included -->
  <xsl:template match="//IM:ManufacturedMaterial[.//*[@keep='1']]/IM:manufacturedMaterialSeq">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- RULE 14: -->
  <!-- For every ActID that's passed through,  -->
  <!-- ActIdSeq needs to be included -->
  <xsl:template match="//IM:actId[.//*[@keep='1']]/IM:actIdSeq">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>


  <!-- RULES 15 thru 21 are obsolete with the addition of the new rule 21A: -->

  <!-- RULE 15: -->
  <!-- For every ObservationReason that's passed through,  -->
  <!-- ReasonCd needs to be included -->
<!--
  <xsl:template match="//IM:ObservationReason[.//*[@keep='1']]/IM:reasonCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
-->
  <!-- RULE 16: -->
  <!-- For every ObservationInterp that's passed through,  -->
  <!-- IntrepretationCd needs to be included  -->
  <!-- InterpretationDescTxt needs to be included -->
<!--
  <xsl:template match="//IM:ObservationInterp[.//*[@keep='1']]/IM:intrepretationCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="//IM:ObservationInterp[.//*[@keep='1']]/IM:interpretationDescTxt">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
-->
  <!-- RULE 17: -->
  <!-- For every ObsValueCodedMod that's passed through, -->
  <!-- Code needs to be included -->
  <!-- CodeModCd needs to be included -->
<!--
  <xsl:template match="//IM:ObservationValueCodedMod[.//*[@keep='1']]/IM:Code">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="//IM:ObservationInterp[.//*[@keep='1']]/IM:CodeModCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
-->
  <!-- RULE 18: -->
  <!-- For every ObsValueCoded that's passed through, -->
  <!-- Code -->
<!--
  <xsl:template match="//IM:obsValueCoded[.//*[@keep='1']]/IM:Code">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
-->
  <!-- RULE 19: -->
  <!-- For every ObsValueTxt that's passed through, -->
  <!-- ObsValueTxtSeq needs to be included -->
<!--
  <xsl:template match="//IM:obsValueTxt[.//*[@keep='1']]/IM:obsValueTxtSeq">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
-->
  <!-- RULE 20: -->
  <!-- For every ObsValueDate that's passed through, -->
  <!-- ObsValueDateSeq needs to be included -->
<!--
  <xsl:template match="//IM:obsValueDate[.//*[@keep='1']]/IM:obsValueDateSeq">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
-->
  <!-- RULE 21: -->
  <!-- For every ObsValueNumeric that's passed through, -->
  <!-- ObsValueNumericSeq needs to be included -->
<!--
  <xsl:template match="//IM:obsValueNumeric[.//*[@keep='1']]/IM:obsValueNumericSeq">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
-->

  <!-- RULE 21A: -->
  <!-- For every Act with a child observation that's passed through, -->
  <!-- the entire Act needs to be included -->
  <xsl:template match="//IM:Act[IM:observation//*[@keep='1']]">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="*"/>
    </xsl:copy>
  </xsl:template>



  <!-- RULE 22: -->
  <!-- For every ConfirmationMethod that's passed through, -->
  <!-- ConfirmationMethodCd needs to be included -->
  <xsl:template match="//IM:confirmationMethod[.//*[@keep='1']]/IM:confirmationMethodCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- DEC 10 2003 - BP -->
  <!-- Margaret Marshburn : would like to keep the ParticipationTypeCd on Person for all the entities *that are kept* -->
  <!-- BP : not much different than Rule #4 - however it may not have worked correctly -->
  <xsl:template match="//IM:Entity[.//*[@keep='1']]/IM:person/IM:participationTypeCd">
    <xsl:copy>
      <xsl:attribute name="keep">1</xsl:attribute>
      <xsl:copy-of select="text()|@*|comment()"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
