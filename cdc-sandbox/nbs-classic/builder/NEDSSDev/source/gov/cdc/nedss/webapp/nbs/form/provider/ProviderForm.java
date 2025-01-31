
package gov.cdc.nedss.webapp.nbs.form.provider;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.util.*;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import org.apache.struts.action.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.form.util.CommonForm;
import gov.cdc.nedss.webapp.nbs.util.*;
import gov.cdc.nedss.ldf.dt.*;

public class ProviderForm
    extends CommonForm {

  private static final LogUtils logger = new LogUtils(ProviderForm.class.
      getName());

  private Boolean loadedFromDB = new Boolean(false);

  private PersonVO provider = null;
  private EntityIdDT quickCodeIdDT = null;

  private ArrayList<Object> addressCollection;
  //private EntityLocatorParticipationDT address;
  private PersonNameDT name;
  private ArrayList<Object> telephoneCollection;

  public void reset() {
    name = null;
   // address = null;
    provider = null;
    quickCodeIdDT = null;
    addressCollection  = null;
    telephoneCollection  = null;
    super.resetLDF();

  }

  public PersonVO getProvider() {

    if (provider == null) {
      provider = new PersonVO();

    }
    return this.provider;
  }

  public ArrayList<Object> getAddressCollection() {
    return addressCollection;
  }

  public ArrayList<Object> getTelephoneCollection() {
    return telephoneCollection;
  }


  public EntityIdDT getQuickCodeIdDT(){
    if (this.quickCodeIdDT == null)
      this.quickCodeIdDT = new EntityIdDT();
    return this.quickCodeIdDT;

  }
  public void setQuickCode(EntityIdDT theQuickCodeIdDT){
    this.quickCodeIdDT = theQuickCodeIdDT;

  }

  public void setProvider(PersonVO newVal) {

    this.provider = newVal;
  }

  public void setAddressCollection(ArrayList<Object>  addressCollection) {
    this.addressCollection  = addressCollection;
  }

  public void setTelephoneCollection(ArrayList<Object>  telephoneCollection) {
    this.telephoneCollection  = telephoneCollection;
  }


  /*public void setAddress(EntityLocatorParticipationDT address) {
    this.address = address;
  }*/





  public EntityLocatorParticipationDT getAddress(int index) {
    // this should really be in the constructor
    if (this.addressCollection  == null) {
      this.addressCollection  = new ArrayList<Object> ();
    }

    int currentSize = this.addressCollection.size();

    // check if we have a this many personNameDTs
    if (index < currentSize) {
      try {
        Object[] tempArray = this.addressCollection.toArray();

        Object tempObj = tempArray[index];

        EntityLocatorParticipationDT tempDT = (EntityLocatorParticipationDT)
            tempObj;

        return tempDT;
      }
      catch (Exception e) {
        //##!! System.out.println(e);
      } // do nothing just continue
    }
    EntityLocatorParticipationDT tempDT = null;

    for (int i = currentSize; i < index + 1; i++) {
      tempDT = new EntityLocatorParticipationDT();
      this.addressCollection.add(tempDT);
    }
    return tempDT;
  }


  public EntityLocatorParticipationDT getTelephone(int index) {
    // this should really be in the constructor
    if (this.telephoneCollection  == null) {
      this.telephoneCollection  = new ArrayList<Object> ();
    }

    int currentSize = this.telephoneCollection.size();

    // check if we have a this many organizationNameDTs
    if (index < currentSize) {
      try {
        Object[] tempArray = this.telephoneCollection.toArray();

        Object tempObj = tempArray[index];

        EntityLocatorParticipationDT tempDT = (EntityLocatorParticipationDT)
            tempObj;

        return tempDT;
      }
      catch (Exception e) {
        logger.debug(e);
      } // do nothing just continue
    }
    EntityLocatorParticipationDT tempDT = null;

    for (int i = currentSize; i < index + 1; i++) {
      tempDT = new EntityLocatorParticipationDT();
      this.telephoneCollection.add(tempDT);
    }
    return tempDT;
  }



  public boolean isLoadedFromDB() {
    return (this.loadedFromDB.booleanValue());
  }

  public void setLoadedFromDB(boolean newVal) {
    this.loadedFromDB = new Boolean(newVal);
  }

}