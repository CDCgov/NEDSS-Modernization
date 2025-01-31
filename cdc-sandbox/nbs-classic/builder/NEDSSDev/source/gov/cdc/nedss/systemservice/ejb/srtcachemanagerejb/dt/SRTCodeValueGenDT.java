package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import java.sql.Timestamp;
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

public class SRTCodeValueGenDT implements SRTCode,Comparable,Serializable {
  private String code;
  private String codeDescTxt;
  private String codeShortDescTxt;

  public SRTCodeValueGenDT() {
  }
  public int compareTo(java.lang.Object o){
    if(!(o instanceof SRTCodeValueGenDT))
      throw new ClassCastException();
    return this.codeShortDescTxt.compareTo(((SRTCodeValueGenDT)o).codeShortDescTxt);
    }//end compareTo

  public boolean equals(Object o){
        if(!(o instanceof SRTCodeValueGenDT))
            return false;
        if(((SRTCodeValueGenDT)o).codeShortDescTxt.equals(codeShortDescTxt))
          return true;
        else return false;
      }//end overridden equals()


  public SRTCodeValueGenDT(SRTLabCodeValueGenRootDT rootDT) {
    this.code = rootDT.getCode();
    this.codeDescTxt = rootDT.getCodeDescTxt();
    this.codeShortDescTxt = rootDT.getCodeShortDescTxt();
  }

  public SRTCodeValueGenDT(SRTConditionCodeValueGenRootDT rootDT) {
    this.code = rootDT.getCode();
    this.codeDescTxt = rootDT.getCodeDescTxt();
    this.codeShortDescTxt = rootDT.getCodeShortDescTxt();
  }


  /**
   * @return
   */
  public String getCode() {
    return code;
  }
  /**
  * @param string
  */
 public void setCode(String string) {
   code = string;
 }

 public String getDesc(){
   return codeShortDescTxt;
 }
 public Long getIndentLevel()
   {
     return null;
   }


  /**
   * @return
   */
  public String getCodeDescTxt() {
    return codeDescTxt;
  }



  /**
   * @param string
   */
  public void setCodeDescTxt(String string) {
    codeDescTxt = string;
  }

  public String getCodeShortDescTxt() {
    return codeShortDescTxt;
  }

  /**
   * @param aCodeShortDescTxt
   */
  public void setCodeShortDescTxt(String aCodeShortDescTxt) {
    codeShortDescTxt = aCodeShortDescTxt;

  }

}