package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt;
/**
 * <p>Title: SRTDisplayCodeDT</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:CSC </p>
 * @author Mark Hankey
 *
 */
import java.io.Serializable;



public class SRTDisplayCodeDT implements SRTCode,Serializable   {

  private String displayCd;

  public void setDisplayCd(String aDisplayCd){
  displayCd = aDisplayCd;
  }

  public String getDisplayCd(){
    return displayCd;
  }

  public SRTDisplayCodeDT() {
  }

  public String getCode(){
    return displayCd;
  }

  public String getDesc(){
    return displayCd;
  }
  public Long getIndentLevel()
  {
    return null;
  }


}