package gov.cdc.nedss.webapp.nbs.form.ldf;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.util.*;

import org.apache.struts.action.ActionForm;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;

public class LdfForm
    extends ActionForm {

  private Boolean loadedFromDB = new Boolean(false);

  private ArrayList<StateDefinedFieldMetaDataDT> LdfCollection;
  private ArrayList<StateDefinedFieldMetaDataDT> CdfCollection;
  private ArrayList<StateDefinedFieldMetaDataDT> LegacyCollection;

  private java.util.ArrayList<Object> oldCollection;

  public void reset() {
    if (LdfCollection  != null) {
      for (int i = 0; LdfCollection.size() > i; i++) {
        LdfCollection.clear();
      }
    }
    if (CdfCollection  != null) {
     for (int i = 0; CdfCollection.size() > i; i++) {
       CdfCollection.clear();
     }
   }

   if (LegacyCollection  != null) {
        for (int i = 0; LegacyCollection.size() > i; i++) {
          LegacyCollection.clear();
        }
      }


    LdfCollection  = null;
    CdfCollection  = null;
    LegacyCollection  = null;
  }

  public ArrayList<StateDefinedFieldMetaDataDT> getLdfCollection() {
    return LdfCollection;
  }

  public void setLdfCollection(ArrayList<StateDefinedFieldMetaDataDT>  LdfCol) {
    this.LdfCollection  = LdfCol;
  }

  public ArrayList<StateDefinedFieldMetaDataDT> getCdfCollection() {
    return CdfCollection;
  }

  public void setCdfCollection(ArrayList<StateDefinedFieldMetaDataDT>  CdfCol) {
    this.CdfCollection  = CdfCol;
  }

  public ArrayList<StateDefinedFieldMetaDataDT> getLegacyCollection() {
    return LegacyCollection;
  }

  public void setLegacyCollection(ArrayList<StateDefinedFieldMetaDataDT>  LegacyColl) {
    this.LegacyCollection  = LegacyColl;
  }

  public StateDefinedFieldMetaDataDT getLdfi(int index) {
    // this should really be in the constructor
    if (this.LdfCollection  == null) {
      this.LdfCollection  = new ArrayList<StateDefinedFieldMetaDataDT> ();
    }

    int currentSize = this.LdfCollection.size();

    // check if we have a this many personNameDTs
    if (index < currentSize) {
      try {
        Object[] tempArray = this.LdfCollection.toArray();

        Object tempObj = tempArray[index];

        StateDefinedFieldMetaDataDT tempDT = (StateDefinedFieldMetaDataDT)
            tempObj;

        return tempDT;
      }
      catch (Exception e) {
        //##!! System.out.println(e);
      } // do nothing just continue
    }
    StateDefinedFieldMetaDataDT tempDT = null;

    for (int i = currentSize; i < index + 1; i++) {
      tempDT = new StateDefinedFieldMetaDataDT();
      this.LdfCollection.add(tempDT);
    }
    return tempDT;
  }

  public StateDefinedFieldMetaDataDT getLdfj(int index) {
  // this should really be in the constructor
  if (this.CdfCollection  == null) {
    this.CdfCollection  = new ArrayList<StateDefinedFieldMetaDataDT> ();
  }

  int currentSize = this.CdfCollection.size();

  // check if we have a this many personNameDTs
  if (index < currentSize) {
    try {
      Object[] tempArray = this.CdfCollection.toArray();

      Object tempObj = tempArray[index];

      StateDefinedFieldMetaDataDT tempDT = (StateDefinedFieldMetaDataDT)
          tempObj;

      return tempDT;
    }
    catch (Exception e) {
      //##!! System.out.println(e);
    } // do nothing just continue
  }
  StateDefinedFieldMetaDataDT tempDT = null;

  for (int i = currentSize; i < index + 1; i++) {
    tempDT = new StateDefinedFieldMetaDataDT();
    this.CdfCollection.add(tempDT);
  }
  return tempDT;
}

public StateDefinedFieldMetaDataDT getLdfk(int index) {
  // this should really be in the constructor
  if (this.LegacyCollection  == null) {
    this.LegacyCollection  = new ArrayList<StateDefinedFieldMetaDataDT> ();
  }

  int currentSize = this.LegacyCollection.size();

  // check if we have a this many personNameDTs
  if (index < currentSize) {
    try {
      Object[] tempArray = this.LegacyCollection.toArray();

      Object tempObj = tempArray[index];

      StateDefinedFieldMetaDataDT tempDT = (StateDefinedFieldMetaDataDT)
          tempObj;

      return tempDT;
    }
    catch (Exception e) {
      //##!! System.out.println(e);
    } // do nothing just continue
  }
  StateDefinedFieldMetaDataDT tempDT = null;

  for (int i = currentSize; i < index + 1; i++) {
    tempDT = new StateDefinedFieldMetaDataDT();
    this.LegacyCollection.add(tempDT);
  }
  return tempDT;
}


  public java.util.ArrayList<Object> getOldCollection() {
    return oldCollection;
  }

  public void setOldCollection(java.util.ArrayList<Object> oldCollection) {
    this.oldCollection  = oldCollection;
  }

}