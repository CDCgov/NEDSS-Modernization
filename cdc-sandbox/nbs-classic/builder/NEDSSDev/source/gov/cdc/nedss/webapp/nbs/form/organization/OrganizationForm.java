
package gov.cdc.nedss.webapp.nbs.form.organization;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.util.*;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import org.apache.struts.action.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.util.*;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.webapp.nbs.form.util.*;

public class OrganizationForm
    extends CommonForm {

  private static final LogUtils logger = new LogUtils(OrganizationForm.class.
      getName());

  private Boolean loadedFromDB = new Boolean(false);

  private OrganizationVO organization = null;

  private ArrayList<Object> addressCollection;
  //private EntityLocatorParticipationDT address;
  private OrganizationNameDT name;
  private ArrayList<Object> telephoneCollection;
  private ArrayList<Object> physicalCollection;
  private EntityIdDT quickCodeIdDT;

 // private ArrayList<Object> ldfCollection;
  private String rootExtensionTextQuickCode;

  public void reset() {
    name = null;
    //address = null;
    organization = null;
    addressCollection  = null;
    telephoneCollection  = null;
    physicalCollection  = null;
    quickCodeIdDT = null;
    super.resetLDF();
  }

  public OrganizationVO getOrganization() {

    if (organization == null) {
      organization = new OrganizationVO();

    }
    return this.organization;
  }

  public ArrayList<Object> getAddressCollection() {
    return addressCollection;
  }

  public ArrayList<Object> getTelephoneCollection() {
    return telephoneCollection;
  }

  public ArrayList<Object> getPhysicalCollection() {
    return physicalCollection;
  }

 /* public ArrayList<Object> getLdfCollection() {
    return ldfCollection;
  }
*/
  public EntityIdDT getQuickCodeIdDT() {
    if (quickCodeIdDT == null) {
      quickCodeIdDT = new EntityIdDT();

    }
    return this.quickCodeIdDT;
  }

  public void setOrganization(OrganizationVO newVal) {

    this.organization = newVal;
  }

  public void setAddressCollection(ArrayList<Object>  addressCollection) {
    this.addressCollection  = addressCollection;
  }

  public void setTelephoneCollection(ArrayList<Object>  telephoneCollection) {
    this.telephoneCollection  = telephoneCollection;
  }

  public void setPhysicalCollection(ArrayList<Object>  physicalCollection) {
    this.physicalCollection  = physicalCollection;
  }
  /**
   *
   * @param physicalCollection
   */
 /* public void setLdfCollection(ArrayList<Object>  aLdfCollection) {
   this.ldfCollection  = aLdfCollection;
 }
*/


//  public void setAddress(EntityLocatorParticipationDT address) {
 //   this.address = address;
 // }





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

  /*     public EntityLocatorParticipationDT getAddress()
     {
        if (address == null)
         address = new EntityLocatorParticipationDT();
      return this.address;
     }
   */
  public void setName(OrganizationNameDT name) {
    this.name = name;
  }

  public OrganizationNameDT getName() {
    if (name == null) {
      name = new OrganizationNameDT();

    }
    return this.name;
  }

  public void setQuickCodeIdDT(EntityIdDT newVal) {
    this.quickCodeIdDT = newVal;
  }

  /*    public EntityLocatorParticipationDT getAddress(int index)
      {
        // this should really be in the constructor
        if (this.addressCollection  == null)
            this.addressCollection  = new ArrayList<Object> ();
        int currentSize = this.addressCollection.size();
        // check if we have a this many organizationNameDTs
        if (index < currentSize)
        {
          try {
            Object[] tempArray = this.addressCollection.toArray();
            Object tempObj  = tempArray[index];
       EntityLocatorParticipationDT tempDT = (EntityLocatorParticipationDT) tempObj;
            return tempDT;
          }
          catch (Exception e) {
             logger.debug(e); } // do nothing just continue
        }
         EntityLocatorParticipationDT tempDT = null;
          for (int i = currentSize; i < index+1; i++)
          {
            tempDT = new EntityLocatorParticipationDT();
            this.addressCollection.add(tempDT);
          }
          return tempDT;
      }
   */
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

  public EntityLocatorParticipationDT getPhysical(int index) {
    // this should really be in the constructor
    if (this.physicalCollection  == null) {
      this.physicalCollection  = new ArrayList<Object> ();
    }

    int currentSize = this.physicalCollection.size();

    // check if we have a this many organizationNameDTs
    if (index < currentSize) {
      try {
        Object[] tempArray = this.physicalCollection.toArray();

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
      this.physicalCollection.add(tempDT);
    }
    return tempDT;
  }

  /**
   * retrieve ldf dt from index
   * @param index
   * @return
   */
 /* public StateDefinedFieldDT getLdf(int index) {
    // this should really be in the constructor
    if (this.ldfCollection  == null) {
      this.ldfCollection  = new ArrayList<Object> ();
    }

    int currentSize = this.ldfCollection.size();

    // check if we have a this many organizationNameDTs
    if (index < currentSize) {
      try {
        Object[] tempArray = this.ldfCollection.toArray();

        Object tempObj = tempArray[index];

        StateDefinedFieldDT tempDT = (StateDefinedFieldDT) tempObj;

        return tempDT;
      }
      catch (Exception e) {
        logger.debug(e);
      } // do nothing just continue
    }
    StateDefinedFieldDT tempDT = null;

    for (int i = currentSize; i < index + 1; i++) {
      tempDT = new StateDefinedFieldDT();
      this.ldfCollection.add(tempDT);
    }
    return tempDT;
  }
*/
  public boolean isLoadedFromDB() {
    return (this.loadedFromDB.booleanValue());
  }

  public void setLoadedFromDB(boolean newVal) {
    this.loadedFromDB = new Boolean(newVal);
  }
  public String getRootExtensionTextQuickCode() {
    return rootExtensionTextQuickCode;
  }
  public void setRootExtensionTextQuickCode(String rootExtensionTextQuickCode) {
    this.rootExtensionTextQuickCode = rootExtensionTextQuickCode;
  }


}