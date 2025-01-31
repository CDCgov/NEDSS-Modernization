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

public class PersonRaceVO implements Serializable {
  public PersonRaceVO() {
  }

  private String raceCd;
  private String raceCategory;
  private String raceDescTxt;

  public String getRaceCd(){
    return raceCd;
  }
  public void setRaceCd(String newValue){
    raceCd = newValue;
  }
  public String getRaceCategoryCd(){
    return raceCategory;
  }
  public void setRaceCategoryCd(String newValue){
    raceCategory = newValue;
  }
  public String getRaceDescTxt(){
    return raceDescTxt;
  }
  public void setRaceDescTxt(String newValue){
    raceDescTxt = newValue;
  }
}