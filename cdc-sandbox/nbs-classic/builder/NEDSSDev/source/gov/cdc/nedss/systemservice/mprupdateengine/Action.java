//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\nedss\\NEDSSBusinessServicesLayer\\CDM\\MPRUpdateEngine\\Design\\updatehandler\\Action.java

package gov.cdc.nedss.systemservice.mprupdateengine;

import gov.cdc.nedss.systemservice.exception.MPRUpdateException;


public interface Action 
{
   
   /**
    * @param updateVO
    * @return boolean
    * @throws 
    * gov.cdc.nedss.systemservice.mprupdateengine.exception.MPRUpdateE
    * xception
    * @roseuid 3E4425F20148
    */
   public boolean perform(MPRUpdateObjectStructure object) throws MPRUpdateException;
   
}
