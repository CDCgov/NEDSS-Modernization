package gov.cdc.nedss.entity.person.vo;

import java.io.Serializable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PersonEthnicityVO implements Serializable{
  public PersonEthnicityVO() {
  }
  private String ethnicGroupCd;
  private String ethnicGroupInd;
  private String ethnicGroupDescTxt;

  public String getEthnicGroupCd(){
    return ethnicGroupCd;
  }
  public void setEthnicGroupCd(String newValue){
    ethnicGroupCd = newValue;
  }
  public String getEthnicGroupInd(){
    return ethnicGroupInd;
  }
  public void setEthnicGroupInd(String newValue){
    ethnicGroupInd = newValue;
  }
  public String getEthnicGroupDescTxt(){
    return ethnicGroupDescTxt;
  }
  public void setEthnicGroupDescTxt(String newValue){
    ethnicGroupDescTxt = newValue;
  }

}