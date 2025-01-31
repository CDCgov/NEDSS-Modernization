//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\nedss\\NEDSSBusinessServicesLayer\\CDM\\MPRUpdateEngine\\MPRUpdateEngineDesign\\updatehandler\\MultiFieldUpdateAction.java

package gov.cdc.nedss.systemservice.mprupdateengine;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.systemservice.exception.MPRUpdateException;
import gov.cdc.nedss.systemservice.util.MPRUpdateEngineConstants;
import gov.cdc.nedss.systemservice.util.MPRUpdateEnigneUtil;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 This action class handles updates of MPR fields with multiple values.  It only
 updates the MPR fields in memory.
 */
class MultiFieldUpdateAction extends AbstractActionImpl {

	static final LogUtils logger =
		new LogUtils(MultiFieldUpdateAction.class.getName());
	List<Object> updateFields = new ArrayList<Object> ();

	/**
	  This is the default constructor for the class.
	 */
	public MultiFieldUpdateAction() {

	}

	/**
	 * This method performs the action on the object passed in the parameter.  It
	 * returns true, if the action is successful.
	 * @param object
	 * @return boolean
	 * @throws MPRUpdateException
	 */

	public boolean perform(MPRUpdateObjectStructure object)
		throws MPRUpdateException {
		if (object == null) {
			logger.debug("Null MPRUpdateVO object.");
			throw new MPRUpdateException("Null MPRUpdateVO object.");
		}
		MPRUpdateVO obj = (MPRUpdateVO) object;
		PersonVO mpr = obj.getMpr();
		Collection<Object>  col = obj.getPersonVOs();

		if (mpr == null || col == null) {
			logger.debug("Illformed MPRUpdateVO object.");
			throw new MPRUpdateException("Illformed MPRUpdateVO object.");
		}

		try {

			// loop through the collection.  use each item in the collection
			// to update mpr
			for (Iterator<Object> it = col.iterator(); it.hasNext();) {
				PersonVO newVO =
					(PersonVO) MPRUpdateEnigneUtil.cloneVO(it.next());
				if (newVO == null) {
					continue;
				}

				for (int i = 0; i < updateFields.size(); i++) {

					MultiUpdateField field =
						(MultiUpdateField) updateFields.get(i);

					// get the source collection.  The collection
					// is not the collection for the PersonVO object.
					// But the collection contains the actual
					// values for the PersonVO
					Method tmpMethod =
						mpr.getClass().getMethod(
							field.collectionFieldGetMethodName(),
							(Class[])null);
					Collection<Object>  mprCollection  =
						(Collection<Object>) tmpMethod.invoke(mpr, (Object[])null);
					Collection<Object>   newCollection  =
						(Collection<Object>) tmpMethod.invoke(newVO, (Object[])null);

					if ( newCollection  == null) {
						// simply continue
						continue;
					}
					if (mprCollection  == null) {
						// if no collection in mpr, create a new one.
						mprCollection  = new ArrayList<Object> ();
						Class<?>[] paraTypes = { Collection.class };
						tmpMethod =
							mpr.getClass().getMethod(
								field.collectionFieldSetMethodName(),
								paraTypes);
						Object[] para = { mprCollection  };
						tmpMethod.invoke(mpr, para);
					}
					for (Iterator<Object> iter = newCollection.iterator();
						iter.hasNext();
						) {
						Object element = iter.next();
						if (element == null) {
							continue;
						}
						if (!isValidItem(element, field)) {
							// not something we want to check on
							continue;
						}

						boolean updated =
							updateMprCollection(mprCollection, element, field);

						if (updated == false) {
							// no match found, append
							appendField(mpr, element, field);
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MPRUpdateException(e.toString(), e);
		}

		return true;
	}

	/**
	 * This method initializes the Action object.  The parameter specifies the
	 *location of the configuration file for the Action object.
	 * @param configFileName
	 */

	public void init(String configFileName) {
		MultiUpdateFieldsBuilder fieldsBuilder =
			new MultiUpdateFieldsBuilder(configFileName);
		if (fieldsBuilder.getUpdateFields() != null) {
			updateFields = fieldsBuilder.getUpdateFields();
		}
	}

	/**
	 *
	 * @param mprCollection
	 * @param element
	 * @param field
	 * @return boolean
	 * @throws MPRUpdateException
	 */

	private boolean updateMprCollection(
		Collection<Object>  mprCollection,
		Object element,
		MultiUpdateField field)
		throws MPRUpdateException {

		try {

			for (Iterator<Object> iter = mprCollection.iterator(); iter.hasNext();) {
				Object mprElement = iter.next();
				if (mprElement == null) {
					continue;
				}

				// do not worry about invalid item in the collection
				if (!isValidItem(mprElement, field)) {
					continue;
				}

				// now compare the value
				int numberOfFields = field.getElements().size();
				boolean isEqual = true;
				for (int i = 0; i < numberOfFields; i++) {

					Object newObj = element;
					Object mprObj = mprElement;
					MultiUpdateFieldElement fieldElement =
						(MultiUpdateFieldElement) field.getElements().get(i);
					String containerGetterName =
						fieldElement.containerFieldGetMethodName();
					Class<?> tmpClass = mprElement.getClass();

					Method tmpMethod = null;
					if (containerGetterName != null) {
						tmpMethod =
							tmpClass.getMethod(containerGetterName, (Class[])null);
						newObj = tmpMethod.invoke(element, (Object[])null);
						mprObj = tmpMethod.invoke(mprElement, (Object[])null);
					}
					Object tmpMprFieldValue = null;
					Object tmpNewFieldValue = null;
					if (newObj != null) {
						tmpMethod =
							newObj.getClass().getMethod(
								fieldElement.fieldGetMethodName(),
								(Class[])null);
						tmpNewFieldValue = tmpMethod.invoke(newObj, (Object[])null);
					}
					if (mprObj != null) {
						tmpMethod =
							mprObj.getClass().getMethod(
								fieldElement.fieldGetMethodName(),
								(Class[])null);
						tmpMprFieldValue = tmpMethod.invoke(mprObj, (Object[])null);
					}

					// treate blank as null
					if (tmpMprFieldValue != null
						&& tmpMprFieldValue.toString().trim().equals("")) {
						tmpMprFieldValue = null;
					}
					if (tmpNewFieldValue != null
						&& tmpNewFieldValue.toString().trim().equals("")) {
						tmpNewFieldValue = null;
					}

					if (tmpMprFieldValue == null && tmpNewFieldValue != null) {
						isEqual = false;
						break;
					} else if (
						tmpMprFieldValue != null && tmpNewFieldValue == null) {
						isEqual = false;
						break;
					} else if (
						tmpMprFieldValue != null
							&& tmpNewFieldValue != null
							&& !tmpMprFieldValue.equals(tmpNewFieldValue)) {
						isEqual = false;
						break;
					}
				}

				if (isEqual) {
					// now we found a match, update the AOD
					Method AODGetMethod =
						mprElement.getClass().getMethod(
							field.aODFieldGetMethodName(),
							(Class[])null);
					Timestamp newAOD =
						(Timestamp) AODGetMethod.invoke(element, (Object[])null);
					Timestamp mprAOD =
						(Timestamp) AODGetMethod.invoke(mprElement, (Object[])null);
					if ((mprAOD == null && newAOD != null)
						|| (newAOD != null && !newAOD.before(mprAOD))) {
						// new AOD update
						Class<?>[] paraTypes = { Timestamp.class };
						Method AODSetMethod =
							mprElement.getClass().getMethod(
								field.aODFieldSetMethodName(),
								paraTypes);
						Object[] para = { newAOD };
                                            AODSetMethod.invoke(mprElement, para);

                                              Class<?>[] paraType = { String.class };
                                              Method RecordStatusCdSetMethod = mprElement.getClass().getMethod("setRecordStatusCd", paraType);
                                              Object[] activePara = { "ACTIVE" };
                                              RecordStatusCdSetMethod.invoke(mprElement, activePara);
											  Method StatusCdSetMethod = mprElement.getClass().getMethod("setStatusCd", paraType);
											  Object[] aPara = { "A" };
											  StatusCdSetMethod.invoke(mprElement, aPara);

						AbstractVO tmpVO = (AbstractVO) mprElement;
						if (tmpVO.isItNew()) {
							tmpVO.setItDirty(false);
							tmpVO.setItDelete(false);
						} else {
							tmpVO.setItDirty(true);
							tmpVO.setItDelete(false);
						}
					}
					return true;
				}

			}

		} catch (Exception e) {
			throw new MPRUpdateException(e.toString(), e);
		}
		return false;
	}

	/**
	 * based on source ID to determine whether this is a
	 * valid update field.  We did not check the class for
	 * item
	 *
	 * @param item
	 * @param field
	 * @return
	 */

	private boolean isValidItem(Object item, MultiUpdateField field) {

		String key = field.getSoruceID();

		// based on source ID to determine whether this is a
		// valid update field.  We did not check the class for
		// item, probably should
		if (key.equals(MPRUpdateEngineConstants.ID_RACE)) {
			return true;
		} else if (key.equals(MPRUpdateEngineConstants.ID_TELEPHONE)) {
                  EntityLocatorParticipationDT element =
                          (EntityLocatorParticipationDT) item;
                  if (element.getClassCd() == null) {
                          return false;
                  } else if (!element.getClassCd().equals("TELE")) {
                          return false;
                  }
                  else
                  return true;

		} else if (key.equals(MPRUpdateEngineConstants.ID_PERSON_NAME)) {
			return true;
		} else if (key.equals(MPRUpdateEngineConstants.ID_NORMAL_ADDRESS)) {

			EntityLocatorParticipationDT element =
				(EntityLocatorParticipationDT) item;
			if (element.getClassCd() == null) {
				return false;
			} else if (!element.getClassCd().equals("PST")) {
				return false;
			} else if (
				element.getUseCd() != null
					&& ((element.getUseCd().equals("BIR")
						&& element.getCd() != null
						&& element.getCd().equals("F"))
						|| element.getUseCd().equals("DTH"))) {
				return false;
			}
			return true;
		} else if (key.equals(MPRUpdateEngineConstants.ID_NORMAL_ENTITY)) {
			EntityIdDT element = (EntityIdDT) item;
			if (element.getTypeCd() != null
				&& element.getTypeCd().equals("SS")
				&& element.getAssigningAuthorityCd() != null
				&& element.getAssigningAuthorityCd().equals("SSA")) {
				return false;
			}

			return true;
		}
		return false;
	}

	private void appendField(
		PersonVO personVO,
		Object element,
		MultiUpdateField field) {

		String key = field.getSoruceID();

		// based on source ID to determine whether this is a
		// valid update field.  We did not check the class for
		// item, probably should
		if (key.equals(MPRUpdateEngineConstants.ID_RACE)) {
			MPRUpdateEnigneUtil.appendPersonRace(
				personVO,
				(PersonRaceDT) element);
		} else if (key.equals(MPRUpdateEngineConstants.ID_TELEPHONE)) {
			MPRUpdateEnigneUtil.appendPersonTelephone(
				personVO,
				(EntityLocatorParticipationDT) element);
		} else if (key.equals(MPRUpdateEngineConstants.ID_PERSON_NAME)) {
			MPRUpdateEnigneUtil.appendPersonName(
				personVO,
				(PersonNameDT) element);
		} else if (key.equals(MPRUpdateEngineConstants.ID_NORMAL_ADDRESS)) {
			MPRUpdateEnigneUtil.appendPersonAddress(
				personVO,
				(EntityLocatorParticipationDT) element);
		} else if (key.equals(MPRUpdateEngineConstants.ID_NORMAL_ENTITY)) {
			MPRUpdateEnigneUtil.appendEntityId(personVO, (EntityIdDT) element);
		}
	}

}
