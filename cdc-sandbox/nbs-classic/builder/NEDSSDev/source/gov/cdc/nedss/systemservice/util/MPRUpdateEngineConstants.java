//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\gov\\cdc\\nedss\\systemservice\\mprupdateengine\\util\\MPRUpdateEngineConstants.java

//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\nedss\\NEDSSBusinessServicesLayer\\CDM\\MPRUpdateEngine\\MPRUpdateEngineDesign\\util\\MPRUpdateEngineConstants.java

package gov.cdc.nedss.systemservice.util;


/**
This interface contains the constants defined for MPR Update Engine.
 */
public interface MPRUpdateEngineConstants
{
   String HANDLERS_CONFIG_FILENAME = System.getProperty("nbs.dir") + 
		System.getProperty("file.separator") + "Properties" + 
		System.getProperty("file.separator") + "mprupdateengine_config.xml";
   
   String DEFAULT_HANDLER = "default";

   /**
   Update handlers config XML tags
    */
   String HANDLER_ELEMENT = "handler";
   String HANDLER_ID_ATTRIBUTE = "id";
   String HANDLER_NAME_ATTRIBUTE = "name";
   String ACTIONS_ELEMENT = "actions";
   String ACTION_ELEMENT = "action";
   String ACTION_NAME_ATTRIBUTE = "name";
   String ACTION_CLASS_ELEMENT = "action-class";
   String ACTION_INIT_ID_ELEMENT = "init-id";
   String SINGLE_FIELD_UPDATE_CONFIG_FILENAME = System.getProperty("nbs.dir") + 
		System.getProperty("file.separator") + "Properties" + 
		System.getProperty("file.separator") + "singlefieldupdate_config.xml";
   
   String SINGLE_FIELD_INIT_ID = "single-update-action";
   
   String MULTI_FIELD_UPDATE_CONFIG_FILENAME = System.getProperty("nbs.dir") + 
		System.getProperty("file.separator") + "Properties" + 
		System.getProperty("file.separator") + "multifieldupdate_config.xml";

   String MULTI_FIELD_INIT_ID = "multi-update-action";

   /**
   Single field vrification config XML tags
    */
   String MPRSF_ROOT_ELEMENT = "MPR-update-fields";
   String MPRSF_FIELD_ELEMENT = "field";
   String MPRSF_FIELD_NAME_ATTRIBUTE = "name";
   String MPRSF_FIELD_SOURCE_ID_ATTRIBUTE = "source-id";
   String MPRSF_AOD_FIELD_ELEMENT = "aod-field";
   String MPRSF_AOD_FIELD_NAME_ATTRIBUTE = "name";
   String MPRSF_AOD_FIELD_SOURCE_ID_ATTRIBUTE = "aod-source-id";

   /**
   data source ids
    */
   String ID_PERSON_ROOT = "PersonRoot";
   String ID_BIRTH_ADDRESS = "BirthAddress";
   String ID_DECEASED_ADDRESS = "DeceasedAddress";
   String ID_SSN = "SSN";
   String ID_PERSON_NAME = "PersonName";
   String ID_NORMAL_ADDRESS = "NormalAddress";
   String ID_RACE = "Race";
   String ID_TELEPHONE = "Telephone";
   String ID_NORMAL_ENTITY = "NormalEntityID";

   /**
   Multi field vrification config XML tags
    */
   String MPRMF_ROOT_ELEMENT = "MPR-update-fields";
   String MPRMF_FIELD_ELEMENT = "field";
   String MPRMF_FIELD_SOURCE_ID_ATTRIBUTE = "source-id";
   String MPRMF_FIELD_COLLECTION_NAME_ATTRIBUTE = "collection-name";
   String MPRMF_SUB_FIELD_ELEMENT = "sub-field";
   String MPRMF_SUB_FIELD_NAME_ATTRIBUTE = "name";
   String MPRMF_SUB_FIELD_CONTAINER_NAME_ATTRIBUTE = "container-name";
   String MPRMF_AOD_FIELD_ELEMENT = "aod-field";
   String MPRMF_AOD_FIELD_NAME_ATTRIBUTE = "name";

   public static final long MILLS_BASE = 245669550419L;
   public static final long MILLS_IN_DAY = 86400000L;
   public static final long MILLS_IN_MONTH = MILLS_IN_DAY * 30;
   public static final long MILLS_IN_YEAR = MILLS_IN_DAY * 365;
}
