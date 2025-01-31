/**
 * Title: ObservationSrchResultVO helper class.
 * Description: A helper class for person search result value objects
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

 package gov.cdc.nedss.entity.observation.vo;

import java.io.*;
import java.util.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.locator.dt.*;

public class LoincSrchResultVO implements Serializable {

  public LoincSrchResultVO() {
  }

  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }

  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }

  /**
    * Set method for the thePersonNameColl property.
    * Sets all the associated names for a person
    * @param personNameColl the new value of the thePersonNameColl property
    * @void Sets the current value of the thePersonNameColl property
    */
  public void setLoincColl(java.util.Collection<Object>  loincColl) {
    this.loincColl = loincColl;
  }

  /**
    * Access method for the thePersonNameColl property.
    * returns all the associated names for a person
    * @return the current value of the thePersonNameColl property
    */
  public java.util.Collection<Object>  getLoincColl() {
    return loincColl;
  }

  private java.util.Collection<Object>  loincColl;
  private String loincCd;

  public String getLoincCd(){
	return this.loincCd;
  }

  public void setLoincCd(String loincCd){
	this.loincCd = loincCd;
  }

  private String labName;

	public String getLabName(){
		return this.labName;
	}

	public void setLabName(String labName){
		this.labName = labName;
	}

}