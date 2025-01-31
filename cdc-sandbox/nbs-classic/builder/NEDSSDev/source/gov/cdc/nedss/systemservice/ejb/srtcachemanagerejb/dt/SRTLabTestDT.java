package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt;
/**
 * <p>Title:SRTLabTestDT </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Mark Hankey
 * @version 1.0
 */

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import java.sql.Timestamp;
import java.lang.Comparable;
import java.io.Serializable;
import gov.cdc.nedss.systemservice.util.TestResultTestFilterDT;


 public class SRTLabTestDT  implements SRTCode, Comparable, Serializable{
  private String laboratoryId;
  private String labTestCd;
  private String labTestDescTxt;
  private String testTypeCd;
  private String drugTestInd;
  private Long indentLevel;


  public int compareTo(java.lang.Object o){
    if(!(o instanceof SRTLabTestDT))
      throw new ClassCastException();
    return this.labTestDescTxt.compareTo(((SRTLabTestDT)o).labTestDescTxt);
    }//end compareTo

  public boolean equals(Object o){
        if(!(o instanceof SRTLabTestDT))
            return false;
        if(((SRTLabTestDT)o).labTestDescTxt.equals(labTestDescTxt))
          return true;
        else return false;
      }//end overridden equals()

  public int hashCode()
  {
    return this.labTestDescTxt.hashCode();
  }

  public SRTLabTestDT() {
  }

  public SRTLabTestDT(TestResultTestFilterDT oldDT)
  {
    this.laboratoryId = oldDT.getLaboratoryId();
    this.labTestCd = oldDT.geLabTestCd();
    this.labTestDescTxt = oldDT.getLabTestDescTxt();
    this.testTypeCd = oldDT.getTestTypeCd();
    this.indentLevel = oldDT.getIndentLevel();

  }

  public String getLabTestCd()
 {
   return labTestCd;
 }

 /**
* @param aProgAreaCd
*/
public void setLabTestCd(String aLabTestCd)
{
 labTestCd = aLabTestCd;

}
public void setIndentLevel(Long aIndentLevel)
{
       indentLevel = aIndentLevel;

}
public Long getIndentLevel()
{
       return indentLevel;
}

 public String getTestTypeCd()
 {
   return testTypeCd;
 }

public void setTestTypeCd(String aTestTypeCd)
{
 testTypeCd = aTestTypeCd;

}


public String getLaboratoryId(){
   return laboratoryId;
 }
public void setLaboratoryId(String aLaboratoryId){
   laboratoryId = aLaboratoryId;
 }

public String getLabTestDescTxt(){
   return labTestDescTxt;
 }

public void setLabTestDescTxt(String aLabTestDescTxt){
   labTestDescTxt = aLabTestDescTxt;
 }
 public String getCode(){
   return labTestCd;
 }
 public String getDesc(){
   return labTestDescTxt;
 }

 public String getDrugTestInd(){
   return drugTestInd;
 }

 public void setDrugTestInd(String aDrugTestInd){
   drugTestInd = aDrugTestInd;
 }






}