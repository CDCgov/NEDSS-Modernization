/**
 * Title:        DSSummaryReportProxyInfo.java
 * Description:  SummaryReportInfo  Object use to store the information
 *                in the session based on the Context
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       NEDSS Development Team
 * @version      1.0
 */
package gov.cdc.nedss.act.summaryreport.vo;
import java.io.Serializable;

public class DSSummaryReportInfo  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String county;
  private String mmwrWeek;
  private String mmwrYear;
  private String countyCdDesc;
  private String MMWRWeekOptions;
  private String condition;
  private String conditionCdDescTxt;
  public String getCounty() {
    return county;
  }
/**
 * sets the value of county property
 * @param county  the String
 */
  public void setCounty(String county) {
    this.county = county;
  }

  /**
  * sets the value of mmwrWeek property
  * @param mmwrWeek  the String
  */
  public void setMmwrWeek(String mmwrWeek) {
    this.mmwrWeek = mmwrWeek;
  }

 /**
 * Acces method for mmwrWeek property
 * @return mmwrWeek   the String
 */
  public String getMmwrWeek() {
    return mmwrWeek;
  }

 /**
 * sets the value of mmwrYear property
 * @param mmwrYear  the String
 */
  public void setMmwrYear(String mmwrYear) {
    this.mmwrYear = mmwrYear;
  }

 /**
 * Access method for mmwrYear property
 * @return mmwrYear  the String
 */
  public String getMmwrYear() {
    return mmwrYear;
  }

 /**
 * set the value of countyCdDesc property
 * @param countyCdDesc  the String
 */
  public void setCountyCdDesc(String countyCdDesc) {
    this.countyCdDesc = countyCdDesc;
  }

 /**
 * Access method for countyCdDesc property
 * @return countyCdDesc  the String
 */
  public String getCountyCdDesc() {
    return countyCdDesc;
  }
 /**
 * sets the value for MMWRWeekOptions properties
 * @param MMWRWeekOptions   the String
 */
  public void setMMWRWeekOptions(String MMWRWeekOptions) {
    this.MMWRWeekOptions = MMWRWeekOptions;
  }

 /**
 * Access method for MMWRWeekOptions  property
 * @return MMWRWeekOptions   the String
 */
  public String getMMWRWeekOptions() {
    return MMWRWeekOptions;
  }

 /**
 * Set the value of condition property
 * @param condition  the String
 */
  public void setCondition(String condition) {
    this.condition = condition;
  }

 /**
 * Access method for condition property
 * @return condition  the String
 */
  public String getCondition() {
    return condition;
  }

 /**
 * Set the value for conditionCdDescTxt  property
 * @param conditionCdDescTxt  the String
 */
  public void setConditionCdDescTxt(String conditionCdDescTxt) {
    this.conditionCdDescTxt = conditionCdDescTxt;
  }

 /**
 * Access method for conditionCdDescTxt  property
 * @return conditionCdDescTxt   the String
 */
  public String getConditionCdDescTxt() {
    return conditionCdDescTxt;
  }


}
