package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt;

import java.lang.Comparable;
import java.io.Serializable;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SRTLabResultDT implements SRTCode, Comparable, Serializable {

  private String labResultCd;
  private String laboratoryId;
  private String labResultDescTxt;
  private String lrOrganismNameInd;
  private String nbsUid;

  public String getLabResultCd(){
    return labResultCd;
  }
  public void setLabResultCd(String aLabResultCd){
    labResultCd = aLabResultCd;
  }

  public String getLaboratoryId(){
  return laboratoryId;
   }
  public void setLaboratoryId(String aLaboratoryId){
   laboratoryId = aLaboratoryId;
  }

  public Long getIndentLevel()
  {
    return null;
  }


  public String getLabResultDescTxt(){
   return labResultDescTxt;
  }
  public void setLabResultDescTxt(String aLabResultDescTxt){
    labResultDescTxt = aLabResultDescTxt;
  }


  public void setOrganismNameInd(String aLrOrganismNameInd){
   lrOrganismNameInd = aLrOrganismNameInd;
  }
  public String getOrganismNameInd(){
   return lrOrganismNameInd;
  }


 public SRTLabResultDT(SRTLabTestLabResultMappingDT srtLabTestLabResultMappingDT){
   this.labResultCd = srtLabTestLabResultMappingDT.getLabResultTestCd();
   this.laboratoryId = srtLabTestLabResultMappingDT.getLabResultLaboratoryId();
   this.labResultDescTxt = srtLabTestLabResultMappingDT.getLabResultDescTxt();
   this.lrOrganismNameInd = srtLabTestLabResultMappingDT.getLabResultOrganismNameInd();
 }


 public int compareTo(java.lang.Object o){
  if(!(o instanceof SRTLabResultDT))
    throw new ClassCastException();
  return this.labResultDescTxt.compareTo(((SRTLabResultDT)o).labResultDescTxt);
  }//end compareTo


  public SRTLabResultDT() {
  }

  public String getCode(){
    return this.labResultCd;
  }

  public String getDesc(){
    return this.labResultDescTxt;
  }

}