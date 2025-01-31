//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\nedss\\NEDSSBusinessServicesLayer\\CDM\\MPRUpdateEngine\\MPRUpdateEngineDesign\\updatehandler\\SingleFieldUpdateAction.java

package gov.cdc.nedss.systemservice.mprupdateengine;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.GregorianCalendar;

import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.systemservice.exception.MPRUpdateException;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

/**
 This action class handles updates of MPR fields with single values.  It only
 updates the MPR fields in memory.
 */
class SingleFieldUpdateAction
    extends AbstractActionImpl {
  private List<Object> updateFields = new ArrayList<Object> ();
  static final LogUtils logger =
      new LogUtils(SingleFieldUpdateAction.class.getName());
  /**
    This is the default constructor.
   */
  public SingleFieldUpdateAction() {

  }

  /**
    This method performs the action on the object passed in the parameter.  It
    returns true, if the action is successful.
   */
  public boolean perform(MPRUpdateObjectStructure object) throws
      MPRUpdateException {
    try {
      if (object == null) {
        logger.debug("Null MPRUpdateVO object.");
        throw new MPRUpdateException("Null MPRUpdateVO object.");
      }
      MPRUpdateVO obj = (MPRUpdateVO) object;
      PersonVO mpr = obj.getMpr();
      Collection<Object>  col = obj.getPersonVOs();
      String deathIndCdNo = null; // this is used to set death address

      if (mpr == null || col == null) {
        logger.debug("Illformed MPRUpdateVO object.");
        throw new MPRUpdateException("Illformed MPRUpdateVO object.");
      }

      // loop through all items in the collection.
      // use those items to update mpr
      for (Iterator<Object> iter = col.iterator(); iter.hasNext(); ) {
        PersonVO newVO =
            (PersonVO) MPRUpdateEnigneUtil.cloneVO(iter.next());
        if (newVO == null) {
          continue;
        }

        // loop through the fields that need verification
        for (int i = 0; i < updateFields.size(); i++) {

          SingleUpdateField field =
              (SingleUpdateField) updateFields.get(i);
          
          // check special update first.
          // field name should never be null if we get the config
          // file right.
          String fieldName = field.getFieldName();
          logger.debug("in for loop fieldName: " + fieldName);
          if (fieldName.equals("BirthTime")) {
            updateDOB(mpr, newVO);
            continue;
          }
          else if (fieldName.equals("EthnicGroupInd")) {
            updateEthnicity(mpr, newVO);
            continue;
          }
          else if (fieldName.equals("DeceasedIndCd")) {
            deathIndCdNo = updateDateOfDeath(mpr, newVO, deathIndCdNo);
            continue;
          }
          else if (fieldName.equals("eHARSId")) {
              updateEHARS(mpr, newVO);
              continue;
            }
          // get the source objects
          Object newSource =
              getSourceObject(newVO, field.getSourceID());

          if (newSource == null) {
            // We can only handles the object defined in the
            // getSourceObject method.
            if (field.getSourceID().equals("DeceasedAddress")) {
               updateMPRNullSource(mpr, newVO, deathIndCdNo, field);
               continue;
            }

            continue;
          }

          Method fieldGetMethod =
              newSource.getClass().getMethod(
              field.fieldGetMethodName(),
              (Class[])null);
          Object newValue = fieldGetMethod.invoke(newSource, (Object[])null);
          logger.debug("in for loop newValue: " + newValue);

          // check whether the new value is blank
          if (newValue == null
              || (newValue instanceof String
                  && ( (String) newValue).trim().equals(""))) {
            // we only check null pointer and empty string here, may
            // need to expand
            continue;
          }

          // handle as-of-date
          Object newAODSource =
              getSourceObject(newVO, field.getAODSourceID());
          Object mprAODSource =
              getSourceObject(mpr, field.getAODSourceID());

          if (newAODSource == null) {
            continue;
          }
          if (mprAODSource == null) {
            mprAODSource =
            createMprSourceObject(mpr, field.getAODSourceID());
          }

          Method AODGetMethod =
              newAODSource.getClass().getMethod(
              field.aODFieldGetMethodName(),
              (Class[])null);
          Timestamp newAOD =
              (Timestamp) AODGetMethod.invoke(newAODSource, (Object[])null);
          Timestamp mprAOD =
              (Timestamp) AODGetMethod.invoke(mprAODSource, (Object[])null);

          // treat null aod as 12-31-1875, i.e. the earlist time posible
          Object mprSource = null;
          boolean hasNewAOD =
              MPRUpdateEnigneUtil.hasNewerAOD(mprAOD, newAOD);
          if (hasNewAOD) {
            logger.debug("newAOD greater than oldAOD: ");
            // if we have new AOD, then update the mpr field
            // we use !before here, since several single field
            // may share one share one as-of-date
            mprSource = getSourceObject(mpr, field.getSourceID());
            if (mprSource == null) {
              logger.debug("Need to create a new one for mpr");
              mprSource =
                  createMprSourceObject(mpr, field.getSourceID());
              if (mprSource == null) {
                throw new MPRUpdateException(
                    "MPR single field to be updated is null.");
              }
            }
            Class<?>[] valueParaTypes = {
                newValue.getClass()};
            Method fieldSetMethod =
                newSource.getClass().getMethod(
                field.fieldSetMethodName(),
                valueParaTypes);
            Object[] valuePara = {
                newValue};
            fieldSetMethod.invoke(mprSource, valuePara);

            if (newAOD != null) {
              Class<?>[] AODParaTypes = {
                  newAOD.getClass()};
              Method AODSetMethod =
                  newAODSource.getClass().getMethod(
                  field.aODFieldSetMethodName(),
                  AODParaTypes);
              Object[] AODPara = {
                  newAOD};
              AODSetMethod.invoke(mprAODSource, AODPara);
            }
            // update the status flag
            AbstractVO updatedMPR = (AbstractVO) mprSource;
            if (updatedMPR.isItNew() == true) {
              updatedMPR.setItDirty(false);
              updatedMPR.setItDelete(false);
            }
            else {
              updatedMPR.setItDirty(true);
              updatedMPR.setItDelete(false);
            }

          }

        }

      }
      logger.debug(
          "in singlefieldupdateaction.perform(): "
          + mpr.getThePersonDT().getMothersMaidenNm());

    }
    catch (Exception e) {
      e.printStackTrace();
      throw new MPRUpdateException(e.toString(), e);

    }

    return true;
  }

  /**
    This method initializes the Action object.  The parameter specifies the
    location of the configuration file for the Action object.
   */
  public void init(String configFileName) {
    logger.debug("in SingleFieldUpdateAction.init");
    SingleUpdateFieldsBuilder fieldsBuilder =
        new SingleUpdateFieldsBuilder(configFileName);
    if (fieldsBuilder.getUpdateFields() != null) {
      updateFields = fieldsBuilder.getUpdateFields();
    }
  }

  private boolean updateDOB(PersonVO mpr, PersonVO newVO) throws
      MPRUpdateException {
    Timestamp newDOB = newVO.getThePersonDT().getBirthTime();
    Timestamp newCDOB = newVO.getThePersonDT().getBirthTimeCalc();
    Timestamp newAODSex = newVO.getThePersonDT().getAsOfDateSex();

    Timestamp mprDOB = mpr.getThePersonDT().getBirthTime();
    Timestamp mprCDOB = mpr.getThePersonDT().getBirthTimeCalc();
    Timestamp mprAODSex = mpr.getThePersonDT().getAsOfDateSex();

    boolean hasNewAOD =
        MPRUpdateEnigneUtil.hasNewerAOD(mprAODSex, newAODSex);
    if (hasNewAOD) {
      if (newDOB != null) {
        mpr.getThePersonDT().setBirthTime(newDOB);
        mpr.getThePersonDT().setBirthTimeCalc(newCDOB);
        if (mpr.getThePersonDT().isItNew()) {
          mpr.getThePersonDT().setItDirty(false);
          mpr.getThePersonDT().setItDelete(false);
          mpr.getThePersonDT().setAsOfDateSex(newAODSex);
        }
        else {
          mpr.getThePersonDT().setAsOfDateSex(newAODSex);
          mpr.getThePersonDT().setItDirty(true);
          mpr.getThePersonDT().setItDelete(false);
        }
      }
      else if (mprDOB == null && newDOB == null) {
        mpr.getThePersonDT().setBirthTimeCalc(newCDOB);
        mpr.getThePersonDT().setAsOfDateSex(newAODSex);
        if (mpr.getThePersonDT().isItNew()) {
          mpr.getThePersonDT().setItDirty(false);
          mpr.getThePersonDT().setItDelete(false);
        }
        else {
          mpr.getThePersonDT().setItDirty(true);
          mpr.getThePersonDT().setItDelete(false);
        }
      }
    }
    return true;
  }

  private boolean updateEthnicity(PersonVO mpr, PersonVO newVO) throws
      MPRUpdateException {

    String mprEthinicityInd = mpr.getThePersonDT().getEthnicGroupInd();
    String newEthinicityInd = newVO.getThePersonDT().getEthnicGroupInd();

    Timestamp newAODEthnicity =
        newVO.getThePersonDT().getAsOfDateEthnicity();
    Timestamp mprAODEthnicity = mpr.getThePersonDT().getAsOfDateEthnicity();

     ArrayList<Object> aList =
          (ArrayList<Object> ) mpr.getThePersonEthnicGroupDTCollection();
     if (aList == null) {
       aList = new ArrayList<Object> ();
     }

    boolean hasNewAOD =
        MPRUpdateEnigneUtil.hasNewerAOD(mprAODEthnicity, newAODEthnicity);
    if (hasNewAOD) {

      if ( (newEthinicityInd != null && !newEthinicityInd.equals(""))) {
        mpr.getThePersonDT().setEthnicGroupInd(newEthinicityInd);
        mpr.getThePersonDT().setAsOfDateEthnicity(newAODEthnicity);

        if (newEthinicityInd.trim().equals("2135-2")) { // if Hispanic
               updateEthnicityCollection(mpr, newVO, aList);
        }
        if (!newEthinicityInd.trim().equals("2135-2")) {
          // if revision is hispanic
            resetEthnicityCollection(aList);

        }
        mpr.setThePersonEthnicGroupDTCollection(aList);
        if (mpr.getThePersonDT().isItNew()) {
          mpr.getThePersonDT().setItDirty(false);
          mpr.getThePersonDT().setItDelete(false);
        }
        else {
          mpr.getThePersonDT().setItDirty(true);
          mpr.getThePersonDT().setItDelete(false);
        }

      }
    }
    else {
         if(mprEthinicityInd != null && mprEthinicityInd.trim().equals("2135-2")
           && newEthinicityInd != null && newEthinicityInd.trim().equals("2135-2")) {
              // we have a update on the sub fields
               updateEthnicityCollection(mpr, newVO, aList);
               mpr.setThePersonEthnicGroupDTCollection(aList);
         }
    }
    return true;
  }

  private void updateEthnicityCollection(PersonVO mpr, PersonVO newVO, ArrayList<Object> aList){
     //	cache old ethnic groups
     HashMap<Object,Object> hm = new HashMap<Object,Object>();
    Iterator<Object>  iter = aList.iterator();
     if (iter != null) {
       while (iter.hasNext()) {
          PersonEthnicGroupDT ethnicGroupDT =
               (PersonEthnicGroupDT) iter.next();
          //put them in a hashmap
          hm.put(
               ethnicGroupDT.getEthnicGroupCd(),
               ethnicGroupDT);
       }
     }

     Collection<Object>  newCol =
          newVO.getThePersonEthnicGroupDTCollection();
    Iterator<Object>  newItor = newCol.iterator();
     while (newItor.hasNext()) {
       PersonEthnicGroupDT newGroupDT =
            (PersonEthnicGroupDT) newItor.next();
       String newGroupCd = newGroupDT.getEthnicGroupCd();
       PersonEthnicGroupDT mprGroupDT =
            (PersonEthnicGroupDT) hm.get(newGroupCd);

       Long personUID = mpr.getThePersonDT().getPersonUid();
       newGroupDT.setPersonUid(personUID);
       newGroupDT.setRecordStatusCd(
            NEDSSConstants.RECORD_STATUS_ACTIVE);
       if (mprGroupDT != null) {
          // the newGroupDT is already there.
          if (mprGroupDT.isItNew()) {
            // this is a new DT, so we have not store it in the database
            // yet. we can simply remove it from the collection, then the new
            // one coming in should be new
            aList.remove(mprGroupDT);
            newGroupDT.setItDelete(false);
            newGroupDT.setItNew(true);
            newGroupDT.setItDirty(false);
          }
          else {
            // we need to change the flags for raceDT so it is marked as
            // deleted, then the new one should be dirty
            mprGroupDT.setRecordStatusCd(
                 NEDSSConstants.RECORD_STATUS_INACTIVE);
            mprGroupDT.setItDelete(true);
            mprGroupDT.setItNew(false);
            mprGroupDT.setItDirty(false);
            newGroupDT.setItNew(false);
            newGroupDT.setItDirty(true);
            newGroupDT.setItDelete(false);
          }
       }
       else {
          // append the new one
          newGroupDT.setItNew(true);
          newGroupDT.setItDirty(false);
          newGroupDT.setItDelete(false);
       }

       // append the new one
       aList.add(newGroupDT);
     }

  }

  private void resetEthnicityCollection(ArrayList<Object>  aList){
    Iterator<Object>  itor = aList.iterator();
     while (itor.hasNext()) {
       PersonEthnicGroupDT personEthnicGroupDT =
            (PersonEthnicGroupDT) itor.next();
       if (personEthnicGroupDT.isItNew()) {
          // this is a new DT, so we have not store it in the database
          // yet. we can simply remove it from the collection, then the new
          // one coming in should be new
          aList.remove(personEthnicGroupDT);
       }
       else {
          // we need to change the flags for raceDT so it is marked as
          // deleted, then the new one should be dirty
          personEthnicGroupDT.setRecordStatusCd(
               NEDSSConstants.RECORD_STATUS_INACTIVE);
          personEthnicGroupDT.setItDelete(true);
          personEthnicGroupDT.setItNew(false);
          personEthnicGroupDT.setItDirty(false);
       }
     }
  }

      private String updateDateOfDeath(PersonVO mpr, PersonVO newVO, String deathIndNo) throws
          MPRUpdateException {
        String newDeathInd = newVO.getThePersonDT().getDeceasedIndCd();
        Timestamp newDeathTime = newVO.getThePersonDT().getDeceasedTime();
        Timestamp newAODMorb = newVO.getThePersonDT().getAsOfDateMorbidity();

        String mprDeathInd = mpr.getThePersonDT().getDeceasedIndCd();
        Timestamp mprDeathTime = mpr.getThePersonDT().getDeceasedTime();
        Timestamp mprAODMorb = mpr.getThePersonDT().getAsOfDateMorbidity();

        boolean hasNewAOD =
            MPRUpdateEnigneUtil.hasNewerAOD(mprAODMorb, newAODMorb);
        if (hasNewAOD) {
          if (newDeathInd != null && !newDeathInd.equals("")) {
            mpr.getThePersonDT().setDeceasedIndCd(newDeathInd);
            if(newDeathInd.equals("N")||newDeathInd.equals("UNK"))
            {
              mpr.getThePersonDT().setDeceasedTime(null);
              deathIndNo = newDeathInd;
            }
            else if(newDeathInd.equals("Y"))
            {
              if(newDeathTime != null)
              mpr.getThePersonDT().setDeceasedTime(newDeathTime);
            }
            if (mpr.getThePersonDT().isItNew()) {
              mpr.getThePersonDT().setItDirty(false);
              mpr.getThePersonDT().setItDelete(false);
              mpr.getThePersonDT().setAsOfDateMorbidity(newAODMorb);
            }
            else {
              mpr.getThePersonDT().setAsOfDateMorbidity(newAODMorb);
              mpr.getThePersonDT().setItDirty(true);
              mpr.getThePersonDT().setItDelete(false);
            }
          }
        }
        return deathIndNo;
      }

  private boolean updateMPRNullSource(PersonVO mpr, PersonVO newVO, String deathIndNo, SingleUpdateField field) throws
      MPRUpdateException {

    Object mprSource = null;
    if(field.getSourceID().equals("DeceasedAddress"))
    {
         //mprSource = this.getSourceObject(mpr, field.getSourceID());
         Collection<Object>  col = mpr.getTheEntityLocatorParticipationDTCollection();
         if (col != null) {
           for (Iterator<Object> iter = col.iterator(); iter.hasNext(); ) {
             EntityLocatorParticipationDT element =
                 (EntityLocatorParticipationDT) iter.next();
             if (element.getClassCd() != null
                 && element.getClassCd().equals("PST")
                 && element.getUseCd() != null
                 && element.getUseCd().equals("DTH")
                 && element.getStatusCd() != null
                 && element.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)
                 && element.getRecordStatusCd() != null
                 && element.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {

               mprSource = element;
               break;
             }
           }
         }

         if(mprSource != null && deathIndNo != null && (deathIndNo.equals("N") || deathIndNo.equals("UNK")))
         {
           EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)mprSource;
           //setting the statusCd and recordStatusCd to inactive need to be changed as soon as decision is made to
           //which flags used
           elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
           elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
           elp.setItDelete(true);
         }
    }

    return true;
  }

  private boolean mergeFieldForMPRSWithSameAOD(PersonVO mpr, PersonVO newVO) throws
      MPRUpdateException {
    // TODO: implement the special rules to break AOD ties for
    // MPR merge
    return true;
  }

  private Object getSourceObject(PersonVO vo, String key) {
    // this is the helper method to return the object
    // containing the fields to be updated.
    if (vo == null || key == null) {
      return null;
    }
    else if (key.equals(MPRUpdateEngineConstants.ID_PERSON_ROOT)) {
      return vo.getThePersonDT();
    }
    else if (key.equals(MPRUpdateEngineConstants.ID_BIRTH_ADDRESS)) {
      Collection<Object>  col = vo.getTheEntityLocatorParticipationDTCollection();
      if (col == null) {
        return null;
      }
      for (Iterator<Object> iter = col.iterator(); iter.hasNext(); ) {
        EntityLocatorParticipationDT element =
            (EntityLocatorParticipationDT) iter.next();
        if (element.getClassCd() != null
            && element.getClassCd().equals("PST")
            && element.getUseCd() != null
            && element.getUseCd().equals("BIR")
            && element.getCd() != null
            && element.getCd().equals("F")) {
          return element.getThePostalLocatorDT();
        }
      }
      return null;
    }
    else if (key.equals(MPRUpdateEngineConstants.ID_DECEASED_ADDRESS)) {
      Collection<Object>  col = vo.getTheEntityLocatorParticipationDTCollection();
      if (col == null) {
        return null;
      }
      for (Iterator<Object> iter = col.iterator(); iter.hasNext(); ) {
        EntityLocatorParticipationDT element =
            (EntityLocatorParticipationDT) iter.next();
        if (element.getClassCd() != null
            && element.getClassCd().equals("PST")
            && element.getUseCd() != null
            && element.getUseCd().equals("DTH")
            && element.getStatusCd() != null
            && element.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)
            && element.getRecordStatusCd() != null
            && element.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {

          return element.getThePostalLocatorDT();
        }
      }
      return null;
    }
    else if (key.equals(MPRUpdateEngineConstants.ID_SSN)) {
      Collection<Object>  col = vo.getTheEntityIdDTCollection();
      if (col == null) {
        return null;
      }
      for (Iterator<Object> iter = col.iterator(); iter.hasNext(); ) {
        EntityIdDT element = (EntityIdDT) iter.next();
        if (element.getTypeCd() != null
            && element.getTypeCd().equals("SS")
            && element.getAssigningAuthorityCd() != null
            && element.getAssigningAuthorityCd().equals("SSA")) {
          return element;
        }
      }
      return null;
    }
    else {
      return null;
    }
  }

  private Object createMprSourceObject(PersonVO vo, String key) {
    // this is the helper method to return the object
    // containing the fields to be updated.
    if (vo == null || key == null) {
      return null;
    }
    else if (key.equals(MPRUpdateEngineConstants.ID_BIRTH_ADDRESS)) {
      EntityLocatorParticipationDT elp =
          MPRUpdateEnigneUtil.createPersonAddress(vo);
      elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      elp.setUseCd("BIR");
      elp.setCd("F");
      elp.setItDirty(false); // we just created this
      return elp.getThePostalLocatorDT();
    }
    else if (key.equals(MPRUpdateEngineConstants.ID_DECEASED_ADDRESS)) {
      EntityLocatorParticipationDT elp =
          MPRUpdateEnigneUtil.createPersonAddress(vo);
      elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      elp.setUseCd("DTH");
      elp.setCd("F");
      elp.setItDirty(false); // we just created this
      return elp.getThePostalLocatorDT();
    }
    else if (key.equals(MPRUpdateEngineConstants.ID_SSN)) {
      EntityIdDT eid = MPRUpdateEnigneUtil.createEntityId(vo);
      eid.setTypeCd("SS");
      eid.setAssigningAuthorityCd("SSA");
      eid.setItDirty(false); // we just created this
      return eid;
    }
    else {
      return null;
    }
  }

  private boolean updateEHARS(PersonVO mpr, PersonVO newVO) throws
  MPRUpdateException {
String newEHARS = newVO.getThePersonDT().getEharsId();
Timestamp newAODSex = newVO.getThePersonDT().getAsOfDateSex();

String mprEHARS = mpr.getThePersonDT().getEharsId();
Timestamp mprAODSex = mpr.getThePersonDT().getAsOfDateSex();

boolean hasNewAOD =
    MPRUpdateEnigneUtil.hasNewerAOD(mprAODSex, newAODSex);
if (hasNewAOD) {
  if (newEHARS != null) {
    mpr.getThePersonDT().setEharsId(newEHARS);
    if (mpr.getThePersonDT().isItNew()) {
      mpr.getThePersonDT().setItDirty(false);
      mpr.getThePersonDT().setItDelete(false);
      mpr.getThePersonDT().setAsOfDateSex(newAODSex);
    }
    else {
      mpr.getThePersonDT().setAsOfDateSex(newAODSex);
      mpr.getThePersonDT().setItDirty(true);
      mpr.getThePersonDT().setItDelete(false);
    }
  }
  else if (mprEHARS == null && newEHARS == null) {
    mpr.getThePersonDT().setAsOfDateSex(newAODSex);
    if (mpr.getThePersonDT().isItNew()) {
      mpr.getThePersonDT().setItDirty(false);
      mpr.getThePersonDT().setItDelete(false);
    }
    else {
      mpr.getThePersonDT().setItDirty(true);
      mpr.getThePersonDT().setItDelete(false);
    }
  }
}
return true;
}

  
}
